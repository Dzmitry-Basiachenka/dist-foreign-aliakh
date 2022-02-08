package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;

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
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 02/07/2022
 *
 * @author Anton Azarenka
 */
@RunWith(Parameterized.class)
public class NumericValidatorTest {

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
    public NumericValidatorTest(String value, boolean isValidatorFailed, boolean isValidValue) {
        this.value = value;
        this.isValidatorFailed = isValidatorFailed;
        this.isValidValue = isValidValue;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
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
    public void testNumericValidator() {
        ValueContext context = createMock(ValueContext.class);
        String errorMessage = "Field value should contain numeric values only";
        ValidationResult result = new NumericValidator().apply(value, context);
        assertEquals(isValidatorFailed, result.isError());
        verifyErrorMessage(result, errorMessage);
    }

    @Test
    public void testNumericValidatorWithCustomMessage() {
        ValueContext context = createMock(ValueContext.class);
        String errorMessage = "Field value should be positive number and should not exceed 10 digits";
        ValidationResult result = new NumericValidator(errorMessage).apply(value, context);
        assertEquals(isValidatorFailed, result.isError());
        verifyErrorMessage(result, errorMessage);
    }

    @Test
    public void testValueValidation() {
        assertEquals(isValidValue, new NumericValidator().isValid(value));
    }

    private void verifyErrorMessage(ValidationResult result, String expectedErrorMessage) {
        try {
            assertEquals(expectedErrorMessage, result.getErrorMessage());
        } catch (IllegalStateException e) {
            assertEquals("The result is not an error. It cannot contain error message", e.getMessage());
        }
    }
}
