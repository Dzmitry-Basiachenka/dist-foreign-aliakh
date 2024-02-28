package com.copyright.rup.dist.foreign.vui.report.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IUndistributedLiabilitiesReportController;
import com.copyright.rup.dist.foreign.vui.report.api.fas.IUndistributedLiabilitiesReportWidget;
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
 * Widget for exporting undistributed liabilities report.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 5/10/2018
 *
 * @author Uladzislau_Shalamitski
 */
public class UndistributedLiabilitiesReportWidget extends CommonDialog
    implements IUndistributedLiabilitiesReportWidget {

    private static final long serialVersionUID = -4109860175769744069L;

    private IUndistributedLiabilitiesReportController controller;
    private LocalDateWidget paymentDateToWidget;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IUndistributedLiabilitiesReportWidget init() {
        setWidth("650px");
        add(initContent());
        getFooter().add(getButtonsLayout());
        setModalWindowProperties("report-undistributed-liabilities-window", false);
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

    private VerticalLayout initContent() {
        initPaymentDateToFilter();
        var content = new VerticalLayout(paymentDateToWidget);
        content.setHeightFull();
        return content;
    }

    private void initPaymentDateToFilter() {
        paymentDateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.payment_date_to"));
        paymentDateToWidget.addValueChangeListener(event ->
            exportButton.setEnabled(Objects.nonNull(event.getValue())));
        VaadinUtils.addComponentStyle(paymentDateToWidget, "payment-date-filter");
    }

    private HorizontalLayout getButtonsLayout() {
        var closeButton = Buttons.createCloseButton((Dialog) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        var downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        return new HorizontalLayout(downloader, closeButton);
    }
}
