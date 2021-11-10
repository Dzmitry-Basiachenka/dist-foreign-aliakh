package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;

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
 * Verifies {@link ClassifiedUsageCsvProcessor}.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 01/24/2020
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
//TODO: split test data into separate files for each test method
@TestData(fileName = "classified-usage-csv-processor-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ClassifiedUsageCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/aacl/classified/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(ClassifiedUsageCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<AaclClassifiedUsage> result = processFile("classified_usages.csv");
        assertNotNull(result);
        assertTrue(result.isSuccessful());
        List<AaclClassifiedUsage> actualUsages = result.get();
        List<AaclClassifiedUsage> expectedUsages = loadExpectedUsages();
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> assertUsage(expectedUsages.get(index), actualUsages.get(index)));
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream(pos);
        try {
            processFile("classified_usages_with_2000_errors.csv");
            fail();
        } catch (DistCsvProcessor.ThresholdExceededException e) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", e.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> e.getProcessingResult().writeToFile(pos));
            reportTestUtils.assertCsvReport("classified_usages_with_2000_errors_report.csv", pis);
        }
    }

    @Test
    public void testProcessorFailure() throws Exception {
        ProcessingResult<AaclClassifiedUsage> result = processFile("classified_usages_with_errors.csv");
        assertFalse(result.isSuccessful());
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(pipedOutputStream));
        reportTestUtils.assertCsvReport("classified_usages_with_errors_report.csv",
            new PipedInputStream(pipedOutputStream));
    }

    @Test
    public void testProcessorWhenHeadersAreInvalid() throws Exception {
        try {
            processFile("classified_usages_invalid_headers.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Detail ID</li>" +
                    "<li>Detail Status</li>" +
                    "<li>Product Family</li>" +
                    "<li>Usage Batch Name</li>" +
                    "<li>Period End Date</li>" +
                    "<li>RH Account #</li>" +
                    "<li>RH Name</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>System Title</li>" +
                    "<li>Standard Number</li>" +
                    "<li>Standard Number Type</li>" +
                    "<li>Det LC ID</li>" +
                    "<li>Det LC Enrollment</li>" +
                    "<li>Det LC Discipline</li>" +
                    "<li>Pub Type</li>" +
                    "<li>Institution</li>" +
                    "<li>Usage Period</li>" +
                    "<li>Usage Source</li>" +
                    "<li>Number of Copies</li>" +
                    "<li>Number of Pages</li>" +
                    "<li>Right Limitation</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private ProcessingResult<AaclClassifiedUsage> processFile(String fileName) throws IOException {
        ProcessingResult<AaclClassifiedUsage> result;
        try (InputStream inputStream = this.getClass().getResourceAsStream(BASE_PATH + "/" + fileName);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            ClassifiedUsageCsvProcessor processor = csvProcessorFactory.getClassifiedUsageCsvProcessor();
            result = processor.process(outputStream);
        }
        return result;
    }

    private void assertUsage(AaclClassifiedUsage expectedUsage, AaclClassifiedUsage actualUsage) {
        assertEquals(expectedUsage.getDetailId(), actualUsage.getDetailId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getDiscipline(), actualUsage.getDiscipline());
        assertEquals(expectedUsage.getEnrollmentProfile(), actualUsage.getEnrollmentProfile());
        assertEquals(expectedUsage.getPublicationType(), actualUsage.getPublicationType());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
    }

    private List<AaclClassifiedUsage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "classified_usages.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<AaclClassifiedUsage>>() {
        });
    }
}
