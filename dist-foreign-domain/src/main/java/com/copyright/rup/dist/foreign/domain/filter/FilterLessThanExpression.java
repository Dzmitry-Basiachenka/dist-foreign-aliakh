package com.copyright.rup.dist.foreign.domain.filter;

/**
 * Filter expression using {@link FilterOperatorEnum#LESS_THAN} operator.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/21
 *
 * @author Uladzislau Shalamitski
 */
public class FilterLessThanExpression extends FilterExpression<Number> {

    /**
     * Constructor.
     *
     * @param fieldValue value of a field
     */
    public FilterLessThanExpression(Number fieldValue) {
        super(FilterOperatorEnum.LESS_THAN, fieldValue, null);
    }
}
