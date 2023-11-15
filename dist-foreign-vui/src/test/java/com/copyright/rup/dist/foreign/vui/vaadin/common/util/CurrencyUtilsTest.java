package com.copyright.rup.dist.foreign.vui.vaadin.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Verifies {@link CurrencyUtils}.
 * <p>
 * Copyright (C) 2014 copyright.com
 * <p>
 * Date: 11/21/14
 *
 * @author Darya Baraukova
 * @author Aliaksei Pchelnikau
 */
public class CurrencyUtilsTest {

    private static final BigDecimal POSITIVE_VALUE = new BigDecimal("1010.355");
    private static final BigDecimal NEGATIVE_VALUE = new BigDecimal("-1010.988");
    private static final String POSITIVE_STRING_VALUE = "1,010.36";
    private static final String NEGATIVE_STRING_VALUE = "-1,010.99";
    private static final String HTML_MONEY_FORMAT = "<span class='%s'>%s</span>";
    private static final String AMOUNT_STYLE = "label-amount";
    private static final String NEGATIVE_AMOUNT_STYLE = "label-negative-amount";

    @Test
    public void testFormatAsHtmlWithNullStyles() {
        assertEquals(getHtmlResult(POSITIVE_STRING_VALUE, AMOUNT_STYLE),
            CurrencyUtils.formatAsHtml(POSITIVE_VALUE, null));
        assertEquals(getHtmlResult(NEGATIVE_STRING_VALUE, AMOUNT_STYLE),
            CurrencyUtils.formatAsHtml(NEGATIVE_VALUE, null));
    }

    @Test
    public void testFormatAsHtmlWithNotNullStyles() {
        assertEquals(getHtmlResult(POSITIVE_STRING_VALUE, AMOUNT_STYLE),
            CurrencyUtils.formatAsHtml(POSITIVE_VALUE, null, AMOUNT_STYLE));
        assertEquals(getHtmlResult(NEGATIVE_STRING_VALUE, NEGATIVE_AMOUNT_STYLE.concat(" ").concat(AMOUNT_STYLE)),
            CurrencyUtils.formatAsHtml(NEGATIVE_VALUE, null, NEGATIVE_AMOUNT_STYLE,
                AMOUNT_STYLE));
    }

    @Test(expected = NullPointerException.class)
    public void testFormatAsHtmlNullValue() {
        CurrencyUtils.formatAsHtml(null);
    }

    @Test
    public void testFormatAsHtmlValue() {
        assertEquals(getHtmlResult(POSITIVE_STRING_VALUE, AMOUNT_STYLE),
            CurrencyUtils.formatAsHtml(POSITIVE_VALUE));
        assertEquals(getHtmlResult(NEGATIVE_STRING_VALUE, AMOUNT_STYLE), CurrencyUtils.formatAsHtml(NEGATIVE_VALUE));
    }

    private String getHtmlResult(String value, String labelAmount) {
        return String.format(HTML_MONEY_FORMAT, labelAmount, value);
    }
}
