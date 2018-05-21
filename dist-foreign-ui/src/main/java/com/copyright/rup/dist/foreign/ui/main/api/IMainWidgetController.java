package com.copyright.rup.dist.foreign.ui.main.api;

import com.copyright.rup.dist.foreign.ui.audit.api.IAuditController;
import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.dist.foreign.ui.usage.impl.CreateScenarioWindow.ScenarioCreateEvent;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.ITabChangeController;

import com.vaadin.util.ReflectTools;

import java.lang.reflect.Method;

/**
 * Interface for controller of {@link IMainWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IMainWidgetController extends IController<IMainWidget>, ITabChangeController {

    /**
     * {@link #onScenarioCreated(ScenarioCreateEvent)}.
     */
    Method ON_SCENARIO_CREATED =
        ReflectTools.findMethod(IMainWidgetController.class, "onScenarioCreated", ScenarioCreateEvent.class);

    /**
     * @return instance of {@link IUsagesController}.
     */
    IUsagesController getUsagesController();

    /**
     * @return {@link IScenariosController}.
     */
    IScenariosController getScenariosController();

    /**
     * Handles {@link com.copyright.rup.dist.foreign.domain.Scenario} creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);

    /**
     * @return {@link IAuditController}.
     */
    IAuditController getAuditController();
}
