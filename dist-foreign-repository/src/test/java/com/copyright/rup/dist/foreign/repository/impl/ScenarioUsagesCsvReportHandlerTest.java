package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.UsageDto;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public void testGetBeanProperties() {
        List<String> beanProperties = scenarioUsagesCsvReportHandler.getBeanProperties(buildUsageDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(27, CollectionUtils.size(beanProperties));
        assertEquals(getUsageDtoProperties(buildUsageDto()), beanProperties);
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

    private List<String> getUsageDtoProperties(UsageDto usageDto) {
        List<String> usageDtoProperties = new ArrayList<>();
        usageDtoProperties.add(usageDto.getId());
        usageDtoProperties.add(usageDto.getBatchName());
        usageDtoProperties.add(usageDto.getProductFamily());
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanFiscalYear(usageDto.getFiscalYear()));
        usageDtoProperties.add(usageDto.getRroAccountNumber().toString());
        usageDtoProperties.add(usageDto.getRroName());
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanLocalDate(usageDto.getPaymentDate()));
        usageDtoProperties.add(usageDto.getWorkTitle());
        usageDtoProperties.add(usageDto.getArticle());
        usageDtoProperties.add(usageDto.getStandardNumber());
        usageDtoProperties.add(usageDto.getWrWrkInst().toString());
        usageDtoProperties.add(usageDto.getRhAccountNumber().toString());
        usageDtoProperties.add(usageDto.getRhName());
        usageDtoProperties.add(usageDto.getPayeeAccountNumber().toString());
        usageDtoProperties.add(usageDto.getPayeeName());
        usageDtoProperties.add(usageDto.getPublisher());
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanLocalDate(usageDto.getPublicationDate()));
        usageDtoProperties.add(usageDto.getNumberOfCopies().toString());
        usageDtoProperties.add(usageDto.getReportedValue().toString());
        usageDtoProperties.add(usageDto.getGrossAmount().toString());
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanBigDecimal(usageDto.getServiceFeeAmount()));
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanBigDecimal(usageDto.getNetAmount()));
        usageDtoProperties.add(scenarioUsagesCsvReportHandler.getBeanServiceFeePercent(usageDto.getServiceFee()));
        usageDtoProperties.add(usageDto.getMarket());
        usageDtoProperties.add(usageDto.getMarketPeriodFrom().toString());
        usageDtoProperties.add(usageDto.getMarketPeriodTo().toString());
        usageDtoProperties.add(usageDto.getAuthor());
        return usageDtoProperties;
    }
}
