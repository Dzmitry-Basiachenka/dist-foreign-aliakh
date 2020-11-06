package com.copyright.rup.dist.foreign.ui.scenario.api.aacl;

import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.vaadin.widget.api.IController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Interface for exclude AACL payees controller.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclExcludePayeeController extends IController<IAaclExcludePayeeWidget> {

    /**
     * {@link #onFilterChanged()}.
     */
    Method ON_FILTER_CHANGED = ReflectTools.findMethod(IAaclExcludePayeeController.class, "onFilterChanged");

    /**
     * Handles filter change event.
     */
    void onFilterChanged();

    /**
     * @return payee filter controller.
     */
    IAaclExcludePayeeFilterController getExcludePayeesFilterController();

    /**
     * @return list of {@link PayeeTotalHolder}s to be displayed on UI.
     */
    List<PayeeTotalHolder> getPayeeTotalHolders();

    /**
     * Exclude details from corresponding scenario with selected payees' account numbers.
     *
     * @param payeeAccountNumbers payee's account numbers
     * @param reason              reason of exclusion
     */
    void excludeDetails(Set<Long> payeeAccountNumbers, String reason);
}
