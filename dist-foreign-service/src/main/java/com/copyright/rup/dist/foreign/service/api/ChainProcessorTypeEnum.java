package com.copyright.rup.dist.foreign.service.api;

/**
 * Enum that enumerates available job processors.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/06/18
 *
 * @author Uladzislau Shalamitski
 */
public enum ChainProcessorTypeEnum {

    /**
     * Matching processor.
     */
    MATCHING,

    /**
     * Rights processor.
     */
    RIGHTS,

    /**
     * Deleting processor.
     */
    DELETE,

    /**
     * Eligibility processor.
     */
    ELIGIBILITY
}
