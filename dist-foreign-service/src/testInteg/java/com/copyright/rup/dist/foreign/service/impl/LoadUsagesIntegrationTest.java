package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.domain.Rightsholder;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UsageCsvProcessor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
public class LoadUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "0f263081-1a5d-4c76-a1ff-d9a6e1e0b694";
    private static final String USAGE_ID_2 = "5ae4880e-0955-4518-8681-2aeeda667474";
    private static final String USAGE_ID_3 = "afef95d3-d525-49ec-91fe-79fdced6830f";
    private static final String USAGE_ID_4 = "ddc1672e-ef63-4965-a6bc-1d299272c953";
    private static final List<String> IDS = Arrays.asList(USAGE_ID_1, USAGE_ID_2,  USAGE_ID_3, USAGE_ID_4);
    private static final String TITLE =
        "Journal of human lactation : official journal of International Lactation Consultant Association (1985- )";
    private static final String UPLOADED_REASON = "Uploaded in 'Test_Batch' Batch";

    @Value("$RUP{dist.foreign.rest.prm.rightsholder.async}")
    private boolean prmRightsholderAsync;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IUsageAuditService usageAuditService;

    private MockRestServiceServer mockServer;
    private MockRestServiceServer asyncMockServer;

    @Test
    public void testLoadUsages() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rights/rms_grants_123059057_request.json", "rights/rms_grants_empty_response.json");
        expectRmsCall("rights/rms_grants_100011725_request.json", "rights/rms_grants_100011725_response.json");
        expectPrmCall(1000024950L, "prm/rightsholder_1000024950_response.json");
        loadUsageBatch();
        assertUsage(USAGE_ID_1, UsageStatusEnum.NTS_WITHDRAWN, null, null, TITLE, "12345XX-79068", null);
        assertAudit(USAGE_ID_1,
            "Detail was made eligible for NTS because sum of gross amounts, grouped by standard number, " +
                "is less than $100",
            UPLOADED_REASON);
        assertUsage(USAGE_ID_2, UsageStatusEnum.WORK_NOT_FOUND, null, null,
            "Reclaiming youth at risk : our hope for the future", "12345XX-123117", null);
        assertAudit(USAGE_ID_2,
            "Wr Wrk Inst was not found by standard number 12345XX-123117",
            UPLOADED_REASON);
        assertUsage(USAGE_ID_3, UsageStatusEnum.RH_NOT_FOUND, 123059057L, null,
            "True directions : living your sacred instructions", null, "VALISBN10");
        assertAudit(USAGE_ID_3,
            "Rightsholder account for 123059057 was not found in RMS",
            "Wr Wrk Inst 123059057 was found by title \"True directions : living your sacred instructions\"",
            UPLOADED_REASON);
        assertUsage(USAGE_ID_4, UsageStatusEnum.ELIGIBLE, 100011725L, 1000024950L, TITLE, "12345XX-79069", "VALISBN10");
        assertAudit(USAGE_ID_4,
            "Usage has become eligible",
            "Rightsholder account 1000024950 was found in RMS",
            "Usage was uploaded with Wr Wrk Inst",
            UPLOADED_REASON);
        mockServer.verify();
        asyncMockServer.verify();
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        UsageCsvProcessor csvProcessor = csvProcessorFactory.getUsageCsvProcessor("FAS2");
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        setPredefinedUsageIds(usages);
        int usagesInsertedCount = usageBatchService.insertFasBatch(batch, usages);
        assertEquals(4, usagesInsertedCount);
        usageBatchService.sendForMatching(usages);
        usageBatchService.sendForGettingRights(usages, batch.getName());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("Test_Batch");
        batch.setRro(buildRro());
        batch.setProductFamily("FAS2");
        batch.setPaymentDate(LocalDate.now());
        batch.setFiscalYear(2018);
        batch.setGrossAmount(BigDecimal.valueOf(550));
        return batch;
    }

    private Rightsholder buildRro() {
        Rightsholder rro = new Rightsholder();
        rro.setId("77b111d3-9eea-49af-b815-100b9716c1b3");
        rro.setAccountNumber(2000017000L);
        rro.setName("CLA, The Copyright Licensing Agency Ltd.");
        return rro;
    }

    // predefined usage ids are used, otherwise during every test run the usage ids will be random
    private void setPredefinedUsageIds(List<Usage> usages) {
        AtomicInteger usageId = new AtomicInteger(0);
        usages.forEach(usage -> usage.setId(IDS.get(usageId.getAndIncrement())));
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/usages_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void assertUsage(String usageId, UsageStatusEnum status, Long wrWrkInst, Long rhAccountNumber,
                             String systemTitle, String standardNumber, String standardNumberType) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(status, usage.getStatus());
        assertEquals(wrWrkInst, usage.getWrWrkInst());
        assertEquals(rhAccountNumber, usage.getRightsholder().getAccountNumber());
        assertEquals(systemTitle, usage.getSystemTitle());
        assertEquals(standardNumber, usage.getStandardNumber());
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
                    Collections.singletonList("period_end_date"))))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponseFileName),
                    MediaType.APPLICATION_JSON));
    }

    private void expectPrmCall(Long accountNumber, String prmResponseFileName) {
        (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo("http://localhost:8080/party-rest/organization/extorgkeysv2?extOrgKeys=" + accountNumber))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(RightsholderService.class, prmResponseFileName), MediaType.APPLICATION_JSON));
    }
}
