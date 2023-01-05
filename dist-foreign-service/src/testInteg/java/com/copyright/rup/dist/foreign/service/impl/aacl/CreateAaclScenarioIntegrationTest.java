package com.copyright.rup.dist.foreign.service.impl.aacl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies AACL scenario creation.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 3/17/20
 *
 * @author Stanislau Rudak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "create-aacl-scenario-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class CreateAaclScenarioIntegrationTest {

    private static final String SCENARIO_NAME = "AACL Scenario";
    private static final String SCENARIO_DESCRIPTION = "AACL Scenario Description";

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;
    @Autowired
    private CreateAaclScenarioIntegrationTestBuilder builder;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testCreateAaclScenario() throws IOException {
        builder
            .withFilter(buildUsageFilter())
            .expectRollups("prm/aacl_rollups_response.json", "b0e6b1f6-89e9-4767-b143-db0f49f32769",
                "60080587-a225-439c-81af-f016cb33aeac", "019acfde-91be-43aa-8871-6305642bcb2c")
            .withScenario(SCENARIO_NAME, SCENARIO_DESCRIPTION, buildAaclFields())
            .expectScenario(buildExpectedScenario())
            .expectScenarioFilter(new ScenarioUsageFilter(buildUsageFilter()))
            .expectScenarioAudit(List.of(Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .expectUsages("usage/aacl/aacl_expected_usages_for_create_scenario.json")
            .build()
            .run();
    }

    private Scenario buildExpectedScenario() {
        Scenario scenario = new Scenario();
        scenario.setName(SCENARIO_NAME);
        scenario.setDescription(SCENARIO_DESCRIPTION);
        scenario.setAaclFields(buildAaclFields());
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        return scenario;
    }

    private AaclFields buildAaclFields() {
        AaclFields aaclFields = new AaclFields();
        aaclFields.setFundPoolId("753bd683-1db2-47ec-8332-136139c512d0");
        aaclFields.setUsageAges(Arrays.asList(
            buildUsageAge(2019, new BigDecimal("1.00")),
            buildUsageAge(2017, new BigDecimal("0.75"))));
        aaclFields.setPublicationTypes(Arrays.asList(
            buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", new BigDecimal("1.24")),
            buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", new BigDecimal("4.37"))));
        aaclFields.setDetailLicenseeClasses(Arrays.asList(
            buildDetailClass(108, 141),
            buildDetailClass(113, 141),
            buildDetailClass(110, 143)));
        return aaclFields;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily("AACL");
        filter.setUsageBatchesIds(Collections.singleton("ea7b6e8d-8454-4052-b639-c0fdb0a3145c"));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private UsageAge buildUsageAge(Integer period, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(period);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPublicationType(String id, BigDecimal weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setWeight(weight);
        return pubType;
    }

    private DetailLicenseeClass buildDetailClass(Integer detailClassId, Integer aggregateClassId) {
        DetailLicenseeClass detailClass = new DetailLicenseeClass();
        AggregateLicenseeClass aggregateClass = new AggregateLicenseeClass();
        detailClass.setId(detailClassId);
        aggregateClass.setId(aggregateClassId);
        detailClass.setAggregateLicenseeClass(aggregateClass);
        return detailClass;
    }
}
