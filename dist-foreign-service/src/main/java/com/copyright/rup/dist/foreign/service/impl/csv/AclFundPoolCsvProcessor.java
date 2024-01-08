package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.AmountValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.service.impl.csv.validator.GrantTypeOfUseValidator;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for {@link AclFundPoolDetail}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/26/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolCsvProcessor extends DistCsvProcessor<AclFundPoolDetail> {

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<AclFundPoolDetail> getConverter() {
        return new AaclFundPoolDetailConverter();
    }

    @Override
    public void initPlainValidators() {
        var requiredValidator = new RequiredValidator();
        addPlainValidators(Header.DET_LIC_CLASS_ID, requiredValidator, new PositiveNumberValidator(),
            new LengthValidator(9));
        addPlainValidators(Header.FUND_POOL_TYPE, requiredValidator, new LengthValidator(7),
            new GrantTypeOfUseValidator());
        addPlainValidators(Header.NET_AMOUNT, requiredValidator, new AmountValidator(false));
        addPlainValidators(Header.GROSS_AMOUNT, requiredValidator, new AmountValidator(false));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {
        DET_LIC_CLASS_ID("DET_LC_ID"),
        FUND_POOL_TYPE("FUND_POOL_TYPE"),
        NET_AMOUNT("NET_AMOUNT"),
        GROSS_AMOUNT("GROSS_AMOUNT");

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
     * Converts row to {@link AclFundPoolDetail}.
     * <p/>
     * Copyright (C) 2022 copyright.com
     * <p>
     * Date: 03/30/2022
     *
     * @author Ihar Suvorau
     */
    private class AaclFundPoolDetailConverter extends CommonCsvConverter<AclFundPoolDetail> {

        @Override
        public AclFundPoolDetail convert(String... row) {
            AclFundPoolDetail detail = new AclFundPoolDetail();
            List<String> headers = getActualHeaders();
            detail.setId(RupPersistUtils.generateUuid());
            DetailLicenseeClass detailLicenseeClass = new DetailLicenseeClass();
            detailLicenseeClass.setId(getInteger(row, Header.DET_LIC_CLASS_ID, headers));
            detail.setDetailLicenseeClass(detailLicenseeClass);
            detail.setTypeOfUse(StringUtils.upperCase(getString(row, Header.FUND_POOL_TYPE, headers)));
            detail.setNetAmount(getBigDecimal(row, Header.NET_AMOUNT, headers));
            detail.setGrossAmount(getBigDecimal(row, Header.GROSS_AMOUNT, headers));
            detail.setLdmtFlag(false);
            return detail;
        }
    }
}
