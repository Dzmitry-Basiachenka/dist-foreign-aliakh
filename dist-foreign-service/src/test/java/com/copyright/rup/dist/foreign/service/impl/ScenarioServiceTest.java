package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.util.RupContextUtils;

import com.google.common.collect.Lists;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.List;

/**
 * Verifies {@link ScenarioService}.
 * <p/>
 * Copyright (C) 2017 copyright.com
 * <p/>
 * Date: 3/15/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class})
public class ScenarioServiceTest {

    private static final String SCENARIO_NAME = "Scenario Name";
    private static final String USAGE_BATCH_ID = RupPersistUtils.generateUuid();
    private static final String DESCRIPTION = "Description";
    private static final BigDecimal GROSS_AMOUNT_1 = new BigDecimal("15.0000000034");
    private static final BigDecimal GROSS_AMOUNT_2 = new BigDecimal("8.0000000052");
    private static final BigDecimal GROSS_AMOUNT_SUM = new BigDecimal("23.0000000086");
    private static final BigDecimal REPORTED_VALUE_1 = new BigDecimal("15.34");
    private static final BigDecimal REPORTED_VALUE_2 = new BigDecimal("8.52");
    private static final BigDecimal REPORTED_VALUE_SUM = new BigDecimal("23.86");
    private static final String USER_NAME = "User Name";
    private ScenarioService scenarioService;
    private IScenarioRepository scenarioRepository;
    private IUsageService usageService;

    @Before
    public void setUp() {
        scenarioRepository = createMock(IScenarioRepository.class);
        usageService = createMock(IUsageService.class);
        scenarioService = new ScenarioService();
        Whitebox.setInternalState(scenarioService, "scenarioRepository", scenarioRepository);
        Whitebox.setInternalState(scenarioService, "usageService", usageService);
    }

    @Test
    public void testGetScenarios() {
        List<Scenario> scenarios = Lists.newArrayList(new Scenario());
        expect(scenarioRepository.getScenarios()).andReturn(scenarios).once();
        replay(scenarioRepository);
        assertEquals(scenarios, scenarioService.getScenarios());
        verify(scenarioRepository);
    }

    @Test
    public void testIsScenarioExists() {
        expect(scenarioRepository.getCountByName(SCENARIO_NAME)).andReturn(1).once();
        replay(scenarioRepository);
        assertTrue(scenarioService.isScenarioExists(SCENARIO_NAME));
        verify(scenarioRepository);
    }

    @Test
    public void testIsScenarioNotExists() {
        expect(scenarioRepository.getCountByName(SCENARIO_NAME)).andReturn(0).once();
        replay(scenarioRepository);
        assertFalse(scenarioService.isScenarioExists(SCENARIO_NAME));
        verify(scenarioRepository);
    }

    @Test
    public void testGetScenariosNamesByUsageBatchId() {
        List<String> scenariosNames = Lists.newArrayList(SCENARIO_NAME);
        expect(scenarioRepository.findScenariosNamesByUsageBatchId(USAGE_BATCH_ID)).andReturn(scenariosNames).once();
        replay(scenarioRepository);
        assertEquals(scenariosNames, scenarioService.getScenariosNamesByUsageBatchId(USAGE_BATCH_ID));
        verify(scenarioRepository);
    }

    @Test
    public void testCreateScenario() {
        mockStatic(RupContextUtils.class);
        Capture<Scenario> captureScenarioForInsert = new Capture<>();
        Capture<Scenario> captureScenario = new Capture<>();
        UsageFilter usageFilter = new UsageFilter();
        Usage usage1 = buildUsage(GROSS_AMOUNT_1, REPORTED_VALUE_1);
        Usage usage2 = buildUsage(GROSS_AMOUNT_2, REPORTED_VALUE_2);
        expect(usageService.getUsagesWithAmounts(usageFilter)).andReturn(Lists.newArrayList(usage1, usage2)).once();
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        scenarioRepository.insert(capture(captureScenarioForInsert));
        expectLastCall().once();
        usageService.addUsagesToScenario(eq(Lists.newArrayList(usage1.getId(), usage2.getId())),
            capture(captureScenario));
        expectLastCall().once();
        replay(scenarioRepository, usageService, RupContextUtils.class);
        scenarioService.createScenario(SCENARIO_NAME, DESCRIPTION, usageFilter);
        Scenario scenarioForInsert = captureScenarioForInsert.getValue();
        Scenario scenario = captureScenario.getValue();
        assertNotNull(scenarioForInsert);
        assertNotNull(scenario);
        assertEquals(scenario, scenarioForInsert);
        assertEquals(SCENARIO_NAME, scenarioForInsert.getName());
        assertEquals(DESCRIPTION, scenarioForInsert.getDescription());
        assertEquals(GROSS_AMOUNT_SUM, scenarioForInsert.getGrossTotal());
        assertEquals(REPORTED_VALUE_SUM, scenarioForInsert.getReportedTotal());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenarioForInsert.getStatus());
        verify(scenarioRepository, usageService, RupContextUtils.class);
    }

    private Usage buildUsage(BigDecimal grossAmount, BigDecimal reportedValue) {
        Usage usage = new Usage();
        usage.setId(RupPersistUtils.generateUuid());
        usage.setGrossAmount(grossAmount);
        usage.setReportedValue(reportedValue);
        return usage;
    }
}
