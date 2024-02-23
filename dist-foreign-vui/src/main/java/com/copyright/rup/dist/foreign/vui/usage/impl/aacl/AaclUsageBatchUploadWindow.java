package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ThresholdExceededException;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.common.converter.LocalDateConverter;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredNumberValidator;
import com.copyright.rup.dist.foreign.vui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.usage.impl.ErrorUploadWindow;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.upload.UploadField;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.RangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Window for uploading AACL usage batch with usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/2019
 *
 * @author Ihar Suvorau
 */
public class AaclUsageBatchUploadWindow extends CommonDialog {

    private static final long serialVersionUID = 9176666947089144170L;
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;
    private static final String YEAR_ERROR_MESSAGE =
        ForeignUi.getMessage("field.error.number_not_in_range", MIN_YEAR, MAX_YEAR);

    private final IAaclUsageController usagesController;
    private final Binder<UsageBatch> binder = new Binder<>();
    private final Binder<String> uploadBinder = new Binder<>();
    private UploadField uploadField;

    /**
     * Constructor.
     *
     * @param usagesController instance of {@link IAaclUsageController}
     */
    public AaclUsageBatchUploadWindow(IAaclUsageController usagesController) {
        this.usagesController = usagesController;
        super.setWidth("500px");
        super.setHeight("420px");
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
                if (StringUtils.isNotEmpty(uploadField.getValue())) {
                    var processor = usagesController.getCsvProcessor();
                    var processingResult = processor.process(uploadField.getStreamToUploadedFile());
                    if (processingResult.isSuccessful()) {
                        int totalCount = usagesController.loadUsageBatch(binder.getBean(), processingResult.get());
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
                    int baselineUsagesCount = usagesController.loadUsageBatch(binder.getBean(), List.of());
                    close();
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.upload_aacl_batch_completed", 0, baselineUsagesCount));
                }
            } catch (ThresholdExceededException e) {
                Windows.showModalWindow(
                    new ErrorUploadWindow(
                        usagesController.getErrorResultStreamSource(uploadField.getValue(), e.getProcessingResult()),
                        String.format("%s%s", e.getMessage(),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded"))));
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
        usageBatch.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
        binder.setBean(usageBatch);
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = new VerticalLayout();
        rootLayout.add(initUsageBatchNameField(), initUploadField(), initPeriodEndDateAndNumberOfBaselineYearsField());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        return rootLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setSizeFull();
        uploadField.addSucceededListener(event -> uploadBinder.validate());
        uploadBinder.forField(uploadField)
            .withValidator(value -> StringUtils.isEmpty(value)
                || StringUtils.endsWith(value, ".csv"), ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(ValueProvider.identity(), (bean, value) -> bean = value)
            .validate();
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initButtonsLayout() {
        var uploadButton = Buttons.createButton(ForeignUi.getMessage("button.upload"));
        uploadButton.addClickListener(event -> onUploadClicked());
        VaadinUtils.setButtonsAutoDisabled(uploadButton);
        return new HorizontalLayout(uploadButton, Buttons.createCloseButton(this));
    }

    private TextField initUsageBatchNameField() {
        var usageBatchNameField = new TextField(ForeignUi.getMessage("label.usage_batch_name"));
        usageBatchNameField.setRequiredIndicatorVisible(true);
        usageBatchNameField.setSizeFull();
        binder.forField(usageBatchNameField)
            .withValidator(new RequiredValidator())
            .withValidator(new StringLengthValidator(ForeignUi.getMessage("field.error.length", 50), 0, 50))
            .withValidator(value -> !usagesController.usageBatchExists(StringUtils.trimToEmpty(value)),
                ForeignUi.getMessage("message.error.unique_name", "Usage Batch"))
            .bind(UsageBatch::getName, UsageBatch::setName);
        VaadinUtils.addComponentStyle(usageBatchNameField, "usage-batch-name-field");
        return usageBatchNameField;
    }

    private HorizontalLayout initPeriodEndDateAndNumberOfBaselineYearsField() {
        var horizontalLayout = new HorizontalLayout(initPeriodEndDateField(), initNumberOfBaselineYearsFields());
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }

    private IntegerField initPeriodEndDateField() {
        var periodEndDateField = new IntegerField(ForeignUi.getMessage("label.distribution_period"));
        periodEndDateField.setRequiredIndicatorVisible(true);
        periodEndDateField.setSizeFull();
        binder.forField(periodEndDateField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(RangeValidator.of(YEAR_ERROR_MESSAGE, MIN_YEAR, MAX_YEAR))
            .withConverter(new LocalDateConverter())
            .bind(UsageBatch::getPaymentDate, UsageBatch::setPaymentDate);
        VaadinUtils.addComponentStyle(periodEndDateField, "distribution-period-field");
        return periodEndDateField;
    }

    private IntegerField initNumberOfBaselineYearsFields() {
        var numberOfBaselineYearsField = new IntegerField(ForeignUi.getMessage("label.number_of_baseline_years"));
        numberOfBaselineYearsField.setRequiredIndicatorVisible(true);
        numberOfBaselineYearsField.setSizeFull();
        binder.forField(numberOfBaselineYearsField)
            .withValidator(new RequiredNumberValidator())
            .withValidator(value -> value >= 0, ForeignUi.getMessage("field.error.positive_number"))
            .bind(UsageBatch::getNumberOfBaselineYears, UsageBatch::setNumberOfBaselineYears);
        VaadinUtils.addComponentStyle(numberOfBaselineYearsField, "number-of-baseline-years-field");
        return numberOfBaselineYearsField;
    }
}
