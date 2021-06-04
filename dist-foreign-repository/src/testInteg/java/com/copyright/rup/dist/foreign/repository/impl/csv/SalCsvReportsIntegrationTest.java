package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Integration tests for csv reports for SAL product family.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/02/21
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=sal-csv-reports-test-data-init.groovy"})
@Transactional
public class SalCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String EMPTY_REPORT = "audit_usages_report_sal_empty.csv";
    
    @Autowired
    private IReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(SalCsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteSalLiabilitiesByRhCsvReport() throws IOException {
        List<Scenario> scenarios = Arrays.asList(
            buildScenario("5af9a0e6-4156-416d-b95b-f1aeeefa9545", "SAL Liabilities by Rightsholder report Scenario 1"),
            buildScenario("ebe447c1-5314-4075-9781-efc0887b6ffc", "SAL Liabilities by Rightsholder report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream),
            "liabilities_by_rightsholder_report_sal.csv");
    }

    @Test
    public void testWriteSalLiabilitiesSummaryByRhAndWorkCsvReport() throws IOException {
        List<Scenario> scenarios = Arrays.asList(
            buildScenario("c0b30809-4a38-46cc-a0dc-641924d1fc43",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 1"),
            buildScenario("0a3b533d-d3ed-48dc-b256-f4f9f6527d91",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(
            scenarios, outputStream), "liabilities_summary_by_rightsholder_and_work_sal.csv");
    }

    @Test
    public void testSalUndistributedLiabilitiesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeSalUndistributedLiabilitiesCsvReport(outputStream),
            "sal_undistributed_liabilities_report.csv");
    }

    @Test
    public void testSalFundPoolsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalFundPoolsCsvReport(2018, outputStream), "sal_fund_pools_report.csv");
    }

    @Test
    public void testWriteSalHistoricalItemBankDetailsCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSalHistoricalItemBankDetailsReport(2000017003L,
            2019, 2020, outputStream), "sal_historical_item_bank.csv");
    }

    @Test
    public void testWriteAuditSalCsvReportSearchBySqlLikePattern() throws Exception {
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
    public void testWriteSalUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("da616e09-ca76-4815-b178-637abf32a76e"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeSalUsagesCsvReport(usageFilter, outputStream),
            "usages_report_sal.csv");
    }

    @Test
    public void testWriteSalUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalUsagesCsvReport(new UsageFilter(), outputStream), "usages_report_sal_empty.csv");
    }

    @Test
    public void testWriteSalScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeSalScenarioUsagesCsvReport("d79c1cef-b764-49ca-ab54-812d84cca548",
                outputStream), "scenario_usages_report_sal.csv");
    }

    @Test
    public void testWriteArchivedSalScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedSalScenarioUsagesCsvReport("5ba85c68-1f19-45c0-a6f3-a5c0e7737636",
                outputStream), "archive_scenario_usages_report_sal.csv");
    }

    @Test
    public void testWriteAuditSalCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Collections.singleton("a375c049-1289-4c85-994b-b2bd8ac043cf"));
        auditFilter.setProductFamily("SAL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditSalCsvReport(auditFilter, outputStream),
            "audit_usages_report_sal.csv");
    }

    private Scenario buildScenario(String scenarioId, String scenarioName) {
        Scenario scenario = new Scenario();
        scenario.setId(scenarioId);
        scenario.setName(scenarioName);
        return scenario;
    }
}
