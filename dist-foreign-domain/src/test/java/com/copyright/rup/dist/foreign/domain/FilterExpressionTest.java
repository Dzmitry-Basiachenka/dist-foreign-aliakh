package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import org.junit.Test;

/**
 * Verifies {@link FilterExpression}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class FilterExpressionTest {

    @Test
    public void testIsEmptyConstructor() {
        assertTrue(new FilterExpression<>().isEmpty());
    }

    @Test
    public void testIsEmptyEquals() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.EQUALS, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.EQUALS, 0, null).isEmpty());
    }

    @Test
    public void testIsEmptyGreaterThan() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.GREATER_THAN, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, 0, null).isEmpty());
    }

    @Test
    public void testIsEmptyGreaterThanOrEqualsTo() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, 0, null).isEmpty());
    }

    @Test
    public void testIsEmptyLessThan() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.LESS_THAN, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 0, null).isEmpty());
    }

    @Test
    public void testIsEmptyLessThanOrEqualsTo() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, 0, null).isEmpty());
    }

    @Test
    public void testIsEmptyBetween() {
        assertTrue(new FilterExpression<Integer>(FilterOperatorEnum.BETWEEN, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 0, 1).isEmpty());
    }

    @Test
    public void testIsEmptyIsNull() {
        assertFalse(new FilterExpression<Integer>(FilterOperatorEnum.IS_NULL, null, null).isEmpty());
    }

    @Test
    public void testIsEmptyIsNotNull() {
        assertFalse(new FilterExpression<Integer>(FilterOperatorEnum.IS_NOT_NULL, null, null).isEmpty());
    }

    @Test
    public void testIsEmptyContains() {
        assertTrue(new FilterExpression<String>(FilterOperatorEnum.CONTAINS, null, null).isEmpty());
        assertFalse(new FilterExpression<>(FilterOperatorEnum.CONTAINS, "0", null).isEmpty());
    }
}
