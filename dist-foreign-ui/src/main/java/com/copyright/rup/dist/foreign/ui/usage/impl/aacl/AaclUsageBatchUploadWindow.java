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
import com.copyright.rup.dist.foreign.ui.usage.impl.LocalDateConverter;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
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
    private TextField numberOfBaselineYears;
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
                        int totalCount = usagesController.loadUsageBatch(buildUsageBatch(), processingResult.get());
                        int uploadedCount = processingResult.get().size();
                        close();
                        Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_aacl_batch_completed",
                            uploadedCount, totalCount - uploadedCount));
                    } else {
                        Windows.showModalWindow(
                            new ErrorUploadWindow(
                                usagesController.getErrorResultStreamSource(uploadField.getValue(), processingResult),
                                ForeignUi.getMessage("message.error.upload")));
                    }
                } else {
                    int baselineUsagesCount =
                        usagesController.loadUsageBatch(buildUsageBatch(), Collections.emptyList());
                    close();
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.upload_aacl_batch_completed", 0, baselineUsagesCount));
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
            Windows.showValidationErrorWindow(
                Arrays.asList(usageBatchNameField, uploadField, periodEndDateField, numberOfBaselineYears));
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
        rootLayout.addComponents(initUsageBatchNameField(), initUploadField(),
            initPeriodEndDateAndNumberOfBaselineYearsField(), buttonsLayout);
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

    private HorizontalLayout initPeriodEndDateAndNumberOfBaselineYearsField() {
        initPeriodEndDateField();
        initNumberOfBaselineYearsFields();
        HorizontalLayout horizontalLayout = new HorizontalLayout(periodEndDateField, numberOfBaselineYears);
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private void initPeriodEndDateField() {
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
        periodEndDateField.setWidth(100, Unit.PERCENTAGE);
    }

    private void initNumberOfBaselineYearsFields() {
        numberOfBaselineYears = new TextField(ForeignUi.getMessage("label.number_of_baseline_years"));
        numberOfBaselineYears.setRequiredIndicatorVisible(true);
        binder.forField(numberOfBaselineYears)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)) || Integer.parseInt(
                StringUtils.trim(value)) >= 0, "Field value should contain positive numeric values only")
            .withConverter(new StringToIntegerConverter("Field value can not be converted"))
            .bind(UsageBatch::getNumberOfBaselineYears, UsageBatch::setNumberOfBaselineYears);
        VaadinUtils.addComponentStyle(numberOfBaselineYears, "number-of-baseline-years-field");
        numberOfBaselineYears.setWidth(100, Unit.PERCENTAGE);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName(StringUtils.trim(usageBatchNameField.getValue()));
        usageBatch.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(LocalDate.of(Integer.parseInt(periodEndDateField.getValue()), 6, 30));
        usageBatch.setNumberOfBaselineYears(Integer.parseInt(StringUtils.trim(numberOfBaselineYears.getValue())));
        return usageBatch;
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }
}
