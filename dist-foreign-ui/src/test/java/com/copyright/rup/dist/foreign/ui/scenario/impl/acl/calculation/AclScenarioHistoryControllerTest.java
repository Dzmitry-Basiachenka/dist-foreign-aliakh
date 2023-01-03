package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclScenarioHistoryController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/30/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioHistoryControllerTest {

    private AclScenarioHistoryController controller;
    private IAclScenarioAuditService aclScenarioAuditService;

    @Before
    public void setUp() {
        aclScenarioAuditService = createMock(IAclScenarioAuditService.class);
        controller = new AclScenarioHistoryController();
        Whitebox.setInternalState(controller, aclScenarioAuditService);
    }

    @Test
    public void testGetActions() {
        String scenarioId = "84087646-0586-43a3-b309-6d50a3618f87";
        List<ScenarioAuditItem> auditItems = Collections.emptyList();
        expect(aclScenarioAuditService.getActions(scenarioId)).andReturn(auditItems).once();
        replay(aclScenarioAuditService);
        assertSame(auditItems, controller.getActions(scenarioId));
        verify(aclScenarioAuditService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(AclScenarioHistoryWidget.class));
    }
}
