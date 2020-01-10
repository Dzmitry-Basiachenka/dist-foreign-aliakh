package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializableComparator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2019 copyright.com
 * <p/>
 * Date: 05/24/19
 *
 * @author Uladzislau Shalamitski
 */
public abstract class AbstractViewUsageBatchWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final ICommonUsageController controller;
    private Grid<UsageBatch> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller {@link ICommonUsageController}
     */
    public AbstractViewUsageBatchWindow(ICommonUsageController controller) {
        this.controller = controller;
        setWidth(1000, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(getSearchMessage());
        initUsageBatchesGrid();
        HorizontalLayout buttonsLayout = initButtons();
        initMediator();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(getCaptionMessage());
        VaadinUtils.addComponentStyle(this, "view-batch-window");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<UsageBatch> dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String search = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(search)) {
            dataProvider.setFilter(batch -> StringUtils.containsIgnoreCase(batch.getName(), search)
                || StringUtils.containsIgnoreCase(batch.getRro().getName(), search)
                || StringUtils.containsIgnoreCase(batch.getRro().getAccountNumber().toString(), search)
                || StringUtils.containsIgnoreCase(batch.getPaymentDate().format(DateTimeFormatter.ISO_DATE), search));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    /**
     * Converts date to string value.
     *
     * @param date value to convert
     * @return converted date as string
     */
    protected String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }

    /**
     * Adds column to data grid.
     *
     * @param valueProvider column value provider
     * @param caption       column caption
     * @param width         column width
     */
    protected void addColumn(ValueProvider<UsageBatch, ?> valueProvider, String caption, int width) {
        grid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(caption))
            .setWidth(width);
    }

    /**
     * Adds column to data grid.
     *
     * @param valueProvider column value provider
     * @param caption       column caption
     * @param width         column width
     * @param comparator    column comparator
     */
    protected void addColumn(ValueProvider<UsageBatch, ?> valueProvider, String caption, int width,
                             SerializableComparator<UsageBatch> comparator) {
        grid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(caption))
            .setComparator(comparator)
            .setWidth(width);
    }

    /**
     * Adds amount column to data grid.
     *
     * @param valueProvider column value provider
     * @param caption       column caption
     * @param width         column width
     * @param comparator    column comparator
     */
    protected void addAmountColumn(ValueProvider<UsageBatch, ?> valueProvider, String caption, int width,
                                   SerializableComparator<UsageBatch> comparator) {
        grid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(caption))
            .setComparator(comparator)
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(width);
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

    private void initMediator() {
        ViewUsageBatchMediator mediator = new ViewUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event ->
            deleteUsageBatch(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-batch-buttons");
        return layout;
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
        grid.addSelectionListener(event ->
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns(grid);
        VaadinUtils.addComponentStyle(grid, "view-batch-grid");
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> preServiceFeeFundNames = controller.getPreServiceFeeFundNamesByUsageBatchId(usageBatch.getId());
        if (CollectionUtils.isNotEmpty(preServiceFeeFundNames)) {
            Windows.showNotificationWindow(buildNotificationMessage("additional funds", preServiceFeeFundNames));
        } else {
            List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
            if (CollectionUtils.isEmpty(scenariosNames)) {
                Windows.showConfirmDialog(getDeleteMessage(usageBatch.getName()), () -> performDelete(usageBatch));
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
        StringBuilder htmlNamesList = new StringBuilder("<ul>");
        for (String name : associatedNames) {
            htmlNamesList.append("<li>").append(name).append("</li>");
        }
        htmlNamesList.append("</ul>");
        return getDeleteErrorMessage(associatedField, htmlNamesList.toString());
    }
}
