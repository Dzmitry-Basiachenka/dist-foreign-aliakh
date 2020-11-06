package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.common.reporting.api.ICsvReportProvider;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
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
public interface IFasExcludePayeeController extends IController<IFasExcludePayeeWidget>, ICsvReportProvider {

    /**
     * {@link #onFilterChanged()}.
     */
    Method ON_FILTER_CHANGED = ReflectTools.findMethod(IFasExcludePayeeController.class, "onFilterChanged");

    /**
     * Handles filter change event.
     */
    void onFilterChanged();

    /**
     * @return payee filter controller
     */
    IFasExcludePayeeFilterController getExcludePayeesFilterController();

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

    /**
     * Redesignates details for corresponding scenario with selected payees' account numbers.
     *
     * @param payeeAccountNumbers payee's account numbers
     * @param reason              reason of exclusion
     */
    void redesignateDetails(Set<Long> payeeAccountNumbers, String reason);

    /**
     * Gets set of payee account numbers that are invalid for exclude due to different payee participating flag.
     *
     * @param accountNumbers set of payees' account numbers
     * @return set of invalid payees
     */
    Set<Long> getAccountNumbersInvalidForExclude(Set<Long> accountNumbers);
}
