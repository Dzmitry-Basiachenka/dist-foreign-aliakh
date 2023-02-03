package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IReconcileRightsholdersController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
class RightsholderDiscrepanciesWindow extends Window {

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
        setWidth(900, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("label.reconcile_rightsholders"));
        VaadinUtils.addComponentStyle(this, "rightsholder-discrepancies-window");
    }

    private Component initContent() {
        initGrid();
        HorizontalLayout buttons = initButtons();
        VerticalLayout content = new VerticalLayout(grid, buttons);
        content.setSizeFull();
        content.setExpandRatio(grid, 1.0f);
        content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        Button approveButton = Buttons.createButton(ForeignUi.getMessage("button.approve"));
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
        buttonsLayout.addComponents(exportButton, approveButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "rightsholder-discrepancies-buttons-layout");
        return buttonsLayout;
    }

    private void initGrid() {
        DataProvider<RightsholderDiscrepancy, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getBeansCount()
        );
        grid = new Grid<>(dataProvider);
        addColumns();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "rightsholder-discrepancies-grid");
    }

    private void addColumns() {
        grid.addColumn(discrepancy -> discrepancy.getOldRightsholder().getAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_number"))
            .setSortProperty("oldRightsholder.rhAccountNumber")
            .setWidth(115);
        grid.addColumn(discrepancy -> discrepancy.getOldRightsholder().getName())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_name"))
            .setSortProperty("oldRightsholder.rhName");
        grid.addColumn(discrepancy -> discrepancy.getNewRightsholder().getAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.new_rh_account_number"))
            .setSortProperty("newRightsholder.rhAccountNumber")
            .setWidth(140);
        grid.addColumn(discrepancy -> discrepancy.getNewRightsholder().getName())
            .setCaption(ForeignUi.getMessage("table.column.new_rh_name"))
            .setSortProperty("newRightsholder.rhName");
        grid.addColumn(RightsholderDiscrepancy::getWrWrkInst)
            .setCaption(ForeignUi.getMessage("table.column.wr_wrk_inst"))
            .setSortProperty("wrWrkInst")
            .setWidth(110);
        grid.addColumn(RightsholderDiscrepancy::getWorkTitle)
            .setCaption(ForeignUi.getMessage("table.column.work_title"))
            .setSortProperty("workTitle");
    }
}
