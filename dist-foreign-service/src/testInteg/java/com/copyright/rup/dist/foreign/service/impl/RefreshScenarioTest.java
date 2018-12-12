package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

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
public class RefreshScenarioTest {

    private static final String RIGHTHOLDER_ID_1 = "019acfde-91be-43aa-8871-6305642bcb2c";
    private static final String RIGHTHOLDER_ID_2 = "37338ed1-7083-45e2-a96b-5872a7de3a98";
    private static final String RIGHTHOLDER_ID_3 = "624dcf73-a30f-4381-b6aa-c86d17198bd5";
    @Autowired
    private RefreshScenarioTestBuilder testBuilder;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testRefreshFasScenario() {
        testBuilder
            .withScenario("e6e2fe6c-4c47-4ca1-bf32-591af4c13060")
            .expectPreferences("prm/preferences_response.json",
                RIGHTHOLDER_ID_1)
            .expectRollups("prm/fas_rollups_response.json",
                RIGHTHOLDER_ID_1)
            .expectUsages(Lists.newArrayList(
                buildUsage("0e49fd89-f094-4023-b729-afe240272ebe", 1000024497L, 1000024497L, "435.008", "2283.792"),
                buildUsage("b1f0b236-3ae9-4a60-9fab-61db84199dss", 7000429266L, 1000009997L, "2871.0528", "6100.9872"),
                buildUsage("cbda7c0d-c455-4d9f-b097-89db8d933264", 1000001820L, 1000001820L, "1629.8304", "3463.3896"),
                buildUsage("cf38d390-11bb-4af7-9685-e034c9c32fb6", 1000002859L, 1000002859L, "1450.0256", "3081.3044"),
                buildUsage("d0816728-4726-483d-91ff-8f24fa605e01", 1000001820L, 1000001820L, "2175.0384",
                    "11418.9516")))
            .expectScenario(buildScenario("26348.4248", "34909.38", "8560.9552", "38520.00"))
            .build()
            .run();
    }

    @Test
    public void testRefreshClaScenario() {
        testBuilder
            .withScenario("8fba95e3-c706-47f7-a1c8-fad9af5e31a9")
            .expectPreferences("prm/not_found_response.json",
                RIGHTHOLDER_ID_2,
                RIGHTHOLDER_ID_3)
            .expectRollups("prm/cla_rollups_response.json",
                RIGHTHOLDER_ID_3,
                RIGHTHOLDER_ID_2)
            .expectUsages(Lists.newArrayList(
                buildUsage("007aff49-831c-46ab-9528-2e043f7564e9", 2000073957L, 2000073957L, "1450.0256", "3081.3044"),
                buildUsage("3c3a3329-d64c-45a9-962c-f247e4bbf3b6", 2000139286L, 2000017000L, "509.322", "4583.898"),
                buildUsage("455681ae-a02d-4cb9-a881-fcdc46cc5585", 7001508482L, 7001508482L, "4350.0768", "9243.9132"),
                buildUsage("8fc81e08-3611-4697-8059-6c970ee5d643", 2000133267L, 2000017000L, "897.204", "8074.836"),
                buildUsage("ec5c39b5-4c16-40a7-b1c8-730320971f11", 1000024950L, 1000024950L, "870.016", "1848.784")))
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

    private Usage buildUsage(String usageId, Long rhAccountNumber, Long payeeAccountNumber,
                             String serviceFeeAmount, String netAmount) {
        Usage usage = new Usage();
        usage.setId(usageId);
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
