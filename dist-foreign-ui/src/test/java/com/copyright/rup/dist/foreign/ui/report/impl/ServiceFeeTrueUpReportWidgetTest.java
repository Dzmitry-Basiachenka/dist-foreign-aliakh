package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;

/**
 * Verifies {@link ServiceFeeTrueUpReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ServiceFeeTrueUpReportWidgetTest {

    private ServiceFeeTrueUpReportWidget widget;

    @Before
    public void setUp() {
        ServiceFeeTrueUpReportController controller = new ServiceFeeTrueUpReportController();
        widget = (ServiceFeeTrueUpReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        assertEquals(350, widget.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(4, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertEquals(LocalDateWidget.class, firstComponent.getClass());
        assertEquals("From Date", firstComponent.getCaption());
        Component secondComponent = content.getComponent(1);
        assertEquals(LocalDateWidget.class, secondComponent.getClass());
        assertEquals("To Date", secondComponent.getCaption());
        Component thirdComponent = content.getComponent(2);
        assertEquals(LocalDateWidget.class, thirdComponent.getClass());
        assertEquals("Payment Date To", thirdComponent.getCaption());
        Component fourthComponent = content.getComponent(3);
        assertEquals(HorizontalLayout.class, fourthComponent.getClass());
        HorizontalLayout buttonsLayout = (HorizontalLayout) fourthComponent;
        assertEquals(2, buttonsLayout.getComponentCount());
        Component firstButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, firstButton.getClass());
        assertEquals("Export", firstButton.getCaption());
        assertFalse(firstButton.isEnabled());
        Component secondButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, secondButton.getClass());
        assertEquals("Close", secondButton.getCaption());
        assertEquals("report-service-fee-true-up-window", widget.getStyleName());
        assertEquals("report-service-fee-true-up-window", widget.getId());
    }

    @Test
    public void testGetFromDate() {
        LocalDateWidget fromDateWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "fromDateWidget", fromDateWidget);
        LocalDate fromDate = LocalDate.now();
        expect(fromDateWidget.getValue()).andReturn(fromDate).once();
        replay(fromDateWidget);
        assertEquals(fromDate, widget.getFromDate());
        verify(fromDateWidget);
    }

    @Test
    public void testGetToDate() {
        LocalDateWidget toDateWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "toDateWidget", toDateWidget);
        LocalDate fromDate = LocalDate.now();
        expect(toDateWidget.getValue()).andReturn(fromDate).once();
        replay(toDateWidget);
        assertEquals(fromDate, widget.getToDate());
        verify(toDateWidget);
    }

    @Test
    public void testGetPaymentDateTo() {
        LocalDateWidget paymentDateToWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "paymentDateToWidget", paymentDateToWidget);
        LocalDate fromDate = LocalDate.now();
        expect(paymentDateToWidget.getValue()).andReturn(fromDate).once();
        replay(paymentDateToWidget);
        assertEquals(fromDate, widget.getPaymentDateTo());
        verify(paymentDateToWidget);
    }
}
