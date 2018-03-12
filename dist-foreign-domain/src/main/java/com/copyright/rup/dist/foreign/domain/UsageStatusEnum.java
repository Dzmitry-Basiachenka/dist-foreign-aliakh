package com.copyright.rup.dist.foreign.domain;

/**
 * Enum for usage statuses.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/12/2017
 *
 * @author Mikita Hladkikh
 * @author Uladzislau Shalamitski
 */
public enum UsageStatusEnum {

    /**
     * Status for usages without rhAccountNumber and wrWrkInst
     */
    NEW,

    /**
     * Status for usages with wrWrkInst and without rhAccountNumber
     */
    WORK_FOUND,

    /**
     * Detail submitted to Data Ops for research.
     */
    WORK_RESEARCH,

    /**
     * Status for usages with rhAccountNumber not found in RMS
     */
    RH_NOT_FOUND,

    /**
     * Status for usages that were sent to RA
     */
    SENT_FOR_RA,

    /**
     * Status for eligible usages.
     */
    ELIGIBLE,

    /**
     * Status for usages which were added to scenario.
     */
    LOCKED,

    /**
     * Status for paid usages.
     */
    PAID
}
