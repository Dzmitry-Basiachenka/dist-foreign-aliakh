package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;

import java.util.List;

/**
 * Represents interface of service for grant set business logic.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/26/2022
 *
 * @author Anton Azarenka
 */
public interface IAclGrantService {

    /**
     * Creates grant details and populates fields based on information from RMS.
     *
     * @param grantSet   instance of {@link AclGrantSet}
     * @param wrWrkInsts list of wrWrkInst
     * @return list of {@link AclGrantDetail}
     */
    List<AclGrantDetail> createAclGrantDetails(AclGrantSet grantSet, List<Long> wrWrkInsts);
}
