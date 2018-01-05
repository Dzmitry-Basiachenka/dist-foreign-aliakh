package com.copyright.rup.dist.foreign.domain;

/**
 * Represents types of actions for {@link Scenario}s.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/13/2017
 *
 * @author Uladzislau_Shalamitski
 */
public enum ScenarioActionTypeEnum {

    /**
     * Action type for add usages to {@link Scenario}.
     */
    ADDED_USAGES,
    /**
     * Action type for submit {@link Scenario}.
     */
    SUBMITTED,
    /**
     * Action type for approve {@link Scenario}.
     */
    APPROVED,
    /**
     * Action type for reject {@link Scenario}.
     */
    REJECTED,
    /**
     * Action type for sending {@link Scenario} to LM.
     */
    SENT_TO_LM,
}
