package com.copyright.rup.dist.foreign.ui.scenario.api.nts;

import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludeUsagesListener;
import com.copyright.rup.vaadin.widget.api.IRefreshable;
import com.copyright.rup.vaadin.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for exclude rightsholder widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/20
 *
 * @author Anton Azarenka
 */
public interface INtsExcludeByRightsholderWidget extends IRefreshable, IWidget<INtsExcludeByRightsholderController> {

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
     * Gets set of account numbers of selected rightsholders.
     *
     * @return set of account numbers of selected rightsholders
     */
    Set<Long> getSelectedAccountNumbers();
}
