package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.report.BatchStatistic;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.core.StringStartsWith;
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
import java.util.List;

/**
 * Verifies {@link StatisticBatchRest}.
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
public class StatisticBatchRestTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(RupDateUtils.DATE_FORMAT_PATTERN);
    private static final String STATISTIC_PATH = "/statistic/batch";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String TEST_BATCH_NAME = "testBatchName";
    private static final String TEST_DATE = "2019-02-25";
    private static final String TEST_EXCEPTION = "Test exception";
    private static final String JSON_PATH_ERROR = "$.error";
    private static final String JSON_PATH_MESSAGE = "$.message";
    private static final String JSON_PATH_STACKTRACE = "$.stackTrace";
    private static final BigDecimal AMOUNT_ZERO = new BigDecimal("0.00");

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StatisticBatchRest statisticBatchRest;

    @Autowired
    @Qualifier("dm.service.usageAuditServiceMock")
    private IUsageAuditService usageAuditServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Whitebox.setInternalState(statisticBatchRest, "usageAuditService", usageAuditServiceMock);
        reset(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchesStatistic() throws Exception {
        expect(usageAuditServiceMock.getBatchesStatisticByBatchNameAndDate(TEST_BATCH_NAME, buildDate()))
            .andReturn(buildStatistics()).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(loadJson()))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .doesNotExist());
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatisticWrongDate() throws Exception {
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, "wrongDate")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("java.time.format.DateTimeParseException: Text 'wrongDate' could not be parsed at index 0"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .doesNotExist());
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchStatisticWrongBatchName() throws Exception {
        expect(usageAuditServiceMock.getBatchesStatisticByBatchNameAndDate(TEST_BATCH_NAME, buildDate()))
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
                .value("Batch not found. BatchName=testBatchName"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .doesNotExist());
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchesStatisticIllegalArgumentException() throws Exception {
        expect(usageAuditServiceMock.getBatchesStatisticByBatchNameAndDate(TEST_BATCH_NAME, buildDate()))
            .andThrow(new IllegalArgumentException(TEST_EXCEPTION)).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value(TEST_EXCEPTION))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .doesNotExist());
        verify(usageAuditServiceMock);
    }

    @Test
    public void testGetBatchesStatisticInternalServerError() throws Exception {
        expect(usageAuditServiceMock.getBatchesStatisticByBatchNameAndDate(TEST_BATCH_NAME, buildDate()))
            .andThrow(new RuntimeException(TEST_EXCEPTION)).once();
        replay(usageAuditServiceMock);
        mockMvc.perform(MockMvcRequestBuilders.get(STATISTIC_PATH)
            .param(NAME, TEST_BATCH_NAME)
            .param(DATE, TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value(TEST_EXCEPTION))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .value(StringStartsWith.startsWith("java.lang.RuntimeException: Test exception")));
        verify(usageAuditServiceMock);
    }

    private LocalDate buildDate() {
        return LocalDate.parse(TEST_DATE, FORMATTER);
    }

    private List<BatchStatistic> buildStatistics() {
        BatchStatistic statistic = new BatchStatistic();
        statistic.setBatchName(TEST_BATCH_NAME);
        statistic.setTotalCount(2);
        statistic.setTotalAmount(new BigDecimal("1000.00"));
        statistic.setMatchedCount(1);
        statistic.setMatchedAmount(new BigDecimal("500.00"));
        statistic.setMatchedPercent(new BigDecimal("50.00"));
        statistic.setWorksNotFoundCount(0);
        statistic.setWorksNotFoundAmount(AMOUNT_ZERO);
        statistic.setWorksNotFoundPercent(AMOUNT_ZERO);
        statistic.setMultipleMatchingCount(0);
        statistic.setMultipleMatchingAmount(AMOUNT_ZERO);
        statistic.setMultipleMatchingPercent(AMOUNT_ZERO);
        statistic.setNtsWithdrawnCount(0);
        statistic.setNtsWithdrawnAmount(AMOUNT_ZERO);
        statistic.setNtsWithdrawnPercent(AMOUNT_ZERO);
        statistic.setRhNotFoundCount(0);
        statistic.setRhNotFoundAmount(AMOUNT_ZERO);
        statistic.setRhNotFoundPercent(AMOUNT_ZERO);
        statistic.setRhFoundCount(0);
        statistic.setRhFoundAmount(AMOUNT_ZERO);
        statistic.setRhFoundPercent(AMOUNT_ZERO);
        statistic.setEligibleCount(1);
        statistic.setEligibleAmount(new BigDecimal("500.00"));
        statistic.setEligiblePercent(new BigDecimal("50.00"));
        statistic.setSendForRaCount(0);
        statistic.setSendForRaAmount(AMOUNT_ZERO);
        statistic.setSendForRaPercent(AMOUNT_ZERO);
        statistic.setPaidCount(0);
        statistic.setPaidAmount(AMOUNT_ZERO);
        statistic.setPaidPercent(AMOUNT_ZERO);
        return List.of(statistic);
    }

    private String loadJson() {
        return TestUtils.fileToString(this.getClass(), "batch_statistic.json")
            .replaceAll("\n", StringUtils.EMPTY)
            .replaceAll("\r", StringUtils.EMPTY)
            .replaceAll(" ", StringUtils.EMPTY);
    }
}
