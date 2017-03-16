package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;

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

/**
 * Verifies {@link ScenarioRepository}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 03/15/17
 *
 * @author Ihar Suvorau
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
    private static final String ID = RupPersistUtils.generateUuid();
    private static final BigDecimal NET_TOTAL = BigDecimal.ONE.setScale(10, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal GROSS_TOTAL = BigDecimal.ONE.setScale(10, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal REPORTED_TOTAL = BigDecimal.ONE.setScale(2, BigDecimal.ROUND_HALF_UP);

    @Autowired
    private IScenarioRepository scenarioRepository;

    @Test
    public void testInsert() {
        Scenario scenario = scenarioRepository.findById(ID);
        assertNull(scenario);
        scenarioRepository.insert(buildScenario(ID, SCENARIO_NAME));
        scenario = scenarioRepository.findById(ID);
        assertNotNull(scenario);
        assertEquals(ID, scenario.getId());
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
        scenarioRepository.insert(buildScenario(ID, SCENARIO_NAME));
        assertEquals(1, scenarioRepository.getCountByName(SCENARIO_NAME));
    }

    @Test
    public void testGetScenarios() {
        assertEquals(1, scenarioRepository.getScenarios().size());
        scenarioRepository.insert(buildScenario(RupPersistUtils.generateUuid(), SCENARIO_NAME));
        assertEquals(2, scenarioRepository.getScenarios().size());
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
