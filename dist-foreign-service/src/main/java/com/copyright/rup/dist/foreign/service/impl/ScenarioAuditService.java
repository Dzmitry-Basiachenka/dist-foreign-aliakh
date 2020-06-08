package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IScenarioAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
@Service
public class ScenarioAuditService implements IScenarioAuditService {

    @Autowired
    private IScenarioAuditRepository scenarioAuditRepository;

    @Override
    public void logAction(String scenarioId, ScenarioActionTypeEnum actionType, String actionReason) {
        scenarioAuditRepository.insert(buildScenarioAuditItem(scenarioId, actionType, actionReason));
    }

    @Override
    public void logAction(Set<String> scenarioIds, ScenarioActionTypeEnum actionType, String actionReason) {
        scenarioIds.forEach(scenarioId -> logAction(scenarioId, actionType, actionReason));
    }

    @Override
    public void deleteActions(String scenarioId) {
        scenarioAuditRepository.deleteByScenarioId(scenarioId);
    }

    @Override
    public List<ScenarioAuditItem> getActions(String scenarioId) {
        return scenarioAuditRepository.findByScenarioId(scenarioId);
    }

    @Override
    public boolean isAuditItemExist(String scenarioId, ScenarioActionTypeEnum actionType) {
        return scenarioAuditRepository.isAuditItemExist(scenarioId, actionType);
    }

    private ScenarioAuditItem buildScenarioAuditItem(String scenarioId, ScenarioActionTypeEnum actionType,
                                                     String actionReason) {
        ScenarioAuditItem scenarioAuditItem = new ScenarioAuditItem();
        scenarioAuditItem.setId(RupPersistUtils.generateUuid());
        scenarioAuditItem.setScenarioId(scenarioId);
        scenarioAuditItem.setActionType(actionType);
        scenarioAuditItem.setActionReason(actionReason);
        String userName = RupContextUtils.getUserName();
        scenarioAuditItem.setCreateUser(userName);
        scenarioAuditItem.setUpdateUser(userName);
        return scenarioAuditItem;
    }
}
