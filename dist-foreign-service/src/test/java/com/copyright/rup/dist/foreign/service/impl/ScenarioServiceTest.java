package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Verifies {@link ScenarioService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 03/15/17
 *
 * @author Aliaksandr Radkevich
 * @author Mikalai Bezmen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, PrmRollUpService.class})
public class ScenarioServiceTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String USAGE_BATCH_ID = "a46937ad-6e23-423d-8a65-b97c40904b95";
    private static final String SCENARIO_ID = "3d9dc27c-a3dd-4a15-85fe-9230f7dec5e2";
    private static final String REASON = "reason";
    private final Scenario scenario = new Scenario();
    private ScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;
    private IScenarioAuditService scenarioAuditService;
    private IScenarioUsageFilterService scenarioUsageFilterService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("FAS");
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, scenarioRepository);
        Whitebox.setInternalState(scenarioService, usageService);
        Whitebox.setInternalState(scenarioService, scenarioAuditService);
        Whitebox.setInternalState(scenarioService, scenarioUsageFilterService);
        Whitebox.setInternalState(scenarioService, rightsholderDiscrepancyService);
    }

    @Test
    public void testScenarioExists() {
        expect(scenarioRepository.findCountByName(SCENARIO_NAME)).andReturn(1).once();
        replay(scenarioRepository);
        assertTrue(scenarioService.scenarioExists(SCENARIO_NAME));
        verify(scenarioRepository);
    }

    @Test
    public void testScenarioNotExists() {
        expect(scenarioRepository.findCountByName(SCENARIO_NAME)).andReturn(0).once();
        replay(scenarioRepository);
        assertFalse(scenarioService.scenarioExists(SCENARIO_NAME));
        verify(scenarioRepository);
    }

    @Test
    public void testGetScenariosByProductFamiliesAndStatuses() {
        List<Scenario> scenarios = List.of(new Scenario());
        Set<String> productFamilies = Sets.newHashSet("FAS", "FAS2");
        expect(scenarioRepository.findByProductFamiliesAndStatuses(productFamilies,
            EnumSet.of(ScenarioStatusEnum.IN_PROGRESS))).andReturn(scenarios).once();
        replay(scenarioRepository);
        assertSame(scenarios, scenarioService.getScenariosByProductFamiliesAndStatuses(productFamilies,
            EnumSet.of(ScenarioStatusEnum.IN_PROGRESS)));
        verify(scenarioRepository);
    }

    @Test
    public void testGetScenariosNamesByUsageBatchId() {
        List<String> scenariosNames = Lists.newArrayList(SCENARIO_NAME);
        expect(scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(scenariosNames).once();
        replay(scenarioRepository);
        assertSame(scenariosNames, scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        expect(scenarioRepository.findWithAmountsAndLastAction(SCENARIO_ID)).andReturn(scenario).once();
        expect(scenarioRepository.findArchivedWithAmountsAndLastAction(SCENARIO_ID)).andReturn(scenario).times(2);
        replay(scenarioRepository);
        assertSame(scenario, scenarioService.getScenarioWithAmountsAndLastAction(scenario));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertSame(scenario, scenarioService.getScenarioWithAmountsAndLastAction(scenario));
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        assertSame(scenario, scenarioService.getScenarioWithAmountsAndLastAction(scenario));
        verify(scenarioRepository);
    }

    @Test
    public void testUpdateName() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn("user@copyright.com").once();
        scenarioRepository.updateNameById(SCENARIO_ID, SCENARIO_NAME, "user@copyright.com");
        expectLastCall().once();
        replay(scenarioRepository, RupContextUtils.class);
        scenarioService.updateName(SCENARIO_ID, SCENARIO_NAME);
        verify(scenarioRepository, RupContextUtils.class);
    }

    @Test
    public void testDeleteScenario() {
        usageService.deleteFromScenario(SCENARIO_ID);
        expectLastCall().once();
        scenarioRepository.remove(SCENARIO_ID);
        expectLastCall().once();
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        scenarioAuditService.deleteActions(SCENARIO_ID);
        expectLastCall().once();
        rightsholderDiscrepancyService.deleteByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replay(usageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService,
            rightsholderDiscrepancyService);
        scenarioService.deleteScenario(scenario);
        verify(usageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService,
            rightsholderDiscrepancyService);
    }

    @Test
    public void testSubmit() {
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SUBMITTED, REASON);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService);
        scenarioService.submit(scenario, REASON);
        assertEquals(ScenarioStatusEnum.SUBMITTED, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService);
    }

    @Test
    public void testReject() {
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.REJECTED, REASON);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService);
        scenarioService.reject(scenario, REASON);
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService);
    }

    @Test
    public void testApprove() {
        rightsholderDiscrepancyService.deleteByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.DRAFT);
        expectLastCall().once();
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.APPROVED, REASON);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, rightsholderDiscrepancyService);
        scenarioService.approve(scenario, REASON);
        assertEquals(ScenarioStatusEnum.APPROVED, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, rightsholderDiscrepancyService);
    }
}
