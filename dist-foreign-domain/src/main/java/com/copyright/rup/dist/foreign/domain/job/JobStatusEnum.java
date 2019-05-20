package com.copyright.rup.dist.foreign.domain.job;

/**
 * Enum that contains job statuses.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/04/2019
 *
 * @author Uladzislau Shalamitski
 */
public enum JobStatusEnum {

    /**
     * Status for a job that was successfully executed.
     */
    FINISHED,

    /**
     * Status for a job that was skipped.
     */
    SKIPPED,

    /**
     * Status for a job that was failed.
     */
    FAILED
}
