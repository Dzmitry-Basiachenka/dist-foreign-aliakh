package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Verifies {@link UdmBaselineFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFiltersWindowTest {

    private static final String LC_DESCRIPTION = "Law Firms";
    private static final String UNCHECKED = "unchecked";
    private static final String SYSTEM_TITLE = "Medical journal";
    private static final String SURVEY_COUNTRY = "United States";
    private static final String USAGE_DETAIL_ID = "OGN174GHHSB109";
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String CAPTION_OPERATOR = "Operator";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String DECIMAL_VALIDATION_MESSAGE =
        "Field value should be positive number and should not exceed 10 digits";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";

    private UdmBaselineFiltersWindow window;
    private Binder<UdmBaselineFilter> binder;

    @Before
    public void setUp() {
        window = new UdmBaselineFiltersWindow(createMock(IUdmBaselineFilterController.class), new UdmBaselineFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM baseline additional filters", window.getCaption());
        assertEquals(750, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(365, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmBaselineFilter baselineFilter = buildExpectedFilter();
        replay(ForeignSecurityUtils.class);
        window = new UdmBaselineFiltersWindow(createMock(IUdmBaselineFilterController.class), baselineFilter);
        verifyFiltersData();
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testWrWrkInstFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(2);
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(3);
    }

    @Test
    public void testUsageDetailIdFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(4);
    }

    @Test
    public void testSurveyCountryFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(5);
    }

    @Test
    public void testAnnualizedCopiesFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(6);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmBaselineFilter appliedBaselineFilter = window.getAppliedBaselineFilter();
        assertTrue(appliedBaselineFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedBaselineFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(7);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedBaselineFilter().isEmpty());
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
    public void testSystemTitleValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
        ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox =
            Whitebox.getInternalState(window, "systemTitleOperatorComboBox");
        assertTextOperatorComboBoxItems(systemTitleOperatorComboBox);
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2000), null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2001),
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testUsageDetailIdValidation() {
        TextField usageDetailIdField = Whitebox.getInternalState(window, "usageDetailIdField");
        ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox =
            Whitebox.getInternalState(window, "usageDetailIdOperatorComboBox");
        assertTextOperatorComboBoxItems(usageDetailIdOperatorComboBox);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(50), null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(51),
            "Field value should not exceed 50 characters", false);
    }

    @Test
    public void testSurveyCountryValidation() {
        TextField surveyCountryField = Whitebox.getInternalState(window, "surveyCountryField");
        ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox =
            Whitebox.getInternalState(window, "surveyCountryOperatorComboBox");
        assertTextOperatorComboBoxItems(surveyCountryOperatorComboBox);
        validateFieldAndVerifyErrorMessage(surveyCountryField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(100), null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(101),
            "Field value should not exceed 100 characters", false);
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        TextField annualizedCopiesFromField = Whitebox.getInternalState(window, "annualizedCopiesFrom");
        TextField annualizedCopiesToField = Whitebox.getInternalState(window, "annualizedCopiesTo");
        ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
            Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox");
        assertNumericOperatorComboBoxItems(annualizedCopiesOperatorComboBox);
        verifyBigDecimalOperationValidations(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(8, verticalLayout.getComponentCount());
        verifyFilterLayout(verticalLayout.getComponent(0), "Detail Licensee Classes", "Aggregate Licensee Classes");
        verifyItemsFilterWidget(verticalLayout.getComponent(1), "Types of Use");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(2), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(3), "System Title");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(4), "Usage Detail ID");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(5), "Survey Country");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(6), "Annualized Copies From",
            "Annualized Copies To");
        verifyButtonsLayout(verticalLayout.getComponent(7), "Save", "Clear", "Close");
    }

    private void verifyFilterLayout(Component component, String... captions) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        IntStream.range(0, captions.length)
            .forEach(index -> verifyItemsFilterWidget(layout.getComponent(index), captions[index]));
    }

    private void verifyFieldWithTextOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextFieldComponent(layout.getComponent(0), caption);
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

    private void verifyTextFieldComponent(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertTrue(component.isEnabled());
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

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, BETWEEN_OPERATOR_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.2345677",
            "Field value should be greater or equal to Annualized Copies From", false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
    }

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "12345679", null, true);
        validateFieldAndVerifyErrorMessage(toField, "12345678",
            "Field value should be greater or equal to Wr Wrk Inst From", false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "1234567890", "Field value should not exceed 9 digits", false);
        validateFieldAndVerifyErrorMessage(toField, "1234567890", "Field value should not exceed 9 digits", false);
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
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, numberValidationMessage, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        validateFieldAndVerifyErrorMessage(fromField, VALID_INTEGER, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_INTEGER, null, true);
    }

    private UdmBaselineFilter buildExpectedFilter() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(26);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(26);
        aggregateLicenseeClass.setDescription(LC_DESCRIPTION);
        UdmBaselineFilter filter = new UdmBaselineFilter();
        filter.setDetailLicenseeClasses(Collections.singleton(detailLicenseeClass));
        filter.setAggregateLicenseeClasses(Collections.singleton(aggregateLicenseeClass));
        filter.setReportedTypeOfUses(Collections.singleton("COPY_FOR_MYSELF"));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, 243904752, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        return filter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("aggregateLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(1)");
        assertTextFieldValue("wrWrkInstFromField", "243904752");
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("usageDetailIdField", USAGE_DETAIL_ID);
        assertComboBoxValue("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("surveyCountryField", SURVEY_COUNTRY);
        assertComboBoxValue("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("annualizedCopiesFrom", "5.5");
        assertComboBoxValue("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertTextFieldValue(String fieldName, String value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL, FilterOperatorEnum.CONTAINS,
                FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(26);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(26);
        aggregateLicenseeClass.setDescription(LC_DESCRIPTION);
        UdmBaselineFilter baselineFilter = Whitebox.getInternalState(window, "baselineFilter");
        baselineFilter.setDetailLicenseeClasses(Collections.singleton(detailLicenseeClass));
        baselineFilter.setAggregateLicenseeClasses(Collections.singleton(aggregateLicenseeClass));
        baselineFilter.setReportedTypeOfUses(Collections.singleton("COPY_FOR_MYSELF"));
        populateTextField("wrWrkInstFromField", "243904752");
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("usageDetailIdField", USAGE_DETAIL_ID);
        populateComboBox("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("surveyCountryField", SURVEY_COUNTRY);
        populateComboBox("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("annualizedCopiesFrom", "5.5");
        populateComboBox("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
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
        assertEquals(1 , fields.size());
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
}
