package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ActionReasonFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AssigneeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PublicationFormatFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedPubTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedTypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.util.List;
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
public class UdmUsageFiltersWindow extends CommonAclFiltersWindow {

    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private static final String EQUALS = "EQUALS";

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
    private final ComboBox<String> typeOfUseComboBox = new ComboBox<>(ForeignUi.getMessage("label.type_of_use"));
    private final ComboBox<UdmChannelEnum> channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
    private final boolean isFilterPermittedForUser = !ForeignSecurityUtils.hasResearcherPermission();
    private final Binder<UdmUsageFilter> filterBinder = new Binder<>();
    private AssigneeFilterWidget assigneeFilterWidget;
    private ReportedPubTypeFilterWidget reportedPubTypeFilterWidget;
    private PublicationFormatFilterWidget publicationFormatFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private ReportedTypeOfUseFilterWidget reportedTypeOfUseFilterWidget;
    private ActionReasonFilterWidget actionReasonFilterWidget;
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
        usageFilter = udmUsageFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_usages_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(560, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-additional-filters-window");
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initAssigneeLicenseeClassLayout(), initReportedPubTypeReportedTypeOfUseLayout(),
            initPublicationFormatActionReasonLayout(), initUsageDateLayout(), initSurveyDateLayout(),
            initChannelTypeOfUseLayout(), initWrWrkInstLayout(), initReportedTitleLayout(), initSystemTitleLayout(),
            initUsageDetailIdLayout(), initCompanyIdLayout(), initCompanyNameLayout(), initSurveyRespondentLayout(),
            initSurveyCountryLayout(), initLanguageLayout(), initAnnualMultiplierLayout(), initAnnualizedCopiesLayout(),
            initStatisticalMultiplierLayout(), initQuantityLayout());
        filterBinder.readBean(usageFilter);
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

    private AssigneeFilterWidget initAssigneeFilterWidget() {
        assigneeFilterWidget = new AssigneeFilterWidget(controller::getAssignees, usageFilter.getAssignees());
        return assigneeFilterWidget;
    }

    private ReportedPubTypeFilterWidget initReportedPublicationTypeFilterWidget() {
        reportedPubTypeFilterWidget =
            new ReportedPubTypeFilterWidget(controller::getPublicationTypes, usageFilter.getReportedPubTypes());
        return reportedPubTypeFilterWidget;
    }

    private PublicationFormatFilterWidget initPublicationFormatFilterWidget() {
        publicationFormatFilterWidget =
            new PublicationFormatFilterWidget(controller::getPublicationFormats, usageFilter.getPubFormats());
        return publicationFormatFilterWidget;
    }

