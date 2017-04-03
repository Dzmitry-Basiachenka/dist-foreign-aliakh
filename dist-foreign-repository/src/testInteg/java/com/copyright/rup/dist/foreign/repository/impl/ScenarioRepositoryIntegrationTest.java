package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Verifies {@link ScenarioRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
 * @author Mikalai Bezmen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-sql-test-context.xml"})
@TransactionConfiguration
@Transactional
public class ScenarioRepositoryIntegrationTest {

    private static final String SCENARIO_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String USAGE_BATCH_ID = "56782dbc-2158-48d4-b026-94d3458a666a";
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final BigDecimal NET_TOTAL = BigDecimal.ONE.setScale(10, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal GROSS_TOTAL = BigDecimal.ONE.setScale(10, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal REPORTED_TOTAL = BigDecimal.ONE.setScale(2, BigDecimal.ROUND_HALF_UP);

    @Autowired
    private ScenarioRepository scenarioRepository;

    @Autowired
    private IUsageRepository usageRepository;

    @Test
    public void testInsert() {
        Scenario scenario = scenarioRepository.findById(SCENARIO_ID);
        assertNull(scenario);
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        scenario = scenarioRepository.findById(SCENARIO_ID);
        assertNotNull(scenario);
        assertEquals(SCENARIO_ID, scenario.getId());
        assertEquals(SCENARIO_NAME, scenario.getName());
        assertEquals(NET_TOTAL, scenario.getNetTotal());
        assertEquals(GROSS_TOTAL, scenario.getGrossTotal());
        assertEquals(REPORTED_TOTAL, scenario.getReportedTotal());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(DESCRIPTION, scenario.getDescription());
        assertEquals("SYSTEM", scenario.getCreateUser());
        assertEquals("SYSTEM", scenario.getUpdateUser());
        assertEquals(1, scenario.getVersion());
        assertNotNull(scenario.getCreateDate());
        assertNotNull(scenario.getUpdateDate());
    }

    @Test
    public void testGetCountByName() {
        assertEquals(0, scenarioRepository.getCountByName(SCENARIO_NAME));
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
    }

    @Test
    public void testFindAll() {
        assertEquals(2, scenarioRepository.findAll().size());
        String scenarioId = RupPersistUtils.generateUuid();
        scenarioRepository.insert(buildScenario(scenarioId, SCENARIO_NAME));
        List<Scenario> scenarios = scenarioRepository.findAll();
        assertEquals(3, scenarios.size());
        verifyScenario(scenarios.get(0), scenarioId, SCENARIO_NAME, DESCRIPTION, GROSS_TOTAL, NET_TOTAL,
            REPORTED_TOTAL);
        verifyScenario(scenarios.get(1), "e27551ed-3f69-4e08-9e4f-8ac03f67595f", "Scenario name 2",
            "The description of scenario 2", new BigDecimal("10000.0000000000"), new BigDecimal("16000.0000000000"),
            new BigDecimal("8000.00"));
        verifyScenario(scenarios.get(2), "b1f0b236-3ae9-4a60-9fab-61db84199d6f", "Scenario name",
            "The description of scenario", new BigDecimal("24000.0000000000"), new BigDecimal("15000.0000000000"),
            new BigDecimal("18000.00"));
    }

    @Test
    public void testFindNamesByUsageBatchId() {
        List<String> scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        Scenario scenario = buildScenario(SCENARIO_ID, SCENARIO_NAME);
        scenarioRepository.insert(scenario);
        usageRepository.insert(buildUsage());
        scenariosNames = scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(2, scenariosNames.size());
    }

    @Test
    public void testRemove() {
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
        scenarioRepository.remove(SCENARIO_ID);
        assertEquals(0, scenarioRepository.getCountByName(SCENARIO_NAME));
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setBatchId(USAGE_BATCH_ID);
        usage.setScenarioId(SCENARIO_ID);
        usage.setDetailId(12345L);
        usage.setWrWrkInst(123456783L);
        usage.setWorkTitle("WorkTitle");
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(7000813806L);
        rightsholder.setName("CADRA, Centro de Administracion de Derechos Reprograficos, Asociacion Civil");
        usage.setRightsholder(rightsholder);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setArticle("Article");
        usage.setStandardNumber("StandardNumber");
        usage.setPublisher("Publisher");
        usage.setPublicationDate(LocalDate.of(2016, 11, 3));
        usage.setMarket("Market");
        usage.setMarketPeriodFrom(2015);
        usage.setMarketPeriodTo(2017);
        usage.setAuthor("Author");
        usage.setNumberOfCopies(1);
        usage.setReportedValue(new BigDecimal("11.25"));
        usage.setGrossAmount(new BigDecimal("54.4400000000"));
        return usage;
    }

    private Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        scenario.setNetTotal(NET_TOTAL);
        scenario.setGrossTotal(GROSS_TOTAL);
        scenario.setReportedTotal(REPORTED_TOTAL);
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        scenario.setDescription(DESCRIPTION);
        return scenario;
    }

    private void verifyScenario(Scenario scenario, String id, String name, String description, BigDecimal grossAmount,
                                BigDecimal netAmount, BigDecimal reportedAmount) {
        assertEquals(id, scenario.getId());
        assertEquals(name, scenario.getName());
        assertEquals(description, scenario.getDescription());
        assertEquals("SYSTEM", scenario.getCreateUser());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals(grossAmount, scenario.getGrossTotal());
        assertEquals(netAmount, scenario.getNetTotal());
        assertEquals(reportedAmount, scenario.getReportedTotal());
    }
}
