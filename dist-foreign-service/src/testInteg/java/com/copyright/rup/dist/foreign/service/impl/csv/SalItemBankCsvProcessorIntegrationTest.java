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
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.SalUsage;
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
 * Verifies {@link SalItemBankCsvProcessor}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 07/30/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SalItemBankCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/sal/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(SalItemBankCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<Usage> result = processFile("sal_item_bank_usages.csv");
        assertNotNull(result);
        List<Usage> actualUsages = result.get();
        List<Usage> expectedUsages = loadExpectedUsages();
        assertEquals(expectedUsages.size(), actualUsages.size());
        IntStream.range(0, expectedUsages.size()).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        try {
            processFile("sal_item_bank_usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException ex) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", ex.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> ex.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("sal_item_bank_usages_with_2000_errors_report.csv", pipedInputStream);
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        ProcessingResult<Usage> result = processFile("sal_item_bank_usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("sal_item_bank_usages_with_errors_report.csv", pipedInputStream);
    }

    @Test
    @TestData(fileName = "sal-item-bank-csv-processor-integration-test/test-processor.groovy")
    public void testProcessorForNegativePathBusinessValidation() throws Exception {
        ProcessingResult<Usage> result = processFile("sal_item_bank_usages_with_business_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("sal_item_bank_usages_with_business_errors_report.csv", pipedInputStream);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("sal_item_bank_invalid_header.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Assessment Name</li>" +
                    "<li>Coverage Year</li>" +
                    "<li>Grade</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>Reported Work Portion ID</li>" +
                    "<li>Reported Standard Number</li>" +
                    "<li>Reported Title</li>" +
                    "<li>Reported Media Type</li>" +
                    "<li>Reported Article or Chapter Title</li>" +
                    "<li>Reported Author</li>" +
                    "<li>Reported Publisher</li>" +
                    "<li>Reported Publication Date</li>" +
                    "<li>Reported Page Range</li>" +
                    "<li>Reported Vol/Number/Series</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    private ProcessingResult<Usage> processFile(String file) throws IOException {
        ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass().getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            SalItemBankCsvProcessor processor = csvProcessorFactory.getSalItemBankCsvProcessor();
            result = processor.process(outputStream);
        }
        return result;
    }

    private void assertUsage(Usage expectedUsage, Usage actualUsage) {
        assertNotNull(expectedUsage.getId());
        assertNotNull(actualUsage.getId());
        assertStoredEntity(expectedUsage);
        assertStoredEntity(actualUsage);
        assertUsageFields(expectedUsage, actualUsage);
    }

    private void assertStoredEntity(StoredEntity entity) {
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals("SYSTEM", entity.getCreateUser());
    }

    private void assertUsageFields(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertSalUsageFields(expectedUsage.getSalUsage(), actualUsage.getSalUsage());
    }

    private void assertSalUsageFields(SalUsage expectedUsage, SalUsage actualUsage) {
        assertEquals(expectedUsage.getAssessmentName(), actualUsage.getAssessmentName());
        assertEquals(expectedUsage.getCoverageYear(), actualUsage.getCoverageYear());
        assertEquals(expectedUsage.getGrade(), actualUsage.getGrade());
        assertEquals(expectedUsage.getDetailType(), actualUsage.getDetailType());
        assertEquals(expectedUsage.getReportedWorkPortionId(), actualUsage.getReportedWorkPortionId());
        assertEquals(expectedUsage.getReportedStandardNumber(), actualUsage.getReportedStandardNumber());
        assertEquals(expectedUsage.getReportedMediaType(), actualUsage.getReportedMediaType());
        assertEquals(expectedUsage.getMediaTypeWeight(), actualUsage.getMediaTypeWeight());
        assertEquals(expectedUsage.getReportedArticle(), actualUsage.getReportedArticle());
        assertEquals(expectedUsage.getReportedAuthor(), actualUsage.getReportedAuthor());
        assertEquals(expectedUsage.getReportedPublisher(), actualUsage.getReportedPublisher());
        assertEquals(expectedUsage.getReportedPublicationDate(), actualUsage.getReportedPublicationDate());
        assertEquals(expectedUsage.getReportedPageRange(), actualUsage.getReportedPageRange());
        assertEquals(expectedUsage.getReportedVolNumberSeries(), actualUsage.getReportedVolNumberSeries());
    }

    private List<Usage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), BASE_PATH + "sal_item_bank_usages.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<Usage>>() {
        });
    }
}
