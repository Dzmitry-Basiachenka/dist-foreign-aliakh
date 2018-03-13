package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        logAction(usageId, null, actionType, actionReason);
    }

    @Override
    public void logAction(Set<String> usageIds, UsageActionTypeEnum actionType, String actionReason) {
        usageIds.forEach(usageId -> logAction(usageId, null, actionType, actionReason));
    }

    @Override
    public void logAction(String usageId, Scenario scenario, UsageActionTypeEnum actionType, String actionReason) {
        usageAuditRepository.insert(buildUsageAuditItem(usageId, scenario, actionType, actionReason));
    }

    @Override
    public void deleteActions(String batchId) {
        usageAuditRepository.deleteByBatchId(batchId);
    }

    @Override
    public List<UsageAuditItem> getUsageAudit(String usageId) {
        return usageAuditRepository.findByUsageId(usageId);
    }

    private UsageAuditItem buildUsageAuditItem(String usageId, Scenario scenario, UsageActionTypeEnum actionType,
                                               String actionReason) {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setId(RupPersistUtils.generateUuid());
        usageAuditItem.setUsageId(usageId);
        usageAuditItem.setScenarioId(null != scenario ? scenario.getId() : null);
        usageAuditItem.setScenarioName(null != scenario ? scenario.getName() : null);
        usageAuditItem.setActionType(actionType);
        usageAuditItem.setActionReason(actionReason);
        String userName = RupContextUtils.getUserName();
        usageAuditItem.setCreateUser(userName);
        usageAuditItem.setUpdateUser(userName);
        return usageAuditItem;
    }
}
