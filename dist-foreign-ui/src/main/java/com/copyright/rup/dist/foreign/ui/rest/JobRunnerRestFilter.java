package com.copyright.rup.dist.foreign.ui.rest;

import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.quartz.WorksMatchingJob;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
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
 */
@Component
public class JobRunnerRestFilter implements Filter {

    @Autowired
    private IRightsService rightsService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private WorksMatchingJob worksMatchingJob;

    private Future future;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext webContext =
            WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        webContext.getAutowireCapableBeanFactory().configureBean(this, "jobRunnerRestFilter");
    }

    @Override
    public void destroy() {
        // empty method
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        if (req instanceof HttpServletRequest && resp instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            switch (StringUtils.lowerCase(
                request.getServletPath() + StringUtils.defaultString(request.getPathInfo()))) {
                case "/job/rightsholder":
                    handle(() -> rightsService.updateRightsholders(), response);
                    break;
                case "/job/ra":
                    handle(() -> rightsService.sendForRightsAssignment(), response);
                    break;
                case "/job/crm":
                    handle(() -> usageService.sendToCrm(), response);
                    break;
                case "/job/pi":
                    handle(() -> worksMatchingJob.executeInternal(null), response);
                    break;
                case "/job/status":
                    handle(null, response);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        }
    }

    private void handle(IJobRunner jobRunner, HttpServletResponse response) {
        if (Objects.isNull(future) || future.isDone()) {
            if (Objects.nonNull(jobRunner)) {
                future = executorService.submit(jobRunner::run);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    }

    private interface IJobRunner {
        void run();
    }
}
