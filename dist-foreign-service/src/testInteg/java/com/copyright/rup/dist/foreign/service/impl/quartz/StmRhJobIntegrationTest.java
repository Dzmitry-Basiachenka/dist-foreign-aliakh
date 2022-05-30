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
 * Verifies {@link RhTaxJob}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/30/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class StmRhJobIntegrationTest {

    @Autowired
    private StmRhJob stmRhJob;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = "stm-rh-job-integration-test/test-execute-internal.groovy")
    public void testExecuteInternal() throws IOException {
        testHelper.createRestServer();
        testHelper.expectGetPreferences("75e057ac-7c24-4ae7-a0f5-aa75ea0895e6",
            "preferences/rh_1000009522_preferences_response.json");
        JobExecutionContext jobExecutionContext = createMock(JobExecutionContext.class);
        JobInfo jobInfo = new JobInfo(JobStatusEnum.FINISHED, "ProductFamily=NTS, UsagesCount=1");
        jobExecutionContext.setResult(jobInfo);
        expectLastCall().once();
        replay(jobExecutionContext);
        stmRhJob.executeInternal(jobExecutionContext);
        verify(jobExecutionContext);
        testHelper.assertUsages(testHelper.loadExpectedUsages("quartz/usage_448824345.json"));
        testHelper.assertAudit("1d798afe-dabb-4dca-9351-7a6ef64f3708",
            testHelper.loadExpectedUsageAuditItems("quartz/usage_audit_448824345.json"));
        testHelper.verifyRestServer();
    }
}
