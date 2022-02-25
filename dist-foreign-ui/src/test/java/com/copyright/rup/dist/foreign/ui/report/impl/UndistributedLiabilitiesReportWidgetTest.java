package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
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
        verifyWindow(widget, StringUtils.EMPTY, 350, -1, Unit.PIXELS);
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertEquals(LocalDateWidget.class, firstComponent.getClass());
        assertEquals("Payment Date To", firstComponent.getCaption());
        Component secondComponent = content.getComponent(1);
        verifyButtonsLayout(secondComponent, "Export", "Close");
        assertEquals("report-undistributed-liabilities-window", widget.getStyleName());
        assertEquals("report-undistributed-liabilities-window", widget.getId());
    }
}
