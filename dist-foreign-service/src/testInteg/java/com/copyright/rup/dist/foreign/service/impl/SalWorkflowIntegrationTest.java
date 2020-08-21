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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Verifies SAL workflow.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 08/20/20
 *
 * @author Anton Azarenka
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=sal-workflow-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SalWorkflowIntegrationTest {

    private static final String USAGE_ID_1 = "6c59d64f-a56e-4ca8-a914-a8ac299c6082";
    private static final String USAGE_ID_2 = "98d17e37-2900-4478-b105-6dd99f48d22b";
    private static final String USAGE_ID_3 = "47d1a298-18bd-4432-a04b-fd958732a87f";
    private static final String UPLOADED_REASON = "Uploaded in 'SAL test batch' Batch";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private SalWorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testSalWorkflow() throws IOException {
        testBuilder
            .withProductFamily(SAL_PRODUCT_FAMILY)
            .withUsagesCsvFile("usage/sal/sal_item_bank_usages_for_workflow.csv", USAGE_ID_1, USAGE_ID_2, USAGE_ID_3)
            .withUsageBatch(buildItemBank())
            .expectRmsRights("rights/rms_grants_122769471_request.json", "rights/rms_grants_122769471_response.json")
            .expectRmsRights("rights/rms_grants_243618757_request.json", "rights/rms_grants_243618757_response.json")
            .expectRmsRights("rights/rms_grants_140160102_request.json", "rights/rms_grants_empty_response.json")
            .expectUsages("usage/sal/sal_expected_item_bank_usages_for workflow.json", 3)
            .expectUsageAudit(USAGE_ID_1, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000000322 was found in RMS"),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122769471 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_ID_2, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.WORK_NOT_GRANTED,
                    "Right for 243618757 is denied for rightsholder account 1000000322"),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 243618757 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_ID_3, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.RH_NOT_FOUND,
                    "Rightsholder account for 140160102 was not found in RMS"),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 140160102 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .build()
            .run();
    }

    private UsageBatch buildItemBank() {
        UsageBatch batch = new UsageBatch();
        batch.setName("SAL test batch");
        batch.setProductFamily(SAL_PRODUCT_FAMILY);
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
