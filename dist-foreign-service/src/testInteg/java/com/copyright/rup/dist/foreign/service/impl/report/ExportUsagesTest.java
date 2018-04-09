package com.copyright.rup.dist.foreign.service.impl.report;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IReportService;

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
import java.util.Collections;
import java.util.concurrent.Executors;

/**
 * Verify filtered usages reporting functionality.
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
@TestPropertySource(properties = {"test.liquibase.changelog=export-usages-data-init.groovy"})
public class ExportUsagesTest {

    @Autowired
    private IReportService reportService;
    @Value("classpath:com/copyright/rup/dist/foreign/service/impl/report/expected_usages_report.csv")
    private Resource expectedReport;

    @Test
    public void testExportUsagesCsvReport() throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);
        UsageFilter usageFilter = buildUsageFilter();
        Executors.newSingleThreadExecutor().execute(() -> reportService.writeUsageCsvReport(usageFilter, out));
        File actualReportFile = new File("build/temp", "usages_report.csv");
        FileUtils.copyInputStreamToFile(in, actualReportFile);
        assertReportEquals(expectedReport.getFile(), actualReportFile);
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter uf = new UsageFilter();
        uf.setProductFamilies(Collections.singleton("FAS"));
        return uf;
    }

    private void assertReportEquals(File expectedReportFile, File actualReportFile) throws IOException {
        assertTrue(new ReportMatcher(expectedReportFile).matches(actualReportFile));
    }
}
