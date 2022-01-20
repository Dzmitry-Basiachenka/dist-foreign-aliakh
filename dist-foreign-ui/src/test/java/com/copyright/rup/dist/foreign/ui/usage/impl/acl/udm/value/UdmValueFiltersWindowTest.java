package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

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
    private static final String CAPTION_OPERATOR = "Operator";
    private static final List<FilterOperatorEnum> FLAG_ITEMS =
        Arrays.asList(FilterOperatorEnum.Y, FilterOperatorEnum.N, FilterOperatorEnum.IS_NULL);
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String LAST_VALUE_PERIOD = "202106";
    private static final Integer WR_WRK_INST = 243904752;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final String SYSTEM_STANDARD_NUMBER = "0927-7765";
    private static final Long RH_ACCOUNT_NUMBER = 100000001L;
    private static final String RH_NAME = "Rothchild Consultants";
    private static final BigDecimal PRICE = new BigDecimal("100.00");
    private static final BigDecimal PRICE_IN_USD = new BigDecimal("200.00");
    private static final FilterExpression<Boolean> PRICE_FLAG = new FilterExpression<>(FilterOperatorEnum.Y);
    private static final String PRICE_COMMENT = "price comment";
    private static final FilterExpression<Boolean> LAST_PRICE_FLAG = new FilterExpression<>(FilterOperatorEnum.Y);
    private static final String LAST_PRICE_COMMENT = "last price comment";
    private static final BigDecimal CONTENT = new BigDecimal("70");
    private static final FilterExpression<Boolean> CONTENT_FLAG = new FilterExpression<>(FilterOperatorEnum.Y);
    private static final String CONTENT_COMMENT = "content comment";
    private static final FilterExpression<Boolean> LAST_CONTENT_FLAG = new FilterExpression<>(FilterOperatorEnum.N);
    private static final String LAST_CONTENT_COMMENT = "last content comment";
    private static final String COMMENT = "comment";
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";
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
        verifyWindow(window, "UDM values additional filters", 600, 650, Unit.PIXELS);
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmValueFilter valueFilter = buildExpectedFilter();
        valueFilter.setAssignees(Collections.singleton(ASSIGNEE));
        valueFilter.setLastValuePeriods(Collections.singleton(LAST_VALUE_PERIOD));
        valueFilter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setCurrency(USD_CURRENCY);
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null));
        valueFilter.setPriceFlagExpression(PRICE_FLAG);
        valueFilter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT, null));
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setContentFlagExpression(CONTENT_FLAG);
        valueFilter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null));
        valueFilter.setLastContentFlagExpression(LAST_CONTENT_FLAG);
        valueFilter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT, null));
        valueFilter.setLastPriceFlagExpression(LAST_PRICE_FLAG);
        valueFilter.setLastPubType(buildPublicationType());
        valueFilter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT, null));
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
    public void testWrWrkInstFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(1);
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(2);
    }

    @Test
    public void testSystemStandardNumberFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(3);
    }

    @Test
    public void testRhAccountNumberOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(4);
    }

    @Test
    public void testRhNameFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(5);
    }

    @Test
    public void testPriceFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(7);
    }

    @Test
    public void testPriceInUsdFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(8);
    }

    @Test
    public void testPriceCommentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(10);
    }

    @Test
    public void testLastPriceCommentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(11);
    }

    @Test
    public void testContentFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(12);
    }

    @Test
    public void testContentCommentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(14);
    }

    @Test
    public void testLastContentCommentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(15);
    }

    @Test
    public void testCommentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(16);
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
        TextField wrWrkInstFromField = Whitebox.getInternalState(window, "wrWrkInstFromField");
        TextField wrWrkInstToField = Whitebox.getInternalState(window, "wrWrkInstToField");
        ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox =
            Whitebox.getInternalState(window, "wrWrkInstOperatorComboBox");
        assertNumericOperatorComboBoxItems(wrWrkInstOperatorComboBox);
        verifyIntegerOperationValidations(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            "Field value should be greater or equal to Wr Wrk Inst From", 9);
    }

    @Test
    public void testSystemTitleValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "systemTitleOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2000), null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2001),
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testSystemStandardNumberValidation() {
        TextField systemStandardNumberField = Whitebox.getInternalState(window, "systemStandardNumberField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "systemStandardNumberOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(systemStandardNumberField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(systemStandardNumberField, buildStringWithExpectedLength(1000), null, true);
        validateFieldAndVerifyErrorMessage(systemStandardNumberField, buildStringWithExpectedLength(1001),
            "Field value should not exceed 1000 characters", false);
    }

    @Test
    public void testRhAccountNumberFieldValidation() {
        TextField rhAccountNumberFromField = Whitebox.getInternalState(window, "rhAccountNumberFromField");
        TextField rhAccountNumberToField = Whitebox.getInternalState(window, "rhAccountNumberToField");
        ComboBox<FilterOperatorEnum> rhAccountNumberOperatorComboBox =
            Whitebox.getInternalState(window, "rhAccountNumberOperatorComboBox");
        assertNumericOperatorComboBoxItems(rhAccountNumberOperatorComboBox);
        verifyIntegerOperationValidations(rhAccountNumberFromField, rhAccountNumberToField,
            rhAccountNumberOperatorComboBox, "Field value should be greater or equal to RH Account # From", 10);
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
        TextField priceFromField = Whitebox.getInternalState(window, "priceFromField");
        TextField priceToField = Whitebox.getInternalState(window, "priceToField");
        ComboBox<FilterOperatorEnum> priceOperatorComboBox =
            Whitebox.getInternalState(window, "priceOperatorComboBox");
        assertNumericOperatorComboBoxItems(priceOperatorComboBox);
        verifyAmountValidationZeroAllowed(priceFromField, priceToField,
            priceOperatorComboBox, "Field value should be greater or equal to Price From");
    }

    @Test
    public void testPriceInUsdValidation() {
        TextField priceInUsdFromField = Whitebox.getInternalState(window, "priceInUsdFromField");
        TextField priceInUsdToField = Whitebox.getInternalState(window, "priceInUsdToField");
        ComboBox<FilterOperatorEnum> priceInUsdOperatorComboBox =
            Whitebox.getInternalState(window, "priceInUsdOperatorComboBox");
        assertNumericOperatorComboBoxItems(priceInUsdOperatorComboBox);
        verifyAmountValidationZeroAllowed(priceInUsdFromField, priceInUsdToField,
            priceInUsdOperatorComboBox, "Field value should be greater or equal to Price in USD From");
    }

    @Test
    public void testPriceCommentValidation() {
        validateCommentField("priceCommentField", "priceCommentOperatorComboBox");
    }

    @Test
    public void testLastPriceCommentValidation() {
        validateCommentField("lastPriceCommentField", "lastPriceCommentOperatorComboBox");
    }

    @Test
    public void testContentValidation() {
        TextField contentFromField = Whitebox.getInternalState(window, "contentFromField");
        TextField contentToField = Whitebox.getInternalState(window, "contentToField");
        ComboBox<FilterOperatorEnum> contentOperatorComboBox =
            Whitebox.getInternalState(window, "contentOperatorComboBox");
        assertNumericOperatorComboBoxItems(contentOperatorComboBox);
        verifyAmountValidationZeroDenied(contentFromField, contentToField,
            contentOperatorComboBox, "Field value should be greater or equal to Content From");
    }

    @Test
    public void testContentCommentValidation() {
        validateCommentField("contentCommentField", "contentCommentOperatorComboBox");
    }

    @Test
    public void testLastContentCommentValidation() {
        validateCommentField("lastContentCommentField", "lastContentCommentOperatorComboBox");
    }

    @Test
    public void testCommentValidation() {
        validateCommentField("commentField", "commentOperatorComboBox");
    }

    private VerticalLayout verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Save", "Clear", "Close");
        return verticalLayout;
    }

    private void verifyPanel(Component component) {
        assertTrue(component instanceof Panel);
        Component panelContent = ((Panel) component).getContent();
        assertTrue(panelContent instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) panelContent;
        assertEquals(17, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Last Value Periods");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(1), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(2), "System Title");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(3), "System Standard Number");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(4), "RH Account # From", "RH Account # To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(5), "RH Name");
        verifyComboBoxLayout(verticalLayout.getComponent(6), "Currency", CURRENCIES, "Last Pub Type",
            Arrays.asList(new PublicationType(), buildPublicationType()));
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(7), "Price From", "Price To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(8), "Price in USD From", "Price in USD To");
        verifyComboBoxLayout(verticalLayout.getComponent(9), "Price Flag", FLAG_ITEMS, "Last Price Flag", FLAG_ITEMS);
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(10), "Price Comment");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(11), "Last Price Comment");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(12), "Content From", "Content To");
        verifyComboBoxLayout(verticalLayout.getComponent(13), "Content Flag", FLAG_ITEMS, "Last Content Flag",
            FLAG_ITEMS);
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(14), "Content Comment");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(15), "Last Content Comment");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(16), "Comment");
    }

    private void verifyComboBoxLayout(Component component, String firstCaption, List<?> firstItemList,
                                      String secondCaption, List<?> secondItemList) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        verifyComboBox(layout.getComponent(0), firstCaption, true, firstItemList);
        verifyComboBox(layout.getComponent(1), secondCaption, true, secondItemList);
    }

    private void verifyFieldWithTextOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals(CAPTION_OPERATOR, layout.getComponent(1).getCaption());
    }

    private void verifyFieldWithNumericOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof TextField);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals(CAPTION_OPERATOR, layout.getComponent(2).getCaption());
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        VerticalLayout panelContent = (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) panelContent.getComponent(index);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
        assertEquals(5, ((ListDataProvider<?>) operatorComboBox.getDataProvider()).getItems().size());
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.DOES_NOT_EQUAL);
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.CONTAINS);
        assertTrue(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NULL);
        assertFalse(textField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NOT_NULL);
        assertFalse(textField.isEnabled());
    }

    @SuppressWarnings(UNCHECKED)
    private void testNumericFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        VerticalLayout panelContent = (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) panelContent.getComponent(index);
        TextField fromField = (TextField) horizontalLayout.getComponent(0);
        TextField toField = (TextField) horizontalLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(2);
        assertEquals(9, ((ListDataProvider<?>) operatorComboBox.getDataProvider()).getItems().size());
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.DOES_NOT_EQUAL);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(fromField.isEnabled());
        assertTrue(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NULL);
        assertFalse(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NOT_NULL);
        assertFalse(fromField.isEnabled());
        assertFalse(toField.isEnabled());
    }

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                   String fieldSpecificErrorMessage, int length) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "12345679", null, true);
        validateFieldAndVerifyErrorMessage(toField, "12345678", fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, buildStringWithExpectedLength(length + 1),
            String.format("Field value should not exceed %d digits", length), false);
        validateFieldAndVerifyErrorMessage(toField, buildStringWithExpectedLength(length + 1),
            String.format("Field value should not exceed %d digits", length), false);
    }

    private void verifyCommonOperationValidations(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                  String numberValidationMessage) {
        validateFieldAndVerifyErrorMessage(fromField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(fromField, INTEGER_WITH_SPACES_STRING, null, true);
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        validateFieldAndVerifyErrorMessage(fromField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, numberValidationMessage, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        validateFieldAndVerifyErrorMessage(fromField, VALID_INTEGER, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_INTEGER, null, true);
    }

    private void verifyAmountValidationZeroAllowed(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                   String fieldSpecificErrorMessage) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, BETWEEN_OPERATOR_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", fieldSpecificErrorMessage, false);
        String numberValidationMessage =
            "Field value should be positive number or zero and should not exceed 10 digits";
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, numberValidationMessage, false);
        verifyAmountValidationZeroAllowed(fromField, numberValidationMessage);
        verifyAmountValidationZeroAllowed(toField, numberValidationMessage);
    }

    private void verifyAmountValidationZeroAllowed(TextField textField,
                                                   String fieldSpecificErrorMessage) {
        validateFieldAndVerifyErrorMessage(textField, "0", null, true);
        validateFieldAndVerifyErrorMessage(textField, " 0.004 ", null, true);
        verifyCommonAmountValidations(textField, fieldSpecificErrorMessage);
    }

    private void verifyAmountValidationZeroDenied(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                  String fieldSpecificErrorMessage) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, BETWEEN_OPERATOR_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", fieldSpecificErrorMessage, false);
        String numberValidationMessage = "Field value should be positive number and should not exceed 10 digits";
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, numberValidationMessage, false);
        verifyAmountValidationZeroDenied(fromField, numberValidationMessage);
        verifyAmountValidationZeroDenied(toField, numberValidationMessage);
    }

    private void verifyAmountValidationZeroDenied(TextField textField,
                                                  String fieldSpecificErrorMessage) {
        validateFieldAndVerifyErrorMessage(textField, "0", fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(textField, " 0.004 ", fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(textField, " 0.005 ", null, true);
        verifyCommonAmountValidations(textField, fieldSpecificErrorMessage);
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

    public void validateCommentField(String fieldName, String operatorComboboxName) {
        TextField priceCommentField = Whitebox.getInternalState(window, fieldName);
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, operatorComboboxName));
        validateFieldAndVerifyErrorMessage(priceCommentField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(priceCommentField, buildStringWithExpectedLength(1024), null, true);
        validateFieldAndVerifyErrorMessage(priceCommentField, buildStringWithExpectedLength(1025),
            "Field value should not exceed 1024 characters", false);
    }

    private UdmValueFilter buildExpectedFilter() {
        UdmValueFilter valueFilter = new UdmValueFilter();
        valueFilter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setSystemStandardNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_STANDARD_NUMBER, null));
        valueFilter.setRhAccountNumberExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_ACCOUNT_NUMBER, null));
        valueFilter.setRhNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, RH_NAME, null));
        valueFilter.setCurrency(USD_CURRENCY);
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE, null));
        valueFilter.setPriceInUsdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_IN_USD, null));
        valueFilter.setPriceCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_COMMENT, null));
        valueFilter.setPriceFlagExpression(PRICE_FLAG);
        valueFilter.setLastPriceFlagExpression(LAST_PRICE_FLAG);
        valueFilter.setLastPriceCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_PRICE_COMMENT, null));
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT, null));
        valueFilter.setContentFlagExpression(CONTENT_FLAG);
        valueFilter.setContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_COMMENT, null));
        valueFilter.setLastContentFlagExpression(LAST_CONTENT_FLAG);
        valueFilter.setLastContentCommentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, LAST_CONTENT_COMMENT, null));
        valueFilter.setLastPubType(buildPublicationType());
        valueFilter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT, null));
        return valueFilter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("assigneeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("lastValuePeriodFilterWidget", "(1)");
        assertTextFieldValue("wrWrkInstFromField", WR_WRK_INST.toString());
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        assertComboBoxValue("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhAccountNumberFromField", RH_ACCOUNT_NUMBER.toString());
        assertComboBoxValue("rhAccountNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("rhNameField", RH_NAME);
        assertComboBoxValue("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("currencyComboBox", USD_CURRENCY);
        assertTextFieldValue("priceFromField", PRICE.toString());
        assertComboBoxValue("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("priceInUsdFromField", PRICE_IN_USD.toString());
        assertComboBoxValue("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("priceFlagComboBox", FilterOperatorEnum.Y);
        assertTextFieldValue("priceCommentField", PRICE_COMMENT);
        assertComboBoxValue("priceCommentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("lastPriceFlagComboBox", FilterOperatorEnum.Y);
        assertTextFieldValue("lastPriceCommentField", LAST_PRICE_COMMENT);
        assertComboBoxValue("lastPriceCommentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("contentFromField", CONTENT.toString());
        assertComboBoxValue("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("contentFlagComboBox", FilterOperatorEnum.Y);
        assertTextFieldValue("contentCommentField", CONTENT_COMMENT);
        assertComboBoxValue("contentCommentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("lastContentFlagComboBox", FilterOperatorEnum.N);
        assertTextFieldValue("lastContentCommentField", LAST_CONTENT_COMMENT);
        assertComboBoxValue("lastContentCommentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("lastPubTypeComboBox", buildPublicationType());
        assertTextFieldValue("commentField", COMMENT);
        assertComboBoxValue("commentOperatorComboBox", FilterOperatorEnum.EQUALS);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertTextFieldValue(String fieldName, Object value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateTextField("wrWrkInstFromField", String.valueOf(WR_WRK_INST));
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemStandardNumberField", SYSTEM_STANDARD_NUMBER);
        populateComboBox("systemStandardNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhAccountNumberFromField", String.valueOf(RH_ACCOUNT_NUMBER));
        populateComboBox("rhAccountNumberOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("rhNameField", RH_NAME);
        populateComboBox("rhNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("currencyComboBox", USD_CURRENCY);
        populateTextField("priceFromField", PRICE.toString());
        populateComboBox("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("priceInUsdFromField", PRICE_IN_USD.toString());
        populateComboBox("priceInUsdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("priceFlagComboBox", FilterOperatorEnum.Y);
        populateTextField("priceCommentField", PRICE_COMMENT);
        populateComboBox("lastPriceFlagComboBox", FilterOperatorEnum.Y);
        populateTextField("lastPriceCommentField", LAST_PRICE_COMMENT);
        populateTextField("contentFromField", String.valueOf(CONTENT));
        populateComboBox("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("contentFlagComboBox", FilterOperatorEnum.Y);
        populateTextField("contentCommentField", CONTENT_COMMENT);
        populateComboBox("lastContentFlagComboBox", FilterOperatorEnum.N);
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
        return StringUtils.repeat('1', length);
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
    }
}
