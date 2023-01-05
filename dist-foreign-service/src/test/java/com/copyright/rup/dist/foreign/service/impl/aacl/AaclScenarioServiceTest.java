package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
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
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.List;

/**
 * Verifies {@link AaclScenarioService}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 04/08/2020
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class AaclScenarioServiceTest {

    private static final String SCENARIO_ID = "958f07f0-a8a8-4dc5-8c24-fe4e484fd054";
    private static final String SCENARIO_NAME = "Scenario name";
    private final Scenario scenario = new Scenario();

    private IScenarioUsageFilterService scenarioUsageFilterService;
    private IScenarioAuditService scenarioAuditService;
    private IScenarioRepository scenarioRepository;
    private IAaclScenarioService aaclScenarioService;
    private IAaclUsageService aaclUsageService;
    private IUsageService usageService;
    private ILmIntegrationService lmIntegrationService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("AACL");
        scenarioRepository = createMock(IScenarioRepository.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        usageService = createMock(IUsageService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        lmIntegrationService = createMock(ILmIntegrationService.class);
        aaclScenarioService = new AaclScenarioService();
        Whitebox.setInternalState(aaclScenarioService, scenarioRepository);
        Whitebox.setInternalState(aaclScenarioService, aaclUsageService);
        Whitebox.setInternalState(aaclScenarioService, usageService);
        Whitebox.setInternalState(aaclScenarioService, scenarioAuditService);
        Whitebox.setInternalState(aaclScenarioService, scenarioUsageFilterService);
        Whitebox.setInternalState(aaclScenarioService, lmIntegrationService);
        Whitebox.setInternalState(aaclScenarioService, "batchSize", 1);
    }

    @Test
    public void testDeleteScenario() {
        aaclUsageService.deleteFromScenario(SCENARIO_ID);
        expectLastCall().once();
        scenarioRepository.remove(SCENARIO_ID);
        expectLastCall().once();
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        expectLastCall().once();
        scenarioAuditService.deleteActions(SCENARIO_ID);
        expectLastCall().once();
        replay(aaclUsageService, scenarioAuditService, scenarioUsageFilterService, scenarioRepository);
        aaclScenarioService.deleteScenario(scenario);
        verify(aaclUsageService, scenarioAuditService, scenarioUsageFilterService, scenarioRepository);
    }

    @Test
    public void testGetScenarioNameByFundPoolId() {
        String fundPoolId = RupPersistUtils.generateUuid();
        expect(scenarioRepository.findNameByAaclFundPoolId(fundPoolId)).andReturn(SCENARIO_NAME).once();
        replay(scenarioRepository);
        assertEquals(SCENARIO_NAME, aaclScenarioService.getScenarioNameByFundPoolId(fundPoolId));
        verify(scenarioRepository);
    }

    @Test
    public void testSendToLm() {
        List<String> usageIds = List.of(RupPersistUtils.generateUuid());
        Usage usage = new Usage();
        expect(aaclUsageService.moveToArchive(scenario)).andReturn(usageIds).once();
        expect(usageService.getArchivedUsagesForSendToLmByIds(usageIds)).andReturn(List.of(usage)).once();
        lmIntegrationService.sendToLm(List.of(new ExternalUsage(usage)));
        expectLastCall().once();
        scenarioRepository.updateStatus(scenario);
        expectLastCall().once();
        scenarioAuditService.logAction(SCENARIO_ID, ScenarioActionTypeEnum.SENT_TO_LM, StringUtils.EMPTY);
        expectLastCall().once();
        replay(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, aaclUsageService);
        aaclScenarioService.sendToLm(scenario);
        assertEquals(ScenarioStatusEnum.SENT_TO_LM, scenario.getStatus());
        verify(scenarioRepository, scenarioAuditService, usageService, lmIntegrationService, aaclUsageService);
    }
}
