package com.copyright.rup.dist.foreign.repository.impl;

import com.copyright.rup.dist.common.test.ReportTestUtils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Integration tests for
 * {@link UsageRepository#writeUndistributedLiabilitiesCsvReport(LocalDate, java.io.OutputStream)}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/15/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(
    properties = {"test.liquibase.changelog=undistributed-liabilities-report-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UndistributedLiabilitiesReportIntegrationTest {

    @Autowired
    private UsageRepository usageRepository;

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory();
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvReport() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        usageRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2011, 5, 5), outputStream);
        reportTestUtils.assertCsvReport("undistributed_liabilities_report.csv",
            new ByteArrayInputStream(outputStream.toByteArray()));
    }

    @Test
    public void testWriteUndistributedLiabilitiesCsvEmptyReport() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        usageRepository.writeUndistributedLiabilitiesCsvReport(LocalDate.of(2001, 5, 5), outputStream);
        reportTestUtils.assertCsvReport("undistributed_liabilities_report_empty.csv",
            new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
