package com.copyright.rup.dist.foreign.ui.usage.api.acl;

import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for UDM proxy value filtering.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 11/23/2021
 *
 * @author Uladzislau Shalamitski
 */
public interface IUdmProxyValueFilterController extends IFilterController<IUdmProxyValueFilterWidget> {

    /**
     * Gets all available proxy value periods.
     *
     * @return list of proxy value periods
     */
    List<Integer> getPeriods();

    /**
     * Gets list of all pub type codes.
     *
     * @return list of pub type codes
     */
    List<String> getPublicationTypeCodes();
}
