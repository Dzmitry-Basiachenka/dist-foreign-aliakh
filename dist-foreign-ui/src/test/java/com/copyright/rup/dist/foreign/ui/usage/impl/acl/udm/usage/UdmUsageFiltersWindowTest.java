package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.collect.Sets;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.provider.ListDataProvider;
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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Verifies {@link UdmUsageFiltersWindow}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ForeignSecurityUtils.class})
public class UdmUsageFiltersWindowTest {

    private static final String UNCHECKED = "unchecked";
    private static final String CAPTION_OPERATOR = "Operator";
    private static final LocalDate DATE_FROM = LocalDate.of(2021, 1, 1);
    private static final LocalDate DATE_TO = LocalDate.of(2021, 1, 2);
    private static final Integer WR_WRK_INST = 243904752;
    private static final Integer COMPANY_ID = 454984566;
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
    private UdmUsageFiltersWindow window;
    private Binder<UdmUsageFilter> binder;

    @Before
    public void setUp() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andStubReturn(false);
        replay(ForeignSecurityUtils.class);
        window = new UdmUsageFiltersWindow(createMock(IUdmUsageFilterController.class), new UdmUsageFilter());
        binder = Whitebox.getInternalState(window, "filterBinder");
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testConstructor() {
        assertEquals("UDM usages additional filters", window.getCaption());
        assertEquals(750, window.getWidth(), 0);
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
        window = new UdmUsageFiltersWindow(createMock(IUdmUsageFilterController.class), usageFilter);
        verifyFiltersData();
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testCompanyIdFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(7);
    }

