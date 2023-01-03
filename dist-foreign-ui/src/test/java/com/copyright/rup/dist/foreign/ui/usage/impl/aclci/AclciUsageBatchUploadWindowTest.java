package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyUploadComponent;
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
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;
import com.copyright.rup.dist.foreign.service.impl.csv.AclciUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Verifies {@link AclciUsageBatchUploadWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/19/2022
 *
 * @author Aliaksanr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, AclciUsageBatchUploadWindow.class})
public class AclciUsageBatchUploadWindowTest {

    private static final String USAGE_BATCH_NAME = "ACLCI Usage Batch";
    private static final Long LICENSEE_ACCOUNT_NUMBER = 1111L;
    private static final String LICENSEE_NAME = "Acuson Corporation";
    private static final Integer PERIOD_END_DATE = 2022;
    private static final String USAGE_BATCH_NAME_FIELD = "usageBatchNameField";
    private static final String UPLOAD_FIELD = "uploadField";
    private static final String LICENSEE_ACCOUNT_NUMBER_FIELD = "licenseeAccountNumberField";
    private static final String LICENSEE_NAME_FIELD = "licenseeNameField";
    private static final String PERIOD_END_DATE_FIELD = "periodEndDateField";
    private static final String SPACES_STRING = "   ";
    private static final String EMPTY_FIELD_ERROR = "Field value should be specified";
    private static final String INVALID_NUMBER_ERROR = "Field value should contain numeric values only";
    private static final String FIELD_LENGTH_EXCEEDS_50_ERROR = "Field value should not exceed 50 characters";
    private static final String USAGE_BATCH_EXISTS_ERROR = "Usage Batch with such name already exists";
    private static final String FILE_EXTENSION_ERROR = "File extension is incorrect";
    private static final String NUMBER_LENGTH_EXCEEDS_10_ERROR = "Field value should not exceed 10 digits";
    private static final String INVALID_PERIOD_ERROR = "Field value should be in range from 1950 to 2099";

    private AclciUsageBatchUploadWindow window;
    private IAclciUsageController aclciUsageController;

    @Before
    public void setUp() {
        aclciUsageController = createMock(IAclciUsageController.class);
    }

