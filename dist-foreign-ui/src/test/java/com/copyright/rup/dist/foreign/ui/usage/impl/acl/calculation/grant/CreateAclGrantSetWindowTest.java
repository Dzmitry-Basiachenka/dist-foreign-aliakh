package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setComboBoxValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyCheckBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link CreateAclGrantSetWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/31/2022
 *
 * @author Dzmitry Basiachenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, CreateAclGrantSetWindow.class})
public class CreateAclGrantSetWindowTest {

    private static final String GRANT_SET_ID = "1caa9169-8587-4d1c-8c07-7f5582daea67";
    private static final String PERIOD_YEAR = "2020";
    private static final String PERIOD_MONTH = "12";
    private static final String GRANT_SET_NAME = "Grant Set Name";
    private static final String GRANT_SET_NAME_2022 = "Grant Set Name 2022";
    private static final Integer GRANT_SET_GRANT_PERIOD = 202206;
    private static final String ACL = "ACL";
    private static final String[] MONTHS = new String[]{"06", "12"};
    private static final Set<Integer> PERIODS = Collections.singleton(202112);
    private static final String[] LICENSE_TYPES = new String[]{"ACL", "MACL", "VGW", "JACDCL"};
    private static final String SPACES_STRING = "   ";
    private static final String GRANT_SET_NAME_FIELD = "grantSetNameFiled";
    private static final String GRANT_PERIOD_YEAR_FIELD = "grantPeriodYearField";
    private static final String GRANT_PERIOD_MONTH_COMBOBOX = "grantPeriodMonthComboBox";
    private static final String PERIOD_VALIDATION_FIELD = "periodValidationField";
    private static final String PERIOD_FILTER_WIDGET = "periodFilterWidget";
    private static final String LICENSE_TYPE_COMBOBOX = "licenseTypeComboBox";
    private static final String COPY_FROM_COMBOBOX = "copyFromComboBox";
    private static final String EDITABLE_CHECK_BOX = "editableCheckBox";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String INVALID_PERIOD_VALIDATION_MESSAGE = "Please select at least one period";

