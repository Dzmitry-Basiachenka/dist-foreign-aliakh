package com.copyright.rup.dist.foreign.ui.main;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for getting batch statistic.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/11/2018
 *
 * @author Ihar Suvorau
 */
@WebServlet(value = "/statistic/*")
public class StatisticServlet extends HttpServlet {

    @Autowired
    private IUsageAuditService usageAuditService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext springContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        registerJavaTimeModule();
        beanFactory.autowireBean(this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            String date = request.getParameter("date");
            if (Objects.nonNull(name)) {
                LocalDate localDate = CommonDateUtils.parseLocalDate(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
                UsageBatchStatistic statistic = usageAuditService.getBatchStatistic(name, localDate);
                if (Objects.nonNull(statistic)) {
                    writeResponse(response,
                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(statistic));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writeResponse(response, String.format("Batch with name '%s' was no found", name));
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writeResponse(response, "Batch name parameter should be specifier");
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates and registers {@link JavaTimeModule} with date serialization pattern for {@link ObjectMapper}.
     */
    void registerJavaTimeModule() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDate.class,
            new LocalDateSerializer(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)));
        objectMapper.registerModule(timeModule);
    }

    private void writeResponse(HttpServletResponse response, String str) throws IOException {
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setContentType(MediaType.JSON_UTF_8.toString());
        // This call is needed to hide response's write() method from FindBugs and avoid XSS_SERVLET warning
        StreamUtils.copy(str, StandardCharsets.UTF_8, response.getOutputStream());
    }
}
