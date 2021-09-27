package com.copyright.rup.dist.foreign.ui.usage.impl.acl.udm.value;

import com.copyright.rup.common.date.RupDateUtils;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.UdmValueDto;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmValueWidget;
import com.copyright.rup.vaadin.ui.Buttons;
import com.copyright.rup.vaadin.ui.component.dataprovider.LoadingIndicatorDataProvider;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import com.copyright.rup.vaadin.util.CurrencyUtils;
import com.copyright.rup.vaadin.util.VaadinUtils;
import com.copyright.rup.vaadin.widget.api.IMediator;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of {@link IUdmValueWidget}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 08/30/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueWidget extends HorizontalSplitPanel implements IUdmValueWidget {

    private static final String EMPTY_STYLE_NAME = "empty-values-grid";
    private static final String FOOTER_LABEL = "Values Count: %s";
    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("#,##0.00########",
        CurrencyUtils.getParameterizedDecimalFormatSymbols());

    private IUdmValueController controller;
    private Grid<UdmValueDto> udmValuesGrid;
    private Button populateButton;
    private MenuBar assignmentMenuBar;

    @Override
    @SuppressWarnings("unchecked")
    public IUdmValueWidget init() {
        setSplitPosition(200, Unit.PIXELS);
        setFirstComponent(controller.initValuesFilterWidget());
        setSecondComponent(initValuesLayout());
        setLocked(true);
        setSizeFull();
        return this;
    }

    @Override
    public void setController(IUdmValueController controller) {
        this.controller = controller;
    }

    @Override
    public IMediator initMediator() {
        UdmValueMediator mediator = new UdmValueMediator();
        mediator.setPopulateButton(populateButton);
        mediator.setAssignmentMenuBar(assignmentMenuBar);
        return mediator;
    }

    /**
     * Formats decimal amount without trailing zeros after the second digit after the decimal point.
     *
     * @param amount instance of {@link BigDecimal}
     * @return formatted string or empty string in case if amount is null
     */
    String formatAmount(BigDecimal amount) {
        return CurrencyUtils.format(amount, AMOUNT_FORMATTER);
    }

    private VerticalLayout initValuesLayout() {
        initValuesGrid();
        initAssignmentMenuBar();
        VerticalLayout layout = new VerticalLayout(initButtonsLayout(), udmValuesGrid);
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setExpandRatio(udmValuesGrid, 1);
        VaadinUtils.addComponentStyle(layout, "udm-value-layout");
        return layout;
    }

    private HorizontalLayout initButtonsLayout() {
        populateButton = Buttons.createButton(ForeignUi.getMessage("button.populate_value_batch"));
        populateButton.addClickListener(event -> Windows.showModalWindow(new UdmPopulateValueBatchWindow(controller)));
        HorizontalLayout layout = new HorizontalLayout(populateButton, assignmentMenuBar);
        layout.setMargin(true);
        VaadinUtils.addComponentStyle(layout, "udm-value-buttons");
        return layout;
    }

    private void initAssignmentMenuBar() {
        assignmentMenuBar = new MenuBar();
        MenuBar.MenuItem item = assignmentMenuBar.addItem(ForeignUi.getMessage("menu.caption.assignment"), null, null);
        item.addItem(ForeignUi.getMessage("menu.item.assign"), null, null);
        item.addItem(ForeignUi.getMessage("menu.item.unassign"), null, null);
        VaadinUtils.addComponentStyle(assignmentMenuBar, "v-menubar-df");
    }

    private void initValuesGrid() {
        DataProvider<UdmValueDto, Void> dataProvider = LoadingIndicatorDataProvider.fromCallbacks(
            query -> controller.loadBeans(query.getOffset(), query.getLimit(), query.getSortOrders()).stream(),
            query -> {
                int size = controller.getBeansCount();
                if (0 < size) {
                    udmValuesGrid.removeStyleName(EMPTY_STYLE_NAME);
                } else {
                    udmValuesGrid.addStyleName(EMPTY_STYLE_NAME);
                }
                udmValuesGrid.getFooterRow(0).getCell("valuePeriod").setText(String.format(FOOTER_LABEL, size));
                return size;
            });
        udmValuesGrid = new Grid<>(dataProvider);
        addColumns();
        udmValuesGrid.setSizeFull();
        udmValuesGrid.setSelectionMode(Grid.SelectionMode.NONE);
        VaadinUtils.addComponentStyle(udmValuesGrid, "udm-value-grid");
    }

    private void addColumns() {
        FooterRow footer = udmValuesGrid.appendFooterRow();
        udmValuesGrid.setFooterVisible(true);
        Column<UdmValueDto, ?> column = addColumn(UdmValueDto::getValuePeriod, "table.column.value_period",
            "valuePeriod", 150);
        footer.getCell(column).setText(String.format(FOOTER_LABEL, 0));
        footer.join(
            addColumn(UdmValueDto::getStatus, "table.column.status", "status", 100),
            addColumn(UdmValueDto::getAssignee, "table.column.assignee", "assignee", 100),
            addColumn(UdmValueDto::getRhAccountNumber, "table.column.rh_account_number", "rhAccountNumber", 150),
            addColumn(UdmValueDto::getRhName, "table.column.rh_account_name", "rhName", 150),
            addColumn(UdmValueDto::getWrWrkInst, "table.column.wr_wrk_inst", "wrWrkInst", 100),
            addColumn(UdmValueDto::getSystemTitle, "table.column.system_title", "systemTitle", 100),
            addColumn(UdmValueDto::getSystemStandardNumber, "table.column.system_standard_number",
                "systemStandardNumber", 190),
            addColumn(UdmValueDto::getStandardNumberType, "table.column.standard_number_type", "standardNumberType",
                190),
            addColumn(UdmValueDto::getLastValuePeriod, "table.column.last_value_period", "lastValuePeriod", 150),
            addColumn(UdmValueDto::getLastPubType, "table.column.last_pub_type", "lastPubType", 150),
            addColumn(UdmValueDto::getPublicationType, "table.column.publication_type", "publicationType", 150),
            addAmountColumn(UdmValueDto::getLastPriceInUsd, "table.column.last_price_in_usd", "lastPriceInUsd", 120),
            addColumn(UdmValueDto::getLastPriceFlag, "table.column.last_price_flag", "lastPriceFlag", 120),
            addColumn(UdmValueDto::getLastPriceSource, "table.column.last_price_source", "lastPriceSource", 150),
            addColumn(UdmValueDto::getLastPriceComment, "table.column.last_price_comment", "lastPriceComment", 150),
            addAmountColumn(UdmValueDto::getPrice, "table.column.price", "price", 100),
            addColumn(UdmValueDto::getCurrency, "table.column.currency", "currency", 100),
            addColumn(UdmValueDto::getPriceType, "table.column.price_type", "priceType", 100),
            addColumn(UdmValueDto::getPriceAccessType, "table.column.price_access_type", "priceAccessType", 150),
            addColumn(UdmValueDto::getPriceYear, "table.column.price_year", "priceYear", 100),
            addColumn(UdmValueDto::getPriceComment, "table.column.price_comment", "priceComment", 120),
            addAmountColumn(UdmValueDto::getPriceInUsd, "table.column.price_in_usd", "priceInUsd", 120),
            addColumn(UdmValueDto::getPriceFlag, "table.column.price_flag", "priceFlag", 100),
            addAmountColumn(UdmValueDto::getCurrencyExchangeRate, "table.column.currency_exchange_rate",
                "currencyExchangeRate", 200),
            addColumn(value -> getStringFromLocalDate(value.getCurrencyExchangeRateDate()),
                "table.column.currency_exchange_rate_date", "currencyExchangeRateDate", 200),
            addColumn(UdmValueDto::getLastContent, "table.column.last_content", "lastContent", 100),
            addColumn(UdmValueDto::getLastContentFlag, "table.column.last_content_flag", "lastContentFlag", 150),
            addColumn(UdmValueDto::getLastContentSource, "table.column.last_content_source", "lastContentSource", 150),
            addColumn(UdmValueDto::getLastContentComment, "table.column.last_content_comment", "lastContentComment",
                200),
            addColumn(UdmValueDto::getContent, "table.column.content", "content", 100),
            addColumn(UdmValueDto::getContentComment, "table.column.content_comment", "contentComment", 200),
            addColumn(UdmValueDto::getContentFlag, "table.column.content_flag", "contentFlag", 100),
            addAmountColumn(UdmValueDto::getContentUnitPrice, "table.column.content_unit_price", "contentUnitPrice",
                200),
            addColumn(UdmValueDto::getComment, "table.column.comment", "comment", 200),
            addColumn(UdmValueDto::getUpdateUser, "table.column.updated_by", "updateUser", 150),
            addColumn(value -> getStringFromDate(value.getUpdateDate()), "table.column.updated_date", "updateDate",
                110));
    }

    private Column<UdmValueDto, ?> addColumn(ValueProvider<UdmValueDto, ?> valueProvider, String captionProperty,
                                             String columnId, double width) {
        return udmValuesGrid.addColumn(valueProvider)
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private Column<UdmValueDto, ?> addAmountColumn(Function<UdmValueDto, BigDecimal> function, String captionProperty,
                                                   String columnId, double width) {
        return udmValuesGrid.addColumn(value -> formatAmount(function.apply(value)))
            .setStyleGenerator(item -> "v-align-right")
            .setCaption(ForeignUi.getMessage(captionProperty))
            .setId(columnId)
            .setSortable(true)
            .setSortProperty(columnId)
            .setHidable(true)
            .setWidth(width);
    }

    private String getStringFromLocalDate(LocalDate date) {
        return CommonDateUtils.format(date, RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT);
    }

    private String getStringFromDate(Date date) {
        return Objects.nonNull(date)
            ? FastDateFormat.getInstance(RupDateUtils.US_DATE_FORMAT_PATTERN_SHORT).format(date)
            : StringUtils.EMPTY;
    }
}
