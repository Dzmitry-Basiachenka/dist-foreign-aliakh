package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;

import java.util.Collections;

/**
 * Window for uploading researched usage details.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
class ResearchedUsagesUploadWindow extends Window {

    private final IUsagesController usagesController;
    private final Binder<String> uploadBinder = new Binder<>();
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController {@link IUsagesController} instance
     */
    ResearchedUsagesUploadWindow(IUsagesController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_researched_usages"));
        setResizable(false);
        setWidth(440, Unit.PIXELS);
        setHeight(130, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "researched-usages-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            StopWatch stopWatch = new Slf4JStopWatch();
            try {
                // TODO {aliakh} replace with actual CSV processor after its implementation
                UsageCsvProcessor processor = usagesController.getCsvProcessor(null);
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                stopWatch.lap("researchedUsages.load_fileProcessed");
                if (processingResult.isSuccessful()) {
                    int usagesCount = 0;
                    stopWatch.lap("researchedUsages.load_stored");
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usagesController.getErrorResultStreamSource(uploadField.getValue(), processingResult),
                            ForeignUi.getMessage("message.error.upload")));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            } finally {
                stopWatch.stop();
            }
        } else {
            Windows.showValidationErrorWindow(Collections.singleton(uploadField));
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
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUploadField(), buttonsLayout);
        rootLayout.setSpacing(true);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        uploadBinder.forField(uploadField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue).validate();
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "researched-usages-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        Button uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        horizontalLayout.setSpacing(true);
        return horizontalLayout;
    }
}
