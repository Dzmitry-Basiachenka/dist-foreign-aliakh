package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

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
@Ignore
public class DeleteUsageBatchUiTest extends ForeignCommonUiTest {

    private UsageBatchInfo usageBatch1 = new UsageBatchInfo("CADRA_11Dec16", "01/11/2017", "FY2017");
    private UsageBatchInfo usageBatch2 = new UsageBatchInfo("AccessCopyright_11Dec16", "09/10/2015", "FY2016");
    private UsageBatchInfo usageBatch3 = new UsageBatchInfo("JAACC_11Dec16", "08/16/2018", "FY2019");

    @Test
    // Test cases IDs: 'fa7466eb-7d5d-4068-877a-835522f1deeb', '18eb492e-5ec1-4c87-ac1c-be9ed9facbdb',
    // '7ffd686f-5080-4d99-bf68-fd4576c58508'
    public void testDeleteUsageBatchModalWindow() {
        WebElement window = openDeleteUsageBatchWindow();
        verifyDeleteUsageBatchWindow(window);
    }

    @Test
    // Test cases IDs: 'b598bf3f-3068-41b8-a5d9-63876b9f2b62', '1c2e8f69-02e7-4477-83c8-24dc69f9cd1e'
    public void testSearch() {
        WebElement window = openDeleteUsageBatchWindow();
        WebElement searchToolbar = getSearchToolbar(window);
        WebElement searchField = findElement(searchToolbar, By.className("v-textfield"));
        assertNotNull(searchField);
        WebElement table = getUsageBatchesTable(window);
        WebElement searchButton = getSearchButton(searchToolbar);
        verifySearchByBatchName(searchField, searchButton, table);
        verifySearchByPaymentDate(searchField, searchButton, table);
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
        loginAsSpecialist();
        WebElement deleteUsageBatchButton = findElement(By.id("Delete_Usage_Batch"));
        clickElementAndWait(deleteUsageBatchButton);
        WebElement deleteUsageBatchWindow = findElement(By.id("delete-usage-batch"));
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
        assertNotNull(prompt);
        assertEquals("Enter Batch Name or Payment Date (MM/dd/yyyy)", prompt.getAttribute("value"));
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
        WebElement closeButton = findElement(window, By.id("Close"));
        assertNotNull(closeButton);
        clickElementAndWait(closeButton);
        assertNull(findElement(By.id("delete-usage-batch")));
    }

    private static class UsageBatchInfo {

        private String name;
        private String paymentDate;
        private String fiscalYear;

        private UsageBatchInfo(String name, String paymentDate, String fiscalYear) {
            this.name = name;
            this.paymentDate = paymentDate;
            this.fiscalYear = fiscalYear;
        }
    }
}
