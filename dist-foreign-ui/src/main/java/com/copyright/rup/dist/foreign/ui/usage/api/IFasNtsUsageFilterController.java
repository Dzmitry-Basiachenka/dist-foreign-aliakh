package com.copyright.rup.dist.foreign.ui.usage.api;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import java.util.List;

/**
 * Interface for controller for FAS, FAS2 and NTS usage filtering.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/12/2019
 *
 * @author Uladzislau Shalamitski
 */
public interface IFasNtsUsageFilterController extends ICommonUsageFilterController {

    /**
     * Gets list of fiscal years associated with specified Product Family.
     *
     * @return list of fiscal years
     */
    List<Integer> getFiscalYears();

    /**
     * Gets list of {@link UsageBatch}es related to selected Product Family.
     *
     * @return list of {@link UsageBatch}es.
     */
    List<UsageBatch> getUsageBatches();

    /**
     * Gets list of RROs associated with selected Product Family.
     *
     * @return list of RROs
     */
    List<Rightsholder> getRros();
}
