package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.ImmutableMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Verifies functionality for loading researched usages.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/32/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=load-researched-usages-data-init.groovy"})
public class LoadResearchedUsagesIntegrationTest {

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private ServiceTestHelper testHelper;

    private static final String USAGE_ID_1 = "c219108e-f319-4636-b837-b71bccb29b76";
    private static final String USAGE_ID_2 = "54580cd4-33b5-4079-bfc7-5c35bf9c5c9e";
    private static final String USAGE_ID_3 = "644cb9ba-396d-4844-ac83-8053412b7cea";

    @Test
    public void testLoadResearchedUsages() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_854030732_request.json",
            "rights/rms_grants_empty_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_658824345_request.json",
            "rights/rms_grants_658824345_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000023401_response.json", 1000023401L);
        usageService.loadResearchedUsages(Arrays.asList(
            buildResearchedUsage("c219108e-f319-4636-b837-b71bccb29b76", 658824345L, "Medical Journal"),
            buildResearchedUsage("54580cd4-33b5-4079-bfc7-5c35bf9c5c9e", 854030732L, "Technical Journal")));
        assertUsage("c219108e-f319-4636-b837-b71bccb29b76", UsageStatusEnum.ELIGIBLE, 658824345L, 1000023401L,
            "Medical Journal", "1008902112377654XX", "VALISSN");
        assertUsage("54580cd4-33b5-4079-bfc7-5c35bf9c5c9e", UsageStatusEnum.RH_NOT_FOUND, 854030732L, null,
            "Technical Journal", "2998622115929154XX", "VALISSN");
        assertUsage("644cb9ba-396d-4844-ac83-8053412b7cea", UsageStatusEnum.WORK_RESEARCH, null, null, null, null,
            null);
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible",
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000023401 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 658824345 was added based on research")));
        testHelper.assertAudit(USAGE_ID_2, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.RH_NOT_FOUND, "Rightsholder account for 854030732 was not found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 854030732 was added based on research")));
        testHelper.assertAudit(USAGE_ID_3, buildUsageAuditItems(USAGE_ID_3, ImmutableMap.of()));
        testHelper.verifyRestServer();
    }

    private void assertUsage(String usageId, UsageStatusEnum status, Long wrWrkInst, Long rhAccounNumber,
                             String systemTitle, String standardNumber, String standardNumberType) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(status, usage.getStatus());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(rhAccounNumber, usage.getRightsholder().getAccountNumber());
        assertEquals(systemTitle, usage.getSystemTitle());
        assertEquals(standardNumber, usage.getStandardNumber());
        assertEquals(standardNumberType, usage.getStandardNumberType());
    }

    private ResearchedUsage buildResearchedUsage(String usageId, Long wrWrkInst, String systemTitle) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setStandardNumber("1008902112377654XX");
        researchedUsage.setSystemTitle(systemTitle);
        researchedUsage.setUsageId(usageId);
        researchedUsage.setWrWrkInst(wrWrkInst);
        return researchedUsage;
    }

    private List<UsageAuditItem> buildUsageAuditItems(String usageId, Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setUsageId(usageId);
        map.forEach((actionTypeEnum, detail) -> {
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
        });
        return usageAuditItems;
    }
}
