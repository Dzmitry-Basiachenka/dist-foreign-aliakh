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
 * Verifies {@link DateFormatValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class DateFormatValidatorTest {

    private String value;
    private boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public DateFormatValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"01/01/2017", true},
            {"1/1/2017", true},
            {"1/1/00", false},
            {" 01/01/2017 ", false},
            {"01/01/2017\r", false},
            {"\n01/01/2017 ", false},
            {"\t01/01/2017 ", false},
            {"2017/01/01", false},
            {"01/2017/01", false},
            {"13/01/2017", false},
            {"01/32/2017", false},
            {"-01/01/2017", false},
            {"01/-01/2017", false},
            {"01/01/-2017", false},
            {"0/01/2017", false},
            {"01/0/2017", false},
            {"01/01/0", false}
        });
    }

    @Test
    public void testGetErrorMessage() {
        DateFormatValidator validator = new DateFormatValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should have MM/dd/yyyy or M/d/yyyy format", validator.getErrorMessage());
    }
}
