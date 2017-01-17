package com.copyright.rup.dist.foreign.ui.common.api;

import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for filtering widget.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @param <T> type of filter controller
 * @author Mikita Hladkikh
 */
// TODO {mhladkikh} analyze moving to rup-vaadin
public interface IFilterWidget<T extends IFilterController<? extends IFilterWidget>> extends IWidget<T> {

    /**
     * Applies specified filters.
     */
    void applyFilter();

    /**
     * Clears current filters.
     */
    void clearFilter();
}
