package com.copyright.rup.dist.foreign.vui.usage.impl.nts;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.WorkClassification;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.nts.IWorkClassificationController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.AbstractGridMultiSelectionModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridMultiSelectionModel.SelectAllCheckboxVisibility;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Works classification window.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 03/07/2019
 *
 * @author Ihar Suvorau
 */
class WorkClassificationWindow extends CommonDialog {

    private static final long serialVersionUID = -4120963939990636156L;

    private final IWorkClassificationController controller;
    private final Set<String> selectedBatchesIds;
    private SearchWidget searchWidget;
    private Grid<WorkClassification> grid;
    private ListDataProvider<WorkClassification> listDataProvider;
    private DataProvider<WorkClassification, Void> dataProvider;

    /**
     * Constructor.
     *
     * @param batchesIds set of batches ids
     * @param controller instance of {@link IWorkClassificationController}
     */
    WorkClassificationWindow(Set<String> batchesIds, IWorkClassificationController controller) {
        this.controller = controller;
        this.selectedBatchesIds = batchesIds;
        super.setWidth("1000px");
        super.setHeight("530px");
        super.add(initRootLayout());
        super.setHeaderTitle(ForeignUi.getMessage("window.works_classification"));
        super.getFooter().add(initButtonsLayout());
        super.setModalWindowProperties("works-classification-window", true);
    }

    private VerticalLayout initRootLayout() {
        var rootLayout = VaadinUtils.initSizeFullVerticalLayout(initLabel(), initToolbarLayout(), initGrid());
        VaadinUtils.setPadding(rootLayout, 10, 10, 0, 10);
        return rootLayout;
    }

    private Html initLabel() {
        var label = new Html(ForeignUi.getMessage("label.work.classification.note"));
        VaadinUtils.addComponentStyle(label, "label-note");
        return label;
    }

    private HorizontalLayout initToolbarLayout() {
        initSearchWidget();
        var toolbarLayout = new HorizontalLayout(initExportDownloader(), searchWidget);
        toolbarLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbarLayout.setWidthFull();
        VaadinUtils.setPadding(toolbarLayout, 0, 3, 0, 0);
        return toolbarLayout;
    }

    private OnDemandFileDownloader initExportDownloader() {
        var exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        var exportDownloader = new OnDemandFileDownloader(
            controller.getExportWorkClassificationStreamSource(selectedBatchesIds, searchWidget::getSearchValue)
                .getSource());
        exportDownloader.extend(exportButton);
        return exportDownloader;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(this::performSearch, ForeignUi.getMessage("prompt.works_classification"),
            "70%");
    }

    private HorizontalLayout initButtonsLayout() {
        var buttonsLayout = new HorizontalLayout();
        var markAsStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_stm"));
        addClickListener(markAsStmButton, "message.confirm.classification.update_usage",
            (classifications) -> {
                controller.updateClassifications(classifications, FdaConstants.STM_CLASSIFICATION);
                refreshDataProvider();
            });
        var markAsNonStmButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_non_stm"));
        addClickListener(markAsNonStmButton, "message.confirm.classification.update_usage",
            (classifications) -> {
                controller.updateClassifications(classifications, FdaConstants.NON_STM_CLASSIFICATION);
                refreshDataProvider();
            });
        var markAsBelletristicButton = Buttons.createButton(ForeignUi.getMessage("button.mark_as_belletristic"));
        addClickListener(markAsBelletristicButton, "message.confirm.classification.delete_usage",
            (classifications) -> {
                controller.updateClassifications(classifications, FdaConstants.BELLETRISTIC_CLASSIFICATION);
                updateAndRefreshDataProvider();
            });
        var deleteClassificationButton = Buttons.createButton(ForeignUi.getMessage("button.delete_classification"));
        addClickListener(deleteClassificationButton, "message.confirm.delete_classification",
            (classifications) -> {
                controller.deleteClassification(classifications);
                updateAndRefreshDataProvider();
            });
        var clearButton = Buttons.createButton(ForeignUi.getMessage("button.clear"));
        clearButton.addClickListener(event -> grid.deselectAll());
        var closeButton = Buttons.createCloseButton(this);
        VaadinUtils.setButtonsAutoDisabled(markAsStmButton, markAsNonStmButton, markAsBelletristicButton,
            deleteClassificationButton, clearButton, closeButton);
        buttonsLayout.add(markAsStmButton, markAsNonStmButton, markAsBelletristicButton,
            deleteClassificationButton, clearButton, closeButton);
        return buttonsLayout;
    }

    private Grid<WorkClassification> initGrid() {
        grid = new Grid<>();
        grid.setDataProvider(initDataProvider());
        grid.setSizeFull();
        var gridSelectionModel =
            (AbstractGridMultiSelectionModel<WorkClassification>) grid.setSelectionMode(SelectionMode.MULTI);
        gridSelectionModel.setSelectAllCheckboxVisibility(SelectAllCheckboxVisibility.HIDDEN);
        gridSelectionModel.setSelectionColumnFrozen(true);
        addColumns();
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.setGridProperties(grid, "works-classification-grid");
        return grid;
    }

