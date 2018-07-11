package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IServiceFeeTrueUpReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.google.common.base.MoreObjects;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Widget for exporting service fee true-up report.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 7/11/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class ServiceFeeTrueUpReportWidget extends Window implements IServiceFeeTrueUpReportWidget {

    private IServiceFeeTrueUpReportController controller;
    private LocalDateWidget paymentDateToWidget;
    private LocalDateWidget toDateWidget;
    private LocalDateWidget fromDateWidget;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IServiceFeeTrueUpReportWidget init() {
        initDatesFilters();
        VerticalLayout content =
            new VerticalLayout(fromDateWidget, toDateWidget, paymentDateToWidget, getButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(350, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "report-service-fee-true-up-window");
        return this;
    }

    @Override
    public void setController(IServiceFeeTrueUpReportController controller) {
        this.controller = controller;
    }

    @Override
    public LocalDate getFromDate() {
        return fromDateWidget.getValue();
    }

    @Override
    public LocalDate getToDate() {
        return MoreObjects.firstNonNull(toDateWidget.getValue(), LocalDate.now());
    }

    @Override
    public LocalDate getPaymentDateTo() {
        return MoreObjects.firstNonNull(paymentDateToWidget.getValue(), LocalDate.now());
    }

    private void initDatesFilters() {
        toDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.to_date"));
        fromDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.from_date"));
        paymentDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        fromDateWidget.addValueChangeListener(event -> exportButton.setEnabled(Objects.nonNull(event.getValue())));
        VaadinUtils.addComponentStyle(toDateWidget, "to-date-filter");
        VaadinUtils.addComponentStyle(fromDateWidget, "from-date-filter");
        VaadinUtils.addComponentStyle(paymentDateToWidget, "payment-date-to-filter");
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader =
            new OnDemandFileDownloader(controller.getServiceFeeTrueUpReportStreamSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSizeFull();
        return layout;
    }
}
