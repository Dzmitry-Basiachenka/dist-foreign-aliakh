package com.copyright.rup.dist.foreign.ui.report.impl.acl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.report.AclCalculationReportsInfoDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportController;
import com.copyright.rup.dist.foreign.ui.report.api.acl.IAclCommonReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.ArrayList;

/**
 * Implementation of {@link IAclCommonReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/05/2022
 *
 * @author Ihar Suvorau
 */
public class AclCommonReportWidget extends Window implements IAclCommonReportWidget {

    private final Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
    private AclScenarioFilterWidget scenarioFilterWidget;
    private IAclCommonReportController controller;

    @Override
    @SuppressWarnings("unchecked")
    public IAclCommonReportWidget init() {
        setContent(initRootLayout());
        setResizable(false);
        setWidth(270, Unit.PIXELS);
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
        reportInfo.setPeriod(periodComboBox.getSelectedItem().orElse(null));
        reportInfo.setScenarios(new ArrayList<>(scenarioFilterWidget.getSelectedItems()));
        reportInfo.setUser(RupContextUtils.getUserName());
        return reportInfo;
    }

    private ComponentContainer initRootLayout() {
        periodComboBox.setItems(controller.getPeriods());
        periodComboBox.setRequiredIndicatorVisible(true);
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        scenarioFilterWidget =
            new AclScenarioFilterWidget(() -> controller.getScenarios(periodComboBox.getSelectedItem().orElse(null)));
        scenarioFilterWidget.setEnabled(false);
        periodComboBox.addValueChangeListener(event -> {
            scenarioFilterWidget.reset();
            scenarioFilterWidget.setEnabled(periodComboBox.getSelectedItem().isPresent());
            exportButton.setEnabled(periodComboBox.getSelectedItem().isPresent());
        });
        HorizontalLayout buttonsLayout = initButtonsLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(periodComboBox, scenarioFilterWidget, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return verticalLayout;
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
        return layout;
    }
}
