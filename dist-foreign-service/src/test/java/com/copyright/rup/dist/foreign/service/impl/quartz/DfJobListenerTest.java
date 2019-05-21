package com.copyright.rup.dist.foreign.service.impl.quartz;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.job.JobStatusProducer;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

import java.time.Instant;
import java.util.Date;

/**
 * Verifies {@link DfJobListener}.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/14/2019
 *
 * @author Uladzislau Shalamitski
 */
public class DfJobListenerTest {

    private static final String JOB_NAME = "df.service.rhEligibilityQuartzJob";

    private JobStatusProducer producer;
    private DfJobListener jobListener;
    private JobExecutionContext jobExecutionContext;

    @Before
    public void setUp() {
        jobListener = new DfJobListener();
        producer = createMock(JobStatusProducer.class);
        jobExecutionContext = createMock(JobExecutionContext.class);
        Whitebox.setInternalState(jobListener, "producer", producer);
    }

    @Test
    public void testJobWasExecuted() {
        Capture<JobInfo> jobInfoCapture = new Capture<>();
        expect(jobExecutionContext.getResult()).andReturn(buildJobInfo()).once();
        expect(jobExecutionContext.getFireTime()).andReturn(Date.from(Instant.ofEpochMilli(1519377196185L))).once();
        expect(jobExecutionContext.getJobDetail()).andReturn(buildJobDetail()).once();
        producer.send(capture(jobInfoCapture));
        expectLastCall().once();
        replay(producer, jobExecutionContext);
        jobListener.jobWasExecuted(jobExecutionContext, null);
        JobInfo capturedJobInfo = jobInfoCapture.getValue();
        assertEquals(JobStatusEnum.FINISHED, capturedJobInfo.getStatus());
        assertEquals(JOB_NAME, capturedJobInfo.getName());
        assertEquals("ProductFamily=FAS, UsagesCount=100", capturedJobInfo.getResult());
        verify(producer, jobExecutionContext);
    }

    @Test
    public void testJobWasExecutedWithException() {
        Capture<JobInfo> jobInfoCapture = new Capture<>();
        expect(jobExecutionContext.getFireTime()).andReturn(Date.from(Instant.ofEpochMilli(1519377196185L))).once();
        expect(jobExecutionContext.getJobDetail()).andReturn(buildJobDetail()).once();
        producer.send(capture(jobInfoCapture));
        expectLastCall().once();
        replay(producer, jobExecutionContext);
        jobListener.jobWasExecuted(jobExecutionContext, new JobExecutionException());
        JobInfo capturedJobInfo = jobInfoCapture.getValue();
        assertEquals(JobStatusEnum.FAILED, capturedJobInfo.getStatus());
        assertEquals(JOB_NAME, capturedJobInfo.getName());
        verify(producer, jobExecutionContext);
    }

    private JobInfo buildJobInfo() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setStatus(JobStatusEnum.FINISHED);
        jobInfo.setResult("ProductFamily=FAS, UsagesCount=100");
        return jobInfo;
    }

    private JobDetail buildJobDetail() {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(JOB_NAME);
        return jobDetail;
    }
}
