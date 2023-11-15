package com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter;

import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.filter.CommonFilterWindow.IFilterSaveListener;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.List;

/**
 * Implement this interface to use {@link LazyFilterWindow}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 11/24/2021
 *
 * @param <T> bean type
 * @author Ihar Suvorau
 * @see LazyFilterWindow
 */
public interface ILazyFilterWindowController<T> extends IFilterSaveListener<T> {

    /**
     * Loads limited number of beans from the storage with given start index.
     *
     * @param searchValue search value
     * @param startIndex  start index
     * @param count       items count to load
     * @param sortOrders  sort orders
     * @return collection of beans
     */
    Collection<T> loadBeans(String searchValue, int startIndex, int count, List<QuerySortOrder> sortOrders);

    /**
     * Get beans count by specific search value.
     *
     * @param searchValue search value
     * @return beans count
     */
    int getBeansCount(String searchValue);

    /**
     * @return {@link ValueProvider} to get String from T object to display in grid.
     */
    ValueProvider<T, String> getGridColumnValueProvider();
}
