package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasServiceFeeTrueUpReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IFasServiceFeeTrueUpReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.LocalDateWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

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
public class FasServiceFeeTrueUpReportWidget extends CommonDialog implements IFasServiceFeeTrueUpReportWidget {

    private static final long serialVersionUID = -8970184754589050227L;

    private IFasServiceFeeTrueUpReportController controller;
    private LocalDateWidget paymentDateToWidget;
    private LocalDateWidget toDateWidget;
    private LocalDateWidget fromDateWidget;
    private Button exportButton;
    private OnDemandFileDownloader downloader;

    @Override
    @SuppressWarnings("unchecked")
    public IFasServiceFeeTrueUpReportWidget init() {
        setWidth("400px");
        add(initContent());
        getFooter().add(getButtonsLayout());
        setModalWindowProperties("report-service-fee-true-up-window", false);
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

    private VerticalLayout initContent() {
        initDatesFilters();
        var content = VaadinUtils.initSizeFullVerticalLayout(fromDateWidget, toDateWidget, paymentDateToWidget);
        content.setPadding(true);
        return content;
    }

    private void initDatesFilters() {
        toDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.to_date"));
        toDateWidget.setValue(LocalDate.now());
        fromDateWidget = new LocalDateWidget(ForeignUi.getMessage("label.from_date"));
        fromDateWidget.addValueChangeListener(event -> {
            boolean isDateValueNotNull = Objects.nonNull(event.getValue());
            exportButton.setEnabled(isDateValueNotNull);
            if (isDateValueNotNull) {
                downloader.setResource(new CsvStreamSource(controller).getSource());
            } else {
                downloader.removeHref();
            }
        });
        paymentDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateToWidget.setValue(LocalDate.now());
        VaadinUtils.addComponentStyle(toDateWidget, "to-date-filter");
        VaadinUtils.addComponentStyle(fromDateWidget, "from-date-filter");
        VaadinUtils.addComponentStyle(paymentDateToWidget, "payment-date-to-filter");
    }

    private HorizontalLayout getButtonsLayout() {
        var closeButton = Buttons.createCloseButton((Dialog) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        downloader = new OnDemandFileDownloader();
        downloader.extend(exportButton);
        return new HorizontalLayout(downloader, closeButton);
    }
}
