package com.copyright.rup.dist.foreign.service.impl.aclci;

import com.copyright.rup.common.logging.RupLogUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclciGradeGroupEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclciUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.aclci.IAclciUsageService;
import com.copyright.rup.dist.foreign.service.api.executor.IChainExecutor;
import com.copyright.rup.dist.foreign.service.api.processor.ChainProcessorTypeEnum;

import com.google.common.collect.Iterables;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of {@link IAclciUsageService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/12/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclciUsageService implements IAclciUsageService {

    private static final Logger LOGGER = RupLogUtils.getLogger();

    @Autowired
    private IAclciUsageRepository aclciUsageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    @Qualifier("usageChainExecutor")
    private IChainExecutor<Usage> chainExecutor;
    @Value("$RUP{dist.foreign.usages.batch_size}")
    private int usagesBatchSize;

    @Override
    @Transactional
    public void insertUsages(UsageBatch usageBatch, List<Usage> usages) {
        String userName = RupContextUtils.getUserName();
        int size = usages.size();
        LOGGER.info("Insert ACLCI usages. Started. UsageBatchName={}, UsagesCount={}, UserName={}",
            usageBatch.getName(), size, userName);
        usages.forEach(usage -> {
            usage.setBatchId(usageBatch.getId());
            usage.setCreateUser(userName);
            usage.setUpdateUser(userName);
            usage.getAclciUsage().setGradeGroup(
                AclciGradeGroupEnum.getGroupByGrade(usage.getAclciUsage().getReportedGrade()));
            aclciUsageRepository.insert(usage);
        });
        String loadedReason = "Uploaded in '" + usageBatch.getName() + "' Batch";
        usages.forEach(usage -> usageAuditService.logAction(usage.getId(), UsageActionTypeEnum.LOADED, loadedReason));
        LOGGER.info("Insert ACLCI usages. Finished. UsageBatchName={}, UsagesCount={}, UserName={}",
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
            ? aclciUsageRepository.findByIds(usageIds)
            : List.of();
    }

    @Override
    public int getUsagesCount(UsageFilter filter) {
        return !filter.isEmpty() ? aclciUsageRepository.findCountByFilter(filter) : 0;
    }

    @Override
    public List<UsageDto> getUsageDtos(UsageFilter filter, Pageable pageable, Sort sort) {
        return !filter.isEmpty()
            ? aclciUsageRepository.findDtosByFilter(filter, pageable, sort)
            : List.of();
    }

    @Override
    @Transactional
    public void updateToEligibleByIds(Set<String> usageIds, Long rhAccountNumber, Long wrWrkInst, String reason) {
        String userName = RupContextUtils.getUserName();
        LOGGER.info("Update RH for ACLCI usage. Started. UsageIds={}, RhAccountNumber={}, Reason={}, UserName={}",
            usageIds, rhAccountNumber, reason, userName);
        aclciUsageRepository.updateToEligibleByIds(usageIds, rhAccountNumber, wrWrkInst, userName);
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.RH_UPDATED, reason);
        usageAuditService.logAction(usageIds, UsageActionTypeEnum.ELIGIBLE,
            "Usage has become eligible. RH and Wr Wrk Inst were updated. Reason=" + reason);
        rightsholderService.updateRighstholdersAsync(Set.of(rhAccountNumber));
        LOGGER.info("Update RH for ACLCI usage. Finished. UsageIds={}, RhAccountNumber={}, Reason={}, UserName={}",
            usageIds, rhAccountNumber, reason, userName);
    }
}
