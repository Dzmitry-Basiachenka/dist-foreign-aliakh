package com.copyright.rup.dist.foreign.vui.usage.impl.aacl;

import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.vui.common.IGridColumnAdder;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link FundPool}s.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/05/2020
 *
 * @author Stanislau Rudak
 */
public class ViewAaclFundPoolWindow extends CommonDialog implements ISearchController, IDateFormatter,
    IGridColumnAdder<FundPool> {

    private static final long serialVersionUID = 6467821318650140527L;

    private final IAaclUsageController usageController;
    private SearchWidget searchWidget;
    private Grid<FundPool> grid;
    private Button viewButton;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param usageController instance of {@link IAaclUsageController}
     */
    public ViewAaclFundPoolWindow(IAaclUsageController usageController) {
        this.usageController = usageController;
        super.setWidth("900px");
        super.setHeight("600px");
        super.setHeaderTitle(ForeignUi.getMessage("window.view_fund_pool"));
        super.add(initContent());
        super.getFooter().add(initButtonsLayout());
        initMediator();
        super.setModalWindowProperties("view-aacl-fund-pool-window", true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        var dataProvider = (ListDataProvider<FundPool>) grid.getDataProvider();
        dataProvider.clearFilters();
        var search = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(search)) {
            dataProvider.setFilter(fundPool -> StringUtils.containsIgnoreCase(fundPool.getName(), search));
        }
    }

    private void initMediator() {
        var mediator = new ViewAaclFundPoolMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private VerticalLayout initContent() {
        return VaadinUtils.initSizeFullVerticalLayout(initSearchWidget(), initGrid());
    }

    private SearchWidget initSearchWidget() {
        searchWidget = new SearchWidget(this, ForeignUi.getMessage("field.prompt.view_fund_pool.search"), "70%");
        return searchWidget;
    }

    private Grid<FundPool> initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(usageController.getFundPools());
        grid.addSelectionListener(event -> {
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()));
            viewButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems()));
        });
        addGridColumns();
        VaadinUtils.setGridProperties(grid, "view-aacl-fund-pool-grid");
        return grid;
    }

    private void addGridColumns() {
        addColumn(grid, FundPool::getName, GridColumnEnum.FUND_POOL_NAME,
            (fundPool1, fundPool2) -> fundPool1.getName().compareToIgnoreCase(fundPool2.getName()));
        addColumn(grid, fundPool -> CurrencyUtils.format(fundPool.getTotalAmount(), null),
            GridColumnEnum.GROSS_FUND_POOL_TOTAL,
            (fundPool1, fundPool2) -> fundPool1.getTotalAmount().compareTo(fundPool2.getTotalAmount()));
        addColumn(grid, FundPool::getCreateUser, GridColumnEnum.CREATED_USER,
            (fundPool1, fundPool2) -> fundPool1.getCreateUser().compareToIgnoreCase(fundPool2.getCreateUser()));
        addColumn(grid, fundPool -> toLongFormat(fundPool.getCreateDate()), GridColumnEnum.CREATED_DATE,
            (fundPool1, fundPool2) -> fundPool1.getCreateDate().compareTo(fundPool2.getCreateDate()));
    }

    private HorizontalLayout initButtonsLayout() {
        return new HorizontalLayout(initViewButton(), initDeleteButton(), Buttons.createCloseButton(this));
    }

    private Button initViewButton() {
        viewButton = Buttons.createButton(ForeignUi.getMessage("button.view"));
        viewButton.addClickListener(event -> {
            grid.getSelectedItems().stream().findFirst().ifPresent(fundPool -> {
                Windows.showModalWindow(new ViewAaclFundPoolDetailsWindow(fundPool,
                    usageController.getFundPoolDetails(fundPool.getId())));
            });
        });
        viewButton.setEnabled(false);
        return viewButton;
    }

    private Button initDeleteButton() {
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event -> {
            grid.getSelectedItems().stream().findFirst().ifPresent(fundPool -> {
                var scenarioName = usageController.getScenarioNameAssociatedWithFundPool(fundPool.getId());
                if (Objects.isNull(scenarioName)) {
                    Windows.showConfirmDialog(
                        ForeignUi.getMessage("message.confirm.delete_action", fundPool.getName(), "fund pool"),
                        () -> {
                            usageController.deleteFundPool(fundPool);
                            grid.setItems(usageController.getFundPools());
                        });
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.delete_action", "Fund pool", "scenario",
                            ' ' + scenarioName));
                }
            });
        });
        deleteButton.setEnabled(false);
        return deleteButton;
    }
}
