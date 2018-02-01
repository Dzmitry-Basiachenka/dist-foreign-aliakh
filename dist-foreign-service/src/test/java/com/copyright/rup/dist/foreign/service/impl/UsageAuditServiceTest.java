package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.impl.UsageAuditRepository;

import com.google.common.collect.ImmutableSet;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UsageAuditService}.
 * <p>
 * Copyright (C) 2017 copyright.com
 * <p>
 * Date: 10/16/2017
 *
 * @author Uladzislau_Shalamitski
 */
public class UsageAuditServiceTest {

    private static final String SCENARIO_UID = "2f9259de-b274-11e7-abc4-cec278b6b50a";
    private static final String USAGE_UID = "4eef3a40-b274-11e7-abc4-cec278b6b50a";
    private static final String USAGE_UID_2 = "5eef3a40-b274-11e7-abc4-cec278b6b50a";
    private static final String BATCH_UID = "f2104232-b274-11e7-abc4-cec278b6b50a";
    private static final String REASON = "Uploaded in 'ABC' Batch";

    private UsageAuditService usageAuditService;
    private UsageAuditRepository usageAuditRepository;

    @Before
    public void setUp() {
        usageAuditService = new UsageAuditService();
        usageAuditRepository = createMock(UsageAuditRepository.class);
        Whitebox.setInternalState(usageAuditService, "usageAuditRepository", usageAuditRepository);
    }

    @Test
    public void logActionWithoutScenario() {
        Capture<UsageAuditItem> usageAuditItemCapture = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(USAGE_UID, UsageActionTypeEnum.LOADED, REASON);
        verifyCapturedAuditItem(usageAuditItemCapture, USAGE_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void logActionWithUsageIds() {
        Capture<UsageAuditItem> usageAuditItemCapture1 = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture1));
        expectLastCall().once();
        Capture<UsageAuditItem> usageAuditItemCapture2 = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture2));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(ImmutableSet.of(USAGE_UID, USAGE_UID_2),
            UsageActionTypeEnum.LOADED, REASON);
        verifyCapturedAuditItem(usageAuditItemCapture1, USAGE_UID);
        verifyCapturedAuditItem(usageAuditItemCapture2, USAGE_UID_2);
        verify(usageAuditRepository);
    }

    @Test
    public void logActionWithScenario() {
        Capture<UsageAuditItem> usageAuditItemCapture = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.logAction(USAGE_UID, buildScenario(), UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
            "Scenario ‘ABC’ was deleted");
        UsageAuditItem usageAuditItem = usageAuditItemCapture.getValue();
        assertEquals("Scenario ‘ABC’ was deleted", usageAuditItem.getActionReason());
        assertEquals(USAGE_UID, usageAuditItem.getUsageId());
        assertEquals(SCENARIO_UID, usageAuditItem.getScenarioId());
        assertEquals("scenario name", usageAuditItem.getScenarioName());
        assertEquals(UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO, usageAuditItem.getActionType());
        verify(usageAuditRepository);
    }

    @Test
    public void testDeleteActions() {
        usageAuditRepository.deleteByBatchId(BATCH_UID);
        expectLastCall().once();
        replay(usageAuditRepository);
        usageAuditService.deleteActions(BATCH_UID);
        verify(usageAuditRepository);
    }

    @Test
    public void testGetUsageAudit() {
        String usageId = RupPersistUtils.generateUuid();
        List<UsageAuditItem> items = Collections.emptyList();
        expect(usageAuditRepository.findByUsageId(usageId)).andReturn(items).once();
        replay(usageAuditRepository);
        assertSame(items, usageAuditService.getUsageAudit(usageId));
        verify(usageAuditRepository);
    }

    private void verifyCapturedAuditItem(Capture<UsageAuditItem> usageAuditItemCapture, String expectedUsageItemId) {
        UsageAuditItem usageAuditItem = usageAuditItemCapture.getValue();
        assertEquals(REASON, usageAuditItem.getActionReason());
        assertEquals(expectedUsageItemId, usageAuditItem.getUsageId());
        assertNull(usageAuditItem.getScenarioId());
        assertNull(usageAuditItem.getScenarioName());
        assertEquals(UsageActionTypeEnum.LOADED, usageAuditItem.getActionType());
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_UID);
        scenario.setName("scenario name");
        return scenario;
    }
}
