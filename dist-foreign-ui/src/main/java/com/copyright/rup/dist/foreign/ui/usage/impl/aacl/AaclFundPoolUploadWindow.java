package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.AaclFundPool;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
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

import java.util.Arrays;

/**
 * Window for uploading {@link AaclFundPool}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolUploadWindow extends Window {

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";

    private final IAaclUsageController aaclUsageController;
    private final Binder<AaclFundPool> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private TextField fundPoolNameField;
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param aaclUsageController {@link IAaclUsageController} instance
     */
    public AaclFundPoolUploadWindow(IAaclUsageController aaclUsageController) {
        this.aaclUsageController = aaclUsageController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_fund_pool"));
        setResizable(false);
        setWidth(380, Unit.PIXELS);
        setHeight(165, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "aacl-fund-pool-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                AaclFundPoolCsvProcessor processor = aaclUsageController.getAaclFundPoolCsvProcessor();
                ProcessingResult<AaclFundPoolDetail> result = processor.process(uploadField.getStreamToUploadedFile());
                if (result.isSuccessful()) {
                    int usagesCount = 0; // TODO {aliakh} to implement aaclUsageController.loadAaclFundPool
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            aaclUsageController.getErrorResultStreamSource(uploadField.getValue(), result),
                            "<br>Press Download button to see detailed list of errors"));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(fundPoolNameField, uploadField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid() && uploadBinder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initFundPoolNameField(), initUploadField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadBinder.forField(uploadField)
            .withValidator(value -> StringUtils.isEmpty(value)
                || StringUtils.endsWith(value, ".csv"), ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(s -> s, (s, v) -> s = v).validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.addComponentStyle(uploadField, "aacl-fund-pool-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private TextField initFundPoolNameField() {
        fundPoolNameField = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        fundPoolNameField.setRequiredIndicatorVisible(true);
        binder.forField(fundPoolNameField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !aaclUsageController.aaclFundPoolExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund Pool"))
            .bind(AaclFundPool::getName, AaclFundPool::setName);
        fundPoolNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(fundPoolNameField);
        VaadinUtils.addComponentStyle(fundPoolNameField, "aac-fund-pool-name-field");
        return fundPoolNameField;
    }
}
