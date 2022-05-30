package com.copyright.rup.dist.foreign.ui.scenario.impl;


import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.UiTestHelper;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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

    private ScenarioHistoryWidget widget;

    @Before
    public void setUp() {
        widget = new ScenarioHistoryWidget();
        widget.setController(createMock(ScenarioHistoryController.class));
        widget.init();
    }

    @Test
    public void testStructure() {
        assertEquals("scenario-history-widget", widget.getId());
        verifyWindow(widget, StringUtils.EMPTY, 700, 350, Unit.PIXELS);
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(new MarginInfo(true, true, true, true), content.getMargin());
        assertEquals(2, content.getComponentCount());
        Grid grid = (Grid) content.getComponent(0);
        UiTestHelper.verifyGrid(grid, Arrays.asList(
            Triple.of("Type", -1.0, -1),
            Triple.of("User", -1.0, -1),
            Triple.of("Date", -1.0, -1),
            Triple.of("Reason", -1.0, -1)
        ));
        verifyButton(content.getComponent(1));
    }

    private void verifyButton(Component component) {
        assertTrue(component instanceof Button);
        Button closeButton = (Button) component;
        assertEquals("Close", closeButton.getCaption());
        assertEquals("Close", closeButton.getId());
        assertTrue(closeButton.isEnabled());
    }
}
