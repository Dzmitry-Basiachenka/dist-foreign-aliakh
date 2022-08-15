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
public class UdmCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "udm-csv-reports-integration-test/";
    private static final String WRITE_PROXY_VALUE_CSV_REPORT = FOLDER_NAME + "write-proxy-value-csv-report.groovy";
    private static final String WRITE_USAGE_CSV_REPORT = FOLDER_NAME + "write-usage-csv-report.groovy";
    private static final String WRITE_BASELINE_USAGE_CSV_REPORT =
        FOLDER_NAME + "write-baseline-usage-csv-report.groovy";
    private static final String WRITE_WEEKLY_SURVEY_CSV_REPORT = FOLDER_NAME + "write-weekly-survey-csv-report.groovy";
    private static final String WRITE_SURVEY_LICENSEE_CSV_REPORT =
        FOLDER_NAME + "write-survey-licensee-csv-report.groovy";
    private static final String WRITE_VERIFIED_DETAILS_BY_SOURCE_CSV_REPORT =
        FOLDER_NAME + "write-verified-details-by-source-csv-report.groovy";
    private static final String WRITE_COMPLETED_ASSIGNMENTS_CSV_REPORT =
        FOLDER_NAME + "write-completed-assignments-csv-report.groovy";
    private static final String WRITE_USAGE_EDITS_IN_BASELINE_CSV_REPORT =
        FOLDER_NAME + "write-usage-edits-in-baseline-csv-report.groovy";
    private static final String WRITE_USABLE_DETAILS_BY_COUNTRY_CSV_REPORT =
        FOLDER_NAME + "write-usable-details-by-country-csv-report.groovy";
    private static final String WRITE_VALUES_BY_STATUS_CSV_REPORT =
        FOLDER_NAME + "write-values-by-status-csv-report.groovy";
    private static final String WRITE_USAGES_BY_STATUS_CSV_REPORT =
        FOLDER_NAME + "write-usages-by-status-csv-report.groovy";

    @Autowired
    private IUdmReportRepository udmReportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UdmCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_PROXY_VALUE_CSV_REPORT)
    public void testWriteProxyValueCsvReport() throws IOException {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(211512, 211006));
        filter.setPubTypeNames(ImmutableSet.of("BK", "NP", "OT"));
        assertFilesWithExecutor(outputStream -> udmReportRepository.writeUdmProxyValueCsvReport(filter, outputStream),
            "udm/proxy_values_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_PROXY_VALUE_CSV_REPORT)
    public void testWriteProxyValueEmptyCsvReport() throws IOException {
        UdmProxyValueFilter filter = new UdmProxyValueFilter();
        filter.setPeriods(ImmutableSet.of(211012, 209906));
        filter.setPubTypeNames(ImmutableSet.of("NP"));
        assertFilesWithExecutor(outputStream -> udmReportRepository.writeUdmProxyValueCsvReport(filter, outputStream),
            "udm/proxy_values_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageCsvReportSpecialistManager() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a23681ae-1cf7-44ee-b09b-6fc06779e05c"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManager(udmUsageFilter, outputStream),
            "udm/usages_report_specialist_manager.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageEmptyCsvReportSpecialistManager() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManager(new UdmUsageFilter(), outputStream),
            "udm/usages_report_empty_specialist_manager.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageCsvReportResearcher() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("94b644cb-ab57-4825-b985-c51734a5aa1e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcher(udmUsageFilter, outputStream),
            "udm/usages_report_researcher.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageEmptyCsvReportResearcher() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcher(new UdmUsageFilter(), outputStream),
            "udm/usages_report_empty_researcher.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageCsvReportView() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a524f8a9-2c95-43ea-8c25-b9f38b1c758e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportView(udmUsageFilter, outputStream),
            "udm/usages_report_view.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageEmptyCsvReportView() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportView(new UdmUsageFilter(), outputStream),
            "udm/usages_report_empty_view.csv");
    }

    @Test
    @TestData(fileName = WRITE_BASELINE_USAGE_CSV_REPORT)
    public void testWriteBaselineUsageCsvReport() throws IOException {
        UdmBaselineFilter udmBaselineFilter = new UdmBaselineFilter();
        udmBaselineFilter.setPeriods(Collections.singleton(202106));
        udmBaselineFilter.setChannel(UdmChannelEnum.CCC);
        udmBaselineFilter.setUdmUsageOrigin(UdmUsageOriginEnum.SS);
        udmBaselineFilter.setReportedTypeOfUses(Collections.singleton("EMAIL_COPY"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmBaselineUsageCsvReport(udmBaselineFilter, outputStream),
            "udm/baseline_usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_BASELINE_USAGE_CSV_REPORT)
    public void testWriteBaselineUsageEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmBaselineUsageCsvReport(new UdmBaselineFilter(), outputStream),
            "udm/baseline_usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_WEEKLY_SURVEY_CSV_REPORT)
    public void testWriteWeeklySurveyCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(ImmutableSet.of(202006, 202112, 202206));
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.SS);
        reportFilter.setChannel(UdmChannelEnum.CCC);
        reportFilter.setDateFrom(LocalDate.of(2020, 2, 1));
        reportFilter.setDateTo(LocalDate.of(2023, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmWeeklySurveyCsvReport(reportFilter, outputStream),
            "udm/weekly_survey_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_WEEKLY_SURVEY_CSV_REPORT)
    public void testWriteWeeklySurveyCsvEmptyReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        reportFilter.setChannel(UdmChannelEnum.Rightsdirect);
        reportFilter.setPeriods(Collections.singleton(202112));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmWeeklySurveyCsvReport(reportFilter, outputStream),
            "udm/weekly_survey_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SURVEY_LICENSEE_CSV_REPORT)
    public void testWriteSurveyLicenseeCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202206));
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        reportFilter.setChannel(UdmChannelEnum.CCC);
        reportFilter.setDateFrom(LocalDate.of(2022, 3, 1));
        reportFilter.setDateTo(LocalDate.of(2023, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmSurveyLicenseeCsvReport(reportFilter, outputStream),
            "udm/survey_licensee_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SURVEY_LICENSEE_CSV_REPORT)
    public void testWriteSurveyLicenseeEmptyCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.RFA);
        reportFilter.setChannel(UdmChannelEnum.Rightsdirect);
        reportFilter.setPeriods(Collections.singleton(202112));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmSurveyLicenseeCsvReport(reportFilter, outputStream),
            "udm/survey_licensee_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_VERIFIED_DETAILS_BY_SOURCE_CSV_REPORT)
    public void testVerifiedDetailsBySourceReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202006));
        reportFilter.setChannel(UdmChannelEnum.CCC);
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.SS);
        reportFilter.setDateFrom(LocalDate.of(2020, 2, 1));
        reportFilter.setDateTo(LocalDate.of(2023, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmVerifiedDetailsBySourceReport(reportFilter, outputStream),
            "udm/verified_details_by_source_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_VERIFIED_DETAILS_BY_SOURCE_CSV_REPORT)
    public void testVerifiedDetailsBySourceReportByPeriod() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202006));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmVerifiedDetailsBySourceReport(reportFilter, outputStream),
            "udm/verified_details_by_source_report_by_period.csv");
    }

    @Test
    @TestData(fileName = WRITE_VERIFIED_DETAILS_BY_SOURCE_CSV_REPORT)
    public void testVerifiedDetailsBySourceEmptyReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setChannel(UdmChannelEnum.CCC);
        reportFilter.setUsageOrigin(UdmUsageOriginEnum.SS);
        reportFilter.setDateFrom(LocalDate.of(2018, 2, 1));
        reportFilter.setDateTo(LocalDate.of(2019, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmVerifiedDetailsBySourceReport(reportFilter, outputStream),
            "udm/verified_details_by_source_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_COMPLETED_ASSIGNMENTS_CSV_REPORT)
    public void testCompletedAssignmentsReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202006));
        reportFilter.setUserNames(Sets.newHashSet("ajohn@copyright.com", "jjohn@copyright.com", "wjohn@copyright.com"));
        reportFilter.setDateFrom(LocalDate.of(2020, 7, 1));
        reportFilter.setDateTo(LocalDate.of(2020, 8, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmCompletedAssignmentsCsvReport(reportFilter, outputStream),
            "udm/completed_assignments_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_COMPLETED_ASSIGNMENTS_CSV_REPORT)
    public void testCompletedAssignmentsEmptyReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202112));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmCompletedAssignmentsCsvReport(reportFilter, outputStream),
            "udm/completed_assignments_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_EDITS_IN_BASELINE_CSV_REPORT)
    public void testWriteUsageEditsInBaselineCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202106));
        reportFilter.setDateFrom(LocalDate.of(2020, 4, 15));
        reportFilter.setDateTo(LocalDate.of(2020, 4, 16));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsageEditsInBaselineCsvReport(reportFilter, outputStream),
            "udm/usage_edits_in_baseline_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_EDITS_IN_BASELINE_CSV_REPORT)
    public void testWriteUsageEditsInBaselineEmptyCsvReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202106));
        reportFilter.setDateFrom(LocalDate.of(2020, 4, 16));
        reportFilter.setDateTo(LocalDate.of(2020, 4, 17));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsageEditsInBaselineCsvReport(reportFilter, outputStream),
            "udm/usage_edits_in_baseline_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USABLE_DETAILS_BY_COUNTRY_CSV_REPORT)
    public void testUsableDetailsByCountryReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(ImmutableSet.of(202006, 202106));
        reportFilter.setDateFrom(LocalDate.of(2020, 2, 1));
        reportFilter.setDateTo(LocalDate.of(2023, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsableDetailsByCountryCsvReport(reportFilter, outputStream),
            "udm/usable_details_by_source_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USABLE_DETAILS_BY_COUNTRY_CSV_REPORT)
    public void testUsableDetailsByCountryEmptyReport() throws IOException {
        UdmReportFilter reportFilter = new UdmReportFilter();
        reportFilter.setPeriods(Collections.singleton(202006));
        reportFilter.setDateFrom(LocalDate.of(2018, 2, 1));
        reportFilter.setDateTo(LocalDate.of(2019, 1, 1));
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsableDetailsByCountryCsvReport(reportFilter, outputStream),
            "udm/usable_details_by_source_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_VALUES_BY_STATUS_CSV_REPORT)
    public void testValuesByStatusReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmValuesByStatusCsvReport(202206, outputStream),
            "udm/values_by_status_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_VALUES_BY_STATUS_CSV_REPORT)
    public void testValuesByStatusEmptyReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmValuesByStatusCsvReport(202212, outputStream),
            "udm/common_status_empty_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_BY_STATUS_CSV_REPORT)
    public void testUsagesByStatusReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsagesByStatusCsvReport(202206, outputStream),
            "udm/usages_by_status_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_BY_STATUS_CSV_REPORT)
    public void testUsagesByStatusEmptyReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> udmReportRepository.writeUdmUsagesByStatusCsvReport(202212, outputStream),
            "udm/common_status_empty_report.csv");
    }
}
