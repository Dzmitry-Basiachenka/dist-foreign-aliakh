package com.copyright.rup.dist.foreign.domain.common.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Month;

/**
 * Utils class for working with Usage Batch and it's properties.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
public final class UsageBatchUtils {

    private UsageBatchUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Generates Fiscal Year value from given {@link LocalDate}.
     * The fiscal year runs from July 1 - June 30.
     * For example, in fiscal year July 1, 2016 - June 30, 2017, a payment date of 12/13/2016 would generate FY2017.
     *
     * @param paymentDate {@link LocalDate} instance
     * @return Fiscal Year value
     */
    public static String getFiscalYear(LocalDate paymentDate) {
        return null != paymentDate ? getFiscalYear(calculateFiscalYear(paymentDate)) : StringUtils.EMPTY;
    }

    /**
     * Formats fiscal year, format: FYYYYY.
     * For example, for given value 2017, result will be FY2017.
     *
     * @param fiscalYear fiscal year
     * @return formatted fiscal year
     */
    public static String getFiscalYear(Integer fiscalYear) {
        return null != fiscalYear ? String.format("FY%s", fiscalYear) : StringUtils.EMPTY;
    }

    /**
     * Calculates Fiscal Year based on payment date.
     * Calculation is the same as {@link UsageBatchUtils#getFiscalYear(LocalDate)}.
     *
     * @param paymentDate {@link LocalDate} instance
     * @return Fiscal Year value
     */
    public static int calculateFiscalYear(LocalDate paymentDate) {
        return 0 >= Month.JULY.compareTo(paymentDate.getMonth()) ? (paymentDate.getYear() + 1) : paymentDate.getYear();
    }
}
