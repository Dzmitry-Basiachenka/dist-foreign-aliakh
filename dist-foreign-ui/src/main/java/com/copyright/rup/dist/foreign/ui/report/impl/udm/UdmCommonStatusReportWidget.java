package com.copyright.rup.dist.foreign.ui.report.impl.udm;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonStatusReportController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmCommonStatusReportWidget;
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

/**
 * Implementation of {@link IUdmCommonStatusReportWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/20/2022
 *
 * @author Ihar Suvorau
 */
public class UdmCommonStatusReportWidget extends Window implements IUdmCommonStatusReportWidget {

    private static final long serialVersionUID = 2610641501942218862L;

    private final Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
    private final ComboBox<Integer> periodComboBox = new ComboBox<>(ForeignUi.getMessage("label.period"));
    private IUdmCommonStatusReportController controller;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmCommonStatusReportWidget init() {
        setContent(initRootLayout());
        setResizable(false);
        setWidth(280, Unit.PIXELS);
        setHeight(120, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "udm-report-window");
        return this;
    }

    @Override
    public void setController(IUdmCommonStatusReportController controller) {
        this.controller = controller;
    }

    @Override
    public Integer getSelectedPeriod() {
        return periodComboBox.getSelectedItem().orElse(null);
    }

    private ComponentContainer initRootLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        periodComboBox.setItems(controller.getPeriods());
        VaadinUtils.setMaxComponentsWidth(periodComboBox);
        HorizontalLayout buttonsLayout = initButtonsLayout();
        verticalLayout.addComponents(periodComboBox, buttonsLayout);
        verticalLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return verticalLayout;
    }

    private HorizontalLayout initButtonsLayout() {
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
