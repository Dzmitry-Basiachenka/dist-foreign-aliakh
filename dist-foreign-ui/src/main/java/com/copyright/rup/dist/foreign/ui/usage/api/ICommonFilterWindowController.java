package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.vaadin.ui.component.filter.IFilterWindowController;

/**
 * Common interface for filter windows.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 1/19/17
 *
 * @author Aliaksandr Radkevich
 * @param <IT> bean id type
 * @param <BT> bean type
 */
public interface ICommonFilterWindowController<IT, BT> extends IFilterWindowController<IT, BT> {

    /**
     * Sets filter widget.
     *
     * @param filterWidget {@link IUsagesFilterWidget}
     */
    void setFilterWidget(IUsagesFilterWidget filterWidget);
}
