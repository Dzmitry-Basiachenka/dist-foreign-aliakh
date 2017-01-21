package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.ui.common.api.IFilterController;

/**
 * Interface for controller for usage filtering.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 * @author Aliaksandr Radkevich
 */
public interface IUsagesFilterController extends IFilterController<IUsagesFilterWidget> {

    /**
     * Handles click on usage batch filter.
     */
    void onUsageBatchFilterClick();

    /**
     * Handles click on rightsholders filter.
     */
    void onRightsholderFilterClick();
}
