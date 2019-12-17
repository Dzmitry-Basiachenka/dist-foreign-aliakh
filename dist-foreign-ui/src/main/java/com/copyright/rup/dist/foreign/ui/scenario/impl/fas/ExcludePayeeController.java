package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link IExcludePayeeController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExcludePayeeController extends CommonController<IExcludePayeeWidget> implements IExcludePayeeController {

    private Scenario scenario;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IExcludePayeeFilterController payeesFilterController;

    @Override
    public IExcludePayeeFilterController getExcludePayeesFilterController() {
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
        usageService.deleteFromScenarioByPayees(scenario.getId(), payeeAccountNumbers, reason);
    }

    @Override
    public void redesignateDetails(Set<Long> payeeAccountNumbers, String reason) {
        usageService.redesignateToNtsWithdrawnByPayees(scenario.getId(), payeeAccountNumbers, reason);
    }

    @Override
    protected IExcludePayeeWidget instantiateWidget() {
        return new ExcludePayeeWidget();
    }

    @Override
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
