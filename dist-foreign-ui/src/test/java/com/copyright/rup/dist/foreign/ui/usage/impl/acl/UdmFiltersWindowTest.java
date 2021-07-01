package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Sets;
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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class UdmFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final LocalDate DATE_FROM = LocalDate.of(2021, 1, 1);
    private static final LocalDate DATE_TO = LocalDate.of(2021, 1, 2);
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final String SURVEY_COUNTRY = "United States";
    private static final String LANGUAGE = "English";
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
    private UdmFiltersWindow window;
    private Binder<UdmUsageFilter> binder;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        replay(ForeignSecurityUtils.class);
        window = new UdmFiltersWindow(createMock(IUdmUsageFilterController.class), new UdmUsageFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM additional filters", window.getCaption());
        assertEquals(550, window.getWidth(), 0);
        assertEquals(Unit.PIXELS, window.getWidthUnits());
        assertEquals(560, window.getHeight(), 0);
        assertEquals(Unit.PIXELS, window.getHeightUnits());
        verifyRootLayout(window.getContent());
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmUsageFilter usageFilter = buildExpectedFilter();
        usageFilter.setAssignees(Collections.singleton("user@copyright.com"));
        usageFilter.setPubFormats(Sets.newHashSet("Digital", "Not Specified"));
        usageFilter.setReportedPubTypes(Sets.newHashSet("Book", "Not Shared"));
        usageFilter.setReportedTypeOfUses(Collections.singleton("COPY_FOR_MYSELF"));
        DetailLicenseeClass licenseeClass = new DetailLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        usageFilter.setDetailLicenseeClasses(Collections.singleton(licenseeClass));
        replay(ForeignSecurityUtils.class);
        window = new UdmFiltersWindow(createMock(IUdmUsageFilterController.class), usageFilter);
        verifyFiltersData();
        verify(ForeignSecurityUtils.class);
    }

    @Test
    @SuppressWarnings(UNCHECKED)
    public void testFilterOperatorChangeListener() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout annualMultiplierLayout = (HorizontalLayout) verticalLayout.getComponent(8);
        TextField fromField = (TextField) annualMultiplierLayout.getComponent(0);
        TextField toField = (TextField) annualMultiplierLayout.getComponent(1);
        ComboBox<FilterOperatorEnum> filterOperatorComboBox =
            (ComboBox<FilterOperatorEnum>) annualMultiplierLayout.getComponent(2);
        assertEquals(FilterOperatorEnum.EQUALS, filterOperatorComboBox.getValue());
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.GREATER_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.LESS_THAN);
        assertTrue(fromField.isEnabled());
        assertFalse(toField.isEnabled());
        filterOperatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        assertTrue(fromField.isEnabled());
        assertTrue(toField.isEnabled());
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmUsageFilter appliedUsageFilter = window.getAppliedUsageFilter();
        assertTrue(appliedUsageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(12);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedUsageFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(12);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedUsageFilter().isEmpty());
    }

    @Test
    public void testFilterPermissions() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class);
        window = new UdmFiltersWindow(createMock(IUdmUsageFilterController.class), new UdmUsageFilter());
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        assertTrue(verticalLayout.getComponent(0).isEnabled());
        assertTrue(verticalLayout.getComponent(1).isEnabled());
        assertTrue(verticalLayout.getComponent(2).isEnabled());
        assertTrue(verticalLayout.getComponent(3).isEnabled());
        assertTrue(verticalLayout.getComponent(4).isEnabled());
        assertTrue(verticalLayout.getComponent(5).isEnabled());
        assertFalse(verticalLayout.getComponent(6).isEnabled());
        HorizontalLayout layout = (HorizontalLayout) verticalLayout.getComponent(7);
        verifyTextFieldComponent(layout.getComponent(0), "Survey Country", false);
        assertFalse(verticalLayout.getComponent(8).isEnabled());
        assertFalse(verticalLayout.getComponent(9).isEnabled());
        assertFalse(verticalLayout.getComponent(10).isEnabled());
        assertFalse(verticalLayout.getComponent(11).isEnabled());
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testUsageDateValidation() {
        LocalDateWidget usageDateFromWidget = Whitebox.getInternalState(window, "usageDateFromWidget");
        LocalDateWidget usageDateToWidget = Whitebox.getInternalState(window, "usageDateToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 1, 1);
        LocalDate localDateTo = LocalDate.of(2022, 1, 1);
        verifyDateWidgetValidationMessage(usageDateFromWidget, localDateFrom, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(usageDateToWidget, localDateTo, StringUtils.EMPTY, true);
        usageDateFromWidget.setValue(LocalDate.of(2023, 1, 1));
        verifyDateWidgetValidationMessage(usageDateToWidget, localDateTo,
            "Field value should be greater or equal to Usage Date From", false);
        usageDateFromWidget.setValue(null);
        verifyDateWidgetValidationMessage(usageDateToWidget, localDateTo, StringUtils.EMPTY, true);
    }

    @Test
    public void testSurveyStartDateValidation() {
        LocalDateWidget surveyStartDateFromWidget = Whitebox.getInternalState(window, "surveyStartDateFromWidget");
        LocalDateWidget surveyStartDateToWidget = Whitebox.getInternalState(window, "surveyStartDateToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 1, 1);
        LocalDate localDateTo = LocalDate.of(2022, 1, 1);
        verifyDateWidgetValidationMessage(surveyStartDateFromWidget, null, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(surveyStartDateToWidget, null, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(surveyStartDateFromWidget, localDateFrom, StringUtils.EMPTY, true);
        verifyDateWidgetValidationMessage(surveyStartDateToWidget, localDateTo, StringUtils.EMPTY, true);
        surveyStartDateFromWidget.setValue(LocalDate.of(2023, 1, 1));
        verifyDateWidgetValidationMessage(surveyStartDateToWidget, localDateTo,
            "Field value should be greater or equal to Survey Start Date From", false);
        surveyStartDateFromWidget.setValue(null);
        verifyDateWidgetValidationMessage(surveyStartDateToWidget, localDateTo, StringUtils.EMPTY, true);
    }

    @Test
    public void testAnnualMultiplierValidation() {
        TextField annualMultiplierFromField = Whitebox.getInternalState(window, "annualMultiplierFromField");
        TextField annualMultiplierToField = Whitebox.getInternalState(window, "annualMultiplierToField");
        ComboBox<FilterOperatorEnum> annualMultiplierOperatorComboBox =
            Whitebox.getInternalState(window, "annualMultiplierOperatorComboBox");
        verifyIntegerOperationValidations(annualMultiplierFromField, annualMultiplierToField,
            annualMultiplierOperatorComboBox);
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        TextField annualizedCopiesFromField = Whitebox.getInternalState(window, "annualizedCopiesFromField");
        TextField annualizedCopiesToField = Whitebox.getInternalState(window, "annualizedCopiesToField");
        ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
            Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox");
        verifyBigDecimalOperationValidations(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox);
    }

    @Test
    public void testStatisticalMultiplierValidation() {
        TextField statisticalMultiplierFromField = Whitebox.getInternalState(window, "statisticalMultiplierFromField");
        TextField statisticalMultiplierToField = Whitebox.getInternalState(window, "statisticalMultiplierToField");
        ComboBox<FilterOperatorEnum> statisticalMultiplierOperatorComboBox =
            Whitebox.getInternalState(window, "statisticalMultiplierOperatorComboBox");
        verifyBigDecimalOperationValidations(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox);
    }

    @Test
    public void testQuantityValidation() {
        TextField quantityFromField = Whitebox.getInternalState(window, "quantityFromField");
        TextField quantityToField = Whitebox.getInternalState(window, "quantityToField");
        ComboBox<FilterOperatorEnum> quantityOperatorComboBox =
            Whitebox.getInternalState(window, "quantityOperatorComboBox");
        verifyIntegerOperationValidations(quantityFromField, quantityToField, quantityOperatorComboBox);
    }

    @Test
    public void testWrWrkInstValidation() {
        TextField wrWrkInstField = Whitebox.getInternalState(window, "wrWrkInstField");
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
    public void testCompanyIdValidation() {
        TextField companyIdField = Whitebox.getInternalState(window, "companyIdField");
        verifyTextFieldValidationMessage(companyIdField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(companyIdField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(companyIdField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(companyIdField, "12345678901",
            "Field value should not exceed 10 digits", false);
        verifyTextFieldValidationMessage(companyIdField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(companyIdField, SPACES_STRING, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(companyIdField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
    }

    @Test
    public void testCompanyNameValidation() {
        TextField companyNameField = Whitebox.getInternalState(window, "companyNameField");
        verifyTextFieldValidationMessage(companyNameField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(companyNameField, buildStringWithExpectedLength(200),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(companyNameField, buildStringWithExpectedLength(201),
            "Field value should not exceed 200 characters", false);
    }

    @Test
    public void testSurveyCountryValidation() {
        TextField surveyCountryField = Whitebox.getInternalState(window, "surveyCountryField");
        verifyTextFieldValidationMessage(surveyCountryField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(surveyCountryField, buildStringWithExpectedLength(100),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(surveyCountryField, buildStringWithExpectedLength(101),
            "Field value should not exceed 100 characters", false);
    }

    @Test
    public void testLanguageValidation() {
        TextField languageField = Whitebox.getInternalState(window, "languageField");
        verifyTextFieldValidationMessage(languageField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(languageField, buildStringWithExpectedLength(255),
            StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(languageField, buildStringWithExpectedLength(256),
            "Field value should not exceed 255 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(13, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Reported Pub Types", "Types of Use");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Publication Formats");
        verifyDateFieldComponent(verticalLayout.getComponent(3), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(4), "Survey Start Date From", "Survey Start Date To");
        verifyChannelWrWkrInstLayout(verticalLayout.getComponent(5));
        verifyTextFieldLayout(verticalLayout.getComponent(6), "Company ID", "Company Name");
        verifyTextFieldLayout(verticalLayout.getComponent(7), "Survey Country", "Language");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(8), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(9), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(10), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithOperatorComponent(verticalLayout.getComponent(11), "Quantity From", "Quantity To");
        verifyButtonsLayout(verticalLayout.getComponent(12));
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyItemsFilterWidget(Component component, String caption) {
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

    private void verifyDateFieldComponent(Component component, String captionFrom, String captionTo) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertEquals(2, layout.getComponentCount());
        assertTrue(layout.getComponent(0) instanceof LocalDateWidget);
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertTrue(layout.getComponent(1) instanceof LocalDateWidget);
        assertEquals(captionTo, layout.getComponent(1).getCaption());
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyChannelWrWkrInstLayout(Component component) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertEquals(2, layout.getComponentCount());
        ComboBox<UdmChannelEnum> channelComboBox = (ComboBox<UdmChannelEnum>) layout.getComponent(0);
        assertEquals(100, channelComboBox.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, channelComboBox.getWidthUnits());
        assertEquals(channelComboBox.getCaption(), "Channel");
        verifyTextFieldComponent(layout.getComponent(1), "Wr Wrk Inst", true);
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
        verifyTextFieldComponent(layout.getComponent(0), firstCaption, true);
        verifyTextFieldComponent(layout.getComponent(1), secondCaption, true);
    }

    private void verifyTextFieldComponent(Component component, String caption, boolean isEnabled) {
        assertTrue(component instanceof TextField);
        assertEquals(100, component.getWidth(), 0);
        assertEquals(Unit.PERCENTAGE, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
        assertEquals(isEnabled, component.isEnabled());
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

    private void verifyIntegerOperationValidations(TextField fromField, TextField toField,
                                                   ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, NUMBER_VALIDATION_MESSAGE);
        verifyTextFieldValidationMessage(fromField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, VALID_DECIMAL, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(fromField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, INVALID_NUMBER, NUMBER_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(fromField, "1234567890", "Field value should not exceed 9 digits", false);
        verifyTextFieldValidationMessage(toField, "1234567890", "Field value should not exceed 9 digits", false);
    }

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, DECIMAL_VALIDATION_MESSAGE);
        verifyTextFieldValidationMessage(fromField, VALID_DECIMAL, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(toField, VALID_DECIMAL, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
    }

    private void verifyCommonOperationValidations(TextField fromField, TextField toField,
                                                  ComboBox<FilterOperatorEnum> operatorComboBox,
                                                  String numberValidationMessage) {
        verifyTextFieldValidationMessage(fromField, StringUtils.EMPTY, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, INTEGER_WITH_SPACES_STRING, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(fromField, SPACES_STRING, numberValidationMessage, false);
        operatorComboBox.setValue(FilterOperatorEnum.BETWEEN);
        verifyTextFieldValidationMessage(fromField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        verifyTextFieldValidationMessage(toField, StringUtils.EMPTY, BETWEEN_OPERATOR_VALIDATION_MESSAGE, false);
        operatorComboBox.setValue(FilterOperatorEnum.EQUALS);
        verifyTextFieldValidationMessage(fromField, VALID_INTEGER, StringUtils.EMPTY, true);
        verifyTextFieldValidationMessage(toField, VALID_INTEGER, StringUtils.EMPTY, true);
    }

    private UdmUsageFilter buildExpectedFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUsageDateFrom(DATE_FROM);
        filter.setUsageDateTo(DATE_TO);
        filter.setSurveyStartDateFrom(DATE_FROM);
        filter.setSurveyStartDateTo(DATE_TO);
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInst(243904752L);
        filter.setCompanyId(454984566L);
        filter.setCompanyName(COMPANY_NAME);
        filter.setSurveyCountry(SURVEY_COUNTRY);
        filter.setLanguage(LANGUAGE);
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1, 10));
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, new BigDecimal("2.2"), null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 3, null));
        return filter;
    }

    @SuppressWarnings(UNCHECKED)
    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("assigneeFilterWidget", "(1)");
        assertFilterWidgetLabelValue("reportedPubTypeFilterWidget", "(2)");
        assertFilterWidgetLabelValue("publicationFormatFilterWidget", "(2)");
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", "(1)");
        assertFilterWidgetLabelValue("typeOfUseFilterWidget", "(1)");
        assertLocalDateFieldValue("usageDateFromWidget", DATE_FROM);
        assertLocalDateFieldValue("usageDateToWidget", DATE_TO);
        assertLocalDateFieldValue("surveyStartDateFromWidget", DATE_FROM);
        assertLocalDateFieldValue("surveyStartDateToWidget", DATE_TO);
        assertEquals(UdmChannelEnum.CCC,
            ((ComboBox<UdmChannelEnum>) Whitebox.getInternalState(window, "channelComboBox")).getValue());
        assertTextFieldValue("wrWrkInstField", "243904752");
        assertTextFieldValue("companyIdField", "454984566");
        assertTextFieldValue("companyNameField", COMPANY_NAME);
        assertTextFieldValue("surveyCountryField", SURVEY_COUNTRY);
        assertTextFieldValue("languageField", LANGUAGE);
        assertTextFieldValue("annualMultiplierFromField", "1");
        assertTextFieldValue("annualMultiplierToField", "10");
        assertComboBoxFieldValue("annualMultiplierOperatorComboBox", FilterOperatorEnum.BETWEEN);
        assertTextFieldValue("annualizedCopiesFromField", "5.5");
        assertComboBoxFieldValue("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("statisticalMultiplierFromField", "2.2");
        assertComboBoxFieldValue("statisticalMultiplierOperatorComboBox", FilterOperatorEnum.GREATER_THAN);
        assertTextFieldValue("quantityFromField", "3");
        assertComboBoxFieldValue("quantityOperatorComboBox", FilterOperatorEnum.LESS_THAN);
    }

    private void assertFilterWidgetLabelValue(String filterName, String value) {
        BaseUdmItemsFilterWidget filterWidget = Whitebox.getInternalState(window, filterName);
        assertEquals(value, ((Label) filterWidget.getComponent(0)).getValue());
    }

    private void assertLocalDateFieldValue(String fieldName, LocalDate value) {
        assertEquals(value, ((LocalDateWidget) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextFieldValue(String fieldName, String value) {
        assertEquals(value, ((TextField) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private void assertComboBoxFieldValue(String fieldName, FilterOperatorEnum value) {
        assertEquals(value, ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateLocalDateWidget("usageDateFromWidget", DATE_FROM);
        populateLocalDateWidget("usageDateToWidget", DATE_TO);
        populateLocalDateWidget("surveyStartDateFromWidget", DATE_FROM);
        populateLocalDateWidget("surveyStartDateToWidget", DATE_TO);
        ((ComboBox<UdmChannelEnum>) Whitebox.getInternalState(window, "channelComboBox")).setValue(UdmChannelEnum.CCC);
        populateTextField("wrWrkInstField", "243904752");
        populateTextField("companyIdField", "454984566");
        populateTextField("companyNameField", COMPANY_NAME);
        populateTextField("surveyCountryField", SURVEY_COUNTRY);
        populateTextField("languageField", LANGUAGE);
        populateTextField("annualMultiplierFromField", "1");
        populateTextField("annualMultiplierToField", "10");
        populateComboBoxOperatorField("annualMultiplierOperatorComboBox", FilterOperatorEnum.BETWEEN);
        populateTextField("annualizedCopiesFromField", "5.5");
        populateComboBoxOperatorField("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("statisticalMultiplierFromField", "2.2");
        populateComboBoxOperatorField("statisticalMultiplierOperatorComboBox", FilterOperatorEnum.GREATER_THAN);
        populateTextField("quantityFromField", "3");
        populateComboBoxOperatorField("quantityOperatorComboBox", FilterOperatorEnum.LESS_THAN);
    }

    private void populateLocalDateWidget(String fieldName, LocalDate value) {
        ((LocalDateWidget) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private void populateComboBoxOperatorField(String fieldName, FilterOperatorEnum value) {
        ((ComboBox<FilterOperatorEnum>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void verifyDateWidgetValidationMessage(LocalDateWidget localDateWidget, LocalDate value, String message,
                                                   boolean isValid) {
        localDateWidget.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
    }

    private void verifyTextFieldValidationMessage(TextField field, String value, String message, boolean isValid) {
        field.setValue(value);
        BinderValidationStatus<UdmUsageFilter> binderStatus = binder.validate();
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
