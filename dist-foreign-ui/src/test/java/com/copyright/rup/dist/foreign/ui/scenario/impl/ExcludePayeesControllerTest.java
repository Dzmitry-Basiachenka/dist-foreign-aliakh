package com.copyright.rup.dist.foreign.ui.scenario.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.PayeeTotalHolder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.ui.scenario.api.IExcludePayeesController;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ExcludePayeesController}.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/17/19
 *
 * @author Uladzislau Shalamitski
 */
public class ExcludePayeesControllerTest {

    private static final String REASON = "reason";
    private IExcludePayeesController controller;
    private IUsageService usageService;
    private Scenario scenario;

    @Before
    public void setUp() {
        usageService = createMock(IUsageService.class);
        controller = new ExcludePayeesController();
        scenario = buildScenario();
        Whitebox.setInternalState(controller, usageService);
        Whitebox.setInternalState(controller, scenario);
    }

    @Test
    public void testRedesignateDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        usageService.redesignateByPayees(scenario, accountNumbers, REASON);
        expectLastCall().once();
        replay(usageService);
        controller.redesignateDetails(accountNumbers, REASON);
        verify(usageService);
    }

    @Test
    public void testExcludeDetails() {
        Set<Long> accountNumbers = Collections.singleton(2000017566L);
        usageService.deleteFromScenarioByPayees(scenario, accountNumbers, REASON);
        expectLastCall().once();
        replay(usageService);
        controller.excludeDetails(accountNumbers, REASON);
        verify(usageService);
    }

    @Test
    public void testGetPayeeTotalHolders() {
        List<PayeeTotalHolder> payeeTotalHolders = Collections.singletonList(new PayeeTotalHolder());
        expect(usageService.getPayeeTotalHoldersByScenarioId(scenario.getId()))
            .andReturn(payeeTotalHolders)
            .once();
        replay(usageService);
        assertEquals(payeeTotalHolders, controller.getPayeeTotalHolders());
        verify(usageService);
    }

    private Scenario buildScenario() {
        Scenario newScenario = new Scenario();
        newScenario.setId(RupPersistUtils.generateUuid());
        return newScenario;
    }
}
