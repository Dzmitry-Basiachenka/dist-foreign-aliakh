package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
public class FasExcludeRightsholdersWindow extends Window implements ISearchController {

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
        setCaption(ForeignUi.getMessage("window.exclude.rh", rroAccountNumber));
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

    /**
     * Adds {@link IExcludeUsagesListener} on window.
     *
     * @param listener instance of {@link IExcludeUsagesListener}
     */
    void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    private void initContent() {
        setWidth(830, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"));
        VaadinUtils.addComponentStyle(this, "exclude-rightsholders-window");
        initGrid();
        HorizontalLayout buttonsLayout = createButtonsLayout();
        VerticalLayout layout = new VerticalLayout(searchWidget, rightsholdersGrid, buttonsLayout);
        layout.setMargin(new MarginInfo(false, true, true, true));
        layout.setSizeFull();
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        layout.setExpandRatio(rightsholdersGrid, 1);
        setContent(layout);
    }

    private void initGrid() {
        rightsholdersGrid = new Grid<>();
        rightsholdersGrid.setItems(scenarioController.getRightsholdersPayeePairs(accountNumber));
        rightsholdersGrid.setSelectionMode(SelectionMode.MULTI);
        rightsholdersGrid.setSizeFull();
        VaadinUtils.addComponentStyle(rightsholdersGrid, "exclude-rightsholders-table");
        addColumns();
    }

    private void addColumns() {
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.payee_account_number"))
            .setSortProperty("payee.accountNumber");
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getName())
            .setCaption(ForeignUi.getMessage("table.column.payee_name"))
            .setSortProperty("payee.name");
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_number"))
            .setSortProperty("rightsholder.accountNumber");
        rightsholdersGrid.addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getName())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_name"))
            .setSortProperty("rightsholder.name");
    }

    private HorizontalLayout createButtonsLayout() {
        Button confirmButton = Buttons.createButton(ForeignUi.getMessage("button.confirm"));
        confirmButton.addClickListener(event -> {
            Set<RightsholderPayeePair> selectedPairs = rightsholdersGrid.getSelectedItems();
            if (CollectionUtils.isNotEmpty(selectedPairs)) {
                Windows.showConfirmDialogWithReason(
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
                    }, new StringLengthValidator(ForeignUi.getMessage("field.error.length", 1024), 0, 1024));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.exclude_rro.empty"));
            }
        });
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> rightsholdersGrid.getSelectionModel().deselectAll());
        return new HorizontalLayout(confirmButton, clearButton, Buttons.createCloseButton(this));
    }
}
