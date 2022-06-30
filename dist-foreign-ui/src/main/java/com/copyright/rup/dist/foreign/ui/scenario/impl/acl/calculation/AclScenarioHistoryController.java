package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclScenarioHistoryController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AclScenarioHistoryController extends CommonController<IAclScenarioHistoryWidget>
    implements IAclScenarioHistoryController {

    @Autowired
    private IAclScenarioAuditService aclScenarioAuditService;

    @Override
    public List<ScenarioAuditItem> getActions(String scenarioId) {
        return aclScenarioAuditService.getActions(scenarioId);
    }

    @Override
    protected IAclScenarioHistoryWidget instantiateWidget() {
        return new AclScenarioHistoryWidget();
    }
}
