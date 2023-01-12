package com.copyright.rup.dist.foreign.domain.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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

    private final BigDecimal expectedUsdAmount;
    private final BigDecimal amount;
    private final BigDecimal conversionRate;

    /**
     * Constructor.
     *
     * @param expectedUsdAmount expected USD amount
     * @param amount            amount to check
     * @param conversionRate    conversion rate
     */
    public CalculationUtilsCalculateUsdAmountTest(BigDecimal expectedUsdAmount, BigDecimal amount,
                                                  BigDecimal conversionRate) {
        this.expectedUsdAmount = expectedUsdAmount;
        this.amount = amount;
        this.conversionRate = conversionRate;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][] {
            {new BigDecimal("0.0000000000"), BigDecimal.ZERO, BigDecimal.ONE},
            {new BigDecimal("1.0000000000"), BigDecimal.ONE, BigDecimal.ONE},
            {new BigDecimal("5.0000000000"), BigDecimal.TEN, new BigDecimal("0.5")},
            {new BigDecimal("151.8435000000"), new BigDecimal("123.45"), new BigDecimal("1.23")},
            {new BigDecimal("121.9259259275"), new BigDecimal("123.45"), new BigDecimal("0.9876543210")},
        });
    }

    @Test
    public void testCalculateUsdAmount() {
        assertEquals(expectedUsdAmount, CalculationUtils.calculateUsdAmount(amount, conversionRate));
    }
}
