package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link UndistributedLiabilitiesReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportWidgetTest {

    private UndistributedLiabilitiesReportWidget widget;

    @Before
    public void setUp() {
        UndistributedLiabilitiesReportController controller = new UndistributedLiabilitiesReportController();
        widget = (UndistributedLiabilitiesReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        assertEquals(350, widget.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertEquals(LocalDateWidget.class, firstComponent.getClass());
        assertEquals("Payment Date To", firstComponent.getCaption());
        Component secondComponent = content.getComponent(1);
        assertEquals(HorizontalLayout.class, secondComponent.getClass());
        HorizontalLayout buttonsLayout = (HorizontalLayout) secondComponent;
        assertEquals(2, buttonsLayout.getComponentCount());
        Component firstButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, firstButton.getClass());
        assertEquals("Export", firstButton.getCaption());
        Component secondButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, secondButton.getClass());
        assertEquals("Close", secondButton.getCaption());
        assertEquals("report-undistributed-liabilities-window", widget.getStyleName());
        assertEquals("report-undistributed-liabilities-window", widget.getId());
    }
}
