package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.CsvProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csvprocessor.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.component.validator.GrossAmountValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.NumberValidator;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
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
import java.util.Collections;
import java.util.Iterator;

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
        "<div>Field should be greater than 0 and contain 2 decimals</div>\n";
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
        expect(usagesController.getRroName(Long.valueOf(ACCOUNT_NUMBER))).andReturn("CANADIAN CERAMIC SOCIETY").once();
        replay(usagesController);
        window = new UsageBatchUploadWindow(usagesController);
        assertEquals("Upload Usage Batch", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(305, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
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
        grossAmountField.setValue("123.5684");
        assertFalse(grossAmountField.isValid());
        assertEquals(INVALID_GROSS_AMOUNT_ERROR_MESSAGE,
            StringEscapeUtils.unescapeHtml4(grossAmountField.getErrorMessage().getFormattedHtmlMessage()));
        grossAmountField.setValue("0.00");
        assertFalse(grossAmountField.isValid());
        assertEquals(INVALID_GROSS_AMOUNT_ERROR_MESSAGE,
            StringEscapeUtils.unescapeHtml4(grossAmountField.getErrorMessage().getFormattedHtmlMessage()));
        grossAmountField.setValue("value");
        assertFalse(grossAmountField.isValid());
        assertEquals(INVALID_GROSS_AMOUNT_ERROR_MESSAGE,
            StringEscapeUtils.unescapeHtml4(grossAmountField.getErrorMessage().getFormattedHtmlMessage()));
        grossAmountField.setValue("10000000000.00");
        assertFalse(grossAmountField.isValid());
        assertEquals(INVALID_GROSS_AMOUNT_ERROR_MESSAGE,
            StringEscapeUtils.unescapeHtml4(grossAmountField.getErrorMessage().getFormattedHtmlMessage()));
        grossAmountField.setValue("9999999999.99");
        assertTrue(grossAmountField.isValid());
        grossAmountField.setValue("98.12");
        assertTrue(grossAmountField.isValid());
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() throws Exception {
        mockStatic(Windows.class);
        UploadField uploadField =
            createPartialMock(UploadField.class, "getStreamToUploadedFile", "getFileName");
        UsageCsvProcessor processor = createMock(UsageCsvProcessor.class);
        LocalDateWidget paymentDateWidget = new LocalDateWidget("Payment Date");
        paymentDateWidget.setValue(PAYMENT_DATE);
        CsvProcessingResult<Usage> processingResult = buildCsvProcessingResult();
        window = createPartialMock(UsageBatchUploadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        Whitebox.setInternalState(window, "uploadField", uploadField);
        Whitebox.setInternalState(window, "usageBatchNameField", new TextField("Usage Batch Name", USAGE_BATCH_NAME));
        Whitebox.setInternalState(window, "accountNumberField", new TextField("RRO Account #", ACCOUNT_NUMBER));
        Whitebox.setInternalState(window, "paymentDateWidget", paymentDateWidget);
        Whitebox.setInternalState(window, "fiscalYearProperty", new ObjectProperty<>("FY2017"));
        Whitebox.setInternalState(window, GROSS_AMOUNT_FIELD, new TextField("Gross Amount", "100.00"));
        Whitebox.setInternalState(window, "rightsholderNameProperty", new ObjectProperty<>(RRO_NAME));
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getCsvProcessor()).andReturn(processor).once();
        expect(processor.process(anyObject(), anyString())).andReturn(processingResult).once();
        expect(usagesController.loadUsageBatch(buildUsageBatch(), processingResult.getResult()))
            .andReturn(1).once();
        expect(uploadField.getStreamToUploadedFile()).andReturn(createMock(ByteArrayOutputStream.class)).once();
        expect(uploadField.getFileName()).andReturn("fileName.csv").once();
        Windows.showNotificationWindow("Upload completed: 1 records were stored successfully");
        expectLastCall().once();
        replay(window, usagesController, Windows.class, processor, uploadField);
        window.onUploadClicked();
        verify(window, usagesController, Windows.class, processor, uploadField);
    }

    @Test
    public void testCsvFileExtensionValidator() {
        UsageBatchUploadWindow.CsvFileExtensionValidator validator =
            new UsageBatchUploadWindow.CsvFileExtensionValidator();
        assertEquals("File extension is incorrect", validator.getErrorMessage());
        assertTrue(validator.isValid("file.csv"));
        assertFalse(validator.isValid("file.txt"));
        assertFalse(validator.isValid(null));
    }

    private CsvProcessingResult<Usage> buildCsvProcessingResult() {
        CsvProcessingResult<Usage> processingResult =
            new CsvProcessingResult<>(Collections.emptyList(), "fileName.csv");
        processingResult.addRecord(1, new Usage());
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
        verifyRequiredField(textField);
    }

    private void verifyUploadComponent(Component component) {
        assertTrue(component instanceof UploadField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        // TODO {aradkevich} verify field is required after making adjustments to UploadField in rup-vaadin
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
        Collection<? extends Field<?>> fields = Lists.newArrayList(
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
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Component numberComponent = horizontalLayout.getComponent(0);
        Component nameComponent = horizontalLayout.getComponent(1);
        Component verifyComponent = horizontalLayout.getComponent(2);
        TextField numberField = verifyTextField(numberComponent, "RRO Account #");
        assertEquals(100, numberField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, numberField.getWidthUnits());
        verifyRequiredField(numberField);
        assertEquals(StringUtils.EMPTY, numberField.getNullRepresentation());
        Collection<Validator> validators = numberField.getValidators();
        assertTrue(CollectionUtils.isNotEmpty(validators));
        assertEquals(2, validators.size());
        assertTrue(validators.iterator().next() instanceof NumberValidator);
        Collection<?> listeners = numberField.getListeners(ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(1, listeners.size());
        TextField nameField = verifyTextField(nameComponent, "RRO Account Name");
        assertTrue(nameField.isReadOnly());
        assertTrue(verifyComponent instanceof Button);
        Button verifyButton = (Button) verifyComponent;
        assertEquals("Verify", verifyComponent.getCaption());
        assertEquals(1, verifyButton.getListeners(ClickEvent.class).size());
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue("value");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue("123456789874541246");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue(ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals("CANADIAN CERAMIC SOCIETY", nameField.getValue());
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
        verifyRequiredField(widget);
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
        verifyRequiredField(grossAmountField);
        assertEquals(StringUtils.EMPTY, grossAmountField.getNullRepresentation());
        Collection<Validator> validators = grossAmountField.getValidators();
        assertTrue(CollectionUtils.isNotEmpty(validators));
        assertEquals(1, validators.size());
        Iterator<Validator> iterator = validators.iterator();
        assertTrue(iterator.next() instanceof GrossAmountValidator);
    }

    private void verifyGrossAmount(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyGrossAmountComponent(horizontalLayout.getComponent(0));
    }

    private TextField verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        return (TextField) component;
    }

    private void verifyRequiredField(AbstractField field) {
        assertTrue(field.isRequired());
        assertEquals("Field value should be specified", field.getRequiredError());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(USAGE_BATCH_NAME);
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(Long.valueOf(ACCOUNT_NUMBER));
        rro.setName(RRO_NAME);
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(PAYMENT_DATE);
        usageBatch.setFiscalYear(2017);
        usageBatch.setGrossAmount(new BigDecimal("100.00"));
        return usageBatch;
    }
}
