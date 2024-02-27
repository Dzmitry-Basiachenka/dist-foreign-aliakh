package com.copyright.rup.dist.foreign.vui.common.utils;

/**
 * Represents enum for grid columns.
 * <p>
 * Copyright (C) 2024 copyright.com
 * <p>
 * Date: 02/26/2024
 *
 * @author Ihar Suvorau
 */
public enum GridColumnEnum {

    // Common columns
    /**
     * Batch name column.
     */
    BATCH_NAME("table.column.batch_name", "batchName", WidthConstants.WIDTH_200),
    /**
     * CCC event id column.
     */
    CCC_EVENT_ID("table.column.event_id", "cccEventId", "100px"),
    /**
     * Comment column.
     */
    COMMENT("table.column.comment", "comment", WidthConstants.WIDTH_200),
    /**
     * Check date column.
     */
    CHECK_DATE("table.column.check_date", "checkDate", "125px"),
    /**
     * Check number column.
     */
    CHECK_NUMBER("table.column.check_number", "checkNumber", "100px"),
    /**
     * Created by user column.
     */
    CREATED_USER("table.column.created_by", "createdUser", "330px"),
    /**
     * Created date column.
     */
    CREATED_DATE("table.column.created_date", "createdDate", WidthConstants.WIDTH_150),
    /**
     * Distribution date column.
     */
    DISTRIBUTION_DATE("table.column.distribution_date", "distributionDate", "110px"),
    /**
     * Distribution name column.
     */
    DISTRIBUTION_NAME("table.column.distribution_name", "distributionName", "115px"),
    /**
     * Id column.
     */
    ID("table.column.detail_id", "detailId", WidthConstants.WIDTH_300),
    /**
     * Number of copied column.
     */
    NUMBER_OF_COPIES("table.column.number_of_copies", "numberOfCopies", "185px"),
    /**
     * Payee account # column.
     */
    PAYEE_ACCOUNT_NUMBER("table.column.payee_account_number", "payeeAccountNumber", "165px"),
    /**
     * Payee account # column.
     */
    PAYEE_NAME("table.column.payee_name", "payeeName", WidthConstants.WIDTH_300),
    /**
     * Period end date column.
     */
    PERIOD_END_DATE("table.column.period_ending", "periodEndDate", WidthConstants.WIDTH_150),
    /**
     * Product family column.
     */
    PRODUCT_FAMILY("table.column.product_family", "productFamily", WidthConstants.WIDTH_160),
    /**
     * Reported standard number column.
     */
    REPORTED_STANDARD_NUMBER("table.column.reported_standard_number", "reportedStandardNumber", "260px"),
    /**
     * Reported title column.
     */
    REPORTED_TITLE("table.column.reported_title", "workTitle", WidthConstants.WIDTH_300),
    /**
     * Rightsholder account # column.
     */
    RH_ACCOUNT_NUMBER("table.column.rh_account_number", "rhAccountNumber", WidthConstants.WIDTH_150),
    /**
     * Rightsholder name column.
     */
    RH_NAME("table.column.rh_account_name", "rhName", WidthConstants.WIDTH_300),
    /**
     * RRO account # column.
     */
    RRO_ACCOUNT_NUMBER("table.column.rro_account_number", "rroAccountNumber", WidthConstants.WIDTH_160),
    /**
     * RRO name column.
     */
    RRO_NAME("table.column.rro_account_name", "rroName", WidthConstants.WIDTH_300),
    /**
     * Scenario name column.
     */
    SCENARIO_NAME("table.column.scenario_name", "scenarioName", "155px"),
    /**
     * Service fee column.
     */
    SERVICE_FEE("table.column.service_fee", "serviceFee", "145px"),
    /**
     * Service fee amount column.
     */
    SERVICE_FEE_AMOUNT("table.column.service_fee_amount", "serviceFeeAmount", WidthConstants.WIDTH_200),
    /**
     * Standard number column.
     */
    STANDARD_NUMBER("table.column.standard_number", "standardNumber", WidthConstants.WIDTH_180),
    /**
     * Standard number type column.
     */
    STANDARD_NUMBER_TYPE("table.column.standard_number_type", "standardNumberType", "225px"),
    /**
     * Status column.
     */
    STATUS("table.column.usage_status", "status", WidthConstants.WIDTH_180),
    /**
     * System title column.
     */
    SYSTEM_TITLE("table.column.system_title", "systemTitle", WidthConstants.WIDTH_300),
    /**
     * Work title column.
     */
    WORK_TITLE("table.column.work_title", "workTitle", WidthConstants.WIDTH_300),
    /**
     * Wr Wrk Inst column.
     */
    WR_WRK_INST("table.column.wr_wrk_inst", "wrWrkInst", WidthConstants.WIDTH_140),

    // AACL specific columns

