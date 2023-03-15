package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.common.util.UsageBatchUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IFasScenariosController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
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
public class RefreshScenarioWindow extends Window {

    private final IFasScenariosController controller;
    private final DataProvider<UsageDto, Void> dataProvider;
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
        setCaption(ForeignUi.getMessage("label.refresh_scenario"));
        setWidth(800, Unit.PIXELS);
        setHeight(400, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "refresh-scenario-window");
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(fetchCallback, countCallback);
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
        addColumn(UsageDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(UsageDto::getStatus, "table.column.usage_status", "status");
        addColumn(UsageDto::getProductFamily, "table.column.product_family", "productFamily");
        addColumn(UsageDto::getBatchName, "table.column.batch_name", "batchName", true, 135);
        addColumn(UsageDto::getRroAccountNumber, "table.column.rro_account_number", "rroAccountNumber");
        addColumn(UsageDto::getRroName, "table.column.rro_account_name", "rroName", true, 135);
        addColumn(UsageDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber");
        addColumn(UsageDto::getRhName, "table.column.rh_account_name", "rhName", true, 300);
        addColumn(UsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst");
        addColumn(UsageDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 300);
        addColumn(usageDto -> Objects.nonNull(usageDto.getFasUsage())
                ? usageDto.getFasUsage().getReportedStandardNumber() : null, "table.column.reported_standard_number",
            "reportedStandardNumber", true, 190);
        addColumn(UsageDto::getStandardNumber, "table.column.standard_number", "standardNumber", true, 125);
        addColumn(
            UsageDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType", true, 155);
        addColumn(usage -> UsageBatchUtils.getFiscalYear(usage.getFiscalYear()), "table.column.fiscal_year",
            "fiscalYear");
        addColumn(usage -> CommonDateUtils.format(usage.getPaymentDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.payment_date", "paymentDate");
        addColumn(UsageDto::getWorkTitle, "table.column.reported_title", "workTitle", true, 300);
        addColumn(UsageDto::getArticle, "table.column.article", "article", true, 135);
        addColumn(UsageDto::getPublisher, "table.column.publisher", "publisher", true, 135);
        addColumn(usage ->
                CommonDateUtils.format(usage.getPublicationDate(), RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT),
            "table.column.publication_date", "publicationDate", true, 80);
        addColumn(UsageDto::getNumberOfCopies, "table.column.number_of_copies", "numberOfCopies");
        addAmountColumn(usage -> CurrencyUtils.format(usage.getReportedValue(), null), "table.column.reported_value",
            "reportedValue", 113);
        addAmountColumn(usage -> CurrencyUtils.format(usage.getGrossAmount(), null), "table.column.gross_amount_in_usd",
            "grossAmount", 130);
        addAmountColumn(usage -> CurrencyUtils.format(usage.getBatchGrossAmount(), null),
            "table.column.batch_gross_amount", "batchGrossAmount", 130);
        addColumn(UsageDto::getMarket, "table.column.market", "market", true, 135);
        addColumn(UsageDto::getMarketPeriodFrom, "table.column.market_period_from", "marketPeriodFrom", true, 150);
        addColumn(UsageDto::getMarketPeriodTo, "table.column.market_period_to", "marketPeriodTo", true, 145);
        addColumn(UsageDto::getAuthor, "table.column.author", "author", true, 300);
        addColumn(UsageDto::getComment, "table.column.comment", "comment", true, 200);
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

    private void addAmountColumn(ValueProvider<UsageDto, ?> provider, String captionProperty, String sort,
                                 double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setSortable(true)
            .setStyleGenerator(item -> "v-align-right")
            .setWidth(width);
    }
}
