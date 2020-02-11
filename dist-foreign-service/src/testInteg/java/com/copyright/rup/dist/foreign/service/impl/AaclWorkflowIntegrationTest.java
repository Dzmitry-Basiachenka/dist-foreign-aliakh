package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Verifies AACL workflow.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 1/15/20
 *
 * @author Stanislau Rudak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AaclWorkflowIntegrationTest {

    private static final String USAGE_ID_1 = "072b4414-74b2-46cc-a69e-4b7f8f7cce5e";
    private static final String USAGE_ID_2 = "5e4ea6e9-83f6-4712-b8ab-b5a5f0eb4a70";
    private static final String USAGE_ID_3 = "8b0f4af8-f0b9-4a71-ac5b-ba6360dbe7d2";
    private static final String USAGE_ID_4 = "0e5d1005-df99-419f-96be-e3af89163879";
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private AaclWorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testAaclWorkflow() throws Exception {
        testBuilder
            .withProductFamily(AACL_PRODUCT_FAMILY)
            .withUsagesCsvFile("usage/aacl/aacl_usages_for_workflow.csv",
                USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4)
            .withUsageBatch(buildUsageBatch())
            .expectRmsRights("rights/aacl/rms_grants_100009840_request_workflow.json",
                "rights/aacl/rms_grants_100009840_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_100010768_request_workflow.json",
                "rights/aacl/rms_grants_100010768_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_123456789_request_workflow.json",
                "rights/rms_grants_empty_response.json")
            .expectPrmCall(1000024950L, "prm/rightsholder_1000024950_response.json")
            .expectUsages("usage/aacl/aacl_expected_usages_for_workflow.json")
            .expectUsageAudit(USAGE_ID_1, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS"),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_ID_2, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS"),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_ID_4, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .build()
            .run();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("AACL test batch");
        batch.setProductFamily(AACL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        return batch;
    }

    private UsageAuditItem buildAuditItem(UsageActionTypeEnum actionType, String reason) {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(actionType);
        auditItem.setActionReason(reason);
        return auditItem;
    }
}
