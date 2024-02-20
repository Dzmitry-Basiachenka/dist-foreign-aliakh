package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.INtsUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link FundPool}s.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/28/2019
 *
 * @author Ihar Suvorau
 */
class ViewAdditionalFundsWindow extends CommonDialog implements ISearchController {

    private static final long serialVersionUID = -2993574115589450787L;

    private final INtsUsageController controller;
    private SearchWidget searchWidget;
    private Grid<FundPool> grid;

    /**
     * Constructor.
     *
     * @param controller instance of {@link INtsUsageController}
     */
    ViewAdditionalFundsWindow(INtsUsageController controller) {
        this.controller = controller;
        super.setWidth("1100px");
        super.setHeight("450px");
        super.setHeaderTitle(ForeignUi.getMessage("window.view_fund"));
        super.add(initContent());
        super.getFooter().add(Buttons.createCloseButton(this));
        setModalWindowProperties("view-fund-pool-window", true);
    }

    @Override
    public void performSearch() {
        var dataProvider = (ListDataProvider<FundPool>) grid.getDataProvider();
        dataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(fundPool -> StringUtils.contains(StringUtils.lowerCase(fundPool.getName()),
                StringUtils.lowerCase(searchValue)));
        }
    }

    private VerticalLayout initContent() {
        searchWidget = new SearchWidget(this, ForeignUi.getMessage("prompt.fund_pool"), "70%");
        return VaadinUtils.initSizeFullVerticalLayout(searchWidget, initGrid());
    }

    private Grid<FundPool> initGrid() {
        grid = new Grid<>();
        grid.setItems(controller.getAdditionalFunds());
        grid.setSelectionMode(SelectionMode.NONE);
        addColumn(FundPool::getName, "table.column.fund_name", "name")
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                fundPool1.getName().compareToIgnoreCase(fundPool2.getName()));
        addColumn(
            fundPool -> CurrencyUtils.format(fundPool.getTotalAmount(), null), "table.column.fund_amount", "amount")
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                fundPool1.getTotalAmount().compareTo(fundPool2.getTotalAmount()))
            .setClassNameGenerator(item -> "v-align-right")
            .setFlexGrow(0)
            .setWidth("140px");
        addColumn(FundPool::getCreateUser, "table.column.created_by", "createUser")
            .setFlexGrow(0)
            .setWidth("300px");
        addColumn(FundPool::getComment, "table.column.comment", "comment")
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                Comparator.comparing(FundPool::getComment, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                    .compare(fundPool1, fundPool2))
            .setFlexGrow(0)
            .setWidth("200px");
        grid.addComponentColumn(fundPool -> {
                var deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
                deleteButton.setId(fundPool.getId());
                deleteButton.addClickListener(event -> deleteFund(fundPool));
                VaadinUtils.setButtonsAutoDisabled(deleteButton);
                return deleteButton;
            })
            .setFlexGrow(0)
            .setWidth("90px")
            .setSortable(false)
            .setResizable(false)
            .setKey("delete")
            .setId("delete");
        VaadinUtils.setGridProperties(grid, "view-fund-pool-grid");
        return grid;
    }

    private Column<FundPool> addColumn(ValueProvider<FundPool, ?> provider, String captionProperty, String sort) {
        return grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(sort)
            .setResizable(true);
    }

    private void deleteFund(FundPool fundPool) {
        var scenarioName = controller.getScenarioNameAssociatedWithAdditionalFund(fundPool.getId());
        if (Objects.nonNull(scenarioName)) {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.delete_action", "Pre-Service Fee Fund", "scenario",
                    ' ' + scenarioName));
        } else {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", fundPool.getName(), "additional fund"),
                () -> performDelete(fundPool));
        }
    }

    private void performDelete(FundPool fundPool) {
        controller.deleteAdditionalFund(fundPool);
        grid.setItems(controller.getAdditionalFunds());
    }
}
