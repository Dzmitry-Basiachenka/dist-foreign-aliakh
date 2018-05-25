package com.copyright.rup.dist.foreign.repository.impl.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link ScenarioUsagesCsvReportHandler}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/20/17
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioUsagesCsvReportHandlerTest {

    private ScenarioUsagesCsvReportHandler scenarioUsagesCsvReportHandler;

    @Before
    public void setUp() {
        scenarioUsagesCsvReportHandler = new ScenarioUsagesCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = scenarioUsagesCsvReportHandler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(27, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Detail ID", "Usage Batch Name", "Product Family", "Fiscal Year",
            "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst",
            "RH Account #", "RH Name", "Payee Account #", "Payee Name", "Publisher", "Pub Date", "Number of Copies",
            "Reported value", "Gross Amt in USD", "Service Fee Amount", "Net Amt in USD", "Service Fee %", "Market",
            "Market Period From", "Market Period To", "Author"), beanHeaders);
    }

    @Test
    public void testGetBeanProperties() throws Exception {
        List<String> beanProperties = scenarioUsagesCsvReportHandler.getBeanProperties(buildUsageDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(27, CollectionUtils.size(beanProperties));
        assertEquals("2c7a9d3b-8506-49a9-b0bf-a7735e2cd906", beanProperties.get(0));
        assertEquals("testBatch", beanProperties.get(1));
        assertEquals("FAS", beanProperties.get(2));
        assertEquals("FY2018", beanProperties.get(3));
        assertEquals("2000017004", beanProperties.get(4));
        assertEquals("Access Copyright, The Canadian Copyright Agency", beanProperties.get(5));
        assertEquals("04/18/2018", beanProperties.get(6));
        assertEquals("workTitle", beanProperties.get(7));
        assertEquals("Appendix: The Principles of Newspeak", beanProperties.get(8));
        assertEquals("9780", beanProperties.get(9));
        assertEquals("123456789", beanProperties.get(10));
        assertEquals("1000009522", beanProperties.get(11));
        assertEquals("RhName", beanProperties.get(12));
        assertEquals("1234", beanProperties.get(13));
        assertEquals("payeeName", beanProperties.get(14));
        assertEquals("publisher", beanProperties.get(15));
        assertEquals("04/20/2010", beanProperties.get(16));
        assertEquals("5", beanProperties.get(17));
        assertEquals("30.86", beanProperties.get(18));
        assertEquals("10.00", beanProperties.get(19));
        assertEquals("30.00", beanProperties.get(20));
        assertEquals("14.30", beanProperties.get(21));
        assertEquals("0.0", beanProperties.get(22));
        assertEquals("Univ", beanProperties.get(23));
        assertEquals("2015", beanProperties.get(24));
        assertEquals("2015", beanProperties.get(25));
        assertEquals("Aarseth, Espen J.", beanProperties.get(26));
    }

    private UsageDto buildUsageDto() {
        UsageDto usageDto = new UsageDto();
        usageDto.setId("2c7a9d3b-8506-49a9-b0bf-a7735e2cd906");
        usageDto.setBatchName("testBatch");
        usageDto.setProductFamily("FAS");
        usageDto.setFiscalYear(2018);
        usageDto.setRroAccountNumber(2000017004L);
        usageDto.setRroName("Access Copyright, The Canadian Copyright Agency");
        usageDto.setPaymentDate(LocalDate.of(2018, 4, 18));
        usageDto.setWorkTitle("workTitle");
        usageDto.setArticle("Appendix: The Principles of Newspeak");
        usageDto.setStandardNumber("9780");
        usageDto.setWrWrkInst(123456789L);
        usageDto.setRhAccountNumber(1000009522L);
        usageDto.setRhName("RhName");
        usageDto.setPayeeAccountNumber(1234L);
        usageDto.setPayeeName("payeeName");
        usageDto.setPublisher("publisher");
        usageDto.setPublicationDate(LocalDate.of(2010, 4, 20));
        usageDto.setNumberOfCopies(5);
        usageDto.setReportedValue(new BigDecimal("30.86"));
        usageDto.setGrossAmount(new BigDecimal("10.00"));
        usageDto.setServiceFeeAmount(new BigDecimal("30.00"));
        usageDto.setNetAmount(new BigDecimal("14.30"));
        usageDto.setServiceFee(new BigDecimal("0.0"));
        usageDto.setMarket("Univ");
        usageDto.setMarketPeriodFrom(2015);
        usageDto.setMarketPeriodTo(2015);
        usageDto.setAuthor("Aarseth, Espen J.");
        return usageDto;
    }
}
