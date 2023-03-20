package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Window to edit multiple ACLCI usages.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/30/2022
 *
 * @author Dzmitry Basiachenka
 */
class AclciMultipleEditUsagesWindow extends Window {

    private final IAclciUsageController controller;
    private final Set<String> usageIds;
    private final Binder<Usage> binder = new Binder<>();
    private final AclciUsageUpdateWindow usageUpdateWindow;
    private TextField rhAccountNumberField;
    private TextField rhNameField;
    private TextField wrWrkInstField;
    private Rightsholder rh;

    /**
     * Constructor.
     *
     * @param controller        {@link IAclciUsageController} instance
     * @param usageUpdateWindow {@link IAclciUsageController} instance
     * @param usageIds          set of usage ids
     */
    AclciMultipleEditUsagesWindow(IAclciUsageController controller, AclciUsageUpdateWindow usageUpdateWindow,
                                  Set<String> usageIds) {
        this.controller = controller;
        this.usageUpdateWindow = usageUpdateWindow;
        this.usageIds = usageIds;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.multiple.edit_aclci_usages"));
        setResizable(false);
        setWidth(440, Unit.PIXELS);
        setHeight(210, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "multiple-edit-aclci-usages-window");
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout(initRhAccountNumberLayout(), buildRhNameField(),
            buildWrWrkInstField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        TextField accountNumber = buildRhAccountNumberField();
        Button verifyButton = buildVerifyButton();
        verifyButton.setWidth(72, Unit.PIXELS);
        HorizontalLayout horizontalLayout = new HorizontalLayout(accountNumber, verifyButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(accountNumber, 1);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        return horizontalLayout;
    }

    private TextField buildRhAccountNumberField() {
        rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
        rhAccountNumberField.setRequiredIndicatorVisible(true);
        binder.forField(rhAccountNumberField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .bind(usage -> usage.getRightsholder().getAccountNumber().toString(),
                (usage, s) -> usage.getRightsholder().setAccountNumber(Long.valueOf(s)));
        VaadinUtils.setMaxComponentsWidth(rhAccountNumberField);
        VaadinUtils.addComponentStyle(rhAccountNumberField, "rh-account-number-field");
        rhAccountNumberField.addValueChangeListener(event -> rhNameField.setValue(StringUtils.EMPTY));
        return rhAccountNumberField;
    }

    private TextField buildRhNameField() {
        rhNameField = new TextField(ForeignUi.getMessage("label.rh_name"));
        rhNameField.setRequiredIndicatorVisible(true);
        rhNameField.setReadOnly(true);
        rhNameField.setSizeFull();
        binder.forField(rhNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usage -> usage.getRightsholder().getName(), (usage, s) -> usage.getRightsholder().setName(s));
        VaadinUtils.setMaxComponentsWidth(rhNameField);
        VaadinUtils.addComponentStyle(rhNameField, "rh-name-field");
        return rhNameField;
    }

    private Button buildVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (Objects.isNull(rhAccountNumberField.getErrorMessage())) {
                rh = controller.getRightsholder(
                    Long.valueOf(StringUtils.trim(rhAccountNumberField.getValue())));
                if (StringUtils.isNotBlank(rh.getName())) {
                    rhNameField.setValue(rh.getName());
                } else {
                    rhNameField.clear();
                }
            }
        });
        return button;
    }

    private TextField buildWrWrkInstField() {
        wrWrkInstField = new TextField(ForeignUi.getMessage("label.wr_wrk_inst"));
        wrWrkInstField.setSizeFull();
        binder.forField(wrWrkInstField)
            .withValidator(value -> StringUtils.isEmpty(value) || StringUtils.isNumeric(value.trim()),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 9), 0, 9))
            .bind(usage -> Objects.toString(usage.getWrWrkInst(), StringUtils.EMPTY),
                (usage, value) -> usage.setWrWrkInst(NumberUtils.createLong(StringUtils.trimToNull(value))));
        wrWrkInstField.addValueChangeListener(event -> binder.validate());
        VaadinUtils.addComponentStyle(wrWrkInstField, "wr-wrk-inst-field");
        return wrWrkInstField;
    }

    private HorizontalLayout buildButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                Long rhAccountNumber = Long.valueOf(rhAccountNumberField.getValue().trim());
                Long wrWrkInst = Objects.nonNull(StringUtils.trimToNull(wrWrkInstField.getValue()))
                    ? Long.valueOf(wrWrkInstField.getValue().trim())
                    : null;
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.update_usages"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        controller.updateToEligibleByIds(usageIds, rhAccountNumber, wrWrkInst, reason);
                        controller.refreshWidget();
                        usageUpdateWindow.refreshDataProvider();
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showValidationErrorWindow(List.of(rhAccountNumberField, rhNameField, wrWrkInstField));
            }
        });
        VaadinUtils.setButtonsAutoDisabled(saveButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(saveButton, closeButton);
        return horizontalLayout;
    }
}