    private DataProvider<WorkClassification, ?> initDataProvider() {
        int classificationCount = controller.getClassificationCount(selectedBatchesIds, null);
        if (controller.getWorkClassificationThreshold() < classificationCount) {
            dataProvider = DataProvider.fromCallbacks(
                query -> controller.getClassifications(selectedBatchesIds, searchWidget.getSearchValue(),
                    query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
                query -> controller.getClassificationCount(selectedBatchesIds, searchWidget.getSearchValue()));
            return dataProvider;
        } else {
            listDataProvider = DataProvider.ofCollection(
                controller.getClassifications(selectedBatchesIds, null, 0, classificationCount, null));
            return listDataProvider;
        }
    }

    private void addColumns() {
        //TODO {aliakh} fix column width
        addColumn(WorkClassification::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", "100px");
        addStringColumn(WorkClassification::getSystemTitle, "table.column.system_title", "systemTitle", "285px");
        addColumn(WorkClassification::getClassification, "table.column.classification", "classification", "110px");
        addColumn(WorkClassification::getStandardNumber, "table.column.standard_number", "standardNumber", "140px");
        addColumn(WorkClassification::getStandardNumberType, "table.column.standard_number_type", "standardNumberType",
            "155px");
        addColumn(WorkClassification::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", "100px");
        addStringColumn(WorkClassification::getRhName, "table.column.rh_account_name", "rhName", "285px");
        addClassificationDateColumn(WorkClassification::getUpdateDate);
        addStringColumn(WorkClassification::getUpdateUser, "table.column.classified_by", "updateUser", "245px");
    }

    private void addStringColumn(ValueProvider<WorkClassification, String> provider, String caption, String property,
                                 String width) {
        addColumn(provider, caption, property, width)
            .setComparator((SerializableComparator<WorkClassification>) (classification1, classification2) ->
                Comparator.comparing(provider, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                    .compare(classification1, classification2));
    }

    //TODO {aliakh} fix columns
    private Column<WorkClassification> addColumn(ValueProvider<WorkClassification, ?> provider, String captionProperty,
                                                 String sort, String width) {
        return grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setWidth(width);
    }

    private void addClassificationDateColumn(ValueProvider<WorkClassification, Date> provider) {
        grid.addColumn(date -> Objects.nonNull(provider.apply(date))
                ? DateFormatUtils.format(provider.apply(date), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)
                : null)
            .setHeader(ForeignUi.getMessage("table.column.classification_date"))
            .setSortProperty("updateDate")
            .setWidth("135px");
    }

    private void performSearch() {
        if (Objects.nonNull(listDataProvider)) {
            listDataProvider.clearFilters();
            var searchValue = searchWidget.getSearchValue();
            if (StringUtils.isNotBlank(searchValue)) {
                listDataProvider.addFilter(
                    value -> StringUtils.containsIgnoreCase(Objects.toString(value.getWrWrkInst(), null), searchValue)
                        || StringUtils.containsIgnoreCase(value.getSystemTitle(), searchValue)
                        || StringUtils.containsIgnoreCase(value.getStandardNumber(), searchValue)
                        || StringUtils.containsIgnoreCase(value.getRhName(), searchValue)
                        || StringUtils.containsIgnoreCase(Objects.toString(value.getRhAccountNumber(), null),
                        searchValue));
            }
        } else {
            dataProvider.refreshAll();
        }
    }

    private void addClickListener(Button button, String message, Consumer<Set<WorkClassification>> consumer) {
        button.addClickListener(event -> {
            var selectedItems = grid.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                int usageCount = controller.getCountToUpdate(selectedItems);
                Windows.showConfirmDialog(0 < usageCount
                        ? ForeignUi.getMessage(message, usageCount)
                        : ForeignUi.getMessage("message.confirm.action"),
                    () -> consumer.accept(selectedItems));
            } else {
                Windows.showNotificationWindow(ForeignUi.getMessage("message.classification.empty"));
            }
        });
    }

    private void updateAndRefreshDataProvider() {
        if (Objects.nonNull(listDataProvider)) {
            listDataProvider = DataProvider.ofCollection(controller.getClassifications(selectedBatchesIds, null, 0,
                controller.getWorkClassificationThreshold(), null));
            grid.setDataProvider(listDataProvider);
        } else {
            dataProvider.refreshAll();
            grid.setDataProvider(dataProvider);
        }
    }

    private void refreshDataProvider() {
        if (Objects.nonNull(listDataProvider)) {
            listDataProvider.refreshAll();
            grid.setDataProvider(listDataProvider);
        } else {
            dataProvider.refreshAll();
            grid.setDataProvider(dataProvider);
        }
    }
}
