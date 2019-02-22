package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies Send To LM functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/11/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=send-scenario-to-lm-data-init.groovy"})
public class SendScenarioToLmTest {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private SqsClientMock sqsClientMock;

    private static final String SCENARIO_ID = "4c014547-06f3-4840-94ff-6249730d537d";

    @Before
    public void setUp() {
        sqsClientMock.reset();
    }

    @Test
    public void testSendToLm() throws IOException {
        assertTrue(CollectionUtils.isEmpty(findUsageDtos()));
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
            Collections.singletonList(TestUtils.fileToString(this.getClass(), "details/details_to_lm.json")),
            Collections.EMPTY_LIST, ImmutableMap.of("source", "FDA"));
        List<UsageDto> usageDtos = findUsageDtos();
        List<UsageDto> expectedUsageDtos = loadExpectedUsageDtos("usage/archived_usage_dtos.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), usageDtos.get(index)));
    }

    private List<UsageDto> findUsageDtos() {
        return sqlSessionTemplate.selectList("IUsageArchiveMapper.findDtoByScenarioId", SCENARIO_ID);
    }

    private void assertUsageDto(UsageDto expected, UsageDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getFiscalYear(), actual.getFiscalYear());
        assertEquals(expected.getRroName(), actual.getRroName());
        assertEquals(expected.getRroAccountNumber(), actual.getRroAccountNumber());
        assertEquals(expected.getPayeeName(), actual.getPayeeName());
        assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
        assertEquals(expected.getSystemTitle(), actual.getSystemTitle());
        assertEquals(expected.getWorkTitle(), actual.getWorkTitle());
        assertEquals(expected.getArticle(), actual.getArticle());
        assertEquals(expected.getRhAccountNumber(), actual.getRhAccountNumber());
        assertEquals(expected.getRhName(), actual.getRhName());
        assertEquals(expected.getStandardNumber(), actual.getStandardNumber());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getNumberOfCopies(), actual.getNumberOfCopies());
        assertEquals(expected.getMarket(), actual.getMarket());
        assertEquals(expected.getArticle(), actual.getArticle());
        assertEquals(expected.getPayeeAccountNumber(), actual.getPayeeAccountNumber());
        assertEquals(expected.getPayeeName(), actual.getPayeeName());
        assertEquals(expected.getGrossAmount(), actual.getGrossAmount());
        assertEquals(expected.getReportedValue(), actual.getReportedValue());
        assertEquals(expected.getNetAmount(), actual.getNetAmount());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getServiceFee(), actual.getServiceFee());
        assertEquals(expected.getComment(), actual.getComment());
    }

    private List<UsageDto> loadExpectedUsageDtos(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }
}
