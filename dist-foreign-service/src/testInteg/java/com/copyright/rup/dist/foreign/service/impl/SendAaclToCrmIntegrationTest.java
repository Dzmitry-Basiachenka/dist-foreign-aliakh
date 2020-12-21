package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies logic for sending AACL {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s to CRM.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/13/20
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=send-aacl-to-crm-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SendAaclToCrmIntegrationTest {

    @Autowired
    private SendToCrmIntegrationTestBuilder testBuilder;

    @Test
    public void testSendToCrm() {
        testBuilder
            .withProductFamilies(Collections.singleton("AACL"))
            .expectCrmCall("crm/sendToCrm/rights_distribution_request_aacl.json",
                "crm/sendToCrm/rights_distribution_response_aacl.json")
            .expectJobInfo(buildJobInfo())
            .expectUsageStatus(ImmutableMap.of(
                "8ab89fcc-abf9-432e-b653-e84f2605697f", UsageStatusEnum.ARCHIVED,
                "e5ae9237-05a0-4c82-b607-0f91f19b2f24", UsageStatusEnum.ARCHIVED))
            .expectScenarioStatus(ImmutableMap.of(
                "351e585c-0b08-429d-9e31-bea283ba33de", ScenarioStatusEnum.ARCHIVED))
            .expectUsageAudit(ImmutableMap.of(
                "8ab89fcc-abf9-432e-b653-e84f2605697f", buildArchivedUsageAudit(),
                "e5ae9237-05a0-4c82-b607-0f91f19b2f24", buildArchivedUsageAudit()))
            .expectScenarioAudit(ImmutableMap.of(
                "351e585c-0b08-429d-9e31-bea283ba33de", Collections.singletonList(
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
