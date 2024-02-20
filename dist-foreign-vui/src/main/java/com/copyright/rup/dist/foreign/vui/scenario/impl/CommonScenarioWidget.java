package com.copyright.rup.dist.foreign.vui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.main.ForeignUi;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioController;
import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenarioWidget;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.Buttons;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.HideGridColumnsProvider;
import com.copyright.rup.dist.foreign.vui.vaadin.common.ui.component.MaximizeModalWindowManager;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.CurrencyUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.util.VaadinUtils;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.CommonDialog;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.SearchWidget;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.FooterRow.FooterCell;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.function.ValueProvider;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public abstract class CommonScenarioWidget extends CommonDialog implements ICommonScenarioWidget {

    private static final long serialVersionUID = -3948172518935236769L;
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String PROPERTY_RIGHTSHOLDER_ACCOUNT_NUMBER = "rightsholder.accountNumber";
    private static final String PROPERTY_GROSS_TOTAL = "grossTotal";
    private static final String PROPERTY_SERVICE_FEE_TOTAL = "serviceFeeTotal";
    private static final String PROPERTY_NET_TOTAL = "netTotal";

    private ICommonScenarioController controller;
    private SearchWidget searchWidget;
    private VerticalLayout emptyUsagesLayout;
    private Grid<RightsholderTotalsHolder> rightsholdersGrid;
    private DataProvider<RightsholderTotalsHolder, Void> dataProvider;
    private Button menuButton;

    @Override
    public CommonScenarioWidget init() {
        setHeaderTitle(controller.getScenario().getName());
        add(initContent());
        setModalWindowProperties("view-scenario-widget", false);
        new ModalWindowManager(this).resize();
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
        updateTotalCell(PROPERTY_GROSS_TOTAL, scenarioWithAmounts.getGrossTotal());
        updateTotalCell(PROPERTY_SERVICE_FEE_TOTAL, scenarioWithAmounts.getServiceFeeTotal());
        updateTotalCell(PROPERTY_NET_TOTAL, scenarioWithAmounts.getNetTotal());
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

    protected Button getMenuButton() {
        return menuButton;
    }

    /**
     * Inits button layout.
     *
     * @return a {@link HorizontalLayout} with buttons
     */
    protected abstract HorizontalLayout initButtons();

    private VerticalLayout initContent() {
        dataProvider = DataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getSize());
        rightsholdersGrid = new Grid<>();
        rightsholdersGrid.setItems(dataProvider);
        rightsholdersGrid.setSelectionMode(SelectionMode.NONE);
        VaadinUtils.setGridProperties(rightsholdersGrid, "rightsholders-totals-table");
        addColumns();
        addFooter();
        initEmptyScenarioMessage();
        getFooter().add(initButtons());
        return VaadinUtils.initSizeFullVerticalLayout(initToolbar(), rightsholdersGrid, emptyUsagesLayout);
    }

    private void addColumns() {
        rightsholdersGrid.addComponentColumn(holder -> {
            Button button = Buttons.createLinkButton(Objects.toString(holder.getRightsholder().getAccountNumber()));
            button.addClickListener(event -> controller.onRightsholderAccountNumberClicked(
                holder.getRightsholder().getAccountNumber(), Objects.toString(holder.getRightsholder().getName())));
            return button;
        }).setHeader(ForeignUi.getMessage("table.column.rh_account_number"))
            .setSortable(true)
            .setSortProperty(PROPERTY_RIGHTSHOLDER_ACCOUNT_NUMBER)
            .setResizable(true)
            .setAutoWidth(true)
            .setKey(PROPERTY_RIGHTSHOLDER_ACCOUNT_NUMBER)
            .setId(PROPERTY_RIGHTSHOLDER_ACCOUNT_NUMBER);
        addColumn(holder -> holder.getRightsholder().getName(), "table.column.rh_account_name", "rightsholder.name",
            StringUtils.EMPTY);
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_account_number",
            "payee.accountNumber", StringUtils.EMPTY);
        addColumn(holder -> holder.getPayee().getName(), "table.column.payee_name", "payee.name", StringUtils.EMPTY);
        addColumn(holder -> CurrencyUtils.format(holder.getGrossTotal(), null),
            "table.column.gross_amount_in_usd", PROPERTY_GROSS_TOTAL, STYLE_ALIGN_RIGHT);
        addColumn(holder -> CurrencyUtils.format(holder.getServiceFeeTotal(), null),
            "table.column.service_fee_amount", PROPERTY_SERVICE_FEE_TOTAL, STYLE_ALIGN_RIGHT);
        addColumn(holder -> CurrencyUtils.format(holder.getNetTotal(), null), "table.column.net_amount_in_usd",
            PROPERTY_NET_TOTAL, STYLE_ALIGN_RIGHT);
        addColumn(holder -> {
            BigDecimal value = holder.getServiceFee();
            return Objects.nonNull(value)
                ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP))
                : StringUtils.EMPTY;
        }, "table.column.service_fee", "serviceFee", StringUtils.EMPTY);
    }

    private void addColumn(ValueProvider<RightsholderTotalsHolder, ?> provider, String captionProperty,
                           String columnId, String className) {
        rightsholdersGrid.addColumn(provider)
            .setHeader(ForeignUi.getMessage(captionProperty))
            .setSortable(true)
            .setSortProperty(columnId)
            .setClassNameGenerator(item -> className)
            .setResizable(true)
            .setAutoWidth(true)
            .setKey(columnId)
            .setId(columnId);
    }

    private void addFooter() {
        rightsholdersGrid.appendFooterRow();
        rightsholdersGrid.getElement().getThemeList().add("hide-first-footer");
        rightsholdersGrid.appendFooterRow();
        FooterRow row = rightsholdersGrid.getFooterRows().get(1);
        row.join(
            rightsholdersGrid.getColumnByKey(PROPERTY_RIGHTSHOLDER_ACCOUNT_NUMBER),
            rightsholdersGrid.getColumnByKey("rightsholder.name"),
            rightsholdersGrid.getColumnByKey("payee.accountNumber"),
            rightsholdersGrid.getColumnByKey("payee.name")
        ).setText("Totals");
        updateFooter();
    }

    private void updateTotalCell(String columnId, BigDecimal amount) {
        FooterRow row = rightsholdersGrid.getFooterRows().get(1);
        FooterCell footerCell = row.getCell(rightsholdersGrid.getColumnByKey(columnId));
        var label = new Label();
        label.addClassName(STYLE_ALIGN_RIGHT);
        label.add(new Html(CurrencyUtils.formatAsHtml(amount, null)));
        footerCell.setComponent(label);
    }

    private void initEmptyScenarioMessage() {
        var emptyScenarioMessage = new Label();
        emptyScenarioMessage.add(
            new Html(String.format("<div>%s</div>", ForeignUi.getMessage("label.content.empty_scenario"))));
        emptyScenarioMessage.setSizeUndefined();
        emptyScenarioMessage.setClassName("label-scenario-empty-message");
        emptyUsagesLayout = new VerticalLayout(emptyScenarioMessage);
        emptyUsagesLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        emptyUsagesLayout.setAlignItems(Alignment.CENTER);
        emptyUsagesLayout.setSizeFull();
        emptyUsagesLayout.setVisible(false);
    }

    private HorizontalLayout initToolbar() {
        initSearchWidget();
        HideGridColumnsProvider<RightsholderTotalsHolder> hideGridColumnsProvider =
            new HideGridColumnsProvider<>();
        hideGridColumnsProvider.hideColumns(rightsholdersGrid.getColumns(), "RH Account #");
        menuButton = hideGridColumnsProvider.getMenuButton();
        var toolbar = new HorizontalLayout(new Div(), searchWidget, menuButton);
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.setWidthFull();
        VaadinUtils.setPadding(toolbar, 0, 3, 0, 0);
        VaadinUtils.addComponentStyle(toolbar, "view-scenario-toolbar");
        return toolbar;
    }

    private void initSearchWidget() {
        searchWidget = new SearchWidget(controller);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget"));
        searchWidget.setWidth("60%");
    }

    /**
     * Implementation of {@link MaximizeModalWindowManager}.
     */
    static class ModalWindowManager extends MaximizeModalWindowManager {

        private final Dialog dialog;

        /**
         * Constructor.
         *
         * @param dialog instance of {@link Dialog}
         */
        ModalWindowManager(Dialog dialog) {
            super(dialog);
            this.dialog = dialog;
        }

        /**
         * Resizes dialog.
         */
        void resize() {
            enablePositioning(true);
            setPosition(new Position("20px", "-15px"));
            dialog.setWidth("101.7%");
            dialog.setHeight("99.5%");
            dialog.setDraggable(false);
        }
    }
}
