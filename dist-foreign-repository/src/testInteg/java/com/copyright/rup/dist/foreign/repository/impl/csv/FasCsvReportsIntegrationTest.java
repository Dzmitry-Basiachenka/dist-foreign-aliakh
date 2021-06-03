package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * Integration tests for csv reports for FAS product family.
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
@TestPropertySource(properties = {"test.liquibase.changelog=fas-csv-reports-test-data-init.groovy"})
@Transactional
public class FasCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String EMPTY_REPORT = "audit_usages_report_empty.csv";

    @Autowired
    private IReportRepository reportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(FasCsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteAuditFasCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Sets.newHashSet("e855bf85-236c-42e7-9b12-8d68dd747bbe",
            "02a09322-5f0f-4cae-888c-73127050dc98", "d016d9c2-5460-41bf-837c-8598cf00b654"));
        auditFilter.setProductFamily("FAS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_report_fas.csv");
    }

    @Test
    public void testExportArchivedScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedScenarioRightsholderTotalsCsvReport("ff47dee9-327a-4ff6-b170-d89f5190ccd8",
                outputStream), "archive_scenario_rightsholder_totals_report_fas.csv");
    }

    @Test
    public void testWriteAuditFas2CsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("034873b3-97fa-475a-9a2a-191e8ec988b3", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        auditFilter.setProductFamily("FAS2");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_report_fas2.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(usageFilter, outputStream),
            "usages_for_research.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIdsEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUsagesForResearchAndFindIds(new UsageFilter(), outputStream),
            "usages_for_research_empty.csv");
    }

    @Test
    public void testWriteResearchStatusCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeResearchStatusCsvReport(outputStream),
            "research_status_report.csv");
    }

    @Test
    public void testWriteAuditFasCsvReportEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeAuditFasCsvReport(new AuditFilter(), outputStream), EMPTY_REPORT);
    }

    @Test
    public void testWriteAuditFasCsvReportSearchBySqlLikePattern() throws Exception {
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
    public void testWriteFasUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("4c9cc089-b812-42cf-a5d2-1f5eda51fa76",
            "02a09322-5f0f-4cae-888c-73127050dc98"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasUsageCsvReport(usageFilter, outputStream),
            "usages_report_fas.csv");
    }

    @Test
    public void testWriteFasUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasUsageCsvReport(new UsageFilter(), outputStream), "usages_report_fas_empty.csv");
    }

    @Test
    public void testWriteFasScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeFasScenarioUsagesCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_usages_report_fas.csv");
    }

    @Test
    public void testWriteArchivedFasScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedFasScenarioUsagesCsvReport("ff47dee9-327a-4ff6-b170-d89f5190ccd8",
                outputStream), "archived_scenario_usages_report_fas.csv");
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream ->
            reportRepository.writeFasBatchSummaryCsvReport(outputStream), "batch_summary_report_fas.csv");
    }

    @Test
    public void testWriteFasServiceFeeTrueUpCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeFasServiceFeeTrueUpCsvReport(LocalDate.of(2012, 1, 1),
            LocalDate.of(2012, 3, 15), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report_fas.csv");
    }

    @Test
    public void testWriteFasServiceFeeTrueUpCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeFasServiceFeeTrueUpCsvReport(LocalDate.of(2013, 1, 1),
            LocalDate.of(2012, 1, 1), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report_fas_empty.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2011, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE, Sets.newHashSet("FAS", "FAS2")),
            "undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE, Sets.newHashSet("FAS", "FAS2")),
            "undistributed_liabilities_report_empty.csv");
    }

    @Test
    public void tesFastWriteExcludeDetailsByPayeeCsvReportNoData() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("d13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter,
            Collections.emptySet(), outputStream), "fas_exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testFasWriteExcludeDetailsByPayeeCsvReportEmptyFilter() throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(
            new ExcludePayeeFilter(), Collections.emptySet(), outputStream), "fas_exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testFasWriteExcludeDetailsByPayeeCsvReport() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("e13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeFasExcludeDetailsByPayeeCsvReport(filter,
            Collections.singleton(7000813806L), outputStream), "fas_exclude_by_payee_report.csv");
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvReport() throws Exception {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.APPROVED), outputStream),
            "ownership_adjustment_report.csv");
    }

    @Test
    public void testWriteOwnershipAdjustmentCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeOwnershipAdjustmentCsvReport(
            "3210b236-1239-4a60-9fab-888b84199321",
            Collections.singleton(RightsholderDiscrepancyStatusEnum.DRAFT), outputStream),
            "ownership_adjustment_report_empty.csv");
    }

    @Test
    public void testExportScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeScenarioRightsholderTotalsCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_rightsholder_totals_report.csv");
    }

    @Test
    public void testWriteSummaryMarketCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSummaryMarketCsvReport(
            Arrays.asList("d016d9c2-5460-41bf-837c-8598cf00b651", "d016d9c2-5460-41bf-837c-8598cf00b652",
                "f1a40b56-54f1-4a46-90fa-77946c2f7805", "d016d9c2-5460-41bf-837c-8598cf00b658"), outputStream),
            "summary_of_market_report.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithEmptySearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("99999999", outputStream),
            "work_classification_report_empty.csv");
    }
}
