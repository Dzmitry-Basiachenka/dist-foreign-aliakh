package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.test.integ.db.embedded.UpdateDatabaseForClassTestExecutionListener;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UI test for "Audit" tab.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/02/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
@TestExecutionListeners(value = UpdateDatabaseForClassTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class AuditTabUiTest extends ForeignCommonUiTest {

    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FILTERS_HEADER_TEXT = "Filters";
    private static final String FILTER_COUNTER_TEXT = "(0)";

    private static final String[] USAGE_1 = {"6997788882", "LOCKED", FAS_PRODUCT_FAMILY, "AccessCopyright_11Dec16",
        "09/10/2015", "1000008666", "CCH", "108738286", "2001 tax legislation: law, explanation, and analysis : " +
        "Economic Growth and Tax Relief Reconciliation Act of 2001", "1008902002377656XX", "2,500.00", "32.0",
        "Scenario name"};
    private static final String[] USAGE_2 = {"6997788885", "ELIGIBLE", FAS_PRODUCT_FAMILY, "CADRA_11Dec16",
        "01/11/2017", "1000002859", "John Wiley & Sons - Books", "244614835", "15th International Conference on " +
        "Environmental Degradation of Materials in Nuclear Power Systems Water Reactors", "1008902002377655XX",
        "6,000.00", "0.0", StringUtils.EMPTY};
    private static final String[] USAGE_3 = {"6997788886", "LOCKED", FAS_PRODUCT_FAMILY, "AccessCopyright_11Dec16",
        "09/10/2015", "1000002859", "John Wiley & Sons - Books", "243904752", "100 ROAD MOVIES", "1008902112377654XX",
        "7,500.00", "32.0", "Scenario name"};
    private static final String[] USAGE_4 = {"6997788888", "ELIGIBLE", FAS_PRODUCT_FAMILY, "CADRA_11Dec16",
        "01/11/2017", "1000009997", "IEEE - Inst of Electrical and Electronics Engrs", "180382914",
        "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA",
        "1008902112377654XX", "9,000.00", "0.0", StringUtils.EMPTY};

    @Test
    // Test case IDs: 4d4b48a9-63b3-460c-a353-b7773c9ff77e, 4e3b9e05-abff-442f-a7a3-00317a026988
    //TODO {dbaraukova} adjust test to filter by Event ID and Dist. Name
    public void testVerifyAuditTabFilters() {
        loginAsViewOnly();
        WebElement auditTab = verifyAuditTab();
        verifyFilters(auditTab);
    }

    @Test
    // Test case ID: e077c184-68cf-474c-8e6e-0728daf8fe32
    public void testVerifyUsageHistory() {
        loginAsViewOnly();
        WebElement auditTab = selectAuditTab();
        WebElement filterWidget = assertWebElement(auditTab, "audit-filter-widget");
        applyRightsholdersFilter(filterWidget, "1000008666 - CCH");
        WebElement filterButtonsLayout = assertWebElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, "Apply");
        WebElement auditTable = assertWebElement(auditTab, "audit-table");
        clickElementAndWait(
            assertWebElement(assertWebElement(auditTable, By.className("v-button-link")), By.tagName("span")));
        WebElement historyWindow = assertWebElement(By.id("usage-history-window"));
        WebElement historyTable = assertWebElement(historyWindow, By.className("v-table"));
        assertTableHeaderElements(historyTable, "Type", "Date", "Reason", "User");
        List<WebElement> rows = assertTableRowElements(historyTable, 2);
        assertTableRowElements(rows.get(0), "RH_FOUND", "02/01/2017",
            "Rightsholder account <1000002859> was found in RMS", "SYSTEM");
        assertTableRowElements(rows.get(1), "LOADED", "01/01/2017", "Uploaded in 'AccessCopyright_11Dec16' Batch",
            "SYSTEM");
    }

    @Test
    // Test case ID: a34b4137-7097-4d44-8971-16e30989a554
    public void testSearch() {
        loginAsViewOnly();
        WebElement auditTab = selectAuditTab();
        WebElement searchToolbar = assertWebElement(auditTab, By.className("search-toolbar"));
        WebElement table = assertWebElement(auditTab, By.className("v-table"));
        Map<String, List<String[]>> searchMap = new HashMap<>();
        searchMap.put("    699778888  ", Lists.newArrayList(USAGE_1, USAGE_2, USAGE_3, USAGE_4));
        searchMap.put("6997788882", Collections.singletonList(USAGE_1));
        searchMap.put("   2001   ", Lists.newArrayList(USAGE_1, USAGE_4));
        searchMap.put("100 ROAD MOVIES", Collections.singletonList(USAGE_3));
        searchMap.put("   24   ", Lists.newArrayList(USAGE_2, USAGE_3));
        searchMap.put("180382914", Collections.singletonList(USAGE_4));
        assertSearch(searchToolbar, table, searchMap);
    }

    private WebElement verifyAuditTab() {
        WebElement auditTab = selectAuditTab();
        verifyFiltersWidget(auditTab);
        WebElement auditLayout = assertWebElement(auditTab, "audit-layout");
        assertAuditToolbar(auditLayout);
        verifyAuditTable(auditLayout);
        return auditTab;
    }

    private void verifyFilters(WebElement auditTab) {
        WebElement filterWidget = assertWebElement(auditTab, "audit-filter-widget");
        assertWebElement(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT);
        applyRightsholdersFilter(filterWidget, "1000002859 - John Wiley & Sons - Books");
        applyBatchesFilter(filterWidget, "CADRA_11Dec16");
        applyAuditStatusFilter(filterWidget, "ELIGIBLE");
        WebElement filterButtonsLayout = assertWebElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, "Apply");
        verifyFoundAudit(auditTab);
        clickButtonAndWait(filterButtonsLayout, "Clear");
        assertFilterEmpty(filterWidget, "audit-rightsholders-filter", "audit-batches-filter", "audit-statuses-filter");
    }

    private void assertAuditToolbar(WebElement auditLayout) {
        WebElement auditToolbar = assertWebElement(auditLayout, By.id("audit-toolbar"));
        assertButtonsToolbar(auditToolbar, Collections.singleton("Export"), Collections.singleton("Export"));
        assertSearchToolbar(auditToolbar, "Enter Detail ID or Wr Wrk Inst or Work Title");
    }

    private void verifyFoundAudit(WebElement auditTab) {
        WebElement auditLayout = assertWebElement(auditTab, "audit-layout");
        WebElement auditTable = assertWebElement(auditLayout, "audit-table");
        assertTableRowElements(auditTable, USAGE_2);
    }

    private void verifyAuditTable(WebElement auditLayout) {
        WebElement auditTable = assertWebElement(auditLayout, "audit-table");
        verifyTableHeaderElements(auditTable);
        assertWebElement(auditTable, By.className("v-table-column-selector"));
        assertTableRowElements(auditTable, 0);
    }

    private void verifyTableHeaderElements(WebElement auditTable) {
        assertTableHeaderElements(auditTable,
            "Detail ID",
            "Detail Status",
            "Product Family",
            "Usage Batch Name",
            "Payment Date",
            "RH Account #",
            "RH Name",
            "Wr Wrk Inst",
            "Title",
            "Standard Number",
            "Amt in USD",
            "Service Fee %",
            "Scenario Name",
            "Check #",
            "Check Date",
            "Event ID",
            "Dist. Name");
    }

    private void verifyFiltersWidget(WebElement tabContainer) {
        WebElement filterWidget = assertWebElement(tabContainer, "audit-filter-widget");
        assertWebElement(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT);
        verifyProductFamiliesFilter(filterWidget);
        verifyRightsholdersFilter(filterWidget);
        verifyBatchesFilter(filterWidget);
        verifyStatusFilter(filterWidget);
        verifyFiltersWidgetButtons(filterWidget);
    }

    private void verifyProductFamiliesFilter(WebElement filterWidget) {
        openProductFamiliesFilterWindow(filterWidget);
        clickButtonAndWait(verifyFilterWindow("product-families-filter-window", "Product Families filter"), "Close");
    }

    private void verifyBatchesFilter(WebElement filterWidget) {
        openBatchesFilterWindow(filterWidget);
        verifyFilterWindowWithSearch("batches-filter-window", "Batches filter", "Enter Usage Batch Name");
    }

    private void verifyRightsholdersFilter(WebElement filterWidget) {
        openRightsholderFilterWindow(filterWidget);
        verifyFilterWindowWithSearch("rightsholders-filter-window", "Rightsholders filter",
            "Enter Rightsholder Name/Account #");
    }

    private void verifyStatusFilter(WebElement filterWidget) {
        openStatusFilterWindow(filterWidget);
        clickButtonAndWait(verifyFilterWindow("status-filter-window", "Status filter"), "Close");
    }

    private void openProductFamiliesFilterWindow(WebElement filterWidget) {
        WebElement batchesFilter = assertWebElement(filterWidget, "product-families-filter");
        assertWebElement(batchesFilter, HTML_DIV_TAG_NAME, FILTER_COUNTER_TEXT);
        WebElement batchesFilterButton = assertWebElement(batchesFilter, HTML_SPAN_TAG_NAME, "Product Families");
        clickElementAndWait(batchesFilterButton);
    }

    private void openRightsholderFilterWindow(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertWebElement(filterWidget, "audit-rightsholders-filter");
        assertWebElement(rightsholdersFilter, HTML_DIV_TAG_NAME, FILTER_COUNTER_TEXT);
        clickElementAndWait(assertWebElement(rightsholdersFilter, HTML_SPAN_TAG_NAME, "Rightsholders"));
    }

    private void openBatchesFilterWindow(WebElement filterWidget) {
        WebElement batchesFilter = assertWebElement(filterWidget, "audit-batches-filter");
        assertWebElement(batchesFilter, HTML_DIV_TAG_NAME, FILTER_COUNTER_TEXT);
        WebElement batchesFilterButton = assertWebElement(batchesFilter, HTML_SPAN_TAG_NAME, "Batches");
        clickElementAndWait(batchesFilterButton);
    }

    private void openStatusFilterWindow(WebElement filterWidget) {
        WebElement statusesFilter = assertWebElement(filterWidget, "audit-statuses-filter");
        assertWebElement(statusesFilter, HTML_DIV_TAG_NAME, FILTER_COUNTER_TEXT);
        clickElementAndWait(assertWebElement(statusesFilter, HTML_SPAN_TAG_NAME, "Status"));
    }

    private WebElement verifyFilterWindow(String id, String windowCaption) {
        WebElement filterWindow = assertWebElement(By.id(id));
        assertEquals(windowCaption, getWindowCaption(filterWindow));
        assertWebElement(filterWindow, "Save");
        assertWebElement(filterWindow, "Clear");
        return filterWindow;
    }

    private void verifyFilterWindowWithSearch(String id, String windowCaption, String promptMessage) {
        WebElement filterWindow = verifyFilterWindow(id, windowCaption);
        assertSearchToolbar(filterWindow, promptMessage);
        clickButtonAndWait(filterWindow, "Close");
    }

    private void verifyFiltersWidgetButtons(WebElement filterWidget) {
        WebElement buttonsContainer = assertWebElement(filterWidget, "filter-buttons");
        assertWebElement(buttonsContainer, "Apply");
        assertWebElement(buttonsContainer, "Clear");
    }
}
