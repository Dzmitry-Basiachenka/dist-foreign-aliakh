package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link RequiredValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/20/17
 *
 * @author Aliaksei Pchelnikau
 */
public class RequiredValidatorTest {

    private RequiredValidator validator;

    @Before
    public void setUp() {
        validator = new RequiredValidator();
    }

    @Test
    public void testIsValid() {
        assertTrue(validator.isValid("test"));
        assertFalse(validator.isValid(""));
        assertFalse(validator.isValid(" "));
        assertFalse(validator.isValid(null));
    }

    @Test
    public void getErrorMessage() {
        assertEquals("Field is required and cannot be null or empty", validator.getErrorMessage());
    }
}
