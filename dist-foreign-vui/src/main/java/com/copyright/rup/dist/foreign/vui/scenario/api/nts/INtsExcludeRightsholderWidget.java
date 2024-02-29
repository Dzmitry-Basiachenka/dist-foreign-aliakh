package com.copyright.rup.dist.foreign.vui.scenario.api.nts;

import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IRefreshable;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import java.util.Set;

/**
 * Interface for exclude rightsholder widget.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/2020
 *
 * @author Anton Azarenka
 */
public interface INtsExcludeRightsholderWidget extends IWidget<INtsExcludeRightsholderController>, IRefreshable {

    /**
     * @return search value string.
     */
    String getSearchValue();

    /**
     * Gets set of account numbers of selected rightsholders.
     *
     * @return set of account numbers of selected rightsholders
     */
    Set<Long> getSelectedAccountNumbers();
}
