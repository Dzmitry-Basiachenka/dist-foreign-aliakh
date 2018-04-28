package com.copyright.rup.dist.foreign.ui.audit.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import com.vaadin.data.provider.QuerySortOrder;

import java.util.List;

/**
 * Audit filter controller.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/12/18
 *
 * @author Aliaksandr Radkevich
 */
public interface IAuditFilterController extends IFilterController<IAuditFilterWidget> {

    /**
     * @return list of {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * @return list of product families.
     */
    List<String> getProductFamilies();

    /**
     * Loads specified number of beans from the storage with given start index.
     *
     * @param searchValue value to search
     * @param startIndex start index
     * @param count      items count to load
     * @param sortOrders sort orders
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
}
