package com.copyright.rup.dist.foreign.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * UI test to verify 'Delete Usage Batch' functionality.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 3/1/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-test-context.xml")
public class DeleteUsageBatchUiTest extends ForeignCommonUiTest {

    private static final String CLOSE_BUTTON_ID = "Close";
    private static final String USAGE_BATCHES_TABLE_ID = "usage-batches-table";
    private static final String BATCH_TO_DELETE_ID = "4b67f17c-3c32-4b55-b2a0-dabebb513304";
    private UsageBatchInfo usageBatch1 = new UsageBatchInfo("56282dbc-2468-48d4-b926-93d3458a656a", "CADRA_11Dec16",
        "01/11/2017", "FY2017",
        "7000813806 - CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
    private UsageBatchInfo usageBatch2 = new UsageBatchInfo("56282dbc-2468-48d4-b926-94d3458a666a",
        "AccessCopyright_11Dec16", "09/10/2015", "FY2016",
        "2000017004 - Access Copyright, The Canadian Copyright Agency");
    private UsageBatchInfo usageBatch3 = new UsageBatchInfo("56782dbc-2158-48d4-b026-94d3458a666a", "JAACC_11Dec16",
        "08/16/2018", "FY2019", "7001440663 - JAACC, Japan Academic Association for Copyright Clearance [T]");
    private UsageBatchInfo usageBatch4 = new UsageBatchInfo(BATCH_TO_DELETE_ID, "Batch to delete",
        "07/11/2017", "FY2018", "1000002797 - British Film Institute (BFI)");
    private UsageBatchInfo usageBatchWithNewUsages = new UsageBatchInfo("5fb4a015-f943-4e29-ae7b-c7301db3c15e",
        "Batch with NEW usages", "01/11/2017", "FY2017", "1000005413 - Kluwer Academic Publishers - Dordrecht");

    @Autowired
    private IUsageAuditRepository usageAuditRepository;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageBatchRepository usageBatchRepository;
    private UsageBatch usageBatchToDelete;

    @After
    public void tearDown() {
        if (null != usageBatchToDelete) {
            usageBatchRepository.deleteUsageBatch(usageBatchToDelete.getId());
            usageRepository.deleteUsages(usageBatchToDelete.getId());
            usageBatchToDelete = null;
        }
    }

    @Test
    // Test cases IDs: 'fa7466eb-7d5d-4068-877a-835522f1deeb', '18eb492e-5ec1-4c87-ac1c-be9ed9facbdb',
    // '7ffd686f-5080-4d99-bf68-fd4576c58508'
    public void testDeleteUsageBatchModalWindow() {
        loginAsSpecialist();
        WebElement window = openDeleteUsageBatchWindow(selectUsagesTab());
        verifyDeleteUsageBatchWindow(window);
    }

    @Test
    // Test cases IDs: 'b598bf3f-3068-41b8-a5d9-63876b9f2b62', '1c2e8f69-02e7-4477-83c8-24dc69f9cd1e'
    public void testSearch() {
        loginAsSpecialist();
        WebElement window = openDeleteUsageBatchWindow(selectUsagesTab());
        WebElement searchToolbar = assertSearchToolbar(window, "Enter Batch Name or Payment Date (MM/dd/yyyy)");
        WebElement searchField = assertWebElement(searchToolbar, By.className("v-textfield"));
        WebElement table = assertWebElement(window, USAGE_BATCHES_TABLE_ID);
        WebElement searchButton = assertWebElement(searchToolbar, By.className("button-search"));
        verifySearchByBatchName(searchField, searchButton, table);
        verifySearchByPaymentDate(searchField, searchButton, table);
    }

    @Test
    // Test cases IDs: '251507d4-af2d-4f5f-a4d0-772b70804f83', '0de548a6-6e56-4141-95fb-71931e7f1c7d',
    // 'af0b68e7-236a-4f21-8691-a6c6192d153c'
    public void testDeleteUsageBatchWithApproval() {
        usageBatchToDelete = buildUsageBatch();
        usageBatchRepository.insert(usageBatchToDelete);
        usageRepository.insert(buildUsage());
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        WebElement filterWidget = assertWebElement(By.id("usages-filter-widget"));
        applyFilters(filterWidget, usageBatch4);
        WebElement usagesTable = assertWebElement(usagesTab, "usages-table");
        assertTableRowElements(usagesTable, 1);
        WebElement window = openDeleteUsageBatchWindow(usagesTab);
        WebElement usageBatchesTable = assertWebElement(window, By.id(USAGE_BATCHES_TABLE_ID));
        verifyTableRows(usageBatchesTable, usageBatch4, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickElementAndWait(assertWebElement(usageBatchesTable, BATCH_TO_DELETE_ID));
        WebElement confirmDialog = assertWebElement(By.id("confirm-dialog-window"));
        assertWebElementText(confirmDialog, "Are you sure you want to delete 'Batch to delete' usage batch?");
        clickButtonAndWait(confirmDialog, "Yes");
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickButtonAndWait(window, CLOSE_BUTTON_ID);
        assertUsagesFilterEmpty(filterWidget);
        assertNullFilterItem(filterWidget, "batches-filter", "batches-filter-window", usageBatch4.getName());
        assertNullFilterItem(filterWidget, "rightsholders-filter", "rightsholders-filter-window", usageBatch4.getRro());
        verifyFiscalYearFilter(filterWidget, StringUtils.SPACE, "2016", "2017", "2019");
        assertEquals(0, usageBatchRepository.findCountByName(usageBatch4.getName()));
        usageBatchToDelete = null;
        verifyUsageAuditForNotDeletedBatches(usageBatch1.getId(), usageBatch2.getId(), usageBatch3.getId());
        verifyUsageAuditForDeletedBatches(usageBatch4.getId());
    }

    @Test
    // Test case ID: '259c2da3-46e5-4493-942b-3ae47cae7f94'
    public void testDeleteUsageBatchWithoutApproval() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        applyFilters(assertWebElement(By.id("usages-filter-widget")), usageBatch1);
        WebElement usagesTable = assertWebElement(usagesTab, "usages-table");
        assertTableRowElements(usagesTable, 1);
        WebElement window = openDeleteUsageBatchWindow(usagesTab);
        WebElement usageBatchesTable = assertWebElement(window, By.id(USAGE_BATCHES_TABLE_ID));
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickElementAndWait(assertWebElement(usageBatchesTable, "56282dbc-2468-48d4-b926-93d3458a656a"));
        WebElement confirmDialog = assertWebElement(By.id("confirm-dialog-window"));
        assertWebElementText(confirmDialog, "Are you sure you want to delete 'CADRA_11Dec16' usage batch?");
        clickButtonAndWait(confirmDialog, "Cancel");
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickButtonAndWait(window, CLOSE_BUTTON_ID);
        assertTableRowElements(usagesTable, 1);
        assertEquals(1, usageBatchRepository.findCountByName(usageBatch1.getName()));
        verifyUsageAuditForNotDeletedBatches(usageBatch1.getId(), usageBatch2.getId(), usageBatch3.getId());
    }

    @Test
    // Test cases IDs: 'd79eba61-99c1-4e8b-bda8-b099facfb8d4', 'c32b46ce-6676-4526-af2f-65deb637a346'
    public void testDeleteUsageBatchAssociatedWithScenarios() {
        loginAsSpecialist();
        WebElement usagesTab = selectUsagesTab();
        applyFilters(assertWebElement(By.id("usages-filter-widget")), usageBatch1);
        WebElement usagesTable = assertWebElement(usagesTab, "usages-table");
        assertTableRowElements(usagesTable, 1);
        WebElement window = openDeleteUsageBatchWindow(usagesTab);
        WebElement usageBatchesTable = assertWebElement(window, By.id(USAGE_BATCHES_TABLE_ID));
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickElementAndWait(assertWebElement(usageBatchesTable, "56282dbc-2468-48d4-b926-94d3458a666a"));
        WebElement notificationWindow = assertWebElement(By.id("notification-window"));
        assertWebElementText(notificationWindow,
            "Usage batch cannot be deleted because it is associated with the following scenarios:\nScenario name");
        clickButtonAndWait(notificationWindow, "Ok");
        verifyTableRows(usageBatchesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        clickButtonAndWait(window, CLOSE_BUTTON_ID);
        assertTableRowElements(usagesTable, 1);
        assertEquals(1, usageBatchRepository.findCountByName(usageBatch1.getName()));
        verifyUsageAuditForNotDeletedBatches(usageBatch1.getId(), usageBatch2.getId(), usageBatch3.getId());
    }

    private void verifyUsageAuditForNotDeletedBatches(String... batchIds) {
        getUsageIds(batchIds).forEach(usageId ->
            assertTrue(CollectionUtils.isNotEmpty(usageAuditRepository.findByUsageId(usageId))));
    }

    private void verifyUsageAuditForDeletedBatches(String... batchIds) {
        getUsageIds(batchIds).forEach(usageId ->
            assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(usageId))));
    }

