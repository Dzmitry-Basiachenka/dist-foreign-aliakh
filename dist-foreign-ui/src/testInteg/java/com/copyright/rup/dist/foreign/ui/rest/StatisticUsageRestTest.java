package com.copyright.rup.dist.foreign.ui.rest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.report.UsageStatistic;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

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

import java.util.List;

/**
 * Verifies {@link StatisticUsageRest}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/06/2019
 *
 * @author Aliaksanr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/ui/dist-foreign-ui-rest-test-context.xml")
@WebAppConfiguration
public class StatisticUsageRestTest {

    private static final String PATH = "/statistic/usage/";
    private static final String TEST_USAGE_ID = "testUsageId";
    private static final String JSON_PATH_ERROR = "$.error";
    private static final String JSON_PATH_MESSAGE = "$.message";
    private static final String JSON_PATH_STACKTRACE = "$.stackTrace";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StatisticUsageRest statisticUsageRest;

    @Autowired
    @Qualifier("dm.repository.usageAuditRepositoryMock")
    private IUsageAuditRepository usageAuditRepository;

    @Autowired
    @Qualifier("dm.repository.usageRepositoryMock")
    private IUsageRepository usageRepository;

    @Autowired
    @Qualifier("dm.repository.usageArchiveRepositoryMock")
    private IUsageArchiveRepository usageArchiveRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Whitebox.setInternalState(statisticUsageRest, usageAuditRepository);
        reset(usageAuditRepository);
    }

    @Test
    public void testGetUsageStatistic() throws Exception {
        expect(usageAuditRepository.getUsageStatistic(TEST_USAGE_ID))
            .andReturn(buildUsageStatistic()).once();
        replay(usageAuditRepository);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + TEST_USAGE_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(loadJson()));
        verify(usageAuditRepository);
    }

    @Test
    public void testGetUsageStatisticWrongUsageId() throws Exception {
        expect(usageAuditRepository.getUsageStatistic(TEST_USAGE_ID)).andReturn(null).once();
        expect(usageRepository.findByIds(List.of(TEST_USAGE_ID))).andReturn(null).once();
        expect(usageArchiveRepository.findByIds(List.of(TEST_USAGE_ID))).andReturn(null).once();
        replay(usageAuditRepository);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + TEST_USAGE_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("Usage not found. UsageId=testUsageId"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .doesNotExist());
        verify(usageAuditRepository);
    }

    @Test
    public void testGetUsageStatisticInternalServerError() throws Exception {
        expect(usageAuditRepository.getUsageStatistic(TEST_USAGE_ID))
            .andThrow(new RuntimeException("Test exception")).once();
        replay(usageAuditRepository);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + TEST_USAGE_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_ERROR)
                .value("INTERNAL_SERVER_ERROR"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_MESSAGE)
                .value("Test exception"))
            .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_STACKTRACE)
                .value(StringStartsWith.startsWith("java.lang.RuntimeException: Test exception")));
        verify(usageAuditRepository);
    }

    private UsageStatistic buildUsageStatistic() {
        UsageStatistic statistic = new UsageStatistic();
        statistic.setUsageId(TEST_USAGE_ID);
        statistic.setStatus(UsageStatusEnum.LOCKED);
        statistic.setMatchingMs(1);
        statistic.setRightsMs(2);
        statistic.setEligibilityMs(3);
        return statistic;
    }

    private String loadJson() {
        return TestUtils.fileToString(this.getClass(), "usage_statistic.json")
            .replaceAll("\n", StringUtils.EMPTY)
            .replaceAll("\r", StringUtils.EMPTY)
            .replaceAll(" ", StringUtils.EMPTY);
    }
}
