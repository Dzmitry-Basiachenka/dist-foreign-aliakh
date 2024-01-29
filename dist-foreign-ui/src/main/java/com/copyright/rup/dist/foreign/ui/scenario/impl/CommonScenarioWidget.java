package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

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
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Contains common functionality for scenario view widget.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/10/19
 *
 * @author Stanislau Rudak
 */
public abstract class CommonScenarioWidget extends Window implements ICommonScenarioWidget {

    private static final long serialVersionUID = 2553035495189233138L;
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String PROPERTY_GROSS_TOTAL = "grossTotal";
    private static final String PROPERTY_SERVICE_FEE_TOTAL = "serviceFeeTotal";
    private static final String PROPERTY_NET_TOTAL = "netTotal";

    private ICommonScenarioController controller;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;
    private Grid<RightsholderTotalsHolder> rightsholdersGrid;
    private DataProvider<RightsholderTotalsHolder, Void> dataProvider;

    @Override
    public CommonScenarioWidget init() {
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
    public void setController(ICommonScenarioController controller) {
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

    /**
     * Updates footer values.
     */
    protected void updateFooter() {
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

    protected SearchWidget getSearchWidget() {
        return searchWidget;
    }

    protected VerticalLayout getEmptyUsagesLayout() {
        return emptyUsagesLayout;
    }

    protected Grid<RightsholderTotalsHolder> getRightsholdersGrid() {
        return rightsholdersGrid;
    }

    protected DataProvider<RightsholderTotalsHolder, Void> getDataProvider() {
        return dataProvider;
    }

    /**
     * Inits button layout.
     *
     * @return a {@link HorizontalLayout} with buttons
     */
    protected abstract HorizontalLayout initButtons();

    /**
     * Inits {@link VerticalLayout} that contains widget components.
     *
     * @param searchLayout              a {@link VerticalLayout} with search widget
     * @param emptyUsagesVerticalLayout a {@link VerticalLayout} with empty usages message
     * @param grid                      rightsholders grid
     * @param buttons                   a {@link HorizontalLayout} with buttons
     * @return a {@link VerticalLayout} with components
     */
    protected abstract VerticalLayout initLayout(VerticalLayout searchLayout, Grid<RightsholderTotalsHolder> grid,
                                                 VerticalLayout emptyUsagesVerticalLayout, HorizontalLayout buttons);

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
        HorizontalLayout buttons = initButtons();
        VerticalLayout searchLayout = new VerticalLayout(initSearchWidget());
        searchLayout.setMargin(false);
        searchLayout.setSpacing(false);
        initEmptyScenarioMessage();
        VerticalLayout layout = initLayout(searchLayout, rightsholdersGrid, emptyUsagesLayout, buttons);
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
        }).setCaption(ForeignUi.getMessage("table.column.rh_account_number"))
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
            .setCaption(ForeignUi.getMessage("table.column.gross_amount_in_usd"))
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
            .setCaption(ForeignUi.getMessage("table.column.net_amount_in_usd"))
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
        }).setCaption(ForeignUi.getMessage("table.column.service_fee"))
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

    private void initEmptyScenarioMessage() {
        Label emptyScenarioMessage =
            new Label(ForeignUi.getMessage("label.content.empty_scenario"), ContentMode.HTML);
        emptyScenarioMessage.setSizeUndefined();
        emptyScenarioMessage.addStyleName(ValoTheme.LABEL_H2);
        emptyUsagesLayout = new VerticalLayout(emptyScenarioMessage);
        emptyUsagesLayout.setComponentAlignment(emptyScenarioMessage, Alignment.MIDDLE_CENTER);
        emptyUsagesLayout.setSizeFull();
        emptyUsagesLayout.setVisible(false);
    }
}
