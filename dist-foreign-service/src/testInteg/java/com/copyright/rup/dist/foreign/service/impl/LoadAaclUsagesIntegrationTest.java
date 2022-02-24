package com.copyright.rup.dist.foreign.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.TestUtils;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.AaclUsage;
import com.copyright.rup.dist.foreign.domain.PublicationType;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
@TestData(fileName = "load-aacl-usages-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadAaclUsagesIntegrationTest {

    private static final String USAGE_COMMENT_1 = "AACL usage Comment 1";
    private static final String USAGE_COMMENT_2 = "AACL usage Comment 2";
    private static final String USAGE_COMMENT_4 = "AACL usage Comment 4";
    private static final String BASELINE_USAGE_COMMENT_1 = "AACL baseline usage Comment 1";
    private static final String BASELINE_USAGE_COMMENT_2 = "AACL baseline usage Comment 2";
    private static final String BASELINE_USAGE_COMMENT_4 = "AACL baseline usage Comment 4";
    private static final String UPLOADED_REASON = "Uploaded in 'AACL test batch' Batch";
    private static final String BASELINE_UPLOADED_REASON = "Pulled from baseline for 'AACL test batch' Batch";
    private static final String RH_FOUND_REASON = "Rightsholder account 1000024950 was found in RMS";
    private static final String AACL_PRODUCT_FAMILY = "AACL";
    private static final LocalDate PAYMENT_DATE = LocalDate.of(2019, 6, 30);

    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUsageBatchService usageBatchService;
    @Autowired
    private IAaclUsageService aaclUsageService;
    @Autowired
    private ServiceTestHelper testHelper;
    @Autowired
    private List<ICacheService<?, ?>> cacheServices;

    @Before
    public void setUp() {
        cacheServices.forEach(ICacheService::invalidateCache);
    }

    @Test
    public void testLoadUsages() throws Exception {
        testHelper.createRestServer();
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_request_2.json",
            "rights/aacl/rms_grants_response_2.json");
        testHelper.expectGetRmsRights("rights/aacl/rms_grants_123456789_request.json",
            "rights/rms_grants_empty_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000024950_response.json", 1000024950L);
        loadUsageBatch();
        assertUsageBatch();
        assertUsages();
        assertAudit();
        testHelper.verifyRestServer();
    }

    private void loadUsageBatch() throws IOException {
        UsageBatch batch = buildUsageBatch();
        AaclUsageCsvProcessor csvProcessor = csvProcessorFactory.getAaclUsageCsvProcessor();
        ProcessingResult<Usage> result = csvProcessor.process(getCsvOutputStream());
        assertTrue(result.isSuccessful());
        List<Usage> usages = result.get();
        List<String> insertedUsageIds = usageBatchService.insertAaclBatch(batch, usages);
        assertEquals(8, insertedUsageIds.size());
        List<String> orderedIds = aaclUsageService.getUsagesByIds(insertedUsageIds).stream()
            .sorted(Comparator.comparing(Usage::getComment))
            .map(Usage::getId)
            .collect(Collectors.toList());
        aaclUsageService.sendForMatching(orderedIds, batch.getName());
    }

    private UsageBatch buildUsageBatch() {
        UsageBatch batch = new UsageBatch();
        batch.setName("AACL test batch");
        batch.setProductFamily(AACL_PRODUCT_FAMILY);
        batch.setPaymentDate(PAYMENT_DATE);
        batch.setNumberOfBaselineYears(2);
        return batch;
    }

    private ByteArrayOutputStream getCsvOutputStream() throws IOException {
        String csvText = TestUtils.fileToString(this.getClass(), "usage/aacl/aacl_usages_for_upload.csv");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(csvText, out, StandardCharsets.UTF_8);
        return out;
    }

    private void assertUsageBatch() {
        UsageBatch batch = usageBatchService.getUsageBatches("AACL")
            .stream()
            .filter(b ->"AACL test batch".equals(b.getName()))
            .findFirst()
            .orElse(null);
        assertNotNull(batch);
        assertEquals(8, batch.getInitialUsagesCount());
    }

    private void assertUsages() throws IOException {
        // using comments to distinguish usages as ids are generated
        Map<String, UsageDto> actualUsageCommentsToUsages = getUsageCommentsToUsageDtosMap();
        List<UsageDto> expectedUsages = loadExpectedUsages("usage/aacl/aacl_expected_usages_for_upload.json");
        assertEquals(expectedUsages.size(), actualUsageCommentsToUsages.size());
        expectedUsages.forEach(
            expectedUsage -> assertUsage(expectedUsage, actualUsageCommentsToUsages.get(expectedUsage.getComment())));
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
        assertAaclUsage(expectedUsage.getAaclUsage(), actualUsage.getAaclUsage());
    }

    private void assertAaclUsage(AaclUsage expectedAaclUsage, AaclUsage actualAaclUsage) {
        assertEquals(expectedAaclUsage.getInstitution(), actualAaclUsage.getInstitution());
        assertEquals(expectedAaclUsage.getUsageSource(), actualAaclUsage.getUsageSource());
        assertEquals(expectedAaclUsage.getNumberOfPages(), actualAaclUsage.getNumberOfPages());
        assertEquals(expectedAaclUsage.getUsageAge().getPeriod(), actualAaclUsage.getUsageAge().getPeriod());
        assertPublicationType(expectedAaclUsage.getPublicationType(), actualAaclUsage.getPublicationType());
        assertEquals(expectedAaclUsage.getOriginalPublicationType(), actualAaclUsage.getOriginalPublicationType());
        assertEquals(expectedAaclUsage.getPublicationType().getWeight(),
            actualAaclUsage.getPublicationType().getWeight());
        assertEquals(expectedAaclUsage.getDetailLicenseeClass().getId(),
            actualAaclUsage.getDetailLicenseeClass().getId());
        assertEquals(expectedAaclUsage.getRightLimitation(), actualAaclUsage.getRightLimitation());
        assertEquals(expectedAaclUsage.getBaselineId(), actualAaclUsage.getBaselineId());
    }

    private void assertPublicationType(PublicationType expectedPublicationType, PublicationType actualPublicationType) {
        assertEquals(expectedPublicationType.getId(), actualPublicationType.getId());
        assertEquals(expectedPublicationType.getName(), actualPublicationType.getName());
    }

    private void assertAudit() {
        Map<String, String> commentsToIds = getUsageCommentsToUsageDtosMap().entrySet().stream()
            .collect(Collectors.toMap(Entry::getKey, usageDto -> usageDto.getValue().getId()));
        testHelper.assertAudit(commentsToIds.get(BASELINE_USAGE_COMMENT_1), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible",
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI",
            UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)));
        testHelper.assertAudit(commentsToIds.get(BASELINE_USAGE_COMMENT_2), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.ELIGIBLE, "Usage has become eligible",
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI",
            UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)));
        testHelper.assertAudit(commentsToIds.get(BASELINE_USAGE_COMMENT_4), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI",
            UsageActionTypeEnum.LOADED, BASELINE_UPLOADED_REASON)));
        testHelper.assertAudit(commentsToIds.get(USAGE_COMMENT_1), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100009840 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(commentsToIds.get(USAGE_COMMENT_2), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, RH_FOUND_REASON,
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 100010768 was found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
        testHelper.assertAudit(commentsToIds.get(USAGE_COMMENT_4), buildUsageAuditItems(ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 963852741 was not found in PI",
            UsageActionTypeEnum.LOADED, UPLOADED_REASON)));
    }

    private List<UsageAuditItem> buildUsageAuditItems(Map<UsageActionTypeEnum, String> map) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        map.forEach((actionTypeEnum, detail) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setActionType(actionTypeEnum);
            usageAuditItem.setActionReason(detail);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }

    private Map<String, UsageDto> getUsageCommentsToUsageDtosMap() {
        UsageFilter filter = new UsageFilter();
        filter.setProductFamily(AACL_PRODUCT_FAMILY);
        filter.setPaymentDate(PAYMENT_DATE);
        return aaclUsageService.getUsageDtos(filter, null, null).stream()
            .collect(Collectors.toMap(UsageDto::getComment, usageDto -> usageDto));
    }
}
