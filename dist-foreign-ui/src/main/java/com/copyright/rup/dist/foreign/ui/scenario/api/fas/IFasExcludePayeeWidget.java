package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for exclude payees widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/15/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IFasExcludePayeeWidget extends IRefreshable, IWidget<IFasExcludePayeeController> {

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

    /**
     * Gets set of account numbers of selected payees.
     *
     * @return set of account numbers of selected payees
     */
    Set<Long> getSelectedAccountNumbers();
}
