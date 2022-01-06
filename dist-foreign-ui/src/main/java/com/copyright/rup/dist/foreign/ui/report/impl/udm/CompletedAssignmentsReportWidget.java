package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.filter.UdmReportFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.udm.ICompletedAssignmentsReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.ICompletedAssignmentsReportWidget;
import com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.PeriodFilterWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.filter.CommonFilterWindow;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.LocalDateWidget;

import com.vaadin.data.Binder;
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
 * Widget provides functionality for configuring items filter widget for user names.
 * <p/>
 * Copyright (C) 2022 copyright.com
 * <p/>
 * Date: 01/05/2022
 *
 * @author Ihar Suvorau
 */
public class CompletedAssignmentsReportWidget extends Window implements ICompletedAssignmentsReportWidget {

    private ICompletedAssignmentsReportController controller;
    private PeriodFilterWidget periodFilterWidget;
    private UserNameFilterWidget userNameFilterWidget;
    private LocalDateWidget dateFromWidget;
    private LocalDateWidget dateToWidget;
    private Binder<LocalDate> dateBinder;
    private Button exportButton;
    private final Set<Integer> periods = new HashSet<>();
    private final Set<String> userNames = new HashSet<>();

    @Override
    @SuppressWarnings("unchecked")
    public ICompletedAssignmentsReportWidget init() {
        setWidth(330, Unit.PIXELS);
        setResizable(false);
        VaadinUtils.addComponentStyle(this, "udm-report-window");
        initContent();
        return this;
    }

    @Override
    public void setController(ICompletedAssignmentsReportController controller) {
        this.controller = controller;
    }

    @Override
    public UdmReportFilter getReportFilter() {
        UdmReportFilter udmReportFilter = new UdmReportFilter();
        udmReportFilter.setPeriods(periods);
        udmReportFilter.setUserNames(userNames);
        udmReportFilter.setDateFrom(dateFromWidget.getValue());
        udmReportFilter.setDateTo(dateToWidget.getValue());
        return udmReportFilter;
    }

    /**
     * Init content.
     */
    public void initContent() {
        initPeriodFilter();
        initUserNamesFilter();
        initDatesFilter();
        HorizontalLayout multipleFiltersLayout = new HorizontalLayout(periodFilterWidget, userNameFilterWidget);
        multipleFiltersLayout.setSizeFull();
        VerticalLayout content =
            new VerticalLayout(multipleFiltersLayout, dateFromWidget, dateToWidget, getButtonsLayout());
        setContent(content);
        VaadinUtils.setMaxComponentsWidth(content);
    }

    private void initPeriodFilter() {
        periodFilterWidget = new PeriodFilterWidget(controller::getAllPeriods);
        periodFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<Integer>) saveEvent -> {
            periods.clear();
            periods.addAll(saveEvent.getSelectedItemsIds());
            updateExportButtonState();
        });
    }

    private void initUserNamesFilter() {
        userNameFilterWidget = new UserNameFilterWidget(controller::getUserNames);
        userNameFilterWidget.addFilterSaveListener((CommonFilterWindow.IFilterSaveListener<String>) saveEvent -> {
            userNames.clear();
            userNames.addAll(saveEvent.getSelectedItemsIds());
        });
    }

    private void initDatesFilter() {
        dateBinder = new Binder<>();
        dateFromWidget = new LocalDateWidget(ForeignUi.getMessage("label.from_date"));
        dateFromWidget.addValueChangeListener(event -> {
            dateBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateFromWidget, "date-from-filter");
        dateToWidget = new LocalDateWidget(ForeignUi.getMessage("label.to_date"));
        dateToWidget.addValueChangeListener(event -> {
            dateBinder.validate();
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(dateToWidget, "date-to-filter");
        dateBinder.forField(dateToWidget)
            .withValidator(value -> {
                LocalDate dateFromWidgetValue = dateFromWidget.getValue();
                LocalDate dateToWidgetValue = dateToWidget.getValue();
                return Objects.isNull(dateFromWidgetValue) && Objects.isNull(dateToWidgetValue)
                    || 0 <= dateToWidgetValue.compareTo(dateFromWidgetValue);
            }, ForeignUi.getMessage("field.error.greater_or_equal_to", ForeignUi.getMessage("label.from_date")))
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
            || dateBinder.isValid() && !dateFromWidget.isEmpty() && !dateToWidget.isEmpty());
    }
}
