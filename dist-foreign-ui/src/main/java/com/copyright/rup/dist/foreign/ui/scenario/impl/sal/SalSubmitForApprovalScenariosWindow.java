package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
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

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalSubmitForApprovalScenariosWindow(ISalScenariosController controller) {
        super(controller,"window.choose_scenarios_to_submit_for_approval", "button.submit",
            ScenarioStatusEnum.IN_PROGRESS);
    }

    @Override
    protected void performAction(Set<Scenario> selectedScenarios) {
        //TODO will be implemented with backend logic
    }
}
