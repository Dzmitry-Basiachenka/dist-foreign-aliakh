package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

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
import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasScenarioService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.ui.main.api.IProductFamilyProvider;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasExcludePayeeController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableMap;
import com.vaadin.ui.Window;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link FasScenariosController}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class FasScenariosControllerTest {

    private static final String SCENARIO_ID = "3476a35d-b8fd-4e7d-97b4-3d324ffc103c";
    private static final String SCENARIO_NAME = "Scenario name";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private FasScenariosController scenariosController;
    private IFasUsageService fasUsageService;
    private IScenarioService scenarioService;
    private IFasScenarioService fasScenarioService;
    private FasScenarioController scenarioController;
    private IFasExcludePayeeController excludePayeesController;
    private Scenario scenario;
    private IFasScenariosWidget scenariosWidget;
    private IFasScenarioWidget scenarioWidget;
    private IProductFamilyProvider productFamilyProvider;

    @Before
    public void setUp() {
        fasUsageService = createMock(IFasUsageService.class);
        scenarioService = createMock(IScenarioService.class);
        fasScenarioService = createMock(IFasScenarioService.class);
        productFamilyProvider = createMock(IProductFamilyProvider.class);
        excludePayeesController = createMock(IFasExcludePayeeController.class);
        scenariosController = new FasScenariosController();
        buildScenario();
        scenarioController = createMock(FasScenarioController.class);
        Whitebox.setInternalState(scenariosController, "scenarioController", scenarioController);
        scenariosWidget = createMock(IFasScenariosWidget.class);
        scenarioWidget = new FasScenarioWidget(scenarioController);
        Whitebox.setInternalState(scenariosController, "widget", scenariosWidget);
        Whitebox.setInternalState(scenariosController, "fasUsageService", fasUsageService);
        Whitebox.setInternalState(scenariosController, "scenarioService", scenarioService);
        Whitebox.setInternalState(scenariosController, "fasScenarioService", fasScenarioService);
        Whitebox.setInternalState(scenariosController, "productFamilyProvider", productFamilyProvider);
        Whitebox.setInternalState(scenariosController, excludePayeesController);
        verify(SecurityUtils.class);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(scenariosController.instantiateWidget());
    }

    @Test
    public void testGetScenarios() {
        expect(productFamilyProvider.getSelectedProductFamily()).andReturn(FAS_PRODUCT_FAMILY).once();
        expect(scenarioService.getScenarios(FAS_PRODUCT_FAMILY)).andReturn(List.of()).once();
        replay(scenarioService, productFamilyProvider);
        assertEquals(List.of(), scenariosController.getScenarios());
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
        scenarioService.deleteScenario(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, scenarioService, Windows.class);
        scenariosController.onDeleteButtonClicked();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, scenarioService, Windows.class);
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
    public void testOnReconcileRightsholdersButtonClickedNoDiscrepancies() {
        mockStatic(Windows.class);
        IRightsholderDiscrepancyService rightsholderDiscrepancyService =
            createMock(IRightsholderDiscrepancyService.class);
        Whitebox.setInternalState(scenariosController, rightsholderDiscrepancyService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        fasScenarioService.reconcileRightsholders(scenario);
        expectLastCall().once();
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT)).andReturn(0).once();
        expect(Windows.showConfirmDialog(eq("There are no rightsholders updates for scenario " +
                "<i><b>Scenario name</b></i>. Do you want to update service fee?"),
            anyObject(ConfirmDialogWindow.IListener.class))).andReturn(null).once();
        replay(scenariosWidget, fasScenarioService, rightsholderDiscrepancyService, Windows.class);
        scenariosController.onReconcileRightsholdersButtonClicked();
        verify(scenariosWidget, fasScenarioService, rightsholderDiscrepancyService, Windows.class);
    }

    @Test
    public void testOnExcludePayeesButtonClicked() {
        mockStatic(Windows.class);
        FasExcludePayeeWidget widget = new FasExcludePayeeWidget();
        expect(excludePayeesController.initWidget()).andReturn(widget).once();
        Windows.showModalWindow(widget);
        expectLastCall().once();
        replay(scenarioService, excludePayeesController, Windows.class);
        scenariosController.onExcludePayeesButtonClicked();
        verify(scenarioService, excludePayeesController, Windows.class);
    }

    @Test
    public void testOnReconcileRightsholdersButtonClickedWithDiscrepancies() {
        mockStatic(Windows.class);
        IReconcileRightsholdersController reconcileRightsholdersController =
            createMock(IReconcileRightsholdersController.class);
        Whitebox.setInternalState(scenariosController, "reconcileRightsholdersController",
            reconcileRightsholdersController);
        IRightsholderDiscrepancyService rightsholderDiscrepancyService =
            createMock(IRightsholderDiscrepancyService.class);
        Whitebox.setInternalState(scenariosController, rightsholderDiscrepancyService);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(
            new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(reconcileRightsholdersController.getCsvStreamSource()).andReturn(streamSource).once();
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        fasScenarioService.reconcileRightsholders(scenario);
        expectLastCall().once();
        expect(rightsholderDiscrepancyService.getCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT)).andReturn(5).once();
        Windows.showModalWindow(anyObject(RightsholderDiscrepanciesWindow.class));
        expectLastCall().once();
        reconcileRightsholdersController.setScenario(scenario);
        expectLastCall().once();
        replay(scenariosWidget, fasScenarioService, reconcileRightsholdersController, rightsholderDiscrepancyService,
            Windows.class, streamSource);
        scenariosController.onReconcileRightsholdersButtonClicked();
        verify(scenariosWidget, fasScenarioService, reconcileRightsholdersController, rightsholderDiscrepancyService,
            Windows.class, streamSource);
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
    public void testOnRefreshScenarioButtonClickedNullFilter() {
        mockStatic(Windows.class);
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(null).once();
        Windows.showNotificationWindow("There are no usages that meet the criteria");
        expectLastCall().once();
        replay(Windows.class, scenariosWidget, scenarioUsageFilterService);
        scenariosController.onRefreshScenarioButtonClicked();
        verify(Windows.class, scenariosWidget, scenarioUsageFilterService);
    }

    @Test
    public void testOnRefreshScenarioButtonClickedNotNullFilter() {
        mockStatic(Windows.class);
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(new ScenarioUsageFilter()).once();
        UsageDto usageDto = new UsageDto();
        usageDto.setId("ef0863ad-eb6f-405d-90bd-f10d363bd320");
        expect(fasUsageService.getUsagesCount(new UsageFilter())).andReturn(1).once();
        Windows.showModalWindow(anyObject(RefreshScenarioWindow.class));
        expectLastCall().once();
        replay(Windows.class, scenariosWidget, scenarioUsageFilterService, fasUsageService);
        scenariosController.onRefreshScenarioButtonClicked();
        verify(Windows.class, scenariosWidget, scenarioUsageFilterService, fasUsageService);
    }

    @Test
    public void testGetCriteriaHtmlRepresentation() {
        IScenarioUsageFilterService scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        Whitebox.setInternalState(scenariosController, scenarioUsageFilterService);
        expect(scenariosWidget.getSelectedScenario()).andReturn(scenario).once();
        ScenarioUsageFilter scenarioUsageFilter = new ScenarioUsageFilter();
        scenarioUsageFilter.setFiscalYear(2018);
        scenarioUsageFilter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        scenarioUsageFilter.setProductFamily(FAS_PRODUCT_FAMILY);
        scenarioUsageFilter.setRhAccountNumbers(Set.of(1000000001L, 1000000002L));
        scenarioUsageFilter.setPaymentDate(LocalDate.of(2010, 1, 1));
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setId("batchId");
        usageBatch.setName("BatchName");
        scenarioUsageFilter.setUsageBatches(Set.of(usageBatch));
        expect(scenarioUsageFilterService.getByScenarioId(SCENARIO_ID)).andReturn(scenarioUsageFilter).once();
        IRightsholderService rightsholderService = createMock(IRightsholderService.class);
        Whitebox.setInternalState(scenariosController, rightsholderService);
        expect(rightsholderService.updateAndGetRightsholders(Set.of(1000000001L, 1000000002L))).andReturn(
            ImmutableMap.of(1000000001L, buildRightsholder(1000000001L, "Rothchild Consultants"), 1000000002L,
                buildRightsholder(1000000002L, "Royal Society of Victoria"))).once();
        replay(scenariosWidget, scenarioUsageFilterService, rightsholderService);
        assertEquals("<b>Selection Criteria:</b>" +
            "<ul><li><b><i>Product Family </i></b>(FAS)</li>" +
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
            capture(listenerCapture))).andReturn(new Window());
        fasScenarioService.sendToLm(scenario);
        expectLastCall().once();
        scenariosWidget.refresh();
        expectLastCall().once();
        replay(scenariosWidget, fasScenarioService, Windows.class);
        scenariosController.sendToLm();
        listenerCapture.getValue().onActionConfirmed();
        verify(scenariosWidget, fasScenarioService, Windows.class);
    }

    private void buildScenario() {
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setName(SCENARIO_NAME);
        scenario.setProductFamily(FAS_PRODUCT_FAMILY);
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setName(name);
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
