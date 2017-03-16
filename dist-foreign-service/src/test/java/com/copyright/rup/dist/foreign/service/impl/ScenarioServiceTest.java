package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link ScenarioService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 */
public class ScenarioServiceTest {

    private ScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;

    @Before
    public void setUp() {
        scenarioRepository = createMock(IScenarioRepository.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, "scenarioRepository", scenarioRepository);
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = Lists.newArrayList(new Scenario());
        expect(scenarioRepository.getScenarios()).andReturn(scenarios).once();
        replay(scenarioRepository);
        assertEquals(scenarios, scenarioService.getScenarios());
        verify(scenarioRepository);
    }
}
