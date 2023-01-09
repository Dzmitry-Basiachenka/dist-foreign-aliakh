package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.same;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioUsageFilterRepository;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.time.LocalDate;
import java.util.Set;

/**
 * Verifies {@link ScenarioUsageFilterService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
public class ScenarioUsageFilterServiceTest {

    private static final String SCENARIO_ID = "2bb64bac-8526-438c-8676-974dd9305bac";
    private static final String FILTER_ID = RupPersistUtils.generateUuid();
    private static final String PRODUCT_FAMILY = "FAS";
    private static final UsageStatusEnum USAGE_STATUS = UsageStatusEnum.ELIGIBLE;
    private static final LocalDate PAYMENT_DATE = LocalDate.now();
    private static final Integer FISCAL_YEAR = LocalDate.now().getYear();
    private static final Set<Long> RH_ACCOUNT_NUMBERS = Set.of(2000017004L);
    private static final Set<String> USAGE_BATCHES_IDS = Set.of("e1c64cac-3f2b-4105-8056-6660e1ec461a");

    private ScenarioUsageFilterService scenarioUsageFilterService;
    private IScenarioUsageFilterRepository scenarioUsageFilterRepository;

    @Before
    public void setUp() {
        scenarioUsageFilterService = new ScenarioUsageFilterService();
        scenarioUsageFilterRepository = createMock(IScenarioUsageFilterRepository.class);
        Whitebox.setInternalState(scenarioUsageFilterService, scenarioUsageFilterRepository);
    }

    @Test
    public void testInsert() {
        ScenarioUsageFilter expectedUsageFilter = buildScenarioUsageFilter();
        scenarioUsageFilterRepository.insertRhAccountNumbers(anyObject(String.class), same(RH_ACCOUNT_NUMBERS));
        expectLastCall().once();
        scenarioUsageFilterRepository.insertUsageBatchesIds(anyObject(String.class), eq(USAGE_BATCHES_IDS));
        expectLastCall().once();
        scenarioUsageFilterRepository.insert(expectedUsageFilter);
        expectLastCall().once();
        replay(scenarioUsageFilterRepository);
        scenarioUsageFilterService.insert(SCENARIO_ID, expectedUsageFilter);
        verify(scenarioUsageFilterRepository);
        assertNotNull(expectedUsageFilter.getId());
        assertEquals(SCENARIO_ID, expectedUsageFilter.getScenarioId());
        assertScenarioUsageFilter(expectedUsageFilter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertNullScenarioId() {
        scenarioUsageFilterService.insert(null, buildScenarioUsageFilter());
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullScenarioUsageFilter() {
        scenarioUsageFilterService.insert(SCENARIO_ID, null);
    }

    @Test
    public void testRemoveByScenarioId() {
        scenarioUsageFilterRepository.deleteByScenarioId(SCENARIO_ID);
        replay(scenarioUsageFilterRepository);
        scenarioUsageFilterService.removeByScenarioId(SCENARIO_ID);
        verify(scenarioUsageFilterRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveByScenarioIdNullScenarioId() {
        scenarioUsageFilterService.removeByScenarioId(null);
    }

    @Test
    public void testGetByScenario() {
        ScenarioUsageFilter expectedUsageFilter = buildScenarioUsageFilter();
        expect(scenarioUsageFilterRepository.findByScenarioId(SCENARIO_ID)).andReturn(expectedUsageFilter).once();
        replay(scenarioUsageFilterRepository);
        ScenarioUsageFilter actualUsageFilter = scenarioUsageFilterService.getByScenarioId(SCENARIO_ID);
        assertEquals(FILTER_ID, expectedUsageFilter.getId());
        assertEquals(SCENARIO_ID, expectedUsageFilter.getScenarioId());
        assertScenarioUsageFilter(actualUsageFilter);
        assertEquals(expectedUsageFilter, actualUsageFilter);
        verify(scenarioUsageFilterRepository);
    }

    @Test
    public void testGetByScenarioNullScenarioUsageFilter() {
        expect(scenarioUsageFilterRepository.findByScenarioId(SCENARIO_ID)).andReturn(null).once();
        replay(scenarioUsageFilterRepository);
        ScenarioUsageFilter actualUsageFilter = scenarioUsageFilterService.getByScenarioId(SCENARIO_ID);
        assertNull(actualUsageFilter);
        verify(scenarioUsageFilterRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByScenarioNullScenarioId() {
        scenarioUsageFilterService.getByScenarioId(null);
    }

    private ScenarioUsageFilter buildScenarioUsageFilter() {
        ScenarioUsageFilter usageFilter = new ScenarioUsageFilter();
        usageFilter.setId(FILTER_ID);
        usageFilter.setScenarioId(SCENARIO_ID);
        usageFilter.setProductFamily(PRODUCT_FAMILY);
        usageFilter.setUsageStatus(USAGE_STATUS);
        usageFilter.setPaymentDate(PAYMENT_DATE);
        usageFilter.setFiscalYear(FISCAL_YEAR);
        usageFilter.setRhAccountNumbers(RH_ACCOUNT_NUMBERS);
        usageFilter.setUsageBatchesIds(USAGE_BATCHES_IDS);
        return usageFilter;
    }

    private void assertScenarioUsageFilter(ScenarioUsageFilter usageFilter) {
        assertNotNull(usageFilter);
        assertEquals(PRODUCT_FAMILY, usageFilter.getProductFamily());
        assertEquals(USAGE_STATUS, usageFilter.getUsageStatus());
        assertEquals(PAYMENT_DATE, usageFilter.getPaymentDate());
        assertEquals(FISCAL_YEAR, usageFilter.getFiscalYear());
    }
}
