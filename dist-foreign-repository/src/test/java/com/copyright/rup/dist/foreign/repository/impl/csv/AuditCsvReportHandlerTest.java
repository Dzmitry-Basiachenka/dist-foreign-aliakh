package com.copyright.rup.dist.foreign.repository.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link AuditCsvReportHandler}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/23/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class AuditCsvReportHandlerTest {

    private AuditCsvReportHandler auditCsvReportHandler;

    @Before
    public void setUp() {
        auditCsvReportHandler = new AuditCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = auditCsvReportHandler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(21, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family",
            "Usage Batch Name", "Payment Date", "RH Account #", "RH Name", "Payee Account #", "Payee Name",
            "Wr Wrk Inst", "Title", "Standard Number", "Amt in USD", "Service Fee %", "Scenario Name",
            "Check #", "Check Date", "Event ID", "Dist. Name", "Dist. Date", "Period Ending"), beanHeaders);
    }

    @Test
    public void testGetBeanProperties() throws Exception {
        List<String> beanProperties = auditCsvReportHandler.getBeanProperties(buildUsageDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(21, CollectionUtils.size(beanProperties));
        assertEquals("2c7a9d3b-8506-49a9-b0bf-a7735e2cd906", beanProperties.get(0));
        assertEquals("NEW", beanProperties.get(1));
        assertEquals("FAS", beanProperties.get(2));
        assertEquals("testBatch", beanProperties.get(3));
        assertEquals("04/20/2018", beanProperties.get(4));
        assertEquals("1000009522", beanProperties.get(5));
        assertEquals("RhName", beanProperties.get(6));
        assertEquals("1234", beanProperties.get(7));
        assertEquals("payeeName", beanProperties.get(8));
        assertEquals("123456789", beanProperties.get(9));
        assertEquals("workTitle", beanProperties.get(10));
        assertEquals("9780", beanProperties.get(11));
        assertEquals("10.00", beanProperties.get(12));
        assertEquals("0.0", beanProperties.get(13));
        assertEquals("scenarioName", beanProperties.get(14));
        assertEquals("1234", beanProperties.get(15));
        assertEquals("04/21/2018", beanProperties.get(16));
        assertEquals("12345", beanProperties.get(17));
        assertEquals("distributionName", beanProperties.get(18));
        assertEquals("04/15/2018", beanProperties.get(19));
        assertEquals("04/23/2018", beanProperties.get(20));
    }

    private UsageDto buildUsageDto() {
        UsageDto usageDto = new UsageDto();
        usageDto.setId("2c7a9d3b-8506-49a9-b0bf-a7735e2cd906");
        usageDto.setStatus(UsageStatusEnum.NEW);
        usageDto.setProductFamily("FAS");
        usageDto.setBatchName("testBatch");
        usageDto.setPaymentDate(LocalDate.of(2018, 4, 20));
        usageDto.setRhAccountNumber(1000009522L);
        usageDto.setRhName("RhName");
        usageDto.setPayeeAccountNumber(1234L);
        usageDto.setPayeeName("payeeName");
        usageDto.setWrWrkInst(123456789L);
        usageDto.setWorkTitle("workTitle");
        usageDto.setStandardNumber("9780");
        usageDto.setGrossAmount(new BigDecimal("10.00"));
        usageDto.setServiceFee(new BigDecimal("0.0"));
        usageDto.setScenarioName("scenarioName");
        usageDto.setCheckNumber("1234");
        usageDto.setCheckDate(LocalDate.of(2018, 4, 21).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime());
        usageDto.setCccEventId("12345");
        usageDto.setDistributionName("distributionName");
        usageDto.setDistributionDate(LocalDate.of(2018, 4, 15).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime());
        usageDto.setPeriodEndDate(LocalDate.of(2018, 4, 23));
        return usageDto;
    }
}
