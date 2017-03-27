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
 * Verifies {@link LengthValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class LengthValidatorTest {

    private String value;
    private boolean expectedResult;
    private int length;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     * @param length         max length
     */
    public LengthValidatorTest(String value, int length, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
        this.length = length;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, 10, true},
            {StringUtils.EMPTY, 10, true},
            {"012356789", 10, true},
            {"01234567891", 10, false},
            {" 0123456789", 10, false},
            {"0", 1, true},
            {"01", 1, false}
        });
    }

    @Test
    public void testIsValid() {
        LengthValidator validator = new LengthValidator(length);
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals(String.format("Field value should not exceed %d characters", length), validator.getErrorMessage());
    }
}
