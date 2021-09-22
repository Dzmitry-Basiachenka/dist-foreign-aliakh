package com.copyright.rup.dist.foreign.domain;

/**
 * Enum for value statuses.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/22/21
 *
 * @author Anton Azarenka
 */
public enum ValueStatusEnum {

    /**
     * Status for created value.
     */
    NEW,

    /**
     * Status for value researched in the previous period.
     */
    RSCHD_IN_THE_PREV_PERIOD,

    /**
     * Status for value prelim research complete.
     */
    PRELIM_RESEARCH_COMPLETE,

    /**
     * Status for value needs further review.
     */
    NEEDS_FURTHER_REVIEW,

    /**
     * Status for value research complete.
     */
    RESEARCH_COMPLETE
}
