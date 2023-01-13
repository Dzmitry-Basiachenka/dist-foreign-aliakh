package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGrid;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.AggregateLicenseeClassMappingWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.PublicationTypeWeightsWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.UsageAgeWeightWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.Lists;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link AaclScenariosWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Windows.class)
public class AaclScenariosWidgetTest {

    private static final String GRID_ID = "scenarioGrid";
    private static final String SCENARIO_ID = "505aeb54-cd42-4f3c-84d9-36e60f5f7023";
    private static final String FUND_POOL_ID = "e0d3b5c5-2a63-473a-9d37-1233cfaad346";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";

    private AaclScenariosWidget scenariosWidget;
    private IAaclScenariosController controller;
    private IAaclUsageController usageController;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IAaclScenariosController.class);
        usageController = createMock(IAaclUsageController.class);
        scenariosWidget = new AaclScenariosWidget(controller, new ScenarioHistoryController(), usageController);
        scenariosWidget.setController(controller);
        scenario = buildAaclScenario();
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        replay(controller, usageController);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        verify(controller, usageController);
        reset(controller, usageController);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponent(0);
        assertThat(component, instanceOf(HorizontalLayout.class));
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponent(1);
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertThat(component, instanceOf(Grid.class));
        Grid grid = (Grid) component;
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        verifyGrid(grid, List.of(
            Triple.of("Name", -1.0, 1),
            Triple.of("Created Date", 100.0, -1),
            Triple.of("Status", 130.0, -1)
        ));
        assertEquals("scenarios-table", grid.getId());
        component = layout.getComponent(1);
        assertThat(component, instanceOf(Panel.class));
        verifyPanel((Panel) component);
    }

    @Test
    public void testInitMediator() {
        assertThat(scenariosWidget.initMediator(), instanceOf(AaclScenariosMediator.class));
    }

    @Test
    public void testRefresh() {
        FundPool fundPool = buildFundPool();
        List<FundPoolDetail> fundPoolDetails = buildFundPoolDetail();
        expect(controller.getScenarios()).andReturn(List.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).times(2);
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID)).andReturn(buildDetailLicenseeClasses())
            .times(2);
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).times(2);
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).times(2);
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).times(2);
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).times(2);
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).times(2);
        replay(controller, usageController);
        scenariosWidget.refresh();
        verifyScenarioMetadataPanel();
        verify(controller, usageController);
    }

    @Test
    public void testSelectScenario() {
        FundPool fundPool = buildFundPool();
        List<FundPoolDetail> fundPoolDetails = buildFundPoolDetail();
        Grid grid = Whitebox.getInternalState(scenariosWidget, GRID_ID);
        assertTrue(CollectionUtils.isEmpty(grid.getSelectedItems()));
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID)).andReturn(buildDetailLicenseeClasses())
            .once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(StringUtils.EMPTY).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).once();
        replay(controller, usageController);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
        verify(controller, usageController);
    }

    @Test
    public void testRefreshSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        FundPool fundPool = buildFundPool();
        List<FundPoolDetail> fundPoolDetails = buildFundPoolDetail();
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID)).andReturn(buildDetailLicenseeClasses())
            .once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).once();
        replay(controller, usageController, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, usageController, grid);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Set.of()).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testShowUsageAgeWeightsWindow() {
        mockStatic(Windows.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        List<UsageAge> usageAges = scenario.getAaclFields().getUsageAges();
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID))
            .andReturn(buildDetailLicenseeClasses()).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(buildFundPool()).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(buildFundPoolDetail()).once();
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        replay(Windows.class, grid, controller, usageController);
        scenariosWidget.refreshSelectedScenario();
        Panel metadataPanel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        ScenarioParameterWidget usageAgeWeightWidget = (ScenarioParameterWidget) metadataLayout.getComponent(7);
        assertEquals("Usage Age Weights", usageAgeWeightWidget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(usageAgeWeightWidget, "button");
        button.click();
        UsageAgeWeightWindow window = (UsageAgeWeightWindow) windowCapture.getValue();
        assertEquals(usageAges, Whitebox.getInternalState(window, "currentValues"));
        verify(Windows.class, grid, controller, usageController);
    }

    @Test
    public void testShowPubTypeWeightsWindow() {
        mockStatic(Windows.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        List<PublicationType> publicationTypes = scenario.getAaclFields().getPublicationTypes();
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID))
            .andReturn(buildDetailLicenseeClasses()).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(buildFundPool()).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(buildFundPoolDetail()).once();
        expect(usageController.getPublicationTypes()).andReturn(publicationTypes).once();
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        replay(Windows.class, grid, controller, usageController);
        scenariosWidget.refreshSelectedScenario();
        Panel metadataPanel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        ScenarioParameterWidget pubTypeWeightWidget = (ScenarioParameterWidget) metadataLayout.getComponent(8);
        assertEquals("Pub Type Weights", pubTypeWeightWidget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(pubTypeWeightWidget, "button");
        button.click();
        PublicationTypeWeightsWindow window = (PublicationTypeWeightsWindow) windowCapture.getValue();
        assertEquals(publicationTypes, Whitebox.getInternalState(window, "currentValues"));
        verify(Windows.class, grid, controller, usageController);
    }

    @Test
    public void testShowLicenseeClassMappingWindow() {
        mockStatic(Windows.class);
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        List<DetailLicenseeClass> detailLicenseeClasses = buildDetailLicenseeClasses();
        expect(grid.getSelectedItems()).andReturn(Set.of(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID))
            .andReturn(detailLicenseeClasses).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(buildFundPool()).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(buildFundPoolDetail()).once();
        expect(usageController.getPublicationTypes()).andReturn(buildPublicationTypes()).once();
        expect(usageController.getDefaultUsageAges(Lists.newArrayList(2019, 2018)))
            .andReturn(buildUsageAges()).once();
        Capture<Window> windowCapture = newCapture();
        Windows.showModalWindow(capture(windowCapture));
        replay(Windows.class, grid, controller, usageController);
        scenariosWidget.refreshSelectedScenario();
        Panel metadataPanel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        VerticalLayout metadataLayout = (VerticalLayout) metadataPanel.getContent();
        ScenarioParameterWidget licenseeClassMappingWidget = (ScenarioParameterWidget) metadataLayout.getComponent(9);
        assertEquals("Licensee Class Mapping", licenseeClassMappingWidget.getComponent(0).getCaption());
        Button button = Whitebox.getInternalState(licenseeClassMappingWidget, "button");
        button.click();
        AggregateLicenseeClassMappingWindow window = (AggregateLicenseeClassMappingWindow) windowCapture.getValue();
        assertEquals(detailLicenseeClasses, Whitebox.getInternalState(window, "currentValues"));
        verify(Windows.class, grid, controller, usageController);
    }

    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
        assertNull(panel.getContent());
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(7, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "View");
        verifyButton(layout.getComponent(1), "Edit Name");
        verifyButton(layout.getComponent(2), "Delete");
        verifyButton(layout.getComponent(3), "Submit for Approval");
        verifyButton(layout.getComponent(4), "Reject");
        verifyButton(layout.getComponent(5), "Approve");
        verifyButton(layout.getComponent(6), "Send to LM");
    }

    private void verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertEquals(caption.replaceAll(StringUtils.SPACE, "_"), button.getId());
        assertFalse(button.isEnabled());
        assertTrue(button.isDisableOnClick());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
    }

    private void verifyScenarioMetadataPanel() {
        Panel panel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", panel.getId());
        Component content = panel.getContent();
        assertThat(content, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(100, layout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnits());
        assertEquals(11, layout.getComponentCount());
        verifyLabel(layout.getComponent(0), "<b>Owner: </b>User@copyright.com", ContentMode.HTML, -1.0f);
        verifyLabel(layout.getComponent(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>", ContentMode.HTML, -1.0f);
        verifyLabel(layout.getComponent(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>", ContentMode.HTML, -1.0f);
        verifyLabel(layout.getComponent(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>", ContentMode.HTML, -1.0f);
        verifyLabel(layout.getComponent(4), "<b>Description: </b>Description", ContentMode.HTML, -1.0f);
        verifyLabel(layout.getComponent(5), SELECTION_CRITERIA, ContentMode.HTML, -1.0f);
        Component fundPool = layout.getComponent(6);
        assertThat(fundPool, instanceOf(Button.class));
        assertEquals("Fund Pool", fundPool.getCaption());
        verifyScenarioParameterWidget(layout.getComponent(7), "Usage Age Weights");
        verifyScenarioParameterWidget(layout.getComponent(8), "Pub Type Weights");
        verifyScenarioParameterWidget(layout.getComponent(9), "Licensee Class Mapping");
        assertThat(layout.getComponent(10), instanceOf(VerticalLayout.class));
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponent(10);
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> ");
        assertThat(lastActionLayout.getComponent(4), instanceOf(Button.class));
        assertEquals("View All Actions", lastActionLayout.getComponent(4).getCaption());
    }

    private void verifyScenarioParameterWidget(Component component, String expectedCaption) {
        assertNotNull(component);
        ScenarioParameterWidget widget = (ScenarioParameterWidget) component;
        assertEquals(expectedCaption, widget.getComponent(0).getCaption());
    }

    private Scenario buildAaclScenario() {
        Scenario aaclScenario = new Scenario();
        aaclScenario.setId(SCENARIO_ID);
        aaclScenario.setDescription("Description");
        aaclScenario.setNetTotal(new BigDecimal("6800.00"));
        aaclScenario.setServiceFeeTotal(new BigDecimal("3200.00"));
        aaclScenario.setGrossTotal(new BigDecimal("10000.00"));
        aaclScenario.setCreateUser("User@copyright.com");
        aaclScenario.setAuditItem(buildScenarioAuditItem());
        aaclScenario.setAaclFields(buildAaclFields());
        return aaclScenario;
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem scenarioAuditItem = new ScenarioAuditItem();
        scenarioAuditItem.setActionType(ScenarioActionTypeEnum.ADDED_USAGES);
        scenarioAuditItem.setActionReason(StringUtils.EMPTY);
        scenarioAuditItem.setCreateUser("user@copyright.com");
        scenarioAuditItem.setCreateDate(
            Date.from(LocalDate.of(2016, 12, 24).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return scenarioAuditItem;
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setId(FUND_POOL_ID);
        return fundPool;
    }

    private List<FundPoolDetail> buildFundPoolDetail() {
        FundPoolDetail fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseeClass(108, "EXGP", "Life Sciences"));
        fundPoolDetail.setGrossAmount(BigDecimal.ONE);
        return List.of(fundPoolDetail);
    }

    private AggregateLicenseeClass buildAggregateLicenseeClass(Integer id, String enrollmentProfile,
                                                               String discipline) {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(id);
        aggregateLicenseeClass.setEnrollmentProfile(enrollmentProfile);
        aggregateLicenseeClass.setDiscipline(discipline);
        return aggregateLicenseeClass;
    }

    private List<DetailLicenseeClass> buildDetailLicenseeClasses() {
        AggregateLicenseeClass aggregateLicenseeClass = buildAggregateLicenseeClass(108, "EXGP", "Life Sciences");
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(108);
        detailLicenseeClass.setEnrollmentProfile("EXGP");
        detailLicenseeClass.setDiscipline("Life Sciences");
        detailLicenseeClass.setAggregateLicenseeClass(aggregateLicenseeClass);
        return List.of(detailLicenseeClass);
    }

    private AaclFields buildAaclFields() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId(FUND_POOL_ID);
        aaclFields.setUsageAges(buildUsageAges());
        aaclFields.setPublicationTypes(buildPublicationTypes());
        return aaclFields;
    }

    private List<PublicationType> buildPublicationTypes() {
        return List.of(
            buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", "1.00"),
            buildPublicationType("68fd94c0-a8c0-4a59-bfe3-6674c4b12199", "Business or Trade Journal", "1.50"),
            buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", "Consumer Magazine", "1.00"),
            buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", "News Source", "4.00"),
            buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", "1.10"));
    }

    private PublicationType buildPublicationType(String id, String name, String weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
        pubType.setWeight(new BigDecimal(weight));
        return pubType;
    }

    private List<UsageAge> buildUsageAges() {
        return List.of(
            buildUsageAge(2019, new BigDecimal("1.00")),
            buildUsageAge(2018, new BigDecimal("0.75")));
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }
}
