package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verifies {@link JobRunnerRestFilter}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/25/2018
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JobKey.class})
public class JobRunnerRestFilterTest {

    private static final String REQUEST = "/job/trigger";
    private static final String PARAMETER = "name";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private JobRunnerRestFilter filter;
    private SchedulerFactoryBean schedulerFactoryBean;
    private JobKey jobKey;

    @Before
    public void setUp() {
        mockStatic(JobKey.class);
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
        chain = createMock(FilterChain.class);
        jobKey = createMock(JobKey.class);
        filter = new JobRunnerRestFilter();
        schedulerFactoryBean = createMock(SchedulerFactoryBean.class);
        Whitebox.setInternalState(filter, schedulerFactoryBean);
        expect(request.getServletPath()).andReturn(StringUtils.EMPTY);
    }

    @Test
    public void testRightsholderJob() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("rightsholder").once();
        expectJobTriggers("df.service.updateRightsholdersQuartzJob");
    }

    @Test
    public void testGetRightsJob() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("getrights").once();
        expectJobTriggers("df.service.getRightsQuartzJob");
    }

    @Test
    public void testSendForRAJob() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("sendforra").once();
        expectJobTriggers("df.service.sendToRightsAssignmentQuartzJob");
    }

    @Test
    public void testCrmJob() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("crm").once();
        expectJobTriggers("df.service.sendToCrmQuartzJob");
    }

    @Test
    public void testPiJob() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("pi").once();
        expectJobTriggers("df.service.worksMatchingQuartzJob");
    }

    @Test
    public void testJobStatus() throws SchedulerException {
        expect(request.getPathInfo()).andReturn("/job/status").once();
        expect(request.getParameter(PARAMETER)).andReturn("crm").once();
        expect(JobKey.jobKey("df.service.sendToCrmQuartzJob")).andReturn(jobKey).once();
        Scheduler scheduler = createMock(Scheduler.class);
        expect(schedulerFactoryBean.getScheduler()).andReturn(scheduler).once();
        expect(scheduler.getJobDetail(jobKey)).andReturn(new JobDetailImpl()).once();
        expect(scheduler.getCurrentlyExecutingJobs()).andReturn(Collections.emptyList()).once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    @Test
    public void testInvalidJobStatus() throws SchedulerException {
        expect(request.getPathInfo()).andReturn("/job/status").once();
        expect(request.getParameter(PARAMETER)).andReturn("crm").once();
        expect(JobKey.jobKey("df.service.sendToCrmQuartzJob")).andReturn(null).once();
        Scheduler scheduler = createMock(Scheduler.class);
        expect(schedulerFactoryBean.getScheduler()).andReturn(scheduler).once();
        expect(scheduler.getJobDetail(null)).andReturn(null).once();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    @Test
    public void testWrongUrl() {
        expect(request.getPathInfo()).andReturn("/work/crm").once();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    @Test
    public void testJobIsAlreadyRunning() throws SchedulerException {
        expect(request.getPathInfo()).andReturn(REQUEST).once();
        expect(request.getParameter(PARAMETER)).andReturn("pi").once();
        expect(JobKey.jobKey("df.service.worksMatchingQuartzJob")).andReturn(jobKey).once();
        Scheduler scheduler = createMock(Scheduler.class);
        expect(scheduler.getJobDetail(jobKey)).andReturn(new JobDetailImpl()).once();
        expect(schedulerFactoryBean.getScheduler()).andReturn(scheduler).once();
        JobExecutionContext context = createMock(JobExecutionContext.class);
        expect(scheduler.getCurrentlyExecutingJobs()).andReturn(Collections.singletonList(context)).once();
        JobDetail jobDetail = createMock(JobDetail.class);
        expect(context.getJobDetail()).andReturn(jobDetail).once();
        expect(jobDetail.getKey()).andReturn(jobKey).once();
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    private void expectJobTriggers(String jobName) throws SchedulerException {
        expect(JobKey.jobKey(jobName)).andReturn(jobKey).once();
        Scheduler scheduler = createMock(Scheduler.class);
        expect(schedulerFactoryBean.getScheduler()).andReturn(scheduler).once();
        expect(scheduler.getJobDetail(jobKey)).andReturn(new JobDetailImpl()).once();
        expect(scheduler.getCurrentlyExecutingJobs()).andReturn(Collections.emptyList()).once();
        scheduler.triggerJob(jobKey);
        expectLastCall().once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }
}
