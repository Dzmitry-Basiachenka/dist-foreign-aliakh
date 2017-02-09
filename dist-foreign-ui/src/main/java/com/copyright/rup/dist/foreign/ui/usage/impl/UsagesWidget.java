package com.copyright.rup.dist.foreign.ui.usage.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.IntegerColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.Windows;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

import org.apache.commons.collections.CollectionUtils;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Main widget for usages.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 01/16/2017
 *
 * @author Mikita Hladkikh
 */
class UsagesWidget extends HorizontalSplitPanel implements IUsagesWidget {

    private static final String EMPTY_STYLE_NAME = "empty-usages-table";
    private static final String DETAIL_ID_PROPERTY = "detailId";
    private static final int COLUMN_WIDTH_300_PIXELS = 300;

    private IUsagesController controller;
    private LazyTable<UsageBeanQuery, UsageDto> usagesTable;

    @Override
    public void refresh() {
        LazyQueryContainer container = usagesTable.getContainerDataSource();
        container.refresh();
        if (CollectionUtils.isNotEmpty(container.getItemIds())) {
            usagesTable.removeStyleName(EMPTY_STYLE_NAME);
        } else {
            usagesTable.addStyleName(EMPTY_STYLE_NAME);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public UsagesWidget init() {
        setFirstComponent(controller.initUsagesFilterWidget());
        setSecondComponent(initUsagesLayout());
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUsagesController controller) {
        this.controller = controller;
    }

    /**
     * @return instance of {@link IUsagesController}.
     */
    IUsagesController getController() {
        return controller;
    }

    private VerticalLayout initUsagesLayout() {
        usagesTable = new LazyTable<>(controller, UsageBeanQuery.class, 1);
        addProperties();
        addColumnGenerators();
        setColumnsWidth();

        usagesTable
            .setVisibleColumns(DETAIL_ID_PROPERTY, "batchName", "fiscalYear", "rroAccountNumber", "rroName",
                "paymentDate", "workTitle", "article", "standardNumber", "wrWrkInst", "rightsholderAccountNumber",
                "rightsholderName", "publisher", "publicationDate", "numberOfCopies", "originalAmount", "grossAmount",
                "market", "marketPeriodFrom", "marketPeriodTo", "author", "status");
        setColumnHeaders();
        usagesTable.setSizeFull();
        usagesTable.setColumnCollapsingAllowed(true);
        usagesTable.setColumnCollapsible(DETAIL_ID_PROPERTY, false);
        VaadinUtils.addComponentStyle(usagesTable, "usages-table");
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), usagesTable);
        layout.setSizeFull();
        layout.setExpandRatio(usagesTable, 1);
        VaadinUtils.addComponentStyle(layout, "usages-layout");
        return layout;
    }

    private void addProperties() {
        usagesTable.addProperty("id", String.class, true);
        usagesTable.addProperty(DETAIL_ID_PROPERTY, Long.class, true);
        usagesTable.addProperty("batchName", String.class, true);
        usagesTable.addProperty("fiscalYear", String.class, true);
        usagesTable.addProperty("rroAccountNumber", Long.class, true);
        usagesTable.addProperty("rroName", String.class, false);
        usagesTable.addProperty("paymentDate", LocalDate.class, true);
        usagesTable.addProperty("workTitle", String.class, true);
        usagesTable.addProperty("article", String.class, true);
        usagesTable.addProperty("standardNumber", String.class, true);
        usagesTable.addProperty("wrWrkInst", Long.class, true);
        usagesTable.addProperty("rightsholderAccountNumber", Long.class, true);
        usagesTable.addProperty("rightsholderName", String.class, false);
        usagesTable.addProperty("publisher", String.class, true);
        usagesTable.addProperty("publicationDate", LocalDate.class, true);
        usagesTable.addProperty("numberOfCopies", Integer.class, true);
        usagesTable.addProperty("originalAmount", BigDecimal.class, true);
        usagesTable.addProperty("grossAmount", BigDecimal.class, true);
        usagesTable.addProperty("market", String.class, true);
        usagesTable.addProperty("marketPeriodFrom", Integer.class, true);
        usagesTable.addProperty("marketPeriodTo", Integer.class, true);
        usagesTable.addProperty("author", String.class, true);
        usagesTable.addProperty("status", String.class, true);
    }

