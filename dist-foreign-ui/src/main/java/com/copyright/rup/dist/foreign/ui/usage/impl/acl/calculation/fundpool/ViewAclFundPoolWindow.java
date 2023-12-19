package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.fundpool;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclFundPoolController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
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

import java.util.List;

/**
 * Modal window that provides functionality for viewing and deleting {@link AclFundPool}s.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 05/17/2022
 *
 * @author Anton Azarenka
 */
public class ViewAclFundPoolWindow extends Window implements SearchWidget.ISearchController, IDateFormatter {

    private static final long serialVersionUID = -1526177730598647509L;

    private final SearchWidget searchWidget;
    private final IAclFundPoolController controller;

    private Grid<AclFundPool> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclFundPoolController}
     */
    public ViewAclFundPoolWindow(IAclFundPoolController controller) {
        this.controller = controller;
        super.setWidth(1000, Unit.PIXELS);
        super.setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_fund_pool.search"));
        initFundPoolGrid();
        var buttonsLayout = initButtons();
        initMediator();
        var layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        super.setCaption(ForeignUi.getMessage("window.view_fund_pool"));
        VaadinUtils.addComponentStyle(this, "view-acl-fund-pool-window");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<AclFundPool> dataProvider = (ListDataProvider<AclFundPool>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(grantSet -> StringUtils.containsIgnoreCase(grantSet.getName(), searchValue));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initFundPoolGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getAllAclFundPools());
        grid.addSelectionListener(
            event -> deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-acl-fund-pool-grid");
    }

    private void addGridColumns() {
        grid.addColumn(AclFundPool::getName)
            .setCaption(ForeignUi.getMessage("table.column.fund_pool_name"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getName().compareToIgnoreCase(fundPool2.getName()))
            .setExpandRatio(1);
        grid.addColumn(AclFundPool::getLicenseType)
            .setCaption(ForeignUi.getMessage("table.column.license_type"))
            .setWidth(100);
        grid.addColumn(AclFundPool::getTotalAmount)
            .setCaption(ForeignUi.getMessage("table.column.gross_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getTotalAmount().compareTo(fundPool2.getTotalAmount()))
            .setWidth(110);
        grid.addColumn(AclFundPool::getNetAmount)
            .setCaption(ForeignUi.getMessage("table.column.net_amount"))
            .setComparator((fundPool1, fundPool2) -> fundPool1.getNetAmount().compareTo(fundPool2.getNetAmount()))
            .setWidth(110);
        grid.addColumn(fundPool -> fundPool.isManualUploadFlag() ? "Manual" : "LDMT")
            .setCaption(ForeignUi.getMessage("table.column.source"))
            .setWidth(80);
        grid.addColumn(AclFundPool::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.created_by"))
            .setComparator(
                (fundPool1, fundPool2) -> fundPool1.getCreateUser().compareToIgnoreCase(fundPool2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(fundPool -> toLongFormat(fundPool.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setWidth(130);
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event ->
            deleteFundPool(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        VaadinUtils.addComponentStyle(layout, "view-acl-fund-pool-buttons");
        return layout;
    }

    private void initMediator() {
        ViewAclFundPoolMediator mediator = new ViewAclFundPoolMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private void deleteFundPool(AclFundPool fundPool) {
        List<String> scenarioNames = controller.getScenarioNamesAssociatedWithFundPool(fundPool.getId());
        if (CollectionUtils.isEmpty(scenarioNames)) {
            Windows.showConfirmDialog(ForeignUi.getMessage(fundPool.isManualUploadFlag()
                    ? "message.confirm.delete_action"
                    : "message.confirm.delete_acl_fund_pool", fundPool.getName(), "fund pool"),
                () -> {
                    controller.deleteAclFundPool(fundPool);
                    grid.setItems(controller.getAllAclFundPools());
                });
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.error.delete_action", "Fund pool", "scenario(s)", scenarioNames));
        }
    }

    private String buildNotificationMessage(String key, String param, String associatedField,
                                            List<String> associatedNames) {
        StringBuilder htmlNamesList = new StringBuilder("<ul>");
        for (String name : associatedNames) {
            htmlNamesList.append("<li>").append(name).append("</li>");
        }
        htmlNamesList.append("</ul>");
        return ForeignUi.getMessage(key, param, associatedField, htmlNamesList.toString());
    }
}
