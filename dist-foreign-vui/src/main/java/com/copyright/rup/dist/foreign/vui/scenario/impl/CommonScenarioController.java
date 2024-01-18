package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PipedOutputStream;
import java.util.List;

/**
 * Common controller for {@link ICommonScenarioWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenarioController extends CommonController<ICommonScenarioWidget>
    implements ICommonScenarioController {

    private static final long serialVersionUID = 5620979618138270846L;

    private Scenario scenario;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IScenarioService scenarioService;

    @Override
    public int getSize() {
        return usageService.getRightsholderTotalsHolderCountByScenario(scenario, getWidget().getSearchValue());
    }

    @Override
    public boolean isScenarioEmpty() {
        return usageService.isScenarioEmpty(getScenario());
    }

    @Override
    public List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getRightsholderTotalsHoldersByScenario(scenario, getWidget().getSearchValue(),
            new Pageable(startIndex, count), sort);
    }

    @Override
    public void performSearch() {
        getWidget().applySearch();
    }

    @Override
    public void onRightsholderAccountNumberClicked(Long accountNumber, String rhName) {
        //TODO: {dbasiachenka} implement
        Windows.showNotificationWindow(StringUtils.EMPTY);
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    @Override
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction() {
        return scenarioService.getScenarioWithAmountsAndLastAction(scenario);
    }

    protected IUsageService getUsageService() {
        return usageService;
    }

    /**
     * Writes scenario usages into csv output stream.
     *
     * @param scenarioForReport a {@link Scenario}
     * @param pos               a {@link PipedOutputStream} instance
     */
    protected abstract void writeScenarioUsagesCsvReport(Scenario scenarioForReport, PipedOutputStream pos);
}
