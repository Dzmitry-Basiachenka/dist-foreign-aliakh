package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link ScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
public class ScenarioControllerTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String SCENARO_NAME = "name";

    private IUsageService usageService;
    private ScenarioController controller;

    @Before
    public void setUp() {
        controller = new ScenarioController();
        controller.setScenario(buildScenario());
        controller.initWidget();
        usageService = createMock(UsageService.class);
        Whitebox.setInternalState(controller, "usageService", usageService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getRightsholderTotalsHoldersByScenarioId(eq(SCENARIO_ID), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        replay(usageService);
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(usageService);
    }

    @Test
    public void testGetSize() {
        expect(usageService.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, null)).andReturn(1).once();
        replay(usageService);
        assertEquals(1, controller.getSize());
        verify(usageService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testApplySearch() {
        ScenarioWidget widget = createMock(ScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget);
        controller.performSearch();
        verify(widget);
    }

    @Test
    public void testGetScenario() {
        assertEquals(buildScenario(), controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        Scenario scenario = new Scenario();
        controller.setScenario(scenario);
        assertEquals(scenario, controller.getScenario());
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARO_NAME);
        return scenario;
    }
}
