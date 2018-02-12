package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
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

import com.google.common.collect.Maps;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Map<ScenarioActionTypeEnum, IActionHandler> actionHandlers;

    @Autowired
    private IScenarioHistoryController scenarioHistoryController;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private ScenarioController scenarioController;
    @Autowired
    private IReconcileRightsholdersController reconcileRightsholdersController;

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
}
