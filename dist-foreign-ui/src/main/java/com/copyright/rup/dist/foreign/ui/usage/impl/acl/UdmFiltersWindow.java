package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterExpression;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmUsageFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

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
import java.util.Collections;
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

    private final TextField annualMultiplierFromField =
        new TextField(ForeignUi.getMessage("label.annual_multiplier_from"));
    private final TextField annualMultiplierToField = new TextField(ForeignUi.getMessage("label.annual_multiplier_to"));
    private final TextField annualizedCopiesFromField =
        new TextField(ForeignUi.getMessage("label.annualized_copies_from"));
    private final ComboBox<FilterOperatorEnum> annualMultiplierOperatorComboBox = buildOperatorComboBox();
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
    private final LocalDateWidget surveyStartFromWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_from"));
    private final LocalDateWidget surveyStartToWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_date_to"));
    private final ComboBox<UdmChannelEnum> channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
    private AssigneeFilterWidget assigneeFilterWidget;
    private ReportedPubTypeFilterWidget reportedPubTypeFilterWidget;
    private PublicationFormatFilterWidget publicationFormatFilterWidget;
    private DetailLicenseeClassFilterWidget detailLicenseeClassFilterWidget;
    private TypeOfUseFilterWidget typeOfUseFilterWidget;
    private UdmUsageFilter usageFilter;
    private UdmUsageFilter appliedUsageFilter;

    /**
     * Constructor.
     */
    public UdmFiltersWindow() {
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
        usageFilter = appliedUsageFilter;
        assigneeFilterWidget.reset();
        reportedPubTypeFilterWidget.reset();
        publicationFormatFilterWidget.reset();
        detailLicenseeClassFilterWidget.reset();
        typeOfUseFilterWidget.reset();
        usageDateFromWidget.clear();
        usageDateToWidget.clear();
        surveyStartFromWidget.clear();
        surveyStartToWidget.clear();
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
        return rootLayout;
    }

    private void initAssigneeFilterWidget() {
        assigneeFilterWidget = new AssigneeFilterWidget(Collections::emptyList);
        assigneeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setAssignees(saveEvent.getSelectedItemsIds()));
    }

    private void initReportedPublicationTypeFilterWidget() {
        reportedPubTypeFilterWidget = new ReportedPubTypeFilterWidget(Collections::emptyList);
        reportedPubTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent ->
            usageFilter.setReportedPubTypes(saveEvent.getSelectedItemsIds()));
    }

    private void initPublicationFormatFilterWidget() {
        publicationFormatFilterWidget = new PublicationFormatFilterWidget(Collections::emptyList);
        publicationFormatFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<String>) saveEvent -> usageFilter.setPubFormats(saveEvent.getSelectedItemsIds()));
    }

    private void initDetailLicenseeClassNameFilterWidget() {
        detailLicenseeClassFilterWidget = new DetailLicenseeClassFilterWidget(Collections::emptyList);
        detailLicenseeClassFilterWidget.addFilterSaveListener(
            (IFilterSaveListener<DetailLicenseeClass>) saveEvent ->
                usageFilter.setDetailLicenseeClasses(saveEvent.getSelectedItemsIds()));
    }

    private void initTypeOfUseFilterWidget() {
        typeOfUseFilterWidget = new TypeOfUseFilterWidget(Collections::emptyList);
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
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setSizeFull();
        wrWrkInstField.setSizeFull();
        comboBoxLayout.setSizeFull();
        comboBoxLayout.setSpacing(true);
        return comboBoxLayout;
    }

    private HorizontalLayout initUsageDateLayout() {
        HorizontalLayout usageDateLayout = new HorizontalLayout(usageDateFromWidget, usageDateToWidget);
        usageDateLayout.setSizeFull();
        usageDateLayout.setSpacing(true);
        return usageDateLayout;
    }

    private HorizontalLayout initSurveyDateLayout() {
        HorizontalLayout surveyDateLayout = new HorizontalLayout(surveyStartFromWidget, surveyStartToWidget);
        surveyDateLayout.setSizeFull();
        surveyDateLayout.setSpacing(true);
        return surveyDateLayout;
    }

    private HorizontalLayout initAnnualMultiplierLayout() {
        HorizontalLayout annualMultiplierLayout =
            new HorizontalLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        annualMultiplierOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualMultiplierToField, event.getValue()));
        annualMultiplierToField.setEnabled(false);
        annualMultiplierFromField.setSizeFull();
        annualMultiplierToField.setSizeFull();
        annualMultiplierLayout.setSizeFull();
        return annualMultiplierLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        annualizedCopiesOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(annualizedCopiesToField, event.getValue()));
        annualizedCopiesToField.setEnabled(false);
        annualizedCopiesFromField.setSizeFull();
        annualizedCopiesToField.setSizeFull();
        annualizedCopiesLayout.setSizeFull();
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initStatisticalMultiplierLayout() {
        HorizontalLayout statisticalMultiplierLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        statisticalMultiplierOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(statisticalMultiplierToField, event.getValue()));
        statisticalMultiplierToField.setEnabled(false);
        statisticalMultiplierFromField.setSizeFull();
        statisticalMultiplierToField.setSizeFull();
        statisticalMultiplierLayout.setSizeFull();
        return statisticalMultiplierLayout;
    }

    private HorizontalLayout initQuantityLayout() {
        HorizontalLayout quantityLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
        quantityOperatorComboBox.addValueChangeListener(
            event -> updateOperatorField(quantityToField, event.getValue()));
        quantityToField.setEnabled(false);
        quantityFromField.setSizeFull();
        quantityToField.setSizeFull();
        quantityLayout.setSizeFull();
        return quantityLayout;
    }

    private HorizontalLayout initCompanyLayout() {
        HorizontalLayout companyLayout = new HorizontalLayout(companyIdField, companyNameField);
        companyIdField.setSizeFull();
        companyNameField.setSizeFull();
        companyLayout.setSizeFull();
        companyLayout.setSpacing(true);
        return companyLayout;
    }

    private HorizontalLayout initSurveyCountryLanguageLayout() {
        HorizontalLayout surveyCountryLanguageLayout = new HorizontalLayout(surveyCountryField, languageField);
        surveyCountryField.setSizeFull();
        languageField.setSizeFull();
        surveyCountryLanguageLayout.setSizeFull();
        surveyCountryLanguageLayout.setSpacing(true);
        return surveyCountryLanguageLayout;
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setSelectedItem(FilterOperatorEnum.EQUALS);
        filterOperatorComboBox.setEmptySelectionAllowed(false);
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.values());
        return filterOperatorComboBox;
    }

    private void updateOperatorField(TextField fieldToUpdate, FilterOperatorEnum value) {
        if (FilterOperatorEnum.BETWEEN == value) {
            fieldToUpdate.setEnabled(true);
        } else {
            fieldToUpdate.clear();
            fieldToUpdate.setEnabled(false);
        }
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            populateFilters();
            appliedUsageFilter = usageFilter;
            close();
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

    //TODO {isuvorau} cover by tests after all test data will be populated
    private void populateFilters() {
        usageFilter.setUsageDateFrom(usageDateFromWidget.getValue());
        usageFilter.setUsageDateTo(usageDateToWidget.getValue());
        usageFilter.setSurveyStartDateFrom(surveyStartFromWidget.getValue());
        usageFilter.setSurveyStartDateTo(surveyStartToWidget.getValue());
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
}
