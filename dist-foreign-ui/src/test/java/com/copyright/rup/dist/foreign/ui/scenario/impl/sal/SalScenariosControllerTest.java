package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

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

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.IFundPoolService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.apache.commons.compress.utils.Sets;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Set;

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
@PrepareForTest({Windows.class})
public class SalScenariosControllerTest {

    private static final String SCENARIO_ID_1 = "abc9deb3-8450-4eac-a13d-d157cf5fc057";
    private static final String SCENARIO_ID_2 = "4599deb3-7450-45ac-b93d-c888cf5fc052";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final String SCENARIO_NAME_1 = "SAL Distribution 2020";
    private static final String SCENARIO_NAME_2 = "SAL Distribution 2019";

    private SalScenariosController scenariosController;
    private ISalScenariosWidget scenariosWidget;
    private Scenario scenario1;
    private Scenario scenario2;
    private IScenarioService scenarioService;
    private ISalScenarioService salScenarioService;
    private SalScenarioController scenarioController;
    private ISalScenarioWidget scenarioWidget;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        salScenarioService = createMock(ISalScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        scenarioController = createMock(SalScenarioController.class);
        scenariosWidget = createMock(ISalScenariosWidget.class);
        scenariosController = new SalScenariosController();
        scenarioWidget = new SalScenarioWidget(scenarioController);
        scenario1 = buildScenario(SCENARIO_ID_1, SCENARIO_NAME_1);
        scenario2 = buildScenario(SCENARIO_ID_2, SCENARIO_NAME_2);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
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
    public void testSendScenarioToLm() {
        mockStatic(Windows.class);
        Capture<ConfirmDialogWindow.IListener> listenerCapture = newCapture();
        expect(Windows.showConfirmDialog(
            eq("Are you sure that you want to send the following scenarios to Liability Manager: <ul><li><i><b>" +
                SCENARIO_NAME_1 + "<br><li>" + SCENARIO_NAME_2 + "</b></i></ul>"),
            capture(listenerCapture))).andReturn(null).once();
        salScenarioService.sendToLm(scenario1);
        expectLastCall().once();
        salScenarioService.sendToLm(scenario2);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, salScenarioService, Windows.class);
        scenariosController.sendToLm(Sets.newHashSet(scenario1, scenario2));
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, salScenarioService, Windows.class);
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
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario1).once();
        scenarioController.setScenario(scenario1);
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
        Capture<IListener> listenerCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario1).once();
        expect(Windows.showConfirmDialog(
            eq("Are you sure you want to delete <i><b>'" + SCENARIO_NAME_1 + "'</b></i> scenario?"),
            capture(listenerCapture))).andReturn(null).once();
        salScenarioService.deleteScenario(scenario1);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, salScenarioService, Windows.class);
        scenariosController.onDeleteButtonClicked();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, salScenarioService, Windows.class);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario1).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(SAL_PRODUCT_FAMILY);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID_1)).andReturn(scenarioUsageFilter).once();
        replay(scenariosWidget, scenarioUsageFilterService);
        assertEquals("<b>Selection Criteria:</b><ul>" +
            "<li><b><i>Product Family </i></b>(SAL)</li>" +
            "<li><b><i>Batch in </i></b>(BatchName)</li>" +
            "<li><b><i>Status </i></b>(ELIGIBLE)</li>" +
            "</ul>", scenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, scenarioUsageFilterService);
    }

    private Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        scenario.setProductFamily(SAL_PRODUCT_FAMILY);
        return scenario;
    }
}
