package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.common.repository.api.Sort;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PayeeAccountAggregateLicenseeClassesPair;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.domain.filter.ExcludePayeeFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Integration test for {@link AaclUsageRepository}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/21/2020
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
//TODO: split test data into separate files for each test method
@TestData(fileName = "aacl-usage-repository-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class AaclUsageRepositoryIntegrationTest {

    private static final String SCENARIO_ID_1 = "09f85d7d-3a37-45b2-ab6e-7a341c3f115c";
    private static final String SCENARIO_ID_2 = "66d10c81-705e-4996-89f4-11e1635c4c31";
    private static final String SCENARIO_ID_3 = "8b01939c-abda-4090-86d1-6231fc20f679";
    private static final String SCENARIO_ID_4 = "aedbd4ba-8b9f-41dc-94d6-bf651009c180";
    private static final String SCENARIO_ID_5 = "59e641bf-48a8-432b-a112-9953b7b7a62e";
    private static final String USAGE_ID_1 = "0b5ac9fc-63e2-4162-8d63-953b7023293c";
    private static final String USAGE_ID_2 = "6c91f04e-60dc-49e0-9cdc-e782e0b923e2";
    private static final String USAGE_ID_3 = "5b41d618-0a2f-4736-bb75-29da627ad677";
    private static final String USAGE_ID_4 = "f89f016d-0cc7-46b6-9f3f-63d2439458d5";
    private static final String USAGE_ID_5 = "49680a3e-2986-44f5-943c-3701d80f2d3d";
    private static final String USAGE_ID_6 = "870ee1dc-8596-409f-8ffe-717d17a33c9e";
    private static final String USAGE_ID_7 = "10bd15c1-b907-457e-94c0-9d6bb66e706f";
    private static final String USAGE_ID_8 = "2f3988e1-7cca-42b2-bdf8-a8850dbf315b";
    private static final String BATCH_ID_3 = "adcc460c-c4ae-4750-99e8-b9fe91787ce1";
    private static final String SYSTEM_TITLE = "Wissenschaft & Forschung Japan";
    private static final String USER_NAME = "user@mail.com";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final String RIGHT_LIMITATION = "ALL";
    private static final String PERCENT = "%";
    private static final String UNDERSCORE = "_";
    private static final String AMOUNT_50 = "50.0000000000";
    private static final BigDecimal AMOUNT_0 = BigDecimal.ZERO.setScale(10, BigDecimal.ROUND_HALF_UP);
    private static final Long RH_ACCOUNT_NUMBER = 7000813806L;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    @Autowired
    private IAaclUsageRepository aaclUsageRepository;

    @Test
    public void testInsert() {
        Usage expectedUsage =
            loadExpectedUsages(Collections.singletonList("json/aacl/aacl_usage_5b41d618.json")).get(0);
        aaclUsageRepository.insert(expectedUsage);
        List<Usage> actualUsages = aaclUsageRepository.findByIds(Collections.singletonList(USAGE_ID_3));
        assertEquals(1, actualUsages.size());
        verifyUsage(expectedUsage, actualUsages.get(0));
    }

    @Test
    public void testInsertFromBaseline() {
        List<String> actualIds = aaclUsageRepository.insertFromBaseline(Sets.newHashSet(2019, 2018, 2016),
            "6e6f656a-e080-4426-b8ea-985b69f8814d", USER_NAME);
        assertEquals(3, actualIds.size());
        List<Usage> actualUsages = aaclUsageRepository.findByIds(actualIds).stream()
            .sorted(Comparator.comparing(usage -> usage.getAaclUsage().getUsageAge().getPeriod()))
            .collect(Collectors.toList());
        verifyUsages(
            Arrays.asList("json/aacl/aacl_baseline_usage_819ad2fc.json", "json/aacl/aacl_baseline_usage_5bcb69c5.json",
                "json/aacl/aacl_baseline_usage_0085ceb9.json"), actualUsages, this::verifyUsageIgnoringId);
    }

    @Test
    public void testUpdateClassifiedUsages() {
        List<Usage> usages =
            aaclUsageRepository.findByIds(Collections.singletonList("8315e53b-0a7e-452a-a62c-17fe959f3f84"));
        assertEquals(1, usages.size());
        Usage expectedUsage = usages.get(0);
        assertEquals(UsageStatusEnum.WORK_RESEARCH, expectedUsage.getStatus());
        assertNull(expectedUsage.getAaclUsage().getDetailLicenseeClass().getId());
        assertNull(expectedUsage.getAaclUsage().getDetailLicenseeClass().getDiscipline());
        assertNull(expectedUsage.getAaclUsage().getDetailLicenseeClass().getEnrollmentProfile());
        assertEquals(11, getNumberOfUsagesWithNotEmptyClassificationData());
        aaclUsageRepository.updateClassifiedUsages(Collections.singletonList(buildAaclClassifiedUsage()), USER_NAME);
        assertEquals(12, getNumberOfUsagesWithNotEmptyClassificationData());
        verifyUsages(Collections.singletonList("json/aacl/aacl_classified_usage_8315e53b.json"),
            aaclUsageRepository.findByIds(Collections.singletonList("8315e53b-0a7e-452a-a62c-17fe959f3f84")),
            this::verifyUsage);
    }

    @Test
    public void testDeleteById() {
        Usage expectedUsage =
            loadExpectedUsages(Collections.singletonList("json/aacl/aacl_usage_5b41d618.json")).get(0);
        aaclUsageRepository.insert(expectedUsage);
        List<Usage> actualUsages = aaclUsageRepository.findByIds(Collections.singletonList(USAGE_ID_3));
        assertEquals(1, actualUsages.size());
        aaclUsageRepository.deleteById(USAGE_ID_3);
        assertTrue(CollectionUtils.isEmpty(aaclUsageRepository.findByIds(Collections.singletonList(USAGE_ID_3))));
        assertEquals(0, aaclUsageRepository.findReferencedAaclUsagesCountByIds(USAGE_ID_3));
    }

    @Test
    public void testUpdateProcessedUsage() {
        List<Usage> usages = aaclUsageRepository.findByIds(Collections.singletonList(USAGE_ID_1));
        assertEquals(1, CollectionUtils.size(usages));
        Usage usage = usages.get(0);
        usage.setStatus(UsageStatusEnum.RH_FOUND);
        usage.getRightsholder().setAccountNumber(RH_ACCOUNT_NUMBER);
        usage.setSystemTitle(SYSTEM_TITLE);
        usage.setStandardNumberType(STANDARD_NUMBER_TYPE);
        usage.setStandardNumber(STANDARD_NUMBER);
        usage.getAaclUsage().setRightLimitation(RIGHT_LIMITATION);
        assertNotNull(aaclUsageRepository.updateProcessedUsage(usage));
        List<Usage> updatedUsages = aaclUsageRepository.findByIds(Collections.singletonList(USAGE_ID_1));
        assertEquals(1, CollectionUtils.size(updatedUsages));
        Usage updatedUsage = updatedUsages.get(0);
        assertEquals(RH_ACCOUNT_NUMBER, updatedUsage.getRightsholder().getAccountNumber());
        assertEquals(UsageStatusEnum.RH_FOUND, updatedUsage.getStatus());
        assertEquals(STANDARD_NUMBER_TYPE, updatedUsage.getStandardNumberType());
        assertEquals(STANDARD_NUMBER, updatedUsage.getStandardNumber());
        assertEquals(SYSTEM_TITLE, updatedUsage.getSystemTitle());
        assertEquals(RIGHT_LIMITATION, updatedUsage.getAaclUsage().getRightLimitation());
    }

    @Test
    public void testFindReferencedAaclUsagesCountByIds() {
        assertEquals(2, aaclUsageRepository.findReferencedAaclUsagesCountByIds(USAGE_ID_1, USAGE_ID_2));
        assertEquals(0, aaclUsageRepository.findReferencedAaclUsagesCountByIds("nonExistingId"));
    }

    @Test
    public void testFindByIds() {
        List<Usage> actualUsages = aaclUsageRepository.findByIds(Arrays.asList(USAGE_ID_1, USAGE_ID_2));
        verifyUsages(Arrays.asList("json/aacl/aacl_usage_0b5ac9fc.json", "json/aacl/aacl_usage_6c91f04e.json"),
            actualUsages, this::verifyUsage);
    }

    @Test
    public void testFindDtosByBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("38e3190a-cf2b-4a2a-8a14-1f6e5f09011c"));
        verifyUsageDtos(Collections.singletonList("json/aacl/aacl_usage_dto_ce439b92.json"),
            aaclUsageRepository.findDtosByFilter(usageFilter, null, null));
    }

    @Test
    public void testFindDtosByStatusAndPeriodFilters() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsagePeriod(2019);
        verifyUsageDtos(
            Arrays.asList("json/aacl/aacl_usage_dto_e21bcd1f.json", "json/aacl/aacl_usage_dto_0b5ac9fc.json",
                "json/aacl/aacl_usage_dto_6c91f04e.json"),
            aaclUsageRepository.findDtosByFilter(usageFilter, null, null));
    }

    @Test
    public void testFindDtosByPeriodFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsagePeriod(2018);
        verifyUsageDtos(Collections.singletonList("json/aacl/aacl_usage_dto_ce439b92.json"),
            aaclUsageRepository.findDtosByFilter(usageFilter, null, null));
    }

    @Test
    public void testFindDtosByBatchFilterWithScenarioUsages() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("a87b82ca-cfca-463d-96e9-fa856618c389"));
        assertEquals(Collections.emptyList(), aaclUsageRepository.findDtosByFilter(usageFilter, null, null));
    }

    @Test
    public void testFindCountByFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsagePeriod(2018);
        assertEquals(1, aaclUsageRepository.findCountByFilter(usageFilter));
    }

    @Test
    public void testFindCountByFilterWithScenarioUsages() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("a87b82ca-cfca-463d-96e9-fa856618c389"));
        assertEquals(0, aaclUsageRepository.findCountByFilter(usageFilter));
    }

    @Test
    public void testFindBaselinePeriodsStartingFromPeriodWithoutUsages() {
        assertEquals(Collections.singleton(2019), aaclUsageRepository.findBaselinePeriods(2020, 1));
        assertEquals(Sets.newHashSet(2019, 2018, 2016), aaclUsageRepository.findBaselinePeriods(2020, 3));
    }

    @Test
    public void testFindBaselinePeriodsStartingFromPeriodWithUsages() {
        assertEquals(Collections.singleton(2019), aaclUsageRepository.findBaselinePeriods(2019, 1));
        assertEquals(Sets.newHashSet(2019, 2018, 2016), aaclUsageRepository.findBaselinePeriods(2019, 3));
    }

    @Test
    public void testFindBaselinePeriodsWithNumberOfYearsGreaterThanExist() {
        assertEquals(Sets.newHashSet(2016, 2015), aaclUsageRepository.findBaselinePeriods(2016, 3));
    }

    @Test
    public void testFindBaselinePeriodsWithNonExistentPeriod() {
        assertEquals(Collections.emptySet(), aaclUsageRepository.findBaselinePeriods(2014, 2));
    }

    @Test
    public void testDeleteByBatchId() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(ImmutableSet.of("940ca71c-fd90-4ffd-aa20-b293c0f49891"));
        assertEquals(2, aaclUsageRepository.findCountByFilter(filter));
        aaclUsageRepository.deleteByBatchId("940ca71c-fd90-4ffd-aa20-b293c0f49891");
        assertEquals(0, aaclUsageRepository.findCountByFilter(filter));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchWithUsagesInCorrectStatusOnly() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("6e6f656a-e080-4426-b8ea-985b69f8814d"));
        assertTrue(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchWithUsagesInDifferentStatusOnly() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("38e3190a-cf2b-4a2a-8a14-1f6e5f09011c"));
        assertFalse(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchWithMixedUsageStatuses() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        assertFalse(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testAreValidFilteredUsageStatusesFilteringByBatchAndCorrectStatus() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        assertTrue(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchAndDifferentStatus() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        usageFilter.setUsageStatus(UsageStatusEnum.WORK_NOT_FOUND);
        assertFalse(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchAndStatusAndCorrectUsagePeriod() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsagePeriod(2020);
        assertTrue(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchAndStatusAndDifferentUsagePeriod() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsagePeriod(2012);
        assertTrue(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidFilteredUsageStatusFilteringByBatchAndStatusWithNoUsages() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton(BATCH_ID_3));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        assertTrue(aaclUsageRepository.isValidFilteredUsageStatus(usageFilter, UsageStatusEnum.RH_FOUND));
    }

    @Test
    public void testIsValidForClassification() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("38718bfe-08bd-4af7-8481-0fe16dcf2750"));
        assertTrue(aaclUsageRepository.isValidForClassification(usageFilter));
    }

    @Test
    public void testIsValidForClassificationWithEligibleStatus() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("38718bfe-08bd-4af7-8481-0fe16dcf2750"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        assertFalse(aaclUsageRepository.isValidForClassification(usageFilter));
    }

    @Test
    public void testIsValidForClassificationWithUsagesFromBaseline() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("52d9c8b9-0de9-4578-915a-60ec01243fc4"));
        assertFalse(aaclUsageRepository.isValidForClassification(usageFilter));
    }

    @Test
    public void testIsValidForClassificationWithEligibleStatusAndUsagesFromBaseline() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("52d9c8b9-0de9-4578-915a-60ec01243fc4"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        assertFalse(aaclUsageRepository.isValidForClassification(usageFilter));
    }

    @Test
    public void testFindInvalidRightsholdersByFilterWithBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("70a96dc1-b0a8-433f-a7f4-c5d94ee75a9e"));
        assertEquals(Arrays.asList(7000000001L, 7000000002L),
            aaclUsageRepository.findInvalidRightsholdersByFilter(usageFilter));
    }

    @Test
    public void testFindInvalidRightsholdersByFilterWithBatchAndPeriodFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("70a96dc1-b0a8-433f-a7f4-c5d94ee75a9e"));
        usageFilter.setUsagePeriod(2019);
        assertEquals(Collections.emptyList(), aaclUsageRepository.findInvalidRightsholdersByFilter(usageFilter));
        usageFilter.setUsagePeriod(2017);
        assertEquals(Collections.singletonList(7000000001L),
            aaclUsageRepository.findInvalidRightsholdersByFilter(usageFilter));
        usageFilter.setUsagePeriod(2015);
        assertEquals(Collections.singletonList(7000000002L),
            aaclUsageRepository.findInvalidRightsholdersByFilter(usageFilter));
    }

    @Test
    public void testFindUsagePeriods() {
        List<Integer> usagePeriods = aaclUsageRepository.findUsagePeriods();
        assertEquals(8, usagePeriods.size());
        assertEquals(2010, usagePeriods.get(0).intValue());
        assertEquals(2015, usagePeriods.get(1).intValue());
        assertEquals(2017, usagePeriods.get(2).intValue());
        assertEquals(2018, usagePeriods.get(3).longValue());
        assertEquals(2019, usagePeriods.get(4).longValue());
        assertEquals(2020, usagePeriods.get(5).longValue());
        assertEquals(2021, usagePeriods.get(6).longValue());
        assertEquals(2040, usagePeriods.get(7).longValue());
    }

    @Test
    public void testFindUsagePeriodsByFilterWithBatchFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(
            Sets.newHashSet("31ac3937-157b-48c2-86b2-db28356fc868", "87279dd4-e100-4b72-a561-49e7effe8238"));
        List<Integer> usagePeriods = aaclUsageRepository.findUsagePeriodsByFilter(filter);
        assertEquals(3, usagePeriods.size());
        assertEquals(2020, usagePeriods.get(0).intValue());
        assertEquals(2019, usagePeriods.get(1).intValue());
        assertEquals(2010, usagePeriods.get(2).intValue());
    }

    @Test
    public void testFindUsagePeriodsByFilterWithBatchAndPeriodFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(
            Sets.newHashSet("31ac3937-157b-48c2-86b2-db28356fc868", "87279dd4-e100-4b72-a561-49e7effe8238"));
        filter.setUsagePeriod(2020);
        List<Integer> usagePeriods = aaclUsageRepository.findUsagePeriodsByFilter(filter);
        assertEquals(1, usagePeriods.size());
        assertEquals(2020, usagePeriods.get(0).intValue());
    }

    @Test
    public void testAddToScenarioByBatchAndStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        aaclUsageRepository.addToScenario(SCENARIO_ID_1, usageFilter, USER_NAME);
        aaclUsageRepository.findByIds(
            Arrays.asList("0cd30b3e-ae74-466a-a7b1-a2d891b2123e", "9342062f-568e-4c27-8f33-c010a2afe61e"))
            .forEach(actualUsage -> {
                assertEquals(SCENARIO_ID_1, actualUsage.getScenarioId());
                assertEquals(UsageStatusEnum.LOCKED, actualUsage.getStatus());
                assertEquals(USER_NAME, actualUsage.getUpdateUser());
            });
        aaclUsageRepository.findByIds(Collections.singletonList("e21bcd1f-8040-4b44-93c7-4af732ac1916"))
            .forEach(actualUsage -> {
                assertNull(actualUsage.getScenarioId());
                assertEquals(UsageStatusEnum.RH_FOUND, actualUsage.getStatus());
            });
    }

    @Test
    public void testAddToScenarioByBatchAndStatusAndPeriodFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setUsagePeriod(2019);
        aaclUsageRepository.addToScenario(SCENARIO_ID_1, usageFilter, USER_NAME);
        aaclUsageRepository.findByIds(Collections.singletonList("0cd30b3e-ae74-466a-a7b1-a2d891b2123e"))
            .forEach(actualUsage -> {
                assertEquals(SCENARIO_ID_1, actualUsage.getScenarioId());
                assertEquals(USER_NAME, actualUsage.getUpdateUser());
            });
        aaclUsageRepository.findByIds(
            Arrays.asList("9342062f-568e-4c27-8f33-c010a2afe61e", "e21bcd1f-8040-4b44-93c7-4af732ac1916"))
            .forEach(actualUsage -> assertNull(actualUsage.getScenarioId()));
    }

    @Test
    public void testExcludeFromScenarioByPayees() {
        assertEquals(6, aaclUsageRepository.findByScenarioId(SCENARIO_ID_3).size());
        Set<String> excludedIds =
            Sets.newHashSet("9ccf8b43-4ad5-4199-8c7f-c5884f27e44f", "ccb115c7-3444-4dbb-9540-7541961febdf");
        assertEquals(excludedIds, aaclUsageRepository.excludeFromScenarioByPayees(SCENARIO_ID_3,
            Collections.singleton(1000000026L), USER_NAME));
        List<Usage> excludedUsages = aaclUsageRepository.findByIds(new ArrayList<>(excludedIds));
        assertEquals(2, excludedUsages.size());
        excludedUsages.forEach(usage -> {
            assertEquals(UsageStatusEnum.SCENARIO_EXCLUDED, usage.getStatus());
            assertNull(usage.getScenarioId());
            assertNull(usage.getAaclUsage().getValueWeight());
            assertNull(usage.getAaclUsage().getVolumeWeight());
            assertNull(usage.getAaclUsage().getValueShare());
            assertNull(usage.getAaclUsage().getVolumeShare());
            assertNull(usage.getAaclUsage().getTotalShare());
        });
    }

    @Test
    public void testCalculateAmounts() {
        aaclUsageRepository.calculateAmounts(SCENARIO_ID_3, USER_NAME);
        List<Usage> usages = aaclUsageRepository.findByScenarioId(SCENARIO_ID_3);
        usages.sort(Comparator.comparing(Usage::getId));
        verifyZeroAmounts(usages.get(0));
        verifyAmounts(usages.get(1), "474.2846861858", "355.7135146393", "118.5711715464", "10.0000000000",
            "500.0000000000", "0.9000900090", "0.9970487357", "0.9485693724");
        verifyAmounts(usages.get(2), "8.6947406155", "6.5210554616", "2.1736851539", "0.3700000000", "0.7400000000",
            "0.0333033303", "0.0014756321", "0.0173894812");
        verifyAmounts(usages.get(3), "500.0000000000", "375.0000000000", "125.0000000000", "5.0000000000",
            AMOUNT_50, "1.0000000000", "1.0000000000", "1.0000000000");
        verifyAmounts(usages.get(4), "17.0205731987", "12.7654298991", "4.2551432997", "0.7400000000", "0.7400000000",
            "0.0666066607", "0.0014756321", "0.0340411464");
        verifyZeroAmounts(usages.get(5));
    }

    @Test
    public void testExcludeZeroAmountUsages() {
        assertEquals(6, aaclUsageRepository.findByScenarioId(SCENARIO_ID_3).size());
        aaclUsageRepository.excludeZeroAmountUsages(SCENARIO_ID_3, USER_NAME);
        List<Usage> excludedUsages = aaclUsageRepository.findByIds(
            Arrays.asList("d88c445f-4b4d-45ef-bc95-fdb9c4ba676d", "335dd803-3763-4fd7-ae78-31821a453fbf"));
        assertEquals(2, excludedUsages.size());
        excludedUsages.forEach(usage -> {
            assertEquals(UsageStatusEnum.SCENARIO_EXCLUDED, usage.getStatus());
            assertNull(usage.getScenarioId());
            verifyZeroAmounts(usage);
        });
        assertEquals(4, aaclUsageRepository.findByScenarioId(SCENARIO_ID_3).size());
    }

    @Test
    public void testFindPayeeTotalHoldersByFilter() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_5));
        List<PayeeTotalHolder> payeeTotalHolders = aaclUsageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(2, payeeTotalHolders.size());
        verifyPayeeTotalHolder(payeeTotalHolders.get(0), 2580011451L, "Delmar Learning, a division of Cengage Learning",
            "100.0000000000", "75.0000000000", "25.0000000000");
        verifyPayeeTotalHolder(payeeTotalHolders.get(1), 1000000027L, "Georgia State University Business Press [C]",
            "200.0000000000", "150.0000000000", AMOUNT_50);
    }

    @Test
    public void testFindPayeeTotalHoldersByFilterWithSearchByName() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_5));
        filter.setSearchValue("Delmar");
        List<PayeeTotalHolder> payeeTotalHolders = aaclUsageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(1, payeeTotalHolders.size());
        verifyPayeeTotalHolder(payeeTotalHolders.get(0), 2580011451L, "Delmar Learning, a division of Cengage Learning",
            "100.0000000000", "75.0000000000", "25.0000000000");
    }

    @Test
    public void testFindPayeeTotalHoldersByFilterWithSearchByAccountNumber() {
        ExcludePayeeFilter filter = new ExcludePayeeFilter();
        filter.setScenarioIds(Collections.singleton(SCENARIO_ID_5));
        filter.setSearchValue("10000");
        List<PayeeTotalHolder> payeeTotalHolders = aaclUsageRepository.findPayeeTotalHoldersByFilter(filter);
        assertEquals(1, payeeTotalHolders.size());
        verifyPayeeTotalHolder(payeeTotalHolders.get(0), 1000000027L, "Georgia State University Business Press [C]",
            "200.0000000000", "150.0000000000", AMOUNT_50);
    }

    @Test
    public void testFindForAudit() {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_5);
        verifyUsageDtosForAudit(loadExpectedUsageDtos(Collections.singletonList("json/aacl/aacl_audit_usage_dto.json")),
            aaclUsageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void testFindForAuditArchived() {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(USAGE_ID_6);
        verifyUsageDtosForAudit(
            loadExpectedUsageDtos(Collections.singletonList("json/aacl/aacl_archived_audit_usage_dto.json")),
            aaclUsageRepository.findForAudit(filter, null, null));
    }

    @Test
    public void findAuditByRightsholder() {
        AuditFilter filter = new AuditFilter();
        filter.setRhAccountNumbers(Sets.newHashSet(1000000027L));
        assertEquals(2, aaclUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = aaclUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, "f89f016d-0cc7-46b6-9f3f-63d2439458d5", "c7c0bbf9-e0a3-4d90-95af-aa9849e69404");
    }

    @Test
    public void testFindForAuditByBatch() {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton("9e0f99e4-1e95-488d-a0c5-ff1353c84e39"));
        assertEquals(3, aaclUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = aaclUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_5, USAGE_ID_4, USAGE_ID_6);
    }

    @Test
    public void testFindForAuditByStatus() {
        AuditFilter filter = new AuditFilter();
        filter.setStatuses(EnumSet.of(UsageStatusEnum.PAID));
        assertEquals(1, aaclUsageRepository.findCountForAudit(filter));
        List<UsageDto> usages = aaclUsageRepository.findForAudit(filter, new Pageable(0, 10), null);
        verifyUsageDtos(usages, USAGE_ID_6);
    }

    @Test
    public void testFindForAuditByPeriodDate() {
        assertFindForAuditSearchByPeriodDate(2020, USAGE_ID_4, USAGE_ID_5);
    }

    @Test
    public void testFindForAuditSearchByCccEventId() {
        assertFindForAuditSearchByCccEventId("53257", USAGE_ID_6);
        assertFindForAuditSearchByCccEventId(PERCENT);
        assertFindForAuditSearchByCccEventId(UNDERSCORE);
        assertFindForAuditSearchByCccEventId("3257");
        assertFindForAuditSearchByCccEventId("5325");
    }

    @Test
    public void testFindForAuditSearchByDistributionName() {
        assertFindForAuditSearchByDistributionName("AACL March 20", USAGE_ID_6);
        assertFindForAuditSearchByDistributionName(PERCENT);
        assertFindForAuditSearchByDistributionName(UNDERSCORE);
        assertFindForAuditSearchByDistributionName("AACL March 20", USAGE_ID_6);
        assertFindForAuditSearchByDistributionName("aacl march 20", USAGE_ID_6);
        assertFindForAuditSearchByDistributionName("ACL March 20");
        assertFindForAuditSearchByDistributionName("AACL March 2");
    }

    @Test
    public void testFindForAuditWithSearch() {
        assertFindForAuditSearch(USAGE_ID_4, USAGE_ID_4);
        assertFindForAuditSearch("122830309", USAGE_ID_5);
        assertFindForAuditSearch(PERCENT);
        assertFindForAuditSearch(UNDERSCORE);
    }

    @Test
    public void testUpdatePublicationTypeWeight() {
        aaclUsageRepository.updatePublicationTypeWeight(SCENARIO_ID_2, "1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e",
            new BigDecimal("10.12"), USER_NAME);
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        Usage usage1 =
            aaclUsageRepository.findByIds(Collections.singletonList("248add96-93d0-428d-9fe2-2e46c237a88b")).get(0);
        Usage usage2 =
            aaclUsageRepository.findByIds(Collections.singletonList("161652a5-d822-493b-8242-d35dc881646f")).get(0);
        Usage usage3 =
            aaclUsageRepository.findByIds(Collections.singletonList("61b7dc5a-aaec-482c-8c16-fe48a9464059")).get(0);
        assertNull(usage1.getAaclUsage().getPublicationType().getWeight());
        assertEquals(new BigDecimal("10.12"), usage2.getAaclUsage().getPublicationType().getWeight());
        assertEquals(new BigDecimal("1.71"), usage3.getAaclUsage().getPublicationType().getWeight());
    }

    @Test
    public void testUsagesExistByDetailLicenseeClassAndFilterWithBatchAndStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("d1108958-44cc-4bb4-9bb5-66fcf5b42104"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        assertTrue(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 108));
        assertFalse(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 110));
    }

    @Test
    public void testUsagesExistByDetailLicenseeClassAndFilterWithBatchAndStatusAndPeriodFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("d1108958-44cc-4bb4-9bb5-66fcf5b42104"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setUsagePeriod(2015);
        assertTrue(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 113));
        assertFalse(aaclUsageRepository.usagesExistByDetailLicenseeClassAndFilter(usageFilter, 110));
    }

    @Test
    public void testUpdatePayeeByAccountNumber() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setBatchesIds(Collections.singleton("a87b82ca-cfca-463d-96e9-fa856618c389"));
        aaclUsageRepository.findForAudit(auditFilter, null, null)
            .forEach(usage -> assertNull(usage.getPayeeAccountNumber()));
        aaclUsageRepository.updatePayeeByAccountNumber(1000000026L, SCENARIO_ID_2, RH_ACCOUNT_NUMBER, USER_NAME);
        aaclUsageRepository.findForAudit(auditFilter, null, null)
            .forEach(usage -> assertEquals(RH_ACCOUNT_NUMBER, usage.getPayeeAccountNumber()));
    }

    @Test
    public void testFindCountByScenarioIdAndRhAccountNumber() {
        assertEquals(2, aaclUsageRepository
            .findCountByScenarioIdAndRhAccountNumber("20bed3d9-8da3-470f-95d7-d839a41488d4", 1000011450L, null));
    }

    @Test
    public void testFindByScenarioIdAndRhAccountNumber() {
        List<UsageDto> expectedUsageDtos =
            loadExpectedUsageDtos(Collections.singletonList("json/aacl/aacl_usage_dtos.json"));
        List<UsageDto> actualUsageDtos = aaclUsageRepository
            .findByScenarioIdAndRhAccountNumber("20bed3d9-8da3-470f-95d7-d839a41488d4", 1000011450L, null, null, null);
        assertEquals(expectedUsageDtos.size(), actualUsageDtos.size());
        IntStream.range(0, expectedUsageDtos.size())
            .forEach(index -> verifyUsageDto(expectedUsageDtos.get(0), actualUsageDtos.get(0)));
    }

    @Test
    public void testSortingFindByScenarioIdAndRhAccountNumber() {
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "detailId", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "detailId", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "batchName", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "batchName", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "periodEndDate", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "periodEndDate", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "wrWrkInst", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "wrWrkInst", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "systemTitle", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "systemTitle", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "standardNumber", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "standardNumber", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "standardNumberType", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "standardNumberType", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "grossAmount", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "grossAmount", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "netAmount", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "netAmount", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "serviceFeeAmount", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "serviceFeeAmount", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "detailLicenseeClassId", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "detailLicenseeClassId", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "detailLicenseeEnrollment", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "detailLicenseeEnrollment", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "detailLicenseeDiscipline", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "detailLicenseeDiscipline", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "aggregateLicenseeClassId", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "aggregateLicenseeClassId", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "aggregateLicenseeEnrollment", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "aggregateLicenseeEnrollment", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "aggregateLicenseeDiscipline", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "aggregateLicenseeDiscipline", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "publicationType", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "publicationType", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "publicationTypeWeight", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "publicationTypeWeight", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "originalPublicationType", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "originalPublicationType", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "institution", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "institution", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "usagePeriod", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "usagePeriod", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "usageAgeWeight", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "usageAgeWeight", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "usageSource", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "usageSource", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "numberOfPages", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "numberOfPages", Sort.Direction.DESC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_7, "rightLimitation", Sort.Direction.ASC);
        assertSortingFindByScenarioIdAndRhAccountNumber(USAGE_ID_8, "rightLimitation", Sort.Direction.DESC);
    }

    @Test
    public void testDeleteFromScenario() {
        List<Usage> usages = aaclUsageRepository.findByScenarioId(SCENARIO_ID_4);
        assertEquals(2, usages.size());
        usages.forEach(this::verifyUsagesBeforeDeleteScenario);
        aaclUsageRepository.deleteFromScenario(SCENARIO_ID_4, "SYSTEM");
        List<Usage> usageList = aaclUsageRepository.findByIds(
            Arrays.asList("9c8ae08e-60a9-4819-b445-eb2bbc50635d", "8eb9dbbc-3535-42cc-8094-2d90849952e2"));
        assertEquals(2, usageList.size());
        verifyUsages(Collections.singletonList("json/aacl/aacl_usage_delete_scenario.json"), usageList,
            this::verifyUsageForDeleteScenario);
    }

    @Test
    public void testAddToBaselineByScenarioId() {
        assertEquals(8, aaclUsageRepository.findBaselineUsages().size());
        aaclUsageRepository.addToBaselineByScenarioId("45f17838-b5cb-47e2-a57a-8d128fa07edf", USER_NAME);
        List<Usage> actualBaselineUsages = aaclUsageRepository.findBaselineUsages();
        assertEquals(10, actualBaselineUsages.size());
        List<Usage> newBaselineUsages = aaclUsageRepository.findBaselineUsages().stream()
            .filter(usage -> Arrays.asList("Newly uploaded LOCKED usage", "Newly uploaded SCENARIO_EXCLUDED usage")
                .contains(usage.getComment()))
            .sorted(Comparator.comparing(Usage::getComment))
            .collect(Collectors.toList());
        assertEquals(2, newBaselineUsages.size());
        verifyUsages(Arrays.asList("json/aacl/aacl_baseline_for_locked.json",
            "json/aacl/aacl_baseline_for_scenario_excluded.json"), newBaselineUsages, this::verifyUsageIgnoringId);
    }

    @Test
    public void testDeleteLockedByScenarioId() {
        List<String> lockedUsageIds =
            Arrays.asList("f92c8af2-dea6-4243-ac58-01055932187e", "a46b6313-11de-4d6e-a51e-b50dd8239ec7");
        List<String> excludedUsageIds =
            Arrays.asList("45ad755f-5b8f-4f38-b362-d37de4b520eb", "bb87e43f-b755-467f-abdd-30ac0500aeff");
        assertEquals(4, aaclUsageRepository.findByIds(ListUtils.union(lockedUsageIds, excludedUsageIds)).size());
        aaclUsageRepository.deleteLockedByScenarioId("45f17838-b5cb-47e2-a57a-8d128fa07edf");
        assertEquals(0, aaclUsageRepository.findByIds(lockedUsageIds).size());
        assertEquals(2, aaclUsageRepository.findByIds(excludedUsageIds).size());
        assertEquals(2, aaclUsageRepository.findReferencedAaclUsagesCountByIds(lockedUsageIds.toArray(new String[]{})));
        assertEquals(2,
            aaclUsageRepository.findReferencedAaclUsagesCountByIds(excludedUsageIds.toArray(new String[]{})));
    }

    @Test
    public void testDeleteExcludedByScenarioId() {
        List<String> lockedUsageIds =
            Arrays.asList("f92c8af2-dea6-4243-ac58-01055932187e", "a46b6313-11de-4d6e-a51e-b50dd8239ec7");
        List<String> excludedUsageIds =
            Arrays.asList("45ad755f-5b8f-4f38-b362-d37de4b520eb", "bb87e43f-b755-467f-abdd-30ac0500aeff");
        assertEquals(4, aaclUsageRepository.findByIds(ListUtils.union(lockedUsageIds, excludedUsageIds)).size());
        aaclUsageRepository.deleteExcludedByScenarioId("45f17838-b5cb-47e2-a57a-8d128fa07edf");
        assertEquals(2, aaclUsageRepository.findByIds(lockedUsageIds).size());
        assertEquals(0, aaclUsageRepository.findByIds(excludedUsageIds).size());
        assertEquals(2, aaclUsageRepository.findReferencedAaclUsagesCountByIds(lockedUsageIds.toArray(new String[]{})));
        assertEquals(0,
            aaclUsageRepository.findReferencedAaclUsagesCountByIds(excludedUsageIds.toArray(new String[]{})));
    }

    @Test
    public void testFindPayeeAggClassesPairsByScenarioId() {
        AggregateLicenseeClass aggregateLicenseeClass = buildAggregateLicenseeClass(108, "EXGP", "Life Sciences");
        List<PayeeAccountAggregateLicenseeClassesPair> pairs = Arrays.asList(
            buildPayeeAggLcPair(1000000027L, aggregateLicenseeClass),
            buildPayeeAggLcPair(2580011451L, aggregateLicenseeClass,
                buildAggregateLicenseeClass(113, "MU", "Life Sciences")));
        assertEquals(pairs, aaclUsageRepository.findPayeeAggClassesPairsByScenarioId(SCENARIO_ID_5));
    }

    private void verifyUsagesBeforeDeleteScenario(Usage usage) {
        assertEquals(UsageStatusEnum.LOCKED, usage.getStatus());
        assertEquals(new BigDecimal("24.0000000000"), usage.getAaclUsage().getValueWeight());
        assertEquals(new BigDecimal("5.0000000000"), usage.getAaclUsage().getVolumeWeight());
        assertEquals(new BigDecimal("1.00"), usage.getAaclUsage().getPublicationType().getWeight());
        assertEquals(new BigDecimal("60.0000000000"), usage.getAaclUsage().getValueShare());
        assertEquals(new BigDecimal(AMOUNT_50), usage.getAaclUsage().getVolumeShare());
        assertEquals(new BigDecimal("2.0000000000"), usage.getAaclUsage().getTotalShare());
        assertNotNull(usage.getGrossAmount());
        assertNotNull(usage.getNetAmount());
        assertNotNull(usage.getServiceFeeAmount());
        assertNotNull(usage.getServiceFee());
        assertNotNull(usage.getGrossAmount());
        assertNotNull(usage.getPayee());
    }

    private void assertSortingFindByScenarioIdAndRhAccountNumber(String detailId, String sortProperty,
                                                                 Sort.Direction sortDirection) {
        String scenarioId = "20bed3d9-8da3-470f-95d7-d839a41488d4";
        List<UsageDto> usageDtos = aaclUsageRepository
            .findByScenarioIdAndRhAccountNumber(scenarioId, 1000011450L, null, null,
                new Sort(sortProperty, sortDirection));
        assertEquals(2, CollectionUtils.size(usageDtos));
        assertEquals(detailId, usageDtos.get(0).getId());
    }

    private void assertFindForAuditSearch(String searchValue, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setSearchValue(searchValue);
        assertEquals(usageIds.length, aaclUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(aaclUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditSearchByPeriodDate(int periodDate, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setProductFamily("AACL");
        filter.setBatchesIds(Collections.singleton("9e0f99e4-1e95-488d-a0c5-ff1353c84e39"));
        filter.setUsagePeriod(periodDate);
        assertEquals(usageIds.length, aaclUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(aaclUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditSearchByCccEventId(String cccEventId, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setCccEventId(cccEventId);
        assertEquals(usageIds.length, aaclUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(aaclUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void assertFindForAuditSearchByDistributionName(String distributionName, String... usageIds) {
        AuditFilter filter = new AuditFilter();
        filter.setDistributionName(distributionName);
        assertEquals(usageIds.length, aaclUsageRepository.findCountForAudit(filter));
        verifyUsageDtos(aaclUsageRepository.findForAudit(filter, null, null), usageIds);
    }

    private void verifyUsageDtos(List<UsageDto> usageDtos, String... usageIds) {
        assertNotNull(usageDtos);
        usageDtos.sort(Comparator.comparing(UsageDto::getId));
        Arrays.sort(usageIds);
        verifyUsageDtosInExactOrder(usageDtos, usageIds);
    }

    private void verifyUsageDtosInExactOrder(List<UsageDto> usageDtos, String... expectedIds) {
        assertNotNull(usageDtos);
        List<String> actualIds = usageDtos.stream()
            .map(UsageDto::getId)
            .collect(Collectors.toList());
        assertEquals(Arrays.asList(expectedIds), actualIds);
    }

    private void verifyUsages(List<String> expectedUsageJsonFiles, List<Usage> actualUsages,
                              BiConsumer<Usage, Usage> verifier) {
        List<Usage> expectedUsages = loadExpectedUsages(expectedUsageJsonFiles);
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifier.accept(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        verifyUsageIgnoringId(expectedUsage, actualUsage);
    }

    private void verifyUsageIgnoringId(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getBatchId(), actualUsage.getBatchId());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRightsholder().getName(), actualUsage.getRightsholder().getName());
        assertEquals(expectedUsage.getRightsholder().getId(), actualUsage.getRightsholder().getId());
        assertEquals(expectedUsage.getRightsholder().getAccountNumber(),
            actualUsage.getRightsholder().getAccountNumber());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifyUsageDtos(List<String> expectedUsageJsonFiles, List<UsageDto> actualUsages) {
        List<UsageDto> expectedUsages = loadExpectedUsageDtos(expectedUsageJsonFiles);
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsageDto(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyUsageForDeleteScenario(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertEquals(expectedUsage.getPayee().getAccountNumber(), actualUsage.getPayee().getAccountNumber());
        verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifyUsageDto(UsageDto expectedUsage, UsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchName(), actualUsage.getBatchName());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getNetAmount(), actualUsage.getNetAmount());
        assertEquals(expectedUsage.getServiceFeeAmount(), actualUsage.getServiceFeeAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifyAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getId(), actualAaclUsage.getPublicationType().getId());
        assertEquals(expectedAaclUsage.getPublicationType().getName(), actualAaclUsage.getPublicationType().getName());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
        assertEquals(expectedAaclUsage.getUsageAge().getWeight(), actualAaclUsage.getUsageAge().getWeight());
        assertEquals(expectedAaclUsage.getValueWeight(), actualAaclUsage.getValueWeight());
        assertEquals(expectedAaclUsage.getVolumeWeight(), actualAaclUsage.getVolumeWeight());
        assertEquals(expectedAaclUsage.getVolumeShare(), actualAaclUsage.getVolumeShare());
        assertEquals(expectedAaclUsage.getValueShare(), actualAaclUsage.getValueShare());
        assertEquals(expectedAaclUsage.getTotalShare(), actualAaclUsage.getTotalShare());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getDiscipline(),
            actualAaclUsage.getDetailLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getDetailLicenseeClass().getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getId(),
            actualAaclUsage.getAggregateLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getDiscipline(),
            actualAaclUsage.getAggregateLicenseeClass().getDiscipline());
        assertEquals(expectedAaclUsage.getAggregateLicenseeClass().getEnrollmentProfile(),
            actualAaclUsage.getAggregateLicenseeClass().getEnrollmentProfile());
    }

    private List<Usage> loadExpectedUsages(List<String> fileNames) {
        List<Usage> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
                }));
            } catch (IOException e) {
                throw new AssertionError(e);
            }
        });
        return usages;
    }

    private List<UsageDto> loadExpectedUsageDtos(List<String> fileNames) {
        List<UsageDto> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<UsageDto>>() {
                }));
            } catch (IOException e) {
                fail(e.getMessage());
            }
        });
        return usages;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamily("AACL");
        return usageFilter;
    }

    private long getNumberOfUsagesWithNotEmptyClassificationData() {
        UsageFilter usageFilter = new UsageFilter();
        usageFilter.setProductFamily("AACL");
        return aaclUsageRepository.findDtosByFilter(usageFilter, null, null).stream()
            .filter(usage -> StringUtils.isNotBlank(usage.getAaclUsage().getPublicationType().getName())
                && Objects.nonNull(usage.getAaclUsage().getDetailLicenseeClass().getId()))
            .count();
    }

    private AaclClassifiedUsage buildAaclClassifiedUsage() {
        AaclClassifiedUsage usage = new AaclClassifiedUsage();
        usage.setDetailId("8315e53b-0a7e-452a-a62c-17fe959f3f84");
        usage.setPublicationType("book");
        usage.setDiscipline("LIFE Sciences");
        usage.setEnrollmentProfile("EXGP");
        usage.setWrWrkInst(123456789L);
        usage.setComment("updated comment for AACL classified usage 1");
        return usage;
    }

    private PayeeAccountAggregateLicenseeClassesPair buildPayeeAggLcPair(Long accountNumber,
                                                                         AggregateLicenseeClass... aggregateClasses) {
        PayeeAccountAggregateLicenseeClassesPair pair = new PayeeAccountAggregateLicenseeClassesPair();
        pair.setPayeeAccountNumber(accountNumber);
        pair.setAggregateLicenseeClasses(Sets.newHashSet(aggregateClasses));
        return pair;
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(int aggLcId, String enrollmentProfile,
                                                               String discipline) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(aggLcId);
        aggregateLicenseeClass.setDiscipline(discipline);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        return aggregateLicenseeClass;
    }

    private void verifyUsageDtosForAudit(List<UsageDto> expectedUsages, List<UsageDto> actualUsages) {
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsageDtoForAudit(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyUsageDtoForAudit(UsageDto expectedUsage, UsageDto actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
        assertEquals(expectedUsage.getBatchName(), actualUsage.getBatchName());
        assertEquals(expectedUsage.getPaymentDate(), actualUsage.getPaymentDate());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getRhName(), actualUsage.getRhName());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getPayeeAccountNumber(), actualUsage.getPayeeAccountNumber());
        assertEquals(expectedUsage.getPayeeName(), actualUsage.getPayeeName());
        assertEquals(expectedUsage.getGrossAmount(), actualUsage.getGrossAmount());
        assertEquals(expectedUsage.getReportedValue(), actualUsage.getReportedValue());
        assertEquals(expectedUsage.getBatchGrossAmount(), actualUsage.getBatchGrossAmount());
        assertEquals(expectedUsage.getServiceFee(), actualUsage.getServiceFee());
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getScenarioName(), actualUsage.getScenarioName());
        assertEquals(expectedUsage.getCheckNumber(), actualUsage.getCheckNumber());
        assertEquals(expectedUsage.getCheckDate(), actualUsage.getCheckDate());
        assertEquals(expectedUsage.getCccEventId(), actualUsage.getCccEventId());
        assertEquals(expectedUsage.getDistributionName(), actualUsage.getDistributionName());
        assertEquals(expectedUsage.getDistributionDate(), actualUsage.getDistributionDate());
        assertEquals(expectedUsage.getPeriodEndDate(), actualUsage.getPeriodEndDate());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        assertNotNull(expectedUsage.getUpdateDate());
        verifyAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifyAmounts(Usage usage, String grossAmount, String netAmount, String serviceFeeAmount,
                               String volumeWeight, String valueWeight, String volumeShare, String valueShare,
                               String totalShare) {
        assertEquals(new BigDecimal(grossAmount), usage.getGrossAmount());
        assertEquals(new BigDecimal(netAmount), usage.getNetAmount());
        assertEquals(new BigDecimal(serviceFeeAmount), usage.getServiceFeeAmount());
        assertEquals(new BigDecimal("0.25000"), usage.getServiceFee());
        assertEquals(new BigDecimal(volumeWeight), usage.getAaclUsage().getVolumeWeight());
        assertEquals(new BigDecimal(valueWeight), usage.getAaclUsage().getValueWeight());
        assertEquals(new BigDecimal(volumeShare), usage.getAaclUsage().getVolumeShare());
        assertEquals(new BigDecimal(valueShare), usage.getAaclUsage().getValueShare());
        assertEquals(new BigDecimal(totalShare), usage.getAaclUsage().getTotalShare());
    }

    private void verifyZeroAmounts(Usage usage) {
        assertEquals(AMOUNT_0, usage.getGrossAmount());
        assertEquals(AMOUNT_0, usage.getNetAmount());
        assertEquals(AMOUNT_0, usage.getServiceFeeAmount());
        assertNull(usage.getServiceFee());
        assertNull(usage.getAaclUsage().getVolumeWeight());
        assertNull(usage.getAaclUsage().getValueWeight());
        assertNull(usage.getAaclUsage().getVolumeShare());
        assertNull(usage.getAaclUsage().getValueShare());
        assertNull(usage.getAaclUsage().getTotalShare());
    }

    private void verifyPayeeTotalHolder(PayeeTotalHolder holder, Long accountNumber, String name, String grossTotal,
                                        String netAmount, String serviceFeeAmount) {
        assertEquals(holder.getPayee().getAccountNumber(), accountNumber);
        assertEquals(holder.getPayee().getName(), name);
        assertEquals(holder.getGrossTotal(), new BigDecimal(grossTotal));
        assertEquals(holder.getNetTotal(), new BigDecimal(netAmount));
        assertEquals(holder.getServiceFeeTotal(), new BigDecimal(serviceFeeAmount));
    }
}
