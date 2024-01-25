package com.copyright.rup.dist.foreign.vui.usage.impl.fas;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.vui.common.validator.AmountRangeValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredBigDecimalValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.RoundingMode;

/**
 * Window for uploading a usage batch with usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/18/2017
 *
 * @author Mikita Hladkikh
 * @author Mikalai Bezmen
 */
public class UsageBatchUploadWindow extends CommonDialog {

    private static final long serialVersionUID = -4055282191058832638L;

    private final IFasUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private final RequiredValidator requiredValidator = new RequiredValidator();

    private TextField usageBatchNameField;
    private UploadField uploadField;
    private TextField accountNumberField;
    private Binder.Binding<UsageBatch, String> accountNumberBinding;
    private TextField productFamilyField;
    private TextField accountNameField;
    private Rightsholder rro;
    private LocalDateWidget paymentDateWidget;
    private TextField fiscalYearField;
    private BigDecimalField grossAmountField;

    /**
     * Constructor.
     *
     * @param usagesController instance of {@link IFasUsageController}
     */
    UsageBatchUploadWindow(IFasUsageController usagesController) {
        this.usagesController = usagesController;
        super.add(initRootLayout());
        super.setHeaderTitle(ForeignUi.getMessage("window.upload_usage_batch"));
        super.setWidth("590px");
        super.setHeight("700px");
        super.getFooter().add(initButtonsLayout());
        setModalWindowProperties("usage-upload-window", false);
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                UsageCsvProcessor processor = usagesController.getCsvProcessor(productFamilyField.getValue());
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    int usagesCount = usagesController.loadUsageBatch(buildUsageBatch(), processingResult.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
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

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return binder.isValid() && uploadBinder.isValid();
    }

    private UsageBatch buildUsageBatch() {
        var usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setProductFamily(StringUtils.trim(productFamilyField.getValue()));
        usageBatch.setRro(rro);
        usageBatch.setPaymentDate(paymentDateWidget.getValue());
        usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
        usageBatch.setGrossAmount(grossAmountField.getValue().setScale(2, RoundingMode.HALF_UP));
        return usageBatch;
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout();
        rootLayout.add(initUsageBatchNameField(), initUploadField(), initRightsholderLayout(),
            initPaymentDataLayout(), initGrossAmountLayout());
        rootLayout.setSpacing(false);
        rootLayout.setPadding(false);
        rootLayout.setMargin(false);
        rootLayout.setSizeFull();
        VaadinUtils.addComponentStyle(rootLayout, "usage-batch-upload-widget-layout");
        binder.validate();
        return rootLayout;
    }

    private TextField initUsageBatchNameField() {
        usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        binder.forField(usageBatchNameField)
            .withValidator(requiredValidator)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        usageBatchNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.setRequiredIndicatorVisible(true);
        uploadBinder.forField(uploadField)
            .withValidator(requiredValidator)
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(ValueProvider.identity(), (usageBatch, value) -> usageBatch = value)
            .validate();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var closeButton = Buttons.createCloseButton(this);
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        var buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(uploadButton, closeButton);
        return buttonsLayout;
    }

    private VerticalLayout initRightsholderLayout() {
        var verticalLayout = new VerticalLayout();
        var rroAccountLayout = new HorizontalLayout();
        var accountNumber = initRightsholderAccountNumberField();
        var productFamily = initProductFamilyField();
        rroAccountLayout.add(accountNumber, productFamily);
        rroAccountLayout.setSizeFull();
        var horizontalLayout = new HorizontalLayout(rroAccountLayout, initVerifyButton());
        horizontalLayout.setSizeFull();
        var accountName = initRightsholderAccountNameField();
        accountName.setSizeFull();
        verticalLayout.add(horizontalLayout, accountName);
        verticalLayout.setSpacing(false);
        verticalLayout.setPadding(false);
        verticalLayout.setMargin(false);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private TextField initRightsholderAccountNumberField() {
        accountNumberField = new TextField(ForeignUi.getMessage("label.rro_account_number"));
        accountNumberField.setRequiredIndicatorVisible(true);
        accountNumberBinding = binder.forField(accountNumberField)
            .withValidator(requiredValidator)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.number_length", 10), 0, 10))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .bind(usageBatch -> usageBatch.getRro().getAccountNumber().toString(),
                (usageBatch, value) -> usageBatch.getRro().setAccountNumber(Long.valueOf(value)));
        VaadinUtils.setMaxComponentsWidth(accountNumberField);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        accountNumberField.addValueChangeListener(event -> {
            accountNameField.setValue(StringUtils.EMPTY);
            productFamilyField.setValue(StringUtils.EMPTY);
        });
        return accountNumberField;
    }

    private TextField initProductFamilyField() {
        productFamilyField = new TextField(ForeignUi.getMessage("label.product_family"));
        productFamilyField.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(productFamilyField);
        VaadinUtils.addComponentStyle(productFamilyField, "product-family-field");
        return productFamilyField;
    }

    private Button initVerifyButton() {
        var button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.addClickListener(event -> {
            if (BindingValidationStatus.Status.OK == accountNumberBinding.validate().getStatus()) {
                rro = usagesController.getRightsholder(Long.valueOf(StringUtils.trim(accountNumberField.getValue())));
                if (StringUtils.isNotBlank(rro.getName())) {
                    accountNameField.setValue(rro.getName());
                    productFamilyField.setValue(
                        usagesController.getClaAccountNumber().equals(rro.getAccountNumber())
                            ? FdaConstants.CLA_FAS_PRODUCT_FAMILY : FdaConstants.FAS_PRODUCT_FAMILY);
                } else {
                    accountNameField.clear();
                    productFamilyField.clear();
                }
            }
        });
        return button;
    }

    private TextField initRightsholderAccountNameField() {
        accountNameField = new TextField(ForeignUi.getMessage("label.rro_account_name"));
        accountNameField.setRequiredIndicatorVisible(true);
        accountNameField.setReadOnly(true);
        binder.forField(accountNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usageBatch -> usageBatch.getRro().getName(),
                (usageBatch, value) -> usageBatch.getRro().setName(value));
        VaadinUtils.setMaxComponentsWidth(accountNameField);
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private HorizontalLayout initPaymentDataLayout() {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(initPaymentDateWidget(), initFiscalYearField());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        VaadinUtils.setMaxComponentsWidth(paymentDateWidget);
        paymentDateWidget.addValueChangeListener(event ->
            fiscalYearField.setValue(UsageBatchUtils.getFiscalYear(paymentDateWidget.getValue())));
        binder.forField(paymentDateWidget)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-field");
        return paymentDateWidget;
    }

    private TextField initFiscalYearField() {
        fiscalYearField = new TextField(ForeignUi.getMessage("label.fiscal_year"));
        fiscalYearField.setReadOnly(true);
        VaadinUtils.setMaxComponentsWidth(fiscalYearField);
        VaadinUtils.addComponentStyle(fiscalYearField, "fiscal-year-field");
        return fiscalYearField;
    }

    private HorizontalLayout initGrossAmountLayout() {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(initGrossAmountField(), new Label());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private BigDecimalField initGrossAmountField() {
        grossAmountField = new BigDecimalField(ForeignUi.getMessage("label.gross_amount_usd"));
        grossAmountField.setRequiredIndicatorVisible(true);
        binder.forField(grossAmountField)
            .withValidator(new RequiredBigDecimalValidator())
            .withValidator(AmountRangeValidator.amountValidator())
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        VaadinUtils.setMaxComponentsWidth(grossAmountField);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }
}
