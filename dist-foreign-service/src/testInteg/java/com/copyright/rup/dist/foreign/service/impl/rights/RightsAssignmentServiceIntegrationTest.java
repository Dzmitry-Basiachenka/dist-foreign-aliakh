package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Verifies functionality for sending usages to RMS for rights assignment.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/31/18
 *
 * @author Darya Baraukova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "send-usages-for-ra-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class RightsAssignmentServiceIntegrationTest {

    private static final String AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";
    private static final Set<String> USAGE_IDS = Sets.newHashSet(
        "d82aaf46-8837-4e59-a158-d485d01f9a16",
        "427f017c-688b-4c89-9560-c3ea01e55134",
        "55710948-f203-4547-92b9-3c4526ac32c5");

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IRightsService rightsAssignmentService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    // Test Case ID: af51f1a4-66a6-4d70-b475-50007454f864
    @Test
    public void testSendForRightsAssignment() {
        testHelper.createRestServer();
        testHelper.expectRmsRightsAssignmentCall("rights/rights_assignment_request.json",
            "rights/rights_assignment_response.json");
        JobInfo jobInfo = rightsAssignmentService.sendForRightsAssignment();
        assertEquals(JobStatusEnum.FINISHED, jobInfo.getStatus());
        assertEquals("UsagesCount=3", jobInfo.getResult());
        assertNtsWithdrawnUsage();
        assertSentForRaUsages();
        testHelper.verifyRestServer();
    }

    private void assertNtsWithdrawnUsage() {
        List<String> usageIds = usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.NTS_WITHDRAWN, "FAS");
        assertEquals(3, usageIds.size());
        usageIds.forEach(usageId -> {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
            assertEquals(AUDIT_MESSAGE, auditItems.get(0).getActionReason());
        });
        assertEquals(Collections.emptyList(),
            usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.NTS_WITHDRAWN, "SAL"));
    }

    private void assertSentForRaUsages() {
        List<String> usageIds = usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, "FAS");
        assertEquals(4, usageIds.size());
        usageIds.stream()
            .filter(USAGE_IDS::contains)
            .forEach(usageId -> {
                List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
                assertEquals("Sent for RA: job name 'SENT_FOR_RA_TEST'", auditItems.get(0).getActionReason());
            });
        assertEquals(Collections.emptyList(),
            usageRepository.findIdsByStatusAndProductFamily(UsageStatusEnum.SENT_FOR_RA, "SAL"));
    }
}
