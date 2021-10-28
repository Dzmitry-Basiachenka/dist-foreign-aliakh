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
 * Verifies {@link AmountValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/27/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(Parameterized.class)
public class AmountValidatorTest {

    private final String value;
    private final boolean isValidatorFailed;

    /**
     * Constructor.
     *
     * @param value             expected value
     * @param isValidatorFailed {@code true} if validator is failed, {@code false} otherwise
     */
    public AmountValidatorTest(String value, boolean isValidatorFailed) {
        this.value = value;
        this.isValidatorFailed = isValidatorFailed;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {StringUtils.EMPTY, false},
            {"   ", false},
            {"0", false},
            {"0.0", false},
            {"0.1", false},
            {"0.00", false},
            {"+0.00", true},
            {"-0.00", true},
            {" -1  ", true},
            {"1", false},
            {"125", false},
            {"125.2", false},
            {"0.10", false},
            {"0.01", false},
            {"1.00", false},
            {"10.00", false},
            {"9999999999.99", false},
            {"0.005", false},
            {"0.004", false},
            {"999999999999", true},
            {"000125.52", true},
            {"01.44", true},
            {"10000000000.00", true},
            {"105.8 9", true},
            {"84.44415", false},
            {"33..5", true},
            {"86.0.5", true},
            {"amount", true},
            {"-15.55", true},
            {".55", true},
            {"874.", true},
            {"15,687", true}
        });
    }

    @Test
    public void testAmountValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new AmountValidator().apply(value, context);
        assertEquals(isValidatorFailed, result.isError());
        verifyErrorMessage(result);
    }

    private void verifyErrorMessage(ValidationResult result) {
        try {
            assertEquals("Field value should be positive number or zero and should not exceed 10 digits",
                result.getErrorMessage());
        } catch (IllegalStateException e) {
            assertEquals("The result is not an error. It cannot contain error message", e.getMessage());
        }
    }
}
