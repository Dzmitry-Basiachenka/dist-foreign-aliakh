package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

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
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.common.reporting.api.IStreamSourceHandler;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.ScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.vaadin.widget.api.IWidget;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AaclScenarioController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/01/20
 *
 * @author Stanislau Rudak
 */
public class AaclScenarioControllerTest {

    private static final String SCENARIO_ID = "428b6247-cdd4-46a4-96df-1d467bf4f64b";

    private AaclScenarioController controller;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private IStreamSourceHandler streamSourceHandler;
    private Scenario scenario;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setReportedTotal(new BigDecimal("30000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new AaclScenarioController();
        controller.setScenario(scenario);
        usageService = createMock(UsageService.class);
        scenarioService = createMock(ScenarioService.class);
        streamSourceHandler = createMock(IStreamSourceHandler.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, streamSourceHandler);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        replay(usageService, scenarioService, streamSourceHandler);
        controller.initWidget();
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(usageService, scenarioService, streamSourceHandler);
    }

    @Test
    public void testGetSize() {
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        expect(usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY)).andReturn(1)
            .once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(usageService, scenarioService, streamSourceHandler);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        verify(usageService, scenarioService, streamSourceHandler);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testApplySearch() {
        IFasScenarioWidget widget = createMock(IFasScenarioWidget.class);
        Whitebox.setInternalState(controller, IWidget.class, widget);
        widget.applySearch();
        expectLastCall().once();
        replay(widget, scenarioService);
        controller.performSearch();
        verify(widget, scenarioService);
    }

    @Test
    public void testGetScenario() {
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        Scenario newScenario = new Scenario();
        controller.setScenario(newScenario);
        assertSame(newScenario, controller.getScenario());
    }
}
