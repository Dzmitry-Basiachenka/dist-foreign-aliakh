package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Integration test to verify reconcile rightsholders functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/01/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class ReconcileRightsholdersTest {

    private static final String FOLDER_NAME = "reconcile-rightsholders-integration-test/";
    private static final String SERVICE_FEE_16 = "0.16000";
    private static final String SERVICE_FEE_32 = "0.32000";
    private static final String BATCH_NAME = "Test Batch 2";

    @Autowired
    private ReconcileRightsholdersTestBuilder testBuilder;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testBuilder.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "reconciliation-without-discrepancies.groovy")
    public void testReconciliationWithoutDiscrepancies() {
        testBuilder
            .forScenario(buildScenario("be66bae9-fa89-499a-8485-fb9e445bedd9", "Test Reconcile rightsholders 1"))
            .expectRmsCall("reconcileRightsholders/rms_no_discrepancies_request.json",
                "reconcileRightsholders/rms_no_discrepancies_response.json")
            .expectPreferences("reconcileRightsholders/preferences_no_discrepancies_response.json",
                "05dc9217-26d4-46ca-aa6e-18572591f3c8")
            .expectUsages(
                buildUsage("fcdaea01-2439-4c51-b3e2-23649cf710c7", 1000003821L, 1000003821L, 471137470L,
                    "1000.0000000000", "840.0000000000", "160.0000000000", SERVICE_FEE_16, new BigDecimal("1000.00"),
                    true, true))
            .expectUsageAudit(ImmutableMap.of("fcdaea01-2439-4c51-b3e2-23649cf710c7",
                Collections.singletonList(buildLoadedAuditItem("Test Batch 1"))))
            .build()
            .run();
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "reconciliation-with-discrepancies.groovy")
    public void testReconciliationWithDiscrepancies() {
        testBuilder
            .forScenario(buildScenario("04263c90-cb54-44f0-b354-a901586e5801", "Test Reconcile rightsholders 2"))
            .expectRmsCall("reconcileRightsholders/rms_with_discrepancies_request.json",
                "reconcileRightsholders/rms_with_discrepancies_response.json")
            .expectPrmCall(1000002137L, "reconcileRightsholders/prm_with_discrepancies_response.json")
            .expectRollups("reconcileRightsholders/rollups_with_discrepancies_response.json",
                "f0ba198d-14fd-49c9-a2c3-ffc65b2f14af", "cd1d10f8-c625-4634-afee-9b983071e725")
            .expectPreferences("reconcileRightsholders/preferences_with_discrepancies_response.json",
                "3259b121-8b0e-4b9e-8d7a-88707cf418a4", "f0ba198d-14fd-49c9-a2c3-ffc65b2f14af",
                "cd1d10f8-c625-4634-afee-9b983071e725", "e395cf0e-312f-425c-a37d-4a7ab824b8f6")
            .expectDiscrepancies(Sets.newHashSet(
                buildDiscrepancy(7000515031L, 2000152614L, 127778305L),
                buildDiscrepancy(2000152614L, 1000002137L, 122861189L),
                buildDiscrepancy(1000000026L, 1000002137L, 122799407L),
                buildDiscrepancy(1000000322L, 2000152614L, 123636551L)))
            .expectUsages(
                buildUsage("4713282c-c698-4ffb-8de1-44863d48954f", 2000152614L, 2000152614L, 127778305L,
                    "5000.0000000000", "4200.0000000000", "800.0000000000", SERVICE_FEE_16, new BigDecimal("5000.00"),
                    true, true),
                buildUsage("cf2b4a25-d786-4fee-9c7f-5bec12b017c1", 1000000322L, 1000000322L, 123642505L,
                    "2500.0000000000", "1700.0000000000", "800.0000000000", SERVICE_FEE_32, new BigDecimal("2500.00"),
                    false, false),
                buildUsage("d2da6044-7ff7-4b5d-984a-69978b9e0678", 1000002137L, 2000017000L, 122799407L,
                    "1800.0000000000", "1224.0000000000", "576.0000000000", SERVICE_FEE_32, new BigDecimal("1800.00"),
                    false, false),
                buildUsage("daf2483b-a7b4-415b-81d2-adb328423661", 1000002137L, 2000017000L, 122861189L,
                    "1000.0000000000", "680.0000000000", "320.0000000000", SERVICE_FEE_32, new BigDecimal("1000.00"),
                    false, false),
                buildUsage("f1d2c084-973b-4c88-9b45-d4060d87b4ba", 2000152614L, 2000152614L, 123636551L,
                    "4500.0000000000", "3780.0000000000", "720.0000000000", SERVICE_FEE_16, new BigDecimal("4500.00"),
                    true, true),
                buildUsage("f9f5d608-c6e7-49dd-b658-174522b0549e", 2000152614L, 2000152614L, 123647460L,
                    "200.0000000000", "168.0000000000", "32.0000000000", SERVICE_FEE_16, new BigDecimal("200.00"),
                    true, true))
            .expectUsageAudit(ImmutableMap.<String, List<UsageAuditItem>>builder()
                .put("4713282c-c698-4ffb-8de1-44863d48954f", Arrays.asList(buildRhUpdatedAuditItem(2000152614L),
                    buildLoadedAuditItem(BATCH_NAME)))
                .put("cf2b4a25-d786-4fee-9c7f-5bec12b017c1",
                    Collections.singletonList(buildLoadedAuditItem(BATCH_NAME)))
                .put("d2da6044-7ff7-4b5d-984a-69978b9e0678", Arrays.asList(buildRhUpdatedAuditItem(1000002137L),
                    buildLoadedAuditItem(BATCH_NAME)))
                .put("daf2483b-a7b4-415b-81d2-adb328423661", Arrays.asList(buildRhUpdatedAuditItem(1000002137L),
                    buildLoadedAuditItem(BATCH_NAME)))
                .put("f1d2c084-973b-4c88-9b45-d4060d87b4ba", Arrays.asList(buildRhUpdatedAuditItem(2000152614L),
                    buildLoadedAuditItem(BATCH_NAME)))
                .put("f9f5d608-c6e7-49dd-b658-174522b0549e",
                    Collections.singletonList(buildLoadedAuditItem(BATCH_NAME)))
                .build())
            .build()
            .run();
    }

    private UsageAuditItem buildRhUpdatedAuditItem(Long accountNumber) {
        return buildAuditItem(UsageActionTypeEnum.RH_UPDATED,
            "Rightsholder account " + accountNumber + " found during reconciliation");
    }

    private UsageAuditItem buildLoadedAuditItem(String batchName) {
        return buildAuditItem(UsageActionTypeEnum.LOADED, "Uploaded in '" + batchName + "'");
    }

    private UsageAuditItem buildAuditItem(UsageActionTypeEnum type, String reason) {
        UsageAuditItem item = new UsageAuditItem();
        item.setActionReason(reason);
        item.setActionType(type);
        return item;
    }

    private Scenario buildScenario(String id, String name) {
        Scenario scenario = new Scenario();
        scenario.setName(name);
        scenario.setId(id);
        return scenario;
    }

    private RightsholderDiscrepancy buildDiscrepancy(Long oldRhAccountNumber, Long newRhAccountNumber, Long wrWrkInst) {
        RightsholderDiscrepancy rightsholderDiscrepancy = new RightsholderDiscrepancy();
        rightsholderDiscrepancy.setOldRightsholder(buildRightsholder(oldRhAccountNumber));
        rightsholderDiscrepancy.setNewRightsholder(buildRightsholder(newRhAccountNumber));
        rightsholderDiscrepancy.setWrWrkInst(wrWrkInst);
        rightsholderDiscrepancy.setWorkTitle("Sunbeams");
        return rightsholderDiscrepancy;
    }

    private Rightsholder buildRightsholder(Long accountNumber) {
        Rightsholder rightsholder = new Rightsholder();
        rightsholder.setAccountNumber(accountNumber);
        return rightsholder;
    }

    private Usage buildUsage(String usageId, Long rhAccountNumber, Long payeeAccountNumber, Long wrWrkInst,
                             String grossAmount, String netAmount, String serviceFeeAmount, String serviceFee,
                             BigDecimal reportedValue, boolean rhParticipating, boolean payeeParticipating) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setStatus(UsageStatusEnum.LOCKED);
        usage.setWrWrkInst(wrWrkInst);
        usage.setRightsholder(buildRightsholder(rhAccountNumber));
        usage.setPayee(buildRightsholder(payeeAccountNumber));
        usage.setServiceFee(new BigDecimal(serviceFee));
        usage.setProductFamily("FAS");
        usage.setReportedValue(reportedValue);
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setNetAmount(new BigDecimal(netAmount));
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount));
        usage.setRhParticipating(rhParticipating);
        usage.setPayeeParticipating(payeeParticipating);
        return usage;
    }
}
