package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.repository.api.Sort;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.widget.api.CommonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Controller class for {@link ScenarioWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/06/17
 *
 * @author Ihar Suvorau
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenarioController extends CommonController<IScenarioWidget> implements IScenarioController {

    private Scenario scenario;

    @Autowired
    private IUsageService usageService;

    @Override
    public int getSize() {
        return usageService.getRightsholderTotalsHolderCountByScenarioId(scenario.getId(),
            getWidget().getSearchValue());
    }

    @Override
    public List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, Object[] sortPropertyIds,
                                                    boolean... sortStates) {
        return usageService.getRightsholderTotalsHoldersByScenarioId(scenario.getId(), getWidget().getSearchValue(),
            new Pageable(startIndex, count), Sort.create(sortPropertyIds, sortStates));
    }

    @Override
    public void performSearch() {
        getWidget().applySearch();
    }

    @Override
    protected IScenarioWidget instantiateWidget() {
        return new ScenarioWidget();
    }

    /**
     * Sets scenario.
     *
     * @param scenario instance of {@link Scenario}
     */
    void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * @return instance of {@link Scenario}.
     */
    Scenario getScenario() {
        return scenario;
    }
}
