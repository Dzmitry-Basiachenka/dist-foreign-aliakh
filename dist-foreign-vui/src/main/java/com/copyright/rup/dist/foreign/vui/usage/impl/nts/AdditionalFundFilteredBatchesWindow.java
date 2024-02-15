package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundBatchesFilterWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IAdditionalFundFilteredBatchesWindow;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableComparator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Window for filtered batches to create Additional Fund.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/29/2019
 *
 * @author Aliaksandr Liakh
 */
class AdditionalFundFilteredBatchesWindow extends CommonDialog implements IAdditionalFundFilteredBatchesWindow {

    private static final long serialVersionUID = -8114032381217044183L;
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GROSS_AMOUNT = "grossAmount";

    private final INtsUsageController controller;
    private final IAdditionalFundBatchesFilterWindow batchesFilterWindow;

    /**
     * Constructor.
     *
     * @param controller          instance of {@link INtsUsageController}
     * @param usageBatches        list of {@link UsageBatch}'es
     * @param batchesFilterWindow instance of {@link IAdditionalFundBatchesFilterWindow}
     */
    AdditionalFundFilteredBatchesWindow(INtsUsageController controller, List<UsageBatch> usageBatches,
                                        IAdditionalFundBatchesFilterWindow batchesFilterWindow) {
        this.controller = controller;
        this.batchesFilterWindow = batchesFilterWindow;
        var grossAmount = usageBatches
            .stream()
            .map(UsageBatch::getGrossAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        super.setHeaderTitle(ForeignUi.getMessage("window.filtered_batches"));
        super.setWidth("700px");
        super.setHeight("400px");
        super.add(initContent(usageBatches, grossAmount));
        super.getFooter().add(initButtonsLayout(usageBatches, grossAmount));
        super.setModalWindowProperties("batches-filter-window", true);
    }

    private VerticalLayout initContent(List<UsageBatch> usageBatches, BigDecimal grossAmount) {
        var content = new VerticalLayout(initGrid(usageBatches, grossAmount));
        content.setSizeFull();
        return content;
    }

    private Grid<UsageBatch> initGrid(List<UsageBatch> usageBatches, BigDecimal grossAmount) {
        Grid<UsageBatch> grid = new Grid<>();
        grid.setItems(usageBatches);
        grid.setSelectionMode(SelectionMode.NONE);
        grid.addColumn(UsageBatch::getName)
            .setHeader(ForeignUi.getMessage("table.column.batch_name"))
            .setSortProperty(COLUMN_NAME)
            .setComparator((SerializableComparator<UsageBatch>) (batch1, batch2) ->
                batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setSortable(true)
            .setResizable(true)
            .setKey(COLUMN_NAME)
            .setId(COLUMN_NAME);
        grid.addColumn(batch -> CurrencyUtils.format(batch.getGrossAmount(), null))
            .setHeader(ForeignUi.getMessage("table.column.withdrawn_amount"))
            .setSortProperty(COLUMN_GROSS_AMOUNT)
            .setComparator((SerializableComparator<UsageBatch>) (batch1, batch2) ->
                batch1.getGrossAmount().compareTo(batch2.getGrossAmount()))
            .setClassNameGenerator(item -> "v-align-right")
            .setFlexGrow(0)
            .setWidth("280px")
            .setSortable(true)
            .setResizable(true)
            .setKey(COLUMN_GROSS_AMOUNT)
            .setId(COLUMN_GROSS_AMOUNT);
        grid.appendFooterRow();
        var footerRow = grid.getFooterRows().get(0);
        var totalCell = footerRow.getCell(grid.getColumnByKey(COLUMN_NAME));
        totalCell.setText(ForeignUi.getMessage("table.column.total"));
        var amountCell = footerRow.getCell(grid.getColumnByKey(COLUMN_GROSS_AMOUNT));
        var label = new Label();
        label.addClassName("v-align-right");
        label.add(new Html(CurrencyUtils.formatAsHtml(grossAmount, null)));
        amountCell.setComponent(label);
        VaadinUtils.setGridProperties(grid, "batches-filter-grid");
        return grid;
    }

    private HorizontalLayout initButtonsLayout(List<UsageBatch> usageBatches, BigDecimal grossAmount) {
        var exportDownloader = new OnDemandFileDownloader(
            controller.getAdditionalFundBatchesStreamSource(usageBatches, grossAmount).getSource());
        exportDownloader.extend(Buttons.createButton("Export"));
        var continueButton = Buttons.createButton("Continue");
        continueButton.addClickListener(event ->
            Windows.showModalWindow(new CreateAdditionalFundWindow(controller,
                usageBatches.stream().map(UsageBatch::getId).collect(Collectors.toSet()), grossAmount,
                batchesFilterWindow, this)));
        var cancelButton = Buttons.createButton("Cancel");
        cancelButton.addClickListener(event -> this.close());
        return new HorizontalLayout(exportDownloader, continueButton, cancelButton);
    }
}
