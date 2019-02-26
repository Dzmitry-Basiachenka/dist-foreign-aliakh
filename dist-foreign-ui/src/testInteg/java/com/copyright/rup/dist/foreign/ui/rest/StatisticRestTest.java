package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.report.UsageBatchStatistic;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Verifies {@link StatisticRest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/25/2019
 *
 * @author Aliaksanr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-rest-test-context.xml")
@WebAppConfiguration
public class StatisticRestTest {

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    private static final String STATISTIC_PATH = "/statistic";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String TEST_BATCH_NAME = "testBatchName";
    private static final String TEST_DATE = "02/25/2019";
    private static final String JSON_PATH_ERROR = "$.error";
    private static final String JSON_PATH_MESSAGE = "$.message";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StatisticRest statisticRest;

    @Autowired
    @Qualifier("dm.service.usageAuditServiceMock")
    private IUsageAuditService usageAuditServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Whitebox.setInternalState(statisticRest, "usageAuditService", usageAuditServiceMock);
        reset(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatistic() throws Exception {
        expect(usageAuditServiceMock.getBatchStatistic(TEST_BATCH_NAME, LocalDate.parse(TEST_DATE, FORMATTER)))
            .andReturn(buildStatistic(LocalDate.of(2019, 2, 25))).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(loadJson("statistic.json")));
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatisticMissedBatchName() throws Exception {
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("Required String parameter 'name' is not present"));
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatisticMissedDate() throws Exception {
        expect(usageAuditServiceMock.getBatchStatistic(TEST_BATCH_NAME, null))
            .andReturn(buildStatistic(null)).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(loadJson("statistic_date_null.json")));
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatisticWrongBatchName() throws Exception {
        expect(usageAuditServiceMock.getBatchStatistic(TEST_BATCH_NAME, LocalDate.parse(TEST_DATE, FORMATTER)))
            .andReturn(null).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("Batch not found. BatchName=testBatchName"));
        verify(usageAuditServiceMock);
    }

    @Test
    public void testTriggerJobInternalServerError() throws Exception {
        expect(usageAuditServiceMock.getBatchStatistic(TEST_BATCH_NAME, LocalDate.parse(TEST_DATE, FORMATTER)))
            .andThrow(new RuntimeException("Test exception")).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("Test exception"));
        verify(usageAuditServiceMock);
    }

    private UsageBatchStatistic buildStatistic(LocalDate date) {
        UsageBatchStatistic ubs = new UsageBatchStatistic();
        ubs.setBatchName(TEST_BATCH_NAME);
        ubs.setDate(date);
        ubs.setLoadedCount(2);
        ubs.setLoadedAmount(new BigDecimal("10.00"));
        ubs.setPaidCount(2);
        ubs.setPaidAmount(new BigDecimal("10.00"));
        ubs.setPaidPercent(new BigDecimal("100.00"));
        return ubs;
    }

    private String loadJson(String fileName) {
        return TestUtils.fileToString(this.getClass(), fileName)
            .replaceAll("\n", StringUtils.EMPTY)
            .replaceAll("\r", StringUtils.EMPTY)
            .replaceAll(" ", StringUtils.EMPTY);
    }
}
