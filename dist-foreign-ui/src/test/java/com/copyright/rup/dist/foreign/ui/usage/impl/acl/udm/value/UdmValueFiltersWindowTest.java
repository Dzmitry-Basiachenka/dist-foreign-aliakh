package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UdmValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmValueFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/23/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String LAST_VALUE_PERIOD = "202106";
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final String SYSTEM_STANDARD_NUMBER = "0927-7765";
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("200.00");
    private static final Boolean PRICE_FLAG = true;
    private static final String PRICE_FLAG_STRING = "Y";
    private static final String PRICE_COMMENT = "price comment";
    private static final Boolean LAST_PRICE_FLAG = true;
    private static final String LAST_PRICE_FLAG_STRING = "Y";
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal CONTENT = new BigDecimal("70");
    private static final Boolean CONTENT_FLAG = true;
    private static final String CONTENT_FLAG_STRING = "Y";
    private static final String CONTENT_COMMENT = "content comment";
    private static final Boolean LAST_CONTENT_FLAG = false;
    private static final String LAST_CONTENT_FLAG_STRING = "N";
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final String COMMENT = "comment";
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final Currency USD_CURRENCY = new Currency("USD", "US Dollar");
    private static final List<Currency> CURRENCIES =
        Arrays.asList(USD_CURRENCY, new Currency("AUD", "Australian Dollar"), new Currency("CAD", "Canadian Dollar"),
            new Currency("EUR", "Euro"), new Currency("GBP", "Pound Sterling"), new Currency("JPY", "Yen"),
            new Currency("BRL", "Brazilian Real"), new Currency("CNY", "Yuan Renminbi"),
            new Currency("CZK", "Czech Koruna"), new Currency("DKK", "Danish Krone"),
            new Currency("NZD", "New Zealand Dollar"), new Currency("NOK", "Norwegian Kron"),
            new Currency("ZAR", "Rand"), new Currency("CHF", "Swiss Franc"), new Currency("INR", "Indian Rupee"));

    private UdmValueFiltersWindow window;
    private Binder<UdmUsageFilter> binder;

    @Before
    public void setUp() {
        IUdmValueFilterController controller = createMock(IUdmValueFilterController.class);
        expect(controller.getPublicationTypes()).andReturn(
            new ArrayList<>(Collections.singletonList(buildPublicationType()))).once();
        expect(controller.getAllCurrencies()).andReturn(CURRENCIES).once();
        replay(controller);
        window = new UdmValueFiltersWindow(controller, new UdmValueFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
        verify(controller);
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM values additional filters", window.getCaption());
        assertEquals(560, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(650, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmValueFilter valueFilter = buildExpectedFilter();
        valueFilter.setAssignees(Collections.singleton(ASSIGNEE));
        valueFilter.setLastValuePeriods(Collections.singleton(LAST_VALUE_PERIOD));
        valueFilter.setWrWrkInst(WR_WRK_INST);
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setCurrency(USD_CURRENCY);
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setPriceFlag(PRICE_FLAG);
        valueFilter.setPriceComment(PRICE_COMMENT);
        valueFilter.setLastPriceFlag(LAST_PRICE_FLAG);
        valueFilter.setLastPriceComment(LAST_PRICE_COMMENT);
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setContentFlag(CONTENT_FLAG);
        valueFilter.setContentComment(CONTENT_COMMENT);
        valueFilter.setLastContentFlag(LAST_CONTENT_FLAG);
        valueFilter.setLastContentComment(LAST_CONTENT_COMMENT);
        valueFilter.setLastPubType(buildPublicationType());
        valueFilter.setComment(COMMENT);
        IUdmValueFilterController controller = createMock(IUdmValueFilterController.class);
        expect(controller.getPublicationTypes()).andReturn(
            new ArrayList<>(Collections.singletonList(buildPublicationType()))).once();
        expect(controller.getAllCurrencies()).andReturn(CURRENCIES).once();
        replay(controller);
        window = new UdmValueFiltersWindow(controller, valueFilter);
        verify(controller);
        verifyFiltersData();
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(2);
    }

    @Test
    public void testSystemStandardNumberFilterOperatorChangeListener3() {
        testFilterOperatorChangeListener(3);
    }

    @Test
    public void testRhNameFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(5);
    }

    @Test
    public void testPriceFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(7);
    }

    @Test
    public void testPriceInUsdFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(7);
    }

    @Test
    public void testContentFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(11);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmValueFilter appliedValueFilter = window.getAppliedValueFilter();
        assertTrue(appliedValueFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedValueFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedValueFilter().isEmpty());
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1234567890", "Field value should not exceed 9 digits",
            false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "234fdsfs", "Field value should contain numeric values only",
            false);
    }

    @Test
    public void testSystemTitleFieldValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2000), null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2001),
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testStandardNumberFieldValidation() {
        TextField systemStandardNumberField = Whitebox.getInternalState(window, "systemStandardNumberField");
        validateFieldAndVerifyErrorMessage(systemStandardNumberField, buildStringWithExpectedLength(1000), null, true);
        validateFieldAndVerifyErrorMessage(systemStandardNumberField, buildStringWithExpectedLength(1001),
            "Field value should not exceed 1000 characters", false);
    }

    @Test
    public void testRhAccountNumberFieldValidation() {
        TextField rhAccountNumberField = Whitebox.getInternalState(window, "rhAccountNumberField");
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1234567897", null, true);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "21234567897",
            "Field value should not exceed 10 digits", false);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "1234567891", null, true);
        validateFieldAndVerifyErrorMessage(rhAccountNumberField, "234fdsfs",
            "Field value should contain numeric values only", false);
    }

    @Test
    public void testRhNameValidation() {
        TextField rhNameField = Whitebox.getInternalState(window, "rhNameField");
        validateFieldAndVerifyErrorMessage(rhNameField, buildStringWithExpectedLength(255), null, true);
        validateFieldAndVerifyErrorMessage(rhNameField, buildStringWithExpectedLength(256),
            "Field value should not exceed 255 characters", false);
    }

    @Test
    public void testPriceValidation() {
        TextField priceField = Whitebox.getInternalState(window, "priceField");
        ComboBox<FilterOperatorEnum> priceOperatorComboBox =
            Whitebox.getInternalState(window, "priceOperatorComboBox");
        assertOperatorComboBoxItems(priceOperatorComboBox);
        verifyAmountValidationZeroAllowed(priceField);
    }

    @Test
    public void testPriceInUsdValidation() {
        TextField priceInUsdField = Whitebox.getInternalState(window, "priceInUsdField");
        ComboBox<FilterOperatorEnum> priceInUsdOperatorComboBox =
            Whitebox.getInternalState(window, "priceInUsdOperatorComboBox");
        assertOperatorComboBoxItems(priceInUsdOperatorComboBox);
        verifyAmountValidationZeroAllowed(priceInUsdField);
    }

    @Test
    public void testContentValidation() {
        TextField contentField = Whitebox.getInternalState(window, "contentField");
        ComboBox<FilterOperatorEnum> contentOperatorComboBox =
            Whitebox.getInternalState(window, "contentOperatorComboBox");
        assertOperatorComboBoxItems(contentOperatorComboBox);
        verifyAmountValidationZeroDenied(contentField);
    }

    @Test
    public void testPriceCommentValidation() {
        validateCommentField("priceCommentField");
    }

    @Test
    public void testContentCommentValidation() {
        validateCommentField("contentCommentField");
    }

    @Test
    public void testLastPriceCommentValidation() {
        validateCommentField("lastPriceCommentField");
    }

    @Test
    public void testLastContentCommentValidation() {
        validateCommentField("lastContentCommentField");
    }

    @Test
    public void testCommentValidation() {
        validateCommentField("commentField");
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1));
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(16, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Last Value Periods");
        verifySizedTextField(verticalLayout.getComponent(1), "Wr Wrk Inst");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(2), "System Title");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(3), "System Standard Number");
        verifySizedTextField(verticalLayout.getComponent(4), "RH Account #");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(5), "RH Name");
        assertSizedComboBoxItems(verticalLayout.getComponent(6), "Currency", true, CURRENCIES);
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(7), "Price");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(8), "Price in USD");
        verifyTextFieldLayout(verticalLayout.getComponent(9), ComboBox.class, "Price Flag",
            TextField.class, "Price Comment");
        assertComboBoxItems("priceFlagComboBox", "Price Flag", true, Y_N_ITEMS);
        verifyTextFieldLayout(verticalLayout.getComponent(10), ComboBox.class, "Last Price Flag",
            TextField.class, "Last Price Comment");
        assertComboBoxItems("lastPriceFlagComboBox", "Last Price Flag", true, Y_N_ITEMS);
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(11), "Content");
        verifyTextFieldLayout(verticalLayout.getComponent(12), ComboBox.class, "Content Flag",
            TextField.class, "Content Comment");
        assertComboBoxItems("contentFlagComboBox", "Content Flag", true, Y_N_ITEMS);
        verifyTextFieldLayout(verticalLayout.getComponent(13), ComboBox.class, "Last Content Flag",
            TextField.class, "Last Content Comment");
        assertComboBoxItems("lastContentFlagComboBox", "Last Content Flag", true, Y_N_ITEMS);
        assertSizedComboBoxItems(verticalLayout.getComponent(14), "Last Pub Type", true,
            Arrays.asList(new PublicationType(), buildPublicationType()));
        verifyTextField(verticalLayout.getComponent(15), "Comment");
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyFieldWithOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(1).getCaption());
    }

    private void verifyTextFieldLayout(Component component, Class<?> firstClass, String firstCaption,
                                       Class<?> secondClass, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyComponent(layout.getComponent(0), firstClass, firstCaption, true);
        verifyComponent(layout.getComponent(1), secondClass, secondCaption, true);
    }

    private void verifyComponent(Component component, Class<?> clazz, String caption, boolean isEnabled) {
        assertTrue(clazz.isInstance(component));
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertEquals(isEnabled, component.isEnabled());
    }

    private void verifySizedTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        verifyComponentWidthAndCaption(component, caption);
    }

    private void verifyComponentWidthAndCaption(Component component, String caption) {
        assertEquals(248, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyTextField(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    private void verifyButtonsLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyButton(layout.getComponent(0), "Save");
        verifyButton(layout.getComponent(1), "Clear");
        verifyButton(layout.getComponent(2), "Close");
    }

    private void verifyButton(Component component, String caption) {
        assertTrue(component instanceof Button);
        assertEquals(caption, component.getCaption());
    }

    @SuppressWarnings(UNCHECKED)
    private void testFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        VerticalLayout panelContent = (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) panelContent.getComponent(index);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO);
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO);
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NULL);
        assertFalse(textField.isEnabled());
    }

    private void verifyAmountValidationZeroAllowed(TextField textField) {
        validateFieldAndVerifyErrorMessage(textField, "0", null, true);
        validateFieldAndVerifyErrorMessage(textField, " 0.004 ", null, true);
        verifyCommonAmountValidations(textField,
            "Field value should be positive number or zero and should not exceed 10 digits");
    }

    private void verifyAmountValidationZeroDenied(TextField textField) {
        String decimalValidationMessage = "Field value should be positive number and should not exceed 10 digits";
        validateFieldAndVerifyErrorMessage(textField, "0", decimalValidationMessage, false);
        validateFieldAndVerifyErrorMessage(textField, " 0.004 ", decimalValidationMessage, false);
        validateFieldAndVerifyErrorMessage(textField, " 0.005 ", null, true);
        verifyCommonAmountValidations(textField, decimalValidationMessage);
    }

    private void verifyCommonAmountValidations(TextField textField, String errorMessage) {
        validateFieldAndVerifyErrorMessage(textField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(textField, SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(textField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(textField, INTEGER_WITH_SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(textField, VALID_INTEGER, null, true);
        validateFieldAndVerifyErrorMessage(textField, INVALID_NUMBER, errorMessage, false);
        validateFieldAndVerifyErrorMessage(textField, ".05", errorMessage, false);
        validateFieldAndVerifyErrorMessage(textField, "99999999999", errorMessage, false);
    }

    private void validateCommentField(String fieldName) {
        TextField commentField = Whitebox.getInternalState(window, fieldName);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(commentField, buildStringWithExpectedLength(1024), null, true);
        validateFieldAndVerifyErrorMessage(commentField, buildStringWithExpectedLength(1025),
            "Field value should not exceed 1024 characters", false);
    }

    private UdmValueFilter buildExpectedFilter() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        valueFilter.setWrWrkInst(WR_WRK_INST);
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS,
            SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumber(RH_ACCOUNT_NUMBER);
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setCurrency(USD_CURRENCY);
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setPriceFlag(PRICE_FLAG);
        valueFilter.setPriceComment(PRICE_COMMENT);
        valueFilter.setLastPriceFlag(LAST_PRICE_FLAG);
        valueFilter.setLastPriceComment(LAST_PRICE_COMMENT);
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setContentFlag(CONTENT_FLAG);
        valueFilter.setContentComment(CONTENT_COMMENT);
        valueFilter.setLastContentFlag(LAST_CONTENT_FLAG);
        valueFilter.setLastContentComment(LAST_CONTENT_COMMENT);
        valueFilter.setLastPubType(buildPublicationType());
        valueFilter.setComment(COMMENT);
        return valueFilter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("assigneeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("lastValuePeriodFilterWidget", "(1)");
        assertTextFieldValue("wrWrkInstField", WR_WRK_INST.toString());
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        assertComboBoxValue("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhAccountNumberField", RH_ACCOUNT_NUMBER.toString());
        assertTextFieldValue("rhNameField", RH_NAME);
        assertComboBoxValue("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("currencyComboBox", USD_CURRENCY);
        assertTextFieldValue("priceField", PRICE.toString());
        assertComboBoxValue("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("priceInUsdField", PRICE_IN_USD.toString());
        assertComboBoxValue("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("priceFlagComboBox", PRICE_FLAG_STRING);
        assertTextFieldValue("priceCommentField", PRICE_COMMENT);
        assertComboBoxValue("lastPriceFlagComboBox", LAST_PRICE_FLAG_STRING);
        assertTextFieldValue("lastPriceCommentField", LAST_PRICE_COMMENT);
        assertTextFieldValue("contentField", CONTENT.toString());
        assertComboBoxValue("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("contentFlagComboBox", CONTENT_FLAG_STRING);
        assertTextFieldValue("contentCommentField", CONTENT_COMMENT);
        assertComboBoxValue("lastContentFlagComboBox", LAST_CONTENT_FLAG_STRING);
        assertTextFieldValue("lastContentCommentField", LAST_CONTENT_COMMENT);
        assertComboBoxValue("lastPubTypeComboBox", buildPublicationType());
        assertTextFieldValue("commentField", COMMENT);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertTextFieldValue(String fieldName, Object value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertSizedComboBoxItems(Component component, String caption, boolean emptySelectionAllowed,
                                              List<T> expectedItems) {
        assertTrue(component instanceof ComboBox);
        ComboBox<T> comboBox = (ComboBox<T>) component;
        assertFalse(comboBox.isReadOnly());
        assertTrue(comboBox.isTextInputAllowed());
        assertEquals(emptySelectionAllowed, comboBox.isEmptySelectionAllowed());
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<?> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
        verifyComponentWidthAndCaption(component, caption);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private <T> void assertComboBoxItems(String fieldName, String caption, boolean emptySelectionAllowed,
                                         List<T> expectedItems) {
        verifyComboBox(Whitebox.getInternalState(window, fieldName), caption, emptySelectionAllowed, expectedItems);
    }

    private void assertOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, "Operator", false, Arrays.asList(FilterOperatorEnum.EQUALS,
            FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.IS_NULL));
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateTextField("wrWrkInstField", String.valueOf(WR_WRK_INST));
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        populateComboBox("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhAccountNumberField", String.valueOf(RH_ACCOUNT_NUMBER));
        populateTextField("rhNameField", RH_NAME);
        populateComboBox("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("currencyComboBox", USD_CURRENCY);
        populateTextField("priceField", PRICE.toString());
        populateComboBox("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("priceInUsdField", PRICE_IN_USD.toString());
        populateComboBox("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("priceFlagComboBox", PRICE_FLAG_STRING);
        populateTextField("priceCommentField", PRICE_COMMENT);
        populateComboBox("lastPriceFlagComboBox", LAST_PRICE_FLAG_STRING);
        populateTextField("lastPriceCommentField", LAST_PRICE_COMMENT);
        populateTextField("contentField", String.valueOf(CONTENT));
        populateComboBox("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("contentFlagComboBox", CONTENT_FLAG_STRING);
        populateTextField("contentCommentField", CONTENT_COMMENT);
        populateComboBox("lastContentFlagComboBox", LAST_CONTENT_FLAG_STRING);
        populateTextField("lastContentCommentField", LAST_CONTENT_COMMENT);
        populateComboBox("lastPubTypeComboBox", buildPublicationType());
        populateTextField("commentField", COMMENT);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void validateFieldAndVerifyErrorMessage(TextField field, String value, String errorMessage,
                                                    boolean isValid) {
        field.setValue(value);
        binder.validate();
        List<HasValue<?>> fields = binder.getFields()
            .filter(actualField -> actualField.equals(field))
            .collect(Collectors.toList());
        assertEquals(1, fields.size());
        TextField actualField = (TextField) fields.get(0);
        assertNotNull(actualField);
        String actualErrorMessage = Objects.nonNull(actualField.getErrorMessage())
            ? actualField.getErrorMessage().toString()
            : null;
        assertEquals(value, actualField.getValue());
        assertEquals(errorMessage, actualErrorMessage);
        assertEquals(isValid, Objects.isNull(actualErrorMessage));
    }

    private String buildStringWithExpectedLength(int length) {
        return StringUtils.repeat('a', length);
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
    }
}
