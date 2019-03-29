package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.vaadin.ui.Buttons;
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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Grid<UsageBatch> grid;

    /**
     * Constructor.
     *
     * @param batches set of {@link UsageBatch}'es
     */
    WithdrawnFilteredBatchesWindow(Set<UsageBatch> batches) {
        setCaption(ForeignUi.getMessage("window.filtered_batches"));
        setWidth(450, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        initUsageBatchesGrid(batches
            .stream()
            .sorted(Comparator.comparing(UsageBatch::getUpdateDate).reversed())
            .collect(Collectors.toList()));
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout buttonsLayout = createButtonsLayout();
        content.addComponents(grid, buttonsLayout);
        content.setExpandRatio(grid, 1f);
        content.setSizeFull();
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(content);
        VaadinUtils.addComponentStyle(this, "batches-filter-window");
    }

    private void initUsageBatchesGrid(List<UsageBatch> batches) {
        grid = new Grid<>();
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
        BigDecimal amount = batches
            .stream()
            .map(UsageBatch::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        amountCell.setText(CurrencyUtils.format(amount, null));
    }

    private HorizontalLayout createButtonsLayout() {
        Button exportButton = Buttons.createButton("Export");
        Button saveButton = Buttons.createButton("Save");
        return new HorizontalLayout(exportButton, saveButton, Buttons.createCloseButton(this));
    }
}
