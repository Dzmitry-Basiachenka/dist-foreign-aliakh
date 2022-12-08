package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmSurveyDashboardReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;

/**
 * Implementation of {@link IUdmSurveyDashboardReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/07/2022
 *
 * @author Anton Azarenka
 */
public class UdmSurveyDashboardReportWidget extends Window implements IUdmSurveyDashboardReportWidget {

    private IUdmSurveyDashboardReportController controller;
    private Grid<Integer> grid;
    private Button exportButton;

    @Override
    public Set<Integer> getSelectedPeriods() {
        return grid.getSelectedItems();
    }

    @Override
    public IUdmSurveyDashboardReportWidget init() {
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "udm-survey-dashboard-report-window");
        initContent();
        return this;
    }

    @Override
    public void setController(IUdmSurveyDashboardReportController controller) {
        this.controller = controller;
    }

    private void initContent() {
        ListDataProvider<Integer> dataProvider = new ListDataProvider<>(controller.getPeriods());
        grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        grid.setHeight(220, Unit.PIXELS);
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addColumn(value -> value)
            .setCaption(ForeignUi.getMessage("table.column.periods"));
        grid.addSelectionListener(
            event -> exportButton.setEnabled(CollectionUtils.isNotEmpty(grid.getSelectedItems())));
        VaadinUtils.addComponentStyle(grid, "udm-survey-dashboard-report-grid");
        VerticalLayout content = new VerticalLayout(grid, getButtonsLayout());
        setContent(content);
        VaadinUtils.setMaxComponentsWidth(content);
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton(this);
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setSizeFull();
        return layout;
    }
}
