package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public List<Integer> getFiscalYears() {
        return usageBatchRepository.findFiscalYears();
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchRepository.findAll();
    }

    @Override
    public List<UsageBatch> getUsageBatchesForFilter() {
        return usageBatchRepository.findForFilter();
    }

    @Override
    public boolean usageBatchExists(String name) {
        return 0 < usageBatchRepository.findCountByName(name);
    }

    @Override
    @Transactional
    @Profiled(tag = "service.UsageBatchService.insertUsageBatch")
    public int insertUsageBatch(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert usage batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        LOGGER.info("Insert usage batch. Finished. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        rightsholderService.updateRightsholder(usageBatch.getRro());
        int count = usageService.insertUsages(usageBatch, usages);
        executorService.execute(() -> updateRightsholders(usages));
        return count;
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
     * Post construct method.
     */
    @PostConstruct
    void postConstruct() {
        executorService = Executors.newSingleThreadExecutor();
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
     * @param usages list of usages
     */
    void updateRightsholders(List<Usage> usages) {
        synchronized (this) {
            rightsholderService.updateRightsholders(
                usages.stream().map(usage -> usage.getRightsholder().getAccountNumber()).collect(Collectors.toSet()));
        }
    }
}
