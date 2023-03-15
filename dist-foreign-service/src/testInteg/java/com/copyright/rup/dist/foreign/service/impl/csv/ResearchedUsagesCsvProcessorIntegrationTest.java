package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ResearchedUsagesCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/researched/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(ResearchedUsagesCsvProcessorIntegrationTest.class);
    }

    @Test
    @TestData(fileName = "researched-usages-csv-processor-integration-test/test-processor.groovy")
    public void testProcessor() throws Exception {
        ProcessingResult<ResearchedUsage> result = processFile("researched_usages.csv");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        List<ResearchedUsage> actualUsages = result.get();
        List<ResearchedUsage> expectedUsages = loadExpectedUsages();
        int expectedSize = 2;
        assertEquals(expectedSize, actualUsages.size());
        assertEquals(expectedSize, expectedUsages.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        try {
            processFile("researched_usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException e) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", e.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> e.getProcessingResult().writeToFile(pos));
            reportTestUtils.assertCsvReport("researched_usages_with_2000_errors_report.csv", pis);
        }
    }

    @Test
    public void testProcessorFailure() throws Exception {
        ProcessingResult<ResearchedUsage> result = processFile("researched_usages_with_errors.csv");
        assertFalse(result.isSuccessful());
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pos));
        reportTestUtils.assertCsvReport("researched_usages_with_errors_report.csv", pis);
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
                    "<li>RRO Account #</li>" +
                    "<li>RRO Name</li>" +
                    "<li>RH Account #</li>" +
                    "<li>RH Name</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>System Title</li>" +
                    "<li>Reported Standard Number</li>" +
                    "<li>Standard Number</li>" +
                    "<li>Standard Number Type</li>" +
                    "<li>Fiscal Year</li>" +
                    "<li>Payment Date</li>" +
                    "<li>Reported Title</li>" +
                    "<li>Article</li>" +
                    "<li>Publisher</li>" +
                    "<li>Pub Date</li>" +
                    "<li>Number of Copies</li>" +
                    "<li>Reported Value</li>" +
                    "<li>Gross Amt in USD</li>" +
                    "<li>Batch Amt in USD</li>" +
                    "<li>Market</li>" +
                    "<li>Market Period From</li>" +
                    "<li>Market Period To</li>" +
                    "<li>Author</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private ProcessingResult<ResearchedUsage> processFile(String fileName) throws IOException {
        ProcessingResult<ResearchedUsage> result;
        try (InputStream is = this.getClass().getResourceAsStream(BASE_PATH + "/" + fileName);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copy(is, baos);
            ResearchedUsagesCsvProcessor processor = csvProcessorFactory.getResearchedUsagesCsvProcessor();
            result = processor.process(baos);
        }
        return result;
    }

    private void assertUsage(ResearchedUsage expectedUsage, ResearchedUsage actualUsage) {
        assertEquals(expectedUsage.getUsageId(), actualUsage.getUsageId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
    }

    private List<ResearchedUsage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "researched_usages.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<ResearchedUsage>>() {
        });
    }
}
