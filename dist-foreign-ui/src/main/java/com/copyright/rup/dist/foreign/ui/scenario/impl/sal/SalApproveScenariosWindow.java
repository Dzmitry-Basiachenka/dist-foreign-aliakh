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

    private static final long serialVersionUID = -5952690384621715413L;

    private final ISalScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalApproveScenariosWindow(ISalScenariosController controller) {
        super(controller, "button.approve", ScenarioStatusEnum.SUBMITTED);
        this.controller = controller;
    }

    @Override
    public void performAction(Set<Scenario> selectedScenarios) {
        controller.handleAction(ScenarioActionTypeEnum.APPROVED, selectedScenarios);
    }
}
