package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link AmountValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/20/2017
 *
 * @author Mikita Hladkikh
 */
public class AmountValidatorTest {

    private AmountValidator amountValidator;

    @Before
    public void setUp() {
        amountValidator = new AmountValidator();
    }

    @Test
    public void testConstructor() {
        assertEquals("Value field range should be from 0 to 9999999999.99", amountValidator.getErrorMessage());
    }

    @Test
    public void testIsValid() {
        assertTrue(amountValidator.isValid(BigDecimal.ZERO));
        assertTrue(amountValidator.isValid(new BigDecimal("9999999999.99")));
        assertFalse(amountValidator.isValid(new BigDecimal("-0.1")));
        assertFalse(amountValidator.isValid(new BigDecimal("10000000000.00")));
    }
}
