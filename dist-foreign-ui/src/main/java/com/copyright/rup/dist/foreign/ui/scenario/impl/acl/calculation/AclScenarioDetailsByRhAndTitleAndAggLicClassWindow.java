package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import com.copyright.rup.dist.foreign.domain.AclScenarioDetailDto;
import com.copyright.rup.dist.foreign.domain.filter.RightsholderResultsFilter;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.acl.IAclScenarioController;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.ValueProvider;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

/**
 * Window for displaying ACL scenario details by selected rightsholder, title, and aggregate licensee class.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 08/24/2022
 *
 * @author Aliaksandr Liakh
 */
// TODO rename the class
public class AclScenarioDetailsByRhAndTitleAndAggLicClassWindow extends Window {

    private static final String STYLE_ALIGN_RIGHT = "v-align-right";

    private final IAclScenarioController controller;
    private final RightsholderResultsFilter filter;
    private Grid<AclScenarioDetailDto> grid;

    /**
     * Constructor.
     *
     * @param controller instance of {@link IAclScenarioController}
     * @param filter     instance of {@link RightsholderResultsFilter}d
     */
    public AclScenarioDetailsByRhAndTitleAndAggLicClassWindow(IAclScenarioController controller,
                                                              RightsholderResultsFilter filter) {
        this.controller = controller;
        this.filter = Objects.requireNonNull(filter);
        VaadinUtils.setMaxComponentsWidth(this);
        VaadinUtils.addComponentStyle(this, "acl-view-scenario-details-by-rh-title-agg-lic-class-window");
        setHeight(95, Unit.PERCENTAGE);
        setDraggable(false);
        setResizable(false);
        setContent(initContent());
    }

    private VerticalLayout initContent() {
        grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.NONE);
        grid.setItems(controller.getRightsholderDetailsResults(filter));
        addColumns();
        // TODO implement addFooter();
        grid.setSizeFull();
        grid.getColumns().forEach(column -> column.setSortable(true));
        VaadinUtils.addComponentStyle(grid, "acl-view-scenario-details-by-rh-title-agg-lic-class-table");
        HorizontalLayout buttonsLayout = new HorizontalLayout(Buttons.createCloseButton(this));
        buttonsLayout.setMargin(new MarginInfo(false, true, true, false));
        VerticalLayout content = new VerticalLayout(grid, buttonsLayout);
        content.setSizeFull();
        content.setMargin(false);
        content.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);
        content.setExpandRatio(grid, 1);
        return content;
    }

    private void addColumns() {
        addColumn(AclScenarioDetailDto::getId, "table.column.detail_id", "detailId", false, 130);
        addColumn(AclScenarioDetailDto::getPeriodEndPeriod, "table.column.usage_period", "periodEndDate", true, 125);
        addBigDecimalColumn(AclScenarioDetailDto::getUsageAgeWeight, "table.column.usage_age_weight",
            "usageAgeWeight", 130);
        addColumn(AclScenarioDetailDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", true, 120);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassId, "table.column.det_lc_id",
            "detailLicenseeClassId", true, 100);
        addColumn(AclScenarioDetailDto::getDetailLicenseeClassName, "table.column.det_lc_name",
            "detailLicenseeClassName", true, 200);
        addColumn(AclScenarioDetailDto::getReportedTypeOfUse, "table.column.tou", "reportedTypeOfUse", true, 120);
        addBigDecimalColumn(AclScenarioDetailDto::getNumberOfCopies, "table.column.acl_number_of_copies",
            "numberOfCopies", 125);
        addBigDecimalColumn(AclScenarioDetailDto::getWeightedCopies, "table.column.number_of_weighted_copies",
            "weightedCopies", 150);
        addColumn(detail -> detail.getPublicationType().getName(), "table.column.publication_type",
            "publicationType", true, 120);
        addBigDecimalColumn(detail -> detail.getPublicationType().getWeight(), "table.column.publication_type_weight",
            "pubTypeWeight", 120);
        addBigDecimalColumn(AclScenarioDetailDto::getPrice, "table.column.price", "price", 100);
        addBooleanColumn(AclScenarioDetailDto::isPriceFlag, "table.column.price_flag", "priceFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContent, "table.column.content", "content", 100);
        addBooleanColumn(AclScenarioDetailDto::isContentFlag, "table.column.content_flag", "contentFlag", 110);
        addBigDecimalColumn(AclScenarioDetailDto::getContentUnitPrice, "table.column.content_unit_price",
            "contentUnitPrice", 150);
        addBooleanColumn(AclScenarioDetailDto::isContentUnitPriceFlag, "table.column.content_unit_price_flag",
            "contentUnitPriceFlag", 160);
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
