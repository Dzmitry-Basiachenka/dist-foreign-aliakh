package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUdmUsageAuditRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Integration test for {@link UdmUsageAuditRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml")
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmUsageAuditRepositoryIntegrationTest {

    private static final String FOLDER_NAME = "udm-usage-audit-repository-integration-test/";
    private static final String UDM_USAGE_UID = "e6040f00-8564-4482-ab67-9965483a8a9f";
    private static final String UDM_USAGE_AUDIT_UID_1 = "0cc67ffc-e565-46ef-abbc-1da6cf2e47b5";
    private static final String UDM_USAGE_AUDIT_UID_2 = "e7370736-60c0-4283-9948-717d075f152f";

    @Autowired
    private IUdmUsageAuditRepository udmUsageAuditRepository;

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-udm-usage-id.groovy")
    public void testFindByUdmUsageId() {
        List<UsageAuditItem> auditItems = udmUsageAuditRepository.findByUdmUsageId(UDM_USAGE_UID);
        assertEquals(1, auditItems.size());
        UsageAuditItem auditItem = auditItems.get(0);
        assertEquals(UDM_USAGE_AUDIT_UID_2, auditItem.getId());
        assertEquals(UDM_USAGE_UID, auditItem.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals("Uploaded in 'UDM Batch 2021 June' Batch", auditItem.getActionReason());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "find-by-udm-usage-id.groovy")
    public void testInsert() {
        List<UsageAuditItem> auditItems = udmUsageAuditRepository.findByUdmUsageId(UDM_USAGE_UID);
        assertEquals(1, auditItems.size());
        udmUsageAuditRepository.insert(buildUsageAuditItem());
        auditItems = udmUsageAuditRepository.findByUdmUsageId(UDM_USAGE_UID);
        assertEquals(2, auditItems.size());
        UsageAuditItem auditItem1 = auditItems.get(0);
        assertEquals(UDM_USAGE_AUDIT_UID_1, auditItem1.getId());
        assertEquals(UDM_USAGE_UID, auditItem1.getUsageId());
        assertEquals(UsageActionTypeEnum.WORK_FOUND, auditItem1.getActionType());
        assertEquals("Wr Wrk Inst 306985867 was found by standard number 1873-7773", auditItem1.getActionReason());
        UsageAuditItem auditItem2 = auditItems.get(1);
        assertEquals(UDM_USAGE_AUDIT_UID_2, auditItem2.getId());
        assertEquals(UDM_USAGE_UID, auditItem2.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, auditItem2.getActionType());
        assertEquals("Uploaded in 'UDM Batch 2021 June' Batch", auditItem2.getActionReason());
    }

    @Test
    @TestData(fileName = FOLDER_NAME + "delete-by-batch-id.groovy")
    public void testDeleteByBatchId() {
        assertEquals(1,
            CollectionUtils.size(udmUsageAuditRepository.findByUdmUsageId("081dbeb4-ec1d-4519-882d-704acb68d8fa")));
        udmUsageAuditRepository.deleteByBatchId("a42efa41-1531-49b2-b065-a40168082a84");
        assertTrue(
            CollectionUtils.isEmpty(udmUsageAuditRepository.findByUdmUsageId("081dbeb4-ec1d-4519-882d-704acb68d8fa")));
    }

    private UsageAuditItem buildUsageAuditItem() {
        UsageAuditItem auditItem = new UsageAuditItem();
        auditItem.setId(UDM_USAGE_AUDIT_UID_1);
        auditItem.setUsageId(UDM_USAGE_UID);
        auditItem.setActionType(UsageActionTypeEnum.WORK_FOUND);
        auditItem.setActionReason("Wr Wrk Inst 306985867 was found by standard number 1873-7773");
        return auditItem;
    }
}
