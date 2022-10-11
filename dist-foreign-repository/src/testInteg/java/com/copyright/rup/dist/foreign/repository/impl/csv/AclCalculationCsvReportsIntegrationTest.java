package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.repository.api.IAclCalculationReportRepository;

import com.google.common.collect.Sets;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

/**
 * Integration tests for csv reports for ACL calculation.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/16/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclCalculationCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "acl-calculation-csv-reports-integration-test/";
    private static final String WRITE_GRANT_DETAIL_CSV_REPORT = FOLDER_NAME + "write-grant-detail-csv-report.groovy";
    private static final String WRITE_USAGE_CSV_REPORT = FOLDER_NAME + "write-usage-csv-report.groovy";
    private static final String WRITE_FUND_POOL_DETAILS_CSV_REPORT =
        FOLDER_NAME + "write-fund-pool-detail-csv-report.groovy";
    private static final String WRITE_SCENARIO_DETAILS_CSV_REPORT =
        FOLDER_NAME + "write-scenario-details-csv-report.groovy";
    private static final String WRITE_SCENARIO_RIGHTSHOLDER_TOTALS_CSV_REPORT =
        FOLDER_NAME + "write-scenario-rightsholder-totals-csv-report.groovy";
    private static final String WRITE_SCENARIO_SUMMARY_OF_WORK_SHARES_BY_AGG_LC_CSV_REPORT =
        FOLDER_NAME + "write-scenario-summary-of-work-shares-by-agg-lc-csv-report.groovy";
    private static final String WRITE_LIABILITIES_BY_AGG_LIC_CLASS_CSV_REPORT =
        FOLDER_NAME + "write-liabilities-by-agg-lic-class-csv-report.groovy";

    @Autowired
    private IAclCalculationReportRepository aclCalculationReportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AclCalculationCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_GRANT_DETAIL_CSV_REPORT)
    public void testWriteGrantDetailCsvReport() throws IOException {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetPeriod(202206);
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclGrantDetailCsvReport(filter, outputStream),
            "acl/grant_detail_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_GRANT_DETAIL_CSV_REPORT)
    public void testWriteGrantDetailEmptyCsvReport() throws IOException {
        AclGrantDetailFilter filter = new AclGrantDetailFilter();
        filter.setGrantSetNames(Collections.singleton("Empty"));
        filter.setGrantSetPeriod(202212);
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclGrantDetailCsvReport(filter, outputStream),
            "acl/grant_detail_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageCsvReport() throws IOException {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName("ACL Usage Batch 2022");
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclUsageCsvReport(filter, outputStream),
            "acl/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGE_CSV_REPORT)
    public void testWriteUsageEmptyCsvReport() throws IOException {
        AclUsageFilter filter = new AclUsageFilter();
        filter.setUsageBatchName("Empty batch");
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclUsageCsvReport(filter, outputStream),
            "acl/usages_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_FUND_POOL_DETAILS_CSV_REPORT)
    public void testWriteAclFundPoolDetailsCsvReport() throws IOException {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setFundPoolNames(Sets.newHashSet("ACL Fund Pool 202112", "VGW LDMT Fund Pool 201906"));
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclFundPoolDetailsCsvReport(filter, outputStream),
            "acl/fund_pool_details_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_FUND_POOL_DETAILS_CSV_REPORT)
    public void testWriteAclFundPoolDetailsEmptyCsvReport() throws IOException {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setFundPoolNames(Sets.newHashSet("ACL Fund Pool 202112", "VGW LDMT Fund Pool 201906"));
        filter.setLicenseType("MACL");
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclFundPoolDetailsCsvReport(filter, outputStream),
            "acl/fund_pool_details_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_DETAILS_CSV_REPORT)
    public void testWriteAclScenarioDetailsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclScenarioDetailsCsvReport("cb3bcdf0-c312-4cb0-bd4c-3f2c9a703d7a",
                outputStream), "acl/scenario_details_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_DETAILS_CSV_REPORT)
    public void testWriteAclScenarioDetailsEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclScenarioDetailsCsvReport("0d9f5bba-4961-449b-ae7d-4586d1b5c0d5",
                outputStream), "acl/scenario_details_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_RIGHTSHOLDER_TOTALS_CSV_REPORT)
    public void testWriteAclScenarioRightsholderTotalsCsvReport() throws IOException {
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclScenarioRightsholderTotalsCsvReport(
                "ac2b822b-c8d1-4599-acdc-e1d13713a6c9", outputStream), "acl/scenario_rightsholder_totals_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_RIGHTSHOLDER_TOTALS_CSV_REPORT)
    public void testWriteAclScenarioRightsholderTotalsEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclScenarioRightsholderTotalsCsvReport(
                "12208959-6b37-4ac4-a921-cda0cc652c09", outputStream),
            "acl/scenario_rightsholder_totals_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_SUMMARY_OF_WORK_SHARES_BY_AGG_LC_CSV_REPORT)
    public void testWriteSummaryOfWorkSharesByAggLcCsvReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = new AclCalculationReportsInfoDto();
        AclScenario scenario = new AclScenario();
        scenario.setId("a0162659-86af-40ab-bc55-2ae0cdebc2a4");
        scenario.setName("ACL Scenario 10/05/202212");
        scenario.setLicenseType("ACL");
        scenario.setPeriodEndDate(202212);
        reportsInfoDto.setReportName("Summary of Work Shares by Agg LC Report");
        reportsInfoDto.setScenarios(Collections.singletonList(scenario));
        reportsInfoDto.setUser("user@copyright.com");
        reportsInfoDto.setReportDateTime(LocalDateTime.of(2022, 10, 5, 14, 30, 30));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeSummaryOfWorkSharesByAggLcCsvReport(reportsInfoDto,
                outputStream), "acl/summary_work_shares_agg_lc_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_SUMMARY_OF_WORK_SHARES_BY_AGG_LC_CSV_REPORT)
    public void testWriteSummaryOfWorkSharesByAggLcEmptyCsvReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = new AclCalculationReportsInfoDto();
        AclScenario scenario = buildScenario("38688258-b86b-41f5-b7fd-a45121cdc2cb", "ACL Scenario 11/05/202212");
        scenario.setLicenseType("ACL");
        scenario.setPeriodEndDate(202212);
        reportsInfoDto.setReportName("Summary of Work Shares by Agg LC Report");
        reportsInfoDto.setScenarios(Collections.singletonList(scenario));
        reportsInfoDto.setUser("user@copyright.com");
        reportsInfoDto.setReportDateTime(LocalDateTime.of(2022, 10, 5, 14, 30, 30));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeSummaryOfWorkSharesByAggLcCsvReport(reportsInfoDto, outputStream),
            "acl/summary_work_shares_agg_lc_report_empty.csv");
    }

    @Test
    @TestData(fileName = WRITE_LIABILITIES_BY_AGG_LIC_CLASS_CSV_REPORT)
    public void testWriteAclLiabilitiesByAggLicClassReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = new AclCalculationReportsInfoDto();
        reportsInfoDto.setPeriod(202212);
        reportsInfoDto.setScenarios(Arrays.asList(
            buildScenario("06fee547-bfc4-4f2a-9578-58c03821e217", "ACL Scenario 10/05/202212"),
            buildScenario("6dbd30f7-91f6-4949-a74c-cfbac5e466ac", "MCL Scenario 10/05/202212"),
            buildScenario("ca1c5532-eb1c-440a-8fbc-8e595dbea5cb", "VGW Scenario 10/05/202212"),
            buildScenario("d86f2c59-a50c-4e54-826a-ee50aeb98904", "JACDCL Scenario 10/05/202212")));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclLiabilitiesByAggLicClassReport(reportsInfoDto, outputStream),
            "acl/liabilities_by_agg_lic_class_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_LIABILITIES_BY_AGG_LIC_CLASS_CSV_REPORT)
    public void testWriteAclLiabilitiesByAggLicClassEmptyReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = new AclCalculationReportsInfoDto();
        reportsInfoDto.setPeriod(202112);
        reportsInfoDto.setScenarios(Collections.emptyList());
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclLiabilitiesByAggLicClassReport(reportsInfoDto, outputStream),
            "acl/liabilities_by_agg_lic_class_empty_report.csv");
    }

    private AclScenario buildScenario(String id, String name) {
        AclScenario scenario = new AclScenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }
}
