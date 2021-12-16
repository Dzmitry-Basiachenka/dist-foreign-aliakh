package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.vaadin.widget.LocalDateWidget;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;

/**
 * Verifies {@link UdmWeeklySurveyReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmWeeklySurveyReportWidgetTest {

    private UdmWeeklySurveyReportWidget widget;

    @Before
    public void setUp() {
        UdmWeeklySurveyReportController controller = new UdmWeeklySurveyReportController();
        widget = (UdmWeeklySurveyReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Sizeable.Unit.PIXELS);
        assertEquals("report-udm-weekly-survey-window", widget.getStyleName());
        assertEquals("report-udm-weekly-survey-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        // TODO add tests for Channels, Usage Origins, Periods widgets
        Component component1 = content.getComponent(0);
        assertEquals(LocalDateWidget.class, component1.getClass());
        assertEquals("Date Received From", component1.getCaption());
        Component component2 = content.getComponent(1);
        assertEquals(LocalDateWidget.class, component2.getClass());
        assertEquals("Date Received To", component2.getCaption());
        verifyButtonsLayout(content.getComponent(2), "Export", "Close");
    }

    // TODO add tests for the getChannels, getUsageOrigin, getPeriods methods

    @Test
    public void testGetDateReceivedFrom() {
        LocalDateWidget dateReceivedFromWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "dateReceivedFromWidget", dateReceivedFromWidget);
        LocalDate dateReceivedFrom = LocalDate.now();
        expect(dateReceivedFromWidget.getValue()).andReturn(dateReceivedFrom).once();
        replay(dateReceivedFromWidget);
        assertEquals(dateReceivedFrom, widget.getDateReceivedFrom());
        verify(dateReceivedFromWidget);
    }

    @Test
    public void testGetDateReceivedTo() {
        LocalDateWidget dateReceivedToWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "dateReceivedToWidget", dateReceivedToWidget);
        LocalDate dateReceivedTo = LocalDate.now();
        expect(dateReceivedToWidget.getValue()).andReturn(dateReceivedTo).once();
        replay(dateReceivedToWidget);
        assertEquals(dateReceivedTo, widget.getDateReceivedTo());
        verify(dateReceivedToWidget);
    }
}
