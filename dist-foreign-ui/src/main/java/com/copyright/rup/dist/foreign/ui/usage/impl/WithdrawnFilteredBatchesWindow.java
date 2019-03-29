package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;

import java.math.BigDecimal;
import java.util.List;

/**
 * Window for filtered batches to create NTS withdrawn fund pool.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/29/2019
 *
 * @author Aliaksandr Liakh
 */
class WithdrawnFilteredBatchesWindow extends Window {

    private final IUsagesController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUsagesController}
     * @param batches    list of {@link UsageBatch}'es
     */
    WithdrawnFilteredBatchesWindow(IUsagesController controller, List<UsageBatch> batches) {
        this.controller = controller;
        BigDecimal grossAmount = batches
            .stream()
            .map(UsageBatch::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        setCaption(ForeignUi.getMessage("window.filtered_batches"));
        setWidth(450, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        Grid<UsageBatch> grid = initUsageBatchesGrid(batches, grossAmount);
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout buttonsLayout = createButtonsLayout(batches, grossAmount);
        content.addComponents(grid, buttonsLayout);
        content.setExpandRatio(grid, 1f);
        content.setSizeFull();
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(content);
        VaadinUtils.addComponentStyle(this, "batches-filter-window");
    }

    private Grid<UsageBatch> initUsageBatchesGrid(List<UsageBatch> batches, BigDecimal grossAmount) {
        Grid<UsageBatch> grid = new Grid<>();
        grid.setItems(batches);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setId("name")
            .setSortProperty("name")
            .setExpandRatio(1);
        grid.addColumn(usageBatch -> CurrencyUtils.format(usageBatch.getGrossAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.withdrawn_amount"))
            .setId("grossAmount")
            .setSortProperty("grossAmount")
            .setWidth(200);
        grid.appendFooterRow();
        grid.setFooterVisible(true);
        FooterRow row = grid.getFooterRow(0);
        FooterCell totalCell = row.getCell("name");
        totalCell.setText(ForeignUi.getMessage("table.column.total"));
        FooterCell amountCell = row.getCell("grossAmount");
        amountCell.setText(CurrencyUtils.format(grossAmount, null));
        return grid;
    }

    private HorizontalLayout createButtonsLayout(List<UsageBatch> batches, BigDecimal grossAmount) {
        Button exportButton = Buttons.createButton("Export");
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(
            controller.getWithdrawnBatchesStreamSource(batches, grossAmount));
        fileDownloader.extend(exportButton);
        Button continueButton = Buttons.createButton("Continue");
        return new HorizontalLayout(exportButton, continueButton, Buttons.createCloseButton(this));
    }
}
