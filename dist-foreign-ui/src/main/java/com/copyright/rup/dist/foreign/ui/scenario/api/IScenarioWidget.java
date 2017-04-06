package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.ui.scenario.impl.ScenarioController;
import com.copyright.rup.vaadin.widget.api.IWidget;

/**
 * Interface for viewing scenario widget.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/31/17
 *
 * @author Ihar Suvorau
 */
public interface IScenarioWidget extends IWidget<ScenarioController> {

    /**
     * @return value from search field.
     */
    String getSearchValue();

    /**
     * Applies search value for table container.
     */
    void applySearch();
}
