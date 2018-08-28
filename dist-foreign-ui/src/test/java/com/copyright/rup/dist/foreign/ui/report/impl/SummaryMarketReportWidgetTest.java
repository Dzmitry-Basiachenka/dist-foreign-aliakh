package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;

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
        IUsageBatchService usageBatchService = createMock(IUsageBatchService.class);
        Whitebox.setInternalState(controller, usageBatchService);
        expect(usageBatchService.getUsageBatches()).andReturn(Collections.emptyList()).once();
        replay(usageBatchService);
        widget = (SummaryMarketReportWidget) controller.initWidget();
        verify(usageBatchService);
    }

    @Test
    public void testInit() {
        assertEquals(350, widget.getWidth(), 0);
        assertEquals(400, widget.getHeight(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(3, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertTrue(firstComponent instanceof SearchWidget);
        Component secondComponent = content.getComponent(1);
        assertTrue(secondComponent instanceof Panel);
        Panel panel = (Panel) secondComponent;
        assertTrue(panel.getContent() instanceof CheckBoxGroup);
        verifyButtonsLayout(content.getComponent(2));
    }

    private void verifyButtonsLayout(Component component) {
        assertEquals(HorizontalLayout.class, component.getClass());
        HorizontalLayout buttonsLayout = (HorizontalLayout) component;
        assertEquals(3, buttonsLayout.getComponentCount());
        Component firstButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, firstButton.getClass());
        assertEquals("Export", firstButton.getCaption());
        assertFalse(firstButton.isEnabled());
        Component secondButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, secondButton.getClass());
        assertEquals("Clear", secondButton.getCaption());
        Component thirdButton = buttonsLayout.getComponent(2);
        assertEquals(Button.class, thirdButton.getClass());
        assertEquals("Close", thirdButton.getCaption());
        assertEquals("summary-market-report-window", widget.getStyleName());
        assertEquals("summary-market-report-window", widget.getId());
    }
}
