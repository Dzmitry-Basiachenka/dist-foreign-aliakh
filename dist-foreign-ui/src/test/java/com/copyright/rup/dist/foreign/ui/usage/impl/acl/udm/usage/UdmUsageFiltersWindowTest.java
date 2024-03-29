package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.validateFieldAndVerifyErrorMessage;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyButtonsLayout;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyComboBox;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyItemsFilterWidget;
import static com.copyright.rup.dist.foreign.ui.usage.UiTestHelper.verifyWindow;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmActionReason;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.BaseUdmItemsFilterWidget;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private static final String USAGE_FILTER = "usageFilter";
    private static final String ASSIGNEE = "wjohn@copyright.com";
    private static final String LC_DESCRIPTION = "Law Firms";
    private static final String REPORTED_PUB_TYPES = "Book";
    private static final String REPORTED_TYPE_OF_USE = "EMAIL_COPY";
    private static final String TYPE_OF_USE = "DIGITAL";
    private static final String PUBLICATION_FORMATS = "PRINT";
    private static final LocalDate DATE_FROM = LocalDate.of(2021, 1, 1);
    private static final LocalDate DATE_TO = LocalDate.of(2021, 1, 2);
    private static final Long WR_WRK_INST = 243904752L;
    private static final String REPORTED_TITLE = "The New York times";
    private static final String SYSTEM_TITLE = "New York times";
    private static final String USAGE_DETAIL_ID = "b989e02b-1f1d-4637-b89e-dc99938a51b9";
    private static final Long COMPANY_ID = 454984566L;
    private static final String COMPANY_NAME = "Skadden, Arps, Slate, Meagher & Flom LLP";
    private static final String SURVEY_RESPONDENT = "fa0276c3-55d6-42cd-8ffe-e9124acae02f";
    private static final String SURVEY_COUNTRY = "United States";
    private static final String LANGUAGE = "English";
    private static final String VALID_INTEGER = "123456789";
    private static final String VALID_DECIMAL = "1.2345678";
    private static final String INVALID_NUMBER = "a12345678";
    private static final String INTEGER_WITH_SPACES_STRING = "  123  ";
    private static final String SPACES_STRING = "   ";
    private static final String FILTER_VALUE_COUNT_1 = "(1)";
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String DECIMAL_VALIDATION_MESSAGE =
        "Field value should be positive number or zero and should not exceed 10 digits";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";
    private final UdmActionReason actionReason =
        new UdmActionReason("1c8f6e43-2ca8-468d-8700-ce855e6cd8c0", "Aggregated Content");
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
        verifyWindow(window, "UDM usages additional filters", 600, 560, Unit.PIXELS);
        verifyRootLayout(window.getContent());
        verifyPanel();
    }

    @Test
    public void testConstructorWithPopulatedFilter() {
        UdmUsageFilter usageFilter = buildExpectedFilter();
        usageFilter.setAssignees(Set.of("user@copyright.com"));
        usageFilter.setPubFormats(Set.of("Digital", "Not Specified"));
        usageFilter.setReportedPubTypes(Set.of("Book", "Not Shared"));
        usageFilter.setReportedTypeOfUses(Set.of(REPORTED_TYPE_OF_USE));
        usageFilter.setTypeOfUse(TYPE_OF_USE);
        DetailLicenseeClass licenseeClass = new DetailLicenseeClass();
        licenseeClass.setId(26);
        licenseeClass.setDescription("Law Firms");
        usageFilter.setDetailLicenseeClasses(Set.of(licenseeClass));
        usageFilter.setActionReasons(Set.of(actionReason));
        replay(ForeignSecurityUtils.class);
        window = new UdmUsageFiltersWindow(createMock(IUdmUsageFilterController.class), usageFilter);
        verifyFiltersData();
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testWrWrkInstFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(6);
    }

    @Test
    public void testReportedTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(7);
    }

    @Test
    public void testSystemTitleFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(8);
    }

    @Test
    public void testUsageDetaiIdIdFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(9);
    }

    @Test
    public void testCompanyIdFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(10);
    }

    @Test
    public void testCompanyNameFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(11);
    }

    @Test
    public void testSurveyRespondentFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(12);
    }

    @Test
    public void testSurveyCountryFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(13);
    }

    @Test
    public void testLanguageFilterOperatorChangeListener() {
        testTextFilterOperatorChangeListener(14);
    }

    @Test
    public void testAnnualMultiplierFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(15);
    }

    @Test
    public void testAnnualizedCopiesFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(16);
    }

    @Test
    public void testStatisticalMultiplierMultiplierFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(17);
    }

    @Test
    public void testQuantityFilterOperatorChangeListener() {
        testNumericFilterOperatorChangeListener(17);
    }

    @Test
    public void testSaveButtonClickListener() {
        UdmUsageFilter usageFilter = Whitebox.getInternalState(window, USAGE_FILTER);
        assertTrue(usageFilter.isEmpty());
        populateData();
        HorizontalLayout buttonsLayout = (HorizontalLayout) ((VerticalLayout) window.getContent()).getComponent(1);
        Button saveButton = (Button) buttonsLayout.getComponent(0);
        saveButton.click();
        assertEquals(buildExpectedFilter(), usageFilter);
    }

    @Test
    public void testClearButtonClickListener() {
        UdmUsageFilter usageFilter = buildExpectedFilter();
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
    public void testFilterPermissions() {
        mockStatic(ForeignSecurityUtils.class);
        expect(ForeignSecurityUtils.hasResearcherPermission()).andReturn(true).once();
        replay(ForeignSecurityUtils.class);
        window = new UdmUsageFiltersWindow(createMock(IUdmUsageFilterController.class), new UdmUsageFilter());
        VerticalLayout verticalLayout = getFiltersLayout();
        assertTrue(verticalLayout.getComponent(0).isEnabled());
        assertTrue(verticalLayout.getComponent(1).isEnabled());
        assertTrue(verticalLayout.getComponent(2).isEnabled());
        assertTrue(verticalLayout.getComponent(3).isEnabled());
        assertTrue(verticalLayout.getComponent(4).isEnabled());
        assertTrue(verticalLayout.getComponent(5).isEnabled());
        assertTrue(verticalLayout.getComponent(6).isEnabled());
        assertTrue(verticalLayout.getComponent(7).isEnabled());
        assertTrue(verticalLayout.getComponent(8).isEnabled());
        assertTrue(verticalLayout.getComponent(9).isEnabled());
        assertFalse(verticalLayout.getComponent(10).isEnabled());
        assertFalse(verticalLayout.getComponent(11).isEnabled());
        assertFalse(verticalLayout.getComponent(12).isEnabled());
        assertFalse(verticalLayout.getComponent(13).isEnabled());
        assertTrue(verticalLayout.getComponent(14).isEnabled());
        assertFalse(verticalLayout.getComponent(15).isEnabled());
        assertFalse(verticalLayout.getComponent(16).isEnabled());
        assertFalse(verticalLayout.getComponent(17).isEnabled());
        assertFalse(verticalLayout.getComponent(18).isEnabled());
        verify(ForeignSecurityUtils.class);
    }

    @Test
    public void testUsageDateValidation() {
        LocalDateWidget usageDateFromWidget = Whitebox.getInternalState(window, "usageDateFromWidget");
        LocalDateWidget usageDateToWidget = Whitebox.getInternalState(window, "usageDateToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 1, 1);
        LocalDate localDateTo = LocalDate.of(2022, 1, 1);
        validateFieldAndVerifyErrorMessage(usageDateFromWidget, localDateFrom, binder, null, true);
        validateFieldAndVerifyErrorMessage(usageDateToWidget, localDateTo, binder, null, true);
        usageDateFromWidget.setValue(LocalDate.of(2023, 1, 1));
        validateFieldAndVerifyErrorMessage(usageDateToWidget, localDateTo, binder,
            "Field value should be greater or equal to Usage Date From", false);
        usageDateFromWidget.setValue(null);
        validateFieldAndVerifyErrorMessage(usageDateToWidget, localDateTo, binder, null, true);
    }

    @Test
    public void testSurveyStartDateValidation() {
        LocalDateWidget surveyStartDateFromWidget = Whitebox.getInternalState(window, "surveyStartDateFromWidget");
        LocalDateWidget surveyStartDateToWidget = Whitebox.getInternalState(window, "surveyStartDateToWidget");
        LocalDate localDateFrom = LocalDate.of(2021, 1, 1);
        LocalDate localDateTo = LocalDate.of(2022, 1, 1);
        validateFieldAndVerifyErrorMessage(surveyStartDateFromWidget, null, binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyStartDateToWidget, null, binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyStartDateFromWidget, localDateFrom, binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyStartDateToWidget, localDateTo, binder, null, true);
        surveyStartDateFromWidget.setValue(LocalDate.of(2023, 1, 1));
        validateFieldAndVerifyErrorMessage(surveyStartDateToWidget, localDateTo, binder,
            "Field value should be greater or equal to Survey Start Date From", false);
        surveyStartDateFromWidget.setValue(null);
        validateFieldAndVerifyErrorMessage(surveyStartDateToWidget, localDateTo, binder, null, true);
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
    public void testReportedTitleValidation() {
        TextField reportedTitleField = Whitebox.getInternalState(window, "reportedTitleField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "reportedTitleOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(reportedTitleField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(reportedTitleField, buildStringWithExpectedLength(2000), binder, null, true);
        validateFieldAndVerifyErrorMessage(reportedTitleField, buildStringWithExpectedLength(2001), binder,
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testSystemTitleValidation() {
        TextField systemTitleField = Whitebox.getInternalState(window, "systemTitleField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "systemTitleOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(systemTitleField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2000), binder, null, true);
        validateFieldAndVerifyErrorMessage(systemTitleField, buildStringWithExpectedLength(2001), binder,
            "Field value should not exceed 2000 characters", false);
    }

    @Test
    public void testUsageDetailIdValidation() {
        TextField usageDetailIdField = Whitebox.getInternalState(window, "usageDetailIdField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "usageDetailIdOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(usageDetailIdField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(50), binder, null, true);
        validateFieldAndVerifyErrorMessage(usageDetailIdField, buildStringWithExpectedLength(51), binder,
            "Field value should not exceed 50 characters", false);
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
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "companyNameOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(companyNameField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(companyNameField, buildStringWithExpectedLength(200), binder, null, true);
        validateFieldAndVerifyErrorMessage(companyNameField, buildStringWithExpectedLength(201), binder,
            "Field value should not exceed 200 characters", false);
    }

    @Test
    public void testSurveyRespondentValidation() {
        TextField surveyRespondentField = Whitebox.getInternalState(window, "surveyRespondentField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "surveyRespondentOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(surveyRespondentField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(
            surveyRespondentField, buildStringWithExpectedLength(200), binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyRespondentField, buildStringWithExpectedLength(201), binder,
            "Field value should not exceed 200 characters", false);
    }

    @Test
    public void testSurveyCountryValidation() {
        TextField surveyCountryField = Whitebox.getInternalState(window, "surveyCountryField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "surveyCountryOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(surveyCountryField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(100), binder, null, true);
        validateFieldAndVerifyErrorMessage(surveyCountryField, buildStringWithExpectedLength(101), binder,
            "Field value should not exceed 100 characters", false);
    }

    @Test
    public void testLanguageValidation() {
        TextField languageField = Whitebox.getInternalState(window, "languageField");
        assertTextOperatorComboBoxItems(Whitebox.getInternalState(window, "languageOperatorComboBox"));
        validateFieldAndVerifyErrorMessage(languageField, StringUtils.EMPTY, binder, null, true);
        validateFieldAndVerifyErrorMessage(languageField, buildStringWithExpectedLength(255), binder, null, true);
        validateFieldAndVerifyErrorMessage(languageField, buildStringWithExpectedLength(256), binder,
            "Field value should not exceed 255 characters", false);
    }

    private void verifyRootLayout(Component component) {
        assertThat(component, instanceOf(VerticalLayout.class));
        VerticalLayout verticalLayout = (VerticalLayout) component;
        assertEquals(2, verticalLayout.getComponentCount());
        verifyButtonsLayout(verticalLayout.getComponent(1), "Save", "Clear", "Close");
    }

    private void verifyPanel() {
        VerticalLayout verticalLayout = getFiltersLayout();
        assertEquals(19, verticalLayout.getComponentCount());
        verifyItemsFilterLayout(verticalLayout.getComponent(0), "Assignees", "Detail Licensee Classes");
        verifyItemsFilterLayout(verticalLayout.getComponent(1), "Reported Pub Types", "Reported Types of Use");
        verifyItemsFilterLayout(verticalLayout.getComponent(2), "Publication Formats", "Action Reasons");
        verifyDateFieldComponent(verticalLayout.getComponent(3), "Usage Date From", "Usage Date To");
        verifyDateFieldComponent(verticalLayout.getComponent(4), "Survey Start Date From", "Survey Start Date To");
        verifyComboBoxLayout(verticalLayout.getComponent(5), "Channel", List.of(UdmChannelEnum.values()),
            "Type of Use", List.of("PRINT", "DIGITAL"));
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(6), "Wr Wrk Inst From", "Wr Wrk Inst To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(7), "Reported Title");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(8), "System Title");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(9), "Usage Detail ID");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(10), "Company ID From", "Company ID To");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(11), "Company Name");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(12), "Survey Respondent");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(13), "Survey Country");
        verifyFieldWithTextOperatorComponent(verticalLayout.getComponent(14), "Language");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(15), "Annual Multiplier From",
            "Annual Multiplier To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(16), "Annualized Copies From",
            "Annualized Copies To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(17), "Statistical Multiplier From",
            "Statistical Multiplier To");
        verifyFieldWithNumericOperatorComponent(verticalLayout.getComponent(18), "Quantity From", "Quantity To");
    }

    private void verifyItemsFilterLayout(Component component, String firstCaption, String secondCaption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        verifyItemsFilterWidget(layout.getComponent(0), firstCaption);
        verifyItemsFilterWidget(layout.getComponent(1), secondCaption);
    }

    private void verifyDateFieldComponent(Component component, String captionFrom, String captionTo) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertTrue(layout.isEnabled());
        assertEquals(2, layout.getComponentCount());
        assertThat(layout.getComponent(0), instanceOf(LocalDateWidget.class));
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertThat(layout.getComponent(1), instanceOf(LocalDateWidget.class));
        assertEquals(captionTo, layout.getComponent(1).getCaption());
    }

    private void verifyComboBoxLayout(Component component, String firstCaption, List<?> firstItemList,
                                      String secondCaption, List<?> secondItemList) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        verifyComboBox(layout.getComponent(0), firstCaption, true, firstItemList);
        verifyComboBox(layout.getComponent(1), secondCaption, true, secondItemList);
    }

    private void verifyFieldWithTextOperatorComponent(Component component, String caption) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(2, layout.getComponentCount());
        assertThat(layout.getComponent(0), instanceOf(TextField.class));
        assertEquals(caption, layout.getComponent(0).getCaption());
        assertThat(layout.getComponent(1), instanceOf(ComboBox.class));
        assertEquals(CAPTION_OPERATOR, layout.getComponent(1).getCaption());
    }

    private void verifyFieldWithNumericOperatorComponent(Component component, String captionFrom, String captionTo) {
        assertThat(component, instanceOf(HorizontalLayout.class));
        HorizontalLayout layout = (HorizontalLayout) component;
        assertEquals(3, layout.getComponentCount());
        assertThat(layout.getComponent(0), instanceOf(TextField.class));
        assertEquals(captionFrom, layout.getComponent(0).getCaption());
        assertThat(layout.getComponent(1), instanceOf(TextField.class));
        assertEquals(captionTo, layout.getComponent(1).getCaption());
        assertThat(layout.getComponent(2), instanceOf(ComboBox.class));
        assertEquals(CAPTION_OPERATOR, layout.getComponent(2).getCaption());
    }

    @SuppressWarnings(UNCHECKED)
    private void testTextFilterOperatorChangeListener(int index) {
        HorizontalLayout horizontalLayout = (HorizontalLayout) (getFiltersLayout()).getComponent(index);
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
        VerticalLayout verticalLayout = getFiltersLayout();
        HorizontalLayout horizontalLayout = (HorizontalLayout) verticalLayout.getComponent(index);
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

    private UdmUsageFilter buildExpectedFilter() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(26);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        UdmUsageFilter filter = Whitebox.getInternalState(window, USAGE_FILTER);
        filter.setAssignees(Set.of(ASSIGNEE));
        filter.setDetailLicenseeClasses(Set.of(detailLicenseeClass));
        filter.setReportedPubTypes(Set.of(REPORTED_PUB_TYPES));
        filter.setReportedTypeOfUses(Set.of(REPORTED_TYPE_OF_USE));
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setPubFormats(Set.of(PUBLICATION_FORMATS));
        filter.setActionReasons(Set.of(actionReason));
        filter.setUsageDateFrom(DATE_FROM);
        filter.setUsageDateTo(DATE_TO);
        filter.setSurveyStartDateFrom(DATE_FROM);
        filter.setSurveyStartDateTo(DATE_TO);
        filter.setChannel(UdmChannelEnum.CCC);
        filter.setWrWrkInstExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, WR_WRK_INST, null));
        filter.setReportedTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, REPORTED_TITLE, null));
        filter.setSystemTitleExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SYSTEM_TITLE, null));
        filter.setUsageDetailIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, USAGE_DETAIL_ID, null));
        filter.setCompanyIdExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_ID, null));
        filter.setCompanyNameExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, COMPANY_NAME, null));
        filter.setSurveyRespondentExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_RESPONDENT,
            null));
        filter.setSurveyCountryExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, SURVEY_COUNTRY, null));
        filter.setLanguageExpression(new FilterExpression<>(FilterOperatorEnum.EQUALS, LANGUAGE, null));
        filter.setAnnualMultiplierExpression(new FilterExpression<>(FilterOperatorEnum.BETWEEN, 1L, 10L));
        filter.setAnnualizedCopiesExpression(
            new FilterExpression<>(FilterOperatorEnum.EQUALS, new BigDecimal("5.5"), null));
        filter.setStatisticalMultiplierExpression(
            new FilterExpression<>(FilterOperatorEnum.GREATER_THAN, new BigDecimal("2.2"), null));
        filter.setQuantityExpression(new FilterExpression<>(FilterOperatorEnum.LESS_THAN, 3L, null));
        return filter;
    }

    private void verifyFiltersData() {
        assertFilterWidgetLabelValue("assigneeFilterWidget", FILTER_VALUE_COUNT_1);
        assertFilterWidgetLabelValue("reportedPubTypeFilterWidget", "(2)");
        assertFilterWidgetLabelValue("publicationFormatFilterWidget", "(2)");
        assertFilterWidgetLabelValue("detailLicenseeClassFilterWidget", FILTER_VALUE_COUNT_1);
        assertFilterWidgetLabelValue("reportedTypeOfUseFilterWidget", FILTER_VALUE_COUNT_1);
        assertFilterWidgetLabelValue("actionReasonFilterWidget", FILTER_VALUE_COUNT_1);
        assertLocalDateFieldValue("usageDateFromWidget", DATE_FROM);
        assertLocalDateFieldValue("usageDateToWidget", DATE_TO);
        assertLocalDateFieldValue("surveyStartDateFromWidget", DATE_FROM);
        assertLocalDateFieldValue("surveyStartDateToWidget", DATE_TO);
        assertComboBoxValue("typeOfUseComboBox", TYPE_OF_USE);
        assertComboBoxValue("channelComboBox", UdmChannelEnum.CCC);
        assertTextFieldValue("wrWrkInstFromField", WR_WRK_INST.toString());
        assertComboBoxValue("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("reportedTitleField", REPORTED_TITLE);
        assertComboBoxValue("reportedTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("systemTitleField", SYSTEM_TITLE);
        assertComboBoxValue("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("usageDetailIdField", USAGE_DETAIL_ID);
        assertComboBoxValue("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        assertTextFieldValue("companyIdFromField", COMPANY_ID.toString());
        assertTextFieldValue("companyNameField", COMPANY_NAME);
        assertTextFieldValue("surveyRespondentField", SURVEY_RESPONDENT);
        assertComboBoxValue("surveyRespondentOperatorComboBox", FilterOperatorEnum.EQUALS);
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
    private <T> void assertComboBoxValue(String fieldName, T value) {
        assertEquals(value, ((ComboBox<T>) Whitebox.getInternalState(window, fieldName)).getValue());
    }

    private void assertTextOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            List.of(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void assertNumericOperatorComboBoxItems(ComboBox<FilterOperatorEnum> operatorComboBox) {
        verifyComboBox(operatorComboBox, CAPTION_OPERATOR, Unit.PIXELS, 230, false,
            List.of(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
                FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
                FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL));
    }

    private void populateData() {
        DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
        detailLicenseeClass.setId(26);
        detailLicenseeClass.setDescription(LC_DESCRIPTION);
        UdmUsageFilter filter = Whitebox.getInternalState(window, USAGE_FILTER);
        filter.setAssignees(Set.of(ASSIGNEE));
        filter.setDetailLicenseeClasses(Set.of(detailLicenseeClass));
        filter.setReportedPubTypes(Set.of(REPORTED_PUB_TYPES));
        filter.setReportedTypeOfUses(Set.of(REPORTED_TYPE_OF_USE));
        filter.setTypeOfUse(TYPE_OF_USE);
        filter.setPubFormats(Set.of(PUBLICATION_FORMATS));
        filter.setActionReasons(Set.of(actionReason));
        populateLocalDateWidget("usageDateFromWidget", DATE_FROM);
        populateLocalDateWidget("usageDateToWidget", DATE_TO);
        populateLocalDateWidget("surveyStartDateFromWidget", DATE_FROM);
        populateLocalDateWidget("surveyStartDateToWidget", DATE_TO);
        populateComboBox("channelComboBox", UdmChannelEnum.CCC);
        populateTextField("wrWrkInstFromField", WR_WRK_INST.toString());
        populateComboBox("wrWrkInstOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("reportedTitleField", REPORTED_TITLE);
        populateComboBox("reportedTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("systemTitleField", SYSTEM_TITLE);
        populateComboBox("systemTitleOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("usageDetailIdField", USAGE_DETAIL_ID);
        populateComboBox("usageDetailIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("companyIdFromField", COMPANY_ID.toString());
        populateComboBox("companyIdOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("companyNameField", COMPANY_NAME);
        populateComboBox("companyNameOperatorComboBox", FilterOperatorEnum.EQUALS);
        populateTextField("surveyRespondentField", SURVEY_RESPONDENT);
        populateComboBox("surveyRespondentOperatorComboBox", FilterOperatorEnum.EQUALS);
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

    private VerticalLayout getFiltersLayout() {
        VerticalLayout verticalLayout = (VerticalLayout) window.getContent();
        return (VerticalLayout) ((Panel) verticalLayout.getComponent(0)).getContent();
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

    private String buildStringWithExpectedLength(int length) {
        return StringUtils.repeat('1', length);
    }
}
