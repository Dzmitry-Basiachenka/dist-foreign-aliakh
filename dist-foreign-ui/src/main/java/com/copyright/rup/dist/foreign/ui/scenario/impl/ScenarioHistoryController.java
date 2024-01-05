package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IScenarioHistoryController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenarioHistoryController extends CommonController<IScenarioHistoryWidget>
    implements IScenarioHistoryController {

    private static final long serialVersionUID = 5318311842924613754L;

    @Autowired
    private IScenarioAuditService scenarioAuditService;

    @Override
    public List<ScenarioAuditItem> getActions(String scenarioId) {
        return scenarioAuditService.getActions(scenarioId);
    }

    @Override
    protected IScenarioHistoryWidget instantiateWidget() {
        return new ScenarioHistoryWidget();
    }
}
