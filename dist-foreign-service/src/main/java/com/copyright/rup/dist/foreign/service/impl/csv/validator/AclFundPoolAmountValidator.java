package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;

/**
 * The validator to check whether Gross Amount greater or equal to the Net Amount.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/28/2022
 *
 * @author Anton Azarenka
 */
public class AclFundPoolAmountValidator implements DistCsvProcessor.IValidator<AclFundPoolDetail> {

    @Override
    public boolean isValid(AclFundPoolDetail fundPoolDetail) {
        return fundPoolDetail.getGrossAmount().compareTo(fundPoolDetail.getNetAmount()) >= 0;
    }

    @Override
    public String getErrorMessage() {
        return "Gross Amount should be greater or equal to the Net Amount";
    }
}
