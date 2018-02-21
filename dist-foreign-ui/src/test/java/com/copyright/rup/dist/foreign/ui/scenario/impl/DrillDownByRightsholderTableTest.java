package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.IntegerColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.google.common.collect.Lists;
import com.vaadin.server.Sizeable.Unit;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Verifies {@link DrillDownByRightsholderTable}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public class DrillDownByRightsholderTableTest {

    private static final String DETAIL_ID_PROPERTY = "detailId";
    private static final String USAGE_BATCH_NAME_PROPERTY = "batchName";
    private static final String PRODUCT_FAMILY_PROPERTY = "productFamily";
    private static final String FISCAL_YEAR_PROPERTY = "fiscalYear";
    private static final String RRO_NAME_PROPERTY = "rroName";
    private static final String RRO_ACCOUNT_NUMBER_PROPERTY = "rroAccountNumber";
    private static final String PAYMENT_DATE_PROPERTY = "paymentDate";
    private static final String TITLE_PROPERTY = "workTitle";
    private static final String ARTICLE_PROPERTY = "article";
    private static final String STANDARD_NUMBER_PROPERTY = "standardNumber";
    private static final String WR_WRK_INST_PROPERTY = "wrWrkInst";
    private static final String PUBLISHER_PROPERTY = "publisher";
    private static final String PUBLICATION_DATE_PROPERTY = "publicationDate";
    private static final String NUMBER_OF_COPIES_PROPERTY = "numberOfCopies";
    private static final String REPORTED_VALUE_PROPERTY = "reportedValue";
    private static final String AMT_IN_USD_PROPERTY = "grossAmount";
    private static final String SERVICE_FEE_AMOUNT_PROPERTY = "serviceFeeAmount";
    private static final String NET_AMOUNT_PROPERTY = "netAmount";
    private static final String SERVICE_FEE_PROPERTY = "serviceFee";
    private static final String MARKET_PROPERTY = "market";
    private static final String MARKET_PERIOD_FROM_PROPERTY = "marketPeriodFrom";
    private static final String MARKET_PERIOD_TO_PROPERTY = "marketPeriodTo";
    private static final String AUTHOR_PROPERTY = "author";

    private DrillDownByRightsholderTable table;

    @Before
    public void setUp() {
        table = new DrillDownByRightsholderTable(createMock(IDrillDownByRightsholderController.class),
            UsageDetailsBeanQuery.class);
    }

    @Test
    public void testGetIdProperty() {
        assertEquals(DETAIL_ID_PROPERTY, table.getIdProperty());
    }

    @Test
    public void testTableStructure() {
        verifySize();
        assertTrue(table.getStyleName().contains("drill-down-by-rightsholder-table"));
        assertTrue(table.isColumnCollapsingAllowed());
        assertFalse(table.isColumnCollapsible(DETAIL_ID_PROPERTY));
        verifyVisibleColumns();
        verifyColumnHeaders();
        verifyProperties();
        verifyGeneratedColumns();
    }

    private void verifyProperties() {
        Collection<?> containerPropertyIds = table.getContainerPropertyIds();
        List<String> expectedContainerPropertyIds = Lists.newArrayList(DETAIL_ID_PROPERTY, USAGE_BATCH_NAME_PROPERTY,
            PRODUCT_FAMILY_PROPERTY, FISCAL_YEAR_PROPERTY, RRO_NAME_PROPERTY, RRO_ACCOUNT_NUMBER_PROPERTY,
            PAYMENT_DATE_PROPERTY, TITLE_PROPERTY, ARTICLE_PROPERTY, STANDARD_NUMBER_PROPERTY, WR_WRK_INST_PROPERTY,
            PUBLISHER_PROPERTY, PUBLICATION_DATE_PROPERTY, NUMBER_OF_COPIES_PROPERTY, REPORTED_VALUE_PROPERTY,
            AMT_IN_USD_PROPERTY, SERVICE_FEE_AMOUNT_PROPERTY, NET_AMOUNT_PROPERTY, SERVICE_FEE_PROPERTY,
            MARKET_PROPERTY, MARKET_PERIOD_FROM_PROPERTY, MARKET_PERIOD_TO_PROPERTY, AUTHOR_PROPERTY);
        assertEquals(CollectionUtils.size(expectedContainerPropertyIds), CollectionUtils.size(containerPropertyIds));
        assertTrue(CollectionUtils.containsAll(expectedContainerPropertyIds, containerPropertyIds));
    }

    private void verifyVisibleColumns() {
        assertArrayEquals(new Object[]{
                DETAIL_ID_PROPERTY,
                USAGE_BATCH_NAME_PROPERTY,
                PRODUCT_FAMILY_PROPERTY,
                FISCAL_YEAR_PROPERTY,
                RRO_ACCOUNT_NUMBER_PROPERTY,
                RRO_NAME_PROPERTY,
                PAYMENT_DATE_PROPERTY,
                TITLE_PROPERTY,
                ARTICLE_PROPERTY,
                STANDARD_NUMBER_PROPERTY,
                WR_WRK_INST_PROPERTY,
                PUBLISHER_PROPERTY,
                PUBLICATION_DATE_PROPERTY,
                NUMBER_OF_COPIES_PROPERTY,
                REPORTED_VALUE_PROPERTY,
                AMT_IN_USD_PROPERTY,
                SERVICE_FEE_AMOUNT_PROPERTY,
                NET_AMOUNT_PROPERTY,
                SERVICE_FEE_PROPERTY,
                MARKET_PROPERTY,
                MARKET_PERIOD_FROM_PROPERTY,
                MARKET_PERIOD_TO_PROPERTY,
                AUTHOR_PROPERTY},
            table.getVisibleColumns());
    }

    private void verifyColumnHeaders() {
        assertArrayEquals(new Object[]{
                "Detail ID",
                "Usage Batch Name",
                "Product Family",
                "Fiscal Year",
                "RRO Account #",
                "RRO Name",
                "Payment Date",
                "Title",
                "Article",
                "Standard Number",
                "Wr Wrk Inst",
                "Publisher",
                "Pub Date",
                "Number of Copies",
                "Reported value",
                "Gross Amt in USD",
                "Service Fee Amount",
                "Net Amt in USD",
                "Service Fee %",
                "Market",
                "Market Period From",
                "Market Period To",
                "Author"},
            table.getColumnHeaders());
    }

    private void verifyGeneratedColumns() {
        verifyColumnGenerator(table.getColumnGenerator(FISCAL_YEAR_PROPERTY), FiscalYearColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(NUMBER_OF_COPIES_PROPERTY), IntegerColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(MARKET_PERIOD_FROM_PROPERTY), IntegerColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(MARKET_PERIOD_TO_PROPERTY), IntegerColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(RRO_ACCOUNT_NUMBER_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(DETAIL_ID_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(WR_WRK_INST_PROPERTY), LongColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(PUBLICATION_DATE_PROPERTY), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(PAYMENT_DATE_PROPERTY), LocalDateColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(REPORTED_VALUE_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(AMT_IN_USD_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(SERVICE_FEE_AMOUNT_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(NET_AMOUNT_PROPERTY), MoneyColumnGenerator.class);
        verifyColumnGenerator(table.getColumnGenerator(SERVICE_FEE_PROPERTY), PercentColumnGenerator.class);
    }

    private void verifyColumnGenerator(LazyTable.ColumnGenerator columnGenerator, Class clazz) {
        assertNotNull(columnGenerator);
        assertEquals(clazz, columnGenerator.getClass());
    }

    private void verifySize() {
        assertEquals(100, table.getWidth(), 0);
        assertEquals(100, table.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, table.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, table.getWidthUnits());
    }
}
