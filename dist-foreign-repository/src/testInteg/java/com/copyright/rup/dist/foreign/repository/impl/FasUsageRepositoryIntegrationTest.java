package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
//TODO: split test data into separate files for each test method
@TestData(fileName = "fas-usage-repository-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class FasUsageRepositoryIntegrationTest {

    private static final LocalDate PAYMENT_DATE = LocalDate.of(2018, 12, 11);
    private static final String USAGE_BATCH_ID_1 = "7b8beb5d-1fc8-47bf-8e06-3ac85457ac5b";
    private static final String SCENARIO_ID_1 = "e726496d-aca1-46d8-b393-999827cc6dda";
    private static final String SCENARIO_ID_2 = "edbcc8b3-8fa4-4c58-9244-a91627cac7a9";
    private static final String SCENARIO_ID_3 = "767a2647-7e6e-4479-b381-e642de480863";
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;
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
    public void testInsert() throws IOException {
        Usage expectedUsage = loadExpectedUsages().get(0);
        expectedUsage.setNetAmount(expectedUsage.getNetAmount().setScale(10, RoundingMode.HALF_UP));
        fasUsageRepository.insert(expectedUsage);
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(expectedUsage.getId()));
        assertEquals(1, CollectionUtils.size(usages));
        verifyFasUsage(expectedUsage, usages.get(0));
    }

    @Test
    public void testDeleteFromScenarioByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            Arrays.asList("7234feb4-a59e-483b-985a-e8de2e3eb190", "582c86e2-213e-48ad-a885-f9ff49d48a69",
                "730d7964-f399-4971-9403-dbedc9d7a180"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage -> assertEquals(SCENARIO_ID_2, usage.getScenarioId()));
        Set<String> excludedIds = fasUsageRepository.deleteFromScenarioByPayees(Collections.singleton(SCENARIO_ID_2),
            Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("730d7964-f399-4971-9403-dbedc9d7a180"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("730d7964-f399-4971-9403-dbedc9d7a180", "582c86e2-213e-48ad-a885-f9ff49d48a69"));
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
    public void testRedisignateToNtsWithdrawnByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            Arrays.asList("209a960f-5896-43da-b020-fc52981b9633", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a",
                "72f6abdb-c82d-4cee-aadf-570942cf0093"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage -> assertEquals(SCENARIO_ID_3, usage.getScenarioId()));
        Set<String> excludedIds =
            fasUsageRepository.redesignateToNtsWithdrawnByPayees(Collections.singleton(SCENARIO_ID_3),
                Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("72f6abdb-c82d-4cee-aadf-570942cf0093"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("72f6abdb-c82d-4cee-aadf-570942cf0093", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a"));
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
    public void testFindAccountNumbersInvalidForExclude() {
        assertEquals(Collections.singleton(7000813806L), fasUsageRepository.findAccountNumbersInvalidForExclude(
            Sets.newHashSet(SCENARIO_ID_2, SCENARIO_ID_3), Sets.newHashSet(7000813806L, 1000002859L)));
        assertTrue(fasUsageRepository.findAccountNumbersInvalidForExclude(Sets.newHashSet(SCENARIO_ID_2, SCENARIO_ID_3),
            Collections.singleton(1000002859L)).isEmpty());
    }

    @Test
    public void testUpdateResearchedUsages() {
        String usageId1 = "721ca627-09bc-4204-99f4-6acae415fa5d";
        String usageId2 = "9c07f6dd-382e-4cbb-8cd1-ab9f51413e0a";
        verifyUsage(usageId1, null, null, STANDARD_NUMBER, null, UsageStatusEnum.WORK_RESEARCH);
        verifyUsage(usageId2, null, null, null, null, UsageStatusEnum.WORK_RESEARCH);
        fasUsageRepository.updateResearchedUsages(Arrays.asList(
            buildResearchedUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN"),
            buildResearchedUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE)));
        verifyUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN", UsageStatusEnum.WORK_FOUND);
        verifyUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE,
            UsageStatusEnum.WORK_FOUND);
    }

    @Test
    public void testUpdateNtsWithdrawnUsagesAndGetIds() {
        List<String> ids = fasUsageRepository.updateNtsWithdrawnUsagesAndGetIds();
        assertEquals(3, ids.size());
        assertTrue(ids.containsAll(Arrays.asList("2f2ca785-a7d3-4a7f-abd9-2bad80ac71dd",
            "cbd6768d-a424-476e-b502-a832d9dbe85e", "d5e3c637-155a-4c05-999a-31a07e335491")));
        usageRepository.findByIds(ids).forEach(usage -> {
            assertEquals(UsageStatusEnum.NTS_WITHDRAWN, usage.getStatus());
            assertEquals("FAS", usage.getProductFamily());
        });
    }

    @Test
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
    public void testFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
                UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, FISCAL_YEAR);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testVerifyFindWithAmountsAndRightsholders() {
        UsageFilter usageFilter =
            buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.singleton(USAGE_BATCH_ID_1),
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
    public void testFindWithAmountsAndRightsholdersByUsageBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.singleton(USAGE_BATCH_ID_1),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByRhAccountNumberFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.singleton(RH_ACCOUNT_NUMBER), Collections.emptySet(),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 1, USAGE_ID_1);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByProductFamiliesFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            UsageStatusEnum.ELIGIBLE, null, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1,
            USAGE_ID_2, USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByPaymentDateFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            UsageStatusEnum.ELIGIBLE, PAYMENT_DATE, null);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
    }

    @Test
    public void testFindWithAmountsAndRightsholdersByFiscalYearFilter() {
        UsageFilter usageFilter = buildUsageFilter(Collections.emptySet(), Collections.emptySet(),
            UsageStatusEnum.ELIGIBLE, null, FISCAL_YEAR);
        verifyUsages(fasUsageRepository.findWithAmountsAndRightsholders(usageFilter), 3, USAGE_ID_1, USAGE_ID_2,
            USAGE_ID_3);
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
        List<Usage> usages = usageRepository.findByIds(Collections.singletonList(usageId));
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
    }
}
