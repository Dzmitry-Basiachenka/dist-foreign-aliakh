package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
@Rollback
@Transactional
public class AaclUsageRepositoryIntegrationTest {

    private static final String USAGE_ID_1 = "0b5ac9fc-63e2-4162-8d63-953b7023293c";
    private static final String USAGE_ID_2 = "6c91f04e-60dc-49e0-9cdc-e782e0b923e2";
    private static final String USAGE_ID_3 = "5b41d618-0a2f-4736-bb75-29da627ad677";
    private static final String BATCH_ID_3 = "adcc460c-c4ae-4750-99e8-b9fe91787ce1";
    private static final String SYSTEM_TITLE = "Wissenschaft & Forschung Japan";
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
    private AaclUsageRepository aaclUsageRepository;

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
    public void testUpdate() {
        List<Usage> usages =
            aaclUsageRepository.findByIds(Collections.singletonList("8315e53b-0a7e-452a-a62c-17fe959f3f84"));
        assertEquals(1, usages.size());
        Usage expectedUsage = usages.get(0);
        assertEquals(UsageStatusEnum.RH_FOUND, expectedUsage.getStatus());
        assertNull(expectedUsage.getAaclUsage().getDetailLicenseeClassId());
        assertNull(expectedUsage.getAaclUsage().getDiscipline());
        assertNull(expectedUsage.getAaclUsage().getEnrollmentProfile());
        expectedUsage.setStatus(UsageStatusEnum.ELIGIBLE);
        expectedUsage.getAaclUsage().setPublicationType("Book");
        expectedUsage.getAaclUsage().setDetailLicenseeClassId("108");
        expectedUsage.getAaclUsage().setEnrollmentProfile("EXGP");
        expectedUsage.getAaclUsage().setDiscipline("Life Sciences");
        aaclUsageRepository.update(Collections.singletonList(expectedUsage));
        verifyUsages(Collections.singletonList("json/aacl/aacl_classified_usage_8315e53b.json"),
            aaclUsageRepository.findByIds(Collections.singletonList("8315e53b-0a7e-452a-a62c-17fe959f3f84")));
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
            actualUsages);
    }

    @Test
    public void testFindDtosByBatchFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageBatchesIds(Collections.singleton("38e3190a-cf2b-4a2a-8a14-1f6e5f09011c"));
        verifyUsageDtos(Collections.singletonList("json/aacl/aacl_usage_dto_ce439b92.json"),
            aaclUsageRepository.findDtosByFilter(usageFilter, null, null));
    }

    @Test
    public void testFindDtosByStatusAndBatchesIdFilter() {
        UsageFilter usageFilter = buildUsageFilter();
        usageFilter.setUsageStatus(UsageStatusEnum.RH_FOUND);
        usageFilter.setUsageBatchesIds(
            Sets.newHashSet("adcc460c-c4ae-4750-99e8-b9fe91787ce1", "6e6f656a-e080-4426-b8ea-985b69f8814d"));
        verifyUsageDtos(
            Arrays.asList("json/aacl/aacl_usage_dto_44600a96.json", "json/aacl/aacl_usage_dto_67750f86.json",
                "json/aacl/aacl_usage_dto_0b5ac9fc.json", "json/aacl/aacl_usage_dto_6c91f04e.json"),
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
        assertEquals(3, usagePeriods.size());
        assertEquals(2018, usagePeriods.get(0).longValue());
        assertEquals(2019, usagePeriods.get(1).longValue());
        assertEquals(2020, usagePeriods.get(2).longValue());
    }

    private void verifyUsages(List<String> usageIds, List<Usage> actualUsages) {
        List<Usage> expectedUsages = loadExpectedUsages(usageIds);
        assertEquals(CollectionUtils.size(expectedUsages), CollectionUtils.size(actualUsages));
        IntStream.range(0, expectedUsages.size())
            .forEach(index -> verifyUsage(expectedUsages.get(index), actualUsages.get(index)));
    }

    private void verifyUsage(Usage expectedUsage, Usage actualUsage) {
        assertEquals(expectedUsage.getId(), actualUsage.getId());
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

    private void verifyUsageDtos(List<String> usageIds, List<UsageDto> actualUsages) {
        List<UsageDto> expectedUsages = loadExpectedUsageDtos(usageIds);
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
        assertEquals(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getEnrollmentProfile(), actualAaclUsage.getEnrollmentProfile());
        assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getDiscipline(), actualAaclUsage.getDiscipline());
        assertEquals(expectedAaclUsage.getBatchPeriodEndDate(), actualAaclUsage.getBatchPeriodEndDate());
        assertEquals(expectedAaclUsage.getDetailLicenseeClassId(), actualAaclUsage.getDetailLicenseeClassId());
    }

    private List<Usage> loadExpectedUsages(List<String> fileNames) {
        List<Usage> usages = new ArrayList<>();
        fileNames.forEach(fileName -> {
            try {
                String content = TestUtils.fileToString(this.getClass(), fileName);
                usages.addAll(OBJECT_MAPPER.readValue(content, new TypeReference<List<Usage>>() {
                }));
            } catch (IOException e) {
                fail();
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
}