    private DetailLicenseeClassFilterWidget initDetailLicenseeClassFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            usageFilter.getDetailLicenseeClasses());
        return detailLicenseeClassFilterWidget;
    }

    private ReportedTypeOfUseFilterWidget initReportedTypeOfUseFilterWidget() {
        reportedTypeOfUseFilterWidget = new ReportedTypeOfUseFilterWidget(controller::getReportedTypeOfUses,
            usageFilter.getReportedTypeOfUses());
        return reportedTypeOfUseFilterWidget;
    }

    private ActionReasonFilterWidget initActionReasonFilterWidget() {
        actionReasonFilterWidget =
            new ActionReasonFilterWidget(controller::getAllActionReasons, usageFilter.getActionReasons());
        return actionReasonFilterWidget;
    }

    private HorizontalLayout initAssigneeLicenseeClassLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(initAssigneeFilterWidget(), initDetailLicenseeClassFilterWidget());
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private HorizontalLayout initReportedPubTypeReportedTypeOfUseLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(initReportedPublicationTypeFilterWidget(), initReportedTypeOfUseFilterWidget());
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private HorizontalLayout initPublicationFormatActionReasonLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(initPublicationFormatFilterWidget(), initActionReasonFilterWidget());
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private HorizontalLayout initChannelTypeOfUseLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initChannelComboBox(), initTypeOfUseCombobox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private ComboBox<UdmChannelEnum> initChannelComboBox() {
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setSelectedItem(usageFilter.getChannel());
        channelComboBox.setSizeFull();
        filterBinder.forField(channelComboBox)
            .bind(UdmUsageFilter::getChannel, UdmUsageFilter::setChannel);
        VaadinUtils.addComponentStyle(channelComboBox, "udm-channel-filter");
        return channelComboBox;
    }

    private ComboBox<String> initTypeOfUseCombobox() {
        typeOfUseComboBox.setItems(List.of("PRINT", "DIGITAL"));
        typeOfUseComboBox.setSelectedItem(usageFilter.getTypeOfUse());
        typeOfUseComboBox.setSizeFull();
        filterBinder.forField(typeOfUseComboBox)
            .bind(UdmUsageFilter::getTypeOfUse, UdmUsageFilter::setTypeOfUse);
        VaadinUtils.addComponentStyle(typeOfUseComboBox, "udm-type-of-use-filter");
        return typeOfUseComboBox;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            usageFilter.getWrWrkInstExpression());
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(wrWrkInstOperatorComboBox, UdmUsageFilter::getWrWrkInstExpression);
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        bindBetweenLongFields(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            UdmUsageFilter::getWrWrkInstExpression, ForeignUi.getMessage("label.wr_wrk_inst_from"), 9);
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initReportedTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(reportedTitleField, reportedTitleOperatorComboBox);
        bindStringField(reportedTitleField, UdmUsageFilter::getReportedTitleExpression, 2000);
        populateOperatorFilters(reportedTitleField, reportedTitleOperatorComboBox,
            usageFilter.getReportedTitleExpression());
        reportedTitleField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(reportedTitleOperatorComboBox, UdmUsageFilter::getReportedTitleExpression);
        reportedTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, reportedTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, reportedTitleField);
        VaadinUtils.addComponentStyle(reportedTitleField, "udm-reported-title-filter");
        VaadinUtils.addComponentStyle(reportedTitleOperatorComboBox, "udm-reported-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        bindStringField(systemTitleField, UdmUsageFilter::getSystemTitleExpression, 2000);
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            usageFilter.getSystemTitleExpression());
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(systemTitleOperatorComboBox, UdmUsageFilter::getSystemTitleExpression);
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        bindStringField(usageDetailIdField, UdmUsageFilter::getUsageDetailIdExpression, 50);
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            usageFilter.getUsageDetailIdExpression());
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(usageDetailIdOperatorComboBox, UdmUsageFilter::getUsageDetailIdExpression);
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
        filterBinder.forField(usageDateFromWidget)
            .bind(UdmUsageFilter::getUsageDateFrom, UdmUsageFilter::setUsageDateFrom);
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
        filterBinder.forField(surveyStartDateFromWidget)
            .bind(UdmUsageFilter::getSurveyStartDateFrom, UdmUsageFilter::setSurveyStartDateFrom);
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
        bindFilterOperator(annualMultiplierOperatorComboBox, UdmUsageFilter::getAnnualMultiplierExpression);
        annualMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, annualMultiplierFromField, annualMultiplierToField, event.getValue()));
        bindBetweenLongFields(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox,
            UdmUsageFilter::getAnnualMultiplierExpression, ForeignUi.getMessage("label.annual_multiplier_from"), 9);
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
        bindFilterOperator(annualizedCopiesOperatorComboBox, UdmUsageFilter::getAnnualizedCopiesExpression);
        annualizedCopiesFromField.addValueChangeListener(event -> filterBinder.validate());
        bindBetweenBigDecimalFields(annualizedCopiesFromField, annualizedCopiesToField,
            annualizedCopiesOperatorComboBox, UdmUsageFilter::getAnnualizedCopiesExpression,
            ForeignUi.getMessage("label.annualized_copies_from"));
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
        bindFilterOperator(statisticalMultiplierOperatorComboBox, UdmUsageFilter::getStatisticalMultiplierExpression);
        statisticalMultiplierOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, statisticalMultiplierFromField, statisticalMultiplierToField,
                event.getValue()));
        bindBetweenBigDecimalFields(statisticalMultiplierFromField, statisticalMultiplierToField,
            statisticalMultiplierOperatorComboBox, UdmUsageFilter::getStatisticalMultiplierExpression,
            ForeignUi.getMessage("label.statistical_multiplier_from"));
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
        bindFilterOperator(quantityOperatorComboBox, UdmUsageFilter::getQuantityExpression);
        quantityOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, quantityFromField, quantityToField, event.getValue()));
        bindBetweenLongFields(quantityFromField, quantityToField, quantityOperatorComboBox,
            UdmUsageFilter::getQuantityExpression, ForeignUi.getMessage("label.quantity_from"), 9);
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
        bindFilterOperator(companyIdOperatorComboBox, UdmUsageFilter::getCompanyIdExpression);
        companyIdOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, companyIdFromField, companyIdToField, event.getValue()));
        bindBetweenLongFields(companyIdFromField, companyIdToField, companyIdOperatorComboBox,
            UdmUsageFilter::getCompanyIdExpression, ForeignUi.getMessage("label.company_id_from"), 10);
        horizontalLayout.setEnabled(isFilterPermittedForUser);
        applyCommonNumericFieldFormatting(horizontalLayout, companyIdFromField, companyIdToField);
        VaadinUtils.addComponentStyle(companyIdFromField, "udm-company-id-from-filter");
        VaadinUtils.addComponentStyle(companyIdToField, "udm-company-id-to-filter");
        VaadinUtils.addComponentStyle(companyIdOperatorComboBox, "udm-company-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initCompanyNameLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(companyNameField, companyNameOperatorComboBox);
        bindStringField(companyNameField, UdmUsageFilter::getCompanyNameExpression, 200);
        populateOperatorFilters(companyNameField, companyNameOperatorComboBox,
            usageFilter.getCompanyNameExpression());
        companyNameField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(companyNameOperatorComboBox, UdmUsageFilter::getCompanyNameExpression);
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
        bindStringField(surveyRespondentField, UdmUsageFilter::getSurveyRespondentExpression, 200);
        populateOperatorFilters(surveyRespondentField, surveyRespondentOperatorComboBox,
            usageFilter.getSurveyRespondentExpression());
        surveyRespondentField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(surveyRespondentOperatorComboBox, UdmUsageFilter::getSurveyRespondentExpression);
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
        bindStringField(surveyCountryField, UdmUsageFilter::getSurveyCountryExpression, 100);
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            usageFilter.getSurveyCountryExpression());
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(surveyCountryOperatorComboBox, UdmUsageFilter::getSurveyCountryExpression);
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
        bindStringField(languageField, UdmUsageFilter::getLanguageExpression, 255);
        populateOperatorFilters(languageField, languageOperatorComboBox, usageFilter.getLanguageExpression());
        languageField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(languageOperatorComboBox, UdmUsageFilter::getLanguageExpression);
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
            try {
                filterBinder.writeBean(usageFilter);
                usageFilter.setAssignees(assigneeFilterWidget.getSelectedItemsIds());
                usageFilter.setReportedPubTypes(reportedPubTypeFilterWidget.getSelectedItemsIds());
                usageFilter.setPubFormats(publicationFormatFilterWidget.getSelectedItemsIds());
                usageFilter.setDetailLicenseeClasses(detailLicenseeClassFilterWidget.getSelectedItemsIds());
                usageFilter.setReportedTypeOfUses(reportedTypeOfUseFilterWidget.getSelectedItemsIds());
                usageFilter.setActionReasons(actionReasonFilterWidget.getSelectedItemsIds());
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(
                    List.of(usageDateToWidget, surveyStartDateToWidget, wrWrkInstFromField, wrWrkInstToField,
                        reportedTitleField, systemTitleField, usageDetailIdField, companyIdFromField, companyIdToField,
                        companyNameField, surveyRespondentField, surveyCountryField, languageField,
                        annualMultiplierFromField, annualMultiplierToField, annualizedCopiesFromField,
                        annualizedCopiesToField, statisticalMultiplierFromField, statisticalMultiplierToField,
                        quantityFromField, quantityToField));
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        assigneeFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        reportedPubTypeFilterWidget.reset();
        reportedTypeOfUseFilterWidget.reset();
        publicationFormatFilterWidget.reset();
        actionReasonFilterWidget.reset();
        filterBinder.readBean(new UdmUsageFilter());
    }

    private void bindStringField(TextField textField, Function<UdmUsageFilter, FilterExpression<String>> getter,
                                 Integer maxLength) {
        filterBinder.forField(textField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", maxLength),
                0, maxLength))
            .bind(filter -> getter.apply(filter).getFieldFirstValue(),
                (filter, value) -> getter.apply(filter).setFieldFirstValue(StringUtils.trimToNull(value)));
    }

    private void bindBetweenLongFields(TextField fromField, TextField toField,
                                       ComboBox<FilterOperatorEnum> operatorComboBox,
                                       Function<UdmUsageFilter, FilterExpression<Number>> getter,
                                       String fromFieldName, Integer maxLength) {
        StringLengthValidator stringLengthValidator =
            new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", maxLength), 0, maxLength);
        filterBinder.forField(fromField)
            .withValidator(stringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(fromField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(toField)
            .withValidator(stringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(toField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(fromField, toField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE, fromFieldName))
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldSecondValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
    }

    private void bindBetweenBigDecimalFields(TextField fromField, TextField toField,
                                             ComboBox<FilterOperatorEnum> operatorComboBox,
                                             Function<UdmUsageFilter, FilterExpression<Number>> getter,
                                             String fromFieldName) {
        filterBinder.forField(fromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(fromField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(toField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(toField, operatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(fromField, toField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE, fromFieldName))
            .bind(filter -> Objects.toString(getter.apply(filter).getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> getter.apply(filter)
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
    }

    private void bindFilterOperator(ComboBox<FilterOperatorEnum> operatorComboBox,
                                    Function<UdmUsageFilter, FilterExpression<?>> getter) {
        filterBinder.forField(operatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    getter.apply(filter).getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> getter.apply(filter).setOperator(value));
    }
}
