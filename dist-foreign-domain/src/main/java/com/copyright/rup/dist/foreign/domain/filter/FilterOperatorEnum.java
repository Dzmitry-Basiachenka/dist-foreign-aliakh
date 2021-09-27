package com.copyright.rup.dist.foreign.domain.filter;

/**
 * Represents enum for UDM filter operators.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/29/2020
 *
 * @author Ihar Suvorau
 */
public enum FilterOperatorEnum {

    /**
     * 'Equals' filter operator.
     */
    EQUALS,
    /**
     * 'Greater than' filter operator.
     */
    GREATER_THAN,
    /**
     * 'Greater than or equals to' filter operator.
     */
    GREATER_THAN_OR_EQUALS_TO,
    /**
     * 'Less than' filter operator.
     */
    LESS_THAN,
    /**
     * 'Less than or equals to' filter operator.
     */
    LESS_THAN_OR_EQUALS_TO,
    /**
     * 'Between' filter operator.
     */
    BETWEEN,
    /**
     * 'Is NULL' filter operator.
     */
    IS_NULL,
    /**
     * 'Is not NULL' filter operator.
     */
    IS_NOT_NULL,
    /**
     * Contains filter operator.
     */
    CONTAINS
}
