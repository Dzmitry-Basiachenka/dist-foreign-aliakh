package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Modal window that provides functionality for viewing aggregate licensee classes by rightsholder.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenarioDrillDownAggLcClassesWindow extends Window {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String PRINT_GROSS_TOTAL_PROPERTY = "grossTotalPrint";
    private static final String PRINT_NET_TOTAL_PROPERTY = "netTotalPrint";
    private static final String DIGITAL_GROSS_TOTAL_PROPERTY = "grossTotalDigital";
    private static final String DIGITAL_NET_TOTAL_PROPERTY = "netTotalDigital";
    private static final String GROSS_TOTAL_PROPERTY = "grossTotal";
    private static final String NET_TOTAL_PROPERTY = "netTotal";

    private final IAclScenarioController controller;
    private final RightsholderResultsFilter filter;

    private List<AclRightsholderTotalsHolderDto> aclRightsholderTotalsHolderDtos;
    private Grid<AclRightsholderTotalsHolderDto> grid;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioController}
     * @param filter     instance of {@link RightsholderResultsFilter}
     */
    public AclScenarioDrillDownAggLcClassesWindow(IAclScenarioController controller,
                                                  RightsholderResultsFilter filter) {
        this.controller = controller;
        this.filter = filter;
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        initGrid();
        // TODO will be implemented label section
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout layout = new VerticalLayout(grid, closeButton);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.acl_scenario_drill_down_aggregate-licensee-class"));
        VaadinUtils.addComponentStyle(this, "acl-scenario-drill-down-aggregate-licensee-class-window");
    }

    private void initGrid() {
        aclRightsholderTotalsHolderDtos = controller.getRightsholderAggLcClassResults(filter);
        ListDataProvider<AclRightsholderTotalsHolderDto> dataProvider =
            DataProvider.ofCollection(aclRightsholderTotalsHolderDtos);
        grid = new Grid<>(dataProvider);
        addColumns();
        addFooter();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "acl-scenario-drill-down-aggregate-licensee-class-grid");
    }

    private void addColumns() {
        grid.addColumn(holder -> holder.getAggregateLicenseeClass().getId())
            .setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_id"))
            .setId("aggregateLicenseeClass.id")
            .setSortProperty("aggregateLicenseeClass.id")
            .setWidth(110);
        grid.addComponentColumn(holder -> {
                Button button = Buttons.createButton(Objects.toString(
                    holder.getAggregateLicenseeClass().getDescription()));
                button.addStyleName(ValoTheme.BUTTON_LINK);
                return button;
            }).setCaption(ForeignUi.getMessage("table.column.aggregate_licensee_class_name"))
            .setId("aggregateLicenseeClass.description")
            .setSortProperty("aggregateLicenseeClass.description")
            .setWidth(256);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotalPrint, "table.column.print_gross_amount",
            PRINT_GROSS_TOTAL_PROPERTY, 150);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotalPrint, "table.column.print_net_amount",
            PRINT_NET_TOTAL_PROPERTY, 150);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotalDigital, "table.column.digital_gross_amount",
            DIGITAL_GROSS_TOTAL_PROPERTY, 150);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotalDigital, "table.column.digital_net_amount",
            DIGITAL_NET_TOTAL_PROPERTY, 150);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotal, "table.column.total_gross_amount",
            GROSS_TOTAL_PROPERTY, 150);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotal, "table.column.total_net_amount",
            NET_TOTAL_PROPERTY, 140);
    }

    private void addAmountColumn(Function<AclRightsholderTotalsHolderDto, BigDecimal> function, String captionProperty,
                                 String columnId, double width) {
        grid.addColumn(holder -> CurrencyUtils.format(function.apply(holder), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortProperty(columnId)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    private void addFooter() {
        grid.appendFooterRow();
        grid.setFooterVisible(true);
        FooterRow row = grid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join("aggregateLicenseeClass.id", "aggregateLicenseeClass.description")
            .setText("Totals");
        initFooterCell(row, PRINT_GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotalPrint);
        initFooterCell(row, PRINT_NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotalPrint);
        initFooterCell(row, DIGITAL_GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotalDigital);
        initFooterCell(row, DIGITAL_NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotalDigital);
        initFooterCell(row, GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotal);
        initFooterCell(row, NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotal);
    }

    private void initFooterCell(FooterRow row, String cellProperty,
                                Function<AclRightsholderTotalsHolderDto, BigDecimal> function) {
        FooterCell cell = row.getCell(cellProperty);
        cell.setText(CurrencyUtils.format(
            aclRightsholderTotalsHolderDtos.stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add),
            null));
        cell.setStyleName(STYLE_ALIGN_RIGHT);
    }
}
