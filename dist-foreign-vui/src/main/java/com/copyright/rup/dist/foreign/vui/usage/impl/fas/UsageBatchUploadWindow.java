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
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.fas.IFasUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.LongField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.LongRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

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

    private UploadField uploadField;
    private LongField accountNumberField;
    private Binder.Binding<UsageBatch, Long> accountNumberBinding;
    private TextField productFamilyField;
    private TextField accountNameField;
    private LocalDateWidget paymentDateWidget;
    private TextField fiscalYearField;

    /**
     * Constructor.
     *
     * @param usagesController instance of {@link IFasUsageController}
     */
    UsageBatchUploadWindow(IFasUsageController usagesController) {
        this.usagesController = usagesController;
        super.setWidth("500px");
        super.setHeight("570px");
        super.setHeaderTitle(ForeignUi.getMessage("window.upload_usage_batch"));
        super.add(initRootLayout());
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("usage-upload-window", false);
        initBinder();
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        binder.validate();
        uploadBinder.validate();
        if (isValid()) {
            try {
                UsageCsvProcessor processor = usagesController.getCsvProcessor(productFamilyField.getValue());
                ProcessingResult<Usage> processingResult = processor.process(uploadField.getStreamToUploadedFile());
                if (processingResult.isSuccessful()) {
                    var usageBatch = binder.getBean();
                    usageBatch.setFiscalYear(UsageBatchUtils.calculateFiscalYear(paymentDateWidget.getValue()));
                    int usagesCount = usagesController.loadUsageBatch(usageBatch, processingResult.get());
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

    private void initBinder() {
        var usageBatch = new UsageBatch();
        usageBatch.setRro(new Rightsholder());
        binder.setBean(usageBatch);
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = VaadinUtils.initCommonVerticalLayout();
        rootLayout.add(initUsageBatchNameField(), initUploadField(), initRightsholderLayout(),
            initPaymentDataLayout(), initGrossAmountLayout());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        return rootLayout;
    }

    private TextField initUsageBatchNameField() {
        var usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        usageBatchNameField.setWidthFull();
        binder.forField(usageBatchNameField)
            .withValidator(requiredValidator)
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setRequiredIndicatorVisible(true);
        uploadField.setWidthFull();
        uploadBinder.forField(uploadField)
            .withValidator(requiredValidator)
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(ValueProvider.identity(), (usageBatch, value) -> usageBatch = value);
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
    }

    private VerticalLayout initRightsholderLayout() {
        var rroAccountLayout =
            new HorizontalLayout(initRightsholderAccountNumberField(), initProductFamilyField(), initVerifyButton());
        rroAccountLayout.setWidthFull();
        rroAccountLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return VaadinUtils.initCommonVerticalLayout(rroAccountLayout, initRightsholderAccountNameField());
    }

    private LongField initRightsholderAccountNumberField() {
        accountNumberField = new LongField(ForeignUi.getMessage("label.rro_account_number"));
        accountNumberField.setWidth("50%");
        accountNumberField.setRequiredIndicatorVisible(true);
        VaadinUtils.addComponentStyle(accountNumberField, "rro-account-number-field");
        accountNumberField.addValueChangeListener(event -> {
            accountNameField.setValue(StringUtils.EMPTY);
            productFamilyField.setValue(StringUtils.EMPTY);
        });
        accountNumberBinding = binder.forField(accountNumberField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(
                new LongRangeValidator(ForeignUi.getMessage("field.error.number_length", 10), 1L, 9999999999L))
            .bind(usageBatch -> usageBatch.getRro().getAccountNumber(),
                (usageBatch, value) -> usageBatch.getRro().setAccountNumber(value));
        return accountNumberField;
    }

    private TextField initProductFamilyField() {
        productFamilyField = new TextField(ForeignUi.getMessage("label.product_family"));
        productFamilyField.setReadOnly(true);
        productFamilyField.setWidth("130px");
        binder.forField(productFamilyField)
            .bind(UsageBatch::getProductFamily, UsageBatch::setProductFamily);
        VaadinUtils.addComponentStyle(productFamilyField, "product-family-field");
        return productFamilyField;
    }

    private Button initVerifyButton() {
        var button = Buttons.createButton(ForeignUi.getMessage("button.verify"));
        button.setWidth("72px");
        button.addClickListener(event -> {
            if (BindingValidationStatus.Status.OK == accountNumberBinding.validate().getStatus()) {
                var rro = usagesController.getRightsholder(accountNumberField.getValue());
                if (StringUtils.isNotBlank(rro.getName())) {
                    accountNameField.setValue(rro.getName());
                    productFamilyField.setValue(
                        usagesController.getClaAccountNumber().equals(rro.getAccountNumber())
                            ? FdaConstants.CLA_FAS_PRODUCT_FAMILY : FdaConstants.FAS_PRODUCT_FAMILY);
                    binder.getBean().getRro().setId(rro.getId());
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
        accountNameField.setWidthFull();
        binder.forField(accountNameField)
            .asRequired(ForeignUi.getMessage("field.error.rro_name.empty"))
            .bind(usageBatch -> usageBatch.getRro().getName(),
                (usageBatch, value) -> usageBatch.getRro().setName(value));
        VaadinUtils.addComponentStyle(accountNameField, "rro-account-name-field");
        return accountNameField;
    }

    private HorizontalLayout initPaymentDataLayout() {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(initPaymentDateWidget(), initFiscalYearField());
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }

    private LocalDateWidget initPaymentDateWidget() {
        paymentDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date"));
        paymentDateWidget.addValueChangeListener(event ->
            fiscalYearField.setValue(UsageBatchUtils.getFiscalYear(paymentDateWidget.getValue())));
        paymentDateWidget.setWidthFull();
        binder.forField(paymentDateWidget)
            .asRequired(ForeignUi.getMessage("field.error.empty"))
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(paymentDateWidget, "payment-date-field");
        return paymentDateWidget;
    }

    private TextField initFiscalYearField() {
        fiscalYearField = new TextField(ForeignUi.getMessage("label.fiscal_year"));
        fiscalYearField.setReadOnly(true);
        fiscalYearField.setWidthFull();
        VaadinUtils.addComponentStyle(fiscalYearField, "fiscal-year-field");
        return fiscalYearField;
    }

    private HorizontalLayout initGrossAmountLayout() {
        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(initGrossAmountField());
        horizontalLayout.setWidth("50%");
        return horizontalLayout;
    }

    private BigDecimalField initGrossAmountField() {
        var grossAmountField = new BigDecimalField(ForeignUi.getMessage("label.gross_amount_usd"));
        grossAmountField.setPrefixComponent(VaadinIcon.DOLLAR.create());
        grossAmountField.setRequiredIndicatorVisible(true);
        grossAmountField.setWidthFull();
        binder.forField(grossAmountField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(AmountRangeValidator.amountValidator())
            .bind(UsageBatch::getGrossAmount, UsageBatch::setGrossAmount);
        VaadinUtils.addComponentStyle(grossAmountField, "gross-amount-field");
        return grossAmountField;
    }
}
