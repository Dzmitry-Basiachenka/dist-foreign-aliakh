package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link IReconcileRightsholdersController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/26/18
 *
 * @author Ihar Suvorau
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReconcileRightsholdersController implements IReconcileRightsholdersController {

    private Set<RightsholderDiscrepancy> rightsholderDiscrepancies;
    private Scenario scenario;

    @Autowired
    private IScenarioService scenarioService;

    @Override
    public Set<RightsholderDiscrepancy> getDiscrepancies() {
        return rightsholderDiscrepancies;
    }

    @Override
    public void setDiscrepancies(Set<RightsholderDiscrepancy> discrepancies) {
        this.rightsholderDiscrepancies = discrepancies;
    }

    @Override
    public void approveReconciliation() {
        Set<Long> prohibitedAccountNumbers = Sets.newHashSet();
        rightsholderDiscrepancies.stream()
            .filter(discrepancy -> Objects.isNull(discrepancy.getNewRightsholder().getAccountNumber()))
            .forEach(discrepancy -> prohibitedAccountNumbers.add(discrepancy.getOldRightsholder().getAccountNumber()));
        if (CollectionUtils.isEmpty(prohibitedAccountNumbers)) {
            scenarioService.approveOwnershipChanges(scenario, rightsholderDiscrepancies);
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("window.prohibition_approval", prohibitedAccountNumbers));
        }
    }

    @Override
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
}
