package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.Usage;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageBatch;
import com.copyright.rup.dist.foreign.domain.UsageDto;
import com.copyright.rup.dist.foreign.domain.filter.UsageFilter;
import com.copyright.rup.dist.foreign.service.api.IUsageBatchService;
import com.copyright.rup.dist.foreign.service.api.aacl.IAaclUsageService;
import com.copyright.rup.dist.foreign.service.impl.csv.AaclUsageCsvProcessor;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Verifies functionality for loading AACL usages.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 12/27/19
 *
 * @author Ihar Suvorau
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LoadAaclUsagesIntegrationTest {

    private static final String USAGE_ID_1 = "3e9566e6-5cb9-4013-88ef-d4f8b66bda5d";
    private static final String USAGE_ID_2 = "599c5313-ed3b-4874-87e9-972425eb734f";
    private static final String USAGE_ID_3 = "78272304-1a43-48f7-a83b-530a90ed27e6";
    private static final String USAGE_ID_4 = "f634ec19-8ed1-4cc8-88bc-54461c62e670";
    private static final List<String> IDS = Arrays.asList(USAGE_ID_1, USAGE_ID_2, USAGE_ID_3, USAGE_ID_4);
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IAaclUsageService usageService;
    @Autowired
    private ServiceTestHelper testHelper;

    @Test
    public void testLoadUsages() throws Exception {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_100009840_request.json",
            "rights/aacl/rms_grants_100009840_response.json");
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_100010768_request.json",
            "rights/aacl/rms_grants_100010768_response.json");
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_123456789_request.json",
            "rights/rms_grants_empty_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000024950_response.json", 1000024950L);
        loadUsageBatch();
        assertUsages();
        testHelper.assertAudit(USAGE_ID_1, buildUsageAuditItems(USAGE_ID_1, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_2, buildUsageAuditItems(USAGE_ID_2, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(USAGE_ID_4, buildUsageAuditItems(USAGE_ID_4, ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.verifyRestServer();
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        setPredefinedUsageIds(usages);
        int usagesInsertedCount = usageBatchService.insertAaclBatch(batch, usages);
        assertEquals(4, usagesInsertedCount);
        usageBatchService.sendForMatching(usages);
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("AACL test batch");
        batch.setProductFamily(AACL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        return batch;
    }

    // predefined usage ids are used, otherwise during every test run the usage ids will be random
    private void setPredefinedUsageIds(List<Usage> usages) {
        AtomicInteger usageId = new AtomicInteger(0);
        usages.forEach(usage -> usage.setId(IDS.get(usageId.getAndIncrement())));
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/aacl/aacl_usages_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void assertUsages() throws IOException {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(AACL_PRODUCT_FAMILY);
        filter.setPaymentDate(PAYMENT_DATE);
        Map<String, UsageDto> actualUsageIdsToUsages = usageService.getUsageDtos(filter, null, null).stream()
            .collect(Collectors.toMap(UsageDto::getId, usageDto -> usageDto));
        List<UsageDto> expectedUsages = loadExpectedUsages("usage/aacl/aacl_expected_usages_for_upload.json");
        assertEquals(expectedUsages.size(), actualUsageIdsToUsages.size());
        expectedUsages.forEach(
            expectedUsage -> assertUsage(expectedUsage, actualUsageIdsToUsages.get(expectedUsage.getId())));
    }

    private List<UsageDto> loadExpectedUsages(String fileName) throws IOException {
        String content = TestUtils.fileToString(this.getClass(), fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        return mapper.readValue(content, new TypeReference<List<UsageDto>>() {
        });
    }

    private void assertUsage(UsageDto expectedUsage, UsageDto actualUsage) {
        assertNotNull(actualUsage);
        assertEquals(expectedUsage.getStatus(), actualUsage.getStatus());
        assertEquals(expectedUsage.getWrWrkInst(), actualUsage.getWrWrkInst());
        assertEquals(expectedUsage.getRhAccountNumber(), actualUsage.getRhAccountNumber());
        assertEquals(expectedUsage.getProductFamily(), actualUsage.getProductFamily());
        assertEquals(expectedUsage.getWorkTitle(), actualUsage.getWorkTitle());
        assertEquals(expectedUsage.getSystemTitle(), actualUsage.getSystemTitle());
        assertEquals(expectedUsage.getStandardNumber(), actualUsage.getStandardNumber());
        assertEquals(expectedUsage.getStandardNumberType(), actualUsage.getStandardNumberType());
        assertEquals(expectedUsage.getNumberOfCopies(), actualUsage.getNumberOfCopies());
        assertEquals(expectedUsage.getComment(), actualUsage.getComment());
        AaclUsage expectedAaclUsage = expectedUsage.getAaclUsage();
        AaclUsage actualAaclUsage = actualUsage.getAaclUsage();
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsagePeriod(), actualAaclUsage.getUsagePeriod());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
    }

    private List<UsageAuditItem> buildUsageAuditItems(String usageId, Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setUsageId(usageId);
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }
}
