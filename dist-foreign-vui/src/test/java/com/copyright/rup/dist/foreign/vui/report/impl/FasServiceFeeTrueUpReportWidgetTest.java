package com.copyright.rup.dist.foreign.vui.report.impl;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.vui.UiTestHelper;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

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
        verifyWindow(widget, StringUtils.EMPTY, "400px", null, Unit.PIXELS, false);
        assertThat(UiTestHelper.getDialogContent(widget), instanceOf(VerticalLayout.class));
        var content = (VerticalLayout) UiTestHelper.getDialogContent(widget);
        assertEquals(3, content.getComponentCount());
        var firstComponent = content.getComponentAt(0);
        assertThat(firstComponent, instanceOf(LocalDateWidget.class));
        assertEquals("From Date", ((LocalDateWidget) firstComponent).getLabel());
        var secondComponent = content.getComponentAt(1);
        assertThat(secondComponent, instanceOf(LocalDateWidget.class));
        assertEquals("To Date", ((LocalDateWidget) secondComponent).getLabel());
        var thirdComponent = content.getComponentAt(2);
        assertThat(thirdComponent, instanceOf(LocalDateWidget.class));
        assertEquals("Payment Date To", ((LocalDateWidget) thirdComponent).getLabel());
        verifyButtonsLayout(UiTestHelper.getFooterComponent(widget, 1));
        assertEquals("report-service-fee-true-up-window", widget.getClassName());
        assertEquals("report-service-fee-true-up-window", widget.getId().orElseThrow());
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

    public void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        var fileDownloader = layout.getComponentAt(0);
        assertThat(fileDownloader, instanceOf(OnDemandFileDownloader.class));
        assertEquals("Export", ((Button) fileDownloader.getChildren().findFirst().get()).getText());
        var closeButton = layout.getComponentAt(1);
        assertThat(closeButton, instanceOf(Button.class));
        assertEquals("Close", ((Button) closeButton).getText());
    }
}
