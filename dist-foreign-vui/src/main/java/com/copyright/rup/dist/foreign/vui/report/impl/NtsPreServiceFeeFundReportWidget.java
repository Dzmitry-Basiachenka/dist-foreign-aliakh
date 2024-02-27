package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.vui.report.api.INtsPreServiceFeeFundReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Objects;

/**
 * Widget for exporting NTS pre-service fee fund report.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportWidget extends CommonDialog implements INtsPreServiceFeeFundReportWidget {

    private static final long serialVersionUID = -6503188590957261712L;

    private INtsPreServiceFeeFundReportController controller;
    private ComboBox<FundPool> fundPoolComboBox;
    private Button exportButton;
    private OnDemandFileDownloader downloader;

    @Override
    @SuppressWarnings("unchecked")
    public INtsPreServiceFeeFundReportWidget init() {
        setWidth("450px");
        add(initContent());
        getFooter().add(initButtonsLayout());
        setModalWindowProperties("nts-pre-service-fee-fund-report-window", false);
        return this;
    }

    @Override
    public void setController(INtsPreServiceFeeFundReportController controller) {
        this.controller = controller;
    }

    @Override
    public FundPool getFundPool() {
        return fundPoolComboBox.getValue();
    }

    private VerticalLayout initContent() {
        var content = VaadinUtils.initCommonVerticalLayout(initFundPoolComboBox());
        content.setPadding(true);
        return content;
    }

    private ComboBox<FundPool> initFundPoolComboBox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("field.fund_name"));
        fundPoolComboBox.addValueChangeListener(event -> updateExportButtonState(event.getValue()));
        fundPoolComboBox.setItems(controller.getPreServiceFeeFunds());
        fundPoolComboBox.setItemLabelGenerator(FundPool::getName);
        fundPoolComboBox.setWidthFull();
        VaadinUtils.addComponentStyle(fundPoolComboBox, "fund-name-combo-box");
        return fundPoolComboBox;
    }

    private HorizontalLayout initButtonsLayout() {
        var closeButton = Buttons.createCloseButton((Dialog) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        downloader = new OnDemandFileDownloader();
        downloader.extend(exportButton);
        return new HorizontalLayout(downloader, closeButton);
    }

    private void updateExportButtonState(FundPool fundPool) {
        exportButton.setEnabled(Objects.nonNull(fundPool));
        if (Objects.nonNull(fundPool)) {
            downloader.setResource(new CsvStreamSource(controller).getSource());
        } else {
            downloader.removeHref();
        }
    }
}
