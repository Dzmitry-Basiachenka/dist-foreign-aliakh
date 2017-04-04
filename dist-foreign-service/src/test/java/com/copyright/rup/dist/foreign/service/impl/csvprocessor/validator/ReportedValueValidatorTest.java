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

    private String value;
    private boolean expectedResult;

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
            {"125", false},
            {"125.2", false},
            {"0.11", true},
            {"0.10", true},
            {"0.01", true},
            {"0.09", true},
            {"1.00", true},
            {"10.00", true},
            {"9999999999.99", true},
            {"999999999999", false},
            {"000125.52", false},
            {"01.44", false},
            {"10000000000.00", false},
            {"555.9", false},
            {"105.8 9", false},
            {"84.44415", false},
            {"99.451", false},
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
            {"0.1", false},
            {"0.00", false},
            {"+0.00", false},
            {"-0.00", false}
        });
    }

    @Test
    public void testIsValid() {
        ReportedValueValidator validator = new ReportedValueValidator();
        assertEquals(expectedResult, validator.isValid(value));
        assertEquals("Field value should be greater than 0 and contain 2 decimals", validator.getErrorMessage());
    }
}
