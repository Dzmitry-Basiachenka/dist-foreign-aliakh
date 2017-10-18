package com.copyright.rup.dist.foreign.service.impl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.copyright.rup.dist.foreign.domain.Scenario;
import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.impl.UsageAuditRepository;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
    private static final String BATCH_UID = "f2104232-b274-11e7-abc4-cec278b6b50a";

    private UsageAuditService usageActionService;
    private UsageAuditRepository usageAuditRepository;

    @Before
    public void setUp() {
        usageActionService = new UsageAuditService();
        usageAuditRepository = createMock(UsageAuditRepository.class);
        Whitebox.setInternalState(usageActionService, "usageAuditRepository", usageAuditRepository);
    }

    @Test
    public void logActionWithoutScenario() {
        Capture<UsageAuditItem> usageAuditItemCapture = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall();
        replay(usageAuditRepository);
        usageActionService.logAction(USAGE_UID, UsageActionTypeEnum.LOADED, "Uploaded in 'ABC' Batch");
        UsageAuditItem usageAuditItem = usageAuditItemCapture.getValue();
        assertEquals("Uploaded in 'ABC' Batch", usageAuditItem.getActionReason());
        assertEquals(USAGE_UID, usageAuditItem.getUsageId());
        assertNull(usageAuditItem.getScenarioId());
        assertNull(usageAuditItem.getScenarioName());
        assertEquals(UsageActionTypeEnum.LOADED, usageAuditItem.getActionType());
        verify(usageAuditRepository);
    }

    @Test
    public void logActionWithScenario() {
        Capture<UsageAuditItem> usageAuditItemCapture = new Capture<>();
        usageAuditRepository.insert(capture(usageAuditItemCapture));
        expectLastCall();
        replay(usageAuditRepository);
        usageActionService.logAction(USAGE_UID, buildScenario(), UsageActionTypeEnum.EXCLUDED_FROM_SCENARIO,
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
        expectLastCall();
        replay(usageAuditRepository);
        usageActionService.deleteActions(BATCH_UID);
        verify(usageAuditRepository);
    }

    private Scenario buildScenario() {
        Scenario scenario = new Scenario();
        scenario.setId(SCENARIO_UID);
        scenario.setName("scenario name");
        return scenario;
    }
}
