package com.copyright.rup.dist.foreign.vui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link RequiredBigDecimalValidator}.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/22/2024
 *
 * @author Ihar Suvorau
 */
@RunWith(Parameterized.class)
public class RequiredBigDecimalValidatorTest {

    private final BigDecimal value;
    private final boolean expectedErrorResult;

    /**
     * Constructor.
     *
     * @param value               expected value
     * @param expectedErrorResult expected error result
     */
    public RequiredBigDecimalValidatorTest(BigDecimal value, boolean expectedErrorResult) {
        this.value = value;
        this.expectedErrorResult = expectedErrorResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, true},
            {BigDecimal.ZERO, false},
            {BigDecimal.ONE, false},
            {new BigDecimal("0000000"), false},
            {new BigDecimal("9999999.99"), false}
        });
    }

    @Test
    public void testValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new RequiredBigDecimalValidator().apply(value, context);
        assertEquals(expectedErrorResult, result.isError());
        verifyErrorMessage(result);
    }

    private void verifyErrorMessage(ValidationResult result) {
        try {
            assertEquals("Field value should be specified", result.getErrorMessage());
        } catch (IllegalStateException e) {
            assertEquals("The result is not an error. It cannot contain error message", e.getMessage());
        }
    }
}
