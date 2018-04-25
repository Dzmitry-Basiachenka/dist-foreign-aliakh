package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies application workflow.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/10/18
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestPropertySource(properties = {"test.liquibase.changelog=workflow-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkflowIntegrationTest {

    private static final LocalDate PAYMENT_DATE = LocalDate.now();

    private static final String USAGE_ID_1 = "2bffdf90-db90-4d92-a15e-03fbc739a0d5";
    private static final String USAGE_ID_2 = "5e8307ee-be63-45d3-b074-17052fbcddda";
    private static final String USAGE_ID_3 = "5f0f7397-6cb6-4538-b3dc-9364ed302e95";
    private static final String USAGE_ID_4 = "ae72ce15-d501-4c0f-b224-3ffca884e0f3";
    private static final String USAGE_ID_5 = "f85a6579-9461-4fa1-948f-734f0a3b3963";

    @Autowired
    private WorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testClaWorkflow() throws Exception {
        List<Pair<UsageActionTypeEnum, String>> expectedUsageAudit = buildExpectedUsageAudit();
        testBuilder
            .withUsagesCsvFile("usage/usages_for_workflow.csv",
                USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4, USAGE_ID_5)
            .withProductFamily("CLA_FAS")
            .withUsageBatch(buildUsageBatch())
            .withUsageFilter(buildUsageFilter())
            .expectInsertedUsagesCount(5)
            .expectPreferences("prm/not_found_response.json")
            .expectRollups("prm/cla_rollups_response.json", "b0e6b1f6-89e9-4767-b143-db0f49f32769",
                "624dcf73-a30f-4381-b6aa-c86d17198bd5", "60080587-a225-439c-81af-f016cb33aeac",
                "37338ed1-7083-45e2-a96b-5872a7de3a98", "f366285a-ce46-48b0-96ee-cd35d62fb243")
            .expectLmDetails("details/cla_details_to_lm.json")
            .expectPaidUsagesFromLm("lm/paid_usages_cla.json")
            .expectPaidDetailsIds(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4, USAGE_ID_5)
            .expectCrmReporting("crm/cla_rights_distribution_request.json", "crm/cla_rights_distribution_response.json")
            .expectArchivedDetailsIds(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4, USAGE_ID_5)
            .expectUsageAudit(USAGE_ID_1, expectedUsageAudit)
            .expectUsageAudit(USAGE_ID_2, expectedUsageAudit)
            .expectUsageAudit(USAGE_ID_3, expectedUsageAudit)
            .expectUsageAudit(USAGE_ID_4, expectedUsageAudit)
            .expectUsageAudit(USAGE_ID_5, expectedUsageAudit)
            .build()
            .run();
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamilies(Collections.singleton("CLA_FAS"));
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("Test_Batch");
        batch.setRro(buildRro());
        batch.setPaymentDate(PAYMENT_DATE);
        batch.setFiscalYear(2018);
        batch.setGrossAmount(BigDecimal.valueOf(199));
        return batch;
    }

    private Rightsholder buildRro() {
        Rightsholder rro = new Rightsholder();
        rro.setId("77b111d3-9eea-49af-b815-100b9716c1b3");
        rro.setAccountNumber(2000017000L);
        rro.setName("CLA, The Copyright Licensing Agency Ltd.");
        return rro;
    }

    private List<Pair<UsageActionTypeEnum, String>> buildExpectedUsageAudit() {
        return Arrays.asList(
            Pair.of(UsageActionTypeEnum.LOADED, "Uploaded in 'Test_Batch' Batch"),
            Pair.of(UsageActionTypeEnum.PAID, "Usage has been paid according to information from the LM"),
            Pair.of(UsageActionTypeEnum.ARCHIVED, "Usage was sent to CRM"));
    }
}
