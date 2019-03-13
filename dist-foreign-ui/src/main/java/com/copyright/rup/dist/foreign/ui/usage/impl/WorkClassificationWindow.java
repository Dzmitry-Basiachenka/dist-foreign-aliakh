package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Works classification window.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
public class WorkClassificationWindow extends Window {

    private Grid<WorkClassification> grid;
    private SearchWidget searchWidget;
    private DataProvider<WorkClassification, Void> dataProvider;
    private final IWorkClassificationController controller;
    private final Set<String> selectedBatchesIds;

    /**
     * Constructor.
     *
     * @param batchesIds set of batches ids
     * @param controller instance of {@link IWorkClassificationController}
     */
    public WorkClassificationWindow(Set<String> batchesIds, IWorkClassificationController controller) {
        this.controller = controller;
        selectedBatchesIds = batchesIds;
        setWidth(1000, Unit.PIXELS);
        setHeight(530, Unit.PIXELS);
        setContent(initContent());
        setCaption(ForeignUi.getMessage("window.works_classification"));
    }

    private Component initContent() {
        initGrid();
        HorizontalLayout buttons = initButtons();
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.works_classification"));
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        VerticalLayout content = new VerticalLayout(searchWidget, grid, buttons);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(grid, 1.0f);
        content.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(buttons, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout initButtons() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> grid.deselectAll());
        Button markAsStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_stm"));
        addClickListener(markAsStmButton, "message.confirm.classification.update_usage",
            (classifications) -> controller.updateClassifications(classifications, FdaConstants.STM_CLASSIFICATION));
        Button markAsNonStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_non_stm"));
        addClickListener(markAsNonStmButton, "message.confirm.classification.update_usage",
            (classifications) -> controller.updateClassifications(classifications,
                FdaConstants.NON_STM_CLASSIFICATION));
        Button markAsBelletristicButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_belletristic"));
        addClickListener(markAsBelletristicButton, "message.confirm.classification.delete_usage",
            (classifications) -> controller.updateClassifications(classifications,
                FdaConstants.BELLETRISTIC_CLASSIFICATION));
        Button removeClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.remove_classification"));
        addClickListener(removeClassificationButton, "message.confirm.remove_classification",
            controller::deleteClassification);
        buttonsLayout.addComponents(markAsStmButton, markAsNonStmButton, markAsBelletristicButton,
            removeClassificationButton, clearButton, Buttons.createCloseButton(this));
        buttonsLayout.setSpacing(true);
        return buttonsLayout;
    }

    private void initGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.getClassifications(selectedBatchesIds, searchWidget.getSearchValue(),
                query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getClassificationCount(selectedBatchesIds,
                searchWidget.getSearchValue()));
        grid = new Grid<>(dataProvider);
        addColumns();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "works-classification-grid");
    }

    private void addColumns() {
        addColumn(WorkClassification::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100);
        addColumn(WorkClassification::getSystemTitle, "table.column.system_title", "systemTitle", 200);
        addColumn(WorkClassification::getArticle, "table.column.article", "article", 140);
        addColumn(WorkClassification::getAuthor, "table.column.author", "author", 100);
        addColumn(WorkClassification::getPublisher, "table.column.publisher", "publisher", 140);
        addColumn(WorkClassification::getClassification, "table.column.classification", "classification", 110);
        addColumn(WorkClassification::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(WorkClassification::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 100);
        addColumn(WorkClassification::getRhName, "table.column.rh_account_name", "rhName", 200);
    }

    private void addColumn(ValueProvider<WorkClassification, ?> provider, String captionProperty, String sort,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setWidth(width);
    }

    private void addClickListener(Button button, String message, Consumer<Set<WorkClassification>> consumer) {
        button.addClickListener(event -> {
            Set<WorkClassification> selectedItems = grid.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                Windows.showConfirmDialog(ForeignUi.getMessage(message, controller.getCountToUpdate(selectedItems)),
                    () -> {
                        consumer.accept(selectedItems);
                        dataProvider.refreshAll();
                        grid.setDataProvider(dataProvider);
                    });
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.classification.empty"));
            }
        });
    }
}
