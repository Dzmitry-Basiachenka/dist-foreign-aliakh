package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.mock.aws.SqsClientMock;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.AuditFilter;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;

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
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SendScenarioToLmTest {

    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private INtsScenarioService ntsScenarioService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private SqsClientMock sqsClientMock;
    @Autowired
    private IUsageService usageService;

    private static final String FAS_SCENARIO_ID = "4c014547-06f3-4840-94ff-6249730d537d";
    private static final String NTS_SCENARIO_ID = "67027e15-17c6-4b9b-b7f0-12ec414ad344";

    @Before
    public void setUp() {
        sqsClientMock.reset();
    }

    @Test
    public void testSendToLmFas() throws IOException {
        assertTrue(CollectionUtils.isEmpty(findUsageDtos(FAS_SCENARIO_ID)));
        Scenario scenario = new Scenario();
        scenario.setId(FAS_SCENARIO_ID);
        scenario.setProductFamily("FAS");
        scenarioService.sendFasToLm(scenario);
        sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
            Collections.singletonList(TestUtils.fileToString(this.getClass(), "details/details_to_lm_fas.json")),
            Collections.emptyList(), ImmutableMap.of("source", "FDA"));
        List<UsageDto> usageDtos = findUsageDtos(FAS_SCENARIO_ID);
        List<UsageDto> expectedUsageDtos = loadExpectedUsageDtos("usage/archived_usage_dtos_fas.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), usageDtos.get(index), true));
    }

    @Test
    public void testSendToLmNts() throws IOException {
        assertTrue(CollectionUtils.isEmpty(findUsageDtos(NTS_SCENARIO_ID)));
        assertNtsFundPoolUsages(UsageStatusEnum.TO_BE_DISTRIBUTED);
        Scenario scenario = new Scenario();
        scenario.setId(NTS_SCENARIO_ID);
        scenario.setProductFamily("NTS");
        ntsScenarioService.sendToLm(scenario);
        sqsClientMock.assertSendMessages("fda-test-sf-detail.fifo",
            Collections.singletonList(TestUtils.fileToString(this.getClass(), "details/details_to_lm_nts.json")),
            Collections.singletonList("detail_id"), ImmutableMap.of("source", "FDA"));
        List<UsageDto> usageDtos = findUsageDtos(NTS_SCENARIO_ID);
        List<UsageDto> expectedUsageDtos = loadExpectedUsageDtos("usage/archived_usage_dtos_nts.json");
        assertEquals(CollectionUtils.size(expectedUsageDtos), CollectionUtils.size(usageDtos));
        IntStream.range(0, usageDtos.size())
            .forEach(index -> assertUsageDto(expectedUsageDtos.get(index), usageDtos.get(index), false));
        assertNtsFundPoolUsages(UsageStatusEnum.ARCHIVED);
    }

    private void assertNtsFundPoolUsages(UsageStatusEnum status) {
        AuditFilter filter = new AuditFilter();
        filter.setBatchesIds(Collections.singleton("a3af8396-acf3-432b-9f23-7554e3d8f50d"));
        List<UsageDto> usages = usageService.getForAudit(filter, null, null);
        assertEquals(2, usages.size());
        usages.forEach(usage -> assertEquals(status, usage.getStatus()));
    }

    private List<UsageDto> findUsageDtos(String scenarioId) {
        return sqlSessionTemplate.selectList("IReportMapper.findArchivedScenarioUsageReportDtos", scenarioId);
    }

    private void assertUsageDto(UsageDto expected, UsageDto actual, boolean isFas) {
        if (isFas) {
            assertEquals(expected.getId(), actual.getId());
        }
        assertEquals(expected.getBatchName(), actual.getBatchName());
        assertEquals(expected.getFiscalYear(), actual.getFiscalYear());
        assertEquals(expected.getRroAccountNumber(), actual.getRroAccountNumber());
        assertEquals(expected.getRroName(), actual.getRroName());
        assertEquals(expected.getWrWrkInst(), actual.getWrWrkInst());
        assertEquals(expected.getSystemTitle(), actual.getSystemTitle());
        assertEquals(expected.getWorkTitle(), actual.getWorkTitle());
        assertEquals(expected.getArticle(), actual.getArticle());
        assertEquals(expected.getRhAccountNumber(), actual.getRhAccountNumber());
        assertEquals(expected.getRhName(), actual.getRhName());
        assertEquals(expected.getStandardNumber(), actual.getStandardNumber());
        assertEquals(expected.getStandardNumberType(), actual.getStandardNumberType());
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
