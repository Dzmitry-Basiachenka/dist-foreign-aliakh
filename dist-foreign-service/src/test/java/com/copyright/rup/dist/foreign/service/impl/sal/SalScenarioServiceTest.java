package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Verifies {@link SalScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 09/24/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class SalScenarioServiceTest {

    private static final String FUND_POOL_ID = "c9ed9369-a71e-485a-bfce-5bd9fa91d418";
    private static final String SCENARIO_NAME = "SAL Scenario";

    private IScenarioRepository scenarioRepository;
    private ISalScenarioService salScenarioService;

    @Before
    public void setUp() {
        scenarioRepository = createMock(IScenarioRepository.class);
        salScenarioService = new SalScenarioService();
        Whitebox.setInternalState(salScenarioService, scenarioRepository);
    }

    @Test
    public void testGetScenarioNameByFundPoolId() {
        expect(scenarioRepository.findNameBySalFundPoolId(FUND_POOL_ID)).andReturn(SCENARIO_NAME).once();
        replay(scenarioRepository);
        assertEquals(SCENARIO_NAME, salScenarioService.getScenarioNameByFundPoolId(FUND_POOL_ID));
        verify(scenarioRepository);
    }
}
