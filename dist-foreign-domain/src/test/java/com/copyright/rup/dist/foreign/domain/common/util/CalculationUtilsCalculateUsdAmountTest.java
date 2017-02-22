package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

/**
 * Parameterized test that verifies {@link CalculationUtils#calculateUsdAmount(BigDecimal, BigDecimal)}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/22/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(Parameterized.class)
public class CalculationUtilsCalculateUsdAmountTest {

    private BigDecimal expected;
    private BigDecimal amount;
    private BigDecimal conversionRate;

    /**
     * Constructor.
     *
     * @param expected       expected result
     * @param amount         amount to check
     * @param conversionRate conversion rate
     */
    public CalculationUtilsCalculateUsdAmountTest(BigDecimal expected, BigDecimal amount, BigDecimal conversionRate) {
        this.expected = expected;
        this.amount = amount;
        this.conversionRate = conversionRate;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {new BigDecimal("0.0000000000"), BigDecimal.ZERO, BigDecimal.ONE},
            {new BigDecimal("1.0000000000"), BigDecimal.ONE, BigDecimal.ONE},
            {new BigDecimal("5.0000000000"), BigDecimal.TEN, new BigDecimal("0.5")},
            {new BigDecimal("151.8435000000"), new BigDecimal("123.45"), new BigDecimal("1.23")},
            {new BigDecimal("121.9259259275"), new BigDecimal("123.45"), new BigDecimal("0.9876543210")},
        });
    }

    @Test
    public void testCalculateUsdAmount() {
        assertEquals(expected, CalculationUtils.calculateUsdAmount(amount, conversionRate));
    }
}
