package com.copyright.rup.dist.foreign.vui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link RequiredValidator}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/27/2021
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(Parameterized.class)
public class RequiredValidatorTest {

    private final String value;
    private final boolean expectedErrorResult;

    /**
     * Constructor.
     *
     * @param value               expected value
     * @param expectedErrorResult expected error result
     */
    public RequiredValidatorTest(String value, boolean expectedErrorResult) {
        this.value = value;
        this.expectedErrorResult = expectedErrorResult;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"   ", true},
            {" 1 ", false},
            {" -2 ", false},
            {"!@#$%^&*()_+=-`~", false},
            {"0.01", false},
            {"1.00", false},
            {"10.0034567", false},
            {"1000", false},
            {"100000000000", false},
            {"999999999999", false},
            {"Value", false},
            {"  value   ", false},
            {"  Abcdef ghijk ", false}
        });
    }

    @Test
    public void testValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new RequiredValidator().apply(value, context);
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
