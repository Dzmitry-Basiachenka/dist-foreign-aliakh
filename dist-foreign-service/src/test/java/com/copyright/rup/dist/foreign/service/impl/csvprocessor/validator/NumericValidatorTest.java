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
 * Verifies {@link NumericValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class NumericValidatorTest {

    private String value;
    private boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public NumericValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"123", true},
            {" 123", true},
            {"123 ", true},
            {"\u0967\u0968\u0969", true},
            {"0x20", false},
            {"0b1010", false},
            {"123L", false},
            {"2F", false},
            {"123\n", true},
            {"123\r", true},
            {"123\t", true},
            {"12 3", false},
            {"12_3", false},
            {"ab2c", false},
            {"12-3", false},
            {"12.3", false},
            {"-123", false},
            {"+123", false}
        });
    }

    @Test
    public void testIsValid() {
        NumericValidator validator = new NumericValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be numeric and positive", validator.getErrorMessage());
    }
}
