package com.copyright.rup.dist.foreign.ui.usage.impl.aclci;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.common.utils.IDateFormatter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.aclci.IAclciUsageController;
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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Modal window that provides functionality for viewing and deleting ACLCI {@link UsageBatch}es.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 04/12/23
 *
 * @author Mikita Maistrenka
 */
public class ViewAclciUsageBatchWindow extends Window implements SearchWidget.ISearchController, IDateFormatter {

    private static final long serialVersionUID = 173678621350692058L;

    private final SearchWidget searchWidget;
    private final IAclciUsageController controller;
    private Grid<UsageBatch> grid;
    private Button deleteButton;

    /**
     * Controller.
     *
     * @param controller {@link IAclciUsageController} instance
     */
    public ViewAclciUsageBatchWindow(IAclciUsageController controller) {
        this.controller = controller;
        super.setWidth(1000, Unit.PIXELS);
        super.setHeight(550, Unit.PIXELS);
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.view_batch.search.sal_aclci"));
        initUsageBatchesGrid();
        var buttonsLayout = initButtons();
        initMediator();
        var layout = new VerticalLayout(searchWidget, grid, buttonsLayout);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_RIGHT);
        super.setContent(layout);
        super.setCaption(ForeignUi.getMessage("window.view_usage_batch"));
        VaadinUtils.addComponentStyle(this, "view-batch-window");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void performSearch() {
        ListDataProvider<UsageBatch> dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(getSearchFilter(searchValue));
        }
        grid.recalculateColumnWidths();
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
        grid.addSelectionListener(event ->
            deleteButton.setEnabled(CollectionUtils.isNotEmpty(event.getAllSelectedItems())));
        grid.setSizeFull();
        addGridColumns();
        VaadinUtils.addComponentStyle(grid, "view-batch-grid");
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

    private void initMediator() {
        ViewAclciUsageBatchMediator mediator = new ViewAclciUsageBatchMediator();
        mediator.setDeleteButton(deleteButton);
        mediator.applyPermissions();
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
        if (CollectionUtils.isEmpty(scenariosNames)) {
            if (controller.isBatchProcessingCompleted(usageBatch.getId())) {
                Windows.showConfirmDialog(
                    ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage batch"),
                    () -> {
                        controller.deleteUsageBatch(usageBatch);
                        grid.setItems(controller.getUsageBatches(controller.getSelectedProductFamily()));
                    });
            } else {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.delete_in_progress_batch", usageBatch.getName()));
            }
        } else {
            Windows.showNotificationWindow(
                buildNotificationMessage("message.error.delete_action", "Usage batch", "scenarios",
                    scenariosNames));
        }
    }

    private SerializablePredicate<UsageBatch> getSearchFilter(String searchValue) {
        return batch -> StringUtils.containsIgnoreCase(batch.getName(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getPaymentDate().format(
            DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT)), searchValue)
            || StringUtils.containsIgnoreCase(batch.getAclciFields().getLicenseeAccountNumber().toString(), searchValue)
            || StringUtils.containsIgnoreCase(batch.getAclciFields().getLicenseeName(), searchValue);
    }

    private void addGridColumns() {
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setComparator((batch1, batch2) -> batch1.getName().compareToIgnoreCase(batch2.getName()))
            .setExpandRatio(1);
        grid.addColumn(usageBatch -> usageBatch.getAclciFields().getLicenseeAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.licensee_account_number"))
            .setComparator((batch1, batch2) -> batch1.getAclciFields()
                .getLicenseeAccountNumber().compareTo(batch2.getAclciFields().getLicenseeAccountNumber()))
            .setWidth(180);
        grid.addColumn(usageBatch -> usageBatch.getAclciFields().getLicenseeName())
            .setCaption(ForeignUi.getMessage("table.column.licensee_name"))
            .setComparator((batch1, batch2) -> batch1.getAclciFields()
                .getLicenseeName().compareTo(batch2.getAclciFields().getLicenseeName()))
            .setWidth(180);
        grid.addColumn(batch -> toShortFormat(batch.getPaymentDate()))
            .setCaption(ForeignUi.getMessage("table.column.period_end_date"))
            .setComparator((batch1, batch2) -> batch1.getPaymentDate().compareTo(batch2.getPaymentDate()))
            .setWidth(120);
        grid.addColumn(UsageBatch::getCreateUser)
            .setCaption(ForeignUi.getMessage("table.column.created_by"))
            .setComparator((batch1, batch2) -> batch1.getCreateUser().compareToIgnoreCase(batch2.getCreateUser()))
            .setWidth(170);
        grid.addColumn(batch -> toLongFormat(batch.getCreateDate()))
            .setCaption(ForeignUi.getMessage("table.column.created_date"))
            .setComparator((batch1, batch2) -> batch1.getCreateDate().compareTo(batch2.getCreateDate()))
            .setWidth(170);
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
