package com.copyright.rup.dist.foreign.domain.filter;

/**
 * Filter expression using {@link FilterOperatorEnum#BETWEEN} operator.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/21
 *
 * @author Uladzislau Shalamitski
 */
public class FilterBetweenExpression extends FilterExpression<Number> {

    /**
     * Constructor.
     *
     * @param fieldFirstValue  first value of a field
     * @param fieldSecondValue second value of a field
     */
    public FilterBetweenExpression(Number fieldFirstValue, Number fieldSecondValue) {
        super(FilterOperatorEnum.BETWEEN, fieldFirstValue, fieldSecondValue);
    }
}
