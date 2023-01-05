package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class CreateScenarioIntegrationTest {

    private static final String FOLDER_NAME = "create-scenario-integration-test/";
    private static final String CREATE_FAS_SCENARIO = FOLDER_NAME + "create-fas-scenario.groovy";
    private static final String CREATE_NTS_SCENARIO = FOLDER_NAME + "create-nts-scenario.groovy";
    private static final String RIGHTSHOLDER_ID_1 = "038bf4aa-b6cc-430a-9b32-655954d95278";
    private static final String RIGHTSHOLDER_ID_2 = "019acfde-91be-43aa-8871-6305642bcb2c";
    private static final String RIGHTSHOLDER_ID_3 = "00d4ae90-5fe7-47bf-ace1-781c8d76d4da";
    private static final String RIGHTSHOLDER_ID_4 = "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f";
    private static final String RIGHTSHOLDER_ID_5 = "756299b5-02ce-4f76-b0bc-ee2571cf906e";
    private static final String RIGHTSHOLDER_ID_6 = "37338ed1-7083-45e2-a96b-5872a7de3a98";
    private static final String RIGHTSHOLDER_ID_7 = "624dcf73-a30f-4381-b6aa-c86d17198bd5";
    private static final String RIGHTSHOLDER_ID_8 = "f366285a-ce46-48b0-96ee-cd35d62fb243";
    private static final String RIGHTSHOLDER_ID_9 = "b0e6b1f6-89e9-4767-b143-db0f49f32769";
    private static final String RIGHTSHOLDER_ID_10 = "60080587-a225-439c-81af-f016cb33aeac";
    private static final BigDecimal SERVICE_FEE_32 = new BigDecimal("0.32000");
    private static final String AMOUNT_ZERO = "0.00";
    private static final String USAGE_ID_6 = "3d921c9c-8036-421a-ab05-39cc4d3c3b68";
    private static final String USAGE_ID_7 = "91813777-3dd4-4f5f-bb83-ca145866317d";
    private static final String FAS_PRODUCT_FAMILY = "FAS";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";
    private static final String NTS_PRODUCT_FAMILY = "NTS";
    private static final String REPORTED_VALUE_1 = "59.30";
    private static final String REPORTED_VALUE_2 = "896.72";

    @Autowired
    private CreateScenarioIntegrationTestBuilder testBuilder;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
        testBuilder.reset();
    }

    @Test
    @TestData(fileName = CREATE_FAS_SCENARIO)
    public void testCreateFasScenario() {
        testBuilder
            .withFilter(buildUsageFilter("31ddaa1a-e60b-44ce-a968-0ca262870358", "FAS"))
            .expectPreferences("prm/preferences_response.json",
                RIGHTSHOLDER_ID_4, RIGHTSHOLDER_ID_3, RIGHTSHOLDER_ID_1, RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectRollups("prm/fas_rollups_response.json",
                RIGHTSHOLDER_ID_4, RIGHTSHOLDER_ID_3, RIGHTSHOLDER_ID_1, RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectUsages(Arrays.asList(
                buildUsage("b1f0b236-3ae9-4a60-9fab-61db84199dss", 7000429266L, 1000009997L, SERVICE_FEE_32,
                    "2871.0528", "6100.9872", "8972.0400000000", 122235134L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("9900.00"), false, false),
                buildUsage("cf38d390-11bb-4af7-9685-e034c9c32fb6", 1000002859L, 2000017000L, SERVICE_FEE_32,
                    "1450.0256", "3081.3044", "4531.3300000000", 243904752L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("5000.00"), false, false),
                buildUsage("d0816728-4726-483d-91ff-8f24fa605e01", 1000001820L, 1000001820L, new BigDecimal("0.16000"),
                    "2175.0384", "11418.9516", "13593.9900000000", 471137967L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("15000.00"), true, true),
                buildUsage("0e49fd89-f094-4023-b729-afe240272ebe", 1000024497L, 1000024497L, new BigDecimal("0.16000"),
                    "435.008", "2283.792", "2718.8000000000", 122235139L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("3000.00"), true, true),
                buildUsage("cbda7c0d-c455-4d9f-b097-89db8d933264", 1000002562L, 1000009997L, SERVICE_FEE_32,
                    "1629.8304", "3463.3896", "5093.2200000000", 471137469L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("5620.00"), false, false)))
            .expectUsagesAlreadyInScenario(Collections.singletonList(
                buildUsage("fcdaea01-2439-4c51-b3e2-23649cf710c7", 1000003821L, 1000003821L, SERVICE_FEE_32, "29.00",
                    "61.63", "90.6300000000", 471137470L, FAS_PRODUCT_FAMILY, new BigDecimal("100.00"), false, false)))
            .expectScenario(buildScenario("26348.4248", "34909.38", "8560.9552", null))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .build()
            .run();
    }

    @Test
    @TestData(fileName = CREATE_FAS_SCENARIO)
    public void testCreateFasScenarioNoRollupsNoPreferences() {
        testBuilder
            .withFilter(buildUsageFilter("31ddaa1a-e60b-44ce-a968-0ca262870358", "FAS"))
            .expectPreferences("prm/not_found_response.json",
                RIGHTSHOLDER_ID_4, RIGHTSHOLDER_ID_3, RIGHTSHOLDER_ID_1, RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectRollups("prm/not_found_response.json",
                RIGHTSHOLDER_ID_4, RIGHTSHOLDER_ID_3, RIGHTSHOLDER_ID_1, RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectUsages(Arrays.asList(
                buildUsage("b1f0b236-3ae9-4a60-9fab-61db84199dss", 7000429266L, 7000429266L, SERVICE_FEE_32,
                    "2871.0528", "6100.9872", "8972.0400000000", 122235134L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("9900.00"), false, false),
                buildUsage("cf38d390-11bb-4af7-9685-e034c9c32fb6", 1000002859L, 1000002859L, SERVICE_FEE_32,
                    "1450.0256", "3081.3044", "4531.3300000000", 243904752L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("5000.00"), false, false),
                buildUsage("d0816728-4726-483d-91ff-8f24fa605e01", 1000001820L, 1000001820L, SERVICE_FEE_32,
                    "4350.0768", "9243.9132", "13593.9900000000", 471137967L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("15000.00"), false, false),
                buildUsage("0e49fd89-f094-4023-b729-afe240272ebe", 1000024497L, 1000024497L, SERVICE_FEE_32, "870.016",
                    "1848.784", "2718.8000000000", 122235139L, FAS_PRODUCT_FAMILY, new BigDecimal("3000.00"), false,
                    false),
                buildUsage("cbda7c0d-c455-4d9f-b097-89db8d933264", 1000002562L, 1000002562L, SERVICE_FEE_32,
                    "1629.8304", "3463.3896", "5093.2200000000", 471137469L, FAS_PRODUCT_FAMILY,
                    new BigDecimal("5620.00"), false, false)))
            .expectUsagesAlreadyInScenario(Collections.singletonList(
                buildUsage("fcdaea01-2439-4c51-b3e2-23649cf710c7", 1000003821L, 1000003821L, SERVICE_FEE_32, "29.00",
                    "61.63", "90.6300000000", 471137470L, FAS_PRODUCT_FAMILY, new BigDecimal("100.00"), false, false)))
            .expectScenario(buildScenario("23738.3784", "34909.38", "11171.0016", null))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .build()
            .run();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "create-cla-scenario.groovy")
    public void testCreateClaScenario() {
        testBuilder
            .withFilter(buildUsageFilter("ce0ca941-1e16-4a3b-a991-b596189b4f22", "FAS2"))
            .expectPreferences("prm/not_found_response.json", RIGHTSHOLDER_ID_4, RIGHTSHOLDER_ID_9, RIGHTSHOLDER_ID_7,
                RIGHTSHOLDER_ID_10, RIGHTSHOLDER_ID_6, RIGHTSHOLDER_ID_8)
            .expectRollups("prm/cla_rollups_response.json",
                RIGHTSHOLDER_ID_9, RIGHTSHOLDER_ID_7, RIGHTSHOLDER_ID_10, RIGHTSHOLDER_ID_6, RIGHTSHOLDER_ID_8)
            .expectUsages(Arrays.asList(
                buildUsage("8fc81e08-3611-4697-8059-6c970ee5d643", 2000133267L, 2000017000L,
                    new BigDecimal("0.10000"), "897.204", "8074.836", "8972.0400000000", 122235134L,
                    FAS2_PRODUCT_FAMILY, new BigDecimal("9900.00"), false, false),
                buildUsage("007aff49-831c-46ab-9528-2e043f7564e9", 2000073957L, 2000073957L,
                    SERVICE_FEE_32, "1450.0256", "3081.3044", "4531.3300000000", 243904752L, FAS2_PRODUCT_FAMILY,
                    new BigDecimal("5000.00"), false, false),
                buildUsage("455681ae-a02d-4cb9-a881-fcdc46cc5585", 7001508482L, 7001508482L,
                    SERVICE_FEE_32, "4350.0768", "9243.9132", "13593.9900000000", 471137967L, FAS2_PRODUCT_FAMILY,
                    new BigDecimal("15000.00"), false, false),
                buildUsage("ec5c39b5-4c16-40a7-b1c8-730320971f11", 1000024950L, 1000024950L,
                    SERVICE_FEE_32, "870.016", "1848.784", "2718.8000000000", 122235139L, FAS2_PRODUCT_FAMILY,
                    new BigDecimal("3000.00"), false, false),
                buildUsage("3c3a3329-d64c-45a9-962c-f247e4bbf3b6", 2000139286L, 2000017000L,
                    new BigDecimal("0.10000"), "509.322", "4583.898", "5093.2200000000", 471137469L,
                    FAS2_PRODUCT_FAMILY, new BigDecimal("5620.00"), false, false)))
            .expectScenario(buildScenario("26832.7356", "34909.38", "8076.6444", null))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .build()
            .run();
    }

    @Test
    @TestData(fileName = CREATE_NTS_SCENARIO)
    public void testCreateNtsScenario() {
        testBuilder
            .withFilter(buildUsageFilter("26282dbd-3463-58d7-c927-03d3458a656a", "NTS"))
            .expectRollups("prm/nts_rollups_response.json", RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectPreferences("prm/preferences_response.json", RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectUsages(Arrays.asList(
                buildUsage(USAGE_ID_6, 7000429266L, 1000009997L, SERVICE_FEE_32, "83.3578205256", "177.1353686169",
                    "260.4931891425", 135632563L, NTS_PRODUCT_FAMILY, new BigDecimal(REPORTED_VALUE_1), false, false),
                buildUsage(USAGE_ID_7, 1000024497L, 1000024497L, SERVICE_FEE_32, "68.6421794744", "145.8646313831",
                    "214.5068108575", 145632563L, NTS_PRODUCT_FAMILY, new BigDecimal(REPORTED_VALUE_2), false, false)))
            .withFilter(buildUsageFilter("26282dbd-3463-58d7-c927-03d3458a656a", NTS_PRODUCT_FAMILY))
            .expectUsages(Arrays.asList(
                buildUsage(USAGE_ID_6, 7000429266L, 1000009997L, SERVICE_FEE_32, "83.3578205256",
                    "177.1353686169", "260.4931891425", 135632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_1), false, false),
                buildUsage(USAGE_ID_7, 1000024497L, 1000024497L, SERVICE_FEE_32,
                    "68.6421794744", "145.8646313831", "214.5068108575", 145632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_2), false, false)))
            .expectScenarioExcludedUsages(Arrays.asList(
                buildScenarioExcludedUsage("669cf304-0921-41a2-85d5-c3905e77c696", 1000002859L),
                buildScenarioExcludedUsage("6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c", 1000001820L),
                buildScenarioExcludedUsage("e001c596-a66f-4fd3-b34c-5ef65a215d68", 1000002562L)))
            .expectScenario(buildScenario("323.00", "475.00", "152.00",
                buildNtsFields(new BigDecimal("100.00"), new BigDecimal("255.0"), BigDecimal.ZERO,
                    "c7ca1ca1-7cd8-49cc-aaeb-ac53fe62d903")))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .build()
            .run();
    }

    @Test
    @TestData(fileName = CREATE_NTS_SCENARIO)
    public void testCreateNtsScenarioWithPostServiceFeeAmount() {
        testBuilder
            .withFilter(buildUsageFilter("26282dbd-3463-58d7-c927-03d3458a656a", "NTS"))
            .expectRollups("prm/nts_rollups_response.json", RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectPreferences("prm/preferences_response.json", RIGHTSHOLDER_ID_5, RIGHTSHOLDER_ID_2)
            .expectUsages(Arrays.asList(
                buildUsage(USAGE_ID_6, 7000429266L, 1000009997L, SERVICE_FEE_32, "5.2647044542",
                    "559.8684143076", "565.1331187618", 135632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_1), false, false),
                buildUsage(USAGE_ID_7, 1000024497L, 1000024497L, SERVICE_FEE_32, "4.3352955458",
                    "461.0315856924", "465.3668812382", 145632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_2), false, false)))
            .expectUsages(Arrays.asList(
                buildUsage(USAGE_ID_6, 7000429266L, 1000009997L, SERVICE_FEE_32, "5.2647044542",
                    "559.8684143076", "565.1331187618", 135632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_1), false, false),
                buildUsage(USAGE_ID_7, 1000024497L, 1000024497L, SERVICE_FEE_32, "4.3352955458",
                    "461.0315856924", "465.3668812382", 145632563L, NTS_PRODUCT_FAMILY,
                    new BigDecimal(REPORTED_VALUE_2), false, false)))
            .expectScenarioExcludedUsages(Arrays.asList(
                buildScenarioExcludedUsage("669cf304-0921-41a2-85d5-c3905e77c696", 1000002859L),
                buildScenarioExcludedUsage("6402d5c8-ba80-4966-a7cc-34ba1fdc1d9c", 1000001820L),
                buildScenarioExcludedUsage("e001c596-a66f-4fd3-b34c-5ef65a215d68", 1000002562L)))
            .expectScenario(buildScenario("1020.9000000000", "1030.5000000000", "9.6000000000",
                buildNtsFields(new BigDecimal("5.00"), BigDecimal.ZERO, new BigDecimal("1000.5"), null)))
            .expectScenarioAudit(Collections.singletonList(
                Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, StringUtils.EMPTY)))
            .build()
            .run();
    }

    private Scenario buildScenario(String netTotal, String grossAmount, String serviceFeeTotal, NtsFields ntsFields) {
        Scenario scenario = new Scenario();
        scenario.setName("Test Scenario");
        scenario.setNetTotal(new BigDecimal(netTotal).setScale(10, RoundingMode.HALF_UP));
        scenario.setGrossTotal(new BigDecimal(grossAmount).setScale(10, RoundingMode.HALF_UP));
        scenario.setServiceFeeTotal(new BigDecimal(serviceFeeTotal).setScale(10, RoundingMode.HALF_UP));
        scenario.setDescription("Scenario Description");
        scenario.setNtsFields(ntsFields);
        return scenario;
    }

    private NtsFields buildNtsFields(BigDecimal rhMinimumAmount, BigDecimal preServiceFeeAmount,
                                     BigDecimal postServiceFeeAmount, String preServiceFeeFundId) {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setRhMinimumAmount(rhMinimumAmount);
        ntsFields.setPreServiceFeeAmount(preServiceFeeAmount);
        ntsFields.setPreServiceFeeFundId(preServiceFeeFundId);
        ntsFields.setPostServiceFeeAmount(postServiceFeeAmount);
        return ntsFields;
    }

    private Usage buildScenarioExcludedUsage(String id, Long rhAccountNumber) {
        Usage usage =
            buildUsage(id, rhAccountNumber, null, SERVICE_FEE_32, AMOUNT_ZERO, AMOUNT_ZERO, AMOUNT_ZERO,
                null, NTS_PRODUCT_FAMILY, null, false, false);
        usage.setStatus(UsageStatusEnum.SCENARIO_EXCLUDED);
        return usage;
    }

    private Usage buildUsage(String id, Long rhAccountNumber, Long payeeAccountNumber, BigDecimal serviceFee,
                             String serviceFeeAmount, String netAmount, String grossAmount, Long wrWrkInst,
                             String productFamily, BigDecimal reportedValue, boolean rhParticipation,
                             boolean payeeParticipation) {
        Usage usage = new Usage();
        usage.setId(id);
        usage.setWrWrkInst(wrWrkInst);
        usage.setRightsholder(buildRightsholder(rhAccountNumber));
        usage.setPayee(buildRightsholder(payeeAccountNumber));
        usage.setServiceFee(serviceFee);
        usage.setProductFamily(productFamily);
        usage.setReportedValue(reportedValue);
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setNetAmount(new BigDecimal(netAmount).setScale(10, RoundingMode.HALF_UP));
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount).setScale(10, RoundingMode.HALF_UP));
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setRhParticipating(rhParticipation);
        usage.setPayeeParticipating(payeeParticipation);
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
        filter.setProductFamily(productFamily);
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }
}
