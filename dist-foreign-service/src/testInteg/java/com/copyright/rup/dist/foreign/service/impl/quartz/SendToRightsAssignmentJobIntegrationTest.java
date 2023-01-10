package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link SendToRightsAssignmentJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/17/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class SendToRightsAssignmentJobIntegrationTest {

    @Autowired
    private SendToRightsAssignmentJob sendToRightsAssignmentJob;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private ISalUsageRepository salUsageRepository;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "send-to-rights-assignment-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectRmsRightsAssignmentCall("quartz/rights_assignment_946768462_946768461_request.json",
            "quartz/rights_assignment_946768462_946768461_response.json");
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "UsagesCount=3");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        sendToRightsAssignmentJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        verifyFasUsageStatus("8e812d97-a7b3-47a0-be4c-fd967ec11e63", UsageStatusEnum.NTS_WITHDRAWN);
        verifyFasUsageStatus("45d55a83-9acf-4e46-b179-179aa8ae05c5", UsageStatusEnum.NTS_WITHDRAWN);
        verifyFasUsageStatus("0acb64aa-32ee-4afb-813b-8241c490717b", UsageStatusEnum.NTS_WITHDRAWN);
        verifyFasUsageStatus("df0471f5-1f0d-4ce5-967b-53f7f9bd94e3", UsageStatusEnum.SENT_FOR_RA);
        verifyFasUsageStatus("03a70593-5064-446e-85c5-e277cb549d99", UsageStatusEnum.SENT_FOR_RA);
        verifyFasUsageStatus("7b997ecc-e427-43da-806c-979d85e27bd7", UsageStatusEnum.SENT_FOR_RA);
        verifyFasUsageStatus("9f32b35f-27cd-4f32-b775-ac3f8dab59d2", UsageStatusEnum.NEW);
        verifyFasUsageStatus("7e3f875a-1dc0-4aed-bd7e-2626b1338aa5", UsageStatusEnum.SENT_FOR_RA);
        verifySalUsageStatus("b0526981-0751-4953-9506-8cb2790e7f6d", UsageStatusEnum.NEW);
        verifySalUsageStatus("2f8f9bdc-7cf1-4b94-af3f-b31b61ab85d4", UsageStatusEnum.RH_NOT_FOUND);
        testHelper.assertAudit("8e812d97-a7b3-47a0-be4c-fd967ec11e63",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_nts_withdrawn.json"));
        testHelper.assertAudit("45d55a83-9acf-4e46-b179-179aa8ae05c5",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_nts_withdrawn.json"));
        testHelper.assertAudit("0acb64aa-32ee-4afb-813b-8241c490717b",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_nts_withdrawn.json"));
        testHelper.assertAudit("df0471f5-1f0d-4ce5-967b-53f7f9bd94e3",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_sent_for_rights_assignment.json"));
        testHelper.assertAudit("03a70593-5064-446e-85c5-e277cb549d99",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_sent_for_rights_assignment.json"));
        testHelper.assertAudit("7b997ecc-e427-43da-806c-979d85e27bd7",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_sent_for_rights_assignment.json"));
        testHelper.assertAudit("9f32b35f-27cd-4f32-b775-ac3f8dab59d2", List.of());
        testHelper.assertAudit("7e3f875a-1dc0-4aed-bd7e-2626b1338aa5", List.of());
        testHelper.assertAudit("b0526981-0751-4953-9506-8cb2790e7f6d", List.of());
        testHelper.assertAudit("2f8f9bdc-7cf1-4b94-af3f-b31b61ab85d4", List.of());
        testHelper.verifyRestServer();
    }

    private void verifyFasUsageStatus(String usageId, UsageStatusEnum status) {
        Usage usage = usageRepository.findByIds(List.of(usageId))
            .stream()
            .filter(u -> u.getId().equals(usageId))
            .findFirst()
            .orElseThrow(AssertionError::new);
        assertEquals(status, usage.getStatus());
    }

    private void verifySalUsageStatus(String usageId, UsageStatusEnum status) {
        Usage usage = salUsageRepository.findByIds(List.of(usageId))
            .stream()
            .filter(u -> u.getId().equals(usageId))
            .findFirst()
            .orElseThrow(AssertionError::new);
        assertEquals(status, usage.getStatus());
    }
}
