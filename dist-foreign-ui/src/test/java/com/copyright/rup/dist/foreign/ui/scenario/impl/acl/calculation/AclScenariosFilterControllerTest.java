package com.copyright.rup.dist.foreign.ui.scenario.impl.acl.calculation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link AclScenariosFilterController}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 10/31/2022
 *
 * @author Mikita Maistrenka
 */
public class AclScenariosFilterControllerTest {

    private IAclScenarioService scenarioService;
    private final AclScenariosFilterController controller = new AclScenariosFilterController();

    @Before
    public void setUp() {
        scenarioService = createMock(IAclScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
    }

    @Test
    public void testInstantiateWidget() {
        assertNotNull(controller.instantiateWidget());
    }

    @Test
    public void testGetPeriods() {
        List<Integer> periods = List.of(202212);
        expect(scenarioService.getScenarioPeriods()).andReturn(periods).once();
        replay(scenarioService);
        assertSame(periods, controller.getPeriods());
        verify(scenarioService);
    }
}
