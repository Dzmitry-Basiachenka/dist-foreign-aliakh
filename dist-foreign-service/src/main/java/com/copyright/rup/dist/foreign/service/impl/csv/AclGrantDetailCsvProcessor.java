package com.copyright.rup.dist.foreign.service.impl.csv;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.api.csv.ICsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.CommonCsvConverter;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.common.service.impl.csv.validator.LengthValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.PositiveNumberValidator;
import com.copyright.rup.dist.common.service.impl.csv.validator.RequiredValidator;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Processor for {@link AclGrantDetailDto}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/30/2022
 *
 * @author Ihar Suvorau
 */
public class AclGrantDetailCsvProcessor extends DistCsvProcessor<AclGrantDetailDto> {

    @Override
    public List<String> getHeadersForValidation() {
        return Stream.of(Header.values()).map(Header::getColumnName).collect(Collectors.toList());
    }

    @Override
    public ICsvConverter<AclGrantDetailDto> getConverter() {
        return new AaclFundPoolDetailConverter();
    }

    @Override
    public void initPlainValidators() {
        RequiredValidator requiredValidator = new RequiredValidator();
        PositiveNumberValidator positiveNumberValidator = new PositiveNumberValidator();
        addPlainValidators(Header.WR_WRK_INST, requiredValidator, positiveNumberValidator, new LengthValidator(9));
        addPlainValidators(Header.TYPE_OF_USE, requiredValidator, new LengthValidator(7));
        addPlainValidators(Header.RH_ACCOUNT_NUMBER, requiredValidator, positiveNumberValidator,
            new LengthValidator(18));
    }

    /**
     * CSV file headers.
     */
    private enum Header implements ICsvColumn {

        WR_WRK_INST("WR_WRK_INST"),
        TYPE_OF_USE("TYPE_OF_USE"),
        RH_ACCOUNT_NUMBER("RH_ACCOUNT_NUMBER");

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
     * Converts row to {@link AclGrantDetailDto}.
     * <p/>
     * Copyright (C) 2022 copyright.com
     * <p>
     * Date: 03/30/2022
     *
     * @author Ihar Suvorau
     */
    private class AaclFundPoolDetailConverter extends CommonCsvConverter<AclGrantDetailDto> {

        @Override
        public AclGrantDetailDto convert(String... row) {
            AclGrantDetailDto detail = new AclGrantDetailDto();
            List<String> headers = getActualHeaders();
            detail.setId(RupPersistUtils.generateUuid());
            detail.setWrWrkInst(getLong(row, Header.WR_WRK_INST, headers));
            detail.setTypeOfUse(StringUtils.upperCase(getString(row, Header.TYPE_OF_USE, headers)));
            detail.setRhAccountNumber(getLong(row, Header.RH_ACCOUNT_NUMBER, headers));
            detail.setGrantStatus("GRANT");
            detail.setEligible(true);
            detail.setManualUploadFlag(true);
            return detail;
        }
    }
}
