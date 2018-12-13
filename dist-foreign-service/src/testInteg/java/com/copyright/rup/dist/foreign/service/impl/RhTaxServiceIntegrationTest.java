package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRhTaxService;

import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Verifies {@link IRhTaxService}.
 * <p/>
 * Copyright (C) 2018 copyright.com
 * <p/>
 * Date: 03/22/18
 *
 * @author Uladzislslau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=rh-tax-service-data-init.groovy"})
@Transactional
public class RhTaxServiceIntegrationTest {

    @Autowired
    private IRhTaxService rhTaxService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testProcessTaxCountryUs() throws IOException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        prepareOracleCall(mockServer, "rh_tax_country_us_request.json", "rh_tax_country_us_response.json");
        rhTaxService.processRhTaxCountry(buildUsage("eae81bc0-a756-43a2-b236-05a0184384f4", 2000133267L));
        assertUsage("eae81bc0-a756-43a2-b236-05a0184384f4", UsageStatusEnum.ELIGIBLE);
        assertAudit("eae81bc0-a756-43a2-b236-05a0184384f4", UsageActionTypeEnum.ELIGIBLE,
            "Usage has become eligible based on US rightsholder tax country");
        mockServer.verify();
    }

    @Test
    public void testProcessTaxCountryFr() throws IOException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        prepareOracleCall(mockServer, "rh_tax_country_fr_request.json", "rh_tax_country_fr_response.json");
        rhTaxService.processRhTaxCountry(buildUsage("4ae8c9cb-3cd0-4497-ac8b-f19f85b259cb", 2000133261L));
        assertNull(usageRepository.findById("4ae8c9cb-3cd0-4497-ac8b-f19f85b259cb"));
        mockServer.verify();
    }

    private void assertUsage(String usageId, UsageStatusEnum expectedStatus) {
        Usage usage = usageRepository.findById(usageId);
        assertEquals(expectedStatus, usage.getStatus());
    }

    private void assertAudit(String usageId, UsageActionTypeEnum actionTypeEnum, String reason) {
        List<UsageAuditItem> auditItemList = usageAuditService.getUsageAudit(usageId);
        assertEquals(1, CollectionUtils.size(auditItemList));
        UsageAuditItem auditItem = auditItemList.get(0);
        assertEquals(actionTypeEnum, auditItem.getActionType());
        assertEquals(reason, auditItem.getActionReason());
    }

    private String formatJson(Object objectToFormat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(objectToFormat.toString(), Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private Usage buildUsage(String usageId, Long rhAccountNumber) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.getRightsholder().setAccountNumber(rhAccountNumber);
        return usage;
    }

    private void prepareOracleCall(MockRestServiceServer server, String request, String response) throws IOException {
        server.expect(MockRestRequestMatchers.requestTo(
            "http://localhost:8080/oracle-ap-rest/getRhTaxInformation?fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content().string(new JsonMatcher(
                formatJson(TestUtils.fileToString(this.getClass(), "tax/" + request)))))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "tax/" + response), MediaType.APPLICATION_JSON));
    }
}
