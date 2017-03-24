package com.copyright.rup.dist.foreign.ui.component.validator;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.junit.Before;
import org.junit.Test;

/**
 * Verifies {@link ScenarioNameUniqueValidator}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/16/17
 *
 * @author Mikalai Bezmen
 */
public class ScenarioNameUniqueValidatorTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String SCENARIO_NEW_NAME = "Scenario New Name";
    private ScenarioNameUniqueValidator validator;
    private IScenarioService scenarioService;

    @Before
    public void setUp() {
        scenarioService = createMock(IScenarioService.class);
        validator = new ScenarioNameUniqueValidator(scenarioService);
    }

    @Test
    public void testIsValidValueStoredNull() {
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(true).once();
        replay(scenarioService);
        assertFalse(validator.isValid(SCENARIO_NAME));
        verify(scenarioService);
    }

    @Test
    public void testIsValidValue() {
        expect(scenarioService.scenarioExists(SCENARIO_NEW_NAME)).andReturn(true).once();
        replay(scenarioService);
        assertFalse(validator.isValid(SCENARIO_NEW_NAME));
        verify(scenarioService);
    }

    @Test
    public void testIsValidValueScenarioNotExist() {
        expect(scenarioService.scenarioExists(SCENARIO_NAME)).andReturn(false).once();
        replay(scenarioService);
        assertTrue(validator.isValidValue(SCENARIO_NAME));
        verify(scenarioService);
    }
}
