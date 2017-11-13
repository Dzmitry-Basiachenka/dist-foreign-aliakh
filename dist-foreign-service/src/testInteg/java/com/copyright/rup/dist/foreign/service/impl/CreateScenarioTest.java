package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.ScenarioStatusEnum;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    // c3719d14-c4a2-4809-911a-915e3e1a7e91, ae37e5a6-88dd-48ae-bd5a-4a12134236ac, ee8ebb66-3584-4aaa-9c44-93e384428efb,
    // 15a4527f-f28e-4ab9-aed7-f5caf3ce5569, 9f2663b9-6006-46cf-a741-cb774ce69754
    @Test
    public void testCreateScenario() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("31ddaa1a-e60b-44ce-a968-0ca262870358"));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "preferences_response.json"), MediaType.APPLICATION_JSON));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f,00d4ae90-5fe7-47bf-ace1-781c8d76d4da," +
                "038bf4aa-b6cc-430a-9b32-655954d95278,756299b5-02ce-4f76-b0bc-ee2571cf906e," +
                "019acfde-91be-43aa-8871-6305642bcb2c&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "rollups_response.json"), MediaType.APPLICATION_JSON));
        String scenarioId = scenarioService.createScenario("Test Scenario", "Scenario Description", filter);
        List<Scenario> scenarios = scenarioService.getScenarios();
        assertEquals(2, scenarios.size());
        assertScenario(scenarioId, scenarios.stream()
            .filter(scenario -> scenario.getId().equals(scenarioId))
            .map(scenario -> scenarioService.getScenarioWithAmounts(scenarioId))
            .collect(Collectors.toList())
            .get(0));
        assertUsage(scenarioId, 7000429266L, 2000017000L, 3667.6928, 7793.8472);
        assertUsage(scenarioId, 1000002859L, 1000002859L, 992.00, 2108.00);
        assertUsage(scenarioId, 1000001820L, 1000001820L, 1648.00, 8652.00);
        assertUsage(scenarioId, 1000024497L, 1000024497L, 160.00, 840.00);
        assertUsage(scenarioId, 1000002562L, 2000017000L, 784.176, 1666.374);
    }

    // Test Case ID: e9a0e8f2-2ef1-4608-8221-1beb116a0748
    @Test
    public void testCreateScenarioNoRollupsNoPreferences() {
        UsageFilter filter = new UsageFilter();
        filter.setUsageBatchesIds(Collections.singleton("31ddaa1a-e60b-44ce-a968-0ca262870358"));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "not_found_response.json"), MediaType.APPLICATION_JSON));
        mockServer.expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                "a5989f7c-fc6f-4e8c-88d4-2fe7bcce8d1f,00d4ae90-5fe7-47bf-ace1-781c8d76d4da," +
                "038bf4aa-b6cc-430a-9b32-655954d95278,756299b5-02ce-4f76-b0bc-ee2571cf906e," +
                "019acfde-91be-43aa-8871-6305642bcb2c&relationshipCode=PARENT&prefCodes%5B%5D=payee"))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(this.getClass(), "not_found_response.json"), MediaType.APPLICATION_JSON));
        String scenarioId = scenarioService.createScenario("Test Scenario", "Scenario Description", filter);
        assertUsage(scenarioId, 7000429266L, 7000429266L, 3667.6928, 7793.8472);
        assertUsage(scenarioId, 1000002859L, 1000002859L, 992.00, 2108.00);
        assertUsage(scenarioId, 1000001820L, 1000001820L, 3296.00, 7004.00);
        assertUsage(scenarioId, 1000024497L, 1000024497L, 320.00, 680.00);
        assertUsage(scenarioId, 1000002562L, 1000002562L, 784.176, 1666.374);
    }

    private void assertScenario(String scenarioId, Scenario scenario) {
        assertNotNull(scenario);
        assertEquals(scenarioId, scenario.getId());
        assertEquals("Test Scenario", scenario.getName());
        assertEquals(new BigDecimal("21060.2212000000"), scenario.getNetTotal());
        assertEquals(new BigDecimal("28312.0900000000"), scenario.getGrossTotal());
        assertEquals(new BigDecimal("7251.8688000000"), scenario.getServiceFeeTotal());
        assertEquals(new BigDecimal("38520.00"), scenario.getReportedTotal());
        assertEquals(ScenarioStatusEnum.IN_PROGRESS, scenario.getStatus());
        assertEquals("Scenario Description", scenario.getDescription());
        assertEquals("SYSTEM", scenario.getCreateUser());
        assertEquals("SYSTEM", scenario.getUpdateUser());
        assertEquals(1, scenario.getVersion());
        assertNotNull(scenario.getCreateDate());
        assertNotNull(scenario.getUpdateDate());
    }

    private void assertUsage(String scenarioId, Long rhAccountNumber, Long payeeAccountNumber, Double serviceFeeAmount,
                             Double netAmount) {
        List<UsageDto> usages = usageRepository.getByScenarioIdAndRhAccountNumber(rhAccountNumber, scenarioId, null,
            new Pageable(0, 10), null);
        assertEquals(1, usages.size());
        UsageDto usageDto = usages.get(0);
        assertEquals(payeeAccountNumber, usageDto.getPayeeAccountNumber(), 0);
        assertEquals(UsageStatusEnum.LOCKED, usageDto.getStatus());
        assertEquals("SYSTEM", usageDto.getUpdateUser());
        assertEquals(BigDecimal.valueOf(serviceFeeAmount).setScale(10), usageDto.getServiceFeeAmount());
        assertEquals(BigDecimal.valueOf(netAmount).setScale(10), usageDto.getNetAmount());
    }
}
