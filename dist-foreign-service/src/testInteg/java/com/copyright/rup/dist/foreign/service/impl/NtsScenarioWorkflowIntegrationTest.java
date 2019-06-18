package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.NtsFields;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@TestPropertySource(properties = {"test.liquibase.changelog=nts-scenario-workflow-data-init.groovy"})
@Transactional
public class NtsScenarioWorkflowIntegrationTest {

    private static final LocalDate DATE = LocalDate.of(2017, 2, 1);
    private static final String BUS_MARKET = "Bus";
    private static final String ORACLE_RH_TAX_1000023401_US_RESPONSE = "tax/rh_1000023401_tax_country_us_response.json";
    private static final String PRM_RH_1000023401_RESPONSE = "prm/rightsholder_1000023401_response.json";
    private static final String RMS_GRANTS_65882434_REQUEST = "rights/rms_grants_658824345_request.json";
    private static final String PRM_ELIGIBLE_RH_1000023401_RESPONSE = "eligibility/pref_eligible_rh_response.json";
    private static final String RH_ID = "85f864f2-30a5-4215-ac4f-f1f541901218";
    private static final BigDecimal STM_AMOUNT = new BigDecimal("100");

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;
    @Autowired
    private NtsScenarioWorkflowIntegrationTestBuilder testBuilder;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
        testBuilder.reset();
    }

    @Test
    public void testNtsScenarioWorkflow() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildFundPool()))
            .expectRmsRights(RMS_GRANTS_65882434_REQUEST, "rights/rms_grants_658824345_response.json")
            .expectPrmCall(1000023401L, PRM_RH_1000023401_RESPONSE)
            .expectOracleCall(1000023401L, ORACLE_RH_TAX_1000023401_US_RESPONSE)
            .expectPreferences(PRM_ELIGIBLE_RH_1000023401_RESPONSE, RH_ID)
            .expectUsage(buildUsage())
            .expectLmDetails("details/nts_details_to_lm.json")
            .expectAudit(getEligibleUsageAuditItem())
            .expectScenario(buildScenario())
            .build()
            .run();
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setStatus(ScenarioStatusEnum.SENT_TO_LM);
        NtsFields ntsFields = new NtsFields();
        scenario.setNtsFields(ntsFields);
        ntsFields.setRhMinimumAmount(new BigDecimal("300"));
        return scenario;
    }

    private UsageBatch buildUsageBatch(FundPool fundPool) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("Batch name");
        usageBatch.setFiscalYear(2017);
        usageBatch.setPaymentDate(DATE);
        usageBatch.setRro(buildRightsholder(2000017001L, "CFC, Centre Francais d'exploitation du droit de Copie"));
        usageBatch.setProductFamily("NTS");
        usageBatch.setFundPool(fundPool);
        return usageBatch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setFundPoolPeriodFrom(2013);
        fundPool.setFundPoolPeriodTo(2016);
        fundPool.setStmAmount(STM_AMOUNT);
        fundPool.setNonStmAmount(new BigDecimal("400.44"));
        fundPool.setStmMinimumAmount(new BigDecimal("300.3"));
        fundPool.setNonStmMinimumAmount(new BigDecimal("200."));
        fundPool.setMarkets(ImmutableSet.of(BUS_MARKET));
        return fundPool;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setWrWrkInst(151811999L);
        usage.setWorkTitle("NON-TITLE NTS");
        usage.setRightsholder(buildRightsholder(1000023401L, "American College of Physicians - Journals"));
        usage.setStatus(UsageStatusEnum.SENT_TO_LM);
        usage.setProductFamily("NTS");
        usage.setGrossAmount(new BigDecimal("400.4400000000"));
        usage.setReportedValue(new BigDecimal("0.00"));
        usage.setNetAmount(new BigDecimal("272.2992000000"));
        usage.setServiceFee(new BigDecimal("0.32000"));
        usage.setServiceFeeAmount(new BigDecimal("128.1408000000"));
        return usage;
    }

    private UsageAuditItem getEligibleUsageAuditItem() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(UsageActionTypeEnum.ELIGIBLE);
        auditItem.setActionReason("Usage has become eligible");
        return auditItem;
    }
}
