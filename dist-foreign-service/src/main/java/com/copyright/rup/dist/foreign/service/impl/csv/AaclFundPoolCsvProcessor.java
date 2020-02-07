package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AaclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for {@link AaclFundPoolDetail}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolCsvProcessor extends DistCsvProcessor<AaclFundPoolDetail> {

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<AaclFundPoolDetail> getConverter() {
        return new AaclFundPoolDetailConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        addPlainValidators(Header.AGGREGATE_LICENSEE_CLASS_ID, requiredValidator, new PositiveNumberValidator(),
            new LengthValidator(9));
        addPlainValidators(Header.GROSS_AMOUNT, requiredValidator, new AmountValidator());
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {

        AGGREGATE_LICENSEE_CLASS_ID("Aggregate Licensee Class ID"),
        GROSS_AMOUNT("Gross Amount");

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
     * Converts row to {@link AaclFundPoolDetail}.
     * <p/>
     * Copyright (C) 2020 copyright.com
     * <p>
     * Date: 04/02/2020
     *
     * @author Aliaksandr Liakh
     */
    private class AaclFundPoolDetailConverter extends CommonCsvConverter<AaclFundPoolDetail> {

        @Override
        public AaclFundPoolDetail convert(String... row) {
            AaclFundPoolDetail detail = new AaclFundPoolDetail();
            AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
            detail.setAggregateLicenseeClass(aggregateLicenseeClass);
            List<String> headers = getActualHeaders();
            aggregateLicenseeClass.setId(getInteger(row, Header.AGGREGATE_LICENSEE_CLASS_ID, headers));
            detail.setGrossAmount(getBigDecimal(row, Header.GROSS_AMOUNT, headers));
            return detail;
        }
    }
}
