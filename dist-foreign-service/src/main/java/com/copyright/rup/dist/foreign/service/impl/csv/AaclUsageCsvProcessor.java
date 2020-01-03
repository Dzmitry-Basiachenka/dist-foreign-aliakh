package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for AACL {@link Usage}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/26/2019
 *
 * @author Ihar Suvorau
 */
public class AaclUsageCsvProcessor extends DistCsvProcessor<Usage> {

    /**
     * Constructor.
     */
    AaclUsageCsvProcessor() {
        super();
    }

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public IConverter<Usage> getConverter() {
        return new AaclUsageConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.INSTITUTION, new LengthValidator(255));
        addPlainValidators(Header.USAGE_SOURCE, requiredValidator, new LengthValidator(150));
        addPlainValidators(Header.NUMBER_OF_COPIES, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.NUMBER_OF_PAGES, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.COMMENT, new LengthValidator(100));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        WR_WRK_INST("Wr Wrk Inst"),
        INSTITUTION("Institution"),
        USAGE_SOURCE("Usage Source"),
        NUMBER_OF_COPIES("Number of Copies"),
        NUMBER_OF_PAGES("Number of Pages"),
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
     * Converts row to AACL usage detail.
     * <p/>
     * Copyright (C) 2019 copyright.com
     * <p/>
     * Date: 12/26/2019
     *
     * @author Ihar Suvorau
     */
    //TODO introduce common converter
    private class AaclUsageConverter implements IConverter<Usage> {

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

        private boolean isPositiveNumber(String value) {
            return null != value && value.matches("[1-9]\\d*(\\.\\d*[eE][+]\\d+)?");
        }

        private String parseScientific(String value) {
            return null != value ? new BigDecimal(value).toPlainString() : null;
        }

        @Override
        public Usage convert(String... row) {
            List<String> headers = getActualHeaders();
            Usage result = new Usage();
            result.setId(RupPersistUtils.generateUuid());
            result.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            result.setStatus(UsageStatusEnum.NEW);
            result.setProductFamily(FdaConstants.AACL_PRODUCT_FAMILY);
            result.setNumberOfCopies(getInteger(row, Header.NUMBER_OF_COPIES, headers));
            result.setComment(getString(row, Header.COMMENT, headers));
            AaclUsage aaclUsage = new AaclUsage();
            aaclUsage.setInstitution(getString(row, Header.INSTITUTION, headers));
            aaclUsage.setUsageSource(getString(row, Header.USAGE_SOURCE, headers));
            aaclUsage.setNumberOfPages(getInteger(row, Header.NUMBER_OF_PAGES, headers));
            result.setAaclUsage(aaclUsage);
            return result;
        }
    }
}
