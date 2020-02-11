package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IFasUsageRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * .
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
@TestPropertySource(properties = {"test.liquibase.changelog=fas-usage-repository-test-data-init.groovy"})
@Transactional
public class FasUsageRepositoryIntegrationTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String STANDARD_NUMBER = "2192-3558";
    private static final String STANDARD_NUMBER_TYPE = "VALISBN13";
    private static final BigDecimal DEFAULT_ZERO_AMOUNT = new BigDecimal("0.0000000000");

    @Autowired
    private UsageRepository usageRepository;
    @Autowired
    private IFasUsageRepository fasUsageRepository;

    @Test
    public void testDeleteFromScenarioByPayees() {
        List<Usage> usagesBeforeExclude = usageRepository.findByIds(
            Arrays.asList("7234feb4-a59e-483b-985a-e8de2e3eb190", "582c86e2-213e-48ad-a885-f9ff49d48a69",
                "730d7964-f399-4971-9403-dbedc9d7a180"));
        assertEquals(3, CollectionUtils.size(usagesBeforeExclude));
        usagesBeforeExclude.forEach(usage ->
            assertEquals("edbcc8b3-8fa4-4c58-9244-a91627cac7a9", usage.getScenarioId()));
        Set<String> excludedIds = fasUsageRepository.deleteFromScenarioByPayees("edbcc8b3-8fa4-4c58-9244-a91627cac7a9",
            Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("730d7964-f399-4971-9403-dbedc9d7a180"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("730d7964-f399-4971-9403-dbedc9d7a180", "582c86e2-213e-48ad-a885-f9ff49d48a69"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "FAS2", UsageStatusEnum.ELIGIBLE));
        List<String> usageIds = usageRepository.findByScenarioId("edbcc8b3-8fa4-4c58-9244-a91627cac7a9")
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
        usagesBeforeExclude.forEach(usage ->
            assertEquals("767a2647-7e6e-4479-b381-e642de480863", usage.getScenarioId()));
        Set<String> excludedIds = fasUsageRepository.redesignateToNtsWithdrawnByPayees(
            "767a2647-7e6e-4479-b381-e642de480863", Collections.singleton(7000813806L), USER_NAME);
        assertEquals(2, CollectionUtils.size(excludedIds));
        assertTrue(excludedIds.contains("72f6abdb-c82d-4cee-aadf-570942cf0093"));
        List<Usage> usages = usageRepository.findByIds(
            Arrays.asList("72f6abdb-c82d-4cee-aadf-570942cf0093", "1ae671ca-ed5a-4d92-8ab6-a10a53d9884a"));
        assertEquals(2, CollectionUtils.size(usages));
        usages.forEach(usage -> verifyUsageExcludedFromScenario(usage, "NTS", UsageStatusEnum.NTS_WITHDRAWN));
        List<String> usageIds = usageRepository.findByScenarioId("767a2647-7e6e-4479-b381-e642de480863")
            .stream()
            .map(Usage::getId)
            .collect(Collectors.toList());
        assertEquals(1, CollectionUtils.size(usageIds));
        assertTrue(usageIds.contains("209a960f-5896-43da-b020-fc52981b9633"));
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
        verifyUsage(usageId1, "Technical Journal", 180382916L, STANDARD_NUMBER, "VALISSN",
            UsageStatusEnum.WORK_FOUND);
        verifyUsage(usageId2, "Medical Journal", 854030733L, "2192-3566", STANDARD_NUMBER_TYPE,
            UsageStatusEnum.WORK_FOUND);
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
}
