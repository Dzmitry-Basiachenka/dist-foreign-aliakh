package com.copyright.rup.dist.foreign.service.impl.csv.validator;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor;
import com.copyright.rup.dist.foreign.domain.AclGrantDetailDto;
import com.copyright.rup.dist.foreign.service.api.acl.IAclGrantDetailService;

/**
 * Validates that Grant with provided Wr Wrk Inst and Type of Use exists in the system.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Ihar Suvorau
 */
public class GrantDetailDuplicateValidator implements DistCsvProcessor.IValidator<AclGrantDetailDto> {

    private final IAclGrantDetailService grantDetailService;
    private final String grantSetId;

    /**
     * Constructor.
     *
     * @param grantDetailService instance of {@link IAclGrantDetailService}
     * @param grantSetId identifier of Grant Set
     */
    public GrantDetailDuplicateValidator(IAclGrantDetailService grantDetailService, String grantSetId) {
        this.grantDetailService = grantDetailService;
        this.grantSetId = grantSetId;
    }

    @Override
    public boolean isValid(AclGrantDetailDto grantDetail) {
        return !grantDetailService.isGrantDetailExist(grantSetId, grantDetail.getWrWrkInst(),
            grantDetail.getTypeOfUse());
    }

    @Override
    public String getErrorMessage() {
        return "Grant with Wr Wrk Inst and Type of Use already present in grant set";
    }
}
