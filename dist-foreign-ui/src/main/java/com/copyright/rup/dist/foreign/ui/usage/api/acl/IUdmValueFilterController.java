package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.dist.foreign.domain.Currency;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

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
     * Gets list of assignees from UDM values.
     *
     * @return list of assignees
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
     * Gets list of all currencies.
     *
     * @return list of {@link Currency}
     */
    List<Currency> getAllCurrencies();
}
