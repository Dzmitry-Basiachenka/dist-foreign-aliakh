package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.impl.RightsholderService;

import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.AsyncRestTemplate;
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

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IRightsService rightsService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.integration.rest.rms.grants.async}")
    private boolean rmsGrantsAsync;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    @Test
    // Test case ID: 'f3568a18-28f1-4807-933f-f8fe4a7396d6'
    public void testUpdateRights() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rms_grants_work_found_usages_request.json", "rms_grants_work_found_usages_response.json");
        expectRmsCall("rms_grants_sent_for_ra_usages_request.json", "rms_grants_sent_for_ra_usages_response.json");
        expectPrmCall();
        rightsService.updateRightsholders();
        assertUsages();
        assertAudit();
        mockServer.verify();
        asyncMockServer.verify();
    }

    private void assertUsages() {
        assertTrue(CollectionUtils.isEmpty(usageRepository.findByStatuses(UsageStatusEnum.WORK_FOUND)));
        assertUsage(8963602L, UsageStatusEnum.NEW, null);
        assertUsage(8963606L, UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage(8963605L, UsageStatusEnum.ELIGIBLE, 1000000322L);
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
        assertAudit("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("11853c83-780a-4533-ad01-dde87c8b8592", "Rightsholder account 1000000322 was found in RMS");
        assertAudit("8aded52d-9507-4883-ab4c-fd2e029298af", "Rightsholder account for 854030731 was not found in RMS");
    }

    private void assertAudit(String usageId, String reason) {
        assertEquals(reason, usageAuditService.getUsageAudit(usageId).get(0).getActionReason());
    }

    private void expectRmsCall(String rmsRequestFileName, String rmsResponseFileName) {
        (rmsGrantsAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo("http://localhost:9051/rms-rights-rest/all-rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), rmsRequestFileName),
                    Lists.newArrayList("period_end_date"))))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponseFileName),
                    MediaType.APPLICATION_JSON));
    }

    private void expectPrmCall() {
        mockServer.expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=1000000322&fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(RightsholderService.class, "prm/rightsholder_1000000322_response.json"),
                MediaType.APPLICATION_JSON));
    }
}
