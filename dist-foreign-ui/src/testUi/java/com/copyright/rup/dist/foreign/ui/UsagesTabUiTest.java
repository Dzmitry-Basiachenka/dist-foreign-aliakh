package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.google.common.collect.Sets;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;

/**
 * UI test for "Usages" tab.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/24/2017
 *
 * @author Mikita Hladkikh
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class UsagesTabUiTest extends ForeignCommonUiTest {

    private static final String SAVE_BUTTON_ID = "Save";
    private static final String APPLY_BUTTON_ID = "Apply";
    private static final String USAGE_BATCH_NAME = "CADRA_11Dec16";
    private static final String RRO_ACCOUNT =
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil";
    private static final String FISCAL_YEAR = "2017";
    private static final String CLEAR_BATTON_ID = "Clear";
    private static final String USAGE_LAYOUT_ID = "usages-layout";
    private static final String USAGE_TABLE_ID = "usages-table";
    private static final String USAGE_FILTER_WIDGET_ID = "usages-filter-widget";
    private static final String FILTERS_HEADER_TEXT = "Filters";
    private static final String FILTER_BUTTONS_LAYOUT_ID = "filter-buttons";
    private static final String ADD_TO_SCENARIO_BUTTON_ID = "Add_To_Scenario";
    private static final String EXPORT_BUTTON_ID = "Export";
    private static final String LOAD_USAGE_BUTTON_ID = "Load";
    private static final String DELETE_USAGE_BUTTON_ID = "Delete_Usage_Batch";
    private static final String CLOSE_BUTTON = "Close";
    private static final Set USAGES_BUTTONS =
        Sets.newHashSet(LOAD_USAGE_BUTTON_ID, ADD_TO_SCENARIO_BUTTON_ID, EXPORT_BUTTON_ID, DELETE_USAGE_BUTTON_ID);

    @Test
    // Test case ID: '65520aa2-3a1c-4c7c-81e6-96a0a845331e'
    public void testVerifyUsagesTabSpecialist() {
        loginAsSpecialist();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonSpecialist(usagesLayout);
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyUsagesTabManager() {
        loginAsManager();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonManager(usagesLayout);
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyUsagesTabViewOnly() {
        loginAsViewOnly();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonViewOnly(usagesLayout);
    }

    @Test
    // Test cases IDs: 'a2a66f64-ab10-44d4-a2b9-c779631cbe6a', '1b24b641-8da0-46a2-917c-7ee99d781d6d'
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlySpecialist() {
        loginAsSpecialist();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlyManager() {
        loginAsManager();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlyViewOnly() {
        loginAsViewOnly();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // Test cases IDs: 'd438bf76-31f9-4266-8a4e-8c416a616aed', '87ac1966-737c-4b18-b5d1-6e583776b3a1'
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonSpecialist() {
        loginAsSpecialist();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonManager() {
        loginAsManager();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    @Test
    // TODO {isuvorau} set test cases IDs
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonViewOnly() {
        loginAsViewOnly();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    private void verifyBatchAndUsageInformationIsDisplayedCorrectly() {
        WebElement usagesTab = getUsagesTab();
        WebElement filterWidget = waitAndFindElement(usagesTab, By.id(USAGE_FILTER_WIDGET_ID));
        assertNotNull(filterWidget);
        assertNotNull(findElementByText(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT));
        openBatchesFilterWindow(filterWidget);
        applyBatchFilter(USAGE_BATCH_NAME);
        clickButtonAndWait(assertElement(filterWidget, FILTER_BUTTONS_LAYOUT_ID), APPLY_BUTTON_ID);
        verifyFoundUsages(usagesTab);
    }

    private void verifyMultiplyUsageDataFilterAndClearFilterButton() {
        WebElement usagesTab = getUsagesTab();
        WebElement usagesLayout = assertElement(usagesTab, USAGE_LAYOUT_ID);
        assertNotNull(usagesLayout);
        WebElement usagesTable = assertElement(usagesLayout, USAGE_TABLE_ID);
        assertNotNull(usagesTable);
        WebElement filterWidget = waitAndFindElement(usagesTab, By.id(USAGE_FILTER_WIDGET_ID));
        assertNotNull(filterWidget);
        assertNotNull(findElementByText(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT));
        openBatchesFilterWindow(filterWidget);
        applyBatchFilter(USAGE_BATCH_NAME);
        openRroFilterWindow(filterWidget);
        applyRroFilter(RRO_ACCOUNT);
        applyPaymentDateFilter();
        applyFiscalYear(FISCAL_YEAR);
        WebElement filterButtonsLayout = assertElement(filterWidget, FILTER_BUTTONS_LAYOUT_ID);
        clickButtonAndWait(filterButtonsLayout, APPLY_BUTTON_ID);
        verifyFoundUsages(usagesTab);
        clickButtonAndWait(filterButtonsLayout, CLEAR_BATTON_ID);
        verifyEmptyTable(usagesTable);
    }

    private void applyFiscalYear(String fiscalYear) {
        WebElement fiscalYearFilter = waitAndFindElement(By.id("fiscal-year-filter"));
        assertNotNull(fiscalYearFilter);
        WebElement fiscalYearFilterSelectButton = waitAndFindElement(By.className("v-filterselect-button"));
        clickElementAndWait(fiscalYearFilterSelectButton);
        WebElement fiscalYearLabel = findElementByText(fiscalYearFilter, HTML_SPAN_TAG_NAME, fiscalYear);
        assertNotNull(fiscalYearLabel);
        clickElementAndWait(fiscalYearLabel);
    }

    private void applyPaymentDateFilter() {
        WebElement paymentDateFilter = waitAndFindElement(By.id("payment-date-filter"));
        findElement(paymentDateFilter, By.className("v-datefield-button")).click();
        WebElement calendarPanel = waitAndFindElement(By.className("v-datefield-calendarpanel"));
        WebElement currentDateSlot = findElement(calendarPanel, By.className("v-datefield-calendarpanel-day-today"));
        clickElementAndWait(currentDateSlot);
    }

    private void applyRroFilter(String rroAccount) {
        WebElement filterWindow = waitAndFindElement(By.id("rightsholders-filter-window"));
        assertNotNull(filterWindow);
        WebElement rroAccountLabel = findElementByText(filterWindow, HTML_LABEL_TAG_NAME, rroAccount);
        assertNotNull(rroAccountLabel);
        clickElementAndWait(rroAccountLabel);
        clickButtonAndWait(filterWindow, SAVE_BUTTON_ID);
    }

    private void applyBatchFilter(String batchName) {
        WebElement filterWindow = findElementById("batches-filter-window");
        assertNotNull(filterWindow);
        WebElement batchLabel = findElementByText(filterWindow, HTML_LABEL_TAG_NAME, batchName);
        assertNotNull(batchLabel);
        clickElementAndWait(batchLabel);
        clickButtonAndWait(filterWindow, SAVE_BUTTON_ID);
    }

    private void verifyFoundUsages(WebElement usagesTab) {
        WebElement usagesLayout = assertElement(usagesTab, USAGE_LAYOUT_ID);
        WebElement usagesTable = assertElement(usagesLayout, USAGE_TABLE_ID);
        WebElement table = findElement(usagesTable, By.className(V_TABLE_BODY_CLASS_NAME));
        List<WebElement> usageTableRows = findElements(table, By.tagName(HTML_TR_TAG_NAME));
        assertEquals(1, usageTableRows.size());
        WebElement usageTableRow = usageTableRows.get(0);
        List<WebElement> usageValues = findElements(usageTableRow, By.tagName(HTML_TD_TAG_NAME));
        assertEquals(22, usageValues.size());
        verifyUsageValues(usageValues);
    }

    private void verifyUsageValues(List<WebElement> usageValues) {
        assertTrue(getInnerHtml(usageValues.get(0)).contains("6997788888"));
        assertTrue(getInnerHtml(usageValues.get(1)).contains("ELIGIBLE"));
        assertTrue(getInnerHtml(usageValues.get(2)).contains("CADRA_11Dec16"));
        assertTrue(getInnerHtml(usageValues.get(3)).contains("FY2017"));
        assertTrue(getInnerHtml(usageValues.get(4)).contains("7000813806"));
        assertTrue(getInnerHtml(usageValues.get(5)).contains(
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil"));
        assertTrue(getInnerHtml(usageValues.get(6)).contains("01/11/2017"));
        assertTrue(getInnerHtml(usageValues.get(7)).contains(
            "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA"));
        assertTrue(getInnerHtml(usageValues.get(8)).contains(
            "Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle"));
        assertTrue(getInnerHtml(usageValues.get(9)).contains("1008902112377654XX"));
        assertTrue(getInnerHtml(usageValues.get(10)).contains("180382914"));
        assertTrue(getInnerHtml(usageValues.get(11)).contains("1000009997"));
        assertTrue(getInnerHtml(usageValues.get(12)).contains("IEEE - Inst of Electrical and Electronics Engrs"));
        assertTrue(getInnerHtml(usageValues.get(13)).contains("IEEE"));
        assertTrue(getInnerHtml(usageValues.get(14)).contains("09/10/2013"));
        assertTrue(getInnerHtml(usageValues.get(15)).contains("2502232"));
        assertTrue(getInnerHtml(usageValues.get(16)).contains("2,500.00"));
        assertTrue(getInnerHtml(usageValues.get(17)).contains("13,461.54"));
        assertTrue(getInnerHtml(usageValues.get(18)).contains("Doc Del"));
        assertTrue(getInnerHtml(usageValues.get(19)).contains("2013"));
        assertTrue(getInnerHtml(usageValues.get(20)).contains("2017"));
        assertTrue(getInnerHtml(usageValues.get(21)).contains("Íñigo López de Mendoza, marqués de Santillana"));
    }

    private WebElement getUsagesTab() {
        WebElement tabContainer = findElementById(Cornerstone.MAIN_TABSHEET);
        return waitAndGetTab(tabContainer, "Usages");
    }

    private WebElement verifyUsagesTab() {
        WebElement usagesTab = getUsagesTab();
        verifyFiltersWidget(usagesTab);
        WebElement usagesLayout = assertElement(usagesTab, USAGE_LAYOUT_ID);
        verifyUsagesTable(usagesLayout);
        return usagesLayout;
    }

    private void verifyUsagesLayoutButtonSpecialist(WebElement usagesLayout) {
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        verifyUsagesLayoutButton(buttonsLayout,
            Sets.newHashSet(ADD_TO_SCENARIO_BUTTON_ID, EXPORT_BUTTON_ID, LOAD_USAGE_BUTTON_ID, DELETE_USAGE_BUTTON_ID));
        verifyUploadUsageWindow(buttonsLayout);
        verifyLoadUsageButton(buttonsLayout);
        verifyDeleteUsageButton(buttonsLayout);
        verifyAddToScenarioButton(buttonsLayout);
        verifyExportButton(buttonsLayout);
    }

    private void verifyUsagesLayoutButtonManager(WebElement usagesLayout) {
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        verifyUsagesLayoutButton(buttonsLayout, Sets.newHashSet(EXPORT_BUTTON_ID));
        verifyExportButton(buttonsLayout);
    }

    private void verifyUsagesLayoutButtonViewOnly(WebElement usagesLayout) {
        WebElement buttonsLayout = assertElement(usagesLayout, "usages-buttons");
        verifyUsagesLayoutButton(buttonsLayout, Sets.newHashSet(EXPORT_BUTTON_ID));
        verifyExportButton(buttonsLayout);
    }

    private void verifyUsagesLayoutButton(WebElement buttonsLayout, Set<String> buttonsIds) {
        assertTrue(USAGES_BUTTONS.containsAll(buttonsIds));
        Set<String> invisibleButtons = Sets.newHashSet(CollectionUtils.subtract(USAGES_BUTTONS, buttonsIds));
        for (String id : invisibleButtons) {
            assertNull(findElement(buttonsLayout, By.id(id)));
        }
    }

    private void verifyLoadUsageButton(WebElement buttonsLayout) {
        WebElement button = assertElement(buttonsLayout, LOAD_USAGE_BUTTON_ID);
        clickElementAndWait(button);
        clickElementAndWait(assertElement(waitAndFindElement(By.id("usage-upload-window")), CLOSE_BUTTON));
    }

    private void verifyDeleteUsageButton(WebElement buttonsLayout) {
        WebElement button = assertElement(buttonsLayout, DELETE_USAGE_BUTTON_ID);
        clickElementAndWait(button);
        clickElementAndWait(assertElement(waitAndFindElement(By.id("delete-usage-batch")), CLOSE_BUTTON));
    }

    private void verifyAddToScenarioButton(WebElement buttonsLayout) {
        WebElement button = assertElement(buttonsLayout, ADD_TO_SCENARIO_BUTTON_ID);
        clickElementAndWait(button);
        clickElementAndWait(
            findElementByText(waitAndFindElement(By.id("notification-window")), HTML_SPAN_TAG_NAME, "Ok"));
    }

    private void verifyExportButton(WebElement buttonsLayout) {
        WebElement button = assertElement(buttonsLayout, EXPORT_BUTTON_ID);
        assertTrue(button.isEnabled());
        //TODO {isuvorau} find solution to close browser window
    }

    private void verifyUploadUsageWindow(WebElement buttonsLayout) {
        clickElementAndWait(findElement(buttonsLayout, By.id(LOAD_USAGE_BUTTON_ID)));
        WebElement uploadWindow = waitAndFindElement(By.id("usage-upload-window"));
        assertEquals("Upload Usage Batch", getWindowCaption(uploadWindow));
        verifyUploadElement(uploadWindow);
        verifyRightsholdersFields(uploadWindow);
        verifyDateFields(uploadWindow);
        assertTextElement(uploadWindow, "usage-batch-name-field", "Usage Batch Name");
        assertTextElement(uploadWindow, "gross-amount-field", "Gross Amount (USD)");
        assertElement(uploadWindow, "Upload");
        clickElementAndWait(assertElement(uploadWindow, CLOSE_BUTTON));
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
        assertTextElement(uploadWindow, "rro-account-name-field", "RRO Account Name");
        assertTextElement(uploadWindow, "rro-account-number-field", "RRO Account #");
        assertElement(uploadWindow, "Verify");
    }

    private void verifyDateFields(WebElement uploadWindow) {
        verifyPaymentDateComponent(uploadWindow, "payment-date-field");
        assertTextElement(uploadWindow, "fiscal-year-field", "Fiscal Year");
    }

    private void verifyUsagesTable(WebElement usagesLayout) {
        WebElement usagesTable = assertElement(usagesLayout, USAGE_TABLE_ID);
        verifyTableHeaderElements(usagesTable);
        verifyTableBody(usagesTable);
        assertNotNull(findElement(usagesTable, By.className("v-table-column-selector")));
        verifyEmptyTable(usagesTable);
    }

    private void verifyEmptyTable(WebElement usagesTable) {
        String backgroundImage = findElement(usagesTable, By.className("v-scrollable")).getCssValue("background-image");
        assertTrue(backgroundImage.contains("img/empty_usages_table.png"));
    }

    private void verifyTableHeaderElements(WebElement usagesTable) {
        WebElement tableHeader = findElement(usagesTable, By.className(V_TABLE_HEADER_CLASS_NAME));
        List<WebElement> tableHeaderElements =
            findElements(tableHeader, By.className(V_TABLE_CAPTION_CONTAINER_CLASS_NAME));
        assertEquals(22, tableHeaderElements.size());
        assertEquals("Detail ID", getInnerHtml(tableHeaderElements.get(0)));
        assertEquals("Detail Status", getInnerHtml(tableHeaderElements.get(1)));
        assertEquals("Usage Batch Name", getInnerHtml(tableHeaderElements.get(2)));
        assertEquals("Fiscal Year", getInnerHtml(tableHeaderElements.get(3)));
        assertEquals("RRO Account #", getInnerHtml(tableHeaderElements.get(4)));
        assertEquals("RRO Name", getInnerHtml(tableHeaderElements.get(5)));
        assertEquals("Payment Date", getInnerHtml(tableHeaderElements.get(6)));
        assertEquals("Title", getInnerHtml(tableHeaderElements.get(7)));
        assertEquals("Article", getInnerHtml(tableHeaderElements.get(8)));
        assertEquals("Standard Number", getInnerHtml(tableHeaderElements.get(9)));
        assertEquals("Wr Wrk Inst", getInnerHtml(tableHeaderElements.get(10)));
        assertEquals("RH Account #", getInnerHtml(tableHeaderElements.get(11)));
        assertEquals("RH Name", getInnerHtml(tableHeaderElements.get(12)));
        assertEquals("Publisher", getInnerHtml(tableHeaderElements.get(13)));
        assertEquals("Pub Date", getInnerHtml(tableHeaderElements.get(14)));
        assertEquals("Number of Copies", getInnerHtml(tableHeaderElements.get(15)));
        assertEquals("Reported value", getInnerHtml(tableHeaderElements.get(16)));
        assertEquals("Amt in USD", getInnerHtml(tableHeaderElements.get(17)));
        assertEquals("Market", getInnerHtml(tableHeaderElements.get(18)));
        assertEquals("Market Period From", getInnerHtml(tableHeaderElements.get(19)));
        assertEquals("Market Period To", getInnerHtml(tableHeaderElements.get(20)));
        assertEquals("Author", getInnerHtml(tableHeaderElements.get(21)));
    }

    private void verifyTableBody(WebElement usagesTable) {
        WebElement table = findElement(usagesTable, By.className(V_TABLE_BODY_CLASS_NAME));
        List<WebElement> tableRows = findElements(table, By.tagName(HTML_TR_TAG_NAME));
        assertTrue(tableRows.isEmpty());
    }

    private void verifyFiltersWidget(WebElement tabContainer) {
        WebElement filterWidget = waitAndFindElement(tabContainer, By.id(USAGE_FILTER_WIDGET_ID));
        assertNotNull(filterWidget);
        assertNotNull(findElementByText(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT));
        verifyBatchesFilter(filterWidget);
        verifyRightsholdersFilter(filterWidget);
        verifyPaymentDateComponent(filterWidget, "payment-date-filter");
        assertComboboxElement(filterWidget, "fiscal-year-filter", HTML_SPAN_TAG_NAME, "Fiscal Year To");
        verifyFiltersWidgetButtons(filterWidget);
    }

    private void verifyBatchesFilter(WebElement filterWidget) {
        openBatchesFilterWindow(filterWidget);
        verifyFilterWindow("batches-filter-window", "Batches filter");
    }

    private void verifyRightsholdersFilter(WebElement filterWidget) {
        openRroFilterWindow(filterWidget);
        verifyFilterWindow("rightsholders-filter-window", "RROs filter");
    }

    private void openRroFilterWindow(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertElement(filterWidget, "rightsholders-filter");
        assertNotNull(findElementByText(rightsholdersFilter, HTML_DIV_TAG_NAME, "(0)"));
        WebElement rightsholderFilterButton = findElementByText(rightsholdersFilter, HTML_SPAN_TAG_NAME, "RROs");
        assertNotNull(rightsholderFilterButton);
        clickElementAndWait(rightsholderFilterButton);
    }

    private void openBatchesFilterWindow(WebElement filterWidget) {
        WebElement batchesFilter = assertElement(filterWidget, "batches-filter");
        assertNotNull(findElementByText(batchesFilter, HTML_DIV_TAG_NAME, "(0)"));
        WebElement batchesFilterButton = findElementByText(batchesFilter, HTML_SPAN_TAG_NAME, "Batches");
        assertNotNull(batchesFilterButton);
        clickElementAndWait(batchesFilterButton);
    }

    private void verifyFilterWindow(String id, String caption) {
        WebElement filterWindow = findElementById(id);
        assertNotNull(filterWindow);
        assertEquals(caption, getWindowCaption(filterWindow));
        verifySearchToolBar(filterWindow);
        assertElement(filterWindow, "Save");
        assertElement(filterWindow, CLEAR_BATTON_ID);
        clickElementAndWait(assertElement(filterWindow, CLOSE_BUTTON));
    }

    private void verifySearchToolBar(WebElement filterWindow) {
        WebElement searchToolBar = assertElement(filterWindow, "search-toolbar");
        assertNotNull(findElement(searchToolBar, By.tagName(HTML_INPUT_TAG_NAME)));
        assertNotNull(findElement(searchToolBar, By.className("button-search")));
        assertNotNull(findElement(searchToolBar, By.className("button-clear")));
    }

    private void verifyPaymentDateComponent(WebElement filterWidget, String id) {
        WebElement paymentDateFilter = assertElement(filterWidget, id);
        assertNotNull(findElementByText(paymentDateFilter, HTML_SPAN_TAG_NAME, "Payment Date To"));
        assertNotNull(findElement(paymentDateFilter, By.className("v-datefield")));
        assertNotNull(findElement(paymentDateFilter, By.className(V_DATE_FIELD_BUTTON_CLASS_NAME)));
    }

    private void verifyFiltersWidgetButtons(WebElement filterWidget) {
        WebElement buttonsContainer = assertElement(filterWidget, FILTER_BUTTONS_LAYOUT_ID);
        assertElement(buttonsContainer, APPLY_BUTTON_ID);
        assertElement(buttonsContainer, CLEAR_BATTON_ID);
    }

    private WebElement assertTextElement(WebElement parentElement, String id, String caption) {
        WebElement webElement = assertElement(parentElement, id);
        WebElement elementContainer = getParentElement(webElement);
        assertNotNull(findElementByText(elementContainer, HTML_SPAN_TAG_NAME, caption));
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
