package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
    private IScenarioService scenarioService;
    @Autowired
    private ScenarioController scenarioController;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios();
    }

    @Override
    public Scenario getScenarioWithAmounts(Scenario scenario) {
        return scenarioService.getScenarioWithAmounts(scenario.getId());
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
    public void handleAction(ScenarioActionTypeEnum actionType) {
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
    }

    @Override
    protected IScenariosWidget instantiateWidget() {
        return new ScenariosWidget();
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
        widget.refreshSelectedScenario();
        widget.refresh();
    }

    private void deleteScenario(Scenario scenario) {
        scenarioService.deleteScenario(scenario.getId());
        getWidget().refresh();
    }
}
