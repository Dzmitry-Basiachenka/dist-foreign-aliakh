package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.proxy;

import com.copyright.rup.dist.foreign.domain.UdmProxyValueDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmProxyValueWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of {@link IUdmProxyValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public class UdmProxyValueWidget extends HorizontalSplitPanel implements IUdmProxyValueWidget {

    private static final String EMPTY_STYLE_NAME = "empty-values-grid";
    private static final DecimalFormat MONEY_FORMATTER = new DecimalFormat("#,##0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());

    private IUdmProxyValueController controller;
    private Grid<UdmProxyValueDto> udmValuesGrid;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmProxyValueWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(controller.initProxyValueFilterWidget());
        setSecondComponent(initProxyValuesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmProxyValueController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        List<UdmProxyValueDto> proxyValues = controller.getProxyValues();
        udmValuesGrid.setDataProvider(DataProvider.ofCollection(proxyValues));
        udmValuesGrid.getFooterRow(0)
            .getCell("period")
            .setText(ForeignUi.getMessage("label.footer.proxy_values_count", CollectionUtils.size(proxyValues)));
        updateGridStyle(proxyValues);
    }

    private VerticalLayout initProxyValuesLayout() {
        initGrid();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), udmValuesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmValuesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-proxy-value-layout");
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        Button exportButton = Buttons.createButton(ForeignUi.getMessage("button.export"));
        HorizontalLayout layout = new HorizontalLayout(exportButton);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-proxy-value-buttons");
        return layout;
    }

    private void initGrid() {
        udmValuesGrid = new Grid<>();
        udmValuesGrid.setSizeFull();
        udmValuesGrid.addStyleName(EMPTY_STYLE_NAME);
        addColumns();
        VaadinUtils.addComponentStyle(udmValuesGrid, "udm-proxy-value-grid");
    }

    private void addColumns() {
        FooterRow footer = udmValuesGrid.appendFooterRow();
        udmValuesGrid.setFooterVisible(true);
        Column<UdmProxyValueDto, ?> column =
            addColumn(UdmProxyValueDto::getPeriod, "table.column.value_period", "period");
        footer.getCell(column).setText(ForeignUi.getMessage("label.footer.proxy_values_count", 0));
        addColumn(UdmProxyValueDto::getPubTypeName, "table.column.publication_type_code", "pubTypeName");
        addAmountColumn(UdmProxyValueDto::getContentUnitPrice, "table.column.content_unit_price", "contentUnitPrice");
        addColumn(UdmProxyValueDto::getContentUnitPriceCount, "table.column.content_unit_price_count",
            "contentUnitPriceCount");
    }

    private Column<UdmProxyValueDto, ?> addColumn(ValueProvider<UdmProxyValueDto, ?> valueProvider,
                                                  String captionProperty, String columnId) {
        return udmValuesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId);
    }

    private void addAmountColumn(Function<UdmProxyValueDto, BigDecimal> function, String captionProperty,
                                 String columnId) {
        udmValuesGrid.addColumn(value -> CurrencyUtils.format(function.apply(value), MONEY_FORMATTER))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true);
    }

    private void updateGridStyle(List<UdmProxyValueDto> proxyValueDtos) {
        if (CollectionUtils.isNotEmpty(proxyValueDtos)) {
            udmValuesGrid.removeStyleName(EMPTY_STYLE_NAME);
        } else {
            udmValuesGrid.addStyleName(EMPTY_STYLE_NAME);
        }
    }
}
