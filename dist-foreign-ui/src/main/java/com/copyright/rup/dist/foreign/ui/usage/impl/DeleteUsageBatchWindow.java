package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.ConfirmDialogWindow;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.SearchWidget.ISearchController;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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

    private static final String PAYMENT_DATE_PROPERTY = "paymentDate";

    private IUsagesController controller;
    private BeanContainer<String, UsageBatch> container;
    private final SearchWidget searchWidget;

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
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.search_usage_batch"));
        Table table = initUsageBatchesTable();
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout layout = new VerticalLayout(searchWidget, table, closeButton);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.setExpandRatio(table, 1);
        layout.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
        setContent(layout);
        VaadinUtils.addComponentStyle(this, "delete-usage-batch");
    }

    private Table initUsageBatchesTable() {
        container = new BeanContainer<>(UsageBatch.class);
        container.setBeanIdResolver(BaseEntity::getId);
        container.addAll(controller.getUsageBatches());
        Table table = new Table(null, container);
        VaadinUtils.addComponentStyle(table, "usage-batches-table");
        table.setSizeFull();
        table.addGeneratedColumn(PAYMENT_DATE_PROPERTY, new LocalDateColumnGenerator(
            RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
        table.addGeneratedColumn("fiscalYear", new FiscalYearColumnGenerator());
        table.addGeneratedColumn("delete", (ColumnGenerator) (source, itemId, columnId) -> {
            Button deleteButton = Buttons.createButton(ForeignUi.getMessage("button.delete"));
            UsageBatch usageBatch = container.getItem(itemId).getBean();
            deleteButton.setId(usageBatch.getId());
            deleteButton.addClickListener(event -> deleteUsageBatch(usageBatch));
            return deleteButton;
        });
        table.setVisibleColumns("name", PAYMENT_DATE_PROPERTY, "fiscalYear", "delete");
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.batch_name"),
            ForeignUi.getMessage("table.column.payment_date"),
            ForeignUi.getMessage("table.column.fiscal_year"),
            StringUtils.EMPTY);
        table.setColumnWidth("delete", 65);
        table.setColumnWidth(PAYMENT_DATE_PROPERTY, 100);
        table.setColumnWidth("fiscalYear", 80);
        table.setColumnExpandRatio("name", 1);
        return table;
    }

    private void deleteUsageBatch(UsageBatch usageBatch) {
        List<String> scenariosNames = controller.getScenariosNamesAssociatedWithUsageBatch(usageBatch.getId());
        if (CollectionUtils.isEmpty(scenariosNames)) {
            Windows.showConfirmDialog(
                ForeignUi.getMessage("message.confirm.delete_action", usageBatch.getName(), "usage batch"),
                new ConfirmDeleteListener(controller, usageBatch, container));
        } else {
            StringBuilder scenariosHtml = new StringBuilder("<ul>");
            for (String scenarioName : scenariosNames) {
                scenariosHtml.append("<li>").append(scenarioName).append("</li>");
            }
            scenariosHtml.append("</ul>");
            Windows.showNotificationWindow(
                ForeignUi.getMessage("message.usage_batch_associated_with_scenarios", scenariosHtml));
        }
    }

    /**
     * {@link Filter} implementation for payment date property. Provides ability to filter by payment date in
     * format MM/dd/yyyy ({@link LocalDate#toString()} returns date in format yyyy-MM-dd).
     */
    static class PaymentDateFilter implements Filter {

        private String searchValue;

        /**
         * Constructor.
         *
         * @param searchValue search value
         */
        PaymentDateFilter(String searchValue) {
            this.searchValue = searchValue;
        }

        @Override
        public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
            Property<?> property = item.getItemProperty(PAYMENT_DATE_PROPERTY);
            LocalDate date = (LocalDate) property.getValue();
            String paymentDate = date.format(DateTimeFormatter.ofPattern(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT));
            return paymentDate.contains(searchValue);
        }

        @Override
        public boolean appliesToProperty(Object propertyId) {
            return PAYMENT_DATE_PROPERTY.equals(propertyId);
        }
    }

    /**
     * Listener for confirming usage datch deleting.
     */
    static class ConfirmDeleteListener extends ConfirmDialogWindow.Listener {

        private IUsagesController controller;
        private UsageBatch usageBatch;
        private BeanContainer<String, UsageBatch> container;

        /**
         * Constructor.
         *
         * @param controller {@link IUsagesController}
         * @param usageBatch {@link UsageBatch} to be deleted
         * @param container  container
         */
        ConfirmDeleteListener(IUsagesController controller, UsageBatch usageBatch,
                              BeanContainer<String, UsageBatch> container) {
            this.controller = controller;
            this.usageBatch = usageBatch;
            this.container = container;
        }

        @Override
        public void onActionConfirmed() {
            controller.deleteUsageBatch(usageBatch);
            container.removeItem(usageBatch.getId());
        }
    }

    /**
     * {@link ISearchController} implementation.
     */
    class SearchController implements ISearchController {

        @Override
        public void performSearch() {
            container.removeAllContainerFilters();
            String searchValue = searchWidget.getSearchValue();
            if (StringUtils.isNotBlank(searchValue)) {
                container.addContainerFilter(new Or(new SimpleStringFilter("name", searchValue, true, false),
                    new PaymentDateFilter(searchValue)));
            }
        }
    }
}
