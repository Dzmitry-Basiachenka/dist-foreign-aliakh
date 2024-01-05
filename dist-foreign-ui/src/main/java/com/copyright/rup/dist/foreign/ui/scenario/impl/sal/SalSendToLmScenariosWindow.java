package com.copyright.rup.dist.foreign.ui.scenario.impl.sal;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.ui.scenario.api.sal.ISalScenariosController;

import java.util.Set;

/**
 * Window to select and send scenarios to LM.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 11/04/2020
 *
 * @author Uladzislau Shalamitski
 */
public class SalSendToLmScenariosWindow extends SalPerformScenariosActionsCommonWindow {

    private static final long serialVersionUID = -4130051824872776380L;

    private final ISalScenariosController controller;

    /**
     * Constructor.
     *
     * @param controller instance of {@link ISalScenariosController}
     */
    public SalSendToLmScenariosWindow(ISalScenariosController controller) {
        super(controller, "button.send_to_lm", ScenarioStatusEnum.APPROVED);
        this.controller = controller;
    }

    @Override
    protected void performAction(Set<Scenario> selectedScenarios) {
        controller.sendToLm(selectedScenarios);
    }
}
