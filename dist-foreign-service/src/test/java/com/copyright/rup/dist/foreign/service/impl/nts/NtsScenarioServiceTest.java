package com.copyright.rup.dist.foreign.service.impl.nts;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
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
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.api.nts.INtsUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link NtsScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 05/18/2020
 *
 * @author Ihar Suvorau
 */
public class NtsScenarioServiceTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String SCENARIO_ID = "f9c61268-36df-418a-af14-bddd49014ef8";
    private final Scenario scenario = new Scenario();
    private NtsScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;
    private INtsUsageService ntsUsageService;
    private IScenarioAuditService scenarioAuditService;
    private ILmIntegrationService lmIntegrationService;
    private IScenarioUsageFilterService scenarioUsageFilterService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("FAS");
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        ntsUsageService = createMock(INtsUsageService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        scenarioService = new NtsScenarioService();
        Whitebox.setInternalState(scenarioService, scenarioRepository);
        Whitebox.setInternalState(scenarioService, usageService);
        Whitebox.setInternalState(scenarioService, ntsUsageService);
        Whitebox.setInternalState(scenarioService, scenarioAuditService);
        Whitebox.setInternalState(scenarioService, lmIntegrationService);
        Whitebox.setInternalState(scenarioService, scenarioUsageFilterService);
        Whitebox.setInternalState(scenarioService, "batchSize", 1);
    }

    @Test
    public void testGetScenarioNameByFundPoolId() {
        String fundPoolId = RupPersistUtils.generateUuid();
        expect(scenarioRepository.findNameByNtsFundPoolId(fundPoolId)).andReturn(SCENARIO_NAME).once();
        replay(scenarioRepository);
        assertEquals(SCENARIO_NAME, scenarioService.getScenarioNameByFundPoolId(fundPoolId));
        verify(scenarioRepository);
    }

    @Test
    public void testDeleteScenario() {
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
    public void testSendToLm() {
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
        scenarioService.sendToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, ntsUsageService);
    }
}
