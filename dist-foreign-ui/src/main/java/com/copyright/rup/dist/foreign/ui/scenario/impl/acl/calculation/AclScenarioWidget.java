package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterCell;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of {@link IAclScenarioWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
public class AclScenarioWidget extends Window implements IAclScenarioWidget, SearchWidget.ISearchController {

    private static final long serialVersionUID = -8227881821301132345L;
    private static final String STYLE_ALIGN_RIGHT = "v-align-right";
    private static final String PROPERTY_PRINT_GROSS_TOTAL = "grossTotalPrint";
    private static final String PROPERTY_PRINT_SERVICE_FEE_TOTAL = "serviceFeeTotalPrint";
    private static final String PROPERTY_PRINT_NET_TOTAL = "netTotalPrint";
    private static final String PROPERTY_DIGITAL_GROSS_TOTAL = "grossTotalDigital";
    private static final String PROPERTY_DIGITAL_SERVICE_FEE_TOTAL = "serviceFeeTotalDigital";
    private static final String PROPERTY_DIGITAL_NET_TOTAL = "netTotalDigital";

    private IAclScenarioController controller;
    private SearchWidget searchWidget;
    private Grid<AclRightsholderTotalsHolder> rightsholdersGrid;
    private ListDataProvider<AclRightsholderTotalsHolder> dataProvider;
    private List<AclRightsholderTotalsHolder> rightsholderTotalsHolders;
    private AclScenario scenario;

    /**
     * Constructor.
     *
     * @param aclScenarioController instance of {@link IAclScenarioController}
     */
    public AclScenarioWidget(IAclScenarioController aclScenarioController) {
        this.controller = aclScenarioController;
    }

    @Override
    @SuppressWarnings("unchecked")
    public IAclScenarioWidget init() {
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-view-scenario-widget");
        scenario = controller.getScenario();
        setCaption(scenario.getName());
        setHeight(95, Unit.PERCENTAGE);
        setDraggable(false);
        setResizable(false);
        setContent(initContent());
        return this;
    }

    @Override
    public void setController(IAclScenarioController controller) {
        this.controller = controller;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void performSearch() {
        dataProvider.clearFilters();
        String searchValue = searchWidget.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)) {
            dataProvider.setFilter(value ->
                StringUtils.containsIgnoreCase(Objects.toString(value.getRightsholder().getAccountNumber(), null),
                    searchValue) || StringUtils.containsIgnoreCase(value.getRightsholder().getName(), searchValue));
        }
        rightsholdersGrid.recalculateColumnWidths();
    }

    private VerticalLayout initContent() {
        rightsholderTotalsHolders = controller.getAclRightsholderTotalsHolders();
        dataProvider = new ListDataProvider<>(rightsholderTotalsHolders);
        rightsholdersGrid = new Grid<>(dataProvider);
        addColumns();
        addFooter();
        rightsholdersGrid.setSizeFull();
        rightsholdersGrid.setSelectionMode(SelectionMode.NONE);
        rightsholdersGrid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(rightsholdersGrid, "acl-rightsholders-totals-table");
        HorizontalLayout buttons = initButtons();
        VerticalLayout searchLayout = new VerticalLayout(initSearchWidget());
        searchLayout.setMargin(false);
        searchLayout.setSpacing(false);
        return initLayout(searchLayout, rightsholdersGrid, buttons);
    }

    private void addColumns() {
        rightsholdersGrid.addComponentColumn(holder -> {
                Button button = Buttons.createButton(Objects.toString(holder.getRightsholder().getAccountNumber()));
                button.addStyleName(ValoTheme.BUTTON_LINK);
                button.addClickListener(event -> controller.onRightsholderAccountNumberClicked(
                    holder.getRightsholder().getAccountNumber(), holder.getRightsholder().getName()));
                VaadinUtils.setButtonsAutoDisabled(button);
                return button;
            }).setCaption(ForeignUi.getMessage("table.column.rh_account_number"))
            .setId("rightsholder.accountNumber")
            .setComparator((holder1, holder2) ->
                holder1.getRightsholder().getAccountNumber().compareTo(holder2.getRightsholder().getAccountNumber()))
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> holder.getRightsholder().getName())
            .setCaption(ForeignUi.getMessage("table.column.rh_account_name"))
            .setId("rightsholder.name")
            .setComparator((holder1, holder2) ->
                holder1.getRightsholder().getName().compareTo(holder2.getRightsholder().getName()))
            .setHidable(true)
            .setExpandRatio(2);
        addColumn(AclRightsholderTotalsHolder::getPrintPayeeAccountNumber, "table.column.print_payee_account_number",
            "printPayeeAccountNumber");
        addColumn(AclRightsholderTotalsHolder::getPrintPayeeName, "table.column.print_payee_name",
            "printPayeeName");
        addColumn(AclRightsholderTotalsHolder::getDigitalPayeeAccountNumber,
            "table.column.digital_payee_account_number", "digitalPayeeAccountNumber");
        addColumn(AclRightsholderTotalsHolder::getDigitalPayeeName, "table.column.digital_payee_name",
            "digitalPayeeName");
        addAmountColumn(AclRightsholderTotalsHolder::getGrossTotalPrint, "table.column.print_gross_amount_in_usd",
            PROPERTY_PRINT_GROSS_TOTAL);
        addAmountColumn(AclRightsholderTotalsHolder::getServiceFeeTotalPrint,
            "table.column.print_service_fee_amount_in_usd", PROPERTY_PRINT_SERVICE_FEE_TOTAL);
        addAmountColumn(AclRightsholderTotalsHolder::getNetTotalPrint, "table.column.print_net_amount_in_usd",
            PROPERTY_PRINT_NET_TOTAL);
        addAmountColumn(AclRightsholderTotalsHolder::getGrossTotalDigital, "table.column.digital_gross_amount_in_usd",
            PROPERTY_DIGITAL_GROSS_TOTAL);
        addAmountColumn(AclRightsholderTotalsHolder::getServiceFeeTotalDigital,
            "table.column.digital_service_fee_amount_in_usd", PROPERTY_DIGITAL_SERVICE_FEE_TOTAL);
        addAmountColumn(AclRightsholderTotalsHolder::getNetTotalDigital, "table.column.digital_net_amount_in_usd",
            PROPERTY_DIGITAL_NET_TOTAL);
        addComponentColumn(AclRightsholderTotalsHolder::getNumberOfTitles,
            filter -> new AclScenarioDrillDownTitlesWindow(controller, filter), "table.column.number_of_titles",
            "numberOfTitles");
        addComponentColumn(AclRightsholderTotalsHolder::getNumberOfAggLcClasses,
            filter -> new AclScenarioDrillDownAggLcClassesWindow(controller, filter),
            "table.column.number_of_aggregate_licensee_classes", "numberOfAggLcClasses");
        rightsholdersGrid.addColumn(AclRightsholderTotalsHolder::getLicenseType)
            .setCaption(ForeignUi.getMessage("table.column.license_type"))
            .setId("licenseType")
            .setComparator((holder1, holder2) -> holder1.getLicenseType().compareTo(holder2.getLicenseType()))
            .setHidable(true)
            .setWidth(120);
    }

    private void addComponentColumn(Function<AclRightsholderTotalsHolder, Integer> function,
                                    Function<RightsholderResultsFilter, Window> windowFunction, String caption,
                                    String columnId) {
        rightsholdersGrid.addComponentColumn(holder -> {
                Button button = Buttons.createButton(Objects.toString(function.apply(holder)));
                button.addStyleName(ValoTheme.BUTTON_LINK);
                button.addClickListener(event -> {
                    RightsholderResultsFilter filter = new RightsholderResultsFilter();
                    filter.setScenarioId(scenario.getId());
                    filter.setRhAccountNumber(holder.getRightsholder().getAccountNumber());
                    filter.setRhName(holder.getRightsholder().getName());
                    Windows.showModalWindow(windowFunction.apply(filter));
                });
                return button;
            }).setCaption(ForeignUi.getMessage(caption))
            .setId(columnId)
            .setComparator((holder1, holder2) -> function.apply(holder1).compareTo(function.apply(holder2)))
            .setHidable(true)
            .setExpandRatio(2);
    }

    private void addAmountColumn(Function<AclRightsholderTotalsHolder, BigDecimal> function, String captionProperty,
                                 String columnId) {
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(function.apply(holder), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setComparator((holder1, holder2) -> function.apply(holder1).compareTo(function.apply(holder2)))
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setExpandRatio(1);
    }

    private void addColumn(ValueProvider<AclRightsholderTotalsHolder, ?> provider, String captionProperty,
                           String columnId) {
        rightsholdersGrid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setHidable(true)
            .setExpandRatio(2);
    }

    private void addFooter() {
        rightsholdersGrid.appendFooterRow();
        rightsholdersGrid.setFooterVisible(true);
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join("rightsholder.accountNumber", "rightsholder.name", "printPayeeAccountNumber", "printPayeeName",
            "digitalPayeeAccountNumber", "digitalPayeeName").setText("Totals");
        updateFooter();
    }

    private void updateFooter() {
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        initFooterCell(row, PROPERTY_PRINT_GROSS_TOTAL, AclRightsholderTotalsHolder::getGrossTotalPrint);
        initFooterCell(row, PROPERTY_PRINT_SERVICE_FEE_TOTAL, AclRightsholderTotalsHolder::getServiceFeeTotalPrint);
        initFooterCell(row, PROPERTY_PRINT_NET_TOTAL, AclRightsholderTotalsHolder::getNetTotalPrint);
        initFooterCell(row, PROPERTY_DIGITAL_GROSS_TOTAL, AclRightsholderTotalsHolder::getGrossTotalDigital);
        initFooterCell(row, PROPERTY_DIGITAL_SERVICE_FEE_TOTAL, AclRightsholderTotalsHolder::getServiceFeeTotalDigital);
        initFooterCell(row, PROPERTY_DIGITAL_NET_TOTAL, AclRightsholderTotalsHolder::getNetTotalDigital);
    }

    private void initFooterCell(FooterRow row, String cellProperty,
                                Function<AclRightsholderTotalsHolder, BigDecimal> function) {
        FooterCell cell = row.getCell(cellProperty);
        cell.setText(CurrencyUtils.format(
            rightsholderTotalsHolders.stream().map(function).reduce(BigDecimal.ZERO, BigDecimal::add), null));
        cell.setStyleName(STYLE_ALIGN_RIGHT);
    }

    private HorizontalLayout initButtons() {
        Button viewDetails = Buttons.createButton(ForeignUi.getMessage("button.view_details"));
        viewDetails.addClickListener(event -> controller.onViewDetailsClicked());
        VaadinUtils.setButtonsAutoDisabled(viewDetails);
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        OnDemandFileDownloader exportScenarioFileDownloader = new OnDemandFileDownloader(
            controller.getExportAclScenarioRightsholderTotalsStreamSource().getSource());
        exportScenarioFileDownloader.extend(exportButton);
        HorizontalLayout buttons = new HorizontalLayout(viewDetails, exportButton,
            Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "acl-scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(this);
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.acl_scenario.search_widget"));
        HorizontalLayout toolbar = new HorizontalLayout(searchWidget);
        VaadinUtils.setMaxComponentsWidth(toolbar);
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        toolbar.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        toolbar.setSizeFull();
        toolbar.setExpandRatio(searchWidget, 1);
        return toolbar;
    }

    private VerticalLayout initLayout(VerticalLayout searchLayout, Grid<AclRightsholderTotalsHolder> grid,
                                      HorizontalLayout buttons) {
        VerticalLayout layout = new VerticalLayout(searchLayout, grid, buttons);
        layout.setExpandRatio(grid, 1);
        layout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);
        layout.setSizeFull();
        layout.setMargin(false);
        return layout;
    }
}
