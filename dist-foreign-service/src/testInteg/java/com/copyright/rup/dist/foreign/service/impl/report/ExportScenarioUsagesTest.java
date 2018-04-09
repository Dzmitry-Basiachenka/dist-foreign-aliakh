package com.copyright.rup.dist.foreign.service.impl.report;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IReportService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Executors;

/**
 * Verify scenario usages reporting functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/9/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=export-scenario-usages-data-init.groovy"})
public class ExportScenarioUsagesTest {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IReportService reportService;
    @Value("classpath:com/copyright/rup/dist/foreign/service/impl/report/expected_scenario_usages_report.csv")
    private Resource expectedReport;

    @Test
    public void testExportScenarioUsagesCsvReport() throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);
        Scenario scenario = getScenario();
        Executors.newSingleThreadExecutor().execute(() -> reportService.writeScenarioUsagesCsvReport(scenario, out));
        File actualReportFile = new File("build/temp", "scenario_usages_report.csv");
        FileUtils.copyInputStreamToFile(in, actualReportFile);
        assertReportEquals(expectedReport.getFile(), actualReportFile);
    }

    private Scenario getScenario() {
        return scenarioService.getScenarios().stream()
            .filter(s -> "12ec845f-0e76-4d1c-85cd-bb3fb7ca260e".equals(s.getId()))
            .findAny()
            .orElse(null);
    }

    private void assertReportEquals(File expectedReportFile, File actualReportFile) throws IOException {
        assertTrue(new ReportMatcher(expectedReportFile).matches(actualReportFile));
    }
}
