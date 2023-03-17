package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link ScenarioAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class ScenarioAuditServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String SCENARIO_UID = "2f9259de-b274-11e7-abc4-cec278b6b50a";
    private static final String AUDIT_ITEM_UID = "fb8184a1-cacc-456c-96bf-0cd1ef36cb18";
    private static final ScenarioActionTypeEnum SCENARIO_ACTION_TYPE = ScenarioActionTypeEnum.SUBMITTED;
    private static final String SCENARIO_ACTION_REASON = "Submitted";

    private IScenarioAuditService scenarioAuditService;
    private IScenarioAuditRepository scenarioAuditRepository;

    @Before
    public void setUp() {
        scenarioAuditService = new ScenarioAuditService();
        scenarioAuditRepository = createMock(IScenarioAuditRepository.class);
        Whitebox.setInternalState(scenarioAuditService, scenarioAuditRepository);
    }

    @Test
    public void testLogAction() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        mockStatic(RupPersistUtils.class);
        expect(RupPersistUtils.generateUuid()).andReturn(AUDIT_ITEM_UID).once();
        ScenarioAuditItem auditItem = buildScenarioAuditItem();
        scenarioAuditRepository.insert(auditItem);
        expectLastCall().once();
        replay(RupContextUtils.class, RupPersistUtils.class, scenarioAuditRepository);
        scenarioAuditService.logAction(SCENARIO_UID, SCENARIO_ACTION_TYPE, SCENARIO_ACTION_REASON);
        verify(RupContextUtils.class, RupPersistUtils.class, scenarioAuditRepository);
    }

    @Test
    public void testDeleteActions() {
        scenarioAuditRepository.deleteByScenarioId(SCENARIO_UID);
        expectLastCall().once();
        replay(scenarioAuditRepository);
        scenarioAuditRepository.deleteByScenarioId(SCENARIO_UID);
        verify(scenarioAuditRepository);
    }

    @Test
    public void testGetActions() {
        expect(scenarioAuditRepository.findByScenarioId(SCENARIO_UID))
            .andReturn(List.of(new ScenarioAuditItem(), new ScenarioAuditItem()))
            .once();
        replay(scenarioAuditRepository);
        List<ScenarioAuditItem> auditItems = scenarioAuditService.getActions(SCENARIO_UID);
        assertTrue(CollectionUtils.isNotEmpty(auditItems));
        assertEquals(2, CollectionUtils.size(auditItems));
        verify(scenarioAuditRepository);
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setId(AUDIT_ITEM_UID);
        auditItem.setScenarioId(SCENARIO_UID);
        auditItem.setActionType(SCENARIO_ACTION_TYPE);
        auditItem.setActionReason(SCENARIO_ACTION_REASON);
        auditItem.setCreateUser(USER_NAME);
        auditItem.setUpdateUser(USER_NAME);
        return auditItem;
    }
}
