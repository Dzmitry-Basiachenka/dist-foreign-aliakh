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
 * Verifies {@link SalGradeValidator}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class SalGradeValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public SalGradeValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"K", true},
            {"0", false},
            {"1", true},
            {"2", true},
            {"3", true},
            {"4", true},
            {"5", true},
            {"6", true},
            {"7", true},
            {"8", true},
            {"10", true},
            {"11", true},
            {"12", true},
            {"13", false}
        });
    }

    @Test
    public void testGetErrorMessage() {
        SalGradeValidator validator = new SalGradeValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be in range K-12 including K,12", validator.getErrorMessage());
    }
}
