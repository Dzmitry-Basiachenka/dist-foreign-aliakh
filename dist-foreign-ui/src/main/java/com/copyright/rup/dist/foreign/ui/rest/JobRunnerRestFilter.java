package com.copyright.rup.dist.foreign.ui.rest;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Map;
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

    private static final Map<String, String> JOBS = ImmutableMap.of(
        "rightsholder", "df.service.updateRightsholdersQuartzJob",
        "getrights", "df.service.getRightsQuartzJob",
        "sendforra", "df.service.sendToRightsAssignmentQuartzJob",
        "crm", "df.service.sendToCrmQuartzJob",
        "pi", "df.service.worksMatchingQuartzJob");

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void init(FilterConfig filterConfig) {
        WebApplicationContext webContext =
            WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        webContext.getAutowireCapableBeanFactory().configureBean(this, "jobRunnerRestFilter");
    }

    @Override
    public void destroy() {
        // empty method
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            switch (StringUtils.lowerCase(
                request.getServletPath() + StringUtils.defaultString(request.getPathInfo()))) {
                case "/job/trigger":
                    triggerJob(JOBS.get(request.getParameter("name")), response);
                    break;
                case "/job/status":
                    getJobStatus(JOBS.get(request.getParameter("name")), response);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        }
    }

    private void triggerJob(String jobName, HttpServletResponse response) {
        JobKey jobKey = JobKey.jobKey(jobName);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        handleAction(jobKey, scheduler, response, () -> {
            try {
                scheduler.triggerJob(jobKey);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (SchedulerException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    private void getJobStatus(String jobName, HttpServletResponse response) {
        JobKey jobKey = JobKey.jobKey(jobName);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        handleAction(jobKey, scheduler, response, () -> response.setStatus(HttpServletResponse.SC_OK));
    }

    private void handleAction(JobKey jobKey, Scheduler scheduler, HttpServletResponse response,
                              IActionHandler actionHandler) {
        try {
            if (Objects.isNull(scheduler.getJobDetail(jobKey))) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                if (Objects.nonNull(scheduler.getCurrentlyExecutingJobs()
                    .stream()
                    .filter(job -> Objects.equals(job.getJobDetail().getKey(), jobKey))
                    .findFirst()
                    .orElse(null))) {
                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
                } else {
                    actionHandler.performAction();
                }
            }
        } catch (SchedulerException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private interface IActionHandler {
        void performAction();
    }
}
