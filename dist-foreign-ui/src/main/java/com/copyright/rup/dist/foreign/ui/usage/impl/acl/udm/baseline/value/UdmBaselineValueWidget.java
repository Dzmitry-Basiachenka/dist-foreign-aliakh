package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.baseline.value;

import com.copyright.rup.dist.foreign.domain.UdmValueBaselineDto;
import com.copyright.rup.dist.foreign.ui.common.utils.BigDecimalUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.BooleanUtils;
import com.copyright.rup.dist.foreign.ui.common.utils.DateUtils;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineValueWidget;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Implementation of {@link IUdmBaselineValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Anton Azarenka
 */
public class UdmBaselineValueWidget extends HorizontalSplitPanel implements IUdmBaselineValueWidget {

    private static final String EMPTY_STYLE_NAME = "empty-values-grid";
    private static final String FOOTER_LABEL = "Values Count: %s";

    private IUdmBaselineValueController controller;
    private Grid<UdmValueBaselineDto> udmBaselineValueGrid;

    @Override
    @SuppressWarnings("unchecked")
    public UdmBaselineValueWidget init() {
        setFirstComponent(controller.initBaselineValuesFilterWidget());
        setSecondComponent(initBaselineLayout());
        setSplitPosition(270, Unit.PIXELS);
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmBaselineValueController controller) {
        this.controller = controller;
    }

    private VerticalLayout initBaselineLayout() {
        initGrid();
        VerticalLayout layout = new VerticalLayout(udmBaselineValueGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmBaselineValueGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-baseline-value-layout");
        return layout;
    }

    private void initGrid() {
        DataProvider<UdmValueBaselineDto, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmBaselineValueGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmBaselineValueGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmBaselineValueGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmBaselineValueGrid = new Grid<>(dataProvider);
        addColumns();
        udmBaselineValueGrid.setSelectionMode(SelectionMode.SINGLE);
        udmBaselineValueGrid.setSizeFull();
        VaadinUtils.addComponentStyle(udmBaselineValueGrid, "udm-baseline-value-grid");
    }

    private void addColumns() {
        FooterRow footer = udmBaselineValueGrid.appendFooterRow();
        udmBaselineValueGrid.setFooterVisible(true);
        //TODO  should be value_id as sort property
        Column<UdmValueBaselineDto, ?> column = udmBaselineValueGrid.addColumn(UdmValueBaselineDto::getId)
            .setCaption(ForeignUi.getMessage("table.column.value_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(200);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmValueBaselineDto::getPeriod, "table.column.value_period", "period", 120),
            addColumn(UdmValueBaselineDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 120),
            addColumn(UdmValueBaselineDto::getSystemTitle, "table.column.system_title", "systemTitle", 200),
            addColumn(UdmValueBaselineDto::getPublicationType, "table.column.publication_type", "pubType", 200),
            addAmountColumn(UdmValueBaselineDto::getPrice, "table.column.price", "price", 120),
            addBooleanColumn(UdmValueBaselineDto::getPriceFlag, "table.column.price_flag", "priceFlag", 120),
            addAmountColumn(UdmValueBaselineDto::getContent, "table.column.content", "content", 100),
            addBooleanColumn(UdmValueBaselineDto::getContentFlag, "table.column.content_flag", "contentFlag", 100),
            addAmountColumn(UdmValueBaselineDto::getContentUnitPrice, "table.column.content_unit_price",
                "contentUnitPrice", 200),
            addColumn(UdmValueBaselineDto::getComment, "table.column.comment", "comment", 200),
            addColumn(UdmValueBaselineDto::getUpdateUser, "table.column.updated_by", "updateUser", 150),
            addColumn(value -> DateUtils.format(value.getUpdateDate()), "table.column.updated_date", "updateDate",
                110));
    }

    private Column<UdmValueBaselineDto, ?> addColumn(ValueProvider<UdmValueBaselineDto, ?> valueProvider,
                                                     String captionProperty, String columnId, double width) {
        return udmBaselineValueGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmValueBaselineDto, ?> addAmountColumn(Function<UdmValueBaselineDto, BigDecimal> function,
                                                           String captionProperty, String columnId, double width) {
        return udmBaselineValueGrid.addColumn(value -> BigDecimalUtils.formatCurrencyForGrid(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmValueBaselineDto, ?> addBooleanColumn(ValueProvider<UdmValueBaselineDto, Boolean> valueProvider,
                                                            String captionProperty, String columnId, double width) {
        return udmBaselineValueGrid.addColumn(value -> BooleanUtils.toYNString(valueProvider.apply(value)))
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    @Override
    public void refresh() {
        udmBaselineValueGrid.getDataProvider().refreshAll();
    }
}
