package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclFundPoolCsvProcessor;
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

import java.util.List;

/**
 * Window for uploading {@link FundPool}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolUploadWindow extends Window {

    private static final long serialVersionUID = -1219956392479581815L;

    private final IAaclUsageController aaclUsageController;
    private final Binder<FundPool> fundPoolBinder = new Binder<>();
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
        super.setContent(initRootLayout());
        super.setCaption(ForeignUi.getMessage("window.upload_fund_pool"));
        super.setResizable(false);
        super.setWidth(380, Unit.PIXELS);
        super.setHeight(165, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "aacl-fund-pool-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                AaclFundPoolCsvProcessor processor = aaclUsageController.getAaclFundPoolCsvProcessor();
                ProcessingResult<FundPoolDetail> result = processor.process(uploadField.getStreamToUploadedFile());
                if (result.isSuccessful()) {
                    int usagesCount = aaclUsageController.insertFundPool(buildFundPool(), result.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            aaclUsageController.getErrorResultStreamSource(uploadField.getValue(), result),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(List.of(fundPoolNameField, uploadField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return fundPoolBinder.isValid() && uploadBinder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initFundPoolNameField(), initUploadField(), buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        fundPoolBinder.validate();
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
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue).validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.addComponentStyle(uploadField, "aacl-fund-pool-upload-component");
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

    private TextField initFundPoolNameField() {
        fundPoolNameField = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        fundPoolNameField.setSizeFull();
        fundPoolNameField.setRequiredIndicatorVisible(true);
        fundPoolBinder.forField(fundPoolNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !aaclUsageController.fundPoolExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund Pool"))
            .bind(FundPool::getName, FundPool::setName);
        VaadinUtils.setMaxComponentsWidth(fundPoolNameField);
        VaadinUtils.addComponentStyle(fundPoolNameField, "aacl-fund-pool-name-field");
        return fundPoolNameField;
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        fundPool.setName(StringUtils.trim(fundPoolNameField.getValue()));
        return fundPool;
    }
}
