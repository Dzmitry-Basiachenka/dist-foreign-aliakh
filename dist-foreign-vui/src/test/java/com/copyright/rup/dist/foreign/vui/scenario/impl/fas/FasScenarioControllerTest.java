package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IWidget;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link FasScenarioController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 04/07/17
 *
 * @author Ihar Suvorau
 */
public class FasScenarioControllerTest {

    private static final String SCENARIO_ID = "1ab5079f-2b90-4d8d-85d5-fa7ef6df0ed6";

    private FasScenarioController controller;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private IFasScenarioService fasScenarioService;
    private Scenario scenario;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new FasScenarioController();
        controller.setScenario(buildScenario());
        usageService = createMock(UsageService.class);
        scenarioService = createMock(IScenarioService.class);
        fasScenarioService = createMock(IFasScenarioService.class);
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenarioService);
        Whitebox.setInternalState(controller, fasScenarioService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = newCapture();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), anyString(),
            capture(pageableCapture), isNull())).andReturn(List.of()).once();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        replay(usageService, scenarioService);
        controller.initWidget();
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(usageService, scenarioService);
    }

    @Test
    public void testGetSize() {
        expect(usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY)).andReturn(1)
            .once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        replay(usageService, scenarioService);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        verify(usageService, scenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(controller.instantiateWidget(), instanceOf(FasScenarioWidget.class));
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
        assertEquals(buildScenario(), controller.getScenario());
    }

    @Test
    public void testSetScenario() {
        controller.setScenario(scenario);
        assertSame(scenario, controller.getScenario());
    }

    @Test
    public void testIsScenarioEmpty() {
        controller.setScenario(scenario);
        expect(usageService.isScenarioEmpty(scenario)).andReturn(true).once();
        replay(usageService);
        assertTrue(controller.isScenarioEmpty());
        verify(usageService);
    }

    @Test
    public void testGetSourceRros() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(fasScenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            List.of(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(fasScenarioService);
        List<Rightsholder> rros = controller.getSourceRros();
        assertEquals(1, CollectionUtils.size(rros));
        assertEquals(Long.valueOf(1000009522L), rros.get(0).getAccountNumber());
        assertEquals("Societa Italiana Autori ed Editori (SIAE)", rros.get(0).getName());
        verify(fasScenarioService);
    }

    @Test
    public void testDeleteFromScenario() {
        List<Long> accountNumbers = List.of(1000009522L);
        usageService.deleteFromScenario(SCENARIO_ID, 2000017010L, accountNumbers, "reason");
        expectLastCall().once();
        replay(usageService);
        controller.deleteFromScenario(2000017010L, accountNumbers, "reason");
        verify(usageService);
    }

    @Test
    public void testGetRightsholdersPayeePairs() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(fasScenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 1000009522L))
            .andReturn(List.of(buildRightsholderPayeePair())).once();
        replay(fasScenarioService);
        List<RightsholderPayeePair> pairs = controller.getRightsholdersPayeePairs(1000009522L);
        assertEquals(1, CollectionUtils.size(pairs));
        RightsholderPayeePair pair = pairs.get(0);
        assertEquals(Long.valueOf(2000017001L), pair.getPayee().getAccountNumber());
        assertEquals("ICLA, Irish Copyright Licensing Agency", pair.getPayee().getName());
        assertEquals(Long.valueOf(2000017006L), pair.getRightsholder().getAccountNumber());
        assertEquals("CAL, Copyright Agency Limited", pair.getRightsholder().getName());
        verify(fasScenarioService);
    }

    private Scenario buildScenario() {
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
        return scenario;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private RightsholderPayeePair buildRightsholderPayeePair() {
        var pair = new RightsholderPayeePair();
        pair.setPayee(buildRightsholder(2000017001L, "ICLA, Irish Copyright Licensing Agency"));
        pair.setRightsholder(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited"));
        return pair;
    }
}
