package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.ScenarioUsageFilter;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=create-aacl-scenario-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class CreateAaclScenarioIntegrationTest {

    private static final String SCENARIO_NAME = "AACL Scenario";
    private static final String SCENARIO_DESCRIPTION = "AACL Scenario Description";

    @Autowired
    private CreateAaclScenarioIntegrationTestBuilder builder;

    @Test
    public void testCreateAaclScenario() throws IOException {
        builder
            .withFilter(buildUsageFilter())
            .withScenario(SCENARIO_NAME, SCENARIO_DESCRIPTION, buildAaclFields())
            .expectScenario(buildExpectedScenario())
            .expectScenarioFilter(new ScenarioUsageFilter(buildUsageFilter()))
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
        aaclFields.setTitleCutoffAmount(new BigDecimal("50.00"));
        aaclFields.setFundPoolId("753bd683-1db2-47ec-8332-136139c512d0");
        aaclFields.setUsageAges(Arrays.asList(
            buildUsageAge(2019, new BigDecimal("1.00")),
            buildUsageAge(2017, new BigDecimal("0.75"))));
        aaclFields.setPublicationTypes(Arrays.asList(
            buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", "STMA Journal", new BigDecimal("1.24")),
            buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", "Book", new BigDecimal("4.37"))));
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

    private PublicationType buildPublicationType(String id, String name, BigDecimal weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(id);
        pubType.setName(name);
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
