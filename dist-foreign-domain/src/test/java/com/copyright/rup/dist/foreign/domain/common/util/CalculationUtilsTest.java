package com.copyright.rup.dist.foreign.domain.common.util;

import com.copyright.rup.dist.common.test.TestUtils;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link CalculationUtils}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/22/17
 *
 * @author Aliaksandr Radkevich
 * @see CalculationUtilsCalculateUsdAmountTest
 */
public class CalculationUtilsTest {

    @Test
    public void testConstructor() {
        TestUtils.validatePrivateConstructor(CalculationUtils.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateUsdAmountNegativeAmount() {
        CalculationUtils.calculateUsdAmount(new BigDecimal("-1"), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateUsdAmountZeroConversionRate() {
        CalculationUtils.calculateUsdAmount(BigDecimal.ONE, new BigDecimal("0.0000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateUsdAmountNegativeConversionRate() {
        CalculationUtils.calculateUsdAmount(BigDecimal.ONE, new BigDecimal("-0.9"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateConversionRateZeroFundPoolAmount() {
        CalculationUtils.calculateConversionRate(new BigDecimal(" - 0.0"), BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateConversionRateZeroTotalAmount() {
        CalculationUtils.calculateConversionRate(BigDecimal.ONE, new BigDecimal("0.000"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateConversionRateNegativeTotalAmount() {
        CalculationUtils.calculateConversionRate(BigDecimal.ONE, new BigDecimal("-1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateConversionRateNegativeFundPoolAmount() {
        CalculationUtils.calculateConversionRate(new BigDecimal("-1"), BigDecimal.ONE);
    }
}
