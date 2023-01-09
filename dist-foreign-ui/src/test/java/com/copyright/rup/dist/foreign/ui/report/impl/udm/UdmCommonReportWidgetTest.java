package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyGridEditableFieldErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Set;

/**
 * Verifies {@link UdmCommonReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmCommonReportWidgetTest {

    private UdmCommonReportWidget widget;
    private Binder<LocalDate> dateBinder;

    @Before
    public void setUp() {
        UdmWeeklySurveyReportController controller = new UdmWeeklySurveyReportController();
        widget = (UdmCommonReportWidget) controller.initWidget();
        dateBinder = Whitebox.getInternalState(widget, "dateBinder");
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Sizeable.Unit.PIXELS);
        assertEquals("udm-report-window", widget.getStyleName());
        assertEquals("udm-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(5, content.getComponentCount());
        Component periods = content.getComponent(0);
        assertEquals(PeriodFilterWidget.class, periods.getClass());
        verifyItemsFilterWidget(content.getComponent(0), "Periods");
        HorizontalLayout multiFiltersLayout = (HorizontalLayout) content.getComponent(1);
        verifyComboBox(multiFiltersLayout.getComponent(0), "Channel", true, UdmChannelEnum.values());
        verifyComboBox(multiFiltersLayout.getComponent(1), "Usage Origin", true, UdmUsageOriginEnum.values());
        Component dateFrom = content.getComponent(2);
        assertEquals(LocalDateWidget.class, dateFrom.getClass());
        assertEquals("Received Date From", dateFrom.getCaption());
        Component dateTo = content.getComponent(3);
        assertEquals(LocalDateWidget.class, dateTo.getClass());
        assertEquals("Received Date To", dateTo.getCaption());
        verifyButtonsLayout(content.getComponent(4), "Export", "Close");
    }

    @Test
    public void testGetReportFilter() {
        ComboBox<UdmChannelEnum> channelComboBox = Whitebox.getInternalState(widget, "channelComboBox");
        ComboBox<UdmUsageOriginEnum> usageOriginComboBox = Whitebox.getInternalState(widget, "usageOriginComboBox");
        channelComboBox.setSelectedItem(UdmChannelEnum.CCC);
        usageOriginComboBox.setSelectedItem(UdmUsageOriginEnum.RFA);
        Set<Integer> periods = Set.of(202112);
        Whitebox.setInternalState(widget, "periods", periods);
        LocalDateWidget dateFromWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "dateFromWidget", dateFromWidget);
        LocalDate dateFrom = LocalDate.now();
        expect(dateFromWidget.getValue()).andReturn(dateFrom).once();
        LocalDateWidget dateToWidget = createMock(LocalDateWidget.class);
        Whitebox.setInternalState(widget, "dateToWidget", dateToWidget);
        LocalDate dateTo = LocalDate.now();
        expect(dateToWidget.getValue()).andReturn(dateTo).once();
        replay(dateFromWidget, dateToWidget);
        UdmReportFilter reportFilter = widget.getReportFilter();
        assertEquals(UdmChannelEnum.CCC, reportFilter.getChannel());
        assertEquals(UdmUsageOriginEnum.RFA, reportFilter.getUsageOrigin());
        assertEquals(periods, reportFilter.getPeriods());
        assertEquals(dateFrom, reportFilter.getDateFrom());
        assertEquals(dateTo, reportFilter.getDateTo());
        verify(dateFromWidget, dateToWidget);
    }

    @Test
    public void testDateReceivedValidation() {
        LocalDateWidget dateFromWidget = Whitebox.getInternalState(widget, "dateFromWidget");
        LocalDateWidget dateToWidget = Whitebox.getInternalState(widget, "dateToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 12, 13);
        LocalDate localDateTo = LocalDate.of(2021, 12, 20);
        verifyGridEditableFieldErrorMessage(dateFromWidget, localDateFrom, dateBinder, null, true);
        verifyGridEditableFieldErrorMessage(dateToWidget, localDateTo, dateBinder, null, true);
        dateFromWidget.setValue(LocalDate.of(2021, 12, 27));
        verifyGridEditableFieldErrorMessage(dateToWidget, localDateTo, dateBinder,
            "Field value should be greater or equal to Received Date From", false);
        verifyGridEditableFieldErrorMessage(dateFromWidget, null, dateBinder, null, true);
        verifyGridEditableFieldErrorMessage(dateToWidget, null, dateBinder, null, true);
    }
}
