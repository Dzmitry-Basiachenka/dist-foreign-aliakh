package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
    private ComboBox<UdmChannelEnum> channelComboBox;
    private ComboBox<UdmUsageOriginEnum> usageOriginComboBox;
    private PeriodFilterWidget periodFilterWidget;
    private LocalDateWidget dateReceivedFromWidget;
    private LocalDateWidget dateReceivedToWidget;
    private Binder<LocalDate> dateReceivedBinder;
    private Button exportButton;
    private final Set<Integer> periods = new HashSet<>();

    @Override
    @SuppressWarnings("unchecked")
    public IUdmWeeklySurveyReportWidget init() {
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "report-udm-weekly-survey-window");
        initContent();
        return this;
    }

    @Override
    public void setController(IUdmWeeklySurveyReportController controller) {
        this.controller = controller;
    }

    @Override
    public String getChannel() {
        return Objects.nonNull(channelComboBox.getValue()) ? channelComboBox.getValue().name() : null;
    }

    @Override
    public String getUsageOrigin() {
        return Objects.nonNull(usageOriginComboBox.getValue()) ? usageOriginComboBox.getValue().name() : null;
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

    private void initContent() {
        initChannelFilter();
        initUsageOriginFilter();
        initPeriodFilter();
        initDateReceivedFilter();
        HorizontalLayout multipleFiltersLayout = new HorizontalLayout(channelComboBox, usageOriginComboBox);
        multipleFiltersLayout.setSizeFull();
        VerticalLayout content = new VerticalLayout(periodFilterWidget, multipleFiltersLayout, dateReceivedFromWidget,
            dateReceivedToWidget, getButtonsLayout());
        setContent(content);
        VaadinUtils.setMaxComponentsWidth(content);
    }

    private void initChannelFilter() {
        channelComboBox = new ComboBox<>(ForeignUi.getMessage("label.channel"));
        channelComboBox.setItems(UdmChannelEnum.values());
        VaadinUtils.setMaxComponentsWidth(channelComboBox);
        VaadinUtils.addComponentStyle(channelComboBox, "channel-filter");
    }

    private void initUsageOriginFilter() {
        usageOriginComboBox = new ComboBox<>(ForeignUi.getMessage("label.usage_origin"));
        usageOriginComboBox.setItems(UdmUsageOriginEnum.values());
        VaadinUtils.setMaxComponentsWidth(usageOriginComboBox);
        VaadinUtils.addComponentStyle(usageOriginComboBox, "usage-origin-filter");
    }

    private void initPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(controller::getAllPeriods);
        periodFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<Integer>) saveEvent -> {
            periods.clear();
            periods.addAll(saveEvent.getSelectedItemsIds());
            updateExportButtonState();
        });
    }

    private void initDateReceivedFilter() {
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
        layout.setSizeFull();
        return layout;
    }

    private void updateExportButtonState() {
        exportButton.setEnabled(!periods.isEmpty()
            || dateReceivedBinder.isValid() && !dateReceivedFromWidget.isEmpty() && !dateReceivedToWidget.isEmpty());
    }
}
