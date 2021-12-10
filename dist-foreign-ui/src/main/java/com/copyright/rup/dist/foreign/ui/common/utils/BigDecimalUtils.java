package com.copyright.rup.dist.foreign.ui.common.utils;

import com.copyright.rup.vaadin.util.CurrencyUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Class for conversions between {@link BigDecimal} and {@link String}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/09/2021
 *
 * @author Aliaksandr Liakh
 */
public final class BigDecimalUtils {

    private static final DecimalFormat CURRENCY_FORMATTER_FOR_GRID = new DecimalFormat("#,##0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());

    private static final DecimalFormat CURRENCY_FORMATTER_FOR_DIALOG = new DecimalFormat("0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());

    private BigDecimalUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Formats decimal amount without trailing zeros after the second digit after the decimal point
     * for grid (with grouping separators).
     *
     * @param value instance of {@link BigDecimal}
     * @return formatted string or empty string in case if amount is null
     */
    public static String formatCurrencyForGrid(BigDecimal value) {
        return CurrencyUtils.format(value, CURRENCY_FORMATTER_FOR_GRID);
    }

    /**
     * Formats decimal amount without trailing zeros after the second digit after the decimal point
     * for dialog (without grouping separators).
     *
     * @param value instance of {@link BigDecimal}
     * @return formatted string or empty string in case if amount is null
     */
    public static String formatCurrencyForDialog(BigDecimal value) {
        return CurrencyUtils.format(value, CURRENCY_FORMATTER_FOR_DIALOG);
    }
}
