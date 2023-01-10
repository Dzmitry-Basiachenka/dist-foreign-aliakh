package com.copyright.rup.dist.foreign.service.impl.fas;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.integration.lm.api.ILmIntegrationService;
import com.copyright.rup.dist.foreign.integration.lm.api.domain.ExternalUsage;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.fas.IFasUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link FasScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
public class FasScenarioServiceTest {

    private static final String SCENARIO_ID = "c8bd2166-6259-4822-aaf6-edfab4502d60";
    private final Scenario scenario = new Scenario();
    private FasScenarioService fasScenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;
    private IFasUsageService fasUsageService;
    private IScenarioAuditService scenarioAuditService;
    private ILmIntegrationService lmIntegrationService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("FAS");
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        fasUsageService = createMock(IFasUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        fasScenarioService = new FasScenarioService();
        Whitebox.setInternalState(fasScenarioService, scenarioRepository);
        Whitebox.setInternalState(fasScenarioService, usageService);
        Whitebox.setInternalState(fasScenarioService, fasUsageService);
        Whitebox.setInternalState(fasScenarioService, scenarioAuditService);
        Whitebox.setInternalState(fasScenarioService, lmIntegrationService);
        Whitebox.setInternalState(fasScenarioService, "batchSize", 1);
    }

    @Test
    public void testGetSourceRros() {
        expect(scenarioRepository.findSourceRros(SCENARIO_ID)).andReturn(List.of()).once();
        replay(scenarioRepository);
        assertEquals(List.of(), fasScenarioService.getSourceRros(SCENARIO_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testSendToLm() {
        List<String> usageIds = List.of("3d81a2d6-c4b4-48d1-b59c-41e67b3719ac");
        Usage usage = new Usage();
        expect(fasUsageService.moveToArchive(scenario)).andReturn(usageIds).once();
        expect(usageService.getArchivedUsagesForSendToLmByIds(usageIds)).andReturn(List.of(usage)).once();
        lmIntegrationService.sendToLm(List.of(new ExternalUsage(usage)));
        expectLastCall().once();
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, fasUsageService);
        fasScenarioService.sendToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, fasUsageService);
    }

    @Test
    public void testGetRightsholdersByScenarioAndSourceRro() {
        expect(scenarioRepository.findRightsholdersByScenarioIdAndSourceRro(SCENARIO_ID, 2000017010L))
            .andReturn(List.of()).once();
        replay(scenarioRepository);
        assertEquals(List.of(), fasScenarioService.getRightsholdersByScenarioAndSourceRro(SCENARIO_ID, 2000017010L));
        verify(scenarioRepository);
    }

    @Test
    public void testUpdateRhPayeeParticipating() {
        Usage usage = new Usage();
        usage.setWrWrkInst(1L);
        usage.getRightsholder().setAccountNumber(2000017010L);
        List<Usage> usages = List.of(usage);
        expect(usageService.getUsagesByScenarioId(SCENARIO_ID)).andReturn(usages).once();
        fasUsageService.updateRhPayeeAmountsAndParticipating(usages);
        expectLastCall().once();
        replay(usageService, fasUsageService);
        fasScenarioService.updateRhPayeeParticipating(scenario);
        replay(usageService, fasUsageService);
    }
}
