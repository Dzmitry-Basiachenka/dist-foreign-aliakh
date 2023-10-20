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
 * Verifies {@link IntegerValidator}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 10/19/2023
 *
 * @author Anton Azarenka
 */
@RunWith(Parameterized.class)
public class IntegerValidatorTest {

    private final String value;
    private final boolean isValidatorFailed;
    private final boolean isValidValue;

    /**
     * Constructor.
     *
     * @param value             expected value
     * @param isValidatorFailed {@code true} if validator is failed, {@code false} otherwise
     * @param isValidValue      {@code true} if value is valid, {@code false} otherwise
     */
    public IntegerValidatorTest(String value, boolean isValidatorFailed, boolean isValidValue) {
        this.value = value;
        this.isValidatorFailed = isValidatorFailed;
        this.isValidValue = isValidValue;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, false, true},
            {StringUtils.EMPTY, false, true},
            {"   ", true, false},
            {"0", false, true},
            {"0.0", true, false},
            {"0.1", true, false},
            {"0.00", true, false},
            {"+0.00", true, false},
            {"-0.00", true, false},
            {" -1  ", true, false},
            {"1", false, true},
            {"125", false, true},
            {"125.2", true, false},
            {"0.10", true, false},
            {"0.01", true, false},
            {"1.00", true, false},
            {"10.00", true, false},
            {"9999999999.99", true, false},
            {"0.005", true, false},
            {"0.004", true, false},
            {"999999999999", false, true},
            {"000125.52", true, false},
            {"01.44", true, false},
            {"10000000000.00", true, false},
            {"105.8 9", true, false},
            {"84.44415", true, false},
            {"33..5", true, false},
            {"86.0.5", true, false},
            {"amount", true, false},
            {"-15.55", true, false},
            {".55", true, false},
            {"874.", true, false},
            {"15,687", true, false}
        });
    }

    @Test
    public void testIntegerValidator() {
        IntegerValidator validator = new IntegerValidator();
        boolean result = validator.isValid(value);
        assertEquals(isValidValue, result);
        assertEquals(isValidatorFailed, !result);
        assertEquals("Field value should contain only integer numbers", validator.getErrorMessage());
    }
}
