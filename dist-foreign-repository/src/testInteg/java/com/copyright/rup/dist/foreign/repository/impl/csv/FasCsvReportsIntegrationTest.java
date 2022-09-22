package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasReportRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * Integration test for {@link com.copyright.rup.dist.foreign.repository.impl.FasReportRepository}.
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
public class FasCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "fas-csv-reports-integration-test/";
    private static final String WRITE_AUDIT_CSV_REPORT = FOLDER_NAME + "write-audit-csv-report.groovy";
    private static final String WRITE_USAGES_FOR_RESEARCH_AND_FIND_IDS_CSV_REPORT =
        FOLDER_NAME + "write-usages-for-research-and-find-ids-csv-report.groovy";
    private static final String WRITE_USAGES_CSV_REPORT = FOLDER_NAME + "write-usages-csv-report.groovy";
    private static final String WRITE_BATCH_SUMMARY_CSV_REPORT = FOLDER_NAME + "write-batch-summary-csv-report.groovy";
    private static final String WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT =
        FOLDER_NAME + "write-service-fee-true-up-csv-report.groovy";
    private static final String WRITE_UNDISTRIBUTED_LIABILITIES_CSV_REPORT =
        FOLDER_NAME + "write-undistributed-liabilities-csv-report.groovy";
    private static final String WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT =
        FOLDER_NAME + "write-exclude-details-by-payee-csv-report.groovy";
    private static final String WRITE_OWNERSHIP_ADJUSTMENT_CSV_REPORT =
        FOLDER_NAME + "write-ownership-adjustment-csv-report.groovy";
    private static final String EMPTY_REPORT = "fas/audit_usages_report_empty.csv";

    @Autowired
    private IFasReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(FasCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Sets.newHashSet("e855bf85-236c-42e7-9b12-8d68dd747bbe",
            "02a09322-5f0f-4cae-888c-73127050dc98", "d016d9c2-5460-41bf-837c-8598cf00b654"));
        auditFilter.setProductFamily("FAS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "fas/audit_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(new AuditFilter(), outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter1 = new AuditFilter();
        filter1.setSearchValue(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter1, outputStream), EMPTY_REPORT);
        filter1.setSearchValue(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter1, outputStream), EMPTY_REPORT);
        AuditFilter filter2 = new AuditFilter();
        filter2.setCccEventId(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter2, outputStream), EMPTY_REPORT);
        filter2.setCccEventId(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter2, outputStream), EMPTY_REPORT);
        AuditFilter filter3 = new AuditFilter();
        filter3.setDistributionName(SEARCH_WITH_PERCENT);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter3, outputStream), EMPTY_REPORT);
        filter3.setDistributionName(SEARCH_WITH_UNDERSCORE);
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(filter3, outputStream), EMPTY_REPORT);
    }

    @Test
    @TestData(fileName = WRITE_AUDIT_CSV_REPORT)
    public void testWriteAuditFas2CsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("034873b3-97fa-475a-9a2a-191e8ec988b3", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        auditFilter.setProductFamily("FAS2");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "fas/audit_usages_report_fas2.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-archived-scenario-rightsholder-totals-csv-report.groovy")
    public void testWriteArchivedScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedScenarioRightsholderTotalsCsvReport("9adcbc00-6fcc-4f6d-842a-134581484ddf",
                outputStream), "fas/archive_scenario_rightsholder_totals_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_FOR_RESEARCH_AND_FIND_IDS_CSV_REPORT)
    public void testWriteUsagesForResearchAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(usageFilter, outputStream),
            "fas/usages_for_research.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_FOR_RESEARCH_AND_FIND_IDS_CSV_REPORT)
    public void testWriteUsagesForResearchAndFindIdsEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(new UsageFilter(), outputStream),
            "fas/usages_for_research_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_FOR_RESEARCH_AND_FIND_IDS_CSV_REPORT)
    public void testWriteResearchStatusCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeResearchStatusCsvReport(outputStream),
            "fas/research_status_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("4c9cc089-b812-42cf-a5d2-1f5eda51fa76",
            "02a09322-5f0f-4cae-888c-73127050dc98"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasUsageCsvReport(usageFilter, outputStream),
            "fas/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasUsageCsvReport(new UsageFilter(), outputStream), "fas/usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-scenario-usages-csv-report.groovy")
    public void testWriteScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasScenarioUsagesCsvReport("a72a3512-082e-46a0-b2b9-67763e30e7fc",
                outputStream), "fas/scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-archived-scenario-usages-csv-report.groovy")
    public void testWriteArchivedScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedFasScenarioUsagesCsvReport("ff47dee9-327a-4ff6-b170-d89f5190ccd8",
                outputStream), "fas/archived_scenario_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_BATCH_SUMMARY_CSV_REPORT)
    public void testWriteBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream ->
            reportRepository.writeFasBatchSummaryCsvReport(outputStream), "fas/batch_summary_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT)
    public void testWriteServiceFeeTrueUpCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeFasServiceFeeTrueUpCsvReport(LocalDate.of(2012, 1, 1),
            LocalDate.of(2012, 3, 15), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "fas/service_fee_true_up_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SERVICE_FEE_TRUE_UP_CSV_REPORT)
    public void testWriteServiceFeeTrueUpCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeFasServiceFeeTrueUpCsvReport(LocalDate.of(2013, 1, 1),
            LocalDate.of(2012, 1, 1), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "fas/service_fee_true_up_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_UNDISTRIBUTED_LIABILITIES_CSV_REPORT)
    public void testWriteUndistributedLiabilitiesCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2011, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE, Sets.newHashSet("FAS", "FAS2")),
            "fas/undistributed_liabilities_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_UNDISTRIBUTED_LIABILITIES_CSV_REPORT)
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE, Sets.newHashSet("FAS", "FAS2")),
            "fas/undistributed_liabilities_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReportNoData() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("d13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter,
            Collections.emptySet(), outputStream), "fas/exclude_by_payee_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReportEmptyFilter() throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(
            new ExcludePayeeFilter(), Collections.emptySet(), outputStream), "fas/exclude_by_payee_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_EXCLUDE_DETAILS_BY_PAYEE_CSV_REPORT)
    public void testWriteExcludeDetailsByPayeeCsvReport() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("e13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter,
            Collections.singleton(7000813806L), outputStream), "fas/exclude_by_payee_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_OWNERSHIP_ADJUSTMENT_CSV_REPORT)
    public void testWriteOwnershipAdjustmentCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.APPROVED), outputStream),
            "fas/ownership_adjustment_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_OWNERSHIP_ADJUSTMENT_CSV_REPORT)
    public void testWriteOwnershipAdjustmentCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.DRAFT), outputStream),
            "fas/ownership_adjustment_report_empty.csv");
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "write-scenario-rightsholder-totals-csv-report.groovy")
    public void testWriteScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeScenarioRightsholderTotalsCsvReport("0a8d8260-dc74-4b38-a096-89572a55c93f",
                outputStream), "fas/scenario_rightsholder_totals_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_BATCH_SUMMARY_CSV_REPORT)
    public void testWriteSummaryMarketCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSummaryMarketCsvReport(
            Arrays.asList("d016d9c2-5460-41bf-837c-8598cf00b651", "d016d9c2-5460-41bf-837c-8598cf00b652",
                "f1a40b56-54f1-4a46-90fa-77946c2f7805", "d016d9c2-5460-41bf-837c-8598cf00b658"), outputStream),
            "fas/summary_of_market_report.csv");
    }
}
