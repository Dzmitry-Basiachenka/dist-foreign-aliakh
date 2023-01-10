package com.copyright.rup.dist.foreign.ui.scenario.impl.nts;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.vaadin.ui.Window;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link NtsScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/19
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class NtsScenariosControllerTest {

    private static final String SCENARIO_ID = "2c3e468a-b04f-458a-b6c1-9301e93dfa68";
    private static final String SCENARIO_NAME = "Scenario name";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private NtsScenariosController scenariosController;
    private IScenarioService scenarioService;
    private INtsScenarioService ntsScenarioService;
    private NtsScenarioController scenarioController;
    private Scenario scenario;
    private INtsScenariosWidget scenariosWidget;
    private INtsScenarioWidget scenarioWidget;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        ntsScenarioService = createMock(INtsScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        scenariosController = new NtsScenariosController();
        buildScenario();
        scenarioController = createMock(NtsScenarioController.class);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        scenariosWidget = createMock(INtsScenariosWidget.class);
        scenarioWidget = new NtsScenarioWidget(scenarioController);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "ntsScenarioService", ntsScenarioService);
        Whitebox.setInternalState(scenariosController, "productFamilyProvider", productFamilyProvider);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetScenarios() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(NTS_PRODUCT_FAMILY)).andReturn(Collections.emptyList()).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(Collections.emptyList(), scenariosController.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testOnDeleteButtonClicked() {
        mockStatic(Windows.class);
        Capture<ConfirmDialogWindow.IListener> listenerCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + SCENARIO_NAME + "'</b></i> scenario?"),
            capture(listenerCapture))).andReturn(null).once();
        ntsScenarioService.deleteScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, ntsScenarioService, Windows.class);
        scenariosController.onDeleteButtonClicked();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, ntsScenarioService, Windows.class);
    }

    @Test
    public void testOnViewButtonClicked() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.initWidget()).andReturn(scenarioWidget).once();
        Windows.showModalWindow((Window) scenarioWidget);
        expectLastCall().once();
        replay(scenariosWidget, scenarioController, Windows.class);
        scenariosController.onViewButtonClicked();
        verify(scenariosWidget, scenarioController, Windows.class);
    }

    @Test
    public void testSendToLm() {
        mockStatic(Windows.class);
        scenario.setName("Scenario");
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure that you want to send scenario <i><b>Scenario</b></i> to Liability Manager?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(new Window()).once();
        replay(Windows.class, scenariosWidget);
        scenariosController.sendToLm();
        verify(Windows.class, scenariosWidget);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setFiscalYear(2018);
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(NTS_PRODUCT_FAMILY);
        scenarioUsageFilter.setRhAccountNumbers(Sets.newHashSet(1000000001L, 1000000002L));
        scenarioUsageFilter.setPaymentDate(LocalDate.of(2010, 1, 1));
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(scenariosController, rightsholderService);
        expect(rightsholderService.updateAndGetRightsholders(Sets.newHashSet(1000000001L, 1000000002L))).andReturn(
            ImmutableMap.of(1000000001L, buildRightsholder(1000000001L, "Rothchild Consultants"), 1000000002L,
                buildRightsholder(1000000002L, "Royal Society of Victoria"))).once();
        replay(scenariosWidget, scenarioUsageFilterService, rightsholderService);
        assertEquals("<b>Selection Criteria:</b>" +
            "<ul><li><b><i>Product Family </i></b>(NTS)</li>" +
            "<li><b><i>Batch in </i></b>(BatchName)</li>" +
            "<li><b><i>RRO in </i></b>(1000000001: Rothchild Consultants, 1000000002: Royal Society of Victoria)</li>" +
            "<li><b><i>Payment Date To </i></b>(01/01/2010)</li>" +
            "<li><b><i>Status </i></b>(ELIGIBLE)</li>" +
            "<li><b><i>Fiscal Year To </i></b>(2018)</li>" +
            "</ul>", scenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, scenarioUsageFilterService, rightsholderService);
    }

    @Test
    public void testSendScenarioToLm() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        Capture<ConfirmDialogWindow.IListener> listenerCapture = newCapture();
        expect(Windows.showConfirmDialog(
            eq("Are you sure that you want to send scenario <i><b>" + SCENARIO_NAME + "</b></i> to Liability Manager?"),
            capture(listenerCapture))).andReturn(null).once();
        ntsScenarioService.sendToLm(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, ntsScenarioService, Windows.class);
        scenariosController.sendToLm();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, ntsScenarioService, Windows.class);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(NTS_PRODUCT_FAMILY);
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
