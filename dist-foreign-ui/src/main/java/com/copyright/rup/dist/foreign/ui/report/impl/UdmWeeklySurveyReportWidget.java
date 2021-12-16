package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportWidget;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of controller for {@link IUdmWeeklySurveyReportWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/15/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmWeeklySurveyReportWidget extends Window implements IUdmWeeklySurveyReportWidget {

    private IUdmWeeklySurveyReportController controller;
    // TODO define the Channels, Usage Origins, Periods widgets
    private LocalDateWidget dateReceivedFromWidget;
    private LocalDateWidget dateReceivedToWidget;
    private Button exportButton;
    private final Set<String> channels = new HashSet<>();
    private final Set<String> usageOrigins = new HashSet<>();
    private final Set<Integer> periods = new HashSet<>();

    @Override
    @SuppressWarnings("unchecked")
    public IUdmWeeklySurveyReportWidget init() {
        initFilters();
        VerticalLayout content = new VerticalLayout(
            // TODO add the Channels, Usage Origins, Periods widgets
            dateReceivedFromWidget, dateReceivedToWidget, getButtonsLayout());
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "report-udm-weekly-survey-window");
        return this;
    }

    @Override
    public void setController(IUdmWeeklySurveyReportController controller) {
        this.controller = controller;
    }

    @Override
    public Set<String> getChannels() {
        return channels;
    }

    @Override
    public Set<String> getUsageOrigin() {
        return usageOrigins;
    }

    @Override
    public Set<Integer> getPeriods() {
        return periods;
    }

    @Override
    public LocalDate getDateReceivedFrom() {
        return dateReceivedFromWidget.getValue();
    }

    @Override
    public LocalDate getDateReceivedTo() {
        return dateReceivedToWidget.getValue();
    }

    private void initFilters() {
        // TODO initialize the Channels, Usage Origins, Periods widgets
        dateReceivedFromWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_received_from"));
        dateReceivedFromWidget.addValueChangeListener(event -> this.updateExportButtonState());
        VaadinUtils.addComponentStyle(dateReceivedFromWidget, "date-received-from-filter");
        dateReceivedToWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_received_to"));
        dateReceivedFromWidget.addValueChangeListener(event -> this.updateExportButtonState());
        VaadinUtils.addComponentStyle(dateReceivedToWidget, "date-received-to-filter");
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

    // TODO implement: data range or period
    private void updateExportButtonState() {
        exportButton.setEnabled(!dateReceivedFromWidget.isEmpty() && !dateReceivedToWidget.isEmpty());
    }
}
