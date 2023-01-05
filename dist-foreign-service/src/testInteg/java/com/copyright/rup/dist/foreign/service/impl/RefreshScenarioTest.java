package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
//TODO: split test data into separate files for each test method
@TestData(fileName = "refresh-scenario-service-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class RefreshScenarioTest {

    private static final String RIGHTHOLDER_ID_1 = "019acfde-91be-43aa-8871-6305642bcb2c";
    private static final String RIGHTHOLDER_ID_2 = "37338ed1-7083-45e2-a96b-5872a7de3a98";
    private static final String RIGHTHOLDER_ID_3 = "624dcf73-a30f-4381-b6aa-c86d17198bd5";
    private static final String SERVICE_FEE_32 = "0.32000";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
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
            .withProductFamily(FAS_PRODUCT_FAMILY)
            .withScenario("e6e2fe6c-4c47-4ca1-bf32-591af4c13060")
            .expectPreferences("prm/preferences_response.json", RIGHTHOLDER_ID_1)
            .expectRollups("prm/fas_rollups_response.json", RIGHTHOLDER_ID_1)
            .expectUsages(Arrays.asList(
                buildUsage("0e49fd89-f094-4023-b729-afe240272ebe", 1000024497L, 1000024497L, "435.008", "2283.792",
                    FAS_PRODUCT_FAMILY, 122235139L, "3000.00", "2718.8000000000", "0.16000", true, true),
                buildUsage("b1f0b236-3ae9-4a60-9fab-61db84199dss", 7000429266L, 1000009997L, "2871.0528", "6100.9872",
                    FAS_PRODUCT_FAMILY, 122235134L, "9900.00", "8972.0400000000", SERVICE_FEE_32, false, true),
                buildUsage("cbda7c0d-c455-4d9f-b097-89db8d933264", 1000001820L, 1000001820L, "814.9152", "4278.3048",
                    FAS_PRODUCT_FAMILY, 471137469L, "5620.00", "5093.2200000000", "0.16000", true, true),
                buildUsage("cf38d390-11bb-4af7-9685-e034c9c32fb6", 1000002859L, 1000002859L, "1450.0256", "3081.3044",
                    FAS_PRODUCT_FAMILY, 243904752L, "5000.00", "4531.3300000000", SERVICE_FEE_32, false, false),
                buildUsage("d0816728-4726-483d-91ff-8f24fa605e01", 1000001820L, 1000001820L, "2175.0384", "11418.9516",
                    FAS_PRODUCT_FAMILY, 471137967L, "15000.00", "13593.9900000000", "0.16000", true, true),
                buildUsage("0c4c3878-20b8-49e3-a967-91e8b73c7570", 7000429266L, 1000009997L, "1629.8304", "3463.3896",
                    FAS_PRODUCT_FAMILY, 122235134L, "5620.00", "5093.2200000000", SERVICE_FEE_32, false, true)))
            .expectScenario(buildScenario("40002.60", "30626.7296", "9375.8704"))
            .expectScenarioAudit(buildScenarioAudit())
            .build()
            .run();
    }

    @Test
    public void testRefreshClaScenario() {
        testBuilder
            .withProductFamily(FAS2_PRODUCT_FAMILY)
            .withScenario("8fba95e3-c706-47f7-a1c8-fad9af5e31a9")
            .expectPreferences("prm/not_found_response.json",
                "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f", RIGHTHOLDER_ID_3, RIGHTHOLDER_ID_2)
            .expectRollups("prm/cla_rollups_response.json", RIGHTHOLDER_ID_3, RIGHTHOLDER_ID_2)
            .expectUsages(Arrays.asList(buildUsage("007aff49-831c-46ab-9528-2e043f7564e9", 2000073957L,
                2000073957L, "1450.0256", "3081.3044", FAS2_PRODUCT_FAMILY, 243904752L, "5000.00", "4531.3300000000",
                SERVICE_FEE_32, false, false),
                buildUsage("3c3a3329-d64c-45a9-962c-f247e4bbf3b6", 2000139286L, 2000017000L, "509.322", "4583.898",
                    FAS2_PRODUCT_FAMILY, 471137469L, "5620.00", "5093.2200000000", "0.10000", false, false),
                buildUsage("455681ae-a02d-4cb9-a881-fcdc46cc5585", 7001508482L, 7001508482L, "4350.0768", "9243.9132",
                    FAS2_PRODUCT_FAMILY, 471137967L, "15000.00", "13593.9900000000", SERVICE_FEE_32, false, false),
                buildUsage("8fc81e08-3611-4697-8059-6c970ee5d643", 2000133267L, 2000017000L, "897.204", "8074.836",
                    FAS2_PRODUCT_FAMILY, 122235134L, "9900.00", "8972.0400000000", "0.10000", false, false),
                buildUsage("ec5c39b5-4c16-40a7-b1c8-730320971f11", 1000024950L, 1000024950L, "870.016", "1848.784",
                    FAS2_PRODUCT_FAMILY, 122235139L, "3000.00", "2718.8000000000", SERVICE_FEE_32, false, false)))
            .expectScenario(buildScenario("34909.38", "26832.7356", "8076.6444"))
            .expectScenarioAudit(buildScenarioAudit())
            .build()
            .run();
    }

    private Scenario buildScenario(String grossAmount, String netTotal, String serviceFeeTotal) {
        Scenario scenario = new Scenario();
        scenario.setName("Test Scenario");
        scenario.setNetTotal(new BigDecimal(netTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setGrossTotal(new BigDecimal(grossAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setServiceFeeTotal(new BigDecimal(serviceFeeTotal).setScale(10, BigDecimal.ROUND_HALF_UP));
        scenario.setDescription("Scenario Description");
        return scenario;
    }

    private List<Pair<ScenarioActionTypeEnum, String>> buildScenarioAudit() {
        return List.of(Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY));
    }

    private Usage buildUsage(String usageId, Long rhAccountNumber, Long payeeAccountNumber, String serviceFeeAmount,
                             String netAmount, String productFamily, Long wrWrkInst, String reportedValue,
                             String grossAmount, String serviceFee, boolean rhParticipating,
                             boolean payeeParticipating) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        usage.setRightsholder(buildRightsholder(rhAccountNumber));
        usage.setPayee(buildRightsholder(payeeAccountNumber));
        usage.setProductFamily(productFamily);
        usage.setReportedValue(new BigDecimal(reportedValue));
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setServiceFee(new BigDecimal(serviceFee));
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        usage.setNetAmount(new BigDecimal(netAmount).setScale(10, BigDecimal.ROUND_HALF_UP));
        usage.setComment("usage from usages.csv");
        usage.setRhParticipating(rhParticipating);
        usage.setPayeeParticipating(payeeParticipating);
        return usage;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }
}
