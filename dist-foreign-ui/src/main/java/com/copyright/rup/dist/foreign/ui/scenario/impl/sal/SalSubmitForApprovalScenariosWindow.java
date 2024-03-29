package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;

import java.util.Set;

/**
 * Window to select and submit for approval SAL scenarios.
 * <p>
 * Copyright (C) 2023 copyright.com
 * <p>
 * Date: 02/08/2023
 *
 * @author Mikita Maistrenka
 */
public class SalSubmitForApprovalScenariosWindow extends SalPerformScenariosActionsCommonWindow {

    private static final long serialVersionUID = -2253386354742173877L;

    private final ISalScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalSubmitForApprovalScenariosWindow(ISalScenariosController controller) {
        super(controller, "button.submit", ScenarioStatusEnum.IN_PROGRESS);
        this.controller = controller;
    }

    @Override
    protected void performAction(Set<Scenario> selectedScenarios) {
        controller.handleAction(ScenarioActionTypeEnum.SUBMITTED, selectedScenarios);
    }
}
