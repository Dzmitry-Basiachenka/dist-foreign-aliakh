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
import java.util.List;

/**
 * Verifies logic for sending FAS {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s to CRM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/04/18
 *
 * @author Darya Baraukova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "send-fas-to-crm-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendFasToCrmIntegrationTest {

    @Autowired
    private SendToCrmIntegrationTestBuilder testBuilder;

    @Test
    public void testSendToCrm() {
        testBuilder
            .expectCrmCall("crm/sendToCrm/rights_distribution_request_fas.json",
                "crm/sendToCrm/rights_distribution_response_fas.json")
            .expectJobInfo(buildJobInfo())
            .expectUsageStatus(ImmutableMap.of(
                "0d1829eb-de35-4f93-bb36-2a7435263051", UsageStatusEnum.ARCHIVED,
                "9e356e22-57b3-49b3-af99-155093a9dc0a", UsageStatusEnum.PAID,
                "53496a2f-fb52-4b5b-9f60-9034cceb69b9", UsageStatusEnum.PAID,
                "feefdfd2-71fe-4c0a-a701-9dacffa9bccb", UsageStatusEnum.SENT_TO_LM,
                "48189e92-b9d2-46be-94a4-c2adf83f21ce", UsageStatusEnum.ARCHIVED))
            .expectScenarioStatus(ImmutableMap.of(
                "cb7e3237-50c3-46a5-938e-46afd8c1e0bf", ScenarioStatusEnum.ARCHIVED,
                "221c5a30-1937-4bf6-977f-93741f9b20f1", ScenarioStatusEnum.SENT_TO_LM))
            .expectUsageAudit(ImmutableMap.of(
                "9e356e22-57b3-49b3-af99-155093a9dc0a", Collections.emptyList(),
                "53496a2f-fb52-4b5b-9f60-9034cceb69b9", Collections.emptyList(),
                "feefdfd2-71fe-4c0a-a701-9dacffa9bccb", Collections.emptyList(),
                "0d1829eb-de35-4f93-bb36-2a7435263051", buildArchivedUsageAudit(),
                "48189e92-b9d2-46be-94a4-c2adf83f21ce", buildArchivedUsageAudit()))
            .expectScenarioAudit(ImmutableMap.of(
                "cb7e3237-50c3-46a5-938e-46afd8c1e0bf", List.of(
                    Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM")),
                "221c5a30-1937-4bf6-977f-93741f9b20f1", Collections.emptyList()))
            .build()
            .run();
    }

    private JobInfo buildJobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setStatus(JobStatusEnum.FINISHED);
        jobInfo.setResult(
            "PaidUsagesCount=5, ArchivedUsagesCount=3, NotReportedUsagesCount=2, ArchivedScenariosCount=1");
        return jobInfo;
    }

    private List<UsageAuditItem> buildArchivedUsageAudit() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(UsageActionTypeEnum.ARCHIVED);
        auditItem.setActionReason("Usage was sent to CRM");
        return List.of(auditItem);
    }
}
