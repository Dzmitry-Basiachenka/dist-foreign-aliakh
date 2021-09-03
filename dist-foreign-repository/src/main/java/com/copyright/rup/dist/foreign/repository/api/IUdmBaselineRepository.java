package com.copyright.rup.dist.foreign.repository.api;

import java.util.Set;

/**
 * Interface for Baseline usage repository.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/02/21
 *
 * @author Anton Azarenka
 */
public interface IUdmBaselineRepository {

    /**
     * Removes UDM usages from baseline. Sets is_baseline_flag {@code false}.
     *
     * @param period   usage period
     * @param userName name of user
     * @return set of UDM usage ids
     */
    Set<String> removeUmdUsagesFromBaseline(Integer period, String userName);
}
