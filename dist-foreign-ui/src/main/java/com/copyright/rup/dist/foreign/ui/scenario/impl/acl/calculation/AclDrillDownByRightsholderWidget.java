package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderController;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclDrillDownByRightsholderWidget;
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

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implementation of {@link IAclDrillDownByRightsholderWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 07/14/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclDrillDownByRightsholderWidget extends Window implements IAclDrillDownByRightsholderWidget {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private IAclDrillDownByRightsholderController controller;
    private SearchWidget searchWidget;
    private Grid<AclScenarioDetailDto> grid;
    private DataProvider<AclScenarioDetailDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public AclDrillDownByRightsholderWidget init() {
        setWidth(1280, Unit.PIXELS);
        setHeight(600, Unit.PIXELS);
        VaadinUtils.addComponentStyle(this, "acl-drill-down-by-rightsholder-widget");
        setContent(initContent());
        return this;
    }

    @Override
    public String getSearchValue() {
        return searchWidget.getSearchValue();
    }

    @Override
    public void setController(IAclDrillDownByRightsholderController controller) {
        this.controller = controller;
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
        grid.getColumns().forEach(column -> column.setSortable(true));
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        VaadinUtils.addComponentStyle(grid, "acl-drill-down-by-rightsholder-table");
    }

    private HorizontalLayout initSearchWidget() {
        searchWidget = new SearchWidget(() -> dataProvider.refreshAll());
        searchWidget.setPrompt(ForeignUi.getMessage("field.prompt.drill_down_by_rightsholder.search_widget.acl"));
        searchWidget.setWidth(60, Unit.PERCENTAGE);
        HorizontalLayout layout = new HorizontalLayout(searchWidget);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setComponentAlignment(searchWidget, Alignment.MIDDLE_CENTER);
        return layout;
    }

    private void addColumns() {
        addColumn(AclScenarioDetailDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(
            AclScenarioDetailDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", true, 130);
        addColumn(AclScenarioDetailDto::getProductFamily, "table.column.product_family", "productFamily", true, 125);
        addColumn(AclScenarioDetailDto::getUsageBatchName, "table.column.batch_name", "usageBatchName", true, 145);
        addColumn(AclScenarioDetailDto::getPeriodEndPeriod, "table.column.period_end_date", "periodEndDate", true, 125);
        addColumn(AclScenarioDetailDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", true, 110);
        addColumn(AclScenarioDetailDto::getSystemTitle, "table.column.system_title", "systemTitle", true, 250);
        addColumn(AclScenarioDetailDto::getRhAccountNumberPrint, "table.column.print_rh_account_number",
            "rhAccountNumberPrint", true, 140);
        addColumn(AclScenarioDetailDto::getRhNamePrint, "table.column.print_rh_name", "rhNamePrint", true, 150);
        addColumn(AclScenarioDetailDto::getRhAccountNumberDigital, "table.column.digital_rh_account_number",
            "rhAccountNumberDigital", true, 140);
        addColumn(AclScenarioDetailDto::getRhNameDigital, "table.column.digital_rh_name", "rhNameDigital", true, 150);
        addColumn(AclScenarioDetailDto::getUsagePeriod, "table.column.usage_period", "usagePeriod", true, 100);
        addBigDecimalColumn(
            AclScenarioDetailDto::getUsageAgeWeight, "table.column.usage_age_weight", "usageAgeWeight", 130);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detailLicenseeClassId",
            true, 100);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassName, "table.column.det_lc_name",
            "detailLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
            "aggregateLicenseeClassId", true, 100);
        addColumn(AclScenarioDetailDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
            "aggregateLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", true, 120);
        addColumn(AclScenarioDetailDto::getReportedTypeOfUse, "table.column.reported_tou",
            "reportedTypeOfUse", true, 120);
        addColumn(AclScenarioDetailDto::getTypeOfUse, "table.column.tou", "typeOfUse", true, 120);
        addBigDecimalColumn(AclScenarioDetailDto::getNumberOfCopies, "table.column.acl_number_of_copies",
            "numberOfCopies", 125);
        addBigDecimalColumn(AclScenarioDetailDto::getWeightedCopies, "table.column.number_of_weighted_copies",
            "weightedCopies", 150);
        addColumn(detail -> detail.getPublicationType().getName(), "table.column.publication_type", "publicationType",
            true, 120);
        addBigDecimalColumn(detail -> detail.getPublicationType().getWeight(), "table.column.publication_type_weight",
            "pubTypeWeight", 120);
        addBigDecimalColumn(AclScenarioDetailDto::getPrice, "table.column.price", "price", 100);
        addBooleanColumn(AclScenarioDetailDto::isPriceFlag, "table.column.price_flag", "priceFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContent, "table.column.content", "content", 100);
        addBooleanColumn(AclScenarioDetailDto::isContentFlag, "table.column.content_flag", "contentFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContentUnitPrice, "table.column.content_unit_price",
            "contentUnitPrice", 150);
        addBooleanColumn(AclScenarioDetailDto::isContentUnitPriceFlag, "table.column.content_unit_price_flag",
            "contentUnitPriceFlag", 90);
        addBigDecimalColumn(AclScenarioDetailDto::getValueSharePrint, "table.column.print_value_share",
            "valueSharePrint", 140);
        addBigDecimalColumn(AclScenarioDetailDto::getVolumeSharePrint, "table.column.print_volume_share",
            "volumeSharePrint", 140);
        addBigDecimalColumn(AclScenarioDetailDto::getDetailSharePrint, "table.column.print_detail_share",
            "detailSharePrint", 140);
        addAmountColumn(AclScenarioDetailDto::getNetAmountPrint, "table.column.print_net_amount",
            "netAmountPrint", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getValueShareDigital, "table.column.digital_value_share",
            "valueShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getVolumeShareDigital, "table.column.digital_volume_share",
            "volumeShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getDetailShareDigital, "table.column.digital_detail_share",
            "detailShareDigital", 150);
        addAmountColumn(AclScenarioDetailDto::getNetAmountDigital, "table.column.digital_net_amount",
            "netAmountDigital", 150);
        addAmountColumn(AclScenarioDetailDto::getCombinedNetAmount, "table.column.combined_net_amount",
            "combinedNetAmount", 170);
    }

    private void addBigDecimalColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                     String sort, double width) {
        grid.addColumn(detail -> BigDecimalUtils.formatCurrencyForGrid(function.apply(detail)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    private void addColumn(ValueProvider<AclScenarioDetailDto, ?> provider, String captionProperty, String sort,
                           boolean isHidable, double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private void addAmountColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                 String sort, double width) {
        grid.addColumn(detail -> CurrencyUtils.format(function.apply(detail), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setSortProperty(sort)
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    private void addBooleanColumn(ValueProvider<AclScenarioDetailDto, Boolean> valueProvider, String captionProperty,
                                  String columnId, double width) {
        grid.addColumn(value -> BooleanUtils.toYNString(valueProvider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }
}
