package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IFasScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FasScenariosController extends CommonScenariosController<IFasScenariosWidget, IFasScenariosController>
    implements IFasScenariosController {

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private IFasScenarioController scenarioController;
    @Autowired
    private IReconcileRightsholdersController reconcileRightsholdersController;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Override
    public void onReconcileRightsholdersButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        scenarioController.setScenario(scenario);
        if (!scenarioController.isScenarioEmpty()) {
            getScenarioService().reconcileRightsholders(scenario);
            if (0 < rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
                RightsholderDiscrepancyStatusEnum.DRAFT)) {
                reconcileRightsholdersController.setScenario(scenario);
                Windows.showModalWindow(new RightsholderDiscrepanciesWindow(reconcileRightsholdersController, this));
            } else {
                Windows.showConfirmDialog(ForeignUi.getMessage("window.reconcile_rightsholders", scenario.getName()),
                    () -> {
                        getScenarioService().updateRhPayeeParticipating(scenario);
                        getWidget().refreshSelectedScenario();
                    });
            }
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.warning.action_for_empty_scenario", "recalculated"));
        }
    }

    @Override
    public void onRefreshScenarioButtonClicked() {
        ScenarioUsageFilter filter =
            getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId());
        if (Objects.nonNull(filter)) {
            UsageFilter usageFilter = new UsageFilter(filter);
            if (0 < getSize(usageFilter)) {
                Windows.showModalWindow(new RefreshScenarioWindow(
                    query ->
                        loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders(), usageFilter).stream(),
                    query -> getSize(usageFilter), this));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.info.refresh_scenario.nothing_to_add"));
            }
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.info.refresh_scenario.nothing_to_add"));
        }
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return getUsageService().getInvalidRightsholdersByFilter(
            new UsageFilter(
                getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId())));
    }

    @Override
    protected IFasScenariosWidget instantiateWidget() {
        return new FasScenariosWidget(scenarioHistoryController);
    }

    @Override
    protected IFasScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected IFasScenarioWidget initScenarioWidget() {
        IFasScenarioWidget scenarioWidget = scenarioController.initWidget();
        IExcludeUsagesListener listener = event -> {
            scenarioWidget.refresh();
            scenarioWidget.refreshTable();
            getWidget().refreshSelectedScenario();
        };
        scenarioWidget.addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
        return scenarioWidget;
    }

    private List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders,
                                     UsageFilter usageFilter) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return getUsageService().getUsageDtos(usageFilter, new Pageable(startIndex, count), sort);
    }

    private int getSize(UsageFilter filter) {
        return getUsageService().getUsagesCount(filter);
    }

}
