package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;
import java.util.Map;

/**
 * Interface for controller for UDM value filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 09/01/2021
 *
 * @author Aliaksandr Liakh
 */
public interface IUdmValueFilterController extends IFilterController<IUdmValueFilterWidget> {

    /**
     * @return list of assignees.
     */
    List<String> getAssignees();

    /**
     * @return list of last value periods.
     */
    List<String> getLastValuePeriods();

    /**
     * Gets all available periods for value.
     *
     * @return list of periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of all {@link PublicationType}s.
     *
     * @return list of {@link PublicationType}
     */
    List<PublicationType> getPublicationTypes();

    /**
     * Gets map of currency codes to currency names.
     *
     * @return map of currency codes to currency names
     */
    Map<String, String> getCurrencyCodesToCurrencyNamesMap();
}
