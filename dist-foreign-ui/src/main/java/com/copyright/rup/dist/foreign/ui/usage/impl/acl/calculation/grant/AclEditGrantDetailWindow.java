package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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

    private ComboBox<String> grantStatusField;
    private TextField rhAccountNumberField;
    private ComboBox<String> eligibleFlag;

    /**
     * Constructor.
     */
    public AclEditGrantDetailWindow() {
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.edit_grant_detail"));
        setResizable(false);
        setWidth(450, Unit.PIXELS);
        setHeight(220, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "edit-grant-detail-window");
    }

    private Component initRootLayout() {
        VerticalLayout rootLayout = new VerticalLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        initGrantStatusComboBox();
        initRhAccountNumberField();
        initEligibleFlagComboBox();
        rootLayout.addComponents(grantStatusField, rhAccountNumberField, eligibleFlag, buttonsLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        rootLayout.setSizeFull();
        return rootLayout;
    }

    private void initGrantStatusComboBox() {
        grantStatusField = new ComboBox<>(ForeignUi.getMessage("label.grant_status"));
        grantStatusField.setItems("GRANT", "DENY");
        grantStatusField.setSizeFull();
        VaadinUtils.addComponentStyle(grantStatusField, "acl-grant-status-field");
    }

    private void initRhAccountNumberField() {
        rhAccountNumberField = new TextField(ForeignUi.getMessage("label.rh_account_number"));
        rhAccountNumberField.setSizeFull();
        VaadinUtils.addComponentStyle(rhAccountNumberField, "acl-rh-account-number-field");
    }

    private void initEligibleFlagComboBox() {
        eligibleFlag = new ComboBox<>(ForeignUi.getMessage("label.eligible"));
        eligibleFlag.setItems("Y", "N");
        eligibleFlag.setSizeFull();
        VaadinUtils.addComponentStyle(eligibleFlag, "acl-eligible-flag-field");
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button saveButton = Buttons.createButton(ForeignUi.getMessage("button.save"));
        saveButton.setEnabled(false);
        Button discardButton = Buttons.createButton(ForeignUi.getMessage("button.discard"));
        discardButton.addClickListener(event -> discardFields());
        return new HorizontalLayout(saveButton, discardButton, closeButton);
    }

    private void discardFields() {
        grantStatusField.clear();
        rhAccountNumberField.clear();
        eligibleFlag.clear();
    }
}
