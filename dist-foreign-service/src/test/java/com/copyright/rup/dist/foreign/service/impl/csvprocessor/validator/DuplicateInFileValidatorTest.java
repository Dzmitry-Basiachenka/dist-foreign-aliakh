package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link DuplicateInFileValidator}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/23/17
 *
 * @author Aliaksei Pchelnikau
 */
public class DuplicateInFileValidatorTest {

    private DuplicateInFileValidator validator;

    @Before
    public void setUp() {
        validator = new DuplicateInFileValidator();
    }

    @Test
    public void testIsValid() {
        String duplicateValue = "value1";
        assertTrue(validator.isValid(duplicateValue));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());

        assertFalse(validator.isValid(duplicateValue));
        assertEquals("Duplicate with Line 2", validator.getErrorMessage());

        assertTrue(validator.isValid(null));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());

        assertFalse(validator.isValid(duplicateValue));
        assertEquals("Duplicate with Line 2, 3", validator.getErrorMessage());

        assertTrue(validator.isValid("value2"));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());
    }

    @Test
    public void testIsValidEmptyValue() {
        assertTrue(validator.isValid(""));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());
        assertTrue(validator.isValid(null));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());
        assertTrue(validator.isValid(null));
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());
    }

    @Test
    public void testGetErrorMessage() {
        assertEquals(StringUtils.EMPTY, validator.getErrorMessage());
    }
}
