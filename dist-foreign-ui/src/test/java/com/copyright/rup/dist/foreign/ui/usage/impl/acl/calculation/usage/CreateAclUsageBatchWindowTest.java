package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

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
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
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
 * Verifies {@link CreateAclUsageBatchWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/05/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, CreateAclUsageBatchWindow.class})
public class CreateAclUsageBatchWindowTest {

    private static final String ACL_USAGE_BATCH_ID = "fe027e07-4aba-4940-9d45-8d342be8dc35";
    private static final String ACL_USAGE_BATCH_NAME = "ACL Usage Batch 2021";
    private static final String DISTRIBUTION_PERIOD_YEAR = "2021";
    private static final String DISTRIBUTION_PERIOD_MONTH = "12";
    private static final Integer DISTRIBUTION_PERIOD = 202112;
    private static final Set<Integer> PERIODS = Collections.singleton(202112);
    private static final String PERIODS_COUNT = String.valueOf(PERIODS.size());
    private static final String USAGE_BATCH_NAME_FIELD = "usageBatchNameFiled";
    private static final String DISTRIBUTION_PERIOD_YEAR_FIELD = "distributionPeriodYearField";
    private static final String DISTRIBUTION_PERIOD_MONTH_COMBOBOX = "distributionPeriodMonthComboBox";
    private static final String COPY_FROM_COMBOBOX = "copyFromComboBox";
    private static final String PERIOD_FILTER_WIDGET = "periodFilterWidget";
    private static final String PERIOD_VALIDATION_FIELD = "periodValidationField";
    private static final String EDITABLE_CHECK_BOX = "editableCheckBox";
    private static final String SPACES_STRING = "  ";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String FIELD_LENGTH_VALIDATION_MESSAGE = "Field value should not exceed 255 characters";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String INVALID_RANGE_VALIDATION_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final String EMPTY_PERIOD_VALIDATION_MESSAGE = "Please select at least one period";

    private IAclUsageController controller;
    private CreateAclUsageBatchWindow window;

