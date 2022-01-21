package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.CommonUdmFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.TypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

/**
 * Window to apply additional filters for UDM baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFiltersWindow extends CommonUdmFiltersWindow {

    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String NOT_NUMERIC_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox = buildTextOperatorComboBox();
    private final TextField surveyCountryField = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox = buildTextOperatorComboBox();
    private final TextField usageDetailIdField = new TextField(ForeignUi.getMessage("label.usage_detail_id"));
    private final ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox = buildTextOperatorComboBox();
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox = buildNumericOperatorComboBox();
    private final TextField annualizedCopiesFrom = new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesTo = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox = buildNumericOperatorComboBox();
    private final Binder<UdmBaselineFilter> filterBinder = new Binder<>();
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;
    private UdmBaselineFilter appliedBaselineFilter;
    private final UdmBaselineFilter baselineFilter;
    private final IUdmBaselineFilterController controller;

    /**
     * Constructor.
     *
     * @param controller        instance of {@link IUdmBaselineFilterController}
     * @param udmBaselineFilter instance of {@link UdmBaselineFilter} to be displayed on window
     */
    public UdmBaselineFiltersWindow(IUdmBaselineFilterController controller, UdmBaselineFilter udmBaselineFilter) {
        this.controller = controller;
        baselineFilter = new UdmBaselineFilter(udmBaselineFilter);
        appliedBaselineFilter = udmBaselineFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_baseline_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(355, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-baseline-additional-filters-window");
    }

    /**
     * @return applied UDM baseline filter.
     */
    public UdmBaselineFilter getAppliedBaselineFilter() {
        return appliedBaselineFilter;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initDetailLicenseeClassFilterWidget();
        initAggregateLicenseeClassFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initLicenseeClassesLayout(), typeOfUseFilterWidget, initWrWrkInstLayout(),
            initSystemTitleLayout(), initUsageDetailIdLayout(), initSurveyCountryLayout(), initAnnualizedCopiesLayout(),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.validate();
        return rootLayout;
    }

    private void initDetailLicenseeClassFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            baselineFilter.getDetailLicenseeClasses());
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                baselineFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initAggregateLicenseeClassFilterWidget() {
        aggregateLicenseeClassFilterWidget =
            new AggregateLicenseeClassFilterWidget(controller::getAggregateLicenseeClasses,
                baselineFilter.getAggregateLicenseeClasses());
        aggregateLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<AggregateLicenseeClass>) saveEvent ->
                baselineFilter.setAggregateLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget =
            new TypeOfUseFilterWidget(controller::getTypeOfUses, baselineFilter.getReportedTypeOfUses());
        typeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            baselineFilter.setReportedTypeOfUses(saveEvent.getSelectedItemsIds()));
    }

    private HorizontalLayout initLicenseeClassesLayout() {
        HorizontalLayout licenseeClassesLayout =
            new HorizontalLayout(detailLicenseeClassFilterWidget, aggregateLicenseeClassFilterWidget);
        licenseeClassesLayout.setSizeFull();
        licenseeClassesLayout.setSpacing(true);
        return licenseeClassesLayout;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout wrWrkInstLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            baselineFilter.getWrWrkInstExpression());
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NOT_NUMERIC_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getWrWrkInstExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldFirstValue(Long.valueOf(value)));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NOT_NUMERIC_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> filter.getWrWrkInstExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getWrWrkInstExpression().setFieldSecondValue(Long.valueOf(value)));
        applyCommonNumericFieldFormatting(wrWrkInstLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-baseline-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-baseline-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-baseline-wr-wrk-inst-operator-filter");
        return wrWrkInstLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            baselineFilter.getSystemTitleExpression());
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(value.trim()));
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-baseline-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-baseline-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox,
            baselineFilter.getAnnualizedCopiesExpression());
        annualizedCopiesFrom.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFrom)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFrom, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesTo)
            .withValidator(new AmountZeroValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesTo, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFrom, annualizedCopiesTo),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, annualizedCopiesFrom, annualizedCopiesTo, event.getValue()));
        applyCommonNumericFieldFormatting(annualizedCopiesLayout, annualizedCopiesFrom, annualizedCopiesTo);
        VaadinUtils.addComponentStyle(annualizedCopiesFrom, "udm-baseline-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesTo, "udm-baseline-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox,
            "udm-baseline-annualized-copies-operator-filter");
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            baselineFilter.getUsageDetailIdExpression());
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .bind(filter -> filter.getUsageDetailIdExpression().getFieldFirstValue(),
                (filter, value) -> filter.getUsageDetailIdExpression().setFieldFirstValue(value.trim()));
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, usageDetailIdField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, usageDetailIdField);
        VaadinUtils.addComponentStyle(usageDetailIdField, "udm-baseline-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "udm-baseline-usage-detail-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            baselineFilter.getSurveyCountryExpression());
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 100), 0, 100))
            .bind(filter -> filter.getSurveyCountryExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyCountryExpression().setFieldFirstValue(value.trim()));
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, surveyCountryField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, surveyCountryField);
        VaadinUtils.addComponentStyle(surveyCountryField, "udm-baseline-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "udm-baseline-survey-country-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (filterBinder.isValid()) {
                populateBaselineFilter();
                appliedBaselineFilter = baselineFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstFromField, wrWrkInstToField, systemTitleField,
                    usageDetailIdField, surveyCountryField, annualizedCopiesFrom, annualizedCopiesTo));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void populateBaselineFilter() {
        baselineFilter.setWrWrkInstExpression(buildNumberFilterExpression(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox, Integer::valueOf));
        baselineFilter.setSystemTitleExpression(buildTextFilterExpression(systemTitleField, systemTitleOperatorComboBox,
            Function.identity()));
        baselineFilter.setUsageDetailIdExpression(buildTextFilterExpression(usageDetailIdField,
            usageDetailIdOperatorComboBox, Function.identity()));
        baselineFilter.setSurveyCountryExpression(buildTextFilterExpression(surveyCountryField,
            surveyCountryOperatorComboBox, Function.identity()));
        baselineFilter.setAnnualizedCopiesExpression(buildAmountFilterExpression(annualizedCopiesFrom,
            annualizedCopiesTo, annualizedCopiesOperatorComboBox, BigDecimal::new));
    }

    private void clearFilters() {
        baselineFilter.setDetailLicenseeClasses(new HashSet<>());
        baselineFilter.setAggregateLicenseeClasses(new HashSet<>());
        baselineFilter.setReportedTypeOfUses(new HashSet<>());
        baselineFilter.setWrWrkInstExpression(new FilterExpression<>());
        baselineFilter.setSystemTitleExpression(new FilterExpression<>());
        baselineFilter.setUsageDetailIdExpression(new FilterExpression<>());
        baselineFilter.setSurveyCountryExpression(new FilterExpression<>());
        baselineFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        clearOperatorLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        clearOperatorLayout(systemTitleField, systemTitleOperatorComboBox);
        clearOperatorLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        clearOperatorLayout(surveyCountryField, surveyCountryOperatorComboBox);
        clearOperatorLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
    }
}
