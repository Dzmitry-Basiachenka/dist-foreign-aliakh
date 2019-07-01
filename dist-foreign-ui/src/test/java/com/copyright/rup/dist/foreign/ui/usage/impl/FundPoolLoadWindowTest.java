package com.copyright.rup.dist.foreign.ui.usage.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.reset;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link FundPoolLoadWindow}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 11/29/2018
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, SecurityUtils.class, FundPoolLoadWindow.class})
public class FundPoolLoadWindowTest {

    private static final String PERIOD_FROM = "2000";
    private static final String PERIOD_TO = "2000";
    private static final String AMOUNT = "100.00";
    private static final String MIN_AMOUNT = "10.00";
    private static final String ZERO_AMOUNT = "0.00";
    private static final String ACCOUNT_NUMBER = "1000001863";
    private static final String RRO_NAME = "RRO name";
    private static final String USAGE_BATCH_NAME = "BatchName";
    private static final String PERIOD_FROM_FIELD = "fundPoolPeriodFromField";
    private static final String PERIOD_TO_FIELD = "fundPoolPeriodToField";
    private static final String STM_FIELD = "stmAmountField";
    private static final String NON_STM_FIELD = "nonStmAmountField";
    private static final String STM_MIN_FIELD = "stmMinAmountField";
    private static final String NON_STM_MIN_FIELD = "nonStmMinAmountField";
    private static final String INVALID_PERIOD_ERROR_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final LocalDate PAYMENT_DATA_WIDGET = LocalDate.of(2019, 6, 20);
    private FundPoolLoadWindow window;
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
        window = new FundPoolLoadWindow(usagesController);
        assertEquals("Load Fund Pool", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(330, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
        verify(usagesController);
    }

    @Test
    public void testIsValid() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).anyTimes();
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        assertFalse(window.isValid());
        setTextFieldValue("usageBatchNameField", USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        setTextFieldValue("accountNumberField", ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        TextField rhNameField = Whitebox.getInternalState(window, "accountNameField");
        rhNameField.setReadOnly(false);
        rhNameField.setValue("Account Name");
        rhNameField.setReadOnly(true);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        setTextFieldValue(PERIOD_TO_FIELD, PERIOD_TO);
        setTextFieldValue(PERIOD_FROM_FIELD, PERIOD_FROM);
        assertFalse(window.isValid());
        setTextFieldValue(STM_FIELD, AMOUNT);
        setTextFieldValue(NON_STM_FIELD, AMOUNT);
        setTextFieldValue(STM_MIN_FIELD, MIN_AMOUNT);
        setTextFieldValue(NON_STM_MIN_FIELD, MIN_AMOUNT);
        assertFalse(window.isValid());
        setTextFieldValue("marketValidationField", "2");
        assertTrue(window.isValid());
        setTextFieldValue(STM_FIELD, AMOUNT);
        setTextFieldValue(NON_STM_FIELD, ZERO_AMOUNT);
        assertTrue(window.isValid());
        setTextFieldValue(STM_FIELD, ZERO_AMOUNT);
        setTextFieldValue(NON_STM_FIELD, AMOUNT);
        assertTrue(window.isValid());
        setTextFieldValue(STM_FIELD, ZERO_AMOUNT);
        setTextFieldValue(NON_STM_FIELD, "0");
        assertFalse(window.isValid());
        verify(usagesController);
    }

    @Test
    public void testAmountFieldValidation() {
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        verifyAmountFieldValidation(STM_FIELD);
        verifyAmountFieldValidation(NON_STM_FIELD);
        verifyAmountFieldValidation(STM_MIN_FIELD);
        verifyAmountFieldValidation(NON_STM_MIN_FIELD);
        verify(usagesController);
    }

    @Test
    public void testFundPoolPeriodValidation() {
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "stringBinder");
        TextField periodFrom = Whitebox.getInternalState(window, PERIOD_FROM_FIELD);
        TextField periodTo = Whitebox.getInternalState(window, PERIOD_TO_FIELD);
        verifyFieldValidationMessage(periodFrom, "1000", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyFieldValidationMessage(periodTo, "1000", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyFieldValidationMessage(periodFrom, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        verifyFieldValidationMessage(periodTo, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        periodTo.setValue("2001");
        verifyFieldValidationMessage(periodFrom, "2018", binder, INVALID_PERIOD_ERROR_MESSAGE, true);
        periodFrom.setValue("2001");
        verifyFieldValidationMessage(periodTo, "2018", binder, INVALID_PERIOD_ERROR_MESSAGE, true);
        periodFrom.setValue("2005");
        verifyFieldValidationMessage(periodTo, "2004", binder,
            "Field value should be greater or equal to Fund pool period from", false);
        verifyFieldValidationMessage(periodTo, "2005", binder,
            "Field value should be greater or equal to Fund pool period from", true);
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        Capture<UsageBatch> usageBatchCapture = new Capture<>();
        window = createPartialMock(FundPoolLoadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        initUploadComponents();
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getUsagesCountForNtsBatch(capture(usageBatchCapture))).andReturn(3).once();
        expect(usagesController.loadNtsBatch(capture(usageBatchCapture))).andReturn(3).once();
        Windows.showNotificationWindow("Upload completed: 3 record(s) were stored successfully");
        expectLastCall().once();
        replay(window, usagesController, Windows.class);
        window.onUploadClicked();
        UsageBatch usageBatch = usageBatchCapture.getValue();
        assertEquals(USAGE_BATCH_NAME, usageBatch.getName());
        assertEquals(Long.valueOf(ACCOUNT_NUMBER), usageBatch.getRro().getAccountNumber());
        assertEquals(RRO_NAME, usageBatch.getRro().getName());
        assertEquals(Integer.valueOf(2019), usageBatch.getFiscalYear());
        FundPool fundPool = usageBatch.getFundPool();
        assertEquals(Integer.valueOf(PERIOD_FROM), fundPool.getFundPoolPeriodFrom());
        assertEquals(Integer.valueOf(PERIOD_TO), fundPool.getFundPoolPeriodTo());
        assertEquals(new BigDecimal(AMOUNT), fundPool.getStmAmount());
        assertEquals(new BigDecimal(AMOUNT), fundPool.getNonStmAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), fundPool.getStmMinimumAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), fundPool.getNonStmMinimumAmount());
        assertNotNull(usageBatch);
        verify(window, usagesController, Windows.class);
    }

    @Test
    public void testOnUploadClickedValidFieldsNoRecords() {
        mockStatic(Windows.class);
        Capture<UsageBatch> usageBatchCapture = new Capture<>();
        window = createPartialMock(FundPoolLoadWindow.class, "isValid");
        Whitebox.setInternalState(window, "usagesController", usagesController);
        initUploadComponents();
        expect(window.isValid()).andReturn(true).once();
        expect(usagesController.getUsagesCountForNtsBatch(capture(usageBatchCapture))).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages matching selected Markets and Fund Pool Period");
        expectLastCall().once();
        replay(window, usagesController, Windows.class);
        window.onUploadClicked();
        UsageBatch usageBatch = usageBatchCapture.getValue();
        assertNotNull(usageBatch);
        verify(window, usagesController, Windows.class);
    }

    private void initUploadComponents() {
        Rightsholder rro = new Rightsholder();
        rro.setAccountNumber(Long.valueOf(ACCOUNT_NUMBER));
        rro.setName(RRO_NAME);
        rro.setId(RupPersistUtils.generateUuid());
        LocalDateWidget paymentDateWidget = new LocalDateWidget("Payment Date");
        paymentDateWidget.setValue(PAYMENT_DATA_WIDGET);
        Whitebox.setInternalState(window, "paymentDateWidget", paymentDateWidget);
        Whitebox.setInternalState(window, "rro", rro);
        setTextField("usageBatchNameField", USAGE_BATCH_NAME);
        setTextField("accountNumberField", ACCOUNT_NUMBER);
        setTextField(PERIOD_TO_FIELD, PERIOD_TO);
        setTextField(PERIOD_FROM_FIELD, PERIOD_FROM);
        setTextField(STM_FIELD, AMOUNT);
        setTextField(NON_STM_FIELD, AMOUNT);
        setTextField(STM_MIN_FIELD, MIN_AMOUNT);
        setTextField(NON_STM_MIN_FIELD, MIN_AMOUNT);
        setTextField("marketValidationField", "2");
    }

    private void verifyAmountFieldValidation(String fieldName) {
        TextField grossAmountField = Whitebox.getInternalState(window, fieldName);
        Binder binder = Whitebox.getInternalState(window, "binder");
        verifyAmountValidationMessage(grossAmountField, "123.5684", binder, true);
        verifyAmountValidationMessage(grossAmountField, ZERO_AMOUNT, binder, true);
        verifyAmountValidationMessage(grossAmountField, "value", binder, false);
        verifyAmountValidationMessage(grossAmountField, "10000000000.00", binder, false);
        verifyAmountValidationMessage(grossAmountField, "9999999999.99", binder, true);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(7, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponent(0));
        verifyRightsholdersComponents(verticalLayout.getComponent(1));
        verifyDateComponents(verticalLayout.getComponent(2));
        verifyMarketsWidget(verticalLayout.getComponent(3));
        verifyAmountsComponent(verticalLayout.getComponent(4));
        verifyMinAmountsComponent(verticalLayout.getComponent(5));
        verifyButtonsLayout(verticalLayout.getComponent(6));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        assertTrue(component instanceof TextField);
        TextField textField = (TextField) component;
        assertEquals(100, component.getWidth(), 0);
        assertEquals(StringUtils.EMPTY, textField.getValue());
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
            Whitebox.getInternalState(window, "accountNumberField"),
            Whitebox.getInternalState(window, "accountNameField"),
            Whitebox.getInternalState(window, "paymentDateWidget"),
            Whitebox.getInternalState(window, PERIOD_TO_FIELD),
            Whitebox.getInternalState(window, PERIOD_FROM_FIELD),
            Whitebox.getInternalState(window, "marketValidationField"),
            Whitebox.getInternalState(window, STM_FIELD),
            Whitebox.getInternalState(window, NON_STM_FIELD),
            Whitebox.getInternalState(window, STM_MIN_FIELD),
            Whitebox.getInternalState(window, NON_STM_MIN_FIELD));
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
        HorizontalLayout rroAccountLayout = (HorizontalLayout) component;
        assertEquals(3, rroAccountLayout.getComponentCount());
        Component numberComponent = rroAccountLayout.getComponent(0);
        Component nameComponent = rroAccountLayout.getComponent(1);
        Component verifyComponent = rroAccountLayout.getComponent(2);
        TextField numberField = verifyTextField(numberComponent, "RRO Account #");
        assertEquals(100, numberField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, numberField.getWidthUnits());
        Collection<?> listeners = numberField.getListeners(ValueChangeEvent.class);
        assertTrue(CollectionUtils.isNotEmpty(listeners));
        assertEquals(2, listeners.size());
        TextField nameField = verifyTextField(nameComponent, "RRO Account Name");
        assertTrue(nameField.isReadOnly());
        assertVerifyButton(verifyComponent, numberField, nameField);
    }

    private void assertVerifyButton(Component verifyComponent, TextField numberField, TextField nameField) {
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
        numberField.setValue("2000017000");
        verifyButton.click();
        assertEquals("CLA, The Copyright Licensing Agency Ltd.", nameField.getValue());
        numberField.setValue("20000170");
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, nameField.getValue());
    }

