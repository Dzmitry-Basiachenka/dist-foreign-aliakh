package com.copyright.rup.dist.foreign.service.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.HeaderValidationException;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ThresholdExceededException;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
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
public class UsageCsvProcessorIntegrationTest {

    private static final String PATH_TO_ACTUAL = "build/temp";
    private static final String PATH_TO_EXPECTED = "src/testInteg/resources/com/copyright/rup/dist/foreign/service/csv";
    private static final String TITLE = "1984";
    private static final String PRODUCT_FAMILY = "FAS";
    private static final String ERRORS_REPORT_CSV = "errors_report.csv";

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL));
    }

    @Test
    public void testProcessor() throws Exception {
        DistCsvProcessor.ProcessingResult<Usage> result = processFile("usages.csv");
        assertNotNull(result);
        List<Usage> usages = result.get();
        assertEquals(5, CollectionUtils.size(usages));
        verifyUsage(usages.get(0), 234L, 123456789L, 1000009522L, UsageStatusEnum.ELIGIBLE, TITLE);
        verifyUsage(usages.get(1), 236L, null, null, UsageStatusEnum.NEW, TITLE);
        verifyUsage(usages.get(2), 237L, 123456789L, null, UsageStatusEnum.WORK_FOUND, TITLE);
        verifyUsage(usages.get(3), 238L, 123456789L, 1000009522L, UsageStatusEnum.ELIGIBLE, null);
        verifyUsageWithEmptyFields(usages.get(4));
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
            FileUtils.copyInputStreamToFile(pipedInputStream, new File(PATH_TO_ACTUAL, "errors_2000_report.csv"));
            isFilesEquals("usages_with_2000_errors_report.csv", "errors_2000_report.csv");
        }
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        DistCsvProcessor.ProcessingResult<Usage> result = processFile("usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        FileUtils.copyInputStreamToFile(pipedInputStream, new File(PATH_TO_ACTUAL, ERRORS_REPORT_CSV));
        isFilesEquals("usages_with_errors_report.csv", ERRORS_REPORT_CSV);
    }

    @Test
    public void testWriteErrorsToFile() throws Exception {
        DistCsvProcessor.ProcessingResult<Usage> result =
            new DistCsvProcessor.ProcessingResult<>(UsageCsvProcessor.getColumns());
        logErrors(result);
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> result.writeToFile(outputStream));
        FileUtils.copyInputStreamToFile(pipedInputStream, new File(PATH_TO_ACTUAL, "errorsToFile.csv"));
        isFilesEquals(ERRORS_REPORT_CSV, "errorsToFile.csv");
    }

    @Test
    public void testProcessorForInvalidHeaderWithHeaderValidation() throws Exception {
        try {
            processFile("invalid_header_usage_data_file.csv");
            fail();
        } catch (HeaderValidationException e) {
            assertEquals(
                "Columns headers are incorrect. Expected columns headers are:\n<ul>" +
                    "<li>Detail ID</li><li>Title</li><li>Article</li><li>Standard Number</li>" +
                    "<li>Wr Wrk Inst</li><li>RH Account #</li><li>Publisher</li><li>Pub Date</li>" +
                    "<li>Number of Copies</li><li>Reported Value</li><li>Market</li><li>Market Period From</li>" +
                    "<li>Market Period To</li><li>Author</li></ul>",
                e.getHtmlMessage());
        }
    }

    @Test
    public void testProcessorWithInvalidHeaderWithoutHeaderValidation() throws Exception {
        ProcessingResult<Usage> result = processFile("invalid_header_usage_data_file.csv", false);
        assertTrue(result.isSuccessful());
    }

    private void logErrors(DistCsvProcessor.ProcessingResult<Usage> result) {
        result.logError(2, buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList(StringUtils.EMPTY)),
            "Detail ID: Field is required and cannot be null or empty");
        result.logError(5, buildOriginalLineWithErrors(Lists.newArrayList(5), Lists.newArrayList("text")),
            "RH Account #: Field value should be numeric");
        String[] lines =
            buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList("text more then 10 symbols"));
        result.logError(3, lines, "Detail ID: Field value should be numeric");
        result.logError(3, lines, "Detail ID: Field max length is 10 characters");
        lines = buildOriginalLineWithErrors(Lists.newArrayList(8, 4), Lists.newArrayList("44.123", "text"));
        result.logError(7, lines, "Reported Value: Field value must be Numeric with decimals to 2 places");
        result.logError(7, lines, "Wr Wrk Inst: Field value should be numeric");
    }

    private String[] buildOriginalLineWithErrors(List<Integer> indexesForReplace, List<String> values) {
        String[] columns =
            new String[]{"234", "1984", "Appendix: The Principles of Newspeak", "9.78015E+12", "123456789",
                "1000009522", "Publisher", "12/22/3000", "65", "30.86", "Univ,Bus,Doc,S", "2015", "2016",
                "Aarseth, Espen J."};
        IntStream.range(0, indexesForReplace.size()).forEach(i -> columns[indexesForReplace.get(i)] = values.get(i));
        return columns;
    }

    private void isFilesEquals(String expectedFileName, String actualFileName) {
        assertTrue(new ReportMatcher(new File(PATH_TO_EXPECTED, expectedFileName))
            .matches(new File(PATH_TO_ACTUAL, actualFileName)));
    }

    private DistCsvProcessor.ProcessingResult<Usage> processFile(String file) throws IOException {
        return processFile(file, true);
    }

    private DistCsvProcessor.ProcessingResult<Usage> processFile(String file, boolean validateHeaders)
        throws IOException {
        DistCsvProcessor.ProcessingResult<Usage> result;
        try (InputStream stream = this.getClass()
            .getResourceAsStream("/com/copyright/rup/dist/foreign/service/csv/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            UsageCsvProcessor processor = new UsageCsvProcessor(PRODUCT_FAMILY);
            processor.setValidateHeaders(validateHeaders);
            result = processor.process(outputStream);
        }
        return result;
    }

    private void verifyUsage(Usage usage, Long detailId, Long wrWrkInst, Long rhAccountNumber, UsageStatusEnum status,
                             String title) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(detailId, usage.getDetailId());
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
        assertEquals(Long.valueOf(235), usage.getDetailId());
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
