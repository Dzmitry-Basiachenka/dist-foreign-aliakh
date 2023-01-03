package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.setTextFieldValue;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.newCapture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.createPartialMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.vaadin.security.SecurityUtils;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Lists;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
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
    private static final String SPACES_STRING = "   ";
    private static final String PERIOD_FROM_FIELD = "fundPoolPeriodFromField";
    private static final String PERIOD_TO_FIELD = "fundPoolPeriodToField";
    private static final String STM_FIELD = "stmAmountField";
    private static final String NON_STM_FIELD = "nonStmAmountField";
    private static final String STM_MIN_FIELD = "stmMinAmountField";
    private static final String NON_STM_MIN_FIELD = "nonStmMinAmountField";
    private static final String ACCOUNT_NUMBER_FIELD = "accountNumberField";
    private static final String MARKET_VALIDATION_FIELD = "marketValidationField";
    private static final String USAGE_BATCH_NAME_FIELD = "usageBatchNameField";
    private static final String INVALID_PERIOD_ERROR_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final String INVALID_MARKET_MESSAGE = "Please select at least one market";
    private static final String INVALID_NUMERIC_VALUE_MESSAGE = "Field value should contain numeric values only";
    private static final String INVALID_NUMBER_LENGTH_MESSAGE = "Field value should not exceed 10 digits";
    private static final String EMPTY_FIELD_VALIDATION_MESSAGE = "Field value should be specified";
    private static final String POSITIVE_OR_ZERO_AND_LENGTH =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final LocalDate PAYMENT_DATA_WIDGET = LocalDate.of(2019, 6, 20);
    private FundPoolLoadWindow window;
    private INtsUsageController usagesController;

    @Before
    public void setUp() {
        usagesController = createMock(INtsUsageController.class);
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
        expect(usagesController.getRightsholder(rroAccountNumber)).andReturn(fasRro).once();
        expect(usagesController.getRightsholder(2000017000L)).andReturn(claRro).once();
        expect(usagesController.getRightsholder(20000170L)).andReturn(new Rightsholder()).once();
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        assertEquals("Load Fund Pool", window.getCaption());
        assertEquals(440, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(360, window.getHeight(), 0);
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
        setTextFieldValue(window, USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        setTextFieldValue(window, ACCOUNT_NUMBER_FIELD, ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        TextField rhNameField = Whitebox.getInternalState(window, "accountNameField");
        rhNameField.setReadOnly(false);
        rhNameField.setValue("Account Name");
        rhNameField.setReadOnly(true);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        setTextFieldValue(window, PERIOD_TO_FIELD, PERIOD_TO);
        setTextFieldValue(window, PERIOD_FROM_FIELD, PERIOD_FROM);
        assertFalse(window.isValid());
        setTextFieldValue(window, STM_FIELD, AMOUNT);
        setTextFieldValue(window, NON_STM_FIELD, AMOUNT);
        setTextFieldValue(window, STM_MIN_FIELD, MIN_AMOUNT);
        setTextFieldValue(window, NON_STM_MIN_FIELD, MIN_AMOUNT);
        assertFalse(window.isValid());
        setTextFieldValue(window, MARKET_VALIDATION_FIELD, "2");
        assertTrue(window.isValid());
        setTextFieldValue(window, STM_FIELD, AMOUNT);
        setTextFieldValue(window, NON_STM_FIELD, ZERO_AMOUNT);
        assertTrue(window.isValid());
        setTextFieldValue(window, STM_FIELD, ZERO_AMOUNT);
        setTextFieldValue(window, NON_STM_FIELD, AMOUNT);
        assertTrue(window.isValid());
        setTextFieldValue(window, STM_FIELD, ZERO_AMOUNT);
        setTextFieldValue(window, NON_STM_FIELD, "0");
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
        validateFieldAndVerifyErrorMessage(
            periodFrom, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "invalidDateFrom", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "-2000", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "invalidDateTo", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "-2000", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "1000", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "1000", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodFrom, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(periodTo, "2100", binder, INVALID_PERIOD_ERROR_MESSAGE, false);
        periodFrom.setValue("2018");
        validateFieldAndVerifyErrorMessage(periodTo, "2001", binder,
            "Field value should be greater or equal to Fund Pool Period From", false);
        periodFrom.setValue("2001");
        validateFieldAndVerifyErrorMessage(periodTo, "2018", binder, null, true);
        periodFrom.setValue("2005");
        validateFieldAndVerifyErrorMessage(periodTo, "2004", binder,
            "Field value should be greater or equal to Fund Pool Period From", false);
        validateFieldAndVerifyErrorMessage(periodTo, "2005", binder, null, true);
        verify(usagesController);
    }

    @Test
    public void testMarketValidationFieldValidation() {
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "stringBinder");
        TextField marketValidationField = Whitebox.getInternalState(window, MARKET_VALIDATION_FIELD);
        validateFieldAndVerifyErrorMessage(marketValidationField, "1000", binder, null, true);
        validateFieldAndVerifyErrorMessage(marketValidationField, "99999999", binder, null, true);
        validateFieldAndVerifyErrorMessage(
            marketValidationField, StringUtils.EMPTY, binder, INVALID_MARKET_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(marketValidationField, "A", binder, INVALID_MARKET_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(marketValidationField, "0", binder, INVALID_MARKET_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(marketValidationField, "-1000", binder, INVALID_MARKET_MESSAGE, false);
        verify(usagesController);
    }

    @Test
    public void testRightsholderAccountNumberFieldValidation() {
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField accountNumberField = Whitebox.getInternalState(window, ACCOUNT_NUMBER_FIELD);
        validateFieldAndVerifyErrorMessage(accountNumberField, "0", binder, null, true);
        validateFieldAndVerifyErrorMessage(accountNumberField, "1000024950", binder, null, true);
        validateFieldAndVerifyErrorMessage(
            accountNumberField, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            accountNumberField, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            accountNumberField, "10000249500", binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            accountNumberField, "9999999999.99", binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(accountNumberField, "value", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(accountNumberField, "-1000", binder, INVALID_NUMERIC_VALUE_MESSAGE, false);
        verify(usagesController);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        Capture<UsageBatch> usageBatchCapture = newCapture();
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
        NtsFields ntsFields = usageBatch.getNtsFields();
        assertEquals(Integer.valueOf(PERIOD_FROM), ntsFields.getFundPoolPeriodFrom());
        assertEquals(Integer.valueOf(PERIOD_TO), ntsFields.getFundPoolPeriodTo());
        assertEquals(new BigDecimal(AMOUNT), ntsFields.getStmAmount());
        assertEquals(new BigDecimal(AMOUNT), ntsFields.getNonStmAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), ntsFields.getStmMinimumAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), ntsFields.getNonStmMinimumAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), ntsFields.getNonStmMinimumAmount());
        assertTrue(ntsFields.isExcludingStm());
        assertNotNull(usageBatch);
        verify(window, usagesController, Windows.class);
    }

    @Test
    public void testOnUploadClickedValidFieldsNoRecords() {
        mockStatic(Windows.class);
        Capture<UsageBatch> usageBatchCapture = newCapture();
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

    @Test
    public void testUsageBatchNameFieldValidation() {
        expect(usagesController.usageBatchExists(USAGE_BATCH_NAME)).andReturn(true).times(3);
        expect(usagesController.usageBatchExists("Batch")).andReturn(false).times(3);
        replay(usagesController);
        window = new FundPoolLoadWindow(usagesController);
        Binder binder = Whitebox.getInternalState(window, "binder");
        TextField usageBatchName = Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD);
        validateFieldAndVerifyErrorMessage(
            usageBatchName, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            usageBatchName, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(usageBatchName, StringUtils.repeat('a', 51), binder,
            "Field value should not exceed 50 characters", false);
        validateFieldAndVerifyErrorMessage(usageBatchName, USAGE_BATCH_NAME, binder,
            "Usage Batch with such name already exists", false);
        validateFieldAndVerifyErrorMessage(usageBatchName, "Batch", binder, null, true);
        verify(usagesController);
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
        setTextField(USAGE_BATCH_NAME_FIELD, USAGE_BATCH_NAME);
        setTextField(ACCOUNT_NUMBER_FIELD, ACCOUNT_NUMBER);
        setTextField(PERIOD_TO_FIELD, PERIOD_TO);
        setTextField(PERIOD_FROM_FIELD, PERIOD_FROM);
        setTextField(STM_FIELD, AMOUNT);
        setTextField(NON_STM_FIELD, AMOUNT);
        setTextField(STM_MIN_FIELD, MIN_AMOUNT);
        setTextField(NON_STM_MIN_FIELD, MIN_AMOUNT);
        setTextField(MARKET_VALIDATION_FIELD, "2");
        Whitebox.setInternalState(window, "excludeStmCheckBox", new CheckBox("Exclude STM RHs", true));
    }

    private void verifyAmountFieldValidation(String fieldName) {
        TextField grossAmountField = Whitebox.getInternalState(window, fieldName);
        Binder binder = Whitebox.getInternalState(window, "binder");
        validateFieldAndVerifyErrorMessage(grossAmountField, ZERO_AMOUNT, binder, null, true);
        validateFieldAndVerifyErrorMessage(grossAmountField, "123.5684", binder, null, true);
        validateFieldAndVerifyErrorMessage(grossAmountField, "9999999999", binder, null, true);
        validateFieldAndVerifyErrorMessage(grossAmountField, "9999999999.99", binder, null, true);
        validateFieldAndVerifyErrorMessage(
            grossAmountField, StringUtils.EMPTY, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            grossAmountField, SPACES_STRING, binder, EMPTY_FIELD_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(grossAmountField, "value", binder, POSITIVE_OR_ZERO_AND_LENGTH, false);
        validateFieldAndVerifyErrorMessage(grossAmountField, ".04", binder, POSITIVE_OR_ZERO_AND_LENGTH, false);
        validateFieldAndVerifyErrorMessage(grossAmountField, "00.33", binder, POSITIVE_OR_ZERO_AND_LENGTH, false);
        validateFieldAndVerifyErrorMessage(grossAmountField, "123.568 4", binder, POSITIVE_OR_ZERO_AND_LENGTH, false);
        validateFieldAndVerifyErrorMessage(
            grossAmountField, "10000000000.00", binder, POSITIVE_OR_ZERO_AND_LENGTH, false);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyUsageBatchNameComponent(verticalLayout.getComponent(0));
        verifyRightsholdersComponents(verticalLayout.getComponent(1));
        verifyDateComponents(verticalLayout.getComponent(2));
        verifyItemsFilterWidget(verticalLayout.getComponent(3), "Markets");
        verifyAmountsComponent(verticalLayout.getComponent(4));
        verifyMinAmountsComponent(verticalLayout.getComponent(5));
        verifyExcludeStmCheckBox(verticalLayout.getComponent(6));
        verifyButtonsLayout(verticalLayout.getComponent(7));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        TextField textField = verifyTextField(component, "Usage Batch Name");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        Button loadButton = verifyButton(layout.getComponent(0), "Upload");
        verifyButton(layout.getComponent(1), "Close");
        assertEquals(1, loadButton.getListeners(ClickEvent.class).size());
        verifyLoadClickListener(loadButton, Lists.newArrayList(
            Whitebox.getInternalState(window, USAGE_BATCH_NAME_FIELD),
            Whitebox.getInternalState(window, ACCOUNT_NUMBER_FIELD),
            Whitebox.getInternalState(window, "accountNameField"),
            Whitebox.getInternalState(window, "paymentDateWidget"),
            Whitebox.getInternalState(window, PERIOD_TO_FIELD),
            Whitebox.getInternalState(window, PERIOD_FROM_FIELD),
            Whitebox.getInternalState(window, MARKET_VALIDATION_FIELD),
            Whitebox.getInternalState(window, STM_FIELD),
            Whitebox.getInternalState(window, NON_STM_FIELD),
            Whitebox.getInternalState(window, STM_MIN_FIELD),
            Whitebox.getInternalState(window, NON_STM_MIN_FIELD)));
    }

    private Button verifyButton(Component component, String caption) {
        assertThat(component, instanceOf(Button.class));
        assertEquals(caption, component.getCaption());
        return (Button) component;
    }

    private void verifyRightsholdersComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
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
        assertThat(verifyComponent, instanceOf(Button.class));
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

    private void verifyDateComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        Component paymentDateComponent = horizontalLayout.getComponent(0);
        Component fundPoolPeriodFromComponent = horizontalLayout.getComponent(1);
        Component fundPoolPeriodToComponent = horizontalLayout.getComponent(2);
        assertThat(paymentDateComponent, instanceOf(LocalDateWidget.class));
        LocalDateWidget widget = (LocalDateWidget) paymentDateComponent;
        assertEquals("Payment Date", widget.getCaption());
        assertThat(fundPoolPeriodFromComponent, instanceOf(TextField.class));
        verifyTextField(fundPoolPeriodFromComponent, "Fund Pool Period From");
        assertThat(fundPoolPeriodToComponent, instanceOf(TextField.class));
        verifyTextField(fundPoolPeriodToComponent, "Fund Pool Period To");
    }

    private void verifyAmountsComponent(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyAmountComponent(horizontalLayout.getComponent(0), "STM Amount", "");
        verifyAmountComponent(horizontalLayout.getComponent(1), "Non-STM Amount", "");
    }

    private void verifyMinAmountsComponent(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
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

    private void verifyExcludeStmCheckBox(Component component) {
        assertThat(component, instanceOf(CheckBox.class));
        CheckBox checkBox = (CheckBox) component;
        assertEquals("Exclude STM RHs", checkBox.getCaption());
        assertEquals("exclude-stm-rhs-checkbox", checkBox.getStyleName());
        assertFalse(checkBox.getValue());
    }

    private void setTextField(String field, String value) {
        Whitebox.setInternalState(window, field, new TextField(field, value));
    }
}
