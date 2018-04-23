package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;

import com.vaadin.data.provider.QuerySortOrder;

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
     * Approves reconciliation of rightsholders.
     */
    void approveReconciliation();

    /**
     * @return an instance of {@link Scenario}.
     */
    Scenario getScenario();

    /**
     * Sets {@link Scenario}.
     *
     * @param scenario instance of {@link Scenario}
     */
    void setScenario(Scenario scenario);

    /**
     * @return number of items.
     */
    int getSize();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<RightsholderDiscrepancy> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * @return list of old rightsholder account numbers where new account number is empty.
     */
    List<Long> getProhibitedAccountNumbers();

    /**
     * Deletes rightsholder discrepancies with IN_PROGRESS status.
     */
    void deleteInProgressDiscrepancies();
}
