package com.copyright.rup.dist.foreign.domain;

/**
 * Represents types of actions for {@link UdmValue}s.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/16/2021
 *
 * @author Ihar Suvorau
 */
public enum UdmValueActionTypeEnum {

    /**
     * Action type for UDM value creation.
     */
    CREATED,

    /**
     * Action type for UDM value edit.
     */
    VALUE_EDIT,

    /**
     * Action type for UDM value assignment.
     */
    ASSIGNEE_CHANGE,

    /**
     * Action type for UDM value unassignment.
     */
    UNASSIGN,

    /**
     * Action type for UDM value publish.
     */
    PUBLISH_TO_BASELINE,

    /**
     * Action type for UDM value proxy calculation.
     */
    PROXY_CALCULATION
}
