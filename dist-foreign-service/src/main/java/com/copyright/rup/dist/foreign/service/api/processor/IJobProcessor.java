package com.copyright.rup.dist.foreign.service.api.processor;

import com.copyright.rup.dist.common.domain.job.JobInfo;

/**
 * Interface for usage processors that will be run by job.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public interface IJobProcessor {

    /**
     * Processes all available items by specified product family.
     *
     * @param productFamily product family
     * @return instance of {@link JobInfo}
     */
    JobInfo jobProcess(String productFamily);
}
