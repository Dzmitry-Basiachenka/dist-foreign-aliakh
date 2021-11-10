package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Verifies application workflow for NTS scenarios.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 10/06/19
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "nts-scenario-workflow-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class NtsWorkflowIntegrationTest {

    private static final LocalDate DATE = LocalDate.of(2017, 2, 1);
    private static final String RH_ID_1 = "85f864f2-30a5-4215-ac4f-f1f541901218";
    private static final String RH_ID_2 = "18062f0c-beba-47a4-ae38-faa612688760";
    private static final BigDecimal STM_AMOUNT = new BigDecimal("100");

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;
    @Autowired
    private NtsWorkflowIntegrationTestBuilder testBuilder;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
        testBuilder.reset();
    }

    @Test
    public void testNtsWorkflow() throws InterruptedException {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields()))
            .expectRollups("prm/nts_rollups_response.json", RH_ID_1)
            .expectRmsRights(ImmutableMap.of(
                "rights/rms_grants_448824345_request.json", "rights/rms_grants_448824345_response.json",
                "rights/rms_grants_658824345_request.json", "rights/rms_grants_658824345_response.json"))
            .expectPrmCall(ImmutableMap.of(
                1000009522L, "prm/rightsholder_1000009522_response.json",
                1000023401L, "prm/rightsholder_1000023401_response.json"))
            .expectPrmCallForUpdateRro(2000017001L, "prm/rightsholder_2000017001_response.json")
            .expectOracleCall(ImmutableMap.of(
                Arrays.asList(1000009522L, 1000023401L), "tax/rh_tax_country_us_response_1.json"))
            .expectPreferences(ImmutableMap.of(
                Arrays.asList(RH_ID_2, RH_ID_1), "preferences/rh_preferences_response_1.json"))
            .expectUsage(buildPaidUsage())
            .expectLmDetails("details/nts_details_to_lm.json")
            .expectPaidInfo("lm/paid_usages_nts.json")
            .expectCrmCall("crm/workflow/rights_distribution_request_nts.json",
                "crm/workflow/rights_distribution_response_nts.json")
            .expectScenario(buildScenario())
            .build()
            .run();
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.ARCHIVED);
        Scenario.NtsFields ntsFields = new Scenario.NtsFields();
        scenario.setNtsFields(ntsFields);
        ntsFields.setRhMinimumAmount(new BigDecimal("300"));
        return scenario;
    }

    private UsageBatch buildUsageBatch(NtsFields ntsFields) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("Batch name");
        usageBatch.setFiscalYear(2017);
        usageBatch.setPaymentDate(DATE);
        usageBatch.setRro(buildRightsholder(2000017001L, "CFC, Centre Francais d'exploitation du droit de Copie"));
        usageBatch.setProductFamily("NTS");
        usageBatch.setNtsFields(ntsFields);
        return usageBatch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    private NtsFields buildNtsFields() {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setFundPoolPeriodFrom(2013);
        ntsFields.setFundPoolPeriodTo(2016);
        ntsFields.setStmAmount(STM_AMOUNT);
        ntsFields.setNonStmAmount(new BigDecimal("400.44"));
        ntsFields.setStmMinimumAmount(new BigDecimal("300.3"));
        ntsFields.setNonStmMinimumAmount(new BigDecimal("200."));
        ntsFields.setMarkets(ImmutableSet.of("Bus"));
        ntsFields.setExcludingStm(true);
        return ntsFields;
    }

    private PaidUsage buildPaidUsage() {
        PaidUsage usage = new PaidUsage();
        usage.setWrWrkInst(151811999L);
        usage.setWorkTitle("NON-TITLE NTS");
        usage.setSystemTitle("NON-TITLE NTS");
        usage.setRightsholder(buildRightsholder(1000023401L, "American College of Physicians - Journals"));
        usage.setPayee(buildRightsholder(1000010029L, "Georg Thieme Verlag KG"));
        usage.setStatus(UsageStatusEnum.ARCHIVED);
        usage.setProductFamily("NTS");
        usage.setGrossAmount(new BigDecimal("400.4400000000"));
        usage.setReportedValue(new BigDecimal("0.00"));
        usage.setNetAmount(new BigDecimal("272.3000000000"));
        usage.setServiceFee(new BigDecimal("0.32000"));
        usage.setServiceFeeAmount(new BigDecimal("128.1400000000"));
        usage.setDistributionName("FDA July 19");
        usage.setDistributionDate(OffsetDateTime.parse("2019-07-09T00:00-04:00"));
        usage.setCccEventId("53256");
        usage.setCheckNumber("578945");
        usage.setCheckDate(OffsetDateTime.parse("2019-07-10T00:00-04:00"));
        usage.setPeriodEndDate(OffsetDateTime.parse("2019-12-12T00:00-05:00"));
        usage.setLmDetailId("2672f512-b688-4bef-a1b0-72fe243dde46");
        return usage;
    }
}
