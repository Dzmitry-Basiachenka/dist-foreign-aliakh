package com.copyright.rup.dist.foreign.ui.report.impl;


import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.common.UsageBatchFilterWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ISummaryMarketReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.filter.FilterWindow.IFilterSaveListener;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Widget for exporting summary of market report.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 8/27/2018
 *
 * @author Ihar Suvorau
 */
public class SummaryMarketReportWidget extends Window implements ISummaryMarketReportWidget {

    private ISummaryMarketReportController controller;
    private UsageBatchFilterWidget filterWidget;
    private List<String> batchIds;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public ISummaryMarketReportWidget init() {
        initFilterWidget();
        VerticalLayout content = new VerticalLayout(filterWidget, getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(350, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "summary-market-report-window");
        return this;
    }

    @Override
    public void setController(ISummaryMarketReportController controller) {
        this.controller = controller;
    }

    @Override
    public List<String> getBatchIds() {
        return batchIds;
    }

    private void initFilterWidget() {
        filterWidget = new UsageBatchFilterWidget(() -> controller.getUsageBatches());
        filterWidget.addFilterSaveListener((IFilterSaveListener<UsageBatch>) saveEvent -> {
            batchIds = saveEvent.getSelectedItemsIds().stream().map(UsageBatch::getId).collect(Collectors.toList());
            exportButton.setEnabled(CollectionUtils.isNotEmpty(batchIds));
        });
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(controller.getSummaryMarketReportStreamSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSizeFull();
        return layout;
    }
}
