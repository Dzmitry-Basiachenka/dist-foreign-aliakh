package com.copyright.rup.dist.foreign.domain.common.util;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class that provides functionality for calculating, converting amounts.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/22/17
 *
 * @author Aliaksandr Radkevich
 */
public final class CalculationUtils {

    private CalculationUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Calculates amount in USD based on the provided conversion rate.
     * Calculation performs by formula: USD amount = amount * conversion rate.
     *
     * @param amount         amount to be calculated in USD
     * @param conversionRate conversion rate
     * @return amount in USD corresponding to the given amount and conversion rate
     */
    public static BigDecimal calculateUsdAmount(BigDecimal amount, BigDecimal conversionRate) {
        checkArgument(0 >= BigDecimal.ZERO.compareTo(amount));
        checkArgument(0 > BigDecimal.ZERO.compareTo(conversionRate));
        return amount.multiply(conversionRate).setScale(10, RoundingMode.HALF_UP);
    }

    /**
     * Calculates conversion rate based on given fund pool amount and total amount by formula:
     * Conversion rate = fund pool amount / total amount. Result has scale = 10 and rounds with
     * {@link RoundingMode#HALF_UP}.
     *
     * @param fundPoolAmount fund pool amount
     * @param totalAmount    total amount in original currency
     * @return conversion rate
     */
    public static BigDecimal calculateConversionRate(BigDecimal fundPoolAmount, BigDecimal totalAmount) {
        checkArgument(0 > BigDecimal.ZERO.compareTo(fundPoolAmount));
        checkArgument(0 > BigDecimal.ZERO.compareTo(totalAmount));
        return fundPoolAmount.divide(totalAmount, 10, RoundingMode.HALF_UP);
    }
}
