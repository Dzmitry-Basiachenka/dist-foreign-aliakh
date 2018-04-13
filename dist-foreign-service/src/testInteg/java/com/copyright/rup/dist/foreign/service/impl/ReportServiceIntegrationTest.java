package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Verifies reporting functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/13/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=report-service-data-init.groovy"})
public final class ReportServiceIntegrationTest {

    private static final String PATH_TO_EXPECTED_REPORTS =
        "src/testInteg/resources/com/copyright/rup/dist/foreign/service/impl/report";

    @Autowired
    private IReportService reportService;
    @Autowired
    private IScenarioService scenarioService;

    @Before
    public void setUp() throws IOException {
        ReportTestUtils.setUpTestDirectory();
    }

    @Test
    public void testExportAuditUsagesCsvReport() throws IOException {
        assertFiles(out -> reportService.writeAuditCsvReport(buildAuditFilter(), out), "audit_usages_report");
    }

    @Test
    public void testExportScenarioUsagesCsvReport() throws IOException {
        assertFiles(out -> reportService.writeScenarioUsagesCsvReport(getScenario(), out), "scenario_usages_report");
    }

    @Test
    public void testExportUsagesCsvReport() throws IOException {
        assertFiles(out -> reportService.writeUsageCsvReport(buildUsageFilter(), out), "usages_report");
    }

    private AuditFilter buildAuditFilter() {
        AuditFilter af = new AuditFilter();
        af.setProductFamilies(Collections.singleton("FAS"));
        return af;
    }

    private Scenario getScenario() {
        return scenarioService.getScenarios().stream()
            .filter(s -> "12ec845f-0e76-4d1c-85cd-bb3fb7ca260e".equals(s.getId()))
            .findAny()
            .orElse(null);
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter uf = new UsageFilter();
        uf.setProductFamilies(Collections.singleton("CLA_FAS"));
        return uf;
    }

    private void assertFiles(Consumer<? super PipedOutputStream> reportWriter, String expectedReportPrefix)
        throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);
        Executors.newSingleThreadExecutor().execute(() -> reportWriter.accept(out));
        assertCsvReport(expectedReportPrefix, in);
    }

    private void assertCsvReport(String expectedReportPrefix, InputStream actualReportStream) throws IOException {
        String reportFileName = expectedReportPrefix + ".csv";
        File actualReportFile = new File("build/temp", reportFileName);
        FileUtils.copyInputStreamToFile(actualReportStream, actualReportFile);
        File expectedReportFile = new File(PATH_TO_EXPECTED_REPORTS, reportFileName);
        assertTrue(new ReportMatcher(expectedReportFile).matches(actualReportFile));
    }
}
