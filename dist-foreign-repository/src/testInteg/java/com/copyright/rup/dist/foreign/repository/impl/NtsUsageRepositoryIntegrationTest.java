package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * Integration test for {@link NtsUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=nts-usage-repository-test-data-init.groovy"})
@Rollback
@Transactional
public class NtsUsageRepositoryIntegrationTest {

    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String BATCH_ID = "b9d0ea49-9e38-4bb0-a7e0-0ca299e3dcfa";
    private static final String WORK_TITLE_1 = "Our fathers lies";
    private static final String WORK_TITLE_2 = "100 ROAD MOVIES";
    private static final String BUS_MARKET = "Bus";
    private static final String DOC_DEL_MARKET = "Doc Del";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0000000000");
    private static final BigDecimal HUNDRED_AMOUNT = new BigDecimal("100.00");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private UsageRepository usageRepository;

    @Autowired
    private NtsUsageRepository ntsUsageRepository;

    @Test
    public void testInsertUsages() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setFundPool(buildFundPool(HUNDRED_AMOUNT));
        List<String> insertedUsageIds = ntsUsageRepository.insertUsages(usageBatch, USER_NAME);
        assertEquals(3, insertedUsageIds.size());
        List<Usage> insertedUsages = usageRepository.findByIds(insertedUsageIds);
        insertedUsages.sort(Comparator.comparing(Usage::getMarketPeriodFrom));
        verifyInsertedFundPoolUsage(243904752L, WORK_TITLE_2, DOC_DEL_MARKET, 2013, new BigDecimal("1176.92"),
            insertedUsages.get(0));
        verifyInsertedFundPoolUsage(105062654L, WORK_TITLE_1, BUS_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(1));
        verifyInsertedFundPoolUsage(243904752L, WORK_TITLE_2, BUS_MARKET, 2016, new BigDecimal("500.00"),
            insertedUsages.get(2));
    }

    @Test
    public void testInsertUsagesZeroFundPoolAmount() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setFundPool(buildFundPool(BigDecimal.ZERO));
        List<String> insertedUsageIds = ntsUsageRepository.insertUsages(usageBatch, USER_NAME);
        assertEquals(1, insertedUsageIds.size());
        List<Usage> insertedUsages = usageRepository.findByIds(insertedUsageIds);
        insertedUsages.sort(Comparator.comparing(Usage::getMarketPeriodFrom));
        verifyInsertedFundPoolUsage(105062654L, WORK_TITLE_1, BUS_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(0));
    }

    private FundPool buildFundPool(BigDecimal nonStmAmount) {
        FundPool fundPool = new FundPool();
        fundPool.setMarkets(Sets.newHashSet(BUS_MARKET, DOC_DEL_MARKET));
        fundPool.setFundPoolPeriodFrom(2015);
        fundPool.setFundPoolPeriodTo(2016);
        fundPool.setStmAmount(HUNDRED_AMOUNT);
        fundPool.setStmMinimumAmount(new BigDecimal("50.00"));
        fundPool.setNonStmAmount(nonStmAmount);
        fundPool.setNonStmMinimumAmount(new BigDecimal("7.00"));
        return fundPool;
    }

    private void verifyInsertedFundPoolUsage(Long wrWrkInst, String workTitle, String market, Integer marketPeriodFrom,
                                             BigDecimal reportedValue, Usage actualUsage) {
        assertEquals(BATCH_ID, actualUsage.getBatchId());
        assertEquals(wrWrkInst, actualUsage.getWrWrkInst(), 0);
        assertEquals(workTitle, actualUsage.getWorkTitle());
        assertEquals(workTitle, actualUsage.getSystemTitle());
        assertEquals(UsageStatusEnum.WORK_FOUND, actualUsage.getStatus());
        assertEquals(NTS_PRODUCT_FAMILY, actualUsage.getProductFamily());
        assertEquals("1008902112317555XX", actualUsage.getStandardNumber());
        assertEquals("VALISBN13", actualUsage.getStandardNumberType());
        assertEquals(market, actualUsage.getMarket());
        assertEquals(marketPeriodFrom, actualUsage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2017), actualUsage.getMarketPeriodTo());
        assertEquals(ZERO_AMOUNT, actualUsage.getGrossAmount());
        assertEquals(reportedValue, actualUsage.getReportedValue());
        assertEquals("test comment", actualUsage.getComment());
        assertEquals(USER_NAME, actualUsage.getCreateUser());
    }
}
