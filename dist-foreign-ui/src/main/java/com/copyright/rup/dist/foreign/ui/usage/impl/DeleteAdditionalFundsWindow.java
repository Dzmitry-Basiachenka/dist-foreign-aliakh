package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.PreServiceFeeFund;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
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

import java.util.Objects;

/**
 * Modal window that provides functionality for deleting {@link PreServiceFeeFund}s.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 03/28/19
 *
 * @author Ihar Suvorau
 */
class DeleteAdditionalFundsWindow extends Window {

    private final SearchWidget searchWidget;
    private final IUsagesController controller;
    private Grid<PreServiceFeeFund> grid;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUsagesController}
     */
    DeleteAdditionalFundsWindow(IUsagesController controller) {
        this.controller = controller;
        setCaption(ForeignUi.getMessage("window.delete_fund"));
        setWidth(700, Unit.PIXELS);
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
        grid.setItems(controller.getPreServiceSeeFunds());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.addColumn(PreServiceFeeFund::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_name"))
            .setComparator((SerializableComparator<PreServiceFeeFund>) (fund1, fund2) ->
                fund1.getName().compareToIgnoreCase(fund2.getName()))
            .setSortProperty("name")
            .setExpandRatio(1);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.fund_amount"))
            .setComparator((SerializableComparator<PreServiceFeeFund>) (fund1, fund2) ->
                fund1.getAmount().compareTo(fund2.getAmount()))
            .setSortProperty("amount")
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(100);
        grid.addColumn(PreServiceFeeFund::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.create_user"))
            .setSortProperty("createUser")
            .setWidth(140);
        grid.addComponentColumn(fundPool -> {
            Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
            deleteButton.setId(fundPool.getId());
            deleteButton.addClickListener(event -> deleteFund(fundPool));
            return deleteButton;
        }).setId("delete")
            .setWidth(90)
            .setSortable(false);
        VaadinUtils.addComponentStyle(grid, "delete-fund-pool-grid");
    }

    private void deleteFund(PreServiceFeeFund fundPool) {
        String scenarioName = controller.getScenarioNameAssociatedWithPreServiceFeeFund(fundPool.getId());
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

    private void performDelete(PreServiceFeeFund fundPool) {
        controller.deletePreServiceFeeFund(fundPool);
        grid.setItems(controller.getPreServiceSeeFunds());
    }

    /**
     * {@link ISearchController} implementation.
     */
    class SearchController implements ISearchController {

        @Override
        public void performSearch() {
            ListDataProvider<PreServiceFeeFund> dataProvider =
                (ListDataProvider<PreServiceFeeFund>) grid.getDataProvider();
            dataProvider.clearFilters();
            String searchValue = searchWidget.getSearchValue();
            if (StringUtils.isNotBlank(searchValue)) {
                dataProvider.setFilter(fundPool -> StringUtils.contains(StringUtils.lowerCase(fundPool.getName()),
                    StringUtils.lowerCase(searchValue)));
            }
        }
    }
}
