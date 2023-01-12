package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.StoredEntity;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class NtsUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "nts-usage-repository-integration-test/";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String BATCH_ID = "b9d0ea49-9e38-4bb0-a7e0-0ca299e3dcfa";
    private static final String SCENARIO_ID = "ca163655-8978-4a45-8fe3-c3b5572c6879";
    private static final String SCENARIO_ID_2 = "03b9357e-0823-4abf-8e31-c615d735bf3b";
    private static final String SCENARIO_ID_3 = "24d11b82-bc2a-429a-92c4-809849d36e75";
    private static final String USAGE_ID_1 = "ba95f0b3-dc94-4925-96f2-93d05db9c469";
    private static final String USAGE_ID_2 = "c09aa888-85a5-4377-8c7a-85d84d255b5a";
    private static final String USAGE_ID_3 = "45445974-5bee-477a-858b-e9e8c1a642b8";
    private static final String USAGE_ID_4 = "ade68eac-0d79-4d23-861b-499a0c6e91d3";
    private static final String USAGE_ID_5 = "f6cb5b07-45c0-4188-9da3-920046eec4c0";
    private static final String USAGE_ID_6 = "f255188f-d582-4516-8c08-835cfe1d68c3";
    private static final String USAGE_ID_BELLETRISTIC = "bbbd64db-2668-499a-9d18-be8b3f87fbf5";
    private static final String USAGE_ID_UNCLASSIFIED = "6cad4cf2-6a19-4e5b-b4e0-f2f7a62ff91c";
    private static final String USAGE_ID_STM = "83a26087-a3b3-43ca-8b34-c66134fb6edf";
    private static final String WORK_TITLE_1 = "Our fathers lies";
    private static final String WORK_TITLE_2 = "100 ROAD MOVIES";
    private static final String EDU_MARKET = "Edu";
    private static final String GOV_MARKET = "Gov";
    private static final String USER_NAME = "user@copyright.com";
    private static final BigDecimal SERVICE_FEE = new BigDecimal("0.32000");
    private static final BigDecimal ZERO_AMOUNT = new BigDecimal("0.0000000000");
    private static final BigDecimal HUNDRED_AMOUNT = new BigDecimal("100.00");
    private static final BigDecimal SERVICE_FEE_AMOUNT = new BigDecimal("200.0000000000");
    private static final Long RH_ACCOUNT_NUMBER_2 = 2000017004L;
    private static final Long RH_ACCOUNT_NUMBER_3 = 2000017004L;

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
    @TestData(fileName = FOLDER_NAME + "delete-from-scenario-by-rightsholder.groovy")
    public void testDeleteFromScenarioByRightsholder() {
        List<Usage> usageList = usageRepository.findByScenarioId(SCENARIO_ID_2);
        assertEquals(4, usageList.size());
        usageList.forEach(usage -> {
            assertEquals(UsageStatusEnum.LOCKED, usage.getStatus());
            assertNotEquals(ZERO_AMOUNT, usage.getServiceFeeAmount());
            assertNotEquals(ZERO_AMOUNT, usage.getNetAmount());
            assertNotEquals(ZERO_AMOUNT, usage.getServiceFee());
            assertNotEquals(ZERO_AMOUNT, usage.getGrossAmount());
        });
        Set<String> usageIds =
            ntsUsageRepository.deleteFromScenarioByRightsholder(SCENARIO_ID_2, Sets.newHashSet(RH_ACCOUNT_NUMBER_2),
                StoredEntity.DEFAULT_USER);
        assertEquals(2, usageIds.size());
        usageList = usageRepository.findByIds(new ArrayList<>(usageIds));
        assertEquals(2, usageList.size());
        usageList.forEach(usage -> {
            assertEquals(UsageStatusEnum.SCENARIO_EXCLUDED, usage.getStatus());
            assertNull(usage.getScenarioId());
            assertEquals(ZERO_AMOUNT, usage.getServiceFeeAmount());
            assertEquals(ZERO_AMOUNT, usage.getNetAmount());
            assertEquals(ZERO_AMOUNT, usage.getGrossAmount());
            assertFalse(usage.isPayeeParticipating());
            assertFalse(usage.isRhParticipating());
            assertNull(usage.getServiceFee());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "recalculate-amounts-from-excluded-rightshoders.groovy")
    public void testRecalculateAmountsFromExcludedRightshoders() {
        List<Usage> usages = usageRepository.findByScenarioId(SCENARIO_ID_3);
        assertEquals(3, usages.size());
        assertEquals(new BigDecimal("800.0000000000"),
            usages.stream().map(Usage::getNetAmount).reduce(BigDecimal::add).get());
        assertEquals(new BigDecimal("1000.0000000000"),
            usages.stream().map(Usage::getGrossAmount).reduce(BigDecimal::add).get());
        assertEquals(SERVICE_FEE_AMOUNT,
            usages.stream().map(Usage::getServiceFeeAmount).reduce(BigDecimal::add).get());
        usages = usageRepository.findByIds(List.of("56f91295-db33-4440-b550-9bb515239750"));
        Usage expectedUsage1 = usages.get(0);
        usages = usageRepository.findByIds(List.of("4604c954-e43b-4606-809a-665c81514dbf"));
        Usage expectedUsage2 = usages.get(0);
        assertEquals(new BigDecimal("294.0000000000"), expectedUsage1.getNetAmount());
        assertEquals(new BigDecimal("336.0000000000"), expectedUsage2.getNetAmount());
        ntsUsageRepository.recalculateAmountsFromExcludedRightshoders(SCENARIO_ID_3,
            Sets.newHashSet(RH_ACCOUNT_NUMBER_3));
        usages = usageRepository.findByIds(
            List.of("56f91295-db33-4440-b550-9bb515239750", "4604c954-e43b-4606-809a-665c81514dbf"));
        assertEquals(new BigDecimal("800.0000000000"),
            usages.stream().map(Usage::getNetAmount).reduce(BigDecimal::add).get());
        usages = usageRepository.findByIds(List.of("56f91295-db33-4440-b550-9bb515239750"));
        Usage actualUsage1 = usages.get(0);
        usages = usageRepository.findByIds(List.of("4604c954-e43b-4606-809a-665c81514dbf"));
        Usage actualUsage2 = usages.get(0);
        assertEquals(new BigDecimal("373.3333333333"), actualUsage1.getNetAmount());
        assertEquals(new BigDecimal("426.6666666667"), actualUsage2.getNetAmount());
        assertEquals(new BigDecimal("466.6666666667"), actualUsage1.getGrossAmount());
        assertEquals(new BigDecimal("533.3333333333"), actualUsage2.getGrossAmount());
        assertEquals(new BigDecimal("93.3333333333"), actualUsage1.getServiceFeeAmount());
        assertEquals(new BigDecimal("106.6666666667"), actualUsage2.getServiceFeeAmount());
        assertEquals(new BigDecimal("1000.0000000000"),
            actualUsage1.getGrossAmount().add(actualUsage2.getGrossAmount()));
        assertEquals(new BigDecimal("800.0000000000"), actualUsage1.getNetAmount().add(actualUsage2.getNetAmount()));
        assertEquals(SERVICE_FEE_AMOUNT,
            actualUsage1.getServiceFeeAmount().add(actualUsage2.getServiceFeeAmount()));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "insert-usages.groovy")
    public void testInsertUsages() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setNtsFields(buildNtsFields(HUNDRED_AMOUNT));
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
    @TestData(fileName = FOLDER_NAME + "insert-usages.groovy")
    public void testInsertUsagesZeroFundPoolAmount() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId(BATCH_ID);
        usageBatch.setNtsFields(buildNtsFields(BigDecimal.ZERO));
        List<String> insertedUsageIds = ntsUsageRepository.insertUsages(usageBatch, USER_NAME);
        assertEquals(1, insertedUsageIds.size());
        List<Usage> insertedUsages = usageRepository.findByIds(insertedUsageIds);
        insertedUsages.sort(Comparator.comparing(Usage::getMarketPeriodFrom));
        verifyInsertedUsage(105062654L, WORK_TITLE_1, EDU_MARKET, 2014, new BigDecimal("500.00"),
            insertedUsages.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-count-for-batch.groovy")
    public void testFindCountForBatch() {
        assertEquals(2, ntsUsageRepository.findCountForBatch(2015, 2016, Sets.newHashSet("Bus", "Doc Del")));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-unclassified-usages-count-by-wr-wrk-insts.groovy")
    public void testFindUnclassifiedUsagesCountByWrWrkInsts() {
        assertEquals(2,
            ntsUsageRepository.findUnclassifiedUsagesCountByWrWrkInsts(
                Sets.newHashSet(987632764L, 12318778798L)));
        assertEquals(0, ntsUsageRepository.findUnclassifiedUsagesCountByWrWrkInsts(Set.of(1L)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "calculate-amounts-and-update-payee-by-account-number.groovy")
    public void testCalculateAmountsAndUpdatePayeeByAccountNumber() {
        assertUsageAfterServiceFeeCalculation("8a80a2e7-4758-4e43-ae42-e8b29802a210",
            new BigDecimal("256.0000000000"), ZERO_AMOUNT, null,
            ZERO_AMOUNT, new BigDecimal("296.72"), null, false);
        assertUsageAfterServiceFeeCalculation("bfc9e375-c489-4600-9308-daa101eed97c",
            new BigDecimal("145.2000000000"), ZERO_AMOUNT, null,
            ZERO_AMOUNT, new BigDecimal("16.24"), null, false);
        assertUsageAfterServiceFeeCalculation("085268cd-7a0c-414e-8b28-2acb299d9698",
            new BigDecimal("1452.0000000000"), ZERO_AMOUNT, null,
            ZERO_AMOUNT, new BigDecimal("162.41"), null, false);
        ntsUsageRepository.calculateAmountsAndUpdatePayeeByAccountNumber(1000002859L,
            "d7e9bae8-6b10-4675-9668-8e3605a47dad", SERVICE_FEE, true, 243904752L, "SYSTEM");
        assertUsageAfterServiceFeeCalculation("8a80a2e7-4758-4e43-ae42-e8b29802a210",
            new BigDecimal("256.0000000000"), new BigDecimal("174.0800000000"), SERVICE_FEE,
            new BigDecimal("81.9200000000"), new BigDecimal("296.72"), 243904752L, true);
        assertUsageAfterServiceFeeCalculation("bfc9e375-c489-4600-9308-daa101eed97c",
            new BigDecimal("145.2000000000"), new BigDecimal("98.7360000000"), SERVICE_FEE,
            new BigDecimal("46.4640000000"), new BigDecimal("16.24"), 243904752L, true);
        assertUsageAfterServiceFeeCalculation("085268cd-7a0c-414e-8b28-2acb299d9698",
            new BigDecimal("1452.0000000000"), ZERO_AMOUNT, null, ZERO_AMOUNT,
            new BigDecimal("162.41"), null, false);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "apply-post-service-fee-amount.groovy")
    public void testApplyPostServiceFeeAmount() {
        // Post Service Fee Amount = 100
        ntsUsageRepository.applyPostServiceFeeAmount("c4bc09c1-eb9b-41f3-ac93-9cd088dff408");
        assertUsageAmounts("7778a37d-6184-42c1-8e23-5841837c5411", new BigDecimal("71.1818181818"),
            new BigDecimal("65.9018181818"), new BigDecimal("0.16000"), new BigDecimal("5.2800000000"),
            new BigDecimal("33.00"));
        assertUsageAmounts("54247c55-bf6b-4ad6-9369-fb4baea6b19b", new BigDecimal("127.8181818182"),
            new BigDecimal("106.6981818182"), SERVICE_FEE, new BigDecimal("21.1200000000"), new BigDecimal("66.00"));
        assertUsageAmounts(USAGE_ID_4, new BigDecimal("11.0000000000"),
            ZERO_AMOUNT, null, ZERO_AMOUNT, new BigDecimal("11.00"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-scenario-id.groovy")
    public void testDeleteByScenarioIdScenarioExcluded() {
        assertEquals(2, usageRepository.findByStatuses(UsageStatusEnum.SCENARIO_EXCLUDED).size());
        assertEquals(1, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_4));
        ntsUsageRepository.deleteByScenarioId("c4bc09c1-eb9b-41f3-ac93-9cd088dff408");
        assertEquals(1, usageRepository.findByStatuses(UsageStatusEnum.SCENARIO_EXCLUDED).size());
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_4));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-from-nts-fund-pool.groovy")
    public void testDeleteFromNtsFundPool() {
        String fundPoolId = "3fef25b0-c0d1-4819-887f-4c6acc01390e";
        List<Usage> usages = usageRepository.findByIds(List.of(USAGE_ID_1));
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(UsageStatusEnum.TO_BE_DISTRIBUTED, usage.getStatus());
        assertEquals(fundPoolId, usage.getFundPoolId());
        ntsUsageRepository.deleteFromNtsFundPool(fundPoolId, USER_NAME);
        usage = usageRepository.findByIds(List.of(USAGE_ID_1)).get(0);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertNull(usage.getFundPoolId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-belletristic-by-scenario-id.groovy")
    public void testDeleteBelletristicByScenarioId() {
        String scenarioId = "dd4fca1d-eac8-4b76-85e4-121b7971d049";
        verifyUsageIdsInScenario(List.of(USAGE_ID_BELLETRISTIC, USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertEquals(1, usageRepository.findByIds(List.of(USAGE_ID_BELLETRISTIC)).size());
        ntsUsageRepository.deleteBelletristicByScenarioId(scenarioId);
        verifyUsageIdsInScenario(List.of(USAGE_ID_STM, USAGE_ID_UNCLASSIFIED), scenarioId);
        assertEquals(0, usageRepository.findByIds(List.of(USAGE_ID_BELLETRISTIC)).size());
        assertEquals(0, usageRepository.findReferencedUsagesCountByIds(USAGE_ID_BELLETRISTIC));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-from-scenario.groovy")
    public void testDeleteFromScenario() {
        List<Usage> usages = usageRepository.findByIds(List.of(USAGE_ID_2, USAGE_ID_3));
        assertEquals(2, usages.size());
        verifyUsage(usages.get(0), UsageStatusEnum.SCENARIO_EXCLUDED, null, false, false, null,
            StoredEntity.DEFAULT_USER, ZERO_AMOUNT, HUNDRED_AMOUNT, null, ZERO_AMOUNT, ZERO_AMOUNT);
        verifyUsage(usages.get(1), UsageStatusEnum.LOCKED, 1000009997L, true, true, SCENARIO_ID,
            StoredEntity.DEFAULT_USER, new BigDecimal("900.0000000000"), new BigDecimal("900.00"),
            new BigDecimal("0.32000"), new BigDecimal("288.0000000000"), new BigDecimal("612.0000000000"));
        ntsUsageRepository.deleteFromScenario(SCENARIO_ID, USER_NAME);
        usages = usageRepository.findByIds(List.of(USAGE_ID_2, USAGE_ID_3));
        assertEquals(2, usages.size());
        verifyUsage(usages.get(0), UsageStatusEnum.ELIGIBLE, null, false, false, null, USER_NAME, ZERO_AMOUNT,
            HUNDRED_AMOUNT, null, ZERO_AMOUNT, ZERO_AMOUNT);
        verifyUsage(usages.get(1), UsageStatusEnum.UNCLASSIFIED, null, false, false, null, USER_NAME, ZERO_AMOUNT,
            new BigDecimal("900.00"), null, ZERO_AMOUNT, ZERO_AMOUNT);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-status-to-unclassified.groovy")
    public void testFindUsageIdsForClassificationUpdate() {
        List<String> actualUsageIds = ntsUsageRepository.findUsageIdsForClassificationUpdate();
        assertNotNull(actualUsageIds);
        assertEquals(1, actualUsageIds.size());
        assertEquals("c6cb5b07-45c0-4188-9da3-920046eec4cf", actualUsageIds.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-status-to-unclassified.groovy")
    public void testUpdateUsagesStatusToUnclassified() {
        ArrayList<String> usageIds = Lists.newArrayList(USAGE_ID_5, USAGE_ID_6);
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(0).getStatus());
        assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(1).getStatus());
        ntsUsageRepository.updateUsagesStatusToUnclassified(Lists.newArrayList(122267671L, 159526526L),
            StoredEntity.DEFAULT_USER);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        assertEquals(UsageStatusEnum.UNCLASSIFIED, usages.get(0).getStatus());
        assertEquals(UsageStatusEnum.UNCLASSIFIED, usages.get(1).getStatus());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "add-withdrawn-usages-to-nts-fund-pool.groovy")
    public void testAddWithdrawnUsagesToNtsFundPool() {
        List<String> usageIds = List.of("4dd8cdf8-ca10-422e-bdd5-3220105e6379");
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
        assertNull(usage.getFundPoolId());
        String fundPoolId = "3fef25b0-c0d1-4819-887f-4c6acc01390e";
        Set<String> batchIds = Set.of("cb597f4e-f636-447f-8710-0436d8994d10");
        ntsUsageRepository.addWithdrawnUsagesToNtsFundPool(fundPoolId, batchIds, StoredEntity.DEFAULT_USER);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(1, usages.size());
        usage = usages.get(0);
        assertEquals(UsageStatusEnum.TO_BE_DISTRIBUTED, usage.getStatus());
        assertEquals(fundPoolId, usage.getFundPoolId());
    }

    private NtsFields buildNtsFields(BigDecimal nonStmAmount) {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setMarkets(Sets.newHashSet(EDU_MARKET, GOV_MARKET));
        ntsFields.setFundPoolPeriodFrom(2015);
        ntsFields.setFundPoolPeriodTo(2016);
        ntsFields.setStmAmount(HUNDRED_AMOUNT);
        ntsFields.setStmMinimumAmount(new BigDecimal("50.00"));
        ntsFields.setNonStmAmount(nonStmAmount);
        ntsFields.setNonStmMinimumAmount(new BigDecimal("7.00"));
        return ntsFields;
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

    private void verifyUsage(Usage usage, UsageStatusEnum status, Long payeeAccountNumber, boolean isPayeeParticipating,
                             boolean isRhParticipating, String scenarioId, String username, BigDecimal grossAmount,
                             BigDecimal reportedValue, BigDecimal serviceFee, BigDecimal serviceFeeAmount,
                             BigDecimal netAmount) {
        assertEquals(status, usage.getStatus());
        assertEquals(scenarioId, usage.getScenarioId());
        assertEquals(payeeAccountNumber, usage.getPayee().getAccountNumber());
        assertEquals(isPayeeParticipating, usage.isPayeeParticipating());
        assertEquals(isRhParticipating, usage.isRhParticipating());
        assertEquals(NTS_PRODUCT_FAMILY, usage.getProductFamily());
        assertEquals(username, usage.getUpdateUser());
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void assertUsageAmounts(String usageId, BigDecimal grossAmount, BigDecimal netAmount,
                                    BigDecimal serviceFee, BigDecimal serviceFeeAmount, BigDecimal reportedValue) {
        Usage usage = usageRepository.findByIds(List.of(usageId)).get(0);
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void assertUsageAfterServiceFeeCalculation(String usageId, BigDecimal grossAmount, BigDecimal netAmount,
                                                       BigDecimal serviceFee, BigDecimal serviceFeeAmount,
                                                       BigDecimal reportedValue, Long payeeAccountNumber,
                                                       boolean rhParticipating) {
        Usage usage = usageRepository.findByIds(List.of(usageId)).get(0);
        assertEquals(rhParticipating, usage.isRhParticipating());
        assertEquals(payeeAccountNumber, usage.getPayee().getAccountNumber());
        assertAmounts(usage, grossAmount, netAmount, serviceFee, serviceFeeAmount, reportedValue);
    }

    private void assertAmounts(Usage usage, BigDecimal grossAmount, BigDecimal netAmount, BigDecimal serviceFee,
                               BigDecimal serviceFeeAmount, BigDecimal reportedValue) {
        assertEquals(reportedValue, usage.getReportedValue());
        assertEquals(grossAmount, usage.getGrossAmount());
        assertEquals(netAmount, usage.getNetAmount());
        assertEquals(serviceFee, usage.getServiceFee());
        assertEquals(serviceFeeAmount, usage.getServiceFeeAmount());
    }
}
