package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.AmountZeroValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.PeriodValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Window to edit ACL Usage.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/11/2022
 *
 * @author Dzmitry Basiachenka
 */
public class EditAclUsageWindow extends Window {

    private static final String NUMBER_VALIDATION_MESSAGE = ForeignUi.getMessage("field.error.not_numeric");

    private final TextField periodField = new TextField(ForeignUi.getMessage("label.edit.period"));
    private final TextField wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
    private final ComboBox<PublicationType> pubTypeComboBox = new ComboBox<>(ForeignUi.getMessage("label.pub_type"));
    private final ComboBox<String> typeOfUseComboBox = new ComboBox<>(ForeignUi.getMessage("label.tou"));
    private final TextField annualizedCopiesField = new TextField(ForeignUi.getMessage("label.annualized_copies"));
    private final TextField contentUnitPriceField = new TextField(ForeignUi.getMessage("label.content_unit_price"));
    private final ComboBox<DetailLicenseeClass> detailLicenseeClassComboBox
        = new ComboBox<>(ForeignUi.getMessage("label.det_lc"));
    private final Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
    private final Binder<AclUsageDto> binder = new Binder<>();
    private final IAclUsageController controller;
    private final Set<AclUsageDto> selectedUsages;
    private final ClickListener saveButtonClickListener;
    private final AclUsageDto bindedUsageDto;

    /**
     * Constructor.
     *
     * @param controller     instance of {@link IAclUsageController}
     * @param selectedUsages selected ACL usages for edit
     * @param clickListener  action that should be performed after Save button was clicked
     */
    public EditAclUsageWindow(IAclUsageController controller, Set<AclUsageDto> selectedUsages,
                              ClickListener clickListener) {
        this.controller = controller;
        this.selectedUsages = selectedUsages;
        this.saveButtonClickListener = clickListener;
        bindedUsageDto = new AclUsageDto();
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_acl_usages"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(300, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-acl-usages-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        rootLayout.addComponents(buildPeriodLayout(), buildWrWrkInstLayout(), buildPubTypeLayout(),
            buildTypeOfUseLayout(), buildDetailLicenseeLayout(), buildAnnualizedCopiesLayout(),
            buildContentUnitPriceLayout(), buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        binder.validate();
        binder.addValueChangeListener(event -> saveButton.setEnabled(binder.hasChanges()));
        return rootLayout;
    }