    @Test
    public void testCompanyNameFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(8);
    }

    @Test
    public void testAnnualMultiplierFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(11);
    }

    @Test
    public void testAnnualizedCopiesFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(12);
    }

    @Test
    public void testStatisticalMultiplierMultiplierFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(13);
    }

    @Test
    public void testQuantityFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(14);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmUsageFilter appliedUsageFilter = window.getAppliedUsageFilter();
        assertTrue(appliedUsageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(15);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), window.getAppliedUsageFilter());
    }

    @Test
    public void testClearButtonClickListener() {
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(15);
        Button clearButton = (Button) buttonsLayout.getComponent(1);
        clearButton.click();
        assertTrue(window.getAppliedUsageFilter().isEmpty());
    }

    @Test
    public void testFilterPermissions() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class);
        window = new UdmUsageFiltersWindow(createMock(IUdmUsageFilterController.class), new UdmUsageFilter());
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        assertTrue(verticalLayout.getComponent(0).isEnabled());
        assertTrue(verticalLayout.getComponent(1).isEnabled());
        assertTrue(verticalLayout.getComponent(2).isEnabled());
        assertTrue(verticalLayout.getComponent(3).isEnabled());
        assertTrue(verticalLayout.getComponent(4).isEnabled());
        assertTrue(verticalLayout.getComponent(5).isEnabled());
        assertTrue(verticalLayout.getComponent(6).isEnabled());
        assertFalse(verticalLayout.getComponent(7).isEnabled());
        assertFalse(verticalLayout.getComponent(8).isEnabled());
        assertFalse(verticalLayout.getComponent(9).isEnabled());
        assertTrue(verticalLayout.getComponent(10).isEnabled());
        assertFalse(verticalLayout.getComponent(11).isEnabled());
        assertFalse(verticalLayout.getComponent(12).isEnabled());
        assertFalse(verticalLayout.getComponent(13).isEnabled());
        assertFalse(verticalLayout.getComponent(14).isEnabled());
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
        assertNumericOperatorComboBoxItems(annualMultiplierOperatorComboBox);
        verifyIntegerOperationValidations(annualMultiplierFromField, annualMultiplierToField,
            annualMultiplierOperatorComboBox, "Field value should be greater or equal to Annual Multiplier From", 9);
    }

    @Test
    public void testAnnualizedCopiesValidation() {
        TextField annualizedCopiesFromField = Whitebox.getInternalState(window, "annualizedCopiesFromField");
        TextField annualizedCopiesToField = Whitebox.getInternalState(window, "annualizedCopiesToField");
        ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
            Whitebox.getInternalState(window, "annualizedCopiesOperatorComboBox");
        assertNumericOperatorComboBoxItems(annualizedCopiesOperatorComboBox);
        verifyBigDecimalOperationValidations(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox, "Field value should be greater or equal to Annualized Copies From");
    }

    @Test
    public void testStatisticalMultiplierValidation() {
        TextField statisticalMultiplierFromField = Whitebox.getInternalState(window, "statisticalMultiplierFromField");
        TextField statisticalMultiplierToField = Whitebox.getInternalState(window, "statisticalMultiplierToField");
        ComboBox<FilterOperatorEnum> statisticalMultiplierOperatorComboBox =
            Whitebox.getInternalState(window, "statisticalMultiplierOperatorComboBox");
        assertNumericOperatorComboBoxItems(statisticalMultiplierOperatorComboBox);
        verifyBigDecimalOperationValidations(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox,
            "Field value should be greater or equal to Statistical Multiplier From");
    }

    @Test
    public void testQuantityValidation() {
        TextField quantityFromField = Whitebox.getInternalState(window, "quantityFromField");
        TextField quantityToField = Whitebox.getInternalState(window, "quantityToField");
        ComboBox<FilterOperatorEnum> quantityOperatorComboBox =
            Whitebox.getInternalState(window, "quantityOperatorComboBox");
        assertNumericOperatorComboBoxItems(quantityOperatorComboBox);
        verifyIntegerOperationValidations(quantityFromField, quantityToField, quantityOperatorComboBox,
            "Field value should be greater or equal to Quantity From", 9);
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
    public void testCompanyIdValidation() {
        TextField companyIdFromField = Whitebox.getInternalState(window, "companyIdFromField");
        TextField companyIdToField = Whitebox.getInternalState(window, "companyIdToField");
        ComboBox<FilterOperatorEnum> companyIdOperatorComboBox =
            Whitebox.getInternalState(window, "companyIdOperatorComboBox");
        assertNumericOperatorComboBoxItems(companyIdOperatorComboBox);
        verifyIntegerOperationValidations(companyIdFromField, companyIdToField, companyIdOperatorComboBox,
            "Field value should be greater or equal to Company ID From", 10);
    }

    @Test
    public void testCompanyNameValidation() {
        TextField companyNameField = Whitebox.getInternalState(window, "companyNameField");
        ComboBox<FilterOperatorEnum> companyNameOperatorComboBox =
            Whitebox.getInternalState(window, "companyNameOperatorComboBox");
        assertTextOperatorComboBoxItems(companyNameOperatorComboBox);
        validateFieldAndVerifyErrorMessage(companyNameField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(companyNameField, buildStringWithExpectedLength(200), null, true);
        validateFieldAndVerifyErrorMessage(companyNameField, buildStringWithExpectedLength(201),
            "Field value should not exceed 200 characters", false);
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
    public void testLanguageValidation() {
        TextField languageField = Whitebox.getInternalState(window, "languageField");
        ComboBox<FilterOperatorEnum> languageOperatorComboBox =
            Whitebox.getInternalState(window, "languageOperatorComboBox");
        assertTextOperatorComboBoxItems(languageOperatorComboBox);
        validateFieldAndVerifyErrorMessage(languageField, StringUtils.EMPTY, null, true);
        validateFieldAndVerifyErrorMessage(languageField, buildStringWithExpectedLength(255), null, true);
        validateFieldAndVerifyErrorMessage(languageField, buildStringWithExpectedLength(256),
            "Field value should not exceed 255 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertTrue(component instanceof VerticalLayout);
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(16, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Reported Pub Types", "Types of Use");
        verifyItemsFilterWidget(verticalLayout.getComponent(2), "Publication Formats");
        verifyDateFieldComponent(verticalLayout.getComponent(3), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(4), "Survey Start Date From", "Survey Start Date To");
        assertSizedComboBoxItems(verticalLayout.getComponent(5), "Channel", true,
            Arrays.asList(UdmChannelEnum.values()));
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(6), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(7), "Company ID From", "Company ID To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(8), "Company Name");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(9), "Survey Country");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(10), "Language");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(11), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(12), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(13), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(14), "Quantity From", "Quantity To");
        verifyButtonsLayout(verticalLayout.getComponent(15), "Save", "Clear", "Close");
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertTrue(component instanceof HorizontalLayout);
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
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

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
        TextField fromField = (TextField) horizontalLayout.getComponent(0);
        ComboBox<FilterOperatorEnum> operatorComboBox =
            (ComboBox<FilterOperatorEnum>) horizontalLayout.getComponent(1);
        assertEquals(FilterOperatorEnum.EQUALS, operatorComboBox.getValue());
        assertTrue(fromField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.DOES_NOT_EQUAL);
        assertTrue(fromField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.CONTAINS);
        assertTrue(fromField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NULL);
        assertFalse(fromField.isEnabled());
        operatorComboBox.setValue(FilterOperatorEnum.IS_NOT_NULL);
        assertFalse(fromField.isEnabled());
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

    private void verifyBigDecimalOperationValidations(TextField fromField, TextField toField,
                                                      ComboBox<FilterOperatorEnum> operatorComboBox,
                                                      String fieldSpecificErrorMessage) {
        verifyCommonOperationValidations(fromField, toField, operatorComboBox, BETWEEN_OPERATOR_VALIDATION_MESSAGE);
        validateFieldAndVerifyErrorMessage(fromField, SPACES_STRING, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(fromField, VALID_DECIMAL, null, true);
        validateFieldAndVerifyErrorMessage(toField, "1.1345678", fieldSpecificErrorMessage, false);
        validateFieldAndVerifyErrorMessage(fromField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
        validateFieldAndVerifyErrorMessage(toField, INVALID_NUMBER, DECIMAL_VALIDATION_MESSAGE, false);
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

    private UdmUsageFilter buildExpectedFilter() {
        UdmUsageFilter filter = new UdmUsageFilter();
        filter.setUsageDateFrom(DATE_FROM);
        filter.setUsageDateTo(DATE_TO);
        filter.setSurveyStartDateFrom(DATE_FROM);
        filter.setSurveyStartDateTo(DATE_TO);
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setCompanyIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_ID, null));
        filter.setCompanyNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME, null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setLanguageExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null));
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
        assertComboBoxValue("channelComboBox", UdmChannelEnum.CCC);
        assertTextFieldValue("wrWrkInstFromField", WR_WRK_INST.toString());
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("companyIdFromField", COMPANY_ID.toString());
        assertTextFieldValue("companyNameField", COMPANY_NAME);
        assertTextFieldValue("surveyCountryField", SURVEY_COUNTRY);
        assertComboBoxValue("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("languageField", LANGUAGE);
        assertComboBoxValue("languageOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("annualMultiplierFromField", "1");
        assertTextFieldValue("annualMultiplierToField", "10");
        assertComboBoxValue("annualMultiplierOperatorComboBox", FilterOperatorEnum.BETWEEN);
        assertTextFieldValue("annualizedCopiesFromField", "5.5");
        assertComboBoxValue("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("statisticalMultiplierFromField", "2.2");
        assertComboBoxValue("statisticalMultiplierOperatorComboBox", FilterOperatorEnum.GREATER_THAN);
        assertTextFieldValue("quantityFromField", "3");
        assertComboBoxValue("quantityOperatorComboBox", FilterOperatorEnum.LESS_THAN);
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
        assertEquals(355, component.getWidth(), 0);
        assertEquals(Unit.PIXELS, component.getWidthUnits());
        assertEquals(component.getCaption(), caption);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, false,
            Arrays.asList(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    @SuppressWarnings(UNCHECKED)
    private void populateData() {
        populateLocalDateWidget("usageDateFromWidget", DATE_FROM);
        populateLocalDateWidget("usageDateToWidget", DATE_TO);
        populateLocalDateWidget("surveyStartDateFromWidget", DATE_FROM);
        populateLocalDateWidget("surveyStartDateToWidget", DATE_TO);
        populateComboBox("channelComboBox", UdmChannelEnum.CCC);
        populateTextField("wrWrkInstFromField", WR_WRK_INST.toString());
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("companyIdFromField", COMPANY_ID.toString());
        populateComboBox("companyIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("companyNameField", COMPANY_NAME);
        populateComboBox("companyNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("surveyCountryField", SURVEY_COUNTRY);
        populateComboBox("surveyCountryOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("languageField", LANGUAGE);
        populateComboBox("languageOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("annualMultiplierFromField", "1");
        populateTextField("annualMultiplierToField", "10");
        populateComboBox("annualMultiplierOperatorComboBox", FilterOperatorEnum.BETWEEN);
        populateTextField("annualizedCopiesFromField", "5.5");
        populateComboBox("annualizedCopiesOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("statisticalMultiplierFromField", "2.2");
        populateComboBox("statisticalMultiplierOperatorComboBox", FilterOperatorEnum.GREATER_THAN);
        populateTextField("quantityFromField", "3");
        populateComboBox("quantityOperatorComboBox", FilterOperatorEnum.LESS_THAN);
    }

    private void populateLocalDateWidget(String fieldName, LocalDate value) {
        ((LocalDateWidget) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void populateTextField(String fieldName, String value) {
        ((TextField) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    @SuppressWarnings(UNCHECKED)
    private <T> void populateComboBox(String fieldName, T value) {
        ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).setValue(value);
    }

    private void verifyDateWidgetValidationMessage(LocalDateWidget localDateWidget, LocalDate value, String message,
                                                   boolean isValid) {
        localDateWidget.setValue(value);
        List<ValidationResult> errors = binder.validate().getValidationErrors();
        List<String> errorMessages =
            errors.stream().map(ValidationResult::getErrorMessage).collect(Collectors.toList());
        assertEquals(!isValid, errorMessages.contains(message));
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
}
