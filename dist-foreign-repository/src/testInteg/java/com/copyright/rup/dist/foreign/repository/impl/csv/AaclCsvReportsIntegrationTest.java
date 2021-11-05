package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;

/**
 * Integration tests for csv reports for AACL product family.
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
//TODO: split test data into separate files for each test method
@TestData(fileName = "aacl-csv-reports-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class AaclCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String EMPTY_REPORT = "audit_usages_report_aacl_empty.csv";

    @Autowired
    private IReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AaclCsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteArchivedWorkSharesByAggLcClassSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "shares_by_agg_lc_class_summary_report.csv");
    }

    @Test
    public void testWriteArchivedWorkSharesByAggLcClassCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "shares_by_agg_lc_class_report.csv");
    }

    @Test
    public void testWriteAuditAaclCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Collections.singleton("29689635-c6ff-483c-972d-09eb2febb9e0"));
        auditFilter.setProductFamily("AACL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditAaclCsvReport(auditFilter, outputStream),
            "audit_usages_report_aacl.csv");
    }

    @Test
    public void testWriteAuditAaclCsvReportEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(new AuditFilter(), outputStream), EMPTY_REPORT);
    }

    @Test
    public void testWriteUsagesForClassificationAndFindIdsEmptyReport() throws IOException {
        assertFiles(
            outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(new UsageFilter(), outputStream),
            "usages_for_classification_empty.csv");
    }

    @Test
    public void testWriteAuditAaclCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter1 = new AuditFilter();
        filter1.setSearchValue(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter1, outputStream), EMPTY_REPORT);
        filter1.setSearchValue(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter1, outputStream), EMPTY_REPORT);
        AuditFilter filter2 = new AuditFilter();
        filter2.setCccEventId(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter2, outputStream), EMPTY_REPORT);
        filter2.setCccEventId(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter2, outputStream), EMPTY_REPORT);
        AuditFilter filter3 = new AuditFilter();
        filter3.setDistributionName(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter3, outputStream), EMPTY_REPORT);
        filter3.setDistributionName(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(filter3, outputStream), EMPTY_REPORT);
    }

    @Test
    public void testWriteAaclUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("600ad926-e7dd-4086-b283-87e6579395ce"));
        usageFilter.setProductFamily("AACL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclUsagesCsvReport(usageFilter, outputStream),
            "usages_report_aacl.csv");
    }

    @Test
    public void testWriteAaclUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAaclUsagesCsvReport(new UsageFilter(), outputStream), "usages_report_aacl_empty.csv");
    }

    @Test
    public void testWriteAaclScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAaclScenarioUsagesCsvReport("153b80ba-85e6-48ee-b5c3-c81664827e8a",
                outputStream), "scenario_usages_report_aacl.csv");
    }

    @Test
    public void testWriteArchivedAaclScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedAaclScenarioUsagesCsvReport("5429c31b-ffd1-4a7f-9b24-8c7809417fce",
                outputStream), "archive_scenario_usages_report_aacl.csv");
    }

    @Test
    public void testAaclUndistributedLiabilitiesCsvReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream),
            "aacl_undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteWorkSharesByAggLcClassSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.IN_PROGRESS, outputStream), "shares_by_agg_lc_class_summary_report.csv");
    }

    @Test
    public void testWriteArchivedWorkSharesByAggLcClassSummaryCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.SENT_TO_LM, outputStream), "shares_by_agg_lc_class_summary_empty_report.csv");
    }

    @Test
    public void testWriteWorkSharesByAggLcClassCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.IN_PROGRESS, outputStream), "shares_by_agg_lc_class_report.csv");
    }

    @Test
    public void testWriteArchivedWorkSharesByAggLcClassCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.SENT_TO_LM, outputStream), "shares_by_agg_lc_class_empty_report.csv");
    }

    @Test
    public void testWriteUsagesForClassificationAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamily("AACL");
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsageBatchesIds(Collections.singleton("aed882d5-7625-4039-8781-a6676e11c579"));
        assertFiles(outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(usageFilter, outputStream),
            "usages_for_classification.csv");
    }

    @Test
    public void testWriteAaclBaselineUsagesCsvEmptyReport() throws Exception {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclBaselineUsagesCsvReport(0, outputStream),
            "aacl_baseline_usages_report_empty.csv");
    }

    @Test
    public void testWriteAaclBaselineUsagesCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclBaselineUsagesCsvReport(2, outputStream),
            "aacl_baseline_usages_report.csv");
    }

    @Test
    public void testWriteAaclExcludeDetailsByPayeeCsvReportNoData() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("2373ef30-1952-4e78-890d-d6b3087de59c"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter,
            Collections.emptySet(), outputStream), "aacl_exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testWriteAaclExcludeDetailsByPayeeCsvReportEmptyFilter() throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(
            new ExcludePayeeFilter(), Collections.emptySet(), outputStream), "aacl_exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testWriteAaclExcludeDetailsByPayeeCsvReport() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter,
            Collections.singleton(1000011881L), outputStream), "aacl_exclude_by_payee_report.csv");
    }
}
