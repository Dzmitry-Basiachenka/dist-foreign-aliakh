package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyTextField;
import static com.copyright.rup.dist.foreign.ui.usage.UiCommonHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineValueFilter;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueFilterController;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
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
import org.powermock.api.easymock.PowerMock;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
    private static final List<String> Y_N_ITEMS = Arrays.asList("Y", "N");
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final Boolean PRICE_FLAG = true;
    private static final String PRICE_FLAG_STRING = "Y";
    private static final Boolean CONTENT_FLAG = false;
    private static final String CONTENT_FLAG_STRING = "N";
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

    private UdmBaselineValueFiltersWindow window;
    private Binder<UdmBaselineValueFilter> binder;

    @Before
    public void setUp() {
        IUdmBaselineValueFilterController controller = PowerMock.createMock(IUdmBaselineValueFilterController.class);
        expect(controller.getPublicationTypes()).andReturn(
            new ArrayList<>(Collections.singletonList(buildPublicationType()))).once();
        replay(controller);
        window = new UdmBaselineValueFiltersWindow(controller, new UdmBaselineValueFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
        verify(controller);
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "UDM baseline values additional filters", 550, 400, Unit.PIXELS);
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmBaselineValueFilter valueFilter = buildExpectedFilter();
        valueFilter.setWrWrkInst(WR_WRK_INST);
        valueFilter.setPubType(buildPublicationType());
        valueFilter.setPriceFlag(PRICE_FLAG);
        valueFilter.setContentFlag(CONTENT_FLAG);
        valueFilter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        valueFilter.setPriceExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        valueFilter.setContentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_FROM, CONTENT_TO));
        valueFilter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        valueFilter.setComment(COMMENT);
        IUdmBaselineValueFilterController controller = PowerMock.createMock(IUdmBaselineValueFilterController.class);
        expect(controller.getPublicationTypes()).andReturn(
            new ArrayList<>(Collections.singletonList(buildPublicationType()))).once();
        replay(controller);
        window = new UdmBaselineValueFiltersWindow(controller, valueFilter);
        verify(controller);
        verifyFiltersData();
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertTextFieldValue("wrWrkInstField", WR_WRK_INST.toString());
        assertComboBoxValue("pubTypeComboBox", buildPublicationType());
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertComboBoxValue("contentFlagComboBox", CONTENT_FLAG_STRING);
        assertComboBoxValue("priceFlagComboBox", PRICE_FLAG_STRING);
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
    }

    @Test
    public void testTitleFilterOperatorChangeListener() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(1);
        TextField textFieldFrom = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(textFieldFrom.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.CONTAINS);
        assertTrue(textFieldFrom.isEnabled());
    }

    @Test
    public void testPriceFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(3);
    }

    @Test
    public void testContentFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(4);
    }

    @Test
    public void testContentUnitPriceFilterOperatorChangeListener() {
        testFilterOperatorChangeListener(5);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmBaselineValueFilter appliedUsageFilter = window.getBaselineValueFilter();
        assertTrue(appliedUsageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getBaselineValueFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getBaselineValueFilter().isEmpty());
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "1234567890", binder,
            "Field value should not exceed 9 digits", false);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "123456789", binder, null, true);
        validateFieldAndVerifyErrorMessage(wrWrkInstField, "234fdsfs", binder,
            "Field value should contain numeric values only", false);
    }

    @Test
    public void testSystemTitleFieldValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
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
        assertOperatorComboboxItems(priceOperatorComboBox);
        verifyBigDecimalOperationValidations(priceFromField, priceToField,
            priceOperatorComboBox, "Field value should be greater or equal to Price From");
    }

    @Test
    public void testContentValidation() {
        TextField contentFromField = Whitebox.getInternalState(window, "contentFromField");
        TextField contentToField = Whitebox.getInternalState(window, "contentToField");
        ComboBox<FilterOperatorEnum> contentOperatorComboBox =
            Whitebox.getInternalState(window, "contentOperatorComboBox");
        assertOperatorComboboxItems(contentOperatorComboBox);
        verifyBigDecimalOperationValidations(contentFromField, contentToField,
            contentOperatorComboBox, "Field value should be greater or equal to Content From");
    }

    @Test
    public void testContentUnitPriceValidation() {
        TextField contentUnitPriceFromField = Whitebox.getInternalState(window, "contentUnitPriceFromField");
        TextField contentUnitPriceToField = Whitebox.getInternalState(window, "contentUnitPriceToField");
        ComboBox<FilterOperatorEnum> contentUnitPriceComboBox =
            Whitebox.getInternalState(window, "contentUnitPriceComboBox");
        assertOperatorComboboxItems(contentUnitPriceComboBox);
        verifyBigDecimalOperationValidations(contentUnitPriceFromField, contentUnitPriceToField,
            contentUnitPriceComboBox, "Field value should be greater or equal to Content Unit Price From");
    }

    @Test
    public void testCommentValidation() {
        TextField commentField = Whitebox.getInternalState(window, "commentField");
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.repeat('a', 1024), binder, null, true);
        validateFieldAndVerifyErrorMessage(commentField, StringUtils.repeat('a', 1025), binder,
            "Field value should not exceed 1024 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyTextFieldLayout(
            verticalLayout.getComponent(0), TextField.class, "Wr Wrk Inst", ComboBox.class, "Pub Type");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(1), "System Title");
        verifyTextFieldLayout(verticalLayout.getComponent(2), ComboBox.class, "Price Flag",
            ComboBox.class, "Content Flag");
        assertComboboxItems("priceFlagComboBox", Y_N_ITEMS);
        assertComboboxItems("contentFlagComboBox", Y_N_ITEMS);
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(3), "Price From", "Price To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(4), "Content From", "Content To");
        verifyLayoutWithOperatorComponent(verticalLayout.getComponent(5), "Content Unit Price From",
            "Content Unit Price To");
        verifyTextField(verticalLayout.getComponent(6), "Comment");
    }

    private void verifyLayoutWithOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), caption);
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(1).getCaption());
    }

    private void verifyLayoutWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        verifyTextField(layout.getComponent(0), captionFrom);
        verifyTextField(layout.getComponent(1), captionTo);
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(2).getCaption());
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

    @SuppressWarnings(UNCHECKED)
    private void testFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
        TextField textFieldFrom = (TextField) horizontalLayout.getComponent(0);
        TextField textFieldTo = (TextField) horizontalLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(2);
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(textFieldFrom.isEnabled());
        assertFalse(textFieldTo.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(textFieldFrom.isEnabled());
        assertFalse(textFieldTo.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(textFieldFrom.isEnabled());
        assertFalse(textFieldTo.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(textFieldFrom.isEnabled());
        assertTrue(textFieldTo.isEnabled());
    }

    private void assertTextFieldValue(String fieldName, Object value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboboxItems(String fieldName, List<T> expectedItems) {
        assertComboboxItems((ComboBox<T>) Whitebox.getInternalState(window, fieldName), expectedItems);
    }

    private <T> void assertComboboxItems(ComboBox<T> comboBox, List<T> expectedItems) {
        ListDataProvider<T> listDataProvider = (ListDataProvider<T>) comboBox.getDataProvider();
        Collection<?> actualItems = listDataProvider.getItems();
        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems, actualItems);
    }

    private void assertOperatorComboboxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        assertComboboxItems(operatorComboBox, Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.GREATER_THAN,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.BETWEEN));
    }

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox,
                                                      String fieldSpecificErrorMessage) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, BETWEEN_OPERATOR_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, null, true);
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

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateTextField("wrWrkInstField", String.valueOf(WR_WRK_INST));
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateComboBox("priceFlagComboBox", PRICE_FLAG_STRING);
        populateComboBox("contentFlagComboBox", CONTENT_FLAG_STRING);
        populateComboBox("pubTypeComboBox", buildPublicationType());
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
        udmBaselineValueFilter.setWrWrkInst(WR_WRK_INST);
        udmBaselineValueFilter.setPubType(buildPublicationType());
        udmBaselineValueFilter.setSystemTitleExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        udmBaselineValueFilter.setPriceFlag(PRICE_FLAG);
        udmBaselineValueFilter.setContentFlag(CONTENT_FLAG);
        udmBaselineValueFilter.setPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        udmBaselineValueFilter.setContentExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_FROM, CONTENT_TO));
        udmBaselineValueFilter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, PRICE_FROM, PRICE_TO));
        udmBaselineValueFilter.setComment(COMMENT);
        return udmBaselineValueFilter;
    }

    private PublicationType buildPublicationType() {
        PublicationType publicationType = new PublicationType();
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        return publicationType;
    }
}
