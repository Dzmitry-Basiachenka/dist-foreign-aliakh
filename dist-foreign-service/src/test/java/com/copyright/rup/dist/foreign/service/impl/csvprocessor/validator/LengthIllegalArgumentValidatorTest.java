package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.junit.Test;

/**
 * Verifies {@link LengthValidator} in case of invalid length.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/28/17
 *
 * @author Ihar Suvorau
 */
public class LengthIllegalArgumentValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullValue() {
        new LengthValidator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeValue() {
        new LengthValidator(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroValue() {
        new LengthValidator(0);
    }
}
