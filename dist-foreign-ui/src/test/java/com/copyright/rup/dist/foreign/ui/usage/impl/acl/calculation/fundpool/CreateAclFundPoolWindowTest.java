package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setComboBoxValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.google.common.collect.ImmutableSet;
import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, CreateAclFundPoolWindow.class})
public class CreateAclFundPoolWindowTest {

    private static final String FUND_POOL_NAME_FIELD = "fundPoolNameField";
    private static final String FUND_POOL_PERIOD_YEAR_FIELD = "fundPoolPeriodYearField";
    private static final String FUND_POOL_PERIOD_MONTH_COMBO_BOX = "fundPoolPeriodMonthComboBox";
    private static final String LICENSE_TYPE_COMBO_BOX = "licenseTypeComboBox";
    private static final String LDMT_CHECK_BOX = "ldmtCheckBox";
    private static final String FUND_POOL_NAME = "Fund Pool Name";
    private static final String PERIOD_YEAR = "2021";
    private static final String PERIOD_MONTH = "12";
    private static final String LICENSE_TYPE = "ACL";
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
        setTextFieldValue(window, FUND_POOL_PERIOD_YEAR_FIELD, PERIOD_YEAR);
        assertFalse(window.isValid());
        setComboBoxValue(window, FUND_POOL_PERIOD_MONTH_COMBO_BOX, PERIOD_MONTH);
        assertFalse(window.isValid());
        setComboBoxValue(window, LICENSE_TYPE_COMBO_BOX, LICENSE_TYPE);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
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
        TextField fundPoolNameFiled = Whitebox.getInternalState(window, FUND_POOL_NAME_FIELD);
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
        TextField periodYearField = Whitebox.getInternalState(window, FUND_POOL_PERIOD_YEAR_FIELD);
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

    @Test
    public void testOnConfirmClickedManualValidFields() {
        window = createPartialMock(CreateAclFundPoolWindow.class, new String[]{"isValid", "close"}, controller);
        expect(window.isValid()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        AclFundPoolCsvProcessor processor = createMock(AclFundPoolCsvProcessor.class);
        expect(controller.getCsvProcessor()).andReturn(processor).once();
        ProcessingResult<AclFundPoolDetail> processingResult = buildCsvProcessingResult();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(controller.loadManualFundPool(buildFundPool(true), processingResult.get())).andReturn(1).once();
        expect(controller.isFundPoolExist(FUND_POOL_NAME)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, processor, Windows.class);
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(window, FUND_POOL_PERIOD_YEAR_FIELD, PERIOD_YEAR);
        setComboBoxValue(window, FUND_POOL_PERIOD_MONTH_COMBO_BOX, PERIOD_MONTH);
        setComboBoxValue(window, LICENSE_TYPE_COMBO_BOX, LICENSE_TYPE);
        ((CheckBox) Whitebox.getInternalState(window, LDMT_CHECK_BOX)).setValue(false);
        window.onConfirmClicked();
        verify(window, controller, processor, Windows.class);
    }

    @Test
    public void testOnConfirmClickedLdmtNoDetails() {
        window = createPartialMock(CreateAclFundPoolWindow.class, new String[]{"isValid", "close"}, controller);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.isFundPoolExist(FUND_POOL_NAME)).andReturn(false).once();
        expect(controller.isLdmtDetailExist(LICENSE_TYPE)).andReturn(false).once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow(
            "Fund Pool cannot be created. There are no LDMT details for specified license type");
        expectLastCall().once();
        replay(window, controller, Windows.class);
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(window, FUND_POOL_PERIOD_YEAR_FIELD, PERIOD_YEAR);
        setComboBoxValue(window, FUND_POOL_PERIOD_MONTH_COMBO_BOX, PERIOD_MONTH);
        setComboBoxValue(window, LICENSE_TYPE_COMBO_BOX, LICENSE_TYPE);
        ((CheckBox) Whitebox.getInternalState(window, LDMT_CHECK_BOX)).setValue(true);
        window.onConfirmClicked();
        verify(window, controller, Windows.class);
    }

    @Test
    public void testOnConfirmClickedLdmtValidFields() {
        window = createPartialMock(CreateAclFundPoolWindow.class, new String[]{"isValid", "close"}, controller);
        expect(window.isValid()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        expect(controller.createLdmtFundPool(buildFundPool(false))).andReturn(1).once();
        expect(controller.isFundPoolExist(FUND_POOL_NAME)).andReturn(false).once();
        expect(controller.isLdmtDetailExist(LICENSE_TYPE)).andReturn(true).once();
        mockStatic(Windows.class);
        Windows.showNotificationWindow("Creation completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, Windows.class);
        setTextFieldValue(window, FUND_POOL_NAME_FIELD, FUND_POOL_NAME);
        setTextFieldValue(window, FUND_POOL_PERIOD_YEAR_FIELD, PERIOD_YEAR);
        setComboBoxValue(window, FUND_POOL_PERIOD_MONTH_COMBO_BOX, PERIOD_MONTH);
        setComboBoxValue(window, LICENSE_TYPE_COMBO_BOX, LICENSE_TYPE);
        ((CheckBox) Whitebox.getInternalState(window, LDMT_CHECK_BOX)).setValue(true);
        window.onConfirmClicked();
        verify(window, controller, Windows.class);
    }

    private AclFundPool buildFundPool(boolean manualUploadFlag) {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setPeriod(202112);
        aclFundPool.setName(FUND_POOL_NAME);
        aclFundPool.setLicenseType(LICENSE_TYPE);
        aclFundPool.setManualUploadFlag(manualUploadFlag);
        return aclFundPool;
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyTextField(verticalLayout.getComponent(0), "Fund Pool Name", "acl-fund-pool-name-field");
        verifyPeriodYearAndPeriodMonthComponents(verticalLayout.getComponent(1));
        verifyComboBox(verticalLayout.getComponent(2), "License Type", true,
            ImmutableSet.of("ACL", "MACL", "VGW", "JACDCL"));
        verifyUploadComponent(verticalLayout.getComponent(3));
        assertThat(verticalLayout.getComponent(4), instanceOf(CheckBox.class));
        CheckBox checkBox = (CheckBox) verticalLayout.getComponent(4);
        assertEquals("LDMT", checkBox.getCaption());
        assertEquals("acl-ldmt-checkbox", checkBox.getStyleName());
        assertFalse(checkBox.getValue());
        verifyButtonsLayout(verticalLayout.getComponent(5), "Confirm", "Close");
    }

    private void verifyPeriodYearAndPeriodMonthComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyTextField(horizontalLayout.getComponent(0), "Fund Pool Period Year", "acl-fund-pool-period-year-field");
        verifyComboBox(horizontalLayout.getComponent(1), "Fund Pool Period Month", true, ImmutableSet.of("06", "12"));
    }

    private ProcessingResult<AclFundPoolDetail> buildCsvProcessingResult() {
        ProcessingResult<AclFundPoolDetail> processingResult = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new AclFundPoolDetail());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
    }
}
