package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Verifies {@link UpdateRightsholdersJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/06/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UpdateRightsholdersJobIntegrationTest {

    @Autowired
    private UpdateRightsholdersJob updateRightsholdersJob;
    @Autowired
    private IRightsholderService rightsholderService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "update-rightsholders-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternalFinished() {
        assertRightsholder(1000009522);
        testHelper.createRestServer();
        testHelper.expectPrmCall("prm/rightsholder_1000000322_response.json", 1000000322L);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "RHsCount=1, UpdatedCount=1");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        updateRightsholdersJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        assertRightsholder(1000000322);
        testHelper.verifyRestServer();
    }

    @Test
    public void testExecuteInternalFinishedNoRhs() {
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "RHsCount=0, UpdatedCount=0");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        updateRightsholdersJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
    }

    @Test
    @TestData(fileName = "update-rightsholders-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternalSkipped() {
        assertRightsholder(1000009522);
        testHelper.createRestServer();
        testHelper.expectPrmCall("prm/not_found_response.json", 1000000322L);
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.SKIPPED, "RHsCount=1, UpdatedCount=0, Reason=No RHs found");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        updateRightsholdersJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        assertRightsholder(1000009522);
        testHelper.verifyRestServer();
    }

    private void assertRightsholder(long rightsholderId) {
        List<Rightsholder> rightsholders = rightsholderService.getAllWithSearch("", null, null);
        assertEquals(1, rightsholders.size());
        Rightsholder rightsholder = rightsholders.get(0);
        assertEquals(rightsholderId, rightsholder.getAccountNumber().longValue());
    }
}
