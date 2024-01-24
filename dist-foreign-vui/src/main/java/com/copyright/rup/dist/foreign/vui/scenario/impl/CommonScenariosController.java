package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.data.validator.StringLengthValidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
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

    private static final long serialVersionUID = 3614460469774293884L;

    private Map<ScenarioActionTypeEnum, IActionHandler> actionHandlers;

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IScenarioUsageFilterService scenarioUsageFilterService;
    @Autowired
    private IProductFamilyProvider productFamilyProvider;

    @Override
    public List<Scenario> getScenarios() {
        return scenarioService.getScenarios(productFamilyProvider.getSelectedProductFamily());
    }

    @Override
    public boolean scenarioExists(String name) {
        return scenarioService.scenarioExists(name);
    }

    @Override
    public Scenario getScenarioWithAmountsAndLastAction(Scenario scenario) {
        return scenarioService.getScenarioWithAmountsAndLastAction(scenario);
    }

    @Override
    public void onViewButtonClicked() {
        getScenarioController().setScenario(getWidget().getSelectedScenario());
        Windows.showModalWindow((Dialog) initScenarioWidget());
    }

    @Override
    public void editScenarioName(String scenarioId, String newScenarioName) {
        scenarioService.updateName(scenarioId, newScenarioName);
    }

    @Override
    public void refreshScenario() {
        scenarioService.refreshScenario(getWidget().getSelectedScenario());
        getWidget().refreshSelectedScenario();
    }

    @Override
    public void handleAction(ScenarioActionTypeEnum actionType) {
        if (!usageService.isScenarioEmpty(getWidget().getSelectedScenario())) {
            var actionHandler = actionHandlers.get(actionType);
            if (Objects.nonNull(actionHandler)) {
                ConfirmWindows.showConfirmDialogWithReason(
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

    /**
     * Initializes handlers for actions.
     */
    @PostConstruct
    public void initActionHandlers() {
        actionHandlers = new EnumMap<>(ScenarioActionTypeEnum.class);
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
        var scenario = widget.getSelectedScenario();
        scenario.setUpdateUser(SecurityUtils.getUserName());
        actionHandler.handleAction(scenario, reason);
        widget.refresh();
    }

    /**
     * @return usage service
     */
    protected IUsageService getUsageService() {
        return usageService;
    }

    /**
     * @return rightsholder service
     */
    protected IRightsholderService getRightsholderService() {
        return rightsholderService;
    }

    /**
     * @return scenario usage filter service
     */
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

    /**
     * Appends creation message.
     *
     * @param builder       string builder
     * @param criterionName name of creation
     * @param values        values
     */
    protected void appendCriterionMessage(StringBuilder builder, String criterionName, Object values) {
        builder.append(String.format("<li><b><i>%s </i></b>(%s)</li>", ForeignUi.getMessage(criterionName), values));
    }

    /**
     * Generates rightsholders.
     *
     * @param rightsholderMap map of rightsholders
     * @return list of rightsholders
     */
    protected List<String> generateRightsholderList(Map<Long, Rightsholder> rightsholderMap) {
        return rightsholderMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> {
                var rightsholderRepresentation = String.valueOf(entry.getKey());
                var rightsholder = entry.getValue();
                if (Objects.nonNull(rightsholder) && StringUtils.isNotBlank(rightsholder.getName())) {
                    rightsholderRepresentation += ": " + rightsholder.getName();
                }
                return rightsholderRepresentation;
            }).collect(Collectors.toList());
    }

    protected IScenarioService getScenarioService() {
        return scenarioService;
    }
}
