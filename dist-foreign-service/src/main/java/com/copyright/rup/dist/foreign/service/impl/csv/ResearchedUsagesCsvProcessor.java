package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;

import java.util.List;
import java.util.stream.Collectors;
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
        super();
    }

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<ResearchedUsage> getConverter() {
        return new ResearchedUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        var requiredValidator = new RequiredValidator();
        var positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.DETAIL_ID, requiredValidator, new LengthValidator(36),
            new DuplicateInFileValidator());
        addPlainValidators(Header.STANDARD_NUMBER, new LengthValidator(1000));
        addPlainValidators(Header.STANDARD_NUMBER_TYPE, new LengthValidator(50));
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
        RRO_ACCOUNT_NUMBER("RRO Account #"),
        RRO_NAME("RRO Name"),
        RH_ACCOUNT_NUMBER("RH Account #"),
        RH_NAME("RH Name"),
        WR_WRK_INST("Wr Wrk Inst"),
        SYSTEM_TITLE("System Title"),
        REPORTED_STANDARD_NUMBER("Reported Standard Number"),
        STANDARD_NUMBER("Standard Number"),
        STANDARD_NUMBER_TYPE("Standard Number Type"),
        FISCAL_YEAR("Fiscal Year"),
        PAYMENT_DATE("Payment Date"),
        WORK_TITLE("Reported Title"),
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
    private class ResearchedUsageConverter extends CommonCsvConverter<ResearchedUsage> {

        @Override
        public ResearchedUsage convert(String... row) {
            List<String> headers = getActualHeaders();
            ResearchedUsage researchedUsage = new ResearchedUsage();
            researchedUsage.setUsageId(getString(row, Header.DETAIL_ID, headers));
            researchedUsage.setStandardNumber(getString(row, Header.STANDARD_NUMBER, headers));
            researchedUsage.setStandardNumberType(getString(row, Header.STANDARD_NUMBER_TYPE, headers));
            researchedUsage.setSystemTitle(getString(row, Header.SYSTEM_TITLE, headers));
            researchedUsage.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            return researchedUsage;
        }
    }
}
