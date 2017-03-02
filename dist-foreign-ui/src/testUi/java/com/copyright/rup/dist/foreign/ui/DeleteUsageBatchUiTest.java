package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.commons.lang3.StringUtils;
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
public class DeleteUsageBatchUiTest extends ForeignCommonUiTest {

    @Test
    // Test cases IDs: 'fa7466eb-7d5d-4068-877a-835522f1deeb', '18eb492e-5ec1-4c87-ac1c-be9ed9facbdb',
    // '7ffd686f-5080-4d99-bf68-fd4576c58508'
    public void testDeleteUsageBatchModalWindow() {
        loginAsSpecialist();
        WebElement deleteUsageBatchButton = findElement(By.id("Delete_Usage_Batch"));
        clickElementAndWait(deleteUsageBatchButton);
        verifyDeleteUsageBatchWindow();
    }

    private void verifyDeleteUsageBatchWindow() {
        WebElement deleteUsageBatchWindow = findElement(By.id("delete-usage-batch"));
        assertNotNull(deleteUsageBatchWindow);
        assertEquals("Delete Usage Batch",
            findElement(deleteUsageBatchWindow, By.className(V_WINDOW_HEADER_CLASS_NAME)).getText());
        verifySearchToolbar(deleteUsageBatchWindow);
        verifyTable(deleteUsageBatchWindow);
        verifyCloseButton(deleteUsageBatchWindow);
    }

    private void verifyTable(WebElement window) {
        WebElement table = findElement(window, By.id("usage-batches-table"));
        assertNotNull(table);
        verifyTableColumns(table, "Usage Batch Name", "Payment Date", "Fiscal Year", StringUtils.EMPTY);
        verifyTableRows(table);
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
        WebElement searchToolbar = findElement(window, By.id("search-toolbar"));
        assertNotNull(searchToolbar);
        WebElement prompt = findElement(searchToolbar, By.className("v-textfield-prompt"));
        assertNotNull(prompt);
        assertEquals("Enter Batch Name or Payment Date (MM/dd/yyyy)", prompt.getAttribute("value"));
        assertNotNull(findElement(searchToolbar, By.className("button-search")));
        assertNotNull(findElement(searchToolbar, By.className("button-clear")));
    }

    private void verifyTableRows(WebElement table) {
        List<WebElement> rows = findElements(findElement(table, By.className(V_TABLE_BODY_CLASS_NAME)),
            By.tagName(HTML_TR_TAG_NAME));
        assertEquals(3, rows.size());
        verifyUsageBatch(rows.get(0), "CADRA_11Dec16", "01/11/2017", "FY2017");
        verifyUsageBatch(rows.get(1), "AccessCopyright_11Dec16", "09/10/2015", "FY2016");
        verifyUsageBatch(rows.get(2), "JAACC_11Dec16", "08/16/2018", "FY2019");
    }

    private void verifyUsageBatch(WebElement row, String name, String paymentDate, String fiscalYear) {
        List<WebElement> cells = findElements(row, By.className(V_TABLE_CELL_CONTENT_CLASS_NAME));
        assertEquals(name, cells.get(0).getText());
        assertEquals(paymentDate, cells.get(1).getText());
        assertEquals(fiscalYear, cells.get(2).getText());
        assertNotNull(findElement(cells.get(3), By.className("Delete")));
    }

    private void verifyCloseButton(WebElement window) {
        WebElement closeButton = findElement(window, By.id("Close"));
        assertNotNull(closeButton);
        clickElementAndWait(closeButton);
        assertNull(findElement(By.id("delete-usage-batch")));
    }
}
