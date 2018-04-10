package com.copyright.rup.dist.foreign.service.impl.report;

import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
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
 * Verify audit usages reporting functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/10/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=export-audit-usages-data-init.groovy"})
public class ExportAuditUsagesTest {

    @Autowired
    private IReportService reportService;
    @Value("classpath:com/copyright/rup/dist/foreign/service/impl/report/expected_audit_usages_report.csv")
    private Resource expectedReport;

    @Test
    public void testExportAuditUsagesCsvReport() throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);
        AuditFilter auditFilter = buildAuditFilter();
        Executors.newSingleThreadExecutor().execute(() -> reportService.writeAuditCsvReport(auditFilter, out));
        File actualReportFile = new File("build/temp", "audit_usages_report.csv");
        FileUtils.copyInputStreamToFile(in, actualReportFile);
        assertReportEquals(expectedReport.getFile(), actualReportFile);
    }

    private AuditFilter buildAuditFilter() {
        AuditFilter af = new AuditFilter();
        af.setProductFamilies(Collections.singleton("FAS"));
        return af;
    }

    private void assertReportEquals(File expectedReportFile, File actualReportFile) throws IOException {
        assertTrue(new ReportMatcher(expectedReportFile).matches(actualReportFile));
    }
}
