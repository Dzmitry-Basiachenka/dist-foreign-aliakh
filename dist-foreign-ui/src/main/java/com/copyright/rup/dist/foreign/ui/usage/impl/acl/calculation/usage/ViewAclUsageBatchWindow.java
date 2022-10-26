package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modal window that provides functionality for viewing and deleting {@link AclUsageBatch}s.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/26/2022
 *
 * @author Mikita Maistrenka
 */
public class ViewAclUsageBatchWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final IAclUsageController controller;
    private Grid<AclUsageBatch> grid;
    private Button deleteButton;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclUsageController}
     */
    public ViewAclUsageBatchWindow(IAclUsageController controller) {
        this.controller = controller;
        setWidth(1100, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_batch.search.udm"));
        initUsageBatchGrid();
        HorizontalLayout buttonsLayout = initButtons();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_acl_usage_batch"));
        VaadinUtils.addComponentStyle(this, "view-acl-usage-batch-window");
    }

    @Override
    public void performSearch() {
        ListDataProvider<AclUsageBatch> dataProvider = (ListDataProvider<AclUsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(usageBatch -> StringUtils.containsIgnoreCase(usageBatch.getName(), searchValue));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initUsageBatchGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getAllAclUsageBatches());
        grid.addSelectionListener(
            event -> deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-acl-usage-batch-grid");
    }

    private void addGridColumns() {
        grid.addColumn(AclUsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((usageBatch1, usageBatch2) ->
                usageBatch1.getName().compareToIgnoreCase(usageBatch2.getName()))
            .setExpandRatio(1);
        grid.addColumn(AclUsageBatch::getDistributionPeriod)
            .setCaption(ForeignUi.getMessage("table.column.distribution_period"))
            .setComparator((usageBatch1, usageBatch2) ->
                usageBatch1.getDistributionPeriod().compareTo(usageBatch2.getDistributionPeriod()))
            .setWidth(150);
        grid.addColumn(usageBatch -> usageBatch.getPeriods().stream()
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.joining(", ")))
            .setCaption(ForeignUi.getMessage("table.column.periods"))
            .setComparator((usageBatch1, usageBatch2) ->
                usageBatch1.getPeriods().toString().compareTo(usageBatch2.getPeriods().toString()))
            .setWidth(580);
        grid.addColumn(usageBatch -> BooleanUtils.toYNString(usageBatch.getEditable()))
            .setCaption(ForeignUi.getMessage("table.column.editable"))
            .setComparator((usageBatch1, usageBatch2) -> usageBatch1.getEditable().compareTo(usageBatch2.getEditable()))
            .setWidth(80);
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
        deleteButton.addClickListener(event ->
            deleteUsageBatch(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.setButtonsAutoDisabled(deleteButton);
        VaadinUtils.addComponentStyle(layout, "view-acl-usage-batch-buttons");
        return layout;
    }

    private void deleteUsageBatch(AclUsageBatch usageBatch) {
        List<String> scenarioNames = controller.getScenarioNamesAssociatedWithUsageBatch(usageBatch.getId());
        if (CollectionUtils.isEmpty(scenarioNames)) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage batch"),
                () -> {
                    controller.deleteAclUsageBatch(usageBatch);
                    grid.setItems(controller.getAllAclUsageBatches());
                });
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.error.delete_action", "Usage batch", "scenario(s)", scenarioNames));
        }
    }

    private String buildNotificationMessage(String key, String param, String associatedField,
                                            List<String> associatedNames) {
        StringBuilder htmlNamesList = new StringBuilder("<ul>");
        for (String name : associatedNames) {
            htmlNamesList.append("<li>").append(name).append("</li>");
        }
        htmlNamesList.append("</ul>");
        return ForeignUi.getMessage(key, param, associatedField, htmlNamesList.toString());
    }
}
