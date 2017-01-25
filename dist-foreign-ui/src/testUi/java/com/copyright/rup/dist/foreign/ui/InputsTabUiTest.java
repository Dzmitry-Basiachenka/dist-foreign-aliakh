package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * UI test for "Inputs" tab.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/24/2017
 *
 * @author Mikita Hladkikh
 */
public class InputsTabUiTest extends ForeignCommonUiTest {

    @Test
    public void testLookAndFeel() {
        loginAsViewOnly();
        WebElement tabContainer = findElementById(Cornerstone.MAIN_TABSHEET);
        tabContainer = waitAndGetTab(tabContainer, "Inputs");
        verifyFiltersWidget(tabContainer);
        verifyUsagesLayout(tabContainer);
    }

    private void verifyUsagesLayout(WebElement tabContainer) {
        WebElement usagesLayout = assertElement(tabContainer, "usages-layout");
        verifyUsagesLayoutButtons(usagesLayout);
        verifyUsagesTable(usagesLayout);
    }

    private void verifyUsagesLayoutButtons(WebElement usagesLayout) {
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        assertElement(buttonsLayout, "Load");
        assertElement(buttonsLayout, "Add_To_Scenario");
    }

    private void verifyUsagesTable(WebElement usagesLayout) {
        WebElement usagesTable = assertElement(usagesLayout, "usages-table");
        verifyTableHeaderElements(usagesTable);
        verifyTableBody(usagesTable);
    }

    private void verifyTableHeaderElements(WebElement usagesTable) {
        WebElement tableHeader = findElement(usagesTable, By.className(V_TABLE_HEADER_CLASS_NAME));
        List<WebElement> tableHeaderElements =
            findElements(tableHeader, By.className(V_TABLE_CAPTION_CONTAINER_CLASS_NAME));
        assertEquals(8, tableHeaderElements.size());
        assertEquals("Wr Wrk Inst", getInnerHtml(tableHeaderElements.get(0)));
        assertEquals("Work Title", getInnerHtml(tableHeaderElements.get(1)));
        assertEquals("RH Acct #", getInnerHtml(tableHeaderElements.get(2)));
        assertEquals("RH Name", getInnerHtml(tableHeaderElements.get(3)));
        assertEquals("Payment Date", getInnerHtml(tableHeaderElements.get(4)));
        assertEquals("Gross Amount", getInnerHtml(tableHeaderElements.get(5)));
        assertEquals("RRO", getInnerHtml(tableHeaderElements.get(6)));
        assertEquals("Eligible", getInnerHtml(tableHeaderElements.get(7)));
    }

    private void verifyTableBody(WebElement usagesTable) {
        WebElement table = findElement(usagesTable, By.className(V_TABLE_BODY_CLASS_NAME));
        List<WebElement> tableRows = findElements(table, By.tagName(HTML_TR_TAG_NAME));
        assertEquals(9, tableRows.size());
    }

    private void verifyFiltersWidget(WebElement tabContainer) {
        WebElement filterWidget = waitAndFindElement(tabContainer, By.id("usages-filter-widget"));
        assertNotNull(filterWidget);
        assertNotNull(findElementByText(filterWidget, HTML_DIV_TAG_NAME, "Filters"));
        verifyBatchesFilter(filterWidget);
        verifyRightsholdersFilter(filterWidget);
        verifyStatusFilter(filterWidget);
        verifyPaymentDateFilter(filterWidget);
        verifyTaxCountryFilter(filterWidget);
        verifyFiltersWidgetButtons(filterWidget);
    }

    private void verifyBatchesFilter(WebElement filterWidget) {
        WebElement batchesFilter = assertElement(filterWidget, "batches-filter");
        assertNotNull(findElementByText(batchesFilter, HTML_DIV_TAG_NAME, "(0)"));
        assertNotNull(findElementByText(batchesFilter, HTML_SPAN_TAG_NAME, "Batches"));
    }

    private void verifyRightsholdersFilter(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertElement(filterWidget, "rightsholders-filter");
        assertNotNull(findElementByText(rightsholdersFilter, HTML_DIV_TAG_NAME, "(0)"));
        assertNotNull(findElementByText(rightsholdersFilter, HTML_SPAN_TAG_NAME, "RROs"));
    }

    private void verifyStatusFilter(WebElement filterWidget) {
        WebElement statusFilter = assertElement(filterWidget, "status-filter");
        assertNotNull(findElementByText(statusFilter, HTML_SPAN_TAG_NAME, "Status"));
        assertNotNull(findElement(statusFilter, By.tagName(HTML_INPUT_TAG_NAME)));
        assertNotNull(findElement(statusFilter, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME)));
    }

    private void verifyPaymentDateFilter(WebElement filterWidget) {
        WebElement paymentDateFilter = assertElement(filterWidget, "payment-date-filter");
        assertNotNull(findElementByText(paymentDateFilter, HTML_SPAN_TAG_NAME, "Payment Date"));
        assertNotNull(findElement(paymentDateFilter, By.className("v-datefield")));
        assertNotNull(findElement(paymentDateFilter, By.className(V_DATE_FIELD_BUTTON_CLASS_NAME)));
    }

    private void verifyTaxCountryFilter(WebElement filterWidget) {
        WebElement taxCountryFilter = assertElement(filterWidget, "tax-country-filter");
        assertNotNull(findElementByText(taxCountryFilter, HTML_SPAN_TAG_NAME, "Tax Country"));
        assertNotNull(findElement(taxCountryFilter, By.tagName(HTML_INPUT_TAG_NAME)));
        assertNotNull(findElement(taxCountryFilter, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME)));
    }

    private void verifyFiltersWidgetButtons(WebElement filterWidget) {
        WebElement buttonsContainer = assertElement(filterWidget, "filter-buttons");
        assertElement(buttonsContainer, "Apply");
        assertElement(buttonsContainer, "Clear");
    }

    private WebElement assertElement(WebElement parentElement, String id) {
        WebElement webElement = findElement(parentElement, By.id(id));
        assertNotNull(webElement);
        return webElement;
    }
}
