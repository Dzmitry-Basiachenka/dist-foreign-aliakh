package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.util.CommonDateUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.ReportedValueValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.YearValidator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * Constructor.
     *
     * @param productFamily product family.
     */
    UsageCsvProcessor(String productFamily) {
        super(new UsageConverter(productFamily), true, getColumns());
    }

    static List<String> getColumns() {
        return Stream.of(Header.values())
            .map(Header::getColumnName)
            .collect(Collectors.toList());
    }

    @Override
    protected void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        LengthValidator lengthValidator2000 = new LengthValidator(2000);
        addPlainValidators(Header.TITLE, lengthValidator2000);
        addPlainValidators(Header.ARTICLE, lengthValidator1000);
        addPlainValidators(Header.STANDARD_NUMBER, lengthValidator1000);
        addPlainValidators(Header.WR_WRK_INST, positiveNumberValidator, new LengthValidator(15));
        addPlainValidators(Header.RH_ACCT_NUMBER, positiveNumberValidator, new LengthValidator(18));
        addPlainValidators(Header.PUBLISHER, lengthValidator1000);
        addPlainValidators(Header.PUB_DATE, new DateFormatValidator());
        addPlainValidators(Header.NUMBER_OF_COPIES, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.REPORTED_VALUE, requiredValidator, new ReportedValueValidator());
        addPlainValidators(Header.MARKET, requiredValidator, new LengthValidator(200));
        addPlainValidators(Header.MARKET_PERIOD_FROM, requiredValidator, new YearValidator());
        addPlainValidators(Header.MARKET_PERIOD_TO, requiredValidator, new YearValidator());
        addPlainValidators(Header.AUTHOR, lengthValidator2000);
    }

    /**
     * Headers to support.
     */
    private enum Header implements ICsvColumn {
        TITLE("Title"),
        ARTICLE("Article"),
        STANDARD_NUMBER("Standard Number"),
        WR_WRK_INST("Wr Wrk Inst"),
        RH_ACCT_NUMBER("RH Account #"),
        PUBLISHER("Publisher"),
        PUB_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        REPORTED_VALUE("Reported Value"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author");

        private String columnName;

        Header(String columnName) {
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
    private static class UsageConverter implements IConverter<Usage> {
        private final String productFamily;

        /**
         * Constructor.
         *
         * @param productFamily product family
         */
        UsageConverter(String productFamily) {
            this.productFamily = Objects.requireNonNull(productFamily);
        }

        private static String getValue(String[] row, ICsvColumn header) {
            return StringUtils.defaultIfBlank(row[header.ordinal()], null);
        }

        private static String getString(String[] row, ICsvColumn column) {
            String value = getValue(row, column);
            return null != value ? isPositiveNumber(value) ? parseScientific(value) : value : null;
        }

        private static Long getLong(String[] row, ICsvColumn column) {
            String value = getValue(row, column);
            return null != value ? Long.valueOf(parseScientific(value)) : null;
        }

        private static Integer getInteger(String[] row, ICsvColumn column) {
            return NumberUtils.createInteger(getValue(row, column));
        }

        private static BigDecimal getBigDecimal(String[] row, ICsvColumn column) {
            String value = getValue(row, column);
            return null != value ? new BigDecimal(value).setScale(2, RoundingMode.HALF_UP) : null;
        }

        private static boolean isPositiveNumber(String value) {
            return null != value && value.matches("[1-9]\\d*(\\.\\d*[eE][+]\\d+)?");
        }

        private static String parseScientific(String value) {
            return null != value ? new BigDecimal(value).toPlainString() : null;
        }

        private static LocalDate getDate(String[] row, ICsvColumn column) {
            return CommonDateUtils.parseLocalDate(getValue(row, column), "M/d/uuuu");
        }

        @Override
        public Usage convert(String... row) {
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setWorkTitle(getString(row, Header.TITLE));
            result.setArticle(getString(row, Header.ARTICLE));
            result.setStandardNumber(getString(row, Header.STANDARD_NUMBER));
            result.setWrWrkInst(getLong(row, Header.WR_WRK_INST));
            Rightsholder rightsholder = new Rightsholder();
            rightsholder.setAccountNumber(getLong(row, Header.RH_ACCT_NUMBER));
            result.setRightsholder(rightsholder);
            result.setPublisher(getString(row, Header.PUBLISHER));
            result.setPublicationDate(getDate(row, Header.PUB_DATE));
            result.setNumberOfCopies(getInteger(row, Header.NUMBER_OF_COPIES));
            result.setReportedValue(getBigDecimal(row, Header.REPORTED_VALUE));
            result.setMarket(getString(row, Header.MARKET));
            result.setMarketPeriodFrom(getInteger(row, Header.MARKET_PERIOD_FROM));
            result.setMarketPeriodTo(getInteger(row, Header.MARKET_PERIOD_TO));
            result.setAuthor(getString(row, Header.AUTHOR));
            result.setStatus(isEligible(result) ? UsageStatusEnum.ELIGIBLE
                : isWorkFound(result) ? UsageStatusEnum.WORK_FOUND : UsageStatusEnum.NEW);
            result.setProductFamily(productFamily);
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