    @Before
    public void setUp() {
        controller = createMock(IAclUsageController.class);
        expect(controller.getAllAclUsageBatches()).andReturn(Collections.singletonList(buildAclUsageBatch())).once();
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        verifyWindow(window, "Create ACL Usage Batch", 400, 265, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verify(controller);
    }

    @Test
    public void testIsValid() {
        expect(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(false).times(5);
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        assertFalse(window.isValid());
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, ACL_USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        setTextFieldValue(window, DISTRIBUTION_PERIOD_YEAR_FIELD, DISTRIBUTION_PERIOD_YEAR);
        assertFalse(window.isValid());
        setComboBoxValue(window, DISTRIBUTION_PERIOD_MONTH_COMBOBOX, DISTRIBUTION_PERIOD_MONTH);
        assertFalse(window.isValid());
        setTextFieldValue(window, PERIOD_VALIDATION_FIELD, PERIODS_COUNT);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testOnCreateClicked() {
        mockStatic(Windows.class);
        Capture<AclUsageBatch> usageBatchCapture = newCapture();
        Windows.showNotificationWindow("Creation completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        expect(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(false).times(2);
        expect(controller.insertAclUsageBatch(capture(usageBatchCapture))).andReturn(1).once();
        replay(Windows.class, controller);
        window = new CreateAclUsageBatchWindow(controller);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, ACL_USAGE_BATCH_NAME);
        setTextFieldValue(window, DISTRIBUTION_PERIOD_YEAR_FIELD, DISTRIBUTION_PERIOD_YEAR);
        setComboBoxValue(window, DISTRIBUTION_PERIOD_MONTH_COMBOBOX, DISTRIBUTION_PERIOD_MONTH);
        setTextFieldValue(window, PERIOD_VALIDATION_FIELD, PERIODS_COUNT);
        Whitebox.setInternalState(window, "selectedPeriods", PERIODS);
        window.onCreateClicked();
        AclUsageBatch usageBatch = usageBatchCapture.getValue();
        assertEquals(ACL_USAGE_BATCH_NAME, usageBatch.getName());
        assertEquals(DISTRIBUTION_PERIOD, usageBatch.getDistributionPeriod());
        assertEquals(PERIODS, usageBatch.getPeriods());
        assertTrue(usageBatch.getEditable());
        verify(Windows.class, controller);
    }

    @Test
    public void testUsageBatchNameFieldValidation() {
        expect(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(true).times(2);
        expect(controller.isAclUsageBatchExist("ACL Usage Batch 2022")).andReturn(false).times(2);
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        setTextFieldValue(window, DISTRIBUTION_PERIOD_YEAR_FIELD, DISTRIBUTION_PERIOD_YEAR);
        setComboBoxValue(window, DISTRIBUTION_PERIOD_MONTH_COMBOBOX, DISTRIBUTION_PERIOD_MONTH);
        setTextFieldValue(window, PERIOD_VALIDATION_FIELD, PERIODS_COUNT);
        Binder binder = Whitebox.getInternalState(window, "usageBatchBinder");
        TextField usageBatchNameFiled = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(
            usageBatchNameFiled, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            usageBatchNameFiled, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            usageBatchNameFiled, StringUtils.repeat('a', 256), binder, FIELD_LENGTH_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            usageBatchNameFiled, ACL_USAGE_BATCH_NAME, binder, "Usage Batch with such name already exists", false);
        validateFieldAndVerifyErrorMessage(usageBatchNameFiled, "ACL Usage Batch 2022", binder, null, true);
        verify(controller);
    }

    @Test
    public void testDistributionPeriodYearFieldValidation() {
        expect(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, ACL_USAGE_BATCH_NAME);
        setComboBoxValue(window, DISTRIBUTION_PERIOD_MONTH_COMBOBOX, DISTRIBUTION_PERIOD_MONTH);
        setTextFieldValue(window, PERIOD_VALIDATION_FIELD, PERIODS_COUNT);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodYearField = Whitebox.getInternalState(window, DISTRIBUTION_PERIOD_YEAR_FIELD);
        validateFieldAndVerifyErrorMessage(
            periodYearField, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "a", binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "1949", binder, INVALID_RANGE_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodYearField, "2100", binder, INVALID_RANGE_VALIDATION_MESSAGE, false);
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
        expect(controller.isAclUsageBatchExist(ACL_USAGE_BATCH_NAME)).andReturn(false).once();
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, ACL_USAGE_BATCH_NAME);
        setTextFieldValue(window, DISTRIBUTION_PERIOD_YEAR_FIELD, DISTRIBUTION_PERIOD_YEAR);
        setComboBoxValue(window, DISTRIBUTION_PERIOD_MONTH_COMBOBOX, DISTRIBUTION_PERIOD_MONTH);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodValidationField = Whitebox.getInternalState(window, PERIOD_VALIDATION_FIELD);
        validateFieldAndVerifyErrorMessage(periodValidationField, PERIODS_COUNT, binder, null, true);
        validateFieldAndVerifyErrorMessage(periodValidationField, "99999999", binder, null, true);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, StringUtils.EMPTY, binder, EMPTY_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "a", binder, EMPTY_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "0", binder, EMPTY_PERIOD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            periodValidationField, "-1", binder, EMPTY_PERIOD_VALIDATION_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testFieldsWhenCopyFromPopulated() {
        replay(controller);
        window = new CreateAclUsageBatchWindow(controller);
        ComboBox<AclUsageBatch> copyFromComboBox = Whitebox.getInternalState(window, COPY_FROM_COMBOBOX);
        copyFromComboBox.setValue(buildAclUsageBatch());
        verifyCreateUsageBatchWindowFields();
        verify(controller);
    }

    private void verifyCreateUsageBatchWindowFields() {
        TextField usageBatchNameField = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        assertEquals(StringUtils.EMPTY, usageBatchNameField.getValue());
        assertTrue(usageBatchNameField.isEnabled());
        TextField distributionPeriodYearField = Whitebox.getInternalState(window, DISTRIBUTION_PERIOD_YEAR_FIELD);
        assertEquals("2021", distributionPeriodYearField.getValue());
        assertFalse(distributionPeriodYearField.isEnabled());
        ComboBox<String> distributionPeriodMonthComboBox = Whitebox.getInternalState(window,
            DISTRIBUTION_PERIOD_MONTH_COMBOBOX);
        assertEquals("12", distributionPeriodMonthComboBox.getValue());
        assertFalse(distributionPeriodMonthComboBox.isEnabled());
        TextField periodValidationField = Whitebox.getInternalState(window, PERIOD_VALIDATION_FIELD);
        assertEquals("1", periodValidationField.getValue());
        PeriodFilterWidget periodFilterWidget = Whitebox.getInternalState(window, PERIOD_FILTER_WIDGET);
        assertFalse(periodFilterWidget.isEnabled());
        CheckBox editableCheckBox = Whitebox.getInternalState(window, EDITABLE_CHECK_BOX);
        assertTrue(editableCheckBox.getValue());
        assertFalse(editableCheckBox.isEnabled());
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyTextField(verticalLayout.getComponent(0), "Usage Batch Name");
        verifyDistributionPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyComboBox(verticalLayout.getComponent(2), "Copy From", true, buildAclUsageBatch());
        verifyItemsFilterWidget(verticalLayout.getComponent(3), "Periods");
        verifyCheckBox(verticalLayout.getComponent(4), "Editable", "acl-editable-checkbox");
        verifyButtonsLayout(verticalLayout.getComponent(5), "Create", "Close");
    }

    private void verifyDistributionPeriodYearAndPeriodMonthComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyTextField(horizontalLayout.getComponent(0), "Distribution Period Year");
        verifyComboBox(horizontalLayout.getComponent(1), "Distribution Period Month", true, new String[]{"06", "12"});
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch aclUsageBatch = new AclUsageBatch();
        aclUsageBatch.setId(ACL_USAGE_BATCH_ID);
        aclUsageBatch.setName(ACL_USAGE_BATCH_NAME);
        aclUsageBatch.setDistributionPeriod(DISTRIBUTION_PERIOD);
        aclUsageBatch.setPeriods(PERIODS);
        aclUsageBatch.setEditable(true);
        return aclUsageBatch;
    }
}
