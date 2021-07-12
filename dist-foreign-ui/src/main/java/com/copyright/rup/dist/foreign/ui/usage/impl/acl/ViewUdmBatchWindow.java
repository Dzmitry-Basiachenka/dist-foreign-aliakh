package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.main.security.ForeignSecurityUtils;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmUsageController;
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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Modal window that provides functionality for viewing and deleting {@link UdmBatch}es.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 07/01/21
 *
 * @author Anton Azarenka
 */
public class ViewUdmBatchWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final IUdmUsageController controller;
    private Grid<UdmBatch> grid;
    private Button deleteBatchButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IUdmUsageController}
     */
    public ViewUdmBatchWindow(IUdmUsageController controller) {
        this.controller = controller;
        setWidth(1000, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_batch.search.udm"));
        initUdmBatchesGrid();
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_udm_usage_batch"));
        VaadinUtils.addComponentStyle(this, "view-udm-batch-window");
    }

    @Override
    public void performSearch() {
        ListDataProvider<UdmBatch> dataProvider = (ListDataProvider<UdmBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(batch -> StringUtils.containsIgnoreCase(batch.getName(), searchValue));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initUdmBatchesGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getUdmBatches());
        grid.addSelectionListener(
            event -> deleteBatchButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-batch-grid");
    }

    private void addGridColumns() {
        grid.addColumn(UdmBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setExpandRatio(1);
        grid.addColumn(UdmBatch::getPeriod)
            .setCaption(ForeignUi.getMessage("table.column.period"))
            .setComparator((batch1, batch2) -> batch1.getPeriod().compareTo(batch2.getPeriod()))
            .setWidth(180);
        grid.addColumn(UdmBatch::getUsageOrigin)
            .setCaption(ForeignUi.getMessage("table.column.usage_origin"))
            .setComparator((batch1, batch2) -> batch1.getUsageOrigin().compareTo(batch2.getUsageOrigin()))
            .setHidden(ForeignSecurityUtils.hasResearcherPermission())
            .setWidth(180);
        grid.addColumn(UdmBatch::getChannel)
            .setCaption(ForeignUi.getMessage("table.column.channel"))
            .setComparator((batch1, batch2) -> batch1.getChannel().compareTo(batch2.getChannel()))
            .setWidth(120);
        grid.addColumn(UdmBatch::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.create_user"))
            .setComparator((batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(udmBatch -> getStringFromDate(udmBatch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.create_date"))
            .setComparator((batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()))
            .setWidth(170);
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteBatchButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteBatchButton.addClickListener(event ->
            deleteUdmBatch(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteBatchButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteBatchButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-batch-buttons");
        return layout;
    }

    private void deleteUdmBatch(UdmBatch udmBatch) {
        if (controller.isUdmBatchProcessingCompleted(udmBatch.getId())) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", udmBatch.getName(), "UDM batch"),
                () -> {
                    controller.deleteUdmBatch(udmBatch);
                    grid.setItems(controller.getUdmBatches());
                });
        } else {
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.delete_in_progress_batch", udmBatch.getName()));
        }
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? new SimpleDateFormat(RupDateUtils.US_DATETIME_FORMAT_PATTERN_LONG, Locale.getDefault()).format(date)
            : StringUtils.EMPTY;
    }
}
