package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosWidget;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow.IListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.ui.Window;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AaclScenariosController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/31/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class AaclScenariosControllerTest {

    private static final String SCENARIO_ID = "2fe241c4-7625-437b-9c8f-ff7ba4d3cb07";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String SCENARIO_NAME = "Scenario name";
    private AaclScenariosController scenariosController;
    private ILicenseeClassService licenseeClassService;
    private IAaclScenariosWidget scenariosWidget;
    private Scenario scenario;
    private IScenarioService scenarioService;
    private IAaclScenarioService aaclScenarioService;
    private AaclScenarioController scenarioController;
    private IAaclScenarioWidget scenarioWidget;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        licenseeClassService = createMock(ILicenseeClassService.class);
        scenarioService = createMock(IScenarioService.class);
        aaclScenarioService = createMock(IAaclScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        scenariosController = new AaclScenariosController();
        buildScenario();
        scenarioController = createMock(AaclScenarioController.class);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        scenariosWidget = createMock(IAaclScenariosWidget.class);
        scenarioWidget = new AaclScenarioWidget(scenarioController);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "licenseeClassService", licenseeClassService);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "productFamilyProvider", productFamilyProvider);
        Whitebox.setInternalState(scenariosController, "aaclScenarioService", aaclScenarioService);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetScenarios() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(AACL_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(AACL_PRODUCT_FAMILY)).andReturn(List.of()).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(List.of(), scenariosController.getScenarios());
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
    public void testGetDetailLicenseeClassesByScenarioId() {
        String scenarioId = "43e6b6e8-4c80-40ba-9836-7b27b2bbca5f";
        List<DetailLicenseeClass> detailLicenseeClasses = List.of(new DetailLicenseeClass());
        expect(licenseeClassService.getDetailLicenseeClassesByScenarioId(scenarioId))
            .andReturn(detailLicenseeClasses).once();
        replay(licenseeClassService);
        assertSame(detailLicenseeClasses, scenariosController.getDetailLicenseeClassesByScenarioId(scenarioId));
        verify(licenseeClassService);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(AACL_PRODUCT_FAMILY);
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        replay(scenariosWidget, scenarioUsageFilterService);
        assertEquals("<b>Selection Criteria:</b><ul>" +
            "<li><b><i>Product Family </i></b>(AACL)</li>" +
            "<li><b><i>Batch in </i></b>(BatchName)</li>" +
            "<li><b><i>Status </i></b>(ELIGIBLE)</li>" +
            "</ul>", scenariosController.getCriteriaHtmlRepresentation());
        verify(scenariosWidget, scenarioUsageFilterService);
    }

    @Test
    public void testSendScenarioToLm() {
        mockStatic(Windows.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        Capture<IListener> listenerCapture = newCapture();
        expect(Windows.showConfirmDialog(
            eq("Are you sure that you want to send scenario <i><b>" + SCENARIO_NAME + "</b></i> to Liability Manager?"),
            capture(listenerCapture))).andReturn(new Window());
        aaclScenarioService.sendToLm(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, aaclScenarioService, Windows.class);
        scenariosController.sendToLm();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, aaclScenarioService, Windows.class);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(AACL_PRODUCT_FAMILY);
    }
}
