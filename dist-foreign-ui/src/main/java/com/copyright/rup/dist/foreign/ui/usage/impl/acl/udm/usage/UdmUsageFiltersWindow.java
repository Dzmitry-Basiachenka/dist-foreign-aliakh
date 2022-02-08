package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PublicationFormatFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedPubTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
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
public class UdmUsageFiltersWindow extends CommonUdmFiltersWindow {

    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String LENGTH_VALIDATION_MESSAGE = "field.error.length";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField annualMultiplierFromField =
        new TextField(ForeignUi.getMessage("label.annual_multiplier_from"));
    private final TextField annualMultiplierToField = new TextField(ForeignUi.getMessage("label.annual_multiplier_to"));
    private final ComboBox<FilterOperatorEnum> annualMultiplierOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField annualizedCopiesFromField =
        new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesToField = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField statisticalMultiplierFromField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier_from"));
    private final TextField statisticalMultiplierToField =
        new TextField(ForeignUi.getMessage("label.statistical_multiplier_to"));
    private final ComboBox<FilterOperatorEnum> statisticalMultiplierOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField quantityFromField = new TextField(ForeignUi.getMessage("label.quantity_from"));
    private final TextField quantityToField = new TextField(ForeignUi.getMessage("label.quantity_to"));
    private final ComboBox<FilterOperatorEnum> quantityOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField surveyRespondentField = new TextField(ForeignUi.getMessage("label.survey_respondent"));
    private final ComboBox<FilterOperatorEnum> surveyRespondentOperatorComboBox = buildTextOperatorComboBox();
    private final TextField surveyCountryField = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox = buildTextOperatorComboBox();
    private final TextField languageField = new TextField(ForeignUi.getMessage("label.language"));
    private final ComboBox<FilterOperatorEnum> languageOperatorComboBox = buildTextOperatorComboBox();
    private final TextField companyIdFromField = new TextField(ForeignUi.getMessage("label.company_id_from"));
    private final TextField companyIdToField = new TextField(ForeignUi.getMessage("label.company_id_to"));
    private final ComboBox<FilterOperatorEnum> companyIdOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField companyNameField = new TextField(ForeignUi.getMessage("label.company_name"));
    private final ComboBox<FilterOperatorEnum> companyNameOperatorComboBox = buildTextOperatorComboBox();
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField reportedTitleField = new TextField(ForeignUi.getMessage("label.reported_title"));
    private final ComboBox<FilterOperatorEnum> reportedTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField usageDetailIdField = new TextField(ForeignUi.getMessage("label.usage_detail_id"));
    private final ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox = buildTextOperatorComboBox();
    private final LocalDateWidget usageDateFromWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.usage_date_from"));
    private final LocalDateWidget usageDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.usage_date_to"));
    private final LocalDateWidget surveyStartDateFromWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_from"));
    private final LocalDateWidget surveyStartDateToWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_to"));
    private final ComboBox<UdmChannelEnum> channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
    private final boolean isFilterPermittedForUser = !ForeignSecurityUtils.hasResearcherPermission();
    private final Binder<UdmUsageFilter> filterBinder = new Binder<>();
    private AssigneeFilterWidget assigneeFilterWidget;
    private ReportedPubTypeFilterWidget reportedPubTypeFilterWidget;
    private PublicationFormatFilterWidget publicationFormatFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;
    private UdmUsageFilter appliedUsageFilter;
    private final UdmUsageFilter usageFilter;
    private final IUdmUsageFilterController controller;

