package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.ReportMatcher;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult.ErrorRow;

import com.google.common.collect.Lists;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Verifies {@link CsvErrorResultWriter}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/22/17
 *
 * @author Ihar Suvorau
 */
public class UsageServiceIntegrationTest {

    private static final String PATH_TO_ACTUAL_REPORT = "build/temp";
    private static final String FILE_NAME = "errors_report.csv";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private CsvProcessingResult<String> result;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL_REPORT).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL_REPORT));
    }

    @Test
    public void testWriteErrorsCsvReport() throws Exception {
        IUsageService usageService = new UsageService();
        List<String> headers = buildHeaders();
        result = new CsvProcessingResult<>(headers, "usages.csv");
        logErrors();
        List<ErrorRow> errors = result.getErrors();
        assertEquals(4, errors.size());
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        executorService.execute(() -> usageService.writeErrorsCsvReport(result, outputStream));
        FileUtils.copyInputStreamToFile(pipedInputStream, new File(PATH_TO_ACTUAL_REPORT, FILE_NAME));
        verifyReport();
    }

    private void logErrors() {
        result.logError(2, buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList(StringUtils.EMPTY)),
            "Detail ID: Field is required and cannot be null or empty");
        result.logError(5, buildOriginalLineWithErrors(Lists.newArrayList(5), Lists.newArrayList("text")),
            "RH Acct Number: Field value should be numeric");
        List<String> lines =
            buildOriginalLineWithErrors(Lists.newArrayList(0), Lists.newArrayList("text more then 10 symbols"));
        result.logError(3, lines, "Detail ID: Field value should be numeric");
        result.logError(3, lines, "Detail ID: Field max length is 10 characters");
        lines = buildOriginalLineWithErrors(Lists.newArrayList(8, 4), Lists.newArrayList("44.123", "text"));
        result.logError(7, lines, "Reported Value: Field value must be Numeric with decimals to 2 places");
        result.logError(7, lines, "Wr Wrk Inst: Field value should be numeric");
    }

    private List<String> buildOriginalLineWithErrors(List<Integer> indexesForReplace, List<String> values) {
        List<String> columns =
            Lists.newArrayList("234", "1984", "Appendix: The Principles of Newspeak", "9.78015E+12", "123456789",
                "1000009522", "Publisher", "12/22/3000", "65", "30.86", "Univ,Bus,Doc,S", "2015", "2016",
                "Aarseth, Espen J.");
        for (int i = 0; i < indexesForReplace.size(); i++) {
            columns.set(indexesForReplace.get(i), values.get(i));
        }
        return columns;
    }

    private List<String> buildHeaders() {
        List<String> headers =
            Lists.newArrayList("Detail ID", "Title", "Article", "Standard Number", "Wr Wrk Inst", "RH Acct Number",
                "Publisher", "Pub Date", "Number of Copies", "Reported Value", "Market", "Market Period From",
                "Market Period To", "Author");
        return headers;
    }

    private void verifyReport() {
        assertTrue(new ReportMatcher(
            new File("src/testInteg/resources/com/copyright/rup/dist/foreign/service/csv", FILE_NAME)).matches(
            new File(PATH_TO_ACTUAL_REPORT, FILE_NAME)));
    }
}
