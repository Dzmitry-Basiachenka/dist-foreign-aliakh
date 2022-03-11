package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

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
    private ComboBox<String> eligibleFlag;
    private Button saveButton;
    private String newGrantStatusValue;
    private String newRhAccountNumberValue;
    private String newEligibleValue;

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
        rootLayout.addComponents(grantStatusField, initRhAccountNumberLayout(), rhNameField, eligibleFlag,
            buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        return rootLayout;
    }

    private void initGrantStatusComboBox() {
        grantStatusField = new ComboBox<>(ForeignUi.getMessage("label.grant_status"));
        grantStatusField.addValueChangeListener(event -> {
            this.newGrantStatusValue = grantStatusField.getValue();
            refreshSaveButton(event);
        });
        grantStatusField.setItems("GRANT", "DENY");
        grantStatusField.setSizeFull();
        VaadinUtils.addComponentStyle(grantStatusField, "acl-grant-status-field");
    }

    private HorizontalLayout initRhAccountNumberLayout() {
        rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
        rhAccountNumberField.setSizeFull();
        rhAccountNumberField.addValueChangeListener(event -> {
            this.newRhAccountNumberValue = rhAccountNumberField.getValue();
            refreshSaveButton(event);
        });
        binder.forField(rhAccountNumberField)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(new NumericValidator())
            .bind(Objects::toString, (grant, value) -> NumberUtils.createLong(StringUtils.trimToNull(value)))
            .validate();
        VaadinUtils.addComponentStyle(rhAccountNumberField, "acl-rh-account-number-field");
        Button verifyButton = Buttons.createButton(ForeignUi.getMessage("button.verify"));
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
        eligibleFlag = new ComboBox<>(ForeignUi.getMessage("label.eligible"));
        eligibleFlag.addValueChangeListener(event -> {
            this.newEligibleValue = eligibleFlag.getValue();
            refreshSaveButton(event);
        });
        eligibleFlag.setItems("Y", "N");
        eligibleFlag.setSizeFull();
        VaadinUtils.addComponentStyle(eligibleFlag, "acl-eligible-flag-field");
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
        //todo will be initialized after implement logic to verify RH account
        String newRhName = StringUtils.EMPTY;
        selectedGrants.forEach(grant -> {
            setValue(newGrantStatusValue, grant::setGrantStatus);
            setValue(newRhAccountNumberValue,
                value -> grant.setRhAccountNumber(NumberUtils.createLong(StringUtils.trimToNull(value))));
            setValue(newRhName, grant::setRhName);
            setValue(newEligibleValue, value -> grant.setEligible(
                StringUtils.isNotEmpty(value) ? "Y".equals(value) : null));
        });
        controller.updateAclGrants(selectedGrants);
    }

    private void setValue(String value, Consumer<String> setter) {
        if (Objects.nonNull(value) && StringUtils.isNotEmpty(value)) {
            setter.accept(value);
        }
    }

    private void discardFields() {
        grantStatusField.clear();
        rhAccountNumberField.clear();
        eligibleFlag.clear();
    }

    private void refreshSaveButton(ValueChangeEvent<String> event) {
        saveButton.setEnabled(StringUtils.isNotEmpty(event.getValue()));
    }
}
