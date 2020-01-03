package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
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
 * Verifies {@link AaclUsageCsvProcessor}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/19
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
public class AaclUsageCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/aacl/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(AaclUsageCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        verifyProcessorResult("aacl_usages.csv");
    }

    @Test
    public void testProcessorMixedHeaders() throws Exception {
        verifyProcessorResult("aacl_usages_mixed_headers.csv");
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        try {
            processFile("aacl_usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException ex) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", ex.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> ex.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("aacl_usages_with_2000_errors_report.csv", pipedInputStream);
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        ProcessingResult<Usage> result = processFile("aacl_usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("aacl_usages_with_errors_report.csv", pipedInputStream);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("aacl_invalid_header_usage_data_file.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>Institution</li>" +
                    "<li>Usage Source</li>" +
                    "<li>Number of Copies</li>" +
                    "<li>Number of Pages</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private void verifyProcessorResult(String fileName) throws IOException {
        ProcessingResult<Usage> result = processFile(fileName);
        assertNotNull(result);
        List<Usage> actualUsages = result.get();
        List<Usage> expectedUsages = loadExpectedUsages();
        int expectedSize = 4;
        assertEquals(expectedSize, actualUsages.size());
        assertEquals(expectedSize, expectedUsages.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    private ProcessingResult<Usage> processFile(String file) throws IOException {
        ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            AaclUsageCsvProcessor processor = csvProcessorFactory.getAaclUsageCsvProcessor();
            result = processor.process(outputStream);
        }
        return result;
    }

    private void assertUsage(Usage expectedUsage, Usage actualUsage) {
        assertNotNull(expectedUsage.getId());
        assertNotNull(actualUsage.getId());
        assertStoredEntity(expectedUsage);
        assertStoredEntity(actualUsage);
        assertUsageParsedFields(expectedUsage, actualUsage);
    }

    private void assertStoredEntity(StoredEntity entity) {
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals("SYSTEM", entity.getCreateUser());
    }

    private void assertUsageParsedFields(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertAaclUsageFields(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void assertAaclUsageFields(AaclUsage expectedUsage, AaclUsage actualUsage) {
        assertEquals(expectedUsage.getInstitution(), actualUsage.getInstitution());
        assertEquals(expectedUsage.getUsageSource(), actualUsage.getUsageSource());
        assertEquals(expectedUsage.getNumberOfPages(), actualUsage.getNumberOfPages());
    }

    private List<Usage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "aacl_usages.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<Usage>>() {
        });
    }
}
