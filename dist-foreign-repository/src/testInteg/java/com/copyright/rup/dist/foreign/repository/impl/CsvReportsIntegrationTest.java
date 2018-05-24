package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

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
import java.time.LocalDate;
import java.util.Collections;
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

    @Autowired
    private IUsageRepository usageRepository;

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory();
    }

    @Test
    public void testWriteAuditCsvReportCsvReport() throws Exception {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setProductFamilies(Collections.singleton("FAS"));
        assertFilesWithExecutor(outputStream -> usageRepository.writeAuditCsvReport(auditFilter, outputStream),
            "audit_usages_report.csv");
    }

    @Test
    public void testWriteAuditCsvReportEmptyCsvReport() throws Exception {
        assertFilesWithExecutor(outputStream -> usageRepository.writeAuditCsvReport(new AuditFilter(), outputStream),
            "audit_usages_report_empty.csv");
    }

    @Test
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamilies(Collections.singleton("CLA_FAS"));
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
            outputStream), "undistributed_liabilities_report.csv");
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        assertFiles(outputStream -> usageRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5),
            outputStream), "undistributed_liabilities_report_empty.csv");
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
}
