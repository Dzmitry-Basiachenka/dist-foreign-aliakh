package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Verifies {@link IUsageService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/22/17
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-service-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageServiceIntegrationTest {

    private static final List<String> SUPPORTED_PRODUCT_FAMILIES = Arrays.asList("FAS", "FAS2", "NTS");
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditRepository auditRepository;

    @Test
    public void testDeleteFromScenario() {
        Scenario scenario = new Scenario();
        scenario.setId("12ec845f-0e76-4d1c-85cd-bb3fb7ca260e");
        scenario.setName("Test Scenario for exclude");
        usageService.deleteFromScenario(scenario, 2000017001L, Lists.newArrayList(1000000003L, 1000000004L),
            "Exclude reason");
        verifyExcludedUsages(scenario, true, 1000000003L, 1000000004L);
        verifyExcludedUsages(scenario, false, 1000000001L, 1000000002L, 1000000006L);
        verifyAuditItems(auditRepository.findByUsageId("2641e7fe-2a5a-4cdf-8879-48816d705169"));
        verifyAuditItems(auditRepository.findByUsageId("405491b1-49a9-4b70-9cdb-d082be6a802d"));
        assertTrue(auditRepository.findByUsageId("9f96760c-0de9-4cee-abf2-65521277281b").isEmpty());
        assertTrue(auditRepository.findByUsageId("e4a81fad-7b0e-4c67-8df2-112c8913e45e").isEmpty());
        assertTrue(auditRepository.findByUsageId("4ddfcb74-cb72-48f6-9ee4-8b4e05afce75").isEmpty());
    }

    @Test
    public void testGetProductFamilies() {
        assertEquals(SUPPORTED_PRODUCT_FAMILIES, usageService.getProductFamilies());
    }

    @Test
    public void testGetProductFamiliesForAudit() {
        assertEquals(SUPPORTED_PRODUCT_FAMILIES, usageService.getProductFamiliesForAudit());
    }

    private void verifyExcludedUsages(Scenario scenario, boolean excluded, Long... accountNumbers) {
        Pageable pageable = new Pageable(0, 10);
        Arrays.stream(accountNumbers)
            .forEach(accountNumber -> assertEquals(excluded,
                usageService.getByScenarioAndRhAccountNumber(accountNumber, scenario, StringUtils.EMPTY, pageable,
                    null).isEmpty()));
    }

    private void verifyAuditItems(List<UsageAuditItem> auditItems) {
        assertEquals(1, auditItems.size());
        UsageAuditItem auditItem = auditItems.get(0);
        assertEquals(UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, auditItem.getActionType());
        assertEquals("Exclude reason", auditItem.getActionReason());
    }
}
