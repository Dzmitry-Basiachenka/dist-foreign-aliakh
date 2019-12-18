package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for exclude payees widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/15/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IExcludePayeeWidget extends SearchWidget.ISearchController, IRefreshable,
    IWidget<IExcludePayeeController> {

    /**
     * @return search value string.
     */
    String getSearchValue();

    /**
     * Adds {@link IExcludeUsagesListener} on window.
     *
     * @param listener instance of {@link IExcludeUsagesListener}
     */
    void addListener(IExcludeUsagesListener listener);
}
