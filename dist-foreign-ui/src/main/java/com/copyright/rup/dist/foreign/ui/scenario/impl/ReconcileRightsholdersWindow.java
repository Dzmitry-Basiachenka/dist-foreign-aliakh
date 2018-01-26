package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Represent window for displaying rightsholder and participation status discrepancies.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/26/18
 *
 * @author Ihar Suvorau
 */
public class ReconcileRightsholdersWindow extends Window {

    private IReconcileRightsholdersController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {link IReconcileRightsholdersController}
     */
    ReconcileRightsholdersWindow(IReconcileRightsholdersController controller) {
        super(ForeignUi.getMessage("label.reconcile_rightsholders"));
        this.controller = controller;
        setWidth(830, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setContent(initContent());
    }

    private VerticalLayout initContent() {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName(Cornerstone.TABSHEET_MINIMAL);
        tabSheet.setSizeFull();
        List<RightsholderDiscrepancy> rightsholderDiscrepancies = controller.getDiscrepancies();
        RightsholderDiscrepanciesWindow rightsholderComponent =
            new RightsholderDiscrepanciesWindow(rightsholderDiscrepancies);
        ServiceFeeDiscrepanciesWindow statusDiscrepanciesWindow =
            new ServiceFeeDiscrepanciesWindow(rightsholderDiscrepancies);
        tabSheet.addTab(rightsholderComponent, ForeignUi.getMessage("label.rightsholders_updates"));
        tabSheet.addTab(statusDiscrepanciesWindow, ForeignUi.getMessage("label.service_fee_updates"));
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(tabSheet, buttonsLayout);
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        layout.setSizeFull();
        layout.setExpandRatio(tabSheet, 1);
        layout.setMargin(true);
        return layout;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
        approveButton.addClickListener(event -> {
            controller.approveReconciliation();
            this.close();
        });
        Button cancelButton = Buttons.createCancelButton(this);
        cancelButton.addClickListener(event -> this.close());
        buttonsLayout.addComponents(approveButton, cancelButton);
        buttonsLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "rightsholder-discrepancies-buttons-layout");
        return buttonsLayout;
    }
}
