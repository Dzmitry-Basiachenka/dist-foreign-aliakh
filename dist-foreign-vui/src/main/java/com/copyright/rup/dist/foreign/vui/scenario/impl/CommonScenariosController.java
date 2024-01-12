package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.CommonController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static final long serialVersionUID = -8428455534890061352L;

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
    public void editScenarioName(String scenarioId, String newScenarioName) {
        scenarioService.updateName(scenarioId, newScenarioName);
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
                String rightsholderRepresentation = String.valueOf(entry.getKey());
                Rightsholder rightsholder = entry.getValue();
                if (Objects.nonNull(rightsholder) && StringUtils.isNotBlank(rightsholder.getName())) {
                    rightsholderRepresentation += ": " + rightsholder.getName();
                }
                return rightsholderRepresentation;
            }).collect(Collectors.toList());
    }
}
