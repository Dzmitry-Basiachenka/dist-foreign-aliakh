package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SalWorkflowIntegrationTest {

    private static final String IB_USAGE_ID_1 = "6c59d64f-a56e-4ca8-a914-a8ac299c6082";
    private static final String IB_USAGE_ID_2 = "98d17e37-2900-4478-b105-6dd99f48d22b";
    private static final String IB_USAGE_ID_3 = "cd5dabe8-bada-42ca-8329-12ac72d10ad8";
    private static final String UD_USAGE_ID_1 = "834f8e6c-b13d-4686-9977-82fe4bde2f20";
    private static final String UD_USAGE_ID_2 = "c1f44067-39b1-42ac-b9b6-7a87f2185b14";
    private static final String UD_USAGE_ID_3 = "20b73f78-c892-4c6d-a023-e6d01f988285";
    private static final String UD_USAGE_ID_4 = "49163e1c-cbdb-454b-8b42-ca775aea2624";
    private static final String UPLOADED_REASON = "Uploaded in 'SAL test batch' Batch";
    private static final String SAL_PRODUCT_FAMILY = "SAL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private SalWorkflowIntegrationTestBuilder testBuilder;

    @Test
    public void testSalWorkflowWithoutUsageData() throws IOException {
        testBuilder
            .withProductFamily(SAL_PRODUCT_FAMILY)
            .withItemBankCsvFile("usage/sal/sal_item_bank_details_for_workflow.csv",
                IB_USAGE_ID_1, IB_USAGE_ID_2, IB_USAGE_ID_3)
            .withUsageBatch(buildItemBank())
            .withFundPoolId("077383cf-8d9b-42ac-bdac-073cde78fa1e")
            .expectRmsRights("rights/rms_grants_122769471_request.json", "rights/rms_grants_122769471_response.json")
            .expectRollups("prm/sal_workflow_rollups_response.json", "85f864f2-30a5-4215-ac4f-f1f541901218")
            .expectUsages("usage/sal/sal_expected_details_for_workflow_1.json")
            .expectUsageAudit(IB_USAGE_ID_1, buildExpectedItemBankDetailAudit())
            .expectUsageAudit(IB_USAGE_ID_2, buildExpectedItemBankDetailAudit())
            .expectUsageAudit(IB_USAGE_ID_3, buildExpectedItemBankDetailAudit())
            .build()
            .run();
    }

    @Test
    public void testSalWorkflowWithUsageData() throws IOException {
        testBuilder
            .withProductFamily(SAL_PRODUCT_FAMILY)
            .withItemBankCsvFile("usage/sal/sal_item_bank_details_for_workflow.csv",
                IB_USAGE_ID_1, IB_USAGE_ID_2, IB_USAGE_ID_3)
            .withUsageDataCsvFile("usage/sal/sal_usage_data_for_workflow.csv",
                UD_USAGE_ID_1, UD_USAGE_ID_2, UD_USAGE_ID_3, UD_USAGE_ID_4)
            .withUsageBatch(buildItemBank())
            .withFundPoolId("1aa3a67a-b206-4953-96e7-a9c213db2902")
            .expectRmsRights("rights/rms_grants_122769471_request.json", "rights/rms_grants_122769471_response.json")
            .expectRollups("prm/sal_workflow_rollups_response.json", "85f864f2-30a5-4215-ac4f-f1f541901218")
            .expectUsages("usage/sal/sal_expected_details_for_workflow_2.json")
            .expectUsageAudit(IB_USAGE_ID_1, buildExpectedItemBankDetailAudit())
            .expectUsageAudit(IB_USAGE_ID_2, buildExpectedItemBankDetailAudit())
            .expectUsageAudit(IB_USAGE_ID_3, buildExpectedItemBankDetailAudit())
            .expectUsageAudit(UD_USAGE_ID_1, buildExpectedUsageDataDetailAudit())
            .expectUsageAudit(UD_USAGE_ID_2, buildExpectedUsageDataDetailAudit())
            .expectUsageAudit(UD_USAGE_ID_3, buildExpectedUsageDataDetailAudit())
            .expectUsageAudit(UD_USAGE_ID_4, buildExpectedUsageDataDetailAudit())
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

    private List<UsageAuditItem> buildExpectedItemBankDetailAudit() {
        return Arrays.asList(
            buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible"),
            buildAuditItem(UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000000322 was found in RMS"),
            buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 122769471 was found in PI"),
            buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
        );
    }

    private List<UsageAuditItem> buildExpectedUsageDataDetailAudit() {
        return Collections.singletonList(buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON));
    }

    private UsageAuditItem buildAuditItem(UsageActionTypeEnum actionType, String reason) {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(actionType);
        auditItem.setActionReason(reason);
        return auditItem;
    }
}
