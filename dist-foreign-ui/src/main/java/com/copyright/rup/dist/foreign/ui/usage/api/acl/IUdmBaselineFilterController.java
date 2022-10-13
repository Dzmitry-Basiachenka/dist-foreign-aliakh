package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM baseline filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmBaselineFilterController extends IFilterController<IUdmBaselineFilterWidget> {

    /**
     * Gets all available periods.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * @return list of type of uses.
     */
    List<String> getTypeOfUses(); // TODO {aliakh} rename to getReportedTypeOfUses

    /**
     * @return list of detail licensee classes.
     */
    List<DetailLicenseeClass> getDetailLicenseeClasses();

    /**
     * @return list of aggregate licensee classes.
     */
    List<AggregateLicenseeClass> getAggregateLicenseeClasses();
}
