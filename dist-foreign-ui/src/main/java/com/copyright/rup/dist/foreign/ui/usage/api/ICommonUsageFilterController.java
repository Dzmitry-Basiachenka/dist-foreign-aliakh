package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

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
     * Gets globally selected product family.
     *
     * @return the selected product family
     */
    String getSelectedProductFamily();

    /**
     * Gets list of {@link UsageBatch}es related to selected Product Family.
     *
     * @return list of {@link UsageBatch}es
     */
    List<UsageBatch> getUsageBatches();
}
