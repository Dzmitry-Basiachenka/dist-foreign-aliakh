package com.copyright.rup.dist.foreign.ui.scenario.impl.aacl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.ui.scenario.api.aacl.IAaclScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.aacl.AaclScenarioParameterWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AaclScenariosWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 03/19/20
 *
 * @author Uladzislau Shalamitski
 */
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
        scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        scenario.setDescription("Description");
        scenario.setNetTotal(new BigDecimal("6800.00"));
        scenario.setServiceFeeTotal(new BigDecimal("3200.00"));
        scenario.setGrossTotal(new BigDecimal("10000.00"));
        scenario.setCreateUser("User@copyright.com");
        scenario.setAuditItem(buildScenarioAuditItem());
        scenario.setAaclFields(buildAaclFields());
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        replay(controller);
        scenariosWidget.init();
        scenariosWidget.initMediator();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        Component component = scenariosWidget.getComponent(0);
        assertTrue(component instanceof HorizontalLayout);
        verifyButtonsLayout((HorizontalLayout) component);
        component = scenariosWidget.getComponent(1);
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        component = layout.getComponent(0);
        assertTrue(component instanceof Grid);
        verifyGrid((Grid) component);
        component = layout.getComponent(1);
        assertTrue(component instanceof Panel);
        verifyPanel((Panel) component);
    }

    @Test
    public void testInitMediator() {
        assertTrue(scenariosWidget.initMediator() instanceof AaclScenariosMediator);
    }

    @Test
    public void testRefresh() {
        FundPool fundPool = buildFundPool();
        List<FundPoolDetail> fundPoolDetails = buildFundPoolDetail();
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).times(2);
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID)).andReturn(buildDetailLicenseeClasses())
            .times(2);
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).times(2);
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).times(2);
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).times(2);
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
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        expect(controller.getScenarioWithAmountsAndLastAction(scenario)).andReturn(scenario).once();
        expect(controller.getDetailLicenseeClassesByScenarioId(SCENARIO_ID)).andReturn(buildDetailLicenseeClasses())
            .once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(usageController.getFundPoolById(FUND_POOL_ID)).andReturn(fundPool).once();
        expect(usageController.getFundPoolDetails(FUND_POOL_ID)).andReturn(fundPoolDetails).once();
        replay(controller, usageController, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, usageController, grid);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        replay(grid);
        assertEquals(scenario, scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    @Test
    public void testGetNotSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, GRID_ID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.EMPTY_SET).once();
        replay(grid);
        assertNull(scenariosWidget.getSelectedScenario());
        verify(grid);
    }

    private void verifyPanel(Panel panel) {
        verifySize(panel);
        assertNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifySize(grid);
        assertEquals("scenarios-table", grid.getId());
        List<Column> columns = grid.getColumns();
        assertEquals(Arrays.asList("Name", "Create Date", "Status"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        Column createDateColumn = columns.get(2);
        assertNotNull(createDateColumn.getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("scenarios-buttons", layout.getId());
        assertEquals(0, layout.getComponentCount());
    }

    private void verifySize(Component component) {
        assertEquals(100, component.getWidth(), 0);
        assertEquals(100, component.getHeight(), 0);
        assertEquals(Unit.PERCENTAGE, component.getHeightUnits());
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyScenarioMetadataPanel() {
        Panel panel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", panel.getId());
        Component content = panel.getContent();
        assertTrue(content instanceof VerticalLayout);
        VerticalLayout layout = (VerticalLayout) content;
        assertEquals(new MarginInfo(false, true, false, true), layout.getMargin());
        assertEquals(100, layout.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, layout.getWidthUnits());
        assertEquals(10, layout.getComponentCount());
        verifyMetadataLabel(layout.getComponent(0), "<b>Owner: </b>User@copyright.com");
        verifyMetadataLabel(layout.getComponent(1),
            "<b>Gross Amt in USD: </b><span class='label-amount'>10,000.00</span>");
        verifyMetadataLabel(layout.getComponent(2),
            "<b>Service Fee Amt in USD: </b><span class='label-amount'>3,200.00</span>");
        verifyMetadataLabel(layout.getComponent(3),
            "<b>Net Amt in USD: </b><span class='label-amount'>6,800.00</span>");
        verifyMetadataLabel(layout.getComponent(4),
            "<b>Title Cutoff Amount: </b><span class='label-amount'>1.35</span>");
        verifyMetadataLabel(layout.getComponent(5), "<b>Description: </b>Description");
        verifyMetadataLabel(layout.getComponent(6), SELECTION_CRITERIA);
        Component fundPool = layout.getComponent(7);
        assertTrue(fundPool instanceof Button);
        assertEquals("Fund Pool", fundPool.getCaption());
        assertTrue(layout.getComponent(8) instanceof AaclScenarioParameterWidget);
        AaclScenarioParameterWidget widget = (AaclScenarioParameterWidget) layout.getComponent(8);
        assertEquals("Licensee Class Mapping", widget.getComponent(0).getCaption());
        assertTrue(layout.getComponent(9) instanceof VerticalLayout);
        VerticalLayout lastActionLayout = (VerticalLayout) layout.getComponent(9);
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyMetadataLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyMetadataLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyMetadataLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 12/24/2016 12:00 AM");
        verifyMetadataLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> ");
        assertTrue(lastActionLayout.getComponent(4) instanceof Button);
        assertEquals("View All Actions", lastActionLayout.getComponent(4).getCaption());
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
        return Collections.singletonList(fundPoolDetail);
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
        return Collections.singletonList(detailLicenseeClass);
    }

    private AaclFields buildAaclFields() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId(FUND_POOL_ID);
        aaclFields.setTitleCutoffAmount(new BigDecimal("1.35"));
        return aaclFields;
    }

    private void verifyMetadataLabel(Component component, String expectedValue) {
        assertTrue(component instanceof Label);
        assertEquals(expectedValue, ((Label) component).getValue());
    }
}
