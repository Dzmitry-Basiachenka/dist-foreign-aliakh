package com.copyright.rup.dist.foreign.service.api.processor;

/**
 * Interface for usage processors that will be run by job.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public interface IUsageJobProcessor {

    /**
     * Processes all available usages by specified product family.
     *
     * @param productFamily product family
     */
    void jobProcess(String productFamily);
}
