package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for ACL fund pool detail filtering.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 04/17/2022
 *
 * @author Anton Azarenka
 */
public interface IAclFundPoolFilterController extends IFilterController<IAclFundPoolFilterWidget> {

    /**
     * Gets all available fund pool names.
     *
     * @return list of fund pool names
     */
    List<AclFundPool> getFundPools();

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
}
