package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link ScenarioHistoryController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class ScenarioHistoryControllerTest {

    private ScenarioHistoryController controller;
    private IScenarioAuditService scenarioAuditService;

    @Before
    public void setUp() {
        scenarioAuditService = createMock(IScenarioAuditService.class);
        controller = new ScenarioHistoryController();
        Whitebox.setInternalState(controller, scenarioAuditService);
    }

    @Test
    public void testGetActions() {
        String scenarioId = "61ecb141-89a9-463b-aee6-2a4581cb8f18";
        List<ScenarioAuditItem> auditItems = Collections.emptyList();
        expect(scenarioAuditService.getActions(scenarioId)).andReturn(auditItems).once();
        replay(scenarioAuditService);
        assertSame(auditItems, controller.getActions(scenarioId));
        verify(scenarioAuditService);
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof ScenarioHistoryWidget);
    }
}
