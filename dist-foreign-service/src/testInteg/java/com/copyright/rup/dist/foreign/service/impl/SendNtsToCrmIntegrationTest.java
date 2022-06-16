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
 * Verifies logic for sending NTS {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s to CRM.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 7/10/19
 *
 * @author Stanislau Rudak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestData(fileName = "send-nts-to-crm-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendNtsToCrmIntegrationTest {

    @Autowired
    private SendToCrmIntegrationTestBuilder testBuilder;

    @Test
    public void testSendToCrm() {
        testBuilder
            .expectCrmCall("crm/sendToCrm/rights_distribution_request_nts.json",
                "crm/sendToCrm/rights_distribution_response_nts.json")
            .expectJobInfo(buildJobInfo())
            .expectUsageStatus(ImmutableMap.of(
                "adcd15c4-eb44-4e67-847a-7f386082646a", UsageStatusEnum.ARCHIVED,
                "6fa92092-5cd3-4a12-bbf4-762f7ff6f815", UsageStatusEnum.ARCHIVED))
            .expectScenarioStatus(ImmutableMap.of(
                "67027e15-17c6-4b9b-b7f0-12ec414ad344", ScenarioStatusEnum.ARCHIVED
            ))
            .expectUsageAudit(ImmutableMap.of(
                "adcd15c4-eb44-4e67-847a-7f386082646a", buildArchivedUsageAudit(),
                "6fa92092-5cd3-4a12-bbf4-762f7ff6f815", buildArchivedUsageAudit()))
            .expectScenarioAudit(ImmutableMap.of(
                "67027e15-17c6-4b9b-b7f0-12ec414ad344", Collections.singletonList(
                    Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM"))))
            .build()
            .run();
    }

    private JobInfo buildJobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setStatus(JobStatusEnum.FINISHED);
        jobInfo.setResult(
            "PaidUsagesCount=2, ArchivedUsagesCount=2, NotReportedUsagesCount=0, ArchivedScenariosCount=1");
        return jobInfo;
    }

    private List<UsageAuditItem> buildArchivedUsageAudit() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(UsageActionTypeEnum.ARCHIVED);
        auditItem.setActionReason("Usage was sent to CRM");
        return Collections.singletonList(auditItem);
    }
}
