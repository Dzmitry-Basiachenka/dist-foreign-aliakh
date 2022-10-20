package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.themes.Cornerstone;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.SerializableComparator;
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

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Window for displaying ACL scenario details by selected rightsholder, title, and aggregate licensee class.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Aliaksandr Liakh
 */
public class AclScenarioDrillDownUsageDetailsWindow extends Window {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private final IAclScenarioController controller;
    private final RightsholderResultsFilter filter;
    private Grid<AclScenarioDetailDto> grid;
    private List<AclScenarioDetailDto> scenarioDetails;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioController}
     * @param filter     instance of {@link RightsholderResultsFilter}
     */
    public AclScenarioDrillDownUsageDetailsWindow(IAclScenarioController controller, RightsholderResultsFilter filter) {
        this.controller = controller;
        this.filter = Objects.requireNonNull(filter);
        setWidth(1280, Unit.PIXELS);
        setHeight(65, Unit.PERCENTAGE);
        initGrid();
        Button closeButton = Buttons.createCloseButton(this);
        VerticalLayout content = new VerticalLayout(initMetaInfoLayout(), grid, closeButton);
        content.setSizeFull();
        content.setExpandRatio(grid, 1);
        content.setComponentAlignment(closeButton, Alignment.BOTTOM_RIGHT);
        setContent(content);
        setCaption(ForeignUi.getMessage("window.acl_scenario_drill_down_usage_details"));
        VaadinUtils.addComponentStyle(this, "acl-scenario-drill-down-usage-details-window");
    }

    private VerticalLayout initMetaInfoLayout() {
        HorizontalLayout[] components = Stream.of(
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.rh_account_number"), filter.getRhAccountNumber()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.rh_name"), filter.getRhName()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.wr_wrk_inst"), filter.getWrWrkInst()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.system_title"), filter.getSystemTitle()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.agg_lic_class_id"),
                filter.getAggregateLicenseeClassId()),
            initLabelsHorizontalLayout(ForeignUi.getMessage("label.agg_lic_class_name"),
                filter.getAggregateLicenseeClassName())
        )
            .filter(Objects::nonNull)
            .toArray(HorizontalLayout[]::new);
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(false);
        verticalLayout.setSpacing(false);
        verticalLayout.addComponents(components);
        return verticalLayout;
    }

    private HorizontalLayout initLabelsHorizontalLayout(String title, Object value) {
        HorizontalLayout horizontalLayout = null;
        if (Objects.nonNull(value)) {
            Label titleLabel = new Label(ForeignUi.getMessage("label.title", title));
            titleLabel.setWidth(90, Unit.PIXELS);
            titleLabel.addStyleName(Cornerstone.LABEL_BOLD);
            Label valueLabel = new Label(String.valueOf(value));
            valueLabel.setSizeFull();
            horizontalLayout = new HorizontalLayout();
            horizontalLayout.setSizeFull();
            horizontalLayout.addComponents(titleLabel, valueLabel);
            horizontalLayout.setExpandRatio(valueLabel, 1);
        }
        return horizontalLayout;
    }

    private void initGrid() {
        grid = new Grid<>();
        scenarioDetails = controller.getRightsholderDetailsResults(filter);
        grid.setItems(scenarioDetails);
        addColumns();
        addFooter();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setSizeFull();
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "acl-scenario-drill-down-usage-details-grid");
    }

    private void addColumns() {
        addColumn(AclScenarioDetailDto::getId, "table.column.detail_id", "detailId", false, 250);
        addColumn(AclScenarioDetailDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", true,
            170);
        addColumn(AclScenarioDetailDto::getUsagePeriod, "table.column.usage_period", "usagePeriod", true, 100);
        addBigDecimalColumn(AclScenarioDetailDto::getUsageAgeWeight, "table.column.usage_age_weight",
            "usageAgeWeight", 130);
        addColumn(AclScenarioDetailDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", true, 120);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassId, "table.column.det_lc_id",
            "detailLicenseeClassId", true, 75);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassName, "table.column.det_lc_name",
            "detailLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getReportedTypeOfUse, "table.column.reported_tou", "reportedTypeOfUse", true,
            120);
        addColumn(AclScenarioDetailDto::getTypeOfUse, "table.column.tou", "typeOfUse", true, 75);
        addBigDecimalColumn(AclScenarioDetailDto::getNumberOfCopies, "table.column.acl_number_of_copies",
            "numberOfCopies", 90);
        addBigDecimalColumn(AclScenarioDetailDto::getWeightedCopies, "table.column.number_of_weighted_copies",
            "weightedCopies", 150);
        addColumn(detail -> detail.getPublicationType().getName(), "table.column.publication_type",
            "publicationType", true, 75);
        addBigDecimalColumn(detail -> detail.getPublicationType().getWeight(), "table.column.publication_type_weight",
            "pubTypeWeight", 120);
        addBigDecimalColumn(AclScenarioDetailDto::getPrice, "table.column.price", "price", 100);
        addBooleanColumn(AclScenarioDetailDto::isPriceFlag, "table.column.price_flag", "priceFlag", 100);
        addBigDecimalColumn(AclScenarioDetailDto::getContent, "table.column.content", "content", 100);
        addBooleanColumn(AclScenarioDetailDto::isContentFlag, "table.column.content_flag", "contentFlag", 100);
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
            "netAmountPrint", 140);
        addBigDecimalColumn(AclScenarioDetailDto::getValueShareDigital, "table.column.digital_value_share",
            "valueShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getVolumeShareDigital, "table.column.digital_volume_share",
            "volumeShareDigital", 150);
        addBigDecimalColumn(AclScenarioDetailDto::getDetailShareDigital, "table.column.digital_detail_share",
            "detailShareDigital", 150);
        addAmountColumn(AclScenarioDetailDto::getNetAmountDigital, "table.column.digital_net_amount",
            "netAmountDigital", 140);
        addAmountColumn(AclScenarioDetailDto::getCombinedNetAmount, "table.column.combined_net_amount",
            "combinedNetAmount", 140);
    }

    private void addColumn(ValueProvider<AclScenarioDetailDto, ?> provider, String captionProperty, String columnId,
                           boolean isHidable, double width) {
        grid.addColumn(provider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortProperty(columnId)
            .setHidable(isHidable)
            .setWidth(width);
    }

    private void addBigDecimalColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                     String columnId, double width) {
        grid.addColumn(detail -> BigDecimalUtils.formatCurrencyForGrid(function.apply(detail)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortProperty(columnId)
            .setComparator((SerializableComparator<AclScenarioDetailDto>) (detail1, detail2) ->
                Comparator.comparing(function, Comparator.nullsLast(Comparator.naturalOrder()))
                    .compare(detail1, detail2))
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    private void addAmountColumn(Function<AclScenarioDetailDto, BigDecimal> function, String captionProperty,
                                 String columnId, double width) {
        grid.addColumn(detail -> CurrencyUtils.format(function.apply(detail), null))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortProperty(columnId)
            .setComparator((SerializableComparator<AclScenarioDetailDto>) (detail1, detail2) ->
                Comparator.comparing(function, Comparator.nullsLast(Comparator.naturalOrder()))
                    .compare(detail1, detail2))
            .setHidable(true)
            .setStyleGenerator(item -> STYLE_ALIGN_RIGHT)
            .setWidth(width);
    }

    private void addBooleanColumn(ValueProvider<AclScenarioDetailDto, Boolean> provider, String captionProperty,
                                  String columnId, double width) {
        grid.addColumn(value -> BooleanUtils.toYNString(provider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private void addFooter() {
        grid.appendFooterRow();
        grid.setFooterVisible(true);
        FooterRow row = grid.getFooterRow(0);
        row.setStyleName("table-ext-footer");
        row.join("detailId", "usageDetailId", "usagePeriod", "usageAgeWeight", "surveyCountry",
                "detailLicenseeClassId", "detailLicenseeClassName", "reportedTypeOfUse", "numberOfCopies",
                "weightedCopies", "publicationType", "pubTypeWeight", "price", "priceFlag", "content", "contentFlag",
                "contentUnitPrice", "contentUnitPriceFlag")
            .setText("Totals");
        initFooterBigDecimalCell(row, "valueSharePrint", AclScenarioDetailDto::getValueSharePrint);
        initFooterBigDecimalCell(row, "volumeSharePrint", AclScenarioDetailDto::getVolumeSharePrint);
        initFooterBigDecimalCell(row, "detailSharePrint", AclScenarioDetailDto::getDetailSharePrint);
        initFooterAmountCell(row, "netAmountPrint", AclScenarioDetailDto::getNetAmountPrint);
        initFooterBigDecimalCell(row, "valueShareDigital", AclScenarioDetailDto::getValueShareDigital);
        initFooterBigDecimalCell(row, "volumeShareDigital", AclScenarioDetailDto::getVolumeShareDigital);
        initFooterBigDecimalCell(row, "detailShareDigital", AclScenarioDetailDto::getDetailShareDigital);
        initFooterAmountCell(row, "netAmountDigital", AclScenarioDetailDto::getNetAmountDigital);
        initFooterAmountCell(row, "combinedNetAmount", AclScenarioDetailDto::getCombinedNetAmount);
    }

    private void initFooterBigDecimalCell(FooterRow row, String columnId,
                                          Function<AclScenarioDetailDto, BigDecimal> function) {
        FooterCell cell = row.getCell(columnId);
        cell.setText(BigDecimalUtils.formatCurrencyForGrid(
            scenarioDetails.stream()
                .map(function)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        ));
        cell.setStyleName(STYLE_ALIGN_RIGHT);
    }

    private void initFooterAmountCell(FooterRow row, String columnId,
                                      Function<AclScenarioDetailDto, BigDecimal> function) {
        FooterCell cell = row.getCell(columnId);
        cell.setText(CurrencyUtils.format(
            scenarioDetails.stream()
                .map(function)
                .reduce(BigDecimal.ZERO, BigDecimal::add),
            null));
        cell.setStyleName(STYLE_ALIGN_RIGHT);
    }
}
