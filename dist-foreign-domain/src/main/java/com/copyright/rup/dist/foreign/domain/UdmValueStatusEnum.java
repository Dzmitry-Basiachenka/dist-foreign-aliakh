package com.copyright.rup.dist.foreign.domain;

/**
 * Represents UDM value statuses.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public enum UdmValueStatusEnum {

    /**
     * Status for new UDM values.
     */
    NEW,

    /**
     * Status for UDM values which is researched in the previous period.
     */
    RSCHD_IN_THE_PREV_PERIOD,

    /**
     * Status for UDM values when preliminary research complete.
     */
    PRELIM_RESEARCH_COMPLETE,

    /**
     * Status for UDM values which needs further review.
     */
    NEEDS_FURTHER_REVIEW,

    /**
     * Status for UDM values when research complete.
     */
    RESEARCH_COMPLETE
}
