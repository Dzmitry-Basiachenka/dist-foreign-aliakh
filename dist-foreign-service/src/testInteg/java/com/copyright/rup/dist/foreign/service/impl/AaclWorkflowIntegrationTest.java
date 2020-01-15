package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Collections;

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
public class AaclWorkflowIntegrationTest {

    private static final String USAGE_ID_1 = "3e9566e6-5cb9-4013-88ef-d4f8b66bda5d";
    private static final String USAGE_ID_2 = "599c5313-ed3b-4874-87e9-972425eb734f";
    private static final String USAGE_ID_3 = "78272304-1a43-48f7-a83b-530a90ed27e6";
    private static final String USAGE_ID_4 = "f634ec19-8ed1-4cc8-88bc-54461c62e670";
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private AaclWorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testAaclWorkflow() throws Exception {
        testBuilder
            .withProductFamily(AACL_PRODUCT_FAMILY)
            .withUsagesCsvFile("usage/usages_for_workflow_aacl.csv", USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4)
            .withUsageBatch(buildUsageBatch())
            .expectUsages("usage/expected_usages_for_workflow_aacl.json")
            .expectUsageAudit(USAGE_ID_1, Collections.singletonList(
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)))
            .expectUsageAudit(USAGE_ID_2, Collections.singletonList(
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)))
            .expectUsageAudit(USAGE_ID_3, Collections.singletonList(
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)))
            .expectUsageAudit(USAGE_ID_4, Collections.singletonList(
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)))
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
