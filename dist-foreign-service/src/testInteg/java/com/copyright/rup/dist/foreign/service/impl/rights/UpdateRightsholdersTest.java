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
    @Value("$RUP{dist.foreign.integration.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    @Test
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
        assertUsage("2de40e13-d353-44ce-b6bb-a11383ba9fb9", UsageStatusEnum.NEW, null);
        assertUsage("e6378e17-b0c9-420f-aa5c-a653156339d2", UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage("11853c83-780a-4533-ad01-dde87c8b8592", UsageStatusEnum.ELIGIBLE, 1000000322L);
        assertUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", UsageStatusEnum.ELIGIBLE, 1000010077L);
        assertUsage("37c4d727-caeb-4a7f-b11a-34e313b0bfcc", UsageStatusEnum.ELIGIBLE, 1000009522L);
        assertUsage("ff321d96-04bd-11e8-ba89-0ed5f89f718b", UsageStatusEnum.LOCKED, 1000009522L);
        assertUsage("19ca7776-48c8-472e-acfe-d49b6e8780ce", UsageStatusEnum.RH_NOT_FOUND, null);
        assertUsage("8aded52d-9507-4883-ab4c-fd2e029298af", UsageStatusEnum.RH_NOT_FOUND, null);
    }

    private void assertUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccounNumber) {
        Usage usage = usageRepository.findById(usageId);
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
        mockServer
            .expect(MockRestRequestMatchers.requestTo("http://localhost:9051/rms-rights-rest/all-rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), rmsRequestFileName),
                    Lists.newArrayList("period_end_date"))))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponseFileName),
                    MediaType.APPLICATION_JSON));
    }

    private void expectPrmCall() {
        (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=1000000322&fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(RightsholderService.class, "prm/rightsholder_1000000322_response.json"),
                MediaType.APPLICATION_JSON));
    }
}
