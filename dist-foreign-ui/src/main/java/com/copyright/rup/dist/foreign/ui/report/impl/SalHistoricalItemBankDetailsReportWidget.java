package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.report.SalLicensee;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalHistoricalItemBankDetailsReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.Binder;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of {@link ISalHistoricalItemBankDetailsReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/25/2020
 *
 * @author Aliaksandr Liakh
 */
public class SalHistoricalItemBankDetailsReportWidget extends Window
    implements ISalHistoricalItemBankDetailsReportWidget {

    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private final Binder<String> stringBinder = new Binder<>();

    private ISalHistoricalItemBankDetailsReportController controller;
    private TextField periodEndDateFromField;
    private TextField periodEndDateToField;
    private ComboBox<SalLicensee> licenseeComboBox;
    private Button exportButton;

    @Override
    public void setController(ISalHistoricalItemBankDetailsReportController controller) {
        this.controller = controller;
    }

    @Override
    @SuppressWarnings("unchecked")
    public SalHistoricalItemBankDetailsReportWidget init() {
        initPeriodEndDateFromField();
        initPeriodEndDateToField();
        HorizontalLayout horizontalLayout = new HorizontalLayout(periodEndDateFromField, periodEndDateToField);
        horizontalLayout.setSizeFull();
        HorizontalLayout buttonsLayout = getButtonsLayout();
        VerticalLayout content = new VerticalLayout(getLicenseeCombobox(), horizontalLayout, buttonsLayout);
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(400, Unit.PIXELS);
        setResizable(false);
        return this;
    }

    @Override
    public Long getLicenseeAccountNumber() {
        return licenseeComboBox.getValue().getAccountNumber();
    }

    @Override
    public int getPeriodEndYearFrom() {
        return Integer.parseInt(periodEndDateFromField.getValue());
    }

    @Override
    public int getPeriodEndYearTo() {
        return Integer.parseInt(periodEndDateToField.getValue());
    }

    private void initPeriodEndDateFromField() {
        periodEndDateFromField = new TextField(ForeignUi.getMessage("field.period_end_date_from"));
        periodEndDateFromField.setRequiredIndicatorVisible(true);
        periodEndDateFromField.setSizeFull();
        periodEndDateFromField.addValueChangeListener(event -> {
            stringBinder.validate();
            exportButton.setEnabled(isValid());
        });
        VaadinUtils.addComponentStyle(periodEndDateFromField, "period-end-date-from-field");
        stringBinder.forField(periodEndDateFromField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(getNumericValidator(), ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(getYearValidator(), ForeignUi.getMessage("field.error.number_not_in_range",
                MIN_YEAR, MAX_YEAR))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
    }

    private void initPeriodEndDateToField() {
        periodEndDateToField = new TextField(ForeignUi.getMessage("field.period_end_date_to"));
        periodEndDateToField.setRequiredIndicatorVisible(true);
        periodEndDateToField.setSizeFull();
        periodEndDateToField.addValueChangeListener(event -> {
            stringBinder.validate();
            exportButton.setEnabled(isValid());
        });
        VaadinUtils.addComponentStyle(periodEndDateToField, "period-end-date-to-field");
        stringBinder.forField(periodEndDateToField)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage("field.error.empty"))
            .withValidator(getNumericValidator(), ForeignUi.getMessage("field.error.not_numeric"))
            .withValidator(getYearValidator(), ForeignUi.getMessage("field.error.number_not_in_range",
                MIN_YEAR, MAX_YEAR))
            .withValidator(value -> {
                String periodFrom = periodEndDateFromField.getValue();
                return StringUtils.isEmpty(periodFrom)
                    || !getNumericValidator().test(periodFrom)
                    || !getYearValidator().test(periodFrom)
                    || 0 <= periodEndDateToField.getValue().compareTo(periodFrom);
            }, ForeignUi.getMessage("field.error.greater_or_equal_to",
                ForeignUi.getMessage("field.period_end_date_from")))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
    }

    private Component getLicenseeCombobox() {
        licenseeComboBox = new ComboBox<>(ForeignUi.getMessage("field.licensee"));
        licenseeComboBox.setRequiredIndicatorVisible(true);
        licenseeComboBox.addValueChangeListener(event -> exportButton.setEnabled(isValid()));
        licenseeComboBox.setItems(controller.getSalLicensees());
        licenseeComboBox.setItemCaptionGenerator(licensee -> licensee.getAccountNumber() + " - " + licensee.getName());
        VaadinUtils.setMaxComponentsWidth(licenseeComboBox);
        VaadinUtils.addComponentStyle(licenseeComboBox, "sal-licensee-filter");
        return licenseeComboBox;
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> {
            licenseeComboBox.clear();
            periodEndDateFromField.clear();
            periodEndDateToField.clear();
        });
        HorizontalLayout layout = new HorizontalLayout(exportButton, clearButton, closeButton);
        layout.setMargin(new MarginInfo(true, false, false, false));
        return layout;
    }

    private SerializablePredicate<String> getNumericValidator() {
        return value -> StringUtils.isNumeric(StringUtils.trim(value));
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(value) >= MIN_YEAR && Integer.parseInt(value) <= MAX_YEAR;
    }

    private boolean isValid() {
        return !licenseeComboBox.isEmpty() && stringBinder.isValid();
    }
}
