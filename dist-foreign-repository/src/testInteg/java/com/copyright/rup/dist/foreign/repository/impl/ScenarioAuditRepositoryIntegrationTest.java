package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Integration test for {@link ScenarioAuditRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
//TODO: split test data into separate files for each test method
@TestData(fileName = "scenario-audit-repository-test-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class ScenarioAuditRepositoryIntegrationTest {

    private static final String SCENARIO_UID = "3210b236-1239-4a60-9fab-888b84199321";

    @Autowired
    private IScenarioAuditRepository scenarioAuditRepository;

    @Test
    public void testInsertAndFind() {
        assertEquals(0, CollectionUtils.size(scenarioAuditRepository.findByScenarioId(SCENARIO_UID)));
        ScenarioAuditItem scenarioAuditItem = buildScenarioAuditItem(SCENARIO_UID);
        scenarioAuditRepository.insert(scenarioAuditItem);
        scenarioAuditRepository.insert(buildScenarioAuditItem("8a06905f-37ae-4e1f-8550-245277f8165c"));
        List<ScenarioAuditItem> scenarioAuditItems = scenarioAuditRepository.findByScenarioId(SCENARIO_UID);
        assertTrue(CollectionUtils.isNotEmpty(scenarioAuditItems));
        assertEquals(1, CollectionUtils.size(scenarioAuditItems));
        assertEquals(scenarioAuditItem, scenarioAuditItems.get(0));
    }

    @Test
    public void testDeleteByBatchId() {
        scenarioAuditRepository.insert(buildScenarioAuditItem(SCENARIO_UID));
        assertEquals(1, CollectionUtils.size(scenarioAuditRepository.findByScenarioId(SCENARIO_UID)));
        scenarioAuditRepository.deleteByScenarioId(SCENARIO_UID);
        assertTrue(CollectionUtils.isEmpty(scenarioAuditRepository.findByScenarioId(SCENARIO_UID)));
    }

    @Test
    public void testIsAuditItemExist() {
        assertFalse(scenarioAuditRepository.isAuditItemExist(SCENARIO_UID, ScenarioActionTypeEnum.SUBMITTED));
        scenarioAuditRepository.insert(buildScenarioAuditItem(SCENARIO_UID));
        assertTrue(scenarioAuditRepository.isAuditItemExist(SCENARIO_UID, ScenarioActionTypeEnum.SUBMITTED));
    }

    private ScenarioAuditItem buildScenarioAuditItem(String scenarioId) {
        ScenarioAuditItem scenarioAuditItem = new ScenarioAuditItem();
        scenarioAuditItem.setId(RupPersistUtils.generateUuid());
        scenarioAuditItem.setScenarioId(scenarioId);
        scenarioAuditItem.setActionType(ScenarioActionTypeEnum.SUBMITTED);
        scenarioAuditItem.setActionReason("Scenario submitted");
        return scenarioAuditItem;
    }
}
