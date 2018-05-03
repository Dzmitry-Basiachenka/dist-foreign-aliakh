package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.quartz.WorksMatchingJob;

import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.concurrent.Future;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
public class JobRunnerRestFilterTest extends EasyMockSupport {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private IRightsService rightsService;
    private IUsageService usageService;
    private WorksMatchingJob worksMatchingJob;
    private JobRunnerRestFilter filter;

    @Before
    public void setUp() {
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
        chain = createMock(FilterChain.class);
        rightsService = createMock(IRightsService.class);
        usageService = createMock(IUsageService.class);
        worksMatchingJob = createMock(WorksMatchingJob.class);
        filter = new JobRunnerRestFilter();
        Whitebox.setInternalState(filter, "rightsService", rightsService);
        Whitebox.setInternalState(filter, "usageService", usageService);
        Whitebox.setInternalState(filter, "worksMatchingJob", worksMatchingJob);
        expect(request.getServletPath()).andReturn(StringUtils.EMPTY);
    }

    @Test
    public void testRightsholderJob() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/job/rightsholder").once();
        rightsService.updateRightsholders();
        expectLastCall().once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        waitAndVerify();
    }

    @Test
    public void testRaJob() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/job/ra").once();
        rightsService.sendForRightsAssignment();
        expectLastCall().once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        waitAndVerify();
    }

    @Test
    public void testCrmJob() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/job/crm").once();
        usageService.sendToCrm();
        expectLastCall().once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        waitAndVerify();
    }

    @Test
    public void testPiJob() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/job/pi").once();
        worksMatchingJob.executeInternal(null);
        expectLastCall().once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        waitAndVerify();
    }

    @Test
    public void testWrongUrl() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/work/crm").once();
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    @Test
    public void testCheckStatus() throws ServletException, IOException, InterruptedException {
        expect(request.getPathInfo()).andReturn("/job/status").once();
        response.setStatus(HttpServletResponse.SC_OK);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    @Test
    public void testCheckStatusWhenAnotherJobIsRunning() throws ServletException, IOException, InterruptedException {
        Future future = createMock(Future.class);
        Whitebox.setInternalState(filter, "future", future);
        expect(request.getPathInfo()).andReturn("/job/status").once();
        expect(future.isDone()).andReturn(false).once();
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        expectLastCall().once();
        replayAll();
        filter.doFilter(request, response, chain);
        verifyAll();
    }

    private void waitAndVerify() throws InterruptedException {
        Future future = Whitebox.getInternalState(filter, "future");
        while (!future.isDone()) {
            Thread.sleep(10);
        }
        verifyAll();
    }
}
