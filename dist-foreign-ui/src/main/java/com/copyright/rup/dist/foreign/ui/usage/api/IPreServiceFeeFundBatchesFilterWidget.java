package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Interface for widget to filter batches to create pre-service fee funds.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 04/04/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IPreServiceFeeFundBatchesFilterWidget {

    /**
     * Get list of selected {@link UsageBatch}es.
     *
     * @return list of selected {@link UsageBatch}es
     */
    List<UsageBatch> getSelectedUsageBatches();
}
