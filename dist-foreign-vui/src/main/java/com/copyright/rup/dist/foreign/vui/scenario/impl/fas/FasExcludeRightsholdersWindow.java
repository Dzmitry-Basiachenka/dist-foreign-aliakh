package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.ConfirmWindows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget.ISearchController;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.StringLengthValidator;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents window with ability to exclude RH details for Source RRO.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 8/3/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class FasExcludeRightsholdersWindow extends CommonDialog implements ISearchController {

    private static final long serialVersionUID = -1083890548488635343L;
    private static final int MAX_LENGTH_MESSAGE = 1024;

    private final Long accountNumber;
    private final IFasScenarioController scenarioController;
    private Grid<RightsholderPayeePair> rightsholdersGrid;
    private SearchWidget searchWidget;

    /**
     * Constructor.
     *
     * @param rroAccountNumber   rro account number
     * @param scenarioController instance of {@link IFasScenarioController}
     */
    FasExcludeRightsholdersWindow(Long rroAccountNumber, IFasScenarioController scenarioController) {
        this.accountNumber = rroAccountNumber;
        this.scenarioController = scenarioController;
        super.setHeaderTitle(ForeignUi.getMessage("window.exclude.rh", rroAccountNumber));
        initContent();
    }

    @Override
    public void performSearch() {
        ListDataProvider<RightsholderPayeePair> dataProvider =
            (ListDataProvider<RightsholderPayeePair>) rightsholdersGrid.getDataProvider();
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

    private void initContent() {
        setWidth("830px");
        setHeight("500px");
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"));
        setModalWindowProperties("exclude-rightsholders-window", true);
        initGrid();
        getFooter().add(createButtonsLayout());
        add(VaadinUtils.initCommonVerticalLayout(searchWidget, rightsholdersGrid));
    }

    private void initGrid() {
        rightsholdersGrid = new Grid<>();
        VaadinUtils.setGridProperties(rightsholdersGrid, "exclude-rightsholders-table");
        rightsholdersGrid.setItems(scenarioController.getRightsholdersPayeePairs(accountNumber));
        rightsholdersGrid.setSelectionMode(SelectionMode.MULTI);
        rightsholdersGrid.setSizeFull();
        addColumns();
    }

    private void addColumns() {
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getAccountNumber())
            .setHeader(ForeignUi.getMessage("table.column.payee_account_number"))
            .setSortProperty("payee.accountNumber")
            .setResizable(true);
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getName())
            .setHeader(ForeignUi.getMessage("table.column.payee_name"))
            .setSortProperty("payee.name")
            .setResizable(true);
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getAccountNumber())
            .setHeader(ForeignUi.getMessage("table.column.rh_account_number"))
            .setSortProperty("rightsholder.accountNumber")
            .setResizable(true);
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getName())
            .setHeader(ForeignUi.getMessage("table.column.rh_account_name"))
            .setSortProperty("rightsholder.name")
            .setResizable(true);
    }

    private HorizontalLayout createButtonsLayout() {
        var confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> {
            Set<RightsholderPayeePair> selectedPairs = rightsholdersGrid.getSelectedItems();
            if (CollectionUtils.isNotEmpty(selectedPairs)) {
                ConfirmWindows.showConfirmDialogWithReason(
                    ForeignUi.getMessage("window.confirm"),
                    ForeignUi.getMessage("message.confirm.exclude.rightsholders"),
                    ForeignUi.getMessage("button.yes"),
                    ForeignUi.getMessage("button.cancel"),
                    reason -> {
                        List<Long> selectedIds = selectedPairs.stream()
                            .map(pair -> pair.getRightsholder().getAccountNumber())
                            .collect(Collectors.toList());
                        scenarioController.deleteFromScenario(accountNumber, selectedIds, reason);
                        fireEvent(new ExcludeUsagesEvent(this));
                        this.close();
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.length", MAX_LENGTH_MESSAGE), 0,
                        MAX_LENGTH_MESSAGE));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude_rro.empty"));
            }
        });
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> rightsholdersGrid.getSelectionModel().deselectAll());
        return new HorizontalLayout(confirmButton, clearButton, Buttons.createCloseButton(this));
    }
}
