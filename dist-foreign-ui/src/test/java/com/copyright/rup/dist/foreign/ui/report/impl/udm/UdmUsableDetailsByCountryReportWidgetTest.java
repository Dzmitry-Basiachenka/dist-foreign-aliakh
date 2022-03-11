package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
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
 * Verifies {@link UdmUsableDetailsByCountryReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/10/2022
 *
 * @author Mikita Maistrenka
 */
public class UdmUsableDetailsByCountryReportWidgetTest {

    private UdmUsableDetailsByCountryReportWidget widget;
    private Binder<LocalDate> dateBinder;

    @Before
    public void setUp() {
        UdmUsableDetailsByCountryReportController controller = new UdmUsableDetailsByCountryReportController();
        widget = (UdmUsableDetailsByCountryReportWidget) controller.initWidget();
        dateBinder = Whitebox.getInternalState(widget, "dateBinder");
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 330, -1, Sizeable.Unit.PIXELS);
        assertEquals("udm-report-window", widget.getStyleName());
        assertEquals("udm-report-window", widget.getId());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(4, content.getComponentCount());
        verifyItemsFilterWidget(content.getComponent(0), "Periods");
        Component dateFrom = content.getComponent(1);
        assertEquals(LocalDateWidget.class, dateFrom.getClass());
        assertEquals("Loaded Date From", dateFrom.getCaption());
        Component dateTo = content.getComponent(2);
        assertEquals(LocalDateWidget.class, dateTo.getClass());
        assertEquals("Loaded Date To", dateTo.getCaption());
        verifyButtonsLayout(content.getComponent(3), "Export", "Close");
    }

    @Test
    public void testGetReportFilter() {
        Set<Integer> periods = Collections.singleton(202212);
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
        assertEquals(periods, reportFilter.getPeriods());
        assertEquals(dateFrom, reportFilter.getDateFrom());
        assertEquals(dateTo, reportFilter.getDateTo());
        verify(dateFromWidget, dateToWidget);
    }

    @Test
    public void testDateValidation() {
        LocalDateWidget dateFromWidget = Whitebox.getInternalState(widget, "dateFromWidget");
        LocalDateWidget dateToWidget = Whitebox.getInternalState(widget, "dateToWidget");
        LocalDate localDateFrom = LocalDate.of(2022, 3, 9);
        LocalDate localDateTo = LocalDate.of(2022, 3, 10);
        verifyDateWidgetValidationMessage(dateFromWidget, localDateFrom, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(dateToWidget, localDateTo, StringUtils.EMPTY, true);
        dateFromWidget.setValue(LocalDate.of(2022, 3, 11));
        verifyDateWidgetValidationMessage(dateToWidget, localDateTo,
            "Field value should be greater or equal to Loaded Date From", false);
        verifyDateWidgetValidationMessage(dateFromWidget, null, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(dateToWidget, null, StringUtils.EMPTY, true);
    }

    private void verifyDateWidgetValidationMessage(LocalDateWidget localDateWidget, LocalDate value, String message,
                                                   boolean isValid) {
        localDateWidget.setValue(value);
        List<ValidationResult> errors = dateBinder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
