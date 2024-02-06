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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;

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

    private final SearchWidget searchWidget;
    private final INtsUsageController controller;
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
        searchWidget = new SearchWidget(this, ForeignUi.getMessage("prompt.fund_pool"), "70%");
        super.add(VaadinUtils.initSizeFullVerticalLayout(searchWidget, initGrid()));
        super.setHeaderTitle(ForeignUi.getMessage("window.view_fund"));
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

    //TODO {aliakh} fix column width
    private Grid<FundPool> initGrid() {
        grid = new Grid<>();
        grid.setItems(controller.getAdditionalFunds());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.addColumn(FundPool::getName)
            .setHeader(ForeignUi.getMessage("table.column.fund_name"))
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                fundPool1.getName().compareToIgnoreCase(fundPool2.getName()))
            .setSortProperty("name")
            .setFlexGrow(0)
            .setSortable(true)
            .setResizable(true);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getTotalAmount(), null))
            .setHeader(ForeignUi.getMessage("table.column.fund_amount"))
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                fundPool1.getTotalAmount().compareTo(fundPool2.getTotalAmount()))
            .setSortProperty("amount")
            .setClassNameGenerator(item -> "v-align-right")
            .setFlexGrow(0)
            .setWidth("100px")
            .setSortable(true)
            .setResizable(true);
        grid.addColumn(FundPool::getCreateUser)
            .setHeader(ForeignUi.getMessage("table.column.created_by"))
            .setSortProperty("createUser")
            .setFlexGrow(0)
            .setWidth("140px")
            .setSortable(true)
            .setResizable(true);
        grid.addColumn(FundPool::getComment)
            .setHeader(ForeignUi.getMessage("table.column.comment"))
            .setComparator((SerializableComparator<FundPool>) (fundPool1, fundPool2) ->
                Comparator.comparing(FundPool::getComment, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                    .compare(fundPool1, fundPool2))
            .setSortProperty("comment")
            .setFlexGrow(0)
            .setWidth("320px")
            .setSortable(true)
            .setResizable(true);
        grid.addComponentColumn(fundPool -> {
                Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
                deleteButton.setId(fundPool.getId());
                deleteButton.addClickListener(event -> deleteFund(fundPool));
                VaadinUtils.setButtonsAutoDisabled(deleteButton);
                return deleteButton;
            })
            .setWidth("90px")
            .setSortable(false)
            .setResizable(false)
            .setKey("delete")
            .setId("delete");
        VaadinUtils.setGridProperties(grid, "view-fund-pool-grid");
        return grid;
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
