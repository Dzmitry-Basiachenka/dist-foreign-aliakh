package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
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

import java.util.Collections;

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
    private final ComboBox<String> typeOfUseComboBox = new ComboBox<>(ForeignUi.getMessage("label.type_of_use"));

    /**
     * Constructor.
     */
    public UdmFiltersWindow() {
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.udm_additional_filters"));
        setResizable(false);
        setWidth(550, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-additional-filters-window");
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        applyFormattingForFields();
        rootLayout.addComponents(initAssigneeFilterWidget(),
            initReportedPublicationTypeFilterWidget(), initPublicationFormatFilterWidget(),
            initDetailLicenseeClassNameFilterWidget(), initComboBoxLayout(), initUsageDateLayout(),
            initSurveyDateLayout(), initAnnualMultiplierLayout(), initAnnualizedCopiesLayout(),
            initStatisticalMultiplierLayout(), initQuantityLayout(), initCompanyLayout(),
            initSurveyCountryLanguageLayout(), wrWrkInstField, buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, true, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private AssigneeFilterWidget initAssigneeFilterWidget() {
        AssigneeFilterWidget assigneeFilterWidget = new AssigneeFilterWidget(Collections::emptyList);
        assigneeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {
            //TODO save selected items to filter
        });
        return assigneeFilterWidget;
    }

    private ReportedPubTypeFilterWidget initReportedPublicationTypeFilterWidget() {
        ReportedPubTypeFilterWidget reportedPublicationTypeFilterWidget =
            new ReportedPubTypeFilterWidget(Collections::emptyList);
        reportedPublicationTypeFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {
            //TODO save selected items to filter
        });
        return reportedPublicationTypeFilterWidget;
    }

    private PublicationFormatFilterWidget initPublicationFormatFilterWidget() {
        PublicationFormatFilterWidget publicationFormatFilterWidget =
            new PublicationFormatFilterWidget(Collections::emptyList);
        publicationFormatFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {
            //TODO save selected items to filter
        });
        return publicationFormatFilterWidget;
    }

    private DetailLicenseeClassFilterWidget initDetailLicenseeClassNameFilterWidget() {
        DetailLicenseeClassFilterWidget detailLicenseeClassNameFilterWidget =
            new DetailLicenseeClassFilterWidget(Collections::emptyList);
        detailLicenseeClassNameFilterWidget.addFilterSaveListener((IFilterSaveListener<String>) saveEvent -> {
            //TODO save selected items to filter
        });
        return detailLicenseeClassNameFilterWidget;
    }

    private HorizontalLayout initComboBoxLayout() {
        HorizontalLayout comboBoxLayout = new HorizontalLayout(channelComboBox, typeOfUseComboBox);
        channelComboBox.setSizeFull();
        typeOfUseComboBox.setSizeFull();
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
        annualMultiplierFromField.setSizeFull();
        annualMultiplierToField.setSizeFull();
        annualMultiplierLayout.setSizeFull();
        return annualMultiplierLayout;
    }

    private HorizontalLayout initAnnualizedCopiesLayout() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        annualizedCopiesFromField.setSizeFull();
        annualizedCopiesToField.setSizeFull();
        annualizedCopiesLayout.setSizeFull();
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initStatisticalMultiplierLayout() {
        HorizontalLayout statisticalMultiplierLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        statisticalMultiplierFromField.setSizeFull();
        statisticalMultiplierToField.setSizeFull();
        statisticalMultiplierLayout.setSizeFull();
        return statisticalMultiplierLayout;
    }

    private HorizontalLayout initQuantityLayout() {
        HorizontalLayout quantityLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
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

    private void applyFormattingForFields() {
        channelComboBox.setItems(UdmChannelEnum.values());
        channelComboBox.setWidth(50, Unit.PERCENTAGE);
        typeOfUseComboBox.setWidth(50, Unit.PERCENTAGE);
        wrWrkInstField.setWidth(50, Unit.PERCENTAGE);
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.values());
        return filterOperatorComboBox;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        return new HorizontalLayout(saveButton, closeButton);
    }
}
