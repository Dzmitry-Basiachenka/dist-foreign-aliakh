package com.copyright.rup.dist.foreign.ui.scenario.impl;

import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.ui.common.util.FiscalYearColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.IntegerColumnGenerator;
import com.copyright.rup.dist.foreign.ui.common.util.PercentColumnGenerator;
import com.copyright.rup.dist.foreign.ui.main.ForeignUi;
import com.copyright.rup.dist.foreign.ui.scenario.api.IDrillDownByRightsholderController;
import com.copyright.rup.vaadin.ui.LocalDateColumnGenerator;
import com.copyright.rup.vaadin.ui.LongColumnGenerator;
import com.copyright.rup.vaadin.ui.MoneyColumnGenerator;
import com.copyright.rup.vaadin.ui.VaadinUtils;
import com.copyright.rup.vaadin.ui.component.lazytable.LazyTable;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents table to view {@link UsageDto} associated with selected
 * {@link com.copyright.rup.dist.common.domain.Rightsholder} on drill down report.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/11/17
 *
 * @author Ihar Suvorau
 */
public class DrillDownByRightsholderTable extends LazyTable<UsageDetailsBeanQuery, UsageDto> {

    private static final String DETAIL_ID_PROPERTY = "detailId";
    private static final String USAGE_BATCH_NAME_PROPERTY = "batchName";
    private static final String FISCAL_YEAR_PROPERTY = "fiscalYear";
    private static final String RRO_NAME_PROPERTY = "rroName";
    private static final String RRO_ACCOUNT_NUMBER_PROPERTY = "rroAccountNumber";
    private static final String PAYMENT_DATE_PROPERTY = "paymentDate";
    private static final String TITLE_PROPERTY = "workTitle";
    private static final String ARTICLE_PROPERTY = "article";
    private static final String STANDARD_NUMBER_PROPERTY = "standardNumber";
    private static final String WR_WRK_INST_PROPERTY = "wrWrkInst";
    private static final String PUBLISHER_PROPERTY = "publisher";
    private static final String PUBLICATION_DATE_PROPERTY = "publicationDate";
    private static final String NUMBER_OF_COPIES_PROPERTY = "numberOfCopies";
    private static final String REPORTED_VALUE_PROPERTY = "reportedValue";
    private static final String AMT_IN_USD_PROPERTY = "grossAmount";
    private static final String SERVICE_FEE_AMOUNT_PROPERTY = "serviceFeeAmount";
    private static final String NET_AMOUNT_PROPERTY = "netAmount";
    private static final String SERVICE_FEE_PROPERTY = "serviceFee";
    private static final String MARKET_PROPERTY = "market";
    private static final String MARKET_PERIOD_FROM_PROPERTY = "marketPeriodFrom";
    private static final String MARKET_PERIOD_TO_PROPERTY = "marketPeriodTo";
    private static final String AUTHOR_PROPERTY = "author";

    /**
     * Constructor.
     *
     * @param controller {@link IDrillDownByRightsholderController} instance
     * @param queryClass query class
     */
    public DrillDownByRightsholderTable(IDrillDownByRightsholderController controller,
                                        Class<UsageDetailsBeanQuery> queryClass) {
        super(controller, queryClass);
        initTable();
    }

    @Override
    protected Object getIdProperty() {
        return DETAIL_ID_PROPERTY;
    }

    private void initTable() {
        setSizeFull();
        addProperties();
        addColumnsGenerators();
        setVisibleColumns();
        setColumnHeaders();
        setColumnCollapsingAllowed(true);
        setColumnCollapsible(DETAIL_ID_PROPERTY, false);
        VaadinUtils.addComponentStyle(this, "drill-down-by-rightsholder-table");
    }

    private void setColumnHeaders() {
        setColumnHeaders(
            ForeignUi.getMessage("table.column.detail_id"),
            ForeignUi.getMessage("table.column.batch_name"),
            ForeignUi.getMessage("table.column.fiscal_year"),
            ForeignUi.getMessage("table.column.rro_account_number"),
            ForeignUi.getMessage("table.column.rro_account_name"),
            ForeignUi.getMessage("table.column.payment_date"),
            ForeignUi.getMessage("table.column.work_title"),
            ForeignUi.getMessage("table.column.article"),
            ForeignUi.getMessage("table.column.standard_number"),
            ForeignUi.getMessage("table.column.wrWrkInst"),
            ForeignUi.getMessage("table.column.publisher"),
            ForeignUi.getMessage("table.column.publication_date"),
            ForeignUi.getMessage("table.column.number_of_copies"),
            ForeignUi.getMessage("table.column.reported_value"),
            ForeignUi.getMessage("table.column.batch_gross_amount"),
            ForeignUi.getMessage("table.column.service_fee_amount"),
            ForeignUi.getMessage("table.column.net_amount"),
            ForeignUi.getMessage("table.column.service_fee"),
            ForeignUi.getMessage("table.column.market"),
            ForeignUi.getMessage("table.column.market_period_from"),
            ForeignUi.getMessage("table.column.market_period_to"),
            ForeignUi.getMessage("table.column.author"));
    }

