package com.copyright.rup.dist.foreign.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IUsageAuditRepository;

import org.apache.commons.collections.CollectionUtils;
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
        UsageAuditItem usageAuditItem = buildUsageAuditItem(USAGE_UID);
        usageAuditRepository.insert(usageAuditItem);
        usageAuditRepository.insert(buildUsageAuditItem("8a06905f-37ae-4e1f-8550-245277f8165c"));
        List<UsageAuditItem> usageAuditItems = usageAuditRepository.findByUsageId(USAGE_UID);
        assertTrue(CollectionUtils.isNotEmpty(usageAuditItems));
        assertEquals(1, CollectionUtils.size(usageAuditItems));
        assertEquals(usageAuditItem, usageAuditItems.get(0));
    }

    @Test
    public void testDeleteByBatchId() {
        usageAuditRepository.insert(buildUsageAuditItem(USAGE_UID));
        assertEquals(1, CollectionUtils.size(usageAuditRepository.findByUsageId(USAGE_UID)));
        usageAuditRepository.deleteByBatchId("56282dbc-2468-48d4-b926-93d3458a656a");
        assertTrue(CollectionUtils.isEmpty(usageAuditRepository.findByUsageId(USAGE_UID)));
    }

    private UsageAuditItem buildUsageAuditItem(String usageId) {
        UsageAuditItem usageAuditItem = new UsageAuditItem();
        usageAuditItem.setId(RupPersistUtils.generateUuid());
        usageAuditItem.setUsageId(usageId);
        usageAuditItem.setActionType(UsageActionTypeEnum.LOADED);
        usageAuditItem.setActionReason("Uploaded in ‘CADRA_11Dec16’ Batch");
        return usageAuditItem;
    }
}
