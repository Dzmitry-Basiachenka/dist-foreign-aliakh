package com.copyright.rup.dist.foreign.ui.main;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Verifies {@link StatisticServlet}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/11/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StreamUtils.class})
public class StatisticServletTest {

    private static final String BATCH_NAME = "batch name";
    private static final String DATE_PARAMETER = "date";
    private static final String NAME_PARAMETER = "name";
    private static final String DATE = "12/10/2018";
    private IUsageAuditService usageAuditService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StatisticServlet servlet;

    @Before
    public void setUp() {
        servlet = new StatisticServlet();
        usageAuditService = createMock(IUsageAuditService.class);
        Whitebox.setInternalState(servlet, usageAuditService);
        request = createMock(HttpServletRequest.class);
        response = createMock(HttpServletResponse.class);
    }

    @Test
    public void testGetStatistic() throws IOException {
        expect(request.getParameter(NAME_PARAMETER)).andReturn(BATCH_NAME).once();
        expect(request.getParameter(DATE_PARAMETER)).andReturn(DATE).once();
        expect(usageAuditService.getBatchStatistic(BATCH_NAME, LocalDate.of(2018, 12, 10)))
            .andReturn(buildStatistic()).once();
        expectResponse(StringUtils.strip(TestUtils.fileToString(this.getClass(), "statistic.json")));
        replayAll();
        servlet.doGet(request, response);
        verifyAll();
    }

    @Test
    public void testGetStatisticInvalidNameParameter() throws IOException {
        expect(request.getParameter(NAME_PARAMETER)).andReturn(BATCH_NAME).once();
        expect(request.getParameter(DATE_PARAMETER)).andReturn(null).once();
        expect(usageAuditService.getBatchStatistic(BATCH_NAME, null)).andReturn(null).once();
        invalidResponse(HttpServletResponse.SC_NOT_FOUND, "Batch with name 'batch name' was no found");
        replayAll();
        servlet.doGet(request, response);
        verifyAll();
    }

    @Test
    public void testGetStatisticEmptyNameParameter() throws IOException {
        expect(request.getParameter(NAME_PARAMETER)).andReturn(null).once();
        expect(request.getParameter(DATE_PARAMETER)).andReturn(null).once();
        invalidResponse(HttpServletResponse.SC_BAD_REQUEST, "Batch name parameter should be specifier");
        replayAll();
        servlet.doGet(request, response);
        verifyAll();
    }

    private UsageBatchStatistic buildStatistic() {
        UsageBatchStatistic statistic = new UsageBatchStatistic();
        statistic.setBatchName(BATCH_NAME);
        statistic.setLoadedCount(2);
        statistic.setLoadedAmount(new BigDecimal("10.00"));
        statistic.setPaidCount(2);
        statistic.setPaidAmount(new BigDecimal("10.00"));
        statistic.setPaidPercent(new BigDecimal("100.00"));
        return statistic;
    }

    private void expectResponse(String message) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        expectLastCall().once();
        response.setContentType("application/json; charset=utf-8");
        expectLastCall().once();
        ServletOutputStream outputStream = createNiceMock(ServletOutputStream.class);
        expect(response.getOutputStream()).andReturn(outputStream).once();
        mockStatic(StreamUtils.class);
        StreamUtils.copy(message, StandardCharsets.UTF_8, outputStream);
        expectLastCall().once();
    }

    private void invalidResponse(int httpStatus, String message) throws IOException {
        response.setStatus(httpStatus);
        expectLastCall().once();
        expectResponse(message);
    }
}
