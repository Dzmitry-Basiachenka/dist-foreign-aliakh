package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

/**
 * Window for uploading classified usage details.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 01/28/2020
 *
 * @author Stanislau Rudak
 */
public class ClassifiedUsagesUploadWindow extends CommonDialog {

    private static final long serialVersionUID = -9108464420946943048L;

    private final IAaclUsageController usageController;
    private final Binder<String> uploadBinder = new Binder<>();
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usageController instance of {@link IAaclUsageController}
     */
    public ClassifiedUsagesUploadWindow(IAaclUsageController usageController) {
        this.usageController = usageController;
        super.setWidth("500px");
        super.setHeight("230px");
        super.setHeaderTitle(ForeignUi.getMessage("window.upload_classified_usages"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("classified-usages-upload-window", false);
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (uploadBinder.validate().isOk()) {
            try {
                var processor = usageController.getClassifiedUsageCsvProcessor();
                var processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    int loadedCount = processingResult.get().size();
                    int updatedCount = usageController.loadClassifiedUsages(processingResult.get());
                    close();
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.upload_classified_completed", updatedCount,
                            loadedCount - updatedCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usageController.getErrorResultStreamSource(uploadField.getValue(), processingResult),
                            ForeignUi.getMessage("message.error.upload")));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usageController.getErrorResultStreamSource(uploadField.getValue(), e.getProcessingResult()),
                        e.getMessage() + ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow();
        }
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout(initUploadField());
        VaadinUtils.setPadding(rootLayout, 0, 10, 0, 10);
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setRequiredIndicatorVisible(true);
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        uploadField.setSizeFull();
        uploadBinder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(ValueProvider.identity(), (bean, value) -> bean = value);
        VaadinUtils.addComponentStyle(uploadField, "classified-usages-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
    }
}
