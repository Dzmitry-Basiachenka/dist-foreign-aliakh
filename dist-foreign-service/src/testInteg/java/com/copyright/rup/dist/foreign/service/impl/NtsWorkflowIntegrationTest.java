package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.Usage;
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
 * Verifies application workflow for NTS usage.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/10/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestPropertySource(properties = {"test.liquibase.changelog=nts-workflow-data-init.groovy"})
@Transactional
public class NtsWorkflowIntegrationTest {

    private static final LocalDate DATE = LocalDate.of(2017, 2, 1);
    private static final String BUS_MARKET = "Bus";

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
    public void testNtsBatchWorkflow() throws InterruptedException {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildFundPool(BUS_MARKET)))
            .expectRmsRights("rights/rms_grants_658824345_request.json", "rights/rms_grants_658824345_response.json")
            .expectPrmCall(1000023401L, "prm/rightsholder_1000023401_response.json")
            .expectOracleCall("tax/rh_1000023401_tax_country_request.json",
                "tax/rh_1000023401_tax_country_us_response.json")
            .expectPreferences("eligibility/pref_eligible_rh_response.json", "85f864f2-30a5-4215-ac4f-f1f541901218")
            .expectUsage(buildUsage())
            .build()
            .run();
    }

    @Test
    public void testNtsBatchWorkflowIneligibleRightsholder() throws InterruptedException {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildFundPool(BUS_MARKET)))
            .expectRmsRights("rights/rms_grants_658824345_request.json", "rights/rms_grants_658824345_response.json")
            .expectPrmCall(1000023401L, "prm/rightsholder_1000023401_response.json")
            .expectOracleCall("tax/rh_1000023401_tax_country_request.json",
                "tax/rh_1000023401_tax_country_us_response.json")
            .expectPreferences("eligibility/pref_ineligible_rh_response.json", "85f864f2-30a5-4215-ac4f-f1f541901218")
            .build()
            .run();
    }

    @Test
    public void testNtsBatchWorkflowNonUsRhTaxCountry() throws InterruptedException {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildFundPool(BUS_MARKET)))
            .expectRmsRights("rights/rms_grants_658824345_request.json", "rights/rms_grants_658824345_response.json")
            .expectPrmCall(1000023401L, "prm/rightsholder_1000023401_response.json")
            .expectOracleCall("tax/rh_1000023401_tax_country_request.json",
                "tax/rh_1000023401_tax_country_fr_response.json")
            .build()
            .run();
    }

    @Test
    public void testNtsBatchWorkflowRhNotFound() throws InterruptedException {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildFundPool("Utiv")))
            .expectRmsRights("rights/rms_grants_854030732_request.json", "rights/rms_grants_empty_response.json")
            .build()
            .run();
    }

    private UsageBatch buildUsageBatch(FundPool fundPool) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("Batch name");
        usageBatch.setFiscalYear(2017);
        usageBatch.setPaymentDate(DATE);
        usageBatch.setRro(buildRightsholder(2000017001L, "CFC, Centre Francais d'exploitation du droit de Copie"));
        usageBatch.setFundPool(fundPool);
        return usageBatch;
    }

    private Rightsholder buildRightsholder(Long accountNumber, String name) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        rightsholder.setName(name);
        return rightsholder;
    }

    private FundPool buildFundPool(String market) {
        FundPool fundPool = new FundPool();
        fundPool.setFundPoolPeriodFrom(2013);
        fundPool.setFundPoolPeriodTo(2016);
        fundPool.setStmAmount(new BigDecimal("100"));
        fundPool.setNonStmAmount(new BigDecimal("200."));
        fundPool.setStmMinimumAmount(new BigDecimal("300.3"));
        fundPool.setNonStmMinimumAmount(new BigDecimal("400.44"));
        fundPool.setMarkets(ImmutableSet.of(market));
        return fundPool;
    }

    private Usage buildUsage() {
        Usage usage = new Usage();
        usage.setWrWrkInst(658824345L);
        usage.setWorkTitle("100 ROAD MOVIES");
        usage.setRightsholder(buildRightsholder(1000023401L, "American College of Physicians - Journals"));
        usage.setStatus(UsageStatusEnum.ELIGIBLE);
        usage.setProductFamily("NTS");
        usage.setStandardNumber("1008902112317555XX");
        usage.setMarket(BUS_MARKET);
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setGrossAmount(new BigDecimal("1176.9160000000"));
        usage.setReportedValue(new BigDecimal("1176.92"));
        return usage;
    }
}
