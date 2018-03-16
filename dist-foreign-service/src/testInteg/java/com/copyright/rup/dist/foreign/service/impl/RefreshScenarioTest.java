package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;

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

/**
 * Verifies correctness of refreshing a scenario.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 03/15/2018
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=refresh-scenario-service-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RefreshScenarioTest {

    @Autowired
    private RefreshScenarioTestBuilder testBuilder;

    @Test
    public void testRefreshFasScenario() {
        testBuilder
            .withScenario("e6e2fe6c-4c47-4ca1-bf32-591af4c13060")
            .expectPreferences("prm/preferences_response.json")
            .expectRollups("prm/fas_rollups_response.json", "038bf4aa-b6cc-430a-9b32-655954d95278",
                "019acfde-91be-43aa-8871-6305642bcb2c")
            .expectUsages(Lists.newArrayList(
                buildUsageForCreatedScenario(7000429266L, 1000009997L, "2871.0528", "6100.9872"),
                buildUsageForCreatedScenario(1000002859L, 1000002859L, "1450.0256", "3081.3044"),
                buildUsageForCreatedScenario(1000001820L, 1000001820L, "2175.0384", "11418.9516"),
                buildUsageForCreatedScenario(1000024497L, 1000024497L, "435.008", "2283.792"),
                buildUsageForCreatedScenario(1000002562L, 1000009997L, "1629.8304", "3463.3896")))
            .expectScenario(buildScenario("26348.4248", "34909.38", "8560.9552", "38520.00"))
            .build()
            .run();
    }

    @Test
    public void testRefreshClaScenario() {
        testBuilder
            .withScenario("8fba95e3-c706-47f7-a1c8-fad9af5e31a9")
            .expectPreferences("prm/not_found_response.json")
            .expectRollups("prm/cla_rollups_response.json", "624dcf73-a30f-4381-b6aa-c86d17198bd5",
                "37338ed1-7083-45e2-a96b-5872a7de3a98")
            .expectUsages(Lists.newArrayList(
                buildUsageForCreatedScenario(2000133267L, 2000017000L, "897.204", "8074.836"),
                buildUsageForCreatedScenario(2000073957L, 2000073957L, "1450.0256", "3081.3044"),
                buildUsageForCreatedScenario(7001508482L, 7001508482L, "4350.0768", "9243.9132"),
                buildUsageForCreatedScenario(1000024950L, 1000024950L, "870.016", "1848.784"),
                buildUsageForCreatedScenario(2000139286L, 2000017000L, "509.322", "4583.898")))
            .expectScenario(buildScenario("26832.7356", "34909.38", "8076.6444", "38520.00"))
            .build()
            .run();
    }

    private Scenario buildScenario(String netTotal, String grossAmount, String serviceFeeTotal, String reportedTotal) {
        Scenario scenario = new Scenario();
        scenario.setName("Test Scenario");
        scenario.setNetTotal(new BigDecimal(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setGrossTotal(new BigDecimal(grossAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setServiceFeeTotal(new BigDecimal(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setReportedTotal(new BigDecimal(reportedTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
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
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        usage.setNetAmount(new BigDecimal(netAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        return usage;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
