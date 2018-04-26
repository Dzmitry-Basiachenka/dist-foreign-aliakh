package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.CallbackDataProvider.CountCallback;
import com.vaadin.data.provider.CallbackDataProvider.FetchCallback;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Widget displays information about usages that will be added to scenario after refreshing.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
public class RefreshScenarioWindow extends Window {

    private final IScenariosController controller;
    private final DataProvider<UsageDto, Void> dataProvider;
    private Grid<UsageDto> grid;
    private HorizontalLayout buttonsLayout;

    /**
     * Constructor.
     *
     * @param fetchCallback callback for fetching a stream of items
     * @param countCallback callback for counting the number of items
     * @param controller    instance of {@link IScenariosController}
     */
    public RefreshScenarioWindow(FetchCallback<UsageDto, Void> fetchCallback,
                                 CountCallback<UsageDto, Void> countCallback, IScenariosController controller) {
        this.controller = controller;
        setCaption(ForeignUi.getMessage("label.refresh_scenario"));
        setWidth(800, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "refresh-scenario-window");
        dataProvider = DataProvider.fromCallbacks(fetchCallback, countCallback);
        setContent(initContent());
    }

    private VerticalLayout initContent() {
        Label label = new Label(ForeignUi.getMessage("label.content.note"), ContentMode.HTML);
        label.setStyleName("label-note");
        initButtonsLayout();
        initTable();
        VerticalLayout content = new VerticalLayout(label, grid, buttonsLayout);
        content.setExpandRatio(grid, 1);
        content.setSizeFull();
        content.setSpacing(true);
        content.setMargin(true);
        content.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        return content;
    }

    private void initButtonsLayout() {
        buttonsLayout = new HorizontalLayout();
        Button continueButton = Buttons.createOkButton();
        continueButton.addClickListener(e -> {
            controller.refreshScenario();
            close();
        });
        buttonsLayout.addComponents(continueButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
    }

    private void initTable() {
        grid = new Grid<>(dataProvider);
        grid.setSizeFull();
        addColumns();
        grid.setSelectionMode(SelectionMode.NONE);
        VaadinUtils.addComponentStyle(this, "refresh-usages-table");
    }

    private void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 100);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily");
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 135);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear");
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber");
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate");
        addColumn(UsageDto::getWorkTitle, "table.column.work_title", "workTitle", true, 300);
        addColumn(UsageDto::getArticle, "table.column.article", "article", true, 135);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 125);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst");
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usage ->
            CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, 80);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies");
        addColumn(usage -> CurrencyUtils.format(usage.getReportedValue(), null), "table.column.reported_value",
            "reportedValue", "v-align-right", 113);
        addColumn(usage -> CurrencyUtils.format(usage.getGrossAmount(), null), "table.column.gross_amount",
            "grossAmount", "v-align-right", 120);
        addColumn(usage -> CurrencyUtils.format(usage.getBatchGrossAmount(), null), "table.column.batch_gross_amount",
            "batchGrossAmount", "v-align-right", 120);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 135);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom");
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo");
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 300);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setSortable(true);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, boolean isHidable,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setSortable(true)
            .setWidth(width);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, String style,
                           double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setSortable(true)
            .setStyleGenerator(item -> style)
            .setWidth(width);
    }
}
