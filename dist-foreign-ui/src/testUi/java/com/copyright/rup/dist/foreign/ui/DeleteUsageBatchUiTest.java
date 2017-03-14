package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * UI test to verify 'Delete Usage Batch' functionality.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/1/17
 *
 * @author Aliaksandr Radkevich
 */
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DeleteUsageBatchUiTest extends ForeignCommonUiTest {

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;

    private UsageBatchInfo usageBatch1 = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
    private UsageBatchInfo usageBatch2 = new UsageBatchInfo("AccessCopyright_11Dec16", "09/10/2015", "FY2016",
        "2000017004 - Access Copyright, The Canadian Copyright Agency");
    private UsageBatchInfo usageBatch3 = new UsageBatchInfo("JAACC_11Dec16", "08/16/2018", "FY2019",
        "7001440663 - JAACC, Japan Academic Association for Copyright Clearance [T]");
    private UsageBatchInfo usageBatch4 =
        new UsageBatchInfo("Batch to delete", "07/11/2017", "FY2018", "1000002797 - British Film Institute (BFI)");

    @Test
    // Test cases IDs: 'fa7466eb-7d5d-4068-877a-835522f1deeb', '18eb492e-5ec1-4c87-ac1c-be9ed9facbdb',
    // '7ffd686f-5080-4d99-bf68-fd4576c58508'
    public void testDeleteUsageBatchModalWindow() {
        loginAsSpecialist();
        WebElement window = openDeleteUsageBatchWindow();
        verifyDeleteUsageBatchWindow(window);
    }

    @Test
    // Test cases IDs: 'b598bf3f-3068-41b8-a5d9-63876b9f2b62', '1c2e8f69-02e7-4477-83c8-24dc69f9cd1e'
    public void testSearch() {
        loginAsSpecialist();
        WebElement window = openDeleteUsageBatchWindow();
        WebElement searchToolbar = getSearchToolbar(window);
        WebElement searchField = findElement(searchToolbar, By.className("v-textfield"));
        assertNotNull(searchField);
        WebElement table = getUsageBatchesTable(window);
        WebElement searchButton = getSearchButton(searchToolbar);
        verifySearchByBatchName(searchField, searchButton, table);
        verifySearchByPaymentDate(searchField, searchButton, table);
    }

    @Test
    // Test cases IDs: '251507d4-af2d-4f5f-a4d0-772b70804f83', '0de548a6-6e56-4141-95fb-71931e7f1c7d',
    // 'af0b68e7-236a-4f21-8691-a6c6192d153c'
    public void testDeleteUsageBatchWithApproval() {
        usageBatchRepository.insert(buildUsageBatch());
        usageRepository.insertUsage(buildUsage());
        loginAsSpecialist();
        WebElement filterWidget = findElementById(USAGE_FILTER_WIDGET_ID);
        assertNotNull(filterWidget);
        applyFilters(filterWidget, usageBatch4);
        WebElement usagesTable = findElementById("usages-table");
        assertUsagesTableNotEmpty(usagesTable);
        WebElement window = openDeleteUsageBatchWindow();
        WebElement usageBatchesTable = getUsageBatchesTable(window);
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatch2, usageBatch3, usageBatch4);
        WebElement confirmDialog = openDeleteUsageBatchConfirmDialog(usageBatchesTable,
            "4b67f17c-3c32-4b55-b2a0-dabebb513304", usageBatch4.name);
        clickButtonAndWait(confirmDialog, "Yes");
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatch2, usageBatch3);
        clickButtonAndWait(window, CLOSE_BUTTON_ID);
        assertUsagesFilterEmpty(filterWidget, usagesTable);
        assertNullFilterItem(filterWidget, BATCHES_FILTER_ID, "batches-filter-window", usageBatch4.name);
        assertNullFilterItem(filterWidget, RRO_FILTER_ID, "rightsholders-filter-window", usageBatch4.rro);
        verifyFiscalYearFilter(filterWidget, " ", "2016", "2017", "2019");
        assertEquals(0, usageBatchRepository.getUsageBatchesCountByName(usageBatch4.name));
    }

    @Test
    // Test case ID: '259c2da3-46e5-4493-942b-3ae47cae7f94'
    public void testDeleteUsageBatchWithoutApproval() {
        loginAsSpecialist();
        WebElement filterWidget = findElementById(USAGE_FILTER_WIDGET_ID);
        assertNotNull(filterWidget);
        applyFilters(filterWidget, usageBatch1);
        WebElement usagesTable = findElementById("usages-table");
        assertUsagesTableNotEmpty(usagesTable);
        WebElement window = openDeleteUsageBatchWindow();
        WebElement usageBatchesTable = getUsageBatchesTable(window);
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatch2, usageBatch3);
        WebElement confirmDialog = openDeleteUsageBatchConfirmDialog(usageBatchesTable,
            "56282dbc-2468-48d4-b926-93d3458a656a", usageBatch1.name);
        clickButtonAndWait(confirmDialog, "Cancel");
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatch2, usageBatch3);
        clickButtonAndWait(window, CLOSE_BUTTON_ID);
        assertUsagesTableNotEmpty(usagesTable);
        assertEquals(1, usageBatchRepository.getUsageBatchesCountByName(usageBatch1.name));
    }

    private void verifyFiscalYearFilter(WebElement filterWidget, String... expectedItems) {
        WebElement fiscalYearFilter = findElement(filterWidget, By.id("fiscal-year-filter"));
        assertNotNull(fiscalYearFilter);
        WebElement button = findElement(fiscalYearFilter, By.className("v-filterselect-button"));
        assertNotNull(button);
        clickElementAndWait(button);
        WebElement optionlist = findElementById("VAADIN_COMBOBOX_OPTIONLIST");
        assertEquals(expectedItems.length, findElements(optionlist, By.tagName(HTML_TR_TAG_NAME)).size());
        for (String item : expectedItems) {
            findElementByText(optionlist, HTML_SPAN_TAG_NAME, item);
        }
    }

    private void applyFilters(WebElement filterWidget, UsageBatchInfo usageBatchInfo) {
        saveFilter(filterWidget, BATCHES_FILTER_ID, "batches-filter-window", usageBatchInfo.name);
        saveFilter(filterWidget, RRO_FILTER_ID, "rightsholders-filter-window", usageBatchInfo.rro);
        WebElement fiscalYearFilter = findElement(filterWidget, By.id("fiscal-year-filter"));
        assertNotNull(fiscalYearFilter);
        WebElement button = findElement(fiscalYearFilter, By.className("v-filterselect-button"));
        assertNotNull(button);
        clickElementAndWait(button);
        WebElement optionlist = findElementById("VAADIN_COMBOBOX_OPTIONLIST");
        WebElement fiscalYear =
            findElementByText(optionlist, HTML_SPAN_TAG_NAME, usageBatchInfo.fiscalYear.substring(2));
        assertNotNull(fiscalYear);
        clickElementAndWait(fiscalYear);
        WebElement filterButtonsLayout = assertElement(filterWidget, "filter-buttons");
        clickButtonAndWait(filterButtonsLayout, APPLY_BUTTON_ID);
    }

    private WebElement openDeleteUsageBatchConfirmDialog(WebElement table, String usageBatchId, String usageBatchName) {
        WebElement deleteButton = findElement(table, By.id(usageBatchId));
        assertNotNull(deleteButton);
        clickElementAndWait(deleteButton);
        WebElement confirmDialog = findElementById("confirm-dialog-window");
        assertNotNull(confirmDialog);
        WebElement label = findElement(confirmDialog, By.className("v-label"));
        assertNotNull(label);
        assertEquals(String.format("Are you sure you want to delete usage batch '%s'?", usageBatchName),
            label.getText());
        return confirmDialog;
    }

    private void assertNullFilterItem(WebElement filterWidget, String filterId, String filterWindowId, String item) {
        WebElement itemsFilter = findElement(filterWidget, By.id(filterId));
        assertNotNull(itemsFilter);
        clickElementAndWait(itemsFilter);
        WebElement filterWindow = findElementById(filterWindowId);
        assertNotNull(filterWindow);
        assertNull(findElementByText(filterWindow, HTML_LABEL_TAG_NAME, item));
        clickButtonAndWait(filterWindow, CLOSE_BUTTON_ID);
    }

    private void assertUsagesTableNotEmpty(WebElement usagesTable) {
        assertNotNull(usagesTable);
        WebElement usagesTableBody = findElement(usagesTable, By.className(V_TABLE_BODY_CLASS_NAME));
        assertNotNull(usagesTableBody);
        List<WebElement> rows = findElements(usagesTableBody, By.tagName(HTML_TR_TAG_NAME));
        assertEquals(1, rows.size());
    }

    private void saveFilter(WebElement filterWidget, String filterId, String filterWindowId, String item) {
        WebElement itemsFilter = findElement(filterWidget, By.id(filterId));
        assertNotNull(itemsFilter);
        clickElementAndWait(itemsFilter);
        WebElement filterWindow = findElementById(filterWindowId);
        assertNotNull(filterWindow);
        WebElement label = findElementByText(filterWindow, HTML_LABEL_TAG_NAME, item);
        assertNotNull(label);
        clickElementAndWait(label);
        clickButtonAndWait(filterWindow, SAVE_BUTTON_ID);
    }

    private WebElement getSearchToolbar(WebElement window) {
        WebElement searchToolbar = findElement(window, By.id("search-toolbar"));
        assertNotNull(searchToolbar);
        return searchToolbar;
    }

    private WebElement getSearchButton(WebElement searchToolbar) {
        WebElement searchButton = findElement(searchToolbar, By.className("button-search"));
        assertNotNull(searchButton);
        return searchButton;
    }

    private WebElement getUsageBatchesTable(WebElement window) {
        WebElement table = findElement(window, By.id("usage-batches-table"));
        assertNotNull(table);
        return table;
    }

    private void verifySearchByBatchName(WebElement searchField, WebElement searchButton, WebElement table) {
        applySearch(searchField, searchButton, "CADRA");
        verifyTableRows(table, usageBatch1);
        applySearch(searchField, searchButton, "AccessCopyright_11Dec16");
        verifyTableRows(table, usageBatch2);
        applySearch(searchField, searchButton, "FakeName");
        verifyTableRows(table);
        applySearch(searchField, searchButton, "jaacc_11dec16");
        verifyTableRows(table, usageBatch3);
        applySearch(searchField, searchButton, "JAACC_11DEC16");
        verifyTableRows(table, usageBatch3);
    }

    private void verifySearchByPaymentDate(WebElement searchField, WebElement searchButton, WebElement table) {
        applySearch(searchField, searchButton, "01/11");
        verifyTableRows(table, usageBatch1);
        applySearch(searchField, searchButton, "01/11/2020");
        verifyTableRows(table);
        applySearch(searchField, searchButton, "09/10/2015");
        verifyTableRows(table, usageBatch2);
    }

    private void applySearch(WebElement searchField, WebElement searchButton, String searchValue) {
        sendKeysToInput(searchField, searchValue);
        clickElementAndWait(searchButton);
    }

    private WebElement openDeleteUsageBatchWindow() {
        WebElement deleteUsageBatchButton = findElementById("Delete_Usage_Batch");
        clickElementAndWait(deleteUsageBatchButton);
        WebElement deleteUsageBatchWindow = findElementById("delete-usage-batch");
        assertNotNull(deleteUsageBatchWindow);
        return deleteUsageBatchWindow;
    }

    private void verifyDeleteUsageBatchWindow(WebElement deleteUsageBatchWindow) {
        assertEquals("Delete Usage Batch",
            findElement(deleteUsageBatchWindow, By.className(V_WINDOW_HEADER_CLASS_NAME)).getText());
        verifySearchToolbar(deleteUsageBatchWindow);
        verifyTable(deleteUsageBatchWindow);
        verifyCloseButton(deleteUsageBatchWindow);
    }

    private void verifyTable(WebElement window) {
        WebElement table = getUsageBatchesTable(window);
        verifyTableColumns(table, "Usage Batch Name", "Payment Date", "Fiscal Year", StringUtils.EMPTY);
        verifyTableRows(table, usageBatch1, usageBatch2, usageBatch3);
        verifyTableSorting(table);
    }

    private void verifyTableSorting(WebElement table) {
        WebElement header = findElement(table, By.className(V_TABLE_HEADER_CLASS_NAME));
        List<WebElement> columns = findElements(header, By.className("v-table-header-sortable"));
        assertEquals(3, columns.size());
        String ascSortStyleName = "v-table-header-cell-asc";
        String descSortStyleName = "v-table-header-cell-desc";
        verifyColumnSorting(header, columns.get(0), ascSortStyleName);
        verifyColumnSorting(header, columns.get(0), descSortStyleName);
        verifyColumnSorting(header, columns.get(1), descSortStyleName);
        verifyColumnSorting(header, columns.get(1), ascSortStyleName);
        verifyColumnSorting(header, columns.get(2), ascSortStyleName);
        verifyColumnSorting(header, columns.get(2), descSortStyleName);
    }

    private void verifyColumnSorting(WebElement tableHeader, WebElement column, String styleName) {
        clickElementAndWait(column);
        assertNotNull(findElement(tableHeader, By.className(styleName)));
    }

    private void verifySearchToolbar(WebElement window) {
        assertNotNull(window);
        WebElement searchToolbar = getSearchToolbar(window);
        WebElement prompt = findElement(searchToolbar, By.className("v-textfield-prompt"));
        assertEquals("Enter Batch Name or Payment Date (MM/dd/yyyy)", getValueAttribute(prompt));
        assertNotNull(getSearchButton(searchToolbar));
        assertNotNull(findElement(searchToolbar, By.className("button-clear")));
    }

    private void verifyTableRows(WebElement table, UsageBatchInfo... usageBatches) {
        List<WebElement> rows = findElements(findElement(table, By.className(V_TABLE_BODY_CLASS_NAME)),
            By.tagName(HTML_TR_TAG_NAME));
        assertEquals(usageBatches.length, rows.size());
        for (int i = 0; i < usageBatches.length; i++) {
            verifyUsageBatch(rows.get(i), usageBatches[i]);
        }
    }

    private void verifyUsageBatch(WebElement row, UsageBatchInfo usageBatch) {
        List<WebElement> cells = findElements(row, By.className(V_TABLE_CELL_CONTENT_CLASS_NAME));
        assertEquals(usageBatch.name, cells.get(0).getText());
        assertEquals(usageBatch.paymentDate, cells.get(1).getText());
        assertEquals(usageBatch.fiscalYear, cells.get(2).getText());
        assertNotNull(findElement(cells.get(3), By.className("Delete")));
    }

    private void verifyCloseButton(WebElement window) {
        WebElement closeButton = findElement(window, By.id(CLOSE_BUTTON_ID));
        assertNotNull(closeButton);
        clickElementAndWait(closeButton);
        assertNull(findElementById("delete-usage-batch"));
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("4b67f17c-3c32-4b55-b2a0-dabebb513304");
        usageBatch.setName("Batch to delete");
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(1000002797L);
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(LocalDate.of(2017, 7, 11));
        usageBatch.setFiscalYear(2018);
        usageBatch.setGrossAmount(new BigDecimal(50000));
        return usageBatch;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("d9d4b36f-0149-4ec8-a5b9-663eaa027b89");
        usage.setBatchId("4b67f17c-3c32-4b55-b2a0-dabebb513304");
        usage.setDetailId(6999977777L);
        usage.setWrWrkInst(101125380L);
        usage.setWorkTitle("Understanding administrative law");
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(1000006746L);
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setArticle("Fox, William F.");
        usage.setStandardNumber("1008902112365655XX");
        usage.setPublisher("IEEE");
        usage.setPublicationDate(LocalDate.of(2013, 9, 10));
        usage.setMarket("Doc Del");
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setNumberOfCopies(250);
        usage.setReportedValue(new BigDecimal(2500));
        return usage;
    }

    private static class UsageBatchInfo {

        private String name;
        private String paymentDate;
        private String fiscalYear;
        private String rro;

        private UsageBatchInfo(String name, String paymentDate, String fiscalYear, String rro) {
            this.name = name;
            this.paymentDate = paymentDate;
            this.fiscalYear = fiscalYear;
            this.rro = rro;
        }
    }
}
