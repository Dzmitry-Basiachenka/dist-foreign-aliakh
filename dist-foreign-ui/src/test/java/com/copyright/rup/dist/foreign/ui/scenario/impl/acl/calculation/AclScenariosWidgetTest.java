package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridItems;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclAggregateLicenseeClassMappingViewWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.AclUsageAgeWeightWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link AclScenariosWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/22/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, AclScenariosWidget.class, ForeignSecurityUtils.class, RupContextUtils.class,
    OffsetDateTime.class})
public class AclScenariosWidgetTest {

    private static final String SCENARIO_UID = "29ca6de6-0496-49e8-8ff4-334ef1bab597";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";
    private static final String SCENARIO_GRID = "scenarioGrid";

    private AclScenariosWidget scenariosWidget;
    private IAclScenariosController controller;
    private AclScenario scenario;
    private AclScenarioDto scenarioDto;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        scenario = buildAclScenario();
        scenarioDto = buildAclScenarioDto();
        mockStatic(ForeignSecurityUtils.class);
        scenariosWidget = new AclScenariosWidget(controller, createMock(IAclScenarioHistoryController.class));
        expect(controller.getScenarios()).andReturn(List.of(scenario)).times(2);
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(scenarioDto).times(2);
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).times(2);
        expect(controller.getUsageAgeWeights()).andReturn(List.of()).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(List.of()).once();
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).times(2);
        expect(controller.getExportAclSummaryOfWorkSharesByAggLcStreamSource()).andReturn(streamSource).once();
        expect(controller.getExportAclWorkSharesByAggLcStreamSource()).andReturn(streamSource).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).times(2);
        expect(controller.initAclScenariosFilterWidget())
            .andReturn(new AclScenariosFilterWidget(new AclScenariosFilterController())).once();
        replay(controller, streamSource, ForeignSecurityUtils.class);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        scenariosWidget.refresh();
        verify(controller, streamSource, ForeignSecurityUtils.class);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        scenario.setEditableFlag(true);
        assertEquals(1, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponent(0);
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        HorizontalSplitPanel splitPanel = (HorizontalSplitPanel) horizontalLayout.getComponent(0);
        assertThat(splitPanel, instanceOf(HorizontalSplitPanel.class));
        assertEquals(2, splitPanel.getComponentCount());
        assertThat(splitPanel.getFirstComponent(), instanceOf(AclScenariosFilterWidget.class));
        VerticalLayout mainLayout = (VerticalLayout) splitPanel.getSecondComponent();
        assertThat(mainLayout, instanceOf(VerticalLayout.class));
        HorizontalLayout buttons = (HorizontalLayout) mainLayout.getComponent(0);
        assertThat(buttons, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout(buttons);
        Grid grid = (Grid) mainLayout.getComponent(1);
        verifyGrid(grid);
        component = horizontalLayout.getComponent(1);
        assertThat(component, instanceOf(Panel.class));
        verifyPanel((Panel) component);
    }

    @Test
    public void testGridValues() {
        HorizontalSplitPanel splitPanel =
            (HorizontalSplitPanel) ((HorizontalLayout) scenariosWidget.getComponent(0)).getComponent(0);
        VerticalLayout mainLayout = (VerticalLayout) splitPanel.getSecondComponent();
        Grid<?> grid = (Grid<?>) mainLayout.getComponent(1);
        Object[][] expectedCells = {
            {"ACL Scenario name", "ACL", 202212, "N", "IN_PROGRESS"}
        };
        verifyGridItems(grid, List.of(scenario), expectedCells);
    }

    @Test
    public void testRefresh() {
        reset(controller, ForeignSecurityUtils.class);
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).times(2);
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller, ForeignSecurityUtils.class);
        scenariosWidget.refresh();
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testSelectScenario() {
        reset(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        Grid grid = Whitebox.getInternalState(scenariosWidget, SCENARIO_GRID);
        grid.deselectAll();
        assertTrue(CollectionUtils.isEmpty(grid.getSelectedItems()));
        expect(controller.getAclScenarioWithAmountsAndLastAction(scenarioDto.getId())).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(StringUtils.EMPTY).once();
        replay(controller, ForeignSecurityUtils.class);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
        verify(controller, ForeignSecurityUtils.class);
    }

    @Test
    public void testRefreshSelectedScenario() {
        reset(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasSpecialistPermission()).andReturn(true).once();
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(scenarioDto.getId())).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller, grid, ForeignSecurityUtils.class);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, grid, ForeignSecurityUtils.class);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.emptySet()).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testShowUsageAgeWeightsWindow() {
        mockStatic(Windows.class);
        List<UsageAge> usageAges = buildUsageAges();
        expect(controller.getUsageAgeWeightsByScenarioId(SCENARIO_UID)).andReturn(usageAges).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(controller, Windows.class);
        Panel metadataPanel = getMetaDataPanel();
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        ScenarioParameterWidget widget = (ScenarioParameterWidget) metadataLayout.getComponent(6);
        assertEquals("Usage Age Weights", widget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(widget, "button");
        button.click();
        AclUsageAgeWeightWindow window = (AclUsageAgeWeightWindow) windowCapture.getValue();
        assertEquals(usageAges, Whitebox.getInternalState(window, "currentValues"));
        verify(controller, Windows.class);
    }

    @Test
    public void testShowPubTypeWeightsWindow() {
        mockStatic(Windows.class);
        List<AclPublicationType> publicationTypes = buildPublicationTypes();
        expect(controller.getAclPublicationTypesByScenarioId(SCENARIO_UID)).andReturn(publicationTypes).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(controller, Windows.class);
        Panel metadataPanel = getMetaDataPanel();
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        AclPublicationTypeWeightsParameterWidget widget =
            (AclPublicationTypeWeightsParameterWidget) metadataLayout.getComponent(7);
        assertEquals("Pub Type Weights", widget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(widget, "button");
        button.click();
        AclPublicationTypeWeightsWindow window = (AclPublicationTypeWeightsWindow) windowCapture.getValue();
        assertEquals(publicationTypes, Whitebox.getInternalState(window, "currentValues"));
        verify(controller, Windows.class);
    }

    @Test
    public void testShowLicenseeClassMappingWindow() {
        mockStatic(Windows.class);
        List<DetailLicenseeClass> detailLicenseeClasses = buildDetailLicenseeClasses();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_UID)).andReturn(detailLicenseeClasses).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(controller, Windows.class);
        Panel metadataPanel = getMetaDataPanel();
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        ScenarioParameterWidget widget = (ScenarioParameterWidget) metadataLayout.getComponent(8);
        assertEquals("Licensee Class Mapping", widget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(widget, "button");
        button.click();
        AclAggregateLicenseeClassMappingViewWindow window =
            (AclAggregateLicenseeClassMappingViewWindow) windowCapture.getValue();
        Grid<DetailLicenseeClass> grid = Whitebox.getInternalState(window, "grid");
        DataProvider<DetailLicenseeClass, ?> dataProvider = grid.getDataProvider();
        assertEquals(detailLicenseeClasses, dataProvider.fetch(new Query<>()).collect(Collectors.toList()));
        verify(controller, Windows.class);
    }

    @Test
    public void testGetReportInfo() {
        OffsetDateTime now = OffsetDateTime.of(2022, 10, 13, 1, 2, 3, 4, ZoneOffset.ofHours(0));
        mockStatic(OffsetDateTime.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(OffsetDateTime.now()).andReturn(now).once();
        replay(controller, grid, OffsetDateTime.class);
        AclCalculationReportsInfoDto reportInfo = scenariosWidget.getReportInfo();
        assertEquals(scenario, reportInfo.getScenarios().get(0));
        assertEquals("SYSTEM", reportInfo.getUser());
        assertEquals(now, reportInfo.getReportDateTime());
        verify(controller, grid, OffsetDateTime.class);
    }

    private AclScenario buildAclScenario() {
        AclScenario aclScenario = new AclScenario();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("some description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        return aclScenario;
    }

    private AclScenarioDto buildAclScenarioDto() {
        AclScenarioDto aclScenario = new AclScenarioDto();
        aclScenario.setId(SCENARIO_UID);
        aclScenario.setName("ACL Scenario name");
        aclScenario.setDescription("some description");
        aclScenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        aclScenario.setEditableFlag(false);
        aclScenario.setPeriodEndDate(202212);
        aclScenario.setLicenseType("ACL");
        aclScenario.setCreateDate(Date.from(LocalDate.of(2022, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        aclScenario.setCreateUser("user@copyright.com");
        aclScenario.setGrossTotal(new BigDecimal("30000.00"));
        aclScenario.setGrossTotalPrint(new BigDecimal("10000.00"));
        aclScenario.setGrossTotalDigital(new BigDecimal("20000.00"));
        aclScenario.setServiceFeeTotal(new BigDecimal("4800.00"));
        aclScenario.setServiceFeeTotalPrint(new BigDecimal("1600.00"));
        aclScenario.setServiceFeeTotalDigital(new BigDecimal("3200.00"));
        aclScenario.setNetTotal(new BigDecimal("25200.00"));
        aclScenario.setNetTotalPrint(new BigDecimal("8400.00"));
        aclScenario.setNetTotalDigital(new BigDecimal("16800.00"));
        aclScenario.setNumberOfRhsPrint(1);
        aclScenario.setNumberOfRhsDigital(2);
        aclScenario.setNumberOfWorksPrint(3);
        aclScenario.setNumberOfWorksDigital(4);
        aclScenario.setAuditItem(buildScenarioAuditItem());
        return aclScenario;
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setActionType(ScenarioActionTypeEnum.ADDED_USAGES);
        auditItem.setActionReason("some reason");
        auditItem.setCreateUser("user@copyright.com");
        auditItem.setCreateDate(Date.from(LocalDate.of(2022, 7, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return auditItem;
    }

    private List<UsageAge> buildUsageAges() {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(0);
        usageAge.setWeight(new BigDecimal("1.00"));
        return List.of(usageAge);
    }

    private List<AclPublicationType> buildPublicationTypes() {
        AclPublicationType publicationType = new AclPublicationType();
        publicationType.setName("BK");
        publicationType.setWeight(new BigDecimal("1.00"));
        publicationType.setPeriod(202012);
        return List.of(publicationType);
    }

    private List<DetailLicenseeClass> buildDetailLicenseeClasses() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(1);
        detailLicenseeClass.setDescription("Food and Tobacco");
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(51);
        aggregateLicenseeClass.setDescription("Materials");
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        return List.of(detailLicenseeClass);
    }

    private Panel getMetaDataPanel() {
        return (Panel) ((VerticalLayout) (((Panel) ((HorizontalLayout) scenariosWidget.getComponent(0)).getComponent(1))
            .getContent())).getComponent(0);
    }

    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
    }

    private void verifyGrid(Grid grid) {
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("acl-scenarios-table", grid.getId());
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Name", -1.0, 1),
            Triple.of("License Type", 100.0, -1),
            Triple.of("Period", 70.0, -1),
            Triple.of("Editable", 90.0, -1),
            Triple.of("Status", 150.0, -1)
        ));
        assertNotNull(((Column) grid.getColumns().get(2)).getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("acl-scenario-buttons-layout", layout.getId());
        assertEquals(8, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Create", 2, true);
        verifyButton(layout.getComponent(1), "View", 2, true);
        verifyButton(layout.getComponent(2), "Delete", 2, true);
        verifyButton(layout.getComponent(3), "Pub Type Weights", 2, true);
        verifyButton(layout.getComponent(4), "Submit for Approval", 2, true);
        verifyButton(layout.getComponent(5), "Reject", 2, false);
        verifyButton(layout.getComponent(6), "Approve", 2, false);
        verifyButton(layout.getComponent(7), "Send to LM", 2, false);
    }

    private void verifyButton(Component component, String caption, int listenersCount, boolean isEnabled) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(isEnabled, button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(listenersCount, button.getListeners(ClickEvent.class).size());
    }

    private void verifyScenarioMetadataPanel() {
        Panel mainPanel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(0)).getComponent(1);
        Panel metadataPanel = getMetaDataPanel();
        Component content = metadataPanel.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout metadataLayout = (VerticalLayout) content;
        assertEquals(11, metadataLayout.getComponentCount());
        verifyLabel(metadataLayout.getComponent(0), "<b>Owner: </b>user@copyright.com");
        assertThat(metadataLayout.getComponent(1), instanceOf(VerticalLayout.class));
        VerticalLayout grossTotalLayout = (VerticalLayout) metadataLayout.getComponent(1);
        assertEquals("scenario-detailed-amount", grossTotalLayout.getId());
        assertEquals("<b>Gross Amt in USD: </b><span class='label-amount'>30,000.00</span>",
            grossTotalLayout.getCaption());
        assertEquals(6, grossTotalLayout.getComponentCount());
        verifyLabel(grossTotalLayout.getComponent(0),
            "<b>Gross Amt in USD by Print: </b><span class='label-amount'>10,000.00</span>");
        verifyLabel(grossTotalLayout.getComponent(1),
            "<b>Gross Amt in USD by Digital: </b><span class='label-amount'>20,000.00</span>");
        verifyLabel(grossTotalLayout.getComponent(2), "<b># of RH Print: </b>1");
        verifyLabel(grossTotalLayout.getComponent(3), "<b># of RH Digital: </b>2");
        verifyLabel(grossTotalLayout.getComponent(4), "<b># of works Print: </b>3");
        verifyLabel(grossTotalLayout.getComponent(5), "<b># of works Digital: </b>4");
        assertThat(metadataLayout.getComponent(2), instanceOf(VerticalLayout.class));
        VerticalLayout serviceFeeTotalLayout = (VerticalLayout) metadataLayout.getComponent(2);
        assertEquals("scenario-detailed-amount", serviceFeeTotalLayout.getId());
        assertEquals("<b>Service Fee Amt in USD: </b><span class='label-amount'>4,800.00</span>",
            serviceFeeTotalLayout.getCaption());
        assertEquals(2, serviceFeeTotalLayout.getComponentCount());
        verifyLabel(serviceFeeTotalLayout.getComponent(0),
            "<b>Service Fee Amt in USD by Print: </b><span class='label-amount'>1,600.00</span>");
        verifyLabel(serviceFeeTotalLayout.getComponent(1),
            "<b>Service Fee Amt in USD by Digital: </b><span class='label-amount'>3,200.00</span>");
        assertThat(metadataLayout.getComponent(3), instanceOf(VerticalLayout.class));
        VerticalLayout netTotalLayout = (VerticalLayout) metadataLayout.getComponent(3);
        assertEquals("scenario-detailed-amount", netTotalLayout.getId());
        assertEquals("<b>Net Amt in USD: </b><span class='label-amount'>25,200.00</span>",
            netTotalLayout.getCaption());
        assertEquals(2, netTotalLayout.getComponentCount());
        verifyLabel(netTotalLayout.getComponent(0),
            "<b>Net Amt in USD by Print: </b><span class='label-amount'>8,400.00</span>");
        verifyLabel(netTotalLayout.getComponent(1),
            "<b>Net Amt in USD by Digital: </b><span class='label-amount'>16,800.00</span>");
        verifyLabel(metadataLayout.getComponent(4), "<b>Description: </b>some description");
        verifyLabel(metadataLayout.getComponent(5), SELECTION_CRITERIA);
        verifyScenarioParameterWidget(metadataLayout.getComponent(6), "Usage Age Weights");
        verifyAclPublicationTypeWeightsParameterWidget(metadataLayout.getComponent(7));
        verifyScenarioParameterWidget(metadataLayout.getComponent(8), "Licensee Class Mapping");
        verifyLabel(metadataLayout.getComponent(9), "<b>Copied From: </b>");
        assertThat(metadataLayout.getComponent(10), instanceOf(VerticalLayout.class));
        VerticalLayout lastActionLayout = (VerticalLayout) metadataLayout.getComponent(10);
        assertEquals("scenario-last-action", lastActionLayout.getId());
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 07/01/2022 12:00 AM");
        verifyLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> some reason");
        UiTestHelper.verifyButton(lastActionLayout.getComponent(4), "View All Actions", true);
        VerticalLayout reportLayout = (VerticalLayout) ((VerticalLayout) mainPanel.getContent()).getComponent(1);
        assertEquals(2, reportLayout.getComponentCount());
        UiTestHelper.verifyButton(
            reportLayout.getComponent(0), "Summary of Work Shares by Aggregate Licensee Class Report", true);
        UiTestHelper.verifyButton(
            reportLayout.getComponent(1), "Work Shares by Aggregate Licensee Class Report", true);
    }

    private void verifyScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        ScenarioParameterWidget widget = (ScenarioParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
    }

    private void verifyAclPublicationTypeWeightsParameterWidget(Component component) {
        assertNotNull(component);
        AclPublicationTypeWeightsParameterWidget widget = (AclPublicationTypeWeightsParameterWidget) component;
        assertEquals("Pub Type Weights", widget.getComponent(0).getCaption());
    }
}
