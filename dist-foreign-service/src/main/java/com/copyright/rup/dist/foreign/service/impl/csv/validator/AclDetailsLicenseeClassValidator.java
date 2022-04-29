package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclFundPoolDetail;
import com.copyright.rup.dist.foreign.service.api.ILicenseeClassService;

/**
 * The validator for ACL fund pool details to check if licensee class id exists in df_detail_licensee_class table.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/28/2022
 *
 * @author Anton Azarenka
 */
public class AclDetailsLicenseeClassValidator implements DistCsvProcessor.IValidator<AclFundPoolDetail> {

    private final ILicenseeClassService licenseeClassService;

    /**
     * Constructor.
     *
     * @param licenseeClassService instance of {@link ILicenseeClassService}
     */
    public AclDetailsLicenseeClassValidator(ILicenseeClassService licenseeClassService) {
        this.licenseeClassService = licenseeClassService;
    }

    @Override
    public boolean isValid(AclFundPoolDetail fundPoolDetail) {
        return licenseeClassService.detailLicenseeClassExists(fundPoolDetail.getDetailLicenseeClass().getId());
    }

    @Override
    public String getErrorMessage() {
        return "Detail License Class Id doesn't exist in the system";
    }
}
