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

import com.copyright.rup.dist.foreign.domain.UdmValueActionTypeEnum;
import com.copyright.rup.dist.foreign.domain.UdmValueAuditItem;
import com.copyright.rup.dist.foreign.repository.impl.UdmValueAuditRepository;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link UdmValueAuditService}.
 * <p>
 * Copyright (C) 2021 copyright.com
 * <p>
 * Date: 12/01/2021
 *
 * @author Aliaksandr Liakh
 */
public class UdmValueAuditServiceTest {

    private static final String UDM_VALUE_UID = "897b37e1-be26-42dd-975a-12161d57787a";
    private static final String REASON = "UDM Value batch for period '2021' was populated";

    private UdmValueAuditService udmValueAuditService;
    private UdmValueAuditRepository udmValueAuditRepository;

    @Before
    public void setUp() {
        udmValueAuditService = new UdmValueAuditService();
        udmValueAuditRepository = createMock(UdmValueAuditRepository.class);
        Whitebox.setInternalState(udmValueAuditService, udmValueAuditRepository);
    }

    @Test
    public void testLogAction() {
        Capture<UdmValueAuditItem> auditItemCapture = newCapture();
        udmValueAuditRepository.insert(capture(auditItemCapture));
        expectLastCall().once();
        replay(udmValueAuditRepository);
        udmValueAuditService.logAction(UDM_VALUE_UID, UdmValueActionTypeEnum.CREATED, REASON);
        UdmValueAuditItem auditItem = auditItemCapture.getValue();
        assertEquals(UDM_VALUE_UID, auditItem.getValueId());
        assertEquals(UdmValueActionTypeEnum.CREATED, auditItem.getActionType());
        assertEquals(REASON, auditItem.getActionReason());
        verify(udmValueAuditRepository);
    }

    @Test
    public void testGetUdmValueAudit() {
        List<UdmValueAuditItem> auditItems = Collections.emptyList();
        expect(udmValueAuditRepository.findByUdmValueId(UDM_VALUE_UID)).andReturn(auditItems).once();
        replay(udmValueAuditRepository);
        assertSame(auditItems, udmValueAuditService.getUdmValueAudit(UDM_VALUE_UID));
        verify(udmValueAuditRepository);
    }

    @Test
    public void testGetUserNames() {
        List<String> userNames = Arrays.asList("jjohn@copyright.com", "wjohn@copyright.com");
        expect(udmValueAuditRepository.findUserNames()).andReturn(userNames).once();
        replay(udmValueAuditRepository);
        assertSame(userNames, udmValueAuditService.getUserNames());
        verify(udmValueAuditRepository);
    }
}
