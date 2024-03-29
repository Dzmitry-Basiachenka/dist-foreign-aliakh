package com.copyright.rup.dist.foreign.vui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.vui.common.utils.GridColumnEnum;
import com.copyright.rup.dist.foreign.vui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Modal window that provides functionality for viewing and deleting {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/24/2019
 *
 * @author Uladzislau Shalamitski
 */
public abstract class AbstractViewUsageBatchWindow extends CommonDialog
    implements SearchWidget.ISearchController, IDateFormatter {

    private static final long serialVersionUID = -963897423065702705L;

    private final ICommonUsageController controller;
    private SearchWidget searchWidget;
    private Grid<UsageBatch> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller {@link ICommonUsageController}
     */
    public AbstractViewUsageBatchWindow(ICommonUsageController controller) {
        this.controller = controller;
        initRootLayout();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        var dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        var searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(getSearchFilter(searchValue));
        }
    }

    /**
     * Adds column to data grid.
     *
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @return the column for method chaining
     */
    protected Column<UsageBatch> addColumn(ValueProvider<UsageBatch, ?> valueProvider, GridColumnEnum gridColumn) {
        return grid.addColumn(valueProvider)
            .setHeader(ForeignUi.getMessage(gridColumn.getCaption()))
            .setFlexGrow(0)
            .setWidth(gridColumn.getWidth())
            .setSortable(true)
            .setResizable(true);
    }

    /**
     * Adds column to data grid.
     *
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @param comparator    column comparator
     * @return the column for method chaining
     */
    protected Column<UsageBatch> addColumn(ValueProvider<UsageBatch, ?> valueProvider, GridColumnEnum gridColumn,
                                           SerializableComparator<UsageBatch> comparator) {
        return addColumn(valueProvider, gridColumn).setComparator(comparator);
    }

    /**
     * Adds amount column to data grid.
     *
     * @param valueProvider column value provider
     * @param gridColumn    grid column
     * @param comparator    column comparator
     * @return the column for method chaining
     */
    protected Column<UsageBatch> addAmountColumn(ValueProvider<UsageBatch, ?> valueProvider, GridColumnEnum gridColumn,
                                                 SerializableComparator<UsageBatch> comparator) {
        return addColumn(valueProvider, gridColumn, comparator).setClassNameGenerator(item -> "label-amount");
    }

    /**
     * @return message for search widget.
     */
    protected abstract String getSearchMessage();

    /**
     * @return message for window's caption.
     */
    protected abstract String getCaptionMessage();

    /**
     * Returns message to confirm deletion.
     *
     * @param butchName batch name
     * @return message to confirm deletion
     */
    protected abstract String getDeleteMessage(String butchName);

    /**
     * Returns error delete message.
     *
     * @param fieldName name of field used in message
     * @param itemsList items associated with batch under deletion
     * @return error delete message
     */
    protected abstract String getDeleteErrorMessage(String fieldName, String itemsList);

    /**
     * Adds columns to data grid.
     *
     * @param dataGrid instance of {@link Grid}
     */
    protected abstract void addGridColumns(Grid<UsageBatch> dataGrid);

    /**
     * Gets {@link SerializablePredicate} to filter batches based on search value.
     *
     * @param searchValue search value
     * @return instance of {@link SerializablePredicate}
     */
    protected abstract SerializablePredicate<UsageBatch> getSearchFilter(String searchValue);

    private void initRootLayout() {
        setWidth("1150px");
        setHeight("550px");
        searchWidget = new SearchWidget(this, getSearchMessage(), "70%");
        initUsageBatchesGrid();
        var buttonsLayout = initButtonsLayout();
        initMediator();
        setHeaderTitle(getCaptionMessage());
        add(VaadinUtils.initSizeFullVerticalLayout(searchWidget, grid));
        getFooter().add(buttonsLayout);
        setModalWindowProperties("view-batch-window", true);
    }

    private void initMediator() {
        var mediator = new ViewUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtonsLayout() {
        var closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event ->
            grid.getSelectedItems().stream().findFirst().ifPresent(this::deleteUsageBatch));
        deleteButton.setEnabled(false);
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        var buttonsLayout = new HorizontalLayout(deleteButton, closeButton);
        buttonsLayout.setSpacing(true);
        VaadinUtils.addComponentStyle(buttonsLayout, "view-batch-buttons");
        return buttonsLayout;
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
        grid.addSelectionListener(event ->
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        addGridColumns(grid);
        VaadinUtils.setGridProperties(grid, "view-batch-grid");
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> additionalFundNames = controller.getAdditionalFundNamesByUsageBatchId(usageBatch.getId());
        if (CollectionUtils.isNotEmpty(additionalFundNames)) {
            Windows.showNotificationWindow(buildNotificationMessage("additional funds", additionalFundNames));
        } else {
            List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
            if (CollectionUtils.isEmpty(scenariosNames)) {
                if (controller.isBatchProcessingCompleted(usageBatch.getId())) {
                    Windows.showConfirmDialog(getDeleteMessage(usageBatch.getName()), () -> performDelete(usageBatch));
                } else {
                    Windows.showNotificationWindow(
                        ForeignUi.getMessage("message.error.delete_in_progress_batch", usageBatch.getName()));
                }
            } else {
                Windows.showNotificationWindow(buildNotificationMessage("scenarios", scenariosNames));
            }
        }
    }

    private void performDelete(UsageBatch usageBatch) {
        controller.deleteUsageBatch(usageBatch);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
    }

    private String buildNotificationMessage(String associatedField, List<String> associatedNames) {
        var sb = new StringBuilder("<ul>");
        for (String name : associatedNames) {
            sb.append("<li>").append(name).append("</li>");
        }
        sb.append("</ul>");
        return getDeleteErrorMessage(associatedField, sb.toString());
    }
}
