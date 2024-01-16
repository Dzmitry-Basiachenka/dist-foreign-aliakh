package com.copyright.rup.dist.foreign.vui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

    private IScenarioAuditService scenarioAuditService;
    private ScenarioHistoryController controller;

    @Before
    public void setUp() {
        scenarioAuditService = createMock(IScenarioAuditService.class);
        controller = new ScenarioHistoryController();
        Whitebox.setInternalState(controller, scenarioAuditService);
    }

    @Test
    public void testGetActions() {
        String scenarioId = "1823baf6-9bb7-40e9-b0b1-06436a2794ba";
        List<ScenarioAuditItem> auditItems = List.of();
        expect(scenarioAuditService.getActions(scenarioId)).andReturn(auditItems).once();
        replay(scenarioAuditService);
        assertSame(auditItems, controller.getActions(scenarioId));
        verify(scenarioAuditService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(ScenarioHistoryWidget.class));
    }
}
