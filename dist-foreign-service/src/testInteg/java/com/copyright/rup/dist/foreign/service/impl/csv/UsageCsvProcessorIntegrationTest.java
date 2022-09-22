package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UsageCsvProcessor}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 02/24/17
 *
 * @author Aliaksei Pchelnikau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UsageCsvProcessorIntegrationTest {

    private static final String BASE_PATH = "/com/copyright/rup/dist/foreign/service/impl/usage/";
    private static final String PATH_TO_CSV = "src/testInteg/resources" + BASE_PATH;
    private static final String PRODUCT_FAMILY = "FAS";

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_CSV);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private ServiceTestHelper testHelper;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory(UsageCsvProcessorIntegrationTest.class);
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<Usage> result = processFile("usages.csv");
        assertNotNull(result);
        List<Usage> actualUsages = result.get();
        List<Usage> expectedUsages = testHelper.loadExpectedUsages(BASE_PATH + "usages.json");
        int expectedSize = 5;
        assertEquals(expectedSize, actualUsages.size());
        assertEquals(expectedSize, expectedUsages.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    @Test
    public void testProcessorForExportedUsages() throws Exception {
        ProcessingResult<Usage> result = processFile("exported_usages.csv");
        assertNotNull(result);
        List<Usage> actualUsages = result.get();
        List<Usage> expectedUsages = testHelper.loadExpectedUsages(BASE_PATH + "usages.json");
        int expectedSize = 5;
        assertEquals(expectedSize, actualUsages.size());
        assertEquals(expectedSize, expectedUsages.size());
        IntStream.range(0, expectedSize).forEach(i ->
            assertUsage(expectedUsages.get(i), actualUsages.get(i))
        );
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        try {
            processFile("usages_with_2000_errors.csv");
            fail();
        } catch (ThresholdExceededException ex) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", ex.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> ex.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("usages_with_2000_errors_report.csv", pipedInputStream);
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        ProcessingResult<Usage> result = processFile("usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("usages_with_errors_report.csv", pipedInputStream);
    }

    @Test
    public void testProcessorForExportedUsagesForNegativePath() throws Exception {
        ProcessingResult<Usage> result = processFile("exported_usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("exported_usages_with_errors_report.csv", pipedInputStream);
    }

    @Test
    public void testProcessorWithInvalidHeader() throws Exception {
        try {
            processFile("invalid_header_usage_data_file.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Title</li>" +
                    "<li>Article</li>" +
                    "<li>Standard Number</li>" +
                    "<li>Standard Number Type</li>" +
                    "<li>Wr Wrk Inst</li>" +
                    "<li>RH Account #</li>" +
                    "<li>Publisher</li>" +
                    "<li>Pub Date</li>" +
                    "<li>Number of Copies</li>" +
                    "<li>Reported Value</li>" +
                    "<li>Market</li>" +
                    "<li>Market Period From</li>" +
                    "<li>Market Period To</li>" +
                    "<li>Author</li>" +
                    "<li>Comment</li>" +
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    @Test
    public void testProcessorExportedUsagesWithInvalidHeader() throws Exception {
        try {
            processFile("invalid_header_exported_usage_data_file.csv");
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
                    "<li>Standard Number</li>" +
                    "<li>Standard Number Type</li>" +
                    "<li>Fiscal Year</li>" +
                    "<li>Payment Date</li>" +
                    "<li>Title</li>" +
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

    private ProcessingResult<Usage> processFile(String file)
        throws IOException {
        ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass()
            .getResourceAsStream(BASE_PATH + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            UsageCsvProcessor processor = csvProcessorFactory.getUsageCsvProcessor(PRODUCT_FAMILY);
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
        assertUsageNonParsedFields(expectedUsage);
        assertUsageNonParsedFields(actualUsage);
        Rightsholder payee = actualUsage.getPayee();
        assertNull(payee.getId());
        assertStoredEntity(payee);
        assertNull(payee.getName());
        assertNull(payee.getAccountNumber());
    }

    private void assertStoredEntity(StoredEntity entity) {
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals("SYSTEM", entity.getCreateUser());
    }

    private void assertUsageParsedFields(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getArticle(), actualUsage.getArticle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertRightsholder(expectedUsage.getRightsholder(), actualUsage.getRightsholder());
        assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
        assertEquals(expectedUsage.getPublicationDate(), actualUsage.getPublicationDate());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
        assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
        assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getAuthor(), actualUsage.getAuthor());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
    }

    private void assertRightsholder(Rightsholder expectedRightsholder, Rightsholder actualRightsholder) {
        assertNull(expectedRightsholder.getId());
        assertNull(actualRightsholder.getId());
        assertStoredEntity(expectedRightsholder);
        assertStoredEntity(actualRightsholder);
        assertNull(expectedRightsholder.getName());
        assertNull(actualRightsholder.getName());
        assertEquals(expectedRightsholder.getAccountNumber(), expectedRightsholder.getAccountNumber());
    }

    private void assertUsageNonParsedFields(Usage usage) {
        assertNull(usage.getBatchId());
        assertNull(usage.getScenarioId());
        assertEquals(BigDecimal.ZERO, usage.getNetAmount());
        assertNull(usage.getServiceFee());
        assertEquals(BigDecimal.ZERO, usage.getServiceFeeAmount());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
        assertFalse(usage.isRhParticipating());
    }
}
