package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties = {"test.liquibase.changelog=aacl-workflow-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AaclWorkflowIntegrationTest {

    private static final String USAGE_COMMENT_1 = "AACL usage Comment 1";
    private static final String USAGE_COMMENT_2 = "AACL usage Comment 2";
    private static final String USAGE_COMMENT_4 = "AACL usage Comment 4";
    private static final String BASELINE_USAGE_COMMENT_1 = "AACL baseline usage Comment 1";
    private static final String BASELINE_USAGE_COMMENT_2 = "AACL baseline usage Comment 2";
    private static final String BASELINE_USAGE_COMMENT_4 = "AACL baseline usage Comment 4";
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String BASELINE_UPLOADED_REASON = "Pulled from baseline for 'AACL test batch' Batch";
    private static final String RH_FOUND_REASON = "Rightsholder account 1000024950 was found in RMS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private AaclWorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testAaclWorkflow() throws Exception {
        testBuilder
            .withProductFamily(AACL_PRODUCT_FAMILY)
            .withUsagesCsvFile("usage/aacl/aacl_usages_for_workflow.csv", 8)
            .withUsageBatch(buildUsageBatch())
            .expectRmsRights("rights/aacl/rms_grants_100009840_request_workflow.json",
                "rights/aacl/rms_grants_100009840_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_100010768_request_workflow.json",
                "rights/aacl/rms_grants_100010768_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_123456789_request_workflow.json",
                "rights/rms_grants_empty_response.json")
            .expectPrmCall(1000024950L, "prm/rightsholder_1000024950_response.json")
            .expectUsages("usage/aacl/aacl_expected_usages_for_workflow.json")
            .expectUsageAudit(BASELINE_USAGE_COMMENT_1, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)
            ))
            .expectUsageAudit(BASELINE_USAGE_COMMENT_2, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)
            ))
            .expectUsageAudit(BASELINE_USAGE_COMMENT_4, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_COMMENT_1, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_COMMENT_2, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_COMMENT_4, Arrays.asList(
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
        batch.setNumberOfBaselineYears(2);
        return batch;
    }

    private UsageAuditItem buildAuditItem(UsageActionTypeEnum actionType, String reason) {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(actionType);
        auditItem.setActionReason(reason);
        return auditItem;
    }
}
