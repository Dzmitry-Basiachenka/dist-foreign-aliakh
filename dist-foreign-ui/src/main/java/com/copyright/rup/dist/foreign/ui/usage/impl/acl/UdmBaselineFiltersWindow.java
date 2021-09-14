package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmBaselineFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineFilterController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

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
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
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
public class UdmBaselineFiltersWindow extends Window {

    private static final String AMOUNT_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.positive_number_and_length", 10);
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String NOT_NUMERIC_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final TextField systemTitle = new TextField(ForeignUi.getMessage("label.system_title"));
    private final TextField surveyCountry = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final TextField usageDetailId = new TextField(ForeignUi.getMessage("label.usage_detail_id"));
    private final TextField wrWrkInst = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final TextField annualizedCopiesFrom = new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesTo = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox = buildOperatorComboBox();
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
        setWidth(550, Unit.PIXELS);
        setHeight(272, Unit.PIXELS);
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
        buttonsLayout.setMargin(new MarginInfo(true, false, false, false));
        initDetailLicenseeClassFilterWidget();
        initAggregateLicenseeClassFilterWidget();
        initTypeOfUseFilterWidget();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initLicenseeClassesLayout(), typeOfUseFilterWidget, initWrWrkInstSystemTitleLayout(),
            initUsageDetailIdSurveyCountryLayout(), initAnnualizedCopiesLayout(), buttonsLayout);
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

    private HorizontalLayout initWrWrkInstSystemTitleLayout() {
        HorizontalLayout layout = new HorizontalLayout(wrWrkInst, systemTitle);
        wrWrkInst.setValue(Objects.nonNull(baselineFilter.getWrWrkInst())
            ? baselineFilter.getWrWrkInst().toString() : StringUtils.EMPTY);
        systemTitle.setValue(ObjectUtils.defaultIfNull(baselineFilter.getSystemTitle(), StringUtils.EMPTY));
        filterBinder.forField(wrWrkInst)
            .withValidator(numberStringLengthValidator)
            .withValidator(getNumberValidator(), NOT_NUMERIC_VALIDATION_MESSAGE)
            .withConverter(new StringToLongConverter(NOT_NUMERIC_VALIDATION_MESSAGE))
            .bind(UdmBaselineFilter::getWrWrkInst, UdmBaselineFilter::setWrWrkInst);
        filterBinder.forField(systemTitle)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 2000), 0, 2000))
            .bind(UdmBaselineFilter::getSystemTitle, UdmBaselineFilter::setSystemTitle);
        wrWrkInst.setSizeFull();
        systemTitle.setSizeFull();
        layout.setSizeFull();
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(wrWrkInst, "udm-baseline-wr-wrk-inst-filter");
        VaadinUtils.addComponentStyle(systemTitle, "udm-baseline-system-title-filter");
        return layout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFrom, annualizedCopiesTo, annualizedCopiesOperatorComboBox,
            UdmBaselineFilter::getAnnualizedCopiesExpression);
        annualizedCopiesFrom.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFrom)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFrom, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldFirstValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldFirstValue(new BigDecimal(value)));
        filterBinder.forField(annualizedCopiesTo)
            .withValidator(getAmountValidator(), AMOUNT_VALIDATION_MESSAGE)
            .withValidator(getBetweenOperatorValidator(annualizedCopiesTo, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFrom, annualizedCopiesTo),
                ForeignUi.getMessage("field.error.greater_or_equal_to",
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> filter.getAnnualizedCopiesExpression().getFieldSecondValue().toString(),
                (filter, value) -> filter.getAnnualizedCopiesExpression().setFieldSecondValue(new BigDecimal(value)));
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualizedCopiesTo, event.getValue()));
        annualizedCopiesFrom.setSizeFull();
        annualizedCopiesTo.setSizeFull();
        annualizedCopiesLayout.setSizeFull();
        VaadinUtils.addComponentStyle(annualizedCopiesFrom, "udm-baseline-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesTo, "udm-baseline-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox,
            "udm-baseline-annualized-copies-operator-filter");
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initUsageDetailIdSurveyCountryLayout() {
        HorizontalLayout layout = new HorizontalLayout(usageDetailId, surveyCountry);
        usageDetailId.setValue(ObjectUtils.defaultIfNull(baselineFilter.getUsageDetailId(), StringUtils.EMPTY));
        surveyCountry.setValue(ObjectUtils.defaultIfNull(baselineFilter.getSurveyCountry(), StringUtils.EMPTY));
        filterBinder.forField(usageDetailId)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .bind(UdmBaselineFilter::getUsageDetailId, UdmBaselineFilter::setUsageDetailId);
        filterBinder.forField(surveyCountry)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 100), 0, 100))
            .bind(UdmBaselineFilter::getSurveyCountry, UdmBaselineFilter::setSurveyCountry);
        usageDetailId.setSizeFull();
        surveyCountry.setSizeFull();
        layout.setSizeFull();
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(usageDetailId, "udm-baseline-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(surveyCountry, "udm-baseline-survey-country-filter");
        return layout;
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.values());
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        return filterOperatorComboBox;
    }

    private void populateOperatorFilters(TextField fromField, TextField toField, ComboBox<FilterOperatorEnum> comboBox,
                                         Function<UdmBaselineFilter, FilterExpression<Number>> getExpressionFunction) {
        FilterExpression<Number> filterExpression = getExpressionFunction.apply(baselineFilter);
        Number firstFieldValue = filterExpression.getFieldFirstValue();
        Number secondFieldValue = filterExpression.getFieldSecondValue();
        toField.setEnabled(Objects.nonNull(secondFieldValue));
        if (Objects.nonNull(firstFieldValue)) {
            FilterOperatorEnum filterOperator = filterExpression.getOperator();
            fromField.setValue(firstFieldValue.toString());
            toField.setValue(Objects.nonNull(secondFieldValue) ? secondFieldValue.toString() : StringUtils.EMPTY);
            comboBox.setSelectedItem(filterOperator);
        }
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
                baselineFilter.setWrWrkInst(getLongFromTextField(wrWrkInst));
                baselineFilter.setSystemTitle(getStringFromTextField(systemTitle));
                baselineFilter.setUsageDetailId(getStringFromTextField(usageDetailId));
                baselineFilter.setSurveyCountry(getStringFromTextField(surveyCountry));
                baselineFilter.setAnnualizedCopiesExpression(buildNumberFilterExpression(annualizedCopiesFrom,
                    annualizedCopiesTo, annualizedCopiesOperatorComboBox, BigDecimal::new));
                appliedBaselineFilter = baselineFilter;
                close();
            } else {
                Windows.showValidationErrorWindow(Arrays.asList(wrWrkInst, systemTitle, usageDetailId,
                    surveyCountry, annualizedCopiesFrom, annualizedCopiesTo));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        baselineFilter.setDetailLicenseeClasses(new HashSet<>());
        baselineFilter.setAggregateLicenseeClasses(new HashSet<>());
        baselineFilter.setReportedTypeOfUses(new HashSet<>());
        baselineFilter.setWrWrkInst(null);
        baselineFilter.setSystemTitle(null);
        baselineFilter.setUsageDetailId(null);
        baselineFilter.setSurveyCountry(null);
        baselineFilter.setAnnualizedCopiesExpression(new FilterExpression<>());
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        wrWrkInst.clear();
        systemTitle.clear();
        usageDetailId.clear();
        surveyCountry.clear();
        annualizedCopiesFrom.clear();
        annualizedCopiesTo.clear();
        annualizedCopiesTo.setEnabled(false);
        annualizedCopiesOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
    }

    //TODO analyze does it make sense to move converters and validators to separate Utils class
    private Long getLongFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? Long.valueOf(textField.getValue().trim()) : null;
    }

    private String getStringFromTextField(TextField textField) {
        return StringUtils.isNotEmpty(textField.getValue()) ? textField.getValue().trim() : null;
    }

    private FilterExpression<Number> buildNumberFilterExpression(TextField fromField, TextField toField,
                                                                 ComboBox<FilterOperatorEnum> comboBox,
                                                                 Function<String, Number> valueConverter) {
        FilterExpression<Number> filterExpression = new FilterExpression<>();
        if (StringUtils.isNotEmpty(fromField.getValue())) {
            filterExpression.setFieldFirstValue(valueConverter.apply(fromField.getValue().trim()));
            filterExpression.setFieldSecondValue(
                StringUtils.isNotEmpty(toField.getValue()) ? valueConverter.apply(toField.getValue().trim()) : null);
            filterExpression.setOperator(comboBox.getValue());
        }
        return filterExpression;
    }

    private SerializablePredicate<String> getAmountValidator() {
        return value -> null == value
            || StringUtils.isEmpty(value)
            || StringUtils.isNotBlank(value) && new AmountValidator(false).isValid(value.trim());
    }

    private SerializablePredicate<String> getNumberValidator() {
        return value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim());
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
            || 0 <= new BigDecimal(toValue.trim()).compareTo(new BigDecimal(fromValue.trim()));
    }
}
