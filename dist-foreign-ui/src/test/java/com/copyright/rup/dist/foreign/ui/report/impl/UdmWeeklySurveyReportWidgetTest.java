package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Binder<LocalDate> dateReceivedBinder;

    @Before
    public void setUp() {
        UdmWeeklySurveyReportController controller = new UdmWeeklySurveyReportController();
        widget = (UdmWeeklySurveyReportWidget) controller.initWidget();
        dateReceivedBinder = Whitebox.getInternalState(widget, "dateReceivedBinder");
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Sizeable.Unit.PIXELS);
        assertEquals("report-udm-weekly-survey-window", widget.getStyleName());
        assertEquals("report-udm-weekly-survey-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(5, content.getComponentCount());
        Component periods = content.getComponent(0);
        assertEquals(PeriodFilterWidget.class, periods.getClass());
        verifyItemsFilterWidget(content.getComponent(0), "Periods");
        HorizontalLayout multiFiltersLayout = (HorizontalLayout) content.getComponent(1);
        verifyComboBox(multiFiltersLayout.getComponent(0), "Channel", true, UdmChannelEnum.values());
        verifyComboBox(multiFiltersLayout.getComponent(1), "Usage Origin", true,  UdmUsageOriginEnum.values());
        Component dateReceivedFrom = content.getComponent(2);
        assertEquals(LocalDateWidget.class, dateReceivedFrom.getClass());
        assertEquals("Date Received From", dateReceivedFrom.getCaption());
        Component dateReceivedTo = content.getComponent(3);
        assertEquals(LocalDateWidget.class, dateReceivedTo.getClass());
        assertEquals("Date Received To", dateReceivedTo.getCaption());
        verifyButtonsLayout(content.getComponent(4), "Export", "Close");
    }

    @Test
    public void testGetChannel() {
        ComboBox<UdmChannelEnum> channelComboBox = Whitebox.getInternalState(widget, "channelComboBox");
        channelComboBox.setSelectedItem(UdmChannelEnum.CCC);
        assertEquals("CCC", widget.getChannel());
    }

    @Test
    public void testGetUsageOrigin() {
        ComboBox<UdmUsageOriginEnum> usageOriginComboBox = Whitebox.getInternalState(widget, "usageOriginComboBox");
        usageOriginComboBox.setSelectedItem(UdmUsageOriginEnum.RFA);
        assertEquals("RFA", widget.getUsageOrigin());
    }

    @Test
    public void testGetPeriods() {
        Set<Integer> periods = Collections.singleton(2021012);
        Whitebox.setInternalState(widget, "periods", periods);
        assertEquals(periods, widget.getPeriods());
    }

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

    @Test
    public void testDateReceivedValidation() {
        LocalDateWidget dateReceivedFromWidget = Whitebox.getInternalState(widget, "dateReceivedFromWidget");
        LocalDateWidget dateReceivedToWidget = Whitebox.getInternalState(widget, "dateReceivedToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 12, 13);
        LocalDate localDateTo = LocalDate.of(2021, 12, 20);
        verifyDateWidgetValidationMessage(dateReceivedFromWidget, localDateFrom, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(dateReceivedToWidget, localDateTo, StringUtils.EMPTY, true);
        dateReceivedFromWidget.setValue(LocalDate.of(2021, 12, 27));
        verifyDateWidgetValidationMessage(dateReceivedToWidget, localDateTo,
            "Field value should be greater or equal to Date Received From", false);
        verifyDateWidgetValidationMessage(dateReceivedFromWidget, null, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(dateReceivedToWidget, null, StringUtils.EMPTY, true);
    }

    private void verifyDateWidgetValidationMessage(LocalDateWidget localDateWidget, LocalDate value, String message,
                                                   boolean isValid) {
        localDateWidget.setValue(value);
        List<ValidationResult> errors = dateReceivedBinder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
