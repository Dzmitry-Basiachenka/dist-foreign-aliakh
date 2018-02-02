package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

/**
 * Verifies correctness of updating usages rightsholders.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 01/26/18
 *
 * @author Pavel Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=update-rightsholders-data-init.groovy"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UpdateRightsholdersTest {

    private static final String RH_WAS_FOUND_REASON = "Rightsholder account %s was found in RMS";
    private static final String RH_WAS_NOT_FOUND_REASON = "Rightsholder account for %s was not found in RMS";
    private MockRestServiceServer mockServer;

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testUpdateRights() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        expectRmsCall("rms/rms_grants_request.json", "rms/rms_grants_response.json");
        expectPrmCall("prm/prm_rightsholder_1000010077_response.json");
        usageService.updateRightsholders();
        mockServer.verify();
        assertUsages();
        assertAudit();
    }

    private void assertUsages() {
        assertTrue(CollectionUtils.isEmpty(usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND)));
        assertUsage(8963602L, UsageStatusEnum.NEW, null);
        assertUsage(8963606L, UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage(8963603L, UsageStatusEnum.ELIGIBLE, 1000010077L);
        assertUsage(8963601L, UsageStatusEnum.ELIGIBLE, 1000009522L);
        assertUsage(8963609L, UsageStatusEnum.LOCKED, 1000009522L);
        assertUsage(8963607L, UsageStatusEnum.RH_NOT_FOUND, null);
        assertUsage(8963604L, UsageStatusEnum.RH_NOT_FOUND, null);
    }

    private void assertUsage(Long detailId, UsageStatusEnum expectedStatus, Long expectedRhAccounNumber) {
        Usage usage = usageRepository.findByDetailId(detailId);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccounNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertAudit() {
        assertAudit("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", String.format(RH_WAS_FOUND_REASON, 1000010077));
        assertAudit("8aded52d-9507-4883-ab4c-fd2e029298af", String.format(RH_WAS_NOT_FOUND_REASON, 854030731));
    }

    private void assertAudit(String usageId, String reason) {
        assertEquals(reason, usageAuditService.getUsageAudit(usageId).get(0).getActionReason());
    }

    private void expectRmsCall(String rmsRequestFileName, String rmsResponseFileName) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:9051/rms-rights-rest/all-rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), rmsRequestFileName),
                    Lists.newArrayList("period_end_date"))))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponseFileName),
                    MediaType.APPLICATION_JSON));
    }

    private void expectPrmCall(String prmResponseFileName) {
        mockServer.expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=1000010077&fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), prmResponseFileName),
                    MediaType.APPLICATION_JSON));
    }
}
