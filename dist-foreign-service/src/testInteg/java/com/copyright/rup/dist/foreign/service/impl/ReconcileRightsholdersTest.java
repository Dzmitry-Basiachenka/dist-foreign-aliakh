package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.RightsholderDiscrepancy;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;

import com.google.common.collect.Sets;

import org.junit.Before;
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
 * Integration test to verify reconcile rightsholders functionality.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 02/01/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=reconcile-rightsholders-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReconcileRightsholdersTest {

    private static final String SERVICE_FEE_16 = "0.16";
    private static final String SERVICE_FEE_32 = "0.32";

    @Autowired
    private ReconcileRightsholdersTestBuilder testBuilder;

    @Before
    public void setUp() {
        testBuilder.reset();
    }

    @Test
    public void testReconciliationWithoutDiscrepancies() {
        testBuilder
            .forScenario(buildScenario("be66bae9-fa89-499a-8485-fb9e445bedd9", "Test Reconcile rightsholders 1"))
            .expectRmsCall("reconcileRightsholders/rms_no_discrepancies_request.json",
                "reconcileRightsholders/rms_no_discrepancies_response.json")
            .expectPreferences("reconcileRightsholders/preferences_no_discrepancies_response.json")
            .expectUsages(
                buildUsage("fcdaea01-2439-4c51-b3e2-23649cf710c7", 1000003821L, 1000003821L, 471137470L, "1000.00",
                    "840.00", "160.00", SERVICE_FEE_16))
            .build()
            .run();
    }

    @Test
    public void testReconciliationWithDiscrepancies() {
        testBuilder
            .forScenario(buildScenario("04263c90-cb54-44f0-b354-a901586e5801", "Test Reconcile rightsholders 2"))
            .expectRmsCall("reconcileRightsholders/rms_with_discrepancies_request.json",
                "reconcileRightsholders/rms_with_discrepancies_response.json")
            .expectPrmCall("reconcileRightsholders/prm_with_discrepancies_response.json")
            .expectRollups("reconcileRightsholders/rollups_with_discrepancies_response.json",
                "f0ba198d-14fd-49c9-a2c3-ffc65b2f14af", "cd1d10f8-c625-4634-afee-9b983071e725")
            .expectPreferences("reconcileRightsholders/preferences_with_discrepancies_response.json")
            .expectDiscrepancies(Sets.newHashSet(
                buildDiscrepancy(7000515031L, 2000152614L, 127778305L),
                buildDiscrepancy(2000152614L, 1000002137L, 122861189L),
                buildDiscrepancy(1000000026L, 1000002137L, 122799407L),
                buildDiscrepancy(1000000322L, 2000152614L, 123636551L)))
            .expectUsages(
                buildUsage("4713282c-c698-4ffb-8de1-44863d48954f", 2000152614L, 2000152614L, 127778305L, "5000.00",
                    "4200.00", "800.00", SERVICE_FEE_16),
                buildUsage("cf2b4a25-d786-4fee-9c7f-5bec12b017c1", 1000000322L, 1000000322L, 123642505L, "2500.00",
                    "1700.00", "800.00", SERVICE_FEE_32),
                buildUsage("d2da6044-7ff7-4b5d-984a-69978b9e0678", 1000002137L, 1000001820L, 122799407L, "1800.00",
                    "1224.00", "576.00", SERVICE_FEE_32),
                buildUsage("daf2483b-a7b4-415b-81d2-adb328423661", 1000002137L, 1000001820L, 122861189L, "1000.00",
                    "680.00", "320.00", SERVICE_FEE_32),
                buildUsage("f1d2c084-973b-4c88-9b45-d4060d87b4ba", 2000152614L, 2000152614L, 123636551L, "4500.00",
                    "3780.00", "720.00", SERVICE_FEE_16),
                buildUsage("f9f5d608-c6e7-49dd-b658-174522b0549e", 2000152614L, 2000152614L, 123647460L, "200.00",
                    "168.00", "32.00", SERVICE_FEE_16))
            .build()
            .run();
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
                             String grossAmount, String netAmount, String serviceFeeAmount, String serviceFee) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.getRightsholder().setAccountNumber(rhAccountNumber);
        usage.getPayee().setAccountNumber(payeeAccountNumber);
        usage.setWrWrkInst(wrWrkInst);
        usage.setGrossAmount(new BigDecimal(grossAmount));
        usage.setNetAmount(new BigDecimal(netAmount));
        usage.setServiceFeeAmount(new BigDecimal(serviceFeeAmount));
        usage.setServiceFee(new BigDecimal(serviceFee));
        return usage;
    }
}
