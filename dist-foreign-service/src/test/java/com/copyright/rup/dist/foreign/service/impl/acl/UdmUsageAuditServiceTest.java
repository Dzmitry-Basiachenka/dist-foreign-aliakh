package com.copyright.rup.dist.foreign.service.impl.acl;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.newCapture;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.copyright.rup.dist.foreign.domain.UsageActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.repository.impl.UdmUsageAuditRepository;
import com.google.common.collect.ImmutableSet;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmUsageAuditService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 06/09/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmUsageAuditServiceTest {

    private static final String UDM_USAGE_UID_1 = "897b37e1-be26-42dd-975a-12161d57787a";
    private static final String UDM_USAGE_UID_2 = "0e66da0c-4c14-471f-9f22-dc16a208c478";
    private static final String REASON = "Uploaded in 'UDM Batch 2021' Batch";

    private UdmUsageAuditService udmUsageAuditService;
    private UdmUsageAuditRepository udmUsageAuditRepository;

    @Before
    public void setUp() {
        udmUsageAuditService = new UdmUsageAuditService();
        udmUsageAuditRepository = createMock(UdmUsageAuditRepository.class);
        Whitebox.setInternalState(udmUsageAuditService, udmUsageAuditRepository);
    }

    @Test
    public void testLogAction() {
        Capture<UsageAuditItem> auditItemCapture = newCapture();
        udmUsageAuditRepository.insert(capture(auditItemCapture));
        expectLastCall().once();
        replay(udmUsageAuditRepository);
        udmUsageAuditService.logAction(UDM_USAGE_UID_1, UsageActionTypeEnum.LOADED, REASON);
        UsageAuditItem auditItem = auditItemCapture.getValue();
        assertEquals(UDM_USAGE_UID_1, auditItem.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals(REASON, auditItem.getActionReason());
        verify(udmUsageAuditRepository);
    }

    @Test
    public void testLogActionUdmUsageIds() {
        Capture<UsageAuditItem> auditItemCapture1 = newCapture();
        udmUsageAuditRepository.insert(capture(auditItemCapture1));
        expectLastCall().once();
        Capture<UsageAuditItem> auditItemCapture2 = newCapture();
        udmUsageAuditRepository.insert(capture(auditItemCapture2));
        expectLastCall().once();
        replay(udmUsageAuditRepository);
        udmUsageAuditService.logAction(ImmutableSet.of(UDM_USAGE_UID_1, UDM_USAGE_UID_2),
            UsageActionTypeEnum.LOADED, REASON);
        UsageAuditItem auditItem1 = auditItemCapture1.getValue();
        assertEquals(UDM_USAGE_UID_1, auditItem1.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, auditItem1.getActionType());
        assertEquals(REASON, auditItem1.getActionReason());
        UsageAuditItem auditItem = auditItemCapture2.getValue();
        assertEquals(UDM_USAGE_UID_2, auditItem.getUsageId());
        assertEquals(UsageActionTypeEnum.LOADED, auditItem.getActionType());
        assertEquals(REASON, auditItem.getActionReason());
        verify(udmUsageAuditRepository);
    }

    @Test
    public void testGetUdmUsageAudit() {
        List<UsageAuditItem> auditItems = Collections.emptyList();
        expect(udmUsageAuditRepository.findByUdmUsageId(UDM_USAGE_UID_1)).andReturn(auditItems).once();
        replay(udmUsageAuditRepository);
        assertSame(auditItems, udmUsageAuditService.getUdmUsageAudit(UDM_USAGE_UID_1));
        verify(udmUsageAuditRepository);
    }
}
