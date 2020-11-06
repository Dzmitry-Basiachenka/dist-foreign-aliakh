package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class FasScenariosController extends CommonScenariosController implements IFasScenariosController {

    private static final String LIST_SEPARATOR = ", ";

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private IFasScenarioController scenarioController;
    @Autowired
    private IFasExcludePayeeController excludePayeesController;
    @Autowired
    private IReconcileRightsholdersController reconcileRightsholdersController;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private IFasScenarioService fasScenarioService;

    @Override
    public void onReconcileRightsholdersButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        scenarioController.setScenario(scenario);
        if (!scenarioController.isScenarioEmpty()) {
            fasScenarioService.reconcileRightsholders(scenario);
            if (0 < rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
                RightsholderDiscrepancyStatusEnum.DRAFT)) {
                reconcileRightsholdersController.setScenario(scenario);
                Windows.showModalWindow(new RightsholderDiscrepanciesWindow(reconcileRightsholdersController, this));
            } else {
                Windows.showConfirmDialog(ForeignUi.getMessage("window.reconcile_rightsholders", scenario.getName()),
                    () -> {
                        fasScenarioService.updateRhPayeeParticipating(scenario);
                        getWidget().refreshSelectedScenario();
                    });
            }
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.warning.action_for_empty_scenario", "recalculated"));
        }
    }

    @Override
    public void onExcludePayeesButtonClicked() {
        IFasExcludePayeeWidget widget = excludePayeesController.initWidget();
        widget.addListener((IExcludeUsagesListener) listener -> getWidget().refreshSelectedScenario());
        Windows.showModalWindow((Window) widget);
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
    public String getCriteriaHtmlRepresentation() {
        ScenarioUsageFilter filter =
            getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId());
        StringBuilder sb = new StringBuilder(ForeignUi.getMessage("label.criteria"));
        if (Objects.nonNull(filter)) {
            sb.append("<ul>");
            if (Objects.nonNull(filter.getProductFamily())) {
                appendCriterionMessage(sb, "label.product_family", filter.getProductFamily());
            }
            if (CollectionUtils.isNotEmpty(filter.getUsageBatches())) {
                appendCriterionMessage(sb, "label.batch_in", StringUtils.join(
                    filter.getUsageBatches().stream().map(UsageBatch::getName).collect(Collectors.toList()),
                    LIST_SEPARATOR));
            }
            if (CollectionUtils.isNotEmpty(filter.getRhAccountNumbers())) {
                appendCriterionMessage(sb, "label.rro_in", StringUtils.join(generateRightsholderList(
                    getRightsholderService().updateAndGetRightsholders(filter.getRhAccountNumbers())), LIST_SEPARATOR));
            }
            if (Objects.nonNull(filter.getPaymentDate())) {
                appendCriterionMessage(sb, "label.payment_date_to",
                    CommonDateUtils.format(filter.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
            }
            if (Objects.nonNull(filter.getUsageStatus())) {
                appendCriterionMessage(sb, "label.status", filter.getUsageStatus());
            }
            if (Objects.nonNull(filter.getFiscalYear())) {
                appendCriterionMessage(sb, "label.fiscal_year_to", filter.getFiscalYear());
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    @Override
    public void sendToLm() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("window.send_scenario", scenario.getName()),
            () -> {
                try {
                    fasScenarioService.sendToLm(scenario);
                } catch (RuntimeException e) {
                    Windows.showNotificationWindow(e.getMessage());
                }
                getWidget().refresh();
            });
    }

    @Override
    public List<Long> getInvalidRightsholders() {
        return getUsageService().getInvalidRightsholdersByFilter(
            new UsageFilter(
                getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId())));
    }

    @Override
    protected IFasScenariosWidget instantiateWidget() {
        return new FasScenariosWidget(this, scenarioHistoryController);
    }

    @Override
    protected IFasScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected IFasScenarioWidget initScenarioWidget() {
        IFasScenarioWidget scenarioWidget = (IFasScenarioWidget) scenarioController.initWidget();
        IExcludeUsagesListener listener = event -> {
            scenarioWidget.refresh();
            scenarioWidget.refreshTable();
            getWidget().refreshSelectedScenario();
        };
        scenarioWidget.addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
        return scenarioWidget;
    }

    @Override
    public void onDeleteButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> {
                getScenarioService().deleteScenario(scenario);
                getWidget().refresh();
            });
    }

    private List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders,
                                     UsageFilter usageFilter) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return fasUsageService.getUsageDtos(usageFilter, new Pageable(startIndex, count), sort);
    }

    private int getSize(UsageFilter filter) {
        return fasUsageService.getUsagesCount(filter);
    }
}
