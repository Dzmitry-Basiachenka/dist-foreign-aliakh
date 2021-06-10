package com.copyright.rup.dist.foreign.domain.filter;

/**
 * Filter expression using {@link FilterOperatorEnum#EQUALS} operator.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/21
 *
 * @author Uladzislau Shalamitski
 */
public class FilterEqualsExpression extends FilterExpression<Number> {

    /**
     * Constructor.
     *
     * @param fieldValue value of a field
     */
    public FilterEqualsExpression(Number fieldValue) {
        super(FilterOperatorEnum.EQUALS, fieldValue, null);
    }
}
