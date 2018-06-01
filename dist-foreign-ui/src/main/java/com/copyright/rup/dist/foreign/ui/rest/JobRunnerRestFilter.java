package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.common.exception.RupRuntimeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that runs jobs. Only one job can be run at a time.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 04/25/2018
 *
 * @author Uladzislau_Shalamitski
 * @author Ihar Suvorau
 */
@Component
public class JobRunnerRestFilter implements Filter {

    private ObjectMapper mapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void init(FilterConfig filterConfig) {
        this.mapper = new ObjectMapper();
        WebApplicationContext webContext =
            WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        webContext.getAutowireCapableBeanFactory().configureBean(this, "jobRunnerRestFilter");
    }

    @Override
    public void destroy() {
        // empty method
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            try {
                Scheduler scheduler = schedulerFactoryBean.getScheduler();
                switch (StringUtils.lowerCase(
                    request.getServletPath() + StringUtils.defaultString(request.getPathInfo()))) {
                    case "/job/trigger":
                        triggerJob(response, scheduler, request.getParameter("name"));
                        break;
                    case "/job/status":
                        getJobStatus(response, scheduler, request.getParameter("name"));
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        break;
                }
            } catch (SchedulerException e) {
                throw new RupRuntimeException(e);
            }
        }
    }

    private void triggerJob(HttpServletResponse response, Scheduler scheduler, String jobName)
        throws SchedulerException, IOException {
        JobKey jobKey = JobKey.jobKey(jobName);
        if (Objects.nonNull(scheduler.getJobDetail(jobKey))) {
            if (isJobCurrentlyRunning(jobKey, scheduler)) {
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                writeResponse(response, buildJsonResponse("ACCEPTED", "Already running", null));
            } else {
                scheduler.triggerJob(jobKey);
                response.setStatus(HttpServletResponse.SC_OK);
                writeResponse(response, buildJsonResponse("SUCCESS", "Triggered", null));
            }
        } else {
            writeJobNotFoundResponse(response);
        }
    }

    private void getJobStatus(HttpServletResponse response, Scheduler scheduler, String jobName)
        throws SchedulerException, IOException {
        JobKey jobKey = JobKey.jobKey(jobName);
        if (Objects.nonNull(scheduler.getJobDetail(jobKey))) {
            String jobStatus;
            if (isJobCurrentlyRunning(jobKey, scheduler)) {
                jobStatus = "RUNNING";
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                jobStatus = "STAND_BY";
                response.setStatus(HttpServletResponse.SC_OK);
            }
            writeResponse(response, buildJsonResponse("SUCCESS", "OK", jobStatus));
        } else {
            writeJobNotFoundResponse(response);
        }
    }

    private boolean isJobCurrentlyRunning(JobKey jobKey, Scheduler scheduler) throws SchedulerException {
        return scheduler.getCurrentlyExecutingJobs().stream()
            .anyMatch(job -> Objects.equals(job.getJobDetail().getKey(), jobKey));
    }

    private void writeJobNotFoundResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        writeResponse(response, buildJsonResponse("ERROR", "No such job", null));
    }

    private void writeResponse(HttpServletResponse response, String str) throws IOException {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setContentType(MediaType.JSON_UTF_8.toString());
        // This call is needed to hide response's write() method from FindBugs and avoid XSS_SERVLET warning
        StreamUtils.copy(str, StandardCharsets.UTF_8, response.getOutputStream());
    }

    private String buildJsonResponse(String code, String message, String jobStatus) {
        ObjectNode root = mapper.createObjectNode();
        ObjectNode response = mapper.createObjectNode();
        ObjectNode status = mapper.createObjectNode();
        status.put("code", code);
        status.put("message", message);
        response.set("status", status);
        if (Objects.nonNull(jobStatus)) {
            ObjectNode job = mapper.createObjectNode();
            job.put("status", jobStatus);
            response.set("job", job);
        }
        root.set("response", response);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new RupRuntimeException(e);
        }
    }
}
