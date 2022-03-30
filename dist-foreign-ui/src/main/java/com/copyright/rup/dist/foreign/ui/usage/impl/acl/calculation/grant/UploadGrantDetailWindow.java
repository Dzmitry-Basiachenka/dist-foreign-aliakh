package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;

/**
 * Window to upload ACL Grant details.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Ihar Suvorau
 */
public class UploadGrantDetailWindow extends Window {

    private final Binder<String> binder = new Binder<>();
    private final UploadField uploadField = new UploadField();
    private final ComboBox<String> comboBox = new ComboBox<>(ForeignUi.getMessage("label.grant_set"));

    /**
     * Default constructor.
     */
    public UploadGrantDetailWindow() {
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_grant_detail"));
        setResizable(false);
        setWidth(350, Unit.PIXELS);
        setHeight(170, Unit.PIXELS);
        binder.validate();
        VaadinUtils.addComponentStyle(this, "upload-grant-detail-window");
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initGrantSetComboBox(), initUploadField(), buttonsLayout);
        rootLayout.setSpacing(true);
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private ComboBox<String> initGrantSetComboBox() {
        comboBox.setSizeFull();
        comboBox.setRequiredIndicatorVisible(true);
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setItems(Collections.emptyList());
        binder.forField(comboBox)
            .withValidator(new RequiredValidator())
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue);
        return comboBox;
    }

    private UploadField initUploadField() {
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        binder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue);
        uploadField.addSucceededListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "grant-details-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> {
            if (binder.isValid()) {
                close();
            } else {
                Windows.showValidationErrorWindow(Arrays.asList(comboBox, uploadField));
            }
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }
}
