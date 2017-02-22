package com.copyright.rup.dist.foreign.integration.prm.api;

import com.copyright.rup.dist.common.domain.Rightsholder;

import java.util.Collection;
import java.util.Set;

/**
 * Interface for service to provide access for PRM information.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 02/21/2017
 *
 * @author Mikalai Bezmen
 */
public interface IPrmRightsholderService {

    /**
     * Retrieves collection of rightsholders by specified set of account numbers from PRM.
     *
     * @param accountNumbers the set of account numbers
     * @return the collection of rightsholders
     */
    Collection<Rightsholder> getRightsholders(Set<Long> accountNumbers);
}
