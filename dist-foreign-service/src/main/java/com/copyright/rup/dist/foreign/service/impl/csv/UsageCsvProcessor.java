package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.FasUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DateFormatValidator;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.YearValidator;

import org.apache.commons.lang3.StringUtils;

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
            Arrays.stream(BasicHeader.values()).map(BasicHeader::getColumnName).collect(Collectors.toList());
        List<String> exportedHeader =
            Arrays.stream(ExportedHeader.values()).map(ExportedHeader::getColumnName).collect(Collectors.toList());
        return getActualHeaders().size() <= basicHeader.size() ? basicHeader : exportedHeader;
    }

    @Override
    public ICsvConverter<Usage> getConverter() {
        return new UsageConverter(productFamily);
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        LengthValidator lengthValidator1000 = new LengthValidator(1000);
        LengthValidator lengthValidator2000 = new LengthValidator(2000);
        addPlainValidators(BasicHeader.TITLE, lengthValidator2000);
        addPlainValidators(BasicHeader.ARTICLE, lengthValidator1000);
        addPlainValidators(BasicHeader.STANDARD_NUMBER, lengthValidator1000);
        addPlainValidators(BasicHeader.STANDARD_NUMBER_TYPE, new LengthValidator(50));
        addPlainValidators(BasicHeader.WR_WRK_INST, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(BasicHeader.RH_ACCT_NUMBER, positiveNumberValidator, new LengthValidator(18));
        addPlainValidators(BasicHeader.PUBLISHER, lengthValidator1000);
        addPlainValidators(BasicHeader.PUB_DATE, new DateFormatValidator());
        addPlainValidators(BasicHeader.NUMBER_OF_COPIES, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(BasicHeader.REPORTED_VALUE, requiredValidator, new AmountValidator());
        addPlainValidators(BasicHeader.MARKET, requiredValidator, new LengthValidator(200));
        addPlainValidators(BasicHeader.MARKET_PERIOD_FROM, requiredValidator, new YearValidator());
        addPlainValidators(BasicHeader.MARKET_PERIOD_TO, requiredValidator, new YearValidator());
        addPlainValidators(BasicHeader.AUTHOR, lengthValidator2000);
        addPlainValidators(BasicHeader.COMMENT, new LengthValidator(100));
    }

    /**
     * Headers for common usages.
     */
    private enum BasicHeader implements ICsvColumn {
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

        private final String columnName;

        BasicHeader(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }
    }

    /**
     * Headers for exported usages.
     */
    private enum ExportedHeader implements ICsvColumn {
        DETAIL_ID("Detail ID"),
        STATUS("Detail Status"),
        PRODUCT_FAMILY("Product Family"),
        BATCH_NAME("Usage Batch Name"),
        RRO_ACCOUNT_NUMBER("RRO Account #"),
        RRO_NAME("RRO Name"),
        RH_ACCOUNT_NUMBER("RH Account #"),
        RH_NAME("RH Name"),
        WR_WRK_INST("Wr Wrk Inst"),
        SYSTEM_TITLE("System Title"),
        STANDARD_NUMBER("Standard Number"),
        STANDARD_NUMBER_TYPE("Standard Number Type"),
        FISCAL_YEAR("Fiscal Year"),
        PAYMENT_DATE("Payment Date"),
        WORK_TITLE("Title"),
        ARTICLE("Article"),
        PUBLISHER("Publisher"),
        PUBLICATION_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        REPORTED_VALUE("Reported Value"),
        GROSS_AMOUNT("Gross Amt in USD"),
        BATCH_GROSS_AMOUNT("Batch Amt in USD"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author"),
        COMMENT("Comment");

        private final String columnName;

        ExportedHeader(String columnName) {
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
    private class UsageConverter extends CommonCsvConverter<Usage> {

        private final String productFamily;

        /**
         * Constructor.
         *
         * @param productFamily product family
         */
        UsageConverter(String productFamily) {
            this.productFamily = Objects.requireNonNull(productFamily);
        }

        @Override
        public Usage convert(String... row) {
            List<String> headers = getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setWorkTitle(getString(row, BasicHeader.TITLE, headers));
            result.setArticle(getString(row, BasicHeader.ARTICLE, headers));
            result.setStandardNumber(getString(row, BasicHeader.STANDARD_NUMBER, headers));
            result.setStandardNumberType(
                StringUtils.upperCase(getString(row, BasicHeader.STANDARD_NUMBER_TYPE, headers)));
            result.setWrWrkInst(getLong(row, BasicHeader.WR_WRK_INST, headers));
            result.setSystemTitle(result.getWorkTitle());
            Rightsholder rightsholder = new Rightsholder();
            rightsholder.setAccountNumber(getLong(row, BasicHeader.RH_ACCT_NUMBER, headers));
            result.setRightsholder(rightsholder);
            result.setPublisher(getString(row, BasicHeader.PUBLISHER, headers));
            result.setPublicationDate(getDate(row, BasicHeader.PUB_DATE, headers));
            result.setNumberOfCopies(getInteger(row, BasicHeader.NUMBER_OF_COPIES, headers));
            result.setReportedValue(getBigDecimal(row, BasicHeader.REPORTED_VALUE, headers));
            result.setMarket(getString(row, BasicHeader.MARKET, headers));
            result.setMarketPeriodFrom(getInteger(row, BasicHeader.MARKET_PERIOD_FROM, headers));
            result.setMarketPeriodTo(getInteger(row, BasicHeader.MARKET_PERIOD_TO, headers));
            result.setAuthor(getString(row, BasicHeader.AUTHOR, headers));
            result.setStatus(isEligible(result) ? UsageStatusEnum.ELIGIBLE
                : isWorkFound(result) ? UsageStatusEnum.WORK_FOUND : UsageStatusEnum.NEW);
            result.setProductFamily(productFamily);
            result.setComment(getString(row, BasicHeader.COMMENT, headers));
            FasUsage fasUsage = new FasUsage();
            fasUsage.setReportedStandardNumber(result.getStandardNumber());
            result.setFasUsage(fasUsage);
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
