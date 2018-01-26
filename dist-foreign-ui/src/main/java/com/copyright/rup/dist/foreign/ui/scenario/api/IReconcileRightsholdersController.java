package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;

import java.util.List;

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
     * @return list of {@link RightsholderDiscrepancy}ies.
     */
    List<RightsholderDiscrepancy> getDiscrepancies();

    /**
     * Sets list of {@link RightsholderDiscrepancy}ies.
     *
     * @param discrepancies list of {@link RightsholderDiscrepancy}ies
     */
    void setDiscrepancies(List<RightsholderDiscrepancy> discrepancies);

    /**
     * Approves reconciliation of rightsholders.
     */
    void approveReconciliation();
}
