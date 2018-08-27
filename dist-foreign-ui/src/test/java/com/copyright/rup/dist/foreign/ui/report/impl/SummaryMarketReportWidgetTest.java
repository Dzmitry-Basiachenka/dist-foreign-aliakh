package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

/**
 * Verifies {@link SummaryMarketReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportWidgetTest {

    private SummaryMarketReportWidget widget;

    @Before
    public void setUp() {
        SummaryMarketReportController controller = new SummaryMarketReportController();
        widget = (SummaryMarketReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        assertEquals(350, widget.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertEquals(UsageBatchFilterWidget.class, firstComponent.getClass());
        assertTrue(firstComponent instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) firstComponent;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals("Batches", button.getCaption());
        assertEquals(2, button.getListeners(Button.ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
        Component secondComponent = content.getComponent(1);
        assertEquals(HorizontalLayout.class, secondComponent.getClass());
        HorizontalLayout buttonsLayout = (HorizontalLayout) secondComponent;
        assertEquals(2, buttonsLayout.getComponentCount());
        Component firstButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, firstButton.getClass());
        assertEquals("Export", firstButton.getCaption());
        assertFalse(firstButton.isEnabled());
        Component secondButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, secondButton.getClass());
        assertEquals("Close", secondButton.getCaption());
        assertEquals("summary-market-report-window", widget.getStyleName());
        assertEquals("summary-market-report-window", widget.getId());
    }
}
