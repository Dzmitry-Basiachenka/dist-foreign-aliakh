package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * Processor for {@link ResearchedUsage}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/27/2018
 *
 * @author Aliaksandr Liakh
 */
public class ResearchedUsagesCsvProcessor extends DistCsvProcessor<ResearchedUsage> {

    /**
     * Constructor.
     */
    ResearchedUsagesCsvProcessor() {
        super(new ResearchedUsageConverter(), true, getColumns());
    }

    /**
     * @return array of expected columns in CSV file.
     */
    static String[] getColumns() {
        return Stream.of(Header.values())
            .map(Header::getColumnName)
            .toArray(String[]::new);
    }

    @Override
    protected void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.DETAIL_ID, requiredValidator, new LengthValidator(36),
            new DuplicateInFileValidator());
        addPlainValidators(Header.STANDARD_NUMBER, requiredValidator, new LengthValidator(1000));
        addPlainValidators(Header.SYSTEM_TITLE, requiredValidator, new LengthValidator(2000));
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {

        DETAIL_ID("Detail ID"),
        STATUS("Detail Status"),
        PRODUCT_FAMILY("Product Family"),
        BATCH_NAME("Usage Batch Name"),
        FISCAL_YEAR("Fiscal Year"),
        RRO_ACCOUNT_NUMBER("RRO Account #"),
        RRO_NAME("RRO Name"),
        PAYMENT_DATE("Payment Date"),
        WORK_TITLE("Title"),
        ARTICLE("Article"),
        STANDARD_NUMBER("Standard Number"),
        STANDARD_NUMBER_TYPE("Standard Number Type"),
        WR_WRK_INST("Wr Wrk Inst"),
        SYSTEM_TITLE("System Title"),
        RH_ACCOUNT_NUMBER("RH Account #"),
        RH_NAME("RH Name"),
        PUBLISHER("Publisher"),
        PUBLICATION_DATE("Pub Date"),
        NUMBER_OF_COPIES("Number of Copies"),
        REPORTED_VALUE("Reported value"),
        GROSS_AMOUNT("Amt in USD"),
        BATCH_GROSS_AMOUNT("Gross Amt in USD"),
        MARKET("Market"),
        MARKET_PERIOD_FROM("Market Period From"),
        MARKET_PERIOD_TO("Market Period To"),
        AUTHOR("Author"),
        COMMENT("Comment");

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
     * Converts row to researched usage detail.
     * <p/>
     * Copyright (C) 2018 copyright.com
     * <p/>
     * Date: 03/27/2018
     *
     * @author Aliaksandr Liakh
     */
    private static class ResearchedUsageConverter implements IConverter<ResearchedUsage> {

        private static String getValue(String[] row, ICsvColumn header) {
            return StringUtils.defaultIfBlank(row[header.ordinal()], null);
        }

        private static String getString(String[] row, ICsvColumn column) {
            return getValue(row, column);
        }

        private static Long getLong(String[] row, ICsvColumn column) {
            String value = getValue(row, column);
            return null != value ? Long.valueOf(parseScientific(value)) : null;
        }

        private static String parseScientific(String value) {
            return null != value ? new BigDecimal(value).toPlainString() : null;
        }

        @Override
        public ResearchedUsage convert(String... row) {
            ResearchedUsage researchedUsage = new ResearchedUsage();
            researchedUsage.setUsageId(getString(row, Header.DETAIL_ID));
            researchedUsage.setStandardNumber(getString(row, Header.STANDARD_NUMBER));
            researchedUsage.setSystemTitle(getString(row, Header.SYSTEM_TITLE));
            researchedUsage.setWrWrkInst(getLong(row, Header.WR_WRK_INST));
            return researchedUsage;
        }
    }
}
