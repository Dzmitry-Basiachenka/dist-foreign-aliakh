package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
@TestPropertySource(properties = {"test.liquibase.changelog=aacl-usage-repository-test-data-init.groovy"})
@Transactional
public class AaclUsageRepositoryIntegrationTest {

    private static final String SCENARIO_ID_1 = "09f85d7d-3a37-45b2-ab6e-7a341c3f115c";
    private static final String SCENARIO_ID_2 = "66d10c81-705e-4996-89f4-11e1635c4c31";
    private static final String USAGE_ID_1 = "0b5ac9fc-63e2-4162-8d63-953b7023293c";
    private static final String USAGE_ID_2 = "6c91f04e-60dc-49e0-9cdc-e782e0b923e2";
    private static final String USAGE_ID_3 = "5b41d618-0a2f-4736-bb75-29da627ad677";
    private static final String BATCH_ID_3 = "adcc460c-c4ae-4750-99e8-b9fe91787ce1";
    private static final String SYSTEM_TITLE = "Wissenschaft & Forschung Japan";
    private static final String USER_NAME = "user@mail.com";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final String RIGHT_LIMITATION = "ALL";
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
            .sorted(Comparator.comparing(usage -> usage.getAaclUsage().getUsagePeriod()))
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
        assertNull(expectedUsage.getAaclUsage().getDetailLicenseeClassId());
        assertNull(expectedUsage.getAaclUsage().getDiscipline());
        assertNull(expectedUsage.getAaclUsage().getEnrollmentProfile());
        assertEquals(5, getNumberOfUsagesWithNotEmptyClassificationData());
        aaclUsageRepository.updateClassifiedUsages(Collections.singletonList(buildAaclClassifiedUsage()), USER_NAME);
        assertEquals(6, getNumberOfUsagesWithNotEmptyClassificationData());
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
    public void testFindDtosByStatusFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        verifyUsageDtos(
            Arrays.asList("json/aacl/aacl_usage_dto_e21bcd1f.json", "json/aacl/aacl_usage_dto_44600a96.json",
                "json/aacl/aacl_usage_dto_67750f86.json", "json/aacl/aacl_usage_dto_0b5ac9fc.json",
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
    public void testFindCountByFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsagePeriod(2018);
        assertEquals(1, aaclUsageRepository.findCountByFilter(usageFilter));
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
    public void testIsValidFilteredUsageStatusFilteringByBatchAndCorrectStatus() {
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
    public void testFindUsagePeriods() {
        List<Integer> usagePeriods = aaclUsageRepository.findUsagePeriods();
        assertEquals(6, usagePeriods.size());
        assertEquals(2010, usagePeriods.get(0).intValue());
        assertEquals(2015, usagePeriods.get(1).intValue());
        assertEquals(2017, usagePeriods.get(2).intValue());
        assertEquals(2018, usagePeriods.get(3).longValue());
        assertEquals(2019, usagePeriods.get(4).longValue());
        assertEquals(2020, usagePeriods.get(5).longValue());
    }

    @Test
    public void testFindUsagePeriodsByFilter() {
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
    public void testAddToScenarioByBatchAndStatusFilter() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID_1);
        scenario.setUpdateUser(USER_NAME);
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        aaclUsageRepository.addToScenario(scenario, usageFilter);
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
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID_1);
        scenario.setUpdateUser(USER_NAME);
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        usageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        usageFilter.setUsagePeriod(2019);
        aaclUsageRepository.addToScenario(scenario, usageFilter);
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
    public void testUpdatePublicationTypeWeight() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID_2);
        scenario.setUpdateUser(USER_NAME);
        aaclUsageRepository.updatePublicationTypeWeight(scenario, "1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e",
            new BigDecimal("10.12"));
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("5ceb887e-502e-463a-ae94-f925feff35d8"));
        Usage usage1 =
            aaclUsageRepository.findByIds(Collections.singletonList("248add96-93d0-428d-9fe2-2e46c237a88b")).get(0);
        Usage usage2 =
            aaclUsageRepository.findByIds(Collections.singletonList("161652a5-d822-493b-8242-d35dc881646f")).get(0);
        Usage usage3 =
            aaclUsageRepository.findByIds(Collections.singletonList("61b7dc5a-aaec-482c-8c16-fe48a9464059")).get(0);
        assertNull(usage1.getAaclUsage().getPublicationTypeWeight());
        assertEquals(new BigDecimal("10.12"), usage2.getAaclUsage().getPublicationTypeWeight());
        assertEquals(new BigDecimal("1.71"), usage3.getAaclUsage().getPublicationTypeWeight());
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
        verifytAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifyUsageDtos(List<String> expectedUsageJsonFiles, List<UsageDto> actualUsages) {
        List<UsageDto> expectedUsages = loadExpectedUsageDtos(expectedUsageJsonFiles);
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsageDto(expectedUsages.get(index), actualUsages.get(index)));
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
        verifytAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void verifytAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getId(), actualAaclUsage.getPublicationType().getId());
        assertEquals(expectedAaclUsage.getPublicationType().getName(), actualAaclUsage.getPublicationType().getName());
        assertEquals(expectedAaclUsage.getPublicationTypeWeight(), actualAaclUsage.getPublicationTypeWeight());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getEnrollmentProfile(), actualAaclUsage.getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getDiscipline(), actualAaclUsage.getDiscipline());
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getDetailLicenseeClassId(), actualAaclUsage.getDetailLicenseeClassId());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
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
                && Objects.nonNull(usage.getAaclUsage().getDetailLicenseeClassId()))
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
}
