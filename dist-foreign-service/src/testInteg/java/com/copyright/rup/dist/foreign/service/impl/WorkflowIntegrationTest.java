package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
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
@TestData(fileName = "workflow-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class WorkflowIntegrationTest {

    private static final LocalDate PAYMENT_DATE = LocalDate.now();

    private static final String USAGE_ID_1 = "2bffdf90-db90-4d92-a15e-03fbc739a0d5";
    private static final String USAGE_ID_2 = "5e8307ee-be63-45d3-b074-17052fbcddda";
    private static final String USAGE_ID_3 = "5f0f7397-6cb6-4538-b3dc-9364ed302e95";
    private static final String USAGE_ID_4 = "ae72ce15-d501-4c0f-b224-3ffca884e0f3";
    private static final String USAGE_ID_5 = "f85a6579-9461-4fa1-948f-734f0a3b3963";
    private static final String USAGE_LM_DETAIL_ID_1 = "d40b2162-d062-4161-8d64-7e869d45da4a";
    private static final String USAGE_LM_DETAIL_ID_2 = "75d22d2c-d9f0-4486-b17e-6e03ccda6bf9";
    private static final String USAGE_LM_DETAIL_ID_3 = "f9233aec-fa18-4973-a97c-5b550474c2fd";
    private static final String USAGE_LM_DETAIL_ID_4 = "b771be06-0206-4e03-9e9a-740c5714bdae";
    private static final String USAGE_LM_DETAIL_ID_5 = "1cb528e4-9712-4a2a-b456-9eb4316799d4";
    private static final String USAGE_LM_DETAIL_ID_6 = "ebae756e-6174-4ce7-9988-7722cdc35ce4";
    private static final String USAGE_LM_DETAIL_ID_7 = "c6a8a6d4-952c-43e2-9f9d-f12212c95fd8";
    private static final String RIGHTHOLDER_ID_1 = "60080587-a225-439c-81af-f016cb33aeac";
    private static final String RIGHTHOLDER_ID_2 = "b0e6b1f6-89e9-4767-b143-db0f49f32769";
    private static final String RIGHTHOLDER_ID_3 = "f366285a-ce46-48b0-96ee-cd35d62fb243";
    private static final String RIGHTHOLDER_ID_4 = "624dcf73-a30f-4381-b6aa-c86d17198bd5";
    private static final String RIGHTHOLDER_ID_5 = "37338ed1-7083-45e2-a96b-5872a7de3a98";
    private static final String AUDIT_USAGE_WAS_SENT_TO_CRM = "Usage was sent to CRM";
    private static final String AUDIT_UPLOADED_IN_BATCH = "Uploaded in 'Test_Batch' Batch";
    private static final String FAS2_PRODUCT_FAMILY = "FAS2";

    @Autowired
    private WorkflowIntegrationTestBuilder testBuilder;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testBuilder.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testClaWorkflow() throws Exception {
        List<Pair<UsageActionTypeEnum, String>> expectedUsageAudit = buildExpectedUsageAudit();
        testBuilder
            .withUsagesCsvFile("usage/usages_for_workflow.csv",
                USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4, USAGE_ID_5)
            .withProductFamily(FAS2_PRODUCT_FAMILY)
            .withUsageBatch(buildUsageBatch())
            .withUsageFilter(buildUsageFilter())
            .expectInsertedUsagesCount(5)
            .expectRmsRights("rights/rms_grants_100012905_request.json",
                "rights/rms_grants_100012905_response.json")
            .expectRmsRights("rights/rms_grants_100011821_request.json",
                "rights/rms_grants_100011821_response.json")
            .expectRollups("prm/cla_rollups_response.json",
                RIGHTHOLDER_ID_2, RIGHTHOLDER_ID_4, RIGHTHOLDER_ID_1, RIGHTHOLDER_ID_5, RIGHTHOLDER_ID_3)
            .expectPreferences("prm/not_found_response.json", "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f", RIGHTHOLDER_ID_2,
                RIGHTHOLDER_ID_4, RIGHTHOLDER_ID_1, RIGHTHOLDER_ID_5, RIGHTHOLDER_ID_3)
            .expectLmDetails(2, "details/cla_details_to_lm1.json", "details/cla_details_to_lm2.json")
            .expectPaidUsagesFromLm("lm/paid_usages_cla.json")
            .expectPaidUsageLmDetailIds(USAGE_LM_DETAIL_ID_1, USAGE_LM_DETAIL_ID_2, USAGE_LM_DETAIL_ID_3,
                USAGE_LM_DETAIL_ID_4, USAGE_LM_DETAIL_ID_5, USAGE_LM_DETAIL_ID_6, USAGE_LM_DETAIL_ID_7)
            .expectCrmReporting("crm/workflow/rights_distribution_request_cla.json",
                "crm/workflow/rights_distribution_response_cla.json")
            .expectUsages("usage/expected_usages_for_workflow.json")
            .expectUsageAudit(USAGE_LM_DETAIL_ID_1, expectedUsageAudit)
            .expectUsageAudit(USAGE_LM_DETAIL_ID_2, expectedUsageAudit)
            .expectUsageAudit(USAGE_LM_DETAIL_ID_3, expectedUsageAudit)
            .expectUsageAudit(USAGE_LM_DETAIL_ID_4, Arrays.asList(
                Pair.of(UsageActionTypeEnum.ARCHIVED, AUDIT_USAGE_WAS_SENT_TO_CRM),
                Pair.of(UsageActionTypeEnum.PAID, "Usage has been paid according to information from the LM"),
                Pair.of(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
                Pair.of(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS"),
                Pair.of(UsageActionTypeEnum.WORK_FOUND, "Usage was uploaded with Wr Wrk Inst"),
                Pair.of(UsageActionTypeEnum.LOADED, AUDIT_UPLOADED_IN_BATCH)))
            .expectUsageAudit(USAGE_LM_DETAIL_ID_5, Arrays.asList(
                Pair.of(UsageActionTypeEnum.ARCHIVED, AUDIT_USAGE_WAS_SENT_TO_CRM),
                Pair.of(UsageActionTypeEnum.PAID, "Usage has been adjusted based on Split process"),
                Pair.of(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
                Pair.of(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 2000139286 was found in RMS"),
                Pair.of(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100012905 was found by standard number " +
                    "12345XX-12978"),
                Pair.of(UsageActionTypeEnum.LOADED, AUDIT_UPLOADED_IN_BATCH)))
            .expectUsageAudit(USAGE_LM_DETAIL_ID_6, Arrays.asList(
                Pair.of(UsageActionTypeEnum.ARCHIVED, AUDIT_USAGE_WAS_SENT_TO_CRM),
                Pair.of(UsageActionTypeEnum.PAID, "Usage has been created based on Split process")))
            .expectUsageAudit(USAGE_LM_DETAIL_ID_7, Arrays.asList(
                Pair.of(UsageActionTypeEnum.ARCHIVED, AUDIT_USAGE_WAS_SENT_TO_CRM),
                Pair.of(UsageActionTypeEnum.PAID, "Usage has been created based on Post-Distribution process")))
            .build()
            .run();
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(FAS2_PRODUCT_FAMILY);
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("Test_Batch");
        batch.setRro(buildRro());
        batch.setProductFamily(FAS2_PRODUCT_FAMILY);
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
            Pair.of(UsageActionTypeEnum.ARCHIVED, AUDIT_USAGE_WAS_SENT_TO_CRM),
            Pair.of(UsageActionTypeEnum.PAID, "Usage has been paid according to information from the LM"),
            Pair.of(UsageActionTypeEnum.ELIGIBLE, "Usage was uploaded with Wr Wrk Inst and RH account number"),
            Pair.of(UsageActionTypeEnum.LOADED, AUDIT_UPLOADED_IN_BATCH));
    }
}
