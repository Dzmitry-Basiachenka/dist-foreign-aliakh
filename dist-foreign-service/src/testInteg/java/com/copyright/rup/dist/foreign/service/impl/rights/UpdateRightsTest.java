package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.impl.RightsholderService;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies correctness of updating usages rights.
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
@TestPropertySource(properties = {"test.liquibase.changelog=update-rights-data-init.groovy"})
@Transactional
public class UpdateRightsTest {

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
    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    @Test
    public void testUpdateRightsSentForRaUsages() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rms_grants_854030732_request.json", "rms_grants_empty_response.json");
        expectRmsCall("rms_grants_122824345_request.json", "rms_grants_122824345_response.json");
        expectPrmCall();
        rightsService.updateRightsSentForRaUsages();
        assertUsage("2de40e13-d353-44ce-b6bb-a11383ba9fb9", UsageStatusEnum.NEW, null);
        assertUsage("e6378e17-b0c9-420f-aa5c-a653156339d2", UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage("11853c83-780a-4533-ad01-dde87c8b8592", UsageStatusEnum.ELIGIBLE, 1000000322L);
        assertUsage("37c4d727-caeb-4a7f-b11a-34e313b0bfcc", UsageStatusEnum.ELIGIBLE, 1000009522L);
        assertUsage("ff321d96-04bd-11e8-ba89-0ed5f89f718b", UsageStatusEnum.LOCKED, 1000009522L);
        assertUsage("19ca7776-48c8-472e-acfe-d49b6e8780ce", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("11853c83-780a-4533-ad01-dde87c8b8592", "Usage has become eligible",
            "Rightsholder account 1000000322 was found in RMS");
        mockServer.verify();
        asyncMockServer.verify();
    }

    @Test
    public void testUpdateRight() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rms_grants_254030731_request.json", "rms_grants_254030731_response.json");
        expectRmsCall("rms_grants_658824345_request.json", "rms_grants_658824345_response.json");
        expectRmsCall("rms_grants_488824345_request.json", "rms_grants_empty_response.json");
        rightsService.updateRight(buildUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", 254030731L));
        rightsService.updateRight(buildUsage("8aded52d-9507-4883-ab4c-fd2e029298af", 254030731L));
        rightsService.updateRight(buildUsage("74ded52a-4454-1225-ab4c-fA2e029298af", 658824345L));
        rightsService.updateRight(buildUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", 488824345L));
        assertUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("8aded52d-9507-4883-ab4c-fd2e029298af", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("74ded52a-4454-1225-ab4c-fA2e029298af", UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("8aded52d-9507-4883-ab4c-fd2e029298af", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("74ded52a-4454-1225-ab4c-fA2e029298af", "Rightsholder account 1000023401 was found in RMS");
        assertAudit("3a6b6f25-9f68-4da7-be4f-dd65574f5168", "Rightsholder account for 488824345 was not found in RMS");
        mockServer.verify();
        asyncMockServer.verify();
    }

    private Usage buildUsage(String usageId, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        usage.setProductFamily("FAS");
        return usage;
    }

    private void assertUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccounNumber) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccounNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertAudit(String usageId, String... reasons) {
        List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
        assertEquals(CollectionUtils.size(auditItems), ArrayUtils.getLength(reasons));
        IntStream.range(0, reasons.length)
            .forEach(index -> assertEquals(reasons[index], auditItems.get(index).getActionReason()));
    }

    private void expectRmsCall(String rmsRequestFileName, String rmsResponseFileName) {
        mockServer
            .expect(MockRestRequestMatchers.requestTo("http://localhost:9051/rms-rights-rest/rights/"))
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
