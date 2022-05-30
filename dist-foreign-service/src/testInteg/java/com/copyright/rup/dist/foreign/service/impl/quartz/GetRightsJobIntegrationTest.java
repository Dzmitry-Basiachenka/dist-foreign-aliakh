package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
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
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link GetRightsJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/27/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class GetRightsJobIntegrationTest {

    @Autowired
    private GetRightsJob getRightsJob;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "get-rights-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_876543210_request.json",
            "rights/rms_grants_876543210_response.json");
        testHelper.expectGetRmsRights("rights/udm/usage/rms_grants_658824345_request.json",
            "rights/udm/usage/rms_grants_658824345_response.json");
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED,
            "ProductFamily=FAS, UsagesCount=1; " +
            "ProductFamily=FAS2, Reason=There are no usages; " +
            "ProductFamily=NTS, Reason=There are no usages; " +
            "ProductFamily=AACL, Reason=There are no usages; " +
            "ProductFamily=SAL, Reason=There are no usages; " +
            "ProductFamily=ACL_UDM, UsagesCount=1");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        getRightsJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        testHelper.assertUsages(testHelper.loadExpectedUsages("quartz/usage_876543210.json"));
        testHelper.assertAudit("29ab73e6-2256-429d-bf36-e52315303165",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_876543210.json"));
        testHelper.assertUdmUsages(testHelper.loadExpectedUdmUsages("quartz/usage_658824345.json"),
            udmUsageService.getUdmUsagesByIds(Collections.singletonList("748223ba-cabd-481f-8c05-4fcdea550cb7")));
        testHelper.assertUdmUsageAudit("748223ba-cabd-481f-8c05-4fcdea550cb7",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_658824345.json"));
        testHelper.verifyRestServer();
    }
}
