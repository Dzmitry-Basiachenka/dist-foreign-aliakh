package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.WorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for {@link WorkflowIntegrationTest}.
 * <p>
 * Copyright (C) 2018 copyright.com
 * <p>
 * Date: 4/11/18
 *
 * @author Ihar Suvorau
 */
@Component
public class WorkflowIntegrationTestBuilder implements Builder<Runner> {

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IScenarioService scenarioService;
    @Autowired
    private IUsageService usageService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Value("$RUP{dist.foreign.integration.rest.prm.rollups.async}")
    private boolean prmRollUpAsync;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    private String usagesCsvFile;
    private UsageBatch usageBatch;
    private UsageFilter usageFilter;
    private int expectedInsertedUsagesCount;
    private String expectedPreferencesJsonFile;
    private String expectedRollupsJsonFile;
    private List<String> expectedRightsholdersIds;

    public WorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile) {
        this.usagesCsvFile = csvFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder withUsageBatch(UsageBatch batch) {
        this.usageBatch = batch;
        return this;
    }

    public WorkflowIntegrationTestBuilder withUsageFilter(UsageFilter filter) {
        this.usageFilter = filter;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectInsertedUsagesCount(int insertedUsagesCount) {
        this.expectedInsertedUsagesCount = insertedUsagesCount;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectPreferences(String jsonFile) {
        this.expectedPreferencesJsonFile = jsonFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectRollups(String jsonFile, String... rightsholdersIds) {
        this.expectedRollupsJsonFile = jsonFile;
        this.expectedRightsholdersIds = Arrays.asList(rightsholdersIds);
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner implements Runnable {

        private Scenario scenario;

        @Override
        public void run() {
            try {
                loadUsageBatch();
                addToScenario();
                scenarioService.submit(scenario, "Submitting scenario for testing purposes");
                scenarioService.approve(scenario, "Approving scenario for testing purposes");
            } catch (Exception e) {
                throw new AssertionError("Exception was thrown", e);
            }
        }

        private void loadUsageBatch() throws IOException {
            UsageCsvProcessor csvProcessor = csvProcessorFactory.getUsageCsvProcessor("CLA_FAS");
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            int usagesInsertedCount = usageBatchService.insertUsageBatch(usageBatch, usages);
            assertEquals(expectedInsertedUsagesCount, usagesInsertedCount);
        }

        private ByteArrayOutputStream getCsvOutputStream() throws IOException {
            String csvText = TestUtils.fileToString(getClass(), usagesCsvFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.write(csvText, out, StandardCharsets.UTF_8);
            return out;
        }

        private void addToScenario() {
            createRestServer();
            expectGetPreferences();
            expectGetRollups();
            scenario = scenarioService.createScenario("Test Scenario", "Test Scenario Description", usageFilter);
            mockServer.verify();
            asyncMockServer.verify();
            List<Usage> usages = usageService.getUsagesByScenarioId(scenario.getId());
            assertEquals(expectedInsertedUsagesCount, usages.size());
        }

        private void createRestServer() {
            mockServer = MockRestServiceServer.createServer(restTemplate);
            asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        }

        private void expectGetPreferences() {
            mockServer.expect(MockRestRequestMatchers
                .requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(
                    TestUtils.fileToString(this.getClass(), expectedPreferencesJsonFile),
                    MediaType.APPLICATION_JSON));
        }

        private void expectGetRollups() {
            (prmRollUpAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
                .requestTo(buildRollupRequestString()))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(
                    TestUtils.fileToString(this.getClass(), expectedRollupsJsonFile),
                    MediaType.APPLICATION_JSON));
        }

        private String buildRollupRequestString() {
            return "http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                StringUtils.join(expectedRightsholdersIds, ",") + "&relationshipCode=PARENT&prefCodes%5B%5D=payee";
        }
    }
}
