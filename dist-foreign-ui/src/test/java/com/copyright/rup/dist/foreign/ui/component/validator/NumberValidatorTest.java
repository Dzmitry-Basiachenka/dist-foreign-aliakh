package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link NumberValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/20/2017
 *
 * @author Mikita Hladkikh
 */
public class NumberValidatorTest {

    private NumberValidator validator;

    @Before
    public void setUp() {
        validator = new NumberValidator();
    }

    @Test
    public void testConstructor() {
        assertEquals("Field value should contain numeric values only", validator.getErrorMessage());
    }

    @Test
    public void testIsValid() {
        assertTrue(validator.isValid("0"));
        assertTrue(validator.isValid("998784"));
        assertFalse(validator.isValid("a"));
        assertFalse(validator.isValid("4a"));
        assertFalse(validator.isValid(" "));
    }
}
