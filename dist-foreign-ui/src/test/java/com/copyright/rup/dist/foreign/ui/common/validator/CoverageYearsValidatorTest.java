package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link CoverageYearsValidator}.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 01/05/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(Parameterized.class)
public class CoverageYearsValidatorTest {

    private final String value;
    private final boolean expectedError;

    /**
     * Constructor.
     *
     * @param value         value
     * @param expectedError expected error
     */
    public CoverageYearsValidatorTest(String value, boolean expectedError) {
        this.value = value;
        this.expectedError = expectedError;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][]{
            {null, true},
            {StringUtils.EMPTY, true},
            {"  ", true},
            {"12345678", true},
            {"123456789", true},
            {"1234567890", true},
            {"1950 2099", true},
            {"1949-2099", true},
            {"1950-2100", true},
            {"1950-2099", false},
            {"2022-2021", true},
            {"2021-2021", true},
            {"2021-2022", false},
            {"  2021-2022  ", false}
        });
    }

    @Test
    public void testCoverageYearsValidator() {
        ValueContext context = createMock(ValueContext.class);
        ValidationResult result = new CoverageYearsValidator("error message").apply(value, context);
        assertEquals(expectedError, result.isError());
        if (expectedError) {
            assertEquals("error message", result.getErrorMessage());
        } else {
            assertFalse(result.isError());
        }
    }
}
