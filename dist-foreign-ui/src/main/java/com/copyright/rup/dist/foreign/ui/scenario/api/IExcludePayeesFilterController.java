package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.Map;

/**
 * Exclude Payees filter controller.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/14/19
 *
 * @author Uladzislau Shalamitski
 */
public interface IExcludePayeesFilterController extends IFilterController<IExcludePayeesFilterWidget> {

    /**
     * @return map of participating statuses, where key is textual representation and value is boolean representation
     * of participating status.
     */
    Map<String, Boolean> getParticipatingStatuses();
}
