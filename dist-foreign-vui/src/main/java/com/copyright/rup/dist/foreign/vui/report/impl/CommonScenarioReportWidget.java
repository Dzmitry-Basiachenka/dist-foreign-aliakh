package com.copyright.rup.dist.foreign.vui.report.impl;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportController;
import com.copyright.rup.dist.foreign.vui.report.api.ICommonScenarioReportWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * Implementation of {@link ICommonScenarioReportWidget}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/22/2020
 *
 * @author Ihar Suvorau
 */
public class CommonScenarioReportWidget extends CommonDialog implements ICommonScenarioReportWidget {

    private static final long serialVersionUID = 1779334293021201886L;

    private final OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader();

    private ICommonScenarioReportController controller;
    private ComboBox<Scenario> scenarioComboBox;
    private Button exportButton;

    @Override
    @SuppressWarnings("unchecked")
    public ICommonScenarioReportWidget init() {
        setWidth("500px");
        add(initContent());
        getFooter().add(getButtonsLayout());
        setModalWindowProperties("report-scenario-widget", false);
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

    private VerticalLayout initContent() {
        var content = VaadinUtils.initCommonVerticalLayout(getScenarioCombobox());
        content.setPadding(true);
        return content;
    }

    private Component getScenarioCombobox() {
        scenarioComboBox = new ComboBox<>(ForeignUi.getMessage("field.scenario_name"));
        scenarioComboBox.addValueChangeListener(event -> updateExportButtonState(scenarioComboBox.getValue()));
        scenarioComboBox.setItems(controller.getScenarios());
        scenarioComboBox.setItemLabelGenerator(Scenario::getName);
        scenarioComboBox.setWidthFull();
        return scenarioComboBox;
    }

    private HorizontalLayout getButtonsLayout() {
        var closeButton = Buttons.createCloseButton((Dialog) controller.getWidget());
        exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        exportButton.setEnabled(false);
        exportButton.addClickListener(event -> {
            if (StringUtils.isBlank(fileDownloader.getHref())) {
                fileDownloader.setResource(new CsvStreamSource(controller).getSource());
                fileDownloader.getElement().callJsFunction("click");
            }
        });
        fileDownloader.extend(exportButton);
        return new HorizontalLayout(fileDownloader, closeButton);
    }

    private void updateExportButtonState(Scenario scenario) {
        if (Objects.nonNull(scenario)) {
            exportButton.setEnabled(true);
            fileDownloader.setResource(new CsvStreamSource(controller).getSource());
        } else {
            fileDownloader.removeHref();
            exportButton.setEnabled(false);
        }
    }
}
