package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.UsageFilter;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.Pageable;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Verifies correctness of creating a scenario.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/25/17
 *
 * @author Aliaksandr Radkevich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CreateScenarioTest {

    @Autowired
    private IScenarioService scenarioService;

    @Autowired
    private IUsageRepository usageRepository;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    // Test Case IDs: a5c08113-a8f3-434d-9fa9-1e6026e5e2b4, 5bb2c3eb-036e-452d-982f-62761ca6b1ae,
    // 7c7ea548-6845-478a-b39f-62a62feeddae, ab35246e-983d-455c-8bf1-2fd39fbdd527, 3d745a35-9256-4aae-8275-c4500d70bde8,
    // c3719d14-c4a2-4809-911a-915e3e1a7e91, ae37e5a6-88dd-48ae-bd5a-4a12134236ac
    @Test
    public void testCreateScenario() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("31ddaa1a-e60b-44ce-a968-0ca262870358"));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f,00d4ae90-5fe7-47bf-ace1-781c8d76d4da," +
                "038bf4aa-b6cc-430a-9b32-655954d95278,756299b5-02ce-4f76-b0bc-ee2571cf906e," +
                "019acfde-91be-43aa-8871-6305642bcb2c&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "rollups_response.json"), MediaType.APPLICATION_JSON));
        String scenarioId = scenarioService.createScenario("Test Scenario", "Scenario Description", filter);
        assertUsage(scenarioId, 7000429266L, 2000017000L);
        assertUsage(scenarioId, 1000002859L, 1000002859L);
        assertUsage(scenarioId, 1000001820L, 1000001820L);
        assertUsage(scenarioId, 1000024497L, 1000024497L);
        assertUsage(scenarioId, 1000002562L, 2000017000L);
    }

    // Test Case ID: e9a0e8f2-2ef1-4608-8221-1beb116a0748
    @Test
    public void testCreateScenarioNoRollups() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("31ddaa1a-e60b-44ce-a968-0ca262870358"));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f,00d4ae90-5fe7-47bf-ace1-781c8d76d4da," +
                "038bf4aa-b6cc-430a-9b32-655954d95278,756299b5-02ce-4f76-b0bc-ee2571cf906e," +
                "019acfde-91be-43aa-8871-6305642bcb2c&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "rollups_not_found.json"), MediaType.APPLICATION_JSON));
        String scenarioId = scenarioService.createScenario("Test Scenario", "Scenario Description", filter);
        assertUsage(scenarioId, 7000429266L, 7000429266L);
        assertUsage(scenarioId, 1000002859L, 1000002859L);
        assertUsage(scenarioId, 1000001820L, 1000001820L);
        assertUsage(scenarioId, 1000024497L, 1000024497L);
        assertUsage(scenarioId, 1000002562L, 1000002562L);
    }

    private void assertUsage(String scenarioId, Long rhAccountNumber, Long payeeAccountNumber) {
        List<UsageDto> usages = usageRepository.getByScenarioIdAndRhAccountNumber(rhAccountNumber, scenarioId, null,
            new Pageable(0, 10), null);
        assertEquals(1, usages.size());
        UsageDto usageDto = usages.get(0);
        assertEquals(payeeAccountNumber, usageDto.getPayeeAccountNumber(), 0);
        assertEquals(UsageStatusEnum.LOCKED, usageDto.getStatus());
        assertEquals("SYSTEM", usageDto.getUpdateUser());
    }
}
