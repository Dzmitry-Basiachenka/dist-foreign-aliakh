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
 * Verifies {@link GetRightsSentForRaJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/15/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class GetRightsSentForRaJobIntegrationTest {

    @Autowired
    private GetRightsSentForRaJob getRightsSentForRaJob;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "get-rights-sent-for-ra-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_122824345_465159524_request.json",
            "rights/rms_grants_122824345_465159524_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000000322_response.json", 1000000322L);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED,
            "ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, Reason=There are no usages;");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        getRightsSentForRaJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        testHelper.assertUsages(testHelper.loadExpectedUsages("quartz/usages_122824345_465159524.json"));
        testHelper.assertAuditIgnoringOrder("14c3d141-5475-4789-8fe4-ecae63a2d262",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_122824345.json"));
        testHelper.assertAudit("e878dfa2-31d8-4506-a7fd-1f4778d406e6", Collections.emptyList());
        testHelper.verifyRestServer();
    }
}
