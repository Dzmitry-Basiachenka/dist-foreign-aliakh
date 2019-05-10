package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.YearValidator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Processor for {@link Usage}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/20/2018
 *
 * @author Nikita Levyankov
 */
public class UsageCsvProcessor extends DistCsvProcessor<Usage> {

    private final String productFamily;

    /**
     * Constructor.
     *
     * @param productFamily product family.
     */
    UsageCsvProcessor(String productFamily) {
        super();
        this.productFamily = productFamily;
    }

    @Override
    public List<String> getHeadersForValidation() {
        List<String> basicHeader =
            Arrays.stream(BasicColumns.values()).map(BasicColumns::getColumnName).collect(Collectors.toList());
        List<String> fullHeader = Arrays.stream(AdditionalColumns.values())
            .map(AdditionalColumns::getColumnName)
            .collect(Collectors.toList());
        fullHeader.addAll(basicHeader);
        return getActualHeaders().size() <= basicHeader.size() ? basicHeader : fullHeader;
    }

    @Override
    public IConverter<Usage> getConverter() {
        return new UsageConverter(productFamily);
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        LengthValidator lengthValidator2000 = new LengthValidator(2000);
        addPlainValidators(BasicColumns.TITLE, lengthValidator2000);
        addPlainValidators(BasicColumns.ARTICLE, lengthValidator1000);
        addPlainValidators(BasicColumns.STANDARD_NUMBER, lengthValidator1000);
        addPlainValidators(BasicColumns.STANDARD_NUMBER_TYPE, new LengthValidator(50));
        addPlainValidators(BasicColumns.WR_WRK_INST, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(BasicColumns.RH_ACCT_NUMBER, positiveNumberValidator, new LengthValidator(18));
        addPlainValidators(BasicColumns.PUBLISHER, lengthValidator1000);
        addPlainValidators(BasicColumns.PUB_DATE, new DateFormatValidator());
        addPlainValidators(BasicColumns.NUMBER_OF_COPIES, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(BasicColumns.REPORTED_VALUE, requiredValidator, new AmountValidator());
        addPlainValidators(BasicColumns.MARKET, requiredValidator, new LengthValidator(200));
        addPlainValidators(BasicColumns.MARKET_PERIOD_FROM, requiredValidator, new YearValidator());
        addPlainValidators(BasicColumns.MARKET_PERIOD_TO, requiredValidator, new YearValidator());
        addPlainValidators(BasicColumns.AUTHOR, lengthValidator2000);
        addPlainValidators(BasicColumns.COMMENT, new LengthValidator(100));
    }

    /**
     * Headers to support.
     */
    private enum BasicColumns implements ICsvColumn {
        TITLE("Title"),
        ARTICLE("Article"),
        STANDARD_NUMBER("Standard Number"),
        STANDARD_NUMBER_TYPE("Standard Number Type"),
        WR_WRK_INST("Wr Wrk Inst"),
        RH_ACCT_NUMBER("RH Account #"),
        PUBLISHER("Publisher"),
        PUB_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        REPORTED_VALUE("Reported Value"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author"),
        COMMENT("Comment");

        private String columnName;

        BasicColumns(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }

    private enum AdditionalColumns implements ICsvColumn {
        DETAIL_ID("Detail ID"),
        STATUS("Detail Status"),
        PRODUCT_FAMILY("Product Family"),
        BATCH_NAME("Usage Batch Name"),
        FISCAL_YEAR("Fiscal Year"),
        RRO_ACCOUNT_NUMBER("RRO Account #"),
        RRO_NAME("RRO Name"),
        PAYMENT_DATE("Payment Date"),
        RH_NAME("RH Name"),
        SYSTEM_TITLE("System Title"),
        GROSS_AMOUNT("Amt in USD"),
        BATCH_GROSS_AMOUNT("Gross Amt in USD");

        private String columnName;

        AdditionalColumns(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }

    /**
     * Converts row to usage.
     * <p>
     * Copyright (C) 2018 copyright.com
     * <p>
     * Date: 03/20/2018
     *
     * @author Nikita Levyankov
     */
    private class UsageConverter implements IConverter<Usage> {

        private final String productFamily;

        /**
         * Constructor.
         *
         * @param productFamily product family
         */
        UsageConverter(String productFamily) {
            this.productFamily = Objects.requireNonNull(productFamily);
        }

        private String getValue(String[] row, ICsvColumn header, List<String> headers) {
            return StringUtils.defaultIfBlank(row[headers.indexOf(header.getColumnName())], null);
        }

        private String getString(String[] row, ICsvColumn column, List<String> headers) {
            String value = getValue(row, column, headers);
            return null != value ? isPositiveNumber(value) ? parseScientific(value) : value : null;
        }

        private Long getLong(String[] row, ICsvColumn column, List<String> headers) {
            String value = getValue(row, column, headers);
            return null != value ? Long.valueOf(parseScientific(value)) : null;
        }

        private Integer getInteger(String[] row, ICsvColumn column, List<String> headers) {
            return NumberUtils.createInteger(getValue(row, column, headers));
        }

        private BigDecimal getBigDecimal(String[] row, ICsvColumn column, List<String> headers) {
            String value = getValue(row, column, headers);
            return null != value ? new BigDecimal(value).setScale(2, RoundingMode.HALF_UP) : null;
        }

        private boolean isPositiveNumber(String value) {
            return null != value && value.matches("[1-9]\\d*(\\.\\d*[eE][+]\\d+)?");
        }

        private String parseScientific(String value) {
            return null != value ? new BigDecimal(value).toPlainString() : null;
        }

        private LocalDate getDate(String[] row, ICsvColumn column, List<String> headers) {
            return CommonDateUtils.parseLocalDate(getValue(row, column, headers), "M/d/uuuu");
        }

        @Override
        public Usage convert(String... row) {
            List<String> headers = UsageCsvProcessor.this.getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setWorkTitle(getString(row, BasicColumns.TITLE, headers));
            result.setArticle(getString(row, BasicColumns.ARTICLE, headers));
            result.setStandardNumber(getString(row, BasicColumns.STANDARD_NUMBER, headers));
            result.setStandardNumberType(
                StringUtils.upperCase(getString(row, BasicColumns.STANDARD_NUMBER_TYPE, headers)));
            result.setWrWrkInst(getLong(row, BasicColumns.WR_WRK_INST, headers));
            if (Objects.nonNull(result.getWrWrkInst())) {
                result.setSystemTitle(result.getWorkTitle());
            }
            Rightsholder rightsholder = new Rightsholder();
            rightsholder.setAccountNumber(getLong(row, BasicColumns.RH_ACCT_NUMBER, headers));
            result.setRightsholder(rightsholder);
            result.setPublisher(getString(row, BasicColumns.PUBLISHER, headers));
            result.setPublicationDate(getDate(row, BasicColumns.PUB_DATE, headers));
            result.setNumberOfCopies(getInteger(row, BasicColumns.NUMBER_OF_COPIES, headers));
            result.setReportedValue(getBigDecimal(row, BasicColumns.REPORTED_VALUE, headers));
            result.setMarket(getString(row, BasicColumns.MARKET, headers));
            result.setMarketPeriodFrom(getInteger(row, BasicColumns.MARKET_PERIOD_FROM, headers));
            result.setMarketPeriodTo(getInteger(row, BasicColumns.MARKET_PERIOD_TO, headers));
            result.setAuthor(getString(row, BasicColumns.AUTHOR, headers));
            result.setStatus(isEligible(result) ? UsageStatusEnum.ELIGIBLE
                : isWorkFound(result) ? UsageStatusEnum.WORK_FOUND : UsageStatusEnum.NEW);
            result.setProductFamily(productFamily);
            result.setComment(getString(row, BasicColumns.COMMENT, headers));
            return result;
        }

        private boolean isEligible(Usage usage) {
            return Objects.nonNull(usage.getRightsholder().getAccountNumber()) && Objects.nonNull(usage.getWrWrkInst());
        }

        private boolean isWorkFound(Usage usage) {
            return Objects.isNull(usage.getRightsholder().getAccountNumber()) && Objects.nonNull(usage.getWrWrkInst());
        }
    }
}
