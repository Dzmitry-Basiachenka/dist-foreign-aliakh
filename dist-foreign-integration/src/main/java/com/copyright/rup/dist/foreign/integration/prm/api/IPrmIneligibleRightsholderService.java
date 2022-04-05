package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.foreign.domain.AclIneligibleRightsholder;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface for service to get ineligible rightsholders.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/04/2022
 *
 * @author Anton Azarenka
 */
public interface IPrmIneligibleRightsholderService {

    /**
     * Gets set of {@link AclIneligibleRightsholder}s.
     *
     * @param periodEndDate period end date
     * @param licenseType   licenseType
     * @return set of {@link AclIneligibleRightsholder}s
     */
    Set<AclIneligibleRightsholder> getIneligibleRightsholders(LocalDate periodEndDate, String licenseType);
}
