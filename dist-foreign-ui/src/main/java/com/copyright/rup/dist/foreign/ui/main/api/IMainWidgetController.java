package com.copyright.rup.dist.foreign.ui.main.api;

import com.copyright.rup.dist.foreign.ui.scenario.api.IScenariosController;
import com.copyright.rup.dist.foreign.ui.usage.api.IUsagesController;
import com.copyright.rup.vaadin.widget.api.IController;
import com.copyright.rup.vaadin.widget.api.ITabChangeController;

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
     * @return instance of {@link IUsagesController}.
     */
    IUsagesController getUsagesController();

    /**
     * @return {@link IScenariosController}.
     */
    IScenariosController getScenariosController();
}
