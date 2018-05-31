package com.copyright.rup.dist.foreign.repository.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.report.BatchSummaryReportDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link BatchSummaryReportHandler}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/31/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class BatchSummaryReportHandlerTest {

    private BatchSummaryReportHandler handler;

    @Before
    public void setUp() {
        handler = new BatchSummaryReportHandler(new ByteArrayOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = handler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(16, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Usage Batch Name", "RRO Account Number", "RRO Name", "Payment Date",
            "Gross Fund Pool in USD", "# non-Eligible Details", "Gross USD non-Eligible Details", "# Details NTS",
            "Gross USD NTS", "# FAS/CLA_FAS Eligible Details", "Gross FAS/CLA_FAS Eligible USD",
            "# Details in-progress Scenarios", "Gross USD in-progress Scenarios", "Royalty Payable USD",
            "# Details Return to CLA", "Gross USD Return to CLA"), beanHeaders);
    }

    @Test
    public void testGetBeanProperties() {
        List<String> beanProperties = handler.getBeanProperties(buildBatchSummaryReportDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(16, CollectionUtils.size(beanProperties));
        assertEquals("Batch", beanProperties.get(0));
        assertEquals("2000017004", beanProperties.get(1));
        assertEquals("Access Copyright, The Canadian Copyright Agency", beanProperties.get(2));
        assertEquals("04/20/2018", beanProperties.get(3));
        assertEquals("5000.01124549", beanProperties.get(4));
        assertEquals("100", beanProperties.get(5));
        assertEquals("1000.20", beanProperties.get(6));
        assertEquals("10", beanProperties.get(7));
        assertEquals("100.20", beanProperties.get(8));
        assertEquals("200", beanProperties.get(9));
        assertEquals("2000.77", beanProperties.get(10));
        assertEquals("34", beanProperties.get(11));
        assertEquals("847.77", beanProperties.get(12));
        assertEquals("4200.44", beanProperties.get(13));
        assertEquals("4", beanProperties.get(14));
        assertEquals("88.31", beanProperties.get(15));
    }

    private BatchSummaryReportDto buildBatchSummaryReportDto() {
        BatchSummaryReportDto reportDto = new BatchSummaryReportDto();
        reportDto.setBatchName("Batch");
        reportDto.setRroAccountNumber(2000017004L);
        reportDto.setRroName("Access Copyright, The Canadian Copyright Agency");
        reportDto.setPaymentDate(LocalDate.of(2018, 4, 20));
        reportDto.setGrossAmount(new BigDecimal("5000.01124549"));
        reportDto.setNonEligibleDetailsCount(100);
        reportDto.setNonEligibleDetailsGrossAmount(new BigDecimal("1000.20"));
        reportDto.setNtsDetailsCount(10);
        reportDto.setNtsDetailsGrossAmount(new BigDecimal("100.20"));
        reportDto.setFasAndClaFasEligibleDetailsCount(200);
        reportDto.setFasAndClaFasEligibleDetailsGrossAmount(new BigDecimal("2000.77"));
        reportDto.setScenariosDetailsCount(34);
        reportDto.setScenariosDetailsGrossAmount(new BigDecimal("847.77"));
        reportDto.setScenariosDetailsNetAmount(new BigDecimal("4200.44"));
        reportDto.setReturnToClaDetailsCount(4);
        reportDto.setReturnToClaDetailsGrossAmount(new BigDecimal("88.31"));
        return reportDto;
    }
}
