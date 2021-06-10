package com.copyright.rup.dist.foreign.domain.filter;

/**
 * Filter expression using {@link FilterOperatorEnum#GREATER_THAN} operator.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/21
 *
 * @author Uladzislau Shalamitski
 */
public class FilterGreaterThanExpression extends FilterExpression<Number> {

    /**
     * Constructor.
     *
     * @param fieldValue value of a field
     */
    public FilterGreaterThanExpression(Number fieldValue) {
        super(FilterOperatorEnum.GREATER_THAN, fieldValue, null);
    }
}
