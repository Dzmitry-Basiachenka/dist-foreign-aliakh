package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.mockStatic;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.domain.UsageAuditItem;
import com.copyright.rup.dist.foreign.service.api.IUsageAuditService;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditWidget;
import com.copyright.rup.dist.foreign.ui.audit.impl.UsageHistoryWindow;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Verifies {@link SalAuditController}.
 * <p>
 * Copyright (C) 2020 copyright.com
 * <p>
 * Date: 12/16/2020
 *
 * @author Aliaksandr Liakh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Windows.class, OffsetDateTime.class, StreamSource.class})
public class SalAuditControllerTest {

    private SalAuditController controller;
    private IUsageAuditService usageAuditService;
    private ISalAuditWidget auditWidget;

    @Before
    public void setUp() {
        usageAuditService = createMock(IUsageAuditService.class);
        auditWidget = createMock(ISalAuditWidget.class);
        controller = new SalAuditController();
        Whitebox.setInternalState(controller, auditWidget);
        Whitebox.setInternalState(controller, usageAuditService);
    }

    @Test
    public void testOnFilterChanged() {
        auditWidget.refresh();
        expectLastCall().once();
        replay(auditWidget);
        controller.onFilterChanged();
        verify(auditWidget);
    }

    @Test
    public void testShowUsageHistory() {
        mockStatic(Windows.class);
        Capture<UsageHistoryWindow> windowCapture = new Capture<>();
        String usageId = "ddc391df-87e6-4b10-92ea-1cc2e295b487";
        String detailId = "11960c0e-1374-4eba-8658-bfe5e11bb304";
        List<UsageAuditItem> items = Collections.emptyList();
        expect(usageAuditService.getUsageAudit(usageId)).andReturn(items).once();
        Windows.showModalWindow(capture(windowCapture));
        expectLastCall().once();
        replay(Windows.class, usageAuditService);
        controller.showUsageHistory(usageId, detailId);
        verify(Windows.class, usageAuditService);
        assertNotNull(windowCapture.getValue());
    }

    @Test
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof SalAuditWidget);
    }
}