    @Test
    public void testConstructor() {
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        assertEquals("Upload Usage Batch", window.getCaption());
        assertEquals(400, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(305, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testVerifyButtonListener() {
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        VerticalLayout verticalLayout = (VerticalLayout) ((VerticalLayout) window.getContent()).getComponent(2);
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        TextField accountNumber = (TextField) horizontalLayout.getComponent(0);
        Button verifyButton = (Button) horizontalLayout.getComponent(1);
        TextField licenseeName = (TextField) verticalLayout.getComponent(1);
        expect(aclciUsageController.getLicenseeName(LICENSEE_ACCOUNT_NUMBER)).andReturn(LICENSEE_NAME).once();
        replay(aclciUsageController);
        accountNumber.setValue("not_a_number");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, licenseeName.getValue());
        accountNumber.setValue(LICENSEE_ACCOUNT_NUMBER.toString());
        verifyButton.click();
        assertEquals(LICENSEE_NAME, licenseeName.getValue());
        accountNumber.setValue(StringUtils.EMPTY);
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, licenseeName.getValue());
        verify(aclciUsageController);
    }

    @Test
    public void testIsValid() {
        expect(aclciUsageController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(7);
        replay(aclciUsageController);
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        assertFalse(window.isValid());
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("file.csv");
        assertFalse(window.isValid());
        setTextFieldValue(window, LICENSEE_ACCOUNT_NUMBER_FIELD, LICENSEE_ACCOUNT_NUMBER.toString());
        assertFalse(window.isValid());
        TextField licenseeName = Whitebox.getInternalState(window, LICENSEE_NAME_FIELD);
        licenseeName.setReadOnly(false);
        licenseeName.setValue(LICENSEE_NAME);
        licenseeName.setReadOnly(true);
        assertFalse(window.isValid());
        setTextFieldValue(window, PERIOD_END_DATE_FIELD, PERIOD_END_DATE.toString());
        assertTrue(window.isValid());
        verify(aclciUsageController);
    }

    @Test
    public void testOnUploadClicked() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        AclciUsageCsvProcessor processor = createMock(AclciUsageCsvProcessor.class);
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        window = createPartialMock(AclciUsageBatchUploadWindow.class, new String[]{"isValid"}, aclciUsageController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(aclciUsageController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        expect(aclciUsageController.getAclciUsageCsvProcessor()).andReturn(processor).once();
        aclciUsageController.loadAclciUsageBatch(buildUsageBatch(), processingResult.get());
        expectLastCall().once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, uploadField, processor, window, aclciUsageController);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        setTextFieldValue(window, LICENSEE_ACCOUNT_NUMBER_FIELD, LICENSEE_ACCOUNT_NUMBER.toString());
        setTextFieldValue(window, LICENSEE_NAME_FIELD, LICENSEE_NAME);
        setTextFieldValue(window, PERIOD_END_DATE_FIELD, PERIOD_END_DATE.toString());
        window.onUploadClicked();
        verify(Windows.class, uploadField, processor, window, aclciUsageController);
    }

    @Test
    public void testUsageBatchNameFieldValidation() {
        expect(aclciUsageController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(true).times(2);
        expect(aclciUsageController.usageBatchExists("Usage Batch")).andReturn(false).times(3);
        replay(aclciUsageController);
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField textField = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField,
            StringUtils.repeat('a', 51), binder, FIELD_LENGTH_EXCEEDS_50_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, USAGE_BATCH_NAME, binder, USAGE_BATCH_EXISTS_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "Usage Batch", binder, null, true);
        verify(aclciUsageController);
    }

    @Test
    public void testUploadFieldValidation() {
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        Binder binder = Whitebox.getInternalState(window, "uploadBinder");
        UploadField uploadField = Whitebox.getInternalState(window, UPLOAD_FIELD);
        validateFieldAndVerifyErrorMessage(uploadField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(uploadField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(uploadField, "file.txt", binder, FILE_EXTENSION_ERROR, false);
        validateFieldAndVerifyErrorMessage(uploadField, "file", binder, FILE_EXTENSION_ERROR, false);
        validateFieldAndVerifyErrorMessage(uploadField, "file.csv", binder, null, true);
    }

    @Test
    public void testLicenseeAccountNumberValidation() {
        replay(aclciUsageController);
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField textField = Whitebox.getInternalState(window, LICENSEE_ACCOUNT_NUMBER_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "abc", binder, INVALID_NUMBER_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "1234567890", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "12345678901", binder, NUMBER_LENGTH_EXCEEDS_10_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, " 12345678 ", binder, null, true);
        verify(aclciUsageController);
    }

    @Test
    public void testPeriodEndDateValidation() {
        replay(aclciUsageController);
        window = new AclciUsageBatchUploadWindow(aclciUsageController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField textField = Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD);
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, binder, EMPTY_FIELD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "abc", binder, INVALID_NUMBER_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1949", binder, INVALID_PERIOD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, "1950", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "2099", binder, null, true);
        validateFieldAndVerifyErrorMessage(textField, "2100", binder, INVALID_PERIOD_ERROR, false);
        validateFieldAndVerifyErrorMessage(textField, " 2022 ", binder, null, true);
        verify(aclciUsageController);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyUsageBatchName(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyLicenseeComponents(verticalLayout.getComponent(2));
        verifyPeriodEndDate(verticalLayout.getComponent(3));
        verifyButtonsLayout(verticalLayout.getComponent(4));
    }

    private void verifyUsageBatchName(Component component) {
        TextField textField = verifyTextField(component, "Usage Batch Name");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyLicenseeComponents(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        TextField licenseeAccountNumber = verifyTextField(horizontalLayout.getComponent(0), "Licensee Account #");
        Collection<?> listeners = licenseeAccountNumber.getListeners(HasValue.ValueChangeEvent.class);
        assertEquals(2, listeners.size());
        Component verifyComponent = horizontalLayout.getComponent(1);
        assertThat(verifyComponent, instanceOf(Button.class));
        assertEquals("Verify", verifyComponent.getCaption());
        TextField licenseeName = verifyTextField(verticalLayout.getComponent(1), "Licensee Name");
        assertTrue(licenseeName.isReadOnly());
    }

    private void verifyPeriodEndDate(Component component) {
        TextField textField = verifyTextField(component, "Period End Date (YYYY)", 40);
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload", true);
        verifyButton(layout.getComponent(1), "Close", true);
        assertEquals(1, loadButton.getListeners(Button.ClickEvent.class).size());
        verifyLoadClickListener(loadButton, Arrays.asList(
            Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD),
            Whitebox.getInternalState(window, UPLOAD_FIELD),
            Whitebox.getInternalState(window, LICENSEE_ACCOUNT_NUMBER_FIELD),
            Whitebox.getInternalState(window, LICENSEE_NAME_FIELD),
            Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD)));
    }

    private ProcessingResult<Usage> buildCsvProcessingResult() {
        ProcessingResult<Usage> processingResult = new ProcessingResult<>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new Usage());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setProductFamily("ACLCI");
        usageBatch.setPaymentDate(LocalDate.of(PERIOD_END_DATE, 6, 30));
        AclciFields aclciFields = new AclciFields();
        aclciFields.setLicenseeName(LICENSEE_NAME);
        aclciFields.setLicenseeAccountNumber(LICENSEE_ACCOUNT_NUMBER);
        usageBatch.setAclciFields(aclciFields);
        return usageBatch;
    }
}
