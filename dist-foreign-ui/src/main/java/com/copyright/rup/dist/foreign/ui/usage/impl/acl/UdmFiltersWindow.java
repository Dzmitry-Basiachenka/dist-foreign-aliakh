package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Window to apply additional filters for {@link UdmUsageFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
public class UdmFiltersWindow extends Window {

    private static final String AMOUNT_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.positive_number_and_length", 9);
    private static final String NUMBER_VALIDATION_MESSAGE = "Field value should contain numeric values only";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        "Field value should be populated for Between Operator";
    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField annualMultiplierFromField =
        new TextField(ForeignUi.getMessage("label.annual_multiplier_from"));
    private final TextField annualMultiplierToField = new TextField(ForeignUi.getMessage("label.annual_multiplier_to"));
    private final ComboBox<FilterOperatorEnum> annualMultiplierOperatorComboBox = buildOperatorComboBox();
    private final TextField annualizedCopiesFromField =
        new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesToField = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox = buildOperatorComboBox();
    private final TextField statisticalMultiplierFromField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier_from"));
    private final TextField statisticalMultiplierToField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier_to"));
    private final ComboBox<FilterOperatorEnum> statisticalMultiplierOperatorComboBox = buildOperatorComboBox();
    private final TextField quantityFromField = new TextField(ForeignUi.getMessage("label.quantity_from"));
    private final TextField quantityToField = new TextField(ForeignUi.getMessage("label.quantity_to"));
    private final ComboBox<FilterOperatorEnum> quantityOperatorComboBox = buildOperatorComboBox();
    private final TextField surveyCountryField = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final TextField languageField = new TextField(ForeignUi.getMessage("label.language"));
    private final TextField companyNameField = new TextField(ForeignUi.getMessage("label.company_name"));
    private final TextField companyIdField = new TextField(ForeignUi.getMessage("label.company_id"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final LocalDateWidget usageDateFromWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.usage_date_from"));
    private final LocalDateWidget usageDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.usage_date_to"));
    private final LocalDateWidget surveyStartDateFromWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_from"));
    private final LocalDateWidget surveyStartDateToWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_to"));
    private final ComboBox<UdmChannelEnum> channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
    private AssigneeFilterWidget assigneeFilterWidget;
    private ReportedPubTypeFilterWidget reportedPubTypeFilterWidget;
    private PublicationFormatFilterWidget publicationFormatFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;
    private UdmUsageFilter usageFilter;
    private UdmUsageFilter appliedUsageFilter;
    private final IUdmUsageFilterController controller;
    private final boolean isFilterPermittedForUser = !ForeignSecurityUtils.hasResearcherPermission();
    private final Binder<UdmUsageFilter> filterBinder = new Binder<>();

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageFilterController}
     */
    public UdmFiltersWindow(IUdmUsageFilterController controller) {
        this.controller = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_additional_filters"));
        setResizable(false);
        setWidth(550, Unit.PIXELS);
        setHeight(560, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-additional-filters-window");
    }

    /**
     * Sets UDM usage filter to display on window.
     *
     * @param udmUsageFilter UDM usage filter
     */
    public void setUdmUsageFilter(UdmUsageFilter udmUsageFilter) {
        usageFilter = new UdmUsageFilter(udmUsageFilter);
        appliedUsageFilter = udmUsageFilter;
    }

    /**
     * @return applied UDM usage filter.
     */
    public UdmUsageFilter getAppliedUsageFilter() {
        return appliedUsageFilter;
    }

    /**
     * Clears all additional filters.
     */
    public void clearFilters() {
        clearUsageFilter();
        assigneeFilterWidget.reset();
        reportedPubTypeFilterWidget.reset();
        publicationFormatFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        usageDateFromWidget.clear();
        usageDateToWidget.clear();
        surveyStartDateFromWidget.clear();
        surveyStartDateToWidget.clear();
        channelComboBox.clear();
        wrWrkInstField.clear();
        companyIdField.clear();
        companyNameField.clear();
        surveyCountryField.clear();
        languageField.clear();
        clearOperatorLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        clearOperatorLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        clearOperatorLayout(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox);
        clearOperatorLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
    }

    private void clearUsageFilter() {
        appliedUsageFilter.setAssignees(null);
        appliedUsageFilter.setPubFormats(null);
        appliedUsageFilter.setReportedTypeOfUses(null);
        appliedUsageFilter.setReportedPubTypes(null);
        appliedUsageFilter.setDetailLicenseeClasses(null);
        appliedUsageFilter.setUsageDateFrom(null);
        appliedUsageFilter.setUsageDateTo(null);
        appliedUsageFilter.setSurveyStartDateFrom(null);
        appliedUsageFilter.setSurveyStartDateTo(null);
        appliedUsageFilter.setChannel(null);
        appliedUsageFilter.setWrWrkInst(null);
        appliedUsageFilter.setCompanyId(null);
        appliedUsageFilter.setCompanyName(null);
        appliedUsageFilter.setSurveyCountry(null);
        appliedUsageFilter.setLanguage(null);
        appliedUsageFilter.setAnnualMultiplierExpression(new FilterExpression<>());
        appliedUsageFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        appliedUsageFilter.setStatisticalMultiplierExpression(new FilterExpression<>());
        appliedUsageFilter.setQuantityExpression(new FilterExpression<>());
        usageFilter = new UdmUsageFilter(appliedUsageFilter);
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initAssigneeFilterWidget();
        initReportedPublicationTypeFilterWidget();
        initPublicationFormatFilterWidget();
        initDetailLicenseeClassNameFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initAssigneeLicenseeClassLayout(), initReportedPubTypeTouLayout(),
            publicationFormatFilterWidget, initUsageDateLayout(), initSurveyDateLayout(), initChannelWrWrkInstLayout(),
            initCompanyLayout(), initSurveyCountryLanguageLayout(), initAnnualMultiplierLayout(),
            initAnnualizedCopiesLayout(), initStatisticalMultiplierLayout(), initQuantityLayout(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.validate();
        return rootLayout;
    }

    private void initAssigneeFilterWidget() {
        assigneeFilterWidget = new AssigneeFilterWidget(controller::getAssignees);
        assigneeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setAssignees(saveEvent.getSelectedItemsIds()));
    }

    private void initReportedPublicationTypeFilterWidget() {
        reportedPubTypeFilterWidget = new ReportedPubTypeFilterWidget(controller::getPublicationTypes);
        reportedPubTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setReportedPubTypes(saveEvent.getSelectedItemsIds()));
    }

    private void initPublicationFormatFilterWidget() {
        publicationFormatFilterWidget = new PublicationFormatFilterWidget(controller::getPublicationFormats);
        publicationFormatFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<String>) saveEvent -> usageFilter.setPubFormats(saveEvent.getSelectedItemsIds()));
    }

    private void initDetailLicenseeClassNameFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses);
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                usageFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget = new TypeOfUseFilterWidget(controller::getTypeOfUses);
        typeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setReportedTypeOfUses(saveEvent.getSelectedItemsIds()));
    }

    private HorizontalLayout initAssigneeLicenseeClassLayout() {
        HorizontalLayout assigneeLicenseeClassLayout =
            new HorizontalLayout(assigneeFilterWidget, detailLicenseeClassFilterWidget);
        assigneeLicenseeClassLayout.setSizeFull();
        assigneeLicenseeClassLayout.setSpacing(true);
        return assigneeLicenseeClassLayout;
    }

    private HorizontalLayout initReportedPubTypeTouLayout() {
        HorizontalLayout reportedPubTypeTouLayout =
            new HorizontalLayout(reportedPubTypeFilterWidget, typeOfUseFilterWidget);
        reportedPubTypeTouLayout.setSizeFull();
        reportedPubTypeTouLayout.setSpacing(true);
        return reportedPubTypeTouLayout;
    }

    private HorizontalLayout initChannelWrWrkInstLayout() {
        HorizontalLayout comboBoxLayout = new HorizontalLayout(channelComboBox, wrWrkInstField);
        filterBinder.forField(wrWrkInstField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withConverter(new StringToLongConverter("Field should be numeric"))
            .bind(UdmUsageFilter::getWrWrkInst, UdmUsageFilter::setWrWrkInst);
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setSizeFull();
        wrWrkInstField.setSizeFull();
        comboBoxLayout.setSizeFull();
        comboBoxLayout.setSpacing(true);
        return comboBoxLayout;
    }

    private HorizontalLayout initUsageDateLayout() {
        HorizontalLayout usageDateLayout = new HorizontalLayout(usageDateFromWidget, usageDateToWidget);
        usageDateFromWidget.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(usageDateToWidget)
            .withValidator(value -> {
                LocalDate usageDateFrom = usageDateFromWidget.getValue();
                LocalDate usageDateTo = usageDateToWidget.getValue();
                return Objects.isNull(usageDateFrom)
                    || Objects.isNull(usageDateTo)
                    || 0 <= usageDateTo.compareTo(usageDateFrom);
            }, "Field value should be greater or equal to Usage Date From")
            .bind(UdmUsageFilter::getUsageDateTo, UdmUsageFilter::setUsageDateTo);
        usageDateLayout.setSizeFull();
        usageDateLayout.setSpacing(true);
        return usageDateLayout;
    }

    private HorizontalLayout initSurveyDateLayout() {
        HorizontalLayout surveyDateLayout = new HorizontalLayout(surveyStartDateFromWidget, surveyStartDateToWidget);
        surveyStartDateFromWidget.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(surveyStartDateToWidget)
            .withValidator(value -> {
                LocalDate surveyStartDateFrom = surveyStartDateFromWidget.getValue();
                LocalDate surveyStartDateTo = surveyStartDateToWidget.getValue();
                return Objects.isNull(surveyStartDateFrom)
                    || Objects.isNull(surveyStartDateTo)
                    || 0 <= surveyStartDateTo.compareTo(surveyStartDateFrom);
            }, "Field value should be greater or equal to Survey Start Date From")
            .bind(UdmUsageFilter::getSurveyStartDateTo, UdmUsageFilter::setSurveyStartDateTo);
        surveyDateLayout.setSizeFull();
        surveyDateLayout.setSpacing(true);
        return surveyDateLayout;
    }

    private HorizontalLayout initAnnualMultiplierLayout() {
        HorizontalLayout annualMultiplierLayout =
            new HorizontalLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        annualMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        annualMultiplierOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualMultiplierToField, event.getValue()));
        filterBinder.forField(annualMultiplierFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualMultiplierFromField, annualMultiplierOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualMultiplierExpression().setFieldFirstValue(Integer.valueOf(value)));
        filterBinder.forField(annualMultiplierToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualMultiplierToField, annualMultiplierOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(annualMultiplierFromField, annualMultiplierToField),
                "Field value should be greater or equal to Annual Multiplier From")
            .bind(filter -> filter.getAnnualMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualMultiplierExpression().setFieldFirstValue(Integer.valueOf(value)));
        annualMultiplierToField.setEnabled(false);
        annualMultiplierFromField.setSizeFull();
        annualMultiplierToField.setSizeFull();
        annualMultiplierLayout.setEnabled(isFilterPermittedForUser);
        annualMultiplierLayout.setSizeFull();
        return annualMultiplierLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        annualizedCopiesFromField.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFromField)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFromField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesToField)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualizedCopiesToField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFromField, annualizedCopiesToField),
                "Field value should be greater or equal to Annualized Copies From")
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualizedCopiesToField, event.getValue()));
        annualizedCopiesToField.setEnabled(false);
        annualizedCopiesFromField.setSizeFull();
        annualizedCopiesToField.setSizeFull();
        annualizedCopiesLayout.setEnabled(isFilterPermittedForUser);
        annualizedCopiesLayout.setSizeFull();
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initStatisticalMultiplierLayout() {
        HorizontalLayout statisticalMultiplierLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        statisticalMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        statisticalMultiplierOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(statisticalMultiplierToField, event.getValue()));
        filterBinder.forField(statisticalMultiplierFromField)
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierFromField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(statisticalMultiplierToField)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .withValidator(
                value -> validateBigDecimalFromToValues(statisticalMultiplierFromField, statisticalMultiplierToField),
                "Field value should be greater or equal to Statistical Multiplier From")
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierToField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldSecondValue(new BigDecimal(value)));
        statisticalMultiplierToField.setEnabled(false);
        statisticalMultiplierFromField.setSizeFull();
        statisticalMultiplierToField.setSizeFull();
        statisticalMultiplierLayout.setEnabled(isFilterPermittedForUser);
        statisticalMultiplierLayout.setSizeFull();
        return statisticalMultiplierLayout;
    }

    private HorizontalLayout initQuantityLayout() {
        HorizontalLayout quantityLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
        quantityFromField.addValueChangeListener(event -> filterBinder.validate());
        quantityOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(quantityToField, event.getValue()));
        filterBinder.forField(quantityFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(quantityFromField, quantityOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getQuantityExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getQuantityExpression().setFieldFirstValue(Integer.valueOf(value)));
        filterBinder.forField(quantityToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(quantityToField, quantityOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(quantityFromField, quantityToField),
                "Field value should be greater or equal to Quantity From")
            .bind(filter -> filter.getQuantityExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getQuantityExpression().setFieldFirstValue(Integer.valueOf(value)));
        quantityToField.setEnabled(false);
        quantityFromField.setSizeFull();
        quantityToField.setSizeFull();
        quantityLayout.setEnabled(isFilterPermittedForUser);
        quantityLayout.setSizeFull();
        return quantityLayout;
    }

    private HorizontalLayout initCompanyLayout() {
        HorizontalLayout companyLayout = new HorizontalLayout(companyIdField, companyNameField);
        filterBinder.forField(companyIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withConverter(new StringToLongConverter("Field should be numeric"))
            .bind(UdmUsageFilter::getCompanyId, UdmUsageFilter::setCompanyId);
        filterBinder.forField(companyNameField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 200), 0, 200))
            .bind(UdmUsageFilter::getCompanyName, UdmUsageFilter::setCompanyName);
        companyIdField.setSizeFull();
        companyNameField.setSizeFull();
        companyLayout.setEnabled(isFilterPermittedForUser);
        companyLayout.setSizeFull();
        companyLayout.setSpacing(true);
        return companyLayout;
    }

    private HorizontalLayout initSurveyCountryLanguageLayout() {
        HorizontalLayout surveyCountryLanguageLayout = new HorizontalLayout(surveyCountryField, languageField);
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 100), 0, 100))
            .bind(UdmUsageFilter::getSurveyCountry, UdmUsageFilter::setSurveyCountry);
        filterBinder.forField(languageField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 255), 0, 255))
            .bind(UdmUsageFilter::getLanguage, UdmUsageFilter::setLanguage);
        surveyCountryField.setEnabled(isFilterPermittedForUser);
        surveyCountryField.setSizeFull();
        languageField.setSizeFull();
        surveyCountryLanguageLayout.setSizeFull();
        surveyCountryLanguageLayout.setSpacing(true);
        return surveyCountryLanguageLayout;
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.values());
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void updateOperatorField(TextField fieldToUpdate, FilterOperatorEnum value) {
        if (FilterOperatorEnum.BETWEEN == value) {
            fieldToUpdate.setEnabled(true);
        } else {
            fieldToUpdate.clear();
            fieldToUpdate.setEnabled(false);
        }
        filterBinder.validate();
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (filterBinder.isValid()) {
                populateUsageFilter();
                appliedUsageFilter = usageFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(
                    Arrays.asList(usageDateToWidget, surveyStartDateToWidget, wrWrkInstField, companyIdField,
                        companyNameField, surveyCountryField, languageField, annualMultiplierFromField,
                        annualMultiplierToField, annualizedCopiesFromField, annualizedCopiesToField,
                        statisticalMultiplierFromField, statisticalMultiplierToField, quantityFromField,
                        quantityToField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearOperatorLayout(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox) {
        fromField.clear();
        toField.clear();
        toField.setEnabled(false);
        comboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    private void populateUsageFilter() {
        usageFilter.setUsageDateFrom(usageDateFromWidget.getValue());
        usageFilter.setUsageDateTo(usageDateToWidget.getValue());
        usageFilter.setSurveyStartDateFrom(surveyStartDateFromWidget.getValue());
        usageFilter.setSurveyStartDateTo(surveyStartDateToWidget.getValue());
        usageFilter.setChannel(channelComboBox.getValue());
        usageFilter.setWrWrkInst(getLongFromTextField(wrWrkInstField));
        usageFilter.setCompanyId(getLongFromTextField(companyIdField));
        usageFilter.setCompanyName(getStringFromTextField(companyNameField));
        usageFilter.setSurveyCountry(getStringFromTextField(surveyCountryField));
        usageFilter.setLanguage(getStringFromTextField(languageField));
        usageFilter.setAnnualMultiplierExpression(buildNumberFilterExpression(annualMultiplierFromField,
            annualMultiplierToField, annualMultiplierOperatorComboBox, Integer::valueOf));
        usageFilter.setAnnualizedCopiesExpression(buildNumberFilterExpression(annualizedCopiesFromField,
            annualizedCopiesToField, annualizedCopiesOperatorComboBox, BigDecimal::new));
        usageFilter.setStatisticalMultiplierExpression(buildNumberFilterExpression(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox, BigDecimal::new));
        usageFilter.setQuantityExpression(buildNumberFilterExpression(quantityFromField, quantityToField,
            quantityOperatorComboBox, Integer::valueOf));
    }

    private Long getLongFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? Long.valueOf(textField.getValue()) : null;
    }

    private String getStringFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? textField.getValue() : null;
    }

    private FilterExpression<Number> buildNumberFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        if (StringUtils.isNotEmpty(fromField.getValue())) {
            filterExpression.setFieldFirstValue(valueConverter.apply(fromField.getValue()));
            filterExpression.setFieldSecondValue(
                StringUtils.isNotEmpty(toField.getValue()) ? valueConverter.apply(toField.getValue()) : null);
            filterExpression.setOperatorEnum(comboBox.getValue());
        }
        return filterExpression;
    }

    private SerializablePredicate<String> getAmountValidator() {
        return value -> new AmountValidator(false).isValid(StringUtils.trimToEmpty(value));
    }

    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(StringUtils.trim(value));
    }

    private SerializablePredicate<String> getBetweenOperatorValidator(TextField fieldToValidate,
                                                                      ComboBox<FilterOperatorEnum> comboBox) {
        return value -> FilterOperatorEnum.BETWEEN != comboBox.getValue()
            || StringUtils.isNotEmpty(fieldToValidate.getValue());
    }

    private boolean validateBigDecimalFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getAmountValidator().test(fromValue)
            || !getAmountValidator().test(toValue)
            || 0 <= new BigDecimal(toValue).compareTo(new BigDecimal(fromValue));
    }

    private boolean validateIntegerFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getNumberValidator().test(fromValue)
            || !getNumberValidator().test(toValue)
            || 0 <= Integer.valueOf(toValue).compareTo(Integer.valueOf(fromValue));
    }
}
