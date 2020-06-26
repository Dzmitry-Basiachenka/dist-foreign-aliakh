package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.reporting.api.IStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.report.api.ITaxNotificationReportController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Verifies {@link TaxNotificationReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 6/25/20
 *
 * @author Stanislau Rudak
 */
public class TaxNotificationReportWidgetTest {

    private static final String SCENARIO_ID_1 = "32894a7f-b218-4347-b8f9-8236f75c9b2a";
    private static final String SCENARIO_ID_2 = "69a22caf-57da-45fa-afbb-35cf96ffd0ba";
    private static final Scenario SCENARIO_1 = buildScenario(SCENARIO_ID_1, "Scenario One");
    private static final Scenario SCENARIO_2 = buildScenario(SCENARIO_ID_2, "Scenario Two");

    private TaxNotificationReportWidget widget;

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }

    @Before
    public void setUp() {
        ITaxNotificationReportController controller = createMock(ITaxNotificationReportController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenarios()).andReturn(Arrays.asList(SCENARIO_1, SCENARIO_2)).once();
        replay(controller, streamSource);
        widget = new TaxNotificationReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, streamSource);
    }

    @Test
    public void testInit() {
        assertEquals(600, widget.getWidth(), 0);
        assertEquals(400, widget.getHeight(), 0);
        assertEquals("tax-notification-report-window", widget.getStyleName());
        assertEquals("tax-notification-report-window", widget.getId());
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(4, content.getComponentCount());
        Component searchWidget = content.getComponent(0);
        assertTrue(searchWidget instanceof SearchWidget);
        Component grid = content.getComponent(1);
        assertTrue(grid instanceof Grid);
        Component numberOfDaysField = content.getComponent(2);
        assertTrue(numberOfDaysField instanceof TextField);
        Component buttonsLayout = content.getComponent(3);
        assertTrue(buttonsLayout instanceof HorizontalLayout);
        verifyGrid((Grid) grid);
        verifyNumberOfDaysField((TextField) numberOfDaysField);
        verifyButtonsLayout((HorizontalLayout) buttonsLayout);
    }

    @Test
    public void testExportButtonState() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        TextField numberOfDaysField = (TextField) content.getComponent(2);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(3);
        Button exportButton = (Button) buttonsLayout.getComponent(0);
        grid.select(SCENARIO_1);
        numberOfDaysField.setValue("asdf");
        assertFalse(exportButton.isEnabled());
        numberOfDaysField.setValue("-1");
        assertFalse(exportButton.isEnabled());
        numberOfDaysField.setValue("10");
        assertTrue(exportButton.isEnabled());
        grid.select(SCENARIO_2);
        assertTrue(exportButton.isEnabled());
        grid.deselectAll();
        assertFalse(exportButton.isEnabled());
    }

    @Test
    public void testGetSelectedScenarioIds() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        grid.select(SCENARIO_1);
        assertEquals(Collections.singleton(SCENARIO_ID_1), widget.getSelectedScenarioIds());
        grid.select(SCENARIO_2);
        assertEquals(Sets.newHashSet(SCENARIO_ID_1, SCENARIO_ID_2), widget.getSelectedScenarioIds());
        grid.deselectAll();
        assertEquals(Collections.emptySet(), widget.getSelectedScenarioIds());
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Collections.singletonList("Scenario Name"),
            columns.stream().map(Grid.Column::getCaption).collect(Collectors.toList()));
        assertEquals(Collections.singletonList(-1.0),
            columns.stream().map(Grid.Column::getWidth).collect(Collectors.toList()));
    }

    private void verifyNumberOfDaysField(TextField field) {
        assertEquals("Number of days since last notification", field.getCaption());
        assertEquals("15", field.getValue());
    }

    private void verifyButtonsLayout(HorizontalLayout buttonsLayout) {
        assertEquals(2, buttonsLayout.getComponentCount());
        Component exportButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, exportButton.getClass());
        assertEquals("Export", exportButton.getCaption());
        assertFalse(exportButton.isEnabled());
        Component closeButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, closeButton.getClass());
        assertEquals("Close", closeButton.getCaption());
    }
}
