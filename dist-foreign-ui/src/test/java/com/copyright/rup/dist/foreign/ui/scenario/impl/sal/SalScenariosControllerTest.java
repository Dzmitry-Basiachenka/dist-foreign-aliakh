package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.IActionHandler;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmActionDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Validator;
import com.vaadin.ui.Window;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

/**
 * Verifies {@link SalScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/22/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class})
public class SalScenariosControllerTest {

    private static final String SCENARIO_ID = "abc9deb3-8450-4eac-a13d-d157cf5fc057";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String SCENARIO_NAME = "Scenario name";

    private SalScenariosController scenariosController;
    private ISalScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IUsageService usageService;
    private IScenarioService scenarioService;
    private ISalScenarioService salScenarioService;
    private SalScenarioController scenarioController;
    private ISalScenarioWidget scenarioWidget;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        scenarioService = createMock(IScenarioService.class);
        salScenarioService = createMock(ISalScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        scenariosController = new SalScenariosController();
        buildScenario();
        scenarioController = createMock(SalScenarioController.class);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        scenariosWidget = createMock(ISalScenariosWidget.class);
        scenarioWidget = new SalScenarioWidget(scenarioController);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        mockStatic(SecurityUtils.class);
        expect(SecurityUtils.getUserName()).andReturn("user@copyright.com").anyTimes();
        replay(SecurityUtils.class);
        scenariosController.initActionHandlers();
        Whitebox.setInternalState(scenariosController, "usageService", usageService);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "salScenarioService", salScenarioService);
        Whitebox.setInternalState(scenariosController, "productFamilyProvider", productFamilyProvider);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetFundPoolName() {
        IFundPoolService fundPoolService = createMock(IFundPoolService.class);
        Whitebox.setInternalState(scenariosController, fundPoolService);
        String fundPoolId = "e77cad25-1b5b-45bb-b678-180288f985c1";
        String fundPoolName = "SAL fund pool";
        FundPool fundPool = new FundPool();
        fundPool.setId(fundPoolId);
        fundPool.setName(fundPoolName);
        expect(fundPoolService.getFundPoolById(fundPoolId)).andReturn(fundPool).once();
        replay(fundPoolService);
        assertEquals(fundPoolName, scenariosController.getFundPoolName(fundPoolId));
        verify(fundPoolService);
    }

    @Test
    public void testGetScenarios() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(SAL_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(SAL_PRODUCT_FAMILY)).andReturn(Collections.emptyList()).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(Collections.emptyList(), scenariosController.getScenarios());
        verify(scenarioService, productFamilyProvider);
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
    public void testOnDeleteButtonClicked() {
        mockStatic(Windows.class);
        Capture<IListener> listenerCapture = new Capture<>();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + SCENARIO_NAME + "'</b></i> scenario?"),
            capture(listenerCapture))).andReturn(null).once();
        salScenarioService.deleteScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, salScenarioService, Windows.class);
        scenariosController.onDeleteButtonClicked();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, salScenarioService, Windows.class);
    }

    @Test
    public void testHandleAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.SUBMITTED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testHandleActionApproved() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.APPROVED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testHandleActionRejected() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(usageService.isScenarioEmpty(scenario)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showConfirmDialogWithReason(eq("Confirm action"), eq("Are you sure you want to perform action?"),
            eq("Yes"), eq("Cancel"), anyObject(ConfirmActionDialogWindow.IListener.class), anyObject(Validator.class));
        expectLastCall().once();
        replay(Windows.class, usageService, scenariosWidget);
        scenariosController.handleAction(ScenarioActionTypeEnum.REJECTED);
        verify(Windows.class, usageService, scenariosWidget);
    }

    @Test
    public void testApplyScenarioAction() {
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenariosWidget.refresh();
        expectLastCall().once();
        IActionHandler handler = createMock(IActionHandler.class);
        handler.handleAction(scenario, "reason");
        expectLastCall().once();
        replay(scenariosWidget, handler);
        scenariosController.applyScenarioAction(handler, "reason");
        verify(scenariosWidget, handler);
    }

    @Test
    public void testHandleActionNull() {
        mockStatic(Windows.class);
        replay(Windows.class);
        scenariosController.handleAction(null);
        verify(Windows.class);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(SAL_PRODUCT_FAMILY);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Collections.singleton(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        replay(scenariosWidget, scenarioUsageFilterService);
        String result = scenariosController.getCriteriaHtmlRepresentation();
        assertTrue(result.contains("<b>Selection Criteria:</b>"));
        assertTrue(result.contains("<li><b><i>Product Family </i></b>(SAL)</li>"));
        assertTrue(result.contains("<li><b><i>Batch in </i></b>(BatchName)</li>"));
        assertTrue(result.contains("<li><b><i>Status </i></b>(ELIGIBLE)</li>"));
        verify(scenariosWidget, scenarioUsageFilterService);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(SAL_PRODUCT_FAMILY);
    }
}
