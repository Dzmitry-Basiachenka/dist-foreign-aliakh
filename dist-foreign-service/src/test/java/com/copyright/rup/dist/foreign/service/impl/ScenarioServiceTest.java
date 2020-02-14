package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

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
@PrepareForTest({RupContextUtils.class, PrmRollUpService.class})
public class ScenarioServiceTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String SCENARIO_ID = RupPersistUtils.generateUuid();
    private static final String REASON = "reason";
    private final Scenario scenario = new Scenario();
    private ScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private INtsUsageService ntsUsageService;
    private IScenarioAuditService scenarioAuditService;
    private ILmIntegrationService lmIntegrationService;
    private IScenarioUsageFilterService scenarioUsageFilterService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("FAS");
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, scenarioRepository);
        Whitebox.setInternalState(scenarioService, usageService);
        Whitebox.setInternalState(scenarioService, ntsUsageService);
        Whitebox.setInternalState(scenarioService, fasUsageService);
        Whitebox.setInternalState(scenarioService, scenarioAuditService);
        Whitebox.setInternalState(scenarioService, lmIntegrationService);
        Whitebox.setInternalState(scenarioService, scenarioUsageFilterService);
        Whitebox.setInternalState(scenarioService, rightsholderDiscrepancyService);
        Whitebox.setInternalState(scenarioService, "batchSize", 1);
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
    public void testGetScenarioNameByPreServiceFeeFundId() {
        String fundId = RupPersistUtils.generateUuid();
        expect(scenarioRepository.findNameByPreServiceFeeFundId(fundId)).andReturn(SCENARIO_NAME).once();
        replay(scenarioRepository);
        assertEquals(SCENARIO_NAME, scenarioService.getScenarioNameByPreServiceFeeFundId(fundId));
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
    public void testDeleteNtsScenario() {
        scenario.setProductFamily("NTS");
        ntsUsageService.deleteBelletristicByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        ntsUsageService.deleteFromScenario(SCENARIO_ID);
        expectLastCall().once();
        scenarioRepository.remove(SCENARIO_ID);
        expectLastCall().once();
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        scenarioAuditService.deleteActions(SCENARIO_ID);
        expectLastCall().once();
        replay(ntsUsageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService);
        scenarioService.deleteScenario(scenario);
        verify(ntsUsageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService);
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

    @Test
    public void testSendFasToLm() {
        List<String> usageIds = Collections.singletonList(RupPersistUtils.generateUuid());
        Usage usage = new Usage();
        expect(fasUsageService.moveToArchive(scenario)).andReturn(usageIds).once();
        expect(usageService.getArchivedUsagesByIds(usageIds)).andReturn(Collections.singletonList(usage));
        lmIntegrationService.sendToLm(Collections.singletonList(new ExternalUsage(usage)));
        expectLastCall().once();
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, fasUsageService);
        scenarioService.sendFasToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, fasUsageService);
    }

    @Test
    public void testSendNtsToLm() {
        List<String> usageIds = Collections.singletonList(RupPersistUtils.generateUuid());
        Usage usage = new Usage();
        expect(ntsUsageService.moveToArchive(scenario)).andReturn(usageIds).once();
        expect(usageService.getArchivedUsagesByIds(usageIds)).andReturn(Collections.singletonList(usage)).once();
        lmIntegrationService.sendToLm(Collections.singletonList(new ExternalUsage(usage)));
        expectLastCall().once();
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, ntsUsageService);
        scenarioService.sendNtsToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, ntsUsageService);
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

    @Test
    public void testUpdateRhPayeeParticipating() {
        Usage usage = new Usage();
        usage.setWrWrkInst(1L);
        usage.getRightsholder().setAccountNumber(2000017010L);
        List<Usage> usages = Collections.singletonList(usage);
        expect(usageService.getUsagesByScenarioId(SCENARIO_ID)).andReturn(usages).once();
        fasUsageService.updateRhPayeeAmountsAndParticipating(usages);
        expectLastCall().once();
        replayAll();
        scenarioService.updateRhPayeeParticipating(scenario);
        verifyAll();
    }
}
