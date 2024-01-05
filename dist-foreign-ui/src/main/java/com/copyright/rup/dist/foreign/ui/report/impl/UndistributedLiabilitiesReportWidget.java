package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUndistributedLiabilitiesReportWidget;
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
 * Widget for exporting undistributed liabilities report.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportWidget extends Window implements IUndistributedLiabilitiesReportWidget {

    private static final long serialVersionUID = -4109860175769744069L;

    private IUndistributedLiabilitiesReportController controller;
    private LocalDateWidget paymentDateToWidget;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IUndistributedLiabilitiesReportWidget init() {
        initPaymentDateToFilter();
        VerticalLayout content = new VerticalLayout(paymentDateToWidget, getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(350, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "report-undistributed-liabilities-window");
        return this;
    }

    @Override
    public void setController(IUndistributedLiabilitiesReportController controller) {
        this.controller = controller;
    }

    @Override
    public LocalDate getPaymentDate() {
        return paymentDateToWidget.getValue();
    }

    private void initPaymentDateToFilter() {
        paymentDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateToWidget.addValueChangeListener(event ->
            exportButton.setEnabled(Objects.nonNull(event.getValue())));
        VaadinUtils.addComponentStyle(paymentDateToWidget, "payment-date-filter");
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
