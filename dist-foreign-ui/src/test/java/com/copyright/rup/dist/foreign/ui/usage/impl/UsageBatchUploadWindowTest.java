package com.copyright.rup.dist.foreign.ui.usage.impl;

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
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UsageBatchUploadWindow}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/21/17
 *
 * @author Mikita Hladkikh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, UsageBatchUploadWindow.class})
public class UsageBatchUploadWindowTest {

    private static final String ACCOUNT_NUMBER = "1000001863";
    private static final String USAGE_BATCH_NAME = "BatchName";
    private static final String RRO_NAME = "RRO name";
    private static final String ACCOUNT_NAME = "Account Name";
    private static final String INVALID_GROSS_AMOUNT_ERROR_MESSAGE =
        "Field value should be positive number and not exceed 10 digits";
    private static final String GROSS_AMOUNT_FIELD = "grossAmountField";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2017, 2, 27);
    private UsageBatchUploadWindow window;
    private IUsagesController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(IUsagesController.class);
    }

    @Test
    public void testConstructor() {
        Rightsholder fasRro = new Rightsholder();
        Long rroAccountNumber = Long.valueOf(ACCOUNT_NUMBER);
        fasRro.setAccountNumber(rroAccountNumber);
        fasRro.setName("CANADIAN CERAMIC SOCIETY");
        fasRro.setId(RupPersistUtils.generateUuid());
        Rightsholder claRro = new Rightsholder();
        claRro.setAccountNumber(2000017000L);
        claRro.setName("CLA, The Copyright Licensing Agency Ltd.");
        claRro.setId(RupPersistUtils.generateUuid());
        expect(usagesController.getRro(rroAccountNumber)).andReturn(fasRro).once();
        expect(usagesController.getRro(2000017000L)).andReturn(claRro).once();
        expect(usagesController.getRro(20000170L)).andReturn(new Rightsholder()).once();
        replay(usagesController);
        window = new UsageBatchUploadWindow(usagesController);
        assertEquals("Upload Usage Batch", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(350, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        replay(usagesController);
        window = new UsageBatchUploadWindow(usagesController);
        assertFalse(window.isValid());
        UploadField uploadField = Whitebox.getInternalState(window, UploadField.class);
        Whitebox.getInternalState(uploadField, TextField.class).setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "accountNumberField")).setValue(ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        TextField rhNameField = Whitebox.getInternalState(window, "accountNameField");
        rhNameField.setReadOnly(false);
        rhNameField.setValue(ACCOUNT_NAME);
        rhNameField.setReadOnly(true);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD)).setValue("100.00");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "usageBatchNameField")).setValue(USAGE_BATCH_NAME);
        assertTrue(window.isValid());
        verify(usagesController);
    }

    @Test
    public void testIsValidGrossAmountField() {
        replay(usagesController);
        window = new UsageBatchUploadWindow(usagesController);
        TextField grossAmountField = Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD);
        Binder binder = Whitebox.getInternalState(window, "binder");
        validateField(grossAmountField, "123.5684", binder, true);
        validateField(grossAmountField, "0.00", binder, false);
        validateField(grossAmountField, "value", binder, false);
        validateField(grossAmountField, "10000000000.00", binder, false);
        validateField(grossAmountField, "9999999999.99", binder, true);
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(Long.valueOf(ACCOUNT_NUMBER));
        rro.setName(RRO_NAME);
        rro.setId(RupPersistUtils.generateUuid());
        UploadField uploadField = createPartialMock(UploadField.class, "getStreamToUploadedFile");
        UsageCsvProcessor processor = createMock(UsageCsvProcessor.class);
        LocalDateWidget paymentDateWidget = new LocalDateWidget("Payment Date");
        paymentDateWidget.setValue(PAYMENT_DATE);
        ProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(UsageBatchUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, "usageBatchNameField", new TextField("Usage Batch Name", USAGE_BATCH_NAME));
        Whitebox.setInternalState(window, "accountNumberField", new TextField("RRO Account #", ACCOUNT_NUMBER));
        Whitebox.setInternalState(window, "paymentDateWidget", paymentDateWidget);
        Whitebox.setInternalState(window, "fiscalYearField", new TextField("FY2017"));
        Whitebox.setInternalState(window, GROSS_AMOUNT_FIELD, new TextField("Gross Amount", "100.00"));
        Whitebox.setInternalState(window, "accountNameField", new TextField(RRO_NAME));
        Whitebox.setInternalState(window, "productFamilyField", new TextField("Product Family", "FAS"));
        Whitebox.setInternalState(window, "rro", rro);
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getCsvProcessor("FAS")).andReturn(processor).once();
        expect(processor.process(anyObject())).andReturn(processingResult).once();
        expect(usagesController.loadUsageBatch(buildUsageBatch(rro), processingResult.get())).andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        Windows.showNotificationWindow("Upload completed: 1 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
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
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyRightsholdersComponents(verticalLayout.getComponent(2));
        verifyDateComponents(verticalLayout.getComponent(3));
        verifyGrossAmount(verticalLayout.getComponent(4));
        verifyButtonsLayout(verticalLayout.getComponent(5));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        assertTrue(component instanceof TextField);
        TextField textField = (TextField) component;
        assertEquals(100, component.getWidth(), 0);
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(ClickEvent.class).size());
        verifyLoadClickListener(loadButton);
    }

    private void verifyLoadClickListener(Button loadButton) {
        mockStatic(Windows.class);
        Collection<? extends AbstractField<?>> fields = Lists.newArrayList(
            Whitebox.getInternalState(window, "usageBatchNameField"),
            Whitebox.getInternalState(window, "uploadField"),
            Whitebox.getInternalState(window, "accountNumberField"),
            Whitebox.getInternalState(window, "accountNameField"),
            Whitebox.getInternalState(window, "paymentDateWidget"),
            Whitebox.getInternalState(window, GROSS_AMOUNT_FIELD));
        Windows.showValidationErrorWindow(fields);
        expectLastCall().once();
        replay(Windows.class);
        loadButton.click();
        verify(Windows.class);
        reset(Windows.class);
    }

    private Button verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyRightsholdersComponents(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(0);
        assertEquals(2, horizontalLayout.getComponentCount());
        HorizontalLayout rroAccountLayout = (HorizontalLayout) horizontalLayout.getComponent(0);
        Component numberComponent = rroAccountLayout.getComponent(0);
        Component productFamilyComponent = rroAccountLayout.getComponent(1);
        Component verifyComponent = horizontalLayout.getComponent(1);
        TextField numberField = verifyTextField(numberComponent, "RRO Account #");
        assertEquals(100, numberField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, numberField.getWidthUnits());
        Collection<?> listeners = numberField.getListeners(ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(2, listeners.size());
        TextField nameField = verifyTextField(verticalLayout.getComponent(1), "RRO Account Name");
        assertTrue(nameField.isReadOnly());
        TextField productFamilyField = verifyTextField(productFamilyComponent, "Product Family");
        assertTrue(productFamilyField.isReadOnly());
        assertVerifyButton(verifyComponent, numberField, nameField, productFamilyField);
    }

    private void assertVerifyButton(Component verifyComponent, TextField numberField, TextField nameField,
                                    TextField productFamilyField) {
        assertTrue(verifyComponent instanceof Button);
        Button verifyButton = (Button) verifyComponent;
        assertEquals("Verify", verifyComponent.getCaption());
        assertEquals(1, verifyButton.getListeners(ClickEvent.class).size());
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        numberField.setValue("value");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        numberField.setValue("123456789874541246");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
        numberField.setValue(ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals("CANADIAN CERAMIC SOCIETY", nameField.getValue());
        assertEquals("FAS", productFamilyField.getValue());
        numberField.setValue("2000017000");
        verifyButton.click();
        assertEquals("CLA, The Copyright Licensing Agency Ltd.", nameField.getValue());
        assertEquals("FAS2", productFamilyField.getValue());
        numberField.setValue("20000170");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        assertEquals(StringUtils.EMPTY, productFamilyField.getValue());
    }

    private void verifyDateComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Component paymentDateComponent = horizontalLayout.getComponent(0);
        Component fiscalYearComponent = horizontalLayout.getComponent(1);
        assertTrue(paymentDateComponent instanceof LocalDateWidget);
        LocalDateWidget widget = (LocalDateWidget) paymentDateComponent;
        assertEquals("Payment Date", widget.getCaption());
        TextField fiscalYearField = verifyTextField(fiscalYearComponent, "Fiscal Year");
        assertTrue(fiscalYearField.isReadOnly());
        assertEquals(StringUtils.EMPTY, fiscalYearField.getValue());
        widget.setValue(LocalDate.of(2017, 2, 2));
        assertEquals("FY2017", fiscalYearField.getValue());
    }

    private void verifyGrossAmountComponent(Component component) {
        TextField grossAmountField = verifyTextField(component, "Gross Amount in USD");
        assertEquals(100, grossAmountField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, grossAmountField.getWidthUnits());
    }

    private void verifyGrossAmount(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyGrossAmountComponent(horizontalLayout.getComponent(0));
    }

    private void validateField(TextField field, String value, Binder binder, boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(INVALID_GROSS_AMOUNT_ERROR_MESSAGE));
    }

    private TextField verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        return (TextField) component;
    }

    private UsageBatch buildUsageBatch(Rightsholder rro) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(2017);
        usageBatch.setGrossAmount(new BigDecimal("100.00"));
        return usageBatch;
    }
}
