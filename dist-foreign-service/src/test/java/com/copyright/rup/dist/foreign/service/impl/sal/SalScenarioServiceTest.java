package com.copyright.rup.dist.foreign.service.impl.sal;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalScenarioService;
import com.copyright.rup.dist.foreign.service.api.sal.ISalUsageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Set;

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
    private static final String SCENARIO_ID = "900f310a-3ed2-4bdc-9712-da0e6deec604";
    private static final String SCENARIO_NAME = "SAL Scenario";

    private IScenarioRepository scenarioRepository;
    private ISalUsageService salUsageService;
    private IScenarioAuditService scenarioAuditService;
    private IScenarioUsageFilterService scenarioUsageFilterService;
    private ISalScenarioService salScenarioService;

    @Before
    public void setUp() {
        scenarioRepository = createMock(IScenarioRepository.class);
        salUsageService = createMock(ISalUsageService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        salScenarioService = new SalScenarioService();
        Whitebox.setInternalState(salScenarioService, scenarioRepository);
        Whitebox.setInternalState(salScenarioService, salUsageService);
        Whitebox.setInternalState(salScenarioService, scenarioAuditService);
        Whitebox.setInternalState(salScenarioService, scenarioUsageFilterService);
    }

    @Test
    public void testGetScenarioNameByFundPoolId() {
        expect(scenarioRepository.findNameBySalFundPoolId(FUND_POOL_ID)).andReturn(SCENARIO_NAME).once();
        replay(scenarioRepository);
        assertEquals(SCENARIO_NAME, salScenarioService.getScenarioNameByFundPoolId(FUND_POOL_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testDeleteScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        salUsageService.deleteFromScenario(SCENARIO_ID);
        expectLastCall().once();
        scenarioRepository.remove(SCENARIO_ID);
        expectLastCall().once();
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        scenarioAuditService.deleteActions(SCENARIO_ID);
        expectLastCall().once();
        replay(scenarioRepository, salUsageService, scenarioAuditService, scenarioUsageFilterService);
        salScenarioService.deleteScenario(scenario);
        verify(scenarioRepository, salUsageService, scenarioAuditService, scenarioUsageFilterService);
    }

    @Test
    public void testChangeScenariosState() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_ID);
        Set<Scenario> scenarios = Set.of(scenario);
        Set<String> scenarioIds = Set.of(scenario.getId());
        scenarioRepository.updateStatus(scenarios, ScenarioStatusEnum.SUBMITTED);
        expectLastCall().once();
        scenarioAuditService.logAction(scenarioIds, ScenarioActionTypeEnum.SUBMITTED, "reason");
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService);
        salScenarioService.changeScenariosState(scenarios, ScenarioStatusEnum.SUBMITTED,
            ScenarioActionTypeEnum.SUBMITTED, "reason");
        assertTrue(scenarios.stream().allMatch(scenario1 ->
            scenario1.getStatus().equals(ScenarioStatusEnum.SUBMITTED)));
        verify(scenarioRepository, scenarioAuditService);
    }
}
