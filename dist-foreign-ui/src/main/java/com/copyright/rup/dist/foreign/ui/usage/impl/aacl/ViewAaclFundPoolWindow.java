package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link FundPool}s.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/05/20
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final IAaclUsageController controller;
    private Grid<FundPool> grid;
    private Button deleteButton;
    private Button viewButton;

    /**
     * Constructor.
     *
     * @param controller {@link IAaclUsageController}
     */
    public ViewAaclFundPoolWindow(IAaclUsageController controller) {
        this.controller = controller;
        setWidth(900, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_fund_pool.search"));
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        initMediator();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_fund_pool"));
        VaadinUtils.addComponentStyle(this, "view-aacl-fund-pool-window");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<FundPool> dataProvider = (ListDataProvider<FundPool>) grid.getDataProvider();
        dataProvider.clearFilters();
        String search = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(search)) {
            dataProvider.setFilter(fundPool -> StringUtils.containsIgnoreCase(fundPool.getName(), search));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initMediator() {
        ViewAaclFundPoolMediator mediator = new ViewAaclFundPoolMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event -> {
            grid.getSelectedItems().stream().findFirst().ifPresent(selectedFundPool -> {
                String scenarioName = controller.getScenarioNameAssociatedWithFundPool(selectedFundPool.getId());
                if (Objects.isNull(scenarioName)) {
                    Windows.showConfirmDialog(
                        ForeignUi.getMessage("message.confirm.delete_action", selectedFundPool.getName(), "fund pool"),
                        () -> {
                            controller.deleteFundPool(selectedFundPool);
                            grid.setItems(controller.getFundPools());
                        });
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.delete_action", "Fund pool", "scenario",
                            ' ' + scenarioName));
                }
            });
        });
        deleteButton.setEnabled(false);
        viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
        viewButton.addClickListener(event -> {
            FundPool selectedFundPool = grid.getSelectedItems().stream().findFirst().orElse(null);
            Windows.showModalWindow(new ViewAaclFundPoolDetailsWindow(selectedFundPool,
                controller.getFundPoolDetails(selectedFundPool.getId())));
        });
        viewButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(viewButton, deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-aacl-fund-pool-buttons");
        return layout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getFundPools());
        grid.addSelectionListener(event -> {
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()));
            viewButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()));
        });
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-aacl-fund-pool-grid");
    }

    private void addGridColumns() {
        grid.addColumn(FundPool::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_pool_name"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getName().compareToIgnoreCase(fundPool2.getName()))
            .setExpandRatio(1);
        grid.addColumn(fundPool -> CurrencyUtils.format(fundPool.getTotalAmount(), null))
            .setCaption(ForeignUi.getMessage("table.column.gross_fund_pool_total"))
            .setComparator((fundPool1, fundPool2) ->
                fundPool1.getTotalAmount().compareTo(fundPool2.getTotalAmount()))
            .setWidth(170);
        grid.addColumn(FundPool::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.create_user"))
            .setComparator(
                (fundPool1, fundPool2) -> fundPool1.getCreateUser().compareToIgnoreCase(fundPool2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(fundPool -> getStringFromDate(fundPool.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getCreateDate().compareTo(fundPool2.getCreateDate()))
            .setExpandRatio(0);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }
}
