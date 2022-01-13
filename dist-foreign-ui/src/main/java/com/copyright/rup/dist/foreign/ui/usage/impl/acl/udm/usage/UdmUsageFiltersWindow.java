package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PublicationFormatFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedPubTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Window to apply additional filters for {@link UdmUsageFilterWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Ihar Suvorau
 */
public class UdmUsageFiltersWindow extends Window {

    private static final int ONE = 1;
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
        setWidth(750, Unit.PIXELS);
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
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initAssigneeFilterWidget();
        initReportedPublicationTypeFilterWidget();
        initPublicationFormatFilterWidget();
        initDetailLicenseeClassFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initAssigneeLicenseeClassLayout(), initReportedPubTypeTouLayout(),
            publicationFormatFilterWidget, initUsageDateLayout(), initSurveyDateLayout(), initChannelLayout(),
            initWrWrkInstLayout(), initReportedTitleLayout(), initSystemTitleLayout(), initUsageDetailIdLayout(),
            initCompanyIdLayout(), initCompanyNameLayout(), initSurveyCountryLayout(), initLanguageLayout(),
            initAnnualMultiplierLayout(), initAnnualizedCopiesLayout(), initStatisticalMultiplierLayout(),
            initQuantityLayout(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.validate();
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
        channelComboBox.setWidth(355, Unit.PIXELS);
        VaadinUtils.addComponentStyle(channelComboBox, "udm-channel-filter");
        return channelComboBox;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout wrWrkInstLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            UdmUsageFilter::getWrWrkInstExpression);
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(wrWrkInstFromField, wrWrkInstToField, event.getValue()));
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
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        wrWrkInstFromField.setSizeFull();
        wrWrkInstToField.setSizeFull();
        wrWrkInstLayout.setSizeFull();
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-wr-wrk-inst-operator-filter");
        return wrWrkInstLayout;
    }

    private HorizontalLayout initReportedTitleLayout() {
        HorizontalLayout reportedTitleLayout = new HorizontalLayout(reportedTitleField, reportedTitleOperatorComboBox);
        filterBinder.forField(reportedTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getReportedTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getReportedTitleExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(reportedTitleField, reportedTitleOperatorComboBox,
            UdmUsageFilter::getReportedTitleExpression);
        reportedTitleField.addValueChangeListener(event -> filterBinder.validate());
        reportedTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(reportedTitleField, event.getValue()));
        reportedTitleField.setSizeFull();
        reportedTitleLayout.setSizeFull();
        VaadinUtils.addComponentStyle(reportedTitleField, "udm-reported-title-filter");
        VaadinUtils.addComponentStyle(reportedTitleOperatorComboBox, "udm-reported-title-operator-filter");
        return reportedTitleLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout systemTitleLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            UdmUsageFilter::getSystemTitleExpression);
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(systemTitleField, event.getValue()));
        systemTitleField.setSizeFull();
        systemTitleLayout.setSizeFull();
        VaadinUtils.addComponentStyle(systemTitleField, "udm-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-system-title-operator-filter");
        return systemTitleLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout usageDetailIdLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 50), 0, 50))
            .bind(filter -> filter.getUsageDetailIdExpression().getFieldFirstValue(),
                (filter, value) -> filter.getUsageDetailIdExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            UdmUsageFilter::getUsageDetailIdExpression);
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(usageDetailIdField, event.getValue()));
        usageDetailIdField.setSizeFull();
        usageDetailIdLayout.setSizeFull();
        VaadinUtils.addComponentStyle(usageDetailIdField, "udm-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "udm-usage-detail-id-operator-filter");
        return usageDetailIdLayout;
    }

    private HorizontalLayout initUsageDateLayout() {
        HorizontalLayout usageDateLayout = new HorizontalLayout(usageDateFromWidget, usageDateToWidget);
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
        usageDateLayout.setSizeFull();
        usageDateLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(usageDateFromWidget, "udm-usage-date-from-filter");
        VaadinUtils.addComponentStyle(usageDateToWidget, "udm-usage-date-to-filter");
        return usageDateLayout;
    }

    private HorizontalLayout initSurveyDateLayout() {
        HorizontalLayout surveyDateLayout = new HorizontalLayout(surveyStartDateFromWidget, surveyStartDateToWidget);
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
        surveyDateLayout.setSizeFull();
        surveyDateLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(surveyStartDateFromWidget, "udm-survey-start-date-from-filter");
        VaadinUtils.addComponentStyle(surveyStartDateToWidget, "udm-survey-start-date-to-filter");
        return surveyDateLayout;
    }

    private HorizontalLayout initAnnualMultiplierLayout() {
        HorizontalLayout annualMultiplierLayout =
            new HorizontalLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        populateOperatorFilters(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox,
            UdmUsageFilter::getAnnualMultiplierExpression);
        annualMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        annualMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(annualMultiplierFromField, annualMultiplierToField, event.getValue()));
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
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annual_multiplier_from")))
            .bind(filter -> filter.getAnnualMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualMultiplierExpression().setFieldFirstValue(Integer.valueOf(value)));
        annualMultiplierFromField.setSizeFull();
        annualMultiplierToField.setSizeFull();
        annualMultiplierLayout.setEnabled(isFilterPermittedForUser);
        annualMultiplierLayout.setSizeFull();
        VaadinUtils.addComponentStyle(annualMultiplierFromField, "udm-annual-multiplier-from-filter");
        VaadinUtils.addComponentStyle(annualMultiplierToField, "udm-annual-multiplier-to-filter");
        VaadinUtils.addComponentStyle(annualMultiplierOperatorComboBox, "udm-annual-multiplier-operator-filter");
        return annualMultiplierLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox,
            UdmUsageFilter::getAnnualizedCopiesExpression);
        annualizedCopiesFromField.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFromField)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFromField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesToField)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesToField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFromField, annualizedCopiesToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(annualizedCopiesFromField, annualizedCopiesToField, event.getValue()));
        annualizedCopiesFromField.setSizeFull();
        annualizedCopiesToField.setSizeFull();
        annualizedCopiesLayout.setEnabled(isFilterPermittedForUser);
        annualizedCopiesLayout.setSizeFull();
        VaadinUtils.addComponentStyle(annualizedCopiesFromField, "udm-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesToField, "udm-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox, "udm-annualized-copies-operator-filter");
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initStatisticalMultiplierLayout() {
        HorizontalLayout statisticalMultiplierLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        populateOperatorFilters(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox, UdmUsageFilter::getStatisticalMultiplierExpression);
        statisticalMultiplierFromField.addValueChangeListener(event -> filterBinder.validate());
        statisticalMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(statisticalMultiplierFromField, statisticalMultiplierToField, event.getValue()));
        filterBinder.forField(statisticalMultiplierFromField)
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierFromField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(new AmountZeroValidator())
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(statisticalMultiplierToField)
            .withValidator(new AmountZeroValidator())
            .withValidator(
                value -> validateBigDecimalFromToValues(statisticalMultiplierFromField, statisticalMultiplierToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.statistical_multiplier_from")))
            .withValidator(getBetweenOperatorValidator(statisticalMultiplierToField,
                statisticalMultiplierOperatorComboBox), BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getStatisticalMultiplierExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getStatisticalMultiplierExpression()
                    .setFieldSecondValue(new BigDecimal(value)));
        statisticalMultiplierFromField.setSizeFull();
        statisticalMultiplierToField.setSizeFull();
        statisticalMultiplierLayout.setEnabled(isFilterPermittedForUser);
        statisticalMultiplierLayout.setSizeFull();
        VaadinUtils.addComponentStyle(statisticalMultiplierFromField, "udm-statistical-multiplier-from-filter");
        VaadinUtils.addComponentStyle(statisticalMultiplierToField, "udm-statistical-multiplier-to-filter");
        VaadinUtils.addComponentStyle(statisticalMultiplierOperatorComboBox,
            "udm-statistical-multiplier-operator-filter");
        return statisticalMultiplierLayout;
    }

    private HorizontalLayout initQuantityLayout() {
        HorizontalLayout quantityLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
        populateOperatorFilters(quantityFromField, quantityToField, quantityOperatorComboBox,
            UdmUsageFilter::getQuantityExpression);
        quantityFromField.addValueChangeListener(event -> filterBinder.validate());
        quantityOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(quantityFromField, quantityToField, event.getValue()));
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
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.quantity_from")))
            .bind(filter -> filter.getQuantityExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getQuantityExpression().setFieldFirstValue(Integer.valueOf(value)));
        quantityFromField.setSizeFull();
        quantityToField.setSizeFull();
        quantityLayout.setEnabled(isFilterPermittedForUser);
        quantityLayout.setSizeFull();
        VaadinUtils.addComponentStyle(quantityFromField, "udm-quantity-from-filter");
        VaadinUtils.addComponentStyle(quantityToField, "udm-quantity-to-filter");
        VaadinUtils.addComponentStyle(quantityOperatorComboBox, "udm-quantity-operator-filter");
        return quantityLayout;
    }

    private HorizontalLayout initCompanyIdLayout() {
        HorizontalLayout companyIdLayout =
            new HorizontalLayout(companyIdFromField, companyIdToField, companyIdOperatorComboBox);
        populateOperatorFilters(companyIdFromField, companyIdToField, companyIdOperatorComboBox,
            UdmUsageFilter::getCompanyIdExpression);
        companyIdFromField.addValueChangeListener(event -> filterBinder.validate());
        companyIdOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(companyIdFromField, companyIdToField, event.getValue()));
        filterBinder.forField(companyIdFromField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(companyIdFromField, companyIdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getCompanyIdExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getCompanyIdExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(companyIdToField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(getNumberValidator(), NUMBER_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(companyIdToField, companyIdOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(companyIdFromField, companyIdToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.company_id_from")))
            .bind(filter -> filter.getCompanyIdExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getCompanyIdExpression().setFieldFirstValue(Long.valueOf(value)));
        companyIdFromField.setSizeFull();
        companyIdToField.setSizeFull();
        companyIdLayout.setEnabled(isFilterPermittedForUser);
        companyIdLayout.setSizeFull();
        VaadinUtils.addComponentStyle(companyIdFromField, "udm-company-id-from-filter");
        VaadinUtils.addComponentStyle(companyIdToField, "udm-company-id-to-filter");
        VaadinUtils.addComponentStyle(companyIdOperatorComboBox, "udm-company-id-operator-filter");
        return companyIdLayout;
    }

    private HorizontalLayout initCompanyNameLayout() {
        HorizontalLayout companyNameLayout = new HorizontalLayout(companyNameField, companyNameOperatorComboBox);
        filterBinder.forField(companyNameField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 200), 0, 200))
            .bind(filter -> filter.getCompanyNameExpression().getFieldFirstValue(),
                (filter, value) -> filter.getCompanyNameExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(companyNameField, companyNameOperatorComboBox,
            UdmUsageFilter::getCompanyNameExpression);
        companyNameField.addValueChangeListener(event -> filterBinder.validate());
        companyNameOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(companyNameField, event.getValue()));
        companyNameField.setSizeFull();
        companyNameLayout.setEnabled(isFilterPermittedForUser);
        companyNameLayout.setSizeFull();
        VaadinUtils.addComponentStyle(companyNameField, "udm-company-name-filter");
        VaadinUtils.addComponentStyle(companyNameOperatorComboBox, "udm-company-name-operator-filter");
        return companyNameLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        HorizontalLayout surveyCountryLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 100), 0, 100))
            .bind(filter -> filter.getSurveyCountryExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyCountryExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            UdmUsageFilter::getSurveyCountryExpression);
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(surveyCountryField, event.getValue()));
        surveyCountryField.setSizeFull();
        surveyCountryLayout.setEnabled(isFilterPermittedForUser);
        surveyCountryLayout.setSizeFull();
        VaadinUtils.addComponentStyle(surveyCountryField, "udm-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "udm-survey-country-operator-filter");
        return surveyCountryLayout;
    }

    private HorizontalLayout initLanguageLayout() {
        HorizontalLayout languageLayout = new HorizontalLayout(languageField, languageOperatorComboBox);
        filterBinder.forField(languageField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 255), 0, 255))
            .bind(filter -> filter.getLanguageExpression().getFieldFirstValue(),
                (filter, value) -> filter.getLanguageExpression().setFieldFirstValue(value.trim()));
        populateOperatorFilters(languageField, languageOperatorComboBox,
            UdmUsageFilter::getLanguageExpression);
        languageField.addValueChangeListener(event -> filterBinder.validate());
        languageOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(languageField, event.getValue()));
        languageField.setSizeFull();
        languageLayout.setSizeFull();
        VaadinUtils.addComponentStyle(languageField, "udm-language-filter");
        VaadinUtils.addComponentStyle(languageOperatorComboBox, "udm-language-operator-filter");
        return languageLayout;
    }

    private ComboBox<FilterOperatorEnum> buildTextOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.CONTAINS, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private ComboBox<FilterOperatorEnum> buildNumericOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.LESS_THAN, FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO,
            FilterOperatorEnum.BETWEEN, FilterOperatorEnum.IS_NULL, FilterOperatorEnum.IS_NOT_NULL);
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateOperatorFilters(TextField textField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmUsageFilter, FilterExpression<?>> expressionFunction) {
        FilterExpression<?> filterExpression = expressionFunction.apply(usageFilter);
        Object fieldValue = filterExpression.getFieldFirstValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        if (Objects.nonNull(fieldValue)) {
            textField.setValue(fieldValue.toString());
            comboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            textField.setEnabled(false);
            comboBox.setSelectedItem(filterOperator);
        }
    }

    private void populateOperatorFilters(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmUsageFilter, FilterExpression<Number>> getExpressionFunction) {
        FilterExpression<Number> filterExpression = getExpressionFunction.apply(usageFilter);
        Number firstFieldValue = filterExpression.getFieldFirstValue();
        Number secondFieldValue = filterExpression.getFieldSecondValue();
        FilterOperatorEnum filterOperator = filterExpression.getOperator();
        toField.setEnabled(Objects.nonNull(secondFieldValue));
        if (Objects.nonNull(firstFieldValue)) {
            fromField.setValue(firstFieldValue.toString());
            toField.setValue(Objects.nonNull(secondFieldValue) ? secondFieldValue.toString() : StringUtils.EMPTY);
            comboBox.setSelectedItem(filterOperator);
        } else if (Objects.nonNull(filterOperator) && 0 == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(false);
            toField.setEnabled(false);
            comboBox.setSelectedItem(filterOperator);
        }
    }

    private void updateOperatorField(TextField textField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            textField.clear();
            textField.setEnabled(false);
        } else {
            textField.setEnabled(true);
        }
        filterBinder.validate();
    }

    private void updateOperatorField(TextField fromField, TextField toField, FilterOperatorEnum filterOperator) {
        if (0 == filterOperator.getArgumentsNumber()) {
            fromField.clear();
            fromField.setEnabled(false);
            toField.clear();
            toField.setEnabled(false);
        } else if (ONE == filterOperator.getArgumentsNumber()) {
            fromField.setEnabled(true);
            toField.clear();
            toField.setEnabled(false);
        } else {
            fromField.setEnabled(true);
            toField.setEnabled(true);
        }
        filterBinder.validate();
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
        surveyCountryField.clear();
        languageField.clear();
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
        usageFilter.setSurveyCountryExpression(new FilterExpression<>());
        usageFilter.setLanguageExpression(new FilterExpression<>());
        usageFilter.setAnnualMultiplierExpression(new FilterExpression<>());
        usageFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        usageFilter.setStatisticalMultiplierExpression(new FilterExpression<>());
        usageFilter.setQuantityExpression(new FilterExpression<>());
    }

    private void clearOperatorLayout(TextField textField, ComboBox<FilterOperatorEnum> comboBox) {
        textField.clear();
        comboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
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
        usageFilter.setWrWrkInstExpression(buildNumberFilterExpression(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox, Integer::valueOf));
        usageFilter.setReportedTitleExpression(buildTextFilterExpression(reportedTitleField,
            reportedTitleOperatorComboBox, Function.identity()));
        usageFilter.setSystemTitleExpression(buildTextFilterExpression(systemTitleField,
            systemTitleOperatorComboBox, Function.identity()));
        usageFilter.setUsageDetailIdExpression(buildTextFilterExpression(usageDetailIdField,
            usageDetailIdOperatorComboBox, Function.identity()));
        usageFilter.setCompanyIdExpression(buildNumberFilterExpression(companyIdFromField, companyIdToField,
            companyIdOperatorComboBox, Integer::valueOf));
        usageFilter.setCompanyNameExpression(buildTextFilterExpression(companyNameField, companyNameOperatorComboBox,
            Function.identity()));
        usageFilter.setSurveyCountryExpression(buildTextFilterExpression(surveyCountryField,
            surveyCountryOperatorComboBox, Function.identity()));
        usageFilter.setLanguageExpression(buildTextFilterExpression(languageField, languageOperatorComboBox,
            Function.identity()));
        usageFilter.setAnnualMultiplierExpression(buildNumberFilterExpression(annualMultiplierFromField,
            annualMultiplierToField, annualMultiplierOperatorComboBox, Integer::valueOf));
        usageFilter.setAnnualizedCopiesExpression(buildAmountFilterExpression(annualizedCopiesFromField,
            annualizedCopiesToField, annualizedCopiesOperatorComboBox, BigDecimal::new));
        usageFilter.setStatisticalMultiplierExpression(buildAmountFilterExpression(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox, BigDecimal::new));
        usageFilter.setQuantityExpression(buildNumberFilterExpression(quantityFromField, quantityToField,
            quantityOperatorComboBox, Integer::valueOf));
    }

    private <T> FilterExpression<T> buildTextFilterExpression(TextField textField,
                                                              ComboBox<FilterOperatorEnum> comboBox,
                                                              Function<String, T> valueConverter) {
        return buildFilterExpression(textField, comboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    private FilterExpression<Number> buildNumberFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, comboBox, valueConverter,
            field -> StringUtils.isNotEmpty(field.getValue()));
    }

    private FilterExpression<Number> buildAmountFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        return buildFilterExpression(fromField, toField, comboBox, valueConverter,
            field -> StringUtils.isNotBlank(field.getValue()));
    }

    private <T> FilterExpression<T> buildFilterExpression(TextField textField,
                                                          ComboBox<FilterOperatorEnum> comboBox,
                                                          Function<String, T> valueConverter,
                                                          Predicate<TextField> successPredicate) {
        FilterExpression<T> filterExpression = new FilterExpression<>();
        if (successPredicate.test(textField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(textField.getValue().trim()));
            filterExpression.setOperator(comboBox.getValue());
        } else if (0 == comboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(comboBox.getValue());
        }
        return filterExpression;
    }

    private FilterExpression<Number> buildFilterExpression(TextField fromField, TextField toField,
                                                           ComboBox<FilterOperatorEnum> comboBox,
                                                           Function<String, Number> valueConverter,
                                                           Predicate<TextField> successPredicate) {
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        if (successPredicate.test(fromField)) {
            filterExpression.setFieldFirstValue(valueConverter.apply(fromField.getValue().trim()));
            filterExpression.setFieldSecondValue(
                successPredicate.test(toField) ? valueConverter.apply(toField.getValue().trim()) : null);
            filterExpression.setOperator(comboBox.getValue());
        } else if (0 == comboBox.getValue().getArgumentsNumber()) {
            filterExpression.setOperator(comboBox.getValue());
        }
        return filterExpression;
    }

    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
    }

    private SerializablePredicate<String> getBetweenOperatorValidator(TextField fieldToValidate,
                                                                      ComboBox<FilterOperatorEnum> comboBox) {
        return value -> FilterOperatorEnum.BETWEEN != comboBox.getValue()
            || StringUtils.isNotBlank(fieldToValidate.getValue());
    }

    private boolean validateBigDecimalFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        AmountZeroValidator amountZeroValidator = new AmountZeroValidator();
        return StringUtils.isBlank(fromValue)
            || StringUtils.isBlank(toValue)
            || !amountZeroValidator.isValid(fromValue)
            || !amountZeroValidator.isValid(toValue)
            || 0 <= new BigDecimal(toValue.trim()).compareTo(new BigDecimal(fromValue.trim()));
    }

    private boolean validateIntegerFromToValues(TextField fromField, TextField toField) {
        String fromValue = fromField.getValue();
        String toValue = toField.getValue();
        return StringUtils.isEmpty(fromValue)
            || StringUtils.isEmpty(toValue)
            || !getNumberValidator().test(fromValue)
            || !getNumberValidator().test(toValue)
            || 0 <= Integer.valueOf(toValue.trim()).compareTo(Integer.valueOf(fromValue.trim()));
    }
}
