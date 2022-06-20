package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;

import com.google.common.collect.Sets;
import com.vaadin.data.Binder;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclUsageFiltersWindow}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String CAPTION_OPERATOR = "Operator";
    private static final String USAGE_FILTER = "usageFilter";
    private static final Integer LC_ID = 26;
    private static final String LC_DESCRIPTION = "Law Firms";
    private static final String PRINT_TYPE_OF_USE = "PRINT";
    private static final UdmUsageOriginEnum RFA_USAGE_ORIGIN = UdmUsageOriginEnum.RFA;
    private static final UdmChannelEnum CCC_CHANNEL = UdmChannelEnum.CCC;
    private static final String USAGE_DETAIL_ID = "8d2a1c08-4678-4d50-acd3-7625bdc03da4";
    private static final Long WR_WRK_INST = 243904752L;
    private static final String SYSTEM_TITLE = "Medical Journal";
    private static final String SURVEY_COUNTRY = "Portugal";
    private static final BigDecimal CONTENT_UNIT_PRICE = new BigDecimal("25.0000000000");
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";

    private AclUsageFiltersWindow window;
    private Binder<AclUsageFilter> binder;

    @Before
    public void setUp() {
        window = new AclUsageFiltersWindow(createMock(IAclUsageFilterController.class), new AclUsageFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
    }

    @Test
    public void testConstructor() {
        verifyWindow(window, "ACL usages additional filters", 600, 490, Unit.PIXELS);
        VerticalLayout verticalLayout = verifyRootLayout(window.getContent());
        verifyPanel(verticalLayout.getComponent(0));
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        AclUsageFilter usageFilter = buildExpectedFilter();
        usageFilter.setPeriods(Sets.newHashSet(202206, 202212));
        usageFilter.setTypeOfUses(Sets.newHashSet("DIGITAL", PRINT_TYPE_OF_USE));
        window = new AclUsageFiltersWindow(createMock(IAclUsageFilterController.class), usageFilter);
        verifyFiltersData();
    }

    @Test
    public void testSaveButtonClickListener() {
        AclUsageFilter aclUsageFilter = Whitebox.getInternalState(window, USAGE_FILTER);
        assertTrue(aclUsageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), aclUsageFilter);
    }

    @Test
    public void testClearButtonClickListener() {
        AclUsageFilter usageFilter = buildExpectedFilter();
        Whitebox.setInternalState(window, USAGE_FILTER, usageFilter);
        assertFalse(usageFilter.isEmpty());
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertTrue(usageFilter.isEmpty());
    }

    @Test
    public void testUsageDetailIdFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(4);
    }

    @Test
    public void testWrWrkInstFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(5);
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(6);
    }

    @Test
    public void testSurveyCountryFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(7);
    }

    @Test
    public void testContentUnitPriceFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(8);
    }

    @Test
    public void testAnnualizedCopiesFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(9);
    }

    @Test
    public void testUsageDetailIdValidation() {
        TextField usageDetailIdField = Whitebox.getInternalState(window, "usageDetailIdField");
        ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox =
            Whitebox.getInternalState(window, "usageDetailIdOperatorComboBox");
        assertTextOperatorComboBoxItems(usageDetailIdOperatorComboBox);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(50), binder, null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(51), binder,
            "Field value should not exceed 50 characters", false);
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
        ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox =
            Whitebox.getInternalState(window, "systemTitleOperatorComboBox");
        assertTextOperatorComboBoxItems(systemTitleOperatorComboBox);
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2000), binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2001), binder,
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testSurveyCountryValidation() {
        TextField surveyCountryField = Whitebox.getInternalState(window, "surveyCountryField");
        ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox =
            Whitebox.getInternalState(window, "surveyCountryOperatorComboBox");
        assertTextOperatorComboBoxItems(surveyCountryOperatorComboBox);
        validateFieldAndVerifyErrorMessage(surveyCountryField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(100), binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(101), binder,
            "Field value should not exceed 100 characters", false);
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        TextField annualizedCopiesFromField = Whitebox.getInternalState(window, "annualizedCopiesFromField");
        TextField annualizedCopiesToField = Whitebox.getInternalState(window, "annualizedCopiesToField");
        ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
            Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox");
        assertNumericOperatorComboBoxItems(annualizedCopiesOperatorComboBox);
        verifyAmountValidationZeroNotAllowed(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox, "Field value should be greater or equal to Annualized Copies From");
    }

    @Test
    public void testContentUnitPriceValidation() {
        TextField contentUnitPriceFromField = Whitebox.getInternalState(window, "contentUnitPriceFromField");
        TextField contentUnitPriceToField = Whitebox.getInternalState(window, "contentUnitPriceToField");
        ComboBox<FilterOperatorEnum> contentUnitPriceComboBox =
            Whitebox.getInternalState(window, "contentUnitPriceOperatorComboBox");
        assertNumericOperatorComboBoxItems(contentUnitPriceComboBox);
        verifyAmountValidationZeroAllowed(contentUnitPriceFromField, contentUnitPriceToField,
            contentUnitPriceComboBox, "Field value should be greater or equal to Content Unit Price From");
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
        assertEquals(10, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Periods", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Aggregate Licensee Classes", "Pub Types");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Types of Use");
        verifyComboBoxLayout(verticalLayout.getComponent(3), "Usage Origin", Arrays.asList(UdmUsageOriginEnum.values()),
            "Channel", Arrays.asList(UdmChannelEnum.values()));
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(4), "Usage Detail ID");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(5), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(6), "System Title");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(7), "Survey Country");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(8), "Content Unit Price From",
            "Content Unit Price To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(9), "Annualized Copies From",
            "Annualized Copies To");
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyComboBoxLayout(Component component, String firstCaption, List<?> firstItemList,
                                      String secondCaption, List<?> secondItemList) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        verifyComboBox(layout.getComponent(0), firstCaption, true, firstItemList);
        verifyComboBox(layout.getComponent(1), secondCaption, true, secondItemList);
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

    private void verifyFieldWithTextOperatorComponent(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof ComboBox);
        assertEquals(CAPTION_OPERATOR, layout.getComponent(1).getCaption());
    }

    private AclUsageFilter buildExpectedFilter() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(LC_ID);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setId(LC_ID);
        aggregateLicenseeClass.setDiscipline(LC_DESCRIPTION);
        PublicationType publicationType = new PublicationType();
        publicationType.setName("BK");
        publicationType.setDescription("Book");
        AclUsageFilter filter = Whitebox.getInternalState(window, USAGE_FILTER);
        filter.setPeriods(Collections.singleton(202206));
        filter.setDetailLicenseeClasses(Collections.singleton(detailLicenseeClass));
        filter.setAggregateLicenseeClasses(Collections.singleton(aggregateLicenseeClass));
        filter.setPubTypes(Collections.singleton(publicationType));
        filter.setTypeOfUses(Collections.singleton(PRINT_TYPE_OF_USE));
        filter.setUsageOrigin(RFA_USAGE_ORIGIN);
        filter.setChannel(CCC_CHANNEL);
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setContentUnitPriceExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, CONTENT_UNIT_PRICE, null));
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        return filter;
    }

    @SuppressWarnings(UNCHECKED)
    private void testNumericFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = getFiltersLayout();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
        TextField fromField = (TextField) horizontalLayout.getComponent(0);
        TextField toField = (TextField) horizontalLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(2);
        assertEquals(7, ((ListDataProvider<?>) operatorComboBox.getDataProvider()).getItems().size());
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
    }

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = getFiltersLayout();
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
    }

    private VerticalLayout getFiltersLayout() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        return (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
    }

    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("periodFilterWidget", "(2)");
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("aggregateLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("pubTypeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(2)");
        assertComboBoxValue("usageOriginComboBox", RFA_USAGE_ORIGIN);
        assertComboBoxValue("channelComboBox", CCC_CHANNEL);
        assertTextFieldValue("usageDetailIdField", USAGE_DETAIL_ID);
        assertComboBoxValue("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("wrWrkInstFromField", WR_WRK_INST.toString());
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("surveyCountryField", SURVEY_COUNTRY);
        assertComboBoxValue("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("contentUnitPriceFromField", CONTENT_UNIT_PRICE.toString());
        assertComboBoxValue("contentUnitPriceOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("annualizedCopiesFromField", "5.5");
        assertComboBoxValue("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextFieldValue(String fieldName, String value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void populateData() {
        AclUsageFilter aclUsageFilter = Whitebox.getInternalState(window, USAGE_FILTER);
        aclUsageFilter.setTypeOfUses(Collections.singleton(PRINT_TYPE_OF_USE));
        populateComboBox("usageOriginComboBox", RFA_USAGE_ORIGIN);
        populateComboBox("channelComboBox", CCC_CHANNEL);
        populateTextField("usageDetailIdField", USAGE_DETAIL_ID);
        populateComboBox("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("wrWrkInstFromField", WR_WRK_INST.toString());
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("surveyCountryField", SURVEY_COUNTRY);
        populateComboBox("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("contentUnitPriceFromField", CONTENT_UNIT_PRICE.toString());
        populateComboBox("contentUnitPriceOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("annualizedCopiesFromField", "5.5");
        populateComboBox("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.CONTAINS));
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL, FilterOperatorEnum.GREATER_THAN,
                FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, FilterOperatorEnum.LESS_THAN,
                FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN));
    }

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                   String fieldSpecificErrorMessage, int length) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, "12345679", binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "12345678", binder, fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, NUMBER_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(fromField, buildStringWithExpectedLength(length + 1), binder,
            String.format("Field value should not exceed %d digits", length), false);
        validateFieldAndVerifyErrorMessage(toField, buildStringWithExpectedLength(length + 1), binder,
            String.format("Field value should not exceed %d digits", length), false);
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

    private void verifyAmountValidationZeroNotAllowed(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox,
                                                      String fieldSpecificErrorMessage) {
        String numberValidationMessage = "Field value should be positive number and should not exceed 10 digits";
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, numberValidationMessage);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", binder, fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, numberValidationMessage, false);
    }

    private void verifyAmountValidationZeroAllowed(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox,
                                                   String fieldSpecificErrorMessage) {
        String numberValidationMessage =
            "Field value should be positive number or zero and should not exceed 10 digits";
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, numberValidationMessage);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, SPACES_STRING, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, binder, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", binder, fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, binder, numberValidationMessage, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, binder, numberValidationMessage, false);
    }

    private String buildStringWithExpectedLength(int length) {
        return StringUtils.repeat('1', length);
    }
}
