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
public class ResearchedUsagesCsvProcessorIntegrationTest {

    private static final String PATH_TO_ACTUAL_REPORTS = "build/temp";
    private static final String PACKAGE = "/com/copyright/rup/dist/foreign/service/csv/researched_usages";
    private static final String PATH_TO_EXPECTED_REPORTS = "src/testInteg/resources" + PACKAGE;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL_REPORTS).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL_REPORTS));
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<ResearchedUsage> result = processFile("researched_usages.csv");
        assertNotNull(result);
        List<ResearchedUsage> usages = result.get();
        assertEquals(2, CollectionUtils.size(usages));
        verifyUsage(usages.get(0), 12345678, 987654321);
        verifyUsage(usages.get(1), 23456789, 876543210);
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
            ResearchedUsagesCsvProcessor processor = new ResearchedUsagesCsvProcessor();
            processor.setValidateHeaders(true);
            result = processor.process(baos);
        }
        return result;
    }

    private void verifyUsage(ResearchedUsage usage, long detailId, long wrWrkInst) {
        assertNotNull(usage);
        assertEquals(detailId, usage.getDetailId().longValue());
        assertEquals(wrWrkInst, usage.getWrWrkInst().longValue());
    }
}
