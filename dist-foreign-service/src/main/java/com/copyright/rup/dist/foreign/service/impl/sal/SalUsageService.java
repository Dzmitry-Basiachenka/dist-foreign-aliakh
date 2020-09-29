package com.copyright.rup.dist.foreign.service.impl.sal;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.common.util.LogUtils;
import com.copyright.rup.dist.common.util.LogUtils.ILogWrapper;
import com.copyright.rup.dist.foreign.domain.GradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of {@link ISalUsageService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/29/2020
 *
 * @author Aliaksandr Liakh
 */
@Service
public class SalUsageService implements ISalUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private ISalUsageRepository salUsageRepository;
    @Autowired
    @Qualifier("usageChainChunkExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? salUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? salUsageRepository.findDtosByFilter(filter, pageable, sort)
            : Collections.emptyList();
    }

    @Override
    public boolean workPortionIdExists(String workPortionId) {
        return salUsageRepository.workPortionIdExists(workPortionId);
    }

    @Override
    public boolean workPortionIdExists(String workPortionId, String batchId) {
        return salUsageRepository.workPortionIdExists(workPortionId, batchId);
    }

    @Override
    @Transactional
    public void insertItemBankDetails(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        ILogWrapper size = LogUtils.size(usages);
        LOGGER.info("Insert SAL item bank details. Started. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            salUsageRepository.insertItemBankDetail(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert SAL item bank details. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
    }

    @Override
    @Transactional
    public void insertUsageDataDetails(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        ILogWrapper size = LogUtils.size(usages);
        LOGGER.info("Insert SAL usage data details. Started. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            SalUsage salUsage = usage.getSalUsage();
            if (Objects.isNull(salUsage.getGrade())) {
                salUsage.setGrade(getItemBankDetailGradeByWorkPortionId(salUsage.getReportedWorkPortionId()));
            }
            usage.getSalUsage().setGradeGroup(GradeGroupEnum.getGroupByGrade(usage.getSalUsage().getGrade()));
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            salUsageRepository.insertUsageDataDetail(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert SAL usage data details. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
    }

    @Override
    public void sendForMatching(List<String> usageIds, String batchName) {
        AtomicInteger usageIdsCount = new AtomicInteger(0);
        chainExecutor.execute(() ->
            Iterables.partition(usageIds, usagesBatchSize)
                .forEach(partition -> {
                    usageIdsCount.addAndGet(partition.size());
                    LOGGER.info("Send usages for PI matching. Started. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                    chainExecutor.execute(getUsagesByIds(partition), ChainProcessorTypeEnum.MATCHING);
                    LOGGER.info("Send usages for PI matching. Finished. UsageBatchName={}, UsagesCount={}", batchName,
                        usageIdsCount);
                }));
    }

    @Override
    public List<Usage> getUsagesByIds(List<String> usageIds) {
        return CollectionUtils.isNotEmpty(usageIds)
            ? salUsageRepository.findByIds(usageIds)
            : Collections.emptyList();
    }

    @Override
    public String getItemBankDetailGradeByWorkPortionId(String workPortionId) {
        return salUsageRepository.findItemBankDetailGradeByWorkPortionId(workPortionId);
    }
}
