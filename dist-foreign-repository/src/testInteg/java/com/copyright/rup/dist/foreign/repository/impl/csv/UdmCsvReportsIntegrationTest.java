package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IUdmReportRepository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
@TestPropertySource(properties = {"test.liquibase.changelog=udm-csv-reports-test-data-init.groovy"})
@Transactional
public class UdmCsvReportsIntegrationTest extends CsvReportsTestHelper {

    @Autowired
    private IUdmReportRepository udmReportRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UdmCsvReportsIntegrationTest.class);
    }

    @Test
    public void testWriteUdmUsageCsvReportSpecialistManagerRoles() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a23681ae-1cf7-44ee-b09b-6fc06779e05c"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManagerRoles(udmUsageFilter, outputStream),
            "usages_report_udm_specialist_manager_roles.csv");
    }

    @Test
    public void testWriteUdmUsageEmptyCsvReportSpecialistManagerRoles() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportSpecialistManagerRoles(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_specialist_manager_roles.csv");
    }

    @Test
    public void testWriteUdmUsageCsvReportResearcherRole() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("94b644cb-ab57-4825-b985-c51734a5aa1e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcherRole(udmUsageFilter, outputStream),
            "usages_report_udm_researcher_role.csv");
    }

    @Test
    public void testWriteUdmUsageEmptyCsvReportResearcherRole() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportResearcherRole(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_researcher_role.csv");
    }

    @Test
    public void testWriteUdmUsageCsvReportViewRole() throws IOException {
        UdmUsageFilter udmUsageFilter = new UdmUsageFilter();
        udmUsageFilter.setUdmBatchesIds(Collections.singleton("a524f8a9-2c95-43ea-8c25-b9f38b1c758e"));
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportViewRole(udmUsageFilter, outputStream),
            "usages_report_udm_view_role.csv");
    }

    @Test
    public void testWriteUdmUsageEmptyCsvReportViewRole() throws IOException {
        assertFilesWithExecutor(outputStream ->
                udmReportRepository.writeUdmUsageCsvReportViewRole(new UdmUsageFilter(), outputStream),
            "usages_report_udm_empty_view_role.csv");
    }
}