    /**
     * Agg LC Discipline column.
     */
    AGGREGATE_LC_DISCIPLINE("table.column.aggregate_lc_discipline", "aggregateLcDiscipline", "230px"),
    /**
     * Agg LC Enrollment column.
     */
    AGGREGATE_LC_ENROLLMENT("table.column.aggregate_lc_enrollment", "aggregateLcEnrollment", "190px"),
    /**
     * Agg LC ID column.
     */
    AGGREGATE_LICENSEE_CLASS_ID("table.column.aggregate_licensee_class_id", "aggregateLicenseeClassId", "110px"),
    /**
     * Batch period end date column.
     */
    BATCH_PERIOD_END_DATE("table.column.period_end_date", "periodEndDate", "155px"),
    /**
     * Detail licensee class discipline column.
     */
    DET_LC_DISCIPLINE("table.column.det_lc_discipline", "detailLicenseeDiscipline", "155px"),
    /**
     * Detail licensee class enrollment column.
     */
    DET_LC_ENROLLMENT("table.column.det_lc_enrollment", "detailLicenseeEnrollment", WidthConstants.WIDTH_180),
    /**
     * Detail licensee class id column.
     */
    DET_LC_ID("table.column.det_lc_id", "detailLicenseeClassId", "105px"),
    /**
     * Gross fund pool total column.
     */
    GROSS_FUND_POOL_TOTAL("table.column.gross_fund_pool_total", "grossFundPoolTotal", "220px"),
    /**
     * Institution column.
     */
    INSTITUTION("table.column.institution", "institution", WidthConstants.WIDTH_140),
    /**
     * Number of baseline years column.
     */
    NUMBER_OF_BASELINE_YEARS("table.column.number_of_baseline_years", "numberOfBaselineYears",
        WidthConstants.WIDTH_180),
    /**
     * Number of pages column.
     */
    NUMBER_OF_PAGES("table.column.number_of_pages", "numberOfPages", "165px"),
    /**
     * Period column.
     */
    PERIOD("table.column.usage_period", "usagePeriod", "135px"),
    /**
     * Publication type column.
     */
    PUB_TYPE("table.column.publication_type", "publicationType", WidthConstants.WIDTH_140),
    /**
     * Rights limitation column.
     */
    RIGHT_LIMITATION("table.column.right_limitation", "rightLimitation", WidthConstants.WIDTH_160),
    /**
     * Usage source column.
     */
    USAGE_SOURCE("table.column.usage_source", "usageSource", WidthConstants.WIDTH_140),
    /**
     * Net amount column.
     */
    NET_AMOUNT("table.column.net_amount_in_usd", "netAmount", WidthConstants.WIDTH_150),

    // FAS NTS specific columns

    /**
     * Article column.
     */
    ARTICLE("table.column.article", "article", "135px"),
    /**
     * Author column.
     */
    AUTHOR("table.column.author", "author", WidthConstants.WIDTH_200),
    /**
     * Batch gross amount column.
     */
    BATCH_GROSS_AMOUNT("table.column.batch_gross_amount", "batchGrossAmount", "170px"),
    /**
     * Classification column.
     */
    CLASSIFICATION("table.column.classification", "classification", WidthConstants.WIDTH_150),
    /**
     * Classified by user column.
     */
    CLASSIFIED_BY("table.column.classified_by", "updateUser", WidthConstants.WIDTH_300),
    /**
     * Fiscal year column.
     */
    FISCAL_YEAR("table.column.fiscal_year", "fiscalYear", "130px"),
    /**
     * Fund Pool name column.
     */
    FUND_POOL_NAME("table.column.fund_pool_name", "fundPoolName", WidthConstants.WIDTH_200),
    //TODO {vaadin23} unify column names: GROSS_AMOUNT and GROSS_AMOUNT_IN_USD
    /**
     * Gross amount column.
     */
    GROSS_AMOUNT("table.column.gross_amount", "grossAmount", WidthConstants.WIDTH_160),
    /**
     * Gross amount in USD column.
     */
    GROSS_AMOUNT_IN_USD("table.column.gross_amount_in_usd", "grossAmount", "170px"),
    /**
     * Market column.
     */
    MARKET("table.column.market", "market", "120px"),
    /**
     * Market period from column.
     */
    MARKET_PERIOD_FROM("table.column.market_period_from", "marketPeriodFrom", WidthConstants.WIDTH_200),
    /**
     * Market period to column.
     */
    MARKET_PERIOD_TO("table.column.market_period_to", "marketPeriodTo", "185px"),
    /**
     * Markets column.
     */
    MARKETS("table.column.markets", "markets", "140px"),
    /**
     * Non STM amount column.
     */
    NON_STM_AMOUNT("table.column.non_stm_amount", "nonStmAmount", "165px"),
    /**
     * Non STM amount minimum column.
     */
    NON_STM_MIN_AMOUNT("table.column.non_stm_minimum_amount", "nonStmMinimumAmount", "235px"),
    /**
     * Payment date column.
     */
    PAYMENT_DATE("table.column.payment_date", "paymentDate", "145px"),
    /**
     * Publication date column.
     */
    PUB_DATE("table.column.publication_date", "publicationDate", "110px"),
    /**
     * Publisher column.
     */
    PUBLISHER("table.column.publisher", "publisher", "135px"),
    /**
     * Reported value column.
     */
    REPORTED_VALUE("table.column.reported_value", "reportedValue", "170px"),
    /**
     * STM amount column.
     */
    STM_AMOUNT("table.column.stm_amount", "stmAmount", "130px"),
    /**
     * STM minimum amount column.
     */
    STM_MIN_AMOUNT("table.column.stm_minimum_amount", "stmMinimumAmount", WidthConstants.WIDTH_200);

    private final String caption;
    private final String sort;
    private final String width;

    /**
     * Constructor.
     *
     * @param caption property of the column's caption
     * @param sort    sort property
     * @param width   width of the column
     */
    GridColumnEnum(String caption, String sort, String width) {
        this.caption = caption;
        this.sort = sort;
        this.width = width;
    }

    public String getCaption() {
        return caption;
    }

    public String getSort() {
        return sort;
    }

    public String getWidth() {
        return width;
    }

    private static class WidthConstants {
        private static final String WIDTH_140 = "140px";
        private static final String WIDTH_150 = "150px";
        private static final String WIDTH_160 = "160px";
        private static final String WIDTH_180 = "180px";
        private static final String WIDTH_200 = "200px";
        private static final String WIDTH_300 = "300px";
    }
}
