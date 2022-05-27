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
import java.util.List;

/**
 * Verifies {@link WorksMatchingJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/29/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class WorksMatchingJobIntegrationTest {

    @Autowired
    private WorksMatchingJob worksMatchingJob;

    @Autowired
    private ServiceTestHelper testHelper;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "works-matching-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/rms_grants_292891647_request.json",
            "rights/rms_grants_292891647_response.json");
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED,
            "ProductFamily=FAS, UsagesCount=1; " +
            "ProductFamily=FAS2, Reason=There are no usages; " +
            "ProductFamily=AACL, Reason=There are no usages; " +
            "ProductFamily=SAL, Reason=There are no usages; " +
            "ProductFamily=ACL_UDM, Reason=There are no usages");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        worksMatchingJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        testHelper.assertUsages(testHelper.loadExpectedUsages("quartz/usage_292891647.json"));
        testHelper.assertAudit("03f307ac-81d1-4ab5-b037-9bd2ca899aab",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_292891647.json"));
        testHelper.verifyRestServer();
    }
}
