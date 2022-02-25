package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link AaclBaselineUsagesReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 5/19/2020
 *
 * @author Ihar Suvorau
 */
public class AaclBaselineUsagesReportWidgetTest {

    private AaclBaselineUsagesReportWidget widget;

    @Before
    public void setUp() {
        AaclBaselineUsagesReportController controller = new AaclBaselineUsagesReportController();
        widget = (AaclBaselineUsagesReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Unit.PIXELS);
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component firstComponent = content.getComponent(0);
        assertEquals(TextField.class, firstComponent.getClass());
        assertEquals("Number of Baseline Years", firstComponent.getCaption());
        Component secondComponent = content.getComponent(1);
        verifyButtonsLayout(secondComponent, "Export", "Close");
        assertEquals("baseline-usages-report-window", widget.getStyleName());
        assertEquals("baseline-usages-report-window", widget.getId());
    }

    @Test
    public void testNumberOfBaselineYearsValidation() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField numberOfBaselineYears = Whitebox.getInternalState(widget, "numberOfBaselineYearsField");
        Button exportButton = Whitebox.getInternalState(widget, "exportButton");
        verifyField(numberOfBaselineYears, "", binder, "Field value should be specified", false);
        assertFalse(exportButton.isEnabled());
        verifyField(numberOfBaselineYears, "two", binder, "Field value should be positive number", false);
        assertFalse(exportButton.isEnabled());
        verifyField(numberOfBaselineYears, "-1", binder, "Field value should be positive number", false);
        assertFalse(exportButton.isEnabled());
        verifyField(numberOfBaselineYears, " -2 ", binder, "Field value should be positive number", false);
        assertFalse(exportButton.isEnabled());
        verifyField(numberOfBaselineYears, " 1 ", binder, null, true);
        assertTrue(exportButton.isEnabled());
        verifyField(numberOfBaselineYears, "1", binder, null, true);
        assertTrue(exportButton.isEnabled());
    }

    private void verifyField(TextField field, String value, Binder binder, String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
