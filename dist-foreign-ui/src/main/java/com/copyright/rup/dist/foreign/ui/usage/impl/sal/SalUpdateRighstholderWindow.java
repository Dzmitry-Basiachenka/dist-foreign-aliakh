package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.google.common.collect.Lists;
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

/**
 * Window for RH update for SAL IB detail.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/27/20
 *
 * @author Darya Baraukova
 */
class SalUpdateRighstholderWindow extends Window {

    private final ISalUsageController salUsageController;
    private final UsageDto selectedUsage;
    private final Binder<Usage> usageBinder = new Binder<>();
    private final SalDetailForRightsholderUpdateWindow detailsWindow;
    private TextField rhAccountNumberField;
    private TextField rhNameField;
    private Rightsholder rh;

    /**
     * Constructor.
     *
     * @param salUsageController {@link ISalUsageController} instance
     * @param detailsWindow      {@link SalDetailForRightsholderUpdateWindow} instance
     * @param selectedUsage      selected {@link UsageDto}
     */
    SalUpdateRighstholderWindow(ISalUsageController salUsageController,
                                SalDetailForRightsholderUpdateWindow detailsWindow, UsageDto selectedUsage) {
        this.salUsageController = salUsageController;
        this.detailsWindow = detailsWindow;
        this.selectedUsage = selectedUsage;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.update_rightsholder"));
        setResizable(false);
        setWidth(440, Unit.PIXELS);
        setHeight(190, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "update-rh-window");
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        VerticalLayout rhLayout = initRightsholderLayout();
        buttonsLayout.setMargin(new MarginInfo(true, false, true, false));
        VerticalLayout rootLayout = new VerticalLayout(rhLayout, buttonsLayout);
        rootLayout.setComponentAlignment(rhLayout, Alignment.MIDDLE_CENTER);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        usageBinder.validate();
        return rootLayout;
    }

    private VerticalLayout initRightsholderLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        TextField accountNumber = buildRhAccountNumberField();
        Button verifyButton = buildVerifyButton();
        verifyButton.setWidth(72, Unit.PIXELS);
        HorizontalLayout horizontalLayout = new HorizontalLayout(accountNumber, verifyButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setExpandRatio(accountNumber, 1);
        horizontalLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        TextField rhName = buildRhNameField();
        rhName.setSizeFull();
        verticalLayout.addComponents(horizontalLayout, rhName);
        verticalLayout.setMargin(false);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private TextField buildRhAccountNumberField() {
        rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
        rhAccountNumberField.setRequiredIndicatorVisible(true);
        usageBinder.forField(rhAccountNumberField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                "Field value should contain numeric values only")
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
        usageBinder.forField(rhNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usage -> usage.getRightsholder().getName(), (usage, s) -> usage.getRightsholder().setName(s));
        VaadinUtils.setMaxComponentsWidth(rhNameField);
        VaadinUtils.addComponentStyle(rhNameField, "rh-name-field");
        return rhNameField;
    }

    private Button buildVerifyButton() {
        Button button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (null == rhAccountNumberField.getErrorMessage()) {
                rh = salUsageController.getRightsholder(
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

    private HorizontalLayout buildButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (usageBinder.isValid()) {
                Windows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.update_righstholder", selectedUsage.getRhAccountNumber()),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        salUsageController.updateToEligibleWithRhAccountNumber(selectedUsage.getId(),
                            Long.valueOf(rhAccountNumberField.getValue()), reason);
                        salUsageController.refreshWidget();
                        detailsWindow.refreshDataProvider();
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showValidationErrorWindow(Lists.newArrayList(rhAccountNumberField, rhNameField));
            }
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(saveButton, closeButton);
        return horizontalLayout;
    }
}
