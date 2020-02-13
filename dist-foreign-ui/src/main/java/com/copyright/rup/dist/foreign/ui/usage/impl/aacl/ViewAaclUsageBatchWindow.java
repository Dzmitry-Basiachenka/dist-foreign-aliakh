package com.copyright.rup.dist.foreign.ui.usage.impl.aacl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aacl.IAaclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.impl.ViewUsageBatchMediator;
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
import com.vaadin.ui.renderers.LocalDateRenderer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting AACL {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2020 copyright.com
 * <p/>
 * Date: 02/12/20
 *
 * @author Uladzislau Shalamitski
 */
public class ViewAaclUsageBatchWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final IAaclUsageController controller;
    private Grid<UsageBatch> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller {@link IAaclUsageController}
     */
    public ViewAaclUsageBatchWindow(IAaclUsageController controller) {
        this.controller = controller;
        setWidth(800, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_batch.search.aacl"));
        initGrid();
        HorizontalLayout buttonsLayout = initButtons();
        initMediator();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_usage_batch"));
        VaadinUtils.addComponentStyle(this, "window.view_usage_batch");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<UsageBatch> dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String search = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(search)) {
            dataProvider.setFilter(batch -> StringUtils.containsIgnoreCase(batch.getName(), search)
                || StringUtils.containsIgnoreCase(batch.getPaymentDate()
                .format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), search));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initMediator() {
        ViewUsageBatchMediator mediator = new ViewUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event -> {
            UsageBatch selectedBatch = grid.getSelectedItems().stream().findFirst().orElse(null);
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", selectedBatch.getName(), "usage batch"),
                () -> {
                    controller.deleteUsageBatch(selectedBatch);
                    grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
                });
        });
        deleteButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-usage-batch-buttons");
        return layout;
    }

    private void initGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
        grid.setSizeFull();
        grid.addSelectionListener(
            event -> deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-usage-batch-grid");
    }

    private void addGridColumns() {
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setExpandRatio(1);
        grid.addColumn(UsageBatch::getPaymentDate)
            .setCaption(ForeignUi.getMessage("table.column.period_end_date"))
            .setRenderer(new LocalDateRenderer(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))
            .setWidth(170);
        grid.addColumn(UsageBatch::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.create_user"))
            .setComparator((batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(batch -> getStringFromDate(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"))
            .setComparator((batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()))
            .setWidth(170);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }
}
