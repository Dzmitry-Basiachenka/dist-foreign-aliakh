package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportController;
import com.copyright.rup.dist.foreign.ui.report.api.INtsPreServiceFeeFundReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Widget for exporting NTS pre-service fee fund report.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 01/31/2024
 *
 * @author Dzmitry Basiachenka
 */
public class NtsPreServiceFeeFundReportWidget extends Window implements INtsPreServiceFeeFundReportWidget {

    private static final long serialVersionUID = -6503188590957261712L;

    private INtsPreServiceFeeFundReportController controller;
    private ComboBox<FundPool> fundPoolComboBox;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public INtsPreServiceFeeFundReportWidget init() {
        setWidth("450px");
        setResizable(false);
        var content = new VerticalLayout(initFundPoolComboBox(), initButtonsLayout());
        content.setSpacing(false);
        setContent(content);
        VaadinUtils.addComponentStyle(this, "nts-pre-service-fee-fund-report-window");
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

    private ComboBox<FundPool> initFundPoolComboBox() {
        fundPoolComboBox = new ComboBox<>(ForeignUi.getMessage("field.fund_name"));
        fundPoolComboBox.setEmptySelectionAllowed(false);
        fundPoolComboBox.setTextInputAllowed(false);
        fundPoolComboBox.addValueChangeListener(event -> exportButton.setEnabled(true));
        fundPoolComboBox.setItems(controller.getPreServiceFeeFunds());
        fundPoolComboBox.setItemCaptionGenerator(FundPool::getName);
        VaadinUtils.setMaxComponentsWidth(fundPoolComboBox);
        VaadinUtils.addComponentStyle(fundPoolComboBox, "fund-name-combo-box");
        return fundPoolComboBox;
    }

    private HorizontalLayout initButtonsLayout() {
        var closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        var downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        var buttonsLayout = new HorizontalLayout(exportButton, closeButton);
        buttonsLayout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        buttonsLayout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        buttonsLayout.setMargin(new MarginInfo(true, false, false, false));
        buttonsLayout.setSizeFull();
        return buttonsLayout;
    }
}
