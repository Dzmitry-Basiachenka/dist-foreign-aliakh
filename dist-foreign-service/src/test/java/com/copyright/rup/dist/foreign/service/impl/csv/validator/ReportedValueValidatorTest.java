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
 * Verifies {@link ReportedValueValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/27/17
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class ReportedValueValidatorTest {

    private final String value;
    private final boolean expectedResult;

    /**
     * Constructor.
     *
     * @param value          expected value
     * @param expectedResult expected result
     */
    public ReportedValueValidatorTest(String value, boolean expectedResult) {
        this.value = value;
        this.expectedResult = expectedResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"1", true},
            {"125", true},
            {"125.2", true},
            {"0.10", true},
            {"0.01", true},
            {"1.00", true},
            {"10.00", true},
            {"9999999999.99", true},
            {"0.005", true},
            {"0.004", false},
            {"999999999999", false},
            {"000125.52", false},
            {"01.44", false},
            {"10000000000.00", false},
            {"105.8 9", false},
            {"84.44415", true},
            {"33..5", false},
            {"86.0.5", false},
            {"amount", false},
            {"-15.55", false},
            {".55", false},
            {"874.", false},
            {" ", false},
            {"15,687", false},
            {"0", false},
            {"0.0", false},
            {"0.1", true},
            {"0.00", false},
            {"+0.00", false},
            {"-0.00", false}
        });
    }

    @Test
    public void testIsValid() {
        ReportedValueValidator validator = new ReportedValueValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be greater than 0 after rounding", validator.getErrorMessage());
    }
}
