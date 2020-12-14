package com.copyright.rup.dist.foreign.ui.audit.impl.sal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import com.copyright.rup.dist.common.reporting.impl.StreamSource;
import com.copyright.rup.dist.foreign.ui.audit.api.sal.ISalAuditWidget;
import com.copyright.rup.vaadin.ui.component.window.Windows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.time.OffsetDateTime;

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
    private ISalAuditWidget auditWidget;

    @Before
    public void setUp() {
        auditWidget = createMock(ISalAuditWidget.class);
        controller = new SalAuditController();
        Whitebox.setInternalState(controller, auditWidget);
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
    public void testInstantiateWidget() {
        assertTrue(controller.instantiateWidget() instanceof SalAuditWidget);
    }
}
