package com.copyright.rup.dist.foreign.ui.report.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link SalFundPoolsReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/04/20
 *
 * @author Anton Azarenka
 */
public class SalFundPoolsReportWidgetTest {

    private SalFundPoolsReportWidget widget;

    @Before
    public void setUp() {
        SalFundPoolsReportController controller = new SalFundPoolsReportController();
        widget = (SalFundPoolsReportWidget) controller.initWidget();
    }

    @Test
    public void testInit() {
        assertEquals(300, widget.getWidth(), 0);
        assertEquals(Sizeable.Unit.PIXELS, widget.getWidthUnits());
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component textFieldComponent = content.getComponent(0);
        assertEquals(TextField.class, textFieldComponent.getClass());
        assertEquals("Distribution Year (YYYY)", textFieldComponent.getCaption());
        Component layoutComponent = content.getComponent(1);
        assertEquals(HorizontalLayout.class, layoutComponent.getClass());
        HorizontalLayout buttonsLayout = (HorizontalLayout) layoutComponent;
        assertEquals(2, buttonsLayout.getComponentCount());
        Component exportButton = buttonsLayout.getComponent(0);
        assertEquals(Button.class, exportButton.getClass());
        assertEquals("Export", exportButton.getCaption());
        assertFalse(exportButton.isEnabled());
        Component closeButton = buttonsLayout.getComponent(1);
        assertEquals(Button.class, closeButton.getClass());
        assertEquals("Close", closeButton.getCaption());
        assertEquals("fund-pools-report-window", widget.getStyleName());
        assertEquals("fund-pools-report-window", widget.getId());
    }

    @Test
    public void testDistributionYearValidation() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField distributionYear = Whitebox.getInternalState(widget, "distributionYear");
        Button exportButton = Whitebox.getInternalState(widget, "exportButton");
        verifyField(distributionYear, "", binder, "Field value should be specified", false);
        assertFalse(exportButton.isEnabled());
        verifyField(distributionYear, "two", binder, "Field value should contain numeric values only",
            false);
        assertFalse(exportButton.isEnabled());
        verifyField(distributionYear, "2100", binder, "Field value should be in range from 1950 to 2099",
            false);
        assertFalse(exportButton.isEnabled());
        verifyField(distributionYear, " 1949 ", binder, "Field value should be in range from 1950 to 2099",
            false);
        assertFalse(exportButton.isEnabled());
        verifyField(distributionYear, " 2020 ", binder, null, true);
        assertTrue(exportButton.isEnabled());
        verifyField(distributionYear, "2045", binder, null, true);
        assertTrue(exportButton.isEnabled());
    }

    @Test
    public void testValidationMessageForConverter() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField distributionYear = Whitebox.getInternalState(widget, "distributionYear");
        Button exportButton = Whitebox.getInternalState(widget, "exportButton");
        binder.setValidatorsDisabled(true);
        verifyField(distributionYear, "invalidValue", binder, "Field value can not be converted", false);
        assertFalse(exportButton.isEnabled());
    }

    private void verifyField(TextField field, String value, Binder binder, String message, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }
}
