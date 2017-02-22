package com.copyright.rup.dist.foreign.domain.common.util;

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
}
