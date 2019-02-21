package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.service.api.scheduler.ISchedulerService;
import com.copyright.rup.dist.common.service.api.scheduler.ScheduledJobStatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

/**
 * Verifies {@link JobsRest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/06/2019
 *
 * @author Aliaksanr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-rest-test-context.xml")
@WebAppConfiguration
public class JobsRestIntegrationTest {

    private static final String JOBS_PATH_TEMPLATE = "/jobs/%s";
    private static final String JOB_NAME = "testJobName";
    private static final String JSON_PATH_ERROR = "$.error";
    private static final String JSON_PATH_MESSAGE = "$.message";
    private static final String NO_SUCH_ELEMENT_EXCEPTION_TEXT = "Scheduled job not found. JobName=testJobName";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JobsRest jobsRest;

    @Autowired
    @Qualifier("dm.service.schedulerServiceMock")
    private ISchedulerService schedulerServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Whitebox.setInternalState(jobsRest, "schedulerService", schedulerServiceMock);
        reset(schedulerServiceMock);
    }

    @Test
    public void testGetJobStatusJobWaiting() throws Exception {
        expect(schedulerServiceMock.getJobStatus(JOB_NAME)).andReturn(ScheduledJobStatus.WAITING).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"WAITING\"}"));
        verify(schedulerServiceMock);
    }

    @Test
    public void testGetJobStatusJobRunning() throws Exception {
        expect(schedulerServiceMock.getJobStatus(JOB_NAME)).andReturn(ScheduledJobStatus.RUNNING).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"RUNNING\"}"));
        verify(schedulerServiceMock);
    }

    @Test
    public void testGetJobStatusJobNotFoundByName() throws Exception {
        expect(schedulerServiceMock.getJobStatus(JOB_NAME)).andThrow(new NoSuchElementException(
            NO_SUCH_ELEMENT_EXCEPTION_TEXT)).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR).value("NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE).value(NO_SUCH_ELEMENT_EXCEPTION_TEXT));
        verify(schedulerServiceMock);
    }

    @Test
    public void testGetJobStatusInternalServerError() throws Exception {
        expect(schedulerServiceMock.getJobStatus(JOB_NAME)).andThrow(new SchedulerException()).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR).value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE).value("org.quartz.SchedulerException"));
        verify(schedulerServiceMock);
    }

    @Test
    public void testTriggerJobJobTriggered() throws Exception {
        expect(schedulerServiceMock.triggerJob(JOB_NAME)).andReturn(ScheduledJobStatus.TRIGGERED).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"TRIGGERED\"}"));
        verify(schedulerServiceMock);
    }

    @Test
    public void testTriggerJobJobRunning() throws Exception {
        expect(schedulerServiceMock.triggerJob(JOB_NAME)).andReturn(ScheduledJobStatus.RUNNING).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"RUNNING\"}"));
        verify(schedulerServiceMock);
    }

    @Test
    public void testTriggerJobJobNotFoundByName() throws Exception {
        expect(schedulerServiceMock.triggerJob(JOB_NAME))
            .andThrow(new NoSuchElementException(NO_SUCH_ELEMENT_EXCEPTION_TEXT)).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR).value("NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE).value(NO_SUCH_ELEMENT_EXCEPTION_TEXT));
        verify(schedulerServiceMock);
    }

    @Test
    public void testTriggerJobInternalServerError() throws Exception {
        expect(schedulerServiceMock.triggerJob(JOB_NAME)).andThrow(new SchedulerException()).once();
        replay(schedulerServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.post(String.format(JOBS_PATH_TEMPLATE, JOB_NAME))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR).value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE).value("org.quartz.SchedulerException"));
        verify(schedulerServiceMock);
    }
}
