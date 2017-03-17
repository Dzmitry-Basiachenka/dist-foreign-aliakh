package com.copyright.rup.dist.foreign.service.impl.csvprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    private UsageCsvProcessor processor;

    @Before
    public void setUp() {
        processor = new UsageCsvProcessor();
    }

    @Test
    public void testProcessorForPositivePath() throws Exception {
        CsvProcessingResult<Usage> result = processFile("usages.csv");
        assertEquals(2, result.getResult().size());
        assertTrue(result.isSuccessful());
        verifyUsage(result.getResult().get(0));
        verifyUsageWithEmptyFields(result.getResult().get(1));
    }

    private void verifyUsage(Usage usage) {
        assertNotNull(usage);
        assertNotNull(usage.getId());
        assertEquals(Long.valueOf(234), usage.getDetailId());
        assertEquals("1984", usage.getWorkTitle());
        assertEquals("Appendix: The Principles of Newspeak", usage.getArticle());
        assertEquals("9.78015E+12", usage.getStandardNumber());
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
        assertEquals(null, usage.getArticle());
        assertEquals("9.78015E+12", usage.getStandardNumber());
        assertEquals(Long.valueOf(123456789), usage.getWrWrkInst());
        assertEquals(Long.valueOf(1000009522), usage.getRightsholder().getAccountNumber());
        assertEquals(null, usage.getPublisher());
        assertEquals(null, usage.getPublicationDate());
        assertEquals(null, usage.getNumberOfCopies());
        assertEquals(new BigDecimal("60.86"), usage.getReportedValue());
        assertEquals("Univ,Bus,Doc,S", usage.getMarket());
        assertEquals(Integer.valueOf(2015), usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2016), usage.getMarketPeriodTo());
        assertEquals(UsageStatusEnum.ELIGIBLE, usage.getStatus());
        assertEquals(null, usage.getAuthor());
        assertEquals(BigDecimal.ZERO, usage.getGrossAmount());
    }

    private CsvProcessingResult<Usage> processFile(String file) throws Exception {
        CsvProcessingResult<Usage> result;
        try (InputStream stream = this.getClass().getResourceAsStream(
            "/com/copyright/rup/dist/foreign/service/csv/" + file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            IOUtils.copy(stream, outputStream);
            result = processor.process(outputStream);
        }
        return result;
    }
}
