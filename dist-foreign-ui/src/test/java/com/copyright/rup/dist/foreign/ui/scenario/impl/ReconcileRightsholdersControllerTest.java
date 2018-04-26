package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.repository.api.Pageable;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IReconcileRightsholdersController;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link ReconcileRightsholdersController}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 1/30/18
 *
 * @author Ihar Suvorau
 */
public class ReconcileRightsholdersControllerTest {

    private IReconcileRightsholdersController controller;
    private IScenarioService scenarioService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;
    private Scenario scenario;

    @Before
    public void setUp() {
        controller = new ReconcileRightsholdersController();
        scenario = new Scenario();
        scenario.setId(RupPersistUtils.generateUuid());
        controller.setScenario(scenario);
        scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        Whitebox.setInternalState(controller, rightsholderDiscrepancyService);
    }

    @Test
    public void testApproveReconciliation() {
        controller.setScenario(scenario);
        scenarioService.approveOwnershipChanges(scenario);
        expectLastCall().once();
        replay(scenarioService);
        controller.approveReconciliation();
        verify(scenarioService);
    }

    @Test
    public void testGetSize() {
        expect(rightsholderDiscrepancyService.getDiscrepanciesCountByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS)).andReturn(5).once();
        replay(rightsholderDiscrepancyService);
        assertEquals(5, controller.getSize(), 0);
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testGetProhibitedAccountNumbers() {
        List<Long> accountNumbers = Arrays.asList(1000000001L, 1000000002L);
        expect(rightsholderDiscrepancyService.getProhibitedAccountNumbers(scenario.getId()))
            .andReturn(accountNumbers).once();
        replay(rightsholderDiscrepancyService);
        assertSame(accountNumbers, controller.getProhibitedAccountNumbers());
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testLoadBeans() {
        Capture<Pageable> pageableCapture = new Capture<>();
        List<RightsholderDiscrepancy> discrepancies = Collections.singletonList(new RightsholderDiscrepancy());
        expect(rightsholderDiscrepancyService.getDiscrepanciesByScenarioIdAndStatus(same(scenario.getId()),
            same(RightsholderDiscrepancyStatusEnum.IN_PROGRESS), capture(pageableCapture), isNull()))
            .andReturn(discrepancies).once();
        replay(rightsholderDiscrepancyService);
        assertSame(discrepancies, controller.loadBeans(0, 10, null));
        Pageable pageable = pageableCapture.getValue();
        assertEquals(0, pageable.getOffset());
        assertEquals(10, pageable.getLimit());
        verify(rightsholderDiscrepancyService);
    }

    @Test
    public void testDeleteInProgressDiscrepancies() {
        rightsholderDiscrepancyService.deleteDiscrepanciesByScenarioIdAndStatus(scenario.getId(),
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS);
        expectLastCall().once();
        replay(rightsholderDiscrepancyService);
        controller.deleteInProgressDiscrepancies();
        verify(rightsholderDiscrepancyService);
    }
}
