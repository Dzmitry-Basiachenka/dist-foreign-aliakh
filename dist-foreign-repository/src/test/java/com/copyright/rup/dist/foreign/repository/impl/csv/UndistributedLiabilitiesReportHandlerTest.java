package com.copyright.rup.dist.foreign.repository.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.report.UndistributedLiabilitiesReportDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UndistributedLiabilitiesReportHandler}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/23/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportHandlerTest {

    private UndistributedLiabilitiesReportHandler handler;

    @Before
    public void setUp() {
        handler = new UndistributedLiabilitiesReportHandler(new ByteArrayOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = handler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(16, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Source RRO Account #", "Source RRO Name", "Payment Date",
            "Gross Undistributed Amount in FDA", "Estimated Service Fee Amount", "Net Estimated Payable Amount",
            "Estimated Service Fee %", "Total FAS Gross Amount Sent to LM", "Total Actual Service Fee Amount in LM",
            "Net Amount Sent to LM", "Actual Service Fee %", "Service Fee True-Up", "Return to CLA Service Fee True-up",
            "Gross Amount Return to CLA", "Total CLA Service Fee Amount", "Net Total Amount Return to CLA"),
            beanHeaders);
    }

    @Test
    public void testGetBeanProperties() {
        List<String> beanProperties = handler.getBeanProperties(buildUndistributedLiabilitiesReportDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(16, CollectionUtils.size(beanProperties));
        assertEquals("2000017004", beanProperties.get(0));
        assertEquals("Access Copyright, The Canadian Copyright Agency", beanProperties.get(1));
        assertEquals("04/20/2018", beanProperties.get(2));
        assertEquals("5000.01124549", beanProperties.get(3));
        assertEquals("950.00", beanProperties.get(4));
        assertEquals("4050.00", beanProperties.get(5));
        assertEquals("19.0", beanProperties.get(6));
        assertEquals("11000.00", beanProperties.get(7));
        assertEquals("1200.00", beanProperties.get(8));
        assertEquals("9900.00", beanProperties.get(9));
        assertEquals("10.0", beanProperties.get(10));
        assertEquals("-150", beanProperties.get(11));
        assertEquals("990", beanProperties.get(12));
        assertEquals("13000.00", beanProperties.get(13));
        assertEquals("1400.00", beanProperties.get(14));
        assertEquals("99000.00", beanProperties.get(15));
    }

    private UndistributedLiabilitiesReportDto buildUndistributedLiabilitiesReportDto() {
        UndistributedLiabilitiesReportDto info = new UndistributedLiabilitiesReportDto();
        info.setRroAccountNumber(2000017004L);
        info.setRroName("Access Copyright, The Canadian Copyright Agency");
        info.setPaymentDate(LocalDate.of(2018, 4, 20));
        info.setGrossAmount(new BigDecimal("5000.01124549"));
        info.setEstimatedServiceFeeAmount(new BigDecimal("950.00"));
        info.setEstimatedNetAmount(new BigDecimal("4050.00"));
        info.setEstimatedServiceFee(new BigDecimal("0.19000"));
        info.setGrossAmountSentToLm(new BigDecimal("11000.00"));
        info.setServiceFeeAmountSentToLm(new BigDecimal("1200.00"));
        info.setNetAmountSentToLm(new BigDecimal("9900.00"));
        info.setServiceFee(new BigDecimal("0.10"));
        info.setServiceFeeTrueUp(new BigDecimal("-150"));
        info.setServiceFeeTrueUpReturnToCla(new BigDecimal("990"));
        info.setGrossAmountReturnToCla(new BigDecimal("13000.00"));
        info.setServiceFeeAmountReturnToCla(new BigDecimal("1400.00"));
        info.setNetAmountReturnToCla(new BigDecimal("99000.00"));
        return info;
    }
}
