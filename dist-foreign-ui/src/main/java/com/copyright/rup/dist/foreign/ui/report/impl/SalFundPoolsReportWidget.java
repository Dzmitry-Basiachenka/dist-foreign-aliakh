package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISalFundPoolsReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of controller for {@link ISalFundPoolsReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/05/20
 *
 * @author Anton Azarenka
 */
public class SalFundPoolsReportWidget extends Window implements ISalFundPoolsReportWidget {

    private static final String EMPTY_FIELD_MESSAGE = "field.error.empty";
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2099;

    private ISalFundPoolsReportController controller;
    private TextField reportedYear;
    private final Binder<Integer> binder = new Binder<>();
    private Button exportButton;

    @Override
    public ISalFundPoolsReportWidget init() {
        initReportedYear();
        VerticalLayout content = new VerticalLayout(reportedYear, getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "fund-pools-report-window");
        return this;
    }

    @Override
    public void setController(ISalFundPoolsReportController controller) {
        this.controller = controller;
    }

    @Override
    public int getDistributionYear() {
        return Integer.parseInt(StringUtils.trim(reportedYear.getValue()));
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSizeFull();
        return layout;
    }

    private void initReportedYear() {
        reportedYear = new TextField(ForeignUi.getMessage("label.distribution_year"));
        reportedYear.setRequiredIndicatorVisible(true);
        binder.forField(reportedYear)
            .withValidator(StringUtils::isNotBlank, ForeignUi.getMessage(EMPTY_FIELD_MESSAGE))
            .withValidator(value -> StringUtils.isNumeric(StringUtils.trim(value)),
                "Field value should contain numeric values only")
            .withValidator(getYearValidator(), "Field value should be in range from 1950 to 2099")
            .withConverter(new StringToIntegerConverter("Field value can not be converted"))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
        VaadinUtils.addComponentStyle(reportedYear, "fund-pools-field-report-window");
        reportedYear.setWidth(100, Unit.PERCENTAGE);
        reportedYear.addValueChangeListener(value -> exportButton.setEnabled(binder.isValid()));
    }

    private SerializablePredicate<String> getYearValidator() {
        return value -> Integer.parseInt(StringUtils.trim(value)) >= MIN_YEAR
            && Integer.parseInt(StringUtils.trim(value)) <= MAX_YEAR;
    }
}
