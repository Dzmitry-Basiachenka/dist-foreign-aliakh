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
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenariosReportController;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.google.common.collect.Sets;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
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
 * Verifies {@link CommonScenariosReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/14/20
 *
 * @author Uladzislau Shalamitski
 */
@SuppressWarnings("unchecked")
public class CommonScenariosReportWidgetTest {

    private static final String SCENARIO_ID_1 = "32894a7f-b218-4347-b8f9-8236f75c9b2a";
    private static final String SCENARIO_ID_2 = "69a22caf-57da-45fa-afbb-35cf96ffd0ba";
    private Scenario scenario1;
    private Scenario scenario2;

    private CommonScenariosReportWidget widget;

    @Before
    public void setUp() {
        scenario1 = buildScenario(SCENARIO_ID_1, "SAL Distribution 10/10/2020");
        scenario2 = buildScenario(SCENARIO_ID_2, "SAL Distribution 10/12/2020");
        ICommonScenariosReportController controller = createMock(ICommonScenariosReportController.class);
        IStreamSource streamSource = createMock(IStreamSource.class);
        expect(streamSource.getSource())
            .andReturn(new SimpleImmutableEntry(createMock(Supplier.class), createMock(Supplier.class))).once();
        expect(controller.getCsvStreamSource()).andReturn(streamSource).once();
        expect(controller.getScenarios()).andReturn(Arrays.asList(scenario1, scenario2)).once();
        replay(controller, streamSource);
        widget = new CommonScenariosReportWidget();
        widget.setController(controller);
        widget.init();
        verify(controller, streamSource);
    }

    @Test
    public void testComponentStructure() {
        assertEquals(500, widget.getWidth(), 0);
        assertEquals(400, widget.getHeight(), 0);
        assertEquals("common-scenarios-report-window", widget.getStyleName());
        assertEquals("common-scenarios-report-window", widget.getId());
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        Component searchWidget = content.getComponent(0);
        assertTrue(searchWidget instanceof SearchWidget);
        verifyGrid((Grid) content.getComponent(1));
        verifyButtonsLayout((HorizontalLayout) content.getComponent(2));
    }

    @Test
    public void testExportButtonState() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        HorizontalLayout buttonsLayout = (HorizontalLayout) content.getComponent(2);
        Button exportButton = (Button) buttonsLayout.getComponent(0);
        grid.select(scenario1);
        assertTrue(exportButton.isEnabled());
        grid.select(scenario2);
        assertTrue(exportButton.isEnabled());
        grid.deselectAll();
        assertFalse(exportButton.isEnabled());
    }

    @Test
    public void testGetSelectedScenarios() {
        VerticalLayout content = (VerticalLayout) widget.getContent();
        Grid<Scenario> grid = (Grid<Scenario>) content.getComponent(1);
        grid.select(scenario1);
        assertEquals(Collections.singleton(scenario1), widget.getSelectedScenarios());
        grid.select(scenario2);
        assertEquals(Sets.newHashSet(scenario1, scenario2), widget.getSelectedScenarios());
        grid.deselectAll();
        assertEquals(Collections.emptySet(), widget.getSelectedScenarios());
    }

    private void verifyGrid(Grid grid) {
        assertNull(grid.getCaption());
        List<Column> columns = grid.getColumns();
        assertEquals(Collections.singletonList("Scenario Name"),
            columns.stream().map(Column::getCaption).collect(Collectors.toList()));
        assertEquals(Collections.singletonList(-1.0),
            columns.stream().map(Column::getWidth).collect(Collectors.toList()));
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

    private static Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setId(id);
        scenario.setName(name);
        return scenario;
    }
}
