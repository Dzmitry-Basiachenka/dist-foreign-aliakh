package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.integration.rest.prm.IPrmPreferenceService;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CreateScenarioTest {

    private static final String RIGHTHOLDER_ID_1 = "038bf4aa-b6cc-430a-9b32-655954d95278";
    private static final String RIGHTHOLDER_ID_2 = "019acfde-91be-43aa-8871-6305642bcb2c";
    private static final String RIGHTHOLDER_ID_3 = "00d4ae90-5fe7-47bf-ace1-781c8d76d4da";
    private static final String RIGHTHOLDER_ID_4 = "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f";
    private static final String RIGHTHOLDER_ID_5 = "756299b5-02ce-4f76-b0bc-ee2571cf906e";
    private static final String RIGHTHOLDER_ID_6 = "37338ed1-7083-45e2-a96b-5872a7de3a98";
    private static final String RIGHTHOLDER_ID_7 = "624dcf73-a30f-4381-b6aa-c86d17198bd5";
    private static final String RIGHTHOLDER_ID_8 = "f366285a-ce46-48b0-96ee-cd35d62fb243";
    private static final String RIGHTHOLDER_ID_9 = "b0e6b1f6-89e9-4767-b143-db0f49f32769";
    private static final String RIGHTHOLDER_ID_10 = "60080587-a225-439c-81af-f016cb33aeac";

    @Autowired
    private CreateScenarioTestBuilder testBuilder;

    @Autowired
    @Qualifier("dist.common.integration.rest.prmPreferenceCacheService")
    private IPrmPreferenceService prmPreferenceService;

    @Before
    public void setUp() {
        ((ICacheService<?, ?>) prmPreferenceService).invalidateCache();
    }

    @Test
    public void testCreateFasScenario() {
        testBuilder
            .withFilter(buildUsageFilter("31ddaa1a-e60b-44ce-a968-0ca262870358", "FAS"))
            .expectPreferences("prm/preferences_response.json",
                RIGHTHOLDER_ID_1,
                RIGHTHOLDER_ID_2,
                RIGHTHOLDER_ID_3,
                RIGHTHOLDER_ID_4,
                RIGHTHOLDER_ID_5)
            .expectRollups("prm/fas_rollups_response.json",
                RIGHTHOLDER_ID_4,
                RIGHTHOLDER_ID_3,
                RIGHTHOLDER_ID_1,
                RIGHTHOLDER_ID_5,
                RIGHTHOLDER_ID_2)
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

    @Test
    public void testCreateFasScenarioNoRollupsNoPreferences() {
        testBuilder
            .withFilter(buildUsageFilter("31ddaa1a-e60b-44ce-a968-0ca262870358", "FAS"))
            .expectPreferences("prm/not_found_response.json",
                RIGHTHOLDER_ID_1,
                RIGHTHOLDER_ID_2,
                RIGHTHOLDER_ID_3,
                RIGHTHOLDER_ID_4,
                RIGHTHOLDER_ID_5)
            .expectRollups("prm/not_found_response.json",
                RIGHTHOLDER_ID_4,
                RIGHTHOLDER_ID_3,
                RIGHTHOLDER_ID_1,
                RIGHTHOLDER_ID_5,
                RIGHTHOLDER_ID_2)
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

    @Test
    public void testCreateClaScenario() {
        testBuilder
            .withFilter(buildUsageFilter("ce0ca941-1e16-4a3b-a991-b596189b4f22", "FAS2"))
            .expectPreferences("prm/not_found_response.json",
                RIGHTHOLDER_ID_6,
                RIGHTHOLDER_ID_7,
                RIGHTHOLDER_ID_8,
                RIGHTHOLDER_ID_9,
                RIGHTHOLDER_ID_10)
            .expectRollups("prm/cla_rollups_response.json",
                RIGHTHOLDER_ID_7,
                RIGHTHOLDER_ID_9,
                RIGHTHOLDER_ID_10,
                RIGHTHOLDER_ID_6,
                RIGHTHOLDER_ID_8)
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

    private UsageFilter buildUsageFilter(String batchId, String productFamily) {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton(batchId));
        filter.setProductFamilies(Sets.newHashSet(productFamily));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }
}
