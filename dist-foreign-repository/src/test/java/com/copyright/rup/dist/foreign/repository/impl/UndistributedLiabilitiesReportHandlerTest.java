package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UndistributedLiabilitiesReportDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
        UndistributedLiabilitiesReportDto reportDto = buildUndistributedLiabilitiesReportDto();
        List<String> beanProperties = handler.getBeanProperties(reportDto);
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(16, CollectionUtils.size(beanProperties));
        assertEquals(getBeanProperties(reportDto), beanProperties);
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
        info.setServiceFeeAmountSentToLm(new BigDecimal("1100.00"));
        info.setNetAmountSentToLm(new BigDecimal("9900.00"));
        info.setServiceFee(new BigDecimal("10.00"));
        info.setServiceFeeTrueUp(new BigDecimal("-150"));
        info.setServiceFeeTrueUpReturnToCla(new BigDecimal("990"));
        info.setGrossAmountReturnToCla(new BigDecimal("11000.00"));
        info.setServiceFeeAmountReturnToCla(new BigDecimal("1100.00"));
        info.setNetAmountReturnToCla(new BigDecimal("9900.00"));
        return info;
    }

    private List<String> getBeanProperties(UndistributedLiabilitiesReportDto bean) {
        List<String> beanProperties = new ArrayList<>();
        beanProperties.add(handler.getBeanPropertyAsString(bean.getRroAccountNumber()));
        beanProperties.add(bean.getRroName());
        beanProperties.add(handler.getBeanLocalDate(bean.getPaymentDate()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getGrossAmount()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getEstimatedServiceFeeAmount()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getEstimatedNetAmount()));
        beanProperties.add(handler.getBeanServiceFeePercent(bean.getEstimatedServiceFee()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getGrossAmountSentToLm()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getServiceFeeAmountSentToLm()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getNetAmountSentToLm()));
        beanProperties.add(handler.getBeanServiceFeePercent(bean.getServiceFee()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getServiceFeeTrueUp()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getServiceFeeTrueUpReturnToCla()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getGrossAmountReturnToCla()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getServiceFeeAmountReturnToCla()));
        beanProperties.add(handler.getBeanBigDecimal(bean.getNetAmountReturnToCla()));
        return beanProperties;
    }
}
