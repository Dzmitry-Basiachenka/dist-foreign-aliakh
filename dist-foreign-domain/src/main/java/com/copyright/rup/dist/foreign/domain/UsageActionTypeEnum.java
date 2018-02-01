package com.copyright.rup.dist.foreign.domain;

/**
 * Represents types of actions for {@link Usage}s.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public enum UsageActionTypeEnum {

    /**
     * Action type for loading {@link Usage}.
     */
    LOADED,

    /**
     * Action type for rightsholder of {@link Usage} found in RMS.
     */
    RH_FOUND,

    /**
     * Action type for rightsholder of {@link Usage} not found in RMS.
     */
    RH_NOT_FOUND,

    /**
     * Action type for sending {@link Usage} to RMS for Rights Assignment.
     */
    SENT_FOR_RA,

    /**
     * Action type for adding {@link Usage} to {@link Scenario}.
     */
    ADDED_TO_SCENARIO,

    /**
     * Action type for excluding {@link Usage} from {@link Scenario}.
     */
    EXCLUDED_FROM_SCENARIO,

    /**
     * Action type for sending {@link Usage} to LM.
     */
    SENT_TO_LM
}
