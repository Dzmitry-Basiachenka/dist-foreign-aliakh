package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

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
@PrepareForTest(RupContextUtils.class)
public class ScenarioServiceTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String REASON = "reason";
    private ScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;
    private IScenarioAuditService scenarioAuditService;
    private ILmIntegrationService lmIntegrationService;
    private Scenario scenario = new Scenario();

    @Before
    public void setUp() {
        scenario.setId(RupPersistUtils.generateUuid());
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, "scenarioRepository", scenarioRepository);
        Whitebox.setInternalState(scenarioService, "usageService", usageService);
        Whitebox.setInternalState(scenarioService, "scenarioAuditService", scenarioAuditService);
        Whitebox.setInternalState(scenarioService, "lmIntegrationService", lmIntegrationService);
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
    public void testGetScenariosNamesByUsageBatchId() {
        List<String> scenariosNames = Lists.newArrayList(SCENARIO_NAME);
        expect(scenarioRepository.findNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(scenariosNames).once();
        replay(scenarioRepository);
        assertSame(scenariosNames, scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testGetScenarioWithAmountsAndLastAction() {
        expect(scenarioRepository.findWithAmountsAndLastAction(scenario.getId())).andReturn(scenario).once();
        expect(scenarioRepository.findArchivedWithAmountsAndLastAction(scenario.getId())).andReturn(scenario).once();
        replay(scenarioRepository);
        assertSame(scenario, scenarioService.getScenarioWithAmountsAndLastAction(scenario));
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        assertSame(scenario, scenarioService.getScenarioWithAmountsAndLastAction(scenario));
        verify(scenarioRepository);
    }

    @Test
    public void testRemove() {
        usageService.deleteFromScenario(scenario.getId());
        expectLastCall().once();
        scenarioRepository.remove(scenario.getId());
        expectLastCall().once();
        scenarioAuditService.deleteActions(scenario.getId());
        expectLastCall().once();
        replay(usageService, scenarioRepository, scenarioAuditService);
        scenarioService.deleteScenario(scenario);
        verify(usageService, scenarioRepository, scenarioAuditService);
    }

    @Test
    public void testGetSourceRros() {
        expect(scenarioRepository.findSourceRros(SCENARIO_ID)).andReturn(Collections.emptyList()).once();
        replay(scenarioRepository);
        assertEquals(Collections.emptyList(), scenarioService.getSourceRros(SCENARIO_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testSubmit() {
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.SUBMITTED, REASON);
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
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.REJECTED, REASON);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService);
        scenarioService.reject(scenario, REASON);
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService);
    }

    @Test
    public void testApprove() {
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.APPROVED, REASON);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService);
        scenarioService.approve(scenario, REASON);
        assertEquals(ScenarioStatusEnum.APPROVED, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService);
    }

    @Test
    public void testSendToLm() {
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(scenario.getId(), ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        expect(usageService.moveToArchive(scenario)).andReturn(Collections.singletonList(new Usage())).once();
        lmIntegrationService.sendToLm(Collections.singletonList(new ExternalUsage(new Usage())));
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService);
        scenarioService.sendToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService);
    }

    @Test
    public void testGetRightsholdersByScenarioAndSourceRro() {
        expect(scenarioRepository.findRightsholdersByScenarioIdAndSourceRro(SCENARIO_ID, 2000017010L))
            .andReturn(Collections.emptyList()).once();
        replay(scenarioRepository);
        assertEquals(Collections.emptyList(),
            scenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 2000017010L));
        verify(scenarioRepository);
    }
}
