package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;

import com.copyright.rup.dist.common.test.liquibase.LiquibaseTestExecutionListener;
import com.copyright.rup.dist.common.test.liquibase.TestData;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.repository.api.IUdmValueAuditRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Integration test for {@link UdmValueAuditRepository}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestExecutionListeners(
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
    listeners = {LiquibaseTestExecutionListener.class}
)
public class UdmValueAuditRepositoryIntegrationTest {

    private static final String UDM_VALUE_UID = "16040f00-8564-4482-ab67-9965483a8a9f";
    private static final String UDM_VALUE_AUDIT_UID_1 = "0cc67ffc-e565-46ef-abbc-1da6cf2e47b5";
    private static final String UDM_VALUE_AUDIT_UID_2 = "e7370736-60c0-4283-9948-717d075f152f";

    @Autowired
    private IUdmValueAuditRepository udmValueAuditRepository;

    @Test
    @TestData(fileName = "udm-value-audit-repository-test-data-init-find-by-udm-value-id.groovy")
    public void testFindByUdmValueId() {
        List<UdmValueAuditItem> auditItems = udmValueAuditRepository.findByUdmValueId(UDM_VALUE_UID);
        assertEquals(1, auditItems.size());
        UdmValueAuditItem auditItem = auditItems.get(0);
        assertEquals(UDM_VALUE_AUDIT_UID_2, auditItem.getId());
        assertEquals(UDM_VALUE_UID, auditItem.getValueId());
        assertEquals(UsageActionTypeEnum.CREATED, auditItem.getActionType());
        assertEquals("UDM Value batch for period '2021' was populated", auditItem.getActionReason());
    }

    @Test
    @TestData(fileName = "udm-value-audit-repository-test-data-init-find-by-udm-value-id.groovy")
    public void testInsert() {
        List<UdmValueAuditItem> auditItems = udmValueAuditRepository.findByUdmValueId(UDM_VALUE_UID);
        assertEquals(1, auditItems.size());
        udmValueAuditRepository.insert(buildUdmValueAuditItem());
        auditItems = udmValueAuditRepository.findByUdmValueId(UDM_VALUE_UID);
        assertEquals(2, auditItems.size());
        UdmValueAuditItem auditItem1 = auditItems.get(0);
        assertEquals(UDM_VALUE_AUDIT_UID_1, auditItem1.getId());
        assertEquals(UDM_VALUE_UID, auditItem1.getValueId());
        assertEquals(UsageActionTypeEnum.WORK_FOUND, auditItem1.getActionType());
        assertEquals("Assignment was changed. Usage was assigned to ‘wjohn@copyright.com’",
            auditItem1.getActionReason());
        UdmValueAuditItem auditItem2 = auditItems.get(1);
        assertEquals(UDM_VALUE_AUDIT_UID_2, auditItem2.getId());
        assertEquals(UDM_VALUE_UID, auditItem2.getValueId());
        assertEquals(UsageActionTypeEnum.CREATED, auditItem2.getActionType());
        assertEquals("UDM Value batch for period '2021' was populated", auditItem2.getActionReason());
    }

    private UdmValueAuditItem buildUdmValueAuditItem() {
        UdmValueAuditItem auditItem = new UdmValueAuditItem();
        auditItem.setId(UDM_VALUE_AUDIT_UID_1);
        auditItem.setValueId(UDM_VALUE_UID);
        auditItem.setActionType(UsageActionTypeEnum.WORK_FOUND);
        auditItem.setActionReason("Assignment was changed. Usage was assigned to ‘wjohn@copyright.com’");
        return auditItem;
    }
}
