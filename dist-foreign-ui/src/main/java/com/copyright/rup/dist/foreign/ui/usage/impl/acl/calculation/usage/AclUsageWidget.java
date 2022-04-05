package com.copyright.rup.dist.foreign.ui.usage.impl.acl.calculation.usage;

import com.copyright.rup.dist.foreign.domain.AclUsageDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclUsageWidget;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implementation of {@link IAclUsageWidget}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Dzmitry Basiachenka
 */
public class AclUsageWidget extends HorizontalSplitPanel implements IAclUsageWidget {

    private static final String EMPTY_STYLE_NAME = "empty-acl-usages-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";

    private IAclUsageController controller;
    private Grid<AclUsageDto> aclUsagesGrid;
    private DataProvider<AclUsageDto, Void> dataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public IAclUsageWidget init() {
        setSplitPosition(270, Unit.PIXELS);
        setFirstComponent(controller.initAclUsageFilterWidget());
        setSecondComponent(initUsagesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void refresh() {
        dataProvider.refreshAll();
    }

    @Override
    public void setController(IAclUsageController controller) {
        this.controller = controller;
    }

    private VerticalLayout initUsagesLayout() {
        initUsagesGrid();
        VerticalLayout layout = new VerticalLayout(aclUsagesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(aclUsagesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "acl-usages-layout");
        return layout;
    }

    private void initUsagesGrid() {
        dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    aclUsagesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    aclUsagesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                aclUsagesGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            }, AclUsageDto::getId);
        aclUsagesGrid = new Grid<>(dataProvider);
        addColumns();
        aclUsagesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        aclUsagesGrid.setSizeFull();
        VaadinUtils.addComponentStyle(aclUsagesGrid, "acl-usages-grid");
    }

    private void addColumns() {
        FooterRow footer = aclUsagesGrid.appendFooterRow();
        aclUsagesGrid.setFooterVisible(true);
        footer.getCell(addColumn(AclUsageDto::getId, "table.column.detail_id", "detailId", 200))
            .setText(String.format(FOOTER_LABEL, 0));
        footer.join(addColumn(AclUsageDto::getPeriod, "table.column.period", "period", 100),
            addColumn(AclUsageDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100),
            addColumn(AclUsageDto::getChannel, "table.column.channel", "channel", 100),
            addColumn(AclUsageDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130),
            addColumn(AclUsageDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(AclUsageDto::getSystemTitle, "table.column.system_title", "systemTitle", 200),
            addColumn(AclUsageDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detLcId", 100),
            addColumn(AclUsageDto::getDetailLicenseeClassName, "table.column.det_lc_name", "detLcName", 100),
            addColumn(AclUsageDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
                "aggLcId", 100),
            addColumn(AclUsageDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
                "aggLcName", 100),
            addColumn(AclUsageDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120),
            addColumn(AclUsageDto::getPubTypeName, "table.column.publication_type", "publicationType", 150),
            addAmountColumn(AclUsageDto::getContentUnitPrice, "table.column.content_unit_price", "contentUnitPrice",
                200),
            addColumn(AclUsageDto::getTypeOfUse, "table.column.tou", "typeOfUse", 120),
            addColumn(AclUsageDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", 130),
            addColumn(AclUsageDto::getUpdateUser, "table.column.updated_by", "updateUser", 150),
            addColumn(value -> DateUtils.format(value.getUpdateDate()), "table.column.updated_date", "updateDate",
                110));
    }

    private Column<AclUsageDto, ?> addColumn(ValueProvider<AclUsageDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width) {
        return aclUsagesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<AclUsageDto, ?> addAmountColumn(Function<AclUsageDto, BigDecimal> function, String captionProperty,
                                                   String columnId, double width) {
        return aclUsagesGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }
}
