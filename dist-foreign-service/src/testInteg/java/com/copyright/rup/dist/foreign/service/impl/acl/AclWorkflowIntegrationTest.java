package com.copyright.rup.dist.foreign.service.impl.acl;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
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

import java.util.Arrays;
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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
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
                .put("rights/rms_grants_udm_876543210_request.json", "rights/rms_grants_udm_876543210_response.json")
                .put("rights/rms_grants_udm_123059057_request.json", "rights/rms_grants_udm_123059057_response.json")
                .put("rights/rms_grants_udm_987654321_request.json", "rights/rms_grants_empty_response.json")
                .put("rights/rms_grants_udm_140160102_request.json", "rights/rms_grants_udm_140160102_response.json")
                .put("rights/rms_grants_udm_243618757_request.json", "rights/rms_grants_udm_243618757_response.json")
                .put("rights/rms_grants_udm_values_202006_1_request.json",
                    "rights/rms_grants_udm_values_202006_1_response.json")
                .put("rights/rms_grants_udm_values_202006_2_request.json",
                    "rights/rms_grants_udm_values_202006_2_response.json")
                .build())
            .withPrmRequests(ImmutableMap.of(
                1000000322L, "prm/rightsholder_1000000322_response.json",
                1000024950L, "prm/rightsholder_1000024950_response.json"
            ))
            .withExpectedCountOfPublishedUsages(4)
            .withExpectedCountOfPopulatedValues(4)
            .withExpectedCountOfPublishedValues(7)
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
}
