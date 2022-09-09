package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Modal window that provides functionality for viewing wr wrk insts and titles by rightsholder.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclScenarioDrillDownTitlesWindow extends Window implements SearchWidget.ISearchController {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String WR_WRK_INST_PROPERTY = "wrWrkInst";
    private static final String SYSTEM_TITLE_PROPERTY = "systemTitle";
    private static final String PRINT_GROSS_TOTAL_PROPERTY = "grossTotalPrint";
    private static final String PRINT_NET_TOTAL_PROPERTY = "netTotalPrint";
    private static final String DIGITAL_GROSS_TOTAL_PROPERTY = "grossTotalDigital";
    private static final String DIGITAL_NET_TOTAL_PROPERTY = "netTotalDigital";
    private static final String GROSS_TOTAL_PROPERTY = "grossTotal";
    private static final String NET_TOTAL_PROPERTY = "netTotal";

    private final IAclScenarioController controller;
    private final RightsholderResultsFilter filter;
    private final SearchWidget searchWidget;

    private ListDataProvider<AclRightsholderTotalsHolderDto> dataProvider;
    private Grid<AclRightsholderTotalsHolderDto> grid;
    private List<AclRightsholderTotalsHolderDto> aclRightsholderTotalsHolderDtos;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioController}
     * @param filter     instance of {@link RightsholderResultsFilter}
     */
    public AclScenarioDrillDownTitlesWindow(IAclScenarioController controller, RightsholderResultsFilter filter) {
        this.controller = controller;
        this.filter = filter;
        setWidth(1280, Unit.PIXELS);
        setHeight(Objects.nonNull(filter.getAggregateLicenseeClassId()) ? 75 : 85, Unit.PERCENTAGE);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_titles_by_rightsholder.search.acl"));
        initGrid();
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout content = new VerticalLayout(initMetaInfoLayout(), searchWidget, grid, closeButton);
        content.setSizeFull();
        content.setExpandRatio(grid, 1);
        content.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        setContent(content);
        setCaption(ForeignUi.getMessage("window.acl_scenario_drill_down_titles"));
        VaadinUtils.addComponentStyle(this, "acl-scenario-drill-down-titles-window");
    }

    private VerticalLayout initMetaInfoLayout() {
        HorizontalLayout[] components = Stream.of(
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.rh_account_number"), filter.getRhAccountNumber()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.rh_name"), filter.getRhName()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.agg_lic_class_id"),
                filter.getAggregateLicenseeClassId()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.agg_lic_class_name"),
                filter.getAggregateLicenseeClassName())
        )
            .filter(Objects::nonNull)
            .toArray(HorizontalLayout[]::new);
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.addComponents(components);
        return verticalLayout;
    }

    private HorizontalLayout initLabelsHorizontalLayout(String title, Object value) {
        HorizontalLayout horizontalLayout = null;
        if (Objects.nonNull(value)) {
            Label titleLabel = new Label(ForeignUi.getMessage("label.title", title));
            titleLabel.setWidth(90, Unit.PIXELS);
            titleLabel.addStyleName(Cornerstone.LABEL_BOLD);
            Label valueLabel = new Label(String.valueOf(value));
            valueLabel.setSizeFull();
            horizontalLayout = new HorizontalLayout();
            horizontalLayout.setSizeFull();
            horizontalLayout.addComponents(titleLabel, valueLabel);
            horizontalLayout.setExpandRatio(valueLabel, 1);
        }
        return horizontalLayout;
    }

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(value ->
                StringUtils.containsIgnoreCase(Objects.toString(value.getWrWrkInst(), null), searchValue)
                    || StringUtils.containsIgnoreCase(value.getSystemTitle(), searchValue));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initGrid() {
        aclRightsholderTotalsHolderDtos = controller.getRightsholderTitleResults(filter);
        dataProvider = DataProvider.ofCollection(aclRightsholderTotalsHolderDtos);
        grid = new Grid<>(dataProvider);
        addGridColumns();
        addFooter();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "acl-scenario-drill-down-titles-grid");
    }

    private void addGridColumns() {
        grid.addComponentColumn(holder -> {
            Button button = Buttons.createButton(Objects.toString(holder.getWrWrkInst()));
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> {
                filter.setWrWrkInst(holder.getWrWrkInst());
                filter.setSystemTitle(holder.getSystemTitle());
                Windows.showModalWindow(
                    Objects.nonNull(filter.getAggregateLicenseeClassId())
                        ? new AclScenarioDrillDownUsageDetailsWindow(controller, new RightsholderResultsFilter(filter))
                        : new AclScenarioDrillDownAggLcClassesWindow(controller, new RightsholderResultsFilter(filter))
                );
            });
            VaadinUtils.setButtonsAutoDisabled(button);
            return button;
        }).setCaption(ForeignUi.getMessage("table.column.wr_wrk_inst"))
            .setId(WR_WRK_INST_PROPERTY)
            .setSortProperty(WR_WRK_INST_PROPERTY)
            .setComparator((holder1, holder2) -> holder1.getWrWrkInst().compareTo(holder2.getWrWrkInst()))
            .setWidth(110);
        grid.addColumn(AclRightsholderTotalsHolderDto::getSystemTitle)
            .setCaption(ForeignUi.getMessage("table.column.system_title"))
            .setId(SYSTEM_TITLE_PROPERTY)
            .setSortProperty(SYSTEM_TITLE_PROPERTY)
            .setComparator((holder1, holder2) -> holder1.getSystemTitle().compareToIgnoreCase(holder2.getSystemTitle()))
            .setWidth(400);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotalPrint, "table.column.print_gross_amount",
            PRINT_GROSS_TOTAL_PROPERTY);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotalPrint, "table.column.print_net_amount",
            PRINT_NET_TOTAL_PROPERTY);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotalDigital, "table.column.digital_gross_amount",
            DIGITAL_GROSS_TOTAL_PROPERTY);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotalDigital, "table.column.digital_net_amount",
            DIGITAL_NET_TOTAL_PROPERTY);
        addAmountColumn(AclRightsholderTotalsHolderDto::getGrossTotal, "table.column.total_gross_amount",
            GROSS_TOTAL_PROPERTY);
        addAmountColumn(AclRightsholderTotalsHolderDto::getNetTotal, "table.column.total_net_amount",
            NET_TOTAL_PROPERTY);
    }

    private void addAmountColumn(Function<AclRightsholderTotalsHolderDto, BigDecimal> function, String captionProperty,
                                 String columnId) {
        grid.addColumn(holder -> CurrencyUtils.format(function.apply(holder), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortProperty(columnId)
            .setComparator((holder1, holder2) -> function.apply(holder1).compareTo(function.apply(holder2)))
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setExpandRatio(1);
    }

    private void addFooter() {
        grid.appendFooterRow();
        grid.setFooterVisible(true);
        FooterRow row = grid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join(WR_WRK_INST_PROPERTY, SYSTEM_TITLE_PROPERTY).setText("Totals");
        initFooterAmountCell(row, PRINT_GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotalPrint);
        initFooterAmountCell(row, PRINT_NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotalPrint);
        initFooterAmountCell(row, DIGITAL_GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotalDigital);
        initFooterAmountCell(row, DIGITAL_NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotalDigital);
        initFooterAmountCell(row, GROSS_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getGrossTotal);
        initFooterAmountCell(row, NET_TOTAL_PROPERTY, AclRightsholderTotalsHolderDto::getNetTotal);
    }

    private void initFooterAmountCell(FooterRow row, String cellProperty,
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
