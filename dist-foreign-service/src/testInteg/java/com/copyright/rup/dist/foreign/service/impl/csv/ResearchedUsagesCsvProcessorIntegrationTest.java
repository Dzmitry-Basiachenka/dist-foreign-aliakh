package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ThresholdExceededException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Verifies {@link ResearchedUsagesCsvProcessor}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=researched-usages-csv-processor-data-init.groovy"})
public class ResearchedUsagesCsvProcessorIntegrationTest {

    private static final String PATH_TO_ACTUAL_REPORTS = "build/temp";
    private static final String PACKAGE = "/com/copyright/rup/dist/foreign/service/impl/usage/researched";
    private static final String PATH_TO_EXPECTED_REPORTS = "src/testInteg/resources" + PACKAGE;

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL_REPORTS).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL_REPORTS));
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<ResearchedUsage> result = processFile("researched_usages.csv");
        assertNotNull(result);
        List<ResearchedUsage> researchedUsages = result.get();
        assertEquals(2, CollectionUtils.size(researchedUsages));
        verifyResearchedUsage(researchedUsages.get(0), "e1108526-5945-4a30-971e-91e584b4bc88", 987654321);
        verifyResearchedUsage(researchedUsages.get(1), "9f717d69-785a-4f25-ae60-8d90c1f334cc", 876543210);
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        try {
            processFile("researched_usages_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException e) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", e.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> e.getProcessingResult().writeToFile(pos));
            FileUtils.copyInputStreamToFile(pis,
                new File(PATH_TO_ACTUAL_REPORTS, "researched_usages_2000_errors_report.csv"));
            isFilesEquals("researched_usages_2000_errors_report.csv");
        }
    }

    @Test
    public void testProcessorFailure() throws Exception {
        ProcessingResult<ResearchedUsage> result = processFile("researched_usages_errors.csv");
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pos));
        FileUtils.copyInputStreamToFile(pis,
            new File(PATH_TO_ACTUAL_REPORTS, "researched_usages_errors_report.csv"));
        isFilesEquals("researched_usages_errors_report.csv");
    }

    @Test
    public void testProcessorForInvalidHeaderWithHeaderValidation() throws Exception {
        try {
            processFile("researched_usages_invalid_header.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Detail ID</li>" +
                    "<li>Detail Status</li>" +
                    "<li>Product Family</li>" +
                    "<li>Usage Batch Name</li>" +
                    "<li>Fiscal Year</li>" +
                    "<li>RRO Account #</li>" +
                    "<li>RRO Name</li>" +
                    "<li>Payment Date</li>" +
                    "<li>Title</li>" +
                    "<li>Article</li>" +
                    "<li>Standard Number</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>RH Account #</li>" +
                    "<li>RH Name</li>" +
                    "<li>Publisher</li>" +
                    "<li>Pub Date</li>" +
                    "<li>Number of Copies</li>" +
                    "<li>Reported value</li>" +
                    "<li>Amt in USD</li>" +
                    "<li>Gross Amt in USD</li>" +
                    "<li>Market</li>" +
                    "<li>Market Period From</li>" +
                    "<li>Market Period To</li>" +
                    "<li>Author</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void isFilesEquals(String fileName) {
        assertTrue(new ReportMatcher(new File(PATH_TO_EXPECTED_REPORTS, fileName))
            .matches(new File(PATH_TO_ACTUAL_REPORTS, fileName)));
    }

    private ProcessingResult<ResearchedUsage> processFile(String fileName) throws IOException {
        ProcessingResult<ResearchedUsage> result;
        try (InputStream is = this.getClass().getResourceAsStream(PACKAGE + "/" + fileName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copy(is, baos);
            ResearchedUsagesCsvProcessor processor = csvProcessorFactory.getResearchedUsagesCsvProcessor();
            result = processor.process(baos);
        }
        return result;
    }

    private void verifyResearchedUsage(ResearchedUsage researchedUsage, String usageId, long wrWrkInst) {
        assertNotNull(researchedUsage);
        assertEquals(usageId, researchedUsage.getUsageId());
        assertEquals(wrWrkInst, researchedUsage.getWrWrkInst().longValue());
    }
}