    private IAclGrantDetailController controller;
    private CreateAclGrantSetWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclGrantDetailController.class);
        expect(controller.getAllAclGrantSets()).andReturn(Collections.singletonList(buildAclGrantSet())).once();
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        verifyWindow(window, "Create ACL Grant Set", 400, 320, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verify(controller);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIsValid() {
        expect(controller.isGrantSetExist(GRANT_SET_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, GRANT_PERIOD_YEAR_FIELD)).setValue(PERIOD_YEAR);
        assertFalse(window.isValid());
        ((ComboBox<String>) Whitebox.getInternalState(window, GRANT_PERIOD_MONTH_COMBOBOX)).setValue(PERIOD_MONTH);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, PERIOD_VALIDATION_FIELD)).setValue("1");
        assertFalse(window.isValid());
        ((ComboBox<String>) Whitebox.getInternalState(window, LICENSE_TYPE_COMBOBOX)).setValue(ACL);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, GRANT_SET_NAME_FIELD)).setValue(GRANT_SET_NAME);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testOnCreateClickedValidFields() {
        expect(controller.isGrantSetExist(GRANT_SET_NAME)).andReturn(false).once();
        Capture<AclGrantSet> grantSetCapture = newCapture();
        expect(controller.insertAclGrantSet(capture(grantSetCapture))).andReturn(1).once();
        replay(controller);
        window = createPartialMock(CreateAclGrantSetWindow.class, new String[]{"isValid", "close"}, controller);
        expect(window.isValid()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow("Creation completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, Windows.class);
        setTextFieldValue(window, GRANT_SET_NAME_FIELD, GRANT_SET_NAME);
        setTextFieldValue(window, GRANT_PERIOD_YEAR_FIELD, PERIOD_YEAR);
        setComboBoxValue(window, GRANT_PERIOD_MONTH_COMBOBOX, PERIOD_MONTH);
        Whitebox.setInternalState(window, "selectedPeriods", PERIODS);
        setComboBoxValue(window, LICENSE_TYPE_COMBOBOX, ACL);
        window.onCreateClicked();
        AclGrantSet grantSet = grantSetCapture.getValue();
        assertEquals(GRANT_SET_NAME, grantSet.getName());
        assertEquals(202012, grantSet.getGrantPeriod().intValue());
        assertEquals(PERIODS, grantSet.getPeriods());
        assertEquals(ACL, grantSet.getLicenseType());
        assertTrue(grantSet.getEditable());
        verify(window, controller, Windows.class);
    }

    @Test
    public void testGrantSetNameFieldValidation() {
        expect(controller.isGrantSetExist(GRANT_SET_NAME)).andReturn(true).times(2);
        expect(controller.isGrantSetExist("Grant")).andReturn(false).times(2);
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "grantSetBinder");
        TextField grantSetNameFiled = Whitebox.getInternalState(window, GRANT_SET_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(
            grantSetNameFiled, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            grantSetNameFiled, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(grantSetNameFiled, StringUtils.repeat('a', 256), binder,
            "Field value should not exceed 255 characters", false);
        validateFieldAndVerifyErrorMessage(grantSetNameFiled, GRANT_SET_NAME, binder,
            "Grant Set with such name already exists", false);
        validateFieldAndVerifyErrorMessage(grantSetNameFiled, "Grant", binder, null, true);
        verify(controller);
    }

    @Test
    public void testGrantPeriodYearFieldValidation() {
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodYearField = Whitebox.getInternalState(window, GRANT_PERIOD_YEAR_FIELD);
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
        verify(controller);
    }

    @Test
    public void testPeriodValidationFieldValidation() {
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodValidationField = Whitebox.getInternalState(window, PERIOD_VALIDATION_FIELD);
        validateFieldAndVerifyErrorMessage(periodValidationField, "1000", binder, null, true);
        validateFieldAndVerifyErrorMessage(periodValidationField, "99999999", binder, null, true);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, StringUtils.EMPTY, binder, INVALID_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "A", binder, INVALID_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "0", binder, INVALID_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "-1000", binder, INVALID_PERIOD_VALIDATION_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testFieldsFillingViaCopyFrom() {
        AclGrantSet aclGrantSet = buildAclGrantSet();
        expect(controller.isGrantSetExist(GRANT_SET_NAME)).andReturn(true).once();
        expect(controller.getAclGrantSetById(GRANT_SET_ID)).andReturn(aclGrantSet).once();
        replay(controller);
        window = new CreateAclGrantSetWindow(controller);
        ComboBox<AclGrantSet> copyFromComboBox = Whitebox.getInternalState(window, COPY_FROM_COMBOBOX);
        copyFromComboBox.setValue(aclGrantSet);
        verifyCreateGrantSetWindowFieldsAfterFillingCopyFromComboBox();
        TextField grantSetNameField = Whitebox.getInternalState(window, GRANT_SET_NAME_FIELD);
        grantSetNameField.setValue(GRANT_SET_NAME);
        copyFromComboBox.clear();
        verifyCreateGrantSetWindowFieldsAfterClearCopyFromComboBox();
        verify(controller);
    }

    private void verifyCreateGrantSetWindowFieldsAfterFillingCopyFromComboBox() {
        TextField grantSetNameField = Whitebox.getInternalState(window, GRANT_SET_NAME_FIELD);
        assertTrue(grantSetNameField.getValue().isEmpty());
        assertTrue(grantSetNameField.isEnabled());
        TextField grantPeriodYearField = Whitebox.getInternalState(window, GRANT_PERIOD_YEAR_FIELD);
        assertEquals("2022", grantPeriodYearField.getValue());
        assertFalse(grantPeriodYearField.isEnabled());
        ComboBox<String> grantPeriodMonthComboBox = Whitebox.getInternalState(window, GRANT_PERIOD_MONTH_COMBOBOX);
        assertEquals("06", grantPeriodMonthComboBox.getValue());
        assertFalse(grantPeriodMonthComboBox.isEnabled());
        TextField periodValidationField = Whitebox.getInternalState(window, PERIOD_VALIDATION_FIELD);
        assertEquals("1", periodValidationField.getValue());
        PeriodFilterWidget periodFilterWidget = Whitebox.getInternalState(window, PERIOD_FILTER_WIDGET);
        assertFalse(periodFilterWidget.isEnabled());
        ComboBox<String> licenseTypeComboBox = Whitebox.getInternalState(window, LICENSE_TYPE_COMBOBOX);
        assertEquals(ACL, licenseTypeComboBox.getValue());
        assertFalse(licenseTypeComboBox.isEnabled());
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECK_BOX);
        assertTrue(editableCheckBox.getValue());
        assertFalse(editableCheckBox.isEnabled());
    }

    private void verifyCreateGrantSetWindowFieldsAfterClearCopyFromComboBox() {
        TextField grantSetNameField = Whitebox.getInternalState(window, GRANT_SET_NAME_FIELD);
        assertEquals(GRANT_SET_NAME, grantSetNameField.getValue());
        assertTrue(grantSetNameField.isEnabled());
        TextField grantPeriodYearField = Whitebox.getInternalState(window, GRANT_PERIOD_YEAR_FIELD);
        assertTrue(grantPeriodYearField.getValue().isEmpty());
        assertTrue(grantPeriodYearField.isEnabled());
        ComboBox<String> grantPeriodMonthComboBox = Whitebox.getInternalState(window, GRANT_PERIOD_MONTH_COMBOBOX);
        assertNull(grantPeriodMonthComboBox.getValue());
        assertTrue(grantPeriodMonthComboBox.isEnabled());
        PeriodFilterWidget periodFilterWidget = Whitebox.getInternalState(window, PERIOD_FILTER_WIDGET);
        assertTrue(periodFilterWidget.isEnabled());
        ComboBox<String> licenseTypeComboBox = Whitebox.getInternalState(window, LICENSE_TYPE_COMBOBOX);
        assertNull(licenseTypeComboBox.getValue());
        assertTrue(licenseTypeComboBox.isEnabled());
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECK_BOX);
        assertTrue(editableCheckBox.getValue());
        assertTrue(editableCheckBox.isEnabled());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(7, verticalLayout.getComponentCount());
        verifyGrantSetNameComponent(verticalLayout.getComponent(0));
        verifyPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Periods");
        verifyComboBox(verticalLayout.getComponent(3), "License Type", true, LICENSE_TYPES);
        verifyComboBox(verticalLayout.getComponent(4), "Copy From", true, buildAclGrantSet());
        verifyCheckBox(verticalLayout.getComponent(5), "Editable", "acl-editable-checkbox");
        verifyButtonsLayout(verticalLayout.getComponent(6), "Create", "Close");
    }

    private void verifyGrantSetNameComponent(Component component) {
        TextField textField = verifyTextField(component, "Grant Set Name");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    @SuppressWarnings("unchecked")
    private void verifyPeriodYearAndPeriodMonthComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        assertTrue(horizontalLayout.getComponent(0) instanceof TextField);
        assertTrue(horizontalLayout.getComponent(1) instanceof ComboBox);
        verifyTextField(horizontalLayout.getComponent(0), "Grant Period Year");
        verifyComboBox(horizontalLayout.getComponent(1), "Grant Period Month", true, MONTHS);
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet aclGrantSet = new AclGrantSet();
        aclGrantSet.setId(GRANT_SET_ID);
        aclGrantSet.setName(GRANT_SET_NAME_2022);
        aclGrantSet.setGrantPeriod(GRANT_SET_GRANT_PERIOD);
        aclGrantSet.setPeriods(PERIODS);
        aclGrantSet.setLicenseType(ACL);
        aclGrantSet.setEditable(true);
        return aclGrantSet;
    }
}
