package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.grant;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.service.impl.csv.AclGrantDetailCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclGrantDetailController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
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

import java.util.List;
import java.util.stream.Collectors;

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

    private final IAclGrantDetailController controller;
    private final Binder<String> binder = new Binder<>();
    private final Binder<AclGrantSet> grantSetBinder = new Binder<>();
    private final UploadField uploadField = new UploadField();
    private final ComboBox<AclGrantSet> comboBox = new ComboBox<>(ForeignUi.getMessage("label.grant_set"));

    /**
     * Default constructor.
     *
     * @param controller instance of {@link IAclGrantDetailController}
     */
    public UploadGrantDetailWindow(IAclGrantDetailController controller) {
        this.controller = controller;
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
        return binder.isValid() && grantSetBinder.isValid();
    }

    /**
     * Handles upload button click.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                AclGrantSet grantSet = comboBox.getSelectedItem().get();
                AclGrantDetailCsvProcessor processor = controller.getCsvProcessor(grantSet.getId());
                ProcessingResult<AclGrantDetailDto> result = processor.process(uploadField.getStreamToUploadedFile());
                if (result.isSuccessful()) {
                    controller.insertAclGrantDetails(grantSet, result.get());
                    close();
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.upload_completed", result.get().size()));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(controller.getErrorResultStreamSource(uploadField.getValue(), result),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(List.of(comboBox, uploadField));
        }
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

    private ComboBox<AclGrantSet> initGrantSetComboBox() {
        comboBox.setSizeFull();
        comboBox.setRequiredIndicatorVisible(true);
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setItems(
            controller.getAllAclGrantSets().stream().filter(AclGrantSet::getEditable).collect(Collectors.toList()));
        comboBox.setItemCaptionGenerator(AclGrantSet::getName);
        VaadinUtils.addComponentStyle(comboBox, "grant-details-upload-combo-box");
        grantSetBinder.forField(comboBox)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(bean -> bean, (bean, fieldValue) -> bean = fieldValue)
            .validate();
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
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }
}
