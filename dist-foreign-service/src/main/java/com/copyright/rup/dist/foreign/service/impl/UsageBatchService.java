package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.integration.pi.api.IPiIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
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

    private static final String GETTING_RIGHTS_FINISHED_LOG =
        "Send usages for getting rights. Finished. UsageBatchName={}, UsagesCount={}, WorkFoundUsagesCount={}";
    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    @Qualifier("df.integration.piIntegrationCacheService")
    private IPiIntegrationService piIntegrationService;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private INtsUsageService ntsUsageService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IChainExecutor<Usage> chainExecutor;

    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;

    private ExecutorService executorService;

    @Override
    public List<Integer> getFiscalYears(String productFamily) {
        return usageBatchRepository.findFiscalYearsByProductFamily(productFamily);
    }

    @Override
    public List<UsageBatch> getUsageBatches() {
        return usageBatchRepository.findAll();
    }

    @Override
    public List<UsageBatch> getUsageBatches(String productFamily) {
        return usageBatchRepository.findByProductFamily(productFamily);
    }

    @Override
    public UsageBatch getUsageBatchById(String batchId) {
        return usageBatchRepository.findById(batchId);
    }

    @Override
    public List<UsageBatch> getUsageBatchesForNtsFundPool() {
        return usageBatchRepository.findUsageBatchesForNtsFundPool();
    }

    @Override
    public boolean usageBatchExists(String name) {
        return 0 < usageBatchRepository.findCountByName(name);
    }

    @Override
    @Transactional
    public int insertFasBatch(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert usage batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        rightsholderService.updateRightsholder(usageBatch.getRro());
        populateTitlesStandardNumberAndType(usages);
        int count = fasUsageService.insertUsages(usageBatch, usages);
        Set<Long> accountNumbersToUpdate = usages.stream()
            .map(usage -> usage.getRightsholder().getAccountNumber())
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        LOGGER.info("Insert usage batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, count);
        rightsholderService.updateRighstholdersAsync(accountNumbersToUpdate);
        return count;
    }

    @Override
    @Transactional
    public List<String> insertNtsBatch(UsageBatch usageBatch, String userName) {
        LOGGER.info("Insert NTS batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setProductFamily(FdaConstants.NTS_PRODUCT_FAMILY);
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        usageBatchRepository.insert(usageBatch);
        rightsholderService.updateRighstholdersAsync(Collections.singleton(usageBatch.getRro().getAccountNumber()));
        List<String> ntsUsageIds = ntsUsageService.insertUsages(usageBatch);
        LOGGER.info("Insert NTS batch. Finished. UsageBatchName={}, UserName={}, UsagesCount={}",
            usageBatch.getName(), userName, LogUtils.size(ntsUsageIds));
        return ntsUsageIds;
    }

    @Override
    @Transactional
    public List<String> insertAaclBatch(UsageBatch usageBatch, List<Usage> uploadedUsages) {
        String userName = RupContextUtils.getUserName();
        usageBatch.setId(RupPersistUtils.generateUuid());
        usageBatch.setCreateUser(userName);
        usageBatch.setUpdateUser(userName);
        LOGGER.info("Insert AACL batch. Started. UsageBatchName={}, UserName={}", usageBatch.getName(), userName);
        usageBatchRepository.insert(usageBatch);
        aaclUsageService.insertUsages(usageBatch, uploadedUsages);
        List<String> baselineUsageIds = 0 < Objects.requireNonNull(usageBatch.getNumberOfBaselineYears())
            ? aaclUsageService.insertUsagesFromBaseline(usageBatch)
            : Collections.emptyList();
        LOGGER.info("Insert AACL batch. Finished. UsageBatchName={}, UserName={}, UploadedCount={}, BaselineCount={}",
            usageBatch.getName(), userName, LogUtils.size(uploadedUsages), LogUtils.size(baselineUsageIds));
        List<String> uploadedUsageIds = uploadedUsages.stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        return ListUtils.union(uploadedUsageIds, baselineUsageIds);
    }

    @Override
    public void sendForMatching(List<Usage> usages) {
        executorService.execute(() -> {
            List<Usage> usagesInNewStatus =
                usages.stream().filter(usage -> UsageStatusEnum.NEW == usage.getStatus()).collect(Collectors.toList());
            chainExecutor.execute(usagesInNewStatus, ChainProcessorTypeEnum.MATCHING);
        });
    }

    @Override
    public void sendAaclForMatching(List<String> usageIds, String batchName) {
        AtomicInteger usageIdsCount = new AtomicInteger(0);
        executorService.execute(() ->
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> {
                    usageIdsCount.addAndGet(partition.size());
                    LOGGER.info("Send usages for PI matching. Started. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                    chainExecutor.execute(aaclUsageService.getUsagesByIds(partition), ChainProcessorTypeEnum.MATCHING);
                    LOGGER.info("Send usages for PI matching. Finished. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                }));
    }

    @Override
    public void sendForGettingRights(List<Usage> usages, String batchName) {
        executorService.execute(() -> {
            LOGGER.info("Send usages for getting rights. Started. UsageBatchName={}, UsagesCount={}", batchName,
                LogUtils.size(usages));
            List<Usage> workFoundUsages =
                usages.stream()
                    .filter(usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus())
                    .collect(Collectors.toList());
            chainExecutor.execute(workFoundUsages, ChainProcessorTypeEnum.RIGHTS);
            LOGGER.info(GETTING_RIGHTS_FINISHED_LOG, batchName, LogUtils.size(usages), LogUtils.size(workFoundUsages));
        });
    }

    @Override
    public void sendNtsForGettingRights(List<String> usageIds, String batchName) {
        AtomicInteger usageIdsCount = new AtomicInteger(0);
        executorService.execute(() ->
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> {
                    usageIdsCount.addAndGet(partition.size());
                    LOGGER.info("Send usages for getting rights. Started. UsageBatchName={}, UsagesCount={}",
                        batchName, usageIdsCount);
                    List<Usage> workFoundUsages = usageService.getUsagesByIds(partition)
                        .stream()
                        .filter(usage -> UsageStatusEnum.WORK_FOUND == usage.getStatus())
                        .collect(Collectors.toList());
                    chainExecutor.execute(workFoundUsages, ChainProcessorTypeEnum.RIGHTS);
                    LOGGER.info(GETTING_RIGHTS_FINISHED_LOG, batchName, usageIdsCount, LogUtils.size(workFoundUsages));
                }));
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

    @Override
    @Transactional
    public void deleteAaclUsageBatch(UsageBatch usageBatch) {
        String userName = RupContextUtils.getUserName();
        String batchName = usageBatch.getName();
        LOGGER.info("Delete AACL usage batch. Started. UsageBatchName={}, UserName={}", batchName, userName);
        aaclUsageService.deleteUsageBatchDetails(usageBatch);
        usageBatchRepository.deleteUsageBatch(usageBatch.getId());
        LOGGER.info("Delete AACL usage batch. Finished. UsageBatchName={}, UserName={}", batchName, userName);
    }

    @Override
    public List<String> getBatchNamesWithUnclassifiedWorks(Set<String> batchIds) {
        return usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds, null);
    }

    @Override
    public Map<String, List<String>> getClassifcationToBatchNamesWithoutUsagesForStmOrNonStm(Set<String> batchIds) {
        Map<String, List<String>> classificationToBatchNamesMap = Maps.newHashMapWithExpectedSize(2);
        classificationToBatchNamesMap.put(FdaConstants.STM_CLASSIFICATION,
            usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds,
                FdaConstants.STM_CLASSIFICATION));
        classificationToBatchNamesMap.put(FdaConstants.NON_STM_CLASSIFICATION,
            usageBatchRepository.findBatchNamesWithoutUsagesForClassification(batchIds,
                FdaConstants.NON_STM_CLASSIFICATION));
        return classificationToBatchNamesMap;
    }

    @Override
    public List<String> getProcessingBatchesNames(Set<String> batchesIds) {
        return usageBatchRepository.findProcessingBatchesNames(batchesIds);
    }

    @Override
    public Map<String, String> getBatchesNamesToScenariosNames(Set<String> batchesIds) {
        return usageBatchRepository.findBatchesNamesToScenariosNames(batchesIds);
    }

    @Override
    public List<String> getBatchNamesAvailableForRightsAssignment() {
        return usageBatchRepository.findBatchNamesWithRhNotFoundUsages();
    }

    /**
     * Gets instance of {@link ExecutorService} with 2 threads.
     * Used for sending usages to queues to process them.
     *
     * @return instance of {@link ExecutorService}
     */
    protected ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
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

    private void populateTitlesStandardNumberAndType(List<Usage> usages) {
        usages.stream()
            .filter(usage -> Objects.nonNull(usage.getWrWrkInst()))
            .forEach(usage -> {
                Work work = piIntegrationService.findWorkByWrWrkInst(usage.getWrWrkInst());
                usage.setStandardNumberType(work.getMainIdnoType());
                usage.setStandardNumber(work.getMainIdno());
                usage.setWorkTitle(work.getMainTitle());
                usage.setSystemTitle(work.getMainTitle());
            });
    }
}
