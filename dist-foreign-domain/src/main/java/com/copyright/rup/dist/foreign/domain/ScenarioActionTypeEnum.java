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
     * Action type for approve {@link Scenario}.
     */
    APPROVED("Approved"),

    /**
     * Action type for reject {@link Scenario}.
     */
    REJECTED("Rejected"),

    /**
     * Action type for submit {@link Scenario}.
     */
    SUBMITTED("Submitted"),

    /**
     * Action type for add usages to {@link Scenario}.
     */
    ADDED_USAGES("Added usages");

    private final String description;

    /**
     * Constructor.
     *
     * @param description scenario action description.
     */
    ScenarioActionTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
