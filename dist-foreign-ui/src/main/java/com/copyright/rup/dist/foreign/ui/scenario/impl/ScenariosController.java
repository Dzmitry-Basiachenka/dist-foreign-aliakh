package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.repository.api.Sort.Direction;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.Maps;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * Controller class for {@link ScenariosWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScenariosController extends CommonController<IScenariosWidget> implements IScenariosController {

    private static final String LIST_SEPARATOR = ", ";
    private Map<ScenarioActionTypeEnum, IActionHandler> actionHandlers;

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private ScenarioController scenarioController;
    @Autowired
    private IReconcileRightsholdersController reconcileRightsholdersController;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios();
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction(Scenario scenario) {
        return scenarioService.getScenarioWithAmountsAndLastAction(scenario);
    }

    @Override
    public void onDeleteButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> deleteScenario(scenario));
    }

    @Override
    public void onViewButtonClicked() {
        scenarioController.setScenario(getWidget().getSelectedScenario());
        IScenarioWidget scenarioWidget = scenarioController.initWidget();
        scenarioWidget.addListener((IExcludeUsagesListener) event -> getWidget().refreshSelectedScenario());
        Window scenarioWindow = (Window) scenarioWidget;
        Windows.showModalWindow(scenarioWindow);
        scenarioWindow.setPositionY(30);
    }

    @Override
    public void onReconcileRightsholdersButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        scenarioController.setScenario(scenario);
        if (!scenarioController.isScenarioEmpty()) {
            scenarioService.reconcileRightsholders(scenario);
            if (0 < rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(scenario.getId(),
                RightsholderDiscrepancyStatusEnum.DRAFT)) {
                reconcileRightsholdersController.setScenario(scenario);
                Windows.showModalWindow(new RightsholderDiscrepanciesWindow(reconcileRightsholdersController, this));
            } else {
                Windows.showConfirmDialog(ForeignUi.getMessage("window.reconcile_rightsholders", scenario.getName()),
                    () -> {
                        scenarioService.updateRhParticipationAndAmounts(scenario);
                        getWidget().refreshSelectedScenario();
                    });
            }
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.warning.action_for_empty_scenario", "recalculated"));
        }
    }

    @Override
    public void handleAction(ScenarioActionTypeEnum actionType) {
        scenarioController.setScenario(getWidget().getSelectedScenario());
        if (!scenarioController.isScenarioEmpty()) {
            IActionHandler actionHandler = actionHandlers.get(actionType);
            if (null != actionHandler) {
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.action"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> applyScenarioAction(actionHandler, reason),
                    new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024));
            }
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.warning.action_for_empty_scenario", "submitted for approval"));
        }
    }

    @Override
    public void sendToLm() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("window.send_scenario", scenario.getName()),
            () -> sendToLM(scenario));
    }

    @Override
    public void onRefreshScenarioButtonClicked() {
        ScenarioUsageFilter filter =
            scenarioUsageFilterService.getByScenarioId(getWidget().getSelectedScenario().getId());
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
    public void refreshScenario() {
        scenarioService.refreshScenario(getWidget().getSelectedScenario());
        getWidget().refreshSelectedScenario();
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        ScenarioUsageFilter filter =
            scenarioUsageFilterService.getByScenarioId(getWidget().getSelectedScenario().getId());
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
                    rightsholderService.updateAndGetRightsholders(filter.getRhAccountNumbers())), LIST_SEPARATOR));
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
    public List<Long> getInvalidRightsholders() {
        return usageService.getInvalidRightsholdersByFilter(
            new UsageFilter(scenarioUsageFilterService.getByScenarioId(getWidget().getSelectedScenario().getId())));
    }

    @Override
    protected IScenariosWidget instantiateWidget() {
        return new ScenariosWidget(scenarioHistoryController);
    }

    /**
     * Initializes handlers for actions.
     */
    @PostConstruct
    void initActionHandlers() {
        actionHandlers = Maps.newHashMap();
        actionHandlers.put(ScenarioActionTypeEnum.SUBMITTED,
            (scenario, reason) -> scenarioService.submit(scenario, reason));
        actionHandlers.put(ScenarioActionTypeEnum.APPROVED,
            (scenario, reason) -> scenarioService.approve(scenario, reason));
        actionHandlers.put(ScenarioActionTypeEnum.REJECTED,
            (scenario, reason) -> scenarioService.reject(scenario, reason));
    }

    /**
     * Applies scenario action.
     *
     * @param actionHandler instance of {@link IActionHandler}
     * @param reason        action reason
     */
    void applyScenarioAction(IActionHandler actionHandler, String reason) {
        IScenariosWidget widget = getWidget();
        Scenario scenario = widget.getSelectedScenario();
        scenario.setUpdateUser(SecurityUtils.getUserName());
        actionHandler.handleAction(scenario, reason);
        widget.refresh();
    }

    /**
     * Sends specified scenario to LM.
     *
     * @param scenario selected {@link Scenario}
     */
    void sendToLM(Scenario scenario) {
        try {
            scenarioService.sendToLm(scenario);
        } catch (RuntimeException e) {
            Windows.showNotificationWindow(e.getMessage());
        }
        getWidget().refresh();
    }

    private void deleteScenario(Scenario scenario) {
        scenarioService.deleteScenario(scenario);
        getWidget().refresh();
    }

    private void appendCriterionMessage(StringBuilder builder, String criterionName, Object values) {
        builder.append(String.format("<li><b><i>%s </i></b>(%s)</li>", ForeignUi.getMessage(criterionName), values));
    }

    private Collection<String> generateRightsholderList(Map<Long, Rightsholder> rightsholderMap) {
        return rightsholderMap.entrySet().stream().map(entry -> {
            String rightsholderRepresentation = String.valueOf(entry.getKey());
            Rightsholder rightsholder = entry.getValue();
            if (Objects.nonNull(rightsholder) && StringUtils.isNotBlank(rightsholder.getName())) {
                rightsholderRepresentation += ": " + rightsholder.getName();
            }
            return rightsholderRepresentation;
        }).collect(Collectors.toList());
    }

    private List<UsageDto> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders,
                                     UsageFilter usageFilter) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(sortOrders)) {
            QuerySortOrder sortOrder = sortOrders.get(0);
            sort = new Sort(sortOrder.getSorted(), Direction.of(SortDirection.ASCENDING == sortOrder.getDirection()));
        }
        return usageService.getUsageDtos(usageFilter, new Pageable(startIndex, count), sort);
    }

    private int getSize(UsageFilter filter) {
        return usageService.getUsagesCount(filter);
    }
}
