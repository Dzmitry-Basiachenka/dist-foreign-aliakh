package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.common.ExportStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
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
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IDrillDownByRightsholderController drillDownByRightsholderController;
    @Autowired
    private IReportService reportService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Override
    public int getSize() {
        return usageService.getRightsholderTotalsHolderCountByScenario(scenario, getWidget().getSearchValue());
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
        drillDownByRightsholderController.showWidget(accountNumber, rhName, scenario);
    }

    @Override
    public IStreamSource getExportScenarioUsagesStreamSource() {
        return new ExportStreamSource(scenario.getName() + "_",
            pipedStream -> reportService.writeScenarioUsagesCsvReport(scenario, pipedStream));
    }

    @Override
    protected IScenarioWidget instantiateWidget() {
        return new ScenarioWidget();
    }

    @Override
    public void onExcludeDetailsClicked() {
        if (0 < rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.APPROVED)) {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.info.exclude_details.reconciled_scenario"));
        } else {
            Windows.showModalWindow(new ExcludeSourceRroWindow(this));
        }
    }

    @Override
    public boolean isScenarioEmpty() {
        return usageService.isScenarioEmpty(getScenario());
    }

    @Override
    public List<Rightsholder> getSourceRros() {
        return scenarioService.getSourceRros(getScenario().getId());
    }

    @Override
    public void deleteFromScenario(Long rroAccountNumber, List<Long> accountNumbers, String reason) {
        usageService.deleteFromScenario(getScenario(), rroAccountNumber, accountNumbers, reason);
    }

    @Override
    public List<RightsholderPayeePair> getRightsholdersPayeePairs(Long rroAccountNumber) {
        return scenarioService.getRightsholdersByScenarioAndSourceRro(getScenario().getId(), rroAccountNumber);
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction() {
        return scenarioService.getScenarioWithAmountsAndLastAction(scenario);
    }

    @Override
    public void fireWidgetEvent(ExcludeUsagesEvent event) {
        getWidget().fireWidgetEvent(event);
    }
}
