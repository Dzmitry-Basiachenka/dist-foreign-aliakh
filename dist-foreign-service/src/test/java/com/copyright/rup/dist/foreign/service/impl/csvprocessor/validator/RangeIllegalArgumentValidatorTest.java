package com.copyright.rup.dist.foreign.service.impl.csvprocessor.validator;

import org.junit.Test;

/**
 * Verifies {@link RangeValidator} in case of invalid range.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/28/17
 *
 * @author Ihar Suvorau
 */
public class RangeIllegalArgumentValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        new RangeValidator(null, null);
    }
}
