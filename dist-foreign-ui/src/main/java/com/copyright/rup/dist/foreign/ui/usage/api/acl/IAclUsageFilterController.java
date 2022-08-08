package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
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
     * @return list of all {@link AclUsageBatch}es
     */
    List<AclUsageBatch> getAllAclUsageBatches();

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of all {@link DetailLicenseeClass}es.
     *
     * @return list of detail licensee classes.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * Gets list of all {@link AggregateLicenseeClass}es.
     *
     * @return list of aggregate licensee classes.
     */
    List<AggregateLicenseeClass> getAggregateLicenseeClasses();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes();
}
