package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;

import java.util.Set;

/**
 * Interface for rightsholder controller.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
public interface IReconcileRightsholdersController {

    /**
     * @return set of {@link RightsholderDiscrepancy}ies.
     */
    Set<RightsholderDiscrepancy> getDiscrepancies();

    /**
     * Sets set of {@link RightsholderDiscrepancy}ies.
     *
     * @param discrepancies set of {@link RightsholderDiscrepancy}ies
     */
    void setDiscrepancies(Set<RightsholderDiscrepancy> discrepancies);

    /**
     * Approves reconciliation of rightsholders.
     */
    void approveReconciliation();

    /**
     * Sets {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario}
     */
    void setScenario(Scenario scenario);
}
