package com.copyright.rup.dist.foreign.service.impl.rights;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.domain.job.JobInfo;
import com.copyright.rup.dist.common.domain.job.JobStatusEnum;
import com.copyright.rup.dist.common.test.JsonMatcher;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.SalUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IAaclUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.ISalUsageRepository;
import com.copyright.rup.dist.foreign.repository.api.IUsageRepository;
import com.copyright.rup.dist.foreign.service.api.IRightsService;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.service.impl.RightsholderService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class UpdateRightsIntegrationTest {

    private static final String FAS = "FAS";
    private static final String AACL = "AACL";
    private static final String RH_FOUND_REASON = "Rightsholder account 1000000322 was found in RMS";
    private static final String RMS_GRANTS_EMPTY_RESPONSE_JSON = "rms_grants_empty_response.json";

    @Autowired
    private IUsageRepository usageRepository;
    @Autowired
    private IAaclUsageRepository aaclUsageRepository;
    @Autowired
    private ISalUsageRepository salUsageRepository;
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
        expectRmsCallIgnoreDate("rms_grants_854030732_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        expectRmsCallIgnoreDate("rms_grants_122824345_request.json", "rms_grants_122824345_response.json");
        expectPrmCall();
        JobInfo jobInfo = rightsService.updateRightsSentForRaUsages();
        assertEquals(JobStatusEnum.FINISHED, jobInfo.getStatus());
        assertEquals("ProductFamily=FAS, UsagesCount=2; ProductFamily=FAS2, Reason=There are no usages;",
            jobInfo.getResult());
        assertUsage("2de40e13-d353-44ce-b6bb-a11383ba9fb9", UsageStatusEnum.NEW, null);
        assertUsage("e6378e17-b0c9-420f-aa5c-a653156339d2", UsageStatusEnum.SENT_FOR_RA, null);
        assertUsage("11853c83-780a-4533-ad01-dde87c8b8592", UsageStatusEnum.ELIGIBLE, 1000000322L);
        assertUsage("37c4d727-caeb-4a7f-b11a-34e313b0bfcc", UsageStatusEnum.ELIGIBLE, 1000009522L);
        assertUsage("ff321d96-04bd-11e8-ba89-0ed5f89f718b", UsageStatusEnum.LOCKED, 1000009522L);
        assertUsage("19ca7776-48c8-472e-acfe-d49b6e8780ce", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("11853c83-780a-4533-ad01-dde87c8b8592", "Usage has become eligible", RH_FOUND_REASON);
        mockServer.verify();
        asyncMockServer.verify();
    }

    @Test
    public void testUpdateRights() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCallIgnoreDate("rms_grants_254030731_request.json", "rms_grants_254030731_response.json");
        expectRmsCallIgnoreDate("rms_grants_658824345_request.json", "rms_grants_658824345_response.json");
        expectRmsCallIgnoreDate("rms_grants_488824345_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        expectRmsCallIgnoreDate("rms_grants_786768461_request.json", "rms_grants_786768461_response.json");
        rightsService.updateRights(Arrays.asList(
            buildUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", FAS, 254030731L),
            buildUsage("8aded52d-9507-4883-ab4c-fd2e029298af", FAS, 254030731L),
            buildUsage("74ded52a-4454-1225-ab4c-fA2e029298af", FAS, 658824345L),
            buildUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", FAS, 488824345L)), true);
        rightsService.updateRights(
            Collections.singletonList(buildUsage("ede81bc0-a756-43a2-b236-05a0184384f4", "NTS", 786768461L)), false);
        assertUsage("ede81bc0-a756-43a2-b236-05a0184384f4", UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUsage("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("8aded52d-9507-4883-ab4c-fd2e029298af", UsageStatusEnum.RH_FOUND, 1000010077L);
        assertUsage("74ded52a-4454-1225-ab4c-fA2e029298af", UsageStatusEnum.RH_FOUND, 1000023401L);
        assertUsage("3a6b6f25-9f68-4da7-be4f-dd65574f5168", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("b77e72d6-ef71-4f4b-a00b-5800e43e5bee", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("8aded52d-9507-4883-ab4c-fd2e029298af", "Rightsholder account 1000010077 was found in RMS");
        assertAudit("74ded52a-4454-1225-ab4c-fA2e029298af", "Rightsholder account 1000023401 was found in RMS");
        assertAudit("3a6b6f25-9f68-4da7-be4f-dd65574f5168", "Rightsholder account for 488824345 was not found in RMS");
        assertAudit("ede81bc0-a756-43a2-b236-05a0184384f4");
        mockServer.verify();
        asyncMockServer.verify();
    }

    @Test
    public void testUpdateAaclRights() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rms_grants_122803735_request.json", "rms_grants_122803735_response.json");
        expectRmsCall("rms_grants_130297955_request.json", "rms_grants_130297955_response.json");
        expectRmsCall("rms_grants_200208329_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        expectPrmCall();
        rightsService.updateAaclRights(Arrays.asList(
            buildAaclUsage("b23cb103-9242-4d58-a65d-2634b3e5a8cf", 122803735),
            buildAaclUsage("7e7b97d1-ad60-4d47-915b-2834c5cc056a", 130297955),
            buildAaclUsage("10c9a60f-28b6-466c-975c-3ea930089a9e", 200208329)));
        assertAaclUsage("b23cb103-9242-4d58-a65d-2634b3e5a8cf", UsageStatusEnum.RH_FOUND, 1000000322L, "ALL");
        assertAaclUsage("7e7b97d1-ad60-4d47-915b-2834c5cc056a", UsageStatusEnum.RH_FOUND, 1000023401L, "PRINT");
        assertAaclUsage("10c9a60f-28b6-466c-975c-3ea930089a9e", UsageStatusEnum.NEW, null, null);
        assertAudit("b23cb103-9242-4d58-a65d-2634b3e5a8cf", RH_FOUND_REASON);
        assertAudit("7e7b97d1-ad60-4d47-915b-2834c5cc056a", "Rightsholder account 1000023401 was found in RMS");
        assertAudit("10c9a60f-28b6-466c-975c-3ea930089a9e");
        mockServer.verify();
        asyncMockServer.verify();
    }

    @Test
    public void testUpdateSalRights() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        asyncMockServer = MockRestServiceServer.createServer(asyncRestTemplate);
        expectRmsCall("rms_grants_122769471_request.json", "rms_grants_122769471_response.json");
        expectRmsCall("rms_grants_243618757_request.json", "rms_grants_243618757_response.json");
        expectRmsCall("rms_grants_140160102_request.json", RMS_GRANTS_EMPTY_RESPONSE_JSON);
        expectPrmCall();
        rightsService.updateSalRights(Arrays.asList(
            buildSalUsage("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", 122769471),
            buildSalUsage("094749c5-08fa-4f57-8c3b-ecbc334a5c2a", 243618757),
            buildSalUsage("ecf46bea-2baa-40c1-a5e1-769c78865b2c", 140160102)));
        assertSalUsage("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", UsageStatusEnum.RH_FOUND, 1000000322L);
        assertSalUsage("094749c5-08fa-4f57-8c3b-ecbc334a5c2a", UsageStatusEnum.WORK_NOT_GRANTED, null);
        assertSalUsage("ecf46bea-2baa-40c1-a5e1-769c78865b2c", UsageStatusEnum.RH_NOT_FOUND, null);
        assertAudit("dcb53a42-7e8d-4a4a-8d72-6f794be2731c", RH_FOUND_REASON);
        assertAudit("094749c5-08fa-4f57-8c3b-ecbc334a5c2a",
            "Right for 243618757 is denied for rightsholder account 1000000322");
        assertAudit("ecf46bea-2baa-40c1-a5e1-769c78865b2c", "Rightsholder account for 140160102 was not found in RMS");
        mockServer.verify();
        asyncMockServer.verify();
    }

    private Usage buildUsage(String usageId, String productFamily, Long wrWrkInst) {
        Usage usage = new Usage();
        usage.setId(usageId);
        usage.setWrWrkInst(wrWrkInst);
        usage.setProductFamily(productFamily);
        return usage;
    }

    private Usage buildAaclUsage(String usageId, long wrWrkInst) {
        Usage usage = buildUsage(usageId, AACL, wrWrkInst);
        usage.setAaclUsage(new AaclUsage());
        usage.getAaclUsage().setBatchPeriodEndDate(LocalDate.of(2015, 6, 30));
        return usage;
    }

    private Usage buildSalUsage(String usageId, long wrWrkInst) {
        Usage usage = buildUsage(usageId, "SAL", wrWrkInst);
        usage.setSalUsage(new SalUsage());
        usage.getSalUsage().setBatchPeriodEndDate(LocalDate.of(2020, 6, 30));
        return usage;
    }

    private void assertAaclUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber,
                                 String expectedRightLimitation) {
        Usage usage = aaclUsageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
        assertEquals(expectedRightLimitation, usage.getAaclUsage().getRightLimitation());
    }

    private void assertSalUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        Usage usage = salUsageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertUsage(String usageId, UsageStatusEnum expectedStatus, Long expectedRhAccountNumber) {
        Usage usage = usageRepository.findByIds(Collections.singletonList(usageId)).get(0);
        assertEquals(expectedStatus, usage.getStatus());
        assertEquals(expectedRhAccountNumber, usage.getRightsholder().getAccountNumber());
    }

    private void assertAudit(String usageId, String... reasons) {
        List<UsageAuditItem> auditItems = usageAuditService.getUsageAudit(usageId);
        assertEquals(CollectionUtils.size(auditItems), ArrayUtils.getLength(reasons));
        IntStream.range(0, reasons.length)
            .forEach(index -> assertEquals(reasons[index], auditItems.get(index).getActionReason()));
    }

    private void expectRmsCallIgnoreDate(String rmsRequestFileName, String rmsResponseFileName) {
        expectRmsCall(rmsRequestFileName, rmsResponseFileName, "period_end_date");
    }

    private void expectRmsCall(String rmsRequestFileName, String rmsResponseFileName, String... excludeFields) {
        mockServer
            .expect(MockRestRequestMatchers.requestTo("http://localhost:9051/rms-rights-rest/rights/"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andExpect(MockRestRequestMatchers.content()
                .string(new JsonMatcher(TestUtils.fileToString(this.getClass(), rmsRequestFileName),
                    Arrays.asList(excludeFields))))
            .andRespond(
                MockRestResponseCreators.withSuccess(TestUtils.fileToString(this.getClass(), rmsResponseFileName),
                    MediaType.APPLICATION_JSON));
    }

    private void expectPrmCall() {
        (prmRightsholderAsync ? asyncMockServer : mockServer).expect(MockRestRequestMatchers
            .requestTo(
                "http://localhost:8080/party-rest/organization/extorgkeysv2?extOrgKeys=1000000322"))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withSuccess(
                TestUtils.fileToString(RightsholderService.class, "prm/rightsholder_1000000322_response.json"),
                MediaType.APPLICATION_JSON));
    }
}
