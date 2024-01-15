package com.copyright.rup.dist.foreign.vui.scenario.api.fas;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IFilterController;

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
public interface IFasExcludePayeeFilterController extends IFilterController<IFasExcludePayeeFilterWidget> {

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
