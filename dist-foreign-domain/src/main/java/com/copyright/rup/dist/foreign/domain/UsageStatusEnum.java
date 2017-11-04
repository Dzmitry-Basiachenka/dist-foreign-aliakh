package com.copyright.rup.dist.foreign.domain;

/**
 * Enum for usage statuses.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/12/2017
 *
 * @author Mikita Hladkikh
 */
public enum UsageStatusEnum {

    /**
     * Status for usages without rhAccountNumber or wrWrkInst
     */
    NEW,

    /**
     * Status for eligible usages.
     */
    ELIGIBLE,

    /**
     * Status for usages which were added to scenario.
     */
    LOCKED
}
