package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
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
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import com.vaadin.data.Binder;
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
import java.util.List;

/**
 * Verifies {@link AaclUsageBatchUploadWindow}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/2019
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, AaclUsageBatchUploadWindow.class})
public class AaclUsageBatchUploadWindowTest {

    private static final String USAGE_BATCH_NAME = "BatchName";
    private static final String USAGE_BATCH_NAME_FIELD = "usageBatchNameField";
    private static final String PERIOD_END_DATE_FIELD = "periodEndDateField";
    private static final String NUMBER_OF_BASELINE_YEARS = "numberOfBaselineYears";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";

    private AaclUsageBatchUploadWindow window;
    private IAaclUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(usagesController);
        window = new AaclUsageBatchUploadWindow(usagesController);
        verifyWindow(window, "Upload Usage Batch", 380, 210, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).anyTimes();
        replay(usagesController);
        window = new AaclUsageBatchUploadWindow(usagesController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD)).setValue(USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD)).setValue("2010");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, NUMBER_OF_BASELINE_YEARS)).setValue("2");
        assertTrue(window.isValid());
        verify(usagesController);
    }

    @Test
    public void testUsageBatchNameFieldValidation() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(true).times(2);
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        replay(usagesController);
        window = new AaclUsageBatchUploadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField usageBatchName = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(usageBatchName, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE,
            false);
        validateFieldAndVerifyErrorMessage(usageBatchName, "   ", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(usageBatchName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters",
            false);
        validateFieldAndVerifyErrorMessage(usageBatchName, USAGE_BATCH_NAME, binder,
            "Usage Batch with such name already exists", false);
        validateFieldAndVerifyErrorMessage(usageBatchName, USAGE_BATCH_NAME, binder, null, true);
        verify(usagesController);
    }

    @Test
    public void testPeriodEndDateValidation() {
        replay(usagesController);
        window = new AaclUsageBatchUploadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField periodEndDate = Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD);
        validateFieldAndVerifyErrorMessage(periodEndDate, "", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodEndDate, "a", binder, "Field value should contain numeric values only",
            false);
        validateFieldAndVerifyErrorMessage(periodEndDate, "1000", binder,
            "Field value should be in range from 1950 to 2099", false);
        validateFieldAndVerifyErrorMessage(periodEndDate, "2100", binder,
            "Field value should be in range from 1950 to 2099", false);
        validateFieldAndVerifyErrorMessage(periodEndDate, "2020", binder, null, true);
        verify(usagesController);
    }

    @Test
    public void testNumberOfBaselineYearsValidation() {
        replay(usagesController);
        window = new AaclUsageBatchUploadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField numberOfBaselineYears = Whitebox.getInternalState(window, NUMBER_OF_BASELINE_YEARS);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, "", binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, "two", binder,
            "Field value should be positive number",
            false);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, "-1", binder, "Field value should be positive number",
            false);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, " -2 ", binder,
            "Field value should be positive number",
            false);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, " 1 ", binder, null, true);
        validateFieldAndVerifyErrorMessage(numberOfBaselineYears, "1", binder, null, true);
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedFileSpecified() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile", "getValue");
        AaclUsageCsvProcessor processor = createMock(AaclUsageCsvProcessor.class);
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(AaclUsageBatchUploadWindow.class, new String[]{"isValid"}, usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(uploadField.getValue()).andReturn("file.csv");
        expect(usagesController.getCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        expect(usagesController.loadUsageBatch(buildUsageBatch(), processingResult.get()))
            .andReturn(3).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow(
            "Upload completed: 1 record(s) were uploaded, 2 record(s) were pulled from baseline");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        setTextFieldValue(window, PERIOD_END_DATE_FIELD, "2019");
        setTextFieldValue(window, NUMBER_OF_BASELINE_YEARS, "0");
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
    }

    @Test
    public void testOnUploadClickedEmptyFile() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getValue");
        AaclUsageCsvProcessor processor = createMock(AaclUsageCsvProcessor.class);
        window = createPartialMock(AaclUsageBatchUploadWindow.class, new String[]{"isValid"}, usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(uploadField.getValue()).andReturn(null);
        expect(usagesController.getCsvProcessor()).andReturn(processor).once();
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        expect(usagesController.loadUsageBatch(buildUsageBatch(), List.of())).andReturn(1).once();
        Windows.showNotificationWindow(
            "Upload completed: 0 record(s) were uploaded, 1 record(s) were pulled from baseline");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        setTextFieldValue(window, PERIOD_END_DATE_FIELD, "2019");
        setTextFieldValue(window, NUMBER_OF_BASELINE_YEARS, "0");
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(4, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyPeriodEndDateAndBaselineYears(verticalLayout.getComponent(2));
        verifyButtonsLayout(verticalLayout.getComponent(3), "Upload", "Close");
        Button loadButton = (Button) ((HorizontalLayout) verticalLayout.getComponent(3)).getComponent(0);
        verifyLoadClickListener(loadButton, List.of(
            Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD),
            Whitebox.getInternalState(window, "uploadField"),
            Whitebox.getInternalState(window, PERIOD_END_DATE_FIELD),
            Whitebox.getInternalState(window, NUMBER_OF_BASELINE_YEARS)));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        TextField textField = verifyTextField(component, "Usage Batch Name", "usage-batch-name-field");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyPeriodEndDateAndBaselineYears(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), "Period End Date (YYYY)", "distribution-period-field");
        verifyTextField(layout.getComponent(1), "Number of Baseline Years", "number-of-baseline-years-field");
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setProductFamily("AACL");
        usageBatch.setPaymentDate(LocalDate.of(2019, 6, 30));
        usageBatch.setNumberOfBaselineYears(0);
        return usageBatch;
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
}
