package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmBatch;
import com.copyright.rup.dist.foreign.domain.UdmChannelEnum;
import com.copyright.rup.dist.foreign.domain.UdmUsageOriginEnum;
import com.copyright.rup.dist.foreign.domain.UsageStatusEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmBatchRepository;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageRepository;

import com.google.common.collect.Sets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmBatchRepository}.
 * <p/>
 * Copyright (C) 2021 copyright.com
 * <p/>
 * Date: 04/28/2021
 *
 * @author Uladzislau Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmBatchRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "udm-batch-repository-integration-test/";
    private static final String UDM_BATCH_UID = "4b5751aa-6258-44c6-b839-a1ec0edfcf4d";
    private static final String UDM_BATCH_UID_2 = "7649518a-33a5-4929-8956-0c4ed0714250";
    private static final String UDM_BATCH_UID_3 = "e133e00c-d8bb-4be2-ae54-a82c9306a224";
    private static final String UDM_BATCH_UID_4 = "5de7aee8-17ca-47b3-a880-ae4e36463e1f";
    private static final String UDM_BATCH_NAME = "UDM Batch 2021 June";

    @Autowired
    private IUdmBatchRepository udmBatchRepository;
    @Autowired
    private IUdmUsageRepository udmUsageRepository;

    @Test
    @TestData(fileName = "rollback-only.groovy")
    public void testInsertUsageBatch() {
        udmBatchRepository.insert(buildUdmBatch());
        UdmBatch udmBatch = udmBatchRepository.findById(UDM_BATCH_UID);
        assertNotNull(udmBatch);
        assertEquals(UDM_BATCH_UID, udmBatch.getId());
        assertEquals(UDM_BATCH_NAME, udmBatch.getName());
        assertEquals(Integer.valueOf(202006), udmBatch.getPeriod());
        assertEquals(UdmChannelEnum.CCC, udmBatch.getChannel());
        assertEquals(UdmUsageOriginEnum.SS, udmBatch.getUsageOrigin());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testFindAll() {
        List<UdmBatch> udmBatches = udmBatchRepository.findAll();
        assertFalse(udmBatches.isEmpty());
        assertEquals(4, udmBatches.size());
        assertEquals("c57dbf33-b0b9-4493-bcff-c30fd07ee0e4", udmBatches.get(0).getId());
        assertEquals("faaab569-35c1-474e-923d-96f4c062a62a", udmBatches.get(1).getId());
        assertEquals("6a4b192c-8f1b-4887-a75d-67688544eb5f", udmBatches.get(2).getId());
        assertEquals("864911e5-34ac-42a5-a4c8-84dc4c24e7b4", udmBatches.get(3).getId());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-all.groovy")
    public void testBatchExists() {
        assertTrue(udmBatchRepository.udmBatchExists("UDM Batch 2021 1"));
        assertFalse(udmBatchRepository.udmBatchExists("No name in database"));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-udm-batch-processing-completed.groovy")
    public void testIsUdmBatchProcessingCompleted() {
        assertFalse(udmBatchRepository.isUdmBatchProcessingCompleted(UDM_BATCH_UID_2,
            Sets.newHashSet(UsageStatusEnum.NEW, UsageStatusEnum.WORK_FOUND)));
        assertTrue(udmBatchRepository.isUdmBatchProcessingCompleted(UDM_BATCH_UID_2,
            Collections.singleton(UsageStatusEnum.RH_FOUND)));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "is-udm-batch-contains-baseline-usages.groovy")
    public void testIsUdmBatchContainsBaselineUsages() {
        assertFalse(udmBatchRepository.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID_3));
        assertTrue(udmBatchRepository.isUdmBatchContainsBaselineUsages(UDM_BATCH_UID_4));
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-id.groovy")
    public void testDeleteById() {
        String batchId = UDM_BATCH_UID_2;
        assertEquals(1, udmBatchRepository.findAll().size());
        udmUsageRepository.deleteByBatchId(batchId);
        udmBatchRepository.deleteById(batchId);
        assertEquals(0, udmBatchRepository.findAll().size());
    }

    private UdmBatch buildUdmBatch() {
        UdmBatch udmBatch = new UdmBatch();
        udmBatch.setId(UDM_BATCH_UID);
        udmBatch.setName(UDM_BATCH_NAME);
        udmBatch.setPeriod(202006);
        udmBatch.setChannel(UdmChannelEnum.CCC);
        udmBatch.setUsageOrigin(UdmUsageOriginEnum.SS);
        return udmBatch;
    }
}
