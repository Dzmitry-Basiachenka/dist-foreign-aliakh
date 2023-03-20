package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.common.exception.RupRuntimeException;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.impl.CommonScenariosController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.validator.StringLengthValidator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

/**
 * Implementation of {@link ISalScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SalScenariosController extends CommonScenariosController implements ISalScenariosController {

    private static final String LIST_SEPARATOR = ", ";
    private static final String SCENARIO_NAMES_LIST_SEPARATOR = "<br><li>";
    private Map<ScenarioActionTypeEnum, ISalScenarioActionHandler> actionHandlers;

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private ISalScenarioController scenarioController;
    @Autowired
    private ISalScenarioService salScenarioService;
    @Autowired
    private IFundPoolService fundPoolService;
    @Autowired
    private IUsageService usageService;

    @Override
    public List<Scenario> getScenariosByStatus(ScenarioStatusEnum status) {
        return getScenarioService().getScenariosByProductFamiliesAndStatuses(
            Set.of(getProductFamilyProvider().getSelectedProductFamily()), Set.of(status));
    }

    @Override
    public void sendToLm(Set<Scenario> scenarios) {
        String scenarioNames = scenarios
            .stream()
            .map(Scenario::getName)
            .collect(Collectors.joining(SCENARIO_NAMES_LIST_SEPARATOR));
        Windows.showConfirmDialog(ForeignUi.getMessage("window.send_scenarios", scenarioNames), () -> {
            try {
                scenarios.forEach(scenario -> salScenarioService.sendToLm(scenario));
                getWidget().refresh();
            } catch (RupRuntimeException e) {
                Windows.showNotificationWindow(e.getMessage());
            }
        });
    }

    @Override
    public String getCriteriaHtmlRepresentation() {
        ScenarioUsageFilter filter =
            getScenarioUsageFilterService().getByScenarioId(getWidget().getSelectedScenario().getId());
        StringBuilder sb = new StringBuilder(128).append(ForeignUi.getMessage("label.criteria"));
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
            if (Objects.nonNull(filter.getUsageStatus())) {
                appendCriterionMessage(sb, "label.status", filter.getUsageStatus());
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    @Override
    public void onDeleteButtonClicked() {
        Scenario scenario = getWidget().getSelectedScenario();
        Windows.showConfirmDialog(ForeignUi.getMessage("message.confirm.delete_action", scenario.getName(), "scenario"),
            () -> {
                salScenarioService.deleteScenario(scenario);
                getWidget().refresh();
            });
    }

    @Override
    public void onSubmitForApprovalButtonClicked() {
        Windows.showModalWindow(new SalSubmitForApprovalScenariosWindow(this));
    }

    @Override
    public void onRejectButtonClicked() {
        Windows.showModalWindow(new SalRejectScenariosWindow(this));
    }

    @Override
    public void onApproveButtonClicked() {
        Windows.showModalWindow(new SalApproveScenariosWindow(this));
    }

    @Override
    public void onSendToLmButtonClicked() {
        Windows.showModalWindow(new SalSendToLmScenariosWindow(this));
    }

    @Override
    public String getFundPoolName(String fundPoolId) {
        return fundPoolService.getFundPoolById(fundPoolId).getName();
    }

    @Override
    public void handleAction(ScenarioActionTypeEnum actionType, Set<Scenario> scenarios) {
        List<String> namesOfEmptyScenarios = scenarios.stream()
            .filter(scenario -> usageService.isScenarioEmpty(scenario))
            .map(Scenario::getName)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toList());
        if (namesOfEmptyScenarios.isEmpty()) {
            ISalScenarioActionHandler actionHandler = actionHandlers.get(actionType);
            if (Objects.nonNull(actionHandler)) {
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.action"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> applyScenariosAction(actionHandler, scenarios, reason),
                    new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024));
            }
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.warning.action_for_empty_scenarios", namesOfEmptyScenarios));
        }
    }

    @Override
    @PostConstruct
    public void initActionHandlers() {
        actionHandlers = new HashMap<>();
        actionHandlers.put(ScenarioActionTypeEnum.SUBMITTED,
            (scenarios, reason) -> salScenarioService.changeScenariosState(scenarios, ScenarioStatusEnum.SUBMITTED,
                ScenarioActionTypeEnum.SUBMITTED, reason));
        actionHandlers.put(ScenarioActionTypeEnum.APPROVED,
            (scenarios, reason) -> salScenarioService.changeScenariosState(scenarios, ScenarioStatusEnum.APPROVED,
                ScenarioActionTypeEnum.APPROVED, reason));
        actionHandlers.put(ScenarioActionTypeEnum.REJECTED,
            (scenarios, reason) -> salScenarioService.changeScenariosState(scenarios, ScenarioStatusEnum.IN_PROGRESS,
                ScenarioActionTypeEnum.REJECTED, reason));
    }

    @Override
    protected ISalScenariosWidget instantiateWidget() {
        return new SalScenariosWidget(this, scenarioHistoryController);
    }

    @Override
    protected ICommonScenarioController getScenarioController() {
        return scenarioController;
    }

    @Override
    protected ICommonScenarioWidget initScenarioWidget() {
        return scenarioController.initWidget();
    }

    /**
     * Applies SAL scenario action.
     *
     * @param actionHandler instance of {@link ISalScenarioActionHandler}
     * @param scenarios     selected {@link Scenario}s
     * @param reason        action reason
     */
    protected void applyScenariosAction(ISalScenarioActionHandler actionHandler, Set<Scenario> scenarios,
                                        String reason) {
        scenarios.forEach(scenario -> scenario.setUpdateUser(SecurityUtils.getUserName()));
        actionHandler.handleAction(scenarios, reason);
        getWidget().refresh();
    }

    private String buildNotificationMessage(String key, List<String> scenarioNames) {
        StringBuilder htmlNamesList = new StringBuilder("<ul>");
        for (String name : scenarioNames) {
            htmlNamesList.append("<li>").append(name).append("</li>");
        }
        htmlNamesList.append("</ul>");
        return ForeignUi.getMessage(key, htmlNamesList.toString());
    }
}
