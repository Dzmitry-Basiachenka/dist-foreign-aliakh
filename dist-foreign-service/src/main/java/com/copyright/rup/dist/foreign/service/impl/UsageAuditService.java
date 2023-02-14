package com.copyright.rup.dist.foreign.service.impl;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
    public void logAction(Iterable<String> usageIds, UsageActionTypeEnum actionType, String actionReason) {
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
    public void deleteActionsByScenarioId(String scenarioId) {
        usageAuditRepository.deleteByScenarioId(scenarioId);
    }

    @Override
    public void deleteActionsForExcludedByScenarioId(String scenarioId) {
        usageAuditRepository.deleteForExcludedByScenarioId(scenarioId);
    }

    @Override
    public List<UsageAuditItem> getUsageAudit(String usageId) {
        return usageAuditRepository.findByUsageId(usageId);
    }

    @Override
    public List<BatchStatistic> getBatchesStatisticByBatchNameAndDate(String batchName, LocalDate date) {
        return usageAuditRepository.findBatchesStatisticByBatchNameAndDate(batchName, date);
    }

    @Override
    public List<BatchStatistic> getBatchesStatisticByDateFromAndDateTo(LocalDate dateFrom, LocalDate dateTo) {
        checkArgument(null != dateFrom, "The parameter 'dateFrom' must be set");
        checkArgument(null != dateTo, "The parameter 'dateTo' must be set");
        checkArgument(dateFrom.compareTo(dateTo) <= 0,
            "The parameter 'dateFrom' must be less than or equal to the parameter 'dateTo'");
        return usageAuditRepository.findBatchesStatisticByDateFromAndDateTo(dateFrom, dateTo);
    }

    @Override
    public void deleteActionsForSalUsageData(String batchId) {
        usageAuditRepository.deleteForSalUsageDataByBatchId(batchId);
    }

    @Override
    public void deleteActionsForSalItemBankUsages(Set<String> workPortionIds) {
        usageAuditRepository.deleteForSalUsageDataByWorkPortionIds(workPortionIds);
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
}