    private void verifyMarketsWidget(Component component) {
        assertTrue(component instanceof MarketFilterWidget);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals("Markets", button.getCaption());
        assertEquals(2, button.getListeners(Button.ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyDateComponents(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Component paymentDateComponent = horizontalLayout.getComponent(0);
        Component fundPoolPeriodFromComponent = horizontalLayout.getComponent(1);
        Component fundPoolPeriodToComponent = horizontalLayout.getComponent(2);
        assertTrue(paymentDateComponent instanceof LocalDateWidget);
        LocalDateWidget widget = (LocalDateWidget) paymentDateComponent;
        assertEquals("Payment Date", widget.getCaption());
        assertTrue(fundPoolPeriodFromComponent instanceof TextField);
        verifyTextField(fundPoolPeriodFromComponent, "Fund Pool Period From");
        assertTrue(fundPoolPeriodToComponent instanceof TextField);
        verifyTextField(fundPoolPeriodToComponent, "Fund Pool Period To");
    }

    private void verifyAmountsComponent(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyAmountComponent(horizontalLayout.getComponent(0), "STM Amount", "");
        verifyAmountComponent(horizontalLayout.getComponent(1), "Non-STM Amount", "");
    }

    private void verifyMinAmountsComponent(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyAmountComponent(horizontalLayout.getComponent(0), "STM Minimum Amount", "50");
        verifyAmountComponent(horizontalLayout.getComponent(1), "Non-STM Minimum Amount", "7");
    }

    private void verifyAmountComponent(Component component, String caption, String defaultValue) {
        TextField amountField = verifyTextField(component, caption);
        assertEquals(100, amountField.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, amountField.getWidthUnits());
        assertEquals(defaultValue, amountField.getValue());
    }

    private void verifyAmountValidationMessage(TextField field, String value, Binder binder, boolean isValid) {
        verifyFieldValidationMessage(field, value, binder,
            "Field value should be positive number or zero and should not exceed 10 digits", isValid);
    }

    private void verifyFieldValidationMessage(TextField field, String value, Binder binder, String message,
                                              boolean isValid) {
        field.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }

    private TextField verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(caption, component.getCaption());
        return (TextField) component;
    }

    private void setTextField(String field, String value) {
        Whitebox.setInternalState(window, field, new TextField(field, value));
    }

    private void setTextFieldValue(String field, String value) {
        ((TextField) Whitebox.getInternalState(window, field)).setValue(value);
    }
}
