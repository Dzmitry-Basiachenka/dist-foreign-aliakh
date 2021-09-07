package com.copyright.rup.dist.foreign.ui.usage.impl.acl;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmBaselineDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmBaselineWidget;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.util.VaadinUtils;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.Objects;

/**
 * Implementation of {@link IUdmBaselineWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Dzmitry Basiachenka
 */
public class UdmBaselineWidget extends HorizontalSplitPanel implements IUdmBaselineWidget {

    private static final String EMPTY_STYLE_NAME = "empty-baseline-grid";
    private static final String FOOTER_LABEL = "Usages Count: %s";
    private IUdmBaselineController controller;
    private Grid<UdmBaselineDto> udmBaselineGrid;

    @Override
    public void refresh() {
        //TODO: add refresh logic here
    }

    @Override
    @SuppressWarnings("unchecked")
    public UdmBaselineWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(controller.initBaselineFilterWidget());
        setSecondComponent(initBaselineLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmBaselineController controller) {
        this.controller = controller;
    }

    private VerticalLayout initBaselineLayout() {
        initBaselineGrid();
        VerticalLayout layout = new VerticalLayout(udmBaselineGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmBaselineGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-baseline-layout");
        return layout;
    }

    private void initBaselineGrid() {
        DataProvider<UdmBaselineDto, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmBaselineGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmBaselineGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmBaselineGrid.getFooterRow(0).getCell("detailId").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmBaselineGrid = new Grid<>(dataProvider);
        addColumns();
        udmBaselineGrid.setSelectionMode(Grid.SelectionMode.NONE);
        udmBaselineGrid.setSizeFull();
        VaadinUtils.addComponentStyle(udmBaselineGrid, "udm-baseline-grid");
    }

    private void addColumns() {
        FooterRow footer = udmBaselineGrid.appendFooterRow();
        udmBaselineGrid.setFooterVisible(true);
        Column<UdmBaselineDto, ?> column = udmBaselineGrid.addColumn(UdmBaselineDto::getId)
            .setCaption(ForeignUi.getMessage("table.column.detail_id"))
            .setId("detailId")
            .setSortProperty("detailId")
            .setWidth(200);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmBaselineDto::getPeriod, "table.column.period", "period", 100),
            addColumn(UdmBaselineDto::getUsageOrigin, "table.column.usage_origin", "usageOrigin", 100),
            addColumn(UdmBaselineDto::getOriginalDetailId, "table.column.usage_detail_id", "usageDetailId", 130),
            addColumn(UdmBaselineDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(UdmBaselineDto::getSystemTitle, "table.column.system_title", "systemTitle", 200),
            addColumn(UdmBaselineDto::getDetailLicenseeClassId, "table.column.det_lc_id", "detLcId", 100),
            addColumn(UdmBaselineDto::getDetailLicenseeClassName, "table.column.det_lc_name", "detLcName", 100),
            addColumn(UdmBaselineDto::getAggregateLicenseeClassId, "table.column.aggregate_licensee_class_id",
                "aggregateLicenseeClassId", 100),
            addColumn(UdmBaselineDto::getAggregateLicenseeClassName, "table.column.aggregate_licensee_class_name",
                "aggregateLicenseeClassName", 100),
            addColumn(UdmBaselineDto::getSurveyCountry, "table.column.survey_country", "surveyCountry", 120),
            addColumn(UdmBaselineDto::getChannel, "table.column.channel", "channel", 100),
            addColumn(UdmBaselineDto::getTypeOfUse, "table.column.tou", "typeOfUse", 100),
            addColumn(UdmBaselineDto::getAnnualizedCopies, "table.column.annualized_copies", "annualizedCopies", 130),
            addColumn(u -> getStringFromDate(u.getCreateDate()), "table.column.created_date", "createDate", 110),
            addColumn(UdmBaselineDto::getCreateUser, "table.column.created_by", "createUser", 100),
            addColumn(u -> getStringFromDate(u.getUpdateDate()), "table.column.updated_date", "updateDate", 100),
            addColumn(UdmBaselineDto::getUpdateUser, "table.column.updated_by", "updateUser", 110));
    }

    private Column<UdmBaselineDto, ?> addColumn(ValueProvider<UdmBaselineDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width) {
        return udmBaselineGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
