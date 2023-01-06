package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SendToCrmJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/16/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendToCrmJobIntegrationTest {

    @Autowired
    private SendToCrmJob sendToCrmJob;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private IScenarioRepository scenarioRepository;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "send-to-crm-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectCrmCall("crm/quartz/rights_distribution_request.json",
            "crm/quartz/rights_distribution_response.json", Collections.emptyList());
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED,
            "PaidUsagesCount=2, ArchivedUsagesCount=2, NotReportedUsagesCount=0, ArchivedScenariosCount=1");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        sendToCrmJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        verifyUsageStatus("06eae1b7-6132-4e19-b8ee-864a5ad65924", UsageStatusEnum.ARCHIVED);
        verifyUsageStatus("68ffad13-55c9-424c-88f7-a4289504d217", UsageStatusEnum.SENT_TO_LM);
        verifyUsageStatus("ac4ccfee-5fb6-4f9f-870d-35b2997f288f", UsageStatusEnum.ARCHIVED);
        verifyScenarioStatus("27755cf6-66fc-4636-862e-3d9c9f4e7a94", ScenarioStatusEnum.ARCHIVED);
        verifyScenarioStatus("612b1a40-411b-4819-9b67-cb4f6abc18eb", ScenarioStatusEnum.SENT_TO_LM);
        testHelper.assertAudit("06eae1b7-6132-4e19-b8ee-864a5ad65924",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_sent_to_crm.json"));
        testHelper.assertAudit("68ffad13-55c9-424c-88f7-a4289504d217", Collections.emptyList());
        testHelper.assertAudit("ac4ccfee-5fb6-4f9f-870d-35b2997f288f",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_sent_to_crm.json"));
        testHelper.assertScenarioAudit("27755cf6-66fc-4636-862e-3d9c9f4e7a94", List.of(
            Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM")));
        testHelper.assertScenarioAudit("612b1a40-411b-4819-9b67-cb4f6abc18eb", Collections.emptyList());
        testHelper.verifyRestServer();
    }

    private void verifyUsageStatus(String usageId, UsageStatusEnum status) {
        List<PaidUsage> usages = usageArchiveRepository.findByIdAndStatus(List.of(usageId), status);
        assertEquals(1, usages.size());
    }

    private void verifyScenarioStatus(String scenarioId, ScenarioStatusEnum status) {
        Scenario scenario = scenarioRepository.findById(scenarioId);
        assertNotNull(scenario);
        assertEquals(status, scenario.getStatus());
    }
}
