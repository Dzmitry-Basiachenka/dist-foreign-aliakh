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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

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
    private static final String WRITE_SCENARIO_WORK_SHARES_BY_AGG_LC_CSV_REPORT =
        FOLDER_NAME + "write-scenario-work-shares-by-agg-lc-csv-report.groovy";
    private static final String WRITE_LIABILITIES_BY_AGG_LIC_CLASS_CSV_REPORT =
        FOLDER_NAME + "write-liabilities-by-agg-lic-class-csv-report.groovy";
    private static final String WRITE_LIABILITY_DETAILS_CSV_REPORT =
        FOLDER_NAME + "write-liability-details-csv-report.groovy";
    private static final String WRITE_LIABILITIES_BY_RH_CSV_REPORT =
        FOLDER_NAME + "write-liabilities-by-rh-csv-report.groovy";
    private static final String WRITE_COMPARISON_BY_AGG_LC_CL_CSV_REPORT =
        FOLDER_NAME + "write-comparison-by-agg-lc-class-and-title-csv-report.groovy";
    private static final String WRITE_FUND_POOL_BY_AGG_LC_CSV_REPORT =
        FOLDER_NAME + "write-fund-pool-by-agg-lc-csv-report.groovy";
    private static final String ACL_SCENARIO_NAME = "ACL Scenario 10/05/202212";

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
        filter.setGrantSetNames(Set.of("Empty"));
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
        filter.setFundPoolNames(Set.of("ACL Fund Pool 202112", "VGW LDMT Fund Pool 201906"));
        assertFilesWithExecutor(
            outputStream -> aclCalculationReportRepository.writeAclFundPoolDetailsCsvReport(filter, outputStream),
            "acl/fund_pool_details_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_FUND_POOL_DETAILS_CSV_REPORT)
    public void testWriteAclFundPoolDetailsEmptyCsvReport() throws IOException {
        AclFundPoolDetailFilter filter = new AclFundPoolDetailFilter();
        filter.setFundPoolNames(Set.of("ACL Fund Pool 202112", "VGW LDMT Fund Pool 201906"));
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
        AclScenario scenario = buildScenario("a0162659-86af-40ab-bc55-2ae0cdebc2a4", ACL_SCENARIO_NAME);
        scenario.setLicenseType("ACL");
        scenario.setPeriodEndDate(202212);
        AclCalculationReportsInfoDto reportsInfoDto = buildReportInfo();
        reportsInfoDto.setScenarios(List.of(scenario));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeSummaryOfWorkSharesByAggLcCsvReport(reportsInfoDto, outputStream),
            "acl/summary_work_shares_agg_lc_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_SCENARIO_WORK_SHARES_BY_AGG_LC_CSV_REPORT)
    public void testWriteWorkSharesByAggLcCsvReport() throws IOException {
        AclScenario scenario = buildScenario("a0ffe3b2-2fe6-4eea-8ed9-9200b24973ea", ACL_SCENARIO_NAME);
        scenario.setLicenseType("ACL");
        scenario.setPeriodEndDate(202212);
        AclCalculationReportsInfoDto reportsInfoDto = buildReportInfo();
        reportsInfoDto.setScenarios(List.of(scenario));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeWorkSharesByAggLcCsvReport(reportsInfoDto, outputStream),
            "acl/work_shares_agg_lc_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_LIABILITIES_BY_AGG_LIC_CLASS_CSV_REPORT)
    public void testWriteAclLiabilitiesByAggLicClassReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = buildReportInfo();
        reportsInfoDto.setScenarios(List.of(
            buildScenario("06fee547-bfc4-4f2a-9578-58c03821e217", ACL_SCENARIO_NAME),
            buildScenario("6dbd30f7-91f6-4949-a74c-cfbac5e466ac", "MCL Scenario 10/05/202212"),
            buildScenario("ca1c5532-eb1c-440a-8fbc-8e595dbea5cb", "VGW Scenario 10/05/202212"),
            buildScenario("d86f2c59-a50c-4e54-826a-ee50aeb98904", "JACDCL Scenario 10/05/202212")));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclLiabilitiesByAggLicClassReport(reportsInfoDto, outputStream),
            "acl/liabilities_by_agg_lic_class_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_LIABILITY_DETAILS_CSV_REPORT)
    public void testWriteAclLiabilityDetailsReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = buildReportInfo();
        reportsInfoDto.setScenarios(List.of(
            buildScenario("06fee547-bfc4-4f2a-9578-58c03821e217", ACL_SCENARIO_NAME),
            buildScenario("d86f2c59-a50c-4e54-826a-ee50aeb98904", "JACDCL Scenario 10/05/202212")));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclLiabilityDetailsReport(reportsInfoDto, outputStream),
            "acl/liability_details_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_LIABILITIES_BY_RH_CSV_REPORT)
    public void testWriteAclLiabilitiesByRhReport() throws IOException {
        AclCalculationReportsInfoDto reportsInfoDto = buildReportInfo();
        reportsInfoDto.setScenarios(List.of(
            buildScenario("ae0910b1-1fb0-420a-96c9-76a18ebca229", ACL_SCENARIO_NAME),
            buildScenario("49c52e95-ca96-400e-a73c-d5732a5d1301", "MACL Scenario 10/05/202212"),
            buildScenario("3ecfcf3b-1be0-4bfa-bc55-44add8432df9", "VGW Scenario 10/05/202212"),
            buildScenario("ce858f53-eea8-4273-888e-ab983f88f59b", "JACDCL Scenario 10/05/202212")));
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclLiabilitiesByRhReport(reportsInfoDto, outputStream),
            "acl/liabilities_by_rightsholder_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_COMPARISON_BY_AGG_LC_CL_CSV_REPORT)
    public void testWriteAclComparisonByAggLcClassAndTitleReport() throws IOException {
        AclCalculationReportsInfoDto reportInfo = new AclCalculationReportsInfoDto();
        reportInfo.setPreviousScenarios(List.of(
            buildScenario("7fa61f70-3310-4ec0-8a50-bdd794e2fd08", "ACL Scenario 202212"),
            buildScenario("c283e1db-5fea-4ad6-bb54-5069ba0ecc1e", "MACL Scenario 202212"),
            buildScenario("2050bc92-4900-403b-88bc-a05ec33c5de4", "VGW Scenario 202212")));
        reportInfo.setScenarios(List.of(
            buildScenario("ec2e8521-a55b-4b70-ad9d-9b07ddfa0b3e", "ACL Scenario 202312"),
            buildScenario("c42237cf-dec7-43e3-a5d6-4427b6d24773", "MACL Scenario 202312"),
            buildScenario("2bd594f8-7289-4d6e-be46-9df909f36263", "VGW Scenario 202312")));
        assertFilesWithExecutor(outputStream ->
                aclCalculationReportRepository.writeAclComparisonByAggLcClassAndTitleReport(reportInfo, outputStream),
            "acl/comparison_by_agg_lc_cl_and_title_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_FUND_POOL_BY_AGG_LC_CSV_REPORT)
    public void testWriteAclFundPoolByAggLcReport() throws IOException {
        Set<String> fundPoolIds =
            Set.of("9be3e7f9-a48a-4d23-86bb-b1dc5054e4bd", "1ab6de5a-f4cd-43b6-b2d3-f4b6b5a1bc7a");
        assertFilesWithExecutor(outputStream ->
            aclCalculationReportRepository.writeAclFundPoolByAggLcReport(fundPoolIds, outputStream),
            "acl/fund_pool_by_agg_lc_report.csv");
    }

    private AclScenario buildScenario(String id, String name) {
        AclScenario scenario = new AclScenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }

    private AclCalculationReportsInfoDto buildReportInfo() {
        AclCalculationReportsInfoDto reportsInfoDto = new AclCalculationReportsInfoDto();
        reportsInfoDto.setPeriod(202212);
        reportsInfoDto.setUser("user@copyright.com");
        reportsInfoDto.setReportDateTime(OffsetDateTime.of(2022, 10, 5, 14, 30, 30, 30, ZoneOffset.ofHours(0)));
        return reportsInfoDto;
    }
}
