package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
public class UsagesTabUiTest extends ForeignCommonUiTest {

    private static final String CLEAR_BATTON_ID = "Clear";
    private static final String FILTERS_HEADER_TEXT = "Filters";
    private static final String EXPORT_BUTTON_ID = "Export";
    private static final String LOAD_BUTTON_ID = "Load";
    private static final String CLOSE_BUTTON_ID = "Close";

    @Test
    // Test case IDs: '65520aa2-3a1c-4c7c-81e6-96a0a845331e', 90031f0e-1d4b-4d1c-86a6-ca5d72ae3637
    public void testVerifyUsagesTabSpecialist() {
        loginAsSpecialist();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonSpecialist(usagesLayout);
    }

    @Test
    // Test case ID: 'd84167c0-2140-4207-a3bb-92e26ffeb5c1'
    public void testVerifyUsagesTabManager() {
        loginAsManager();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonManager(usagesLayout);
    }

    @Test
    // Test case ID: 'd84167c0-2140-4207-a3bb-92e26ffeb5c1'
    public void testVerifyUsagesTabViewOnly() {
        loginAsViewOnly();
        WebElement usagesLayout = verifyUsagesTab();
        verifyUsagesLayoutButtonViewOnly(usagesLayout);
    }

    @Test
    // Test cases IDs: 'a2a66f64-ab10-44d4-a2b9-c779631cbe6a', '1b24b641-8da0-46a2-917c-7ee99d781d6d',
    // '90031f0e-1d4b-4d1c-86a6-ca5d72ae3637'
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlySpecialist() {
        loginAsSpecialist();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // Test case ID: 'd84167c0-2140-4207-a3bb-92e26ffeb5c1'
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlyManager() {
        loginAsManager();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // Test case ID: 'd84167c0-2140-4207-a3bb-92e26ffeb5c1'
    public void testVerifyBatchAndUsageInformationIsDisplayedCorrectlyViewOnly() {
        loginAsViewOnly();
        verifyBatchAndUsageInformationIsDisplayedCorrectly();
    }

    @Test
    // Test cases IDs: 'd438bf76-31f9-4266-8a4e-8c416a616aed', '87ac1966-737c-4b18-b5d1-6e583776b3a1',
    // 'fbadb917-85db-4099-8c00-b218317a9818'
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonSpecialist() {
        loginAsSpecialist();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    @Test
    // Test cases ID: 'fbadb917-85db-4099-8c00-b218317a9818'
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonManager() {
        loginAsManager();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    @Test
    // Test cases ID: 'fbadb917-85db-4099-8c00-b218317a9818'
    public void testVerifyMultiplyUsageDataFilterAndClearFilterButtonViewOnly() {
        loginAsViewOnly();
        verifyMultiplyUsageDataFilterAndClearFilterButton();
    }

    private void verifyBatchAndUsageInformationIsDisplayedCorrectly() {
        WebElement usagesTab = selectUsagesTab();
        WebElement filterWidget = assertWebElement(usagesTab, "usages-filter-widget");
        assertWebElement(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT);
        applyBatchesFilter(filterWidget, "CADRA_11Dec16");
        clickButtonAndWait(assertWebElement(filterWidget, "filter-buttons"), "Apply");
        verifyFoundUsages(usagesTab);
    }

    private void verifyMultiplyUsageDataFilterAndClearFilterButton() {
        WebElement usagesTab = selectUsagesTab();
        WebElement filterWidget = assertWebElement(usagesTab, "usages-filter-widget");
        assertWebElement(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT);
        applyBatchesFilter(filterWidget, "CADRA_11Dec16");
        applyRrosFilter(filterWidget,
            "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
        applyCurrentDateForDateField(assertWebElement(By.id("payment-date-filter")));
        applyStatus();
        applyFiscalYear();
        WebElement filterButtonsLayout = assertWebElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, "Apply");
        verifyFoundUsages(usagesTab);
        clickButtonAndWait(filterButtonsLayout, CLEAR_BATTON_ID);
        assertUsagesFilterEmpty(filterWidget);
    }

    private void applyStatus() {
        WebElement statusFilter = assertWebElement(By.id("status-filter"));
        WebElement statusFilterSelectButton =
            assertWebElement(statusFilter, By.className("v-filterselect-button"));
        clickElementAndWait(statusFilterSelectButton);
        clickElementAndWait(assertWebElement(statusFilter, HTML_SPAN_TAG_NAME, "ELIGIBLE"));
    }

    private void applyFiscalYear() {
        WebElement fiscalYearFilter = assertWebElement(By.id("fiscal-year-filter"));
        WebElement fiscalYearFilterSelectButton =
            assertWebElement(fiscalYearFilter, By.className("v-filterselect-button"));
        clickElementAndWait(fiscalYearFilterSelectButton);
        clickElementAndWait(assertWebElement(fiscalYearFilter, HTML_SPAN_TAG_NAME, "2017"));
    }

    private void verifyFoundUsages(WebElement usagesTab) {
        WebElement usagesLayout = assertWebElement(usagesTab, "usages-layout");
        WebElement usagesTable = assertWebElement(usagesLayout, "usages-table");
        assertTableRowElements(usagesTable,
            "6997788888",
            "ELIGIBLE",
            "CADRA_11Dec16",
            "FY2017", "7000813806",
            "CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil",
            "01/11/2017",
            "2001 IEEE Workshop on High Performance Switching and Routing, 29-31 May 2001, Dallas, Texas, USA",
            "Efficient Generation of H2 by Splitting Water with an Isothermal Redox Cycle",
            "1008902112377654XX",
            "180382914",
            "1000009997",
            "IEEE - Inst of Electrical and Electronics Engrs",
            "IEEE",
            "09/10/2013",
            "2502232",
            "2,500.00",
            "35,000.00",
            "35,000.00",
            "Doc Del",
            "2013",
            "2017",
            "Íñigo López de Mendoza, marqués de Santillana");
    }

    private WebElement verifyUsagesTab() {
        WebElement usagesTab = selectUsagesTab();
        verifyFiltersWidget(usagesTab);
        WebElement usagesLayout = assertWebElement(usagesTab, "usages-layout");
        verifyUsagesTable(usagesLayout);
        return usagesLayout;
    }

    private void verifyUsagesLayoutButtonSpecialist(WebElement usagesLayout) {
        WebElement buttonsLayout = assertWebElement(usagesLayout, "usages-buttons");
        verifyUsagesButtonLayout(buttonsLayout,
            Sets.newHashSet(LOAD_BUTTON_ID, "Add_To_Scenario", EXPORT_BUTTON_ID, "Delete_Usage_Batch"));
        verifyUploadUsageWindow(buttonsLayout);
        verifyLoadUsageButton(buttonsLayout);
        verifyDeleteUsageButton(buttonsLayout);
        verifyAddToScenarioButton(buttonsLayout);
    }

    private void verifyUsagesLayoutButtonManager(WebElement usagesLayout) {
        WebElement buttonsLayout = assertWebElement(usagesLayout, "usages-buttons");
        verifyUsagesButtonLayout(buttonsLayout, Sets.newHashSet(EXPORT_BUTTON_ID));
    }

    private void verifyUsagesLayoutButtonViewOnly(WebElement usagesLayout) {
        WebElement buttonsLayout = assertWebElement(usagesLayout, "usages-buttons");
        verifyUsagesButtonLayout(buttonsLayout, Sets.newHashSet(EXPORT_BUTTON_ID));
    }

    private void verifyUsagesButtonLayout(WebElement buttonsLayout, Set<String> buttonsIds) {
        Set<String> usagesButtons =
            Sets.newHashSet(LOAD_BUTTON_ID, "Add_To_Scenario", EXPORT_BUTTON_ID, "Delete_Usage_Batch");
        assertButtonsToolbar(buttonsLayout, usagesButtons, buttonsIds);
    }

    private void verifyLoadUsageButton(WebElement buttonsLayout) {
        clickButtonAndWait(buttonsLayout, LOAD_BUTTON_ID);
        WebElement uploadWindow = assertWebElement(By.id("usage-upload-window"));
        clickButtonAndWait(uploadWindow, CLOSE_BUTTON_ID);
    }

    private void verifyDeleteUsageButton(WebElement buttonsLayout) {
        clickButtonAndWait(buttonsLayout, "Delete_Usage_Batch");
        WebElement deleteUsageWindow = assertWebElement(By.id("delete-usage-batch"));
        clickButtonAndWait(deleteUsageWindow, CLOSE_BUTTON_ID);
    }

    private void verifyAddToScenarioButton(WebElement buttonsLayout) {
        clickButtonAndWait(buttonsLayout, "Add_To_Scenario");
        WebElement addToScenarionWindow = assertWebElement(By.id("notification-window"));
        clickButtonAndWait(addToScenarionWindow, "Ok");
    }

    private void verifyUploadUsageWindow(WebElement buttonsLayout) {
        clickButtonAndWait(buttonsLayout, LOAD_BUTTON_ID);
        WebElement uploadWindow = assertWebElement(By.id("usage-upload-window"));
        assertEquals("Upload Usage Batch", getWindowCaption(uploadWindow));
        verifyUploadElement(uploadWindow);
        verifyRightsholdersFields(uploadWindow);
        verifyDateFields(uploadWindow);
        assertTextFieldElement(uploadWindow, "usage-batch-name-field", "Usage Batch Name");
        assertTextFieldElement(uploadWindow, "gross-amount-field", "Gross Amount in USD");
        assertWebElement(uploadWindow, "Upload");
        clickButtonAndWait(uploadWindow, CLOSE_BUTTON_ID);
    }

    private void verifyUploadElement(WebElement uploadWindow) {
        WebElement uploadField = assertWebElement(uploadWindow, "usage-upload-component");
        assertWebElement(uploadField, HTML_SPAN_TAG_NAME, "File to Upload");
        assertWebElement(uploadField, HTML_SPAN_TAG_NAME, "*");
        assertWebElement(uploadField, By.tagName(HTML_INPUT_TAG_NAME));
        assertWebElement(assertWebElement(uploadField, By.tagName("form")), HTML_SPAN_TAG_NAME, "Browse");
    }

    private void verifyRightsholdersFields(WebElement uploadWindow) {
        assertTextFieldElement(uploadWindow, "rro-account-name-field", "RRO Account Name");
        assertTextFieldElement(uploadWindow, "rro-account-number-field", "RRO Account #");
        assertWebElement(uploadWindow, "Verify");
    }

    private void verifyDateFields(WebElement uploadWindow) {
        verifyPaymentDateComponent(uploadWindow, "payment-date-field");
        assertTextFieldElement(uploadWindow, "fiscal-year-field", "Fiscal Year");
    }

    private void verifyUsagesTable(WebElement usagesLayout) {
        WebElement usagesTable = assertWebElement(usagesLayout, "usages-table");
        verifyTableHeaderElements(usagesTable);
        assertWebElement(usagesTable, By.className("v-table-column-selector"));
        assertTableRowElements(usagesTable, 0);
    }

    private void verifyTableHeaderElements(WebElement usagesTable) {
        assertTableHeaderElements(usagesTable,
            "Detail ID",
            "Detail Status",
            "Usage Batch Name",
            "Fiscal Year",
            "RRO Account #",
            "RRO Name",
            "Payment Date",
            "Title",
            "Article",
            "Standard Number",
            "Wr Wrk Inst",
            "RH Account #",
            "RH Name",
            "Publisher",
            "Pub Date",
            "Number of Copies",
            "Reported value",
            "Amt in USD",
            "Gross Amt in USD",
            "Market",
            "Market Period From",
            "Market Period To",
            "Author");
    }

    private void verifyFiltersWidget(WebElement tabContainer) {
        WebElement filterWidget = assertWebElement(tabContainer, "usages-filter-widget");
        assertWebElement(filterWidget, HTML_DIV_TAG_NAME, FILTERS_HEADER_TEXT);
        verifyBatchesFilter(filterWidget);
        verifyRROsFilter(filterWidget);
        verifyPaymentDateComponent(filterWidget, "payment-date-filter");
        assertComboboxElement(assertWebElement(By.id("status-filter")),
            " ", "NEW", "WORK_FOUND", "RH_NOT_FOUND", "SENT_FOR_RA", "ELIGIBLE");
        assertComboboxElement(filterWidget, "fiscal-year-filter", "Fiscal Year To");
        verifyFiltersWidgetButtons(filterWidget);
    }

    private void verifyBatchesFilter(WebElement filterWidget) {
        openBatchesFilterWindow(filterWidget);
        verifyFilterWindow("batches-filter-window", "Batches filter", "Enter Usage Batch Name");
    }

    private void verifyRROsFilter(WebElement filterWidget) {
        openRroFilterWindow(filterWidget);
        verifyFilterWindow("rightsholders-filter-window", "RROs filter", "Enter RRO Name/Account #");
    }

    private void openRroFilterWindow(WebElement filterWidget) {
        WebElement rightsholdersFilter = assertWebElement(filterWidget, "rightsholders-filter");
        assertWebElement(rightsholdersFilter, HTML_DIV_TAG_NAME, "(0)");
        WebElement rightsholderFilterButton = assertWebElement(rightsholdersFilter, HTML_SPAN_TAG_NAME, "RROs");
        clickElementAndWait(rightsholderFilterButton);
    }

    private void openBatchesFilterWindow(WebElement filterWidget) {
        WebElement batchesFilter = assertWebElement(filterWidget, "batches-filter");
        assertWebElement(batchesFilter, HTML_DIV_TAG_NAME, "(0)");
        WebElement batchesFilterButton = assertWebElement(batchesFilter, HTML_SPAN_TAG_NAME, "Batches");
        clickElementAndWait(batchesFilterButton);
    }

    private void verifyFilterWindow(String id, String windowCaption, String promptMessage) {
        WebElement filterWindow = assertWebElement(By.id(id));
        assertEquals(windowCaption, getWindowCaption(filterWindow));
        assertSearchToolbar(filterWindow, promptMessage);
        assertWebElement(filterWindow, "Save");
        assertWebElement(filterWindow, "Clear");
        clickButtonAndWait(filterWindow, CLOSE_BUTTON_ID);
    }

    private void verifyPaymentDateComponent(WebElement filterWidget, String id) {
        WebElement paymentDateFilter = assertWebElement(filterWidget, id);
        assertWebElement(paymentDateFilter, HTML_SPAN_TAG_NAME, "Payment Date To");
        assertWebElement(paymentDateFilter, By.className("v-datefield"));
        assertWebElement(paymentDateFilter, By.className(V_DATE_FIELD_BUTTON_CLASS_NAME));
    }

    private void verifyFiltersWidgetButtons(WebElement filterWidget) {
        WebElement buttonsContainer = assertWebElement(filterWidget, "filter-buttons");
        assertWebElement(buttonsContainer, "Apply");
        assertWebElement(buttonsContainer, "Clear");
    }
}
