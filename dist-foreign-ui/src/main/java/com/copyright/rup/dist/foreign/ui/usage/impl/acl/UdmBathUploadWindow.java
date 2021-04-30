package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.upload.UploadField;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.server.SerializablePredicate;
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
 * Window for uploading a usage batch with usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 04/28/21
 *
 * @author Anton Azarenka
 */
public class UdmBathUploadWindow extends Window {

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;
    private static final String[] MONTHES = new String[]{"06", "12"};
    private final Binder<UdmBatch> batchBinder = new Binder<>();
    private final Binder<String> binder = new Binder<>();
    private TextField periodYearField;
    private UploadField uploadField;
    private ComboBox<UdmChannelEnum> channelField;
    private ComboBox<UdmUsageOriginEnum> usageOriginField;
    private ComboBox<String> monthfield;

    /**
     * Constructor.
     */
    public UdmBathUploadWindow() {
        setContent(initRootLayout());
        setCaption(ForeignUi.getMessage("window.upload_udm_usage_batch"));
        setResizable(false);
        setWidth(400, Unit.PIXELS);
        setHeight(211, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "usage-upload-window");
    }

    /**
     * Initiates file uploading.
     */
    void onUploadClicked() {
        if (isValid()) {
            return;//TODO {aazarenka} will implemented later
        } else {
            Windows.showValidationErrorWindow(
                Arrays.asList(periodYearField, uploadField, channelField, usageOriginField, monthfield));
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
        rootLayout.addComponents(initUploadField(), initPeriodYearAndPeriodMonthFields(), initChannelAndUsageOrigin(),
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
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(uploadButton, closeButton);
        return horizontalLayout;
    }

    private UploadField initUploadField() {
        uploadField = new UploadField();
        uploadField.setRequiredIndicatorVisible(true);
        binder.forField(uploadField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.endsWith(value, ".csv"),
                ForeignUi.getMessage("error.upload_file.invalid_extension"))
            .bind(s -> s, (s, v) -> s = v).validate();
        uploadField.addSucceededListener(event -> binder.validate());
        VaadinUtils.setMaxComponentsWidth(uploadField);
        VaadinUtils.addComponentStyle(uploadField, "usage-upload-component");
        return uploadField;
    }

    private HorizontalLayout initPeriodYearAndPeriodMonthFields() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(intiPeriodYearField(), initPeriodMonthField());
        horizontalLayout.setSizeFull();
        return horizontalLayout;
    }

    private TextField intiPeriodYearField() {
        periodYearField = new TextField(ForeignUi.getMessage("label.distribution_udm_period_year"));
        periodYearField.setSizeFull();
        periodYearField.setPlaceholder("YYYY");
        periodYearField.setRequiredIndicatorVisible(true);
        binder.forField(periodYearField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                "Field value should contain numeric values only")
            .withValidator(getYearValidator(), "Field value should be in range from 1950 to 2099")
            .bind(s -> s, (s, v) -> s = v).validate();
        VaadinUtils.addComponentStyle(periodYearField, "distribution-udm-period-year-field");
        return periodYearField;
    }

    private ComboBox<String> initPeriodMonthField() {
        monthfield = new ComboBox<>(ForeignUi.getMessage("label.distribution_udm_period_month"));
        monthfield.setItems(MONTHES);
        monthfield.setRequiredIndicatorVisible(true);
        binder.forField(monthfield)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .bind(s -> s, (s, v) -> s = v).validate();
        monthfield.setSizeFull();
        VaadinUtils.addComponentStyle(monthfield, "usage-origin-filter");
        return monthfield;
    }

    private HorizontalLayout initChannelAndUsageOrigin() {
        HorizontalLayout horizontalLayout = new HorizontalLayout(initChannelField(), initUsageOriginField());
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
        VaadinUtils.addComponentStyle(usageOriginField, "usage-origin-filter");
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
        VaadinUtils.addComponentStyle(channelField, "usage-channel-filter");
        return channelField;
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(StringUtils.trim(value)) >= MIN_YEAR
            && Integer.parseInt(StringUtils.trim(value)) <= MAX_YEAR;
    }
}
