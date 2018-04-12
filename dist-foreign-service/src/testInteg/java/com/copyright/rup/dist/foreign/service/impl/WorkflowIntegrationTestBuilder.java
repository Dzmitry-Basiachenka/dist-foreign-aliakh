package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.JsonEndpointMatcher;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.PaidUsage;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.repository.impl.UsageArchiveRepository;
import com.copyright.rup.dist.foreign.service.api.IScenarioService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.IUsageService;
import com.copyright.rup.dist.foreign.service.impl.WorkflowIntegrationTestBuilder.Runner;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.mock.PaidUsageConsumerMock;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
    @EndpointInject(context = "df.integration.camelContext", uri = "mock:sf.processor.detail")
    private MockEndpoint mockLmEndPoint;
    @Produce
    private ProducerTemplate producerTemplate;
    @Autowired
    @Qualifier("df.service.paidUsageConsumer")
    private PaidUsageConsumerMock paidUsageConsumer;
    @Autowired
    private UsageArchiveRepository usageArchiveRepository;

    private String usagesCsvFile;
    private String productFamily;
    private UsageBatch usageBatch;
    private UsageFilter usageFilter;
    private int expectedInsertedUsagesCount;
    private String expectedPreferencesJsonFile;
    private String expectedRollupsJsonFile;
    private List<String> expectedRightsholdersIds;
    private String expectedLmDetailsJsonFile;
    private String expectedPaidUsagesJsonFile;
    private List<Long> expectedPaidDetailsIds;
    private String expectedCrmRequestJsonFile;
    private String expectedCrmResponseJsonFile;
    private List<Long> expectedArchivedDetailsIds;

    public WorkflowIntegrationTestBuilder withUsagesCsvFile(String csvFile) {
        this.usagesCsvFile = csvFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder withProductFamily(String productFamilyToUse) {
        this.productFamily = productFamilyToUse;
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

    public WorkflowIntegrationTestBuilder expectLmDetails(String jsonFile) {
        this.expectedLmDetailsJsonFile = jsonFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectPaidUsagesFromLm(String jsonFile) {
        this.expectedPaidUsagesJsonFile = jsonFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectPaidDetailsIds(Long... detailsIds) {
        this.expectedPaidDetailsIds = Arrays.asList(detailsIds);
        return this;
    }

    public WorkflowIntegrationTestBuilder expectCrmReporting(String requestJsonFile, String responseJsonFile) {
        this.expectedCrmRequestJsonFile = requestJsonFile;
        this.expectedCrmResponseJsonFile = responseJsonFile;
        return this;
    }

    public WorkflowIntegrationTestBuilder expectArchivedDetailsIds(Long... detailsIds) {
        this.expectedArchivedDetailsIds = Arrays.asList(detailsIds);
        return this;
    }

    @Override
    public Runner build() {
        return new Runner();
    }

    /**
     * Test runner class.
     */
    public class Runner {

        private MockRestServiceServer mockServer;
        private MockRestServiceServer asyncMockServer;

        private Scenario scenario;

        public void run() throws IOException, InterruptedException {
            loadUsageBatch();
            addToScenario();
            scenarioService.submit(scenario, "Submitting scenario for testing purposes");
            scenarioService.approve(scenario, "Approving scenario for testing purposes");
            sendScenarioToLm();
            receivePaidUsagesFromLm();
            sendUsagesToCrm();
        }

        private void loadUsageBatch() throws IOException {
            UsageCsvProcessor csvProcessor = csvProcessorFactory.getUsageCsvProcessor(productFamily);
            ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
            assertTrue(result.isSuccessful());
            List<Usage> usages = result.get();
            int usagesInsertedCount = usageBatchService.insertUsageBatch(usageBatch, usages);
            assertEquals(expectedInsertedUsagesCount, usagesInsertedCount);
        }

        private ByteArrayOutputStream getCsvOutputStream() throws IOException {
            String csvText = TestUtils.fileToString(this.getClass(), usagesCsvFile);
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
            String responseBody = TestUtils.fileToString(this.getClass(), expectedPreferencesJsonFile);
            mockServer.expect(
                MockRestRequestMatchers.requestTo("http://localhost:8080/party-rest/orgPreference/all?fmt=json"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));
        }

        private void expectGetRollups() {
            String responseBody = TestUtils.fileToString(this.getClass(), expectedRollupsJsonFile);
            (prmRollUpAsync ? asyncMockServer : mockServer).expect(
                MockRestRequestMatchers.requestTo(buildRollupRequestString()))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));
        }

        private String buildRollupRequestString() {
            return "http://localhost:8080/party-rest/orgPreference/orgrelprefrollup?orgIds%5B%5D=" +
                StringUtils.join(expectedRightsholdersIds, ",") + "&relationshipCode=PARENT&prefCodes%5B%5D=payee";
        }

        private void sendScenarioToLm() throws InterruptedException {
            expectSendToLm();
            scenarioService.sendToLm(scenario);
            mockLmEndPoint.assertIsSatisfied();
        }

        private void expectSendToLm() {
            String expectedJson = TestUtils.fileToString(this.getClass(), expectedLmDetailsJsonFile);
            mockLmEndPoint.expectedMessageCount(1);
            mockLmEndPoint.expectedHeaderReceived("source", "FDA");
            mockLmEndPoint.expects(new JsonEndpointMatcher(mockLmEndPoint, Collections.singletonList(expectedJson)));
        }

        private void receivePaidUsagesFromLm() throws InterruptedException {
            expectReceivePaidUsages();
            assertPaidUsages();
        }

        private void expectReceivePaidUsages() throws InterruptedException {
            producerTemplate.setDefaultEndpointUri("direct:topic:sf.processor.detail.paid");
            CountDownLatch latch = new CountDownLatch(1);
            paidUsageConsumer.setLatch(latch);
            String body = TestUtils.fileToString(this.getClass(), expectedPaidUsagesJsonFile);
            producerTemplate.sendBodyAndHeader(body, "source", "FDA");
            assertTrue(latch.await(10, TimeUnit.SECONDS));
        }

        private void assertPaidUsages() {
            int usageCount = findPaidUsagesCountByDetailIdsAndStatus(expectedPaidDetailsIds, UsageStatusEnum.PAID);
            assertEquals(expectedPaidDetailsIds.size(), usageCount);
        }

        private int findPaidUsagesCountByDetailIdsAndStatus(List<Long> detailIds, UsageStatusEnum status) {
            Map<Long, String> detailIdToIdMap = usageArchiveRepository.findDetailIdToIdMap(detailIds);
            List<String> usageIds = new ArrayList<>(detailIdToIdMap.values());
            List<PaidUsage> usages = usageArchiveRepository.findByIdAndStatus(usageIds, status);
            return usages.size();
        }

        private void sendUsagesToCrm() throws IOException {
            createRestServer();
            expectSendToCrm();
            usageService.sendToCrm();
            mockServer.verify();
            assertArchivedUsages();
        }

        private void expectSendToCrm() throws IOException {
            String requestBody = TestUtils.fileToString(this.getClass(), expectedCrmRequestJsonFile);
            String responseBody = TestUtils.fileToString(this.getClass(), expectedCrmResponseJsonFile);
            List<String> excludedFields = Collections.singletonList("licenseCreateDate");
            mockServer.expect(MockRestRequestMatchers.requestTo(
                "http://localhost:9061/legacy-integration-rest/insertCCCRightsDistribution"))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().string(new JsonMatcher(requestBody, excludedFields)))
                .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));
        }

        private void assertArchivedUsages() {
            int usageCount =
                findPaidUsagesCountByDetailIdsAndStatus(expectedArchivedDetailsIds, UsageStatusEnum.ARCHIVED);
            assertEquals(expectedArchivedDetailsIds.size(), usageCount);
        }
    }
}
