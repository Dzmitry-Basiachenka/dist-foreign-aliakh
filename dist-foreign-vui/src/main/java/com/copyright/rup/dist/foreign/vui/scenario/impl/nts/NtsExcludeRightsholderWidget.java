package com.copyright.rup.dist.foreign.vui.scenario.impl.nts;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderController;
import com.copyright.rup.dist.foreign.vui.scenario.api.nts.INtsExcludeRightsholderWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Represents window with ability to exclude details by Rightsholder.
 *
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/03/2020
 *
 * @author Anton Azarenka
 */
public class NtsExcludeRightsholderWidget extends CommonDialog implements INtsExcludeRightsholderWidget {

    private static final long serialVersionUID = 8680276555307729867L;

    private SearchWidget searchWidget;
    private INtsExcludeRightsholderController controller;
    private Grid<RightsholderPayeePair> rightsholderPayeePairGrid;
    private ListDataProvider<RightsholderPayeePair> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public NtsExcludeRightsholderWidget init() {
        setWidth("830px");
        setHeight("500px");
        setHeaderTitle(ForeignUi.getMessage("window.exclude.rightsholder"));
        add(initContent());
        getFooter().add(createButtonsLayout());
        setModalWindowProperties("exclude-details-by-rightsholder-window", true);
        return this;
    }

    @Override
    public void refresh() {
        initDataProvider();
        performSearch();
    }

    @Override
    public void setController(INtsExcludeRightsholderController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public Set<Long> getSelectedAccountNumbers() {
        return rightsholderPayeePairGrid.getSelectedItems()
            .stream()
            .map(holder -> holder.getRightsholder().getAccountNumber())
            .collect(Collectors.toSet());
    }

    private VerticalLayout initContent() {
        initGrid();
        searchWidget = new SearchWidget(this::performSearch,
            ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"), "70%");
        return VaadinUtils.initSizeFullVerticalLayout(searchWidget, rightsholderPayeePairGrid);
    }

    private void initGrid() {
        rightsholderPayeePairGrid = new Grid<>();
        rightsholderPayeePairGrid.setSelectionMode(SelectionMode.MULTI);
        initDataProvider();
        addColumns();
        VaadinUtils.setGridProperties(rightsholderPayeePairGrid, "exclude-details-by-rightsholder-grid");
    }

    private void initDataProvider() {
        dataProvider = DataProvider.ofCollection(controller.getRightsholderPayeePairs());
        rightsholderPayeePairGrid.setItems(dataProvider);
    }

    private void addColumns() {
        addColumn(holder -> holder.getRightsholder().getAccountNumber(), GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(holder -> holder.getRightsholder().getName(), GridColumnEnum.RH_NAME)
            .setComparator((SerializableComparator<RightsholderPayeePair>) (holder1, holder2) ->
                holder1.getRightsholder().getName().compareToIgnoreCase(holder2.getRightsholder().getName()));
        addColumn(holder -> holder.getPayee().getAccountNumber(), GridColumnEnum.PAYEE_ACCOUNT_NUMBER);
        addColumn(holder -> holder.getPayee().getName(), GridColumnEnum.PAYEE_NAME)
            .setComparator((SerializableComparator<RightsholderPayeePair>) (holder1, holder2) ->
                holder1.getPayee().getName().compareToIgnoreCase(holder2.getPayee().getName()));
    }

    private Column<RightsholderPayeePair> addColumn(ValueProvider<RightsholderPayeePair, ?> provider,
                                                    GridColumnEnum gridColumn) {
        return rightsholderPayeePairGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(gridColumn.getCaption()))
            .setSortable(true)
            .setSortProperty(gridColumn.getSort())
            .setResizable(true);
    }

    private HorizontalLayout createButtonsLayout() {
        var excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        addClickListener(excludeDetails,
            (accountNumbers, reason) -> controller.excludeDetails(accountNumbers, reason));
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> rightsholderPayeePairGrid.getSelectionModel().deselectAll());
        return new HorizontalLayout(excludeDetails, clearButton, Buttons.createCloseButton(this));
    }

    private void addClickListener(Button button, BiConsumer<Set<Long>, String> actionHandler) {
        button.addClickListener(event -> {
            Set<Long> selectedAccountNumbers = getSelectedAccountNumbers();
            if (CollectionUtils.isNotEmpty(selectedAccountNumbers)) {
                ConfirmWindows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.exclude.rightsholders"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        actionHandler.accept(selectedAccountNumbers, reason);
                        fireEvent(new ExcludeUsagesEvent(this));
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.empty.length", 1024), 1, 1024));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude_rro.empty"));
            }
        });
    }

    private void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(pair ->
                StringUtils.containsIgnoreCase(pair.getPayee().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getPayee().getName(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getRightsholder().getAccountNumber().toString(), searchValue)
                    || StringUtils.containsIgnoreCase(pair.getRightsholder().getName(), searchValue));
        }
    }
}
