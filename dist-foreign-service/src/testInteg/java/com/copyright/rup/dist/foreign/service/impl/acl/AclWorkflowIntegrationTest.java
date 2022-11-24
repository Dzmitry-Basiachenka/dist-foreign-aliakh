package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AclFundPool;
import com.copyright.rup.dist.foreign.domain.AclGrantSet;
import com.copyright.rup.dist.foreign.domain.AclScenario;
import com.copyright.rup.dist.foreign.domain.AclUsageBatch;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies ACL workflow.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 10/21/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "acl-workflow-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclWorkflowIntegrationTest {

    @Autowired
    private AclWorkflowIntegrationTestBuilder testBuilder;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testBuilder.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testAclWorkflow() throws Exception {
        testBuilder
            .withUdmBatch(buildUdmBatch())
            .withUsagesToUpload("acl/usage/udm_usages_workflow.csv")
            .withRmsRequests(ImmutableMap.<String, String>builder()
                .put("rights/udm/usage/rms_grants_876543210_request.json",
                    "rights/udm/usage/rms_grants_876543210_response.json")
                .put("rights/udm/usage/rms_grants_request_2.json", "rights/udm/usage/rms_grants_response_2.json")
                .put("rights/udm/usage/rms_grants_request_3.json", "rights/udm/usage/rms_grants_response_3.json")
                .put("rights/udm/value/rms_grants_202006_1_request.json",
                    "rights/udm/value/rms_grants_202006_1_response.json")
                .put("rights/udm/value/rms_grants_202006_2_request.json",
                    "rights/udm/value/rms_grants_202006_2_response.json")
                .put("rights/acl/rms_grants_243618757_876543213_876543212_request.json",
                    "rights/acl/rms_grants_243618757_876543213_876543212_response.json")
                .put("rights/acl/rms_grants_123059057_876543211_876543210_request.json",
                    "rights/acl/rms_grants_123059057_876543211_876543210_response.json")
                .put("rights/acl/rms_grants_140160102_request.json",
                    "rights/acl/rms_grants_140160102_response.json")
                .build())
            .withPrmRequests(ImmutableMap.of(
                1000000322L, "prm/rightsholder_1000000322_response.json",
                1000024950L, "prm/rightsholder_1000024950_response.json",
                1000010079L, "prm/rightsholder_1000010079_response.json"
            ))
            .withPrmIneligibleParentCall("acl/ineligible.rightsholders/ineligible_rightsholder_empty.json")
            .withPrmRollups("prm/acl_workflow_rollups_response.json", "1180895b-04e3-4052-b11a-ec4b1a612a35")
            .withExpectedUsages("acl/usage/udm_usages_workflow.json")
            .withExpectedValues("acl/value/udm_value_dto_workflow.json")
            .withExpectedValuesBaseline("acl/value/udm_value_dto_baseline_workflow.json")
            .withUdmUsageAuditItems(Arrays.asList(
                "acl/audit/udm_usages_audit_workflow_1.json",
                "acl/audit/udm_usages_audit_workflow_2.json",
                "acl/audit/udm_usages_audit_workflow_3.json",
                "acl/audit/udm_usages_audit_workflow_4.json",
                "acl/audit/udm_usages_audit_workflow_5.json",
                "acl/audit/udm_usages_audit_workflow_6.json"))
            .withUdmValueAuditItems(Arrays.asList(
                "acl/audit/udm_values_audit_workflow_1.json",
                "acl/audit/udm_values_audit_workflow_2.json",
                "acl/audit/udm_values_audit_workflow_3.json",
                "acl/audit/udm_values_audit_workflow_4.json"))
            .withAclUsageBatch(buildAclUsageBatch())
            .withLdmtFundPoolDetails("oracle/acl_workflow_ldmt_details.json")
            .withAclFundPool(buildAclFundPool())
            .withAclGrantSet(buildAclGrantSet())
            .withDefaultAclScenarioUsageAges()
            .withDefaultAclScenarioPubTypes()
            .withDefaultAclScenarioDetLicClasses()
            .withAclScenario(buildAclScenario())
            .withLmDetails("details/acl_details_to_lm.json")
            .withExpectedAclScenario("acl/scenario/acl_workflow_scenario.json")
            .withExpectedAclScenarioDetails("acl/scenario/acl_workflow_scenario_details.json")
            .build()
            .run();
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setName("UDM Batch 2020 June");
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }

    private AclUsageBatch buildAclUsageBatch() {
        AclUsageBatch usageBatch = new AclUsageBatch();
        usageBatch.setName("Test ACL Usage Batch");
        usageBatch.setDistributionPeriod(202006);
        usageBatch.setPeriods(Collections.singleton(202006));
        usageBatch.setEditable(false);
        return usageBatch;
    }

    private AclFundPool buildAclFundPool() {
        AclFundPool fundPool = new AclFundPool();
        fundPool.setName("Test ACL Fund Pool");
        fundPool.setPeriod(202006);
        fundPool.setLicenseType("ACL");
        fundPool.setManualUploadFlag(false);
        fundPool.setTotalAmount(new BigDecimal("1000.00"));
        fundPool.setNetAmount(new BigDecimal("800.00"));
        return fundPool;
    }

    private AclGrantSet buildAclGrantSet() {
        AclGrantSet grantSet = new AclGrantSet();
        grantSet.setName("Test ACl Grant Set");
        grantSet.setGrantPeriod(202006);
        grantSet.setPeriods(Collections.singleton(202006));
        grantSet.setLicenseType("ACL");
        grantSet.setEditable(false);
        return grantSet;
    }

    private AclScenario buildAclScenario() {
        AclScenario scenario = new AclScenario();
        scenario.setName("Test ACL Scenario");
        scenario.setDescription("some description");
        scenario.setPeriodEndDate(202006);
        scenario.setLicenseType("ACL");
        scenario.setEditableFlag(false);
        return scenario;
    }
}
