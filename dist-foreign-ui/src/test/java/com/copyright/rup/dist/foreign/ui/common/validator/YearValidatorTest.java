package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link YearValidator}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/18/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class YearValidatorTest {

    private final String value;
    private final boolean expectedErrorResult;

    /**
     * Constructor.
     *
     * @param value               expected value
     * @param expectedErrorResult expected error result
     */
    public YearValidatorTest(String value, boolean expectedErrorResult) {
        this.value = value;
        this.expectedErrorResult = expectedErrorResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, false},
            {StringUtils.EMPTY, false},
            {"   ", false},
            {"1949", true},
            {"1950", false},
            {" 1950 ", false},
            {"1999", false},
            {" 1999 ", false},
            {"2099", false},
            {" 2099 ", false},
            {"2100", true}
        });
    }

    @Test
    public void testYearValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new YearValidator().apply(value, context);
        assertEquals(expectedErrorResult, result.isError());
        verifyErrorMessage(result);
    }

    private void verifyErrorMessage(ValidationResult result) {
        try {
            assertEquals("Field value should be in range from 1950 to 2099", result.getErrorMessage());
        } catch (IllegalStateException e) {
            assertEquals("The result is not an error. It cannot contain error message", e.getMessage());
        }
    }
}
