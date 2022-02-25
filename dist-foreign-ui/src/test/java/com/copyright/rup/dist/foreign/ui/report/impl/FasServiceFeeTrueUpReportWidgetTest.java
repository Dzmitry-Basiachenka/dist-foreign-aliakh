package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;

/**
 * Verifies {@link FasServiceFeeTrueUpReportWidget}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class FasServiceFeeTrueUpReportWidgetTest {

    private FasServiceFeeTrueUpReportWidget widget;

    @Before
    public void setUp() {
        FasServiceFeeTrueUpReportController controller = new FasServiceFeeTrueUpReportController();
        widget = (FasServiceFeeTrueUpReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY,  350, -1, Unit.PIXELS);
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
        verifyButtonsLayout(fourthComponent, "Export", "Close");
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
        LocalDate toDate = LocalDate.now();
        expect(toDateWidget.getValue()).andReturn(toDate).once();
        replay(toDateWidget);
        assertEquals(toDate, widget.getToDate());
        verify(toDateWidget);
    }

    @Test
    public void testGetPaymentDateTo() {
        LocalDateWidget paymentDateToWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "paymentDateToWidget", paymentDateToWidget);
        LocalDate paymentDateTo = LocalDate.now();
        expect(paymentDateToWidget.getValue()).andReturn(paymentDateTo).once();
        replay(paymentDateToWidget);
        assertEquals(paymentDateTo, widget.getPaymentDateTo());
        verify(paymentDateToWidget);
    }
}
