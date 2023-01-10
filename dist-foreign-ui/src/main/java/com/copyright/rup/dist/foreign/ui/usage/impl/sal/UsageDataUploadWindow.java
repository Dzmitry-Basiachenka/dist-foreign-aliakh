package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.SalUsageDataCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Window for uploading a SAL usage data.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/28/20
 *
 * @author Stanislau Rudak
 */
public class UsageDataUploadWindow extends Window {

    private final ISalUsageController usagesController;
    private final Binder<UsageBatch> itemBankBinder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private ComboBox<UsageBatch> itemBankComboBox;
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController usages controller
     */
    UsageDataUploadWindow(ISalUsageController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_usage_data"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(170, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            UsageBatch batch = itemBankComboBox.getSelectedItem().get();
            if (!usagesController.usageDataExists(batch.getId())) {
                if (usagesController.getIneligibleBatchesNames(Set.of(batch.getId())).isEmpty()) {
                    uploadUsageData();
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.invalid_item_bank.non_eligible_usages"));
                }
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.invalid_item_bank.has_usage_data"));
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(itemBankComboBox, uploadField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return itemBankBinder.isValid() && uploadBinder.isValid();
    }

    private void uploadUsageData() {
        UsageBatch selectedBatch = itemBankComboBox.getSelectedItem().get();
        try {
            SalUsageDataCsvProcessor processor =
                usagesController.getSalUsageDataCsvProcessor(selectedBatch.getId());
            ProcessingResult<Usage> processingResult =
                processor.process(uploadField.getStreamToUploadedFile());
            if (processingResult.isSuccessful()) {
                List<Usage> usages = processingResult.get();
                usagesController.loadUsageData(selectedBatch, usages);
                close();
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.upload_completed", usages.size()));
            } else {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getValue(),
                            processingResult),
                        ForeignUi.getMessage("message.error.upload")));
            }
        } catch (ThresholdExceededException e) {
            Windows.showModalWindow(
                new ErrorUploadWindow(
                    usagesController.getErrorResultStreamSource(uploadField.getValue(),
                        e.getProcessingResult()), String.format("%s%s",
                    e.getMessage(), ForeignUi.getMessage("message.error.upload.threshold.exceeded"))));
        } catch (ValidationException e) {
            Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
        }
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUploadField(), initItemBankComboBox(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        itemBankBinder.validate();
        uploadBinder.validate();
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        uploadBinder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(s -> s, (s, v) -> s = v).validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
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

    private ComboBox<UsageBatch> initItemBankComboBox() {
        itemBankComboBox = new ComboBox<>(ForeignUi.getMessage("label.item_bank"));
        itemBankComboBox.setItems(usagesController.getBatchesNotAttachedToScenario());
        itemBankComboBox.setItemCaptionGenerator(UsageBatch::getName);
        itemBankComboBox.setRequiredIndicatorVisible(true);
        itemBankBinder.forField(itemBankComboBox)
            .asRequired(ForeignUi.getMessage("field.error.item_bank.empty"))
            .bind(s -> s, (s, v) -> s = v);
        itemBankComboBox.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(itemBankComboBox);
        VaadinUtils.addComponentStyle(itemBankComboBox, "item-banks-filter");
        return itemBankComboBox;
    }
}
