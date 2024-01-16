package com.copyright.rup.dist.foreign.vui.scenario.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifySize;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.scenario.api.IScenarioHistoryController;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Verifies {@link ScenarioHistoryWidget}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 12/18/2017
 *
 * @author Uladzislau Shalamitski
 */
public class ScenarioHistoryWidgetTest {

    private IScenarioHistoryController controller;
    private ScenarioHistoryWidget widget;

    @Before
    public void setUp() {
        controller = createMock(ScenarioHistoryController.class);
        widget = new ScenarioHistoryWidget();
        widget.setController(controller);
        widget.init();
    }

    @Test
    public void testStructure() {
        verifyWindow(widget, StringUtils.EMPTY, "1000px", "400px", Unit.PIXELS, true);
        assertEquals("scenario-history-widget", widget.getClassName());
        verifyMainLayout(widget.getChildren().findAny().get());
    }

    @Test
    public void testPopulateHistory() {
        var scenario = new Scenario();
        scenario.setId("fd275db6-603e-48f7-95af-4b22fcd0e097");
        scenario.setName("Scenario name");
        expect(controller.getActions(scenario.getId())).andReturn(List.of(new ScenarioAuditItem())).once();
        replay(controller);
        widget.populateHistory(scenario);
        Grid grid = (Grid) ((VerticalLayout) widget.getChildren().findAny().get()).getComponentAt(0);
        assertFalse(((ListDataProvider<Object>) grid.getDataProvider()).getItems().isEmpty());
        assertEquals("History for Scenario name scenario", widget.getHeaderTitle());
        verify(controller);
    }

    private void verifyMainLayout(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout layout = (VerticalLayout) component;
        assertEquals(1, layout.getComponentCount());
        verifySize(layout, "100%", Unit.PERCENTAGE, "100%", Unit.PERCENTAGE);
        verifyGrid(layout.getComponentAt(0));
        verifyButton(getFooterComponent(widget, 1), "Close", true);
    }

    @SuppressWarnings("unchecked")
    private void verifyGrid(Component component) {
        assertNotNull(component);
        assertThat(component, instanceOf(Grid.class));
        Grid<ScenarioAuditItem> grid = (Grid<ScenarioAuditItem>) component;
        UiTestHelper.verifyGrid(grid, List.of(
            Pair.of("Type", null),
            Pair.of("User", null),
            Pair.of("Date", null),
            Pair.of("Reason", null)
        ));
        assertEquals("scenario-history-grid", grid.getClassName());
    }
}
