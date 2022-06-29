package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link AclScenarioController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/28/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioControllerTest {

    private AclScenarioController controller;
    private AclScenario scenario;

    @Before
    public void setUp() {
        scenario = buildAclScenario();
        controller = new AclScenarioController();
        controller.setScenario(scenario);
    }

    @Test
    public void testGetScenario() {
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        controller.setScenario(scenario);
        assertSame(scenario, controller.getScenario());
    }

    @Test
    public void testPerformSearch() {
        IAclScenarioWidget widget = createMock(IAclScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget);
        controller.performSearch();
        verify(widget);
    }

    @Test
    public void testGetAclScenarioWithAmountsAndLastAction() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testLoadBeans() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testGetSize() {
        //TODO {dbasiachenka} implement
    }

    @Test
    public void testInstantiateWidget() {
        IAclScenarioWidget widget = controller.instantiateWidget();
        assertNotNull(widget);
        assertEquals(AclScenarioWidget.class, widget.getClass());
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId("2398769d-8862-42e8-9504-9cbe19376b4b");
        aclScenario.setName("Scenario name");
        return aclScenario;
    }
}
