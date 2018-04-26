package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static junit.framework.TestCase.assertTrue;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.ScenarioService;
import com.copyright.rup.dist.foreign.service.impl.UsageService;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.component.downloader.IStreamSource;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.api.IWidget;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.PipedOutputStream;
import java.math.BigDecimal;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class, Windows.class})
public class ScenarioControllerTest {

    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();

    private IUsageService usageService;
    private ScenarioController controller;
    private ScenarioService scenarioService;
    private Scenario scenario;

    @Before
    public void setUp() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("10000.00"));
        scenario.setGrossTotal(new BigDecimal("20000.00"));
        scenario.setReportedTotal(new BigDecimal("30000.00"));
        scenario.setCreateUser("User@copyright.com");
        controller = new ScenarioController();
        controller.setScenario(buildScenario());
        usageService = createMock(UsageService.class);
        scenarioService = createMock(ScenarioService.class);
        mockStatic(ForeignSecurityUtils.class);
        Whitebox.setInternalState(controller, "usageService", usageService);
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        expect(usageService.getRightsholderTotalsHoldersByScenario(eq(scenario), anyString(),
            capture(pageableCapture), isNull())).andReturn(Collections.emptyList()).once();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(ForeignSecurityUtils.hasExcludeFromScenarioPermission()).andReturn(true).once();
        replay(usageService, scenarioService, ForeignSecurityUtils.class);
        controller.initWidget();
        List<RightsholderTotalsHolder> result = controller.loadBeans(10, 150, null);
        Pageable pageable = pageableCapture.getValue();
        assertEquals(10, pageable.getOffset());
        assertEquals(150, pageable.getLimit());
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(usageService, scenarioService, ForeignSecurityUtils.class);
    }

    @Test
    public void testGetSize() {
        expect(usageService.getRightsholderTotalsHolderCountByScenario(scenario, StringUtils.EMPTY)).andReturn(1)
            .once();
        expect(controller.getScenarioWithAmountsAndLastAction()).andReturn(scenario).once();
        expect(ForeignSecurityUtils.hasExcludeFromScenarioPermission()).andReturn(true).once();
        replay(usageService, scenarioService, ForeignSecurityUtils.class);
        controller.initWidget();
        assertEquals(1, controller.getSize());
        verify(usageService, scenarioService, ForeignSecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testApplySearch() {
        IScenarioWidget widget = createMock(IScenarioWidget.class);
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
        assertEquals(scenario, controller.getScenario());
    }

    @Test
    public void testGetScenarioUsagesExportStream() {
        IStreamSource exportScenarioUsagesStreamSource = controller.getExportScenarioUsagesStreamSource();
        ExecutorService executorService = createMock(ExecutorService.class);
        Whitebox.setInternalState(exportScenarioUsagesStreamSource, executorService);
        Capture<Runnable> captureRunnable = new Capture<>();
        executorService.execute(capture(captureRunnable));
        expectLastCall().once();
        expect(scenarioService.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(ForeignSecurityUtils.hasExcludeFromScenarioPermission()).andReturn(true).once();
        replay(usageService, executorService, scenarioService, ForeignSecurityUtils.class);
        controller.initWidget();
        assertNotNull(exportScenarioUsagesStreamSource.getStream());
        Runnable runnable = captureRunnable.getValue();
        assertNotNull(runnable);
        assertSame(exportScenarioUsagesStreamSource, Whitebox.getInternalState(runnable, "arg$1"));
        assertTrue(Whitebox.getInternalState(runnable, "arg$2") instanceof PipedOutputStream);
        verify(usageService, executorService, scenarioService, ForeignSecurityUtils.class);
    }

    @Test
    public void testGetScenarioUsagesExportFileName() {
        assertEquals("Scenario_name.csv", controller.getExportScenarioUsagesStreamSource().getFileName());
    }

    @Test
    public void testOnExcludeDetailsClickedWithDiscrepancies() {
        mockStatic(Windows.class);
        IRightsholderDiscrepancyService rightsholderDiscrepancyService =
            createMock(IRightsholderDiscrepancyService.class);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(1).once();
        Windows.showNotificationWindow("Details can not be excluded after reconciliation");
        expectLastCall().once();
        replay(rightsholderDiscrepancyService, Windows.class);
        controller.onExcludeDetailsClicked();
        verify(rightsholderDiscrepancyService, Windows.class);
    }

    @Test
    public void testOnExcludeDetailsClickedWithoutDiscrepancies() {
        mockStatic(Windows.class);
        IRightsholderDiscrepancyService rightsholderDiscrepancyService =
            createMock(IRightsholderDiscrepancyService.class);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED)).andReturn(0).once();
        Windows.showModalWindow(anyObject(ExcludeSourceRroWindow.class));
        expectLastCall().once();
        expect(scenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            Collections.singletonList(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
            .once();
        replay(rightsholderDiscrepancyService, scenarioService, Windows.class);
        controller.onExcludeDetailsClicked();
        verify(rightsholderDiscrepancyService, scenarioService, Windows.class);
    }

    @Test
    public void testGetSourceRros() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getSourceRros(SCENARIO_ID)).andReturn(
            Collections.singletonList(buildRightsholder(1000009522L, "Societa Italiana Autori ed Editori (SIAE)")))
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
        usageService.deleteFromScenario(buildScenario(), 2000017010L, accountNumbers, "reason");
        expectLastCall().once();
        replay(usageService);
        controller.deleteFromScenario(2000017010L, accountNumbers, "reason");
        verify(usageService);
    }

    @Test
    public void testGetRightsholdersPayeePairs() {
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        expect(scenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 1000009522L))
            .andReturn(Collections.singletonList(buildRightsholderPayeePair())).once();
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
        scenario.setId(SCENARIO_ID);
        scenario.setName("Scenario name");
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
