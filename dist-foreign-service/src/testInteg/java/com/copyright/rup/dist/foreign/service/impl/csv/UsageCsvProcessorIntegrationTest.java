package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.test.ReportTestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;

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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usages-csv-processor-data-init.groovy"})
public class UsageCsvProcessorIntegrationTest {

    private static final String PACKAGE = "/com/copyright/rup/dist/foreign/service/impl/usage";
    private static final String PATH_TO_EXPECTED_REPORTS = "src/testInteg/resources" + PACKAGE;
    private static final String TITLE = "1984";
    private static final String PRODUCT_FAMILY = "FAS";

    private final ReportTestUtils reportTestUtils = new ReportTestUtils(PATH_TO_EXPECTED_REPORTS);
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        ReportTestUtils.setUpTestDirectory();
    }

    @Test
    public void testProcessor() throws Exception {
        ProcessingResult<Usage> result = processFile("usages.csv", true);
        assertNotNull(result);
        List<Usage> usages = result.get();
        assertEquals(5, CollectionUtils.size(usages));
        verifyUsage(usages.get(0), 123456789L, 1000009522L, UsageStatusEnum.ELIGIBLE, TITLE);
        verifyUsage(usages.get(1), null, null, UsageStatusEnum.NEW, TITLE);
        verifyUsage(usages.get(2), 123456789L, null, UsageStatusEnum.WORK_FOUND, TITLE);
        verifyUsage(usages.get(3), 123456789L, 999999999999999999L, UsageStatusEnum.ELIGIBLE, TITLE);
        verifyUsageWithEmptyFields(usages.get(4));
    }

    @Test
    public void testProcessorErrorsExceededThreshold() throws Exception {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        try {
            processFile("usages_with_2000_errors.csv", true);
            fail();
        } catch (ThresholdExceededException ex) {
            assertEquals("The file could not be uploaded. There are more than 2000 errors", ex.getMessage());
            Executors.newSingleThreadExecutor().execute(() -> ex.getProcessingResult().writeToFile(outputStream));
            reportTestUtils.assertCsvReport("usages_with_2000_errors_report.csv", pipedInputStream);
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        ProcessingResult<Usage> result = processFile("usages_with_errors.csv", true);
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        reportTestUtils.assertCsvReport("usages_with_errors_report.csv", pipedInputStream);
    }

    @Test
    public void testProcessorForInvalidHeaderWithHeaderValidation() throws Exception {
        try {
            processFile("invalid_header_usage_data_file.csv", true);
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n" +
                    "<ul>" +
                    "<li>Title</li>" +
                    "<li>Article</li>" +
                    "<li>Standard Number</li>" +
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
                    "</ul>",
                e.getHtmlMessage());
        }
    }

    @Test
    public void testProcessorWithInvalidHeaderWithoutHeaderValidation() throws Exception {
        ProcessingResult<Usage> result = processFile("invalid_header_usage_data_file.csv", false);
        assertTrue(result.isSuccessful());
    }

    private ProcessingResult<Usage> processFile(String file, boolean validateHeaders)
        throws IOException {
        ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass()
            .getResourceAsStream(PACKAGE + "/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            UsageCsvProcessor processor = csvProcessorFactory.getUsageCsvProcessor(PRODUCT_FAMILY);
            processor.setValidateHeaders(validateHeaders);
            result = processor.process(outputStream);
        }
        return result;
    }

    private void verifyUsage(Usage usage, Long wrWrkInst, Long rhAccountNumber, UsageStatusEnum status, String title) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(title, usage.getWorkTitle());
        assertEquals("Appendix: The Principles of Newspeak", usage.getArticle());
        assertEquals("9780150000000", usage.getStandardNumber());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(rhAccountNumber, usage.getRightsholder().getAccountNumber());
        assertEquals("Publisher", usage.getPublisher());
        assertEquals(LocalDate.of(3000, 12, 22), usage.getPublicationDate());
        assertEquals(Integer.valueOf(65), usage.getNumberOfCopies());
        assertEquals(new BigDecimal("30.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2016), usage.getMarketPeriodTo());
        assertEquals(status, usage.getStatus());
        assertEquals("Aarseth, Espen J.", usage.getAuthor());
        assertEquals(PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
    }

    private void verifyUsageWithEmptyFields(Usage usage) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(TITLE, usage.getWorkTitle());
        assertNull(usage.getArticle());
        assertEquals("9780150000000", usage.getStandardNumber());
        assertNull(usage.getWrWrkInst());
        assertNull(usage.getRightsholder().getAccountNumber());
        assertNull(usage.getPublisher());
        assertNull(usage.getPublicationDate());
        assertNull(usage.getNumberOfCopies());
        assertEquals(new BigDecimal("60.80"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.NEW, usage.getStatus());
        assertNull(usage.getAuthor());
        assertEquals(PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
    }
}
