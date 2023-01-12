package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link YearValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class YearValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public YearValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"1000", true},
            {"9999", true},
            {"1001", true},
            {"9998", true},
            {"999", false},
            {"10000", false},
            {"a111", false},
            {"1a11", false},
            {"11a1", false},
            {"111a", false},
            {"0", false},
            {"-111", false},
            {"0111", false},
            {"1111111111", false}
        });
    }

    @Test
    public void testIsValid() {
        YearValidator validator = new YearValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be numeric in range from 1000 to 9999", validator.getErrorMessage());
    }
}
