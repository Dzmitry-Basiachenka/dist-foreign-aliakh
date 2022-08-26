package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolderDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * Modal window that provides functionality for viewing wr wrk insts and titles by rightsholder.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclViewTitlesByRightsholderWindow extends Window implements SearchWidget.ISearchController {

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

    private SearchWidget searchWidget;
    private ListDataProvider<AclRightsholderTotalsHolderDto> dataProvider;
    private Grid<AclRightsholderTotalsHolderDto> grid;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioController}
     * @param filter     instance of {@link RightsholderResultsFilter}
     */
    public AclViewTitlesByRightsholderWindow(IAclScenarioController controller, RightsholderResultsFilter filter) {
        this.controller = controller;
        this.filter = filter;
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        initGrid();
        // TODO {dbasiachenka} implement label section
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout layout = new VerticalLayout(initSearchWidget(), grid, closeButton);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_acl_titles_by_rightsholder"));
        VaadinUtils.addComponentStyle(this, "view-acl-titles-by-rightsholder-window");
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
        dataProvider = DataProvider.ofCollection(controller.getRightsholderTitleResults(filter));
        grid = new Grid<>(dataProvider);
        addGridColumns();
        addFooter();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "view-acl-titles-by-rightsholder-grid");
    }

    private void addGridColumns() {
        grid.addColumn(AclRightsholderTotalsHolderDto::getWrWrkInst)
            .setCaption(ForeignUi.getMessage("table.column.wr_wrk_inst"))
            .setId(WR_WRK_INST_PROPERTY)
            .setSortProperty(WR_WRK_INST_PROPERTY)
            .setComparator((holder1, holder2) -> holder1.getWrWrkInst().compareTo(holder2.getWrWrkInst()))
            .setWidth(110);
        grid.addComponentColumn(holder -> {
            Button button = Buttons.createButton(Objects.toString(holder.getSystemTitle()));
            button.addStyleName(ValoTheme.BUTTON_LINK);
            // TODO implement
            button.addClickListener(event -> {});
            return button;
        }).setCaption(ForeignUi.getMessage("table.column.system_title"))
            .setId(SYSTEM_TITLE_PROPERTY)
            .setSortProperty(SYSTEM_TITLE_PROPERTY)
            .setComparator((holder1, holder2) -> holder1.getSystemTitle().compareToIgnoreCase(holder2.getSystemTitle()))
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
        // TODO {dbasiachenka} implement
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_titles_by_rightsholder.search.acl"));
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        HorizontalLayout layout = new HorizontalLayout(searchWidget);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        return layout;
    }
}
