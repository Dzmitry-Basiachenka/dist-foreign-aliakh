package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyWindow;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
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
        assertEquals(4, content.getComponentCount());
        // TODO add tests for Channels, Usage Origins widgets
        Component component1 = content.getComponent(0);
        assertEquals(PeriodFilterWidget.class, component1.getClass());
        verifyItemsFilterWidget(content.getComponent(0), "Periods");
        Component component2 = content.getComponent(1);
        assertEquals(LocalDateWidget.class, component2.getClass());
        assertEquals("Date Received From", component2.getCaption());
        Component component3 = content.getComponent(2);
        assertEquals(LocalDateWidget.class, component3.getClass());
        assertEquals("Date Received To", component3.getCaption());
        verifyButtonsLayout(content.getComponent(3), "Export", "Close");
    }

    // TODO add tests for the getChannels, getUsageOrigin methods
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

    private void verifyItemsFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(Button.ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
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
