package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;

import com.vaadin.data.Binder;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link UdmBaselineValueFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/26/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String CAPTION_OPERATOR = "Operator";
    private static final List<FilterOperatorEnum> FLAG_ITEMS = List.of(FilterOperatorEnum.Y, FilterOperatorEnum.N);
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final FilterOperatorEnum PRICE_FLAG = FilterOperatorEnum.Y;
    private static final FilterOperatorEnum CONTENT_FLAG = FilterOperatorEnum.N;
    private static final FilterOperatorEnum CUP_FLAG = FilterOperatorEnum.N;
    private static final BigDecimal PRICE_FROM = new BigDecimal("100.00");
    private static final BigDecimal PRICE_TO = new BigDecimal("120.00");
    private static final BigDecimal CONTENT_FROM = new BigDecimal("70");
    private static final BigDecimal CONTENT_TO = new BigDecimal("80");
    private static final String COMMENT = "comment";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String VALID_INTEGER = "123456789";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String DECIMAL_VALIDATION_MESSAGE =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";

    private UdmBaselineValueFiltersWindow window;
    private Binder<UdmBaselineValueFilter> binder;

    @Before
    public void setUp() {
        window = new UdmBaselineValueFiltersWindow(new UdmBaselineValueFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "UDM baseline values additional filters", 600, 400, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmBaselineValueFilter valueFilter = buildExpectedFilter();
        valueFilter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        valueFilter.setPriceFlagExpression(new FilterExpression<>(PRICE_FLAG));
        valueFilter.setContentFlagExpression(new FilterExpression<>(CONTENT_FLAG));
        valueFilter.setContentUnitPriceFlagExpression(new FilterExpression<>(CUP_FLAG));
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_FROM, CONTENT_TO));
        valueFilter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        valueFilter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT, null));
        window = new UdmBaselineValueFiltersWindow(valueFilter);
        verifyFiltersData();
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(1);
    }

    @Test
    public void testPriceFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(3);
    }

    @Test
    public void testContentFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(4);
    }

    @Test
    public void testContentUnitPriceFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(5);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmBaselineValueFilter baselineValueFilter = Whitebox.getInternalState(window, "baselineValueFilter");
        assertTrue(baselineValueFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), baselineValueFilter);
    }

    @Test
    public void testClearButtonClickListener() {
        UdmBaselineValueFilter baselineValueFilter = buildExpectedFilter();
        Whitebox.setInternalState(window, "baselineValueFilter", baselineValueFilter);
        assertFalse(baselineValueFilter.isEmpty());
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertTrue(baselineValueFilter.isEmpty());
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstFromField = Whitebox.getInternalState(window, "wrWrkInstFromField");
        TextField wrWrkInstToField = Whitebox.getInternalState(window, "wrWrkInstToField");
        ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox =
            Whitebox.getInternalState(window, "wrWrkInstOperatorComboBox");
        assertNumericOperatorComboBoxItems(wrWrkInstOperatorComboBox);
        verifyIntegerOperationValidations(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
    }

    @Test
    public void testSystemTitleFieldValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
        ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox =
            Whitebox.getInternalState(window, "systemTitleOperatorComboBox");
        assertTextOperatorComboBoxItems(systemTitleOperatorComboBox);
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, SPACES_STRING, binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.repeat('a', 2000), binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.repeat('a', 2001), binder,
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testPriceValidation() {
        TextField priceFromField = Whitebox.getInternalState(window, "priceFromField");
        TextField priceToField = Whitebox.getInternalState(window, "priceToField");
        ComboBox<FilterOperatorEnum> priceOperatorComboBox =
            Whitebox.getInternalState(window, "priceOperatorComboBox");
        assertNumericOperatorComboBoxItems(priceOperatorComboBox);
        verifyBigDecimalOperationValidations(priceFromField, priceToField,
            priceOperatorComboBox, "Field value should be greater or equal to Price From");
    }

    @Test
    public void testContentValidation() {
        TextField contentFromField = Whitebox.getInternalState(window, "contentFromField");
        TextField contentToField = Whitebox.getInternalState(window, "contentToField");
        ComboBox<FilterOperatorEnum> contentOperatorComboBox =
            Whitebox.getInternalState(window, "contentOperatorComboBox");
        assertNumericOperatorComboBoxItems(contentOperatorComboBox);
        verifyBigDecimalOperationValidations(contentFromField, contentToField,
            contentOperatorComboBox, "Field value should be greater or equal to Content From");
    }

    @Test
    public void testContentUnitPriceValidation() {
        TextField contentUnitPriceFromField = Whitebox.getInternalState(window, "contentUnitPriceFromField");
        TextField contentUnitPriceToField = Whitebox.getInternalState(window, "contentUnitPriceToField");
        ComboBox<FilterOperatorEnum> contentUnitPriceComboBox =
            Whitebox.getInternalState(window, "contentUnitPriceComboBox");
        assertNumericOperatorComboBoxItems(contentUnitPriceComboBox);
        verifyBigDecimalOperationValidations(contentUnitPriceFromField, contentUnitPriceToField,
            contentUnitPriceComboBox, "Field value should be greater or equal to Content Unit Price From");
    }

    @Test
    public void testCommentValidation() {
        TextField commentField = Whitebox.getInternalState(window, "commentField");
        ComboBox<FilterOperatorEnum> commentOperatorComboBox =
            Whitebox.getInternalState(window, "commentOperatorComboBox");
        assertTextOperatorComboBoxItems(commentOperatorComboBox);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.repeat('a', 1024), binder, null, true);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.repeat('a', 1025), binder,
            "Field value should not exceed 1024 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(0), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(1), "System Title");
        verifyComboBoxLayout(verticalLayout.getComponent(2), "Price Flag", "Content Flag", "CUP Flag");
        assertComboBoxItems("priceFlagComboBox", "Price Flag", true, FLAG_ITEMS);
        assertComboBoxItems("contentFlagComboBox", "Content Flag", true, FLAG_ITEMS);
        assertComboBoxItems("contentUnitPriceFlagComboBox", "CUP Flag", true, FLAG_ITEMS);
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(3), "Price From", "Price To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(4), "Content From", "Content To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(5), "Content Unit Price From",
            "Content Unit Price To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(6), "Comment");
    }

    private void verifyLayoutWithOperatorComponent(Component component, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), caption);
        assertThat(layout.getComponent(1), instanceOf(ComboBox.class));
        assertEquals(CAPTION_OPERATOR, layout.getComponent(1).getCaption());
    }

    private void verifyLayoutWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), captionFrom);
        verifyTextField(layout.getComponent(1), captionTo);
        assertThat(layout.getComponent(2), instanceOf(ComboBox.class));
        assertEquals(CAPTION_OPERATOR, layout.getComponent(2).getCaption());
    }

    private void verifyComboBoxLayout(Component component, String... captions) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        assertEquals(layout.getComponent(0).getCaption(), captions[0]);
        assertEquals(layout.getComponent(1).getCaption(), captions[1]);
        assertEquals(layout.getComponent(2).getCaption(), captions[2]);
    }

    private void verifyFiltersData() {
        populateTextField("wrWrkInstFromField", WR_WRK_INST.toString());
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("contentFlagComboBox", CONTENT_FLAG);
        assertComboBoxValue("priceFlagComboBox", PRICE_FLAG);
        assertComboBoxValue("contentUnitPriceFlagComboBox", CUP_FLAG);
        assertTextFieldValue("priceFromField", PRICE_FROM.toString());
        assertTextFieldValue("priceToField", PRICE_TO.toString());
        assertComboBoxValue("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("contentFromField", CONTENT_FROM.toString());
        assertTextFieldValue("contentToField", CONTENT_TO.toString());
        assertComboBoxValue("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("contentUnitPriceFromField", PRICE_FROM.toString());
        assertTextFieldValue("contentUnitPriceToField", PRICE_TO.toString());
        assertComboBoxValue("contentUnitPriceComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("commentField", COMMENT);
        assertComboBoxValue("commentOperatorComboBox", FilterOperatorEnum.EQUALS);
    }

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
        TextField textField = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
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
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
        TextField fromField = (TextField) horizontalLayout.getComponent(0);
        TextField toField = (TextField) horizontalLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(2);
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

    private void assertTextFieldValue(String fieldName, Object value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private <T> void assertComboBoxItems(String fieldName, String caption, boolean emptySelectionAllowed,
                                         List<T> expectedItems) {
        verifyComboBox(Whitebox.getInternalState(window, fieldName), caption, Unit.PIXELS, -1, emptySelectionAllowed,
            expectedItems);
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            List.of(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            List.of(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL, FilterOperatorEnum.CONTAINS,
                FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "12345679", binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "12345678", binder,
            "Field value should be greater or equal to Wr Wrk Inst From", false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "1234567890", binder, "Field value should not exceed 9 digits",
            false);
        validateFieldAndVerifyErrorMessage(toField, "1234567890", binder, "Field value should not exceed 9 digits",
            false);
    }

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox,
                                                      String fieldSpecificErrorMessage) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, DECIMAL_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, DECIMAL_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, binder, DECIMAL_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", binder, fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, DECIMAL_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, DECIMAL_VALIDATION_MESSAGE, false);
    }

    private void verifyCommonOperationValidations(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                  String numberValidationMessage) {
        validateFieldAndVerifyErrorMessage(fromField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(fromField, INTEGER_WITH_SPACES_STRING, binder, null, true);
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        validateFieldAndVerifyErrorMessage(
            fromField, StringUtils.EMPTY, binder, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(
            toField, StringUtils.EMPTY, binder, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, binder, numberValidationMessage, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        validateFieldAndVerifyErrorMessage(fromField, VALID_INTEGER, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_INTEGER, binder, null, true);
    }

    private void populateData() {
        populateTextField("wrWrkInstFromField", WR_WRK_INST.toString());
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("priceFlagComboBox", PRICE_FLAG);
        populateComboBox("contentFlagComboBox", CONTENT_FLAG);
        populateComboBox("contentUnitPriceFlagComboBox", CUP_FLAG);
        populateTextField("priceFromField", PRICE_FROM.toString());
        populateTextField("priceToField", PRICE_TO.toString());
        populateComboBox("priceOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("contentFromField", String.valueOf(CONTENT_FROM));
        populateTextField("contentToField", String.valueOf(CONTENT_TO));
        populateComboBox("contentOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("contentUnitPriceFromField", PRICE_FROM.toString());
        populateTextField("contentUnitPriceToField", PRICE_TO.toString());
        populateComboBox("contentUnitPriceComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("commentField", COMMENT);
        populateComboBox("commentOperatorComboBox", FilterOperatorEnum.EQUALS);

    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private UdmBaselineValueFilter buildExpectedFilter() {
        UdmBaselineValueFilter udmBaselineValueFilter = new UdmBaselineValueFilter();
        udmBaselineValueFilter.setWrWrkInstExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        udmBaselineValueFilter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        udmBaselineValueFilter.setPriceFlagExpression(new FilterExpression<>(PRICE_FLAG));
        udmBaselineValueFilter.setContentFlagExpression(new FilterExpression<>(CONTENT_FLAG));
        udmBaselineValueFilter.setContentUnitPriceFlagExpression(new FilterExpression<>(CUP_FLAG));
        udmBaselineValueFilter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        udmBaselineValueFilter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_FROM, CONTENT_TO));
        udmBaselineValueFilter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        udmBaselineValueFilter.setCommentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMMENT, null));
        return udmBaselineValueFilter;
    }
}
