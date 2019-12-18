package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.CommonController;

import com.google.common.collect.Maps;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * Common controller for {@link ICommonScenariosWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/11/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenariosController extends CommonController<ICommonScenariosWidget>
    implements ICommonScenariosController {

    private static final String LIST_SEPARATOR = ", ";
    private Map<ScenarioActionTypeEnum, IActionHandler> actionHandlers;

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios()
            .stream()
            .filter(scenario -> Objects.equals(productFamilyProvider.getSelectedProductFamily(),
                scenario.getProductFamily()))
            .collect(Collectors.toList());
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
        getScenarioController().setScenario(getWidget().getSelectedScenario());
        ICommonScenarioWidget scenarioWidget = initScenarioWidget();
        Window scenarioWindow = (Window) scenarioWidget;
        Windows.showModalWindow(scenarioWindow);
        scenarioWindow.setPositionY(30);
    }

    @Override
    public void handleAction(ScenarioActionTypeEnum actionType) {
        if (!usageService.isScenarioEmpty(getWidget().getSelectedScenario())) {
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

    /**
     * Initializes handlers for actions.
     */
    @PostConstruct
    public void initActionHandlers() {
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
    public void applyScenarioAction(IActionHandler actionHandler, String reason) {
        ICommonScenariosWidget widget = getWidget();
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
    public void sendToLM(Scenario scenario) {
        try {
            scenarioService.sendToLm(scenario);
        } catch (RuntimeException e) {
            Windows.showNotificationWindow(e.getMessage());
        }
        getWidget().refresh();
    }

    protected IUsageService getUsageService() {
        return usageService;
    }

    protected IScenarioService getScenarioService() {
        return scenarioService;
    }

    protected IScenarioUsageFilterService getScenarioUsageFilterService() {
        return scenarioUsageFilterService;
    }

    /**
     * @return an {@link ICommonScenarioController} instance.
     */
    protected abstract ICommonScenarioController getScenarioController();

    /**
     * Inits scenario view widget.
     *
     * @return an {@link ICommonScenarioWidget} instance
     */
    protected abstract ICommonScenarioWidget initScenarioWidget();

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
}
