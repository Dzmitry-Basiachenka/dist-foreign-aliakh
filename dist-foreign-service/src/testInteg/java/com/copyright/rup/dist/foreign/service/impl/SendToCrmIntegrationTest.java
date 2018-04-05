package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;

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
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Verifies logic for sending {@link com.copyright.rup.dist.foreign.domain.PaidUsage}s to CRM.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 04/04/18
 *
 * @author Darya Baraukova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=send-to-crm-data-init.groovy"})
public class SendToCrmIntegrationTest {

    @Autowired
    private IUsageService usageService;
    @Autowired
    private IUsageAuditService usageAuditService;
    @Autowired
    private IUsageArchiveRepository usageArchiveRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testSendToCrm() throws IOException {
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(MockRestRequestMatchers.requestTo(
            "http://localhost:9061/legacy-integration-rest/insertCCCRightsDistribution"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(
                    formatJson(TestUtils.fileToString(this.getClass(), "crm/rights_distribution_request.json")))))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "crm/rights_distribution_response.json"),
                MediaType.APPLICATION_JSON));
        usageService.sendToCrm();
        verifyUsages("0d1829eb-de35-4f93-bb36-2a7435263051", UsageStatusEnum.ARCHIVED);
        verifyUsages("9e356e22-57b3-49b3-af99-155093a9dc0a", UsageStatusEnum.PAID);
        verifyUsages("feefdfd2-71fe-4c0a-a701-9dacffa9bccb", UsageStatusEnum.LOCKED);
        //TODO {dbaraukova} add veirifcation for scenario
        mockServer.verify();
    }

    private void verifyUsagesCount(List<PaidUsage> usages) {
        assertTrue(CollectionUtils.isNotEmpty(usages));
        assertEquals(1, usages.size());
    }

    private void verifyUsages(String usageId, UsageStatusEnum status) {
        List<PaidUsage> usages =
            usageArchiveRepository.findByIdAndStatus(Collections.singletonList(usageId), status);
        verifyUsagesCount(usages);
        if (UsageStatusEnum.ARCHIVED == status) {
            List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
            assertTrue(CollectionUtils.isNotEmpty(auditItems));
            assertEquals(1, auditItems.size());
            assertEquals("Usage was sent to CRM", auditItems.get(0).getActionReason());
        }
    }

    private String formatJson(Object objectToFormat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(objectToFormat.toString(), Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}