    private HorizontalLayout buildPeriodLayout() {
        periodField.setSizeFull();
        binder.forField(periodField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(value -> StringUtils.isEmpty(value) || value.trim().length() == PeriodValidator.VALID_LENGTH,
                ForeignUi.getMessage("field.error.period_length", PeriodValidator.VALID_LENGTH))
            .withValidator(value -> StringUtils.isEmpty(value) || PeriodValidator.isYearValid(value),
                ForeignUi.getMessage("field.error.year_not_in_range",
                    PeriodValidator.MIN_YEAR, PeriodValidator.MAX_YEAR))
            .withValidator(value -> StringUtils.isEmpty(value) || PeriodValidator.isMonthValid(value),
                ForeignUi.getMessage("field.error.month_invalid",
                    PeriodValidator.MONTH_6, PeriodValidator.MONTH_12))
            .bind(usage -> Objects.toString(usage.getPeriod(), StringUtils.EMPTY),
                (usage, value) -> usage.setPeriod(NumberUtils.createInteger(StringUtils.trimToNull(value))));
        periodField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(periodField, "acl-usage-edit-period-field");
        return buildCommonLayout(periodField, "label.edit.period");
    }

    private HorizontalLayout buildWrWrkInstLayout() {
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                NUMBER_VALIDATION_MESSAGE)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "acl-usage-edit-wr-wrk-inst-field");
        return buildCommonLayout(wrWrkInstField, "label.wr_wrk_inst");
    }

    private HorizontalLayout buildPubTypeLayout() {
        pubTypeComboBox.setSizeFull();
        pubTypeComboBox.setItems(controller.getPublicationTypes());
        pubTypeComboBox.setPageLength(12);
        pubTypeComboBox.setItemCaptionGenerator(value -> Objects.nonNull(value.getName())
            ? value.getNameAndDescription()
            : StringUtils.EMPTY);
        binder.forField(pubTypeComboBox).bind(AclUsageDto::getPublicationType, AclUsageDto::setPublicationType);
        VaadinUtils.addComponentStyle(pubTypeComboBox, "acl-usage-edit-pub-type-combo-box");
        return buildCommonLayout(pubTypeComboBox, "label.pub_type");
    }

    private HorizontalLayout buildTypeOfUseLayout() {
        typeOfUseComboBox.setSizeFull();
        typeOfUseComboBox.setItems("PRINT", "DIGITAL");
        typeOfUseComboBox.addValueChangeListener(event -> binder.validate());
        binder.forField(typeOfUseComboBox).bind(AclUsageDto::getTypeOfUse, AclUsageDto::setTypeOfUse);
        VaadinUtils.addComponentStyle(typeOfUseComboBox, "acl-usage-edit-type-of-use-combo-box");
        return buildCommonLayout(typeOfUseComboBox, "label.tou");
    }

    private HorizontalLayout buildDetailLicenseeLayout() {
        detailLicenseeClassComboBox.setSizeFull();
        detailLicenseeClassComboBox.setItems(controller.getDetailLicenseeClasses());
        detailLicenseeClassComboBox.setItemCaptionGenerator(DetailLicenseeClass::getIdAndDescription);
        binder.forField(detailLicenseeClassComboBox)
            .bind(AclUsageDto::getDetailLicenseeClass, AclUsageDto::setDetailLicenseeClass);
        VaadinUtils.addComponentStyle(detailLicenseeClassComboBox, "acl-usage-edit-detail-licensee-class-combo-box");
        return buildCommonLayout(detailLicenseeClassComboBox, "label.det_lc");
    }

    private HorizontalLayout buildAnnualizedCopiesLayout() {
        annualizedCopiesField.setSizeFull();
        binder.forField(annualizedCopiesField)
            .withValidator(new AmountZeroValidator())
            .bind(usage -> Objects.toString(usage.getAnnualizedCopies(), StringUtils.EMPTY),
                (usage, value) -> usage.setAnnualizedCopies(
                    NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        annualizedCopiesField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(annualizedCopiesField, "acl-usage-edit-annualized-copies-field");
        return buildCommonLayout(annualizedCopiesField, "label.annualized_copies");
    }

    private HorizontalLayout buildContentUnitPriceLayout() {
        contentUnitPriceField.setSizeFull();
        binder.forField(contentUnitPriceField)
            .withValidator(new AmountValidator())
            .bind(usage -> Objects.toString(usage.getContentUnitPrice(), StringUtils.EMPTY),
                (usage, value) -> usage.setContentUnitPrice(
                    NumberUtils.createBigDecimal(StringUtils.trimToNull(value))));
        contentUnitPriceField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(contentUnitPriceField, "acl-usage-edit-content-unit-price-field");
        return buildCommonLayout(contentUnitPriceField, "label.content_unit_price");
    }

    private HorizontalLayout buildCommonLayout(Component component, String labelCaption) {
        Label label = new Label(ForeignUi.getMessage(labelCaption));
        label.addStyleName(Cornerstone.LABEL_BOLD);
        label.setWidth(130, Unit.PIXELS);
        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setSizeFull();
        layout.setExpandRatio(component, 1);
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton.addClickListener(event -> {
            try {
                binder.writeBean(bindedUsageDto);
                updateUsages();
                saveButtonClickListener.buttonClick(event);
                close();
            } catch (ValidationException e) {
                Windows.showValidationErrorWindow(Arrays.asList(periodField, wrWrkInstField, annualizedCopiesField,
                    contentUnitPriceField));
            }
        });
        saveButton.setEnabled(false);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> binder.readBean(null));
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void updateUsages() {
        selectedUsages.forEach(usageDto -> {
            setField(usageDto::setPeriod, bindedUsageDto.getPeriod());
            setField(usageDto::setWrWrkInst, bindedUsageDto.getWrWrkInst());
            setField(usageDto::setPublicationType, bindedUsageDto.getPublicationType());
            setField(usageDto::setTypeOfUse, bindedUsageDto.getTypeOfUse());
            setField(usageDto::setDetailLicenseeClass, bindedUsageDto.getDetailLicenseeClass());
            setField(usageDto::setAnnualizedCopies, bindedUsageDto.getAnnualizedCopies());
            setField(usageDto::setContentUnitPrice, bindedUsageDto.getContentUnitPrice());
        });
        controller.updateUsages(selectedUsages);
    }

    private <T> void setField(Consumer<T> usageDtoConsumer, T value) {
        if (Objects.nonNull(value)) {
            usageDtoConsumer.accept(value);
        }
    }
}
