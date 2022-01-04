package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmProxyValueFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;

import com.google.common.collect.ImmutableSet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

/**
 * Integration tests for csv reports for UDM product family.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/05/21
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class UdmCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String DATA_INIT = "udm-csv-reports-test-data-init.groovy";

    @Autowired
    private IUdmReportRepository udmReportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UdmCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = "udm-csv-reports/udm-csv-reports-test-data-init-write-udm-proxy-value-csv-report.groovy")
    public void testWriteUdmProxyValueCsvReport() throws IOException {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(211512, 211006));
        filter.setPubTypeNames(ImmutableSet.of("NP", "OT"));
        assertFilesWithExecutor(outputStream -> udmReportRepository.writeUdmProxyValueCsvReport(filter, outputStream),
            "proxy_values_report.csv");
    }

    @Test
    @TestData(fileName = "udm-csv-reports/udm-csv-reports-test-data-init-write-udm-proxy-value-csv-report.groovy")
    public void testWriteUdmProxyValueEmptyCsvReport() throws IOException {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(211012, 209906));
        filter.setPubTypeNames(ImmutableSet.of("NP"));
        assertFilesWithExecutor(outputStream -> udmReportRepository.writeUdmProxyValueCsvReport(filter, outputStream),
            "proxy_values_empty_report.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageCsvReportSpecialistManager() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a23681ae-1cf7-44ee-b09b-6fc06779e05c"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManager(udmUsageFilter, outputStream),
            "usages_report_udm_specialist_manager.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageEmptyCsvReportSpecialistManager() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManager(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_specialist_manager.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageCsvReportResearcher() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("94b644cb-ab57-4825-b985-c51734a5aa1e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcher(udmUsageFilter, outputStream),
            "usages_report_udm_researcher.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageEmptyCsvReportResearcher() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcher(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_researcher.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageCsvReportView() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a524f8a9-2c95-43ea-8c25-b9f38b1c758e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportView(udmUsageFilter, outputStream),
            "usages_report_udm_view.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmUsageEmptyCsvReportView() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportView(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_view.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmBaselineUsageCsvReport() throws IOException {
        UdmBaselineFilter udmBaselineFilter = new UdmBaselineFilter();
        udmBaselineFilter.setPeriods(Collections.singleton(202106));
        udmBaselineFilter.setChannel(UdmChannelEnum.CCC);
        udmBaselineFilter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        udmBaselineFilter.setReportedTypeOfUses(Collections.singleton("EMAIL_COPY"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmBaselineUsageCsvReport(udmBaselineFilter, outputStream),
            "baseline_usages_report_udm.csv");
    }

    @Test
    //TODO: split test data into separate files for each test method
    @TestData(fileName = DATA_INIT)
    public void testWriteUdmBaselineUsageEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmBaselineUsageCsvReport(new UdmBaselineFilter(), outputStream),
            "baseline_usages_report_udm_empty.csv");
    }

    @Test
    @TestData(fileName = "udm-csv-reports/udm-csv-reports-test-data-init-write-udm-weekly-survey-csv-report.groovy")
    public void testWriteUdmWeeklySurveyCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setDateFrom(LocalDate.of(1900, 1, 1));
        reportFilter.setDateTo(LocalDate.of(2100, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmWeeklySurveyCsvReport(reportFilter, outputStream),
            "udm_weekly_survey_report.csv");
    }

    @Test
    @TestData(fileName = "udm-csv-reports/udm-csv-reports-test-data-init-write-udm-weekly-survey-csv-report.groovy")
    public void testWriteUdmWeeklySurveyCsvEmptyReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        reportFilter.setChannel(UdmChannelEnum.Rightsdirect);
        reportFilter.setPeriods(Collections.singleton(202112));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmWeeklySurveyCsvReport(reportFilter, outputStream),
            "udm_weekly_survey_empty_report.csv");
    }
}
