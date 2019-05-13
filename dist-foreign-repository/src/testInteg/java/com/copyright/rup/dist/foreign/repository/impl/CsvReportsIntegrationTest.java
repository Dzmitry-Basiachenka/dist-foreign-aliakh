package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.Executors;
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
@TransactionConfiguration
@Transactional
public class CsvReportsIntegrationTest {

    private static final String SEARCH_WITH_SQL_1 = "%";
    private static final String SEARCH_WITH_SQL_2 = "_";
    private static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");

    @Autowired
    private IUsageRepository usageRepository;

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(CsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteAuditCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Sets.newHashSet("e855bf85-236c-42e7-9b12-8d68dd747bbe",
            "034873b3-97fa-475a-9a2a-191e8ec988b3", "02a09322-5f0f-4cae-888c-73127050dc98",
            "d016d9c2-5460-41bf-837c-8598cf00b654", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        assertFilesWithExecutor(outputStream -> usageRepository.writeAuditCsvReport(auditFilter, outputStream),
            "audit_usages_report.csv");
    }

    @Test
    public void testWriteAuditCsvReportForPostDistribution() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setSearchValue("75693c90-d6f5-401a-8c26-134adc9745c5");
        assertFilesWithExecutor(outputStream -> usageRepository.writeAuditCsvReport(auditFilter, outputStream),
            "audit_usages_report_post_distribution.csv");
    }

    @Test
    public void testWriteAuditCsvReportEmptyCsvReport() throws Exception {
        assertEmptyAuditReport(new AuditFilter());
    }

    @Test
    public void testWriteAuditCsvReportSearchBySqlLikePattern() throws Exception {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(SEARCH_WITH_SQL_1);
        assertEmptyAuditReport(filter);
        filter.setSearchValue(SEARCH_WITH_SQL_2);
        assertEmptyAuditReport(filter);
        filter = new AuditFilter();
        filter.setCccEventId(SEARCH_WITH_SQL_1);
        assertEmptyAuditReport(filter);
        filter.setCccEventId(SEARCH_WITH_SQL_2);
        assertEmptyAuditReport(filter);
        filter = new AuditFilter();
        filter.setDistributionName(SEARCH_WITH_SQL_1);
        assertEmptyAuditReport(filter);
        filter.setDistributionName(SEARCH_WITH_SQL_2);
        assertEmptyAuditReport(filter);
    }

    @Test
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Sets.newHashSet("e855bf85-236c-42e7-9b12-8d68dd747bbe",
            "034873b3-97fa-475a-9a2a-191e8ec988b3", "02a09322-5f0f-4cae-888c-73127050dc98",
            "d016d9c2-5460-41bf-837c-8598cf00b654", "acae006c-a4fe-45f0-a0cc-098e12db00c5"));
        assertFilesWithExecutor(outputStream -> usageRepository.writeUsagesCsvReport(usageFilter, outputStream),
            "usages_report.csv");
    }

    @Test
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream -> usageRepository.writeUsagesCsvReport(new UsageFilter(), outputStream),
            "usages_report_empty.csv");
    }

    @Test
    public void testExportScenarioUsagesCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            usageRepository.writeScenarioUsagesCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_usages_report.csv");
    }

    @Test
    public void testExportScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            usageRepository.writeScenarioRightsholderTotalsCsvReport("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e",
                outputStream), "scenario_rightsholder_totals_report.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIds() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertFiles(outputStream -> usageRepository.writeUsagesForResearchAndFindIds(usageFilter, outputStream),
            "usages_for_research.csv");
    }

    @Test
    public void testWriteUsagesForResearchAndFindIdsEmptyReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeUsagesForResearchAndFindIds(new UsageFilter(), outputStream),
            "usages_for_research_empty.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() throws Exception {
        assertFiles(outputStream -> usageRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2011, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE), "undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5),
            outputStream, DEFAULT_ESTIMATED_SERVICE_FEE), "undistributed_liabilities_report_empty.csv");
    }

    @Test
    public void testWriteResearchStatusCsvReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeResearchStatusCsvReport(outputStream),
            "research_status_report.csv");
    }

    @Test
    public void testWriteSummaryMarketCsvReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeSummaryMarketCsvReport(
            Arrays.asList("d016d9c2-5460-41bf-837c-8598cf00b651", "d016d9c2-5460-41bf-837c-8598cf00b652",
                "f1a40b56-54f1-4a46-90fa-77946c2f7805", "d016d9c2-5460-41bf-837c-8598cf00b658"), outputStream),
            "summary_of_market_report.csv");
    }

    @Test
    public void testWriteFasBatchSummaryCsvReport() throws IOException {
        assertFiles(outputStream ->
            usageRepository.writeFasBatchSummaryCsvReport(outputStream), "fas_batch_summary_report.csv");
    }

    @Test
    public void testWriteServiceFeeTrueUpCsvReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeServiceFeeTrueUpCsvReport(LocalDate.of(2012, 1, 1),
            LocalDate.of(2012, 3, 15), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_report.csv");
    }

    @Test
    public void testWriteServiceFeeTrueUpCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeServiceFeeTrueUpCsvReport(LocalDate.of(2013, 1, 1),
            LocalDate.of(2012, 1, 1), LocalDate.of(2014, 5, 5), outputStream, 2000017000L,
            DEFAULT_ESTIMATED_SERVICE_FEE), "service_fee_true_up_empty_report.csv");
    }

    private void assertFiles(Consumer<ByteArrayOutputStream> reportWriter, String fileName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportWriter.accept(outputStream);
        reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(outputStream.toByteArray()));
    }

    private void assertFilesWithExecutor(Consumer<PipedOutputStream> reportWriter, String fileName)
        throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        Executors.newSingleThreadExecutor().execute(() -> reportWriter.accept(outputStream));
        reportTestUtils.assertCsvReport(fileName, new PipedInputStream(outputStream));
    }

    private void assertEmptyAuditReport(AuditFilter filter) throws IOException {
        assertFilesWithExecutor(outputStream -> usageRepository.writeAuditCsvReport(filter, outputStream),
            "audit_usages_report_empty.csv");
    }
}
