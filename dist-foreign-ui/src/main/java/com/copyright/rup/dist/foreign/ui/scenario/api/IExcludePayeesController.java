package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Interface for exclude payees controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IExcludePayeesController {

    /**
     * @return number of items.
     */
    int getBeansCount();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
     * @return list of items to be displayed on UI
     */
    List<RightsholderTotalsHolder> loadBeans(int startIndex, int count, List<QuerySortOrder> sortOrders);
}
