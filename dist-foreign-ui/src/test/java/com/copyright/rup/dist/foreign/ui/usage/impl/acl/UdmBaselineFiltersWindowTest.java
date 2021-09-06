package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.ValidationResult;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(272, window.getHeight(), 0);
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
    @SuppressWarnings(UNCHECKED)
    public void testFilterOperatorChangeListener() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout annualizedCopiesLayout = (HorizontalLayout) verticalLayout.getComponent(4);
        TextField annualizedCopiesFromField = (TextField) annualizedCopiesLayout.getComponent(0);
        TextField annualizedCopiesToField = (TextField) annualizedCopiesLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> annualizedCopiesFilterOperatorComboBox =
            (ComboBox<FilterOperatorEnum>) annualizedCopiesLayout.getComponent(2);
        assertEquals(FilterOperatorEnum.EQUALS, annualizedCopiesFilterOperatorComboBox.getValue());
        assertTrue(annualizedCopiesFromField.isEnabled());
        assertFalse(annualizedCopiesToField.isEnabled());
        annualizedCopiesFilterOperatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(annualizedCopiesFromField.isEnabled());
        assertFalse(annualizedCopiesToField.isEnabled());
        annualizedCopiesFilterOperatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(annualizedCopiesFromField.isEnabled());
        assertFalse(annualizedCopiesToField.isEnabled());
        annualizedCopiesFilterOperatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(annualizedCopiesFromField.isEnabled());
        assertTrue(annualizedCopiesToField.isEnabled());
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmBaselineFilter appliedBaselineFilter = window.getAppliedBaselineFilter();
        assertTrue(appliedBaselineFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(5);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedBaselineFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(5);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedBaselineFilter().isEmpty());
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        TextField annualizedCopiesFromField = Whitebox.getInternalState(window, "annualizedCopiesFrom");
        TextField annualizedCopiesToField = Whitebox.getInternalState(window, "annualizedCopiesTo");
        ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
            Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox");
        verifyBigDecimalOperationValidations(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox);
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInst");
        verifyTextFieldValidationMessage(wrWrkInstField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(wrWrkInstField, "1234567890",
            "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(wrWrkInstField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(wrWrkInstField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testUsageDetailIdValidation() {
        TextField surveyCountryField = Whitebox.getInternalState(window, "usageDetailId");
        verifyTextFieldValidationMessage(surveyCountryField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(surveyCountryField, buildStringWithExpectedLength(50),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(surveyCountryField, buildStringWithExpectedLength(51),
            "Field value should not exceed 50 characters", false);
    }

    @Test
    public void testSystemTitleValidation() {
        TextField languageField = Whitebox.getInternalState(window, "systemTitle");
        verifyTextFieldValidationMessage(languageField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(languageField, buildStringWithExpectedLength(2000),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(languageField, buildStringWithExpectedLength(2001),
            "Field value should not exceed 2000 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(6, verticalLayout.getComponentCount());
        verifyFilterLayout(verticalLayout.getComponent(0), "Detail Licensee Classes", "Aggregate Licensee Classes");
        verifyFilterWidget(verticalLayout.getComponent(1), "Types of Use");
        verifyTextFieldLayout(verticalLayout.getComponent(2), "Wr Wrk Inst", "System Title");
        verifyTextFieldLayout(verticalLayout.getComponent(3), "Usage Detail ID", "Survey Country");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(4), "Annualized Copies From",
            "Annualized Copies To");
        verifyButtonsLayout(verticalLayout.getComponent(5));
    }

    private void verifyFilterLayout(Component component, String... captions) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(captions.length, layout.getComponentCount());
        IntStream.range(0, captions.length)
            .forEach(index -> verifyFilterWidget(layout.getComponent(index), captions[index]));
    }

    private void verifyFilterWidget(Component component, String caption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertTrue(layout.isSpacing());
        Iterator<Component> iterator = layout.iterator();
        assertEquals("(0)", ((Label) iterator.next()).getValue());
        Button button = (Button) iterator.next();
        assertEquals(caption, button.getCaption());
        assertEquals(2, button.getListeners(ClickEvent.class).size());
        assertTrue(button.isDisableOnClick());
        assertTrue(StringUtils.contains(button.getStyleName(), Cornerstone.BUTTON_LINK));
        assertFalse(iterator.hasNext());
    }

    private void verifyFieldWithOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof TextField);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof TextField);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertTrue(layout.getComponent(2) instanceof ComboBox);
        assertEquals("Operator", layout.getComponent(2).getCaption());
    }

    private void verifyTextFieldLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyTextFieldComponent(layout.getComponent(0), firstCaption);
        verifyTextFieldComponent(layout.getComponent(1), secondCaption);
    }

    private void verifyTextFieldComponent(Component component, String caption) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertTrue(component.isEnabled());
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

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox);
        verifyTextFieldValidationMessage(fromField, VALID_DECIMAL, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(toField, VALID_DECIMAL, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
    }

    private void verifyCommonOperationValidations(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyTextFieldValidationMessage(fromField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, SPACES_STRING, DECIMAL_VALIDATION_MESSAGE, false);
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        verifyTextFieldValidationMessage(fromField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        verifyTextFieldValidationMessage(fromField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(toField, VALID_INTEGER, StringUtils.EMPTY, true);
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
        filter.setWrWrkInst(243904752L);
        filter.setSystemTitle(SYSTEM_TITLE);
        filter.setUsageDetailId(USAGE_DETAIL_ID);
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        return filter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("aggregateLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(1)");
        assertTextFieldValue("wrWrkInst", "243904752");
        assertTextFieldValue("systemTitle", SYSTEM_TITLE);
        assertTextFieldValue("usageDetailId", USAGE_DETAIL_ID);
        assertTextFieldValue("surveyCountry", SURVEY_COUNTRY);
        assertTextFieldValue("annualizedCopiesFrom", "5.5");
        assertEquals(FilterOperatorEnum.EQUALS,
            ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox"))
                .getValue());
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertTextFieldValue(String fieldName, String value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
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
        populateTextField("wrWrkInst", "243904752");
        populateTextField("systemTitle", SYSTEM_TITLE);
        populateTextField("usageDetailId", USAGE_DETAIL_ID);
        populateTextField("surveyCountry", SURVEY_COUNTRY);
        populateTextField("annualizedCopiesFrom", "5.5");
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox"))
            .setValue(FilterOperatorEnum.EQUALS);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
        BinderValidationStatus<UdmBaselineFilter> binderStatus = binder.validate();
        assertEquals(isValid, binderStatus.isOk());
        if (!isValid) {
            List<ValidationResult> errors = binderStatus.getValidationErrors();
            List<String> errorMessages =
                errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
            assertTrue(errorMessages.contains(message));
        }
    }

    private String buildStringWithExpectedLength(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append('a');
        }
        return result.toString();
    }
}