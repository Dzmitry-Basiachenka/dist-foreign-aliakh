package com.copyright.rup.dist.foreign.ui.scenario.api;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.vaadin.widget.api.IController;

import java.util.List;

/**
 * Scenarios controller interface.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/14/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
public interface IScenariosController extends IController<IScenariosWidget> {

    /**
     * @return list of {@link Scenario}s.
     */
    List<Scenario> getScenarios();

    /**
     * Handles click on 'Delete' button.
     */
    void onDeleteButtonClicked();
}
