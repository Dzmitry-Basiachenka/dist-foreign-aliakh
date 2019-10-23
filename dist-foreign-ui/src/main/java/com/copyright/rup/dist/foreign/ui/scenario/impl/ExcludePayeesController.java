package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterController;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IExcludePayeesController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcludePayeesController extends CommonController<IExcludePayeeWidget> implements IExcludePayeesController {

    private Scenario scenario;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IExcludePayeesFilterController payeesFilterController;

    @Override
    public IExcludePayeesFilterController getExcludePayeesFilterController() {
        return payeesFilterController;
    }

    @Override
    public void onFilterChanged() {
        getWidget().refresh();
    }

    @Override
    public List<PayeeTotalHolder> getPayeeTotalHolders() {
        return usageService.getPayeeTotalHoldersByScenarioId(scenario.getId());
    }

    @Override
    public void excludeDetails(Set<Long> payeeAccountNumbers, String reason) {
        usageService.deleteFromScenarioByPayees(scenario, payeeAccountNumbers, reason);
    }

    @Override
    public void redesignateDetails(Set<Long> payeeAccountNumbers, String reason) {
        usageService.redesignateByPayees(scenario, payeeAccountNumbers, reason);
    }

    @Override
    protected IExcludePayeeWidget instantiateWidget() {
        return new ExcludePayeesWidget();
    }

    @Override
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
