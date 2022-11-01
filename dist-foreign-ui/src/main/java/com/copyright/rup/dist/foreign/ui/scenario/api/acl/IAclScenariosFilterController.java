package com.copyright.rup.dist.foreign.ui.scenario.api.acl;

import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;

/**
 * Interface for controller for ACL scenarios filtering.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public interface IAclScenariosFilterController extends IFilterController<IAclScenariosFilterWidget> {

    /**
     * Gets list of ACL scenario periods.
     *
     * @return list of ACL scenario periods
     */
    List<Integer> getPeriods();
}
