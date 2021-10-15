package com.copyright.rup.dist.foreign.ui.common.validator;

/**
 * Class to validate periods (year and month).
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/15/2021
 *
 * @author Aliaksandr Liakh
 */
public final class PeriodValidator {

    /**
     * Valid period length.
     */
    public static final int VALID_LENGTH = 6;

    /**
     * Minimum year of the period.
     */
    public static final int MIN_YEAR = 1950;

    /**
     * Maximum year of the period.
     */
    public static final int MAX_YEAR = 2099;

    /**
     * Valid 6th month number of the period.
     */
    public static final int MONTH_6 = 6;

    /**
     * Valid 12th month number of the period.
     */
    public static final int MONTH_12 = 12;

    private PeriodValidator() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Checks if the year of the period is valid.
     *
     * @param period period
     * @return {@code true} if the period has valid year, otherwise {@code false}.
     */
    public static boolean isYearValid(String period) {
        int year = Integer.parseInt(period.trim().substring(0, 4));
        return year >= MIN_YEAR && year <= MAX_YEAR;
    }

    /**
     * Checks if the month of the period is valid.
     *
     * @param period period
     * @return {@code true} if the period has valid month, otherwise {@code false}.
     */
    public static boolean isMonthValid(String period) {
        int month = Integer.parseInt(period.trim().substring(4, 6));
        return month == MONTH_6 || month == MONTH_12;
    }
}
