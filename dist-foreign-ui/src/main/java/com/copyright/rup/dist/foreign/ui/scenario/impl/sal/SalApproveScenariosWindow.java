package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;

import java.util.Set;

/**
 * Window to select and approve SAL scenarios.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/13/2023
 *
 * @author Mikita Maistrenka
 */
public class SalApproveScenariosWindow extends SalPerformScenariosActionsCommonWindow {

    private final ISalScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalApproveScenariosWindow(ISalScenariosController controller) {
        super(controller,"window.choose_scenarios_to_approve", "button.approve", ScenarioStatusEnum.SUBMITTED);
        this.controller = controller;
    }

    @Override
    public void performAction(Set<Scenario> selectedScenarios) {
        controller.handleAction(ScenarioActionTypeEnum.APPROVED, selectedScenarios);
    }
}