    private List<String> getUsageIds(String... batchIds) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Arrays.stream(batchIds).collect(Collectors.toSet()));
        return usageRepository.findByFilter(usageFilter, new Pageable(0, 200), null).stream()
            .map(UsageDto::getId)
            .collect(Collectors.toList());
    }

    private void verifyFiscalYearFilter(WebElement filterWidget, String... expectedItems) {
        WebElement fiscalYearFilter = assertWebElement(filterWidget, "fiscal-year-filter");
        assertComboboxElement(fiscalYearFilter, expectedItems);
    }

    private void assertNullFilterItem(WebElement filterWidget, String filterId, String filterWindowId, String item) {
        WebElement itemsFilter = assertWebElement(filterWidget, filterId);
        clickElementAndWait(itemsFilter);
        WebElement filterWindow = assertWebElement(By.id(filterWindowId));
        assertNull(findElementByText(filterWindow, HTML_LABEL_TAG_NAME, item));
        clickButtonAndWait(filterWindow, CLOSE_BUTTON_ID);
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
        verifyTableRows(table, usageBatch1, usageBatchWithNewUsages);
        applySearch(searchField, searchButton, "01/11/2020");
        verifyTableRows(table);
        applySearch(searchField, searchButton, "09/10/2015");
        verifyTableRows(table, usageBatch2);
    }

    private WebElement openDeleteUsageBatchWindow(WebElement usagesTab) {
        clickButtonAndWait(usagesTab, "Delete_Usage_Batch");
        return assertWebElement(By.id("delete-usage-batch"));
    }

    private void verifyDeleteUsageBatchWindow(WebElement deleteUsageBatchWindow) {
        assertEquals("Delete Usage Batch", getWindowCaption(deleteUsageBatchWindow));
        assertSearchToolbar(deleteUsageBatchWindow, "Enter Batch Name or Payment Date (MM/dd/yyyy)");
        WebElement usagesTable = assertWebElement(deleteUsageBatchWindow, By.id(USAGE_BATCHES_TABLE_ID));
        assertTableHeaderElements(usagesTable, "Usage Batch Name", "Payment Date", "Fiscal Year", StringUtils.EMPTY);
        assertTableRowElements(usagesTable, 4);
        verifyTableRows(usagesTable, usageBatch1, usageBatchWithNewUsages, usageBatch2, usageBatch3);
        assertTableSorting(usagesTable, "name", "createDate", "fiscalYear");
        clickButtonAndWait(deleteUsageBatchWindow, CLOSE_BUTTON_ID);
        assertNull(waitAndFindElement(By.id("delete-usage-batch")));
    }

    private void verifyTableRows(WebElement table, UsageBatchInfo... usageBatches) {
        List<WebElement> rowElements = assertTableRowElements(table, usageBatches.length);
        IntStream.range(0, usageBatches.length)
            .forEach(i -> assertTableRowElements(rowElements.get(i),
                usageBatches[i].getName(),
                usageBatches[i].getPaymentDate(),
                usageBatches[i].getFiscalYear(),
                "Delete"));
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_TO_DELETE_ID);
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
        usage.setBatchId(BATCH_TO_DELETE_ID);
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
}
