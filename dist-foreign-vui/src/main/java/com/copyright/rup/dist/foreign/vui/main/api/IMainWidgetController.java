package com.copyright.rup.dist.foreign.vui.main.api;

import com.copyright.rup.dist.foreign.vui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.vui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.vui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.vui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.vui.vaadin.common.widget.api.IController;

/**
 * Interface for controller of {@link IMainWidget}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 01/16/17
 *
 * @author Nikita Levyankov
 */
public interface IMainWidgetController extends IController<IMainWidget> {

    /**
     * Handles global product family selection.
     */
    void onProductFamilyChanged();

    /**
     * Returns instance of {@link IProductFamilyProvider}.
     *
     * @return instance of {@link IProductFamilyProvider}.
     */
    IProductFamilyProvider getProductFamilyProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonUsageController}.
     */
    IControllerProvider<ICommonUsageController> getUsagesControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonScenariosController}.
     */
    IControllerProvider<ICommonScenariosController> getScenariosControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonBatchStatusController}.
     */
    IControllerProvider<ICommonBatchStatusController> getBatchStatusControllerProvider();

    /**
     * Handles scenario creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);
}
