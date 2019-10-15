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
     * Deleting processor.
     */
    DELETE,

    /**
     * RH Tax processor.
     */
    RH_TAX,

    /**
     * STM RH processor.
     */
    STM_RH,

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
