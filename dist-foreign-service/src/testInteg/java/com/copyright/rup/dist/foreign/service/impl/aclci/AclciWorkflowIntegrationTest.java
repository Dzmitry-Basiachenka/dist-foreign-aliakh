package com.copyright.rup.dist.foreign.service.impl.aclci;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.FdaConstants;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageBatch.AclciFields;

import com.google.common.collect.ImmutableMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

/**
 * Verifies ACLCI workflow.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 12/14/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "aclci-workflow-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class AclciWorkflowIntegrationTest {

    private static final Long LICENSEE_ACCOUNT_NUMBER = 1111L;
    private static final String LICENSEE_NAME = "Acuson Corporation";

    @Autowired
    private AclciWorkflowIntegrationTestBuilder testBuilder;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        testBuilder.reset();
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testAclciWorkflow() throws Exception {
        testBuilder
            .withUsageBatch(buildUsageBatch())
            .withUsagesToUpload("aclci/usage/aclci_usages_workflow.csv")
            .withRmsRequests(ImmutableMap.of(
                "rights/aclci/rms_grants_854030732_request.json", "rights/aclci/rms_grants_854030732_response.json",
                "rights/aclci/rms_grants_658824345_request.json", "rights/aclci/rms_grants_658824345_response.json"))
            .withPrmRequests(ImmutableMap.of(
                1000000322L, "prm/rightsholder_1000000322_response.json",
                1000009522L, "prm/rightsholder_1000009522_response.json"))
            .withExpectedUsages("aclci/usage/aclci_usages_workflow.json")
            .withUsageAuditItems(List.of(
                "aclci/audit/aclci_usages_audit_workflow_1.json",
                "aclci/audit/aclci_usages_audit_workflow_2.json"))
            .build()
            .run();
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch usageBatch = new UsageBatch();
        usageBatch.setName("ACLCI Usage Batch");
        usageBatch.setProductFamily(FdaConstants.ACLCI_PRODUCT_FAMILY);
        usageBatch.setPaymentDate(LocalDate.of(2022, 6, 30));
        usageBatch.setAclciFields(buildAclciFields());
        return usageBatch;
    }

    private AclciFields buildAclciFields() {
        AclciFields aclciFields = new AclciFields();
        aclciFields.setLicenseeAccountNumber(LICENSEE_ACCOUNT_NUMBER);
        aclciFields.setLicenseeName(LICENSEE_NAME);
        return aclciFields;
    }
}
