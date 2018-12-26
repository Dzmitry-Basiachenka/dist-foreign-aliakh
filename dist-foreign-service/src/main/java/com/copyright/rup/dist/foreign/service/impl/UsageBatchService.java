package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Implementation of {@link IUsageBatchService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/03/2017
 *
 * @author Mikalai Bezmen
 * @author Aliaksandr Radkevich
 */
@Service
public class UsageBatchService implements IUsageBatchService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    private ExecutorService executorService;

    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IChainExecutor<Usage> chainExecutor;

    @Override
    public List<Integer> getFiscalYears() {
        return usageBatchRepository.findFiscalYears();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchRepository.findAll();
    }

    @Override
    public boolean usageBatchExists(String name) {
        return 0 < usageBatchRepository.findCountByName(name);
    }

    @Override
    @Transactional
    public int insertFasBatch(UsageBatch usageBatch, Collection<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert usage batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        rightsholderService.updateRightsholder(usageBatch.getRro());
        int count = usageService.insertUsages(usageBatch, usages);
        Set<Long> accountNumbersToUpdate = usages.stream()
            .map(usage -> usage.getRightsholder().getAccountNumber())
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        LOGGER.info("Insert usage batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, count);
        executorService.execute(() -> updateRightsholders(accountNumbersToUpdate));
        return count;
    }

    @Override
    @Transactional
    public List<Usage> insertNtsBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert NTS batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        List<Usage> ntsBatchUsages = usageService.getUsagesForNtsBatch(usageBatch);
        usageService.insertNtsUsages(usageBatch, ntsBatchUsages);
        LOGGER.info("Insert NTS batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, LogUtils.size(ntsBatchUsages));
        return ntsBatchUsages;
    }

    @Override
    public void sendForMatching(Collection<Usage> usages) {
        List<Usage> usagesInNewStatus =
            usages.stream().filter(usage -> UsageStatusEnum.NEW == usage.getStatus()).collect(Collectors.toList());
        chainExecutor.execute(usagesInNewStatus, ChainProcessorTypeEnum.MATCHING);
    }

    @Override
    public void sendForGettingRights(Collection<Usage> usages) {
        List<Usage> workFoundUsages =
            usages.stream()
                .filter(usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus())
                .collect(Collectors.toList());
        chainExecutor.execute(workFoundUsages, ChainProcessorTypeEnum.RIGHTS);
    }

    @Override
    @Transactional
    public void deleteUsageBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Delete usage batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageService.deleteUsageBatchDetails(usageBatch);
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        LOGGER.info("Delete usage batch. Finished. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
    }

    /**
     * Gets instance of {@link ExecutorService} with 2 threads.
     * First is for update rightsholders task.
     * Second is for sending usages on queue for PI matching.
     *
     * @return instance of {@link ExecutorService}
     */
    protected ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(2);
    }

    /**
     * Post construct method.
     */
    @PostConstruct
    void postConstruct() {
        executorService = getExecutorService();
    }

    /**
     * Pre destroy method.
     */
    @PreDestroy
    void preDestroy() {
        executorService.shutdown();
    }

    /**
     * Updates rightsholders based on provided list of {@link Usage}s.
     *
     * @param rightsholdersIds list of rightsholdersIds
     */
    void updateRightsholders(Set<Long> rightsholdersIds) {
        synchronized (this) {
            rightsholderService.updateRightsholders(rightsholdersIds);
        }
    }
}
