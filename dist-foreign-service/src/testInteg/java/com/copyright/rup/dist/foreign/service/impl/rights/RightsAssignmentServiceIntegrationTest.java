package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

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
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=send-usages-for-ra-data-init.groovy"})
public class RightsAssignmentServiceIntegrationTest {

    private static final String AUDIT_MESSAGE =
        "Detail was made eligible for NTS because sum of gross amounts, grouped by Wr Wrk Inst, is less than $100";

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IRightsService rightsAssignmentService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private RestTemplate restTemplate;

    private static final Set<String> USAGE_IDS = Sets.newHashSet(
        "d82aaf46-8837-4e59-a158-d485d01f9a16",
        "427f017c-688b-4c89-9560-c3ea01e55134",
        "55710948-f203-4547-92b9-3c4526ac32c5");

    // Test Case ID: af51f1a4-66a6-4d70-b475-50007454f864
    @Test
    public void testSendForRightsAssignment() {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(MockRestRequestMatchers.requestTo("http://localhost:9051/rms-rights-rest/jobs/wrwrkinst/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(
                    StringUtils.trim(TestUtils.fileToString(this.getClass(), "rights_assignment_request.json")))))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "rights_assignment_response.json"),
                MediaType.APPLICATION_JSON));
        JobInfo jobInfo = rightsAssignmentService.sendForRightsAssignment();
        assertEquals(JobStatusEnum.FINISHED, jobInfo.getStatus());
        assertEquals("UsagesCount=3", jobInfo.getResult());
        assertNtsWithdrawnUsage();
        assertSentForRaUsages();
        mockServer.verify();
    }

    private void assertNtsWithdrawnUsage() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.NTS_WITHDRAWN);
        assertEquals(3, usages.size());
        usages.forEach(usage -> {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usage.getId());
            assertEquals(AUDIT_MESSAGE, auditItems.get(0).getActionReason());
        });
    }

    private void assertSentForRaUsages() {
        List<Usage> usages = usageRepository.findByStatuses(UsageStatusEnum.SENT_FOR_RA);
        assertEquals(4, usages.size());
        usages.stream()
            .filter(usage -> USAGE_IDS.contains(usage.getId()))
            .forEach(usage -> {
                List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usage.getId());
                assertEquals("Sent for RA: job name 'SENT_FOR_RA_TEST'", auditItems.get(0).getActionReason());
            });
    }
}
