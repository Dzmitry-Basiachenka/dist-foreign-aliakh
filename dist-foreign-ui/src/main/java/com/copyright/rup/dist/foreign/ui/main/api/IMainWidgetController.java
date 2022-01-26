package com.copyright.rup.dist.foreign.ui.main.api;

import com.copyright.rup.dist.foreign.ui.audit.api.ICommonAuditController;
import com.copyright.rup.dist.foreign.ui.report.api.udm.IUdmReportController;
import com.copyright.rup.dist.foreign.ui.scenario.api.ICommonScenariosController;
import com.copyright.rup.dist.foreign.ui.status.api.ICommonBatchStatusController;
import com.copyright.rup.dist.foreign.ui.usage.api.ICommonUsageController;
import com.copyright.rup.dist.foreign.ui.usage.api.ScenarioCreateEvent;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IAclCalculationController;
import com.copyright.rup.dist.foreign.ui.usage.api.acl.IUdmController;
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
     * @return instance of {@link IControllerProvider} for {@link IUdmController}.
     */
    IControllerProvider<IUdmController> getUdmControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link IAclCalculationController}.
     */
    IControllerProvider<IAclCalculationController> getAclCalculationControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonUsageController}.
     */
    IControllerProvider<ICommonUsageController> getUsagesControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonScenariosController}.
     */
    IControllerProvider<ICommonScenariosController> getScenariosControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonAuditController}.
     */
    IControllerProvider<ICommonAuditController> getAuditControllerProvider();

    /**
     * @return instance of {@link IControllerProvider} for {@link ICommonAuditController}.
     */
    IControllerProvider<ICommonBatchStatusController> getBatchStatusControllerProvider();

    /**
     * @return instance of {@link IUdmReportController}.
     */
    IUdmReportController getUdmReportController();

    /**
     * Handles {@link com.copyright.rup.dist.foreign.domain.Scenario} creation.
     *
     * @param event {@link ScenarioCreateEvent}
     */
    void onScenarioCreated(ScenarioCreateEvent event);

    /**
     * Handles global product family selection.
     */
    void onProductFamilyChanged();
}
