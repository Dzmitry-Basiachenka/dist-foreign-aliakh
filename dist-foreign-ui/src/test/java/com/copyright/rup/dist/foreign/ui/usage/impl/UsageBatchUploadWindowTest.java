package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.CurrencyEnum;
import com.copyright.rup.dist.foreign.ui.component.CsvUploadComponent;
import com.copyright.rup.dist.foreign.ui.component.LocalDateWidget;
import com.copyright.rup.dist.foreign.ui.component.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.component.validator.NumberValidator;
import com.copyright.rup.vaadin.ui.Windows;

import com.google.common.collect.Lists;
import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToBigDecimalConverter;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
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

import java.time.LocalDate;
import java.util.Collection;

/**
 * Verifies {@link UsageBatchUploadWindow}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/21/2017
 *
 * @author Mikita Hladkikh
 */
@RunWith(PowerMockRunner.class)
public class UsageBatchUploadWindowTest {

    private static final String ACCOUNT_NUMBER = "1234567890";
    private UsageBatchUploadWindow window;

    @Before
    public void setUp() {
        window = new UsageBatchUploadWindow();
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testConstructor() {
        assertEquals("Upload Usage Batch", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(305, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testIsValid() {
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "usageBatchNameField")).setValue("BatchName");
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, CsvUploadComponent.class).getFileNameField().setValue("test.csv");
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "accountNumberField")).setValue(ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        ((TextField) Whitebox.getInternalState(window, "grossAmountField")).setValue("100");
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, ComboBox.class).setValue(CurrencyEnum.EUR);
        assertTrue(window.isValid());
    }

    @Test
    @PrepareForTest(Windows.class)
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        ((TextField) Whitebox.getInternalState(window, "usageBatchNameField")).setValue("BatchName");
        Whitebox.getInternalState(window, CsvUploadComponent.class).getFileNameField().setValue("test.csv");
        ((TextField) Whitebox.getInternalState(window, "accountNumberField")).setValue(ACCOUNT_NUMBER);
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        ((TextField) Whitebox.getInternalState(window, "grossAmountField")).setValue("100");
        Whitebox.getInternalState(window, ComboBox.class).setValue(CurrencyEnum.EUR);
        Windows.showNotificationWindow("Uploading is started");
        replay(Windows.class);
        window.onUploadClicked();
        verify(Windows.class);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponent(0));
        verifyUploadComponent(verticalLayout.getComponent(1));
        verifyRightsholdersComponents(verticalLayout.getComponent(2));
        verifyDateComponents(verticalLayout.getComponent(3));
        verifyReportedCurrencyAndGrossAmount(verticalLayout.getComponent(4));
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
        assertTrue(component instanceof CsvUploadComponent);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        CsvUploadComponent uploadComponent = (CsvUploadComponent) component;
        verifyRequiredField(uploadComponent.getFileNameField());
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
        Windows.showValidationErrorWindow(Lists.newArrayList(
            Whitebox.getInternalState(window, "usageBatchNameField"),
            Whitebox.getInternalState(window, CsvUploadComponent.class).getFileNameField(),
            Whitebox.getInternalState(window, "accountNumberField"),
            Whitebox.getInternalState(window, LocalDateWidget.class),
            Whitebox.getInternalState(window, "grossAmountField"),
            Whitebox.getInternalState(window, ComboBox.class)));
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
        TextField nameField = verifyTextField(nameComponent, "RRO Account Name");
        assertTrue(nameField.isReadOnly());
        assertTrue(verifyComponent instanceof Button);
        Button verifyButton = (Button) verifyComponent;
        assertEquals("Verify", verifyComponent.getCaption());
        assertEquals(1, verifyButton.getListeners(ClickEvent.class).size());
        assertEquals(StringUtils.EMPTY, nameField.getValue());
        numberField.setValue(ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals(ACCOUNT_NUMBER, nameField.getValue());
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
        TextField grossAmountField = verifyTextField(component, "Gross Amount (USD)");
        assertEquals(100, grossAmountField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, grossAmountField.getWidthUnits());
        verifyRequiredField(grossAmountField);
        assertEquals(StringUtils.EMPTY, grossAmountField.getNullRepresentation());
        Converter<String, Object> converter = grossAmountField.getConverter();
        assertNotNull(converter);
        assertEquals(StringToBigDecimalConverter.class, converter.getClass());
        assertEquals("Field value should contain numeric values only", grossAmountField.getConversionError());
        Collection<Validator> validators = grossAmountField.getValidators();
        assertTrue(CollectionUtils.isNotEmpty(validators));
        assertEquals(1, validators.size());
        assertTrue(validators.iterator().next() instanceof AmountValidator);
    }

    private void verifyReportedCurrencyAndGrossAmount(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Component reportedCurrencyComponent = horizontalLayout.getComponent(0);
        assertTrue(reportedCurrencyComponent instanceof ComboBox);
        ComboBox comboBox = (ComboBox) reportedCurrencyComponent;
        verifyRequiredField(comboBox);
        assertArrayEquals(CurrencyEnum.values(), comboBox.getItemIds().toArray());
        verifyGrossAmountComponent(horizontalLayout.getComponent(1));
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
}
