package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setBigDecimalFieldValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setLongFieldValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyBigDecimalField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLongField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyUploadComponent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Verifies {@link UsageBatchUploadWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, UsageBatchUploadWindow.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class UsageBatchUploadWindowTest {

    private static final String USAGE_BATCH_NAME = "Usage Batch";
    private static final Long FAS_RRO_ACCOUNT_NUMBER = 1000001863L;
    private static final String FAS_RRO_ACCOUNT_NAME = "CANADIAN CERAMIC SOCIETY";
    private static final Long CLA_RRO_ACCOUNT_NUMBER = 2000017000L;
    private static final String CLA_RRO_ACCOUNT_NAME = "CLA, The Copyright Licensing Agency Ltd.";
    private static final String RRO_NAME = "RRO name";
    private static final Long UNKNOWN_ACCOUNT_NAME = 20000170L;
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 2, 27);
    private static final String FISCAL_YEAR = "FY2017";
    private static final String GROSS_AMOUNT = "100.00";
    private static final String USAGE_BATCH_NAME_FIELD = "usageBatchNameField";
    private static final String UPLOAD_FIELD = "uploadField";
    private static final String ACCOUNT_NUMBER_FIELD = "accountNumberField";
    private static final String PRODUCT_FAMILY_FIELD = "productFamilyField";
    private static final String ACCOUNT_NAME_FIELD = "accountNameField";
    private static final String PAYMENT_DATE_WIDGET = "paymentDateWidget";
    private static final String GROSS_AMOUNT_FIELD = "grossAmountField";
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String INVALID_USAGE_BATCH_LENGTH_MESSAGE = "Field value should not exceed 50 characters";
    private static final String USAGE_BATCH_EXISTS_MESSAGE = "Usage Batch with such name already exists";
    private static final String INVALID_NUMBER_LENGTH_MESSAGE = "Field value should not exceed 10 digits";
    private static final String INVALID_GROSS_AMOUNT_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String WIDTH_CALC = "calc(99.9% - 0rem)";

    private UsageBatchUploadWindow window;
    private IFasUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(IFasUsageController.class);
    }

    @Test
    public void testConstructor() {
        var fasRro = new Rightsholder();
        fasRro.setAccountNumber(FAS_RRO_ACCOUNT_NUMBER);
        fasRro.setName(FAS_RRO_ACCOUNT_NAME);
        var claRro = new Rightsholder();
        claRro.setAccountNumber(CLA_RRO_ACCOUNT_NUMBER);
        claRro.setName(CLA_RRO_ACCOUNT_NAME);
        expect(controller.getClaAccountNumber()).andReturn(CLA_RRO_ACCOUNT_NUMBER).times(2);
        expect(controller.getRightsholder(FAS_RRO_ACCOUNT_NUMBER)).andReturn(fasRro).once();
        expect(controller.getRightsholder(CLA_RRO_ACCOUNT_NUMBER)).andReturn(claRro).once();
        expect(controller.getRightsholder(UNKNOWN_ACCOUNT_NAME)).andReturn(new Rightsholder()).once();
        replay(controller);
        window = new UsageBatchUploadWindow(controller);
        verifyWindow(window, "Upload Usage Batch", "500px", "570px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testIsValidUsageBatchNameField() {
        String batchName = "Existing Batch Name";
        expect(controller.usageBatchExists(batchName)).andReturn(true).times(2);
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new UsageBatchUploadWindow(controller);
        TextField usageBatchNameField = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        assertFieldValidationMessage(usageBatchNameField, StringUtils.EMPTY, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, "   ", binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, StringUtils.repeat('a', 51), binder,
            INVALID_USAGE_BATCH_LENGTH_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, batchName, binder, USAGE_BATCH_EXISTS_MESSAGE, false);
        assertFieldValidationMessage(usageBatchNameField, USAGE_BATCH_NAME, binder, null, true);
        verify(controller);
    }

    @Test
    public void testIsValidAccountNumberField() {
        replay(controller);
        window = new UsageBatchUploadWindow(controller);
        LongField accountNumberField = Whitebox.getInternalState(window, ACCOUNT_NUMBER_FIELD);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        assertFieldValidationMessage(accountNumberField, 0L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        assertFieldValidationMessage(accountNumberField, 1L, binder, null, true);
        assertFieldValidationMessage(accountNumberField, FAS_RRO_ACCOUNT_NUMBER, binder, null, true);
        assertFieldValidationMessage(accountNumberField, 9999999999L, binder, null, true);
        assertFieldValidationMessage(accountNumberField, 10000000000L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testIsValidGrossAmountField() {
        replay(controller);
        window = new UsageBatchUploadWindow(controller);
        BigDecimalField grossAmountField = Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD);
        Binder<?> binder = Whitebox.getInternalState(window, "binder");
        assertFieldValidationMessage(grossAmountField, null, binder, EMPTY_FIELD_MESSAGE, false);
        assertFieldValidationMessage(grossAmountField, BigDecimal.ZERO, binder, INVALID_GROSS_AMOUNT_MESSAGE,
            false);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("0.00"), binder,
            INVALID_GROSS_AMOUNT_MESSAGE, false);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("0.009"), binder,
            INVALID_GROSS_AMOUNT_MESSAGE, false);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("10000000000.00"), binder,
            INVALID_GROSS_AMOUNT_MESSAGE, false);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("0.01"), binder, null, true);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("123.5684"), binder, null, true);
        assertFieldValidationMessage(grossAmountField, new BigDecimal("9999999999.99"), binder, null, true);
        verify(controller);
    }

    @Test
    public void testIsValid() {
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new UsageBatchUploadWindow(controller);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.setInternalState(uploadField, "fileName", "test.csv");
        assertFalse(window.isValid());
        setLongFieldValue(window, ACCOUNT_NUMBER_FIELD, FAS_RRO_ACCOUNT_NUMBER.toString());
        assertFalse(window.isValid());
        TextField accountNameField = Whitebox.getInternalState(window, ACCOUNT_NAME_FIELD);
        accountNameField.setReadOnly(false);
        accountNameField.setValue(RRO_NAME);
        accountNameField.setReadOnly(true);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        setBigDecimalFieldValue(window, GROSS_AMOUNT_FIELD, GROSS_AMOUNT);
        assertFalse(window.isValid());
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        assertTrue(window.isValid());
        verify(controller);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        var rro = new Rightsholder();
        rro.setAccountNumber(FAS_RRO_ACCOUNT_NUMBER);
        rro.setName(RRO_NAME);
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        UsageCsvProcessor processor = createMock(UsageCsvProcessor.class);
        var paymentDateWidget = new LocalDateWidget("Payment Date");
        paymentDateWidget.setValue(PAYMENT_DATE);
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(UsageBatchUploadWindow.class, new String[]{"isValid", "close"}, controller);
        Whitebox.setInternalState(window, UPLOAD_FIELD, uploadField);
        expect(window.isValid()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        expect(controller.getCsvProcessor(FAS_PRODUCT_FAMILY)).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(controller.loadUsageBatch(buildUsageBatch(rro), processingResult.get())).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, controller, uploadField, processor);
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        setLongFieldValue(window, ACCOUNT_NUMBER_FIELD, FAS_RRO_ACCOUNT_NUMBER.toString());
        setTextFieldValue(window, PRODUCT_FAMILY_FIELD, FAS_PRODUCT_FAMILY);
        setTextFieldValue(window, ACCOUNT_NAME_FIELD, RRO_NAME);
        Whitebox.setInternalState(window, "rro", rro);
        ((LocalDateWidget) Whitebox.getInternalState(window, PAYMENT_DATE_WIDGET)).setValue(PAYMENT_DATE);
        setBigDecimalFieldValue(window, GROSS_AMOUNT_FIELD, GROSS_AMOUNT);
        window.onUploadClicked();
        verify(Windows.class, window, controller, uploadField, processor);
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

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var verticalLayout = (VerticalLayout) component;
        assertEquals(5, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponentAt(0));
        verifyUploadComponent(verticalLayout.getComponentAt(1), WIDTH_CALC);
        verifyRightsholdersComponents(verticalLayout.getComponentAt(2));
        verifyDateComponents(verticalLayout.getComponentAt(3));
        verifyGrossAmount(verticalLayout.getComponentAt(4));
        verifyButtonsLayout(getFooterLayout(window));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        var textField = verifyTextField(component, "Usage Batch Name", WIDTH_CALC, "usage-batch-name-field");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        var uploadButton = verifyButton(horizontalLayout.getComponentAt(0), "Upload", true);
        verifyLoadClickListener(uploadButton);
        verifyButton(horizontalLayout.getComponentAt(1), "Close", true);
    }

    private void verifyRightsholdersComponents(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        var horizontalLayout = (HorizontalLayout) verticalLayout.getComponentAt(0);
        var accountNameField = verifyTextField(verticalLayout.getComponentAt(1), "RRO Account Name",
            "rro-account-name-field");
        assertTrue(accountNameField.isReadOnly());
        assertEquals(3, horizontalLayout.getComponentCount());
        var accountNumberField = verifyLongField(horizontalLayout.getComponentAt(0), "RRO Account #", "50%",
            "rro-account-number-field");
        var productFamilyField = verifyTextField(horizontalLayout.getComponentAt(1), "Product Family", "130px",
            "product-family-field");
        assertTrue(productFamilyField.isReadOnly());
        verifyVerifyButton(horizontalLayout.getComponentAt(2), accountNumberField, accountNameField,
            productFamilyField);
    }

    private void verifyVerifyButton(Component component, LongField accountNumberField, TextField accountNameField,
                                    TextField productFamilyField) {
        assertThat(component, instanceOf(Button.class));
        var verifyButton = (Button) component;
        assertEquals("Verify", verifyButton.getText());
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        accountNumberField.setValue(123456789987654321L);
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        accountNumberField.setValue(FAS_RRO_ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals(FAS_RRO_ACCOUNT_NAME, accountNameField.getValue());
        assertEquals(FAS_PRODUCT_FAMILY, productFamilyField.getValue());
        accountNumberField.setValue(CLA_RRO_ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals(CLA_RRO_ACCOUNT_NAME, accountNameField.getValue());
        assertEquals(FAS2_PRODUCT_FAMILY, productFamilyField.getValue());
        accountNumberField.setValue(UNKNOWN_ACCOUNT_NAME);
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
    }

    private void verifyDateComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        var paymentDateLayout = horizontalLayout.getComponentAt(0);
        var fiscalYearLayout = horizontalLayout.getComponentAt(1);
        assertThat(paymentDateLayout, instanceOf(LocalDateWidget.class));
        var localDateWidget = (LocalDateWidget) paymentDateLayout;
        assertEquals("Payment Date", localDateWidget.getLabel());
        var fiscalYearField = verifyTextField(fiscalYearLayout, "Fiscal Year", WIDTH_CALC, "fiscal-year-field");
        assertTrue(fiscalYearField.isReadOnly());
        assertEquals(StringUtils.EMPTY, fiscalYearField.getValue());
        localDateWidget.setValue(LocalDate.of(2017, 2, 2));
        assertEquals(FISCAL_YEAR, fiscalYearField.getValue());
    }

    private void verifyGrossAmount(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(1, horizontalLayout.getComponentCount());
        BigDecimalField grossAmountField = verifyBigDecimalField(horizontalLayout.getComponentAt(0),
            "Gross Amount in USD", WIDTH_CALC, "gross-amount-field");
        Component prefixComponent = grossAmountField.getPrefixComponent();
        assertNotNull(prefixComponent);
        assertThat(prefixComponent, instanceOf(Icon.class));
    }

    private UsageBatch buildUsageBatch(Rightsholder rro) {
        var usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setRro(rro);
        usageBatch.setProductFamily(FAS_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(2017);
        usageBatch.setGrossAmount(new BigDecimal(GROSS_AMOUNT));
        return usageBatch;
    }
}
