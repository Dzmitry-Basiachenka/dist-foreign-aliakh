package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageFilter;

import com.google.common.collect.Lists;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Verifies correctness of creating a scenario.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/25/17
 *
 * @author Aliaksandr Radkevich
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=create-scenario-service-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CreateScenarioTest {

    @Autowired
    private CreateScenarioTestBuilder testBuilder;

    // Test Case IDs: a5c08113-a8f3-434d-9fa9-1e6026e5e2b4, 5bb2c3eb-036e-452d-982f-62761ca6b1ae,
    // 7c7ea548-6845-478a-b39f-62a62feeddae, ab35246e-983d-455c-8bf1-2fd39fbdd527, 3d745a35-9256-4aae-8275-c4500d70bde8,
    // c3719d14-c4a2-4809-911a-915e3e1a7e91, ae37e5a6-88dd-48ae-bd5a-4a12134236ac, ee8ebb66-3584-4aaa-9c44-93e384428efb,
    // 15a4527f-f28e-4ab9-aed7-f5caf3ce5569, 9f2663b9-6006-46cf-a741-cb774ce69754
    @Test
    public void testCreateScenario() {
        testBuilder
            .withFilter(buildUsageFilter())
            .expectPreferences("prm/preferences_response.json")
            .expectRollups("prm/rollups_response.json", "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f",
                "00d4ae90-5fe7-47bf-ace1-781c8d76d4da", "038bf4aa-b6cc-430a-9b32-655954d95278",
                "756299b5-02ce-4f76-b0bc-ee2571cf906e", "019acfde-91be-43aa-8871-6305642bcb2c")
            .expectUsages(Lists.newArrayList(
                buildUsageForCreatedScenario(7000429266L, 1000009997L, "2871.0528", "6100.9872"),
                buildUsageForCreatedScenario(1000002859L, 1000002859L, "1450.0256", "3081.3044"),
                buildUsageForCreatedScenario(1000001820L, 1000001820L, "2175.0384", "11418.9516"),
                buildUsageForCreatedScenario(1000024497L, 1000024497L, "435.008", "2283.792"),
                buildUsageForCreatedScenario(1000002562L, 1000009997L, "1629.8304", "3463.3896"),
                buildUsage("4c014547-06f3-4840-94ff-6249730d537d", 1000003821L, 1000003821L, "29.00", "61.63")))
            .expectScenario(buildScenario("26348.4248", "34909.38", "8560.9552", "38520.00"))
            .build()
            .run();
    }

    // Test Case ID: e9a0e8f2-2ef1-4608-8221-1beb116a0748
    @Test
    public void testCreateScenarioNoRollupsNoPreferences() {
        testBuilder
            .withFilter(buildUsageFilter())
            .expectPreferences("prm/not_found_response.json")
            .expectRollups("prm/not_found_response.json", "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f",
                "00d4ae90-5fe7-47bf-ace1-781c8d76d4da", "038bf4aa-b6cc-430a-9b32-655954d95278",
                "756299b5-02ce-4f76-b0bc-ee2571cf906e", "019acfde-91be-43aa-8871-6305642bcb2c")
            .expectUsages(Lists.newArrayList(
                buildUsageForCreatedScenario(7000429266L, 7000429266L, "2871.0528", "6100.9872"),
                buildUsageForCreatedScenario(1000002859L, 1000002859L, "1450.0256", "3081.3044"),
                buildUsageForCreatedScenario(1000001820L, 1000001820L, "4350.0768", "9243.9132"),
                buildUsageForCreatedScenario(1000024497L, 1000024497L, "870.016", "1848.784"),
                buildUsageForCreatedScenario(1000002562L, 1000002562L, "1629.8304", "3463.3896"),
                buildUsage("4c014547-06f3-4840-94ff-6249730d537d", 1000003821L, 1000003821L, "29.00", "61.63")))
            .expectScenario(buildScenario("23738.3784", "34909.38", "11171.0016", "38520.00"))
            .build()
            .run();
    }

    private Scenario buildScenario(String netTotal, String grossAmount, String serviceFeeTotal, String reportedTotal) {
        Scenario scenario = new Scenario();
        scenario.setName("Test Scenario");
        scenario.setNetTotal(new BigDecimal(netTotal).setScale(10));
        scenario.setGrossTotal(new BigDecimal(grossAmount).setScale(10));
        scenario.setServiceFeeTotal(new BigDecimal(serviceFeeTotal).setScale(10));
        scenario.setReportedTotal(new BigDecimal(reportedTotal).setScale(2));
        scenario.setDescription("Scenario Description");
        return scenario;
    }

    private Usage buildUsageForCreatedScenario(Long rhAccountNumber, Long payeeAccountNumber, String serviceFeeAmount,
                                               String netAmount) {
        return buildUsage(null, rhAccountNumber, payeeAccountNumber, serviceFeeAmount, netAmount);
    }

    private Usage buildUsage(String scenarioId, Long rhAccountNumber, Long payeeAccountNumber, String serviceFeeAmount,
                             String netAmount) {
        Usage usage = new Usage();
        usage.setScenarioId(scenarioId);
        usage.setRightsholder(buildRightsholder(rhAccountNumber));
        usage.setPayee(buildRightsholder(payeeAccountNumber));
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount).setScale(10));
        usage.setNetAmount(new BigDecimal(netAmount).setScale(10));
        return usage;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("31ddaa1a-e60b-44ce-a968-0ca262870358"));
        return filter;
    }
}
