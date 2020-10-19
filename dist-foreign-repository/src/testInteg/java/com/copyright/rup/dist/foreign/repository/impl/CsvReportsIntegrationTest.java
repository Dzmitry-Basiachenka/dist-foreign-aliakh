package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IReportRepository;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Integration tests for csv reports.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/21/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=csv-reports-test-data-init.groovy"})
@Transactional
public class CsvReportsIntegrationTest {

    private static final String SEARCH_WITH_SQL_1 = "%";
    private static final String SEARCH_WITH_SQL_2 = "_";
    private static final String AACL_SCENARIO_ID = "42ad575b-5d0d-4d82-b1c5-d0982f6f6f1b";
    private static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");

    @Autowired
    private IReportRepository reportRepository;

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(CsvReportsIntegrationTest.class);
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
    public void testWriteAuditFas2CsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("034873b3-97fa-475a-9a2a-191e8ec988b3", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        auditFilter.setProductFamily("FAS2");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(auditFilter, outputStream),
            "audit_usages_report_fas2.csv");
    }

    @Test
    public void testWriteAuditNtsCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(
            Sets.newHashSet("0c0a379a-461c-4e84-8062-326ece3c1f65", "f20ac1a3-eee4-4027-b5fb-def9adf0f871"));
        auditFilter.setProductFamily("NTS");
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditNtsCsvReport(auditFilter, outputStream),
            "audit_usages_report_nts.csv");
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
    public void testWriteAuditFasCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditFasReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditNtsCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditNtsReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditAaclCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditAaclReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditFasCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditFasReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditFasReport(filter);
    }

    @Test
    public void testWriteAuditNtsCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditNtsReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditNtsReport(filter);
    }

    @Test
    public void testWriteAuditAaclCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditAaclReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditAaclReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditAaclReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditAaclReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditAaclReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditAaclReport(filter);
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
    public void testWriteAaclUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("600ad926-e7dd-4086-b283-87e6579395ce"));
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
    public void testWriteNtsUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("f20ac1a3-eee4-4027-b5fb-def9adf0f871",
            "c0c07d51-2216-43c3-b61b-b904d86ec36a"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeNtsUsageCsvReport(usageFilter, outputStream),
            "usages_report_nts.csv");
    }

    @Test
    public void testWriteNtsUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsUsageCsvReport(new UsageFilter(), outputStream), "usages_report_nts_empty.csv");
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
    public void testWriteNtsScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeNtsScenarioUsagesCsvReport("85ba864e-1939-4a60-9fab-888b84199321",
                outputStream), "scenario_usages_report_nts.csv");
    }

    @Test
    public void testWriteArchivedNtsScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedNtsScenarioUsagesCsvReport("1ead8a3e-1231-43a5-a3c5-ed766abe5a2f",
                outputStream), "archive_scenario_usages_report_nts.csv");
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
    public void testExportScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeScenarioRightsholderTotalsCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_rightsholder_totals_report.csv");
    }

    @Test
    public void testAaclUndistributedLiabilitiesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeAaclUndistributedLiabilitiesCsvReport(outputStream),
            "aacl_undistributed_liabilities_report.csv");
    }

    @Test
    public void testExportArchivedScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            reportRepository.writeArchivedScenarioRightsholderTotalsCsvReport("ff47dee9-327a-4ff6-b170-d89f5190ccd8",
                outputStream), "archive_scenario_rightsholder_totals_report_fas.csv");
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
    public void testWriteArchivedWorkSharesByAggLcClassSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassSummaryCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "shares_by_agg_lc_class_summary_report.csv");
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
    public void testWriteArchivedWorkSharesByAggLcClassCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeWorkSharesByAggLcClassCsvReport(
            "3704ecc3-927b-4b30-a860-6c58e7654c5e", ScenarioStatusEnum.SENT_TO_LM, outputStream),
            "shares_by_agg_lc_class_report.csv");
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
    public void testWriteUsagesForClassificationAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamily("AACL");
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsageBatchesIds(Collections.singleton("aed882d5-7625-4039-8781-a6676e11c579"));
        assertFiles(outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(usageFilter, outputStream),
            "usages_for_classification.csv");
    }

    @Test
    public void testWriteUsagesForClassificationAndFindIdsEmptyReport() throws IOException {
        assertFiles(
            outputStream -> reportRepository.writeUsagesForClassificationAndFindIds(new UsageFilter(), outputStream),
            "usages_for_classification_empty.csv");
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
    public void testWriteResearchStatusCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeResearchStatusCsvReport(outputStream),
            "research_status_report.csv");
    }

    @Test
    public void testWriteSummaryMarketCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeSummaryMarketCsvReport(
            Arrays.asList("d016d9c2-5460-41bf-837c-8598cf00b651", "d016d9c2-5460-41bf-837c-8598cf00b652",
                "f1a40b56-54f1-4a46-90fa-77946c2f7805", "d016d9c2-5460-41bf-837c-8598cf00b658"), outputStream),
            "summary_of_market_report.csv");
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream ->
            reportRepository.writeFasBatchSummaryCsvReport(outputStream), "batch_summary_report_fas.csv");
    }

    @Test
    public void testWriteNtsWithdrawnBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream -> reportRepository.writeNtsWithdrawnBatchSummaryCsvReport(outputStream),
            "withdrawn_batch_summary_report_nts.csv");
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
    public void testWriteNtsServiceFeeTrueUpCsvReportWithFundPool() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("a537da01-b211-4b81-b2b9-7dc0c791811a");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report_nts_1.csv");
    }

    @Test
    public void testWriteNtsServiceFeeTrueUpCsvReportWithoutFundPool() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("dc6df4bd-7059-4975-8898-78b4a50d30b0");
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report_nts_2.csv");
    }

    @Test
    public void testWriteNtsServiceFeeTrueUpCsvEmptyReport() throws IOException {
        Scenario scenario = new Scenario();
        scenario.setId("1871799a-157a-4fb2-82ab-9092bb3b6395");
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        assertFiles(outputStream -> reportRepository.writeNtsServiceFeeTrueUpCsvReport(scenario, outputStream,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report_nts_empty.csv");
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
    public void testWriteWorkClassificationCsvReportWithBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeWorkClassificationCsvReport(
                    Collections.singleton("e17ebc80-e74e-436d-ba6e-acf3d355b7ff"), "987654321", outputStream),
            "work_classification_report_by_batch_ids.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithEmptyBatchIds() throws Exception {
        assertFilesWithExecutor(outputStream ->
                reportRepository.writeWorkClassificationCsvReport(Collections.singleton("not-existing-uid"),
                    null, outputStream),
            "work_classification_report_empty.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithSearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("987654321", outputStream),
            "work_classification_report_by_search.csv");
    }

    @Test
    public void testWriteWorkClassificationCsvReportWithEmptySearch() throws Exception {
        assertFilesWithExecutor(
            outputStream -> reportRepository.writeWorkClassificationCsvReport("99999999", outputStream),
            "work_classification_report_empty.csv");
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
    public void testWriteExcludeDetailsByPayeeCsvReportNoData() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("d13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeExcludeDetailsByPayeeCsvReport(filter,
            Collections.emptySet(), outputStream), "exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testWriteExcludeDetailsByPayeeCsvReportEmptyFilter() throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeExcludeDetailsByPayeeCsvReport(
            new ExcludePayeeFilter(), Collections.emptySet(), outputStream), "exclude_by_payee_report_empty.csv");
    }

    @Test
    public void testWriteExcludeDetailsByPayeeCsvReport() throws IOException {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton("e13ecc44-6795-4b75-90f0-4a3fc191f1b9"));
        assertFilesWithExecutor(outputStream -> reportRepository.writeExcludeDetailsByPayeeCsvReport(filter,
            Collections.singleton(7000813806L), outputStream), "exclude_by_payee_report.csv");
    }

    @Test
    public void testWriteNtsUndistributedLiabilitiesCsvReport() throws IOException {
        assertFiles(
            outputStream -> reportRepository.writeNtsUndistributedLiabilitiesCsvReport(DEFAULT_ESTIMATED_SERVICE_FEE,
                outputStream),
            "nts_undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteSalLiabilitiesByRhCsvReport() throws IOException {
        List<Scenario> scenarios = Arrays.asList(
            buildScenario("5af9a0e6-4156-416d-b95b-f1aeeefa9545", "SAL Liabilities by Rightsholder report Scenario 1"),
            buildScenario("ebe447c1-5314-4075-9781-efc0887b6ffc", "SAL Liabilities by Rightsholder report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesByRhCsvReport(scenarios, outputStream),
            "liabilities_by_rightsholder_report.csv");
    }

    @Test
    public void testWriteSalLiabilitiesSummaryByRhAndWorkReportCsvReport() throws IOException {
        List<Scenario> scenarios = Arrays.asList(
            buildScenario("c0b30809-4a38-46cc-a0dc-641924d1fc43",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 1"),
            buildScenario("0a3b533d-d3ed-48dc-b256-f4f9f6527d91",
                "SAL Liabilities Summary by Rightsholder and Work report Scenario 2"));
        assertFiles(outputStream -> reportRepository.writeSalLiabilitiesSummaryByRhAndWorkCsvReport(
            scenarios, outputStream), "liabilities_summary_by_rightsholder_and_work.csv");
    }

    private void assertFiles(Consumer<ByteArrayOutputStream> reportWriter, String fileName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportWriter.accept(outputStream);
        reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(outputStream.toByteArray()));
    }

    private void assertFilesWithExecutor(Consumer<PipedOutputStream> reportWriter, String fileName)
        throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream(outputStream);
        Future<?> writeReportFuture = executorService.submit(() -> reportWriter.accept(outputStream));
        Future<byte[]> readReportFuture = executorService.submit(() -> {
            try {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        byte[] actualReportBytes;
        try {
            writeReportFuture.get(10, TimeUnit.SECONDS);
            actualReportBytes = readReportFuture.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new AssertionError(e);
        }
        reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(actualReportBytes));
    }

    private void assertEmptyAuditFasReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(filter, outputStream),
            "audit_usages_report_empty.csv");
    }

    private void assertEmptyAuditNtsReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditFasCsvReport(filter, outputStream),
            "audit_usages_report_empty.csv");
    }

    private void assertEmptyAuditAaclReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> reportRepository.writeAuditAaclCsvReport(filter, outputStream),
            "audit_usages_report_aacl_empty.csv");
    }

    private Scenario buildScenario(String scenarioId, String scenarioName) {
        Scenario scenario = new Scenario();
        scenario.setId(scenarioId);
        scenario.setName(scenarioName);
        return scenario;
    }
}
