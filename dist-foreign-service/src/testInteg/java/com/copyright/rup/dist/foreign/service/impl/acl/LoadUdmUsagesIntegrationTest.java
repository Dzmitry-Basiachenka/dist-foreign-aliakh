package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.caching.api.ICacheService;
import com.copyright.rup.dist.common.domain.BaseEntity;
import com.copyright.rup.dist.common.service.impl.csv.DistCsvProcessor.ProcessingResult;
import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsage;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmBatchService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageAuditService;
import com.copyright.rup.dist.foreign.service.api.acl.IUdmUsageService;
import com.copyright.rup.dist.foreign.service.impl.ServiceTestHelper;
import com.copyright.rup.dist.foreign.service.impl.csv.CsvProcessorFactory;
import com.copyright.rup.dist.foreign.service.impl.csv.UdmCsvProcessor;

import com.google.common.collect.ImmutableMap;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Verifies functionality for loading UDM usages.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 05/04/21
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/service/dist-foreign-service-test-context.xml")
@TestData(fileName = "empty-change-set-data-init.groovy")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class LoadUdmUsagesIntegrationTest {

    private static final String UDM_BATCH_NAME = "UDM Batch 2021 June";
    private static final String LOADED_REASON = "Uploaded in 'UDM Batch 2021 June' Batch";
    private final UdmBatch batch = buildUdmBatch();
    @Autowired
    private CsvProcessorFactory csvProcessorFactory;
    @Autowired
    private IUdmBatchService udmBatchService;
    @Autowired
    private IUdmUsageService udmUsageService;
    @Autowired
    private IUdmUsageRepository udmUsageRepository;
    @Autowired
    private IUdmBatchRepository udmBatchRepository;
    @Autowired
    private IUdmUsageAuditService udmUsageAuditService;
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
        testHelper.expectGetRmsRights("rights/rms_grants_udm_123059057_request.json",
            "rights/rms_grants_udm_123059057_response.json");
        testHelper.expectGetRmsRights("rights/rms_grants_udm_987654321_request.json",
            "rights/rms_grants_empty_response.json");
        testHelper.expectPrmCall("prm/rightsholder_1000024950_response.json", 1000024950L);
        List<String> udmUsageIds = loadUdmBatch();
        verifyUdmBatch();
        verifyUdmUsages(udmUsageIds);
        verifyUdmAudit(udmUsageIds);
        testHelper.verifyRestServer();
    }

    private List<String> loadUdmBatch() throws IOException {
        UdmCsvProcessor csvProcessor = csvProcessorFactory.getUdmCsvProcessor();
        ProcessingResult<UdmUsage> result =
            csvProcessor.process(testHelper.getCsvOutputStream("acl/usage/udm_usages_for_upload.csv"));
        assertTrue(result.isSuccessful());
        List<UdmUsage> usages = result.get();
        udmBatchService.insertUdmBatch(batch, usages);
        udmUsageService.sendForMatching(usages);
        return usages.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    private void verifyUdmBatch() {
        UdmBatch actualBatch = udmBatchRepository.findById(batch.getId());
        assertEquals(batch.getName(), actualBatch.getName());
        assertEquals(batch.getPeriod(), actualBatch.getPeriod());
        assertEquals(batch.getChannel(), actualBatch.getChannel());
        assertEquals(batch.getUsageOrigin(), actualBatch.getUsageOrigin());
    }

    private void verifyUdmUsages(List<String> udmUsageIds) throws IOException {
        List<UdmUsage> expectedUsages = testHelper.loadExpectedUdmUsages("acl/usage/udm_usages_for_upload.json");
        List<UdmUsage> actualUsages = udmUsageRepository.findByIds(udmUsageIds);
        testHelper.assertUdmUsages(expectedUsages, actualUsages);
    }

    private void verifyUdmAudit(List<String> udmUsageIds) {
        List<UdmUsage> actualUsages = udmUsageRepository.findByIds(udmUsageIds);
        Map<String, String> originalDetailIdsToIds = actualUsages
            .stream()
            .collect(Collectors.toMap(UdmUsage::getOriginalDetailId, UdmUsage::getId));
        String udmUsageId1 = originalDetailIdsToIds.get("OGN674GHHSB001");
        assertUdmAudit(udmUsageId1, buildUsageAuditItems(udmUsageId1, ImmutableMap.of(
            UsageActionTypeEnum.INELIGIBLE, "No reported use",
            UsageActionTypeEnum.LOADED, LOADED_REASON)));
        String udmUsageId2 = originalDetailIdsToIds.get("OGN674GHHSB002");
        assertUdmAudit(udmUsageId2, buildUsageAuditItems(udmUsageId2, ImmutableMap.of(
            UsageActionTypeEnum.WORK_NOT_FOUND, "Wr Wrk Inst 122825347 was not found in PI",
            UsageActionTypeEnum.LOADED, LOADED_REASON)));
        String udmUsageId3 = originalDetailIdsToIds.get("OGN674GHHSB003");
        assertUdmAudit(udmUsageId3, buildUsageAuditItems(udmUsageId3, ImmutableMap.of(
            UsageActionTypeEnum.RH_FOUND, "Rightsholder account 1000024950 was found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 123059057 was found by standard number 978-0-271-01750-1",
            UsageActionTypeEnum.LOADED, LOADED_REASON)));
        String udmUsageId4 = originalDetailIdsToIds.get("OGN674GHHSB004");
        assertUdmAudit(udmUsageId4, buildUsageAuditItems(udmUsageId4, ImmutableMap.of(
            UsageActionTypeEnum.RH_NOT_FOUND, "Rightsholder account for 987654321 was not found in RMS",
            UsageActionTypeEnum.WORK_FOUND, "Wr Wrk Inst 987654321 was found in PI",
            UsageActionTypeEnum.LOADED, LOADED_REASON)));
    }

    private List<UsageAuditItem> buildUsageAuditItems(String udmUsageId,
                                                      Map<UsageActionTypeEnum, String> actionTypesToActionReasonsMap) {
        List<UsageAuditItem> usageAuditItems = new ArrayList<>();
        actionTypesToActionReasonsMap.forEach((actionType, actionReason) -> {
            UsageAuditItem usageAuditItem = new UsageAuditItem();
            usageAuditItem.setUsageId(udmUsageId);
            usageAuditItem.setActionType(actionType);
            usageAuditItem.setActionReason(actionReason);
            usageAuditItems.add(usageAuditItem);
        });
        return usageAuditItems;
    }

    // TODO: investigate the order of audit items committed in one transaction
    private void assertUdmAudit(String udmUsageId, List<UsageAuditItem> expectedAuditItems) {
        List<UsageAuditItem> actualAuditItems = udmUsageAuditService.getUdmUsageAudit(udmUsageId);
        assertEquals(CollectionUtils.size(expectedAuditItems), CollectionUtils.size(actualAuditItems));
        expectedAuditItems.forEach(expectedItem -> {
            assertTrue(actualAuditItems.stream().anyMatch(actualItem ->
                expectedItem.getActionReason().equals(actualItem.getActionReason()) &&
                    expectedItem.getActionType() == actualItem.getActionType()));
        });
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }
}
