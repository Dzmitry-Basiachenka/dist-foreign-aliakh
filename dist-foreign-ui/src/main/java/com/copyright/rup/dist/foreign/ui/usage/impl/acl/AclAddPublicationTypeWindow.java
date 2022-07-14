package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.AclPublicationType;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.PeriodValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ScenarioParameterWidget.ParametersSaveEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Window to add ACL publication type weight.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/18/2022
 *
 * @author Aliaksandr Liakh
 */
class AclAddPublicationTypeWindow extends Window {

    private final IAclScenariosController controller;
    private final Binder<AclPublicationType> binder = new Binder<>();
    private ComboBox<PublicationType> pubTypeComboBox;
    private TextField pubTypePeriodField;
    private TextField pubTypeWeightField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenariosController}
     */
    AclAddPublicationTypeWindow(IAclScenariosController controller) {
        this.controller = controller;
        setResizable(false);
        setWidth(200, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.add_pub_type_weight"));
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout layout = new VerticalLayout(initPublicationTypeCombobox(), initPubTypePeriodLayout(),
            initPubTypeWeightField(), buttonsLayout);
        layout.setSpacing(true);
        layout.setMargin(true);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        pubTypeComboBox.focus();
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "acl-add-pub-type-weight-window");
    }

    private ComboBox<PublicationType> initPublicationTypeCombobox() {
        pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
        pubTypeComboBox.setItems(controller.getPublicationTypes());
        pubTypeComboBox.setEmptySelectionAllowed(false);
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(PublicationType::getNameAndDescription);
        binder.forField(pubTypeComboBox)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(pubType -> pubType, (pubType, value) -> {
                pubType.setId(value.getId());
                pubType.setName(value.getName());
                pubType.setDescription(value.getDescription());
            });
        pubTypeComboBox.setSizeFull();
        VaadinUtils.addComponentStyle(pubTypeComboBox, "acl-pub-type-field");
        return pubTypeComboBox;
    }

    private TextField initPubTypePeriodLayout() {
        pubTypePeriodField = new TextField(ForeignUi.getMessage("label.edit.period"));
        pubTypePeriodField.setRequiredIndicatorVisible(true);
        binder.forField(pubTypePeriodField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(value.trim()),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(value -> value.trim().length() == PeriodValidator.VALID_LENGTH,
                ForeignUi.getMessage("field.error.period_length", PeriodValidator.VALID_LENGTH))
            .withValidator(PeriodValidator::isYearValid, ForeignUi.getMessage("field.error.year_not_in_range",
                PeriodValidator.MIN_YEAR, PeriodValidator.MAX_YEAR))
            .withValidator(PeriodValidator::isMonthValid, ForeignUi.getMessage("field.error.month_invalid",
                PeriodValidator.MONTH_6, PeriodValidator.MONTH_12))
            .bind(pubType -> Objects.toString(pubType.getPeriod(), StringUtils.EMPTY),
                (usage, value) -> usage.setPeriod(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        pubTypePeriodField.setSizeFull();
        VaadinUtils.addComponentStyle(pubTypePeriodField, "acl-pub-type-period-field");
        return pubTypePeriodField;
    }

    private TextField initPubTypeWeightField() {
        pubTypeWeightField = new TextField(ForeignUi.getMessage("label.weight"));
        pubTypeWeightField.setRequiredIndicatorVisible(true);
        binder.forField(pubTypeWeightField)
            .withValidator(new RequiredValidator())
            .withValidator(new AmountZeroValidator())
            .withConverter(new StringToBigDecimalConverter(ForeignUi.getMessage("field.error.not_numeric")))
            .bind(AclPublicationType::getWeight, AclPublicationType::setWeight);
        pubTypeWeightField.setSizeFull();
        VaadinUtils.addComponentStyle(pubTypeWeightField, "acl-pub-type-weight-field");
        return pubTypeWeightField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(listener -> onConfirmButtonClicked());
        HorizontalLayout layout = new HorizontalLayout(confirmButton, Buttons.createCancelButton(this));
        layout.setSpacing(true);
        return layout;
    }

    private void onConfirmButtonClicked() {
        try {
            AclPublicationType publicationType = new AclPublicationType();
            binder.writeBean(publicationType);
            fireEvent(new ParametersSaveEvent<>(this, publicationType));
            close();
        } catch (ValidationException e) {
            Windows.showValidationErrorWindow(Arrays.asList(pubTypeComboBox, pubTypePeriodField, pubTypeWeightField));
        }
    }
}
