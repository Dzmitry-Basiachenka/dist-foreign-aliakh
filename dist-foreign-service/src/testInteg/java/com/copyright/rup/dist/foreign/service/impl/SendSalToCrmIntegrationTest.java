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
 * Verifies logic for sending SAL {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s to CRM.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/17/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestData(fileName = "send-sal-to-crm-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendSalToCrmIntegrationTest {

    @Autowired
    private SendToCrmIntegrationTestBuilder testBuilder;

    @Test
    public void testSendToCrm() {
        testBuilder
            .expectCrmCall("crm/sendToCrm/rights_distribution_request_sal.json",
                "crm/sendToCrm/rights_distribution_response_sal.json")
            .expectJobInfo(buildJobInfo())
            .expectUsageStatus(ImmutableMap.of(
                "14704648-838e-444f-8987-c4f1dc3aa38d", UsageStatusEnum.ARCHIVED,
                "2b2cf124-8c96-4662-8949-c56002247f39", UsageStatusEnum.ARCHIVED))
            .expectScenarioStatus(ImmutableMap.of(
                "183c0b55-3665-4863-a28c-0370feccad24", ScenarioStatusEnum.ARCHIVED))
            .expectUsageAudit(ImmutableMap.of(
                "14704648-838e-444f-8987-c4f1dc3aa38d", buildArchivedUsageAudit(),
                "2b2cf124-8c96-4662-8949-c56002247f39", buildArchivedUsageAudit(),
                "52604648-838e-333f-8987-c4f1dc3aa38a", Collections.emptyList(),
                "563cf124-8c96-4662-8529-c56002247f39", Collections.emptyList()))
            .expectScenarioAudit(ImmutableMap.of(
                "183c0b55-3665-4863-a28c-0370feccad24", Collections.singletonList(
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
