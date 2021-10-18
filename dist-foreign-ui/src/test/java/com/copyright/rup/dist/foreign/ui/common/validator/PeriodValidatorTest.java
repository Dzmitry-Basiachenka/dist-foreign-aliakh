package com.copyright.rup.dist.foreign.ui.common.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import org.junit.Test;

/**
 * Verifies {@link PeriodValidator}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/15/2021
 *
 * @author Aliaksandr Liakh
 */
public class PeriodValidatorTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(PeriodValidator.class);
    }

    @Test(expected = NullPointerException.class)
    public void testIsYearValidNull() {
        PeriodValidator.isYearValid(null);
    }

    @Test
    public void testIsYearValid() {
        assertFalse(PeriodValidator.isYearValid("194900"));
        assertTrue(PeriodValidator.isYearValid("195000"));
        assertTrue(PeriodValidator.isYearValid(" 195000 "));
        assertTrue(PeriodValidator.isYearValid("209900"));
        assertTrue(PeriodValidator.isYearValid(" 209900 "));
        assertFalse(PeriodValidator.isYearValid("210000"));
    }

    @Test(expected = NullPointerException.class)
    public void testIsMonthValidNull() {
        PeriodValidator.isMonthValid(null);
    }

    @Test
    public void testIsMonthValid() {
        assertFalse(PeriodValidator.isMonthValid("000000"));
        assertFalse(PeriodValidator.isMonthValid("000001"));
        assertFalse(PeriodValidator.isMonthValid("000002"));
        assertFalse(PeriodValidator.isMonthValid("000003"));
        assertFalse(PeriodValidator.isMonthValid("000004"));
        assertFalse(PeriodValidator.isMonthValid("000005"));
        assertTrue(PeriodValidator.isMonthValid("000006"));
        assertTrue(PeriodValidator.isMonthValid(" 000006 "));
        assertFalse(PeriodValidator.isMonthValid("000007"));
        assertFalse(PeriodValidator.isMonthValid("000008"));
        assertFalse(PeriodValidator.isMonthValid("000009"));
        assertFalse(PeriodValidator.isMonthValid("000010"));
        assertFalse(PeriodValidator.isMonthValid("000011"));
        assertTrue(PeriodValidator.isMonthValid("000012"));
        assertTrue(PeriodValidator.isMonthValid(" 000012 "));
        assertFalse(PeriodValidator.isMonthValid("000013"));
    }
}
