package com.copyright.rup.dist.foreign.ui.usage.impl.sal;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.sal.ISalUsageController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.SerializablePredicate;
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
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Modal window that provides functionality for viewing and deleting SAL {@link UsageBatch}es.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/28/20
 *
 * @author Anton Azarenka
 */
public class ViewSalUsageBatchWindow extends Window implements SearchWidget.ISearchController {

    private final SearchWidget searchWidget;
    private final ISalUsageController controller;
    private Grid<UsageBatch> grid;
    private Button deleteBatchButton;
    private Button deleteUsageDetailsButton;

    /**
     * Controller.
     *
     * @param controller {@link ISalUsageController} instance
     */
    public ViewSalUsageBatchWindow(ISalUsageController controller) {
        this.controller = controller;
        setWidth(1000, Unit.PIXELS);
        setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_batch.search.sal"));
        initUsageBatchesGrid();
        HorizontalLayout buttonsLayout = initButtons();
        initMediator();
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        setCaption(ForeignUi.getMessage("window.view_usage_batch"));
        VaadinUtils.addComponentStyle(this, "view-batch-window");
    }

    @Override
    public void performSearch() {
        ListDataProvider<UsageBatch> dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(getSearchFilter(searchValue));
        }
        // Gets round an issue when Vaadin do not recalculates columns widths once vertical scroll is disappeared
        grid.recalculateColumnWidths();
    }

    private void initMediator() {
        ViewSalUsageBatchMediator mediator = new ViewSalUsageBatchMediator();
        mediator.setDeleteBatchButton(deleteBatchButton);
        mediator.setDeleteUsageDetailsButton(deleteUsageDetailsButton);
        mediator.applyPermissions();
    }

    private HorizontalLayout initButtons() {
        Button closeButton = Buttons.createCloseButton(this);
        deleteBatchButton = Buttons.createButton(ForeignUi.getMessage("button.delete_usage_batch"));
        deleteBatchButton.addClickListener(event ->
            deleteUsageBatch(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteBatchButton.setEnabled(false);
        deleteUsageDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.delete_usage_details"));
        deleteUsageDetailsButton.addClickListener(event ->
            deleteUsageDetails(grid.getSelectedItems().stream().findFirst().orElse(null)));
        deleteUsageDetailsButton.setEnabled(false);
        HorizontalLayout layout = new HorizontalLayout(deleteBatchButton, deleteUsageDetailsButton, closeButton);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "view-batch-buttons");
        return layout;
    }

    private void deleteUsageDetails(UsageBatch usageBatch) {
        if (!controller.usageDataExists(usageBatch.getId())) {
            Windows.showNotificationWindow(ForeignUi.getMessage("message.error.no_usage_details"));
        } else {
            List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
            if (CollectionUtils.isEmpty(scenariosNames)) {
                Windows.showConfirmDialog(
                    ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage details"),
                    () -> performDelete(controller::deleteUsageDetails, usageBatch));
            } else {
                Windows.showNotificationWindow(
                    buildNotificationMessage("message.error.delete_usage_details", "Usage details", "scenarios",
                        scenariosNames));
            }
        }
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
        if (CollectionUtils.isEmpty(scenariosNames)) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage batch"),
                () -> performDelete(controller::deleteUsageBatch, usageBatch));
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.error.delete_action", "Usage batch", "scenarios",
                    scenariosNames));
        }
    }

    private void performDelete(Consumer<UsageBatch> consumer, UsageBatch usageBatch) {
        consumer.accept(usageBatch);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
    }

    private SerializablePredicate<UsageBatch> getSearchFilter(String searchValue) {
        return batch -> StringUtils.containsIgnoreCase(batch.getName(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getPaymentDate().format(
            DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), searchValue)
            || StringUtils.containsIgnoreCase(batch.getSalFields().getLicenseeAccountNumber().toString(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getSalFields().getLicenseeName(), searchValue);
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
        grid.addSelectionListener(event ->
            deleteBatchButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.addSelectionListener(
            event -> deleteUsageDetailsButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-batch-grid");
    }

    private void addGridColumns() {
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setExpandRatio(1);
        grid.addColumn(usageBatch -> usageBatch.getSalFields().getLicenseeAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.licensee_account_number"))
            .setComparator((batch1, batch2) -> batch1.getSalFields()
                .getLicenseeAccountNumber().compareTo(batch2.getSalFields().getLicenseeAccountNumber()))
            .setWidth(180);
        grid.addColumn(usageBatch -> usageBatch.getSalFields().getLicenseeName())
            .setCaption(ForeignUi.getMessage("table.column.licensee_name"))
            .setComparator((batch1, batch2) -> batch1.getSalFields()
                .getLicenseeName().compareTo(batch2.getSalFields().getLicenseeName()))
            .setWidth(180);
        grid.addColumn(UsageBatch::getPaymentDate)
            .setCaption(ForeignUi.getMessage("table.column.period_end_date"))
            .setRenderer(new LocalDateRenderer(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT))
            .setWidth(120);
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
