package com.copyright.rup.dist.foreign.repository.impl.csv;

import com.copyright.rup.dist.common.test.ReportTestUtils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Common CSV reports test helper.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/02/21
 *
 * @author Anton Azarenka
 */
public class CsvReportsTestHelper {

    protected static final String SEARCH_WITH_PERCENT = "%";
    protected static final String SEARCH_WITH_UNDERSCORE = "_";
    protected static final BigDecimal DEFAULT_ESTIMATED_SERVICE_FEE = new BigDecimal("0.18500");

    private final ReportTestUtils reportTestUtils =
        new ReportTestUtils("src/testInteg/resources/com/copyright/rup/dist/foreign/repository/impl/csv");

    protected void assertFilesWithExecutor(Consumer<PipedOutputStream> reportWriter, String fileName)
        throws IOException {
        try (PipedOutputStream outputStream = new PipedOutputStream();
             PipedInputStream inputStream = new PipedInputStream(outputStream)) {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Future<?> writeReportFuture = executorService.submit(() -> reportWriter.accept(outputStream));
            Future<byte[]> readReportFuture = executorService.submit(() -> {
                try {
                    return IOUtils.toByteArray(inputStream);
                } catch (IOException e) {
                    throw new AssertionError(e);
                }
            });
            byte[] actualReportBytes;
            writeReportFuture.get(10, TimeUnit.SECONDS);
            actualReportBytes = readReportFuture.get(10, TimeUnit.SECONDS);
            reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(actualReportBytes));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new AssertionError(e);
        }
    }

    protected void assertFiles(Consumer<ByteArrayOutputStream> reportWriter, String fileName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        reportWriter.accept(outputStream);
        reportTestUtils.assertCsvReport(fileName, new ByteArrayInputStream(outputStream.toByteArray()));
    }
}
