package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;
import java.util.Set;

/**
 * Audit filter controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/17/2019
 *
 * @author Aliaksanr Liakh
 */
public interface ICommonAuditFilterController extends IFilterController<ICommonAuditFilterWidget> {

    /**
     * @return list of {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * @return the selected product family.
     */
    String getProductFamily();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param searchValue value to search
     * @param startIndex  start index
     * @param count       items count to load
     * @param sortOrders  sort orders
     * @return list of items to be displayed on UI
     */
    List<Rightsholder> loadBeans(String searchValue, int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Gets count of beans based on search value.
     *
     * @param searchValue value to search
     * @return number of items
     */
    int getBeansCount(String searchValue);

    /**
     * Returns list of {@link Rightsholder}s with given account numbers.
     *
     * @param accountNumbers set of {@link Rightsholder}s account numbers
     * @return list of {@link Rightsholder}s
     */
    List<Rightsholder> getRightsholdersByAccountNumbers(Set<Long> accountNumbers);
}
