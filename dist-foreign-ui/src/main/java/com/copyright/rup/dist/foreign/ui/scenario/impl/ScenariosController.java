package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ExcludeRightsholdersWindow.IExcludeUsagesListener;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
            Set<RightsholderDiscrepancy> discrepancies = scenarioService.getRightsholderDiscrepancies(scenario);
            if (CollectionUtils.isNotEmpty(discrepancies)) {
                reconcileRightsholdersController.setDiscrepancies(discrepancies);
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
                Window window = new ConfirmActionDialogWindow(
                    reason -> applyScenarioAction(actionHandler, reason),
                    ForeignUi.getMessage("window.confirm"), ForeignUi.getMessage("message.confirm.action"),
                    ForeignUi.getMessage("button.yes"), ForeignUi.getMessage("button.cancel"),
                    new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024, true)
                );
                Windows.showModalWindow(window);
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
    public void refreshScenario() {
        // TODO {isuvorau} call service method after implementation
        List<UsageDto> usages = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(usages)) {
            Windows.showModalWindow(new RefreshScenarioWindow(usages));
        } else {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.info.refresh_scenario.nothing_to_add"));
        }
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        // TODO {isuvorau} call service method to get ScenarioUsageFilter by selected scenario id
        ScenarioUsageFilter filter = new ScenarioUsageFilter();
        StringBuilder sb = new StringBuilder(ForeignUi.getMessage("label.criteria"));
        if (!filter.isEmpty()) {
            sb.append("<ul>");
            if (Objects.nonNull(filter.getProductFamily())) {
                appendCriterionMessage(sb, "label.product_family", filter.getProductFamily());
            }
            if (CollectionUtils.isNotEmpty(filter.getUsageBatchesIds())) {
                appendCriterionMessage(sb, "label.batch_in",
                    StringUtils.join(filter.getUsageBatchesIds(), LIST_SEPARATOR));
            }
            if (CollectionUtils.isNotEmpty(filter.getRhAccountNumbers())) {
                appendCriterionMessage(sb, "label.rro_in", StringUtils.join(generateRightsholderList(
                    rightsholderService.updateAndGetRightsholders(filter.getRhAccountNumbers())), LIST_SEPARATOR));
            }
            if (Objects.nonNull(filter.getPaymentDate())) {
                appendCriterionMessage(sb, "label.payment_date_to", filter.getPaymentDate()
                    .format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
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
        builder.append(String.format("<li><b><i>%s </i></b> (%s)</li>", ForeignUi.getMessage(criterionName), values));
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
}
