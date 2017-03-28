package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link RangeValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class RangeValidatorTest {

    private String value;
    private boolean expectedResult;
    private Integer minValue;
    private Integer maxValue;
    private String message;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param minValue       min value
     * @param maxValue       max value
     * @param expectedResult expected result
     */
    public RangeValidatorTest(String value, String message, Integer minValue, Integer maxValue,
                              boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.message = message;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, StringUtils.EMPTY, 1000, 1500, true},
            {"3333", "Field value should be in range from 2000 to 5000", 2000, 5000, true},
            {" 3333  ", "Field value should be in range from 2000 to 5000", 2000, 5000, true},
            {"333", "Field value should be in range from 2005 to 2005", 2005, 2005, false},
            {"33333", "Field value should be in range from 1000 to 5000", 1000, 5000, false},
            {"555", "Field value should be less than 5000", null, 5000, true},
            {"5555", "Field value should be less than 5000", null, 5000, false},
            {"10000", "Field value should be greater than 1000", 1000, null, true},
            {"100", "Field value should be greater than 1000", 1000, null, false},
            {"0", "Field value should be greater than 0", 0, null, false},
            {"999", "Field value should be less than 999", null, 999, false},
        });
    }

    @Test
    public void testIsValid() {
        RangeValidator validator = new RangeValidator(minValue, maxValue);
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals(message, validator.getErrorMessage());
    }
}
