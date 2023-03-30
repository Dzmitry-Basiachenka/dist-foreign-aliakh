package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Verifies {@link AclComparisonByAggLcClassAndTitleReportWidget}.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Mikita Maistrenka
 */
public class AclComparisonByAggLcClassAndTitleReportWidgetTest {

    private final AclComparisonByAggLcClassAndTitleReportWidget widget =
        new AclComparisonByAggLcClassAndTitleReportWidget();
    private final IAclCommonReportController controller = createMock(IAclCommonReportController.class);
    private final IStreamSource streamSource = createMock(IStreamSource.class);

    private ComboBox<Integer> previousPeriodComboBox;
    private ComboBox<Integer> currentPeriodComboBox;
    private AclScenarioFilterWidget previousScenariosFilterWidget;
    private AclScenarioFilterWidget currentScenariosFilterWidget;
    private Button exportButton;
    private Button closeButton;

    @Before
    public void setUp() {
        widget.setController(controller);
        expect(controller.getPeriods()).andReturn(List.of(202012, 202112)).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(streamSource.getSource()).andReturn(new SimpleImmutableEntry(createMock(Supplier.class),
            createMock(Supplier.class))).once();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInit() {
        replay(controller, streamSource);
        widget.init();
        verifyWindow(widget, StringUtils.EMPTY, 410, 145, Sizeable.Unit.PIXELS);
        assertEquals("acl-report-window", widget.getStyleName());
        assertEquals("acl-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        HorizontalLayout periodComboBoxes = (HorizontalLayout) content.getComponent(0);
        previousPeriodComboBox = (ComboBox<Integer>) periodComboBoxes.getComponent(0);
        currentPeriodComboBox = (ComboBox<Integer>) periodComboBoxes.getComponent(1);
        HorizontalLayout scenariosFilterWidgets = (HorizontalLayout) content.getComponent(1);
        previousScenariosFilterWidget = (AclScenarioFilterWidget) scenariosFilterWidgets.getComponent(0);
        currentScenariosFilterWidget = (AclScenarioFilterWidget) scenariosFilterWidgets.getComponent(1);
        HorizontalLayout buttons = (HorizontalLayout) content.getComponent(2);
        exportButton = (Button) buttons.getComponent(0);
        closeButton = (Button) buttons.getComponent(1);
        verifyComboBox(previousPeriodComboBox, "Previous Period", true, 202012, 202112);
        verifyComboBox(currentPeriodComboBox, "Current Period", true, 202012, 202112);
        verifyAvailabilityReportWidgetItems(false, false, false);
        previousPeriodComboBox.setSelectedItem(202012);
        verifyAvailabilityReportWidgetItems(true, false, false);
        currentPeriodComboBox.setSelectedItem(202112);
        verifyAvailabilityReportWidgetItems(true, true,false);
        verifyItemsFilterWidget(previousScenariosFilterWidget, "Previous Scenarios");
        verifyItemsFilterWidget(currentScenariosFilterWidget, "Current Scenarios");
        verifyButtonsLayout(buttons, "Export", "Close");
        verify(controller, streamSource);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetReportInfo() {
        AclScenarioFilterWidget previousScenarioFilterWidget = createMock(AclScenarioFilterWidget.class);
        AclScenarioFilterWidget currentScenarioFilterWidget = createMock(AclScenarioFilterWidget.class);
        Set<AclScenario> previousScenarios = Set.of(buildAclScenario("Previous"));
        Set<AclScenario> currentScenarios = Set.of(buildAclScenario("Current"));
        expect(previousScenarioFilterWidget.getSelectedItems()).andReturn(previousScenarios).once();
        expect(currentScenarioFilterWidget.getSelectedItems()).andReturn(currentScenarios).once();
        replay(controller, previousScenarioFilterWidget, currentScenarioFilterWidget, streamSource);
        widget.init();
        VerticalLayout verticalLayout = (VerticalLayout) widget.getContent();
        HorizontalLayout periodComboBoxes = (HorizontalLayout) verticalLayout.getComponent(0);
        ComboBox<Integer> previousPeriodComboBoxFilter = (ComboBox<Integer>) periodComboBoxes.getComponent(0);
        ComboBox<Integer> currentPeriodComboBoxFilter = (ComboBox<Integer>) periodComboBoxes.getComponent(1);
        previousPeriodComboBoxFilter.setSelectedItem(202012);
        currentPeriodComboBoxFilter.setSelectedItem(202112);
        Whitebox.setInternalState(widget, "previousScenarioFilterWidget", previousScenarioFilterWidget);
        Whitebox.setInternalState(widget, "currentScenarioFilterWidget", currentScenarioFilterWidget);
        AclCalculationReportsInfoDto reportInfo = widget.getReportInfo();
        assertEquals("Previous", reportInfo.getPreviousScenarios().get(0).getName());
        assertEquals("Current", reportInfo.getScenarios().get(0).getName());
        verify(controller, previousScenarioFilterWidget, currentScenarioFilterWidget, streamSource);
    }

    private void verifyAvailabilityReportWidgetItems(boolean previousScenarios, boolean currentScenarios,
                                                     boolean export) {
        assertTrue(previousPeriodComboBox.isEnabled());
        assertTrue(currentPeriodComboBox.isEnabled());
        assertEquals(previousScenarios, previousScenariosFilterWidget.isEnabled());
        assertEquals(currentScenarios, currentScenariosFilterWidget.isEnabled());
        assertEquals(export, this.exportButton.isEnabled());
        assertTrue(this.closeButton.isEnabled());
    }

    private AclScenario buildAclScenario(String name) {
        AclScenario scenario = new AclScenario();
        scenario.setName(name);
        return scenario;
    }
}
