package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.usage;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ValidationException;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;
import com.copyright.rup.dist.foreign.ui.common.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.ui.common.validator.YearValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Window for uploading a UDM usage batch with usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/21
 *
 * @author Anton Azarenka
 */
public class UdmBatchUploadWindow extends Window {

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final String[] MONTHS = new String[]{"06", "12"};
    private final Binder<UdmBatch> batchBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private final IUdmUsageController udmUsageController;
    private TextField periodYearField;
    private UploadField uploadField;
    private ComboBox<UdmChannelEnum> channelField;
    private ComboBox<UdmUsageOriginEnum> usageOriginField;
    private ComboBox<String> monthField;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageController}
     */
    public UdmBatchUploadWindow(IUdmUsageController controller) {
        this.udmUsageController = controller;
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_udm_usage_batch"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(211, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-batch-usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            try {
                UdmCsvProcessor processor = udmUsageController.getCsvProcessor();
                ProcessingResult<UdmUsage> result = processor.process(uploadField.getStreamToUploadedFile());
                if (result.isSuccessful()) {
                    int usagesCount = udmUsageController.loadUdmBatch(buildUdmBatch(), result.get());
                    close();
                    Windows.showNotificationWindow(ForeignUi.getMessage("message.upload_completed", usagesCount));
                } else {
                    Windows.showModalWindow(
                        new ErrorUploadWindow(
                            udmUsageController.getErrorResultStreamSource(uploadField.getValue(), result),
                            ForeignUi.getMessage("message.error.upload.threshold.exceeded")));
                }
            } catch (ValidationException e) {
                Windows.showNotificationWindow(ForeignUi.getMessage("window.error"), e.getHtmlMessage());
            }
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(periodYearField, uploadField, channelField, usageOriginField, monthField));
        }
    }

    /**
     * Validates input fields.
     *
     * @return {@code true} if all inputs are valid, {@code false} - otherwise
     */
    boolean isValid() {
        return batchBinder.isValid() && binder.isValid();
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addComponents(initUploadField(), initPeriodYearAndPeriodMonthFields(),
            initChannelAndUsageOriginFields(),
            buttonsLayout);
        rootLayout.setMargin(new MarginInfo(true, true, false, true));
        VaadinUtils.setMaxComponentsWidth(rootLayout);
        rootLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        binder.validate();
        return rootLayout;
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

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setRequiredIndicatorVisible(true);
        binder.forField(uploadField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .withValidator(value -> !udmUsageController.udmBatchExists(getBatchName()),
                ForeignUi.getMessage("message.error.unique_name", "UDM Batch"))
            .bind(s -> s, (s, v) -> s = v).validate();
        uploadField.addSucceededListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "udm-usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initPeriodYearAndPeriodMonthFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initPeriodYearField(), initPeriodMonthField());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField initPeriodYearField() {
        periodYearField = new TextField(ForeignUi.getMessage("label.distribution_udm_period_year"));
        periodYearField.setSizeFull();
        periodYearField.setPlaceholder("YYYY");
        periodYearField.setRequiredIndicatorVisible(true);
        binder.forField(periodYearField)
            .withValidator(new RequiredValidator())
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(new YearValidator())
            .bind(s -> s, (s, v) -> s = v).validate();
        VaadinUtils.addComponentStyle(periodYearField, "period-year-field");
        return periodYearField;
    }

    private ComboBox<String> initPeriodMonthField() {
        monthField = new ComboBox<>(ForeignUi.getMessage("label.distribution_udm_period_month"));
        monthField.setItems(MONTHS);
        monthField.setRequiredIndicatorVisible(true);
        binder.forField(monthField)
            .withValidator(new RequiredValidator())
            .bind(s -> s, (s, v) -> s = v).validate();
        monthField.setSizeFull();
        VaadinUtils.addComponentStyle(monthField, "period-month-field");
        return monthField;
    }

    private HorizontalLayout initChannelAndUsageOriginFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initUsageOriginField(), initChannelField());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private ComboBox<UdmUsageOriginEnum> initUsageOriginField() {
        usageOriginField = new ComboBox<>(ForeignUi.getMessage("label.usage_origin"));
        usageOriginField.setItems(UdmUsageOriginEnum.values());
        usageOriginField.setRequiredIndicatorVisible(true);
        batchBinder.forField(usageOriginField)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(UdmBatch::getUsageOrigin, UdmBatch::setUsageOrigin).validate();
        usageOriginField.setSizeFull();
        VaadinUtils.addComponentStyle(usageOriginField, "usage-origin-field");
        return usageOriginField;
    }

    private ComboBox<UdmChannelEnum> initChannelField() {
        channelField = new ComboBox<>(ForeignUi.getMessage("label.channel"));
        channelField.setItems(UdmChannelEnum.values());
        channelField.setRequiredIndicatorVisible(true);
        batchBinder.forField(channelField)
            .asRequired(ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(UdmBatch::getChannel, UdmBatch::setChannel).validate();
        channelField.setSizeFull();
        VaadinUtils.addComponentStyle(channelField, "usage-channel-field");
        return channelField;
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setName(getBatchName());
        udmBatch.setPeriod(Integer.parseInt(
            String.format("%s%s", StringUtils.trim(periodYearField.getValue()), monthField.getValue())));
        udmBatch.setChannel(channelField.getValue());
        udmBatch.setUsageOrigin(usageOriginField.getValue());
        return udmBatch;
    }

    private String getBatchName() {
        String fileName = uploadField.getValue();
        return StringUtils.trim(fileName.substring(0, fileName.lastIndexOf(".csv")));
    }
}
