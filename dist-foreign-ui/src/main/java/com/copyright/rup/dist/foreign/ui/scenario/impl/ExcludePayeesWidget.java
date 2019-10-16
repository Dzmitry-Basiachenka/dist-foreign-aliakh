package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.RightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeeWidget;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesFilterWidget;
import com.copyright.rup.dist.foreign.ui.usage.api.FilterChangedEvent;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
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
import com.vaadin.ui.Window;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents window with ability to exclude RH details by Payee.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/2019
 *
 * @author Uladzislau_Shalamitski
 */
public class ExcludePayeesWidget extends Window implements IExcludePayeeWidget {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private SearchWidget searchWidget;
    private IExcludePayeesController controller;
    private Grid<RightsholderTotalsHolder> payeesGrid;
    private DataProvider<RightsholderTotalsHolder, Void> dataProvider;

    @Override
    public void performSearch() {
        dataProvider.refreshAll();
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IExcludePayeesController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ExcludePayeesWidget init() {
        setWidth(1200, Unit.PIXELS);
        setHeight(500, Unit.PIXELS);
        setCaption(ForeignUi.getMessage("window.exclude.payee"));
        VaadinUtils.addComponentStyle(this, "exclude-details-by-payee-window");
        initGrid();
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.scenario.search_widget.payee"));
        HorizontalLayout buttonsLayout = createButtonsLayout();
        VerticalLayout mainLayout = new VerticalLayout(searchWidget, payeesGrid, buttonsLayout);
        mainLayout.setMargin(new MarginInfo(true, true));
        mainLayout.setSizeFull();
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setExpandRatio(payeesGrid, 1);
        IExcludePayeesFilterWidget filterWidget = controller.getExcludePayeesFilterController().initWidget();
        filterWidget.addListener(FilterChangedEvent.class, controller, IExcludePayeesController.ON_FILTER_CHANGED);
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        splitPanel.addComponents(filterWidget, mainLayout);
        splitPanel.setSplitPosition(200, Unit.PIXELS);
        splitPanel.setLocked(true);
        setContent(splitPanel);
        return this;
    }

    private void initGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> controller.getBeansCount());
        payeesGrid = new Grid<>(dataProvider);
        payeesGrid.setSelectionMode(SelectionMode.MULTI);
        payeesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(payeesGrid, "exclude-details-by-payee-grid");
        addColumns();
    }

    private void addColumns() {
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_account_number",
            "payee.accountNumber");
        addColumn(holder -> holder.getPayee().getAccountNumber(), "table.column.payee_name", "payee.name");
        addAmountColumn(RightsholderTotalsHolder::getGrossTotal, "table.column.amount_in_usd", "grossTotal");
        addAmountColumn(RightsholderTotalsHolder::getServiceFeeTotal, "table.column.service_fee_amount",
            "serviceFeeTotal");
        addAmountColumn(RightsholderTotalsHolder::getServiceFeeTotal, "table.column.net_amount", "netTotal");
        addColumn(holder -> getServiceFeeString(holder.getServiceFee()), "table.column.service_fee", "serviceFee");
        payeesGrid.getColumns().forEach(column -> column.setSortable(true));
    }

    private void addColumn(ValueProvider<RightsholderTotalsHolder, ?> provider, String captionProperty,
                           String sortProperty) {
        payeesGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty);
    }

    private void addAmountColumn(Function<RightsholderTotalsHolder, BigDecimal> function, String captionProperty,
                                 String sortProperty) {
        payeesGrid.addColumn(usageDto -> CurrencyUtils.format(function.apply(usageDto), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sortProperty)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT);
    }

    private String getServiceFeeString(BigDecimal value) {
        return Objects.nonNull(value)
            ? Objects.toString(value.multiply(new BigDecimal("100")).setScale(1, BigDecimal.ROUND_HALF_UP))
            : StringUtils.EMPTY;
    }

    private HorizontalLayout createButtonsLayout() {
        Button excludeDetails = Buttons.createButton(ForeignUi.getMessage("button.exclude_details"));
        Button redesignateDetails = Buttons.createButton(ForeignUi.getMessage("button.redesignate_details"));
        return new HorizontalLayout(excludeDetails, redesignateDetails, Buttons.createCloseButton(this));
    }
}
