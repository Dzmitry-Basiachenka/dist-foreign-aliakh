package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IReconcileRightsholdersController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * Represents component to display rightsholder discrepancies.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/25/18
 *
 * @author Ihar Suvorau
 */
class RightsholderDiscrepanciesWindow extends CommonDialog {

    private static final long serialVersionUID = -6803326666265723117L;

    private final IReconcileRightsholdersController controller;
    private final IFasScenariosController scenariosController;
    private Grid<RightsholderDiscrepancy> grid;

    /**
     * Constructor.
     *
     * @param reconcileRightsholdersController instance of {@link IReconcileRightsholdersController}
     * @param scenariosController              instance of {@link IFasScenariosController}
     */
    RightsholderDiscrepanciesWindow(IReconcileRightsholdersController reconcileRightsholdersController,
                                    IFasScenariosController scenariosController) {
        this.controller = reconcileRightsholdersController;
        this.scenariosController = scenariosController;
        super.setWidth("1100px");
        super.setHeight("530px");
        super.add(initContent());
        super.setHeaderTitle(ForeignUi.getMessage("label.reconcile_rightsholders"));
        super.setModalWindowProperties("rightsholder-discrepancies-window", true);
    }

    private Component initContent() {
        initGrid();
        getFooter().add(initButtons());
        var content = VaadinUtils.initCommonVerticalLayout(grid);
        content.setHeightFull();
        VaadinUtils.setGridProperties(grid, "rightsholder-discrepancies-grid");
        return content;
    }

    private HorizontalLayout initButtons() {
        var buttonsLayout = new HorizontalLayout();
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        var approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
        var fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        approveButton.addClickListener(event -> {
            List<Long> prohibitedAccountNumbers = controller.getProhibitedAccountNumbers();
            if (CollectionUtils.isEmpty(prohibitedAccountNumbers)) {
                Windows.showConfirmDialog(
                    ForeignUi.getMessage("message.confirm.approve", controller.getScenario().getName()), () -> {
                        controller.approveReconciliation();
                        this.close();
                        scenariosController.getWidget().refreshSelectedScenario();
                    });
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("window.prohibition_approval", prohibitedAccountNumbers));
            }
        });
        VaadinUtils.setButtonsAutoDisabled(approveButton);
        buttonsLayout.add(fileDownloader, approveButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "rightsholder-discrepancies-buttons-layout");
        return buttonsLayout;
    }

    private void initGrid() {
        DataProvider<RightsholderDiscrepancy, Void> dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getBeansCount()
        );
        grid = new Grid<>();
        grid.setItems(dataProvider);
        addColumns();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "rightsholder-discrepancies-grid");
    }

    private void addColumns() {
        grid.addColumn(discrepancy -> discrepancy.getOldRightsholder().getAccountNumber())
            .setHeader(ForeignUi.getMessage("table.column.rh_account_number"))
            .setSortProperty("oldRightsholder.rhAccountNumber")
            .setWidth("115px");
        grid.addColumn(discrepancy -> discrepancy.getOldRightsholder().getName())
            .setHeader(ForeignUi.getMessage("table.column.rh_account_name"))
            .setSortProperty("oldRightsholder.rhName");
        grid.addColumn(discrepancy -> discrepancy.getNewRightsholder().getAccountNumber())
            .setHeader(ForeignUi.getMessage("table.column.new_rh_account_number"))
            .setSortProperty("newRightsholder.rhAccountNumber")
            .setWidth("140px");
        grid.addColumn(discrepancy -> discrepancy.getNewRightsholder().getName())
            .setHeader(ForeignUi.getMessage("table.column.new_rh_name"))
            .setSortProperty("newRightsholder.rhName");
        grid.addColumn(RightsholderDiscrepancy::getWrWrkInst)
            .setHeader(ForeignUi.getMessage("table.column.wr_wrk_inst"))
            .setSortProperty("wrWrkInst")
            .setWidth("110px");
        grid.addColumn(RightsholderDiscrepancy::getWorkTitle)
            .setHeader(ForeignUi.getMessage("table.column.work_title"))
            .setSortProperty("workTitle");
    }
}
