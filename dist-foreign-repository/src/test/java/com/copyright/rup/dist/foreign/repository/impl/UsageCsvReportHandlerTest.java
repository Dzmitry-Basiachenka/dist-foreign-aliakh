package com.copyright.rup.dist.foreign.repository.impl;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link UsageCsvReportHandler}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/19/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class UsageCsvReportHandlerTest {

    private UsageCsvReportHandler usagesCsvReportHandler;

    @Before
    public void setUp() {
        usagesCsvReportHandler = new UsageCsvReportHandler(new PipedOutputStream());
    }

    @Test
    public void testGetBeanHeaders() {
        List<String> beanHeaders = usagesCsvReportHandler.getBeanHeaders();
        assertTrue(CollectionUtils.isNotEmpty(beanHeaders));
        assertEquals(24, CollectionUtils.size(beanHeaders));
        assertEquals(Arrays.asList("Detail ID", "Detail Status", "Product Family", "Usage Batch Name", "Fiscal Year",
            "RRO Account #", "RRO Name", "Payment Date", "Title", "Article", "Standard Number", "Wr Wrk Inst",
            "RH Account #", "RH Name", "Publisher", "Pub Date", "Number of Copies", "Reported value", "Amt in USD",
            "Gross Amt in USD", "Market", "Market Period From", "Market Period To", "Author"), beanHeaders);
    }

    @Test
    public void testGetBeanProperties() {
        List<String> beanProperties = usagesCsvReportHandler.getBeanProperties(buildUsageDto());
        assertTrue(CollectionUtils.isNotEmpty(beanProperties));
        assertEquals(24, CollectionUtils.size(beanProperties));
        assertEquals(getUsageDtoProperties(buildUsageDto()), beanProperties);
    }

    private UsageDto buildUsageDto() {
        UsageDto usageDto = new UsageDto();
        usageDto.setId("2c7a9d3b-8506-49a9-b0bf-a7735e2cd906");
        usageDto.setStatus(UsageStatusEnum.NEW);
        usageDto.setProductFamily("FAS");
        usageDto.setBatchName("testBatch");
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
        usageDto.setPublisher("publisher");
        usageDto.setPublicationDate(LocalDate.of(2010, 4, 20));
        usageDto.setNumberOfCopies(5);
        usageDto.setReportedValue(new BigDecimal("30.86"));
        usageDto.setGrossAmount(new BigDecimal("10.00"));
        usageDto.setBatchGrossAmount(new BigDecimal("30.00"));
        usageDto.setMarket("Univ");
        usageDto.setMarketPeriodFrom(2015);
        usageDto.setMarketPeriodTo(2015);
        usageDto.setAuthor("Aarseth, Espen J.");
        return usageDto;
    }

    private List<String> getUsageDtoProperties(UsageDto usageDto) {
        List<String> usageDtoProperties = new ArrayList<>();
        usageDtoProperties.add(usageDto.getId());
        usageDtoProperties.add(usageDto.getStatus().name());
        usageDtoProperties.add(usageDto.getProductFamily());
        usageDtoProperties.add(usageDto.getBatchName());
        usageDtoProperties.add(usagesCsvReportHandler.getBeanFiscalYear(usageDto.getFiscalYear()));
        usageDtoProperties.add(usageDto.getRroAccountNumber().toString());
        usageDtoProperties.add(usageDto.getRroName());
        usageDtoProperties.add(usagesCsvReportHandler.getBeanLocalDate(usageDto.getPaymentDate()));
        usageDtoProperties.add(usageDto.getWorkTitle());
        usageDtoProperties.add(usageDto.getArticle());
        usageDtoProperties.add(usageDto.getStandardNumber());
        usageDtoProperties.add(usageDto.getWrWrkInst().toString());
        usageDtoProperties.add(usageDto.getRhAccountNumber().toString());
        usageDtoProperties.add(usageDto.getRhName());
        usageDtoProperties.add(usageDto.getPublisher());
        usageDtoProperties.add(usagesCsvReportHandler.getBeanLocalDate(usageDto.getPublicationDate()));
        usageDtoProperties.add(usageDto.getNumberOfCopies().toString());
        usageDtoProperties.add(usageDto.getReportedValue().toString());
        usageDtoProperties.add(usageDto.getGrossAmount().toString());
        usageDtoProperties.add(usageDto.getBatchGrossAmount().toString());
        usageDtoProperties.add(usageDto.getMarket());
        usageDtoProperties.add(usageDto.getMarketPeriodFrom().toString());
        usageDtoProperties.add(usageDto.getMarketPeriodTo().toString());
        usageDtoProperties.add(usageDto.getAuthor());
        return usageDtoProperties;
    }
}
