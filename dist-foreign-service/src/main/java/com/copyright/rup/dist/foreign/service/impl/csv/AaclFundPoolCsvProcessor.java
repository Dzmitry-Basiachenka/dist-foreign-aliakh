package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.DuplicateInFileValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for {@link FundPoolDetail}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/02/2020
 *
 * @author Aliaksandr Liakh
 */
public class AaclFundPoolCsvProcessor extends DistCsvProcessor<FundPoolDetail> {

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<FundPoolDetail> getConverter() {
        return new AaclFundPoolDetailConverter();
    }

    @Override
    public void initPlainValidators() {
        var requiredValidator = new RequiredValidator();
        addPlainValidators(Header.AGGREGATE_LICENSEE_CLASS_ID, requiredValidator, new PositiveNumberValidator(),
            new DuplicateInFileValidator(), new LengthValidator(9));
        addPlainValidators(Header.GROSS_AMOUNT, requiredValidator, new AmountValidator());
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {

        AGGREGATE_LICENSEE_CLASS_ID("Agg LC ID"),
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
     * Converts row to {@link FundPoolDetail}.
     * <p/>
     * Copyright (C) 2020 copyright.com
     * <p>
     * Date: 04/02/2020
     *
     * @author Aliaksandr Liakh
     */
    private class AaclFundPoolDetailConverter extends CommonCsvConverter<FundPoolDetail> {

        @Override
        public FundPoolDetail convert(String... row) {
            FundPoolDetail detail = new FundPoolDetail();
            AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
            detail.setAggregateLicenseeClass(aggregateLicenseeClass);
            List<String> headers = getActualHeaders();
            aggregateLicenseeClass.setId(getInteger(row, Header.AGGREGATE_LICENSEE_CLASS_ID, headers));
            detail.setGrossAmount(getBigDecimal(row, Header.GROSS_AMOUNT, headers));
            return detail;
        }
    }
}
