package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Modal window that provides functionality for deleting {@link UsageBatch}es.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 2/14/17
 *
 * @author Aliaksandr Radkevich
 */
class DeleteUsageBatchWindow extends Window {

    private final SearchWidget searchWidget;
    private final IUsagesController controller;
    private Grid<UsageBatch> grid;

    /**
     * Constructor.
     *
     * @param controller {@link IUsagesController}
     */
    DeleteUsageBatchWindow(IUsagesController controller) {
        this.controller = controller;
        setCaption(ForeignUi.getMessage("window.delete_usage_batch"));
        setWidth(700, Unit.PIXELS);
        setHeight(450, Unit.PIXELS);
        searchWidget = new SearchWidget(new SearchController());
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.batch.search_usage_batch"));
        initUsageBatchesGrid();
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout layout = new VerticalLayout(searchWidget, grid, closeButton);
        layout.setSizeFull();
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "delete-usage-batch");
    }

    private void initUsageBatchesGrid() {
        grid = new Grid<>();
        grid.setItems(controller.getUsageBatches());
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.addColumn(UsageBatch::getName)
            .setCaption(ForeignUi.getMessage("table.column.batch_name"))
            .setSortProperty("name")
            .setExpandRatio(1);
        grid.addColumn(UsageBatch::getPaymentDate)
            .setCaption(ForeignUi.getMessage("table.column.payment_date"))
            .setSortProperty("paymentDate")
            .setWidth(115);
        grid.addColumn(batch -> UsageBatchUtils.getFiscalYear(batch.getFiscalYear()))
            .setCaption(ForeignUi.getMessage("table.column.fiscal_year"))
            .setSortProperty("fiscalYear")
            .setWidth(110);
        grid.addComponentColumn(batch -> {
            Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
            deleteButton.setId(batch.getId());
            deleteButton.addClickListener(event -> deleteUsageBatch(batch));
            return deleteButton;
        }).setId("delete")
            .setWidth(90)
            .setSortable(false);
        VaadinUtils.addComponentStyle(grid, "usage-batches-grid");
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
        if (CollectionUtils.isEmpty(scenariosNames)) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage batch"),
                () -> performDelete(usageBatch));
        } else {
            StringBuilder scenariosHtml = new StringBuilder("<ul>");
            for (String scenarioName : scenariosNames) {
                scenariosHtml.append("<li>").append(scenarioName).append("</li>");
            }
            scenariosHtml.append("</ul>");
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.error.delete_usage_batch", scenariosHtml));
        }
    }

    private void performDelete(UsageBatch usageBatch) {
        controller.deleteUsageBatch(usageBatch);
        grid.setItems(controller.getUsageBatches());
    }

    /**
     * {@link ISearchController} implementation.
     */
    class SearchController implements ISearchController {

        @Override
        public void performSearch() {
            ListDataProvider<UsageBatch> dataProvider = (ListDataProvider<UsageBatch>) grid.getDataProvider();
            dataProvider.clearFilters();
            String searchValue = searchWidget.getSearchValue();
            if (StringUtils.isNotBlank(searchValue)) {
                dataProvider.setFilter(batch -> caseInsensitiveContains(batch.getName(), searchValue) ||
                    caseInsensitiveContains(batch.getPaymentDate().format(DateTimeFormatter.ISO_DATE), searchValue));
            }
        }

        private Boolean caseInsensitiveContains(String where, String what) {
            return StringUtils.contains(StringUtils.lowerCase(where), StringUtils.lowerCase(what));
        }
    }
}
