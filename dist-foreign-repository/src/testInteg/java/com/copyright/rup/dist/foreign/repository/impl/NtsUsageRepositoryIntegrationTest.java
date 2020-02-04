package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private static final String SCENARIO_ID = "ca163655-8978-4a45-8fe3-c3b5572c6879";
    private static final String USAGE_ID_1 = "ba95f0b3-dc94-4925-96f2-93d05db9c469";
    private static final String USAGE_ID_2 = "c09aa888-85a5-4377-8c7a-85d84d255b5a";
    private static final String USAGE_ID_3 = "45445974-5bee-477a-858b-e9e8c1a642b8";
    private static final String USAGE_ID_BELLETRISTIC = "bbbd64db-2668-499a-9d18-be8b3f87fbf5";
    private static final String USAGE_ID_UNCLASSIFIED = "6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c";
    private static final String USAGE_ID_STM = "83a26087-a3b3-43ca-8b34-c66134fb6edf";
    private static final String WORK_TITLE_1 = "Our fathers lies";
    private static final String WORK_TITLE_2 = "100 ROAD MOVIES";
    private static final String EDU_MARKET = "Edu";
    private static final String GOV_MARKET = "Gov";
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
        verifyInsertedUsage(243904752L, WORK_TITLE_2, GOV_MARKET, 2013, new BigDecimal("1176.92"),
            insertedUsages.get(0));
        verifyInsertedUsage(105062654L, WORK_TITLE_1, EDU_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(1));
        verifyInsertedUsage(243904752L, WORK_TITLE_2, EDU_MARKET, 2016, new BigDecimal("500.00"),
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
        verifyInsertedUsage(105062654L, WORK_TITLE_1, EDU_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(0));
    }

    @Test
    public void testFindCountForBatch() {
        assertEquals(2, ntsUsageRepository.findCountForBatch(2015, 2016, Sets.newHashSet("Bus", "Doc Del")));
    }

    @Test
    public void testDeleteFromAdditionalFund() {
        String fundPoolId = "3fef25b0-c0d1-4819-887f-4c6acc01390e";
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(USAGE_ID_1));
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(UsageStatusEnum.TO_BE_DISTRIBUTED, usage.getStatus());
        assertEquals(fundPoolId, usage.getFundPoolId());
        ntsUsageRepository.deleteFromPreServiceFeeFund(fundPoolId, USER_NAME);
        usage = usageRepository.findByIds(Collections.singletonList(USAGE_ID_1)).get(0);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertNull(usage.getFundPoolId());
    }

    @Test
    public void testDeleteBelletristicByScenarioId() {
        String scenarioId = "dd4fca1d-eac8-4b76-85e4-121b7971d049";
        verifyUsageIdsInScenario(Arrays.asList(USAGE_ID_BELLETRISTIC, USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertEquals(1, usageRepository.findByIds(Collections.singletonList(USAGE_ID_BELLETRISTIC)).size());
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
        verifyUsageIdsInScenario(Arrays.asList(USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertEquals(0, usageRepository.findByIds(Collections.singletonList(USAGE_ID_BELLETRISTIC)).size());
        assertEquals(0, usageRepository.findReferencedFasUsagesCountByIds(USAGE_ID_BELLETRISTIC));
    }

    @Test
    public void testDeleteFromNtsScenario() {
        List<Usage> usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_2, USAGE_ID_3));
        assertEquals(2, usages.size());
        verifyUsage(usages.get(0), UsageStatusEnum.NTS_EXCLUDED, null, StoredEntity.DEFAULT_USER, ZERO_AMOUNT,
            HUNDRED_AMOUNT, null, ZERO_AMOUNT, ZERO_AMOUNT);
        verifyUsage(usages.get(1), UsageStatusEnum.LOCKED, SCENARIO_ID, StoredEntity.DEFAULT_USER,
            new BigDecimal("900.0000000000"), new BigDecimal("900.00"), new BigDecimal("0.32000"),
            new BigDecimal("288.0000000000"), new BigDecimal("612.0000000000"));
        ntsUsageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        usages = usageRepository.findByIds(Arrays.asList(USAGE_ID_2, USAGE_ID_3));
        assertEquals(2, usages.size());
        verifyUsage(usages.get(0), UsageStatusEnum.ELIGIBLE, null, USER_NAME, ZERO_AMOUNT, HUNDRED_AMOUNT, null,
            ZERO_AMOUNT, ZERO_AMOUNT);
        verifyUsage(usages.get(1), UsageStatusEnum.UNCLASSIFIED, null, USER_NAME, ZERO_AMOUNT, new BigDecimal("900.00"),
            null, ZERO_AMOUNT, ZERO_AMOUNT);
    }

    private FundPool buildFundPool(BigDecimal nonStmAmount) {
        FundPool fundPool = new FundPool();
        fundPool.setMarkets(Sets.newHashSet(EDU_MARKET, GOV_MARKET));
        fundPool.setFundPoolPeriodFrom(2015);
        fundPool.setFundPoolPeriodTo(2016);
        fundPool.setStmAmount(HUNDRED_AMOUNT);
        fundPool.setStmMinimumAmount(new BigDecimal("50.00"));
        fundPool.setNonStmAmount(nonStmAmount);
        fundPool.setNonStmMinimumAmount(new BigDecimal("7.00"));
        return fundPool;
    }

    private void verifyInsertedUsage(Long wrWrkInst, String workTitle, String market, Integer marketPeriodFrom,
                                     BigDecimal reportedValue, Usage usage) {
        assertEquals(BATCH_ID, usage.getBatchId());
        assertEquals(wrWrkInst, usage.getWrWrkInst(), 0);
        assertEquals(workTitle, usage.getWorkTitle());
        assertEquals(workTitle, usage.getSystemTitle());
        assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
        assertEquals(NTS_PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals("1008902112317555XX", usage.getStandardNumber());
        assertEquals("VALISBN13", usage.getStandardNumberType());
        assertEquals(market, usage.getMarket());
        assertEquals(marketPeriodFrom, usage.getMarketPeriodFrom());
        assertEquals(Integer.valueOf(2017), usage.getMarketPeriodTo());
        assertEquals(ZERO_AMOUNT, usage.getGrossAmount());
        assertEquals(reportedValue, usage.getReportedValue());
        assertEquals("test comment", usage.getComment());
        assertEquals(USER_NAME, usage.getCreateUser());
        assertEquals(USER_NAME, usage.getUpdateUser());
    }

    private void verifyUsageIdsInScenario(List<String> expectedUsageIds, String scenarioId) {
        List<Usage> actualUsages = usageRepository.findByScenarioId(scenarioId);
        assertEquals(expectedUsageIds.size(), actualUsages.size());
        List<String> usagesIdsBeforeDeletion = actualUsages.stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertTrue(CollectionUtils.containsAll(usagesIdsBeforeDeletion, expectedUsageIds));
    }

    private void verifyUsage(Usage usage, UsageStatusEnum status, String scenarioId, String username,
                             BigDecimal grossAmount, BigDecimal reportedValue, BigDecimal serviceFee,
                             BigDecimal serviceFeeAmount, BigDecimal netAmount) {
        assertEquals(status, usage.getStatus());
        assertEquals(scenarioId, usage.getScenarioId());
        assertEquals(null, usage.getPayee().getAccountNumber());
        assertEquals(NTS_PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals(reportedValue, usage.getReportedValue());
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(netAmount, usage.getNetAmount());
        assertEquals(serviceFee, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
        assertEquals(username, usage.getUpdateUser());
    }
}