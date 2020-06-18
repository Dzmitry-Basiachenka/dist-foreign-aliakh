package com.copyright.rup.dist.foreign.ui.scenario.api.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IFilterController;

import java.util.List;
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
public interface IExcludePayeeFilterController extends IFilterController<IExcludePayeeFilterWidget> {

    /**
     * @return map of participating statuses, where key is textual representation and value is boolean representation
     * of participating status.
     */
    Map<String, Boolean> getParticipatingStatuses();

    /**
     * @return list of scenarios
     */
    List<Scenario> getScenarios();
}
