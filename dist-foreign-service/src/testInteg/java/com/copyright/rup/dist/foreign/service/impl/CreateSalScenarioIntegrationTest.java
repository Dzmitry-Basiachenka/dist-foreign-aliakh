package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.SalFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Verifies SAL scenario creation.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 10/12/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "create-sal-scenario-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
@Transactional
public class CreateSalScenarioIntegrationTest {

    private static final String SCENARIO_NAME = "SAL Scenario";
    private static final String SCENARIO_DESCRIPTION = "SAL Scenario description";

    @Autowired
    private CreateSalScenarioIntegrationTestBuilder builder;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testCreateSalScenario() throws IOException {
        builder
            .withFilter(buildUsageFilter())
            .expectRollups("prm/sal_rollups_response.json", "05844db0-e0e4-4423-8966-7f1c6160f000")
            .withScenario(SCENARIO_NAME, SCENARIO_DESCRIPTION, buildSalFields())
            .expectScenario(buildExpectedScenario())
            .expectScenarioFilter(new ScenarioUsageFilter(buildUsageFilter()))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .expectUsages("usage/sal/sal_expected_usages_for_create_scenario.json")
            .build()
            .run();
    }

    private Scenario buildExpectedScenario() {
        Scenario scenario = new Scenario();
        scenario.setName(SCENARIO_NAME);
        scenario.setDescription(SCENARIO_DESCRIPTION);
        scenario.setSalFields(buildSalFields());
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        return scenario;
    }

    private SalFields buildSalFields() {
        SalFields salFields = new SalFields();
        salFields.setFundPoolId("462111b6-5d30-4a43-a35b-14796d34d847");
        return salFields;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("SAL");
        filter.setUsageBatchesIds(Collections.singleton("85df79f3-7e3f-4d74-9931-9aa513195815"));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }
}
