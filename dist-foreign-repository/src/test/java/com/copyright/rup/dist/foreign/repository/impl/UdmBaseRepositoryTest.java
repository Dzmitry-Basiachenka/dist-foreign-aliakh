package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import org.junit.Test;

/**
 * Verifies {@link UdmBaseRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 24/01/2022
 *
 * @author Aliaksandr Liakh
 */
public class UdmBaseRepositoryTest {

    private final UdmBaseRepository udmBaseRepository = new UdmBaseRepository();

    @Test
    public void testEscapePropertyForMyBatisSqlFragment() {
        String value = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
        FilterExpression<String> filterExpression = new FilterExpression<>(FilterOperatorEnum.EQUALS, value, value);
        FilterExpression<String> escapedFilterExpression =
            udmBaseRepository.escapePropertyForMyBatisSqlFragment(filterExpression);
        assertNotSame(filterExpression, escapedFilterExpression);
        assertEquals(filterExpression.getOperator(), escapedFilterExpression.getOperator());
        assertEquals("!\"#$\\%&''()*+,-./:;<=>?@[\\\\]^\\_`{|}~", escapedFilterExpression.getFieldFirstValue());
        assertEquals(filterExpression.getFieldSecondValue(), escapedFilterExpression.getFieldSecondValue());
    }

    @Test
    public void testEscapePropertyForMyBatisSqlFragmentOperatorIsNull() {
        String value = "value";
        FilterExpression<String> filterExpression = new FilterExpression<>(null, value, value);
        FilterExpression<String> escapedFilterExpression =
            udmBaseRepository.escapePropertyForMyBatisSqlFragment(filterExpression);
        assertSame(filterExpression, escapedFilterExpression);
    }
}