    /**
     * Constructor.
     *
     * @param controller     instance of {@link IUdmUsageFilterController}
     * @param udmUsageFilter instance of {@link UdmUsageFilter} to be displayed on window
     */
    public UdmUsageFiltersWindow(IUdmUsageFilterController controller, UdmUsageFilter udmUsageFilter) {
        this.controller = controller;
        usageFilter = new UdmUsageFilter(udmUsageFilter);
        appliedUsageFilter = udmUsageFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_usages_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(560, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-additional-filters-window");
    }

    /**
     * @return applied UDM usage filter.
     */
    public UdmUsageFilter getAppliedUsageFilter() {
        return appliedUsageFilter;
    }

    private ComponentContainer initRootLayout() {
        initAssigneeFilterWidget();
        initReportedPublicationTypeFilterWidget();
        initPublicationFormatFilterWidget();
        initDetailLicenseeClassFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initAssigneeLicenseeClassLayout(), initReportedPubTypeTouLayout(),
            publicationFormatFilterWidget, initUsageDateLayout(), initSurveyDateLayout(), initChannelLayout(),
            initWrWrkInstLayout(), initReportedTitleLayout(), initSystemTitleLayout(), initUsageDetailIdLayout(),
            initCompanyIdLayout(), initCompanyNameLayout(), initSurveyRespondentLayout(), initSurveyCountryLayout(),
            initLanguageLayout(), initAnnualMultiplierLayout(), initAnnualizedCopiesLayout(),
            initStatisticalMultiplierLayout(), initQuantityLayout());
        filterBinder.validate();
        return buildRootLayout(fieldsLayout);
    }

    private VerticalLayout buildRootLayout(VerticalLayout fieldsLayout) {
        VerticalLayout rootLayout = new VerticalLayout();
        Panel panel = new Panel(fieldsLayout);
        panel.setSizeFull();
        fieldsLayout.setMargin(new MarginInfo(true));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(panel, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setExpandRatio(panel, 1f);
        rootLayout.setSizeFull();
        panel.setStyleName(Cornerstone.FORMLAYOUT_LIGHT);
        return rootLayout;
    }

    private void initAssigneeFilterWidget() {
        assigneeFilterWidget = new AssigneeFilterWidget(controller::getAssignees, usageFilter.getAssignees());
        assigneeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setAssignees(saveEvent.getSelectedItemsIds()));
    }

