package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.impl.UsageRepository;
import com.copyright.rup.dist.foreign.service.impl.UsageService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@TransactionConfiguration
@Transactional
public class UsageCsvProcessorIntegrationTest {

    private static final String PATH_TO_ACTUAL = "build/temp";

    private UsageCsvProcessor processor;
    @Autowired
    private UsageCsvProcessorFactory usageCsvProcessorFactory;
    @Autowired
    private UsageRepository usageRepository;

    @BeforeClass
    public static void setUpTestDirectory() throws IOException {
        FileUtils.deleteQuietly(Paths.get(PATH_TO_ACTUAL).toFile());
        Files.createDirectory(Paths.get(PATH_TO_ACTUAL));
    }

    @Before
    public void setUp() {
        processor = usageCsvProcessorFactory.getProcessor();
    }

    @Test
    public void testProcessorForPositivePath() throws Exception {
        CsvProcessingResult<Usage> usageCsvProcessingResult = processFile("usages.csv");
        assertNotNull(usageCsvProcessingResult);
        assertTrue(usageCsvProcessingResult.isSuccessful());
        assertEquals(2, usageCsvProcessingResult.getResult().size());
        verifyUsage(usageCsvProcessingResult.getResult().get(0));
        verifyUsageWithEmptyFields(usageCsvProcessingResult.getResult().get(1));
    }

    @Test
    public void testProcessorForNegativePath() throws Exception {
        usageRepository.insert(buildUsage());
        CsvProcessingResult<Usage> result = processFile("usages_with_errors.csv");
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(outputStream);
        Executors.newSingleThreadExecutor().execute(() -> new UsageService().writeErrorsToFile(result, outputStream));
        File actual = new File(PATH_TO_ACTUAL, "errors_report.csv");
        FileUtils.copyInputStreamToFile(pipedInputStream, actual);
        File expected = new File("src/testInteg/resources/com/copyright/rup/dist/foreign/service/csv",
            "usages_with_errors_report.csv");
        isFilesEquals(expected, actual);
    }

    private void verifyUsage(Usage usage) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(Long.valueOf(234), usage.getDetailId());
        assertEquals("1984", usage.getWorkTitle());
        assertEquals("Appendix: The Principles of Newspeak", usage.getArticle());
        assertEquals("9780150000000", usage.getStandardNumber());
        assertEquals(Long.valueOf(123456789), usage.getWrWrkInst());
        assertEquals(Long.valueOf(1000009522), usage.getRightsholder().getAccountNumber());
        assertEquals("Publisher", usage.getPublisher());
        assertEquals(LocalDate.of(3000, 12, 22), usage.getPublicationDate());
        assertEquals(Integer.valueOf(65), usage.getNumberOfCopies());
        assertEquals(new BigDecimal("30.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2016), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals("Aarseth, Espen J.", usage.getAuthor());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
    }

    private void verifyUsageWithEmptyFields(Usage usage) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(Long.valueOf(235), usage.getDetailId());
        assertEquals("1984", usage.getWorkTitle());
        assertNull(usage.getArticle());
        assertEquals("9780150000000", usage.getStandardNumber());
        assertNull(usage.getWrWrkInst());
        assertNull(usage.getRightsholder().getAccountNumber());
        assertNull(usage.getPublisher());
        assertNull(usage.getPublicationDate());
        assertNull(usage.getNumberOfCopies());
        assertEquals(new BigDecimal("60.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertNull(usage.getAuthor());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
    }

    private CsvProcessingResult<Usage> processFile(String file) throws Exception {
        CsvProcessingResult<Usage> result;
        try (InputStream stream = this.getClass()
            .getResourceAsStream("/com/copyright/rup/dist/foreign/service/csv/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            result = processor.process(outputStream, file);
        }
        return result;
    }

    private void isFilesEquals(File expected, File actual) throws IOException {
        List<String> expectedLineList = FileUtils.readLines(expected);
        List<String> actualLineList = FileUtils.readLines(actual);
        assertEquals(expectedLineList.size(), actualLineList.size());
        for (int i = 0; i < expectedLineList.size(); i++) {
            assertEquals("Line: " + i, expectedLineList.get(i), actualLineList.get(i));
        }
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId("d9d4b36f-0149-4ec8-a5b9-663eaa027b89");
        usage.setBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        usage.setDetailId(200L);
        usage.setWrWrkInst(101125380L);
        usage.setWorkTitle("Understanding administrative law");
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(1000006746L);
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setArticle("Fox, William F.");
        usage.setStandardNumber("1008902112365655XX");
        usage.setPublisher("IEEE");
        usage.setPublicationDate(LocalDate.of(2013, 9, 10));
        usage.setMarket("Doc Del");
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setNumberOfCopies(250);
        usage.setReportedValue(new BigDecimal(2500));
        return usage;
    }
}
