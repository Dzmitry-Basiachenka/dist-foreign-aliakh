package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AclCommonReportWidget} for ACL Comparison by Aggregate Licensee Class and Title report.
 * <p/>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 03/22/2023
 *
 * @author Mikita Maistrenka
 */
public class AclComparisonByAggLcClassAndTitleReportWidget extends AclCommonReportWidget {

    private static final String EMPTY_FILTER_STYLE = "empty-item-filter-widget";

    private final Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
    private final ComboBox<Integer> previousPeriodComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.previous_period"));
    private final ComboBox<Integer> currentPeriodComboBox =
        new ComboBox<>(ForeignUi.getMessage("label.current_period"));
    private AclScenarioFilterWidget previousScenarioFilterWidget;
    private AclScenarioFilterWidget currentScenarioFilterWidget;
    private IAclCommonReportController controller;

    @Override
    @SuppressWarnings("unchecked")
    public AclComparisonByAggLcClassAndTitleReportWidget init() {
        setContent(initRootLayout());
        setResizable(false);
        setWidth(410, Unit.PIXELS);
        setHeight(145, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-report-window");
        return this;
    }

    @Override
    public void setController(IAclCommonReportController controller) {
        this.controller = controller;
    }

    @Override
    public AclCalculationReportsInfoDto getReportInfo() {
        AclCalculationReportsInfoDto reportInfo = new AclCalculationReportsInfoDto();
        reportInfo.setPreviousScenarios(new ArrayList<>(previousScenarioFilterWidget.getSelectedItems()));
        reportInfo.setScenarios(new ArrayList<>(currentScenarioFilterWidget.getSelectedItems()));
        return reportInfo;
    }

    private ComponentContainer initRootLayout() {
        HorizontalLayout scenarioLayout = initScenarioLayout();
        HorizontalLayout periodLayout = initPeriodLayout();
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(periodLayout, scenarioLayout, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return verticalLayout;
    }

    private HorizontalLayout initScenarioLayout() {
        previousScenarioFilterWidget = initScenarioFilterWidget(previousPeriodComboBox, "label.previous_scenarios",
            "acl-previous-scenarios-report-filter");
        currentScenarioFilterWidget = initScenarioFilterWidget(currentPeriodComboBox, "label.current_scenarios",
            "acl-current-scenarios-report-filter");
        HorizontalLayout scenarioLayout =
            new HorizontalLayout(previousScenarioFilterWidget, currentScenarioFilterWidget);
        scenarioLayout.setSizeFull();
        scenarioLayout.setSpacing(true);
        return scenarioLayout;
    }

    private HorizontalLayout initPeriodLayout() {
        List<Integer> periods = controller.getPeriods();
        HorizontalLayout periodLayout = new HorizontalLayout(
            initPeriodComboBox(previousPeriodComboBox, previousScenarioFilterWidget, periods,
                "acl-previous-period-report-filter"),
            initPeriodComboBox(currentPeriodComboBox, currentScenarioFilterWidget, periods,
                "acl-current-period-report-filter"));
        periodLayout.setSizeFull();
        return periodLayout;
    }

    private ComboBox<Integer> initPeriodComboBox(ComboBox<Integer> periodComboBox,
                                                 AclScenarioFilterWidget scenarioFilterWidget, List<Integer> periods,
                                                 String styleName) {
        periodComboBox.setItems(periods);
        periodComboBox.setRequiredIndicatorVisible(true);
        periodComboBox.setSizeFull();
        periodComboBox.addValueChangeListener(event -> {
            scenarioFilterWidget.reset();
            boolean isNotEmptyComboBox = periodComboBox.getSelectedItem().isPresent();
            scenarioFilterWidget.setEnabled(isNotEmptyComboBox);
            applyFilterEmptyStyle(scenarioFilterWidget, isNotEmptyComboBox);
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(periodComboBox, styleName);
        return periodComboBox;
    }

    private AclScenarioFilterWidget initScenarioFilterWidget(ComboBox<Integer> periodComboBox,
                                                             String widgetButtonCaptionLabelName, String styleName) {
        AclScenarioFilterWidget scenarioFilterWidget = new AclScenarioFilterWidget(() ->
            controller.getScenarios(periodComboBox.getSelectedItem().orElse(null)), widgetButtonCaptionLabelName);
        scenarioFilterWidget.setEnabled(false);
        scenarioFilterWidget.addFilterSaveListener(event -> {
            applyFilterEmptyStyle(scenarioFilterWidget, 0 == event.getSelectedItemsIds().size());
            updateExportButtonState();
        });
        VaadinUtils.addComponentStyle(scenarioFilterWidget, styleName);
        return scenarioFilterWidget;
    }

    private HorizontalLayout initButtonsLayout() {
        exportButton.setEnabled(false);
        Button closeButton = Buttons.createCloseButton(this);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        downloader.extend(exportButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setSizeFull();
        VaadinUtils.setButtonsAutoDisabled(exportButton);
        return layout;
    }

    private void updateExportButtonState() {
        exportButton.setEnabled(CollectionUtils.isNotEmpty(previousScenarioFilterWidget.getSelectedItems())
            && CollectionUtils.isNotEmpty(currentScenarioFilterWidget.getSelectedItems()));
    }

    private void applyFilterEmptyStyle(AclScenarioFilterWidget filterWidget, boolean addEmptyStyle) {
        if (addEmptyStyle) {
            filterWidget.addStyleName(EMPTY_FILTER_STYLE);
        } else {
            filterWidget.removeStyleName(EMPTY_FILTER_STYLE);
        }
    }
}
