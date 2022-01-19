package com.copyright.rup.dist.foreign.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;

/**
 * Verifies {@link UdmValueFilter}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 10/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFilterTest {

    private static final Integer PERIOD = 202012;
    private static final UdmValueStatusEnum STATUS = UdmValueStatusEnum.NEW;
    private static final Currency CURRENCY = new Currency("USD", "US Dollar");
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String LAST_VALUE_PERIOD = "202106";
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final String SYSTEM_STANDARD_NUMBER = "0927-7765";
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("200.00");
    private static final String PRICE_COMMENT = "price comment";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal CONTENT = new BigDecimal("70");
    private static final String CONTENT_COMMENT = "content comment";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final PublicationType PUB_TYPE;
    private static final PublicationType LAST_PUB_TYPE;
    private static final String COMMENT = "comment";

    static {
        PUB_TYPE = new PublicationType();
        PUB_TYPE.setName("Book");
        LAST_PUB_TYPE = new PublicationType();
        LAST_PUB_TYPE.setName("News Source");
    }

    @Test
    public void testIsEmpty() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPeriods(Collections.singleton(PERIOD));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPeriods(new HashSet<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPeriods(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setStatus(STATUS);
        assertFalse(valueFilter.isEmpty());
        valueFilter.setStatus(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setCurrency(CURRENCY);
        assertFalse(valueFilter.isEmpty());
        valueFilter.setCurrency(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setAssignees(new HashSet<>(Collections.singleton(ASSIGNEE)));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setAssignees(new HashSet<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setAssignees(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastValuePeriods(Collections.singleton(LAST_VALUE_PERIOD));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastValuePeriods(new HashSet<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastValuePeriods(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPubType(PUB_TYPE);
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPubType(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastPubType(LAST_PUB_TYPE);
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastPubType(null);
        assertTrue(valueFilter.isEmpty());
        valueFilter.setComment(COMMENT);
        assertFalse(valueFilter.isEmpty());
        valueFilter.setComment(null);
        assertTrue(valueFilter.isEmpty());
    }

    @Test
    public void testIsEmptyExpressions() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        assertTrue(valueFilter.isEmpty());
        valueFilter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setWrWrkInstExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setSystemTitleExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_STANDARD_NUMBER, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setRhAccountNumberExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setRhNameExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPriceExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPriceInUsdExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.N));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPriceFlagExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastPriceFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastPriceFlagExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setPriceCommentExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastPriceCommentExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setContentExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setContentFlagExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastContentFlagExpression(new FilterExpression<>(FilterOperatorEnum.Y));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastContentFlagExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setContentCommentExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
        valueFilter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT, null));
        assertFalse(valueFilter.isEmpty());
        valueFilter.setLastContentCommentExpression(new FilterExpression<>());
        assertTrue(valueFilter.isEmpty());
    }

    @Test
    public void testConstructor() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        assertTrue(valueFilter.getPeriods().isEmpty());
        assertNull(valueFilter.getStatus());
        assertNull(valueFilter.getCurrency());
        assertTrue(valueFilter.getAssignees().isEmpty());
        assertTrue(valueFilter.getLastValuePeriods().isEmpty());
        assertEquals(new FilterExpression<>(), valueFilter.getWrWrkInstExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getSystemTitleExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getSystemStandardNumberExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getRhAccountNumberExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getRhNameExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getPriceExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getPriceInUsdExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getPriceFlagExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getLastPriceFlagExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getPriceCommentExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getLastPriceCommentExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getContentExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getContentFlagExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getLastContentFlagExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getContentCommentExpression());
        assertEquals(new FilterExpression<>(), valueFilter.getLastContentCommentExpression());
        assertNull(valueFilter.getPubType());
        assertNull(valueFilter.getLastPubType());
        assertNull(valueFilter.getComment());
    }
}
