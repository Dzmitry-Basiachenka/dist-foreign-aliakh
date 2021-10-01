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
    private static final String LAST_PRICE_FLAG = "Y";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal CONTENT = new BigDecimal("70");
    private static final String LAST_CONTENT_FLAG = "N";
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
        UdmValueFilter udmValueFilter = new UdmValueFilter();
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setPeriods(Collections.singleton(PERIOD));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPeriods(new HashSet<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setPeriods(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setStatus(STATUS);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setStatus(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setCurrency(CURRENCY);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setCurrency(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setAssignees(new HashSet<>(Collections.singleton(ASSIGNEE)));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setAssignees(new HashSet<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setAssignees(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastValuePeriods(Collections.singleton(LAST_VALUE_PERIOD));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastValuePeriods(new HashSet<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastValuePeriods(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setWrWrkInst(WR_WRK_INST);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setWrWrkInst(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setRhAccountNumber(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastPriceFlag(LAST_PRICE_FLAG);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastPriceFlag(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastPriceComment(LAST_PRICE_COMMENT);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastPriceComment(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastContentFlag(LAST_CONTENT_FLAG);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastContentFlag(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastContentComment(LAST_CONTENT_COMMENT);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastContentComment(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setPubType(PUB_TYPE);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPubType(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setLastPubType(LAST_PUB_TYPE);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setLastPubType(null);
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setComment(COMMENT);
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setComment(null);
        assertTrue(udmValueFilter.isEmpty());
    }

    @Test
    public void testIsEmptyExpressions() {
        UdmValueFilter udmValueFilter = new UdmValueFilter();
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setSystemTitleExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setSystemTitleExpression(new FilterExpression<>(
            FilterOperatorEnum.CONTAINS, SYSTEM_TITLE, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setSystemTitleExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setSystemStandardNumberExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, SYSTEM_STANDARD_NUMBER, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setSystemStandardNumberExpression(new FilterExpression<>(
            FilterOperatorEnum.CONTAINS, SYSTEM_STANDARD_NUMBER, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setSystemStandardNumberExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setRhNameExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, RH_NAME, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setRhNameExpression(new FilterExpression<>(
            FilterOperatorEnum.CONTAINS, RH_NAME, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setRhNameExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, PRICE, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceExpression(new FilterExpression<>(
            FilterOperatorEnum.IS_NULL, null, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setPriceInUsdExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceInUsdExpression(new FilterExpression<>(
            FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, PRICE_IN_USD, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceInUsdExpression(new FilterExpression<>(
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, PRICE_IN_USD, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceInUsdExpression(new FilterExpression<>(
            FilterOperatorEnum.IS_NULL, null, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setPriceInUsdExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
        udmValueFilter.setContentExpression(new FilterExpression<>(
            FilterOperatorEnum.EQUALS, CONTENT, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setContentExpression(new FilterExpression<>(
            FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, CONTENT, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setContentExpression(new FilterExpression<>(
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, CONTENT, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setContentExpression(new FilterExpression<>(
            FilterOperatorEnum.IS_NULL, null, null));
        assertFalse(udmValueFilter.isEmpty());
        udmValueFilter.setContentExpression(new FilterExpression<>());
        assertTrue(udmValueFilter.isEmpty());
    }

    @Test
    public void testConstructor() {
        UdmValueFilter udmValueFilter = new UdmValueFilter();
        assertTrue(udmValueFilter.getPeriods().isEmpty());
        assertNull(udmValueFilter.getStatus());
        assertNull(udmValueFilter.getCurrency());
        assertTrue(udmValueFilter.getAssignees().isEmpty());
        assertTrue(udmValueFilter.getLastValuePeriods().isEmpty());
        assertNull(udmValueFilter.getWrWrkInst());
        assertEquals(new FilterExpression<>(), udmValueFilter.getSystemTitleExpression());
        assertEquals(new FilterExpression<>(), udmValueFilter.getSystemStandardNumberExpression());
        assertNull(udmValueFilter.getRhAccountNumber());
        assertEquals(new FilterExpression<>(), udmValueFilter.getRhNameExpression());
        assertEquals(new FilterExpression<>(), udmValueFilter.getPriceExpression());
        assertEquals(new FilterExpression<>(), udmValueFilter.getPriceInUsdExpression());
        assertNull(udmValueFilter.getLastPriceFlag());
        assertNull(udmValueFilter.getLastPriceComment());
        assertEquals(new FilterExpression<>(), udmValueFilter.getContentExpression());
        assertNull(udmValueFilter.getLastContentFlag());
        assertNull(udmValueFilter.getLastContentComment());
        assertNull(udmValueFilter.getPubType());
        assertNull(udmValueFilter.getLastPubType());
        assertNull(udmValueFilter.getComment());
    }
}