    private void setColumnHeaders() {
        usagesTable
            .setColumnHeaders(ForeignUi.getMessage("table.column.detail_id"),
                ForeignUi.getMessage("table.column.batch_name"), ForeignUi.getMessage("table.column.fiscal_year"),
                ForeignUi.getMessage("table.column.rro_account_number"),
                ForeignUi.getMessage("table.column.rro_account_name"),
                ForeignUi.getMessage("table.column.payment_date"), ForeignUi.getMessage("table.column.work_title"),
                ForeignUi.getMessage("table.column.article"), ForeignUi.getMessage("table.column.standard_number"),
                ForeignUi.getMessage("table.column.wrWrkInst"), ForeignUi.getMessage("table.column.rh_account_number"),
                ForeignUi.getMessage("table.column.rh_account_name"), ForeignUi.getMessage("table.column.publisher"),
                ForeignUi.getMessage("table.column.publication_date"),
                ForeignUi.getMessage("table.column.number_of_copies"),
                ForeignUi.getMessage("table.column.original_amount"), ForeignUi.getMessage("table.column.gross_amount"),
                ForeignUi.getMessage("table.column.market"), ForeignUi.getMessage("table.column.market_period_from"),
                ForeignUi.getMessage("table.column.market_period_to"), ForeignUi.getMessage("table.column.author"),
                ForeignUi.getMessage("table.column.usage_status"));
    }

    private void addColumnGenerators() {
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        usagesTable.addGeneratedColumn("numberOfCopies", integerColumnGenerator);
        usagesTable.addGeneratedColumn("marketPeriodFrom", integerColumnGenerator);
        usagesTable.addGeneratedColumn("marketPeriodTo", integerColumnGenerator);
        usagesTable.addGeneratedColumn("fiscalYear", new FiscalYearColumnGenerator());
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        usagesTable.addGeneratedColumn(DETAIL_ID_PROPERTY, longColumnGenerator);
        usagesTable.addGeneratedColumn("wrWrkInst", longColumnGenerator);
        usagesTable.addGeneratedColumn("rightsholderAccountNumber", longColumnGenerator);
        usagesTable.addGeneratedColumn("rroAccountNumber", longColumnGenerator);
        LocalDateColumnGenerator localDateColumnGenerator = new LocalDateColumnGenerator();
        usagesTable.addGeneratedColumn("publicationDate", localDateColumnGenerator);
        usagesTable.addGeneratedColumn("paymentDate", localDateColumnGenerator);
        MoneyColumnGenerator moneyColumnGenerator = new MoneyColumnGenerator();
        usagesTable.addGeneratedColumn("originalAmount", moneyColumnGenerator);
        usagesTable.addGeneratedColumn("grossAmount", moneyColumnGenerator);
    }

    private void setColumnsWidth() {
        usagesTable.setColumnWidth("rroName", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("workTitle", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("article", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("rightsholderName", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("publisher", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("market", COLUMN_WIDTH_300_PIXELS);
        usagesTable.setColumnWidth("author", COLUMN_WIDTH_300_PIXELS);
    }

    private HorizontalLayout initButtonsLayout() {
        Button loadButton = Buttons.createButton(ForeignUi.getMessage("button.load"));
        loadButton.addClickListener(event -> Windows.showModalWindow(new UsageBatchUploadWindow()));
        Button addToScenarioButton = Buttons.createButton(ForeignUi.getMessage("button.add_to_scenario"));
        addToScenarioButton.addClickListener(event -> Windows.showNotificationWindow("Add to scenario button clicked"));
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(getController());
        fileDownloader.extend(exportButton);
        HorizontalLayout layout = new HorizontalLayout(loadButton, addToScenarioButton, exportButton);
        layout.setSpacing(true);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "usages-buttons");
        return layout;
    }
}
