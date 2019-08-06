package com.copyright.rup.dist.foreign.ui.audit.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.reporting.impl.CsvStreamSource;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditFilterWidget;
import com.copyright.rup.dist.foreign.ui.audit.api.IAuditWidget;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

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

    private static final String EMPTY_STYLE_NAME = "empty-audit-grid";

    private IAuditController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> auditGrid;
    private DataProvider<UsageDto, Void> dataProvider;

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
        dataProvider.refreshAll();
    }

    @Override
    public String getSearchValue() {
        return StringUtils.defaultIfBlank(searchWidget.getSearchValue(), null);
    }

    Grid<UsageDto> getAuditGrid() {
        return auditGrid;
    }

    private void initContent() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getSize();
                if (0 < size) {
                    auditGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    auditGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                return size;
            });
        auditGrid = new Grid<>(dataProvider);
        auditGrid.setSelectionMode(SelectionMode.NONE);
        VaadinUtils.addComponentStyle(auditGrid, "audit-grid");
        auditGrid.setSizeFull();
        addColumns();
        auditGrid.getColumns().forEach(column -> column.setSortable(true));
        IAuditFilterWidget filterWidget = controller.getAuditFilterController().initWidget();
        filterWidget.addListener(FilterChangedEvent.class, controller, IAuditController.ON_FILTER_CHANGED);
        searchWidget = new SearchWidget(this::refresh);
        searchWidget.setPrompt(ForeignUi.getMessage("prompt.audit_search"));
        searchWidget.setWidth(75, Unit.PERCENTAGE);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader fileDownloader = new OnDemandFileDownloader(new CsvStreamSource(controller).getSource());
        fileDownloader.extend(exportButton);
        HorizontalLayout toolbar = new HorizontalLayout(exportButton, searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        toolbar.setComponentAlignment(exportButton, Alignment.BOTTOM_LEFT);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setExpandRatio(searchWidget, 1f);
        toolbar.setMargin(new MarginInfo(false, true, false, true));
        VaadinUtils.addComponentStyle(toolbar, "audit-toolbar");
        VerticalLayout layout = new VerticalLayout(toolbar, auditGrid);
        layout.setSizeFull();
        layout.setExpandRatio(auditGrid, 1f);
        layout.setMargin(false);
        layout.setSpacing(true);
        VaadinUtils.addComponentStyle(layout, "audit-layout");
        addComponents(filterWidget, layout);
    }

    private void addColumns() {
        auditGrid.addComponentColumn(usage -> {
            String detailId = Objects.toString(usage.getId());
            Button button = Buttons.createButton(detailId);
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.showUsageHistory(usage.getId(), detailId));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setSortProperty("detailId")
            .setWidth(130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status", 115);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", 125);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", 140);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", 115);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 115);
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", 300);
        addColumn(UsageDto::getPayeeAccountNumber, "table.column.payee_account_number", "payeeAccountNumber", 115);
        addColumn(UsageDto::getPayeeName, "table.column.payee_name", "payeeName", 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 300);
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle", 300);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", 140);
        addColumn(UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", 155);
        addAmountColumn(UsageDto::getReportedValue, "table.column.reported_value", "reportedValue", 115);
        addAmountColumn(UsageDto::getGrossAmount, "table.column.gross_amount", "grossAmount", 100);
        addAmountColumn(UsageDto::getBatchGrossAmount, "table.column.batch_gross_amount", "batchGrossAmount", 120);
        addColumn(usage -> {
            BigDecimal value = usage.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
                : StringUtils.EMPTY;
        }, "table.column.service_fee", "serviceFee", 115);
        addColumn(UsageDto::getScenarioName, "table.column.scenario_name", "scenarioName", 125);
        addColumn(UsageDto::getCheckNumber, "table.column.check_number", "checkNumber", 85);
        addColumn(usage -> CommonDateUtils.format(usage.getCheckDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.check_date", "checkDate", 105);
        addColumn(UsageDto::getCccEventId, "table.column.event_id", "cccEventId", 85);
        addColumn(UsageDto::getDistributionName, "table.column.distribution_name", "distributionName", 110);
        addColumn(usage ->
                CommonDateUtils.format(usage.getDistributionDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.distribution_date", "distributionDate", 105);
        addColumn(usage -> CommonDateUtils.format(usage.getPeriodEndDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.period_end_date", "periodEndDate", 115);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", 200);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, double width) {
        auditGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setWidth(width);
    }

    private void addAmountColumn(Function<UsageDto, BigDecimal> function, String captionProperty, String sort,
                                 double width) {
        auditGrid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setStyleGenerator(item -> "v-align-right")
            .setHidable(true)
            .setWidth(width);
    }
}
