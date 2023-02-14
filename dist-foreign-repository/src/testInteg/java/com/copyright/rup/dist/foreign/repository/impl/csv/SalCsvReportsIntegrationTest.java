package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.ISalReportRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Integration test for {@link com.copyright.rup.dist.foreign.repository.impl.SalReportRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/02/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SalCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "sal-csv-reports-integration-test/";
    private static final String WRITE_AUDIT_CSV_REPORT = FOLDER_NAME + "write-audit-csv-report.groovy";
    private static final String WRITE_USAGES_CSV_REPORT = FOLDER_NAME + "write-usages-csv-report.groovy";
    private static final String WRITE_SCENARIO_USAGES_CSV_REPORT =
        FOLDER_NAME + "write-scenario-usages-csv-report.groovy";
    private static final String EMPTY_REPORT = "sal/audit_usages_report_empty.csv";
    
    @Autowired
    private ISalReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(SalCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-liabilities-by-rh-csv-report.groovy")
    public void testWriteLiabilitiesByRhCsvReport() throws IOException {
        List<Scenario> scenarios = List.of(
            buildScenario("5af9a0e6-4156-416d-b95b-f1aeeefa9545", "SAL Liabilities by Rightsholder report Scenario 1"),
            buildScenario("ebe447c1-5314-4075-9781-efc0887b6ffc", "SAL Liabilities by Rightsholder report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream),
            "sal/liabilities_by_rightsholder_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-liabilities-summary-by-rh-and-work-csv-report.groovy")
    public void testWriteLiabilitiesSummaryByRhAndWorkCsvReport() throws IOException {
        List<Scenario> scenarios = List.of(
            buildScenario("c0b30809-4a38-46cc-a0dc-641924d1fc43",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 1"),
            buildScenario("0a3b533d-d3ed-48dc-b256-f4f9f6527d91",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(
            scenarios, outputStream), "sal/liabilities_summary_by_rightsholder_and_work.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-undistributed-liabilities-csv-report.groovy")
    public void testWriteUndistributedLiabilitiesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeSalUndistributedLiabilitiesCsvReport(outputStream),
            "sal/undistributed_liabilities_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-fund-pools-csv-report.groovy")
    public void testWriteFundPoolsListCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalFundPoolsCsvReport(outputStream), "sal/fund_pools_list.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-fund-pools-csv-report.groovy")
    public void testWriteFundPoolsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalFundPoolsCsvReport(2018, outputStream), "sal/fund_pools_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-historical-item-bank-details-csv-report.groovy")
    public void testWriteHistoricalItemBankDetailsCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSalHistoricalItemBankDetailsReport(2000017003L,
            2019, 2020, outputStream), "sal/historical_item_bank.csv");
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter1 = new AuditFilter();
        filter1.setSearchValue(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter1, outputStream), EMPTY_REPORT);
        filter1.setSearchValue(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter1, outputStream), EMPTY_REPORT);
        AuditFilter filter2 = new AuditFilter();
        filter2.setCccEventId(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter2, outputStream), EMPTY_REPORT);
        filter2.setCccEventId(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter2, outputStream), EMPTY_REPORT);
        AuditFilter filter3 = new AuditFilter();
        filter3.setDistributionName(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter3, outputStream), EMPTY_REPORT);
        filter3.setDistributionName(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditSalCsvReport(filter3, outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Set.of("a375c049-1289-4c85-994b-b2bd8ac043cf"));
        auditFilter.setProductFamily("SAL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditSalCsvReport(auditFilter, outputStream),
            "sal/audit_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Set.of("da616e09-ca76-4815-b178-637abf32a76e"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeSalUsagesCsvReport(usageFilter, outputStream),
            "sal/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalUsagesCsvReport(new UsageFilter(), outputStream), "sal/usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_USAGES_CSV_REPORT)
    public void testWriteScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalScenarioUsagesCsvReport("d79c1cef-b764-49ca-ab54-812d84cca548",
                outputStream), "sal/scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_USAGES_CSV_REPORT)
    public void testWriteArchivedScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedSalScenarioUsagesCsvReport("5ba85c68-1f19-45c0-a6f3-a5c0e7737636",
                outputStream), "sal/archive_scenario_usages_report.csv");
    }

    private Scenario buildScenario(String scenarioId, String scenarioName) {
        Scenario scenario = new Scenario();
        scenario.setId(scenarioId);
        scenario.setName(scenarioName);
        return scenario;
    }
}
