package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for ACL grant detail filtering.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 01/27/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclGrantDetailFilterController extends IFilterController<IAclGrantDetailFilterWidget> {

    /**
     * Gets list of all ACL grant sets.
     *
     * @return list of all {@link AclGrantSet}s
     */
    List<AclGrantSet> getAllAclGrantSets();

    /**
     * Gets list of grant periods.
     *
     * @return list of grant periods
     */
    List<Integer> getGrantPeriods();
}
