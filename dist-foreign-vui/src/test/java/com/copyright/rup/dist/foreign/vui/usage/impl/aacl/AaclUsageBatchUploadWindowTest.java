package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyIntegerField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

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
import com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class AaclUsageBatchUploadWindowTest implements IVaadinComponentFinder {

    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final String USAGE_BATCH_NAME = "Usage Batch";
    private static final int PERIOD_END_DATE_YEAR = 2019;
    private static final int NUMBER_OF_BASELINE_YEARS = 0;
    private static final String USAGE_BATCH_NAME_LABEL = "Usage Batch Name";
    private static final String PERIOD_END_DATE_LABEL = "Period End Date (YYYY)";
    private static final String NUMBER_OF_BASELINE_YEARS_LABEL = "Number of Baseline Years";
    private static final String SPACES_STRING = "    ";
    private static final String STRING_50_CHARACTERS = StringUtils.repeat('a', 50);
    private static final String STRING_51_CHARACTERS = StringUtils.repeat('a', 51);
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String VALUE_EXCEED_50_CHARACTERS_MESSAGE = "Field value should not exceed 50 characters";
    private static final String USAGE_BATCH_EXISTS_MESSAGE = "Usage Batch with such name already exists";
    private static final String INVALID_PERIOD_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final String VALUE_NOT_POSITIVE_NUMBER = "Field value should be positive number";
    private static final String WIDTH_FULL = "100%";

    private AaclUsageBatchUploadWindow window;
    private IAaclUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IAaclUsageController.class);
    }

    @Test
    public void testConstructor() {
        replay(controller);
        window = new AaclUsageBatchUploadWindow(controller);
        verifyWindow(window, "Upload Usage Batch", "500px", "420px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/aacl/aacl-usage-batch-upload-window.json",
            new AaclUsageBatchUploadWindow(controller));
    }

    @Test
    public void testIsValid() {
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).anyTimes();
        replay(controller);
        window = new AaclUsageBatchUploadWindow(controller);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.setInternalState(uploadField, "fileName", "test.csv");
        assertFalse(window.isValid());
        setTextFieldValue(window, USAGE_BATCH_NAME_LABEL, USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        setIntegerFieldValue(window, PERIOD_END_DATE_LABEL, 2010);
        assertFalse(window.isValid());
        setIntegerFieldValue(window, NUMBER_OF_BASELINE_YEARS_LABEL, 2);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testUsageBatchNameFieldValidation() {
        String usageBatchName = "existing Usage Batch";
        expect(controller.usageBatchExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        expect(controller.usageBatchExists(usageBatchName)).andReturn(true).times(2);
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new AaclUsageBatchUploadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        var usageBatchNameField = getTextField(window, USAGE_BATCH_NAME_LABEL);
        assertFieldValidationMessage(usageBatchNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, SPACES_STRING, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, STRING_50_CHARACTERS, binder, null, true);
        assertFieldValidationMessage(usageBatchNameField, STRING_51_CHARACTERS, binder,
            VALUE_EXCEED_50_CHARACTERS_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, usageBatchName, binder, USAGE_BATCH_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, USAGE_BATCH_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testPeriodEndDateValidation() {
        replay(controller);
        window = new AaclUsageBatchUploadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        var periodEndDateField = getIntegerField(window, PERIOD_END_DATE_LABEL);
        assertFieldValidationMessage(periodEndDateField, 1949, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodEndDateField, 1950, binder, null, true);
        assertFieldValidationMessage(periodEndDateField, 2099, binder, null, true);
        assertFieldValidationMessage(periodEndDateField, 2100, binder, INVALID_PERIOD_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testNumberOfBaselineYearsFieldValidation() {
        replay(controller);
        window = new AaclUsageBatchUploadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        var numberOfBaselineYearsField = getIntegerField(window, NUMBER_OF_BASELINE_YEARS_LABEL);
        assertFieldValidationMessage(numberOfBaselineYearsField, -1, binder, VALUE_NOT_POSITIVE_NUMBER, false);
        assertFieldValidationMessage(numberOfBaselineYearsField, 0, binder, null, true);
        assertFieldValidationMessage(numberOfBaselineYearsField, 1, binder, null, true);
        verify(controller);
    }

    @Test
    public void testOnUploadClickedFileSpecified() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile", "getValue");
        AaclUsageCsvProcessor processor = createMock(AaclUsageCsvProcessor.class);
        var processingResult = buildCsvProcessingResult();
        window = createPartialMock(AaclUsageBatchUploadWindow.class, new String[]{"isValid"}, controller);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(uploadField.getValue()).andReturn("file.csv");
        expect(controller.getCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        expect(controller.loadUsageBatch(buildUsageBatch(), processingResult.get())).andReturn(3).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow(
            "Upload completed: 1 record(s) were uploaded, 2 record(s) were pulled from baseline");
        expectLastCall().once();
        replay(Windows.class, window, controller, uploadField, processor);
        setTextFieldValue(window, USAGE_BATCH_NAME_LABEL, USAGE_BATCH_NAME);
        setIntegerFieldValue(window, PERIOD_END_DATE_LABEL, PERIOD_END_DATE_YEAR);
        setIntegerFieldValue(window, NUMBER_OF_BASELINE_YEARS_LABEL, NUMBER_OF_BASELINE_YEARS);
        window.onUploadClicked();
        verify(Windows.class, window, controller, uploadField, processor);
    }

    @Test
    public void testOnUploadClickedEmptyFile() {
        mockStatic(Windows.class);
        UploadField uploadField = createPartialMock(UploadField.class, "getValue");
        AaclUsageCsvProcessor processor = createMock(AaclUsageCsvProcessor.class);
        window = createPartialMock(AaclUsageBatchUploadWindow.class, new String[]{"isValid"}, controller);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        expect(window.isValid()).andReturn(true).once();
        expect(uploadField.getValue()).andReturn(null);
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        expect(controller.loadUsageBatch(buildUsageBatch(), List.of())).andReturn(1).once();
        Windows.showNotificationWindow(
            "Upload completed: 0 record(s) were uploaded, 1 record(s) were pulled from baseline");
        expectLastCall().once();
        replay(window, controller, Windows.class, processor, uploadField);
        setTextFieldValue(window, USAGE_BATCH_NAME_LABEL, USAGE_BATCH_NAME);
        setIntegerFieldValue(window, PERIOD_END_DATE_LABEL, PERIOD_END_DATE_YEAR);
        setIntegerFieldValue(window, NUMBER_OF_BASELINE_YEARS_LABEL, NUMBER_OF_BASELINE_YEARS);
        window.onUploadClicked();
        verify(window, controller, Windows.class, processor, uploadField);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(3, rootLayout.getComponentCount());
        verifyUsageBatchNameComponent(rootLayout.getComponentAt(0));
        verifyUploadComponent(rootLayout.getComponentAt(1), WIDTH_FULL);
        verifyPeriodEndDateAndBaselineYears(rootLayout.getComponentAt(2));
        verifyButtonsLayout(getFooterLayout(window));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        var textField = verifyTextField(component, "Usage Batch Name", "usage-batch-name-field");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyPeriodEndDateAndBaselineYears(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyIntegerField(horizontalLayout.getComponentAt(0), "Period End Date (YYYY)", WIDTH_FULL,
            "distribution-period-field");
        verifyIntegerField(horizontalLayout.getComponentAt(1), "Number of Baseline Years", WIDTH_FULL,
            "number-of-baseline-years-field");
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var buttonsLayout = (HorizontalLayout) component;
        assertEquals(2, buttonsLayout.getComponentCount());
        var uploadButton = verifyButton(buttonsLayout.getComponentAt(0), "Upload", true);
        verifyLoadClickListener(uploadButton);
        verifyButton(buttonsLayout.getComponentAt(1), "Close", true);
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setProductFamily(AACL_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(LocalDate.of(PERIOD_END_DATE_YEAR, 6, 30));
        usageBatch.setNumberOfBaselineYears(NUMBER_OF_BASELINE_YEARS);
        return usageBatch;
    }

    private ProcessingResult<Usage> buildCsvProcessingResult() {
        var processingResult = new ProcessingResult<Usage>();
        try {
            Whitebox.invokeMethod(processingResult, "addRecord", new Usage());
        } catch (Exception e) {
            fail();
        }
        return processingResult;
    }
}
