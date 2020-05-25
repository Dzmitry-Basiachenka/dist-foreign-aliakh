package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
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
@TestPropertySource(properties = {"test.liquibase.changelog=aacl-workflow-data-init.groovy"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class AaclWorkflowIntegrationTest {

    private static final String USAGE_ID_1 = "157b5b4e-cf77-48f5-aeb0-bd3ac75d8585";
    private static final String USAGE_ID_2 = "30930c3f-e3d0-492d-a1af-587a8e9201bb";
    private static final String USAGE_ID_3 = "91b42694-5057-4a6a-99df-b10599549653";
    private static final String USAGE_COMMENT_1 = "AACL usage Comment 1";
    private static final String USAGE_COMMENT_2 = "AACL usage Comment 2";
    private static final String BASELINE_USAGE_COMMENT_1 = "AACL baseline usage Comment 1";
    private static final String BASELINE_USAGE_COMMENT_2 = "AACL baseline usage Comment 2";
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
            .withFundPool(buildFundPool())
            .withFundPoolDetails(Collections.singletonList(buildFundPoolDetail()))
            .withUsageFilter(buildUsageFilter())
            .withAaclFields(buildAaclFields())
            .withUsagesCsvFile("usage/aacl/aacl_usages_for_workflow.csv", 6, USAGE_ID_1, USAGE_ID_2, USAGE_ID_3)
            .withClassifiedUsagesCsvFile("usage/aacl/classified/classified_usages_workflow.csv")
            .withUsageBatch(buildUsageBatch())
            .expectScenario(buildExpectedScenario())
            .expectUsages("usage/aacl/aacl_expected_usages_for_workflow.json")
            .expectRollups("prm/aacl_workflow_rollups_response.json", "60080587-a225-439c-81af-f016cb33aeac")
            .expectRmsRights("rights/aacl/rms_grants_100009840_request_workflow.json",
                "rights/aacl/rms_grants_100009840_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_100010768_request_workflow.json",
                "rights/aacl/rms_grants_100010768_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_123456789_request_workflow.json",
                "rights/rms_grants_empty_response.json")
            .expectLmDetails(1, "details/aacl_details_to_lm.json")
            .expectPaidUsagesFromLm("lm/paid_usages_aacl_workflow.json")
            .expectPaidUsageLmDetailIds("b86a63d6-48c3-4291-b5bc-3bc9ddef0e66", "11ca9410-8ae0-4e7e-bd66-b2c9b9211269")
            .expectCrmReporting("crm/workflow/rights_distribution_request_aacl.json",
                "crm/workflow/rights_distribution_response_aacl.json")
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
            .expectUsageAudit(USAGE_COMMENT_1, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.ARCHIVED, "Usage was sent to CRM"),
                buildAuditItem(UsageActionTypeEnum.PAID, "Usage has been paid according to information from the LM"),
                buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usages has become eligible after classification"),
                buildAuditItem(UsageActionTypeEnum.WORK_RESEARCH, "Usage detail was sent for classification"),
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI"),
                buildAuditItem(UsageActionTypeEnum.LOADED, UPLOADED_REASON)
            ))
            .expectUsageAudit(USAGE_COMMENT_2, Arrays.asList(
                buildAuditItem(UsageActionTypeEnum.ARCHIVED, "Usage was sent to CRM"),
                buildAuditItem(UsageActionTypeEnum.PAID, "Usage has been paid according to information from the LM"),
                buildAuditItem(UsageActionTypeEnum.ELIGIBLE, "Usages has become eligible after classification"),
                buildAuditItem(UsageActionTypeEnum.WORK_RESEARCH, "Usage detail was sent for classification"),
                buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
                buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI"),
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

    private Scenario buildExpectedScenario() {
        Scenario scenario = new Scenario();
        scenario.setName("Test AACL Scenario");
        scenario.setDescription("Test Scenario Description");
        scenario.setAaclFields(buildAaclFields());
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        return scenario;
    }

    private FundPool buildFundPool() {
        FundPool fundPool = new FundPool();
        fundPool.setName("Fund pool");
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
        fundPool.setTotalAmount(new BigDecimal("500.00"));
        return fundPool;
    }

    private FundPoolDetail buildFundPoolDetail() {
        FundPoolDetail fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setFundPoolId("8efb2b5e-e457-4963-8e52-233e09104b40");
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseClass());
        fundPoolDetail.setGrossAmount(new BigDecimal("500.00"));
        return fundPoolDetail;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(AACL_PRODUCT_FAMILY);
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private AaclFields buildAaclFields() {
        AaclFields fields = new AaclFields();
        fields.setFundPoolId("8efb2b5e-e457-4963-8e52-233e09104b40");
        fields.setTitleCutoffAmount(BigDecimal.ZERO);
        fields.setUsageAges(Arrays.asList(
            buildUsageAge(2019, new BigDecimal("4.29")),
            buildUsageAge(2018, new BigDecimal("3.5"))));
        fields.setPublicationTypes(Arrays.asList(
            buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", new BigDecimal("3.5")),
            buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", new BigDecimal("1.24")),
            buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", new BigDecimal("4.29")),
            buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", new BigDecimal("2"))));
        fields.setDetailLicenseeClasses(Arrays.asList(
            buildDetailClass(113, 113),
            buildDetailClass(195, 113),
            buildDetailClass(141, 113)));
        return fields;
    }

    private UsageAge buildUsageAge(int date, BigDecimal weight) {
        UsageAge usageAge = new UsageAge();
        usageAge.setPeriod(date);
        usageAge.setWeight(weight);
        return usageAge;
    }

    private PublicationType buildPublicationType(String pubTypeId, BigDecimal weight) {
        PublicationType pubType = new PublicationType();
        pubType.setId(pubTypeId);
        pubType.setWeight(weight);
        return pubType;
    }

    private AggregateLicenseeClass buildAggregateLicenseClass() {
        AggregateLicenseeClass aggregateLicenseeClass = new AggregateLicenseeClass();
        aggregateLicenseeClass.setDiscipline("Life Sciences");
        aggregateLicenseeClass.setEnrollmentProfile("MU");
        return aggregateLicenseeClass;
    }

    private DetailLicenseeClass buildDetailClass(Integer detailClassId, Integer aggregateClassId) {
        DetailLicenseeClass detailClass = new DetailLicenseeClass();
        AggregateLicenseeClass aggregateClass = new AggregateLicenseeClass();
        detailClass.setId(detailClassId);
        aggregateClass.setId(aggregateClassId);
        detailClass.setAggregateLicenseeClass(aggregateClass);
        return detailClass;
    }
}
