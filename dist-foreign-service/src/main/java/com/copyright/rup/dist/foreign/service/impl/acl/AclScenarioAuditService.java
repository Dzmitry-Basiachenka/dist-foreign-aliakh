package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioAuditRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IAclScenarioAuditService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Aliaksandr Liakh
 */
@Service
public class AclScenarioAuditService implements IAclScenarioAuditService {

    @Autowired
    private IAclScenarioAuditRepository aclScenarioAuditRepository;

    @Override
    public void logAction(String scenarioId, ScenarioActionTypeEnum actionType, String actionReason) {
        aclScenarioAuditRepository.insert(buildScenarioAuditItem(scenarioId, actionType, actionReason));
    }

    @Override
    public List<ScenarioAuditItem> getActions(String scenarioId) {
        return aclScenarioAuditRepository.findByScenarioId(scenarioId);
    }

    @Override
    public void deleteActions(String scenarioId) {
        aclScenarioAuditRepository.deleteByScenarioId(scenarioId);
    }

    private ScenarioAuditItem buildScenarioAuditItem(String scenarioId, ScenarioActionTypeEnum actionType,
                                                     String actionReason) {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setId(RupPersistUtils.generateUuid());
        auditItem.setScenarioId(scenarioId);
        auditItem.setActionType(actionType);
        auditItem.setActionReason(actionReason);
        String userName = RupContextUtils.getUserName();
        auditItem.setCreateUser(userName);
        auditItem.setUpdateUser(userName);
        return auditItem;
    }
}
