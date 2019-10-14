package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IWorkClassificationController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.components.grid.MultiSelectionModel.SelectAllCheckBoxVisibility;
import com.vaadin.ui.components.grid.MultiSelectionModelImpl;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Objects;
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
    private MultiSelectionModelImpl gridSelectionModel;
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
        HorizontalLayout toolbarLayout = buildToolbarLayout();
        HorizontalLayout buttonsLayout = buildButtonsLayout();
        Label label = new Label(ForeignUi.getMessage("label.work.classification.note"), ContentMode.HTML);
        label.setStyleName("label-note");
        VerticalLayout content = new VerticalLayout(label, toolbarLayout, grid, buttonsLayout);
        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.setExpandRatio(grid, 1.0f);
        content.setComponentAlignment(toolbarLayout, Alignment.MIDDLE_CENTER);
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private HorizontalLayout buildToolbarLayout() {
        initSearchWidget();
        Button exportButton = buildExportButton();
        HorizontalLayout toolbarLayout = new HorizontalLayout(exportButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbarLayout);
        toolbarLayout.setComponentAlignment(exportButton, Alignment.BOTTOM_LEFT);
        toolbarLayout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbarLayout.setExpandRatio(searchWidget, 1f);
        toolbarLayout.setMargin(new MarginInfo(false, true, false, true));
        return toolbarLayout;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.works_classification"));
        searchWidget.setWidth(70, Unit.PERCENTAGE);
    }

    private Button buildExportButton() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader =
            new OnDemandFileDownloader(
                controller.getExportWorkClassificationStreamSource(selectedBatchesIds, searchWidget::getSearchValue)
                    .getSource());
        fileDownloader.extend(exportButton);
        return exportButton;
    }

    private HorizontalLayout buildButtonsLayout() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> grid.deselectAll());
        Button markAsStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_stm"));
        addClickListener(markAsStmButton, "message.confirm.classification.update_usage", (classifications) ->
            controller.updateClassifications(classifications, FdaConstants.STM_CLASSIFICATION));
        Button markAsNonStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_non_stm"));
        addClickListener(markAsNonStmButton, "message.confirm.classification.update_usage", (classifications) ->
            controller.updateClassifications(classifications, FdaConstants.NON_STM_CLASSIFICATION));
        Button markAsBelletristicButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_belletristic"));
        addClickListener(markAsBelletristicButton, "message.confirm.classification.delete_usage", (classifications) ->
            controller.updateClassifications(classifications, FdaConstants.BELLETRISTIC_CLASSIFICATION));
        Button deleteClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.delete_classification"));
        addClickListener(deleteClassificationButton, "message.confirm.delete_classification",
            controller::deleteClassification);
        buttonsLayout.addComponents(markAsStmButton, markAsNonStmButton, markAsBelletristicButton,
            deleteClassificationButton, clearButton, Buttons.createCloseButton(this));
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
        gridSelectionModel = (MultiSelectionModelImpl) grid.setSelectionMode(SelectionMode.MULTI);
        gridSelectionModel.setSelectAllCheckBoxVisibility(SelectAllCheckBoxVisibility.VISIBLE);
        grid.getColumns().forEach(column -> column.setSortable(true));
        grid.addItemClickListener((ItemClickListener<WorkClassification>) event -> {
            if (!event.getMouseEventDetails().isDoubleClick()) {
                WorkClassification classification = event.getItem();
                if (gridSelectionModel.isSelected(classification)) {
                    gridSelectionModel.deselect(classification);
                } else {
                    gridSelectionModel.select(classification);
                }
            }
        });
        VaadinUtils.addComponentStyle(grid, "works-classification-grid");
    }

    private void addColumns() {
        addColumn(WorkClassification::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100);
        addColumn(WorkClassification::getSystemTitle, "table.column.system_title", "systemTitle", 285);
        addColumn(WorkClassification::getClassification, "table.column.classification", "classification", 110);
        addColumn(WorkClassification::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(
            WorkClassification::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", 155);
        addColumn(WorkClassification::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 100);
        grid.addColumn(WorkClassification::getRhName)
            .setCaption(ForeignUi.getMessage("table.column.rh_account_name"))
            .setSortProperty("rhName");
        addClassificationDateColumn(WorkClassification::getUpdateDate);
        addColumn(WorkClassification::getUpdateUser, "table.column.classified_by", "updateUser", 245);
    }

    private void addColumn(ValueProvider<WorkClassification, ?> provider, String captionProperty, String sort,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setWidth(width);
    }

    private void addClassificationDateColumn(ValueProvider<WorkClassification, Date> provider) {
        grid.addColumn(date -> Objects.nonNull(provider.apply(date))
                ? DateFormatUtils.format(provider.apply(date), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
                : null)
            .setCaption(ForeignUi.getMessage("table.column.classification_date"))
            .setSortProperty("updateDate")
            .setWidth(135);
    }

    private void addClickListener(Button button, String message, Consumer<Set<WorkClassification>> consumer) {
        button.addClickListener(event -> {
            Set<WorkClassification> selectedItems = getSelectedItems();
            if (!selectedItems.isEmpty()) {
                int usageCount = controller.getCountToUpdate(selectedItems);
                Windows.showConfirmDialog(0 < usageCount
                        ? ForeignUi.getMessage(message, usageCount)
                        : ForeignUi.getMessage("message.confirm.action"),
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

    private Set<WorkClassification> getSelectedItems() {
        // Server selection is not synched with UI-client on search changes while "allSelected" is checked.
        // See https://github.com/vaadin/framework/issues/11479
        if (gridSelectionModel.isAllSelected()) {
            gridSelectionModel.selectAll();
        }
        return grid.getSelectedItems();
    }
}
