package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.AaclClassifiedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.ClassifiedUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Window for uploading classified usage details.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 1/28/20
 *
 * @author Stanislau Rudak
 */
public class ClassifiedUsagesUploadWindow extends Window {

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
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_classified_usages"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(135, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "classified-usages-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                ClassifiedUsageCsvProcessor processor = usageController.getClassifiedUsageCsvProcessor();
                ProcessingResult<AaclClassifiedUsage> processingResult =
                    processor.process(uploadField.getStreamToUploadedFile());
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
            Windows.showValidationErrorWindow(Set.of(uploadField));
        }
    }

    /**
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return uploadBinder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        buttonsLayout.setMargin(new MarginInfo(true, false, true, false));
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUploadField(), buttonsLayout);
        rootLayout.setSpacing(true);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        closeButton.setWidth(73, Unit.PIXELS);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.setWidth(73, Unit.PIXELS);
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        uploadBinder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue).validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "classified-usages-upload-component");
        return uploadField;
    }
}
