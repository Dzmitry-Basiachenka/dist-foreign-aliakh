package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAclciReportRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Set;

/**
 * Integration test for {@link IAclciReportRepository}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/13/2023
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclciCsvReportsIntegrationTest extends CsvReportsTestHelper {

    private static final String FOLDER_NAME = "aclci-csv-reports-integration-test/";
    private static final String WRITE_USAGES_CSV_REPORT = FOLDER_NAME + "write-usages-csv-report.groovy";

    @Autowired
    private IAclciReportRepository aclciReportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AclciCsvReportsIntegrationTest.class);
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesCsvReport() throws IOException {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageBatchesIds(Set.of("5ac83c1b-0287-407e-b237-b06734fddec1"));
        usageFilter.setProductFamily("ACLCI");
        assertFilesWithExecutor(
            outputStream -> aclciReportRepository.writeAclciUsagesCsvReport(usageFilter, outputStream),
            "aclci/usages_report.csv");
    }

    @Test
    @TestData(fileName = WRITE_USAGES_CSV_REPORT)
    public void testWriteUsagesEmptyCsvReport() throws IOException {
        assertFilesWithExecutor(
            outputStream -> aclciReportRepository.writeAclciUsagesCsvReport(new UsageFilter(), outputStream),
            "aclci/usages_report_empty.csv");
    }
}
