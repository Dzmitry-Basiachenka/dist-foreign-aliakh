package com.copyright.rup.dist.foreign.vui.scenario.api.fas;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

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
public interface IFasExcludePayeeWidget extends IWidget<IFasExcludePayeeController>, IRefreshable {

    /**
     * @return search value string.
     */
    String getSearchValue();

    /**
     * Gets set of account numbers of selected payees.
     *
     * @return set of account numbers of selected payees
     */
    Set<Long> getSelectedAccountNumbers();
}
