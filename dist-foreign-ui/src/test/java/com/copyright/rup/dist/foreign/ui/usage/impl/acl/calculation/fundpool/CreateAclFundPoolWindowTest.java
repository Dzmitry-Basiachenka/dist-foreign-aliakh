package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;

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

    private static final String[] LICENSE_TYPES = new String[]{"ACL", "MACL", "VGW", "JACDCL"};
    private static final String FUND_POOL_NAME = "Fund Pool Name";
    private static final String PERIOD_YEAR = "2020";
    private static final String PERIOD_MONTH = "12";
    private static final String ACL = "ACL";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String SPACES_STRING = "   ";
    private static final String METHOD_NAME = "getValue";

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
        ((TextField) Whitebox.getInternalState(window, "fundPoolNameField")).setValue(FUND_POOL_NAME);
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
        TextField fundPoolNameFiled = Whitebox.getInternalState(window, "fundPoolNameField");
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

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, METHOD_NAME, "getStreamToUploadedFile");
        AclFundPoolCsvProcessor processor = createMock(AclFundPoolCsvProcessor.class);
        ComboBox periodMonth = createPartialMock(ComboBox.class, METHOD_NAME);
        ComboBox licenseType = createPartialMock(ComboBox.class, METHOD_NAME);
        CheckBox ldmtCheckBox = createPartialMock(CheckBox.class, METHOD_NAME);
        ProcessingResult<AclFundPoolDetail> processingResult = buildCsvProcessingResult();
        window = createPartialMock(CreateAclFundPoolWindow.class, "isValid");
        Whitebox.setInternalState(window, "fundPoolController", controller);
        Whitebox.setInternalState(window, "fundPoolNameField", new TextField("Fund Pool Name", "FP Name"));
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, "fundPoolPeriodYearField", new TextField("Period Year", "2021"));
        Whitebox.setInternalState(window, "fundPoolPeriodMonthComboBox", periodMonth);
        Whitebox.setInternalState(window, "licenseTypeComboBox", licenseType);
        Whitebox.setInternalState(window, "ldmtCheckBox", ldmtCheckBox);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.getCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(periodMonth.getValue()).andReturn("12").once();
        expect(licenseType.getValue()).andReturn(ACL).once();
        expect(ldmtCheckBox.getValue()).andReturn(false).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        expect(controller.loadFundPool(buildFundPool(), processingResult.get())).andReturn(1).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, controller, Windows.class, processor, uploadField, periodMonth, licenseType, ldmtCheckBox);
        window.manualUpload();
        verify(window, controller, Windows.class, processor, uploadField, periodMonth, licenseType, ldmtCheckBox);
    }

    private AclFundPool buildFundPool() {
        AclFundPool aclFundPool = new AclFundPool();
        aclFundPool.setPeriod(202112);
        aclFundPool.setName("FP Name");
        aclFundPool.setLicenseType(ACL);
        aclFundPool.setManualUploadFlag(true);
        return aclFundPool;
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
