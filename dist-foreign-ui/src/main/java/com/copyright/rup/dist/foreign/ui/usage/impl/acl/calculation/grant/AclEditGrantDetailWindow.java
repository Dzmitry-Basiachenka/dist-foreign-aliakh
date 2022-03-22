package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.ui.common.validator.NumericValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
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
 * Window to edit UDM Grant.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/08/2022
 *
 * @author Anton Azarenka
 */
public class AclEditGrantDetailWindow extends Window {

    private final Binder<AclGrantDetailDto> binder = new Binder<>();
    private final IAclGrantDetailController controller;
    private final Set<AclGrantDetailDto> selectedGrants;
    private final ClickListener saveButtonClickListener;
    private ComboBox<String> grantStatusField;
    private TextField rhAccountNumberField;
    private TextField rhNameField;
    private ComboBox<String> eligibleFlagField;
    private Button saveButton;
    private Rightsholder rightsholder = new Rightsholder();

    /**
     * Constructor.
     *
     * @param selectedGrants selected ACL grants for edit
     * @param controller     instance of {@link IAclGrantDetailController}
     * @param clickListener  action that should be performed after Save button was clicked
     */
    public AclEditGrantDetailWindow(Set<AclGrantDetailDto> selectedGrants, IAclGrantDetailController controller,
                                    ClickListener clickListener) {
        this.controller = controller;
        this.selectedGrants = selectedGrants;
        this.saveButtonClickListener = clickListener;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_grant_detail"));
        setResizable(false);
        setWidth(350, Unit.PIXELS);
        setHeight(270, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-grant-detail-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initGrantStatusComboBox();
        initRhName();
        initEligibleFlagComboBox();
        rootLayout.addComponents(grantStatusField, initRhAccountNumberLayout(), rhNameField, eligibleFlagField,
            buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        return rootLayout;
    }

    private void initGrantStatusComboBox() {
        grantStatusField = new ComboBox<>(ForeignUi.getMessage("label.grant_status"));
        grantStatusField.addValueChangeListener(this::refreshSaveButton);
        grantStatusField.setItems("GRANT", "DENY");
        grantStatusField.setSizeFull();
        VaadinUtils.addComponentStyle(grantStatusField, "acl-grant-status-field");
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
        rhAccountNumberField.setSizeFull();
        rhAccountNumberField.addValueChangeListener(this::refreshSaveButton);
        binder.forField(rhAccountNumberField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(new NumericValidator())
            .bind(Objects::toString, (grant, value) -> NumberUtils.createLong(StringUtils.trimToNull(value)))
            .validate();
        VaadinUtils.addComponentStyle(rhAccountNumberField, "acl-rh-account-number-field");
        Button verifyButton = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        verifyButton.addClickListener(event -> {
            String rhAccountNumber = StringUtils.trimToNull(rhAccountNumberField.getValue());
            if (Objects.nonNull(rhAccountNumber) && Objects.isNull(rhAccountNumberField.getErrorMessage())) {
                rightsholder = controller.getRightsholder(NumberUtils.createLong(rhAccountNumber));
                if (Objects.nonNull(rightsholder)) {
                    rhNameField.setValue(rightsholder.getName());
                }
            }
        });
        VaadinUtils.addComponentStyle(rhAccountNumberField, "acl-rh-account-number-field");
        HorizontalLayout rhAccountNumberLayout = new HorizontalLayout(rhAccountNumberField, verifyButton);
        rhAccountNumberLayout.setSizeFull();
        rhAccountNumberLayout.setExpandRatio(rhAccountNumberField, 1);
        rhAccountNumberLayout.setComponentAlignment(verifyButton, Alignment.BOTTOM_RIGHT);
        return rhAccountNumberLayout;
    }

    private void initRhName() {
        rhNameField = new TextField(ForeignUi.getMessage("label.rh_name"));
        binder.forField(rhNameField)
            .withValidator(
                value -> StringUtils.isNotEmpty(value.trim()) && StringUtils.isNotEmpty(rhAccountNumberField.getValue())
                    || StringUtils.isEmpty(rhAccountNumberField.getValue()),
                ForeignUi.getMessage("field.error.rh_name.empty"))
            .bind(AclGrantDetailDto::getRhName, AclGrantDetailDto::setRhName)
            .validate();
        rhNameField.setSizeFull();
        rhNameField.setReadOnly(true);
        VaadinUtils.addComponentStyle(rhNameField, "acl-rh-name-field");
    }

    private void initEligibleFlagComboBox() {
        eligibleFlagField = new ComboBox<>(ForeignUi.getMessage("label.eligible"));
        eligibleFlagField.addValueChangeListener(this::refreshSaveButton);
        eligibleFlagField.setItems("Y", "N");
        eligibleFlagField.setSizeFull();
        VaadinUtils.addComponentStyle(eligibleFlagField, "acl-eligible-flag-field");
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.addClickListener(event -> {
            if (binder.isValid()) {
                updateGrants();
                saveButtonClickListener.buttonClick(event);
                close();
            } else {
                Windows.showValidationErrorWindow(Arrays.asList(rhAccountNumberField, rhNameField));
            }
        });
        saveButton.setEnabled(false);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> discardFields());
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void updateGrants() {
        boolean doUpdateTouStatus =
            StringUtils.isNotEmpty(grantStatusField.getValue()) || Objects.nonNull(rightsholder.getAccountNumber());
        selectedGrants.forEach(grant -> {
            if (Objects.nonNull(rightsholder.getAccountNumber())) {
                grant.setRhAccountNumber(rightsholder.getAccountNumber());
                grant.setRhName(rightsholder.getName());
            }
            setValue(grantStatusField.getValue(), grant::setGrantStatus);
            setValue(eligibleFlagField.getValue(),
                value -> grant.setEligible(StringUtils.isNotEmpty(value) ? "Y".equals(value) : null));
        });
        controller.updateAclGrants(selectedGrants, doUpdateTouStatus);
    }

    private void setValue(String value, Consumer<String> setter) {
        if (StringUtils.isNotEmpty(value)) {
            setter.accept(value);
        }
    }

    private void discardFields() {
        grantStatusField.clear();
        rhAccountNumberField.clear();
        rhNameField.clear();
        eligibleFlagField.clear();
    }

    private void refreshSaveButton(ValueChangeEvent<String> event) {
        saveButton.setEnabled(StringUtils.isNotEmpty(event.getValue()));
    }
}