    private void initReportedPublicationTypeFilterWidget() {
        reportedPubTypeFilterWidget =
            new ReportedPubTypeFilterWidget(controller::getPublicationTypes, usageFilter.getReportedPubTypes());
        reportedPubTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setReportedPubTypes(saveEvent.getSelectedItemsIds()));
    }

    private void initPublicationFormatFilterWidget() {
        publicationFormatFilterWidget =
            new PublicationFormatFilterWidget(controller::getPublicationFormats, usageFilter.getPubFormats());
        publicationFormatFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<String>) saveEvent -> usageFilter.setPubFormats(saveEvent.getSelectedItemsIds()));
    }

    private void initDetailLicenseeClassFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            usageFilter.getDetailLicenseeClasses());
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                usageFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget =
            new TypeOfUseFilterWidget(controller::getTypeOfUses, usageFilter.getReportedTypeOfUses());
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

    private ComboBox<UdmChannelEnum> initChannelLayout() {
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setSelectedItem(usageFilter.getChannel());
        channelComboBox.setSizeFull();
        channelComboBox.setWidth(50, Unit.PERCENTAGE);
        VaadinUtils.addComponentStyle(channelComboBox, "udm-channel-filter");
        return channelComboBox;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            usageFilter.getWrWrkInstExpression());
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> filter.getWrWrkInstExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldSecondValue(Long.valueOf(value)));
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initReportedTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(reportedTitleField, reportedTitleOperatorComboBox);
        filterBinder.forField(reportedTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getReportedTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getReportedTitleExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(reportedTitleField, reportedTitleOperatorComboBox,
            usageFilter.getReportedTitleExpression());
        reportedTitleField.addValueChangeListener(event -> filterBinder.validate());
        reportedTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, reportedTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, reportedTitleField);
        VaadinUtils.addComponentStyle(reportedTitleField, "udm-reported-title-filter");
        VaadinUtils.addComponentStyle(reportedTitleOperatorComboBox, "udm-reported-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            usageFilter.getSystemTitleExpression());
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 50), 0, 50))
            .bind(filter -> filter.getUsageDetailIdExpression().getFieldFirstValue(),
                (filter, value) -> filter.getUsageDetailIdExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            usageFilter.getUsageDetailIdExpression());
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, usageDetailIdField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, usageDetailIdField);
        VaadinUtils.addComponentStyle(usageDetailIdField, "udm-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "udm-usage-detail-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initUsageDateLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDateFromWidget, usageDateToWidget);
        usageDateFromWidget.addValueChangeListener(event -> filterBinder.validate());
        usageDateFromWidget.setValue(usageFilter.getUsageDateFrom());
        usageDateToWidget.setValue(usageFilter.getUsageDateTo());
        filterBinder.forField(usageDateToWidget)
            .withValidator(value -> {
                LocalDate usageDateFrom = usageDateFromWidget.getValue();
                LocalDate usageDateTo = usageDateToWidget.getValue();
                return Objects.isNull(usageDateFrom)
                    || Objects.isNull(usageDateTo)
                    || 0 <= usageDateTo.compareTo(usageDateFrom);
            }, ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                ForeignUi.getMessage("label.usage_date_from")))
            .bind(UdmUsageFilter::getUsageDateTo, UdmUsageFilter::setUsageDateTo);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(usageDateFromWidget, "udm-usage-date-from-filter");
        VaadinUtils.addComponentStyle(usageDateToWidget, "udm-usage-date-to-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyDateLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyStartDateFromWidget, surveyStartDateToWidget);
        surveyStartDateFromWidget.addValueChangeListener(event -> filterBinder.validate());
        surveyStartDateFromWidget.setValue(usageFilter.getSurveyStartDateFrom());
        surveyStartDateToWidget.setValue(usageFilter.getSurveyStartDateTo());
        filterBinder.forField(surveyStartDateToWidget)
            .withValidator(value -> {
                LocalDate surveyStartDateFrom = surveyStartDateFromWidget.getValue();
                LocalDate surveyStartDateTo = surveyStartDateToWidget.getValue();
                return Objects.isNull(surveyStartDateFrom)
                    || Objects.isNull(surveyStartDateTo)
                    || 0 <= surveyStartDateTo.compareTo(surveyStartDateFrom);
            }, ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                ForeignUi.getMessage("label.survey_start_date_from")))
            .bind(UdmUsageFilter::getSurveyStartDateTo, UdmUsageFilter::setSurveyStartDateTo);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(surveyStartDateFromWidget, "udm-survey-start-date-from-filter");
        VaadinUtils.addComponentStyle(surveyStartDateToWidget, "udm-survey-start-date-to-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initAnnualMultiplierLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        populateOperatorFilters(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox,
            usageFilter.getAnnualMultiplierExpression());
        annualMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        annualMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, annualMultiplierFromField, annualMultiplierToField, event.getValue()));
        filterBinder.forField(annualMultiplierFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(annualMultiplierFromField, annualMultiplierOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualMultiplierExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(annualMultiplierToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(annualMultiplierToField, annualMultiplierOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(annualMultiplierFromField, annualMultiplierToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annual_multiplier_from")))
            .bind(filter -> filter.getAnnualMultiplierExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualMultiplierExpression().setFieldSecondValue(Long.valueOf(value)));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, annualMultiplierFromField, annualMultiplierToField);
        VaadinUtils.addComponentStyle(annualMultiplierFromField, "udm-annual-multiplier-from-filter");
        VaadinUtils.addComponentStyle(annualMultiplierToField, "udm-annual-multiplier-to-filter");
        VaadinUtils.addComponentStyle(annualMultiplierOperatorComboBox, "udm-annual-multiplier-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox,
            usageFilter.getAnnualizedCopiesExpression());
        annualizedCopiesFromField.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFromField)
            .withValidator(new AmountValidator(NUMBER_VALIDATION_MESSAGE))
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFromField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesToField)
            .withValidator(new AmountValidator(NUMBER_VALIDATION_MESSAGE))
            .withValidator(getBetweenOperatorValidator(annualizedCopiesToField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFromField, annualizedCopiesToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, annualizedCopiesFromField, annualizedCopiesToField, event.getValue()));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, annualizedCopiesFromField, annualizedCopiesToField);
        VaadinUtils.addComponentStyle(annualizedCopiesFromField, "udm-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesToField, "udm-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox, "udm-annualized-copies-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initStatisticalMultiplierLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        populateOperatorFilters(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox, usageFilter.getStatisticalMultiplierExpression());
        statisticalMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        statisticalMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, statisticalMultiplierFromField, statisticalMultiplierToField,
                event.getValue()));
        filterBinder.forField(statisticalMultiplierFromField)
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierFromField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(new AmountValidator(NUMBER_VALIDATION_MESSAGE))
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(statisticalMultiplierToField)
            .withValidator(new AmountValidator(NUMBER_VALIDATION_MESSAGE))
            .withValidator(
                value -> validateBigDecimalFromToValues(statisticalMultiplierFromField, statisticalMultiplierToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.statistical_multiplier_from")))
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierToField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldSecondValue(new BigDecimal(value)));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, statisticalMultiplierFromField,
            statisticalMultiplierToField);
        VaadinUtils.addComponentStyle(statisticalMultiplierFromField, "udm-statistical-multiplier-from-filter");
        VaadinUtils.addComponentStyle(statisticalMultiplierToField, "udm-statistical-multiplier-to-filter");
        VaadinUtils.addComponentStyle(statisticalMultiplierOperatorComboBox,
            "udm-statistical-multiplier-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initQuantityLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
        populateOperatorFilters(quantityFromField, quantityToField, quantityOperatorComboBox,
            usageFilter.getQuantityExpression());
        quantityFromField.addValueChangeListener(event -> filterBinder.validate());
        quantityOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, quantityFromField, quantityToField, event.getValue()));
        filterBinder.forField(quantityFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(quantityFromField, quantityOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getQuantityExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getQuantityExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(quantityToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(quantityToField, quantityOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(quantityFromField, quantityToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.quantity_from")))
            .bind(filter -> filter.getQuantityExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getQuantityExpression().setFieldSecondValue(Long.valueOf(value)));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, quantityFromField, quantityToField);
        VaadinUtils.addComponentStyle(quantityFromField, "udm-quantity-from-filter");
        VaadinUtils.addComponentStyle(quantityToField, "udm-quantity-to-filter");
        VaadinUtils.addComponentStyle(quantityOperatorComboBox, "udm-quantity-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCompanyIdLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(companyIdFromField, companyIdToField, companyIdOperatorComboBox);
        populateOperatorFilters(companyIdFromField, companyIdToField, companyIdOperatorComboBox,
            usageFilter.getCompanyIdExpression());
        companyIdFromField.addValueChangeListener(event -> filterBinder.validate());
        companyIdOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, companyIdFromField, companyIdToField, event.getValue()));
        filterBinder.forField(companyIdFromField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(companyIdFromField, companyIdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getCompanyIdExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getCompanyIdExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(companyIdToField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(companyIdToField, companyIdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(companyIdFromField, companyIdToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.company_id_from")))
            .bind(filter -> filter.getCompanyIdExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getCompanyIdExpression().setFieldSecondValue(Long.valueOf(value)));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, companyIdFromField, companyIdToField);
        VaadinUtils.addComponentStyle(companyIdFromField, "udm-company-id-from-filter");
        VaadinUtils.addComponentStyle(companyIdToField, "udm-company-id-to-filter");
        VaadinUtils.addComponentStyle(companyIdOperatorComboBox, "udm-company-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCompanyNameLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(companyNameField, companyNameOperatorComboBox);
        filterBinder.forField(companyNameField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 200), 0, 200))
            .bind(filter -> filter.getCompanyNameExpression().getFieldFirstValue(),
                (filter, value) -> filter.getCompanyNameExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(companyNameField, companyNameOperatorComboBox,
            usageFilter.getCompanyNameExpression());
        companyNameField.addValueChangeListener(event -> filterBinder.validate());
        companyNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, companyNameField, event.getValue()));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonTextFieldFormatting(horizontalLayout, companyNameField);
        VaadinUtils.addComponentStyle(companyNameField, "udm-company-name-filter");
        VaadinUtils.addComponentStyle(companyNameOperatorComboBox, "udm-company-name-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyRespondentLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(surveyRespondentField, surveyRespondentOperatorComboBox);
        filterBinder.forField(surveyRespondentField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 200), 0, 200))
            .bind(filter -> filter.getSurveyRespondentExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyRespondentExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(surveyRespondentField, surveyRespondentOperatorComboBox,
            usageFilter.getSurveyRespondentExpression());
        surveyRespondentField.addValueChangeListener(event -> filterBinder.validate());
        surveyRespondentOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, surveyRespondentField, event.getValue()));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonTextFieldFormatting(horizontalLayout, surveyRespondentField);
        VaadinUtils.addComponentStyle(surveyRespondentField, "udm-survey-respondent-filter");
        VaadinUtils.addComponentStyle(surveyRespondentOperatorComboBox, "udm-survey-respondent-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 100), 0, 100))
            .bind(filter -> filter.getSurveyCountryExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyCountryExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            usageFilter.getSurveyCountryExpression());
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, surveyCountryField, event.getValue()));
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonTextFieldFormatting(horizontalLayout, surveyCountryField);
        VaadinUtils.addComponentStyle(surveyCountryField, "udm-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "udm-survey-country-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initLanguageLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(languageField, languageOperatorComboBox);
        filterBinder.forField(languageField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 255), 0, 255))
            .bind(filter -> filter.getLanguageExpression().getFieldFirstValue(),
                (filter, value) -> filter.getLanguageExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(languageField, languageOperatorComboBox, usageFilter.getLanguageExpression());
        languageField.addValueChangeListener(event -> filterBinder.validate());
        languageOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, languageField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, languageField);
        VaadinUtils.addComponentStyle(languageField, "udm-language-filter");
        VaadinUtils.addComponentStyle(languageOperatorComboBox, "udm-language-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            // TODO {aliakh} rewrite using binder.writeBean
            if (filterBinder.isValid()) {
                populateUsageFilter();
                appliedUsageFilter = usageFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(
                    Arrays.asList(usageDateToWidget, surveyStartDateToWidget, wrWrkInstFromField, wrWrkInstToField,
                        reportedTitleField, systemTitleField, usageDetailIdField, companyIdFromField, companyIdToField,
                        companyNameField, surveyRespondentField, surveyCountryField, languageField,
                        annualMultiplierFromField, annualMultiplierToField, annualizedCopiesFromField,
                        annualizedCopiesToField, statisticalMultiplierFromField, statisticalMultiplierToField,
                        quantityFromField, quantityToField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        clearUsageFilter();
        assigneeFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        reportedPubTypeFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        publicationFormatFilterWidget.reset();
        usageDateFromWidget.clear();
        usageDateToWidget.clear();
        surveyStartDateFromWidget.clear();
        surveyStartDateToWidget.clear();
        channelComboBox.clear();
        clearOperatorLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        clearOperatorLayout(reportedTitleField, reportedTitleOperatorComboBox);
        clearOperatorLayout(systemTitleField, systemTitleOperatorComboBox);
        clearOperatorLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        clearOperatorLayout(companyIdFromField, companyIdToField, companyIdOperatorComboBox);
        clearOperatorLayout(companyNameField, companyNameOperatorComboBox);
        clearOperatorLayout(surveyRespondentField, surveyRespondentOperatorComboBox);
        clearOperatorLayout(surveyCountryField, surveyCountryOperatorComboBox);
        clearOperatorLayout(languageField, languageOperatorComboBox);
        clearOperatorLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        clearOperatorLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        clearOperatorLayout(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox);
        clearOperatorLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
    }

    private void clearUsageFilter() {
        usageFilter.setAssignees(new HashSet<>());
        usageFilter.setDetailLicenseeClasses(new HashSet<>());
        usageFilter.setReportedPubTypes(new HashSet<>());
        usageFilter.setReportedTypeOfUses(new HashSet<>());
        usageFilter.setPubFormats(new HashSet<>());
        usageFilter.setUsageDateFrom(null);
        usageFilter.setUsageDateTo(null);
        usageFilter.setSurveyStartDateFrom(null);
        usageFilter.setSurveyStartDateTo(null);
        usageFilter.setChannel(null);
        usageFilter.setWrWrkInstExpression(new FilterExpression<>());
        usageFilter.setReportedTitleExpression(new FilterExpression<>());
        usageFilter.setSystemTitleExpression(new FilterExpression<>());
        usageFilter.setUsageDetailIdExpression(new FilterExpression<>());
        usageFilter.setCompanyIdExpression(new FilterExpression<>());
        usageFilter.setCompanyNameExpression(new FilterExpression<>());
        usageFilter.setSurveyRespondentExpression(new FilterExpression<>());
        usageFilter.setSurveyCountryExpression(new FilterExpression<>());
        usageFilter.setLanguageExpression(new FilterExpression<>());
        usageFilter.setAnnualMultiplierExpression(new FilterExpression<>());
        usageFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        usageFilter.setStatisticalMultiplierExpression(new FilterExpression<>());
        usageFilter.setQuantityExpression(new FilterExpression<>());
    }

    private void populateUsageFilter() {
        usageFilter.setUsageDateFrom(usageDateFromWidget.getValue());
        usageFilter.setUsageDateTo(usageDateToWidget.getValue());
        usageFilter.setSurveyStartDateFrom(surveyStartDateFromWidget.getValue());
        usageFilter.setSurveyStartDateTo(surveyStartDateToWidget.getValue());
        usageFilter.setChannel(channelComboBox.getValue());
        usageFilter.setWrWrkInstExpression(buildNumberFilterExpression(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox, Long::valueOf));
        usageFilter.setReportedTitleExpression(buildTextFilterExpression(reportedTitleField,
            reportedTitleOperatorComboBox, Function.identity()));
        usageFilter.setSystemTitleExpression(buildTextFilterExpression(systemTitleField,
            systemTitleOperatorComboBox, Function.identity()));
        usageFilter.setUsageDetailIdExpression(buildTextFilterExpression(usageDetailIdField,
            usageDetailIdOperatorComboBox, Function.identity()));
        usageFilter.setCompanyIdExpression(buildNumberFilterExpression(companyIdFromField, companyIdToField,
            companyIdOperatorComboBox, Long::valueOf));
        usageFilter.setCompanyNameExpression(buildTextFilterExpression(companyNameField, companyNameOperatorComboBox,
            Function.identity()));
        usageFilter.setSurveyRespondentExpression(buildTextFilterExpression(surveyRespondentField,
            surveyRespondentOperatorComboBox, Function.identity()));
        usageFilter.setSurveyCountryExpression(buildTextFilterExpression(surveyCountryField,
            surveyCountryOperatorComboBox, Function.identity()));
        usageFilter.setLanguageExpression(buildTextFilterExpression(languageField, languageOperatorComboBox,
            Function.identity()));
        usageFilter.setAnnualMultiplierExpression(buildNumberFilterExpression(annualMultiplierFromField,
            annualMultiplierToField, annualMultiplierOperatorComboBox, Long::valueOf));
        usageFilter.setAnnualizedCopiesExpression(buildAmountFilterExpression(annualizedCopiesFromField,
            annualizedCopiesToField, annualizedCopiesOperatorComboBox, BigDecimal::new));
        usageFilter.setStatisticalMultiplierExpression(buildAmountFilterExpression(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox, BigDecimal::new));
        usageFilter.setQuantityExpression(buildNumberFilterExpression(quantityFromField, quantityToField,
            quantityOperatorComboBox, Long::valueOf));
    }
}
