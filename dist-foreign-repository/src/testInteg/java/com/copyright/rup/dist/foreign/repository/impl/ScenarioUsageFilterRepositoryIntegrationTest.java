package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.repository.api.IScenarioUsageFilterRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Set;

/**
 * Verifies {@link ScenarioUsageFilterRepository}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/05/2018
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ScenarioUsageFilterRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "scenario-usage-filter-repository-integration-test/";
    private static final String SCENARIO_ID = "2bb64bac-8526-438c-8676-974dd9305bac";
    private static final String FILTER_ID = RupPersistUtils.generateUuid();
    private static final String PRODUCT_FAMILY = "FAS";
    private static final UsageStatusEnum USAGE_STATUS = UsageStatusEnum.ELIGIBLE;
    private static final LocalDate PAYMENT_DATE = LocalDate.now();
    private static final Integer FISCAL_YEAR = LocalDate.now().getYear();
    private static final Integer USAGE_PERIOD = LocalDate.now().getYear();
    private static final Set<Long> RH_ACCOUNT_NUMBERS = Set.of(2000017004L);
    private static final Set<String> USAGE_BATCHES_IDS = Set.of("e1c64cac-3f2b-4105-8056-6660e1ec461a");

    @Autowired
    private IScenarioUsageFilterRepository scenarioUsageFilterRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "insert.groovy")
    public void testInsert() {
        ScenarioUsageFilter actualUsageFilter = buildScenarioUsageFilter();
        scenarioUsageFilterRepository.insert(actualUsageFilter);
        ScenarioUsageFilter expectedUsageFilter = scenarioUsageFilterRepository.findByScenarioId(SCENARIO_ID);
        assertScenarioUsageFilter(expectedUsageFilter);
        assertEquals(0, expectedUsageFilter.getRhAccountNumbers().size());
        assertEquals(0, expectedUsageFilter.getUsageBatches().size());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testFindByScenarioId() {
        ScenarioUsageFilter actualUsageFilter = buildScenarioUsageFilter();
        scenarioUsageFilterRepository.insert(actualUsageFilter);
        scenarioUsageFilterRepository.insertUsageBatchesIds(actualUsageFilter.getId(),
            actualUsageFilter.getUsageBatchesIds());
        scenarioUsageFilterRepository.insertRhAccountNumbers(actualUsageFilter.getId(),
            actualUsageFilter.getRhAccountNumbers());
        ScenarioUsageFilter usageFilter = scenarioUsageFilterRepository.findByScenarioId(SCENARIO_ID);
        assertScenarioUsageFilter(usageFilter);
        assertEquals(RH_ACCOUNT_NUMBERS, usageFilter.getRhAccountNumbers());
        assertEquals(USAGE_BATCHES_IDS, usageFilter.getUsageBatchesIds());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-scenario-id.groovy")
    public void testDeleteByScenarioId() {
        ScenarioUsageFilter actualUsageFilter = buildScenarioUsageFilter();
        scenarioUsageFilterRepository.insert(actualUsageFilter);
        scenarioUsageFilterRepository.insertUsageBatchesIds(actualUsageFilter.getId(),
            actualUsageFilter.getUsageBatchesIds());
        scenarioUsageFilterRepository.insertRhAccountNumbers(actualUsageFilter.getId(),
            actualUsageFilter.getRhAccountNumbers());
        scenarioUsageFilterRepository.deleteByScenarioId(SCENARIO_ID);
        assertNull(scenarioUsageFilterRepository.findByScenarioId(SCENARIO_ID));
    }

    private ScenarioUsageFilter buildScenarioUsageFilter() {
        ScenarioUsageFilter usageFilter = new ScenarioUsageFilter();
        usageFilter.setId(FILTER_ID);
        usageFilter.setScenarioId(SCENARIO_ID);
        usageFilter.setProductFamily(PRODUCT_FAMILY);
        usageFilter.setUsageStatus(USAGE_STATUS);
        usageFilter.setPaymentDate(PAYMENT_DATE);
        usageFilter.setFiscalYear(FISCAL_YEAR);
        usageFilter.setUsagePeriod(USAGE_PERIOD);
        usageFilter.setRhAccountNumbers(RH_ACCOUNT_NUMBERS);
        usageFilter.setUsageBatchesIds(USAGE_BATCHES_IDS);
        return usageFilter;
    }

    private void assertScenarioUsageFilter(ScenarioUsageFilter usageFilter) {
        assertNotNull(usageFilter);
        assertEquals(FILTER_ID, usageFilter.getId());
        assertEquals(SCENARIO_ID, usageFilter.getScenarioId());
        assertEquals(PRODUCT_FAMILY, usageFilter.getProductFamily());
        assertEquals(USAGE_STATUS, usageFilter.getUsageStatus());
        assertEquals(PAYMENT_DATE, usageFilter.getPaymentDate());
        assertEquals(FISCAL_YEAR, usageFilter.getFiscalYear());
        assertEquals(USAGE_PERIOD, usageFilter.getUsagePeriod());
    }
}
