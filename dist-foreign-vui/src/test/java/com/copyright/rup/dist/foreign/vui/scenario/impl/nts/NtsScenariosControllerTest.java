package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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
import com.copyright.rup.dist.foreign.vui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsScenariosWidget;
import com.copyright.rup.dist.foreign.vui.scenario.impl.EditScenarioNameWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Verifies {@link NtsScenariosController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/12/2019
 *
 * @author Stanislau Rudak
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class NtsScenariosControllerTest {

    private static final String SCENARIO_ID = "199bb4cb-4743-4bb1-a2cd-fbd6888614ff";
    private static final String SCENARIO_NAME = "Scenario name";
    private static final String NTS_PRODUCT_FAMILY = "NTS";

    private NtsScenariosController scenariosController;
    private IScenarioService scenarioService;
    private Scenario scenario;
    private INtsScenariosWidget scenariosWidget;
    private IProductFamilyProvider productFamilyProvider;
    private INtsScenarioController scenarioController;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        scenariosWidget = createMock(INtsScenariosWidget.class);
        scenarioController = createMock(INtsScenarioController.class);
        buildScenario();
        scenariosController = new NtsScenariosController();
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "productFamilyProvider", productFamilyProvider);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
    }

    @Test
    public void testInstantiateWidget() {
        assertThat(scenariosController.instantiateWidget(), instanceOf(NtsScenariosWidget.class));
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = List.of(scenario);
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(NTS_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(NTS_PRODUCT_FAMILY)).andReturn(scenarios).once();
        replay(scenarioService, productFamilyProvider);
        assertSame(scenarios, scenariosController.getScenarios());
        verify(scenarioService, productFamilyProvider);
    }

    @Test
    public void testOnDeleteButtonClicked() {
        //TODO: {dbasiachenka} implement
    }

    @Test
    public void testOnViewButtonClicked() {
        mockStatic(Windows.class);
        NtsScenarioWidget scenarioWidget = createMock(NtsScenarioWidget.class);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        scenarioController.setScenario(scenario);
        expectLastCall().once();
        expect(scenarioController.initWidget()).andReturn(scenarioWidget).once();
        Windows.showModalWindow(scenarioWidget);
        expectLastCall().once();
        replay(scenariosWidget, scenarioController, Windows.class);
        scenariosController.onViewButtonClicked();
        verify(scenariosWidget, scenarioController, Windows.class);
    }

    @Test
    public void testOnEditNameButtonClicked() {
        mockStatic(Windows.class);
        Capture<EditScenarioNameWindow> editScenarioNameWindowCapture = newCapture();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        Windows.showModalWindow(capture(editScenarioNameWindowCapture));
        expectLastCall().once();
        replay(scenariosWidget, Windows.class);
        scenariosController.onEditNameButtonClicked();
        assertEquals("Edit Scenario Name", editScenarioNameWindowCapture.getValue().getHeaderTitle());
        verify(scenariosWidget, Windows.class);
    }

    @Test
    public void testSendToLm() {
        //TODO: {dbasiachenka} implement
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        Whitebox.setInternalState(scenariosController, rightsholderService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        var scenarioUsageFilter = buildScenarioUsageFilter();
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        expect(rightsholderService.updateAndGetRightsholders(Set.of(1000000001L, 1000000002L))).andReturn(
            Map.of(1000000001L, buildRightsholder(1000000001L, "Rothchild Consultants"), 1000000002L,
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

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(NTS_PRODUCT_FAMILY);
    }

    private ScenarioUsageFilter buildScenarioUsageFilter() {
        var scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setFiscalYear(2018);
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(NTS_PRODUCT_FAMILY);
        scenarioUsageFilter.setRhAccountNumbers(Set.of(1000000001L, 1000000002L));
        scenarioUsageFilter.setPaymentDate(LocalDate.of(2010, 1, 1));
        var usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        return scenarioUsageFilter;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        var rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