    private void setVisibleColumns() {
        setVisibleColumns(
            DETAIL_ID_PROPERTY,
            USAGE_BATCH_NAME_PROPERTY,
            FISCAL_YEAR_PROPERTY,
            RRO_ACCOUNT_NUMBER_PROPERTY,
            RRO_NAME_PROPERTY,
            PAYMENT_DATE_PROPERTY,
            TITLE_PROPERTY,
            ARTICLE_PROPERTY,
            STANDARD_NUMBER_PROPERTY,
            WR_WRK_INST_PROPERTY,
            PUBLISHER_PROPERTY,
            PUBLICATION_DATE_PROPERTY,
            NUMBER_OF_COPIES_PROPERTY,
            REPORTED_VALUE_PROPERTY,
            AMT_IN_USD_PROPERTY,
            SERVICE_FEE_AMOUNT_PROPERTY,
            NET_AMOUNT_PROPERTY,
            SERVICE_FEE_PROPERTY,
            MARKET_PROPERTY,
            MARKET_PERIOD_FROM_PROPERTY,
            MARKET_PERIOD_TO_PROPERTY,
            AUTHOR_PROPERTY);
    }

    private void addProperties() {
        addProperty(DETAIL_ID_PROPERTY, Long.class, true);
        addProperty(USAGE_BATCH_NAME_PROPERTY, String.class, true);
        addProperty(FISCAL_YEAR_PROPERTY, Integer.class, true);
        addProperty(RRO_ACCOUNT_NUMBER_PROPERTY, Long.class, true);
        addProperty(RRO_NAME_PROPERTY, String.class, true);
        addProperty(PAYMENT_DATE_PROPERTY, LocalDate.class, true);
        addProperty(TITLE_PROPERTY, String.class, true);
        addProperty(ARTICLE_PROPERTY, String.class, true);
        addProperty(STANDARD_NUMBER_PROPERTY, String.class, true);
        addProperty(WR_WRK_INST_PROPERTY, Long.class, true);
        addProperty(PUBLISHER_PROPERTY, String.class, true);
        addProperty(PUBLICATION_DATE_PROPERTY, LocalDate.class, true);
        addProperty(NUMBER_OF_COPIES_PROPERTY, Integer.class, true);
        addProperty(REPORTED_VALUE_PROPERTY, BigDecimal.class, true);
        addProperty(AMT_IN_USD_PROPERTY, BigDecimal.class, true);
        addProperty(SERVICE_FEE_AMOUNT_PROPERTY, BigDecimal.class, true);
        addProperty(NET_AMOUNT_PROPERTY, BigDecimal.class, true);
        addProperty(SERVICE_FEE_PROPERTY, BigDecimal.class, true);
        addProperty(MARKET_PROPERTY, String.class, true);
        addProperty(MARKET_PERIOD_FROM_PROPERTY, Integer.class, true);
        addProperty(MARKET_PERIOD_TO_PROPERTY, Integer.class, true);
        addProperty(AUTHOR_PROPERTY, String.class, true);
    }

    private void addColumnsGenerators() {
        addGeneratedColumn(FISCAL_YEAR_PROPERTY, new FiscalYearColumnGenerator());
        IntegerColumnGenerator integerColumnGenerator = new IntegerColumnGenerator();
        addGeneratedColumn(NUMBER_OF_COPIES_PROPERTY, integerColumnGenerator);
        addGeneratedColumn(MARKET_PERIOD_FROM_PROPERTY, integerColumnGenerator);
        addGeneratedColumn(MARKET_PERIOD_TO_PROPERTY, integerColumnGenerator);
        LongColumnGenerator longColumnGenerator = new LongColumnGenerator();
        addGeneratedColumn(RRO_ACCOUNT_NUMBER_PROPERTY, longColumnGenerator);
        addGeneratedColumn(DETAIL_ID_PROPERTY, longColumnGenerator);
        addGeneratedColumn(WR_WRK_INST_PROPERTY, longColumnGenerator);
        LocalDateColumnGenerator localDateColumnGenerator = new LocalDateColumnGenerator();
        addGeneratedColumn(PUBLICATION_DATE_PROPERTY, localDateColumnGenerator);
        addGeneratedColumn(PAYMENT_DATE_PROPERTY, localDateColumnGenerator);
        MoneyColumnGenerator moneyColumnGenerator = new MoneyColumnGenerator();
        addGeneratedColumn(REPORTED_VALUE_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(AMT_IN_USD_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(SERVICE_FEE_AMOUNT_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(NET_AMOUNT_PROPERTY, moneyColumnGenerator);
        addGeneratedColumn(SERVICE_FEE_PROPERTY, new PercentColumnGenerator());
    }
}
