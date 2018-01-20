package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.dist.foreign.ui.usage.impl.UsageBeanQuery;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Audit widget.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/11/18
 *
 * @author Aliaksandr Radkevich
 */
public class AuditWidget extends HorizontalSplitPanel implements IAuditWidget {

    private static final String EMPTY_STYLE_NAME = "empty-audit-table";

    private IAuditController controller;
    private LazyTable<UsageBeanQuery, UsageDto> table;
    private LazyQueryContainer container;
    private SearchWidget searchWidget;

    @SuppressWarnings("unchecked")
    @Override
    public IAuditWidget init() {
        initContent();
        setSplitPosition(200, Unit.PIXELS);
        setLocked(true);
        VaadinUtils.addComponentStyle(this, "audit-widget");
        return this;
    }

    @Override
    public void setController(IAuditController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        container.refresh();
        if (CollectionUtils.isNotEmpty(container.getItemIds())) {
            table.removeStyleName(EMPTY_STYLE_NAME);
        } else {
            table.addStyleName(EMPTY_STYLE_NAME);
        }
    }

    @Override
    public String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    private void initContent() {
        initTable();
        IAuditFilterWidget filterWidget = controller.getAuditFilterController().initWidget();
        filterWidget.addListener(FilterChangedEvent.class, controller, IAuditController.ON_FILTER_CHANGED);
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.audit_search"));
        searchWidget.setWidth(75, Unit.PERCENTAGE);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(controller.getExportUsagesStreamSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout toolbar = new HorizontalLayout(exportButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(exportButton, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        VerticalLayout layout = new VerticalLayout(toolbar, table);
        layout.setSizeFull();
        layout.setExpandRatio(table, 1f);
        layout.setSpacing(true);
        addComponents(filterWidget, layout);
    }

    private void initTable() {
        table = new LazyTable<>(controller, UsageBeanQuery.class);
        container = table.getContainerDataSource();
        table.setSizeFull();
        table.addProperty("id", String.class, false);
        table.addProperty("detailId", Long.class, true);
        table.addProperty("status", String.class, true);
        table.addProperty("batchName", String.class, true);
        table.addProperty("paymentDate", LocalDate.class, true);
        table.addProperty("rhAccountNumber", Long.class, true);
        table.addProperty("rhName", String.class, true);
        table.addProperty("wrWrkInst", Long.class, true);
        table.addProperty("workTitle", String.class, true);
        table.addProperty("standardNumber", String.class, true);
        table.addProperty("grossAmount", BigDecimal.class, true);
        table.addProperty("serviceFee", BigDecimal.class, true);
        table.addProperty("scenarioName", String.class, true);
        table.setVisibleColumns("detailId", "status", "batchName", "paymentDate", "rhAccountNumber", "rhName",
            "wrWrkInst", "workTitle", "standardNumber", "grossAmount", "serviceFee", "scenarioName");
        table.setColumnHeaders(
            ForeignUi.getMessage("table.column.detail_id"),
            ForeignUi.getMessage("table.column.usage_status"),
            ForeignUi.getMessage("table.column.batch_name"),
            ForeignUi.getMessage("table.column.payment_date"),
            ForeignUi.getMessage("table.column.rh_account_number"),
            ForeignUi.getMessage("table.column.rh_account_name"),
            ForeignUi.getMessage("table.column.wrWrkInst"),
            ForeignUi.getMessage("table.column.work_title"),
            ForeignUi.getMessage("table.column.standard_number"),
            ForeignUi.getMessage("table.column.gross_amount"),
            ForeignUi.getMessage("table.column.service_fee"),
            ForeignUi.getMessage("table.column.scenario_name"));
        addColumnsGenerators();
    }

    private void addColumnsGenerators() {
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        table.addGeneratedColumn("wrWrkInst", longColumnGenerator);
        table.addGeneratedColumn("rhAccountNumber", longColumnGenerator);
        table.addGeneratedColumn("grossAmount", new MoneyColumnGenerator());
        table.addGeneratedColumn("paymentDate", new LocalDateColumnGenerator());
        table.addGeneratedColumn("serviceFee", new PercentColumnGenerator());
        table.addGeneratedColumn("detailId", (ColumnGenerator) (source, itemId, columnId) -> {
            String detailId =
                Objects.toString(VaadinUtils.getContainerPropertyValue(source, itemId, columnId, Long.class));
            Button button = Buttons.createButton(detailId);
            button.addStyleName(BaseTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUsageHistory(Objects.toString(itemId), detailId));
            return button;
        });
    }
}
