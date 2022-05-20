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
 * Verifies {@link AmountZeroValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/27/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(Parameterized.class)
public class AmountZeroValidatorTest {

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
    public AmountZeroValidatorTest(String value, boolean isValidatorFailed, boolean isValidValue) {
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
            {"0", true, false},
            {"0.0", true, false},
            {"0.1", false, true},
            {" 0.1 ", false, true},
            {"0.00", true, false},
            {"+0.00", true, false},
            {"-0.00", true, false},
            {" -1  ", true, false},
            {"1", false, true},
            {"125", false, true},
            {" 125 ", false, true},
            {"125.2", false, true},
            {"0.10", false, true},
            {"0.01", false, true},
            {"1.00", false, true},
            {"10.00", false, true},
            {"9999999999.99", false, true},
            {" 9999999999.99 ", false, true},
            {"0.005", false, true},
            {"0.004", true, false},
            {"999999999999", true, false},
            {"000125.52", true, false},
            {"01.44", true, false},
            {"10000000000.00", true, false},
            {"105.8 9", true, false},
            {"84.44415", false, true},
            {"33..5", true, false},
            {"86.0.5", true, false},
            {"amount", true, false},
            {"-15.55", true, false},
            {".55", true, false},
            {"874.", true, false},
            {"15,687", true, false},
            {"0.1", false, true},
            {"0.12", false, true},
            {"0.123", false, true},
            {"0.1234", false, true},
            {"0.12345", false, true},
            {"0.123456", false, true},
            {"0.1234567", false, true},
            {"0.12345678", false, true},
            {"0.123456789", false, true},
            {"0.1234567890", false, true},
            {" 0.1234567890 ", false, true},
            {"0.12345678901", true, false}
        });
    }

    @Test
    public void testAmountZeroValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new AmountZeroValidator().apply(value, context);
        assertEquals(isValidatorFailed, result.isError());
        verifyErrorMessage(result);
    }

    @Test
    public void testValueValidation() {
        assertEquals(isValidValue, new AmountZeroValidator().isValid(value));
    }

    private void verifyErrorMessage(ValidationResult result) {
        try {
            assertEquals("Field value should be positive number and should not exceed 10 digits",
                result.getErrorMessage());
        } catch (IllegalStateException e) {
            assertEquals("The result is not an error. It cannot contain error message", e.getMessage());
        }
    }
}
