package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link IAclCommonReportController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AclCommonReportController extends CommonController<IAclCommonReportWidget>
    implements IAclCommonReportController {

    @Autowired
    private IAclScenarioService scenarioService;

    @Override
    public List<Integer> getPeriods() {
        return scenarioService.getScenarioPeriods();
    }

    @Override
    public List<AclScenario> getScenarios(Integer period) {
        return scenarioService.getScenariosByPeriod(period);
    }

    @Override
    public IAclCommonReportWidget instantiateWidget() {
        return new AclCommonReportWidget();
    }
}
