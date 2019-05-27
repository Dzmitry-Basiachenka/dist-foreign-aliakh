package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportController;
import com.copyright.rup.dist.foreign.ui.report.api.IOwnershipAdjustmentReportWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Implementation of {@link IOwnershipAdjustmentReportWidget}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 05/27/2019
 *
 * @author Aliaksandr Liakh
 */
public class OwnershipAdjustmentReportWidget extends Window implements IOwnershipAdjustmentReportWidget {

    private IOwnershipAdjustmentReportController controller;
    private ComboBox<Scenario> scenarioComboBox;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public IOwnershipAdjustmentReportWidget init() {
        VerticalLayout content = new VerticalLayout(getScenarioCombobox(), getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(350, Unit.PIXELS);
        setResizable(false);
        return this;
    }

    @Override
    public void setController(IOwnershipAdjustmentReportController controller) {
        this.controller = controller;
    }

    @Override
    public Scenario getScenario() {
        return scenarioComboBox.getValue();
    }

    private Component getScenarioCombobox() {
        scenarioComboBox = new ComboBox<>(ForeignUi.getMessage("field.scenario_name"));
        scenarioComboBox.setEmptySelectionAllowed(false);
        scenarioComboBox.setTextInputAllowed(false);
        scenarioComboBox.addValueChangeListener(event -> exportButton.setEnabled(true));
        scenarioComboBox.setItems(controller.getScenarios());
        scenarioComboBox.setItemCaptionGenerator(Scenario::getName);
        return scenarioComboBox;
    }

    private HorizontalLayout getButtonsLayout() {
        Button closeButton = Buttons.createCloseButton((Window) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        OnDemandFileDownloader downloader = new OnDemandFileDownloader(
            controller.getOwnershipAdjustmentReportStreamSource());
        downloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(exportButton, closeButton);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        layout.setComponentAlignment(exportButton, Alignment.MIDDLE_LEFT);
        layout.setMargin(new MarginInfo(true, false, false, false));
        layout.setSizeFull();
        return layout;
    }
}
