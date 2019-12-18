package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.vaadin.widget.api.IFilterController;

/**
 * Interface for common controller for usage filtering.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
public interface ICommonUsageFilterController extends IFilterController<ICommonUsageFilterWidget> {

    /**
     * @return globally selected product family.
     */
    String getSelectedProductFamily();
}
