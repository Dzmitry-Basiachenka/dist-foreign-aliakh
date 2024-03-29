package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
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
 * Window for uploading researched usage details.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
class ResearchedUsagesUploadWindow extends CommonDialog {

    private static final long serialVersionUID = 3241667285068475662L;

    private final IFasUsageController usagesController;
    private final Binder<String> uploadBinder = new Binder<>();
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController instance of {@link IFasUsageController}
     */
    ResearchedUsagesUploadWindow(IFasUsageController usagesController) {
        this.usagesController = usagesController;
        super.setWidth("520px");
        super.setHeight("230px");
        super.setHeaderTitle(ForeignUi.getMessage("window.upload_researched_usages"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("researched-usages-upload-window", false);
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (uploadBinder.validate().isOk()) {
            try {
                var processor = usagesController.getResearchedUsagesCsvProcessor();
                var processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    int count = processingResult.get().size();
                    usagesController.loadResearchedUsages(processingResult.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", count));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usagesController.getErrorResultStreamSource(uploadField.getValue(), processingResult),
                            ForeignUi.getMessage("message.error.upload")));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getValue(), e.getProcessingResult()),
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
        VaadinUtils.addComponentStyle(uploadField, "researched-usages-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
    }
}
