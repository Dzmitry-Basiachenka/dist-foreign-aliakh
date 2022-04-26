package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link CreateAclFundPoolWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/19/2022
 *
 * @author Anton Azarenka
 */
public class CreateAclFundPoolWindowTest {

    private static final String[] LICENSE_TYPES = new String[]{"ACL", "MACL", "VGW", "JACDCL"};
    private static final String FUND_POOL_NAME = "Fund Pool Name";
    private static final String PERIOD_YEAR = "2020";
    private static final String PERIOD_MONTH = "12";
    private static final String ACL = "ACL";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String SPACES_STRING = "   ";

    private IAclFundPoolController controller;
    private CreateAclFundPoolWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclFundPoolController.class);
    }

    @Test
    public void testConstructor() {
        window = new CreateAclFundPoolWindow(controller);
        verifyWindow(window, "Create ACL Fund Pool", 400, 285, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsValid() {
        expect(controller.isFundPoolExist(FUND_POOL_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new CreateAclFundPoolWindow(controller);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "fundPoolPeriodYearField")).setValue(PERIOD_YEAR);
        assertFalse(window.isValid());
        ((ComboBox<String>) Whitebox.getInternalState(window, "fundPoolPeriodMonthComboBox")).setValue(PERIOD_MONTH);
        assertFalse(window.isValid());
        ((ComboBox<String>) Whitebox.getInternalState(window, "licenseTypeComboBox")).setValue(ACL);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "fundPoolNameFiled")).setValue(FUND_POOL_NAME);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testFundPoolNameFieldValidation() {
        expect(controller.isFundPoolExist(FUND_POOL_NAME)).andReturn(true).times(2);
        expect(controller.isFundPoolExist("Grant")).andReturn(false).times(2);
        replay(controller);
        window = new CreateAclFundPoolWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "fundPoolBinder");
        TextField fundPoolNameFiled = Whitebox.getInternalState(window, "fundPoolNameFiled");
        validateFieldAndVerifyErrorMessage(
            fundPoolNameFiled, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            fundPoolNameFiled, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fundPoolNameFiled, StringUtils.repeat('a', 256), binder,
            "Field value should not exceed 255 characters", false);
        validateFieldAndVerifyErrorMessage(fundPoolNameFiled, FUND_POOL_NAME, binder,
            "Fund Pool with such name already exists", false);
        validateFieldAndVerifyErrorMessage(fundPoolNameFiled, "Grant", binder, null, true);
        verify(controller);
    }

    @Test
    public void testFundPoolYearFieldValidation() {
        window = new CreateAclFundPoolWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodYearField = Whitebox.getInternalState(window, "fundPoolPeriodYearField");
        validateFieldAndVerifyErrorMessage(
            periodYearField, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "a955", binder, "Field value should contain numeric values only", false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "1949", binder, "Field value should be in range from 1950 to 2099", false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "2100", binder, "Field value should be in range from 1950 to 2099", false);
        validateFieldAndVerifyErrorMessage(periodYearField, "1950", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodYearField, " 1950 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodYearField, "1999", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodYearField, " 1999 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodYearField, "2099", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodYearField, " 2099 ", binder, null, true);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyTextField(verticalLayout.getComponent(0), "Fund Pool Name");
        verifyPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyComboBox(verticalLayout.getComponent(2), "License Type", true, LICENSE_TYPES);
        verifyUploadComponent(verticalLayout.getComponent(3));
        assertTrue(verticalLayout.getComponent(4) instanceof CheckBox);
        CheckBox checkBox = (CheckBox) verticalLayout.getComponent(4);
        assertEquals("LDMT", checkBox.getCaption());
        assertEquals("acl-ldmt-checkbox", checkBox.getStyleName());
        assertFalse(checkBox.getValue());
        verifyButtonsLayout(verticalLayout.getComponent(5), "Confirm", "Close");
    }

    private void verifyPeriodYearAndPeriodMonthComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyTextField(horizontalLayout.getComponent(0), "Fund Pool Period Year");
        verifyComboBox(horizontalLayout.getComponent(1), "Fund Pool Period Month", true, "06", "12");
    }
}
