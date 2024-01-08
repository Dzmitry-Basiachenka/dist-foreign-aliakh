package com.copyright.rup.dist.foreign.domain.common.util;

import static com.google.common.base.Preconditions.checkArgument;

import com.copyright.rup.dist.foreign.domain.Usage;

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

    /**
     * Calculates service fee amount based on given gross amount and service fee by formula:
     * Service Fee amount = gross amount * service fee. Result has scale = 10 and rounds with
     * {@link RoundingMode#HALF_UP}.
     *
     * @param grossAmount gross amount
     * @param serviceFee  service fee
     * @return service fee amount
     */
    public static BigDecimal calculateServiceFeeAmount(BigDecimal grossAmount, BigDecimal serviceFee) {
        checkArgument(0 > BigDecimal.ZERO.compareTo(grossAmount));
        checkArgument(0 > BigDecimal.ZERO.compareTo(serviceFee));
        return grossAmount.multiply(serviceFee).setScale(10, RoundingMode.HALF_UP);
    }

    /**
     * Calculates net amount based on given gross amount and service fee amount by formula:
     * Net amount = gross amount - service fee amount. Result has scale = 10 and rounds with
     * {@link RoundingMode#HALF_UP}.
     *
     * @param grossAmount      gross amount
     * @param serviceFeeAmount service fee amount
     * @return net amount
     */
    public static BigDecimal calculateNetAmount(BigDecimal grossAmount, BigDecimal serviceFeeAmount) {
        checkArgument(0 > BigDecimal.ZERO.compareTo(grossAmount));
        checkArgument(0 > BigDecimal.ZERO.compareTo(serviceFeeAmount));
        return grossAmount.subtract(serviceFeeAmount);
    }

    /**
     * Recalculates service fee and net amounts for specified {@link Usage} based on service fee value.
     *
     * @param usage               {@link Usage} to recalculate
     * @param rhParticipatingFlag rh participating flag
     * @param serviceFee          service fee value
     */
    public static void recalculateAmounts(Usage usage, boolean rhParticipatingFlag, BigDecimal serviceFee) {
        usage.setRhParticipating(rhParticipatingFlag);
        usage.setServiceFee(serviceFee);
        usage.setServiceFeeAmount(calculateServiceFeeAmount(usage.getGrossAmount(), serviceFee));
        usage.setNetAmount(calculateNetAmount(usage.getGrossAmount(), usage.getServiceFeeAmount()));
    }
}
