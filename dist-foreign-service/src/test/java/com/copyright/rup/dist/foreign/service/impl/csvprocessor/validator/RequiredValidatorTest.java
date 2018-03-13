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
 * Verifies {@link RequiredValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/20/17
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(Parameterized.class)
public class RequiredValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public RequiredValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"test", true},
            {"test\n", true},
            {"test\r", true},
            {"test\t", true},
            {StringUtils.EMPTY, false},
            {" ", false},
            {null, false},
            {"\n", false},
            {"\r", false},
            {"\t", false}
        });
    }

    @Test
    public void testIsValid() {
        RequiredValidator validator = new RequiredValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field is required and cannot be null or empty", validator.getErrorMessage());
    }
}
