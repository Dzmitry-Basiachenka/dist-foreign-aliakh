package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertSame;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.common.persist.RupPersistUtils;
import com.copyright.rup.dist.common.service.impl.util.RupContextUtils;
import com.copyright.rup.dist.foreign.domain.ScenarioActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.ScenarioAuditItem;
import com.copyright.rup.dist.foreign.repository.api.IAclScenarioAuditRepository;
import com.copyright.rup.dist.foreign.service.api.acl.IAclScenarioAuditService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link AclScenarioAuditService}.
 * <p>
 * Copyright (C) 2022 copyright.com
 * <p>
 * Date: 06/23/2022
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RupContextUtils.class, RupPersistUtils.class})
public class AclScenarioAuditServiceTest {

    private static final String USER_NAME = "user@copyright.com";
    private static final String SCENARIO_UID = "47673eae-1c42-40c3-8c4a-1ba3fe66df96";
    private static final String AUDIT_ITEM_UID = "9523c915-f3d4-4185-b2ca-83c881a45c9e";
    private static final ScenarioActionTypeEnum SCENARIO_ACTION_TYPE = ScenarioActionTypeEnum.SUBMITTED;
    private static final String SCENARIO_ACTION_REASON = "Submitted";

    private IAclScenarioAuditService aclScenarioAuditService;
    private IAclScenarioAuditRepository aclScenarioAuditRepository;

    @Before
    public void setUp() {
        aclScenarioAuditService = new AclScenarioAuditService();
        aclScenarioAuditRepository = createMock(IAclScenarioAuditRepository.class);
        Whitebox.setInternalState(aclScenarioAuditService, aclScenarioAuditRepository);
    }

    @Test
    public void testLogAction() {
        mockStatic(RupContextUtils.class);
        expect(RupContextUtils.getUserName()).andReturn(USER_NAME).once();
        mockStatic(RupPersistUtils.class);
        expect(RupPersistUtils.generateUuid()).andReturn(AUDIT_ITEM_UID).once();
        ScenarioAuditItem auditItem = buildScenarioAuditItem();
        aclScenarioAuditRepository.insert(auditItem);
        expectLastCall().once();
        replay(RupContextUtils.class, RupPersistUtils.class, aclScenarioAuditRepository);
        aclScenarioAuditService.logAction(SCENARIO_UID, SCENARIO_ACTION_TYPE, SCENARIO_ACTION_REASON);
        verify(RupContextUtils.class, RupPersistUtils.class, aclScenarioAuditRepository);
    }

    @Test
    public void testGetActions() {
        List<ScenarioAuditItem> auditItems = Collections.singletonList(new ScenarioAuditItem());
        expect(aclScenarioAuditRepository.findByScenarioId(SCENARIO_UID)).andReturn(auditItems).once();
        replay(aclScenarioAuditRepository);
        assertSame(auditItems, aclScenarioAuditService.getActions(SCENARIO_UID));
        verify(aclScenarioAuditRepository);
    }

    private ScenarioAuditItem buildScenarioAuditItem() {
        ScenarioAuditItem auditItem = new ScenarioAuditItem();
        auditItem.setId(AUDIT_ITEM_UID);
        auditItem.setScenarioId(SCENARIO_UID);
        auditItem.setActionType(SCENARIO_ACTION_TYPE);
        auditItem.setActionReason(SCENARIO_ACTION_REASON);
        auditItem.setCreateUser(USER_NAME);
        auditItem.setUpdateUser(USER_NAME);
        return auditItem;
    }
}
