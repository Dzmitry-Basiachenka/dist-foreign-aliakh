package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IFasServiceFeeTrueUpReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

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
public class FasServiceFeeTrueUpReportWidget extends Window implements IFasServiceFeeTrueUpReportWidget {

    private static final long serialVersionUID = -8970184754589050227L;

    private IFasServiceFeeTrueUpReportController controller;
    private LocalDateWidget paymentDateToWidget;
    private LocalDateWidget toDateWidget;
    private LocalDateWidget fromDateWidget;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IFasServiceFeeTrueUpReportWidget init() {
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
    public void setController(IFasServiceFeeTrueUpReportController controller) {
        this.controller = controller;
    }

    @Override
    public LocalDate getFromDate() {
        return fromDateWidget.getValue();
    }

    @Override
    public LocalDate getToDate() {
        return toDateWidget.getValue();
    }

    @Override
    public LocalDate getPaymentDateTo() {
        return paymentDateToWidget.getValue();
    }

    private void initDatesFilters() {
        toDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.to_date"));
        toDateWidget.setValue(LocalDate.now());
        fromDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.from_date"));
        fromDateWidget.addValueChangeListener(event -> exportButton.setEnabled(Objects.nonNull(event.getValue())));
        paymentDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateToWidget.setValue(LocalDate.now());
        VaadinUtils.addComponentStyle(toDateWidget, "to-date-filter");
        VaadinUtils.addComponentStyle(fromDateWidget, "from-date-filter");
        VaadinUtils.addComponentStyle(paymentDateToWidget, "payment-date-to-filter");
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
}
