package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents widget to display drill down report.
 * Shows information about {@link com.copyright.rup.dist.foreign.domain.UsageDto}
 * for selected {@link com.copyright.rup.dist.common.domain.Rightsholder}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public class DrillDownByRightsholderWidget extends Window implements IDrillDownByRightsholderWidget {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private IDrillDownByRightsholderController controller;
    private SearchWidget searchWidget;
    private Grid<UsageDto> grid;
    private DataProvider<UsageDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public DrillDownByRightsholderWidget init() {
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "drill-down-by-rightsholder-widget");
        setContent(initContent());
        return this;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void setController(IDrillDownByRightsholderController drillDownByRightsholderController) {
        this.controller = drillDownByRightsholderController;
    }

    private VerticalLayout initContent() {
        initGrid();
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        VerticalLayout content = new VerticalLayout(initSearchWidget(), grid, buttonsLayout);
        content.setSizeFull();
        content.setMargin(new MarginInfo(false, true, true, true));
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        content.setExpandRatio(grid, 1);
        return content;
    }

    private void initGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        grid = new Grid<>(dataProvider);
        addColumns();
        grid.getColumns().forEach(usageDtoColumn -> usageDtoColumn.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "drill-down-by-rightsholder-table");
    }

    private void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 145);
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(usageDto -> Objects.nonNull(usageDto.getFiscalYear()) ? "FY" + usageDto.getFiscalYear() : null,
            "table.column.fiscal_year", "fiscalYear", true, 105);
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber", true, 125);
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(usageDto ->
            CommonDateUtils.format(usageDto.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate", true, 115);
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle");
        addColumn(UsageDto::getArticle, "table.column.article", "article");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 140);
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, 155);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usageDto ->
            CommonDateUtils.format(usageDto.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, 90);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies", true, 140);
        addColumn(usageDto -> CurrencyUtils.format(usageDto.getReportedValue(), null), "table.column.reported_value",
            "reportedValue", STYLE_ALIGN_RIGHT, 130);
        addColumn(usageDto -> CurrencyUtils.format(usageDto.getGrossAmount(), null), "table.column.gross_amount",
            "grossAmount", STYLE_ALIGN_RIGHT, 110);
        addColumn(usageDto -> 0 != BigDecimal.ZERO.compareTo(usageDto.getBatchGrossAmount())
                ? CurrencyUtils.format(usageDto.getBatchGrossAmount(), null) : null,
            "table.column.batch_gross_amount", "batchGrossAmount", STYLE_ALIGN_RIGHT, 135);
        addColumn(usageDto -> CurrencyUtils.format(usageDto.getServiceFeeAmount(), null),
            "table.column.service_fee_amount", "serviceFeeAmount", STYLE_ALIGN_RIGHT, 150);
        addColumn(usageDto -> CurrencyUtils.format(usageDto.getNetAmount(), null), "table.column.net_amount",
            "netAmount", STYLE_ALIGN_RIGHT, 120);
        addColumn(usageDto -> {
            BigDecimal value = usageDto.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
                : StringUtils.EMPTY;
        }, "table.column.service_fee", "serviceFee", true, 115);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 115);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 90);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, boolean isHidable,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, String style,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> style)
            .setWidth(width);
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.usage.search_widget"));
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        HorizontalLayout layout = new HorizontalLayout(searchWidget);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        return layout;
    }
}
