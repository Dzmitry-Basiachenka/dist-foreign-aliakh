package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.FundPool;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

/**
 * Window for uploading {@link FundPool}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolUploadWindow extends CommonDialog {

    private static final long serialVersionUID = -1219956392479581815L;

    private final IAaclUsageController usageController;
    private final Binder<FundPool> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usageController instance of {@link IAaclUsageController}
     */
    public AaclFundPoolUploadWindow(IAaclUsageController usageController) {
        this.usageController = usageController;
        super.setWidth("500px");
        super.setHeight("340px");
        super.setHeaderTitle(ForeignUi.getMessage("window.upload_fund_pool"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("aacl-fund-pool-upload-window", false);
        initBinder();
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                var processor = usageController.getAaclFundPoolCsvProcessor();
                var result = processor.process(uploadField.getStreamToUploadedFile());
                if (result.isSuccessful()) {
                    int usagesCount = usageController.insertFundPool(binder.getBean(), result.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            usageController.getErrorResultStreamSource(uploadField.getValue(), result),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow();
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        var isUploadBinderValid = uploadBinder.validate().isOk();
        var isBinderValid = binder.validate().isOk();
        return isUploadBinderValid && isBinderValid;
    }

    private void initBinder() {
        var fundPool = new FundPool();
        fundPool.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        binder.setBean(fundPool);
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout();
        rootLayout.add(initFundPoolNameField(), initUploadField());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        return rootLayout;
    }

    private TextField initFundPoolNameField() {
        var fundPoolNameField = new TextField(ForeignUi.getMessage("label.fund_pool.name"));
        fundPoolNameField.setRequiredIndicatorVisible(true);
        fundPoolNameField.setSizeFull();
        binder.forField(fundPoolNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usageController.fundPoolExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Fund Pool"))
            .bind(FundPool::getName, FundPool::setName);
        VaadinUtils.addComponentStyle(fundPoolNameField, "aacl-fund-pool-name-field");
        return fundPoolNameField;
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
        VaadinUtils.addComponentStyle(uploadField, "aacl-fund-pool-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
    }
}
