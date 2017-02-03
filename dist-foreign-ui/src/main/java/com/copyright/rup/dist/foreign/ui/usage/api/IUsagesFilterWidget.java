package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.ui.common.api.IFilterWidget;

import java.util.Set;

/**
 * Interface for widget for usage filtering.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/11/2017
 *
 * @author Mikita Hladkikh
 */
public interface IUsagesFilterWidget extends IFilterWidget<IUsagesFilterController> {

    /**
     * Sets selected usage batches.
     *
     * @param usageBatchesIds ids of selected usage batches
     */
    void setSelectedUsageBatches(Set<String> usageBatchesIds);

    /**
     * Sets selected rightsholders account numbers.
     *
     * @param accountNumbers rightsholders account numbers
     */
    void setSelectedRightsholders(Set<Long> accountNumbers);

    /**
     * @return {@link UsageFilter}.
     */
    UsageFilter getFilter();
}
