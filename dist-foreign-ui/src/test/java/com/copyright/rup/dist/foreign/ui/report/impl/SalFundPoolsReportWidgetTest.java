package com.copyright.rup.dist.foreign.ui.report.impl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
        verifyWindow(widget, StringUtils.EMPTY, 300, -1, Unit.PIXELS);
        assertEquals(VerticalLayout.class, widget.getContent().getClass());
        VerticalLayout content = (VerticalLayout) widget.getContent();
        assertEquals(2, content.getComponentCount());
        Component textFieldComponent = content.getComponent(0);
        assertEquals(TextField.class, textFieldComponent.getClass());
        assertEquals("Distribution Year (YYYY)", textFieldComponent.getCaption());
        verifyButtonsLayout(content.getComponent(1), "Export", "Close");
        assertEquals("fund-pools-report-window", widget.getStyleName());
        assertEquals("fund-pools-report-window", widget.getId());
    }

    @Test
    public void testDistributionYearValidation() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField distributionYear = Whitebox.getInternalState(widget, "distributionYear");
        Button exportButton = Whitebox.getInternalState(widget, "exportButton");
        validateFieldAndVerifyErrorMessage(distributionYear, "", binder, "Field value should be specified", false);
        assertFalse(exportButton.isEnabled());
        validateFieldAndVerifyErrorMessage(
            distributionYear, "two", binder, "Field value should contain numeric values only", false);
        assertFalse(exportButton.isEnabled());
        validateFieldAndVerifyErrorMessage(
            distributionYear, "2100", binder, "Field value should be in range from 1950 to 2099", false);
        assertFalse(exportButton.isEnabled());
        validateFieldAndVerifyErrorMessage(
            distributionYear, " 1949 ", binder, "Field value should be in range from 1950 to 2099", false);
        assertFalse(exportButton.isEnabled());
        validateFieldAndVerifyErrorMessage(distributionYear, " 2020 ", binder, null, true);
        assertTrue(exportButton.isEnabled());
        validateFieldAndVerifyErrorMessage(distributionYear, "2045", binder, null, true);
        assertTrue(exportButton.isEnabled());
    }

    @Test
    public void testValidationMessageForConverter() {
        Binder binder = Whitebox.getInternalState(widget, "binder");
        TextField distributionYear = Whitebox.getInternalState(widget, "distributionYear");
        Button exportButton = Whitebox.getInternalState(widget, "exportButton");
        binder.setValidatorsDisabled(true);
        validateFieldAndVerifyErrorMessage(
            distributionYear, "invalidValue", binder, "Field value can not be converted", false);
        assertFalse(exportButton.isEnabled());
    }
}
