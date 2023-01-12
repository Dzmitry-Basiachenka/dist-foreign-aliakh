package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.AclUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageFilterController;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.CommonAclFiltersWindow;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.AggregateLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.DetailLicenseeClassFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PublicationTypeFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.ReportedTypeOfUseFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
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
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Window to apply additional filters for {@link AclUsageFilterWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageFiltersWindow extends CommonAclFiltersWindow {

    private static final String LENGTH_VALIDATION_MESSAGE = "field.error.length";
    private static final String BETWEEN_OPERATOR_VALIDATION_MESSAGE =
        ForeignUi.getMessage("field.error.populated_for_between_operator");
    private static final String GRATER_OR_EQUAL_VALIDATION_MESSAGE = "field.error.greater_or_equal_to";
    private static final String EQUALS = "EQUALS";
    private static final FilterOperatorEnum[] TEXT_OPERATOR_ITEMS = {
            FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL, FilterOperatorEnum.CONTAINS};
    private static final FilterOperatorEnum[] NUMERIC_OPERATOR_ITEMS = {
            FilterOperatorEnum.EQUALS, FilterOperatorEnum.DOES_NOT_EQUAL,
            FilterOperatorEnum.GREATER_THAN, FilterOperatorEnum.GREATER_THAN_OR_EQUALS_TO, FilterOperatorEnum.LESS_THAN,
            FilterOperatorEnum.LESS_THAN_OR_EQUALS_TO, FilterOperatorEnum.BETWEEN};

    private final StringLengthValidator numberStringLengthValidator =
        new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9);
    private final Binder<AclUsageFilter> filterBinder = new Binder<>();
    private final AclUsageFilter usageFilter;
    private final IAclUsageFilterController controller;
    private final ComboBox<UdmUsageOriginEnum> usageOriginComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.usage_origin"));
    private final ComboBox<UdmChannelEnum> channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
    private final ComboBox<String> typeOfUseComboBox = new ComboBox<>(ForeignUi.getMessage("label.type_of_use"));
    private final TextField usageDetailIdField = new TextField(ForeignUi.getMessage("label.usage_detail_id"));
    private final ComboBox<FilterOperatorEnum> usageDetailIdOperatorComboBox =
        buildTextOperatorComboBox(TEXT_OPERATOR_ITEMS);
    private final TextField wrWrkInstFromField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_from"));
    private final TextField wrWrkInstToField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst_to"));
    private final ComboBox<FilterOperatorEnum> wrWrkInstOperatorComboBox =
        buildNumericOperatorComboBox(NUMERIC_OPERATOR_ITEMS);
    private final TextField systemTitleField = new TextField(ForeignUi.getMessage("label.system_title"));
    private final ComboBox<FilterOperatorEnum> systemTitleOperatorComboBox =
        buildTextOperatorComboBox(TEXT_OPERATOR_ITEMS);
    private final TextField surveyCountryField = new TextField(ForeignUi.getMessage("label.survey_country"));
    private final ComboBox<FilterOperatorEnum> surveyCountryOperatorComboBox =
        buildTextOperatorComboBox(TEXT_OPERATOR_ITEMS);
    private final TextField contentUnitPriceFromField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_from"));
    private final TextField contentUnitPriceToField =
        new TextField(ForeignUi.getMessage("label.content_unit_price_to"));
    private final ComboBox<FilterOperatorEnum> contentUnitPriceOperatorComboBox =
        buildNumericOperatorComboBox(NUMERIC_OPERATOR_ITEMS);
    private final ComboBox<FilterOperatorEnum> contentUnitPriceFlagComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.content_unit_price_flag"));
    private final TextField annualizedCopiesFromField =
        new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final TextField annualizedCopiesToField = new TextField(ForeignUi.getMessage("label.annualized_copies_to"));
    private final ComboBox<FilterOperatorEnum> annualizedCopiesOperatorComboBox =
        buildNumericOperatorComboBox(NUMERIC_OPERATOR_ITEMS);

    private PeriodFilterWidget periodFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private AggregateLicenseeClassFilterWidget aggregateLicenseeClassFilterWidget;
    private PublicationTypeFilterWidget pubTypeFilterWidget;
    private ReportedTypeOfUseFilterWidget reportedTypeOfUseFilterWidget;

    /**
     * Constructor.
     *
     * @param controller     instance of {@link IAclUsageFilterController}
     * @param aclUsageFilter instance of {@link AclUsageFilter} to be displayed on window
     */
    public AclUsageFiltersWindow(IAclUsageFilterController controller, AclUsageFilter aclUsageFilter) {
        this.controller = controller;
        this.usageFilter = aclUsageFilter;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.acl_usages_additional_filters"));
        setResizable(false);
        setWidth(600, Unit.PIXELS);
        setHeight(540, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-usages-additional-filters-window");
    }

    /**
     * @return applied ACL usage filter.
     */
    public AclUsageFilter getAppliedUsageFilter() {
        return usageFilter;
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout fieldsLayout = new VerticalLayout();
        fieldsLayout.addComponents(initPeriodDetailLicenseeClassLayout(), initAggregateLicenseeClassPubTypeLayout(),
            initReportedTypeOfUseFilterWidget(), initUsageOriginChannelLayout(), initTypeOfUseComboBox(),
            initUsageDetailIdLayout(), initWrWrkInstLayout(), initSystemTitleLayout(), initSurveyCountryLayout(),
            initContentUnitPriceLayout(), initContentUnitPriceFlagComboBox(), initAnnualizedCopiesLayout());
        filterBinder.readBean(usageFilter);
        filterBinder.validate();
        return buildRootLayout(fieldsLayout);
    }

    private HorizontalLayout initPeriodDetailLicenseeClassLayout() {
        periodFilterWidget = new PeriodFilterWidget(controller::getPeriods, usageFilter.getPeriods());
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(controller::getDetailLicenseeClasses,
            usageFilter.getDetailLicenseeClasses());
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(periodFilterWidget, detailLicenseeClassFilterWidget);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private HorizontalLayout initAggregateLicenseeClassPubTypeLayout() {
        aggregateLicenseeClassFilterWidget = new AggregateLicenseeClassFilterWidget(
            controller::getAggregateLicenseeClasses, usageFilter.getAggregateLicenseeClasses());
        pubTypeFilterWidget = new PublicationTypeFilterWidget(controller::getPublicationTypes,
            usageFilter.getPubTypes());
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(aggregateLicenseeClassFilterWidget, pubTypeFilterWidget);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }

    private ReportedTypeOfUseFilterWidget initReportedTypeOfUseFilterWidget() {
        reportedTypeOfUseFilterWidget = new ReportedTypeOfUseFilterWidget(controller::getReportedTypeOfUses,
            usageFilter.getReportedTypeOfUses());
        return reportedTypeOfUseFilterWidget;
    }

    private HorizontalLayout initUsageOriginChannelLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initUsageOriginComboBox(), initChannelComboBox());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private ComboBox<UdmUsageOriginEnum> initUsageOriginComboBox() {
        filterBinder.forField(usageOriginComboBox)
            .bind(AclUsageFilter::getUsageOrigin, AclUsageFilter::setUsageOrigin);
        usageOriginComboBox.setItems(UdmUsageOriginEnum.values());
        usageOriginComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(usageOriginComboBox, "acl-usage-origin-filter");
        return usageOriginComboBox;
    }

    private ComboBox<UdmChannelEnum> initChannelComboBox() {
        filterBinder.forField(channelComboBox)
            .bind(AclUsageFilter::getChannel, AclUsageFilter::setChannel);
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(channelComboBox, "acl-usage-channel-filter");
        return channelComboBox;
    }

    private ComboBox<String> initTypeOfUseComboBox() {
        filterBinder.forField(typeOfUseComboBox)
            .bind(AclUsageFilter::getTypeOfUse, AclUsageFilter::setTypeOfUse);
        typeOfUseComboBox.setItems(Arrays.asList("PRINT", "DIGITAL"));
        typeOfUseComboBox.setSizeFull();
        typeOfUseComboBox.setWidth(50, Unit.PERCENTAGE);
        VaadinUtils.addComponentStyle(typeOfUseComboBox, "acl-usage-type-of-use-filter");
        return typeOfUseComboBox;
    }

    private HorizontalLayout initUsageDetailIdLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(usageDetailIdField, usageDetailIdOperatorComboBox);
        filterBinder.forField(usageDetailIdField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 50), 0, 50))
            .bind(filter -> filter.getUsageDetailIdExpression().getFieldFirstValue(),
                (filter, value) -> filter.getUsageDetailIdExpression()
                    .setFieldFirstValue(StringUtils.trimToNull(value)));
        populateOperatorFilters(usageDetailIdField, usageDetailIdOperatorComboBox,
            usageFilter.getUsageDetailIdExpression());
        usageDetailIdField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(usageDetailIdOperatorComboBox, AclUsageFilter::getUsageDetailIdExpression);
        usageDetailIdOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, usageDetailIdField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, usageDetailIdField);
        VaadinUtils.addComponentStyle(usageDetailIdField, "acl-usage-detail-id-filter");
        VaadinUtils.addComponentStyle(usageDetailIdOperatorComboBox, "acl-usage-detail-id-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initWrWrkInstLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(wrWrkInstFromField, wrWrkInstToField,
            wrWrkInstOperatorComboBox);
        populateOperatorFilters(wrWrkInstFromField, wrWrkInstToField, wrWrkInstOperatorComboBox,
            usageFilter.getWrWrkInstExpression());
        wrWrkInstFromField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(wrWrkInstOperatorComboBox, AclUsageFilter::getWrWrkInstExpression);
        wrWrkInstOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, wrWrkInstFromField, wrWrkInstToField, event.getValue()));
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
        applyCommonNumericFieldFormatting(horizontalLayout, wrWrkInstFromField, wrWrkInstToField);
        VaadinUtils.addComponentStyle(wrWrkInstFromField, "acl-usage-wr-wrk-inst-from-filter");
        VaadinUtils.addComponentStyle(wrWrkInstToField, "acl-usage-wr-wrk-inst-to-filter");
        VaadinUtils.addComponentStyle(wrWrkInstOperatorComboBox, "acl-usage-wr-wrk-inst-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSystemTitleLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(systemTitleField, systemTitleOperatorComboBox);
        filterBinder.forField(systemTitleField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 2000), 0, 2000))
            .bind(filter -> filter.getSystemTitleExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSystemTitleExpression().setFieldFirstValue(StringUtils.trimToNull(value)));
        populateOperatorFilters(systemTitleField, systemTitleOperatorComboBox,
            usageFilter.getSystemTitleExpression());
        systemTitleField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(systemTitleOperatorComboBox, AclUsageFilter::getSystemTitleExpression);
        systemTitleOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, systemTitleField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, systemTitleField);
        VaadinUtils.addComponentStyle(systemTitleField, "acl-usage-system-title-filter");
        VaadinUtils.addComponentStyle(systemTitleOperatorComboBox, "acl-usage-system-title-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initSurveyCountryLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(surveyCountryField, surveyCountryOperatorComboBox);
        filterBinder.forField(surveyCountryField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage(LENGTH_VALIDATION_MESSAGE, 100), 0, 100))
            .bind(filter -> filter.getSurveyCountryExpression().getFieldFirstValue(),
                (filter, value) -> filter.getSurveyCountryExpression()
                    .setFieldFirstValue(StringUtils.trimToNull(value)));
        populateOperatorFilters(surveyCountryField, surveyCountryOperatorComboBox,
            usageFilter.getSurveyCountryExpression());
        surveyCountryField.addValueChangeListener(event -> filterBinder.validate());
        bindFilterOperator(surveyCountryOperatorComboBox, AclUsageFilter::getSurveyCountryExpression);
        surveyCountryOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, surveyCountryField, event.getValue()));
        applyCommonTextFieldFormatting(horizontalLayout, surveyCountryField);
        VaadinUtils.addComponentStyle(surveyCountryField, "acl-usage-survey-country-filter");
        VaadinUtils.addComponentStyle(surveyCountryOperatorComboBox, "acl-usage-survey-country-operator-filter");
        return horizontalLayout;
    }

    private HorizontalLayout initContentUnitPriceLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceOperatorComboBox);
        populateOperatorFilters(contentUnitPriceFromField, contentUnitPriceToField, contentUnitPriceOperatorComboBox,
            usageFilter.getContentUnitPriceExpression());
        bindFilterOperator(contentUnitPriceOperatorComboBox, AclUsageFilter::getContentUnitPriceExpression);
        contentUnitPriceFromField.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(contentUnitPriceFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentUnitPriceFromField, contentUnitPriceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter ->
                    Objects.toString(filter.getContentUnitPriceExpression().getFieldFirstValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentUnitPriceExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(contentUnitPriceToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(contentUnitPriceToField, contentUnitPriceOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(contentUnitPriceFromField, contentUnitPriceToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.content_unit_price_from")))
            .bind(filter ->
                    Objects.toString(filter.getContentUnitPriceExpression().getFieldSecondValue(), StringUtils.EMPTY),
                (filter, value) -> filter.getContentUnitPriceExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(contentUnitPriceOperatorComboBox)
            .bind(filter -> ObjectUtils.defaultIfNull(
                    filter.getContentUnitPriceExpression().getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> filter.getContentUnitPriceExpression().setOperator(value));
        contentUnitPriceOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(filterBinder, contentUnitPriceFromField, contentUnitPriceToField,
                event.getValue()));
        applyCommonNumericFieldFormatting(horizontalLayout, contentUnitPriceFromField, contentUnitPriceToField);
        VaadinUtils.addComponentStyle(contentUnitPriceFromField, "acl-usage-content-unit-price-from-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceToField, "acl-usage-content-unit-price-to-filter");
        VaadinUtils.addComponentStyle(contentUnitPriceOperatorComboBox,
            "acl-usage-content-unit-price-operator-filter");
        return horizontalLayout;
    }

    private ComboBox<FilterOperatorEnum> initContentUnitPriceFlagComboBox() {
        filterBinder.forField(contentUnitPriceFlagComboBox)
            .bind(filter -> filter.getContentUnitPriceFlagExpression().getOperator(),
                (filter, value) -> filter.getContentUnitPriceFlagExpression().setOperator(value));
        populateFlagComboBox(contentUnitPriceFlagComboBox,
            usageFilter.getContentUnitPriceFlagExpression().getOperator(), "acl-usage-content-unit-price-flag-filter");
        contentUnitPriceFlagComboBox.setWidth(50, Unit.PERCENTAGE);
        return contentUnitPriceFlagComboBox;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout horizontalLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        populateOperatorFilters(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox,
            usageFilter.getAnnualizedCopiesExpression());
        bindFilterOperator(annualizedCopiesOperatorComboBox, AclUsageFilter::getAnnualizedCopiesExpression);
        annualizedCopiesFromField.addValueChangeListener(event -> filterBinder.validate());
        filterBinder.forField(annualizedCopiesFromField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesFromField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .bind(filter -> Objects.toString(filter.getAnnualizedCopiesExpression().getFieldFirstValue(),
                    StringUtils.EMPTY),
                (filter, value) -> filter.getAnnualizedCopiesExpression()
                    .setFieldFirstValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        filterBinder.forField(annualizedCopiesToField)
            .withValidator(new AmountValidator())
            .withValidator(getBetweenOperatorValidator(annualizedCopiesToField, annualizedCopiesOperatorComboBox),
                BETWEEN_OPERATOR_VALIDATION_MESSAGE)
            .withValidator(value -> validateBigDecimalFromToValues(annualizedCopiesFromField, annualizedCopiesToField),
                ForeignUi.getMessage(GRATER_OR_EQUAL_VALIDATION_MESSAGE,
                    ForeignUi.getMessage("label.annualized_copies_from")))
            .bind(filter -> Objects.toString(filter.getAnnualizedCopiesExpression().getFieldSecondValue(),
                    StringUtils.EMPTY),
                (filter, value) -> filter.getAnnualizedCopiesExpression()
                    .setFieldSecondValue(NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        annualizedCopiesOperatorComboBox.addValueChangeListener(event ->
            updateOperatorField(filterBinder, annualizedCopiesFromField, annualizedCopiesToField, event.getValue()));
        applyCommonNumericFieldFormatting(horizontalLayout, annualizedCopiesFromField, annualizedCopiesToField);
        VaadinUtils.addComponentStyle(annualizedCopiesFromField, "acl-usage-annualized-copies-from-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesToField, "acl-usage-annualized-copies-to-filter");
        VaadinUtils.addComponentStyle(annualizedCopiesOperatorComboBox, "acl-usage-annualized-copies-operator-filter");
        return horizontalLayout;
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

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            try {
                filterBinder.writeBean(usageFilter);
                usageFilter.setPeriods(periodFilterWidget.getSelectedItemsIds());
                usageFilter.setDetailLicenseeClasses(detailLicenseeClassFilterWidget.getSelectedItemsIds());
                usageFilter.setAggregateLicenseeClasses(aggregateLicenseeClassFilterWidget.getSelectedItemsIds());
                usageFilter.setPubTypes(pubTypeFilterWidget.getSelectedItemsIds());
                usageFilter.setReportedTypeOfUses(reportedTypeOfUseFilterWidget.getSelectedItemsIds());
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(usageDetailIdField, wrWrkInstFromField,
                    wrWrkInstToField, systemTitleField, surveyCountryField, contentUnitPriceFromField,
                    contentUnitPriceToField, annualizedCopiesFromField, annualizedCopiesToField));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> clearFilters());
        return new HorizontalLayout(saveButton, clearButton, closeButton);
    }

    private void clearFilters() {
        periodFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        aggregateLicenseeClassFilterWidget.reset();
        pubTypeFilterWidget.reset();
        reportedTypeOfUseFilterWidget.reset();
        filterBinder.readBean(new AclUsageFilter());
    }

    private void bindFilterOperator(ComboBox<FilterOperatorEnum> field,
                                    Function<AclUsageFilter, FilterExpression<?>> getter) {
        filterBinder.forField(field)
            .bind(filter -> ObjectUtils.defaultIfNull(
                getter.apply(filter).getOperator(), FilterOperatorEnum.valueOf(EQUALS)),
                (filter, value) -> getter.apply(filter).setOperator(value));
    }
}
