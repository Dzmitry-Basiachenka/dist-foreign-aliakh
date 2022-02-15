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
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AaclCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "aacl-csv-reports-integration-test/";
    private static final String WRITE_AUDIT_CSV_REPORT = FOLDER_NAME + "write-audit-csv-report.groovy";
    private static final String WRITE_USAGES_FOR_CLASSIFICATION_AND_FIND_IDS =
        FOLDER_NAME + "write-usages-for-classification-and-find-ids.groovy";
    private static final String WRITE_USAGES_CSV_REPORT = FOLDER_NAME + "write-usages-csv-report.groovy";
    private static final String WRITE_SCENARIO_USAGES_CSV_REPORT =
        FOLDER_NAME + "write-scenario-usages-csv-report.groovy";
    private static final String WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT =
        FOLDER_NAME + "write-work-shares-by-agg-lc-class-csv-report.groovy";
    private static final String WRITE_BASELINE_USAGES_CSV_REPORT =
        FOLDER_NAME + "write-baseline-usages-csv-report.groovy";
    private static final String WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT =
        FOLDER_NAME + "write-exclude-details-by-payee-csv-report.groovy";
    private static final String EMPTY_REPORT = "aacl/audit_usages_report_empty.csv";
    private static final String AACL_SCENARIO_ID = "42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b";

    @Autowired
    private IReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AaclCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Collections.singleton("29689635-c6ff-483c-972d-09eb2febb9e0"));
        auditFilter.setProductFamily("AACL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditAaclCsvReport(auditFilter, outputStream),
            "aacl/audit_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditAaclCsvReport(new AuditFilter(), outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReportSearchBySqlLikePattern() throws Exception {
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
    @TestData(fileName = WRITE_USAGES_FOR_CLASSIFICATION_AND_FIND_IDS)
    public void testWriteUsagesForClassificationAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamily("AACL");
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsageBatchesIds(Collections.singleton("aed882d5-7625-4039-8781-a6676e11c579"));
        assertFiles(outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(usageFilter, outputStream),
            "aacl/usages_for_classification.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_FOR_CLASSIFICATION_AND_FIND_IDS)
    public void testWriteUsagesForClassificationAndFindIdsEmptyReport() throws IOException {
        assertFiles(
            outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(new UsageFilter(), outputStream),
            "aacl/usages_for_classification_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("600ad926-e7dd-4086-b283-87e6579395ce"));
        usageFilter.setProductFamily("AACL");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclUsagesCsvReport(usageFilter, outputStream),
            "aacl/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAaclUsagesCsvReport(new UsageFilter(), outputStream), "aacl/usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_USAGES_CSV_REPORT)
    public void testWriteScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAaclScenarioUsagesCsvReport("153b80ba-85e6-48ee-b5c3-c81664827e8a", outputStream),
            "aacl/scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_USAGES_CSV_REPORT)
    public void testWriteArchivedScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedAaclScenarioUsagesCsvReport("5429c31b-ffd1-4a7f-9b24-8c7809417fce",
                outputStream), "aacl/archive_scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "undistributed-liabilities-csv-report.groovy")
    public void testUndistributedLiabilitiesCsvReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream),
            "aacl/undistributed_liabilities_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteWorkSharesByAggLcClassSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.IN_PROGRESS, outputStream), "aacl/shares_by_agg_lc_class_summary_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteArchivedWorkSharesByAggLcClassSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "aacl/shares_by_agg_lc_class_summary_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteArchivedWorkSharesByAggLcClassSummaryCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.SENT_TO_LM, outputStream), "aacl/shares_by_agg_lc_class_summary_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteWorkSharesByAggLcClassCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.IN_PROGRESS, outputStream), "aacl/shares_by_agg_lc_class_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteArchivedWorkSharesByAggLcClassCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "aacl/shares_by_agg_lc_class_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WORK_SHARES_BY_ADD_LC_CLASS_CSV_REPORT)
    public void testWriteArchivedWorkSharesByAggLcClassCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(AACL_SCENARIO_ID,
            ScenarioStatusEnum.SENT_TO_LM, outputStream), "aacl/shares_by_agg_lc_class_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_BASELINE_USAGES_CSV_REPORT)
    public void testWriteBaselineUsagesCsvEmptyReport() throws Exception {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclBaselineUsagesCsvReport(0, outputStream),
            "aacl/baseline_usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_BASELINE_USAGES_CSV_REPORT)
    public void testWriteBaselineUsagesCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclBaselineUsagesCsvReport(2, outputStream),
            "aacl/baseline_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReportNoData() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("2373ef30-1952-4e78-890d-d6b3087de59c"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter,
            Collections.emptySet(), outputStream), "aacl/exclude_by_payee_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReportEmptyFilter() throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(
            new ExcludePayeeFilter(), Collections.emptySet(), outputStream),
            "aacl/exclude_by_payee_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReport() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("281ee7fa-7a94-48f9-9865-10ff69e94f07"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeAaclExcludeDetailsByPayeeCsvReport(filter,
            Collections.singleton(1000011881L), outputStream), "aacl/exclude_by_payee_report.csv");
    }
}
