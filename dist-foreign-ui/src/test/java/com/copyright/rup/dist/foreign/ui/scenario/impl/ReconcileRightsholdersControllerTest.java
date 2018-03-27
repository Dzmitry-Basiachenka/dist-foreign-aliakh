package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.vaadin.ui.component.window.Windows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.Set;

/**
 * Verifies {@link ReconcileRightsholdersController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/30/18
 *
 * @author Ihar Suvorau
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class})
public class ReconcileRightsholdersControllerTest {

    private final ReconcileRightsholdersController controller = new ReconcileRightsholdersController();

    @Test
    public void testGetSetRightsholderDiscrepancies() {
        assertNull(controller.getDiscrepancies());
        Set<RightsholderDiscrepancy> discrepancies =
            Collections.singleton(buildRightsholderDiscrepancy(100009522L, 1000000001L));
        controller.setDiscrepancies(discrepancies);
        assertSame(discrepancies, controller.getDiscrepancies());
    }

    @Test
    public void testApproveReconciliation() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, "scenarioService", scenarioService);
        Set<RightsholderDiscrepancy> discrepancies =
            Collections.singleton(buildRightsholderDiscrepancy(100009522L, 1000000001L));
        controller.setDiscrepancies(discrepancies);
        Scenario scenario = new Scenario();
        controller.setScenario(scenario);
        scenarioService.approveOwnershipChanges(scenario, discrepancies);
        expectLastCall().once();
        replay(scenarioService);
        controller.approveReconciliation();
        verify(scenarioService);
    }

    private RightsholderDiscrepancy buildRightsholderDiscrepancy(Long oldAccountNumber, Long newAccountNumber) {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setWrWrkInst(1L);
        rightsholderDiscrepancy.setOldRightsholder(buildRightsholder(oldAccountNumber));
        rightsholderDiscrepancy.setNewRightsholder(buildRightsholder(newAccountNumber));
        return rightsholderDiscrepancy;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
