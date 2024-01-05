package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;

import java.util.Set;

/**
 * Window to select and reject SAL scenarios.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Mikita Maistrenka
 */
public class SalRejectScenariosWindow extends SalPerformScenariosActionsCommonWindow {

    private static final long serialVersionUID = 2383716140133764717L;

    private final ISalScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalRejectScenariosWindow(ISalScenariosController controller) {
        super(controller, "button.reject", ScenarioStatusEnum.SUBMITTED);
        this.controller = controller;
    }

    @Override
    protected void performAction(Set<Scenario> selectedScenarios) {
        controller.handleAction(ScenarioActionTypeEnum.REJECTED, selectedScenarios);
    }
}
