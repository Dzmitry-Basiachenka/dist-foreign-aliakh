package com.copyright.rup.dist.foreign.ui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.ui.report.api.ICommonScenarioReportWidget;
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
 * Implementation of {@link ICommonScenarioReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public class CommonScenarioReportWidget extends Window implements ICommonScenarioReportWidget {

    private ICommonScenarioReportController controller;
    private ComboBox<Scenario> scenarioComboBox;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public ICommonScenarioReportWidget init() {
        VerticalLayout content = new VerticalLayout(getScenarioCombobox(), getButtonsLayout());
        content.setSpacing(false);
        VaadinUtils.setMaxComponentsWidth(content);
        setContent(content);
        setWidth(450, Unit.PIXELS);
        setResizable(false);
        return this;
    }

    @Override
    public void setController(ICommonScenarioReportController controller) {
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
        VaadinUtils.setMaxComponentsWidth(scenarioComboBox);
        return scenarioComboBox;
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
}
