package com.copyright.rup.dist.foreign.service.impl.aacl;

import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioAuditService;
import com.copyright.rup.dist.foreign.service.api.IScenarioUsageFilterService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclScenarioService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

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
    private final Scenario scenario = new Scenario();

    private IScenarioUsageFilterService scenarioUsageFilterService;
    private IScenarioAuditService scenarioAuditService;
    private IScenarioRepository scenarioRepository;
    private IAaclScenarioService aaclScenarioService;
    private IAaclUsageService aaclUsageService;

    @Before
    public void setUp() {
        scenario.setId(SCENARIO_ID);
        scenario.setProductFamily("AACL");
        scenarioRepository = createMock(IScenarioRepository.class);
        aaclUsageService = createMock(IAaclUsageService.class);
        scenarioAuditService = createMock(IScenarioAuditService.class);
        scenarioUsageFilterService = createMock(IScenarioUsageFilterService.class);
        aaclScenarioService = new AaclScenarioService();
        Whitebox.setInternalState(aaclScenarioService, scenarioRepository);
        Whitebox.setInternalState(aaclScenarioService, aaclUsageService);
        Whitebox.setInternalState(aaclScenarioService, scenarioAuditService);
        Whitebox.setInternalState(aaclScenarioService, scenarioUsageFilterService);
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
}
