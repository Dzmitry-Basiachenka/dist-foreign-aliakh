package com.copyright.rup.dist.foreign.service.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantDetail;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * @param grantSet                instance of {@link AclGrantSet}
     * @param wrWrkInstToSystemTitles map of wrWrkInsts to system titles
     * @param userName                name of user
     * @return list of {@link AclGrantDetail}
     */
    List<AclGrantDetail> createAclGrantDetails(AclGrantSet grantSet, Map<Long, String> wrWrkInstToSystemTitles,
                                               String userName);

    /**
     * Sets eligible flag to grant based on ineligible rightsholder on PRM.
     * Sets <code>ineligible</code> if ineligible rightsholder and grant have similar account numbers and types of use.
     *
     * @param grantDetailDtos list of {@link AclGrantDetail}
     * @param date            period end date
     * @param licenseType     license type
     */
    void setEligibleFlag(List<AclGrantDetail> grantDetailDtos, LocalDate date, String licenseType);
}
