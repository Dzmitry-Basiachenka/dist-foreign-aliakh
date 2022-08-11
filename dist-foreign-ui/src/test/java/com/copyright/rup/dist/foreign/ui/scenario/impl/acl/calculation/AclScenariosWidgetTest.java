package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLabel;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;

import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioHistoryController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.AclPublicationTypeWeightsParameterWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

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
@PrepareForTest({Windows.class, ForeignSecurityUtils.class})
public class AclScenariosWidgetTest {

    private static final String SCENARIO_UID = "29ca6de6-0496-49e8-8ff4-334ef1bab597";
    private static final String SELECTION_CRITERIA = "<b>Selection Criteria:</b>";
    private static final String SCENARIO_GRID = "scenarioGrid";

    private AclScenariosWidget scenariosWidget;
    private IAclScenariosController controller;
    private AclScenario scenario;

    @Before
    public void setUp() {
        controller = createMock(IAclScenariosController.class);
        scenario = buildAclScenario();
        mockStatic(ForeignSecurityUtils.class);
        scenariosWidget = new AclScenariosWidget(controller, createMock(IAclScenarioHistoryController.class));
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(new AclScenarioDto()).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        expect(controller.getUsageAgeWeights()).andReturn(Collections.emptyList()).once();
        expect(controller.getDetailLicenseeClasses()).andReturn(Collections.emptyList()).once();
        replay(controller);
        scenariosWidget.init();
        verify(controller);
        reset(controller);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(2, scenariosWidget.getComponentCount());
        verifyButtonsLayout((HorizontalLayout) scenariosWidget.getComponent(0));
        Component component = scenariosWidget.getComponent(1);
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
    public void testRefresh() {
        expect(controller.getScenarios()).andReturn(Collections.singletonList(scenario)).once();
        expect(controller.getAclScenarioWithAmountsAndLastAction(SCENARIO_UID)).andReturn(new AclScenarioDto()).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller);
        scenariosWidget.refresh();
        verify(controller);
    }

    @Test
    public void testSelectScenario() {
        Grid grid = Whitebox.getInternalState(scenariosWidget, SCENARIO_GRID);
        grid.deselectAll();
        assertTrue(CollectionUtils.isEmpty(grid.getSelectedItems()));
        AclScenarioDto scenarioDto = buildAclScenarioDto();
        expect(controller.getAclScenarioWithAmountsAndLastAction(scenarioDto.getId())).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(StringUtils.EMPTY).once();
        replay(controller);
        scenariosWidget.selectScenario(scenario);
        assertEquals(scenario, grid.getSelectedItems().iterator().next());
        verify(controller);
    }

    @Test
    public void testRefreshSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
        AclScenarioDto scenarioDto = buildAclScenarioDto();
        expect(controller.getAclScenarioWithAmountsAndLastAction(scenarioDto.getId())).andReturn(scenarioDto).once();
        expect(controller.getCriteriaHtmlRepresentation()).andReturn(SELECTION_CRITERIA).once();
        replay(controller, grid);
        scenariosWidget.refreshSelectedScenario();
        verifyScenarioMetadataPanel();
        verify(controller, grid);
    }

    @Test
    public void testGetSelectedScenario() {
        Grid grid = createMock(Grid.class);
        Whitebox.setInternalState(scenariosWidget, SCENARIO_GRID, grid);
        expect(grid.getSelectedItems()).andReturn(Collections.singleton(scenario)).once();
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

    private void verifyPanel(Panel panel) {
        verifyWindow(panel, null, 100, 100, Unit.PERCENTAGE);
        assertNotNull(panel.getContent());
    }

    private void verifyGrid(Grid grid) {
        verifyWindow(grid, null, 100, 100, Unit.PERCENTAGE);
        assertEquals("acl-scenarios-table", grid.getId());
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Name", -1.0, 1),
            Triple.of("License Type", 110.0, -1),
            Triple.of("Period", 100.0, -1),
            Triple.of("Editable", 100.0, -1),
            Triple.of("Created Date", 120.0, -1),
            Triple.of("Status", 130.0, -1)
        ));
        assertNotNull(((Column) grid.getColumns().get(2)).getComparator(SortDirection.ASCENDING));
    }

    private void verifyButtonsLayout(HorizontalLayout layout) {
        assertEquals("acl-scenario-buttons-layout", layout.getId());
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Create", false, 1);
        verifyButton(layout.getComponent(1), "View", true, 2);
        verifyButton(layout.getComponent(2), "Pub Type Weights", false, 1);
    }

    private void verifyButton(Component component, String caption, boolean isDisabled, int listenersCount) {
        assertTrue(component instanceof Button);
        Button button = (Button) component;
        assertEquals(caption, button.getCaption());
        assertTrue(button.isEnabled());
        assertEquals(isDisabled, button.isDisableOnClick());
        assertEquals(listenersCount, button.getListeners(ClickEvent.class).size());
    }

    private void verifyScenarioMetadataPanel() {
        Panel metadataPanel = (Panel) ((HorizontalLayout) scenariosWidget.getComponent(1)).getComponent(1);
        assertEquals("scenarios-metadata", metadataPanel.getId());
        Component content = metadataPanel.getContent();
        assertTrue(content instanceof VerticalLayout);
        VerticalLayout metadataLayout = (VerticalLayout) content;
        assertEquals(11, metadataLayout.getComponentCount());
        verifyLabel(metadataLayout.getComponent(0), "<b>Owner: </b>user@copyright.com");
        assertTrue(metadataLayout.getComponent(1) instanceof VerticalLayout);
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
        assertTrue(metadataLayout.getComponent(2) instanceof VerticalLayout);
        VerticalLayout serviceFeeTotalLayout = (VerticalLayout) metadataLayout.getComponent(2);
        assertEquals("scenario-detailed-amount", serviceFeeTotalLayout.getId());
        assertEquals("<b>Service Fee Amt in USD: </b><span class='label-amount'>4,800.00</span>",
            serviceFeeTotalLayout.getCaption());
        assertEquals(2, serviceFeeTotalLayout.getComponentCount());
        verifyLabel(serviceFeeTotalLayout.getComponent(0),
            "<b>Service Fee Amt in USD by Print: </b><span class='label-amount'>1,600.00</span>");
        verifyLabel(serviceFeeTotalLayout.getComponent(1),
            "<b>Service Fee Amt in USD by Digital: </b><span class='label-amount'>3,200.00</span>");
        assertTrue(metadataLayout.getComponent(3) instanceof VerticalLayout);
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
        assertTrue(metadataLayout.getComponent(10) instanceof VerticalLayout);
        VerticalLayout lastActionLayout = (VerticalLayout) metadataLayout.getComponent(10);
        assertEquals("scenario-last-action", lastActionLayout.getId());
        assertEquals(5, lastActionLayout.getComponentCount());
        verifyLabel(lastActionLayout.getComponent(0), "<b>Type:</b> ADDED_USAGES");
        verifyLabel(lastActionLayout.getComponent(1), "<b>User:</b> user@copyright.com");
        verifyLabel(lastActionLayout.getComponent(2), "<b>Date:</b> 07/01/2022 12:00 AM");
        verifyLabel(lastActionLayout.getComponent(3), "<b>Reason:</b> some reason");
        UiTestHelper.verifyButton(lastActionLayout.getComponent(4), "View All Actions", true);
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
