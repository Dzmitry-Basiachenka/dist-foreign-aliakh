package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "load-researched-usages-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadResearchedUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "c219108e-f319-4636-b837-b71bccb29b76";
    private static final String USAGE_ID_2 = "54580cd4-33b5-4079-bfc7-5c35bf9c5c9e";
    private static final String USAGE_ID_3 = "644cb9ba-396d-4844-ac83-8053412b7cea";

    @Autowired
    private IFasUsageService fasUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testLoadResearchedUsages() {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/fas/rms_grants_request_2.json", "rights/fas/rms_grants_response_2.json");
        testHelper.expectPrmCall("prm/rightsholder_1000023401_response.json", 1000023401L);
        fasUsageService.loadResearchedUsages(List.of(
            buildResearchedUsage(USAGE_ID_1, 658824345L, "1008902112377654XX", "VALISBN13", "Medical Journal"),
            buildResearchedUsage(USAGE_ID_2, 854030732L, null, null, "Technical Journal")));
        testHelper.assertUsages(List.of(buildUsage(USAGE_ID_1, UsageStatusEnum.ELIGIBLE, 658824345L,
            1000023401L, "Medical Journal", "1008902112377654XX", "VALISSN"),
            buildUsage(USAGE_ID_2, UsageStatusEnum.RH_NOT_FOUND, 854030732L, null, "Technical Journal",
                "2998622115929154XX", "VALISSN"),
            buildUsage(USAGE_ID_3, UsageStatusEnum.WORK_RESEARCH, null, null, null, null, null)
        ));
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

    private Usage buildUsage(String usageId, UsageStatusEnum status, Long wrWrkInst, Long rhAccounNumber,
                             String systemTitle, String standardNumber, String standardNumberType) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(rhAccounNumber);
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setStatus(status);
        usage.setWrWrkInst(wrWrkInst);
        usage.setRightsholder(rightsholder);
        usage.setSystemTitle(systemTitle);
        usage.setStandardNumber(standardNumber);
        usage.setStandardNumberType(standardNumberType);
        usage.setPublicationDate(LocalDate.of(2013, 9, 10));
        usage.setPublisher("Network for Science");
        usage.setAuthor("Philippe de Mézières");
        usage.setArticle("DIN EN 779:2012");
        usage.setWorkTitle("Wissenschaft & Forschung Japan");
        usage.setProductFamily("FAS");
        usage.setMarket("Doc Del");
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setReportedValue(new BigDecimal("500.00"));
        usage.setGrossAmount(new BigDecimal("500.0000000000"));
        usage.setNetAmount(new BigDecimal("420.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("80.0000000000"));
        usage.setServiceFee(new BigDecimal("0.16000"));
        return usage;
    }

    private ResearchedUsage buildResearchedUsage(String usageId, Long wrWrkInst, String standardNumber,
                                                 String standardNumberType, String systemTitle) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setStandardNumber(standardNumber);
        researchedUsage.setStandardNumberType(standardNumberType);
        researchedUsage.setSystemTitle(systemTitle);
        researchedUsage.setUsageId(usageId);
        researchedUsage.setWrWrkInst(wrWrkInst);
        return researchedUsage;
    }

    private List<UsageAuditItem> buildUsageAuditItems(String usageId, Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setUsageId(usageId);
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }
}
