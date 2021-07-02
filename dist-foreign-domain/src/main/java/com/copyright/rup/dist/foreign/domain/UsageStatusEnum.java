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
     * Status for usages without rhAccountNumber and wrWrkInst.
     */
    NEW,

    /**
     * Status for usages with WrWrkInst not found.
     */
    WORK_NOT_FOUND,

    /**
     * Detail submitted to Data Ops for research.
     */
    WORK_RESEARCH,

    /**
     * Status for usages with wrWrkInst and without rhAccountNumber.
     */
    WORK_FOUND,

    /**
     * Status for usages with work that is not granted.
     */
    WORK_NOT_GRANTED,

    /**
     * Status for usages with rhAccountNumber not found in RMS.
     */
    RH_NOT_FOUND,

    /**
     * Status for usages with rhAccountNumber found in RMS.
     */
    RH_FOUND,

    /**
     * Status for usages that were sent to RA.
     */
    SENT_FOR_RA,

    /**
     * Status for usages with non-STM RH.
     */
    NON_STM_RH,

    /**
     * Status for NTS usages with US tax rightsholder.
     */
    US_TAX_COUNTRY,

    /**
     * Status for usages with unclassified work.
     */
    UNCLASSIFIED,

    /**
     * Status for ineligible usages.
     */
    INELIGIBLE,

    /**
     * Status for eligible usages.
     */
    ELIGIBLE,

    /**
     * Status for OPS review.
     */
    OPS_REVIEW,

    /**
     * Status for Specialist review.
     */
    SPECIALIST_REVIEW,

    /**
     * Status for withdrawn usages.
     */
    NTS_WITHDRAWN,

    /**
     * Status for withdrawn usages to be distributed.
     */
    TO_BE_DISTRIBUTED,

    /**
     * Status for usages excluded from scenario by minimum amount.
     */
    SCENARIO_EXCLUDED,

    /**
     * Status for usages which were added to scenario.
     */
    LOCKED,

    /**
     * Status for paid usages.
     */
    PAID,

    /**
     * Status for usages that were sent to LM.
     */
    SENT_TO_LM,

    /**
     * Status for sent to CRM usages.
     */
    ARCHIVED
}
