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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Verifies {@link ScenarioRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
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
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
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
    public void testGetScenarios() {
        assertEquals(1, scenarioRepository.getScenarios().size());
        scenarioRepository.insert(buildScenario(RupPersistUtils.generateUuid(), SCENARIO_NAME));
        assertEquals(2, scenarioRepository.getScenarios().size());
    }

    @Test
    public void testFindScenariosNamesByUsageBatchId() {
        List<String> scenariosNames = scenarioRepository.findScenariosNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(1, scenariosNames.size());
        Scenario scenario = buildScenario(SCENARIO_ID, SCENARIO_NAME);
        scenarioRepository.insert(scenario);
        usageRepository.insertUsage(buildUsage());
        scenariosNames = scenarioRepository.findScenariosNamesByUsageBatchId(USAGE_BATCH_ID);
        assertNotNull(scenariosNames);
        assertEquals(2, scenariosNames.size());
    }

    @Test
    public void testDeleteScenario() {
        scenarioRepository.insert(buildScenario(SCENARIO_ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
        scenarioRepository.deleteScenario(SCENARIO_ID);
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
}
