package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for ACL usage filtering.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 03/31/2022
 *
 * @author Dzmitry Basiachenka
 */
public interface IAclUsageFilterController extends IFilterController<IAclUsageFilterWidget> {

    /**
     * Gets list of all ACL usage batches.
     *
     * @return list of all {@link AclUsageBatch}s
     */
    List<AclUsageBatch> getAllAclUsageBatches();
}
