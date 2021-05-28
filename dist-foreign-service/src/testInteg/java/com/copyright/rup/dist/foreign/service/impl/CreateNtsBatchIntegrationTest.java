package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.NtsFields;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies creation of NTS batches.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/10/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestPropertySource(properties = {"test.liquibase.changelog=create-nts-batch-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class CreateNtsBatchIntegrationTest {

    private static final LocalDate DATE = LocalDate.of(2017, 2, 1);
    private static final String BUS_MARKET = "Bus";
    private static final String DOC_DEL_MARKET = "Doc Del";
    private static final String ORACLE_RH_TAX_1000023401_US_RESPONSE = "tax/rh_1000023401_tax_country_us_response.json";
    private static final String PRM_RH_1000023401_RESPONSE = "prm/rightsholder_1000023401_response.json";
    private static final String PRM_RH_2000017001_RESPONSE = "prm/rightsholder_2000017001_response.json";
    private static final String RMS_GRANTS_65882434_REQUEST = "rights/rms_grants_658824345_request.json";
    private static final String RMS_GRANTS_65882434_RESPONSE = "rights/rms_grants_658824345_response.json";
    private static final String RMS_GRANTS_958824349_REQUEST = "rights/rms_grants_958824349_request.json";
    private static final String RMS_GRANTS_958824349_RESPONSE = "rights/rms_grants_958824349_response.json";
    private static final String PRM_ELIGIBLE_RH_1000023401_RESPONSE =
        "preferences/rh_1000023401_eligible_response.json";
    private static final String RH_ID_1 = "85f864f2-30a5-4215-ac4f-f1f541901218";
    private static final String RH_ID_2 = "96533f85-d59c-422d-a6b5-a0553ad7e7f0";
    private static final BigDecimal STM_AMOUNT = new BigDecimal("100");
    private static final BigDecimal DEFAULT_GROSS_AMOUNT = new BigDecimal("0.0000000000");

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;
    @Autowired
    private CreateNtsBatchIntegrationTestBuilder testBuilder;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
        testBuilder.reset();
    }

    @Test
    public void testCreateNtsBatch() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(BUS_MARKET, STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(
                RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE))
            .expectOracleCall(ImmutableMap.of(
                Collections.singletonList(1000023401L), ORACLE_RH_TAX_1000023401_US_RESPONSE))
            .expectPreferences(ImmutableMap.of(
                Collections.singletonList(RH_ID_1), PRM_ELIGIBLE_RH_1000023401_RESPONSE))
            .expectUsages(Collections.singletonList(buildUsage(BUS_MARKET, UsageStatusEnum.ELIGIBLE, 658824345L)))
            .expectAudit(getEligibleUsageAuditItem())
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchExcludingStmWithStmRh() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(DOC_DEL_MARKET, STM_AMOUNT, true)))
            .withInitialUsagesCount(2)
            .expectRmsRights(ImmutableMap.of(
                RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE,
                RMS_GRANTS_958824349_REQUEST, RMS_GRANTS_958824349_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE,
                1000023995L, "prm/rightsholder_1000023995_response.json"))
            .expectOracleCall(ImmutableMap.of(
                Arrays.asList(1000023401L, 1000023995L),
                "tax/rh_1000023401_1000023995_tax_country_us_response.json"))
            .expectPreferences(ImmutableMap.of(
                Arrays.asList(RH_ID_1, RH_ID_2), "preferences/rh_1000023401_1000023995_stm_nts_response.json"))
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchExcludingStmWithoutStmRh() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(DOC_DEL_MARKET, STM_AMOUNT, true)))
            .withInitialUsagesCount(2)
            .expectRmsRights(ImmutableMap.of(
                RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE,
                RMS_GRANTS_958824349_REQUEST, RMS_GRANTS_958824349_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE,
                1000023995L, "prm/rightsholder_1000023995_response.json"))
            .expectOracleCall(ImmutableMap.of(
                Arrays.asList(1000023401L, 1000023995L),
                "tax/rh_1000023401_1000023995_tax_country_us_response.json"))
            .expectPreferences(ImmutableMap.of(
                Arrays.asList(RH_ID_1, RH_ID_2),
                "preferences/rh_1000023401_1000023995_stm_fas_response.json"))
            .expectUsages(Arrays.asList(
                buildUsage(DOC_DEL_MARKET, UsageStatusEnum.ELIGIBLE, 658824345L),
                buildUsage(DOC_DEL_MARKET, UsageStatusEnum.ELIGIBLE, 958824349L)))
            .expectAudit(getEligibleUsageAuditItem())
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchExcludingStmWithStmRhForAnotherProduct() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(DOC_DEL_MARKET, STM_AMOUNT, true)))
            .withInitialUsagesCount(2)
            .expectRmsRights(ImmutableMap.of(
                RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE,
                RMS_GRANTS_958824349_REQUEST, RMS_GRANTS_958824349_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE,
                1000023995L, "prm/rightsholder_1000023995_response.json"))
            .expectOracleCall(ImmutableMap.of(
                Arrays.asList(1000023401L, 1000023995L), "tax/rh_1000023401_1000023995_tax_country_us_response.json"))
            .expectPreferences(ImmutableMap.of(
                Arrays.asList(RH_ID_1, RH_ID_2), "preferences/rh_1000023401_1000023995_eligible_response.json"))
            .expectUsages(Arrays.asList(
                buildUsage(DOC_DEL_MARKET, UsageStatusEnum.ELIGIBLE, 658824345L),
                buildUsage(DOC_DEL_MARKET, UsageStatusEnum.ELIGIBLE, 958824349L)))
            .expectAudit(getEligibleUsageAuditItem())
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchWithUnclassified() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields("Gov", STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(
                "rights/rms_grants_576324545_request.json", "rights/rms_grants_576324545_response.json"))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE))
            .expectOracleCall(ImmutableMap.of(
                Collections.singletonList(1000023401L), ORACLE_RH_TAX_1000023401_US_RESPONSE))
            .expectPreferences(ImmutableMap.of(
                Collections.singletonList(RH_ID_1), PRM_ELIGIBLE_RH_1000023401_RESPONSE))
            .expectUsages(Collections.singletonList(buildUsage("Gov", UsageStatusEnum.UNCLASSIFIED, 576324545L)))
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchWithIneligibleRh() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(BUS_MARKET, STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE))
            .expectOracleCall(ImmutableMap.of(
                Collections.singletonList(1000023401L), ORACLE_RH_TAX_1000023401_US_RESPONSE))
            .expectPreferences(ImmutableMap.of(
                Collections.singletonList(RH_ID_1), "preferences/rh_1000023401_ineligible_response.json"))
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchWithNonUsRhTaxCountry() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields(BUS_MARKET, STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, "prm/rightsholder_1000023401_response.json"))
            .expectOracleCall(ImmutableMap.of(
                Collections.singletonList(1000023401L), "tax/rh_1000023401_tax_country_fr_response.json"))
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchWithRhNotFound() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields("Univ", STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectPrmCall(ImmutableMap.of(2000017001L, PRM_RH_2000017001_RESPONSE))
            .expectRmsRights(
                ImmutableMap.of("rights/rms_grants_854030732_request.json", "rights/rms_grants_empty_response.json"))
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchWithUsageUnderMinimum() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields("Lib", STM_AMOUNT, false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE))
            .expectOracleCall(
                ImmutableMap.of(Collections.singletonList(1000023401L), ORACLE_RH_TAX_1000023401_US_RESPONSE))
            .expectPreferences(ImmutableMap.of(Collections.singletonList(RH_ID_1), PRM_ELIGIBLE_RH_1000023401_RESPONSE))
            .expectUsages(Collections.singletonList(buildUsage("Lib", UsageStatusEnum.ELIGIBLE, 658824345L)))
            .expectAudit(getEligibleUsageAuditItem())
            .build()
            .run();
    }

    @Test
    public void testCreateNtsBatchZeroStmAmount() {
        testBuilder
            .withUsageBatch(buildUsageBatch(buildNtsFields("Edu", new BigDecimal("0.000"), false)))
            .withInitialUsagesCount(1)
            .expectRmsRights(ImmutableMap.of(RMS_GRANTS_65882434_REQUEST, RMS_GRANTS_65882434_RESPONSE))
            .expectPrmCall(ImmutableMap.of(
                2000017001L, PRM_RH_2000017001_RESPONSE,
                1000023401L, PRM_RH_1000023401_RESPONSE))
            .expectOracleCall(
                ImmutableMap.of(Collections.singletonList(1000023401L), ORACLE_RH_TAX_1000023401_US_RESPONSE))
            .expectPreferences(ImmutableMap.of(Collections.singletonList(RH_ID_1), PRM_ELIGIBLE_RH_1000023401_RESPONSE))
            .expectUsages(Collections.singletonList(buildUsage("Edu", UsageStatusEnum.ELIGIBLE, 658824345L)))
            .expectAudit(getEligibleUsageAuditItem())
            .build()
            .run();
    }

    private UsageBatch buildUsageBatch(NtsFields ntsFields) {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("Batch name");
        usageBatch.setFiscalYear(2017);
        usageBatch.setPaymentDate(DATE);
        usageBatch.setRro(buildRightsholder(2000017001L));
        usageBatch.setProductFamily("NTS");
        usageBatch.setNtsFields(ntsFields);
        usageBatch.setGrossAmount(new BigDecimal("0.00"));
        return usageBatch;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private NtsFields buildNtsFields(String market, BigDecimal stmAmount, boolean excludingStm) {
        NtsFields ntsFields = new NtsFields();
        ntsFields.setFundPoolPeriodFrom(2013);
        ntsFields.setFundPoolPeriodTo(2016);
        ntsFields.setStmAmount(stmAmount);
        ntsFields.setNonStmAmount(new BigDecimal("400.44"));
        ntsFields.setStmMinimumAmount(new BigDecimal("300.3"));
        ntsFields.setNonStmMinimumAmount(new BigDecimal("200."));
        ntsFields.setMarkets(ImmutableSet.of(market));
        ntsFields.setExcludingStm(excludingStm);
        return ntsFields;
    }

    private Usage buildUsage(String market, UsageStatusEnum status, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setWrWrkInst(wrWrkInst);
        usage.setWorkTitle("100 ROAD MOVIES");
        usage.setRightsholder(buildRightsholder(1000023401L));
        usage.setStatus(status);
        usage.setProductFamily("NTS");
        usage.setStandardNumber("1008902112317555XX");
        usage.setStandardNumberType("VALISBN13");
        usage.setMarket(market);
        usage.setMarketPeriodFrom(2013);
        usage.setMarketPeriodTo(2017);
        usage.setGrossAmount(DEFAULT_GROSS_AMOUNT);
        usage.setReportedValue(new BigDecimal("1176.92"));
        usage.setNetAmount(new BigDecimal("0.0000000000"));
        usage.setServiceFeeAmount(new BigDecimal("0.0000000000"));
        return usage;
    }

    private UsageAuditItem getEligibleUsageAuditItem() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(UsageActionTypeEnum.ELIGIBLE);
        auditItem.setActionReason("Usage has become eligible");
        return auditItem;
    }
}
