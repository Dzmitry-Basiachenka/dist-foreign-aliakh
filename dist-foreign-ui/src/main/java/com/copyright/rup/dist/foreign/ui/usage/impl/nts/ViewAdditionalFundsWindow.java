package com.copyright.rup.dist.foreign.ui.usage.impl.nts;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.nts.INtsUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link FundPool}s.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/28/19
 *
 * @author Ihar Suvorau
 */
class ViewAdditionalFundsWindow extends Window {

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
        setCaption(ForeignUi.getMessage("window.view_fund"));
        setWidth(1100, Unit.PIXELS);
        setHeight(450, Unit.PIXELS);
        searchWidget = new SearchWidget(new SearchController());
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.fund_pool"));
        initUsageBatchesGrid();
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, closeButton);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        setContent(layout);
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setItems(controller.getAdditionalFunds());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.addColumn(FundPool::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_name"))
            .setComparator((SerializableComparator<FundPool>) (fund1, fund2) ->
                fund1.getName().compareToIgnoreCase(fund2.getName()))
            .setSortProperty("name")
            .setExpandRatio(1);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getTotalAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.fund_amount"))
            .setComparator((SerializableComparator<FundPool>) (fund1, fund2) ->
                fund1.getTotalAmount().compareTo(fund2.getTotalAmount()))
            .setSortProperty("amount")
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(100);
        grid.addColumn(FundPool::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.created_by"))
            .setSortProperty("createUser")
            .setWidth(140);
        grid.addColumn(FundPool::getComment)
            .setCaption(ForeignUi.getMessage("table.column.comment"))
            .setComparator((SerializableComparator<FundPool>) (fund1, fund2) ->
                Comparator.comparing(FundPool::getComment, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                    .compare(fund1, fund2))
            .setSortProperty("comment")
            .setWidth(320);
        grid.addComponentColumn(fundPool -> {
            Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
            deleteButton.setId(fundPool.getId());
            deleteButton.addClickListener(event -> deleteFund(fundPool));
            return deleteButton;
        }).setId("delete")
            .setWidth(90)
            .setSortable(false);
        VaadinUtils.addComponentStyle(grid, "view-fund-pool-grid");
    }

    private void deleteFund(FundPool fundPool) {
        String scenarioName = controller.getScenarioNameAssociatedWithAdditionalFund(fundPool.getId());
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

    /**
     * {@link ISearchController} implementation.
     */
    class SearchController implements ISearchController {

        @Override
        public void performSearch() {
            ListDataProvider<FundPool> dataProvider =
                (ListDataProvider<FundPool>) grid.getDataProvider();
            dataProvider.clearFilters();
            String searchValue = searchWidget.getSearchValue();
            if (StringUtils.isNotBlank(searchValue)) {
                dataProvider.setFilter(fundPool -> StringUtils.contains(StringUtils.lowerCase(fundPool.getName()),
                    StringUtils.lowerCase(searchValue)));
            }
        }
    }
}
