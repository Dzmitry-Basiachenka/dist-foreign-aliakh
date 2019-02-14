package com.copyright.rup.dist.foreign.ui.servlet;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.reset;

import com.copyright.rup.dist.common.service.api.scheduler.ISchedulerService;
import com.copyright.rup.dist.common.service.api.scheduler.ScheduledJobStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.quartz.SchedulerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verifies {@link JobsRestServlet}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/14/2019
 *
 * @author Aliaksanr Liakh
 */
public class JobsRestServletTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JOBS_PATH = "/jobs";
    private static final String JOB_NAME = "testJobName";
    private static final String NO_SUCH_ELEMENT_EXCEPTION_TEXT = "Scheduled job not found. JobName=testJobName";

    private JobsRestServlet jobsRestServlet;
    private ISchedulerService schedulerService;

    @Before
    public void setUp() {
        jobsRestServlet = new JobsRestServlet();
        schedulerService = createMock(ISchedulerService.class);
        Whitebox.setInternalState(jobsRestServlet, "schedulerService", schedulerService);
        reset(schedulerService);
    }

    @Test
    public void testGetJobStatusJobWaiting() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.getJobStatus(JOB_NAME)).andReturn(ScheduledJobStatus.WAITING).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_OK, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doGet(request, response);
        verify(request, schedulerService, response);
        assertJobStatus(stream.getBody(), "WAITING");
    }

    @Test
    public void testGetJobStatusJobRunning() throws Exception {
        expect(schedulerService.getJobStatus(JOB_NAME)).andReturn(ScheduledJobStatus.RUNNING).once();
        HttpServletRequest request = expectRequest();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_ACCEPTED, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doGet(request, response);
        verify(request, schedulerService, response);
        assertJobStatus(stream.getBody(), "RUNNING");
    }

    @Test
    public void testGetJobStatusJobNotFoundByName() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.getJobStatus(JOB_NAME))
            .andThrow(new NoSuchElementException(NO_SUCH_ELEMENT_EXCEPTION_TEXT)).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_NOT_FOUND, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doGet(request, response);
        verify(request, schedulerService, response);
        assertErrorStatus(stream.getBody(), "NOT_FOUND", NO_SUCH_ELEMENT_EXCEPTION_TEXT);
    }

    @Test
    public void testGetJobStatusInternalServerError() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.getJobStatus(JOB_NAME)).andThrow(new SchedulerException()).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doGet(request, response);
        verify(request, schedulerService, response);
        assertErrorStatus(stream.getBody(), "INTERNAL_SERVER_ERROR", "org.quartz.SchedulerException");
    }

    @Test
    public void testTriggerJobJobTriggered() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.triggerJob(JOB_NAME)).andReturn(ScheduledJobStatus.TRIGGERED).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_OK, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doPost(request, response);
        verify(request, schedulerService, response);
        assertJobStatus(stream.getBody(), "TRIGGERED");
    }

    @Test
    public void testTriggerJobJobRunning() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.triggerJob(JOB_NAME)).andReturn(ScheduledJobStatus.RUNNING).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_ACCEPTED, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doPost(request, response);
        verify(request, schedulerService, response);
        assertJobStatus(stream.getBody(), "RUNNING");
    }

    @Test
    public void testTriggerJobJobNotFoundByName() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.triggerJob(JOB_NAME))
            .andThrow(new NoSuchElementException(NO_SUCH_ELEMENT_EXCEPTION_TEXT)).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_NOT_FOUND, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doPost(request, response);
        verify(request, schedulerService, response);
        assertErrorStatus(stream.getBody(), "NOT_FOUND", NO_SUCH_ELEMENT_EXCEPTION_TEXT);
    }

    @Test
    public void testTriggerJobInternalServerError() throws Exception {
        HttpServletRequest request = expectRequest();
        expect(schedulerService.triggerJob(JOB_NAME)).andThrow(new SchedulerException()).once();
        TestServletOutputStream stream = new TestServletOutputStream();
        HttpServletResponse response = expectResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, stream);
        replay(request, schedulerService, response);
        jobsRestServlet.doPost(request, response);
        verify(request, schedulerService, response);
        assertErrorStatus(stream.getBody(), "INTERNAL_SERVER_ERROR", "org.quartz.SchedulerException");
    }

    private HttpServletRequest expectRequest() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getServletPath()).andReturn(JOBS_PATH).once();
        expect(request.getPathInfo()).andReturn("/" + JOB_NAME).once();
        return request;
    }

    private HttpServletResponse expectResponse(int status, ServletOutputStream stream) throws IOException {
        HttpServletResponse response = createMock(HttpServletResponse.class);
        response.setStatus(status);
        expectLastCall().once();
        response.setHeader("Cache-Control", "no-cache");
        expectLastCall().once();
        response.setContentType("application/json; charset=utf-8");
        expectLastCall().once();
        expect(response.getOutputStream()).andReturn(stream).once();
        return response;
    }

    private void assertJobStatus(String json, String status) throws IOException {
        JobStatus jobStatus = OBJECT_MAPPER.readValue(json, JobStatus.class);
        assertEquals(status, jobStatus.getStatus());
    }

    private void assertErrorStatus(String json, String error, String message) throws IOException {
        ErrorStatus errorStatus = OBJECT_MAPPER.readValue(json, ErrorStatus.class);
        assertEquals(error, errorStatus.getError());
        assertEquals(message, errorStatus.getMessage());
    }

    private static class TestServletOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        @Override
        public void write(int b) throws IOException {
            baos.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // empty implementation is enough for the test class
        }

        String getBody() throws UnsupportedEncodingException {
            return baos.toString(StandardCharsets.UTF_8.toString());
        }
    }
}
