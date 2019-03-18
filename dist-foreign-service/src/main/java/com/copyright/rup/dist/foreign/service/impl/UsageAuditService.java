package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IUsageAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 * @author Aliaksandr Radkevich
 */
@Service
public class UsageAuditService implements IUsageAuditService {

    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Override
    public void logAction(String usageId, UsageActionTypeEnum actionType, String actionReason) {
        usageAuditRepository.insert(buildUsageAuditItem(usageId, actionType, actionReason));
    }

    @Override
    public void logAction(Set<String> usageIds, UsageActionTypeEnum actionType, String actionReason) {
        usageIds.forEach(usageId -> logAction(usageId, actionType, actionReason));
    }

    @Override
    public void deleteActionsByBatchId(String batchId) {
        usageAuditRepository.deleteByBatchId(batchId);
    }

    @Override
    public void deleteActionsForArchivedByBatchId(String batchId) {
        usageAuditRepository.deleteForArchivedByBatchId(batchId);
    }

    @Override
    public void deleteActionsByUsageId(String usageId) {
        usageAuditRepository.deleteByUsageId(usageId);
    }

    @Override
    public List<UsageAuditItem> getUsageAudit(String usageId) {
        return usageAuditRepository.findByUsageId(usageId);
    }

    @Override
    public UsageBatchStatistic getBatchStatistic(String batchName, LocalDate date) {
        UsageBatchStatistic statistic = usageAuditRepository.findBatchStatistic(batchName, date);
        if (Objects.nonNull(statistic)) {
            statistic.setBatchName(batchName);
            statistic.setDate(date);
            if (0 == statistic.getTotalCount()) {
                throw new RupRuntimeException("Total usages count is 0 for the batch " + batchName);
            }
            double totalCount = statistic.getTotalCount();
            statistic.setLoadedPercent(buildPercent(statistic.getLoadedCount() / totalCount));
            statistic.setCreatedPercent(buildPercent(statistic.getCreatedCount() / totalCount));
            statistic.setMatchedPercent(buildPercent(statistic.getMatchedCount() / totalCount));
            statistic.setNtsWithDrawnPercent(buildPercent(statistic.getNtsWithDrawnCount() / totalCount));
            statistic.setWorksNotFoundPercent(buildPercent(statistic.getWorksNotFoundCount() / totalCount));
            statistic.setMultipleMatchingPercent(buildPercent(statistic.getMultipleMatchingCount() / totalCount));
            statistic.setRhNotFoundPercent(buildPercent(statistic.getRhNotFoundCount() / totalCount));
            statistic.setRhFoundPercent(buildPercent(statistic.getRhFoundCount() / totalCount));
            statistic.setEligiblePercent(buildPercent(statistic.getEligibleCount() / totalCount));
            statistic.setSendForRaPercent(buildPercent(statistic.getSendForRaCount() / totalCount));
            statistic.setPaidPercent(buildPercent(statistic.getPaidCount() / totalCount));
        }
        return statistic;
    }

    private UsageAuditItem buildUsageAuditItem(String usageId, UsageActionTypeEnum actionType, String actionReason) {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setId(RupPersistUtils.generateUuid());
        usageAuditItem.setUsageId(usageId);
        usageAuditItem.setActionType(actionType);
        usageAuditItem.setActionReason(actionReason);
        String userName = RupContextUtils.getUserName();
        usageAuditItem.setCreateUser(userName);
        usageAuditItem.setUpdateUser(userName);
        Date currentDate = Date.from(Instant.now().atZone(ZoneId.systemDefault()).toInstant());
        usageAuditItem.setCreateDate(currentDate);
        usageAuditItem.setUpdateDate(currentDate);
        return usageAuditItem;
    }

    private BigDecimal buildPercent(double value) {
        return new BigDecimal(String.valueOf(value * 100)).setScale(1, RoundingMode.HALF_UP);
    }
}
