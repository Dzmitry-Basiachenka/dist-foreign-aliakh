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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
        usageService = createMock(UsageService.class);
        Whitebox.setInternalState(controller, "usageService", usageService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getRightsholderTotalsHoldersByScenarioId(eq(SCENARIO_ID), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(usageService.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, null)).andReturn(1).once();
        replay(usageService);
        controller.initWidget();
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
        expect(usageService.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, null)).andReturn(1).times(2);
        replay(usageService);
        controller.initWidget();
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

    @Test
    public void testGetScenarioUsagesExportStream() {
        expect(usageService.getRightsholderTotalsHolderCountByScenarioId(SCENARIO_ID, null)).andReturn(1).once();
        IStreamSource exportScenarioUsagesStreamSource = controller.getExportScenarioUsagesStreamSource();
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(exportScenarioUsagesStreamSource, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        replay(usageService, executorService);
        controller.initWidget();
        assertNotNull(exportScenarioUsagesStreamSource.getStream());
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        assertSame(exportScenarioUsagesStreamSource, Whitebox.getInternalState(runnable, "arg$1"));
        assertTrue(Whitebox.getInternalState(runnable, "arg$2") instanceof PipedOutputStream);
        verify(usageService, executorService);
    }

    @Test
    public void testGetScenarioUsagesExportFileName() {
        assertEquals("name.csv", controller.getExportScenarioUsagesStreamSource().getFileName());
    }

    @Test
    public void testGetSourceRros() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getSourceRros(SCENARIO_ID))
            .andReturn(Lists.newArrayList(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(scenarioService);
        List<Rightsholder> rros = controller.getSourceRros();
        assertEquals(1, CollectionUtils.size(rros));
        assertEquals(Long.valueOf(1000009522L), rros.get(0).getAccountNumber());
        assertEquals("Societa Italiana Autori ed Editori (SIAE)", rros.get(0).getName());
        verify(scenarioService);
    }

    @Test
    public void testDeleteFromScenario() {
        List<Long> accountNumbers = Lists.newArrayList(1000009522L);
        usageService.deleteFromScenario(buildScenario(), accountNumbers, "reason");
        expectLastCall().once();
        replay(usageService);
        controller.deleteFromScenario(accountNumbers, "reason");
        verify(usageService);
    }

    @Test
    public void testGetRightsholdersPayeePairs() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 1000009522L))
            .andReturn(Lists.newArrayList(buildRightsholderPayeePair())).once();
        replay(scenarioService);
        List<RightsholderPayeePair> pairs = controller.getRightsholdersPayeePairs(1000009522L);
        assertEquals(1, CollectionUtils.size(pairs));
        RightsholderPayeePair pair = pairs.get(0);
        assertEquals(Long.valueOf(2000017001L), pair.getPayee().getAccountNumber());
        assertEquals("ICLA, Irish Copyright Licensing Agency", pair.getPayee().getName());
        assertEquals(Long.valueOf(2000017006L), pair.getRightsholder().getAccountNumber());
        assertEquals("CAL, Copyright Agency Limited", pair.getRightsholder().getName());
        verify(scenarioService);
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARO_NAME);
        return scenario;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private RightsholderPayeePair buildRightsholderPayeePair() {
        RightsholderPayeePair pair = new RightsholderPayeePair();
        pair.setPayee(buildRightsholder(2000017001L, "ICLA, Irish Copyright Licensing Agency"));
        pair.setRightsholder(buildRightsholder(2000017006L, "CAL, Copyright Agency Limited"));
        return pair;
    }
}
