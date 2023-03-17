package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.Work;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Integration test for {@link FasUsageRepository}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/11/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class FasUsageRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "fas-usage-repository-integration-test/";
    private static final String FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS =
        FOLDER_NAME + "find-with-amounts-and-rightsholders.groovy";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2018, 12, 11);
    private static final String USAGE_BATCH_ID_1 = "7b8beb5d-1fc8-47bf-8e06-3ac85457ac5b";
    private static final String SCENARIO_ID_1 = "e726496d-aca1-46d8-b393-999827cc6dda";
    private static final String SCENARIO_ID_2 = "edbcc8b3-8fa4-4c58-9244-a91627cac7a9";
    private static final String SCENARIO_ID_3 = "767a2647-7e6e-4479-b381-e642de480863";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
    private static final Long WR_WRK_INST = 122820638L;
    private static final String WORK_TITLE = "100 ROAD MOVIES";
    private static final Integer FISCAL_YEAR = 2019;
    private static final String USAGE_ID_1 = "3ab5e80b-89c0-4d78-9675-54c7ab284450";
    private static final String USAGE_ID_2 = "d77f2163-15ed-450f-896f-ecaa1ebce3b4";
    private static final String USAGE_ID_3 = "c3df34f3-c6ed-4ed3-9cfd-586996e9d45f";
    private static final String USER_NAME = "user@copyright.com";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final BigDecimal DEFAULT_ZERO_AMOUNT = new BigDecimal("0.0000000000");

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private UsageRepository usageRepository;
    @Autowired
    private IFasUsageRepository fasUsageRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() throws IOException {
        Usage expectedUsage = loadExpectedUsages().get(0);
        expectedUsage.setNetAmount(expectedUsage.getNetAmount().setScale(10, RoundingMode.HALF_UP));
        fasUsageRepository.insert(expectedUsage);
        List<Usage> usages = usageRepository.findByIds(List.of(expectedUsage.getId()));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(expectedUsage, usages.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-from-scenario-by-payees.groovy")
    public void testDeleteFromScenarioByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            List.of("7234feb4-a59e-483b-985a-e8de2e3eb190", "582c86e2-213e-48ad-a885-f9ff49d48a69",
                "730d7964-f399-4971-9403-dbedc9d7a180"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage -> assertEquals(SCENARIO_ID_2, usage.getScenarioId()));
        Set<String> excludedIds = fasUsageRepository.deleteFromScenarioByPayees(Set.of(SCENARIO_ID_2),
            Set.of(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("730d7964-f399-4971-9403-dbedc9d7a180"));
        List<Usage> usages = usageRepository.findByIds(
            List.of("730d7964-f399-4971-9403-dbedc9d7a180", "582c86e2-213e-48ad-a885-f9ff49d48a69"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "FAS2", UsageStatusEnum.ELIGIBLE));
        List<String> usageIds = usageRepository.findByScenarioId(SCENARIO_ID_2)
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(usageIds));
        assertTrue(usageIds.contains("7234feb4-a59e-483b-985a-e8de2e3eb190"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "redesignate-to-nts-withdrawn-by-payees.groovy")
    public void testRedisignateToNtsWithdrawnByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            List.of("209a960f-5896-43da-b020-fc52981b9633", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a",
                "72f6abdb-c82d-4cee-aadf-570942cf0093"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage -> assertEquals(SCENARIO_ID_3, usage.getScenarioId()));
        Set<String> excludedIds = fasUsageRepository.redesignateToNtsWithdrawnByPayees(Set.of(SCENARIO_ID_3),
            Set.of(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("72f6abdb-c82d-4cee-aadf-570942cf0093"));
        List<Usage> usages = usageRepository.findByIds(
            List.of("72f6abdb-c82d-4cee-aadf-570942cf0093", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "FAS2", UsageStatusEnum.NTS_WITHDRAWN));
        List<String> usageIds = usageRepository.findByScenarioId(SCENARIO_ID_3)
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(usageIds));
        assertTrue(usageIds.contains("209a960f-5896-43da-b020-fc52981b9633"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-account-numbers-invalid-for-exclude.groovy")
    public void testFindAccountNumbersInvalidForExclude() {
        assertEquals(Set.of(7000813806L), fasUsageRepository.findAccountNumbersInvalidForExclude(
            Set.of(SCENARIO_ID_2, SCENARIO_ID_3), Set.of(7000813806L, 1000002859L)));
        assertTrue(fasUsageRepository.findAccountNumbersInvalidForExclude(Set.of(SCENARIO_ID_2, SCENARIO_ID_3),
            Set.of(1000002859L)).isEmpty());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-researched-usages.groovy")
    public void testUpdateResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        verifyUsage(usageId1, null, null, STANDARD_NUMBER, null, UsageStatusEnum.WORK_RESEARCH);
        verifyUsage(usageId2, null, null, null, null, UsageStatusEnum.WORK_RESEARCH);
        fasUsageRepository.updateResearchedUsages(List.of(
            buildResearchedUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN"),
            buildResearchedUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE)));
        verifyUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN", UsageStatusEnum.WORK_FOUND);
        verifyUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE,
            UsageStatusEnum.WORK_FOUND);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-nts-withdrawn-usages-and-get-ids.groovy")
    public void testUpdateNtsWithdrawnUsagesAndGetIds() {
        List<String> ids = fasUsageRepository.updateNtsWithdrawnUsagesAndGetIds();
        assertEquals(3, ids.size());
        assertTrue(ids.containsAll(List.of("2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd",
            "cbd6768d-a424-476e-b502-a832d9dbe85e", "d5e3c637-155a-4c05-999a-31a07e335491")));
        usageRepository.findByIds(ids).forEach(usage -> {
            assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
            assertEquals("FAS", usage.getProductFamily());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-for-reconcile.groovy")
    public void testFindForReconcile() {
        List<Usage> usages = fasUsageRepository.findForReconcile(SCENARIO_ID_1);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(243904752L, usage.getWrWrkInst(), 0);
            assertEquals(1000009523L, usage.getRightsholder().getAccountNumber(), 0);
            assertEquals("John Wiley & Sons - Books", usage.getRightsholder().getName());
            assertEquals(WORK_TITLE, usage.getWorkTitle());
            assertNotNull(usage.getGrossAmount());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-rightsholders-information.groovy")
    public void testFindRightsholdersInformation() {
        Map<Long, Usage> rhInfo = fasUsageRepository.findRightsholdersInformation(SCENARIO_ID_1);
        assertEquals(1, rhInfo.size());
        Entry<Long, Usage> entry = rhInfo.entrySet().iterator().next();
        assertEquals(1000009523L, entry.getKey(), 0);
        assertEquals(1000002859L, entry.getValue().getPayee().getAccountNumber(), 0);
        assertTrue(entry.getValue().isRhParticipating());
        assertTrue(entry.getValue().isPayeeParticipating());
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(RH_ACCOUNT_NUMBER), Set.of(USAGE_BATCH_ID_1),
            UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testVerifyFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(RH_ACCOUNT_NUMBER), Set.of(USAGE_BATCH_ID_1),
            UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        List<Usage> usages = fasUsageRepository.findWithAmountsAndRightsholders(usageFilter);
        assertEquals(1, usages.size());
        Usage usage = usages.get(0);
        assertEquals(USAGE_ID_1, usage.getId());
        assertEquals(new BigDecimal("35000.0000000000"), usage.getGrossAmount());
        assertEquals(BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP), usage.getNetAmount());
        assertEquals(new BigDecimal("2500.00"), usage.getReportedValue());
        assertNull(usage.getServiceFee());
        assertNotNull(usage.getCreateDate());
        assertNotNull(usage.getUpdateDate());
        assertEquals("SYSTEM", usage.getCreateUser());
        assertEquals("SYSTEM", usage.getUpdateUser());
        assertEquals(1, usage.getVersion());
        assertNotNull(usage.getRightsholder());
        assertEquals(1000009997L, usage.getRightsholder().getAccountNumber(), 0);
        assertEquals("9905f006-a3e1-4061-b3d4-e7ece191103f", usage.getRightsholder().getId());
        assertEquals("IEEE - Inst of Electrical and Electronics Engrs", usage.getRightsholder().getName());
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(), Set.of(USAGE_BATCH_ID_1),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(RH_ACCOUNT_NUMBER), Set.of(),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByProductFamiliesFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(), Set.of(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_3,
            USAGE_ID_2);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(), Set.of(), UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_3,
            USAGE_ID_2);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByPaymentDateFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(), Set.of(), UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_3,
            USAGE_ID_2);
    }

    @Test
    @TestData(fileName = FIND_WITH_AMOUNTS_AND_RIGHTSHOLDERS)
    public void testFindWithAmountsAndRightsholdersByFiscalYearFilter() {
        UsageFilter usageFilter = buildUsageFilter(Set.of(), Set.of(), UsageStatusEnum.ELIGIBLE, null, FISCAL_YEAR);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_3,
            USAGE_ID_2);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-work-and-status.groovy")
    public void testUpdateUsagesWorkAndStatusWorkFound() {
        List<String> usageIds = List.of("5be457bd-12c3-42d2-b62e-cc2f3e056566", "da1a1603-ea75-4933-8f63-818e46f2d49a");
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertNotEquals(WR_WRK_INST, usage.getWrWrkInst());
            assertNotEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
            assertNotEquals(WORK_TITLE, usage.getSystemTitle());
            assertNotNull(usage.getRightsholder().getAccountNumber());
            assertNotEquals(STANDARD_NUMBER, usage.getStandardNumber());
            assertNotEquals(STANDARD_NUMBER_TYPE, usage.getStandardNumberType());
            assertNotEquals(USER_NAME, usage.getUpdateUser());
        });
        Work work = new Work(WR_WRK_INST, WORK_TITLE, STANDARD_NUMBER, STANDARD_NUMBER_TYPE);
        fasUsageRepository.updateUsagesWorkAndStatus(usageIds, work, UsageStatusEnum.WORK_FOUND, USER_NAME);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(WR_WRK_INST, usage.getWrWrkInst());
            assertEquals(UsageStatusEnum.WORK_FOUND, usage.getStatus());
            assertEquals(WORK_TITLE, usage.getSystemTitle());
            assertNull(usage.getRightsholder().getAccountNumber());
            assertEquals(STANDARD_NUMBER, usage.getStandardNumber());
            assertEquals(STANDARD_NUMBER_TYPE, usage.getStandardNumberType());
            assertEquals(USER_NAME, usage.getUpdateUser());
        });
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "update-usages-work-and-status.groovy")
    public void testUpdateUsagesWorkAndStatusWorkNotFound() {
        List<String> usageIds = List.of("5be457bd-12c3-42d2-b62e-cc2f3e056566", "da1a1603-ea75-4933-8f63-818e46f2d49a");
        List<Usage> usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertNotEquals(WR_WRK_INST, usage.getWrWrkInst());
            assertNotEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
            assertNotNull(usage.getSystemTitle());
            assertNotNull(usage.getRightsholder().getAccountNumber());
            assertNotNull(usage.getStandardNumber());
            assertNotNull(usage.getStandardNumberType());
            assertNotEquals(USER_NAME, usage.getUpdateUser());
        });
        Work work = new Work(WR_WRK_INST, null, null, null);
        fasUsageRepository.updateUsagesWorkAndStatus(usageIds, work, UsageStatusEnum.WORK_NOT_FOUND, USER_NAME);
        usages = usageRepository.findByIds(usageIds);
        assertEquals(2, usages.size());
        usages.forEach(usage -> {
            assertEquals(WR_WRK_INST, usage.getWrWrkInst());
            assertEquals(UsageStatusEnum.WORK_NOT_FOUND, usage.getStatus());
            assertNull(usage.getSystemTitle());
            assertNull(usage.getRightsholder().getAccountNumber());
            assertNull(usage.getStandardNumber());
            assertNull(usage.getStandardNumberType());
            assertEquals(USER_NAME, usage.getUpdateUser());
        });
    }

    private void verifyUsages(List<Usage> usages, int count, String... usageIds) {
        assertNotNull(usages);
        assertEquals(count, usages.size());
        IntStream.range(0, count).forEach(i -> {
            assertEquals(usageIds[i], usages.get(i).getId());
            assertEquals(UsageStatusEnum.ELIGIBLE, usages.get(i).getStatus());
        });
    }

    private UsageFilter buildUsageFilter(Set<Long> accountNumbers, Set<String> usageBatchIds, UsageStatusEnum status,
                                         LocalDate paymentDate, Integer fiscalYear) {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setUsageStatus(status);
        usageFilter.setRhAccountNumbers(accountNumbers);
        usageFilter.setUsageStatus(status);
        usageFilter.setUsageBatchesIds(usageBatchIds);
        usageFilter.setPaymentDate(paymentDate);
        usageFilter.setFiscalYear(fiscalYear);
        usageFilter.setProductFamily("FAS");
        return usageFilter;
    }

    private ResearchedUsage buildResearchedUsage(String id, String title, Long wrWrkInst, String standardNumber,
                                                 String standardNumberType) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setUsageId(id);
        researchedUsage.setSystemTitle(title);
        researchedUsage.setWrWrkInst(wrWrkInst);
        researchedUsage.setStandardNumber(standardNumber);
        researchedUsage.setStandardNumberType(standardNumberType);
        return researchedUsage;
    }

    private List<Usage> loadExpectedUsages() throws IOException {
        String content = TestUtils.fileToString(this.getClass(), "json/fas_usage.json");
        return OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
        });
    }

    private void verifyUsage(String usageId, String title, Long wrWrkInst, String standardNumber,
                             String standardNumberType, UsageStatusEnum status) {
        List<Usage> usages = usageRepository.findByIds(List.of(usageId));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        assertEquals(status, usage.getStatus());
        assertEquals("Wissenschaft & Forschung Japan", usage.getWorkTitle());
        assertEquals(title, usage.getSystemTitle());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(standardNumber, usage.getStandardNumber());
        assertEquals(standardNumberType, usage.getStandardNumberType());
    }

    private void verifyUsageExcludedFromScenario(Usage usage, String productFamily, UsageStatusEnum status) {
        assertEquals(status, usage.getStatus());
        assertNull(usage.getScenarioId());
        assertNull(usage.getPayee().getAccountNumber());
        assertNull(usage.getServiceFee());
        assertEquals(DEFAULT_ZERO_AMOUNT, usage.getServiceFeeAmount());
        assertEquals(DEFAULT_ZERO_AMOUNT, usage.getNetAmount());
        assertFalse(usage.isRhParticipating());
        assertFalse(usage.isPayeeParticipating());
        assertEquals(USER_NAME, usage.getUpdateUser());
        assertEquals(productFamily, usage.getProductFamily());
    }

    private void verifyFasUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getScenarioId(), actualUsage.getScenarioId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPublisher(), actualUsage.getPublisher());
        assertEquals(expectedUsage.getMarket(), actualUsage.getMarket());
        assertEquals(expectedUsage.getMarketPeriodTo(), actualUsage.getMarketPeriodTo());
        assertEquals(expectedUsage.getMarketPeriodFrom(), actualUsage.getMarketPeriodFrom());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getRightsholder().getName(), actualUsage.getRightsholder().getName());
        assertEquals(expectedUsage.getRightsholder().getId(), actualUsage.getRightsholder().getId());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertEquals(expectedUsage.getFasUsage().getReportedStandardNumber(),
            actualUsage.getFasUsage().getReportedStandardNumber());
    }
}
