package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.dist.foreign.domain.RightsholderPayeePair;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.function.ValueProvider;

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
        super.setWidth("830px");
        super.setHeight("500px");
        super.setHeaderTitle(ForeignUi.getMessage("window.exclude.rh", rroAccountNumber));
        super.add(initContent());
        super.getFooter().add(createButtonsLayout());
        super.setModalWindowProperties("exclude-rightsholders-window", true);
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

    private VerticalLayout initContent() {
        searchWidget =
            new SearchWidget(this, ForeignUi.getMessage("field.prompt.scenario.search_widget.rh_payee"), "70%");
        initGrid();
        return VaadinUtils.initSizeFullVerticalLayout(searchWidget, rightsholdersGrid);
    }

    private void initGrid() {
        rightsholdersGrid = new Grid<>();
        rightsholdersGrid.setItems(scenarioController.getRightsholdersPayeePairs(accountNumber));
        rightsholdersGrid.setSelectionMode(SelectionMode.MULTI);
        addColumns();
        VaadinUtils.setGridProperties(rightsholdersGrid, "exclude-rightsholders-table");
    }

    private void addColumns() {
        addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getAccountNumber(),
            GridColumnEnum.PAYEE_ACCOUNT_NUMBER);
        addColumn(rightsholderPayeePair -> rightsholderPayeePair.getPayee().getName(), GridColumnEnum.PAYEE_NAME);
        addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getAccountNumber(),
            GridColumnEnum.RH_ACCOUNT_NUMBER);
        addColumn(rightsholderPayeePair -> rightsholderPayeePair.getRightsholder().getName(),
            GridColumnEnum.RH_NAME);
    }

    private void addColumn(ValueProvider<RightsholderPayeePair, ?> provider, GridColumnEnum gridColumn) {
        rightsholdersGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(gridColumn.getCaption()))
            .setSortable(true)
            .setSortProperty(gridColumn.getSort())
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
