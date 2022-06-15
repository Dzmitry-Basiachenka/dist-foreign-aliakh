package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verifies logic for sending {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s
 * of different product families to CRM.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/21/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestData(fileName = "send-to-crm-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendToCrmIntegrationTest {

    private static final String AACL_SCENARIO_UID = "351e585c-0b08-429d-9e31-bea283ba33de";
    private static final String FAS_SCENARIO_UID = "221c5a30-1937-4bf6-977f-93741f9b20f1";
    private static final String NTS_SCENARIO_UID = "67027e15-17c6-4b9b-b7f0-12ec414ad344";
    private static final String SAL_SCENARIO_UID = "183c0b55-3665-4863-a28c-0370feccad24";
    private static final String AACL_USAGE_UID = "e5ae9237-05a0-4c82-b607-0f91f19b2f24";
    private static final String FAS_USAGE_UID = "0d1829eb-de35-4f93-bb36-2a7435263051";
    private static final String NTS_USAGE_UID = "adcd15c4-eb44-4e67-847a-7f386082646a";
    private static final String SAL_USAGE_UID_1 = "14704648-838e-444f-8987-c4f1dc3aa38d";
    private static final String SAL_USAGE_UID_2 = "2b2cf124-8c96-4662-8949-c56002247f39";

    @Autowired
    private SendToCrmIntegrationTestBuilder testBuilder;

    @Test
    public void testSendToCrm() {
        testBuilder
            .expectCrmCall("crm/sendToCrm/rights_distribution_request.json",
                "crm/sendToCrm/rights_distribution_response.json")
            .expectJobInfo(buildJobInfo())
            .expectUsageStatus(ImmutableMap.of(
                AACL_USAGE_UID, UsageStatusEnum.ARCHIVED,
                FAS_USAGE_UID, UsageStatusEnum.ARCHIVED,
                NTS_USAGE_UID, UsageStatusEnum.ARCHIVED,
                SAL_USAGE_UID_1, UsageStatusEnum.ARCHIVED,
                SAL_USAGE_UID_2, UsageStatusEnum.ARCHIVED))
            .expectScenarioStatus(ImmutableMap.of(
                AACL_SCENARIO_UID, ScenarioStatusEnum.ARCHIVED,
                FAS_SCENARIO_UID, ScenarioStatusEnum.ARCHIVED,
                NTS_SCENARIO_UID, ScenarioStatusEnum.SENT_TO_LM,
                SAL_SCENARIO_UID, ScenarioStatusEnum.ARCHIVED))
            .expectUsageAudit(buildExpectedUsageAuditMap())
            .expectScenarioAudit(ImmutableMap.of(
                AACL_SCENARIO_UID, Collections.singletonList(
                    Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM")),
                FAS_SCENARIO_UID, Collections.singletonList(
                    Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM")),
                SAL_SCENARIO_UID, Collections.singletonList(
                    Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM")),
                NTS_SCENARIO_UID, Collections.emptyList()))
            .build()
            .run();
    }

    private Map<String, List<UsageAuditItem>> buildExpectedUsageAuditMap() {
        HashMap<String, List<UsageAuditItem>> expectedUsageAudit = new HashMap<>();
        expectedUsageAudit.put(AACL_USAGE_UID, buildArchivedUsageAudit());
        expectedUsageAudit.put(FAS_USAGE_UID, buildArchivedUsageAudit());
        expectedUsageAudit.put(NTS_USAGE_UID, buildArchivedUsageAudit());
        expectedUsageAudit.put(SAL_USAGE_UID_1, buildArchivedUsageAudit());
        expectedUsageAudit.put(SAL_USAGE_UID_2, buildArchivedUsageAudit());
        expectedUsageAudit.put("52604648-838e-333f-8987-c4f1dc3aa38a", Collections.emptyList());
        expectedUsageAudit.put("563cf124-8c96-4662-8529-c56002247f39", Collections.emptyList());
        return expectedUsageAudit;
    }

    private JobInfo buildJobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setStatus(JobStatusEnum.FINISHED);
        jobInfo.setResult(
            "PaidUsagesCount=5, ArchivedUsagesCount=5, NotReportedUsagesCount=0, ArchivedScenariosCount=3");
        return jobInfo;
    }

    private List<UsageAuditItem> buildArchivedUsageAudit() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(UsageActionTypeEnum.ARCHIVED);
        auditItem.setActionReason("Usage was sent to CRM");
        return Collections.singletonList(auditItem);
    }
}
