package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ExcludeUsagesEvent;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludeUsagesListener;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;
import com.copyright.rup.vaadin.widget.api.IMediator;
import com.copyright.rup.vaadin.widget.api.IMediatorProvider;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Widget for viewing information about rightsholders, payee and their associated amounts grouped by rightsholder.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public class ScenarioWidget extends Window implements IScenarioWidget, IMediatorProvider {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String PROPERTY_GROSS_TOTAL = "grossTotal";
    private static final String PROPERTY_SERVICE_FEE_TOTAL = "serviceFeeTotal";
    private static final String PROPERTY_NET_TOTAL = "netTotal";

    private ScenarioController controller;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;
    private Button exportDetailsButton;
    private Button exportResultsByRhButton;
    private Button excludeButton;
    private ScenarioMediator mediator;
    private Grid<RightsholderTotalsHolder> rightsholdersGrid;
    private DataProvider<RightsholderTotalsHolder, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public ScenarioWidget init() {
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "view-scenario-widget");
        Scenario scenario = controller.getScenario();
        setCaption(scenario.getName());
        setHeight(95, Unit.PERCENTAGE);
        setDraggable(false);
        setResizable(false);
        setContent(initContent());
        return this;
    }

    @Override
    public void setController(ScenarioController controller) {
        this.controller = controller;
    }

    @Override
    public void applySearch() {
        dataProvider.refreshAll();
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void fireWidgetEvent(Event event) {
        fireEvent(event);
    }

    @Override
    public void addListener(IExcludeUsagesListener listener) {
        addListener(ExcludeUsagesEvent.class, listener, IExcludeUsagesListener.EXCLUDE_DETAILS_HANDLER);
    }

    @Override
    public void refresh() {
        mediator.onScenarioUpdated(controller.isScenarioEmpty(), controller.getScenario());
    }

    @Override
    public void refreshTable() {
        dataProvider.refreshAll();
        updateFooter();
    }

    @Override
    public IMediator initMediator() {
        mediator = new ScenarioMediator();
        mediator.setExcludeButton(excludeButton);
        mediator.setExportDetailsButton(exportDetailsButton);
        mediator.setExportResultsByRhButton(exportResultsByRhButton);
        mediator.setEmptyUsagesLayout(emptyUsagesLayout);
        mediator.setRightsholderGrid(rightsholdersGrid);
        mediator.setSearchWidget(searchWidget);
        return mediator;
    }

    @Override
    public void attach() {
        super.attach();
        refresh();
    }

    private VerticalLayout initContent() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        rightsholdersGrid = new Grid<>(dataProvider);
        addColumns();
        addFooter();
        rightsholdersGrid.setSizeFull();
        rightsholdersGrid.setSelectionMode(SelectionMode.NONE);
        rightsholdersGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(rightsholdersGrid, "rightsholders-totals-table");
        initEmptyScenarioMessage();
        HorizontalLayout buttons = initButtons();
        VerticalLayout searchLayout = new VerticalLayout(initSearchWidget());
        searchLayout.setMargin(false);
        searchLayout.setSpacing(false);
        VerticalLayout layout = new VerticalLayout(searchLayout, rightsholdersGrid, emptyUsagesLayout, buttons);
        layout.setExpandRatio(rightsholdersGrid, 1);
        layout.setExpandRatio(emptyUsagesLayout, 1);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        layout.setSizeFull();
        layout.setMargin(false);
        return layout;
    }

    private void addColumns() {
        rightsholdersGrid.addComponentColumn(holder -> {
            Button button = Buttons.createButton(Objects.toString(holder.getRightsholder().getAccountNumber()));
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> controller.onRightsholderAccountNumberClicked(
                holder.getRightsholder().getAccountNumber(), Objects.toString(holder.getRightsholder().getName())));
            return button;
        })
            .setCaption(ForeignUi.getMessage("table.column.rh_account_number"))
            .setId("rightsholder.accountNumber")
            .setSortProperty("rightsholder.accountNumber")
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> holder.getRightsholder().getName())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_name"))
            .setId("rightsholder.name")
            .setSortProperty("rightsholder.name")
            .setHidable(true)
            .setExpandRatio(2);
        rightsholdersGrid.addColumn(holder -> holder.getPayee().getAccountNumber())
            .setCaption(ForeignUi.getMessage("table.column.payee_account_number"))
            .setId("payee.accountNumber")
            .setSortProperty("payee.accountNumber")
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> holder.getPayee().getName())
            .setCaption(ForeignUi.getMessage("table.column.payee_name"))
            .setId("payee.name")
            .setSortProperty("payee.name")
            .setHidable(true)
            .setExpandRatio(2);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getGrossTotal(), null))
            .setCaption(ForeignUi.getMessage("table.column.amount_in_usd"))
            .setId(PROPERTY_GROSS_TOTAL)
            .setSortProperty(PROPERTY_GROSS_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getServiceFeeTotal(), null))
            .setCaption(ForeignUi.getMessage("table.column.service_fee_amount"))
            .setId(PROPERTY_SERVICE_FEE_TOTAL)
            .setSortProperty(PROPERTY_SERVICE_FEE_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getNetTotal(), null))
            .setCaption(ForeignUi.getMessage("table.column.net_amount"))
            .setId(PROPERTY_NET_TOTAL)
            .setSortProperty(PROPERTY_NET_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> {
            BigDecimal value = holder.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
                : StringUtils.EMPTY;
        })
            .setCaption(ForeignUi.getMessage("table.column.service_fee"))
            .setId("serviceFee")
            .setSortProperty("serviceFee")
            .setHidable(true)
            .setExpandRatio(1);
    }

    private void addFooter() {
        rightsholdersGrid.appendFooterRow();
        rightsholdersGrid.setFooterVisible(true);
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join("rightsholder.accountNumber", "rightsholder.name", "payee.accountNumber", "payee.name")
            .setText("Totals");
        row.getCell(PROPERTY_GROSS_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_SERVICE_FEE_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_NET_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        updateFooter();
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(controller);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget"));
        HorizontalLayout toolbar = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setSizeFull();
        toolbar.setExpandRatio(searchWidget, 1);
        return toolbar;
    }

    private HorizontalLayout initButtons() {
        exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(controller.getExportScenarioUsagesStreamSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        exportResultsByRhButton = Buttons.createButton(ForeignUi.getMessage("button.export_results_by_rh"));
        OnDemandFileDownloader exportResultsByRhFileDownloader =
            new OnDemandFileDownloader(controller.getExportScenarioRightsholderTotalsStreamSource());
        exportResultsByRhFileDownloader.extend(exportResultsByRhButton);
        excludeButton = new Button(ForeignUi.getMessage("button.exclude.details"));
        excludeButton.addClickListener(event -> controller.onExcludeDetailsClicked());
        HorizontalLayout buttons = new HorizontalLayout(
            excludeButton, exportDetailsButton, exportResultsByRhButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private void initEmptyScenarioMessage() {
        Label emptyScenarioMessage = new Label(ForeignUi.getMessage("label.content.empty_scenario"), ContentMode.HTML);
        emptyScenarioMessage.setSizeUndefined();
        emptyScenarioMessage.addStyleName(Cornerstone.LABEL_H2);
        emptyUsagesLayout = new VerticalLayout(emptyScenarioMessage);
        emptyUsagesLayout.setComponentAlignment(emptyScenarioMessage, Alignment.MIDDLE_CENTER);
        emptyUsagesLayout.setSizeFull();
    }

    private void updateFooter() {
        Scenario scenarioWithAmounts = controller.getScenarioWithAmountsAndLastAction();
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        FooterCell grossTotalCell = row.getCell(PROPERTY_GROSS_TOTAL);
        grossTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getGrossTotal(), null));
        grossTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell serviceFeeTotalCell = row.getCell(PROPERTY_SERVICE_FEE_TOTAL);
        serviceFeeTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getServiceFeeTotal(), null));
        serviceFeeTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell netTotalCell = row.getCell(PROPERTY_NET_TOTAL);
        netTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getNetTotal(), null));
        netTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
    }
}
