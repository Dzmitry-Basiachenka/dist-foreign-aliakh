package com.copyright.rup.dist.foreign.ui.scenario.impl.fas;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.ui.scenario.api.fas.IExcludePayeeFilterController;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verifies {@link ExcludePayeeFilterController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 06/17/2020
 *
 * @author Ihar Suvorau
 */
public class ExcludePayeeFilterControllerTest {

    private final IExcludePayeeFilterController controller = new ExcludePayeeFilterController();

    @Test
    public void testGetParticipatingStatuses() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("PARTICIPATING", true);
        map.put("NON-PARTICIPATING", false);
        assertEquals(map, controller.getParticipatingStatuses());
    }

    @Test
    public void testGetScenarios() {
        IScenarioService scenarioService = createMock(IScenarioService.class);
        Whitebox.setInternalState(controller, scenarioService);
        List<Scenario> scenarios = Collections.singletonList(buildScenario());
        expect(scenarioService.getScenariosByProductFamiliesAndStatuses(Sets.newHashSet("FAS", "FAS2"),
            EnumSet.of(ScenarioStatusEnum.IN_PROGRESS))).andReturn(scenarios);
        replay(scenarioService);
        assertSame(scenarios, controller.getScenarios());
        verify(scenarioService);
    }

    private Scenario buildScenario() {
        Scenario newScenario = new Scenario();
        newScenario.setId(RupPersistUtils.generateUuid());
        return newScenario;
    }
}
