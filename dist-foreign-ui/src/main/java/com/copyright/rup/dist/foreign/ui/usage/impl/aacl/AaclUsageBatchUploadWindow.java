package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * Window for uploading AACL usage batch with usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/19
 *
 * @author Ihar Suvorau
 */
public class AaclUsageBatchUploadWindow extends Window {

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private final IAaclUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private TextField periodEndDateField;
    private TextField usageBatchNameField;
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController {@link IAaclUsageController} instance
     */
    public AaclUsageBatchUploadWindow(IAaclUsageController usagesController) {
        this.usagesController = usagesController;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_usage_batch"));
        setResizable(false);
        setWidth(380, Unit.PIXELS);
        setHeight(210, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                AaclUsageCsvProcessor processor = usagesController.getCsvProcessor();
                if (StringUtils.isNotEmpty(uploadField.getValue())) {
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
                } else {
                    usagesController.loadUsageBatch(buildUsageBatch(), Collections.emptyList());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", 0));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getValue(), e.getProcessingResult()),
                        e.getMessage() + "<br>Press Download button to see detailed list of errors"));
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(Arrays.asList(usageBatchNameField, uploadField, periodEndDateField));
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
        rootLayout.addComponents(initUsageBatchNameField(), initUploadField(), initPeriodEndDateField(), buttonsLayout);
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

    private TextField initUsageBatchNameField() {
        usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        binder.forField(usageBatchNameField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        usageBatchNameField.setSizeFull();
        VaadinUtils.setMaxComponentsWidth(usageBatchNameField);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private TextField initPeriodEndDateField() {
        periodEndDateField = new TextField(ForeignUi.getMessage("label.distribution_period"));
        periodEndDateField.setRequiredIndicatorVisible(true);
        binder.forField(periodEndDateField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                "Field value should contain numeric values only")
            .withValidator(getYearValidator(), "Field value should be in range from 1950 to 2099")
            .withConverter(new LocalDateConverter())
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(periodEndDateField, "distribution-period-field");
        periodEndDateField.setWidth(50, Unit.PERCENTAGE);
        return periodEndDateField;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(convertYearToDate(periodEndDateField.getValue()));
        return usageBatch;
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }

    private LocalDate convertYearToDate(String value) {
        return LocalDate.of(Integer.parseInt(value), 6, 30);
    }

    private class LocalDateConverter implements Converter<String, LocalDate> {

        @Override
        public Result<LocalDate> convertToModel(String value, ValueContext context) {
            return Result.ok(convertYearToDate(value));
        }

        @Override
        public String convertToPresentation(LocalDate value, ValueContext context) {
            return String.valueOf(value.getYear());
        }
    }
}
