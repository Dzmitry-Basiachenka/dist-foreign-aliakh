package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioAuditRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Integration test for {@link ScenarioAuditRepository}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclScenarioAuditRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "acl-scenario-audit-repository-integration-test/";
    private static final String SCENARIO_UID_1 = "8cd69469-cb63-46bf-9e33-2ba0e0904278";
    private static final String SCENARIO_UID_2 = "70c70f5b-074f-4936-8347-a5c19306b5ac";

    @Autowired
    private IAclScenarioAuditRepository aclScenarioAuditRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        assertEquals(0, CollectionUtils.size(aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1)));
        ScenarioAuditItem auditItem = buildScenarioAuditItem(SCENARIO_UID_1);
        aclScenarioAuditRepository.insert(auditItem);
        List<ScenarioAuditItem> auditItems = aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1);
        assertEquals(1, CollectionUtils.size(auditItems));
        assertEquals(auditItem, auditItems.get(0));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-acl-scenario-id.groovy")
    public void testFindByScenarioId() {
        assertEquals(0, CollectionUtils.size(aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1)));
        ScenarioAuditItem auditItem1 = buildScenarioAuditItem(SCENARIO_UID_1);
        aclScenarioAuditRepository.insert(auditItem1);
        ScenarioAuditItem auditItem2 = buildScenarioAuditItem(SCENARIO_UID_1);
        aclScenarioAuditRepository.insert(auditItem2);
        aclScenarioAuditRepository.insert(buildScenarioAuditItem(SCENARIO_UID_2));
        List<ScenarioAuditItem> auditItems = aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1);
        assertEquals(2, CollectionUtils.size(auditItems));
        assertEquals(auditItem2, auditItems.get(0));
        assertEquals(auditItem1, auditItems.get(1));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-acl-scenario-id.groovy")
    public void testDeleteByScenarioId() {
        aclScenarioAuditRepository.insert(buildScenarioAuditItem(SCENARIO_UID_1));
        assertEquals(1, CollectionUtils.size(aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1)));
        aclScenarioAuditRepository.deleteByScenarioId(SCENARIO_UID_1);
        assertTrue(CollectionUtils.isEmpty(aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID_1)));
    }

    private ScenarioAuditItem buildScenarioAuditItem(String scenarioId) {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setId(RupPersistUtils.generateUuid());
        auditItem.setScenarioId(scenarioId);
        auditItem.setActionType(ScenarioActionTypeEnum.SUBMITTED);
        auditItem.setActionReason("Scenario submitted");
        return auditItem;
    }
}
