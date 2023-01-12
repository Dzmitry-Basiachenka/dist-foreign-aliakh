package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedTypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * Window to apply additional filters for UDM baseline.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmBaselineFiltersWindow extends CommonAclFiltersWindow {

    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private static final String EQUALS = "EQUALS";

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final ComboBox<String> typeOfUseComboBox = new ComboBox<>(ForeignUi.getMessage("label.type_of_use"));
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
    private ReportedTypeOfUseFilterWidget reportedTypeOfUseFilterWidget;
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
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_baseline_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-baseline-additional-filters-window");
    }

    /**
     * @return applied UDM baseline filter.
     */
    public UdmBaselineFilter getAppliedBaselineFilter() {
        return baselineFilter;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initLicenseeClassesLayout(), initReportedTypeOfUseFilterWidget(),
            initTypeOfUseLayout(), initWrWrkInstLayout(), initSystemTitleLayout(), initUsageDetailIdLayout(),
            initSurveyCountryLayout(), initAnnualizedCopiesLayout(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        filterBinder.readBean(baselineFilter);
        filterBinder.validate();
        return rootLayout;
    }

    private DetailLicenseeClassFilterWidget initDetailLicenseeClassFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            baselineFilter.getDetailLicenseeClasses());
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                baselineFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
        return detailLicenseeClassFilterWidget;
    }

    private AggregateLicenseeClassFilterWidget initAggregateLicenseeClassFilterWidget() {
        aggregateLicenseeClassFilterWidget =
            new AggregateLicenseeClassFilterWidget(controller::getAggregateLicenseeClasses,
                baselineFilter.getAggregateLicenseeClasses());
        aggregateLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<AggregateLicenseeClass>) saveEvent ->
                baselineFilter.setAggregateLicenseeClasses(saveEvent.getSelectedItemsIds()));
        return aggregateLicenseeClassFilterWidget;
    }

    private ReportedTypeOfUseFilterWidget initReportedTypeOfUseFilterWidget() {
        reportedTypeOfUseFilterWidget = new ReportedTypeOfUseFilterWidget(controller::getReportedTypeOfUses,
            baselineFilter.getReportedTypeOfUses());
        reportedTypeOfUseFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            baselineFilter.setReportedTypeOfUses(saveEvent.getSelectedItemsIds()));
        return reportedTypeOfUseFilterWidget;
    }

    private HorizontalLayout initLicenseeClassesLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(initDetailLicenseeClassFilterWidget(), initAggregateLicenseeClassFilterWidget());
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private ComboBox<String> initTypeOfUseLayout() {
        typeOfUseComboBox.setItems(Arrays.asList("PRINT", "DIGITAL"));
        typeOfUseComboBox.setSelectedItem(baselineFilter.getTypeOfUse());
        typeOfUseComboBox.setSizeFull();
        typeOfUseComboBox.setWidth(50, Unit.PERCENTAGE);
        filterBinder.forField(typeOfUseComboBox)
            .bind(UdmBaselineFilter::getTypeOfUse, UdmBaselineFilter::setTypeOfUse);
        VaadinUtils.addComponentStyle(typeOfUseComboBox, "udm-baseline-type-of-use-filter");
        return typeOfUseComboBox;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        wrWrkInstToField.setEnabled(false);
        filterBinder.forField(wrWrkInstFromField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(wrWrkInstFromField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getWrWrkInstExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getWrWrkInstExpression()
                    .setFieldFirstValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(wrWrkInstToField)
            .withValidator(numberStringLengthValidator)
            .withValidator(new NumericValidator())
            .withValidator(getBetweenOperatorValidator(wrWrkInstToField, wrWrkInstOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateIntegerFromToValues(wrWrkInstFromField, wrWrkInstToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.wr_wrk_inst_from")))
            .bind(filter -> Objects.toString(filter.getWrWrkInstExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getWrWrkInstExpression()
                    .setFieldSecondValue(NumberUtils.createLong(StringUtils.trimToNull(value))));
        filterBinder.forField(wrWrkInstOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getWrWrkInstExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getWrWrkInstExpression().setOperator(value));
        wrWrkInstOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
        HorizontalLayout wrWrkInstLayout =
            new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox);
        applyCommonNumericFieldFormatting(wrWrkInstLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "udm-baseline-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "udm-baseline-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "udm-baseline-wr-wrk-inst-operator-filter");
        return wrWrkInstLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(filter -> Objects.toString(filter.getSystemTitleExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(systemTitleOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getSystemTitleExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getSystemTitleExpression().setOperator(value));
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "udm-baseline-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "udm-baseline-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        annualizedCopiesTo.setEnabled(false);
        filterBinder.forField(annualizedCopiesFrom)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFrom, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter ->
                    Objects.toString(filter.getAnnualizedCopiesExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getAnnualizedCopiesExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(annualizedCopiesTo)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesTo, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFrom, annualizedCopiesTo),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter ->
                    Objects.toString(filter.getAnnualizedCopiesExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getAnnualizedCopiesExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(annualizedCopiesOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getAnnualizedCopiesExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setOperator(value));
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, annualizedCopiesFrom, annualizedCopiesTo, event.getValue()));
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
        applyCommonNumericFieldFormatting(annualizedCopiesLayout, annualizedCopiesFrom, annualizedCopiesTo);
        VaadinUtils.addComponentStyle(annualizedCopiesFrom, "udm-baseline-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesTo, "udm-baseline-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox,
            "udm-baseline-annualized-copies-operator-filter");
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .bind(filter ->
                    Objects.toString(filter.getUsageDetailIdExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) ->
                    filter.getUsageDetailIdExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(usageDetailIdOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getUsageDetailIdExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getUsageDetailIdExpression().setOperator(value));
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, usageDetailIdField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, usageDetailIdField);
        VaadinUtils.addComponentStyle(usageDetailIdField, "udm-baseline-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "udm-baseline-usage-detail-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 100), 0, 100))
            .bind(filter ->
                    Objects.toString(filter.getSurveyCountryExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) ->
                    filter.getSurveyCountryExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        filterBinder.forField(surveyCountryOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getSurveyCountryExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getSurveyCountryExpression().setOperator(value));
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, surveyCountryField, event.getValue()));
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        applyCommonTextFieldFormatting(horizontalLayout, surveyCountryField);
        VaadinUtils.addComponentStyle(surveyCountryField, "udm-baseline-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "udm-baseline-survey-country-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(baselineFilter);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInstFromField, wrWrkInstToField, systemTitleField,
                    usageDetailIdField, surveyCountryField, annualizedCopiesFrom, annualizedCopiesTo));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFields());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFields() {
        baselineFilter.setDetailLicenseeClasses(new HashSet<>());
        baselineFilter.setAggregateLicenseeClasses(new HashSet<>());
        baselineFilter.setReportedTypeOfUses(new HashSet<>());
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        reportedTypeOfUseFilterWidget.reset();
        filterBinder.readBean(new UdmBaselineFilter());
    }
}
