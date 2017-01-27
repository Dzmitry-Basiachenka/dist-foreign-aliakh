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
        WebElement loadButton = assertElement(buttonsLayout, "Load");
        clickElementAndWait(loadButton);
        assertElement(buttonsLayout, "Add_To_Scenario");
        verifyUploadUsageWindow();
    }

    private void verifyUploadUsageWindow() {
        WebElement uploadWindow = waitAndFindElement(By.id("usage-upload-window"));
        assertEquals("Upload Usage Batch", getWindowCaption(uploadWindow));
        verifyUploadElement(uploadWindow);
        verifyRightsholdersFields(uploadWindow);
        verifyDateFields(uploadWindow);
        assertTextElement(uploadWindow, "gross-amount-field", "Gross Amount (USD)");
        assertComboboxElement(uploadWindow, "reported-currency-field", HTML_DIV_TAG_NAME, "Reported Currency");
        assertElement(uploadWindow, "Upload");
        assertElement(uploadWindow, "Close");
        clickButtonAndWait(uploadWindow, "Close");
    }

    private void verifyUploadElement(WebElement uploadWindow) {
        WebElement uploadField = assertElement(uploadWindow, "usage-upload-component");
        assertNotNull(findElementByText(uploadField, HTML_SPAN_TAG_NAME, "File to Upload"));
        assertNotNull(findElementByText(uploadField, HTML_SPAN_TAG_NAME, "*"));
        assertNotNull(findElement(uploadField, By.tagName(HTML_INPUT_TAG_NAME)));
        WebElement uploadForm = findElement(uploadField, By.tagName("form"));
        assertNotNull(uploadForm);
        assertNotNull(findElementByText(uploadForm, HTML_SPAN_TAG_NAME, "Browse"));
    }

    private void verifyRightsholdersFields(WebElement uploadWindow) {
        assertTextElement(uploadWindow, "rro-account-number-field", "RRO Account #");
        assertTextElement(uploadWindow, "rro-account-name-field", "RRO Account Name");
        assertElement(uploadWindow, "Verify");
    }

    private void verifyDateFields(WebElement uploadWindow) {
        verifyPaymentDateComponent(uploadWindow, "payment-date-field");
        assertTextElement(uploadWindow, "fiscal-year-field", "Fiscal Year");
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
        verifyPaymentDateComponent(filterWidget, "payment-date-filter");
        assertComboboxElement(filterWidget, "tax-country-filter", HTML_SPAN_TAG_NAME, "Tax Country");
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

    private void verifyPaymentDateComponent(WebElement filterWidget, String id) {
        WebElement paymentDateFilter = assertElement(filterWidget, id);
        assertNotNull(findElementByText(paymentDateFilter, HTML_SPAN_TAG_NAME, "Payment Date"));
        assertNotNull(findElement(paymentDateFilter, By.className("v-datefield")));
        assertNotNull(findElement(paymentDateFilter, By.className(V_DATE_FIELD_BUTTON_CLASS_NAME)));
    }

    private void verifyFiltersWidgetButtons(WebElement filterWidget) {
        WebElement buttonsContainer = assertElement(filterWidget, "filter-buttons");
        assertElement(buttonsContainer, "Apply");
        assertElement(buttonsContainer, "Clear");
    }

    private WebElement assertTextElement(WebElement parentElement, String id, String caption) {
        WebElement webElement = assertElement(parentElement, id);
        WebElement elementContainer = getParentElement(webElement);
        assertNotNull(findElementByText(elementContainer, HTML_DIV_TAG_NAME, caption));
        return webElement;
    }

    private WebElement assertComboboxElement(WebElement parentElement, String id, String captionTagName,
                                             String caption) {
        WebElement webElement = assertElement(parentElement, id);
        assertNotNull(findElementByText(webElement, captionTagName, caption));
        assertNotNull(findElement(webElement, By.tagName(HTML_INPUT_TAG_NAME)));
        assertNotNull(findElement(webElement, By.className(V_FILTER_SELECT_BUTTON_CLASS_NAME)));
        return webElement;
    }

    private WebElement assertElement(WebElement parentElement, String id) {
        WebElement webElement = findElement(parentElement, By.id(id));
        assertNotNull(webElement);
        return webElement;
    }
}
