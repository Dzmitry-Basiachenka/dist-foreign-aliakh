package com.copyright.rup.dist.foreign.service.api;

/**
 * Interface for processor that will be run by job.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public interface IJobProcessor {

    /**
     * Processes all available items.
     */
    void process();

    /**
     * @return instance of {@link JobProcessorTypeEnum}.
     */
    JobProcessorTypeEnum getJobProcessorType();
}
