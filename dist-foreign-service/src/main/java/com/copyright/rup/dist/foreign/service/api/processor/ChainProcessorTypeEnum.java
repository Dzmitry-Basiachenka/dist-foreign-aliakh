package com.copyright.rup.dist.foreign.service.api.processor;

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
     * Rightsholder not found processor.
     */
    RH_NOT_FOUND,

    /**
     * Deleting processor.
     */
    DELETE,

    /**
     * RH Tax processor.
     */
    RH_TAX,

    /**
     * RH Eligibility processor.
     */
    RH_ELIGIBILITY,

    /**
     * Classification processor.
     */
    CLASSIFICATION,

    /**
     * Eligibility processor.
     */
    ELIGIBILITY
}
