package com.copyright.rup.dist.foreign.repository.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.report.ResearchStatusReportDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link ResearchStatusReportHandler}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 05/31/2018
 *
 * @author Ihar Suvorau
 */
public class ResearchStatusReportHandlerTest {

    private ResearchStatusReportHandler handler;

    @Before
    public void setUp() {
        handler = new ResearchStatusReportHandler(new ByteArrayOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = handler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(12, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Usage Batch Name", "RRO Account Number", "RRO Name", "Payment Date",
            "# Details Work Not Found", "Gross USD Work Not Found", "# Details Work Research",
            "Gross USD Work Research", "# Details Sent for RA", "Gross USD Sent for RA", "# Details RH Not Found",
            "Gross USD RH Not Found"), beanHeaders);
    }

    @Test
    public void testGetBeanProperties() {
        List<String> beanProperties = handler.getBeanProperties(buildResearchStatusReportDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(12, CollectionUtils.size(beanProperties));
        assertEquals("Batch Name", beanProperties.get(0));
        assertEquals("2000017004", beanProperties.get(1));
        assertEquals("Access Copyright, The Canadian Copyright Agency", beanProperties.get(2));
        assertEquals("04/20/2018", beanProperties.get(3));
        assertEquals("5", beanProperties.get(4));
        assertEquals("950.00", beanProperties.get(5));
        assertEquals("150", beanProperties.get(6));
        assertEquals("11020.50", beanProperties.get(7));
        assertEquals("0", beanProperties.get(8));
        assertEquals("0.00", beanProperties.get(9));
        assertEquals("1", beanProperties.get(10));
        assertEquals("5200.00", beanProperties.get(11));
    }

    private ResearchStatusReportDto buildResearchStatusReportDto() {
        ResearchStatusReportDto info = new ResearchStatusReportDto();
        info.setBatchName("Batch Name");
        info.setRroAccountNumber(2000017004L);
        info.setRroName("Access Copyright, The Canadian Copyright Agency");
        info.setPaymentDate(LocalDate.of(2018, 4, 20));
        info.setWorkNotFoundDetailsCount(5);
        info.setWorkNotFoundGrossAmount(new BigDecimal("950.00"));
        info.setWorkResearchDetailsCount(150);
        info.setWorkResearchGrossAmount(new BigDecimal("11020.50"));
        info.setSendForRaDetailsCount(0);
        info.setRhNotFoundDetailsCount(1);
        info.setRhNotFoundGrossAmount(new BigDecimal("5200.00"));
        return info;
    }
}
