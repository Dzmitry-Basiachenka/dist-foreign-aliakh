package com.copyright.rup.dist.foreign.vui.scenario.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.window.Windows;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider.CountCallback;
import com.vaadin.flow.data.provider.CallbackDataProvider.FetchCallback;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Widget displays information about usages that will be added to scenario after refreshing.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/12/18
 *
 * @author Ihar Suvorau
 */
public class RefreshScenarioWindow extends CommonDialog {

    private static final long serialVersionUID = -6803326666265723117L;

    private final IFasScenariosController controller;
    private final DataProvider<UsageDto, Void> dataProvider;
    private final HideGridColumnsProvider<UsageDto> hideGridColumnsProvider = new HideGridColumnsProvider<>();
    private Grid<UsageDto> grid;
    private HorizontalLayout buttonsLayout;

    /**
     * Constructor.
     *
     * @param fetchCallback callback for fetching a stream of items
     * @param countCallback callback for counting the number of items
     * @param controller    instance of {@link IFasScenariosController}
     */
    public RefreshScenarioWindow(FetchCallback<UsageDto, Void> fetchCallback,
                                 CountCallback<UsageDto, Void> countCallback, IFasScenariosController controller) {
        this.controller = controller;
        super.setHeaderTitle(ForeignUi.getMessage("label.refresh_scenario"));
        super.setWidth("1000px");
        super.setHeight("600px");
        super.setModalWindowProperties("refresh-scenario-window", true);
        dataProvider = DataProvider.fromCallbacks(fetchCallback, countCallback);
        super.add(initContent());
    }

    private VerticalLayout initContent() {
        var label = new Html(ForeignUi.getMessage("label.content.note"));
        VaadinUtils.addComponentStyle(label, "label-note");
        initButtonsLayout();
        var horizontalLayout = new HorizontalLayout(label, hideGridColumnsProvider.getMenuButton());
        horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        VaadinUtils.setMaxComponentsWidth(horizontalLayout);
        initTable();
        var content = new VerticalLayout(horizontalLayout, grid);
        getFooter().add(buttonsLayout);
        content.setSizeFull();
        content.setSpacing(false);
        content.setMargin(false);
        return content;
    }

    private void initButtonsLayout() {
        buttonsLayout = new HorizontalLayout();
        var continueButton = Buttons.createOkButton();
        continueButton.addClickListener(event -> {
            List<Long> accountNumbers = controller.getInvalidRightsholders();
            if (CollectionUtils.isNotEmpty(accountNumbers)) {
                Windows.showNotificationWindow(
                    ForeignUi.getMessage("message.error.add_to_scenario.invalid_rightsholders", "refreshed",
                        accountNumbers));
            } else {
                controller.refreshScenario();
                close();
            }
        });
        buttonsLayout.add(continueButton, Buttons.createCancelButton(this));
        buttonsLayout.setSpacing(true);
    }

    private void initTable() {
        grid = new Grid<>();
        grid.setItems(dataProvider);
        grid.setSizeFull();
        addColumns();
        grid.setSelectionMode(SelectionMode.NONE);
        VaadinUtils.setGridProperties(grid, "refresh-usages-table");
    }

    private void addColumns() {
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, "135px");
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily");
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, "190px");
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber");
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, "165px");
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, "360px");
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst");
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, "360px");
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
                ? usageDto.getFasUsage().getReportedStandardNumber() : null, "table.column.reported_standard_number",
            "reportedStandardNumber", true, "260px");
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, "185px");
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, "230px");
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear");
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate");
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, "350px");
        addColumn(UsageDto::getArticle, "table.column.article", "article", true, "185px");
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, "140px");
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, "112px");
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies");
        addAmountColumn(usage -> CurrencyUtils.format(usage.getReportedValue(), null), "table.column.reported_value",
            "reportedValue", "170px");
        addAmountColumn(usage -> CurrencyUtils.format(usage.getGrossAmount(), null), "table.column.gross_amount_in_usd",
            "grossAmount", "175px");
        addAmountColumn(usage -> CurrencyUtils.format(usage.getBatchGrossAmount(), null),
            "table.column.batch_gross_amount", "batchGrossAmount", "175px");
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, "135px");
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, "205px");
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, "200px");
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, "300px");
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, "200px");
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort) {
        Column<UsageDto> column = grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setResizable(true)
            .setSortable(true)
            .setAutoWidth(true);
        hideGridColumnsProvider.hideColumn(column);
    }

    private void addColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort, boolean isHidable,
                           String width) {
        Column<UsageDto> column = grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setSortable(true)
            .setResizable(true)
            .setWidth(width)
            .setFlexGrow(0);
        if (isHidable) {
            hideGridColumnsProvider.hideColumn(column);
        }
    }

    private void addAmountColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                                 String width) {
        Column<UsageDto> column = grid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setSortable(true)
            .setResizable(true)
            .setClassNameGenerator(item -> "v-align-right")
            .setWidth(width)
            .setFlexGrow(0);
        hideGridColumnsProvider.hideColumn(column);
    }
}
