package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclRightsholderTotalsHolder;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclScenarioDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.downloader.OnDemandFileDownloader;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.SearchWidget;

import com.vaadin.data.provider.DataProvider;
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

import java.util.Objects;

/**
 * Implementation of {@link IAclScenarioWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Anton Azarenka
 */
public class AclScenarioWidget extends Window implements IAclScenarioWidget {

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
    private DataProvider<AclRightsholderTotalsHolder, Void> dataProvider;
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
    public void applySearch() {
        dataProvider.refreshAll();
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
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getGrossTotalPrint(), null))
            .setCaption(ForeignUi.getMessage("table.column.print_gross_amount_in_usd"))
            .setId(PROPERTY_PRINT_GROSS_TOTAL)
            .setSortProperty(PROPERTY_PRINT_GROSS_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getServiceFeeTotalPrint(), null))
            .setCaption(ForeignUi.getMessage("table.column.print_service_fee_amount_in_usd"))
            .setId(PROPERTY_PRINT_SERVICE_FEE_TOTAL)
            .setSortProperty(PROPERTY_PRINT_SERVICE_FEE_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getNetTotalPrint(), null))
            .setCaption(ForeignUi.getMessage("table.column.print_net_amount_in_usd"))
            .setId(PROPERTY_PRINT_NET_TOTAL)
            .setSortProperty(PROPERTY_PRINT_NET_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getGrossTotalDigital(), null))
            .setCaption(ForeignUi.getMessage("table.column.digital_gross_amount_in_usd"))
            .setId(PROPERTY_DIGITAL_GROSS_TOTAL)
            .setSortProperty(PROPERTY_DIGITAL_GROSS_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getServiceFeeTotalDigital(), null))
            .setCaption(ForeignUi.getMessage("table.column.digital_service_fee_amount_in_usd"))
            .setId(PROPERTY_DIGITAL_SERVICE_FEE_TOTAL)
            .setSortProperty(PROPERTY_DIGITAL_SERVICE_FEE_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addColumn(holder -> CurrencyUtils.format(holder.getNetTotalDigital(), null))
            .setCaption(ForeignUi.getMessage("table.column.digital_net_amount_in_usd"))
            .setId(PROPERTY_DIGITAL_NET_TOTAL)
            .setSortProperty(PROPERTY_DIGITAL_NET_TOTAL)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setHidable(true)
            .setExpandRatio(1);
        rightsholdersGrid.addComponentColumn(holder -> {
            Button button = Buttons.createButton(Objects.toString(holder.getNumberOfTitles()));
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> {
                RightsholderResultsFilter filter = new RightsholderResultsFilter();
                filter.setScenarioId(scenario.getId());
                filter.setRhAccountNumber(holder.getRightsholder().getAccountNumber());
                filter.setRhName(holder.getRightsholder().getName());
                Windows.showModalWindow(new AclScenarioDrillDownTitlesWindow(controller, filter));
            });
            return button;
        }).setCaption(ForeignUi.getMessage("table.column.number_of_titles"))
            .setId("numberOfTitles")
            .setSortProperty("numberOfTitles")
            .setHidable(true)
            .setExpandRatio(2);
        rightsholdersGrid.addComponentColumn(holder -> {
            Button button = Buttons.createButton(Objects.toString(holder.getNumberOfAggLcClasses()));
            button.addStyleName(ValoTheme.BUTTON_LINK);
            button.addClickListener(event -> {
                RightsholderResultsFilter filter = new RightsholderResultsFilter();
                filter.setScenarioId(scenario.getId());
                filter.setRhAccountNumber(holder.getRightsholder().getAccountNumber());
                filter.setRhName(holder.getRightsholder().getName());
                Windows.showModalWindow(
                    new AclScenarioDrillDownAggLcClassesWindow(controller, filter));
                });
            return button;
        }).setCaption(ForeignUi.getMessage("table.column.number_of_aggregate_licensee_classes"))
            .setId("numberOfAggLcClasses")
            .setSortProperty("numberOfAggLcClasses")
            .setHidable(true)
            .setExpandRatio(2);
        rightsholdersGrid.addColumn(AclRightsholderTotalsHolder::getLicenseType)
            .setCaption(ForeignUi.getMessage("table.column.license_type"))
            .setId("licenseType")
            .setSortProperty("licenseType")
            .setHidable(true)
            .setExpandRatio(2);
    }

    private void addFooter() {
        rightsholdersGrid.appendFooterRow();
        rightsholdersGrid.setFooterVisible(true);
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join("rightsholder.accountNumber", "rightsholder.name")
            .setText("Totals");
        row.getCell(PROPERTY_PRINT_GROSS_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_PRINT_SERVICE_FEE_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_PRINT_NET_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_DIGITAL_GROSS_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_DIGITAL_SERVICE_FEE_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        row.getCell(PROPERTY_DIGITAL_NET_TOTAL)
            .setStyleName(STYLE_ALIGN_RIGHT);
        updateFooter();
    }

    private void updateFooter() {
        AclScenarioDto scenarioWithAmounts = controller.getAclScenarioWithAmountsAndLastAction();
        FooterRow row = rightsholdersGrid.getFooterRow(0);
        FooterCell printGrossTotalCell = row.getCell(PROPERTY_PRINT_GROSS_TOTAL);
        printGrossTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getGrossTotalPrint(), null));
        printGrossTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell printServiceFeeTotalCell = row.getCell(PROPERTY_PRINT_SERVICE_FEE_TOTAL);
        printServiceFeeTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getServiceFeeTotalPrint(), null));
        printServiceFeeTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell printNetTotalCell = row.getCell(PROPERTY_PRINT_NET_TOTAL);
        printNetTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getNetTotalPrint(), null));
        printNetTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell digitalGrossTotalCell = row.getCell(PROPERTY_DIGITAL_GROSS_TOTAL);
        digitalGrossTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getGrossTotalDigital(), null));
        digitalGrossTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell digitalServiceFeeTotalCell = row.getCell(PROPERTY_DIGITAL_SERVICE_FEE_TOTAL);
        digitalServiceFeeTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getServiceFeeTotalDigital(), null));
        digitalServiceFeeTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
        FooterCell digitalNetTotalCell = row.getCell(PROPERTY_DIGITAL_NET_TOTAL);
        digitalNetTotalCell.setText(CurrencyUtils.format(scenarioWithAmounts.getNetTotalDigital(), null));
        digitalNetTotalCell.setStyleName(STYLE_ALIGN_RIGHT);
    }

    private HorizontalLayout initButtons() {
        Button exportDetailsButton = Buttons.createButton(ForeignUi.getMessage("button.export_details"));
        OnDemandFileDownloader exportDetailsFileDownloader =
            new OnDemandFileDownloader(controller.getExportAclScenarioDetailsStreamSource().getSource());
        exportDetailsFileDownloader.extend(exportDetailsButton);
        HorizontalLayout buttons = new HorizontalLayout(exportDetailsButton, Buttons.createCloseButton(this));
        VaadinUtils.addComponentStyle(buttons, "acl-scenario-buttons-layout");
        buttons.setMargin(new MarginInfo(false, true, true, false));
        return buttons;
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(controller);
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
