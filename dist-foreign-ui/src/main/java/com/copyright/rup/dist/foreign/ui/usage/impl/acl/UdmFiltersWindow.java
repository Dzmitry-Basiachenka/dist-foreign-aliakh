package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.filter.FilterOperatorEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

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
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_from"));
    private final LocalDateWidget surveyStartToWidget =
        new LocalDateWidget(ForeignUi.getMessage("label.survey_start_to"));
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
        rootLayout.addComponents(buildLabelLayout("label.assignee"), buildLabelLayout("label.reported_pub_type"),
            buildLabelLayout("label.publication_format"), buildLabelLayout("label.det_lc_name"),
            buildLabelLayout("label.channel"), initUsageDateField(), initSurveyDateField(),
            initAnnualMultiplierField(), initAnnualizedCopiesField(), initStatisticalMultiplierField(),
            initQuantityField(), typeOfUseComboBox, surveyCountryField, languageField, companyNameField, companyIdField,
            wrWrkInstField, buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    //TODO should be replaced with corresponding filter widget
    private HorizontalLayout buildLabelLayout(String name) {
        Label label = new Label("(0)");
        Button button = new Button(ForeignUi.getMessage(name));
        button.addStyleName(ValoTheme.BUTTON_LINK);
        return new HorizontalLayout(label, button);
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        return new HorizontalLayout(saveButton, closeButton);
    }

    private HorizontalLayout initAnnualMultiplierField() {
        HorizontalLayout annualMultiplierLayout =
            new HorizontalLayout(annualMultiplierFromField, annualMultiplierToField, annualMultiplierOperatorComboBox);
        annualMultiplierFromField.setSizeFull();
        annualMultiplierToField.setSizeFull();
        annualMultiplierLayout.setSizeFull();
        return annualMultiplierLayout;
    }

    private HorizontalLayout initAnnualizedCopiesField() {
        HorizontalLayout annualizedCopiesLayout =
            new HorizontalLayout(annualizedCopiesFromField, annualizedCopiesToField, annualizedCopiesOperatorComboBox);
        annualizedCopiesFromField.setSizeFull();
        annualizedCopiesToField.setSizeFull();
        annualizedCopiesLayout.setSizeFull();
        return annualizedCopiesLayout;
    }

    private HorizontalLayout initStatisticalMultiplierField() {
        HorizontalLayout statisticalMultiplierLayout = new HorizontalLayout(statisticalMultiplierFromField,
            statisticalMultiplierToField, statisticalMultiplierOperatorComboBox);
        statisticalMultiplierFromField.setSizeFull();
        statisticalMultiplierToField.setSizeFull();
        statisticalMultiplierLayout.setSizeFull();
        return statisticalMultiplierLayout;
    }

    private HorizontalLayout initQuantityField() {
        HorizontalLayout quantityLayout =
            new HorizontalLayout(quantityFromField, quantityToField, quantityOperatorComboBox);
        quantityFromField.setSizeFull();
        quantityToField.setSizeFull();
        quantityLayout.setSizeFull();
        return quantityLayout;
    }

    private HorizontalLayout initUsageDateField() {
        HorizontalLayout usageDateLayout = new HorizontalLayout(usageDateFromWidget, usageDateToWidget);
        usageDateLayout.setSpacing(true);
        usageDateLayout.setSizeFull();
        return usageDateLayout;
    }

    private HorizontalLayout initSurveyDateField() {
        HorizontalLayout surveyDateLayout = new HorizontalLayout(surveyStartFromWidget, surveyStartToWidget);
        surveyDateLayout.setSpacing(true);
        surveyDateLayout.setSizeFull();
        return surveyDateLayout;
    }

    private void applyFormattingForFields() {
        typeOfUseComboBox.setWidth(50, Unit.PERCENTAGE);
        surveyCountryField.setWidth(50, Unit.PERCENTAGE);
        languageField.setWidth(50, Unit.PERCENTAGE);
        companyNameField.setWidth(50, Unit.PERCENTAGE);
        companyIdField.setWidth(50, Unit.PERCENTAGE);
        wrWrkInstField.setWidth(50, Unit.PERCENTAGE);
    }

    private ComboBox<FilterOperatorEnum> buildOperatorComboBox() {
        ComboBox<FilterOperatorEnum> filterOperatorComboBox = new ComboBox<>(ForeignUi.getMessage("label.operator"));
        filterOperatorComboBox.setSizeFull();
        filterOperatorComboBox.setItems(FilterOperatorEnum.values());
        return filterOperatorComboBox;
    }
}
