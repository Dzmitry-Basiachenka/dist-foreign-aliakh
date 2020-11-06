package com.copyright.rup.dist.foreign.ui.scenario.api.aacl;

import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for exclude AACL payees widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/2020
 *
 * @author Ihar Suvorau
 */
public interface IAaclExcludePayeeWidget extends IRefreshable, IWidget<IAaclExcludePayeeController> {

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
