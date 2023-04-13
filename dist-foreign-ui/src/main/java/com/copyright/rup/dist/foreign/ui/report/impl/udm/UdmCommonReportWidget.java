package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.ui.common.validator.DateValidator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonReportWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
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
 * Implementation of controller for {@link IUdmCommonReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/03/2022
 *
 * @author Aliaksandr Liakh
 * @author Anton Azarenka
 */
public class UdmCommonReportWidget extends Window implements IUdmCommonReportWidget {

    private IUdmCommonReportController controller;
    private ComboBox<UdmChannelEnum> channelComboBox;
    private ComboBox<UdmUsageOriginEnum> usageOriginComboBox;
    private PeriodFilterWidget periodFilterWidget;
    private LocalDateWidget dateFromWidget;
    private LocalDateWidget dateToWidget;
    private Binder<LocalDate> dateBinder;
    private Button exportButton;
    private final Set<Integer> periods = new HashSet<>();
    private final String dateCaption;

    /**
     * @param dateCaption caption for date label.
     */
    public UdmCommonReportWidget(String dateCaption) {
        this.dateCaption = dateCaption;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UdmCommonReportWidget init() {
        setWidth(300, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "udm-report-window");
        initContent();
        return this;
    }

    @Override
    public void setController(IUdmCommonReportController controller) {
        this.controller = controller;
    }

    @Override
    public UdmReportFilter getReportFilter() {
        UdmReportFilter udmReportFilter = new UdmReportFilter();
        udmReportFilter.setChannel(
            Objects.nonNull(channelComboBox.getValue()) ? channelComboBox.getValue() : null);
        udmReportFilter.setPeriods(periods);
        udmReportFilter.setUsageOrigin(
            Objects.nonNull(usageOriginComboBox.getValue()) ? usageOriginComboBox.getValue() : null);
        udmReportFilter.setDateFrom(dateFromWidget.getValue());
        udmReportFilter.setDateTo(dateToWidget.getValue());
        return udmReportFilter;
    }

    private void initContent() {
        initChannelFilter();
        initUsageOriginFilter();
        initPeriodFilter();
        initDatesFilter();
        HorizontalLayout multipleFiltersLayout = new HorizontalLayout(channelComboBox, usageOriginComboBox);
        multipleFiltersLayout.setSizeFull();
        VerticalLayout content = new VerticalLayout(periodFilterWidget, multipleFiltersLayout, dateFromWidget,
            dateToWidget, getButtonsLayout());
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

    private void initDatesFilter() {
        dateBinder = new Binder<>();
        dateFromWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_report_from", dateCaption));
        dateFromWidget.addValueChangeListener(event -> {
            dateBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateFromWidget, "date-from-filter");
        dateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.date_report_to", dateCaption));
        dateToWidget.addValueChangeListener(event -> {
            dateBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateToWidget, "date-to-filter");
        dateBinder.forField(dateToWidget)
            .withValidator(new DateValidator(ForeignUi.getMessage("label.date_report_from", dateCaption),
                dateFromWidget, dateToWidget))
            .bind(ValueProvider.identity(), (bean, fieldValue) -> bean = fieldValue)
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
        boolean arePeriodsSet = !periods.isEmpty();
        boolean areDateRangeSet = !dateFromWidget.isEmpty() && !dateToWidget.isEmpty();
        exportButton.setEnabled(dateBinder.isValid() && (arePeriodsSet || areDateRangeSet));
    }
}
