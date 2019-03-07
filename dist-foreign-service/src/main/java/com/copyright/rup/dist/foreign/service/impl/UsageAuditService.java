package com.copyright.rup.dist.foreign.service.impl;

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
            double loadedCount = statistic.getLoadedCount();
            statistic.setMatchedPercent(buildPercent(statistic.getMatchedCount() / loadedCount));
            statistic.setNtsWithDrawnPercent(buildPercent(statistic.getNtsWithDrawnCount() / loadedCount));
            statistic.setWorksNotFoundPercent(buildPercent(statistic.getWorksNotFoundCount() / loadedCount));
            statistic.setMultipleMatchingPercent(buildPercent(statistic.getMultipleMatchingCount() / loadedCount));
            statistic.setRhNotFoundPercent(buildPercent(statistic.getRhNotFoundCount() / loadedCount));
            statistic.setRhFoundPercent(buildPercent(statistic.getRhFoundCount() / loadedCount));
            statistic.setEligiblePercent(buildPercent(statistic.getEligibleCount() / loadedCount));
            statistic.setSendForRaPercent(buildPercent(statistic.getSendForRaCount() / loadedCount));
            statistic.setPaidPercent(buildPercent(statistic.getPaidCount() / loadedCount));
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
