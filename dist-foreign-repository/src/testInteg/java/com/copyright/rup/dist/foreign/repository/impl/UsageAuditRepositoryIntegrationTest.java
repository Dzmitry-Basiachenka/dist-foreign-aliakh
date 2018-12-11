package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Integration test for {@link UsageAuditRepository}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    value = {"classpath:/com/copyright/rup/dist/foreign/repository/dist-foreign-repository-test-context.xml"})
@TestPropertySource(properties = {"test.liquibase.changelog=usage-audit-repository-test-data-init.groovy"})
@TransactionConfiguration
@Transactional
public class UsageAuditRepositoryIntegrationTest {

    private static final String USAGE_UID = "3ab5e80b-89c0-4d78-9675-54c7ab284450";

    @Autowired
    private IUsageAuditRepository usageAuditRepository;

    @Test
    public void testInsertAndFind() {
        List<UsageAuditItem> usageAuditItems = usageAuditRepository.findByUsageId(USAGE_UID);
        assertEquals(1, CollectionUtils.size(usageAuditItems));
        usageAuditRepository.insert(buildUsageAuditItem());
        usageAuditItems = usageAuditRepository.findByUsageId(USAGE_UID);
        assertEquals(2, CollectionUtils.size(usageAuditItems));
        UsageAuditItem auditItem = usageAuditItems.get(0);
        assertEquals(UsageActionTypeEnum.WORK_NOT_FOUND, auditItem.getActionType());
        assertEquals(USAGE_UID, auditItem.getUsageId());
        assertEquals("Usage has no standard number and title", auditItem.getActionReason());
        auditItem = usageAuditItems.get(1);
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals("Uploaded in 'Test Batch 1' Batch", auditItem.getActionReason());
        assertEquals(USAGE_UID, auditItem.getUsageId());
    }

    @Test
    public void testDeleteByBatchId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID)));
        usageAuditRepository.deleteByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID)));
    }

    @Test
    public void testDeleteByUsageId() {
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID)));
        usageAuditRepository.deleteByUsageId(USAGE_UID);
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID)));
    }

    private UsageAuditItem buildUsageAuditItem() {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setId(RupPersistUtils.generateUuid());
        usageAuditItem.setUsageId(USAGE_UID);
        usageAuditItem.setActionType(UsageActionTypeEnum.WORK_NOT_FOUND);
        usageAuditItem.setActionReason("Usage has no standard number and title");
        return usageAuditItem;
    }
}
