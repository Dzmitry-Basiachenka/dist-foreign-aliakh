package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IScenarioAuditRepository;
import com.copyright.rup.dist.foreign.repository.impl.ScenarioAuditRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link UsageAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class ScenarioAuditServiceTest {

    private static final String SCENARIO_UID = "2f9259de-b274-11e7-abc4-cec278b6b50a";

    private IScenarioAuditService scenarioAuditService;
    private IScenarioAuditRepository scenarioAuditRepository;

    @Before
    public void setUp() {
        scenarioAuditService = new ScenarioAuditService();
        scenarioAuditRepository = createMock(ScenarioAuditRepository.class);
        Whitebox.setInternalState(scenarioAuditService, "scenarioAuditRepository", scenarioAuditRepository);
    }

    @Test
    public void testLogAction() {
        Capture<ScenarioAuditItem> scenarioAuditItemCapture = new Capture<>();
        scenarioAuditRepository.insert(capture(scenarioAuditItemCapture));
        expectLastCall().once();
        replay(scenarioAuditRepository);
        scenarioAuditService.logAction(SCENARIO_UID, ScenarioActionTypeEnum.SUBMITTED, "Submitted");
        ScenarioAuditItem scenarioAuditItem = scenarioAuditItemCapture.getValue();
        assertEquals("Submitted", scenarioAuditItem.getActionReason());
        assertEquals(SCENARIO_UID, scenarioAuditItem.getScenarioId());
        assertEquals(ScenarioActionTypeEnum.SUBMITTED, scenarioAuditItem.getActionType());
        verify(scenarioAuditRepository);
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
            .andReturn(Lists.newArrayList(new ScenarioAuditItem(), new ScenarioAuditItem()))
            .once();
        replay(scenarioAuditRepository);
        List<ScenarioAuditItem> auditItems = scenarioAuditService.getActions(SCENARIO_UID);
        assertTrue(CollectionUtils.isNotEmpty(auditItems));
        assertEquals(2, CollectionUtils.size(auditItems));
        verify(scenarioAuditRepository);
    }
}
