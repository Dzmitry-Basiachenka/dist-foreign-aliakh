package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getBigDecimalField;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getButton;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getIntegerField;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getLongField;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.getTextField;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setBigDecimalFieldValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setCheckboxValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setIntegerFieldValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setLongFieldValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinComponentFinder.setTextFieldValue;
import static com.copyright.rup.dist.foreign.vui.IVaadinJsonConverter.assertJsonSnapshot;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.assertFieldValidationMessage;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getDialogContent;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.getFooterLayout;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyBigDecimalField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyButton;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyIntegerField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLoadClickListener;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyLongField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.vui.UiTestHelper.verifyWindow;

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

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.security.SecurityUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class FundPoolLoadWindowTest {

    private static final String USAGE_BATCH_NAME = "Usage Batch";
    private static final Long FAS_RRO_ACCOUNT_NUMBER = 1000001863L;
    private static final String FAS_RRO_ACCOUNT_NAME = "CANADIAN CERAMIC SOCIETY";
    private static final Long CLA_RRO_ACCOUNT_NUMBER = 2000017000L;
    private static final String CLA_RRO_ACCOUNT_NAME = "CLA, The Copyright Licensing Agency Ltd.";
    private static final String RRO_NAME = "RRO name";
    private static final Long UNKNOWN_ACCOUNT_NAME = 20000170L;
    private static final Integer FISCAL_YEAR = 2019;
    private static final LocalDate PAYMENT_DATE = LocalDate.of(FISCAL_YEAR, 6, 20);
    private static final Integer PERIOD_FROM = 2000;
    private static final Integer PERIOD_TO = 2000;
    private static final String AMOUNT = "100.00";
    private static final String MIN_AMOUNT = "10.00";
    private static final String ZERO_AMOUNT = "0.00";
    private static final String BINDER = "binder";
    private static final String USAGE_BATCH_NAME_LABEL = "Usage Batch Name";
    private static final String PERIOD_FROM_LABEL = "Fund Pool Period From";
    private static final String PERIOD_TO_LABEL = "Fund Pool Period To";
    private static final String STM_LABEL = "STM Amount";
    private static final String NON_STM_LABEL = "Non-STM Amount";
    private static final String STM_MIN_LABEL = "STM Minimum Amount";
    private static final String NON_STM_MIN_LABEL = "Non-STM Minimum Amount";
    private static final String ACCOUNT_NUMBER_LABEL = "RRO Account #";
    private static final String EXCLUDE_STM_CHECKBOX_LABEL= "Exclude STM RHs";
    private static final String SPACES_STRING = "    ";
    private static final String STRING_50_CHARACTERS = StringUtils.repeat('a', 50);
    private static final String STRING_51_CHARACTERS = StringUtils.repeat('a', 51);
    private static final String EMPTY_FIELD_MESSAGE = "Field value should be specified";
    private static final String VALUE_EXCEED_50_CHARACTERS_MESSAGE = "Field value should not exceed 50 characters";
    private static final String USAGE_BATCH_EXISTS_MESSAGE = "Usage Batch with such name already exists";
    private static final String INVALID_PERIOD_MESSAGE = "Field value should be in range from 1950 to 2099";
    private static final String INVALID_NUMBER_LENGTH_MESSAGE = "Field value should not exceed 10 digits";
    private static final String WIDTH_FULL = "100%";

    private FundPoolLoadWindow window;
    private INtsUsageController controller;

    @Before
    public void setUp() {
        controller = createMock(INtsUsageController.class);
    }

    @Test
    public void testConstructor() {
        var fasRro = new Rightsholder();
        fasRro.setAccountNumber(FAS_RRO_ACCOUNT_NUMBER);
        fasRro.setName(FAS_RRO_ACCOUNT_NAME);
        var claRro = new Rightsholder();
        claRro.setAccountNumber(CLA_RRO_ACCOUNT_NUMBER);
        claRro.setName(CLA_RRO_ACCOUNT_NAME);
        expect(controller.getRightsholder(FAS_RRO_ACCOUNT_NUMBER)).andReturn(fasRro).once();
        expect(controller.getRightsholder(CLA_RRO_ACCOUNT_NUMBER)).andReturn(claRro).once();
        expect(controller.getRightsholder(UNKNOWN_ACCOUNT_NAME)).andReturn(new Rightsholder()).once();
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        verifyWindow(window, "Load Fund Pool", "520px", "510px", Unit.PIXELS, false);
        verifyRootLayout(getDialogContent(window));
        verify(controller);
    }

    @Test
    public void testJsonSnapshot() {
        assertJsonSnapshot("usage/impl/nts/fund-pool-load-window.json", new FundPoolLoadWindow(controller));
    }

    @Test
    public void testUsageBatchNameFieldValidation() {
        String usageBatchName = "existing Usage Batch";
        expect(controller.usageBatchExists(STRING_50_CHARACTERS)).andReturn(false).times(2);
        expect(controller.usageBatchExists(usageBatchName)).andReturn(true).times(2);
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).times(2);
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
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
    public void testRightsholderAccountNumberFieldValidation() {
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        var accountNumberField = getLongField(window, ACCOUNT_NUMBER_LABEL);
        assertFieldValidationMessage(accountNumberField, 0L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        assertFieldValidationMessage(accountNumberField, 1L, binder, null, true);
        assertFieldValidationMessage(accountNumberField, FAS_RRO_ACCOUNT_NUMBER, binder, null, true);
        assertFieldValidationMessage(accountNumberField, 9999999999L, binder, null, true);
        assertFieldValidationMessage(accountNumberField, 10000000000L, binder, INVALID_NUMBER_LENGTH_MESSAGE, false);
        verify(controller);
    }

    @Test
    public void testFundPoolPeriodValidation() {
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        IntegerField periodFromField = getIntegerField(window, PERIOD_FROM_LABEL);
        IntegerField periodToField = getIntegerField(window, PERIOD_TO_LABEL);
        assertFieldValidationMessage(periodFromField, -2000, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodToField, -2000, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodFromField, 1000, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodToField, 1000, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodFromField, 2100, binder, INVALID_PERIOD_MESSAGE, false);
        assertFieldValidationMessage(periodToField, 2100, binder, INVALID_PERIOD_MESSAGE, false);
        periodFromField.setValue(2018);
        assertFieldValidationMessage(periodToField, 2001, binder,
            "Field value should be greater or equal to Fund Pool Period From", false);
        periodFromField.setValue(2001);
        assertFieldValidationMessage(periodToField, 2018, binder, null, true);
        periodFromField.setValue(2005);
        assertFieldValidationMessage(periodToField, 2004, binder,
            "Field value should be greater or equal to Fund Pool Period From", false);
        assertFieldValidationMessage(periodToField, 2005, binder, null, true);
        verify(controller);
    }

    @Test
    public void testMarketValidationField() {
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        VerticalLayout marketsLayout = getMarketsLayout();
        assertEquals(1, marketsLayout.getComponentCount());
        validateSelectedMarket(marketsLayout, Set.of("Bus"), 1);
        validateSelectedMarket(marketsLayout, null, 2);
        validateSelectedMarket(marketsLayout, Set.of(), 2);
        Component errorDiv = marketsLayout.getComponentAt(1);
        assertThat(errorDiv, instanceOf(Div.class));
        assertEquals("Please select at least one market", errorDiv.getElement().getProperty("innerHTML"));
        verify(controller);
    }

    @Test
    public void testAmountFieldValidation() {
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        verifyAmountFieldValidation(STM_LABEL);
        verifyAmountFieldValidation(NON_STM_LABEL);
        verifyAmountFieldValidation(STM_MIN_LABEL);
        verifyAmountFieldValidation(NON_STM_MIN_LABEL);
        verify(controller);
    }

    @Test
    public void testIsValid() {
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).anyTimes();
        replay(controller);
        window = new FundPoolLoadWindow(controller);
        populateSelectedMarkets(Set.of("Bus"));
        assertFalse(window.isValid());
        setTextFieldValue(window, USAGE_BATCH_NAME_LABEL, USAGE_BATCH_NAME);
        assertFalse(window.isValid());
        setLongFieldValue(window, ACCOUNT_NUMBER_LABEL, FAS_RRO_ACCOUNT_NUMBER);
        assertFalse(window.isValid());
        TextField accountNameField = Whitebox.getInternalState(window, "accountNameField");
        accountNameField.setReadOnly(false);
        accountNameField.setValue("Account Name");
        accountNameField.setReadOnly(true);
        assertFalse(window.isValid());
        Whitebox.getInternalState(window, LocalDateWidget.class).setValue(LocalDate.now());
        assertFalse(window.isValid());
        setIntegerFieldValue(window, PERIOD_TO_LABEL, PERIOD_TO);
        setIntegerFieldValue(window, PERIOD_FROM_LABEL, PERIOD_FROM);
        assertFalse(window.isValid());
        setBigDecimalFieldValue(window, STM_LABEL, AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_LABEL, AMOUNT);
        setBigDecimalFieldValue(window, STM_MIN_LABEL, MIN_AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_MIN_LABEL, MIN_AMOUNT);
        assertTrue(window.isValid());
        setBigDecimalFieldValue(window, STM_LABEL, AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_LABEL, ZERO_AMOUNT);
        assertTrue(window.isValid());
        setBigDecimalFieldValue(window, STM_LABEL, ZERO_AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_LABEL, AMOUNT);
        assertTrue(window.isValid());
        setBigDecimalFieldValue(window, STM_LABEL, ZERO_AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_LABEL, "0");
        assertFalse(window.isValid());
        verify(controller);
    }

    @Test
    public void testOnUploadClickedValidFields() {
        mockStatic(Windows.class);
        window = createPartialMock(FundPoolLoadWindow.class, new String[]{"isValid", "close"}, controller);
        expect(window.isValid()).andReturn(true).once();
        window.close();
        expectLastCall().once();
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        Capture<UsageBatch> usageBatchCapture = newCapture();
        expect(controller.getRightsholder(FAS_RRO_ACCOUNT_NUMBER)).andReturn(buildRro()).once();
        expect(controller.getUsagesCountForNtsBatch(capture(usageBatchCapture))).andReturn(3).once();
        expect(controller.loadNtsBatch(capture(usageBatchCapture))).andReturn(3).once();
        Windows.showNotificationWindow("Upload completed: 3 record(s) were stored successfully");
        expectLastCall().once();
        replay(Windows.class, window, controller);
        initUploadComponents();
        window.onUploadClicked();
        var usageBatch = usageBatchCapture.getValue();
        assertNotNull(usageBatch);
        assertEquals(USAGE_BATCH_NAME, usageBatch.getName());
        assertEquals(FAS_RRO_ACCOUNT_NUMBER, usageBatch.getRro().getAccountNumber());
        assertEquals(RRO_NAME, usageBatch.getRro().getName());
        assertEquals(FISCAL_YEAR, usageBatch.getFiscalYear());
        var ntsFields = usageBatch.getNtsFields();
        assertEquals(PERIOD_FROM, ntsFields.getFundPoolPeriodFrom());
        assertEquals(PERIOD_TO, ntsFields.getFundPoolPeriodTo());
        assertEquals(new BigDecimal(AMOUNT), ntsFields.getStmAmount());
        assertEquals(new BigDecimal(AMOUNT), ntsFields.getNonStmAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), ntsFields.getStmMinimumAmount());
        assertEquals(new BigDecimal(MIN_AMOUNT), ntsFields.getNonStmMinimumAmount());
        assertTrue(ntsFields.isExcludingStm());
        verify(Windows.class, window, controller);
    }

    @Test
    public void testOnUploadClickedValidFieldsNoRecords() {
        mockStatic(Windows.class);
        window = createPartialMock(FundPoolLoadWindow.class, new String[]{"isValid"}, controller);
        expect(window.isValid()).andReturn(true).once();
        expect(controller.usageBatchExists(USAGE_BATCH_NAME)).andReturn(false).once();
        expect(controller.getRightsholder(FAS_RRO_ACCOUNT_NUMBER)).andReturn(buildRro()).once();
        Capture<UsageBatch> usageBatchCapture = newCapture();
        expect(controller.getUsagesCountForNtsBatch(capture(usageBatchCapture))).andReturn(0).once();
        Windows.showNotificationWindow("There are no usages matching selected Markets and Fund Pool Period");
        expectLastCall().once();
        replay(Windows.class, window, controller);
        initUploadComponents();
        window.onUploadClicked();
        UsageBatch usageBatch = usageBatchCapture.getValue();
        assertNotNull(usageBatch);
        verify(Windows.class, window, controller);
    }

    private void initUploadComponents() {
        setTextFieldValue(window, USAGE_BATCH_NAME_LABEL, USAGE_BATCH_NAME);
        setLongFieldValue(window, ACCOUNT_NUMBER_LABEL, FAS_RRO_ACCOUNT_NUMBER);
        getButton(window, "Verify").click();
        ((LocalDateWidget) Whitebox.getInternalState(window, "paymentDateWidget")).setValue(PAYMENT_DATE);
        setIntegerFieldValue(window, PERIOD_FROM_LABEL, PERIOD_FROM);
        setIntegerFieldValue(window, PERIOD_TO_LABEL, PERIOD_TO);
        setBigDecimalFieldValue(window, STM_LABEL, AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_LABEL, AMOUNT);
        setBigDecimalFieldValue(window, STM_MIN_LABEL, MIN_AMOUNT);
        setBigDecimalFieldValue(window, NON_STM_MIN_LABEL, MIN_AMOUNT);
        setCheckboxValue(window, EXCLUDE_STM_CHECKBOX_LABEL, true);
    }

    private void verifyAmountFieldValidation(String fieldName) {
        var amountField = getBigDecimalField(window, fieldName);
        Binder<?> binder = Whitebox.getInternalState(window, BINDER);
        assertFieldValidationMessage(amountField, BigDecimal.ZERO, binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("0.00"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("0.009"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("0.01"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("123.4567"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("9999999999"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("9999999999.99"), binder, null, true);
        assertFieldValidationMessage(amountField, new BigDecimal("10000000000.00"), binder,
            "Field value should be positive number and should not exceed 10 digits", false);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        var rootLayout = (VerticalLayout) component;
        assertEquals(7, rootLayout.getComponentCount());
        verifyUsageBatchNameComponent(rootLayout.getComponentAt(0));
        verifyRightsholdersComponents(rootLayout.getComponentAt(1));
        verifyDateComponents(rootLayout.getComponentAt(2));
        verifyItemsFilterWidget(getMarketsLayout().getComponentAt(0), "Markets");
        verifyAmountsComponent(rootLayout.getComponentAt(4));
        verifyMinAmountsComponent(rootLayout.getComponentAt(5));
        verifyExcludeStmCheckbox(rootLayout.getComponentAt(6));
        verifyButtonsLayout(getFooterLayout(window));
    }

    private void verifyUsageBatchNameComponent(Component component) {
        var textField = verifyTextField(component, "Usage Batch Name", WIDTH_FULL, "usage-batch-name-field");
        assertEquals(StringUtils.EMPTY, textField.getValue());
    }

    private void verifyButtonsLayout(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        Button uploadButton = verifyButton(horizontalLayout.getComponentAt(0), "Upload", true);
        verifyLoadClickListener(uploadButton);
        verifyButton(horizontalLayout.getComponentAt(1), "Close", true);
    }

    private void verifyRightsholdersComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var rroAccountLayout = (HorizontalLayout) component;
        assertEquals(3, rroAccountLayout.getComponentCount());
        var accountNumberField = verifyLongField(rroAccountLayout.getComponentAt(0), "RRO Account #", "30%",
            "rro-account-number-field");
        var accountNameField = verifyTextField(rroAccountLayout.getComponentAt(1), "RRO Account Name", "50%",
            "rro-account-name-field");
        assertTrue(accountNameField.isReadOnly());
        verifyVerifyButton(rroAccountLayout.getComponentAt(2), accountNumberField, accountNameField);
    }

    private void verifyVerifyButton(Component component, LongField accountNumberField, TextField accountNameField) {
        assertThat(component, instanceOf(Button.class));
        var verifyButton = (Button) component;
        assertEquals("Verify", verifyButton.getText());
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        accountNumberField.setValue(123456789987654321L);
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
        accountNumberField.setValue(FAS_RRO_ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals(FAS_RRO_ACCOUNT_NAME, accountNameField.getValue());
        accountNumberField.setValue(CLA_RRO_ACCOUNT_NUMBER);
        verifyButton.click();
        assertEquals(CLA_RRO_ACCOUNT_NAME, accountNameField.getValue());
        accountNumberField.setValue(UNKNOWN_ACCOUNT_NAME);
        verifyButton.click();
        assertEquals(StringUtils.EMPTY, accountNameField.getValue());
    }

    private void verifyDateComponents(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(3, horizontalLayout.getComponentCount());
        var paymentDateComponent = horizontalLayout.getComponentAt(0);
        assertThat(paymentDateComponent, instanceOf(LocalDateWidget.class));
        var widget = (LocalDateWidget) paymentDateComponent;
        assertEquals("Payment Date", widget.getLabel());
        var fundPoolPeriodFromComponent = horizontalLayout.getComponentAt(1);
        assertThat(fundPoolPeriodFromComponent, instanceOf(IntegerField.class));
        verifyIntegerField(fundPoolPeriodFromComponent, "Fund Pool Period From", "33%", "fund-pool-period-from-field");
        var fundPoolPeriodToComponent = horizontalLayout.getComponentAt(2);
        assertThat(fundPoolPeriodToComponent, instanceOf(IntegerField.class));
        verifyIntegerField(fundPoolPeriodToComponent, "Fund Pool Period To", "30%", "fund-pool-period-to-field");
    }

    private void verifyAmountsComponent(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyAmountComponent(horizontalLayout.getComponentAt(0), "STM Amount", null,
            "stm-amount-field");
        verifyAmountComponent(horizontalLayout.getComponentAt(1), "Non-STM Amount", null,
            "non-stm-amount-field");
    }

    private void verifyMinAmountsComponent(Component component) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        var horizontalLayout = (HorizontalLayout) component;
        assertEquals(2, horizontalLayout.getComponentCount());
        verifyAmountComponent(horizontalLayout.getComponentAt(0), "STM Minimum Amount", new BigDecimal("50"),
            "stm-minimum-amount-field");
        verifyAmountComponent(horizontalLayout.getComponentAt(1), "Non-STM Minimum Amount", new BigDecimal("7"),
            "non-stm-minimum-amount-field");
    }

    private void verifyAmountComponent(Component component, String caption, BigDecimal defaultValue, String styleName) {
        var amountField = verifyBigDecimalField(component, caption, WIDTH_FULL, styleName);
        assertEquals(WIDTH_FULL, amountField.getWidth());
        assertEquals(defaultValue, amountField.getValue());
    }

    private void verifyExcludeStmCheckbox(Component component) {
        assertThat(component, instanceOf(Checkbox.class));
        var checkbox = (Checkbox) component;
        assertEquals("Exclude STM RHs", checkbox.getLabel());
        assertEquals("exclude-stm-rhs-checkbox", checkbox.getId().get());
        assertFalse(checkbox.getValue());
    }

    private VerticalLayout getMarketsLayout() {
        var rootLayout = (VerticalLayout) getDialogContent(window);
        return (VerticalLayout) rootLayout.getComponentAt(3);
    }

    private void validateSelectedMarket(VerticalLayout marketsLayout, Set<String> markets,
                                        int expectedComponentsCount) {
        populateSelectedMarkets(markets);
        window.isValid();
        assertEquals(expectedComponentsCount, marketsLayout.getComponentCount());
    }

    private void populateSelectedMarkets(Set<String> markets) {
        Whitebox.setInternalState(window, "selectedMarkets", markets);
    }

    private Rightsholder buildRro() {
        var rro = new Rightsholder();
        rro.setAccountNumber(FAS_RRO_ACCOUNT_NUMBER);
        rro.setName(RRO_NAME);
        return rro;
    }
}
