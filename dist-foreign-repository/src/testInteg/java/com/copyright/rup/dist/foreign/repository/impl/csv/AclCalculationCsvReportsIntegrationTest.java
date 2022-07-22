package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.filter.AclFundPoolDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclGrantDetailFilter;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
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
}
