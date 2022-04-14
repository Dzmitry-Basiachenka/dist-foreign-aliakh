package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link GrantTypeOfUseValidator}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/14/2022
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class GrantTypeOfUseValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public GrantTypeOfUseValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"PRINT", true},
            {"print", true},
            {"DigitaL", true},
            {"DIGITAL", true},
            {"0", false},
            {"aaa", false},
            {" print ", false},
            {" DITITAL ", false},
            {"PRINT&DIGITAL", false}
        });
    }

    @Test
    public void testIsValid() {
        GrantTypeOfUseValidator validator = new GrantTypeOfUseValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be PRINT or DIGITAL", validator.getErrorMessage());
    }
}
