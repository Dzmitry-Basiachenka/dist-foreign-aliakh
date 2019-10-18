package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.PayeeTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for exclude payees controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IExcludePayeesController extends IController<IExcludePayeeWidget> {

    /**
     * {@link #onFilterChanged()}.
     */
    Method ON_FILTER_CHANGED = ReflectTools.findMethod(IExcludePayeesController.class, "onFilterChanged");

    /**
     * Handles filter change event.
     */
    void onFilterChanged();

    /**
     * @return payee filter controller
     */
    IExcludePayeesFilterController getExcludePayeesFilterController();

    /**
     * @return list of {@link PayeeTotalsHolder}s to be displayed on UI.
     */
    List<PayeeTotalsHolder> getPayeeTotalsHolders();

    /**
     * Exclude details from corresponding scenario with selected payees' account numbers.
     *
     * @param payeeAccountNumbers payee's account numbers
     * @param reason              reason of exclusion
     */
    void excludeDetails(Set<Long> payeeAccountNumbers, String reason);

    /**
     * Redesignates details for corresponding scenario with selected payees' account numbers.
     *
     * @param payeeAccountNumbers payee's account numbers
     * @param reason              reason of exclusion
     */
    void redesignateDetails(Set<Long> payeeAccountNumbers, String reason);

    /**
     * Sets {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario} to set
     */
    void setScenario(Scenario scenario);
}
