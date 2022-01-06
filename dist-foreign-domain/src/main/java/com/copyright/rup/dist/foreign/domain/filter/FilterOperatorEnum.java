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
    EQUALS(1),
    /**
     * 'Does Not Equal' filter operator.
     */
    DOES_NOT_EQUAL(1),
    /**
     * 'Greater than' filter operator.
     */
    GREATER_THAN(1),
    /**
     * 'Greater than or equals to' filter operator.
     */
    GREATER_THAN_OR_EQUALS_TO(1),
    /**
     * 'Less than' filter operator.
     */
    LESS_THAN(1),
    /**
     * 'Less than or equals to' filter operator.
     */
    LESS_THAN_OR_EQUALS_TO(1),
    /**
     * 'Between' filter operator.
     */
    BETWEEN(2),
    /**
     * 'Is NULL' filter operator.
     */
    IS_NULL(0),
    /**
     * 'Is not NULL' filter operator.
     */
    IS_NOT_NULL(0),
    /**
     * Contains filter operator.
     */
    CONTAINS(1);

    private final int argumentsNumber;

    /**
     * Constructor.
     *
     * @param argumentsNumber arguments number.
     */
    FilterOperatorEnum(int argumentsNumber) {
        this.argumentsNumber = argumentsNumber;
    }

    public int getArgumentsNumber() {
        return argumentsNumber;
    }
}
