package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IUdmWeeklySurveyReportWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;
import com.vaadin.data.Binder;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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
    // TODO define the Channels, Usage Origins
    private PeriodFilterWidget periodFilterWidget;
    private LocalDateWidget dateReceivedFromWidget;
    private LocalDateWidget dateReceivedToWidget;
    private Binder<LocalDate> dateReceivedBinder;
    private Button exportButton;
    private final Set<String> channels = new HashSet<>();
    private final Set<String> usageOrigins = new HashSet<>();
    private final Set<Integer> periods = new HashSet<>();

    @Override
    @SuppressWarnings("unchecked")
    public IUdmWeeklySurveyReportWidget init() {
        initFilters();
        VerticalLayout content = new VerticalLayout(
            // TODO add the Channels, Usage Origins
            periodFilterWidget, dateReceivedFromWidget, dateReceivedToWidget, getButtonsLayout());
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
        // TODO initialize the Channels, Usage Origins
        periodFilterWidget = new PeriodFilterWidget(controller::getAllPeriods);
        periodFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<Integer>) saveEvent -> {
            periods.clear();
            periods.addAll(saveEvent.getSelectedItemsIds());
            updateExportButtonState();
        });
        dateReceivedBinder = new Binder<>();
        dateReceivedFromWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_received_from"));
        dateReceivedFromWidget.addValueChangeListener(event -> {
            dateReceivedBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateReceivedFromWidget, "date-received-from-filter");
        dateReceivedToWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_received_to"));
        dateReceivedToWidget.addValueChangeListener(event -> {
            dateReceivedBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateReceivedToWidget, "date-received-to-filter");
        dateReceivedBinder.forField(dateReceivedToWidget)
            .withValidator(value -> {
                LocalDate dateReceivedFrom = dateReceivedFromWidget.getValue();
                LocalDate dateReceivedTo = dateReceivedToWidget.getValue();
                return Objects.isNull(dateReceivedFrom) && Objects.isNull(dateReceivedTo)
                    || 0 <= dateReceivedTo.compareTo(dateReceivedFrom);
            }, ForeignUi.getMessage("field.error.greater_or_equal_to",
                ForeignUi.getMessage("label.date_received_from")))
            .bind(source -> source, (bean, fieldValue) -> bean = fieldValue)
            .validate();
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

    private void updateExportButtonState() {
        exportButton.setEnabled(!periods.isEmpty()
            || dateReceivedBinder.isValid() && !dateReceivedFromWidget.isEmpty() && !dateReceivedToWidget.isEmpty());
    }
}
