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
 * Parameterized test that verifies {@link CalculationUtils#calculateConversionRate(BigDecimal, BigDecimal)}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/22/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(Parameterized.class)
public class CalculationUtilsCalculateConversionRateTest {

    private final BigDecimal expectedRate;
    private final BigDecimal fundPoolAmount;
    private final BigDecimal totalAmount;

    /**
     * Constructor.
     *
     * @param expectedRate   expected conversion rate
     * @param fundPoolAmount fund pool amount to check
     * @param totalAmount    conversion rate
     */
    public CalculationUtilsCalculateConversionRateTest(BigDecimal expectedRate, BigDecimal fundPoolAmount,
                                                       BigDecimal totalAmount) {
        this.expectedRate = expectedRate;
        this.fundPoolAmount = fundPoolAmount;
        this.totalAmount = totalAmount;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return List.of(new Object[][] {
            {new BigDecimal("1.1111111111"), BigDecimal.TEN, new BigDecimal("9.0")},
            {new BigDecimal("0.9090909091"), BigDecimal.TEN, new BigDecimal("11.0")},
            {new BigDecimal("0.1000000000"), new BigDecimal("0.1"), BigDecimal.ONE},
            {new BigDecimal("0.0000000000"), new BigDecimal("0.0000000001"), BigDecimal.TEN},
        });
    }

    @Test
    public void testCalculateUsdAmount() {
        assertEquals(expectedRate, CalculationUtils.calculateConversionRate(fundPoolAmount, totalAmount));
    }
}
