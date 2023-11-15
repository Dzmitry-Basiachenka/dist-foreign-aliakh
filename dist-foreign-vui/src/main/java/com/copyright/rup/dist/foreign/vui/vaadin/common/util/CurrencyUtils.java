package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

/**
 * Utility class which provides methods for converting and formatting monetary amount values.
 * <p>
 * Copyright (C) 2014 copyright.com
 * <p>
 * Date: 10/28/14
 *
 * @author Darya Baraukova
 * @author Aliaksei Pchelnikau
 */
public final class CurrencyUtils {

    private static final String HTML_MONEY_FORMAT = "<span class='%s'>%s</span>";
    private static final String AMOUNT_STYLE = "label-amount";
    private static final String FORMAT_MONEY_PATTERN = "#,##0.00;-#,##0.00";
    private static final DecimalFormat MONEY_FORMATTER;
    private static final char DECIMAL_SEPARATOR = '.';
    private static final char GROUPING_SEPARATOR = ',';

    private CurrencyUtils() {
        throw new AssertionError("Constructor shouldn't be called directly");
    }

    /**
     * Get decimal format symbols with decimal separator "." and grouping separator ",".
     *
     * @return formatted symbols
     */
    public static DecimalFormatSymbols getParameterizedDecimalFormatSymbols() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(DECIMAL_SEPARATOR);
        symbols.setGroupingSeparator(GROUPING_SEPARATOR);
        return symbols;
    }

    /**
     * Creates string which is used to display amount values as span element.
     *
     * @param value the value to display
     * @return value wrapped with &lt;span&gt; tag
     */
    public static String formatAsHtml(BigDecimal value) {
        return formatAsHtml(Objects.requireNonNull(value), null);
    }

    /**
     * Creates string which is used to display Money values as span element.
     * For empty styles set default style AMOUNT_CLASS_STYLE
     *
     * @param value     the value to format
     * @param formatter formatter to format
     * @param styles    styles
     * @return formatted string
     * @throws NullPointerException if value is null
     */
    public static String formatAsHtml(BigDecimal value, DecimalFormat formatter, String... styles) {
        Objects.requireNonNull(value);
        if (ArrayUtils.isNotEmpty(styles)) {
            String joinedStyles = String.join(" ", styles);
            return String.format(HTML_MONEY_FORMAT, joinedStyles, format(value, formatter));
        } else {
            return String.format(HTML_MONEY_FORMAT, AMOUNT_STYLE, format(value, formatter));
        }
    }

    /**
     * Formats decimal value to string using {@link DecimalFormat} formatter.
     * Uses the next separators for formatting:
     * <ul>
     * <li>{@link #DECIMAL_SEPARATOR}</li>
     * <li>{@link #GROUPING_SEPARATOR}</li>
     * </ul>
     *
     * @param value     value to format
     * @param formatter formatter to format, MONEY_FORMATTER if null
     * @return formatted string or empty string in case if value is null
     */
    public static String format(BigDecimal value, DecimalFormat formatter) {
        return Objects.isNull(value)
            ? StringUtils.EMPTY
            : Objects.nonNull(formatter) ? formatter.format(value) : MONEY_FORMATTER.format(value);
    }

    static {
        MONEY_FORMATTER = new DecimalFormat(FORMAT_MONEY_PATTERN, getParameterizedDecimalFormatSymbols());
        MONEY_FORMATTER.setRoundingMode(RoundingMode.HALF_UP);
    }
}
