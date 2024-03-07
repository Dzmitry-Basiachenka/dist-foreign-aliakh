package com.copyright.rup.dist.foreign.vui.usage.api.aacl;

import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageFilterController;

import java.util.List;

/**
 * Interface for controller for AACL usages filtering.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 12/23/2019
 *
 * @author Aliaksandr Liakh
 */
public interface IAaclUsageFilterController extends ICommonUsageFilterController {

    /**
     * Gets list of AACL usage periods.
     *
     * @return list of AACL usage periods
     */
    List<Integer> getUsagePeriods();
}
