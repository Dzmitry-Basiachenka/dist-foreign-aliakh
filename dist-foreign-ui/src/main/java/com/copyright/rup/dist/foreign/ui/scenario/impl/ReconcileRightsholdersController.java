package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
        //TODO {isuvorau} apply after implementation
    }
}
