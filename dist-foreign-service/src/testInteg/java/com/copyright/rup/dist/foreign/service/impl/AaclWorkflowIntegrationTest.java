package com.copyright.rup.dist.foreign.service.impl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AggregateLicenseeClass;
import com.copyright.rup.dist.foreign.domain.DetailLicenseeClass;
import com.copyright.rup.dist.foreign.domain.FundPool;
import com.copyright.rup.dist.foreign.domain.FundPoolDetail;
import com.copyright.rup.dist.foreign.domain.PublicationType;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Scenario.AaclFields;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAge;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
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
import java.util.Collections;
import java.util.List;

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
//TODO: split test data into separate files for each test method
@TestData(fileName = "aacl-workflow-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AaclWorkflowIntegrationTest {

    private static final String USAGE_ID_1 = "157b5b4e-cf77-48f5-aeb0-bd3ac75d8585";
    private static final String USAGE_ID_2 = "30930c3f-e3d0-492d-a1af-587a8e9201bb";
    private static final String USAGE_ID_3 = "91b42694-5057-4a6a-99df-b10599549653";
    private static final String USAGE_ID_4 = "63cf2b2c-4c96-443a-bc9d-04cc5a31f1be";
    private static final String USAGE_ID_5 = "5be7d7eb-e25e-4273-a287-5346c2538ea9";
    private static final String USAGE_ID_6 = "41161ec5-d33d-43ec-9db0-282d0eb1de66";
    private static final String USAGE_ID_7 = "d259aee8-7442-45ba-b399-b0ddfd7a7a20";
    private static final String FUND_POOL_ID_1 = "6360063a-bd53-47f2-aaa6-3252df06fb6c";
    private static final String FUND_POOL_ID_2 = "9fed0f9e-7c97-42f6-bd80-83c759879839";
    private static final String USAGE_COMMENT_1 = "AACL usage Comment 1";
    private static final String USAGE_COMMENT_2 = "AACL usage Comment 2";
    private static final String BASELINE_USAGE_COMMENT_1 = "AACL baseline usage Comment 1";
    private static final String BASELINE_USAGE_COMMENT_2 = "AACL baseline usage Comment 2";
    private static final String UPLOADED_REASON_1 = "Uploaded in 'AACL test batch' Batch";
    private static final String UPLOADED_REASON_2 = "Uploaded in 'AACL test batch-2' Batch";
    private static final String BASELINE_UPLOADED_REASON = "Pulled from baseline for 'AACL test batch' Batch";
    private static final String RH_FOUND_REASON = "Rightsholder account 1000024950 was found in RMS";
    private static final String ARCHIVED_REASON = "Usage was sent to CRM";
    private static final String PAID_REASON = "Usage has been paid according to information from the LM";
    private static final String ELIGIBLE_REASON = "Usages has become eligible after classification";
    private static final String CLASSIFICATION_REASON = "Usage detail was sent for classification";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private AaclWorkflowIntegrationTestBuilder testBuilder;

    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testBuilder.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testAaclWorkflow() throws Exception {
        testBuilder
            .withProductFamily(AACL_PRODUCT_FAMILY)
            .withFundPool(buildFundPool("Fund pool"))
            .withFundPoolDetails(Collections.singletonList(buildFundPoolDetail(FUND_POOL_ID_1)))
            .withUsageFilter(buildUsageFilter())
            .withAaclFields(buildAaclFields(FUND_POOL_ID_1))
            .withUsagesCsvFile("usage/aacl/aacl_usages_for_workflow.csv", 6, USAGE_ID_1, USAGE_ID_2, USAGE_ID_3)
            .withClassifiedUsagesCsvFile("usage/aacl/classified/classified_usages_workflow.csv")
            .withUsageBatch(buildUsageBatch("AACL test batch", 2))
            .expectScenario(buildExpectedScenario(FUND_POOL_ID_1), 4)
            .expectUsages("usage/aacl/aacl_expected_usages_for_workflow.json")
            .expectArchivedUsages("usage/aacl/aacl_expected_archived_usages_for_workflow.json")
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
            .expectScenarioAudit(buildExpectedScenarioAudit())
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
            .expectUsageAudit(USAGE_COMMENT_1, buildAuditItems(100009840, UPLOADED_REASON_1))
            .expectUsageAudit(USAGE_COMMENT_2, buildAuditItems(100010768, UPLOADED_REASON_1))
            .build()
            .run();
    }

    @Test
    public void testAaclWorkflowWithExcludedUsagesByPayees() throws Exception {
        testBuilder
            .withProductFamily(AACL_PRODUCT_FAMILY)
            .withFundPool(buildFundPool("Fund pool-2"))
            .withFundPoolDetails(Collections.singletonList(buildFundPoolDetail(FUND_POOL_ID_2)))
            .withUsageFilter(buildUsageFilter())
            .withAaclFields(buildAaclFields(FUND_POOL_ID_2))
            .withUsagesCsvFile("usage/aacl/aacl_usages_for_workflow_excluded.csv", 4, USAGE_ID_4, USAGE_ID_5,
                USAGE_ID_6, USAGE_ID_7)
            .withClassifiedUsagesCsvFile("usage/aacl/classified/classified_usages_for_workflow_excluded.csv")
            .withUsageBatch(buildUsageBatch("AACL test batch-2", 0))
            .expectRmsRights("rights/aacl/rms_grants_100010768_request_workflow.json",
                "rights/aacl/rms_grants_100010768_response_workflow.json")
            .expectRmsRights("rights/aacl/rms_grants_123456789_request_workflow.json",
                "rights/aacl/rms_grants_123456789_response_workflow.json")
            .expectRollups("prm/aacl_workflow_excluded_rollups_response.json",
                "60080587-a225-439c-81af-f016cb33aeac", "a02af8f9-2ee4-4045-8bb5-79529fc087a6")
            .expectScenario(buildExpectedScenario(FUND_POOL_ID_2), 4)
            .withPayeesToExclude(7001508482L)
            .expectLmDetails(1, "details/aacl_details_to_lm2.json")
            .expectPaidUsagesFromLm("lm/paid_usages_aacl_workflow_excluded.json")
            .expectPaidUsageLmDetailIds("c8bb25a2-0b65-41ee-ad50-32711e7bea97", "6311542c-0f26-4b15-bd70-c82d6b74800b")
            .expectCrmReporting("crm/workflow/rights_distribution_request_aacl-excluded.json",
                "crm/workflow/rights_distribution_response_aacl.json")
            .expectArchivedUsages("usage/aacl/aacl_expected_archived_usages_for_workflow_excluded.json")
            .expectScenarioAudit(buildExpectedScenarioAuditExcluded())
            .expectUsageAudit(USAGE_COMMENT_1, buildAuditItems(100010768, UPLOADED_REASON_2))
            .expectUsageAudit(USAGE_COMMENT_2, buildAuditItems(100010768, UPLOADED_REASON_2))
            .build()
            .run();
    }

    private UsageBatch buildUsageBatch(String name, int numbersOfYears) {
        UsageBatch batch = new UsageBatch();
        batch.setName(name);
        batch.setProductFamily(AACL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        batch.setNumberOfBaselineYears(numbersOfYears);
        return batch;
    }

    private List<UsageAuditItem> buildAuditItems(int wrWkrInst, String uploadedRason) {
        return Arrays.asList(
            buildAuditItem(UsageActionTypeEnum.ARCHIVED, ARCHIVED_REASON),
            buildAuditItem(UsageActionTypeEnum.PAID, PAID_REASON),
            buildAuditItem(UsageActionTypeEnum.ELIGIBLE, ELIGIBLE_REASON),
            buildAuditItem(UsageActionTypeEnum.WORK_RESEARCH, CLASSIFICATION_REASON),
            buildAuditItem(UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON),
            buildAuditItem(UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst " + wrWkrInst + " was found in PI"),
            buildAuditItem(UsageActionTypeEnum.LOADED, uploadedRason));
    }

    private UsageAuditItem buildAuditItem(UsageActionTypeEnum actionType, String reason) {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setActionType(actionType);
        auditItem.setActionReason(reason);
        return auditItem;
    }

    private Scenario buildExpectedScenario(String fundPoolId) {
        Scenario scenario = new Scenario();
        scenario.setName("Test AACL Scenario");
        scenario.setDescription("Test Scenario Description");
        scenario.setAaclFields(buildAaclFields(fundPoolId));
        scenario.setStatus(ScenarioStatusEnum.IN_PROGRESS);
        return scenario;
    }

    private FundPool buildFundPool(String name) {
        FundPool fundPool = new FundPool();
        fundPool.setName(name);
        fundPool.setProductFamily(AACL_PRODUCT_FAMILY);
        return fundPool;
    }

    private FundPoolDetail buildFundPoolDetail(String fundPoolId) {
        FundPoolDetail fundPoolDetail = new FundPoolDetail();
        fundPoolDetail.setFundPoolId(fundPoolId);
        fundPoolDetail.setAggregateLicenseeClass(buildAggregateLicenseClass());
        fundPoolDetail.setGrossAmount(new BigDecimal("1000.00"));
        return fundPoolDetail;
    }

    private UsageFilter buildUsageFilter() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(AACL_PRODUCT_FAMILY);
        filter.setUsageStatus(UsageStatusEnum.ELIGIBLE);
        return filter;
    }

    private AaclFields buildAaclFields(String fundPoolId) {
        AaclFields fields = new AaclFields();
        fields.setFundPoolId(fundPoolId);
        fields.setUsageAges(Arrays.asList(
            buildUsageAge(2019, new BigDecimal("4.29")),
            buildUsageAge(2018, new BigDecimal("3.5")),
            buildUsageAge(2020, new BigDecimal("3.5"))));
        fields.setPublicationTypes(Arrays.asList(
            buildPublicationType("2fe9c0a0-7672-4b56-bc64-9d4125fecf6e", new BigDecimal("3.5")),
            buildPublicationType("1f6f1925-7aa1-4b1a-b3a8-8903acc3d18e", new BigDecimal("1.24")),
            buildPublicationType("a3dff475-fc06-4d8c-b7cc-f093073ada6f", new BigDecimal("4.29")),
            buildPublicationType("46634907-882e-4f91-b1ad-f57db945aff7", new BigDecimal("2"))));
        fields.setDetailLicenseeClasses(Arrays.asList(
            buildDetailClass(113, 113),
            buildDetailClass(195, 113),
            buildDetailClass(108, 113),
            buildDetailClass(110, 113),
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
        aggregateLicenseeClass.setId(113);
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

    private List<Pair<ScenarioActionTypeEnum, String>> buildExpectedScenarioAudit() {
        return Arrays.asList(
            Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, ""),
            Pair.of(ScenarioActionTypeEnum.SUBMITTED, "Submitting scenario for testing purposes"),
            Pair.of(ScenarioActionTypeEnum.APPROVED, "Approving scenario for testing purposes"),
            Pair.of(ScenarioActionTypeEnum.SENT_TO_LM, ""));
    }

    private List<Pair<ScenarioActionTypeEnum, String>> buildExpectedScenarioAuditExcluded() {
        return Arrays.asList(
            Pair.of(ScenarioActionTypeEnum.ADDED_USAGES, ""),
            Pair.of(ScenarioActionTypeEnum.SUBMITTED, "Submitting scenario for testing purposes"),
            Pair.of(ScenarioActionTypeEnum.APPROVED, "Approving scenario for testing purposes"),
            Pair.of(ScenarioActionTypeEnum.SENT_TO_LM, ""),
            Pair.of(ScenarioActionTypeEnum.ARCHIVED, "All usages from scenario have been sent to CRM"));
    }
}
