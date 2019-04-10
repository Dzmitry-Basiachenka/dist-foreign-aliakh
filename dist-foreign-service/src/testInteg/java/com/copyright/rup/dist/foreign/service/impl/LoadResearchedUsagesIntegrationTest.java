package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.ResearchedUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

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
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Verifies functionality for loading researched usages.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 12/32/18
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=load-researched-usages-data-init.groovy"})
public class LoadResearchedUsagesIntegrationTest {

    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    @Test
    public void testLoadResearchedUsages() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rights/rms_grants_854030732_request.json", "rights/rms_grants_empty_response.json");
        expectRmsCall("rights/rms_grants_658824345_request.json", "rights/rms_grants_658824345_response.json");
        expectPrmCall(1000023401L, "prm/rightsholder_1000023401_response.json");
        usageService.loadResearchedUsages(Arrays.asList(
            buildResearchedUsage("c219108e-f319-4636-b837-b71bccb29b76", 658824345L, "Medical Journal"),
            buildResearchedUsage("54580cd4-33b5-4079-bfc7-5c35bf9c5c9e", 854030732L, "Technical Journal")));
        assertUsage("c219108e-f319-4636-b837-b71bccb29b76", UsageStatusEnum.ELIGIBLE, 1000023401L, "Medical Journal",
            "VALISSN");
        assertUsage("54580cd4-33b5-4079-bfc7-5c35bf9c5c9e", UsageStatusEnum.RH_NOT_FOUND, null, "Technical Journal",
            "VALISSN");
        assertUsage("644cb9ba-396d-4844-ac83-8053412b7cea", UsageStatusEnum.WORK_RESEARCH, null, null, null);
        assertAudit("c219108e-f319-4636-b837-b71bccb29b76", "Usage has become eligible",
            "Rightsholder account 1000023401 was found in RMS", "Wr Wrk Inst 658824345 was added based on research");
        assertAudit("54580cd4-33b5-4079-bfc7-5c35bf9c5c9e", "Rightsholder account for 854030732 was not found in RMS",
            "Wr Wrk Inst 854030732 was added based on research");
        assertAudit("644cb9ba-396d-4844-ac83-8053412b7cea");
        mockServer.verify();
    }

    private void assertUsage(String usageId, UsageStatusEnum status, Long rhAccounNumber, String systemTitle,
                             String standardNumberType) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(status, usage.getStatus());
        assertEquals(rhAccounNumber, usage.getRightsholder().getAccountNumber());
        assertEquals(systemTitle, usage.getSystemTitle());
        assertEquals(standardNumberType, usage.getStandardNumberType());
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

    private void expectPrmCall(Long accountNumber, String prmResponseFileName) {
        (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/organization/extorgkeys?extOrgKeys%5B%5D=" + accountNumber +
                "&fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(RightsholderService.class, prmResponseFileName), MediaType.APPLICATION_JSON));
    }

    private ResearchedUsage buildResearchedUsage(String usageId, Long wrWrkInst, String systemTitle) {
        ResearchedUsage researchedUsage = new ResearchedUsage();
        researchedUsage.setStandardNumber("978-0-7695-2365-2");
        researchedUsage.setSystemTitle(systemTitle);
        researchedUsage.setUsageId(usageId);
        researchedUsage.setWrWrkInst(wrWrkInst);
        return researchedUsage;
    }
}
