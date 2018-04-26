package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;
import static org.powermock.api.easymock.PowerMock.verifyAll;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.PrmRollUpService;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancyStatusEnum;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.integration.prm.api.IPrmIntegrationService;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsholderDiscrepancyService;
import com.copyright.rup.dist.foreign.service.api.IRightsholderService;
import com.copyright.rup.dist.foreign.service.api.IRmsGrantsService;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
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
    private IScenarioAuditService scenarioAuditService;
    private ILmIntegrationService lmIntegrationService;
    private IRmsGrantsService rmsGrantsService;
    private IRightsholderService rightsholderService;
    private IScenarioUsageFilterService scenarioUsageFilterService;
    private IRightsholderDiscrepancyService rightsholderDiscrepancyService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        rmsGrantsService = createMock(IRmsGrantsService.class);
        rightsholderService = createMock(IRightsholderService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        rightsholderDiscrepancyService = createMock(IRightsholderDiscrepancyService.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, "scenarioRepository", scenarioRepository);
        Whitebox.setInternalState(scenarioService, "usageService", usageService);
        Whitebox.setInternalState(scenarioService, "scenarioAuditService", scenarioAuditService);
        Whitebox.setInternalState(scenarioService, "lmIntegrationService", lmIntegrationService);
        Whitebox.setInternalState(scenarioService, "rmsGrantsService", rmsGrantsService);
        Whitebox.setInternalState(scenarioService, "rightsholderService", rightsholderService);
        Whitebox.setInternalState(scenarioService, "scenarioUsageFilterService", scenarioUsageFilterService);
        Whitebox.setInternalState(scenarioService, "rightsholderDiscrepancyService", rightsholderDiscrepancyService);
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
    public void testRemove() {
        usageService.deleteFromScenario(SCENARIO_ID);
        expectLastCall().once();
        scenarioRepository.remove(SCENARIO_ID);
        expectLastCall().once();
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        scenarioAuditService.deleteActions(SCENARIO_ID);
        expectLastCall().once();
        rightsholderDiscrepancyService.deleteDiscrepanciesByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.APPROVED);
        replay(usageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService);
        scenarioService.deleteScenario(scenario);
        verify(usageService, scenarioRepository, scenarioAuditService, scenarioUsageFilterService);
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
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.APPROVED, REASON);
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
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
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

    @Test
    public void testSaveRightsholderDiscrepancies() {
        expect(usageService.getUsagesForReconcile(SCENARIO_ID))
            .andReturn(Lists.newArrayList(buildUsage(1L, 2000017010L), buildUsage(2L, 2000017010L))).once();
        expect(rmsGrantsService.getAccountNumbersByWrWrkInsts(Lists.newArrayList(1L, 2L)))
            .andReturn(ImmutableMap.of(1L, 2000017010L, 2L, 1000000001L)).once();
        expect(rightsholderService.updateAndGetRightsholders(Sets.newHashSet(2000017010L, 1000000001L)))
            .andReturn(ImmutableMap.of(2000017010L, buildRightsholder(2000017010L), 1000000001L,
                buildRightsholder(1000000001L))).once();
        Capture<List<RightsholderDiscrepancy>> discrepanciesCapture = new Capture<>();
        expect(rightsholderDiscrepancyService.getDiscrepanciesCountByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS)).andReturn(2).once();
        rightsholderDiscrepancyService.insertDiscrepancies(capture(discrepanciesCapture), eq(SCENARIO_ID));
        expectLastCall().once();
        replayAll();
        scenarioService.getOwnershipChanges(scenario);
        List<RightsholderDiscrepancy> discrepancies = discrepanciesCapture.getValue();
        assertEquals(1, discrepancies.size());
        RightsholderDiscrepancy discrepancy = discrepancies.iterator().next();
        assertEquals(2L, discrepancy.getWrWrkInst(), 0);
        assertEquals(2000017010L, discrepancy.getOldRightsholder().getAccountNumber(), 0);
        assertEquals(1000000001L, discrepancy.getNewRightsholder().getAccountNumber(), 0);
        verifyAll();
    }

    @Test
    public void testUpdateRhParticipationAndAmounts() {
        List<Usage> usages = Collections.singletonList(buildUsage(1L, 2000017010L));
        expect(usageService.getUsagesByScenarioId(SCENARIO_ID)).andReturn(usages).once();
        usageService.updateRhPayeeAndAmounts(usages);
        expectLastCall().once();
        replayAll();
        scenarioService.updateRhParticipationAndAmounts(scenario);
        verifyAll();
    }

    @Test
    public void testApproveOwnershipChanges() {
        IPrmIntegrationService prmIntegrationService = createMock(IPrmIntegrationService.class);
        Whitebox.setInternalState(scenarioService, "prmIntegrationService", prmIntegrationService);
        mockStatic(PrmRollUpService.class);
        RightsholderDiscrepancy discrepancy1 = buildDiscrepancy(1L, 2000017010L, 1000000001L);
        RightsholderDiscrepancy discrepancy2 = buildDiscrepancy(2L, 2000017020L, 1000000002L);
        List<RightsholderDiscrepancy> discrepancies = new ArrayList<>();
        discrepancies.add(discrepancy1);
        discrepancies.add(discrepancy2);
        expect(rightsholderDiscrepancyService.getDiscrepanciesByScenarioIdAndStatus(SCENARIO_ID,
            RightsholderDiscrepancyStatusEnum.IN_PROGRESS, null, null)).andReturn(discrepancies).once();
        List<Usage> usages = Lists.newArrayList(buildUsage(1L, 2000017010L), buildUsage(2L, 2000017020L));
        expect(usageService.getUsagesForReconcile(SCENARIO_ID)).andReturn(usages).once();
        Rightsholder rightsholder1 = discrepancy1.getNewRightsholder();
        Rightsholder rightsholder2 = discrepancy2.getNewRightsholder();
        expect(prmIntegrationService.getRollUps(Sets.newHashSet(rightsholder1.getId(), rightsholder2.getId())))
            .andReturn(HashBasedTable.create()).once();
        expect(PrmRollUpService.getPayeeAccountNumber(HashBasedTable.create(), rightsholder1, "FAS"))
            .andReturn(1000000003L);
        expect(PrmRollUpService.getPayeeAccountNumber(HashBasedTable.create(), rightsholder2, "FAS"))
            .andReturn(1000000004L);
        usageService.updateRhPayeeAndAmounts(usages);
        expectLastCall().once();
        rightsholderDiscrepancyService.approveDiscrepanciesByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        replayAll();
        scenarioService.approveOwnershipChanges(scenario);
        assertEquals(1000000001L, usages.get(0).getRightsholder().getAccountNumber(), 0);
        assertEquals(1000000002L, usages.get(1).getRightsholder().getAccountNumber(), 0);
        assertEquals(1000000003L, usages.get(0).getPayee().getAccountNumber(), 0);
        assertEquals(1000000004L, usages.get(1).getPayee().getAccountNumber(), 0);
        verifyAll();
    }

    private Usage buildUsage(Long wrWrkInst, Long accountNumber) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.getRightsholder().setAccountNumber(accountNumber);
        return usage;
    }

    private RightsholderDiscrepancy buildDiscrepancy(Long wrWrkInst, Long oldAccountNumber, Long newAccountNumber) {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setOldRightsholder(buildRightsholder(oldAccountNumber));
        rightsholderDiscrepancy.setNewRightsholder(buildRightsholder(newAccountNumber));
        rightsholderDiscrepancy.setWrWrkInst(wrWrkInst);
        rightsholderDiscrepancy.setProductFamily("FAS");
        return rightsholderDiscrepancy;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setId(RupPersistUtils.generateUuid());
        return rightsholder;
    }
}
